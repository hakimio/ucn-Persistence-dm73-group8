package westernstyle.gui;

import javax.swing.*;

public class GUI extends JFrame
{   
    public GUI()
    {
        super("Western Style");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.add("Customers", new CustomerTab());
        
        add(jTabbedPane);
        pack();
        setVisible(true);
    }
}
