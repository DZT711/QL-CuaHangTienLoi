package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import dto.ChiTietHoaDonDTO;
import util.JDBCUtil;

public class ChiTietHoaDonDAO {
    public static boolean themChiTietHoaDon(ChiTietHoaDonDTO ctHoaDon) {
        String query = "INSERT INTO CHITIETHOADON (MaHD, MaHang, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, ctHoaDon.getMaHD());
            stmt.setString(2, ctHoaDon.getMaHang());
            stmt.setInt(3, ctHoaDon.getSoLuong());
            stmt.setInt(4, ctHoaDon.getDonGia());
            stmt.setInt(5, ctHoaDon.getThanhTien());

            int rowAffected = stmt.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thêm chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static List<ChiTietHoaDonDTO> timChiTietHoaDon(String maHD) {
        if (maHD == null || maHD.trim().isEmpty()) {
            System.err.println("❌ Mã hóa đơn không được rỗng!");
            return new ArrayList<>();
        }

        String query = """
            SELECT ct.MaHang, sp.TenSP, ct.SoLuong, ct.DonGia, ct.ThanhTien 
            FROM CHITIETHOADON ct 
            INNER JOIN HANGHOA hh ON ct.MaHang = hh.MaHang 
            INNER JOIN SANPHAM sp ON hh.MaSP = sp.MaSP 
            WHERE ct.MaHD = ?
        """;

        List<ChiTietHoaDonDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maHD);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    try {
                        list.add(new ChiTietHoaDonDTO(
                            maHD,
                            rs.getString("MaHang"),
                            rs.getString("TenSP"),
                            rs.getInt("SoLuong"),
                            rs.getInt("DonGia"),
                            rs.getInt("ThanhTien")
                        ));
                    } catch (SQLException rowEx) {
                        System.err.println("❌ Lỗi đọc dòng dữ liệu: " + rowEx.getMessage());
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}
