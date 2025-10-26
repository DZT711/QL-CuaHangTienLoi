package dao;

import dto.ChiTietPhieuNhapDTO;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import util.JDBCUtil;


public class ChiTietPhieuNhapDAO {

    public static boolean themChiTietPhieuNhap(Connection conn, ChiTietPhieuNhapDTO ctPhieuNhap) throws SQLException {
        if (ctPhieuNhap == null) throw new IllegalArgumentException("Chi tiết phiếu nhập không được null");
        if (ctPhieuNhap.getSoLuong() <= 0) throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        if (ctPhieuNhap.getGiaNhap() < 0) throw new IllegalArgumentException("Giá nhập không được âm");


        if (kiemTraTrungChiTiet(conn, ctPhieuNhap.getMaPhieu(), ctPhieuNhap.getMaHang())) {
            System.out.println("❌ Hàng hóa " + ctPhieuNhap.getMaHang() + " đã tồn tại trong phiếu nhập!");
            return false;
        }

        String query = "INSERT INTO CHITIETPHIEUNHAP (MaPhieu, MaHang, SoLuong, GiaNhap, ThanhTien) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ctPhieuNhap.getMaPhieu());
            stmt.setString(2, ctPhieuNhap.getMaHang());
            stmt.setInt(3, ctPhieuNhap.getSoLuong());
            stmt.setInt(4, ctPhieuNhap.getGiaNhap());
            stmt.setInt(5, ctPhieuNhap.getThanhTien());
            return stmt.executeUpdate() > 0;
        }
    }

    private static boolean kiemTraTrungChiTiet(Connection conn, String maPhieu, String maHang) throws SQLException {
        String query = "SELECT 1 FROM CHITIETPHIEUNHAP WHERE MaPhieu = ? AND MaHang = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maPhieu);
            stmt.setString(2, maHang);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static List<ChiTietPhieuNhapDTO> timChiTietPhieuNhap(String maPhieu) {

        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            System.err.println("❌ Mã phiếu không được rỗng!");
            return new ArrayList<>();
        }

        String query = """
            SELECT ctpn.MaPhieu, ctpn.MaHang, sp.TenSP, dv.TenDonVi AS DonViTinh, 
                ctpn.SoLuong, ctpn.GiaNhap, ctpn.ThanhTien
            FROM CHITIETPHIEUNHAP ctpn
            INNER JOIN HANGHOA hh ON ctpn.MaHang = hh.MaHang
            INNER JOIN SANPHAM sp ON hh.MaSP = sp.MaSP
            INNER JOIN DONVI dv ON sp.DonViTinh = dv.MaDonVi
            WHERE ctpn.MaPhieu = ?
            ORDER BY ctpn.MaHang ASC
        """;

        List<ChiTietPhieuNhapDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maPhieu);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new ChiTietPhieuNhapDTO(
                        rs.getString("MaPhieu"),
                        rs.getString("MaHang"),
                        rs.getString("TenSP"),
                        rs.getString("DonViTinh"),
                        rs.getInt("SoLuong"),
                        rs.getInt("GiaNhap"),
                        rs.getInt("ThanhTien")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm chi tiết phiếu nhập: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public static List<ChiTietPhieuNhapDTO> getAllChiTietPhieuNhap() {
        List<ChiTietPhieuNhapDTO> list  = new ArrayList<>();

        String query = """
                SELECT ctpn.MaPhieu, ctpn.MaHang, sp.TenSP, dv.TenDonVi AS DonViTinh, 
                    ctpn.SoLuong, ctpn.GiaNhap, ctpn.ThanhTien
                FROM CHITIETPHIEUNHAP ctpn
                INNER JOIN HANGHOA hh ON ctpn.MaHang = hh.MaHang
                INNER JOIN SANPHAM sp ON hh.MaSP = sp.MaSP
                INNER JOIN DONVI dv ON sp.MaDonVi = dv.MaDonVi
                ORDER BY ctpn.MaPhieu ASC, ctpn.MaHang ASC;
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new ChiTietPhieuNhapDTO(
                    rs.getString("MaPhieu"),
                    rs.getString("MaHang"),
                    rs.getString("TenSP"),
                    rs.getString("DonViTinh"),
                    rs.getInt("SoLuong"),
                    rs.getInt("GiaNhap"),
                    rs.getInt("ThanhTien")
                ));
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi lấy tất cả chi tiết phiếu nhập: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public static List<Map<String, Object>> thongKeSanPhamNhapNhieuNhat(LocalDate fromDate, LocalDate toDate, int limit) {
        String query = """
                SELECT 
                    sp.MaSP,
                    sp.TenSP,
                    SUM(ctpn.SoLuong) AS TongSoLuongNhap,
                    COUNT(DISTINCT ctpn.MaPhieu) AS SoLanNhap,
                    SUM(ctpn.ThanhTien) AS TongGiaTriNhap
                FROM CHITIETPHIEUNHAP ctpn
                INNER JOIN HANGHOA hh ON ctpn.MaHang = hh.MaHang
                INNER JOIN SANPHAM sp ON hh.MaSP = sp.MaSP
                INNER JOIN PHIEUNHAP pn ON ctpn.MaPhieu = pn.MaPhieu
                WHERE pn.NgayLapPhieu >= ? AND pn.NgayLapPhieu < ?
                GROUP BY sp.MaSP, sp.TenSP
                ORDER BY TongSoLuongNhap DESC
                LIMIT ?;
        """;

        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();

            stmt.setTimestamp(1, Timestamp.valueOf(fromDateTime));
            stmt.setTimestamp(2, Timestamp.valueOf(toDateTime));
            stmt.setInt(3, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("MaSP", rs.getString("MaSP"));
                    row.put("TenSP", rs.getString("TenSP"));
                    row.put("TongSoLuongNhap", rs.getInt("TongSoLuongNhap"));
                    row.put("SoLanNhap", rs.getInt("SoLanNhap"));
                    row.put("TongGiaTriNhap", rs.getLong("TongGiaTriNhap"));
                    result.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thống kê sản phẩm nhập nhiều nhất theo ngày: " + e.getMessage());
        }
        return result;
    }
}
