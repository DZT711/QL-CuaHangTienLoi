package dao;

import dto.ChiTietPhieuNhapDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import util.JDBCUtil;

public class ChiTietPhieuNhapDAO {
    public static void themChiTietPhieuNhap(ChiTietPhieuNhapDTO ctPhieuNhap) {
        String query = "INSERT INTO CHITIETPHIEUNHAP (MaPhieu, MaSP, SoLuong, GiaNhap, ThanhTien) VALUES (?, ?, ?, ?, ?);";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ctPhieuNhap.getMaPhieu());
            stmt.setString(2, ctPhieuNhap.getMaSP());
            stmt.setInt(3, ctPhieuNhap.getSoLuong());
            stmt.setInt(4, ctPhieuNhap.getGiaNhap());
            stmt.setInt(5, ctPhieuNhap.getThanhTien());

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

    public static List<ChiTietPhieuNhapDTO> timChiTietPhieuNhap(String maPhieu) {
        String query = """
                SELECT ctpn.MaSP, sp.TenSP, dv.TenDonVi, ctpn.SoLuong, ctpn.GiaNhap, ctpn.ThanhTien
                FROM CHITIETPHIEUNHAP ctpn
                INNER JOIN SANPHAM sp ON ctpn.MaSP = sp.MaSP
                INNER JOIN DONVI dv ON sp.MaDonVi = dv.MaDonVi
                INNER JOIN PHIEUNHAP pn ON ctpn.MaPhieu = pn.MaPhieu
                WHERE ctpn.MaPhieu = ?;
        """;

        List<ChiTietPhieuNhapDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maPhieu);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new ChiTietPhieuNhapDTO(
                    maPhieu,
                    rs.getString("MaSP"),
                    rs.getString("TenSP"),
                    rs.getString("DonViTinh"),
                    rs.getInt("SoLuong"),
                    rs.getInt("GiaNhap"),
                    rs.getInt("ThanhTien")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm chi tiết phiếu nhập: " + e.getMessage());
        }
        return list;
    }
}
