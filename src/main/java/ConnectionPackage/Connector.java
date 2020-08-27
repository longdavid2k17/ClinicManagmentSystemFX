package ConnectionPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector
{
    static Connection connection = null;

    public static Connection getConnection()
    {
        try
        {
            String url = "jdbc:mysql://localhost:3306/clinic";
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        }
        catch (SQLException  e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
