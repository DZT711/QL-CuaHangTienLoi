package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import dto.HoaDonDTO;
import src.database.JDBCUtil;

public class HoaDonDAO {
    public static List<HoaDonDTO> getAllHoaDon() {
        String query = "SELECT MaHD, MaKH, MaNV, MaKM, TongTien, NgayLapHD FROM HOADON";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                list.add(new HoaDonDTO(
                    rs.getString("MaHD"), 
                    rs.getString("MaKH"), 
                    rs.getString("MaNV"), 
                    rs.getString("MaKM"), 
                    rs.getInt("TongTien"), 
                    rs.getDate("NgayLapHD"))
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy tất cả hóa đơn: " + e.getMessage());
        }
        return list;
    }

    

    public static void themHoaDon(HoaDonDTO hd) {
        String query = "INSERT INTO HOADON (MaHD, MaKH, MaNV, MaKM, TongTien, NgayLapHD) VALUES (?, ?, ?, ?, ?, current_timestamp());";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, hd.getMaHD());
            stmt.setString(2, hd.getMaKH());
            stmt.setString(3, hd.getMaNV());
            stmt.setString(4, hd.getMaKM());
            stmt.setInt(5, hd.getTongTien());
            stmt.setDate(6, hd.getNgayLapHD());
            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Thêm hóa đơn thành công");
            } else {
                System.out.println("Thêm hóa đơn thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm hóa đơn: " + e.getMessage());
        }
    }

    public static void xoaHoaDon(String maHD) {

        String query = "DELETE FROM HOADON WHERE MaHD = ?";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, maHD);
            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Xóa hóa đơn thành công");
            } else {
                System.out.println("Xóa hóa đơn thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa hóa đơn: " + e.getMessage());
        }
    }

    
}
