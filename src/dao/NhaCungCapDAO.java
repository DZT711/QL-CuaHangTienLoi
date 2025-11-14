package dao;

import dto.NhaCungCapDTO;
import util.JDBCUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhaCungCapDAO {

    public static String generateMaNCC() {
        String prefix = "NCC";
        String newID = prefix + "001";
        String query = "SELECT MaNCC FROM NHACUNGCAP " +
                "ORDER BY CAST(SUBSTRING(MaNCC, 4) AS UNSIGNED) DESC " +
                "LIMIT 1";

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String lastID = rs.getString("MaNCC");
                int number = Integer.parseInt(lastID.substring(3));
                newID = prefix + String.format("%03d", number + 1);
            }

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tạo mã nhà cung cấp: " + e.getMessage());
            e.printStackTrace();
        }

        return newID;
    }

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
                        rs.getString("TrangThai")));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách nhà cung cấp: " + e.getMessage());
        }
        return list;
    }

    // Tìm nhà cung cấp theo mã
    public static NhaCungCapDTO timnccTheoMa(String ma) {
        if (ma == null || ma.trim().isEmpty()) {
            System.err.println("❌ Mã NCC không được rỗng!");
            return null;
        }

        String query = """
                        SELECT MaNCC, TenNCC, DiaChi, DienThoai, Email, TrangThai
                        FROM NHACUNGCAP
                        WHERE MaNCC = ?
                """;

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ma);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new NhaCungCapDTO(
                            rs.getString("MaNCC"),
                            rs.getString("TenNCC"),
                            rs.getString("DiaChi"),
                            rs.getString("DienThoai"),
                            rs.getString("Email"),
                            rs.getString("TrangThai"));
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm NCC theo mã: " + e.getMessage());
            e.printStackTrace();
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
                        rs.getString("TrangThai")));
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

    // Hàm mới để kiểm tra Email có tồn tại hay không (trùng lặp)
    public static boolean checkEmailExist(String email) {
        String query = "SELECT 1 FROM NHACUNGCAP WHERE Email = ? LIMIT 1";

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi kiểm tra email tồn tại: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // Hàm mới để kiểm tra Số Điện Thoại có tồn tại hay không ( trùng lặp )
    public static boolean checkDienThoaiExist(String dienThoai) {
        String query = "SELECT 1 FROM NHACUNGCAP WHERE DienThoai = ? LIMIT 1";

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, dienThoai);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi kiểm tra SĐT tồn tại: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // HÀM MỚI: Thống kê NCC theo trạng thái
    public static void thongKeTheoTrangThai() {
        String query = "SELECT TrangThai, COUNT(*) AS SoLuong FROM NHACUNGCAP GROUP BY TrangThai";
        int tongCong = 0;
        boolean coDuLieu = false;

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║       THỐNG KÊ NHÀ CUNG CẤP               ║");
        System.out.println("╠═══════════════════════╦════════════════════╣");
        System.out.printf("║ %-25s ║ %-18s ║%n", "Trạng Thái", "Số Lượng");
        System.out.println("╠═══════════════════════╬════════════════════╣");

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                coDuLieu = true;
                String trangThai = rs.getString("TrangThai");
                int soLuong = rs.getInt("SoLuong");

                // Căn chỉnh tên trạng thái cho đẹp
                String trangThaiFormatted;
                if (trangThai.equalsIgnoreCase("active")) {
                    trangThaiFormatted = "Đang hoạt động (active)";
                } else if (trangThai.equalsIgnoreCase("inactive")) {
                    trangThaiFormatted = "Ngừng hoạt động (inactive)";
                } else {
                    trangThaiFormatted = trangThai; // Để dự phòng nếu có trạng thái lạ
                }

                System.out.printf("║ %-25s ║ %-18d ║%n", trangThaiFormatted, soLuong);
                tongCong += soLuong;
            }

            if (!coDuLieu) {
                System.out.printf("║ %-25s ║ %-18d ║%n", "Không có dữ liệu", 0);
            }

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê: " + e.getMessage());
            e.printStackTrace(); // In chi tiết lỗi để gỡ lỗi
        }

        System.out.println("╠═══════════════════════╩════════════════════╣");
        System.out.printf("║ %-25s ║ %-18d ║%n", "TỔNG CỘNG", tongCong);
        System.out.println("╚════════════════════════════════════════════╝");
    }

    // HÀM MỚI: Thống kê NCC theo Khu Vực (Tỉnh/Thành)
    public static void thongKeTheoKhuVuc() {
        // Lệnh SQL này dùng SUBSTRING_INDEX để "cắt" chuỗi DiaChi
        // và lấy phần cuối cùng sau dấu phẩy (,) cuối cùng.
        String query = """
                    SELECT
                        TRIM(SUBSTRING_INDEX(DiaChi, ',', -1)) AS KhuVuc,
                        COUNT(*) AS SoLuong
                    FROM
                        NHACUNGCAP
                    WHERE
                        DiaChi IS NOT NULL AND DiaChi != ''
                    GROUP BY
                        KhuVuc
                    ORDER BY
                        SoLuong DESC
                """;

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║       THỐNG KÊ NCC THEO KHU VỰC           ║");
        System.out.println("╠═══════════════════════╦════════════════════╣");
        System.out.printf("║ %-25s ║ %-18s ║%n", "Khu Vực (Tỉnh/Thành)", "Số Lượng NCC");
        System.out.println("╠═══════════════════════╬════════════════════╣");

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            boolean coDuLieu = false;
            while (rs.next()) {
                coDuLieu = true;
                String khuVuc = rs.getString("KhuVuc");
                int soLuong = rs.getInt("SoLuong");

                // Nếu khu vực bị rỗng (do địa chỉ không chuẩn)
                if (khuVuc == null || khuVuc.trim().isEmpty()) {
                    khuVuc = "Không xác định";
                }

                System.out.printf("║ %-25s ║ %-18d ║%n", khuVuc, soLuong);
            }

            if (!coDuLieu) {
                System.out.printf("║ %-25s ║ %-18d ║%n", "Không có dữ liệu", 0);
            }

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê khu vực: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("╚═══════════════════════╩════════════════════╝");
        System.out.println("*Lưu ý: Khu vực được tự động trích xuất từ địa chỉ.");
    }
}
