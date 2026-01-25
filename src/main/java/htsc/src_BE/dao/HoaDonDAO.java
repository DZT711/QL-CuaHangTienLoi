package htsc.src_BE.dao;

import java.sql.*;

import htsc.src_BE.dto.ChiTietHoaDonDTO;
import htsc.src_BE.dto.HangHoaDTO;
import htsc.src_BE.dto.HoaDonDTO;
import htsc.src_BE.util.FormatUtil;
import htsc.src_BE.util.JDBCUtil;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HoaDonDAO {
    public static List<HoaDonDTO> getAllHoaDon(boolean baoGomHuy) {
        List<HoaDonDTO> list = new ArrayList<>();

        String query = baoGomHuy ?
        """
        SELECT MaHD, MaKH, MaNV, TongTien, ThoiGianLapHD, PhuongThucTT, 
            TienKhachDua, TienThua, TrangThai
        FROM HOADON 
        ORDER BY ThoiGianLapHD DESC
        """ :
        """
        SELECT MaHD, MaKH, MaNV, TongTien, ThoiGianLapHD, PhuongThucTT, 
            TienKhachDua, TienThua, TrangThai
        FROM HOADON 
        WHERE TrangThai = 'active'
        ORDER BY ThoiGianLapHD DESC
        """ ;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    HoaDonDTO hd = new HoaDonDTO();
                    hd.setMaHD(rs.getString("MaHD"));
                    hd.setMaKH(rs.getString("MaKH"));
                    hd.setMaNV(rs.getString("MaNV"));
                    hd.setTongTien(rs.getInt("TongTien"));
                    hd.setNgayLapHD(rs.getTimestamp("ThoiGianLapHD").toLocalDateTime()); 
                    hd.setPhuongThucTT(rs.getString("PhuongThucTT"));
                    hd.setTienKhachDua(rs.getInt("TienKhachDua"));
                    hd.setTienThua(rs.getInt("TienThua"));
                    hd.setTrangThai(rs.getString("TrangThai")); 
                    list.add(hd);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi lấy tất cả hóa đơn: " + e.getMessage());
            e.printStackTrace();
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

            if (chiTietList == null || chiTietList.isEmpty()) {
                System.out.println("⚠️ Hóa đơn không có chi tiết.");
            } else {
                // hoàn lại tồn kho 
                for (ChiTietHoaDonDTO ctHoaDon : chiTietList) {
                    String maHang = ctHoaDon.getMaHang();
                    int soLuong = ctHoaDon.getSoLuong();
                    
                    // ✅ QUAN TRỌNG: Lấy maSP từ HangHoaDTO
                    HangHoaDTO hangHoa = HangHoaDAO.timHangHoaTheoMa(maHang);
                    if (hangHoa == null) {
                        System.err.println("❌ Không tìm thấy hàng hóa: " + maHang);
                        conn.rollback();
                        return false;
                    }
                    
                    String maSP = hangHoa.getMaSP();
                    
                    // cập nhật số lượng còn lại của lô hàng
                    if (!HangHoaDAO.congSoLuongConLai(conn, maHang, soLuong)) {
                        System.err.println("❌ Lỗi khi cộng số lượng lô hàng!");
                        conn.rollback();
                        return false;
                    }
                    
                    // cập nhật tồn kho sản phẩm với maSP
                    if (!SanPhamDAO.congSoLuongTon(conn, maSP, soLuong)) {
                        System.err.println("❌ Lỗi khi cộng số lượng tồn!");
                        conn.rollback();
                        return false;
                    }
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

    public static List<Map<String, Object>> timHoaDonTheoMaKH(String maKH, boolean baoGomHuy) {
        if (maKH == null || maKH.trim().isEmpty()) {
            System.err.println("❌ Mã khách hàng không được rỗng!");
            return new ArrayList<>();
        }

        String query = baoGomHuy ?
            """
            SELECT hd.MaHD, hd.ThoiGianLapHD, nv.Ho, nv.Ten, hd.TongTien, hd.PhuongThucTT, hd.TrangThai
            FROM HOADON hd 
            INNER JOIN NHANVIEN nv ON hd.MaNV = nv.MaNV 
            WHERE hd.MaKH = ?
            ORDER BY hd.ThoiGianLapHD DESC
            """ :
            """
            SELECT hd.MaHD, hd.ThoiGianLapHD, nv.Ho, nv.Ten, hd.TongTien, hd.PhuongThucTT, hd.TrangThai
            FROM HOADON hd 
            INNER JOIN NHANVIEN nv ON hd.MaNV = nv.MaNV 
            WHERE hd.MaKH = ? AND hd.TrangThai = 'active'
            ORDER BY hd.ThoiGianLapHD DESC
            """;

        List<Map<String, Object>> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maKH);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("MaHD", rs.getString("MaHD"));
                    row.put("ThoiGianLapHD", rs.getTimestamp("ThoiGianLapHD").toLocalDateTime());
                    row.put("HoNV", rs.getString("Ho"));
                    row.put("TenNV", rs.getString("Ten"));
                    row.put("TongTien", rs.getInt("TongTien"));
                    row.put("PhuongThucTT", rs.getString("PhuongThucTT"));
                    row.put("TrangThai", rs.getString("TrangThai"));
                    list.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm hóa đơn theo mã khách hàng: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public static List<Map<String, Object>> timHoaDonTheoMaNV(String maNV, boolean baoGomHuy) {
        if (maNV == null || maNV.trim().isEmpty()) {
            System.err.println("❌ Mã nhân viên không được rỗng!");
            return new ArrayList<>();
        }

        String query = baoGomHuy ?
            """
            SELECT hd.MaHD, hd.MaKH, hd.ThoiGianLapHD, hd.TongTien, hd.PhuongThucTT, hd.TrangThai
            FROM HOADON hd 
            INNER JOIN NHANVIEN nv ON hd.MaNV = nv.MaNV 
            WHERE hd.MaNV = ?
            ORDER BY hd.ThoiGianLapHD DESC
            """ :
            """
            SELECT hd.MaHD, hd.MaKH, hd.ThoiGianLapHD, hd.TongTien, hd.PhuongThucTT, hd.TrangThai
            FROM HOADON hd 
            INNER JOIN NHANVIEN nv ON hd.MaNV = nv.MaNV 
            WHERE hd.MaNV = ? AND hd.TrangThai = 'active'
            ORDER BY hd.ThoiGianLapHD DESC
            """;

        List<Map<String, Object>> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maNV);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("MaHD", rs.getString("MaHD"));
                    row.put("MaKH", rs.getString("MaKH"));
                    row.put("ThoiGianLapHD", rs.getTimestamp("ThoiGianLapHD").toLocalDateTime()); 
                    row.put("TongTien", rs.getInt("TongTien"));
                    row.put("PhuongThucTT", rs.getString("PhuongThucTT"));
                    row.put("TrangThai", rs.getString("TrangThai"));
                    list.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm hóa đơn theo mã nhân viên: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public static List<HoaDonDTO> timHoaDonTheoNgayLap(LocalDate fromDate, LocalDate toDate, boolean baoGomHuy) {
        if (fromDate == null || toDate == null) {
            System.err.println("❌ Ngày tháng không được rỗng!");
            return new ArrayList<>();
        }

        if (fromDate.isAfter(toDate)) {
            System.err.println("❌ Ngày bắt đầu không được sau ngày kết thúc!");
            return new ArrayList<>();
        }

        List<HoaDonDTO> list = new ArrayList<>();

        String query = baoGomHuy ?
            """
            SELECT MaHD, MaKH, MaNV, TongTien, PhuongThucTT, ThoiGianLapHD, 
                TienKhachDua, TienThua, TrangThai
            FROM HOADON 
            WHERE ThoiGianLapHD >= ? AND ThoiGianLapHD < ? 
            ORDER BY ThoiGianLapHD ASC
            """ :
            """
            SELECT MaHD, MaKH, MaNV, TongTien, PhuongThucTT, ThoiGianLapHD, 
                TienKhachDua, TienThua, TrangThai
            FROM HOADON 
            WHERE ThoiGianLapHD >= ? AND ThoiGianLapHD < ? 
            AND TrangThai = 'active'
            ORDER BY ThoiGianLapHD ASC
            """ ;

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
                    hd.setNgayLapHD(rs.getTimestamp("ThoiGianLapHD").toLocalDateTime());
                    hd.setPhuongThucTT(rs.getString("PhuongThucTT"));
                    hd.setTienKhachDua(rs.getInt("TienKhachDua"));
                    hd.setTienThua(rs.getInt("TienThua"));
                    hd.setTrangThai(rs.getString("TrangThai")); 
                    list.add(hd);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm hóa đơn theo ngày lập: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public static Map<String, Object> thongKeHDTheoThoiGian(LocalDate fromDate, LocalDate toDate, boolean baoGomHuy) {
        if (fromDate == null || toDate == null) {
            System.err.println("❌ Ngày tháng không được rỗng!");
            return new HashMap<>();
        }

        if (fromDate.isAfter(toDate)) {
            System.err.println("❌ Ngày bắt đầu không được sau ngày kết thúc!");
            return new HashMap<>();
        }

        Map<String, Object> result = new HashMap<>();

        String query = baoGomHuy ?
        """
        SELECT 
            COUNT(DISTINCT hd.MaHD) AS SoHoaDon,
            COUNT(DISTINCT hd.MaKH) AS SoKhachHang,
            COALESCE(SUM(ct.SoLuong), 0) AS TongSanPham,
            COALESCE(SUM(hd.TongTien), 0) AS TongDoanhThu,
            CASE 
                WHEN COUNT(DISTINCT hd.MaHD) > 0 
                THEN CAST(SUM(hd.TongTien) AS DECIMAL) / COUNT(DISTINCT hd.MaHD)
                ELSE 0 
            END AS DoanhThuTrungBinh,
            COUNT(CASE WHEN hd.TrangThai = 'cancelled' THEN 1 END) AS SoHoaDonHuy
        FROM HOADON hd
        LEFT JOIN CHITIETHOADON ct ON hd.MaHD = ct.MaHD
        WHERE hd.ThoiGianLapHD >= ? AND hd.ThoiGianLapHD < ?
        """ :
        """
        SELECT 
            COUNT(DISTINCT hd.MaHD) AS SoHoaDon,
            COUNT(DISTINCT hd.MaKH) AS SoKhachHang,
            COALESCE(SUM(ct.SoLuong), 0) AS TongSanPham,
            COALESCE(SUM(hd.TongTien), 0) AS TongDoanhThu,
            CASE 
                WHEN COUNT(DISTINCT hd.MaHD) > 0 
                THEN CAST(SUM(hd.TongTien) AS DECIMAL) / COUNT(DISTINCT hd.MaHD)
                ELSE 0 
            END AS DoanhThuTrungBinh
        FROM HOADON hd
        LEFT JOIN CHITIETHOADON ct ON hd.MaHD = ct.MaHD
        WHERE hd.ThoiGianLapHD >= ? AND hd.ThoiGianLapHD < ?
            AND hd.TrangThai = 'active'
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
                
            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toExclusive = toDate.plusDays(1).atStartOfDay();

            stmt.setTimestamp(1, Timestamp.valueOf(fromDateTime));
            stmt.setTimestamp(2, Timestamp.valueOf(toExclusive));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    result.put("SoHoaDon", rs.getInt("SoHoaDon"));
                    result.put("SoKhachHang", rs.getInt("SoKhachHang"));
                    result.put("TongSanPham", rs.getInt("TongSanPham"));
                    result.put("TongDoanhThu", rs.getLong("TongDoanhThu"));
                    result.put("DoanhThuTrungBinh", rs.getLong("DoanhThuTrungBinh"));
                    
                    if (baoGomHuy) {
                        result.put("SoHoaDonHuy", rs.getInt("SoHoaDonHuy"));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê hóa đơn theo thời gian: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static List<Map<String, Object>> thongKeHDTheoNhanVien(LocalDate fromDate, LocalDate toDate, boolean baoGomHuy) {
        if (fromDate == null || toDate == null) {
            System.err.println("❌ Ngày không được null!");
            return new ArrayList<>();
        }
        
        if (fromDate.isAfter(toDate)) {
            System.err.println("❌ Ngày bắt đầu phải trước hoặc bằng ngày kết thúc!");
            return new ArrayList<>();
        }

        List<Map<String, Object>> result = new ArrayList<>();

        String query = baoGomHuy ?
            """
            SELECT 
                nv.MaNV,
                nv.Ho,
                nv.Ten,
                COUNT(DISTINCT hd.MaHD) AS SoHoaDon,
                COALESCE(SUM(ct.SoLuong), 0) AS TongSanPham,
                COALESCE(SUM(hd.TongTien), 0) AS TongDoanhThu,
                COUNT(CASE WHEN hd.TrangThai = 'cancelled' THEN 1 END) AS SoHoaDonHuy
            FROM NHANVIEN nv
            LEFT JOIN HOADON hd ON nv.MaNV = hd.MaNV 
                AND hd.ThoiGianLapHD >= ? AND hd.ThoiGianLapHD < ?
            LEFT JOIN CHITIETHOADON ct ON hd.MaHD = ct.MaHD
            GROUP BY nv.MaNV, nv.Ho, nv.Ten
            HAVING COUNT(DISTINCT hd.MaHD) > 0
            ORDER BY TongDoanhThu DESC
            """ :
            """
            SELECT 
                nv.MaNV,
                nv.Ho,
                nv.Ten,
                COUNT(DISTINCT hd.MaHD) AS SoHoaDon,
                COALESCE(SUM(ct.SoLuong), 0) AS TongSanPham,
                COALESCE(SUM(hd.TongTien), 0) AS TongDoanhThu
            FROM NHANVIEN nv
            LEFT JOIN HOADON hd ON nv.MaNV = hd.MaNV 
                AND hd.ThoiGianLapHD >= ? AND hd.ThoiGianLapHD < ?
                AND hd.TrangThai = 'active'
            LEFT JOIN CHITIETHOADON ct ON hd.MaHD = ct.MaHD
            GROUP BY nv.MaNV, nv.Ho, nv.Ten
            HAVING COUNT(DISTINCT hd.MaHD) > 0
            ORDER BY TongDoanhThu DESC
            """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toExclusive = toDate.plusDays(1).atStartOfDay();

            stmt.setTimestamp(1, Timestamp.valueOf(fromDateTime));
            stmt.setTimestamp(2, Timestamp.valueOf(toExclusive));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("MaNV", rs.getString("MaNV"));
                    row.put("Ho", rs.getString("Ho"));
                    row.put("Ten", rs.getString("Ten"));
                    row.put("HoTen", rs.getString("Ho") + " " + rs.getString("Ten")); 
                    row.put("SoHoaDon", rs.getInt("SoHoaDon"));
                    row.put("TongSanPham", rs.getInt("TongSanPham"));
                    row.put("TongDoanhThu", rs.getLong("TongDoanhThu"));
                    
                    if (baoGomHuy) {
                        row.put("SoHoaDonHuy", rs.getInt("SoHoaDonHuy"));
                    }
                    result.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê hóa đơn theo nhân viên: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


    public static List<Map<String, Object>> thongKeHDTheoKhachHang(LocalDate fromDate, LocalDate toDate, boolean baoGomHuy) {
        if (fromDate == null || toDate == null) {
            System.err.println("❌ Ngày không được null!");
            return new ArrayList<>();
        }
        
        if (fromDate.isAfter(toDate)) {
            System.err.println("❌ Ngày bắt đầu phải trước hoặc bằng ngày kết thúc!");
            return new ArrayList<>();
        }

        List<Map<String, Object>> result = new ArrayList<>();

        String query = baoGomHuy ?
            """
            SELECT 
                kh.MaKH,
                kh.Ho,
                kh.Ten,
                COUNT(DISTINCT hd.MaHD) AS SoHoaDon,
                COALESCE(SUM(ct.SoLuong), 0) AS TongSanPham,
                COALESCE(SUM(hd.TongTien), 0) AS TongChiTieu,
                COUNT(CASE WHEN hd.TrangThai = 'cancelled' THEN 1 END) AS SoHoaDonHuy
            FROM KHACHHANG kh
            LEFT JOIN HOADON hd ON kh.MaKH = hd.MaKH 
                AND hd.ThoiGianLapHD >= ? AND hd.ThoiGianLapHD < ?
            LEFT JOIN CHITIETHOADON ct ON hd.MaHD = ct.MaHD
            GROUP BY kh.MaKH, kh.Ho, kh.Ten
            HAVING COUNT(DISTINCT hd.MaHD) > 0
            ORDER BY TongChiTieu DESC
            """ :
            """
            SELECT 
                kh.MaKH,
                kh.Ho,
                kh.Ten,
                COUNT(DISTINCT hd.MaHD) AS SoHoaDon,
                COALESCE(SUM(ct.SoLuong), 0) AS TongSanPham,
                COALESCE(SUM(hd.TongTien), 0) AS TongChiTieu
            FROM KHACHHANG kh
            LEFT JOIN HOADON hd ON kh.MaKH = hd.MaKH 
                AND hd.ThoiGianLapHD >= ? AND hd.ThoiGianLapHD < ?
                AND hd.TrangThai = 'active'
            LEFT JOIN CHITIETHOADON ct ON hd.MaHD = ct.MaHD
            GROUP BY kh.MaKH, kh.Ho, kh.Ten
            HAVING COUNT(DISTINCT hd.MaHD) > 0
            ORDER BY TongChiTieu DESC
            """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
        
            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toExclusive = toDate.plusDays(1).atStartOfDay();

            stmt.setTimestamp(1, Timestamp.valueOf(fromDateTime));
            stmt.setTimestamp(2, Timestamp.valueOf(toExclusive));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("MaKH", rs.getString("MaKH"));
                    row.put("Ho", rs.getString("Ho"));
                    row.put("Ten", rs.getString("Ten"));
                    row.put("HoTen", rs.getString("Ho") + " " + rs.getString("Ten")); 
                    row.put("SoHoaDon", rs.getInt("SoHoaDon"));
                    row.put("TongSanPham", rs.getInt("TongSanPham"));
                    row.put("TongChiTieu", rs.getLong("TongChiTieu"));
                    
                    if (baoGomHuy) {
                        row.put("SoHoaDonHuy", rs.getInt("SoHoaDonHuy"));
                    }
                    
                    result.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê hóa đơn theo khách hàng: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


    public static List<Map<String, Object>> thongKeHDTheoNam(int year, boolean baoGomHuy) {
        if (year < 2000 || year > LocalDate.now().getYear() + 1) {
            System.err.println("❌ Năm không hợp lệ!");
            return new ArrayList<>();
        }

        List<Map<String, Object>> result = new ArrayList<>();

        String query = baoGomHuy ?
            """
            SELECT 
                MONTH(ThoiGianLapHD) AS Thang,
                COUNT(DISTINCT hd.MaHD) AS SoHoaDon,
                COALESCE(SUM(ct.SoLuong), 0) AS TongSanPham,
                COALESCE(SUM(hd.TongTien), 0) AS TongDoanhThu,
                COUNT(CASE WHEN hd.TrangThai = 'cancelled' THEN 1 END) AS SoHoaDonHuy
            FROM HOADON hd
            LEFT JOIN CHITIETHOADON ct ON hd.MaHD = ct.MaHD
            WHERE YEAR(ThoiGianLapHD) = ?
            GROUP BY MONTH(ThoiGianLapHD)
            ORDER BY Thang ASC
            """ :
            """
            SELECT 
                MONTH(ThoiGianLapHD) AS Thang,
                COUNT(DISTINCT hd.MaHD) AS SoHoaDon,
                COALESCE(SUM(ct.SoLuong), 0) AS TongSanPham,
                COALESCE(SUM(hd.TongTien), 0) AS TongDoanhThu
            FROM HOADON hd
            LEFT JOIN CHITIETHOADON ct ON hd.MaHD = ct.MaHD
            WHERE YEAR(ThoiGianLapHD) = ?
            AND hd.TrangThai = 'active'
            GROUP BY MONTH(ThoiGianLapHD)
            ORDER BY Thang ASC
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
                    
                    if (baoGomHuy) {
                        row.put("SoHoaDonHuy", rs.getInt("SoHoaDonHuy"));
                    }
                    
                    result.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê hóa đơn theo năm: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


    public static List<Map<String, Object>> thongKeHDTheoPhuongThucTT(LocalDate fromDate, LocalDate toDate, boolean baoGomHuy) {
        if (fromDate == null || toDate == null) {
            System.err.println("❌ Ngày không được null!");
            return new ArrayList<>();
        }
        
        if (fromDate.isAfter(toDate)) {
            System.err.println("❌ Ngày bắt đầu phải trước hoặc bằng ngày kết thúc!");
            return new ArrayList<>();
        }

        List<Map<String, Object>> result = new ArrayList<>();

        String query = baoGomHuy ?
            """
            SELECT 
                hd.PhuongThucTT,
                COUNT(DISTINCT hd.MaHD) AS SoHoaDon,
                COALESCE(SUM(ct.SoLuong), 0) AS TongSanPham,
                COALESCE(SUM(hd.TongTien), 0) AS TongDoanhThu,
                COUNT(CASE WHEN hd.TrangThai = 'cancelled' THEN 1 END) AS SoHoaDonHuy
            FROM HOADON hd
            LEFT JOIN CHITIETHOADON ct ON hd.MaHD = ct.MaHD
            WHERE hd.ThoiGianLapHD >= ? AND hd.ThoiGianLapHD < ?
            GROUP BY hd.PhuongThucTT
            ORDER BY TongDoanhThu DESC
            """ :
            """
            SELECT 
                hd.PhuongThucTT,
                COUNT(DISTINCT hd.MaHD) AS SoHoaDon,
                COALESCE(SUM(ct.SoLuong), 0) AS TongSanPham,
                COALESCE(SUM(hd.TongTien), 0) AS TongDoanhThu
            FROM HOADON hd
            LEFT JOIN CHITIETHOADON ct ON hd.MaHD = ct.MaHD
            WHERE hd.ThoiGianLapHD >= ? AND hd.ThoiGianLapHD < ?
            AND hd.TrangThai = 'active'
            GROUP BY hd.PhuongThucTT
            ORDER BY TongDoanhThu DESC
            """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toExclusive = toDate.plusDays(1).atStartOfDay();

            stmt.setTimestamp(1, Timestamp.valueOf(fromDateTime));
            stmt.setTimestamp(2, Timestamp.valueOf(toExclusive));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("PTTT", rs.getString("PhuongThucTT"));
                    row.put("SoHoaDon", rs.getInt("SoHoaDon"));
                    row.put("TongSanPham", rs.getInt("TongSanPham"));
                    row.put("TongDoanhThu", rs.getLong("TongDoanhThu"));
                    
                    if (baoGomHuy) {
                        row.put("SoHoaDonHuy", rs.getInt("SoHoaDonHuy"));
                    }
                    
                    result.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê hóa đơn theo phương thức thanh toán: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
