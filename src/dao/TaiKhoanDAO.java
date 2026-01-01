package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.TaiKhoanDTO;
import util.JDBCUtil;
import org.mindrot.jbcrypt.BCrypt;

public class TaiKhoanDAO {

    public static TaiKhoanDTO kiemTraTaiKhoan(String username, String password) {
        TaiKhoanDTO taiKhoan = null;

        String query = "SELECT * FROM TAIKHOAN WHERE UserName = ?";

        try {
            Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                // Lấy password từ database
                String passwordFromDB = rs.getString("PassWord");

                // Xác thực mật khẩu
                boolean passwordValid = password.equals(passwordFromDB);

                if (passwordValid) {
                    // thành công - Mật khẩu đúng, xác thực thành công
                    String role = rs.getString("VaiTro");
                    String fullName = rs.getString("HoTen");
                    String maNV = rs.getString("MaNV");
                    String status = rs.getString("TrangThai");

                    // Kiểm tra mật khẩu mặc định
                    boolean isDefault = laMatKhauMacDinh(passwordFromDB, maNV);

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

    public static boolean khoaTaiKhoanTheoNhanVien(String maNV) {
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

    public static boolean taiKhoanTonTaiChoNhanVien(String maNV) {
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

    public static boolean taoTaiKhoanMacDinhChoNhanVien(String maNV, String hoTen, String vaiTro, String email) {
        // Username mặc định = MaNV, mật khẩu mặc định = MaNV, trạng thái Active
        if (maNV == null || maNV.isEmpty())
            return false;
        // Tránh tạo trùng
        if (taiKhoanTonTaiChoNhanVien(maNV))
            return true;

        String mappedRole = anhXaChucVuToiVaiTro(vaiTro);

        String sql = "INSERT INTO TAIKHOAN (UserName, PassWord, MaNV, VaiTro, HoTen, TrangThai, Email) VALUES (?, ?, ?, ?, ?, 'Active', ?)";
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNV);
            ps.setString(2, maNV);
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

    private static String anhXaChucVuToiVaiTro(String chucVu) {
        if (chucVu == null)
            return "NhanVien";
        String cv = chucVu.trim();
        if (cv.equalsIgnoreCase("QL") || cv.equalsIgnoreCase("QuanLy") || cv.equalsIgnoreCase("Admin")) {
            return "Admin";
        }
        // Mặc định là nhân viên để phù hợp ENUM('Admin','NhanVien')
        return "NhanVien";
    }

    public static String maHoaMatKhau(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean xacThucMatKhau(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    public static boolean laMatKhauMacDinh(String passwordFromDB, String maNV) {
        // Mật khẩu mặc định là MaNV (ví dụ: "NV001", "NV002")
        String defaultPassword = maNV;

        // So sánh trực tiếp với plain text
        return defaultPassword.equals(passwordFromDB);
    }

    public static boolean batBuocDoiMatKhau(String username, String newPassword, String maNV) {
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

    public static boolean chuyenDoiMatKhauSangBCrypt(String username) {
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

    // ======= CẬP NHẬT THÔNG TIN CÁ NHÂN CỦA NHÂN VIÊN ========

    // ======= ĐỔI MẬT KHẨU TÀI KHOẢN (NV, AD) ========

    public static boolean doiMatKhau(String username, String currentPassword, String newPassword) {
        // Kiểm tra đầu vào
        if (username == null || username.trim().isEmpty()) {
            System.out.println("❌ Tên đăng nhập không được để trống!");
            return false;
        }

        if (currentPassword == null || currentPassword.trim().isEmpty()) {
            System.out.println("❌ Mật khẩu hiện tại không được để trống!");
            return false;
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            System.out.println("❌ Mật khẩu mới không được để trống!");
            return false;
        }

        // Kiểm tra mật khẩu mới có hợp lệ không (ít nhất 3 ký tự)
        if (newPassword.length() < 3) {
            System.out.println("❌ Mật khẩu phải có ít nhất 3 ký tự!");
            return false;
        }

        // Kiểm tra mật khẩu hiện tại có đúng không
        String sqlCheck = "SELECT PassWord, MaNV FROM TAIKHOAN WHERE UserName = ?";
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement psCheck = conn.prepareStatement(sqlCheck)) {

            psCheck.setString(1, username);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                String passwordFromDB = rs.getString("PassWord");
                String maNV = rs.getString("MaNV");

                // Kiểm tra mật khẩu hiện tại
                if (!currentPassword.equals(passwordFromDB)) {
                    System.out.println("❌ Mật khẩu hiện tại không đúng!");
                    return false;
                }

                // Kiểm tra mật khẩu mới không được trùng với mật khẩu mặc định
                if (newPassword.equals(maNV)) {
                    System.out.println("❌ Mật khẩu mới không được trùng với mã nhân viên!");
                    return false;
                }

                // Cập nhật mật khẩu mới
                String sqlUpdate = "UPDATE TAIKHOAN SET PassWord = ? WHERE UserName = ?";
                try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                    psUpdate.setString(1, newPassword);
                    psUpdate.setString(2, username);

                    int rowsAffected = psUpdate.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("✅ Đổi mật khẩu thành công!");
                        return true;
                    } else {
                        System.out.println("❌ Không thể cập nhật mật khẩu!");
                        return false;
                    }
                }
            } else {
                System.out.println("❌ Không tìm thấy tài khoản với tên đăng nhập: " + username);
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi đổi mật khẩu: " + e.getMessage());
            return false;
        }
    }

    // ================ ĐẶT LẠI MK CHO BẤT KỲ TK ==================
    public static boolean datLaiMatKhau(String username, String newPassword) {
        // Kiểm tra đầu vào
        if (username == null || username.trim().isEmpty()) {
            System.out.println("❌ Tên đăng nhập không được để trống!");
            return false;
        }

        // Lấy thông tin tài khoản
        String sqlCheck = "SELECT MaNV FROM TAIKHOAN WHERE UserName = ?";
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement psCheck = conn.prepareStatement(sqlCheck)) {

            psCheck.setString(1, username);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                String maNV = rs.getString("MaNV");

                // Nếu newPassword là null hoặc rỗng, đặt về mật khẩu mặc định (MaNV)
                String passwordToSet;
                if (newPassword == null || newPassword.trim().isEmpty()) {
                    passwordToSet = maNV;
                    System.out.println("🔄 Đặt lại mật khẩu về mặc định: " + maNV);
                } else {
                    // Kiểm tra mật khẩu mới có hợp lệ không
                    if (newPassword.length() < 3) {
                        System.out.println("❌ Mật khẩu phải có ít nhất 3 ký tự!");
                        return false;
                    }
                    passwordToSet = newPassword;
                    System.out.println("🔄 Đặt lại mật khẩu mới cho tài khoản: " + username);
                }

                // Cập nhật mật khẩu
                String sqlUpdate = "UPDATE TAIKHOAN SET PassWord = ? WHERE UserName = ?";
                try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                    psUpdate.setString(1, passwordToSet);
                    psUpdate.setString(2, username);

                    int rowsAffected = psUpdate.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("✅ Đặt lại mật khẩu thành công!");
                        return true;
                    } else {
                        System.out.println("❌ Không thể cập nhật mật khẩu!");
                        return false;
                    }
                }
            } else {
                System.out.println("❌ Không tìm thấy tài khoản với tên đăng nhập: " + username);
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi đặt lại mật khẩu: " + e.getMessage());
            return false;
        }
    }

    // ================ ĐẶT LẠI MẬT KHẨU TK ==================
    public static boolean datLaiMatKhauMacDinh(String username) {
        return datLaiMatKhau(username, null);
    }

    public static boolean capNhatThongTinCaNhanAdmin(String maNV, String ho, String ten, String gioiTinh,
            java.time.LocalDate ngaySinh, String diaChi, String email) {

        // Kiểm tra đầu vào
        if (maNV == null || maNV.trim().isEmpty()) {
            System.out.println("❌ Mã nhân viên không được để trống!");
            return false;
        }

        if (ho == null || ho.trim().isEmpty()) {
            System.out.println("❌ Họ không được để trống!");
            return false;
        }

        if (ten == null || ten.trim().isEmpty()) {
            System.out.println("❌ Tên không được để trống!");
            return false;
        }

        if (email == null || email.trim().isEmpty()) {
            System.out.println("❌ Email không được để trống!");
            return false;
        }

        // Kiểm tra định dạng email cơ bản
        if (!email.contains("@") || !email.contains(".")) {
            System.out.println("❌ Email không đúng định dạng!");
            return false;
        }

        String sql = "UPDATE NHANVIEN SET Ho = ?, Ten = ?, GioiTinh = ?, NgaySinh = ?, DiaChi = ?, Email = ? WHERE MaNV = ?";

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ho.trim());
            ps.setString(2, ten.trim());
            ps.setString(3, gioiTinh != null ? gioiTinh.trim() : null);

            if (ngaySinh != null) {
                ps.setDate(4, java.sql.Date.valueOf(ngaySinh));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }

            ps.setString(5, diaChi != null ? diaChi.trim() : null);
            ps.setString(6, email.trim());
            ps.setString(7, maNV.trim());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Cập nhật thông tin cá nhân thành công!");

                // Cập nhật thông tin trong bảng TAIKHOAN nếu có
                capNhatThongTinTaiKhoan(maNV, ho.trim() + " " + ten.trim(), email.trim());

                return true;
            } else {
                System.out.println("❌ Không tìm thấy nhân viên với mã: " + maNV);
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật thông tin cá nhân: " + e.getMessage());
            return false;
        }
    }

    private static void capNhatThongTinTaiKhoan(String maNV, String hoTen, String email) {
        String sql = "UPDATE TAIKHOAN SET HoTen = ?, Email = ? WHERE MaNV = ?";

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hoTen);
            ps.setString(2, email);
            ps.setString(3, maNV);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật thông tin tài khoản: " + e.getMessage());
        }
    }

    public static dto.NhanVienDTO layThongTinCaNhanAdmin(String maNV) {
        String sql = "SELECT MaNV, Ho, Ten, GioiTinh, NgaySinh, DiaChi, Email, Luong, ChucVu, TrangThai " +
                "FROM NHANVIEN WHERE MaNV = ?";

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNV);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                java.sql.Date ngaySinhDB = rs.getDate("NgaySinh");
                java.time.LocalDate ngaySinh = ngaySinhDB != null ? ngaySinhDB.toLocalDate() : null;

                return new dto.NhanVienDTO(
                        rs.getString("MaNV"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getString("GioiTinh"),
                        ngaySinh,
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getInt("Luong"),
                        rs.getString("ChucVu"));
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thông tin cá nhân: " + e.getMessage());
        }

        return null;
    }

    // ======= XEM DANH SÁCH TÀI KHOẢN (AD) ========

    public static java.util.List<TaiKhoanDTO> xemDanhSachTaiKhoan() {
        String sql = "SELECT UserName, PassWord, MaNV, VaiTro, HoTen, TrangThai, Email FROM TAIKHOAN ORDER BY VaiTro DESC, UserName ASC";

        java.util.List<TaiKhoanDTO> danhSachTaiKhoan = new java.util.ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String username = rs.getString("UserName");
                String password = rs.getString("PassWord");
                String maNV = rs.getString("MaNV");
                String vaiTro = rs.getString("VaiTro");
                String hoTen = rs.getString("HoTen");
                String trangThai = rs.getString("TrangThai");
                String email = rs.getString("Email");

                // Kiểm tra mật khẩu mặc định
                boolean isDefault = laMatKhauMacDinh(password, maNV);

                // Không lưu mật khẩu thô vì lý do bảo mật
                TaiKhoanDTO taiKhoan = new TaiKhoanDTO(username, "***", maNV, vaiTro, hoTen, trangThai, isDefault);
                taiKhoan.setEmail(email);

                danhSachTaiKhoan.add(taiKhoan);
            }

            System.out.println("✅ Lấy danh sách tài khoản thành công!");

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách tài khoản: " + e.getMessage());
            return null;
        }

        return danhSachTaiKhoan;
    }

    // ================ LẤY SỐ LIỆU THỐNG KÊ CÁC TK ==================
    public static int[] layThongKeTaiKhoan() {
        String sql = "SELECT " +
                "COUNT(*) as total, " +
                "SUM(CASE WHEN VaiTro = 'Admin' THEN 1 ELSE 0 END) as admin, " +
                "SUM(CASE WHEN VaiTro = 'NhanVien' THEN 1 ELSE 0 END) as nhanvien, " +
                "SUM(CASE WHEN TrangThai = 'Active' THEN 1 ELSE 0 END) as active, " +
                "SUM(CASE WHEN TrangThai = 'Inactive' THEN 1 ELSE 0 END) as inactive " +
                "FROM TAIKHOAN";

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new int[] {
                        rs.getInt("total"),
                        rs.getInt("admin"),
                        rs.getInt("nhanvien"),
                        rs.getInt("active"),
                        rs.getInt("inactive")
                };
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thống kê tài khoản: " + e.getMessage());
        }

        return new int[] { 0, 0, 0, 0, 0 };
    }

    // ================ TÌM KIẾM TK THEO TỪ KHOA ==================
    public static java.util.List<TaiKhoanDTO> timKiemTaiKhoan(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return xemDanhSachTaiKhoan();
        }

        String sql = "SELECT UserName, PassWord, MaNV, VaiTro, HoTen, TrangThai, Email FROM TAIKHOAN " +
                "WHERE UserName LIKE ? OR HoTen LIKE ? OR MaNV LIKE ? " +
                "ORDER BY VaiTro DESC, UserName ASC";

        java.util.List<TaiKhoanDTO> ketQuaTimKiem = new java.util.ArrayList<>();
        String searchPattern = "%" + keyword.trim() + "%";

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String username = rs.getString("UserName");
                String password = rs.getString("PassWord");
                String maNV = rs.getString("MaNV");
                String vaiTro = rs.getString("VaiTro");
                String hoTen = rs.getString("HoTen");
                String trangThai = rs.getString("TrangThai");
                String email = rs.getString("Email");

                boolean isDefault = laMatKhauMacDinh(password, maNV);

                TaiKhoanDTO taiKhoan = new TaiKhoanDTO(username, "***", maNV, vaiTro, hoTen, trangThai, isDefault);
                taiKhoan.setEmail(email);

                ketQuaTimKiem.add(taiKhoan);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm tài khoản: " + e.getMessage());
            return null;
        }

        return ketQuaTimKiem;
    }

    // ================ VÔ HIỆU HÓA/KÍCH HOẠT TÀI KHOẢN ==================
    public static boolean voHieuHoaTaiKhoan(String username) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("❌ Tên đăng nhập không được để trống!");
            return false;
        }

        String sql = "UPDATE TAIKHOAN SET TrangThai = 'Inactive' WHERE UserName = ?";
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Vô hiệu hóa tài khoản thành công!");
                return true;
            } else {
                System.out.println("❌ Không tìm thấy tài khoản với tên đăng nhập: " + username);
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi vô hiệu hóa tài khoản: " + e.getMessage());
            return false;
        }
    }

    public static boolean kichHoatTaiKhoan(String username) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("❌ Tên đăng nhập không được để trống!");
            return false;
        }

        String sql = "UPDATE TAIKHOAN SET TrangThai = 'Active' WHERE UserName = ?";
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Kích hoạt tài khoản thành công!");
                return true;
            } else {
                System.out.println("❌ Không tìm thấy tài khoản với tên đăng nhập: " + username);
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kích hoạt tài khoản: " + e.getMessage());
            return false;
        }
    }

    public static String layTrangThaiTaiKhoan(String username) {
        String sql = "SELECT TrangThai FROM TAIKHOAN WHERE UserName = ?";
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("TrangThai");
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy trạng thái tài khoản: " + e.getMessage());
        }
        return null;
    }

}
