package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;

public class JDBCUtil {
    // Connect to database
    public static Connection getConnection() {
        Connection conn = null;
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            String url  = "jdbc:mysql://localhost:3306/QL_chtienloi";
            String username = "root";
            String password = "";

            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // Close connection
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Print database info
    public static void printDatabaseInfo(Connection conn) {
        try {
            DatabaseMetaData md = conn.getMetaData();
            System.out.println("Database Name: " + md.getDatabaseProductName());
            System.out.println("Database Version: " + md.getDatabaseProductVersion());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}