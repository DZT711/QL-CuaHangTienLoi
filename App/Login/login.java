package Login;
import database.JDBCUtil;
import java.sql.*;
import java.util.Scanner;

public class login {
    public void LoginForm() throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String username, password;

        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                          ĐĂNG NHẬP HỆ THỐNG                                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("──────────────────────────────────────────────────────────────────────────────");
        System.out.print("\n│  Tên đăng nhập : ");
        username = scanner.nextLine();
        

        // Clear the previous line and print password prompt
        System.out.print("\n│  Mật khẩu      : ");
        password = scanner.nextLine();
        System.out.println("\n──────────────────────────────────────────────────────────────────────────────");

        // Add loading animation
        System.out.print("\nVui lòng chờ 1 lát , hệ thống đang xác thực");
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(1000);
                System.out.print(".");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\n");
        try(Connection conn = JDBCUtil.getConnection()){
            if (conn != null) {
                // System.out.println("Kết nối cơ sở dữ liệu thành công!");
                String query="SELECT * FROM taikhoan WHERE UserName=? AND PassWord=?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    System.out.println("╔════════════════════════════════════════════════════════════════════════════╗");
                    System.out.println("\n   ĐĂNG NHẬP THÀNH CÔNG! XIN CHÀO  " + username.toUpperCase() + " \n                     ");
                    System.out.println("╚════════════════════════════════════════════════════════════════════════════╝");
                } else {
                    System.out.println("╔════════════════════════════════════════════════════════════════════════════╗");
                    System.out.println("║              ĐĂNG NHẬP THẤT BẠI! VUI LÒNG KIỂM TRA LẠI                     ║");
                    System.out.println("╚════════════════════════════════════════════════════════════════════════════╝");
                }
            } else {
                System.out.println("Kết nối cơ sở dữ liệu thất bại!");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }
}