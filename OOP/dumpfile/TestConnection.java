import java.sql.Connection;

import database.JDBCUtil;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("🔌 Đang test kết nối database...");
        
        Connection conn = JDBCUtil.getConnection();
        if (conn != null) {
            System.out.println("✅ Kết nối database thành công!");
            JDBCUtil.closeConnection(conn);
        } else {
            System.out.println("❌ Kết nối database thất bại!");
        }
    }
}
