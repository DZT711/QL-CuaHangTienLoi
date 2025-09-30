package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import dto.KhachHangDTO;
import util.JDBCUtil;

public class KhachHangDAO {
    public static List<KhachHangDTO> getAllKhachHang() {
        String query = "SELECT MaKH, Ho, Ten, GioiTinh, NgaySinh, DienThoai, DiaChi FROM KHACHHANG";

        List<KhachHangDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                list.add(new KhachHangDTO(
                    rs.getString("MaKH"), 
                    rs.getString("Ho"), 
                    rs.getString("Ten"), 
                    rs.getString("GioiTinh"), 
                    rs.getDate("NgaySinh").toLocalDate(), 
                    rs.getString("DienThoai"), 
                    rs.getString("DiaChi")));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy tất cả khách hàng: " + e.getMessage());
        }
        return list;
    }

    public static void themKhachHang(KhachHangDTO kh) {
        String query = "INSERT INTO KHACHHANG (MaKH, Ho, Ten, GioiTinh, NgaySinh, DienThoai, DiaChi) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, kh.getMaKH());
            stmt.setString(2, kh.getHo());
            stmt.setString(3, kh.getTen());
            stmt.setString(4, kh.getGioiTinh());
            stmt.setDate(5, java.sql.Date.valueOf(kh.getNgaySinh()));
            stmt.setString(6, kh.getDienThoai());
            stmt.setString(7, kh.getDiaChi());
            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Thêm khách hàng thành công");
            } else {
                System.out.println("Thêm khách hàng thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm khách hàng: " + e.getMessage());
        }
    }

    public static void suaKhachHang(KhachHangDTO kh, String maKH) {
        String query = "UPDATE KHACHHANG SET Ho = ?, Ten = ?, GioiTinh = ?, NgaySinh = ?, DienThoai = ?, DiaChi = ? WHERE MaKH = ?";

        try (Connection conn  = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, kh.getHo());
            stmt.setString(2, kh.getTen());
            stmt.setString(3, kh.getGioiTinh());
            stmt.setDate(4, java.sql.Date.valueOf(kh.getNgaySinh()));
            stmt.setString(5, kh.getDienThoai());
            stmt.setString(6, kh.getDiaChi());
            stmt.setString(7, maKH);
            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Sửa khách hàng thành công");
            } else {
                System.out.println("Sửa khách hàng thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi sửa khách hàng: " + e.getMessage());
        }
    }

    public static void xoaKhachHang(String maKH) {
        String query = "DELETE FROM KHACHHANG WHERE MaKH = ?";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, maKH);
            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Xóa khách hàng thành công");
            } else {
                System.out.println("Xóa khách hàng thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa khách hàng: " + e.getMessage());        
        }
    }

    public static List<KhachHangDTO> timKhachHangTheoTen(String tenKH) {
        String query = "SELECT MaKH, Ho, Ten, GioiTinh, NgaySinh, DienThoai, DiaChi FROM KHACHHANG WHERE LOWER(Ten) LIKE ?";

        List<KhachHangDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + tenKH + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                list.add(new KhachHangDTO(rs.getString("MaKH"), rs.getString("Ho"), rs.getString("Ten"), rs.getString("GioiTinh"), rs.getDate("NgaySinh").toLocalDate(), rs.getString("DienThoai"), rs.getString("DiaChi")));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm khách hàng theo tên: " + e.getMessage());
        }

        return list;
    }

    public static KhachHangDTO timKhachHangTheoMa(String maKH) {
        String query = "SELECT MaKH, Ho, Ten, GioiTinh, NgaySinh, DienThoai, DiaChi FROM KHACHHANG WHERE MaKH = ?";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, maKH);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return new KhachHangDTO(
                    rs.getString("MaKH"), 
                    rs.getString("Ho"), 
                    rs.getString("Ten"), 
                    rs.getString("GioiTinh"), 
                    rs.getDate("NgaySinh").toLocalDate(), 
                    rs.getString("DienThoai"), 
                    rs.getString("DiaChi")
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm khách hàng theo mã: " + e.getMessage());
        }
        return null;
    }

    public static KhachHangDTO timKhachHangTheoDienThoai(String dienThoai){
        String query = "SELECT MaKH, Ho, Ten, GioiTinh, NgaySinh, DienThoai, DiaChi FROM KHACHHANG WHERE DienThoai = ?";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, dienThoai);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return new KhachHangDTO(
                    rs.getString("MaKH"), 
                    rs.getString("Ho"), 
                    rs.getString("Ten"), 
                    rs.getString("GioiTinh"), 
                    rs.getDate("NgaySinh").toLocalDate(), 
                    rs.getString("DienThoai"), 
                    rs.getString("DiaChi")
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm khách hàng theo điện thoại: " + e.getMessage());
        }
        return null;
    }

    public static boolean kiemTraMaKH(String maKH) {
        String query = "SELECT COUNT(*) FROM KHACHHANG WHERE MaKH = ?";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, maKH);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1) > 0;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra mã khách hàng: " + e.getMessage());
        }
        return false;
    }
}