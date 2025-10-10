package dao;

import java.sql.*;
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

        String query = "SELECT MaHD, MaKH, MaNV, TongTien, NgayLapHD, PhuongThucTT FROM HOADON";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HoaDonDTO hd = new HoaDonDTO();
                hd.setMaHD(rs.getString("MaHD"));
                hd.setMaKH(rs.getString("MaKH"));
                hd.setMaNV(rs.getString("MaNV"));
                hd.setTongTien(rs.getInt("TongTien"));
                hd.setNgayLapHD(rs.getTimestamp("NgayLapHD").toLocalDateTime());
                hd.setPhuongThucTT(rs.getString("PhuongThucTT"));
                list.add(hd);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy tất cả hóa đơn: " + e.getMessage());
        }
        return list;
    }

    public static void themHoaDon(HoaDonDTO hd) {
        String query = "INSERT INTO HOADON (MaHD, MaKH, MaNV, TongTien, PhuongThucTT, TienKhachDua, TienThua) VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, hd.getMaHD());
            stmt.setString(2, hd.getMaKH());
            stmt.setString(3, hd.getMaNV());
            stmt.setInt(4, hd.getTongTien());
            stmt.setString(5, hd.getPhuongThucTT());
            stmt.setInt(6, hd.getTienKhachDua());
            stmt.setInt(7, hd.getTienThua());

            int rowAffected = stmt.executeUpdate(); 
            if (rowAffected > 0) {
                System.out.println("Thêm hóa đơn thành công");
            } else {
                System.out.println("Thêm hóa đơn thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm hóa đơn: " + e.getMessage());
        }
    }

    public static void xoaHoaDon(String maHD) {

        String query = "DELETE FROM HOADON WHERE MaHD = ?";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, maHD);
            
            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Xóa hóa đơn thành công");
            } else {
                System.out.println("Xóa hóa đơn thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa hóa đơn: " + e.getMessage());
        }
    }

    public static String generateIDHoaDon() {
        String prefix = "HD";
        String newID = prefix + "001";
        String query = "SELECT MaHD FROM HOADON ORDER BY MaHD DESC LIMIT 1";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();) {

            if (rs.next()) {
                String lastID = rs.getString("MaHD");
                int number = Integer.parseInt(lastID.substring(2));
                number++;
                newID = prefix + String.format("%03d", number);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tạo mã hóa đơn: " + e.getMessage());
        }
        return newID;
    }

    public static HoaDonDTO timHoaDon(String maHD) {
        String query = "SELECT MaKH, MaNV, TienKhachDua, TienThua, TongTien, PhuongThucTT, NgayLapHD FROM HOADON WHERE MaHD = ?";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, maHD);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new HoaDonDTO(
                    maHD,
                    rs.getString("MaKH"),
                    rs.getString("MaNV"),
                    rs.getInt("TienKhachDua"),
                    rs.getInt("TienThua"),
                    rs.getInt("TongTien"),
                    rs.getTimestamp("NgayLapHD").toLocalDateTime(),
                    rs.getString("PhuongThucTT")
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi in hóa đơn: " + e.getMessage());
        }
        return null;
    }

    public static void timHoaDonTheoMaKH(String maKH) {
        String query = 
            "SELECT hd.MaHD, hd.ThoiGianLapHD, nv.Ho, nv.Ten, hd.TongTien, hd.PhuongThucTT " + 
            "FROM HOADON hd " +
            "INNER JOIN NHANVIEN nv ON hd.MaNV = nv.MaNV " +
            "WHERE hd.MaKH = ?";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, maKH);
            ResultSet rs = stmt.executeQuery();

            // Làm lại giao diện cho dễ nhìn
            int count = 0;
            while (rs.next()) {
                System.out.println(
                    "Mã hóa đơn: " + rs.getString("MaHD") +
                    "Ngày lập hóa đơn: " + rs.getTimestamp("ThoiGianLapHD").toLocalDateTime() + 
                    "Nhân viên: " + rs.getString("Ho") + " " + rs.getString("Ten") + 
                    "Tổng tiền: " + FormatUtil.formatVND(rs.getInt("TongTien")) +
                    "Phương thức thanh toán: " + rs.getString("PhuongThucTT")
                );
                count++;
            }
            // ưng thì sửa lại là tổng cộng hay gì cũng được
            System.out.println("Tìm thấy " + count + " hóa đơn có mã khách hàng: " + maKH);
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm hóa đơn theo mã khách hàng: " + e.getMessage());
        }
    }

    public static void timHoaDonTheoMaNV(String maNV) {
        String query = 
            "SELECT hd.MaHD, hd.MaKH, hd.ThoiGianLapHD, hd.TongTien, hd.PhuongThucTT " +
            "FROM HOADON hd " +
            "INNER JOIN NHANVIEN nv ON hd.MaNV = nv.MaNV " +
            "WHERE hd.MaNV = ?";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                // làm lại giao diện 
                System.out.println(
                    "Mã hóa đơn: " + rs.getString("MaHD") +
                    "Mã khách hàng: " + rs.getString("MaKH") +
                    "Ngày lập hóa đơn: " + rs.getTimestamp("ThoiGianLapHD").toLocalDateTime() +
                    "Tổng tiền: " + FormatUtil.formatVND(rs.getInt("TongTien")) +
                    "Phương thức thanh toán: " + rs.getString("PhuongThucTT")
                );
                count++;
            }
            if (count > 0) {
                System.out.println("Tìm thấy " + count + " hóa đơn đã lập bởi nhân viên: " + maNV);
            } else {
                System.out.println("Không tìm thấy hóa đơn đã lập bởi nhân viên: " + maNV);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm hóa đơn theo mã nhân viên: " + e.getMessage());
        }
    }
    
    public static List<HoaDonDTO> timHoaDonTheoNgayLap(LocalDate fromDate, LocalDate toDate) {
        List<HoaDonDTO> list = new ArrayList<>();

        String query = 
            "SELECT MaHD, MaKH, MaNV, TongTien, PhuongThucTT, NgayLapHD " +
            "FROM HOADON " +
            "WHERE NgayLapHD >= ? AND NgayLapHD < ?" +
            "ORDER BY NgayLapHD ASC";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toExclusive = toDate.plusDays(1).atStartOfDay();

            stmt.setTimestamp(1, Timestamp.valueOf(fromDateTime));
            stmt.setTimestamp(2, Timestamp.valueOf(toExclusive));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HoaDonDTO hd = new HoaDonDTO();
                hd.setMaHD(rs.getString("MaHD"));
                hd.setMaKH(rs.getString("MaKH"));
                hd.setMaNV(rs.getString("MaNV"));
                hd.setTongTien(rs.getInt("TongTien"));
                hd.setNgayLapHD(rs.getTimestamp("NgayLapHD").toLocalDateTime());
                hd.setPhuongThucTT(rs.getString("PhuongThucTT"));
                list.add(hd);
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm hóa đơn theo ngày lập: " + e.getMessage());
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

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    result.put("SoHoaDon", rs.getInt("SoHoaDon"));
                    result.put("SoKhachHang", rs.getInt("SoKhachHang"));
                    result.put("TongSanPham", rs.getInt("TongSanPham"));
                    result.put("TongDoanhThu", rs.getLong("TongDoanhThu"));
                    result.put("DoanhThuTrungBinh", rs.getDouble("DoanhThuTrungBinh"));
                }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thống kê hóa đơn theo thời gian: " + e.getMessage());
        }
        return result;
    }
}
