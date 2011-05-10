package westernstyle.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import net.sf.nachocalendar.CalendarFactory;
import net.sf.nachocalendar.components.DateField;

import westernstyle.core.Invoice;
import westernstyle.DB.InvoiceDB;
import westernstyle.DB.SalesOrderDB;
import westernstyle.core.SalesOrder;
import westernstyle.DB.GetMax;

public class InvoiceTab extends JPanel
{
    private JTable table;
    private InvoiceDB invoiceDB;
    private SalesOrderTab salesOrderTab;
    
    public InvoiceTab(SalesOrderTab salesOrderTab)
    {
        invoiceDB = new InvoiceDB();
        this.salesOrderTab = salesOrderTab;
        
        this.setLayout(new GridLayout(1, 0));
        String columns[] = {"#", "id", "Invoice Nr", "Payment Date", "Amount"};
        table = createTable(columns);
        updateTable();

        JScrollPane scrollPane = new JScrollPane(table);
        JButton add = new JButton("Add");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                add();
            }
        });
        JButton edit = new JButton("Edit");
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (invoiceDB.getInvoices().isEmpty())
                    showError("No invoices have been added.",
                            "Error");
                else if (table.getSelectedRowCount() == 0)
                    showError("Invoice must be selected", "Error");
                else
                    edit((Integer)table.getValueAt(table.getSelectedRow(), 1));
            }
        });
        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 999, 1);
        final JSpinner searchField = new JSpinner(model);
        JLabel searchLabel = new JLabel(" Invoice Nr: ");
        JButton remove = new JButton("Remove");
        remove.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (invoiceDB.getInvoices().isEmpty())
                    showError("No invoices have been added.",
                            "Error");
                else if (table.getSelectedRowCount() == 0)
                    showError("Invoice must be selected", "Error");
                else
                    removeInvoice((Integer)table.
                            getValueAt(table.getSelectedRow(), 1));
            }
        });
        JButton search = new JButton("Search");
        search.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                showSearchResults((Integer)searchField.getValue());
            }
        });
        JToolBar toolBar = new JToolBar();
        toolBar.add(add);
        toolBar.add(edit);
        toolBar.add(remove);
        toolBar.add(searchLabel);
        toolBar.add(searchField);
        toolBar.add(search);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(toolBar, BorderLayout.PAGE_START);
        panel.add(scrollPane);

        this.add(panel);
        this.setVisible(true);
    }
    
    private JTable createTable(Object[] columnNames)
    {
        JTable localTable;

        DefaultTableModel model = new DefaultTableModel();

        for (Object columnName: columnNames)
            model.addColumn(columnName);

        localTable = new JTable(model);
        localTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        return localTable;
    }
    
    private void removeInvoice(int id)
    {
        Invoice invoice = invoiceDB.getInvoice(id);
        int invoiceNr = invoice.getInvoiceNo();
        int choice;
        SalesOrderDB salesOrderDB = new SalesOrderDB();
        ArrayList<SalesOrder> salesOrders = salesOrderDB.
                getSalesOrdersByInvoiceId(id);
        if (!salesOrders.isEmpty())
        {
            choice = JOptionPane.showConfirmDialog(
                    this,
                    "Removing invoice nr \""+ invoiceNr +"\" will also remove "
                    + "all associated sales orders. Are you sure you want to "
                    + "do that?",
                    "Removal",
                    JOptionPane.YES_NO_OPTION);
            if (choice == 0)
            {
                for (int i = 0; i < salesOrders.size(); i++)
                {
                    salesOrderDB.delete(salesOrders.get(i).getId());
                }
                salesOrderTab.updateTable();
            }   
        }
        else
        {
        choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to remove invoice nr \""
                + invoiceNr + "\" ?", "Removal",
                JOptionPane.YES_NO_OPTION);
        }
        if (choice == 0)
        {
            invoiceDB.delete(id);
            updateTable();
        }
    }
    
    private void showSearchResults(int invoiceNr)
    {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        while (model.getRowCount() > 0)
            model.removeRow(0);
        
        int count = invoiceDB.getInvoicesByNr(invoiceNr).size();

        for (int i = 0; i < count; i++)
        {
            Invoice invoice = invoiceDB.getInvoicesByNr(invoiceNr).get(i);
            Object[] data = {i+1, invoice.getId(),
                invoice.getInvoiceNo(), invoice.getPaymentDate().toString(), 
                invoice.getAmount()};
            model.addRow(data);
        }
    }
    
    private void updateTable()
    {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        while (model.getRowCount() > 0)
            model.removeRow(0);
        
        int count = invoiceDB.getInvoices().size();
        for (int i = 1; i <= count; i++)
        {
            Invoice invoice = invoiceDB.getInvoice(i);
            if (invoice != null)
            {
                Object[] data = {i, invoice.getId(),
                    invoice.getInvoiceNo(), invoice.getPaymentDate(),
                    invoice.getAmount()};
                model.addRow(data);
            }
            else
                count++;
        }
    }
    
    private void add()
    {
        final JDialog addDialog = dialog("Add Invoice");
        JPanel myPanel = (JPanel)addDialog.getContentPane().getComponent(0);
        
        final JSpinner invoiceNr = ((JSpinner)myPanel.getComponent(1));
        final DateField paymentDate = ((DateField)myPanel.getComponent(3));
        final JSpinner amount = ((JSpinner)myPanel.getComponent(5));

        JButton okButton = ((JButton)myPanel.getComponent(6));
        okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int nextId = GetMax.getMaxId("select max(id) from invoice") + 1;
                Invoice invoice = new Invoice(nextId);
                invoice.setInvoiceNo((Integer)invoiceNr.getValue());
                invoice.setPaymentDate((Date)paymentDate.getValue());
                invoice.setAmount((Integer)amount.getValue());
                invoiceDB.insertInvoice(invoice);
                addDialog.setVisible(false);
                updateTable();
            }
        });

        addDialog.setVisible(true);
    }
    
    private JDialog dialog(String title)
    {
        final JDialog dialog = new JDialog();
        dialog.setTitle(title);
        JPanel myPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        dialog.getContentPane().add(myPanel);
        myPanel.setBorder(new LineBorder(myPanel.getBackground(), 10));

        SpinnerModel model = new SpinnerNumberModel(1, 1, 999, 1);
        final JSpinner invoiceNr = new JSpinner(model);
        final DateField paymentDate = CalendarFactory.createDateField();
        paymentDate.setValue(new Date());
        model = new SpinnerNumberModel(1, 1, 999, 1);
        final JSpinner amount = new JSpinner(model);
        
        final JComponent[] inputs = new JComponent[] {invoiceNr, paymentDate, 
            amount};
        final JComponent[] labels = new JComponent[] {new JLabel("Invoice Nr"),
                new JLabel("Payment Date"), new JLabel("Amount")};
        for (int i = 0; i < inputs.length; i++)
        {
            labels[i].setSize(120, 20);
            inputs[i].setSize(150,20);
            myPanel.add(labels[i]);
            myPanel.add(inputs[i]);
        }
        JButton okButton = new JButton("Ok");
        myPanel.add(okButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dialog.setVisible(false);
            }
        });
        myPanel.add(cancelButton);
        dialog.setResizable(false);
        dialog.pack();

        return dialog;
    }
    
    private void showError(String message, String title)
    {
        JOptionPane.showMessageDialog(this, message, title,
            JOptionPane.ERROR_MESSAGE);
    }
    
    private void edit(int id)
    {
        final JDialog editDialog = dialog("Edit Invoice");
        JPanel myPanel = (JPanel)editDialog.getContentPane().getComponent(0);
        
        final Invoice invoice = invoiceDB.getInvoice(id);
        final JSpinner invoiceNr = ((JSpinner)myPanel.getComponent(1));
        invoiceNr.setValue(invoice.getInvoiceNo());
        final DateField paymentDate = ((DateField)myPanel.getComponent(3));
        paymentDate.setValue(invoice.getPaymentDate());
        //paymentDate.setBaseDate(invoice.getPaymentDate());
        final JSpinner amount = ((JSpinner)myPanel.getComponent(5));
        amount.setValue(invoice.getAmount());

        JButton okButton = ((JButton)myPanel.getComponent(6));
        okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                invoice.setInvoiceNo((Integer)invoiceNr.getValue());
                invoice.setPaymentDate((Date)paymentDate.getValue());
                invoice.setAmount((Integer)amount.getValue());
                invoiceDB.updateInvoice(invoice);
                editDialog.setVisible(false);
                updateTable();
            }
        });

        editDialog.setVisible(true);
    }    
}
