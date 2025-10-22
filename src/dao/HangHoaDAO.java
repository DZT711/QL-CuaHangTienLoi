package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import dto.HangHoaDTO;
import util.JDBCUtil;

public class HangHoaDAO {
    public static HangHoaDTO timHangHoaTheoMa(String maHang) {
        String query = "SELECT * FROM HANGHOA WHERE MaHang = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maHang);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new HangHoaDTO(
                    rs.getString("MaHang"),
                    rs.getString("MaSP"),
                    rs.getInt("SoLuongConLai"),
                    rs.getDate("NgaySanXuat").toLocalDate(),
                    rs.getDate("HanSuDung").toLocalDate(),
                    rs.getString("TrangThai")
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm hàng hóa: " + e.getMessage());
        }
        return null;
    }

    public static void truSoLuongConLai(String maHang, int soLuong) {
        String query = "UPDATE HANGHOA SET SoLuongConLai = SoLuongConLai - ? WHERE MaHang = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, soLuong);
            stmt.setString(2, maHang);

            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Cập nhật số lượng hàng hóa thành công");
            } else {
                System.out.println("Cập nhật số lượng hàng hóa thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật số lượng hàng hóa: " + e.getMessage());
        }
    }
}
