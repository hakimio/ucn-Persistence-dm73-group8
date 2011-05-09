package westernstyle.gui;

import java.awt.Dimension;
import javax.swing.*;

public class GUI extends JFrame
{   
    public GUI()
    {
        super("Western Style");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(640, 480));
        
        JTabbedPane jTabbedPane = new JTabbedPane();
        SalesOrderTab salesOrderTab = new SalesOrderTab();
        jTabbedPane.add("Customers", new CustomerTab(salesOrderTab));
        jTabbedPane.add("Invoices", new InvoiceTab(salesOrderTab));
        jTabbedPane.add("Sales Orders", salesOrderTab);
        
        add(jTabbedPane);
        pack();
        setVisible(true);
    }
}
