package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.TaiKhoanDTO;
import util.JDBCUtil;

public class TaiKhoanDAO {
    public static TaiKhoanDTO checkAccount(String username, String password) {
        TaiKhoanDTO taiKhoan = null;

        String query = "SELECT * FROM TAIKHOAN WHERE UserName = ? AND PassWord = ?";

        try {
            Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("VaiTro");
                String fullName = rs.getString("HoTen");
                String maNV = rs.getString("MaNV");
                String status = rs.getString("TrangThai");
                taiKhoan = new TaiKhoanDTO(username, password, maNV, role, fullName, status);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra tài khoản: " + e.getMessage());
        }
        return taiKhoan;
    }

    public static boolean lockAccountByEmployee(String maNV) {
        String sql = "UPDATE TAIKHOAN SET TrangThai = 'Inactive' WHERE MaNV = ?";
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNV);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi khóa tài khoản theo MaNV: " + e.getMessage());
            return false;
        }
    }

    public static boolean accountExistsForEmployee(String maNV) {
        String sql = "SELECT 1 FROM TAIKHOAN WHERE MaNV = ?";
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNV);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra tồn tại tài khoản theo MaNV: " + e.getMessage());
            return false;
        }
    }

    public static boolean createDefaultAccountForEmployee(String maNV, String hoTen, String vaiTro, String email) {
        // Username mặc định = MaNV, mật khẩu mặc định = MaNV, trạng thái Active
        if (maNV == null || maNV.isEmpty())
            return false;
        // Tránh tạo trùng
        if (accountExistsForEmployee(maNV))
            return true;

        String mappedRole = mapChucVuToVaiTro(vaiTro);
        String sql = "INSERT INTO TAIKHOAN (UserName, PassWord, MaNV, VaiTro, HoTen, TrangThai, Email) VALUES (?, ?, ?, ?, ?, 'Active', ?)";
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNV);
            ps.setString(2, maNV);
            ps.setString(3, maNV);
            ps.setString(4, mappedRole);
            ps.setString(5, hoTen != null ? hoTen : maNV);
            ps.setString(6, email);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi tạo tài khoản mặc định cho nhân viên: " + e.getMessage());
            return false;
        }
    }

    private static String mapChucVuToVaiTro(String chucVu) {
        if (chucVu == null)
            return "NhanVien";
        String cv = chucVu.trim();
        if (cv.equalsIgnoreCase("QL") || cv.equalsIgnoreCase("QuanLy") || cv.equalsIgnoreCase("Admin")) {
            return "Admin";
        }
        // Mặc định là nhân viên để phù hợp ENUM('Admin','NhanVien')
        return "NhanVien";
    }
}
