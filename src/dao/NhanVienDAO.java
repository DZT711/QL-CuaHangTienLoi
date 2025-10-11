package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import util.JDBCUtil;
import java.util.List;
import java.util.ArrayList;
import dto.NhanVienDTO;

public class NhanVienDAO {

    // Tìm nhân viên theo mã
    public static NhanVienDTO timNhanVienTheoMa(String maNV) {
        String query = "SELECT MaNV, Ho, Ten, GioiTinh, NgaySinh, DiaChi, Email, Luong, ChucVu, TrangThai " +
                "FROM NHANVIEN " +
                "WHERE MaNV = ?";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();

            java.sql.Date d = rs.getDate("NgaySinh");
            LocalDate ns = d != null ? d.toLocalDate() : null;

            if (rs.next()) {
                return new NhanVienDTO(
                        rs.getString("MaNV"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getString("GioiTinh"),
                        ns,
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getInt("Luong"),
                        rs.getString("ChucVu"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm nhân viên theo mã: " + e.getMessage());
        }
        return null;
    }

    // Tìm nhân viên theo tên
    public static List<NhanVienDTO> timNhanVienTheoTen(String ten) {
        String query = "SELECT MaNV, Ho, Ten, GioiTinh, NgaySinh, DiaChi, Email, Luong, ChucVu " +
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

                list.add(new NhanVienDTO(
                        rs.getString("MaNV"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getString("GioiTinh"),
                        ns,
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getInt("Luong"),
                        rs.getString("ChucVu")));
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
            }

        } catch (Exception e) {
            System.err.println("Lỗi khi thêm nhân viên: " + e.getMessage());

        }
    }

    // Sửa thông tin nhân viên
    public static void suaNhanVien(NhanVienDTO nv, String trangThai) {
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
            }

        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật nhân viên: " + e.getMessage());

        }

    }

    // Xóa nhân viên
    public static boolean xoaNhanVien(String maNV) {
        String query = "UPDATE NHANVIEN SET TrangThai = 'inactive' Where MaSP = ?";

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, maNV);
            int rowAffected = pstmt.executeUpdate();
            return rowAffected > 0;

        } catch (SQLException e) {
            System.out.println("Lỗi khi đổi trạng thái nhân viên theo mã : " + e.getMessage());
        }
        return false;

    }

    // tính độ dài và khung danh sách
    private static String repeat(char ch, int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++)
            sb.append(ch);
        return sb.toString();
    }

    // Lấy toàn bộ dữ liệu nhân viên
    public static List<NhanVienDTO> getAllNhanVien() {
        String query = "SELECT nv.MaNV, nv.Ho, nv.Ten, nv.GioiTinh, nv.NgaySinh, nv.DiaChi, nv.Email, nv.Luong, nv.ChucVu, nv.TrangThai AS TrangThaiNV, "
                + "tk.UserName, tk.VaiTro, tk.TrangThai AS TrangThaiTK, tk.Email AS EmailTK " +
                "FROM NHANVIEN nv " +
                "LEFT JOIN TAIKHOAN tk ON tk.MaNV = nv.MaNV";

        List<NhanVienDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            java.sql.Date d = rs.getDate("NgaySinh");
            LocalDate ns = d != null ? d.toLocalDate() : null;
            while (rs.next()) {

                list.add(new NhanVienDTO(
                        rs.getString("MANV"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getString("GioiTinh"),
                        ns,
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getInt("Luong"),
                        rs.getString("ChucVu")));
            }

        } catch (SQLException ex) {
            System.out.println("Lỗi khi in tất cả nhân viên : " + ex.getMessage());
        }
        return list;
    }

    // In toàn bộ danh sách nhân viên
    public static void inDanhSachNhanVien() {
        List<NhanVienDTO> list = getAllNhanVien();
        final int innerWidth = 126; // điều chỉnh nếu bạn thay đổi độ rộng cột
        String top = "╔" + repeat('═', innerWidth) + "╗";
        String sep = "╟" + repeat('─', innerWidth) + "╢";
        String bottom = "╚" + repeat('═', innerWidth) + "╝";

        System.out.println(top);
        System.out.printf("║ %-8s │ %-22s │ %-6s │ %-12s │ %-22s │ %-25s │ %-10s │ %-6s ║%n",
                "MaNV", "Họ tên", "GT", "Ngày sinh", "Địa chỉ", "Email", "Lương", "Chức");
        System.out.println(sep);

        if (list == null || list.isEmpty()) {
            String msg = "⚠️  Không có nhân viên nào trong hệ thống.";
            System.out.printf("║ %-" + (innerWidth - 1) + "s║%n", msg);
        } else {
            for (NhanVienDTO nv : list) {
                String fullName = nv.getFullName();
                String ngaySinh = nv.getNgaySinh() != null ? nv.getNgaySinhFormat() : "";
                System.out.printf("║ %-8s │ %-22s │ %-6s │ %-12s │ %-22s │ %-25s │ %-10d │ %-6s ║%n",
                        nv.getMaNV(),
                        fullName,
                        nv.getGioiTinh(),
                        ngaySinh,
                        nv.getDiaChi(),
                        nv.getEmail(),
                        nv.getLuong(),
                        nv.getChucVu());
            }
        }
        System.out.println(bottom);
    }

    // In thông tin một nhân viên
    public static void inThongTinNhanVien(NhanVienDTO nv) {
        if (nv == null) {
            System.out.println("❌ Không có thông tin nhân viên để hiển thị!");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                              THÔNG TIN NHÂN VIÊN                                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");

        System.out.println("┌─────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 📋 Mã nhân viên    │ " + String.format("%-45s", nv.getMaNV()) + " │");
        System.out.println("│ 👤 Họ và tên       │ " + String.format("%-45s", nv.getFullName()) + " │");
        System.out.println("│ ⚧ Giới tính       │ " + String.format("%-45s", nv.getGioiTinh()) + " │");
        System.out.println("│ 🎂 Ngày sinh       │ " + String.format("%-45s",
                nv.getNgaySinh() != null ? nv.getNgaySinhFormat() : "Không có") + " │");
        System.out.println("│ 🏠 Địa chỉ         │ " + String.format("%-45s",
                nv.getDiaChi() != null ? nv.getDiaChi() : "Không có") + " │");
        System.out.println("│ 📧 Email           │ " + String.format("%-45s", nv.getEmail()) + " │");
        System.out.println("│ 💰 Lương           │ " + String.format("%,d VNĐ", nv.getLuong())
                + String.format("%" + (45 - String.format("%,d VNĐ", nv.getLuong()).length()) + "s", "") + " │");
        System.out.println("│ 💼 Chức vụ         │ " + String.format("%-45s", nv.getChucVu()) + " │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────────────┘");
    }

}
