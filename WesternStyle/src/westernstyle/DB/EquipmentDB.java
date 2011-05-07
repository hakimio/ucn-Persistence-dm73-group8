package westernstyle.DB;
import java.util.ArrayList;
import java.sql.*;
import westernstyle.core.Equipment;
import westernstyle.core.Product;

public class EquipmentDB
{
    private Connection con;
    
    public EquipmentDB()
    {
        con = DBConnection.getInstance().getDBConnection();
    }
    
    public ArrayList<Equipment> getEquipment()
    {
        return where("");
    }

    public Equipment getEquipmentByProductId(int productId)
    {
        return singleWhere("productId = "+productId);
    }
    
    public Equipment getEquipment(int id)
    {
        return singleWhere("id = "+id);
    }
    
    private Equipment singleWhere(String wClause)
    {
        ResultSet results;
        String query = buildQuery(wClause);
        Equipment equipment = null;
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            if(results.next())
            {
                equipment = createEquipment(results);
            }
            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return  equipment;
    }

    private ArrayList<Equipment> where(String wClause)
    {
        ResultSet results;
        ArrayList<Equipment> list = new ArrayList<Equipment>();
        String query = buildQuery(wClause);
        
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            while(results.next())
            {
                Equipment equipment = createEquipment(results);
                list.add(equipment);
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
        String query = "SELECT * FROM equipment";
        if (!whereC.isEmpty())
        {
            query = query + " WHERE " + whereC;
        }
        return query;
    }
    
    private Equipment createEquipment(ResultSet rs)
    {
        try
        {
            Equipment equipment = new Equipment(rs.getInt("productId"));
            equipment.setType(rs.getString("type"));
            equipment.setDescription(rs.getString("description"));
            ProductDB productDB = new ProductDB();
            Product product = productDB.getProduct(rs.getInt("productId"));
            equipment.setName(product.getName());
            equipment.setPurchasePrice(product.getPurchasePrice());
            equipment.setSalesPrice(product.getSalesPrice());
            equipment.setRentPrice(product.getRentPrice());
            equipment.setCountryOfOrigin(product.getCountryOfOrigin());
            equipment.setMinStock(product.getMinStock());
            equipment.setSupplier(product.getSupplier());
            return equipment;
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
        String query = "DELETE FROM equipment WHERE id="+id;
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
    public int insertEquipment(Equipment equipment)
    {
        int nextId = GetMax.getMaxId("select max(id) from equipment") + 1;
        int rc = -1;
        String query = "INSERT INTO equipment(id,productId,type,description)"
                +"VALUES('"
                + nextId + "','" 
                + equipment.getId() + "','" 
                + equipment.getType() + "','" 
                + equipment.getDescription() + "')";
        ProductDB productDB = new ProductDB();
        productDB.insertProduct(equipment);
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
    
    public int updateEquipment(Equipment equipment)
    {
        int rc = -1;
        String query = "Update equipment SET "+
                "type ='" + equipment.getType() + "'"+
                "description ='" + equipment.getDescription() + "'"+
                "WHERE productId="+ equipment.getId();
        ProductDB productDB = new ProductDB();
        productDB.updateProduct(equipment);
        
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
