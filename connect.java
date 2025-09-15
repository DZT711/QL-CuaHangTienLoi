import java.sql.*;

public class connect {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/ql-douong?serverTimezone=UTC";
        String user = "root";
        String pass = ""; // XAMPP mặc định MySQL không có mật khẩu

        try {
            // Tai Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Thiet lap ket noi
            try (Connection conn = DriverManager.getConnection(url, user, pass)) {
                System.out.println("Ket noi thanh cong!");
                
                // Tao Statement de thuc thi cau lenh SQL
                try (Statement stmt = conn.createStatement()) {

                    // Truy cap ket qua truy van
                    ResultSet rs = stmt.executeQuery("SELECT NOW()");
                    if (rs.next()) {
                        
                        // Xuat ket qua 
                        System.out.println("Time DB: " + rs.getString(1));
                    }
                    rs.close();
                    stmt.close();
                }
                conn.close();
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy driver JDBC!");
        } catch (SQLException e) {
            System.err.println("Loi ket noi: " + e.getMessage());
        }
    }
}
// con.setAutoCommit(false);

// PreparedStatement ps1 = con.prepareStatement("UPDATE accounts SET balance=balance-100 WHERE id=1");
// PreparedStatement ps2 = con.prepareStatement("UPDATE accounts SET balance=balance+100 WHERE id=2");

// ps1.executeUpdate();
// ps2.executeUpdate();

// con.commit();  // commit if both succeed
// con.rollback(); // rollback if error