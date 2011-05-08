package westernstyle.DB;

import java.sql.*;
import westernstyle.core.Invoice;
import java.util.ArrayList;

public class InvoiceDB
{
    private Connection con;
    
    public InvoiceDB()
    {
        con = DBConnection.getInstance().getDBConnection();
    }
    
    public ArrayList<Invoice> getInvoices()
    {
        return where("");
    }

    public ArrayList<Invoice> getInvoicesByNr(int invoiceNr)
    {
        if (invoiceNr != 0)
            return where("invoiceNo = "+invoiceNr);
        else
            return getInvoices();
    }
    public Invoice getInvoice(int id)
    {
        return singleWhere("id = "+id);
    }
    
    private Invoice singleWhere(String wClause)
    {
        ResultSet results;
        String query = buildQuery(wClause);
        Invoice invoice = null;
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            if(results.next())
            {
                invoice = createInvoice(results);
            }
            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return  invoice;
    }

    private ArrayList<Invoice> where(String wClause)
    {
        ResultSet results;
        ArrayList<Invoice> list = new ArrayList<Invoice>();
        String query = buildQuery(wClause);
        
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            while(results.next())
            {
                Invoice invoice = createInvoice(results);
                list.add(invoice);
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
        String query = "SELECT * FROM invoice";
        if (!whereC.isEmpty())
        {
            query = query + " WHERE " + whereC;
        }
        return query;
    }
    
    private Invoice createInvoice(ResultSet rs)
    {
        try
        {
            Invoice invoice = new Invoice(rs.getInt("id"));
            invoice.setInvoiceNo(rs.getInt("invoiceNo"));
            invoice.setPaymentDate(rs.getDate("paymentDate"));
            invoice.setAmount(rs.getInt("amount"));
            return invoice;
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
        String query = "DELETE FROM invoice WHERE id="+id;
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
    public int insertInvoice(Invoice invoice)
    {
        //int nextId = GetMax.getMaxId("select max(id) from invoice") + 1;
        int rc = -1;
        String query = "INSERT INTO invoice(id,invoiceNo,paymentDate,amount)"
                +"VALUES('"
                + invoice.getId() + "','" 
                + invoice.getInvoiceNo() + "','" 
                + new Date (invoice.getPaymentDate().getTime()) + "','" 
                + invoice.getAmount() + "')";
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
    
    public int updateInvoice(Invoice invoice)
    {
        int rc = -1;
        Date paymentDate = new Date(invoice.getPaymentDate().getTime());
        String query = "Update invoice SET "+
                "invoiceNo ='" + invoice.getInvoiceNo() + "', "+
                "paymentDate ='" + paymentDate + "', "+
                "amount ='" + invoice.getAmount() + "' "+
                "WHERE id="+invoice.getId();
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
