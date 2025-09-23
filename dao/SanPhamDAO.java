package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import dto.sanPhamDTO;
import src.database.JDBCUtil;

public class SanPhamDAO {
    public static List<sanPhamDTO> getAllSanPham() {
        String query = "SELECT maSP, tenSP, loaiSP, soLuongTon, gia, hSD, moTa FROM SANPHAM\n" +
        "FROM SANPHAM\n" +
        "INNER JOIN LOAI ON SANPHAM.loai = LOAI.MaLoai\n" +
        "INNER JOIN DONVI ON SANPHAM.donVi = DONVI.DonViTinh\n";

        List<sanPhamDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                list.add(new sanPhamDTO(rs.getString("maSP"), rs.getString("tenSP"), rs.getString("loaiSP"), rs.getInt("soLuongTon"), rs.getInt("gia"), rs.getInt("hSD"), rs.getString("moTa")));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy tất cả sản phẩm: " + e.getMessage());
        }
        return list;
    }

    public static List<sanPhamDTO> timSanPhamTheoTen(String name) throws Exception{
        String query = "SELECT maSP, tenSP, loaiSP, soLuongTon, gia, hSD, moTa FROM SANPHAM\n" +
        "INNER JOIN LOAI ON SANPHAM.loai = LOAI.MaLoai\n" +
        "INNER JOIN DONVI ON SANPHAM.donVi = DONVI.DonViTinh\n" +
        "WHERE tenSP LIKE ?"

        List<sanPhamDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            stmt.setString(1, "%" + name + "%");        
            while(rs.next()){
                list.add(new sanPhamDTO(rs.getString("maSP"), rs.getString("tenSP"), rs.getString("loaiSP"), rs.getInt("soLuongTon"), rs.getInt("gia"), rs.getInt("hSD"), rs.getString("moTa")));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm sản phẩm theo tên: " + e.getMessage());
        }
        return list;
    }

    public static List<sanPhamDTO> timSanPhamTheoMa(String ma) {
        String query = "SELECT maSP, tenSP, loaiSP, soLuongTon, gia, hSD, moTa FROM SANPHAM\n" +
        "INNER JOIN LOAI ON SANPHAM.loai = LOAI.MaLoai\n" +
        "INNER JOIN DONVI ON SANPHAM.donVi = DONVI.DonViTinh\n" +
        "WHERE maSP LIKE ?"

        List<sanPhamDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            stmt.setString(1, "%" + ma + "%");
            while(rs.next()){
                list.add(new sanPhamDTO(rs.getString("maSP"), rs.getString("tenSP"), rs.getString("loaiSP"), rs.getInt("soLuongTon"), rs.getInt("gia"), rs.getInt("hSD"), rs.getString("moTa")));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm sản phẩm theo mã: " + e.getMessage());
        }
        return list;
    }

    public static void themSanPham(sanPhamDTO sp, int loai, int donVi) {
        String query = "INSERT INTO SANPHAM (maSP, tenSP, loaiSP, soLuongTon, gia, hSD, moTa) VALUES (?, ?, ?, ?, 0, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sp.getMaSP());
            stmt.setString(2, sp.getTenSP());
            stmt.setString(3, sp.getLoaiSP());
            stmt.setInt(4, sp.getSoLuongTon());
            stmt.setInt(5, sp.getGia());
            stmt.setInt(6, sp.getHSD());
            stmt.setString(7, sp.getMoTa());
            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Thêm sản phẩm thành công");
            } else {
                System.out.println("Thêm sản phẩm thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
        }
    }

    public static void suaSanPham(sanPhamDTO sp, int loai, int donVi) {
        String query = "UPDATE SANPHAM SET tenSP = ?, loaiSP = ?, soLuongTon = ?, gia = ?, hSD = ?, moTa = ? WHERE maSP = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sp.getTenSP());
            stmt.setString(2, loai);
            stmt.setInt(3, donVi);
            stmt.setInt(4, sp.getGia());
            stmt.setInt(5, sp.getHSD());
            stmt.setString(6, sp.getMoTa());
            stmt.setString(7, sp.getMaSP());
            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Sửa sản phẩm thành công");
            } else {
                System.out.println("Sửa sản phẩm thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi sửa sản phẩm theo mã: " + e.getMessage());
        }
    }

    public static void xoaSanPham(String ma) {
        String query = "DELETE FROM SANPHAM WHERE maSP = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ma);
            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Xóa sản phẩm thành công");
            } else {
                System.out.println("Xóa sản phẩm thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa sản phẩm theo mã: " + e.getMessage());
        }
    }
}

