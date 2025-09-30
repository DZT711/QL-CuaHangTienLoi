package dao;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import dto.sanPhamDTO;
import database.JDBCUtil;

public class SanPhamDAO {
    public static List<sanPhamDTO> getAllSanPham() {
        String query = "SELECT MaSP, TenSP, Loai, SoLuongTon, DonViTinh, GiaBan, NgaySanXuat, HanSuDung, MoTa FROM SANPHAM\n" +
        "FROM SANPHAM\n" +
        "INNER JOIN LOAI ON SANPHAM.loai = LOAI.MaLoai\n" +
        "INNER JOIN DONVI ON SANPHAM.donVi = DONVI.DonViTinh\n";

        List<sanPhamDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                LocalDate ngaySanXuat = rs.getDate("NgaySanXuat").toLocalDate();

                // HSD (Date -> int ddMMyyyy)
                LocalDate hSD = rs.getDate("HanSuDung").toLocalDate();
                int hsdInt = hSD.getDayOfMonth() * 1000000
                           + hSD.getMonthValue() * 10000
                           + hSD.getYear();

                
                list.add(new sanPhamDTO(
                    rs.getString("MaSP"), 
                    rs.getString("TenSP"), 
                    rs.getInt("Loai"), 
                    rs.getInt("DonViTinh"), 
                    rs.getInt("SoLuongTon"), 
                    rs.getInt("GiaBan"), 
                    ngaySanXuat, 
                    hsdInt, 
                    rs.getString("MoTa")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy tất cả sản phẩm: " + e.getMessage());
        }
        return list;
    }

    public static List<sanPhamDTO> timSanPhamTheoTen(String name) throws Exception{
        String query = "SELECT MaSP, TenSP, Loai, SoLuongTon, DonViTinh, GiaBan, NgaySanXuat, HanSuDung, MoTa FROM SANPHAM\n" +
        "INNER JOIN LOAI ON SANPHAM.loai = LOAI.MaLoai\n" +
        "INNER JOIN DONVI ON SANPHAM.donVi = DONVI.DonViTinh\n" +
        "WHERE tenSP LIKE ?";

        List<sanPhamDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            stmt.setString(1, "%" + name + "%");        
            while(rs.next()){
                LocalDate ngaySanXuat = rs.getDate("NgaySanXuat").toLocalDate();

                // HSD (Date -> int ddMMyyyy)
                LocalDate hSD = rs.getDate("HanSuDung").toLocalDate();
                int hsdInt = hSD.getDayOfMonth() * 1000000
                           + hSD.getMonthValue() * 10000
                           + hSD.getYear();


                list.add(new sanPhamDTO(
                    rs.getString("MaSP"), 
                    rs.getString("TenSP"), 
                    rs.getInt("Loai"), 
                    rs.getInt("DonViTinh"), 
                    rs.getInt("SoLuongTon"), 
                    rs.getInt("GiaBan"), 
                    ngaySanXuat, 
                    hsdInt, 
                    rs.getString("MoTa")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm sản phẩm theo tên: " + e.getMessage());
        }
        return list;
    }

    public static sanPhamDTO timSanPhamTheoMa(String ma) {
        String query = "SELECT MaSP, TenSP, Loai, SoLuongTon, DonViTinh, GiaBan, NgaySanXuat, HanSuDung, MoTa FROM SANPHAM\n" +
        "INNER JOIN LOAI ON SANPHAM.loai = LOAI.MaLoai\n" +
        "INNER JOIN DONVI ON SANPHAM.donVi = DONVI.DonViTinh\n" +
        "WHERE maSP = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ma);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                LocalDate ngaySanXuat = rs.getDate("NgaySanXuat").toLocalDate();

                // HSD (Date -> int ddMMyyyy)
                LocalDate hSD = rs.getDate("HanSuDung").toLocalDate();
                int hsdInt = hSD.getDayOfMonth() * 1000000
                           + hSD.getMonthValue() * 10000
                           + hSD.getYear();



                return new sanPhamDTO(
                    rs.getString("MaSP"), 
                    rs.getString("TenSP"), 
                    rs.getInt("Loai"), 
                    rs.getInt("DonViTinh"), 
                    rs.getInt("SoLuongTon"), 
                    rs.getInt("GiaBan"), 
                    ngaySanXuat, 
                    hsdInt, 
                    rs.getString("MoTa")
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm sản phẩm theo mã: " + e.getMessage());
        }
        return null;
    }

    public static void themSanPham(sanPhamDTO sp, int loai, int donVi) {
        String query = "INSERT INTO SANPHAM (MaSP, TenSP, Loai, DonViTinh, GiaBan, NgaySanXuat, HanSuDung, MoTa) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sp.getMaSP());
            stmt.setString(2, sp.getTenSP());
            stmt.setInt(3, sp.getLoaiSP());
            stmt.setInt(4, sp.getDonViTinh());
            stmt.setInt(5, sp.getGiaBan());
            stmt.setDate(6, Date.valueOf(sp.getNgaySanXuat()));
            
            // HSD (int ddMMyyyy -> LocalDate -> Date)
            int hSD = sp.getHSD();
            String hSDStr = String.format("%08d", hSD);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            LocalDate hSDDate = LocalDate.parse(hSDStr, formatter);
            stmt.setDate(7, Date.valueOf(hSDDate));

            stmt.setString(8, sp.getMoTa());
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

    public static void suaSanPham(sanPhamDTO sp, String maSP) {
        String query = "UPDATE SANPHAM SET TenSP = ?, LoaiSP = ?, DonViTinh = ?, GiaBan = ?, NgaySanXuat = ?, HanSuDung = ?, MoTa = ? WHERE MaSP = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sp.getTenSP());
            stmt.setInt(2, sp.getLoaiSP());
            stmt.setInt(3, sp.getDonViTinh());
            stmt.setInt(4, sp.getGiaBan());
            stmt.setDate(5, Date.valueOf(sp.getNgaySanXuat()));

            // HSD (int ddMMyyyy -> LocalDate -> Date)
            int hSD = sp.getHSD();
            String hSDStr = String.format("%08d", hSD);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            LocalDate hSDDate = LocalDate.parse(hSDStr, formatter);
            stmt.setDate(6, Date.valueOf(hSDDate));

            
            stmt.setString(7, sp.getMoTa());
            stmt.setString(8, maSP);
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