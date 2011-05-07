package westernstyle.DB;

import java.sql.*;
import westernstyle.core.Supplier;
import java.util.ArrayList;

public class SupplierDB
{
    private Connection con;
    
    public SupplierDB()
    {
        con = DBConnection.getInstance().getDBConnection();
    }
    
    public ArrayList<Supplier> getSuppliers()
    {
        return where("");
    }
    
    public Supplier getSupplier(int id)
    {
        return singleWhere("id = "+id);
    }
    
    private Supplier singleWhere(String wClause)
    {
        ResultSet results;
        String query = buildQuery(wClause);
        Supplier supplier = null;
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            if(results.next())
            {
                supplier = createSupplier(results);
            }
            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return  supplier;
    }

    private ArrayList<Supplier> where(String wClause)
    {
        ResultSet results;
        ArrayList<Supplier> list = new ArrayList<Supplier>();
        String query = buildQuery(wClause);
        
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            while(results.next())
            {
                Supplier supplier = createSupplier(results);
                list.add(supplier);
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
        String query = "SELECT * FROM supplier";
        if (!whereC.isEmpty())
        {
            query = query + " WHERE " + whereC;
        }
        return query;
    }
    
    private Supplier createSupplier(ResultSet rs)
    {
        try
        {
            Supplier supplier = new Supplier(rs.getInt("id"));
            supplier.setName(rs.getString("name"));
            supplier.setAddress(rs.getString("address"));
            supplier.setCountry(rs.getString("country"));
            supplier.setPhoneno(rs.getString("phoneNo"));
            supplier.setEmail(rs.getString("email"));
            return supplier;
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
        String query = "DELETE FROM supplier WHERE id="+id;
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
    public int insertSupplier(Supplier supplier)
    {
        //int nextId = GetMax.getMaxId("select max(id) from supplier") + 1;
        int rc = -1;
        String query = "INSERT INTO supplier(id,name,address,phoneNo,country,email)"
                +"VALUES('"
                + supplier.getId() + "','" 
                + supplier.getName() + "','" 
                + supplier.getAddress() + "','" 
                + supplier.getPhoneno() + "','" 
                + supplier.getCountry() + "','" 
                + supplier.getEmail() + "')";
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
    
    public int updateSupplier(Supplier supplier)
    {
        int rc = -1;
        String query = "Update customer SET "+
                "name ='" + supplier.getName() + "'"+
                "address ='" + supplier.getAddress() + "'"+
                "phoneNo ='" + supplier.getPhoneno() + "'"+
                "country ='" + supplier.getCountry() + "'"+
                "email ='" + supplier.getEmail() + "'"+
                "WHERE id="+supplier.getId();
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
