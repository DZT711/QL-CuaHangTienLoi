package dao;

import java.sql.*;

import dto.ChiTietHoaDonDTO;
import dto.HoaDonDTO;
import util.FormatUtil;
import util.JDBCUtil;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HoaDonDAO {
    public static List<HoaDonDTO> getAllHoaDon() {
        List<HoaDonDTO> list = new ArrayList<>();

        String query = "SELECT MaHD, MaKH, MaNV, TongTien, NgayLapHD, PhuongThucTT, TienKhachDua, TienThua FROM HOADON ORDER BY NgayLapHD DESC";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    HoaDonDTO hd = new HoaDonDTO();
                    hd.setMaHD(rs.getString("MaHD"));
                    hd.setMaKH(rs.getString("MaKH"));
                    hd.setMaNV(rs.getString("MaNV"));
                    hd.setTongTien(rs.getInt("TongTien"));
                    hd.setNgayLapHD(rs.getTimestamp("NgayLapHD").toLocalDateTime());
                    hd.setPhuongThucTT(rs.getString("PhuongThucTT"));
                    hd.setTienKhachDua(rs.getInt("TienKhachDua"));
                    hd.setTienThua(rs.getInt("TienThua"));
                    list.add(hd);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi lấy tất cả hóa đơn: " + e.getMessage());
        }
        return list;
    }

    public static boolean themHoaDon(HoaDonDTO hd) {
        String query = "INSERT INTO HOADON (MaHD, MaKH, MaNV, TongTien, PhuongThucTT, TienKhachDua, TienThua) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, hd.getMaHD());
            stmt.setString(2, hd.getMaKH());
            stmt.setString(3, hd.getMaNV());
            stmt.setInt(4, hd.getTongTien());
            stmt.setString(5, hd.getPhuongThucTT());
            stmt.setInt(6, hd.getTienKhachDua());
            stmt.setInt(7, hd.getTienThua());

            int rowAffected = stmt.executeUpdate(); 
            return rowAffected > 0;
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thêm hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static boolean huyHoaDon(String maHD) {
        if (maHD == null || maHD.trim().isEmpty()) {
            System.err.println("❌ Mã hóa đơn không được rỗng!");
            return false;
        }

        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);

            // kiểm tra hóa đơn đã đc hủy chưa 
            String queryCheck = "SELECT TrangThai FROM HOADON WHERE MaHD = ?";
            try (PreparedStatement stmt = conn.prepareStatement(queryCheck)) {
                stmt.setString(1, maHD);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        System.err.println("❌ Không tìm thấy hóa đơn!");
                        conn.rollback();
                        return false;
                    }
                    if ("cancelled".equals(rs.getString("TrangThai"))) {
                        System.err.println("❌ Hóa đơn đã bị hủy trước đó!");
                        conn.rollback();
                        return false;
                    }
                }
            }

            // lấy chi tiết hóa đơn
            List<ChiTietHoaDonDTO> chiTietList = ChiTietHoaDonDAO.timChiTietHoaDon(maHD);

            // hoàn lại tồn kho 
            for (ChiTietHoaDonDTO ctHoaDon : chiTietList) {
                String maHang = ctHoaDon.getMaHang();
                int soLuong = ctHoaDon.getSoLuong();
                
                // cập nhật thuộc tính soLuongConLai của hàng hóa
                if (!HangHoaDAO.congSoLuongConLai(conn, maHang, soLuong)) {
                    System.err.println("❌ Lỗi khi cộng số lượng lô hàng!");
                    conn.rollback();
                    return false;
                }
                
                // cập nhật tồn kho của sản phẩm
                if (!SanPhamDAO.congSoLuongTon(conn, maHang, soLuong)) {
                    System.err.println("❌ Lỗi khi cộng số lượng tồn!");
                    conn.rollback();
                    return false;
                }
            }
            
            // cập nhật trạng thái hóa đơn thành 'cancelled'
            String queryHuyHD = "UPDATE HOADON SET TrangThai = 'cancelled' WHERE MaHD = ?";
            try (PreparedStatement stmt = conn.prepareStatement(queryHuyHD)) {
                stmt.setString(1, maHD);
                int rowAffected = stmt.executeUpdate();
                
                if (rowAffected > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println("❌ Đã rollback do lỗi: " + e.getMessage());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.err.println("❌ Lỗi khi hủy hóa đơn: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String generateIDHoaDon() {
        String prefix = "HD";
        String newID = prefix + "001";
        String query = "SELECT MaHD FROM HOADON ORDER BY MaHD DESC LIMIT 1";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String lastID = rs.getString("MaHD");
                int number = Integer.parseInt(lastID.substring(prefix.length()));
                number++;
                newID = prefix + String.format("%03d", number);
            }
        } catch (SQLException | NumberFormatException e) {
            System.err.println("❌ Lỗi khi tạo mã hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
        return newID;
    }

    public static HoaDonDTO timHoaDon(String maHD) {
        if (maHD == null || maHD.trim().isEmpty()) {
            System.err.println("❌ Mã hóa đơn không được rỗng!");
            return null;
        }

        String query = "SELECT MaKH, MaNV, TienKhachDua, TienThua, TongTien, PhuongThucTT, ThoiGianLapHD, TrangThai FROM HOADON WHERE MaHD = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, maHD);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new HoaDonDTO(
                        maHD,
                        rs.getString("MaKH"),
                        rs.getString("MaNV"),
                        rs.getInt("TienKhachDua"),
                        rs.getInt("TienThua"),
                        rs.getInt("TongTien"),
                        rs.getTimestamp("ThoiGianLapHD").toLocalDateTime(),
                        rs.getString("PhuongThucTT"),
                        rs.getString("TrangThai") 
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static void timHoaDonTheoMaKH(String maKH) {
        String query = 
            "SELECT hd.MaHD, hd.ThoiGianLapHD, nv.Ho, nv.Ten, hd.TongTien, hd.PhuongThucTT " + 
            "FROM HOADON hd " +
            "INNER JOIN NHANVIEN nv ON hd.MaNV = nv.MaNV " +
            "WHERE hd.MaKH = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maKH);
            
            try (ResultSet rs = stmt.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    System.out.println("--------------------------------");
                    System.out.println("Mã hóa đơn         : " + rs.getString("MaHD"));
                    System.out.println("Ngày lập           : " + rs.getTimestamp("NgayLapHD").toLocalDateTime()
                        .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                    System.out.println("Nhân viên          : " + rs.getString("Ho") + " " + rs.getString("Ten"));
                    System.out.println("Tổng tiền          : " + FormatUtil.formatVND(rs.getInt("TongTien")));
                    System.out.println("Phương thức TT     : " + rs.getString("PhuongThucTT"));
                    count++;
                }
                System.out.println("--------------------------------");
                System.out.println("📊 Tổng cộng: " + count + " hóa đơn");
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm hóa đơn theo mã khách hàng: " + e.getMessage());
        }
    }

    public static void timHoaDonTheoMaNV(String maNV) {
        String query = 
            "SELECT hd.MaHD, hd.MaKH, hd.ThoiGianLapHD, hd.TongTien, hd.PhuongThucTT " +
            "FROM HOADON hd " +
            "INNER JOIN NHANVIEN nv ON hd.MaNV = nv.MaNV " +
            "WHERE hd.MaNV = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maNV);
            
            try (ResultSet rs = stmt.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    System.out.println("--------------------------------");
                    System.out.println("Mã hóa đơn         : " + rs.getString("MaHD"));
                    System.out.println("Mã khách hàng      : " + rs.getString("MaKH"));
                    System.out.println("Ngày lập           : " + rs.getTimestamp("NgayLapHD").toLocalDateTime()
                        .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                    System.out.println("Tổng tiền          : " + FormatUtil.formatVND(rs.getInt("TongTien")));
                    System.out.println("Phương thức TT     : " + rs.getString("PhuongThucTT"));
                    count++;
                }
                System.out.println("--------------------------------");
                if (count > 0) {
                    System.out.println("📊 Tổng cộng: " + count + " hóa đơn");
                } else {
                    System.out.println("⚠️ Không tìm thấy hóa đơn nào");
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm hóa đơn theo mã nhân viên: " + e.getMessage());
        }
    }
    
    public static List<HoaDonDTO> timHoaDonTheoNgayLap(LocalDate fromDate, LocalDate toDate) {
        List<HoaDonDTO> list = new ArrayList<>();

        String query = 
            "SELECT MaHD, MaKH, MaNV, TongTien, PhuongThucTT, NgayLapHD, TienKhachDua, TienThua " +
            "FROM HOADON " +
            "WHERE NgayLapHD >= ? AND NgayLapHD < ? " +
            "ORDER BY NgayLapHD ASC";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toExclusive = toDate.plusDays(1).atStartOfDay();

            stmt.setTimestamp(1, Timestamp.valueOf(fromDateTime));
            stmt.setTimestamp(2, Timestamp.valueOf(toExclusive));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    HoaDonDTO hd = new HoaDonDTO();
                    hd.setMaHD(rs.getString("MaHD"));
                    hd.setMaKH(rs.getString("MaKH"));
                    hd.setMaNV(rs.getString("MaNV"));
                    hd.setTongTien(rs.getInt("TongTien"));
                    hd.setNgayLapHD(rs.getTimestamp("NgayLapHD").toLocalDateTime());
                    hd.setPhuongThucTT(rs.getString("PhuongThucTT"));
                    hd.setTienKhachDua(rs.getInt("TienKhachDua"));
                    hd.setTienThua(rs.getInt("TienThua"));
                    list.add(hd);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi tìm hóa đơn theo ngày lập: " + e.getMessage());
        }
        return list;
    }

    public static Map<String, Object> thongKeHDTheoThoiGian(LocalDate fromDate, LocalDate toDate) {
        Map<String, Object> result = new HashMap<>();

        String query = """
                SELECT 
                    COUNT(DISTINCT hd.MaHD) AS SoHoaDon,
                    COUNT(DISTINCT hd.MaKH) AS SoKhachHang,
                    SUM(ct.SoLuong) AS TongSanPham,
                    SUM(hd.TongTien) AS TongDoanhThu,
                    (SUM(hd.TongTien) / COUNT(DISTINCT hd.MaHD)) AS DoanhThuTrungBinh
                FROM HOADON hd
                JOIN CHITIETHOADON ct ON hd.MaHD = ct.MaHD
                WHERE hd.NgayLapHD BETWEEN ? AND ?;
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
                
            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();

            stmt.setTimestamp(1, Timestamp.valueOf(fromDateTime));
            stmt.setTimestamp(2, Timestamp.valueOf(toDateTime));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    result.put("SoHoaDon", rs.getInt("SoHoaDon"));
                    result.put("SoKhachHang", rs.getInt("SoKhachHang"));
                    result.put("TongSanPham", rs.getInt("TongSanPham"));
                    result.put("TongDoanhThu", rs.getLong("TongDoanhThu"));
                    result.put("DoanhThuTrungBinh", rs.getDouble("DoanhThuTrungBinh"));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê hóa đơn theo thời gian: " + e.getMessage());
        }
        return result;
    }

    public static List<Map<String, Object>> thongKeHDTheoNhanVien(LocalDate fromDate, LocalDate toDate) {
        List<Map<String, Object>> result = new ArrayList<>();

        String query = """
                SELECT 
                    nv.MaNV,
                    nv.Ho,
                    nv.Ten,
                    COUNT(DISTINCT hd.MaHD) AS SoHoaDon,
                    SUM(ct.SoLuong) AS TongSanPham,
                    SUM(hd.TongTien) AS TongDoanhThu
                FROM HOADON hd
                JOIN NHANVIEN nv ON hd.MaNV = nv.MaNV
                JOIN CHITIETHOADON ct ON hd.MaHD = ct.MaHD
                WHERE hd.NgayLapHD BETWEEN ? AND ?
                GROUP BY nv.MaNV, nv.Ho, nv.Ten
                ORDER BY TongDoanhThu DESC;
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();

            stmt.setTimestamp(1, Timestamp.valueOf(fromDateTime));
            stmt.setTimestamp(2, Timestamp.valueOf(toDateTime));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("MaNV", rs.getString("MaNV"));
                    row.put("Ho Ten", rs.getString("Ho") + " " + rs.getString("Ten"));
                    row.put("SoHoaDon", rs.getInt("SoHoaDon"));
                    row.put("TongSanPham", rs.getInt("TongSanPham"));
                    row.put("TongDoanhThu", rs.getLong("TongDoanhThu"));
                    result.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê hóa đơn theo nhân viên: " + e.getMessage());
        }
        return result;
    }

    public static List<Map<String, Object>> thongKeHDTheoKhachHang(LocalDate fromDate, LocalDate toDate) {
        List<Map<String, Object>> result = new ArrayList<>();

        String query = """
                SELECT 
                    kh.MaKH,
                    kh.Ho,
                    kh.Ten,
                    COUNT(DISTINCT hd.MaHD) AS SoHoaDon,
                    SUM(ct.SoLuong) AS TongSanPham,
                    SUM(hd.TongTien) AS TongChiTieu
                FROM HOADON hd
                JOIN KHACHHANG kh ON hd.MaKH = kh.MaKH
                JOIN CHITIETHOADON ct ON hd.MaHD = ct.MaHD
                WHERE hd.NgayLapHD BETWEEN ? AND ?
                GROUP BY kh.MaKH, kh.Ho, kh.Ten
                ORDER BY TongChiTieu DESC;
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
        
            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();

            stmt.setTimestamp(1, Timestamp.valueOf(fromDateTime));
            stmt.setTimestamp(2, Timestamp.valueOf(toDateTime));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("MaKH", rs.getString("MaKH"));
                    row.put("Ho Ten", rs.getString("Ho") + " " + rs.getString("Ten"));
                    row.put("SoHoaDon", rs.getInt("SoHoaDon"));
                    row.put("TongSanPham", rs.getInt("TongSanPham"));
                    row.put("TongChiTieu", rs.getLong("TongChiTieu"));
                    result.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê hóa đơn theo khách hàng: " + e.getMessage());
        }
        return result;
    }

    public static List<Map<String, Object>> thongKeHDTheoNam(int year) {
        List<Map<String, Object>> result = new ArrayList<>();

        String query = """
            SELECT 
                MONTH(NgayLapHD) AS Thang,
                COUNT(DISTINCT MaHD) AS SoHoaDon,
                SUM(ct.SoLuong) AS TongSanPham,
                SUM(hd.TongTien) AS TongDoanhThu
            FROM HOADON hd
            JOIN CHITIETHOADON ct ON hd.MaHD = ct.MaHD
            WHERE YEAR(NgayLapHD) = ?
            GROUP BY MONTH(NgayLapHD)
            ORDER BY Thang ASC;
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
                
            stmt.setInt(1, year);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("Thang", rs.getInt("Thang"));
                    row.put("SoHoaDon", rs.getInt("SoHoaDon"));
                    row.put("TongSanPham", rs.getInt("TongSanPham"));
                    row.put("TongDoanhThu", rs.getLong("TongDoanhThu"));
                    result.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê hóa đơn theo năm: " + e.getMessage());
        }
        return result;
    }

    public static List<Map<String, Object>> thongKeHDTheoPhuongThucTT(LocalDate fromDate, LocalDate toDate) {
        List<Map<String, Object>> result = new ArrayList<>();

        String query = """
            SELECT 
                hd.PhuongThucTT,
                COUNT(DISTINCT hd.MaHD) AS SoHoaDon,
                SUM(ct.SoLuong) AS TongSanPham,
                SUM(hd.TongTien) AS TongDoanhThu
            FROM HOADON hd
            JOIN CHITIETHOADON ct ON hd.MaHD = ct.MaHD
            WHERE NgayLapHD BETWEEN ? AND ?
            GROUP BY PhuongThucTT
            ORDER BY TongDoanhThu DESC;
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();

            stmt.setTimestamp(1, Timestamp.valueOf(fromDateTime));
            stmt.setTimestamp(2, Timestamp.valueOf(toDateTime));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("PTTT", rs.getString("PhuongThucTT"));
                    row.put("SoHoaDon", rs.getInt("SoHoaDon"));
                    row.put("TongSanPham", rs.getInt("TongSanPham"));
                    row.put("TongDoanhThu", rs.getLong("TongDoanhThu"));
                    result.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê hóa đơn theo phương thức thanh toán: " + e.getMessage());
        }
        return result;
    }
}
