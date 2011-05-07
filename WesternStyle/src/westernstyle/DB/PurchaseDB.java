package westernstyle.DB;

import java.sql.*;
import java.util.ArrayList;
import westernstyle.core.Purchase;
import westernstyle.core.Product;
import westernstyle.core.SalesOrder;
import westernstyle.core.Clothing;
import westernstyle.core.Equipment;
import westernstyle.core.GunReplica;

public class PurchaseDB
{
    private Connection con;
    
    public PurchaseDB()
    {
        con = DBConnection.getInstance().getDBConnection();
    }
    public ArrayList<Purchase> getPurchases()
    {
        return where("");
    }
    
    public Purchase getPurchase(int id)
    {
        return singleWhere("id = "+id);
    }
    
    private Purchase singleWhere(String wClause)
    {
        ResultSet results;
        String query = buildQuery(wClause);
        Purchase purchase = null;
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            if(results.next())
            {
                purchase = createPurchase(results);
            }

            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return  purchase;
    }

    private ArrayList<Purchase> where(String wClause)
    {
        ResultSet results;
        ArrayList<Purchase> list = new ArrayList<Purchase>();
        String query = buildQuery(wClause);
        
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            while(results.next())
            {
                Purchase purchase = createPurchase(results);                
                list.add(purchase);
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
        String query = "SELECT * FROM purchase";
        if (!whereC.isEmpty())
        {
            query = query + " WHERE " + whereC;
        }
        return query;
    }
    
    private Purchase createPurchase(ResultSet rs)
    {
        try
        {
            Purchase purchase = new Purchase(rs.getInt("id"));
            
            SalesOrderDB salesOrderDB = new SalesOrderDB();
            SalesOrder salesOrder = salesOrderDB.getSalesOrder(rs.getInt("salesOrderId"));
            purchase.setSalesOrder(salesOrder);
            
            ProductDB productDB = new ProductDB();
            Product product = productDB.getProduct(rs.getInt("productId"));
            ClothingDB clothingDB = new ClothingDB();
            EquipmentDB equipmentDB = new EquipmentDB();
            GunReplicaDB gunReplicaDB = new GunReplicaDB();
            
            int productId = product.getId();
            Clothing clothing = clothingDB.getClothingByProductId(productId);
            Equipment equipment = equipmentDB.getEquipmentByProductId(productId);
            GunReplica gunReplica = gunReplicaDB.getGunReplicaByProductId(productId);
            
            if (clothing != null)
            {
                purchase.setProduct(clothing);
            }
            else if (equipment != null)
            {
                purchase.setProduct(equipment);
            }
            else if (gunReplica != null)
            {
                purchase.setProduct(gunReplica);
            }
            
            return purchase;
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
        String query = "DELETE FROM purchase WHERE id="+id;
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
    public int insertPurchase(Purchase purchase)
    {
        //int nextId = GetMax.getMaxId("select max(id) from invoice") + 1;
        int rc = -1;
        String query = "INSERT INTO purchase(id,productId,salesOrderId)"
                +"VALUES('"
                + purchase.getId() + "','" 
                + purchase.getProduct().getId() + "','" 
                + purchase.getSalesOrder().getId() + "')";
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
    
    public int updatePurchase(Purchase purchase)
    {
        int rc = -1;
        String query = "Update purchase SET "+
                "productId ='" + purchase.getProduct().getId() + "', "+
                "salesOrderId ='" + purchase.getSalesOrder().getId() + "' "+
                "WHERE id=" + purchase.getId();
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
