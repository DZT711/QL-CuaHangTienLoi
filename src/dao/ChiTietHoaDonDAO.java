package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import dto.ChiTietHoaDonDTO;
import util.JDBCUtil;

public class ChiTietHoaDonDAO {
    public static void themChiTietHoaDon(ChiTietHoaDonDTO ctHoaDon) {
        String query = "INSERT INTO CHITIETHOADON (MaHD, MaSP, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?);";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setString(1, ctHoaDon.getMaHD());
            stmt.setString(2, ctHoaDon.getMaSP());
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


    // Làm lại giao diện cho giống thực tế, đẹp hơn
    public static void inChiTietHoaDon(ChiTietHoaDonDTO ctHoaDon) {
        String query = 
            "SELECT sp.TenSP, ct.SoLuong, ct.DonGia, ct.ThanhTien " + 
            "FROM CHITIETHOADON ct " +
            "INNER JOIN SANPHAM sp ON ct.MaSP = sp.MaSP " +
            "WHERE ct.MaHD = ?";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, ctHoaDon.getMaHD());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String tenSP = rs.getString("TenSP");
                int soLuong = rs.getInt("SoLuong");
                int donGia = rs.getInt("DonGia");
                int thanhTien = rs.getInt("ThanhTien");

                System.out.println(tenSP + " | " + soLuong + " | " + donGia + " | " + thanhTien);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi in chi tiết hóa đơn: " + e.getMessage());
        }
    }
}
