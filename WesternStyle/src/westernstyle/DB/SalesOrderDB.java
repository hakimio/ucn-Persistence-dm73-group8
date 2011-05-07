package westernstyle.DB;

import java.sql.*;
import java.util.ArrayList;
import westernstyle.core.SalesOrder;
import westernstyle.core.Invoice;
import westernstyle.core.Customer;

public class SalesOrderDB
{
    private Connection con;
    
    public SalesOrderDB()
    {
        con = DBConnection.getInstance().getDBConnection();
    }
    
    public ArrayList<SalesOrder> getSalesOrders()
    {
        return where("");
    }
    
    public SalesOrder getSalesOrder(int id)
    {
        return singleWhere("id = "+id);
    }
    
    private SalesOrder singleWhere(String wClause)
    {
        ResultSet results;
        String query = buildQuery(wClause);
        SalesOrder salesOrder = null;
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            if(results.next())
            {
                salesOrder = createSalesOrder(results);
            }

            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return  salesOrder;
    }

    private ArrayList<SalesOrder> where(String wClause)
    {
        ResultSet results;
        ArrayList<SalesOrder> list = new ArrayList<SalesOrder>();
        String query = buildQuery(wClause);
        
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            while(results.next())
            {
                SalesOrder salesOrder = createSalesOrder(results);                
                list.add(salesOrder);
            }
            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return list;
    }

    private String buildQuery(String whereC)
    {
        String query = "SELECT * FROM salesOrder";
        if (!whereC.isEmpty())
        {
            query = query + " WHERE " + whereC;
        }
        return query;
    }
    
    private SalesOrder createSalesOrder(ResultSet rs)
    {
        try
        {
            SalesOrder salesOrder = new SalesOrder(rs.getInt("id"));
            salesOrder.setAmount(rs.getInt("amount"));
            salesOrder.setDate(rs.getDate("date"));
            salesOrder.setDeliveryDate(rs.getDate("deliveryDate"));
            salesOrder.setDeliveryStatus(rs.getString("deliveryStatus"));
            
            CustomerDB customerDB = new CustomerDB();
            Customer customer = customerDB.getCustomer(rs.getInt("customerId"));
            salesOrder.setCustomer(customer);
            InvoiceDB invoiceDB = new InvoiceDB();
            Invoice invoice = invoiceDB.getInvoice(rs.getInt("invoiceId"));
            salesOrder.setInvoice(invoice);
            
            return salesOrder;
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    public int delete(int id)
    {
        //row count
        int rc = -1;
        String query = "DELETE FROM salesOrder WHERE id="+id;
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            rc = stmt.executeUpdate(query);
            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return rc;
    }
    //@SuppressWarnings("empty-statement")
    public int insertSalesOrder(SalesOrder salesOrder)
    {
        //int nextId = GetMax.getMaxId("select max(id) from invoice") + 1;
        int rc = -1;
        String query = "INSERT INTO salesOrder(id,date,amount,deliveryStatus,"
                + "deliveryDate,customerId,InvoiceId)"
                +"VALUES('"
                + salesOrder.getId() + "','" 
                + salesOrder.getDate() + "','" 
                + salesOrder.getDeliveryStatus() + "','" 
                + salesOrder.getDeliveryDate() + "','" 
                + salesOrder.getCustomer().getId() + "','" 
                + salesOrder.getInvoice().getId() + "')";
        try
        {
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            rc = stmt.executeUpdate(query);
            stmt.close();
            con.commit();
            con.setAutoCommit(true);
            
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            try
            {
                rc = -1;
                con.rollback();
                con.setAutoCommit(true);
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
        }
        return rc;
    }
    
    public int updateSalesOrder(SalesOrder salesOrder)
    {
        int rc = -1;
        String query = "Update salesOrder SET "+
                "date ='" + salesOrder.getDate() + "', "+
                "amount ='" + salesOrder.getAmount() + "', "+
                "deliveryStatus ='" + salesOrder.getDeliveryStatus() + "', "+
                "deliveryDate ='" + salesOrder.getDeliveryDate() + "', "+
                "customerId ='" + salesOrder.getCustomer().getId() + "', "+
                "invoiceId ='" + salesOrder.getInvoice().getId() + "' "+
                "WHERE id="+salesOrder.getId();
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            rc = stmt.executeUpdate(query);
            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return rc;
    }
}
