package htsc.src_BE.util;

// import java.sql.Connection;
// import java.sql.DatabaseMetaData;
// import java.sql.DriverManager;
// import java.sql.SQLException;

// public class JDBCUtil {
//     // Connect to database

//     public static Connection getConnection() {
//         Connection conn = null;

//         try {
//             DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

//             String url = "jdbc:mysql://localhost:3306/QL_chtienloi";
//             String username = "root";
//             String password = "";

//             conn = DriverManager.getConnection(url, username, password);
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return conn;
//     }

//     // Close connection
//     public static void closeConnection(Connection conn) {
//         try {
//             if (conn != null) {
//                 conn.close();
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     // Print database info
//     public static void printDatabaseInfo(Connection conn) {
//         try {
//             DatabaseMetaData md = conn.getMetaData();
//             System.out.println("Database Name: " + md.getDatabaseProductName());
//             System.out.println("Database Version: " + md.getDatabaseProductVersion());
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }
// }


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {

    public static Connection getConnection() {
        Connection c = null;
        try {
            // 1. Khai báo thông số (Nên để vào file config sau này)
            String url = "jdbc:mysql://localhost:3306/ql_cuahang"; // Thay ql_cuahang bằng tên DB của bạn
            String user = "root";
            String password = "";

            // 2. Đăng ký MySQL Driver (Với Maven/JDBC 4.0+, dòng này có thể bỏ qua)
            // Nhưng viết vào để đảm bảo tính tương thích cao nhất
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 3. Tạo kết nối
            c = DriverManager.getConnection(url, user, password);
            
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy Driver MySQL! Hãy kiểm tra file pom.xml");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối Database! Hãy kiểm tra URL/User/Password");
            e.printStackTrace();
        }
        return c;
    }

    public static void closeConnection(Connection c) {
        try {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
