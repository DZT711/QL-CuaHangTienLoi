package dao;

import dto.NhaCungCapDTO;
import util.JDBCUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhaCungCapDAO {

    // Lấy danh sách tất cả nhà cung cấp
    public static List<NhaCungCapDTO> getAllNhaCungCap() {
        List<NhaCungCapDTO> list = new ArrayList<>();
        String query = "SELECT MaNCC, TenNCC, DiaChi, DienThoai, Email, TrangThai FROM NHACUNGCAP";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new NhaCungCapDTO(
                        rs.getString("MaNCC"),
                        rs.getString("TenNCC"),
                        rs.getString("DiaChi"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getString("TrangThai")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách nhà cung cấp: " + e.getMessage());
        }
        return list;
    }

    // Tìm nhà cung cấp theo mã
    public static NhaCungCapDTO timnccTheoMa(String ma) {
        String query = "SELECT * FROM NHACUNGCAP WHERE MaNCC = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ma);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new NhaCungCapDTO(
                        rs.getString("MaNCC"),
                        rs.getString("TenNCC"),
                        rs.getString("DiaChi"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getString("TrangThai")
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm NCC theo mã: " + e.getMessage());
        }
        return null;
    }

    // Tìm nhà cung cấp theo tên
    public static List<NhaCungCapDTO> timnccTheoTen(String ten) {
        List<NhaCungCapDTO> list = new ArrayList<>();
        String query = "SELECT * FROM NHACUNGCAP WHERE TenNCC LIKE ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + ten + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new NhaCungCapDTO(
                        rs.getString("MaNCC"),
                        rs.getString("TenNCC"),
                        rs.getString("DiaChi"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getString("TrangThai")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm NCC theo tên: " + e.getMessage());
        }
        return list;
    }

    // Thêm nhà cung cấp mới
    public static boolean themNCC(NhaCungCapDTO ncc) {
        String query = "INSERT INTO NHACUNGCAP (MaNCC, TenNCC, DiaChi, DienThoai, Email, TrangThai) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ncc.getMaNCC());
            stmt.setString(2, ncc.getTenNCC());
            stmt.setString(3, ncc.getDiaChi());
            stmt.setString(4, ncc.getDienThoai());
            stmt.setString(5, ncc.getEmail());
            stmt.setString(6, ncc.getTrangThai());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm nhà cung cấp: " + e.getMessage());
            return false;
        }
    }

    // Sửa thông tin nhà cung cấp
    public static void suaNhaCungCap(NhaCungCapDTO ncc) {
        String query = "UPDATE NHACUNGCAP SET TenNCC=?, DiaChi=?, DienThoai=?, Email=?, TrangThai=? WHERE MaNCC=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ncc.getTenNCC());
            stmt.setString(2, ncc.getDiaChi());
            stmt.setString(3, ncc.getDienThoai());
            stmt.setString(4, ncc.getEmail());
            stmt.setString(5, ncc.getTrangThai());
            stmt.setString(6, ncc.getMaNCC());

            int rows = stmt.executeUpdate();
             if (rows > 0) {
                System.out.println("Sửa nhà cung cấp thành công");
            } else {
                System.out.println("Sửa nhà cung cấp thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi sửa thông tin NCC: " + e.getMessage());
            
        }
    }

    // Xóa (hoặc chuyển trạng thái inactive)
    public static boolean xoaNCC(String maNCC) {
        String query = "UPDATE NHACUNGCAP SET TrangThai='inactive' WHERE MaNCC=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maNCC);    
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa NCC: " + e.getMessage());
        }
        return false;
    }
    
public static void xuatDanhSachNCC() {
    List<NhaCungCapDTO> list = getAllNhaCungCap();

    System.out.println("================================ DANH SÁCH NHÀ CUNG CẤP ================================");
    System.out.printf("%-10s | %-25s | %-25s | %-12s | %-25s | %-10s\n",
            "Mã NCC", "Tên nhà cung cấp", "Địa chỉ", "Điện thoại", "Email", "Trạng thái");
    System.out.println("----------------------------------------------------------------------------------------");

    if (list.isEmpty()) {
        System.out.println("⚠️  Không có nhà cung cấp nào trong hệ thống.");
    } else {
        for (NhaCungCapDTO ncc : list) {
            ncc.inThongTinNCC();
        }
    }

    System.out.println("========================================================================================");
}
}
