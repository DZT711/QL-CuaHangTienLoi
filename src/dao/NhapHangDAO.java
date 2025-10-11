package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.NhapHangDTO;
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

    public static void themPhieuNhap(NhapHangDTO pn) {
        String query = "INSERT INTO PHIEUNHAP (MaPhieu, MaNCC, MaNV, TongTien) VALUES (?, ?, ?, ?);";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pn.getMaPhieu());
            stmt.setString(2, pn.getMaNCC());
            stmt.setString(3, pn.getMaNV());
            stmt.setInt(4, pn.getTongTien());

            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Thêm phiếu nhập thành công");
            } else {
                System.out.println("Thêm phiếu nhập thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm phiếu nhập: " + e.getMessage());
        }
    }
}
