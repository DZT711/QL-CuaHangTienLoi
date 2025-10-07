package dao;

import java.sql.*;
import dto.HoaDonDTO;
import util.FormatUtil;
import util.JDBCUtil;

public class HoaDonDAO {
    /*public static List<HoaDonDTO> getAllHoaDon() {
        String query = "SELECT MaHD, MaKH, MaNV, TongTien, NgayLapHD FROM HOADON";
        List<HoaDonDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                list.add(new HoaDonDTO(
                    rs.getString("MaHD"), 
                    rs.getString("MaKH"), 
                    rs.getString("MaNV"), 
                    rs.getInt("TongTien"), 
                    rs.getTimestamp("NgayLapHD").toLocalDateTime())
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy tất cả hóa đơn: " + e.getMessage());
        }
        return list;
    }*/

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
            while (rs.next()) {
                System.out.println(
                    "Mã hóa đơn: " + rs.getString("MaHD") +
                    "Ngày lập hóa đơn: " + rs.getTimestamp("ThoiGianLapHD").toLocalDateTime() + 
                    "Nhân viên: " + rs.getString("Ho") + " " + rs.getString("Ten") + 
                    "Tổng tiền: " + FormatUtil.formatVND(rs.getInt("TongTien")) +
                    "Phương thức thanh toán: " + rs.getString("PhuongThucTT")
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm hóa đơn theo mã khách hàng: " + e.getMessage());
        }
            
        
    }
}
