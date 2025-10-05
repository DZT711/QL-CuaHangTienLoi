package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.JDBCUtil;

public final class IdUtil {
    private IdUtil() {}

    public static String generateId() {
        String prefix = "HD";
        String newID = prefix + "001";
        String query = "SELECT MaHD FROM HOADON ORDER BY MaHD DESC LIMIT 1";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();) {

            if (rs.next()) {
                String lastID = rs.getString("MaHD");
                int number = Integer.parseInt(lastID.substring(2));
                number++;
                newID = prefix + String.format("%03d", number);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tạo mã hóa đơn: " + e.getMessage());
        }
        return newID;
    }
}
