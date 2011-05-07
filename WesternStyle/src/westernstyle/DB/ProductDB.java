package westernstyle.DB;

import java.sql.*;
import java.util.ArrayList;
import westernstyle.core.Product;
import westernstyle.core.Supplier;

public class ProductDB
{
    private Connection con;
    
    public ProductDB()
    {
        con = DBConnection.getInstance().getDBConnection();
    }
    
    public ArrayList<Product> getProducts()
    {
        return where("");
    }
    
    public Product getProduct(int id)
    {
        return singleWhere("id = "+id);
    }
    
    private Product singleWhere(String wClause)
    {
        ResultSet results;
        String query = buildQuery(wClause);
        Product product = null;
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            if(results.next())
            {
                product = createProduct(results);
            }
            
            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return  product;
    }

    private ArrayList<Product> where(String wClause)
    {
        ResultSet results;
        ArrayList<Product> list = new ArrayList<Product>();
        String query = buildQuery(wClause);
        
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            
            while(results.next())
            {
                Product product = createProduct(results);                
                list.add(product);
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
        String query = "SELECT * FROM product";
        if (!whereC.isEmpty())
        {
            query = query + " WHERE " + whereC;
        }
        return query;
    }
    
    private Product createProduct(ResultSet rs)
    {
        try
        {
            Product product = new Product(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setMinStock(rs.getInt("minStock"));
            product.setPurchasePrice(rs.getDouble("purchasePrice"));
            product.setRentPrice(rs.getDouble("rentPrice"));
            product.setSalesPrice(rs.getDouble("salesPrice"));
            product.setCountryOfOrigin(rs.getString("countryOfOrigin"));
            SupplierDB supplierDB = new SupplierDB();
            Supplier supplier = supplierDB.getSupplier(rs.getInt("supplierId"));
            product.setSupplier(supplier);
            return product;
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
        String query = "DELETE FROM product WHERE id="+id;
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
    public int insertProduct(Product product)
    {
        //int nextId = GetMax.getMaxId("select max(id) from invoice") + 1;
        int rc = -1;
        String query = "INSERT INTO product(id,name,purchasePrice,salesPrice,"
                + "rentPrice,countryOfOrigin,minStock,supplierId)"
                +"VALUES('"
                + product.getId() + "','" 
                + product.getName() + "','" 
                + product.getPurchasePrice() + "','" 
                + product.getSalesPrice() + "','" 
                + product.getRentPrice() + "','" 
                + product.getCountryOfOrigin() + "','" 
                + product.getMinStock() + "','" 
                + product.getSupplier().getId() + "')";
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
    
    public int updateProduct(Product product)
    {
        int rc = -1;
        String query = "Update product SET "+
                "name ='" + product.getName() + "', "+
                "purchasePrice ='" + product.getPurchasePrice() + "', "+
                "salesPrice ='" + product.getSalesPrice() + "', "+
                "rentPrice ='" + product.getRentPrice() + "', "+
                "countryOfOrigin ='" + product.getCountryOfOrigin() + "', "+
                "minStock ='" + product.getMinStock() + "', "+
                "supplierId ='" + product.getSupplier().getId() + "' "+
                "WHERE id="+product.getId();
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
