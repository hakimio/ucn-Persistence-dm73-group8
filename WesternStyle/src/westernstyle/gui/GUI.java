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
        jTabbedPane.add("Customers", new CustomerTab());
        jTabbedPane.add("Invoices", new InvoiceTab());
        jTabbedPane.add("Sales Orders", new SalesOrderTab());
        
        add(jTabbedPane);
        pack();
        setVisible(true);
    }
}
