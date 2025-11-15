package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import util.JDBCUtil;
import util.AuditLogger;
import main.Main;
import java.util.List;
import java.util.ArrayList;
import dto.NhanVienDTO;

public class NhanVienDAO {

    // Tạo mã nhân viên tự động
    public static String generateMaNV() {
        String prefix = "NV";
        String newID = prefix + "001";
        String query = "SELECT MaNV FROM NHANVIEN ORDER BY CAST(SUBSTRING(MaNV, 3) AS UNSIGNED) DESC LIMIT 1";

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String lastID = rs.getString("MaNV");
                int number = Integer.parseInt(lastID.substring(2)) + 1;
                newID = prefix + String.format("%03d", number);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi tạo mã nhân viên: " + e.getMessage());
        }

        return newID;
    }

    // Tìm nhân viên theo mã

    public static NhanVienDTO timNhanVienTheoMa(String maNV) {

        if (maNV == null || maNV.trim().isEmpty()) {
            System.err.println("❌ Mã nhân viên không được rỗng!");
            return null;
        }

        String query = """
                SELECT MaNV, Ho, Ten, GioiTinh, NgaySinh, DiaChi, Email,
                       Luong, ChucVu, TrangThai
                FROM NHANVIEN
                WHERE MaNV = ?
                """;

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maNV);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Date d = rs.getDate("NgaySinh");
                    LocalDate ns = (d != null) ? d.toLocalDate() : null;

                    NhanVienDTO nv = new NhanVienDTO(
                            rs.getString("MaNV"),
                            rs.getString("Ho"),
                            rs.getString("Ten"),
                            rs.getString("GioiTinh"),
                            ns,
                            rs.getString("DiaChi"),
                            rs.getString("Email"),
                            rs.getInt("Luong"),
                            rs.getString("ChucVu"));
                    nv.setTrangThai(rs.getString("TrangThai"));
                    return nv;
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm nhân viên theo mã: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // Tìm nhân viên theo tên
    public static List<NhanVienDTO> timNhanVienTheoTen(String ten) {
        String query = "SELECT MaNV, Ho, Ten, GioiTinh, NgaySinh, DiaChi, Email, Luong, ChucVu, TrangThai " +
                "FROM NHANVIEN WHERE (Ten = ? OR CONCAT(Ho, ' ', Ten) = ?) AND TrangThai = 'active'";

        List<NhanVienDTO> list = new ArrayList<>();
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, ten);
            pstmt.setString(2, ten);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                java.sql.Date d = rs.getDate("NgaySinh");
                LocalDate ns = d != null ? d.toLocalDate() : null;

                NhanVienDTO nv = new NhanVienDTO(
                        rs.getString("MaNV"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getString("GioiTinh"),
                        ns,
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getInt("Luong"),
                        rs.getString("ChucVu"));
                nv.setTrangThai(rs.getString("TrangThai"));
                list.add(nv);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm nhân viên theo tên: " + e.getMessage());
        }
        return list;
    }

    // Thêm nhân viên
    public static void themNhanVien(NhanVienDTO nv) {
        // Tao truy van
        String query = "INSERT INTO NHANVIEN (MaNV, Ho, Ten, GioiTinh, NgaySinh, DiaChi, Email, Luong, ChucVu, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, nv.getMaNV());
            pstmt.setString(2, nv.getHo());
            pstmt.setString(3, nv.getTen());
            pstmt.setString(4, nv.getGioiTinh());
            if (nv.getNgaySinh() != null) {
                pstmt.setDate(5, java.sql.Date.valueOf(nv.getNgaySinh()));
            } else {
                pstmt.setNull(5, java.sql.Types.DATE);
            }
            pstmt.setString(6, nv.getDiaChi());
            pstmt.setString(7, nv.getEmail());
            pstmt.setInt(8, nv.getLuong());
            pstmt.setString(9, nv.getChucVu());
            pstmt.setString(10, "active");

            int checkRow = pstmt.executeUpdate();
            if (checkRow == 0) {
                System.out.println("Thêm nhân viên thất bại");
            } else {
                System.out.println("Thêm nhân viên thành công");
                // Tạo tài khoản mặc định cho nhân viên vừa thêm
                String hoTen = nv.getFullName();
                String vaiTro = nv.getChucVu();
                boolean created = TaiKhoanDAO.taoTaiKhoanMacDinhChoNhanVien(nv.getMaNV(), hoTen, vaiTro,
                        nv.getEmail());
                if (!created) {
                    System.out.println("Lưu ý: Không thể tạo tài khoản mặc định cho nhân viên " + nv.getMaNV());
                } else {
                    System.out.println("Đã tạo tài khoản mặc định cho nhân viên " + nv.getMaNV()
                            + " (UserName=" + nv.getMaNV() + ")");
                }
            }

        } catch (Exception e) {
            System.err.println("Lỗi khi thêm nhân viên: " + e.getMessage());

        }
    }

    // Sửa thông tin nhân viên
    public static void suaNhanVien(NhanVienDTO nv, String trangThai, String oldStatus, String reason) {
        String sql = "UPDATE NHANVIEN SET Ho = ?, Ten = ?, GioiTinh = ?, NgaySinh = ?, DiaChi = ?, Email = ?, Luong = ?, ChucVu = ?, TrangThai = ? WHERE MaNV = ?";
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nv.getHo());
            pstmt.setString(2, nv.getTen());
            pstmt.setString(3, nv.getGioiTinh());
            if (nv.getNgaySinh() != null) {
                pstmt.setDate(4, java.sql.Date.valueOf(nv.getNgaySinh()));
            } else {
                pstmt.setNull(4, java.sql.Types.DATE);
            }
            pstmt.setString(5, nv.getDiaChi());
            pstmt.setString(6, nv.getEmail());
            pstmt.setInt(7, nv.getLuong());
            pstmt.setString(8, nv.getChucVu());
            pstmt.setString(9, trangThai);
            pstmt.setString(10, nv.getMaNV());

            int checkRow = pstmt.executeUpdate();
            if (checkRow == 0) {
                System.out.println("Sửa thông tin nhân viên thất bại");
            } else {
                System.out.println("Sửa thông tin nhân viên thành công");
                // Nếu chuyển sang inactive: khóa đăng nhập
                if ("inactive".equalsIgnoreCase(trangThai)) {
                    TaiKhoanDAO.khoaTaiKhoanTheoNhanVien(nv.getMaNV());
                }
                // Audit log
                String actor = (Main.CURRENT_ACCOUNT != null) ? Main.CURRENT_ACCOUNT.getUsername() : "unknown";
                if (oldStatus == null)
                    oldStatus = nv.getTrangThai();
                AuditLogger.logEmployeeStatusChange(actor, nv.getMaNV(), oldStatus, trangThai, reason);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật nhân viên: " + e.getMessage());

        }

    }

    // Xóa nhân viên
    public static boolean xoaNhanVien(String maNV, String reason) {
        // 1) Kiểm tra trạng thái hiện tại
        String getStatusSql = "SELECT TrangThai FROM NHANVIEN WHERE MaNV = ?";
        String softDeleteSql = "UPDATE NHANVIEN SET TrangThai = 'inactive' WHERE MaNV = ?";

        try (Connection conn = JDBCUtil.getConnection()) {
            String currentStatus = null;
            try (PreparedStatement psCheck = conn.prepareStatement(getStatusSql)) {
                psCheck.setString(1, maNV);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        currentStatus = rs.getString("TrangThai");
                    } else {
                        System.out.println("Không tìm thấy nhân viên với mã: " + maNV);
                        return false;
                    }
                }
            }

            String actor = (Main.CURRENT_ACCOUNT != null) ? Main.CURRENT_ACCOUNT.getUsername() : "unknown";

            // 2) Nếu đã inactive rồi: ghi thông báo cho quản trị và trả về false
            if (currentStatus != null && currentStatus.equalsIgnoreCase("inactive")) {
                AuditLogger.notifyAdminEmployeeAlreadyDeleted(actor, maNV, currentStatus, reason);
                System.out.println("Nhân viên đã bị xóa trước đó (inactive). Đã thông báo cho quản trị.");
                return false;
            }

            // 3) Thực hiện soft-delete như bình thường
            try (PreparedStatement psDelete = conn.prepareStatement(softDeleteSql)) {
                psDelete.setString(1, maNV);
                int rowAffected = psDelete.executeUpdate();
                if (rowAffected > 0) {
                    // Khóa login nếu có tài khoản
                    TaiKhoanDAO.khoaTaiKhoanTheoNhanVien(maNV);
                    // Audit log trạng thái
                    AuditLogger.logEmployeeStatusChange(actor, maNV, currentStatus, "inactive", reason);
                    return true;
                }
            }
            return false;

        } catch (SQLException e) {
            System.out.println("Lỗi khi đổi trạng thái nhân viên theo mã : " + e.getMessage());
        }
        return false;

    }

    // Lấy toàn bộ dữ liệu nhân viên
    public static List<NhanVienDTO> getAllNhanVien() {
        String query = "SELECT nv.MaNV, nv.Ho, nv.Ten, nv.GioiTinh, nv.NgaySinh, nv.DiaChi, nv.Email, nv.Luong, nv.ChucVu, nv.TrangThai "
                + "FROM NHANVIEN nv";

        List<NhanVienDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                java.sql.Date d = rs.getDate("NgaySinh");
                LocalDate ns = d != null ? d.toLocalDate() : null;

                NhanVienDTO nv = new NhanVienDTO(
                        rs.getString("MaNV"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getString("GioiTinh"),
                        ns,
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getInt("Luong"),
                        rs.getString("ChucVu"));
                nv.setTrangThai(rs.getString("TrangThai"));
                list.add(nv);
            }

        } catch (SQLException ex) {
            System.out.println("Lỗi khi in tất cả nhân viên : " + ex.getMessage());
        }
        return list;
    }

    // ========= THỐNG KÊ NHÂN VIÊN =======

    // Lấy thống kê cơ bản: [tổng số, đang làm việc, đã nghỉ việc]
    public static int[] layThongKeCoBan() {
        String query = "SELECT " +
                "COUNT(*) as total, " +
                "SUM(CASE WHEN TrangThai = 'active' THEN 1 ELSE 0 END) as active, " +
                "SUM(CASE WHEN TrangThai = 'inactive' THEN 1 ELSE 0 END) as inactive " +
                "FROM NHANVIEN";

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new int[] {
                        rs.getInt("total"),
                        rs.getInt("active"),
                        rs.getInt("inactive")
                };
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thống kê cơ bản: " + e.getMessage());
        }
        return new int[] { 0, 0, 0 };
    }

    // Lấy lương trung bình
    public static long layLuongTrungBinh() {
        String query = "SELECT AVG(Luong) as avgSalary FROM NHANVIEN WHERE TrangThai = 'active'";

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Math.round(rs.getDouble("avgSalary"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy lương trung bình: " + e.getMessage());
        }
        return 0;
    }

    // Lấy tổng quỹ lương (chính xác từ database)
    public static long layTongQuyLuong() {
        String query = "SELECT SUM(Luong) as totalSalary FROM NHANVIEN WHERE TrangThai = 'active'";

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("totalSalary");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy tổng quỹ lương: " + e.getMessage());
        }
        return 0;
    }

    // Lấy thống kê theo chức vụ: [số nhân viên, số quản lý]
    public static int[] layThongKeTheoChucVu() {
        String query = "SELECT " +
                "SUM(CASE WHEN ChucVu = 'NV' THEN 1 ELSE 0 END) as nv, " +
                "SUM(CASE WHEN ChucVu = 'QL' THEN 1 ELSE 0 END) as ql " +
                "FROM NHANVIEN WHERE TrangThai = 'active'";

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new int[] {
                        rs.getInt("nv"),
                        rs.getInt("ql")
                };
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thống kê chức vụ: " + e.getMessage());
        }
        return new int[] { 0, 0 };
    }

    // Lấy thống kê theo giới tính: [số nam, số nữ]
    public static int[] layThongKeTheoGioiTinh() {
        String query = "SELECT " +
                "SUM(CASE WHEN GioiTinh = 'Nam' THEN 1 ELSE 0 END) as nam, " +
                "SUM(CASE WHEN GioiTinh = 'Nu' THEN 1 ELSE 0 END) as nu " +
                "FROM NHANVIEN WHERE TrangThai = 'active'";

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new int[] {
                        rs.getInt("nam"),
                        rs.getInt("nu")
                };
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thống kê giới tính: " + e.getMessage());
        }
        return new int[] { 0, 0 };
    }

}
