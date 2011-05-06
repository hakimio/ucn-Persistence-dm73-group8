package westernstyle.DB;

import java.sql.*;
import java.util.ArrayList;
import westernstyle.core.GunReplica;
import westernstyle.core.Product;

public class GunReplicaDB
{
    private Connection con;

    public GunReplicaDB()
    {
        con = DBConnection.getInstance().getDBConnection();
    }
    public ArrayList<GunReplica> getEquipment()
    {
        return where("");
    }
    
    public GunReplica getGunReplica(int id)
    {
        return singleWhere("id = "+id);
    }
    
    private GunReplica singleWhere(String wClause)
    {
        ResultSet results;
        String query = buildQuery(wClause);
        GunReplica gunReplica = null;
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            if(results.next())
            {
                gunReplica = createGunReplica(results);
            }
            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return  gunReplica;
    }

    private ArrayList<GunReplica> where(String wClause)
    {
        ResultSet results;
        ArrayList<GunReplica> list = new ArrayList<GunReplica>();
        String query = buildQuery(wClause);
        
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            while(results.next())
            {
                GunReplica gunReplica = createGunReplica(results);
                list.add(gunReplica);
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
        String query = "SELECT * FROM gunReplica";
        if (!whereC.isEmpty())
        {
            query = query + " WHERE " + whereC;
        }
        return query;
    }
    
    private GunReplica createGunReplica(ResultSet rs)
    {
        try
        {
            GunReplica gunReplica = new GunReplica(rs.getInt("productId"));
            gunReplica.setFabric(rs.getString("fabric"));
            gunReplica.setCalibre(rs.getString("calibre"));
            ProductDB productDB = new ProductDB();
            Product product = productDB.getProduct(rs.getInt("productId"));
            gunReplica.setName(product.getName());
            gunReplica.setPurchasePrice(product.getPurchasePrice());
            gunReplica.setSalesPrice(product.getSalesPrice());
            gunReplica.setRentPrice(product.getRentPrice());
            gunReplica.setCountryOfOrigin(product.getCountryOfOrigin());
            gunReplica.setMinStock(product.getMinStock());
            gunReplica.setSupplier(product.getSupplier());
            return gunReplica;
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
        String query = "DELETE FROM gunReplica WHERE id="+id;
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
    public int insertGunReplica(GunReplica gunReplica)
    {
        int nextId = GetMax.getMaxId("select max(id) from gunReplica") + 1;
        int rc = -1;
        String query = "INSERT INTO gunReplica(id,productId,fabric,calibre)"
                +"VALUES('"
                + nextId + "','" 
                + gunReplica.getId() + "','" 
                + gunReplica.getFabric() + "','" 
                + gunReplica.getCalibre() + ")";
        ProductDB productDB = new ProductDB();
        productDB.insertProduct(gunReplica);
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
    
    public int updateGunReplica(GunReplica gunReplica)
    {
        int rc = -1;
        String query = "Update gunReplica SET "+
                "fabric ='" + gunReplica.getFabric() + "'"+
                "calibre ='" + gunReplica.getCalibre() + "'"+
                "WHERE productId="+ gunReplica.getId();
        ProductDB productDB = new ProductDB();
        productDB.updateProduct(gunReplica);
        
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
