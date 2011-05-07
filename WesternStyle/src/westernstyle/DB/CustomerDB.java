package westernstyle.DB;

import westernstyle.core.Customer;
import java.sql.*;
import java.util.ArrayList;

public class CustomerDB
{
    private Connection con;

    public CustomerDB()
    {
        con = DBConnection.getInstance().getDBConnection();
    }

    public ArrayList<Customer> getCustomers()
    {
        return where("");
    }
    
    public ArrayList<Customer> getCustomersByName(String name)
    {
        if (!name.isEmpty())
            return where("name='"+name+"'");
        else
            return getCustomers();
    }
    
    public Customer getCustomer(int id)
    {
        return singleWhere("id = "+id);
    }
    
    private Customer singleWhere(String wClause)
    {
        ResultSet results;
        String query = buildQuery(wClause);
        Customer customer = null;
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            if(results.next())
            {
                customer = createCustomer(results);
            }
            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return  customer;
    }

    private ArrayList<Customer> where(String wClause)
    {
        ResultSet results;
        ArrayList<Customer> list = new ArrayList<Customer>();
        String query = buildQuery(wClause);
        
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            while(results.next())
            {
                Customer customer = createCustomer(results);
                list.add(customer);
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
        String query = "SELECT * FROM customer";
        if (!whereC.isEmpty())
        {
            query = query + " WHERE " + whereC;
        }
        return query;
    }
    
    private Customer createCustomer(ResultSet rs)
    {
        try
        {
            Customer customer = new Customer(rs.getInt("id"));
            customer.setName(rs.getString("name"));
            customer.setAddress(rs.getString("address"));
            customer.setCity(rs.getString("city"));
            customer.setPhoneno(rs.getString("phoneNo"));
            customer.setOrderZipCode(rs.getInt("zipCode"));
            return customer;
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
        String query = "DELETE FROM customer WHERE id="+id;
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
    public int insertCustomer(Customer customer)
    {
        //int nextId = GetMax.getMaxId("select max(id) from customer") + 1;
        int rc = -1;
        String query = "INSERT INTO customer(id,name,address,phoneNo,city,zipCode)"
                +"VALUES('"
                + customer.getId() + "','" 
                + customer.getName() + "','" 
                + customer.getAddress() + "','" 
                + customer.getPhoneno() + "','" 
                + customer.getCity() + "','" 
                + customer.getOrderZipCode() + "')";
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
    
    public int updateCustomer(Customer customer)
    {
        int rc = -1;
        String query = "Update customer SET "+
                "name ='" + customer.getName() + "', "+
                "address ='" + customer.getAddress() + "', "+
                "phoneNo ='" + customer.getPhoneno() + "', "+
                "city ='" + customer.getCity() + "', "+
                "zipCode ='" + customer.getOrderZipCode() + "' "+
                "WHERE id="+customer.getId();
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
