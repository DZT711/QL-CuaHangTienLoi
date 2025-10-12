package util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    public static Connection conn = null;
    // Connect to database
    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            String url  = "jdbc:mysql://localhost:3306/QL_chtienloi";
            String username = "root";
            String password = "";

            if (conn == null) {
                conn = DriverManager.getConnection(url, username, password);
            }
            return conn;
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