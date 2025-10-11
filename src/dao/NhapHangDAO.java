package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.JDBCUtil;

public class NhapHangDAO {
    public static String generateMaPhieuNhap() {
        String prefix = "PN";
        String newID = prefix + "001";
        String query = "SELECT MaPhieu FROM PHIEUNHAP ORDER BY MaPhieu DESC LIMIT 1";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();) {

            if (rs.next()) {
                String lastID = rs.getString("MaPhieu");
                int number = Integer.parseInt(lastID.substring(2));
                number++;
                newID = prefix + String.format("%03d", number);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tạo mã phiếu nhập: " + e.getMessage());
        }
        return newID;
    }
}
