package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.Timestamp;

import dto.ChiTietPhieuNhapDTO;
import dto.NhapHangDTO;
import util.JDBCUtil;

public class NhapHangDAO {
    public static String generateMaPhieu() {
        String prefix = "PN";
        String newID = prefix + "001";
        String query = "SELECT MaPhieu FROM PHIEUNHAP " +
                    "ORDER BY CAST(SUBSTRING(MaPhieu, 3) AS UNSIGNED) DESC " +
                    "LIMIT 1";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String lastID = rs.getString("MaPhieu");
                int number = Integer.parseInt(lastID.substring(2));
                newID = prefix + String.format("%03d", number + 1); 
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tạo mã phiếu nhập: " + e.getMessage());
            e.printStackTrace();  
        }

        return newID;
    }

    public static boolean themPhieuNhap(NhapHangDTO pn) {
        String query = "INSERT INTO PHIEUNHAP (MaPhieu, MaNCC, MaNV, TongTien, NgayLapPhieu) " +
                    "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pn.getMaPhieu());
            stmt.setString(2, pn.getMaNCC());
            stmt.setString(3, pn.getMaNV());
            stmt.setInt(4, pn.getTongTien());
            stmt.setTimestamp(5, Timestamp.valueOf(pn.getNgayLapPhieu()));
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tạo phiếu nhập: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static NhapHangDTO timPhieuNhapTheoMa(String maPhieu) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            System.err.println("❌ Mã phiếu không được rỗng!");
            return null;
        }

        String query = """
            SELECT MaPhieu, MaNCC, MaNV, TongTien, NgayLapPhieu
            FROM PHIEUNHAP
            WHERE MaPhieu = ?        
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maPhieu);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new NhapHangDTO(
                        rs.getString("MaPhieu"),
                        rs.getString("MaNCC"),
                        rs.getString("MaNV"),
                        rs.getInt("TongTien"),
                        rs.getTimestamp("NgayLapPhieu").toLocalDateTime()
                    );
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm phiếu nhập: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static List<NhapHangDTO> timPhieuNhapTheoMaNCC(String maNCC) {
        List<NhapHangDTO> danhSachPhieuNhap = new ArrayList<>();

        if (maNCC == null || maNCC.trim().isEmpty()) {
            System.err.println("❌ Mã NCC không được rỗng!");
            return danhSachPhieuNhap;
        }

        String query = """
            SELECT MaPhieu, MaNCC, MaNV, TongTien, NgayLapPhieu 
            FROM PHIEUNHAP 
            WHERE MaNCC = ?
            ORDER BY NgayLapPhieu DESC
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, maNCC);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    danhSachPhieuNhap.add(new NhapHangDTO(
                        rs.getString("MaPhieu"), 
                        rs.getString("MaNCC"), 
                        rs.getString("MaNV"), 
                        rs.getInt("TongTien"), 
                        rs.getTimestamp("NgayLapPhieu").toLocalDateTime()
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm phiếu nhập theo mã NCC: " + e.getMessage());
            e.printStackTrace();
        }
        return danhSachPhieuNhap;
    }

    public static List<NhapHangDTO> timPhieuNhapTheoMaNV(String maNV) {
        List<NhapHangDTO> danhSachPhieuNhap = new ArrayList<>();

        if (maNV == null || maNV.trim().isEmpty()) {
            System.err.println("❌ Mã nhân viên không được rỗng!");
            return danhSachPhieuNhap;
        }

        String query = """
            SELECT MaPhieu, MaNCC, MaNV, TongTien, NgayLapPhieu
            FROM PHIEUNHAP
            WHERE MaNV = ?
            ORDER BY NgayLapPhieu DESC
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, maNV);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    danhSachPhieuNhap.add(new NhapHangDTO(
                        rs.getString("MaPhieu"), 
                        rs.getString("MaNCC"), 
                        rs.getString("MaNV"), 
                        rs.getInt("TongTien"), 
                        rs.getTimestamp("NgayLapPhieu").toLocalDateTime()
                    ));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm phiếu nhập theo mã NV: " + e.getMessage());
            e.printStackTrace();
        }
        return danhSachPhieuNhap;
    }

    public static List<NhapHangDTO> timPhieuNhapTheoNgay(LocalDate fromDate, LocalDate toDate) {
        List<NhapHangDTO> danhSachPhieuNhap = new ArrayList<>();

        if (fromDate == null || toDate == null) {
            System.err.println("❌ Ngày không được null!");
            return danhSachPhieuNhap;
        }
        
        if (fromDate.isAfter(toDate)) {
            System.err.println("❌ Ngày bắt đầu phải trước hoặc bằng ngày kết thúc!");
            return danhSachPhieuNhap;
        }

        String query = """
            SELECT MaPhieu, MaNCC, MaNV, TongTien, NgayLapPhieu
            FROM PHIEUNHAP
            WHERE NgayLapPhieu >= ? AND NgayLapPhieu < ?
            ORDER BY NgayLapPhieu ASC
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();

            stmt.setTimestamp(1, Timestamp.valueOf(fromDateTime));
            stmt.setTimestamp(2, Timestamp.valueOf(toDateTime));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    danhSachPhieuNhap.add(new NhapHangDTO(
                        rs.getString("MaPhieu"), 
                        rs.getString("MaNCC"), 
                        rs.getString("MaNV"), 
                        rs.getInt("TongTien"), 
                        rs.getTimestamp("NgayLapPhieu").toLocalDateTime()
                    ));
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm phiếu nhập theo ngày: " + e.getMessage());
            e.printStackTrace();
        }
        return danhSachPhieuNhap;
    }

    public static boolean suaPhieuNhap(String maPhieu, String newMaNCC, String newMaNV) {

        if (maPhieu == null || maPhieu.trim().isEmpty() || 
            newMaNCC == null || newMaNCC.trim().isEmpty() ||
            newMaNV == null || newMaNV.trim().isEmpty()) {
            System.err.println("❌ Dữ liệu không hợp lệ khi cập nhật phiếu nhập!");
            return false;
        }

        List<ChiTietPhieuNhapDTO> chiTietList = ChiTietPhieuNhapDAO.timChiTietPhieuNhap(maPhieu);
        if (chiTietList != null && !chiTietList.isEmpty()) {
            System.err.println("❌ Không thể sửa phiếu nhập vì đã có chi tiết hàng hóa!");
            return false;
        }

        String query = "UPDATE PHIEUNHAP SET MaNCC = ?, MaNV = ? WHERE MaPhieu = ?";
        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newMaNCC);
            stmt.setString(2, newMaNV);
            stmt.setString(3, maPhieu);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi sửa phiếu nhập: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean capNhatTongTien(Connection conn, String maPhieu, int tongTien) throws SQLException {
        if (maPhieu == null || maPhieu.trim().isEmpty()) 
            throw new IllegalArgumentException("Mã phiếu không được rỗng!");
        if (tongTien < 0) 
            throw new IllegalArgumentException("Tổng tiền không được âm!");

        String query = "UPDATE PHIEUNHAP SET TongTien = ? WHERE MaPhieu = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, tongTien);
            stmt.setString(2, maPhieu);
            return stmt.executeUpdate() > 0;
        }
    }

    public static boolean xoaPhieuNhapTheoMa(String maPhieu) {
        String sql = "DELETE FROM PHIEUNHAP WHERE MaPhieu = ?";
        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPhieu);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa phiếu nhập: " + e.getMessage());
            return false;
        }
    }

    public static Map<String, Object> thongKePhieuNhapTheoNgay(LocalDate fromDate, LocalDate toDate) {
        String query = """
            SELECT 
                COUNT(DISTINCT pn.MaPhieu) AS tongPhieuNhap,
                SUM(pn.TongTien) AS tongGiaTri,
                COUNT(DISTINCT pn.MaNCC) AS soNCC,
                SUM(ct.SoLuong) AS tongSanPham
            FROM PHIEUNHAP pn
            JOIN CHITIETPHIEUNHAP ct ON pn.MaPhieu = ct.MaPhieu
            WHERE pn.NgayLapPhieu >= ? AND pn.NgayLapPhieu < ?
        """;

        Map<String, Object> result = new HashMap<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();

            stmt.setTimestamp(1, Timestamp.valueOf(fromDateTime));
            stmt.setTimestamp(2, Timestamp.valueOf(toDateTime));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int tongPhieuNhap = rs.getInt("tongPhieuNhap");
                long tongGiaTri = rs.getLong("tongGiaTri");
                int tongSanPham = rs.getInt("tongSanPham");
                int soNCC = rs.getInt("soNCC");
                long giaTriTB = (tongPhieuNhap > 0) ? (tongGiaTri / tongPhieuNhap) : 0;

                result.put("tongPhieuNhap", tongPhieuNhap);
                result.put("tongGiaTri", tongGiaTri);
                result.put("tongSanPham", tongSanPham);
                result.put("soNCC", soNCC);
                result.put("giaTriTB", giaTriTB);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thống kê phiếu nhập theo ngày: " + e.getMessage());
        }
        return result;
    }

    public static List<Map<String, Object>> thongKeChiTietTheoNgay(LocalDate fromDate, LocalDate toDate) {
        String query = """
            SELECT 
                DATE(pn.NgayLapPhieu) AS Ngay,
                COUNT(pn.MaPhieu) AS soPhieu,
                SUM(pn.TongTien) AS tongTien
            FROM PHIEUNHAP pn
            WHERE pn.NgayLapPhieu >= ? AND pn.NgayLapPhieu < ?
            GROUP BY Ngay
            ORDER BY Ngay ASC
        """;

        List<Map<String, Object>> result = new ArrayList<>();
        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();

            stmt.setTimestamp(1, Timestamp.valueOf(fromDateTime));
            stmt.setTimestamp(2, Timestamp.valueOf(toDateTime));

            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("Ngay", rs.getDate("Ngay").toLocalDate());
                row.put("SoPhieu", rs.getInt("soPhieu"));
                row.put("TongTien", rs.getLong("tongTien"));
                result.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thống kê chi tiết phiếu nhập theo ngày: " + e.getMessage());
        }
        return result;
    }

    public static List<Map<String,Object>> thongKePhieuNhapTheoNCC(LocalDate fromDate, LocalDate toDate) {
        List<Map<String,Object>> result = new ArrayList<>();

        if (fromDate == null || toDate == null) {
            System.err.println("❌ Tham số ngày không được null!");
            return result;
        }
        if (fromDate.isAfter(toDate)) {
            System.err.println("❌ Ngày bắt đầu phải trước ngày kết thúc!");
            return result;
        }

        String query = """
            SELECT 
                ncc.MaNCC, ncc.TenNCC,
                COUNT(DISTINCT pn.MaPhieu) AS soPhieu,
                SUM(ct.SoLuong) AS tongSanPham,
                SUM(ct.ThanhTien) AS tongGiaTri
            FROM PHIEUNHAP pn
            JOIN NHACUNGCAP ncc ON pn.MaNCC = ncc.MaNCC
            JOIN CHITIETPHIEUNHAP ct ON pn.MaPhieu = ct.MaPhieu
            WHERE pn.NgayLapPhieu >= ? AND pn.NgayLapPhieu < ?
            GROUP BY ncc.MaNCC, ncc.TenNCC
            ORDER BY tongGiaTri DESC;
        """;


        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();

            stmt.setTimestamp(1, Timestamp.valueOf(fromDateTime));
            stmt.setTimestamp(2, Timestamp.valueOf(toDateTime));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    try {
                        Map<String, Object> row = new HashMap<>();
                        row.put("MaNCC", rs.getString("MaNCC"));
                        row.put("TenNCC", rs.getString("TenNCC"));
                        row.put("SoPhieu", rs.getInt("soPhieu"));
                        row.put("TongSanPham", rs.getInt("tongSanPham"));
                        row.put("TongGiaTri", rs.getLong("tongGiaTri"));
                        result.add(row);
                    } catch (SQLException sqlrow) {
                        System.err.println("❌ Lỗi lấy dòng ResultSet: " + sqlrow.getMessage());
                        continue;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê phiếu nhập theo nhà cung cấp: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static List<Map<String, Object>> thongKePhieuNhapTheoNV(LocalDate fromDate, LocalDate toDate) {
        List<Map<String,Object>> result = new ArrayList<>();

        if (fromDate == null || toDate == null) {
            System.err.println("❌ Tham số ngày không được null!");
            return result;
        }
        if (fromDate.isAfter(toDate)) {
            System.err.println("❌ Ngày bắt đầu phải trước ngày kết thúc!");
            return result;
        }

        String query = """
                SELECT nv.MaNV, nv.Ho, nv.Ten,
                COUNT(DISTINCT pn.MaPhieu) AS soPhieu,
                SUM(ctpn.SoLuong) AS tongSoLuong, 
                SUM(ctpn.ThanhTien) AS tongGiaTri
                FROM PHIEUNHAP pn
                JOIN NHANVIEN nv ON pn.MaNV = nv.MaNV
                JOIN CHITIETPHIEUNHAP ctpn ON pn.MaPhieu = ctpn.MaPhieu
                WHERE pn.NgayLapPhieu >= ? AND pn.NgayLapPhieu < ?
                GROUP BY nv.MaNV, nv.Ho, nv.Ten
                ORDER BY tongGiaTri DESC;
        """;

        
        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();

            stmt.setTimestamp(1, Timestamp.valueOf(fromDateTime));
            stmt.setTimestamp(2, Timestamp.valueOf(toDateTime));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    try {
                        Map<String, Object> row = new HashMap<>();
                        row.put("MaNV", rs.getString("MaNV"));
                        row.put("HoTen", rs.getString("Ho") + " " + rs.getString("Ten"));
                        row.put("SoPhieu", rs.getInt("soPhieu"));
                        row.put("TongSanPham", rs.getInt("tongSoLuong"));
                        row.put("TongGiaTri", rs.getLong("tongGiaTri"));
                        result.add(row);
                    } catch (SQLException rowEx) {
                        System.err.println("❌ Lỗi lấy dữ liệu dòng ResultSet: " + rowEx.getMessage());
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê phiếu nhập theo nhân viên: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static List<Map<String, Object>> thongKePhieuNhapTheoSanPham(LocalDate fromDate, LocalDate toDate) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (fromDate == null || toDate == null) {
            System.err.println("❌ Tham số ngày không được null!");
            return result;
        }
        if (fromDate.isAfter(toDate)) {
            System.err.println("❌ Ngày bắt đầu phải trước hoặc bằng ngày kết thúc!");
            return result;
        }

        String query = """
            SELECT sp.MaSP, sp.TenSP,
                COUNT(DISTINCT ctpn.MaPhieu) AS soPhieu,
                SUM(ctpn.SoLuong) AS tongSoLuong,
                SUM(ctpn.ThanhTien) AS tongGiaTri
            FROM CHITIETPHIEUNHAP ctpn
            JOIN HANGHOA hh ON ctpn.MaHang = hh.MaHang
            JOIN SANPHAM sp ON hh.MaSP = sp.MaSP
            JOIN PHIEUNHAP pn ON ctpn.MaPhieu = pn.MaPhieu
            WHERE pn.NgayLapPhieu >= ? AND pn.NgayLapPhieu < ?
            GROUP BY sp.MaSP, sp.TenSP
            ORDER BY tongGiaTri DESC
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            LocalDateTime fromDT = fromDate.atStartOfDay();
            LocalDateTime toDT = toDate.plusDays(1).atStartOfDay();
            stmt.setTimestamp(1, Timestamp.valueOf(fromDT));
            stmt.setTimestamp(2, Timestamp.valueOf(toDT));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    try {
                        Map<String, Object> row = new HashMap<>();
                        row.put("MaSP", rs.getString("MaSP"));
                        row.put("TenSP", rs.getString("TenSP"));
                        row.put("SoPhieu", rs.getInt("soPhieu"));
                        row.put("TongSanPham", rs.getInt("tongSoLuong"));
                        row.put("TongGiaTri", rs.getLong("tongGiaTri"));
                        result.add(row);
                    } catch (SQLException rowEx) {
                        System.err.println("❌ Lỗi lấy dòng ResultSet: " + rowEx.getMessage());
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê phiếu nhập theo sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static List<Map<String, Object>> thongKePhieuNhapTheoNam(int year) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (year < 2000 || year > LocalDate.now().getYear()) {
            System.err.println("❌ Năm không hợp lệ cho thống kê!");
            return result;
        }

        String query = """
            SELECT 
                MONTH(pn.NgayLapPhieu) AS Thang,
                COUNT(DISTINCT pn.MaPhieu) AS SoPhieu,
                SUM(ct.SoLuong) AS TongSanPham,
                SUM(ct.ThanhTien) AS TongGiaTri
            FROM PHIEUNHAP pn
            JOIN CHITIETPHIEUNHAP ct ON pn.MaPhieu = ct.MaPhieu
            WHERE YEAR(pn.NgayLapPhieu) = ?
            GROUP BY MONTH(pn.NgayLapPhieu)
            ORDER BY Thang ASC
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, year);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    try {
                        Map<String, Object> row = new HashMap<>();
                        row.put("Thang", rs.getInt("Thang"));
                        row.put("SoPhieu", rs.getInt("SoPhieu"));
                        row.put("TongSanPham", rs.getLong("TongSanPham"));
                        row.put("TongGiaTri", rs.getLong("TongGiaTri"));
                        result.add(row);
                    } catch (SQLException ex) {
                        System.err.println("❌ Lỗi lấy dữ liệu dòng ResultSet: " + ex.getMessage());
                        continue;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê phiếu nhập theo năm: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

}
