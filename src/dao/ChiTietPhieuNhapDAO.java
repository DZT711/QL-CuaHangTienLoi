package dao;

import dto.ChiTietPhieuNhapDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.JDBCUtil;

public class ChiTietPhieuNhapDAO {
    public static void themChiTietPhieuNhap(ChiTietPhieuNhapDTO ctPhieuNhap) {
        String query = "INSERT INTO CHITIETPHIEUNHAP (MaPhieu, MaSP, SoLuong, GiaNhap) VALUES (?, ?, ?, ?);";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ctPhieuNhap.getMaPhieu());
            stmt.setString(2, ctPhieuNhap.getMaSP());
            stmt.setInt(3, ctPhieuNhap.getSoLuong());
            stmt.setInt(4, ctPhieuNhap.getGiaNhap());

            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                SanPhamDAO.congSoLuongTon(ctPhieuNhap.getMaSP(), ctPhieuNhap.getSoLuong());
                System.out.println("Thêm chi tiết phiếu nhập thành công");
            } else {
                System.out.println("Thêm chi tiết phiếu nhập thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm chi tiết phiếu nhập: " + e.getMessage());
        }
    }
}
