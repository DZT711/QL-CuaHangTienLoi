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

import dto.NhapHangDTO;
import util.JDBCUtil;

public class NhapHangDAO {
    public static String generateMaPhieuNhap() {
        String prefix = "PN";
        String newID = prefix + "001";
        String query = "SELECT MaPhieu FROM PHIEUNHAP ORDER BY MaPhieu DESC LIMIT 1";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();) {

            if (rs.next()) {
                String lastID = rs.getString("MaPhieu");
                int number = Integer.parseInt(lastID.substring(2));
                number++;
                newID = prefix + String.format("%03d", number);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tạo mã phiếu nhập: " + e.getMessage());
        }
        return newID;
    }

    public static boolean themPhieuNhap(NhapHangDTO pn) {
        String query = "INSERT INTO PHIEUNHAP (MaPhieu, MaNCC, MaNV, TongTien) VALUES (?, ?, ?, ?);";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pn.getMaPhieu());
            stmt.setString(2, pn.getMaNCC());
            stmt.setString(3, pn.getMaNV());
            stmt.setInt(4, pn.getTongTien());

            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Thêm phiếu nhập thành công");
                return true;
            } else {
                System.out.println("Thêm phiếu nhập thất bại");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm phiếu nhập: " + e.getMessage());
            return false;
        }
    }

    public static NhapHangDTO timPhieuNhapTheoMa(String maPhieu) {
        String query = "SELECT MaPhieu, MaNCC, MaNV, TongTien, NgayLapPhieu FROM PHIEUNHAP WHERE MaPhieu = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maPhieu);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new NhapHangDTO(
                    rs.getString("MaPhieu"), 
                    rs.getString("MaNCC"), 
                    rs.getString("MaNV"), 
                    rs.getInt("TongTien"), 
                    rs.getTimestamp("NgayLapPhieu").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm phiếu nhập theo mã: " + e.getMessage());
        }
        return null;
    }

    public static List<NhapHangDTO> timPhieuNhapTheoMaNCC(String maNCC) {
        List<NhapHangDTO> danhSachPhieuNhap = new ArrayList<>();

        String query = "SELECT MaPhieu, MaNCC, MaNV, TongTien, NgayLapPhieu FROM PHIEUNHAP WHERE MaNCC = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, maNCC);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                danhSachPhieuNhap.add(new NhapHangDTO(
                    rs.getString("MaPhieu"), 
                    rs.getString("MaNCC"), 
                    rs.getString("MaNV"), 
                    rs.getInt("TongTien"), 
                    rs.getTimestamp("NgayLapPhieu").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm phiếu nhập theo mã NCC: " + e.getMessage());
        }
        return danhSachPhieuNhap;
    }

    public static List<NhapHangDTO> timPhieuNhapTheoMaNV(String maNV) {
        List<NhapHangDTO> danhSachPhieuNhap = new ArrayList<>();

        String query = "SELECT MaPhieu, MaNCC, MaNV, TongTien, NgayLapPhieu FROM PHIEUNHAP WHERE MaNV = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                danhSachPhieuNhap.add(new NhapHangDTO(
                    rs.getString("MaPhieu"), 
                    rs.getString("MaNCC"), 
                    rs.getString("MaNV"), 
                    rs.getInt("TongTien"), 
                    rs.getTimestamp("NgayLapPhieu").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm phiếu nhập theo mã NV: " + e.getMessage());
        }
        return danhSachPhieuNhap;
    }

    public static List<NhapHangDTO> timPhieuNhapTheoNgay(LocalDate fromDate, LocalDate toDate) {
        List<NhapHangDTO> danhSachPhieuNhap = new ArrayList<>();

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

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                danhSachPhieuNhap.add(new NhapHangDTO(
                    rs.getString("MaPhieu"), 
                    rs.getString("MaNCC"), 
                    rs.getString("MaNV"), 
                    rs.getInt("TongTien"), 
                    rs.getTimestamp("NgayLapPhieu").toLocalDateTime()
                ));
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm phiếu nhập theo ngày: " + e.getMessage());
        }
        return danhSachPhieuNhap;
    }

    public static void suaPhieuNhap(NhapHangDTO pn, String maPhieu) {
        String query = "UPDATE PHIEUNHAP SET MaNCC = ?, MaNV = ? WHERE MaPhieu = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pn.getMaNCC());
            stmt.setString(2, pn.getMaNV());
            stmt.setString(3, maPhieu);

            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Sửa phiếu nhập thành công");
            } else {
                System.out.println("Sửa phiếu nhập thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi sửa phiếu nhập: " + e.getMessage());
        }
    }

    public static Map<String, Object> thongKePhieuNhapTheoNgay(LocalDate fromDate, LocalDate toDate) {
        String query = """  
            SELECT 
                COUNT(DISTINCT pn.MaPhieu) AS tongPhieuNhap,
                SUM(pn.TongTien) AS tongGiaTRi,
                COUNT(DISTINCT pn.MaNCC) AS soNCC,
                SUM(ct.SoLuong) AS tongSanPham,
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
                long giaTriTB = (rs.getInt("tongPhieuNhap") > 0) ? (rs.getLong("tongGiaTri") / rs.getInt("tongPhieuNhap")) : 0;
                result.put("tongPhieuNhap", rs.getInt("tongPhieuNhap"));
                result.put("tongGiaTri", rs.getLong("tongGiaTri"));
                result.put("tongSanPham", rs.getInt("tongSanPham"));
                result.put("soNCC", rs.getInt("soNCC"));
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
                pn.NgayLapPhieu,
                COUNT(pn.MaPhieu) AS soPhieu,
                SUM(pn.TongTien) AS tongTien,
            FROM PHIEUNHAP pn
            WHERE pn.NgayLapPhieu >= ? AND pn.NgayLapPhieu < ?
            GROUp BY pn.NgayLapPhieu
            ORDER BY pn.NgayLapPhieu ASC;
        """;

        List<Map<String,Object>> result = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();

            stmt.setTimestamp(1, Timestamp.valueOf(fromDateTime));
            stmt.setTimestamp(2, Timestamp.valueOf(toDateTime));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("NgayLapPhieu", rs.getTimestamp("NgayLapPhieu").toLocalDateTime().toLocalDate());
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
        String query = """
            SELECT 
                ncc.MaNCC, ncc.TenNCC,
                COUNT(DISTINCT pn.MaPhieu) AS soPhieu,
                SUM(ct.SoLuong) AS tongSanPham,
                SUM(pn.TongTien) AS tongGiaTri
            FROM PHIEUNHAP pn
            JOIN NHACUNGCAP ncc ON pn.MaNCC = ncc.MaNCC
            JOIN CHITIETPHIEUNHAP ct ON pn.MaPhieu = ct.MaPhieu
            WHERE pn.NgayLapPhieu >= ? AND pn.NgayLapPhieu < ?
            GROUP BY ncc.MaNCC, ncc.TenNCC
            ORDER BY tongGiaTri DESC;
        """;

        List<Map<String,Object>> result = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();

            stmt.setTimestamp(1, Timestamp.valueOf(fromDateTime));
            stmt.setTimestamp(2, Timestamp.valueOf(toDateTime));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("MaNCC", rs.getString("MaNCC"));
                row.put("TenNCC", rs.getString("TenNCC"));
                row.put("SoPhieu", rs.getInt("soPhieu"));
                row.put("TongSanPham", rs.getInt("tongSanPham"));
                row.put("TongGiaTri", rs.getLong("tongGiaTri"));
                result.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thống kê phiếu nhập theo nhà cung cấp: " + e.getMessage());
        }
        return result;
    }

    public static List<Map<String, Object>> thongKePhieuNhapTheoNV(LocalDate fromDate, LocalDate toDate) {
        String query = """
                SELECT nv.MaNV, nv.Ho, nv.Ten,
                COUNT (DISTINCT pn.MaPhieu) AS soPhieu,
                SUM (ctpn.SoLuong) AS tongSoLuong, 
                SUM (ctpn.ThanhTien) AS tongGiaTri
                FROM PHIEUNHAP pn
                JOIN NHANVIEN nv ON pn.MaNV = nv.MaNV
                JOIN CHITIETPHIEUNHAP ctpn ON pn.MaPhieu = ctpn.MaPhieu
                WHERE pn.NgayLapPhieu >= ? AND pn.NgayLapPhieu < ?
                GROUP BY nv.MaNV, nv.Ho, nv.Ten
                ORDER BY tongGiaTri DESC;
        """;

        List<Map<String,Object>> result = new ArrayList<>();
        
        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();

            stmt.setTimestamp(1, Timestamp.valueOf(fromDateTime));
            stmt.setTimestamp(2, Timestamp.valueOf(toDateTime));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("MaNV", rs.getString("MaNV"));
                row.put("Ho Ten", rs.getString("Ho") + " " + rs.getString("Ten"));
                row.put("SoPhieu", rs.getInt("soPhieu"));
                row.put("TongSanPham", rs.getInt("tongSoLuong"));
                row.put("TongGiaTri", rs.getLong("tongGiaTri"));
                result.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thống kê phiếu nhập theo nhân viên: " + e.getMessage());
        }
        return result;
    }

    public static List<Map<String, Object>> thongKePhieuNhapTheoSanPham(LocalDate fromDate, LocalDate toDate) {
        String query = """
            SELECT sp.MaSP, sp.TenSP,
            COUNT (DISTINCT ctpn.MaPhieu) AS soPhieu,
            SUM (ctpn.SoLuong) AS tongSoLuong,
            SUM (ctpn.ThanhTien) AS tongGiaTri
            FROM CHITIETPHIEUNHAP ctpn
            JOIN SANPHAM sp ON ctpn.MaSP = sp.MaSP
            WHERE pn.NgayLapPhieu >= ? AND pn.NgayLapPhieu < ?
            GROUP BY sp.MaSP, sp.TenSP
            ORDER BY tongGiaTri DESC;
        """;

        List<Map<String,Object>> result = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();

            stmt.setTimestamp(1, Timestamp.valueOf(fromDateTime));
            stmt.setTimestamp(2, Timestamp.valueOf(toDateTime));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("MaSP", rs.getString("MaSP"));
                row.put("TenSP", rs.getString("TenSP"));
                row.put("SoPhieu", rs.getInt("soPhieu"));
                row.put("TongSanPham", rs.getInt("tongSoLuong"));
                row.put("TongGiaTri", rs.getLong("tongGiaTri"));
                result.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thống kê phiếu nhập theo sản phẩm: " + e.getMessage());
        }
        return result;
    }

    public static List<Map<String, Object>> thongKePhieuNhapTheoNam (int year) {
        String query = """
            SELECT 
                MONTH(pn.NgayLapPhieu) AS Thang,
                COUNT(DISTINCT pn.MaPhieu) AS SoPhieu,
                SUM (ctpn.SoLuong) AS TongSanPham,
                SUM (pn.TongTien) AS TongGiaTri
            FROM PHIEUNHAP pn
            JOIN (
                SELECT MaPhieu, SUM(SoLuong) AS TongSoLuong
                FROM CHITIETPHIEUNHAP
                GROUP BY MaPhieu
            ) ctpn ON pn.MaPhieu = ctpn.MaPhieu
            WHERE YEAR(pn.NgayLapPhieu) = ?
            GROUP BY MONTH(pn.NgayLapPhieu)
            ORDER BY Thang ASC;
        """;

        List<Map<String,Object>> result = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, year);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("Thang", rs.getInt("Thang"));
                row.put("SoPhieu", rs.getInt("SoPhieu"));
                row.put("TongSanPham", rs.getInt("TongSanPham"));
                row.put("TongGiaTri", rs.getLong("TongGiaTri"));
                result.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thống kê phiếu nhập theo năm: " + e.getMessage());
        }
        return result;
    }
}
