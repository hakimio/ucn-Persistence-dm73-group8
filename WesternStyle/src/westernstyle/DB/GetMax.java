package westernstyle.DB;

import java.sql.*;

public class GetMax
{
    public static int getMaxId(String query)
    {
        ResultSet results;
        int id = -1;
        try
        {
            Connection con = DBConnection.getInstance().getDBConnection();
            Statement stmt = con.createStatement();
            results = stmt.executeQuery(query);
            
            if (results.next())
                id = results.getInt(1);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return id;
    }
}
