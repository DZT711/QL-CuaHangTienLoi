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
    public static void themChiTietHoaDon(ChiTietHoaDonDTO ctHoaDon) {
        String query = "INSERT INTO CHITIETHOADON (MaHD, MaHang, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?);";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setString(1, ctHoaDon.getMaHD());
            stmt.setString(2, ctHoaDon.getMaHang());
            stmt.setInt(3, ctHoaDon.getSoLuong());
            stmt.setInt(4, ctHoaDon.getDonGia());
            stmt.setInt(5, ctHoaDon.getThanhTien());

            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Thêm chi tiết hóa đơn thành công");
            } else {
                System.out.println("Thêm chi tiết hóa đơn thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm chi tiết hóa đơn: " + e.getMessage());
        }
    }

    
    public static List<ChiTietHoaDonDTO> timChiTietHoaDon(String maHD) {
        String query = 
            "SELECT sp.MaSP, sp.TenSP, ct.SoLuong, ct.DonGia, ct.ThanhTien " + 
            "FROM CHITIETHOADON ct " +
            "INNER JOIN SANPHAM sp ON ct.MaSP = sp.MaSP " +
            "WHERE ct.MaHD = ?";

        List<ChiTietHoaDonDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, maHD);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(
                    new ChiTietHoaDonDTO(
                        maHD,
                        rs.getString("MaSP"),
                        rs.getString("TenSP"),
                        rs.getInt("SoLuong"),
                        rs.getInt("DonGia"),
                        rs.getInt("ThanhTien")
                    )
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm chi tiết hóa đơn: " + e.getMessage());
        }
        return list;
    }
}
