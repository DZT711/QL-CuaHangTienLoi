package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.TaiKhoanDTO;
import util.JDBCUtil;
import org.mindrot.jbcrypt.BCrypt;

public class TaiKhoanDAO {

    public static TaiKhoanDTO checkAccount(String username, String password) {
        TaiKhoanDTO taiKhoan = null;

        // Bước 1: Tìm username trong database
        String query = "SELECT * FROM TAIKHOAN WHERE UserName = ?";

        try {
            Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Bước 2: Lấy password từ database
                String passwordFromDB = rs.getString("PassWord");

                // Bước 3: Xác thực mật khẩu (plain text)
                boolean passwordValid = password.equals(passwordFromDB);

                if (passwordValid) {
                    // Bước A thành công - Mật khẩu đúng, xác thực thành công
                    String role = rs.getString("VaiTro");
                    String fullName = rs.getString("HoTen");
                    String maNV = rs.getString("MaNV");
                    String status = rs.getString("TrangThai");

                    // Bước B: Kiểm tra mật khẩu mặc định (Default Check)
                    boolean isDefault = isDefaultPassword(passwordFromDB, maNV);

                    // Không lưu mật khẩu thô vì lý do bảo mật
                    taiKhoan = new TaiKhoanDTO(username, "***", maNV, role, fullName, status, isDefault);
                } else {
                    // Mật khẩu sai
                    System.out.println("Sai mật khẩu");
                }
            } else {
                // Username không tồn tại
                System.out.println("Tên đăng nhập không tồn tại");
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
            ps.setString(2, maNV); // Lưu plain text password (mặc định là MaNV)
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

    /**
     * Hash password sử dụng BCrypt
     * 
     * @param plainPassword Mật khẩu văn bản thô
     * @return Mật khẩu đã được hash
     */
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * Kiểm tra mật khẩu có đúng không
     * 
     * @param plainPassword  Mật khẩu văn bản thô
     * @param hashedPassword Mật khẩu đã hash
     * @return true nếu mật khẩu đúng, false nếu sai
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    /**
     * Bước B: Kiểm tra mật khẩu mặc định (Default Check)
     * 
     * @param hashedPasswordFromDB Mật khẩu đã hash từ database
     * @param maNV                 Mã nhân viên (dùng làm mật khẩu mặc định)
     * @return true nếu đang sử dụng mật khẩu mặc định, false nếu đã đổi
     */
    public static boolean isDefaultPassword(String passwordFromDB, String maNV) {
        // Mật khẩu mặc định là MaNV (ví dụ: "NV001", "NV002")
        String defaultPassword = maNV;

        // So sánh trực tiếp với plain text
        return defaultPassword.equals(passwordFromDB);
    }

    /**
     * Bước C: Bắt buộc đổi mật khẩu (Force Change Password)
     * 
     * @param username    Tên đăng nhập
     * @param newPassword Mật khẩu mới
     * @param maNV        Mã nhân viên (để kiểm tra không được trùng với mật khẩu
     *                    mặc định)
     * @return true nếu đổi mật khẩu thành công
     */
    public static boolean forceChangePassword(String username, String newPassword, String maNV) {
        // Kiểm tra mật khẩu mới không được trùng với mật khẩu mặc định
        if (newPassword.equals(maNV)) {
            System.out.println("❌ Mật khẩu mới không được trùng với mật khẩu mặc định!");
            return false;
        }

        // Kiểm tra mật khẩu mới có hợp lệ không (ít nhất 3 ký tự)
        if (newPassword.length() < 3) {
            System.out.println("❌ Mật khẩu phải có ít nhất 3 ký tự!");
            return false;
        }

        // Lưu mật khẩu mới dạng plain text
        String sql = "UPDATE TAIKHOAN SET PassWord = ? WHERE UserName = ?";
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setString(2, username);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Đổi mật khẩu thành công!");
                return true;
            } else {
                System.out.println("❌ Không thể cập nhật mật khẩu!");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đổi mật khẩu: " + e.getMessage());
            return false;
        }
    }

    /**
     * Migrate password từ plain text sang BCrypt hash
     * 
     * @param username Tên đăng nhập
     * @return true nếu migrate thành công
     */
    public static boolean migratePasswordToBCrypt(String username) {
        String sql = "SELECT PassWord FROM TAIKHOAN WHERE UserName = ?";
        String updateSql = "UPDATE TAIKHOAN SET PassWord = ? WHERE UserName = ?";

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement selectStmt = conn.prepareStatement(sql);
                PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            selectStmt.setString(1, username);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                String currentPassword = rs.getString("PassWord");

                // Chỉ migrate nếu password chưa được hash
                if (!currentPassword.startsWith("$2a$") && !currentPassword.startsWith("$2b$")
                        && !currentPassword.startsWith("$2y$")) {
                    String hashedPassword = BCrypt.hashpw(currentPassword, BCrypt.gensalt());
                    updateStmt.setString(1, hashedPassword);
                    updateStmt.setString(2, username);
                    return updateStmt.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi migrate password: " + e.getMessage());
        }
        return false;
    }

    // =========

}
