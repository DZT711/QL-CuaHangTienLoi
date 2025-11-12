package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import dto.KhachHangDTO;
import util.FormatUtil;
import util.JDBCUtil;
import util.ValidatorUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class KhachHangDAO {
    public static List<KhachHangDTO> getAllKhachHang() {
        
        String query = """
            SELECT MaKH, Ho, Ten, GioiTinh, NgaySinh, DienThoai, DiaChi
            FROM KHACHHANG
            ORDER BY MaKH ASC        
        """;

        List<KhachHangDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String maKH = rs.getString("MaKH");
                String ho = rs.getString("Ho");
                String ten = rs.getString("Ten");
                String gioiTinh = rs.getString("GioiTinh");
                String dienThoai = rs.getString("DienThoai");
                
                LocalDate ngaySinh = null;
                java.sql.Date sqlDate = rs.getDate("NgaySinh");
                if (sqlDate != null) {
                    ngaySinh = sqlDate.toLocalDate();
                }
                
                String diaChi = rs.getString("DiaChi");
                
                list.add(new KhachHangDTO(maKH, ho, ten, gioiTinh, ngaySinh, diaChi, dienThoai));
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi lấy tất cả khách hàng: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public static boolean themKhachHang(KhachHangDTO kh) {
    // Check trùng SĐT
    KhachHangDTO existing = timKhachHangTheoDienThoai(kh.getDienThoai());
    if (existing != null) {
        System.out.println("❌ Số điện thoại đã tồn tại trong hệ thống!");
        System.out.println("📋 Khách hàng: " + existing.getHo() + " " + existing.getTen() + 
                           " (Mã: " + existing.getMaKH() + ")");
        return false;
    }
    
    String query = "INSERT INTO KHACHHANG (MaKH, Ho, Ten, GioiTinh, NgaySinh, DienThoai, DiaChi) " +
                   "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = JDBCUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, kh.getMaKH());
        stmt.setString(2, kh.getHo());
        stmt.setString(3, kh.getTen());
        stmt.setString(4, kh.getGioiTinh());
        
        if (kh.getNgaySinh() != null) {
            stmt.setDate(5, java.sql.Date.valueOf(kh.getNgaySinh()));
        } else {
            stmt.setNull(5, java.sql.Types.DATE);
        }
        stmt.setString(6, kh.getDienThoai());
        

        if (kh.getDiaChi() != null && !kh.getDiaChi().isEmpty()) {
            stmt.setString(7, kh.getDiaChi());
        } else {
            stmt.setNull(7, java.sql.Types.VARCHAR);
        }

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
        
    } catch (SQLException e) {
        System.err.println("❌ Lỗi khi thêm khách hàng: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}

    public static boolean suaKhachHang(KhachHangDTO kh) {
        
        String query = """
            UPDATE KHACHHANG 
            SET Ho = ?, Ten = ?, GioiTinh = ?, NgaySinh = ?, DienThoai = ?, DiaChi = ? 
            WHERE MaKH = ?        
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, kh.getHo());
            stmt.setString(2, kh.getTen());
            stmt.setString(3, kh.getGioiTinh());
            
            if (kh.getNgaySinh() != null) {
                stmt.setDate(4, java.sql.Date.valueOf(kh.getNgaySinh()));
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
            }
            
            stmt.setString(5, kh.getDienThoai());
            
            if (kh.getDiaChi() != null && !kh.getDiaChi().isEmpty()) {
                stmt.setString(6, kh.getDiaChi());
            } else {
                stmt.setNull(6, java.sql.Types.VARCHAR);
            }
            
            stmt.setString(7, kh.getMaKH());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi sửa khách hàng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean xoaKhachHang(String maKH) {
        String query = "UPDATE KHACHHANG SET TrangThai = 'inactive' WHERE MaKH = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, maKH);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi xóa khách hàng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static List<KhachHangDTO> timKhachHangTheoTen(String tenKH) {
        
        String query = """
            SELECT MaKH, Ho, Ten, GioiTinh, NgaySinh, DienThoai, DiaChi
            FROM KHACHHANG
            WHERE CONCAT(Ho, ' ', Ten) COLLATE utf8mb4_unicode_ci LIKE ?
        """;

        List<KhachHangDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
        
            stmt.setString(1, "%" + tenKH.trim() + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String maKH = rs.getString("MaKH");
                    String ho = rs.getString("Ho");
                    String ten = rs.getString("Ten");
                    String gioiTinh = rs.getString("GioiTinh");
                    String dienThoai = rs.getString("DienThoai");
                    
                    LocalDate ngaySinh = null;
                    java.sql.Date sqlDate = rs.getDate("NgaySinh");
                    if (sqlDate != null) {
                        ngaySinh = sqlDate.toLocalDate();
                    }
                    
                    String diaChi = rs.getString("DiaChi");
                    
                    list.add(new KhachHangDTO(maKH, ho, ten, gioiTinh, ngaySinh, diaChi, dienThoai));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm khách hàng theo tên: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public static KhachHangDTO timKhachHangTheoMa(String maKH) {
        String query = "SELECT MaKH, Ho, Ten, GioiTinh, NgaySinh, DienThoai, DiaChi FROM KHACHHANG WHERE MaKH = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
        
            stmt.setString(1, maKH);
        
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String ho = rs.getString("Ho");
                    String ten = rs.getString("Ten");
                    String gioiTinh = rs.getString("GioiTinh");
                    String dienThoai = rs.getString("DienThoai");
                    
                    LocalDate ngaySinh = null;
                    java.sql.Date sqlDate = rs.getDate("NgaySinh");
                    if (sqlDate != null) {
                        ngaySinh = sqlDate.toLocalDate();
                    }
                    
                    String diaChi = rs.getString("DiaChi");
                    
                    return new KhachHangDTO(maKH, ho, ten, gioiTinh, ngaySinh, diaChi, dienThoai);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm khách hàng theo mã: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static KhachHangDTO timKhachHangTheoDienThoai(String dienThoai) {
        if (dienThoai == null || dienThoai.trim().isEmpty()) {
            System.err.println("❌ Điện thoại không được rỗng!");
            return null;
        }

        String query = "SELECT MaKH, Ho, Ten, GioiTinh, NgaySinh, DienThoai, DiaChi FROM KHACHHANG WHERE DienThoai = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
        
            stmt.setString(1, dienThoai);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String maKH = rs.getString("MaKH");
                    String ho = rs.getString("Ho");
                    String ten = rs.getString("Ten");
                    String gioiTinh = rs.getString("GioiTinh");
                    
                    LocalDate ngaySinh = null;
                    Date sqlDate = rs.getDate("NgaySinh");
                    if (sqlDate != null) {
                        ngaySinh = sqlDate.toLocalDate();
                    }
                    
                    String diaChi = rs.getString("DiaChi");
                    
                    return new KhachHangDTO(maKH, ho, ten, gioiTinh, ngaySinh, diaChi, dienThoai);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm khách hàng theo điện thoại: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static boolean kiemTraMaKH(String maKH) {
        String query = "SELECT COUNT(*) FROM KHACHHANG WHERE MaKH = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, maKH);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi kiểm tra mã khách hàng: " + e.getMessage());
        }
        
        return false;
    }

    public static void importDSKH(String filePath) {
        int lineNumber = 0;
        int added = 0;
        int skipped = 0;

        System.out.println("🔄 Đang đọc file: " + filePath);

        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (isFirstLine && line.startsWith("\uFEFF")) {
                    line = line.substring(1);
                }
                isFirstLine = false;
                
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] data = line.split(",", -1);

                if (data.length < 7) {
                    System.out.println("❌ Dòng " + lineNumber + ": Thiếu dữ liệu (cần 7 cột, có " + data.length + ")");
                    skipped++;
                    continue;
                }

                try {
                    String maKH = data[0].trim();
                    String ho = data[1].trim();
                    String ten = data[2].trim();
                    String gioiTinh = data[3].trim();
                    String ngaySinhStr = data[4].trim();
                    String dienThoai = data[5].trim();
                    String diaChi = data[6].trim();

                    if (maKH.isEmpty() || ho.isEmpty() || ten.isEmpty() || gioiTinh.isEmpty() || dienThoai.isEmpty()) {
                        System.out.println("❌ Dòng " + lineNumber + ": Thiếu dữ liệu bắt buộc.");
                        skipped++;
                        continue;
                    }
                    
                    if (kiemTraMaKH(maKH)) {
                        System.out.println("⚠️  Dòng " + lineNumber + ": Mã KH đã tồn tại (" + maKH + ")");
                        skipped++;
                        continue;
                    }

                    if (!ValidatorUtil.isValidString(ho)) {
                        System.out.println("❌ Dòng " + lineNumber + ": Họ khách hàng không hợp lệ: " + ho);
                        skipped++;
                        continue;
                    }

                    if (!ValidatorUtil.isValidString(ten)) {
                        System.out.println("❌ Dòng " + lineNumber + ": Tên khách hàng không hợp lệ: " + ten);
                        skipped++;
                        continue;
                    }

                    String lower = gioiTinh.toLowerCase();
                    if (lower.equals("nam")) {
                        gioiTinh = "Nam";
                    } else if (lower.equals("nữ") || lower.equals("nu") || lower.equals("nư")) {
                        gioiTinh = "Nữ";
                    } else {
                        System.out.println("❌ Dòng " + lineNumber + ": Giới tính không hợp lệ: " + gioiTinh);
                        skipped++;
                        continue;
                    }

                    LocalDate ngaySinh = null;
                    if (!ngaySinhStr.isEmpty()) {
                        if (!ValidatorUtil.isValidateDate(ngaySinhStr)) {
                            System.out.println("❌ Dòng " + lineNumber + ": Ngày sinh không hợp lệ: " + ngaySinhStr);
                            skipped++;
                            continue;
                        }

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        ngaySinh = LocalDate.parse(ngaySinhStr, formatter);

                        if (ngaySinh.isAfter(LocalDate.now())) {
                            System.out.println("❌ Dòng " + lineNumber + ": Ngày sinh không được lớn hơn ngày hiện tại: " + ngaySinhStr);
                            skipped++;
                            continue;
                        }

                        int tuoi = Period.between(ngaySinh, LocalDate.now()).getYears();
                        if (tuoi < 0 || tuoi > 100) {
                            System.out.println("❌ Dòng " + lineNumber + ": Tuổi khách hàng không hợp lệ: " + tuoi);
                            skipped++;
                            continue;
                        }
                    }

                    if (dienThoai.isEmpty() || !ValidatorUtil.isValidPhoneNumber(dienThoai.replaceAll("\\s", ""))) {
                        System.out.println("❌ Dòng " + lineNumber + ": Số điện thoại không hợp lệ: " + dienThoai);
                        skipped++;
                        continue;
                    }
                    KhachHangDTO existing = timKhachHangTheoDienThoai(dienThoai.replaceAll("\\s", ""));
                    if (existing != null) {
                        System.out.println("⚠️  Dòng " + lineNumber + ": SĐT đã tồn tại (" + dienThoai + ")");
                        skipped++;
                        continue;
                    }
                    
                    if (!diaChi.isEmpty()) {
                        if (!ValidatorUtil.isValidAddress(diaChi)) {
                            System.out.println("❌ Dòng " + lineNumber + ": Địa chỉ không hợp lệ: " + diaChi);
                            skipped++;
                            continue;
                        }
                    } else {
                        diaChi = null;
                    }

                    KhachHangDTO kh = new KhachHangDTO(maKH, ho, ten, gioiTinh, ngaySinh, diaChi, dienThoai.replaceAll("\\s", ""));

                    if (themKhachHang(kh)) {
                        added++;
                        System.out.println("✅ Dòng " + lineNumber + ": Thêm thành công - " + maKH + " (" + ho + " " + ten + ")");
                    } else {
                        System.out.println("❌ Dòng " + lineNumber + ": Lỗi khi thêm vào DB");
                        skipped++;
                    }
                } catch (Exception e) {
                    System.err.println("❌ Dòng " + lineNumber + ": Lỗi - " + e.getMessage());
                    skipped++;
                }
            }

            System.out.println("\n╔═══════════════════════════════════════════════════╗");
            System.out.println("║            KẾT QUẢ IMPORT KHÁCH HÀNG              ║");
            System.out.println("╠═══════════════════════════════════════════════════╣");
            System.out.printf("║  📁 File           : %-28s║\n", new File(filePath).getName());
            System.out.printf("║  📊 Tổng dòng đọc  : %-28d║\n", lineNumber);
            System.out.printf("║  ✅ Thêm thành công: %-28d║\n", added);
            System.out.printf("║  ⚠️  Bỏ qua        : %-28d║\n", skipped);
            System.out.println("╚═══════════════════════════════════════════════════╝");

        } catch (IOException e) {
            System.err.println("❌ Lỗi khi đọc file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void thongKeTheoGioiTinh() {
        String query = "SELECT GioiTinh, COUNT(*) AS SoLuong FROM KHACHHANG GROUP BY GioiTinh ORDER BY SoLuong DESC";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n╔═════════════════════════════════════════╗");
            System.out.println("║    THỐNG KÊ KHÁCH HÀNG THEO GIỚI TÍNH   ║");
            System.out.println("╠══════════════════╤══════════════════════╣");
            System.out.printf("║ %-16s │ %-20s ║\n", "Giới Tính", "Số Lượng");
            System.out.println("╠══════════════════╪══════════════════════╣");

            int tongSo = 0;
            while (rs.next()) {
                String gioiTinh = rs.getString("GioiTinh");
                int soLuong = rs.getInt("SoLuong");
                tongSo += soLuong;

                System.out.printf("║ %-16s │ %-20d ║\n", gioiTinh, soLuong);
            }
            
            System.out.println("╠══════════════════╪══════════════════════╣");
            System.out.printf("║ %-16s │ %-20d ║\n", "Tổng cộng", tongSo);
            System.out.println("╚══════════════════╧══════════════════════╝");
            
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê khách hàng theo giới tính: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void thongKeTheoDoTuoi() {
        String query = """
            SELECT 
                CASE 
                    WHEN TIMESTAMPDIFF(YEAR, NgaySinh, CURDATE()) < 18 THEN 'Dưới 18 tuổi'
                    WHEN TIMESTAMPDIFF(YEAR, NgaySinh, CURDATE()) BETWEEN 18 AND 30 THEN '18-30 tuổi'
                    WHEN TIMESTAMPDIFF(YEAR, NgaySinh, CURDATE()) BETWEEN 31 AND 45 THEN '31-45 tuổi'
                    WHEN TIMESTAMPDIFF(YEAR, NgaySinh, CURDATE()) BETWEEN 46 AND 60 THEN '46-60 tuổi'
                    ELSE 'Trên 60 tuổi'
                END AS DoTuoi,
                COUNT(*) AS SoLuong        
            FROM KHACHHANG
            WHERE NgaySinh IS NOT NULL
            GROUP BY DoTuoi
            ORDER BY 
                CASE 
                    WHEN DoTuoi = 'Dưới 18 tuổi' THEN 1
                    WHEN DoTuoi = '18-30 tuổi' THEN 2
                    WHEN DoTuoi = '31-45 tuổi' THEN 3
                    WHEN DoTuoi = '46-60 tuổi' THEN 4
                    ELSE 5
                END
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {
            
            System.out.println("\n╔═════════════════════════════════════════╗");
            System.out.println("║    THỐNG KÊ KHÁCH HÀNG THEO ĐỘ TUỔI     ║");
            System.out.println("╠══════════════════╤══════════════════════╣");
            System.out.printf("║ %-16s │ %-20s ║\n", "Độ Tuổi", "Số Lượng");
            System.out.println("╠══════════════════╪══════════════════════╣");

            int tongSo = 0;
            while (rs.next()) {
                String doTuoi = rs.getString("DoTuoi");
                int soLuong = rs.getInt("SoLuong");
                tongSo += soLuong;

                System.out.printf("║ %-16s │ %-20d ║\n", doTuoi, soLuong);
            }
            
            System.out.println("╠══════════════════╪══════════════════════╣");
            System.out.printf("║ %-16s │ %-20d ║\n", "Tổng cộng", tongSo);
            System.out.println("╚══════════════════╧══════════════════════╝");
            
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê khách hàng theo độ tuổi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void thongKeTheoSohd() {
        String query = """
            SELECT KH.MaKH, KH.Ho, KH.Ten, COUNT(HD.MaHD) AS SoHoaDon
            FROM KHACHHANG KH
            LEFT JOIN HOADON HD ON KH.MaKH = HD.MaKH
            GROUP BY KH.MaKH, KH.Ho, KH.Ten
            ORDER BY SoHoaDon DESC
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
            System.out.println("║       THỐNG KÊ KHÁCH HÀNG THEO SỐ LƯỢNG ĐƠN HÀNG         ║");
            System.out.println("╠════════════╤══════════════════╤═══════════════╤══════════╣");
            System.out.printf("║ %-10s │ %-16s │ %-13s │ %-8s ║\n",
                    "Mã KH", "Họ", "Tên", "Số đơn");
            System.out.println("╠════════════╪══════════════════╪═══════════════╪══════════╣");

            int tongDon = 0;
            int soKH = 0;
            while (rs.next()) {
                String maKH = rs.getString("MaKH");
                String ho = rs.getString("Ho");
                String ten = rs.getString("Ten");
                int soHoaDon = rs.getInt("SoHoaDon");
                
                tongDon += soHoaDon;
                soKH++;

                System.out.printf("║ %-10s │ %-16s │ %-13s │ %-8d ║\n", 
                        maKH, ho, ten, soHoaDon);
            }
            
            System.out.println("╠════════════╧══════════════════╧═══════════════╪══════════╣");
            System.out.printf("║ %-42s    │ %-8d ║\n", "Tổng đơn hàng", tongDon);
            System.out.println("╚════════════════════════════════════════════════╧══════════╝");
            System.out.println("📊 Tổng số khách hàng: " + soKH);
            
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê khách hàng theo số lượng hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void thongKeTheoTongChiTieu() {
        String query = """
            SELECT KH.MaKH, KH.Ho, KH.Ten, COALESCE(SUM(HD.TongTien), 0) AS TongChiTieu
            FROM KHACHHANG KH        
            LEFT JOIN HOADON HD ON KH.MaKH = HD.MaKH
            GROUP BY KH.MaKH, KH.Ho, KH.Ten
            ORDER BY TongChiTieu DESC
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
            System.out.println("║          THỐNG KÊ KHÁCH HÀNG THEO TỔNG CHI TIÊU                  ║");
            System.out.println("╠════════════╤══════════════════╤═══════════════╤══════════════════╣");
            System.out.printf("║ %-10s │ %-16s │ %-13s │ %-16s ║\n",
                    "Mã KH", "Họ", "Tên", "Tổng chi tiêu");
            System.out.println("╠════════════╪══════════════════╪═══════════════╪══════════════════╣");

            long tongChiTieuTatCa = 0;
            int soKH = 0;
            while (rs.next()) {
                String maKH = rs.getString("MaKH");
                String ho = rs.getString("Ho");
                String ten = rs.getString("Ten");
                long tongChiTieu = rs.getLong("TongChiTieu");
                
                tongChiTieuTatCa += tongChiTieu;
                soKH++;

                System.out.printf("║ %-10s │ %-16s │ %-13s │ %-16s ║\n", 
                        maKH, ho, ten, FormatUtil.formatVND(tongChiTieu));
            }
            
            System.out.println("╠════════════╧══════════════════╧═══════════════╪══════════════════╣");
            System.out.printf("║ %-42s │ %-16s ║\n", "Tổng cộng", 
                    FormatUtil.formatVND(tongChiTieuTatCa));
            System.out.println("╚════════════════════════════════════════════════╧══════════════════╝");
            System.out.println("📊 Tổng số khách hàng: " + soKH);
            
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê khách hàng theo tổng chi tiêu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String generateIDKhachHang() {
        String prefix = "KH";
        String newID = prefix + "001";
        String query = "SELECT MaKH FROM KHACHHANG ORDER BY MaKH DESC LIMIT 1";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String lastID = rs.getString("MaKH");
                int number = Integer.parseInt(lastID.substring(prefix.length()));
                number++;
                newID = prefix + String.format("%03d", number);
            }
        } catch (SQLException | NumberFormatException e) {
            System.err.println("❌ Lỗi khi tạo mã khách hàng: " + e.getMessage());
            e.printStackTrace();
        }
        return newID;
    }
}
            