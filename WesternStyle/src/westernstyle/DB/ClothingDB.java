package westernstyle.DB;

import java.sql.*;
import java.util.ArrayList;
import westernstyle.core.Clothing;
import westernstyle.core.Product;

public class ClothingDB
{
    private Connection con;
    
    public ClothingDB()
    {
        con = DBConnection.getInstance().getDBConnection();
    }
    
    public ArrayList<Clothing> getClothings()
    {
        return where("");
    }
    
    public Clothing getClothing(int id)
    {
        return singleWhere("id = "+id);
    }
    
    private Clothing singleWhere(String wClause)
    {
        ResultSet results;
        String query = buildQuery(wClause);
        Clothing clothing = null;
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            if(results.next())
            {
                clothing = createClothing(results);
            }
            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return  clothing;
    }

    private ArrayList<Clothing> where(String wClause)
    {
        ResultSet results;
        ArrayList<Clothing> list = new ArrayList<Clothing>();
        String query = buildQuery(wClause);
        
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            while(results.next())
            {
                Clothing clothing = createClothing(results);
                list.add(clothing);
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
        String query = "SELECT * FROM clothing";
        if (!whereC.isEmpty())
        {
            query = query + " WHERE " + whereC;
        }
        return query;
    }
    
    private Clothing createClothing(ResultSet rs)
    {
        try
        {
            Clothing clothing = new Clothing(rs.getInt("productId"));
            clothing.setColour(rs.getString("colour"));
            clothing.setSize(rs.getInt("size"));
            ProductDB productDB = new ProductDB();
            Product product = productDB.getProduct(rs.getInt("productId"));
            clothing.setName(product.getName());
            clothing.setPurchasePrice(product.getPurchasePrice());
            clothing.setSalesPrice(product.getSalesPrice());
            clothing.setRentPrice(product.getRentPrice());
            clothing.setCountryOfOrigin(product.getCountryOfOrigin());
            clothing.setMinStock(product.getMinStock());
            clothing.setSupplier(product.getSupplier());
            return clothing;
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
        String query = "DELETE FROM clothing WHERE id="+id;
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
    public int insertClothing(Clothing clothing)
    {
        int nextId = GetMax.getMaxId("select max(id) from clothing") + 1;
        int rc = -1;
        String query = "INSERT INTO clothing(id,productId,size,colour)"
                +"VALUES('"
                + nextId + "','" 
                + clothing.getId() + "','" 
                + clothing.getSize() + "','" 
                + clothing.getColour() + ")";
        ProductDB productDB = new ProductDB();
        productDB.insertProduct(clothing);
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
    
    public int updateClothing(Clothing clothing)
    {
        int rc = -1;
        String query = "Update clothing SET "+
                "size ='" + clothing.getSize() + "'"+
                "colour ='" + clothing.getColour() + "'"+
                "WHERE productId="+clothing.getId();
        ProductDB productDB = new ProductDB();
        productDB.updateProduct(clothing);
        
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
