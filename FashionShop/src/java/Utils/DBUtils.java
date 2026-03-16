
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    public static Connection getConnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        String url = "jdbc:sqlserver://localhost:1433;databaseName=FashionShopDB;encrypt=true;trustServerCertificate=true;";
        
        String username = "sa"; 
        String password = "12345"; 
        
        return DriverManager.getConnection(url, username, password);
    }
}