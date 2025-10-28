package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.ResultSet;
import dto.HangHoaDTO;
import util.JDBCUtil;

public class HangHoaDAO {
    private static String generateMaHang(Connection conn) throws SQLException {
        String prefix = "MH"; 
        String newID = prefix + "001";
        String query = "SELECT MaHang FROM HANGHOA ORDER BY MaHang DESC LIMIT 1";
        
        try (PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                String lastID = rs.getString("MaHang");
                int number = Integer.parseInt(lastID.substring(2));
                newID = prefix + String.format("%03d", number + 1);
            }
        }
        
        return newID;
    }

    public static String taoHangHoa(Connection conn, String maSP, int soLuong, LocalDate ngaySanXuat, LocalDate hanSuDung) throws SQLException {
        String maHang = generateMaHang(conn);
        String query = "INSERT INTO HANGHOA (MaHang, MaSP, SoLuongConLai, NgaySanXuat, HanSuDung, TrangThai) "
                    + "VALUES (?, ?, ?, ?, ?, 'active')";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maHang);
            stmt.setString(2, maSP);
            stmt.setInt(3, soLuong);
            stmt.setDate(4, Date.valueOf(ngaySanXuat));
            stmt.setDate(5, Date.valueOf(hanSuDung));
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return maHang;
            }
        }
        return null;
    }

    public static HangHoaDTO timHangHoaTheoMa(String maHang) {
        if (maHang == null || maHang.trim().isEmpty()) {
            System.err.println("❌ Mã hàng không được rỗng!");
            return null;
        }

        String query = "SELECT * FROM HANGHOA WHERE MaHang = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maHang);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Date ngaySX = rs.getDate("NgaySanXuat");
                    Date hanSD = rs.getDate("HanSuDung");
                    
                    return new HangHoaDTO(
                        rs.getString("MaHang"),
                        rs.getString("MaSP"),
                        rs.getInt("SoLuongConLai"),
                        ngaySX != null ? ngaySX.toLocalDate() : null,
                        hanSD != null ? hanSD.toLocalDate() : null,
                        rs.getString("TrangThai")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm hàng hóa: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    public static void truSoLuongConLai(String maHang, int soLuong) {
        String query = "UPDATE HANGHOA SET SoLuongConLai = SoLuongConLai - ? WHERE MaHang = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, soLuong);
            stmt.setString(2, maHang);

            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Cập nhật số lượng hàng hóa thành công");
            } else {
                System.out.println("Cập nhật số lượng hàng hóa thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật số lượng hàng hóa: " + e.getMessage());
        }
    }

    // Xem danh sách hàng hóa nhóm theo sản phẩm
    public static List<Map<String, Object>> xemDanhSachHangHoaTheoSanPham() {
        List<Map<String, Object>> result = new ArrayList<>();
        String query = """
            SELECT sp.MaSP, sp.TenSP, sp.GiaBan, 
                COUNT(hh.MaHang) AS SoLo, 
                SUM(hh.SoLuongConLai) AS TongSoLuong, 
                MIN(hh.HanSuDung) AS HanSuDungGanNhat
            FROM SANPHAM sp
            LEFT JOIN HANGHOA hh ON sp.MaSP = hh.MaSP AND hh.TrangThai = 'active'
            GROUP BY sp.MaSP, sp.TenSP, sp.GiaBan
            ORDER BY sp.MaSP
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                try {
                    Map<String, Object> row = new HashMap<>();
                    row.put("MaSP", rs.getString("MaSP"));
                    row.put("TenSP", rs.getString("TenSP"));
                    row.put("GiaBan", rs.getInt("GiaBan"));
                    row.put("SoLo", rs.getInt("SoLo"));
                    row.put("TongSoLuong", rs.getInt("TongSoLuong"));
                    row.put("HanSuDungGanNhat", rs.getDate("HanSuDungGanNhat")); // Có thể null
                    result.add(row);
                } catch (SQLException rowEx) {
                    System.err.println("❌ Lỗi lấy dữ liệu dòng: " + rowEx.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi xem danh sách hàng hóa: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


    // Xem chi tiết các lô hàng của một sản phẩm
    public static List<HangHoaDTO> timChiTietLoHangTheoSanPham(String maSP) {
        List<HangHoaDTO> result = new ArrayList<>();

        if (maSP == null || maSP.trim().isEmpty()) {
            System.err.println("❌ Mã sản phẩm không được rỗng!");
            return result;
        }

        String query = """
            SELECT hh.MaHang, hh.SoLuongConLai, hh.NgaySanXuat, hh.HanSuDung, hh.TrangThai
            FROM HANGHOA hh
            WHERE hh.MaSP = ?
            ORDER BY hh.HanSuDung ASC
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maSP);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    try {
                        Date ngaySX = rs.getDate("NgaySanXuat");
                        Date hanSD = rs.getDate("HanSuDung");
                        result.add(new HangHoaDTO(
                            rs.getString("MaHang"),
                            maSP,
                            rs.getInt("SoLuongConLai"),
                            ngaySX != null ? ngaySX.toLocalDate() : null,
                            hanSD != null ? hanSD.toLocalDate() : null,
                            rs.getString("TrangThai")
                        ));
                    } catch (SQLException rowEx) {
                        System.err.println("❌ Lỗi lấy dữ liệu lô hàng: " + rowEx.getMessage());
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi xem chi tiết lô hàng: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    // Xem tất cả hàng hóa
    public static List<Map<String, Object>> layDanhSachHangHoa() {
        List<Map<String, Object>> result  = new ArrayList<>();

        String query = """
            SELECT hh.MaHang, hh.MaSP, sp.TenSP, hh.SoLuongConLai,
                hh.NgaySanXuat, hh.HanSuDung, hh.TrangThai, sp.GiaBan
            FROM HANGHOA hh
            INNER JOIN SANPHAM sp ON hh.MaSP = sp.MaSP
            ORDER BY hh.HanSuDung ASC
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                try {
                    Map<String, Object> row = new HashMap<>();
                    row.put("MaHang", rs.getString("MaHang"));
                    row.put("MaSP", rs.getString("MaSP"));
                    row.put("TenSP", rs.getString("TenSP"));
                    row.put("SoLuongConLai", rs.getInt("SoLuongConLai"));
                    
                    Date ngaySX = rs.getDate("NgaySanXuat");
                    Date hanSD = rs.getDate("HanSuDung");
                    row.put("NgaySanXuat", ngaySX != null ? ngaySX.toLocalDate() : null);
                    row.put("HanSuDung", hanSD != null ? hanSD.toLocalDate() : null);
                    
                    row.put("TrangThai", rs.getString("TrangThai"));
                    row.put("GiaBan", rs.getInt("GiaBan"));
                    result.add(row);
                } catch (SQLException rowEx) {
                    System.err.println("❌ Lỗi đọc dòng dữ liệu: " + rowEx.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi lấy danh sách hàng hóa: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    // Tìm hàng hóa theo hạn sử dụng
    public static List<Map<String, Object>> timHangHoaTheoHanSuDung(LocalDate hanSuDung) {
        String query = """
                SELECT hh.MaHang, hh.MaSP, sp.TenSP, hh.SoLuongConLai,
                    hh.NgaySanXuat, hh.HanSuDung, hh.TrangThai, sp.GiaBan
                FROM HANGHOA hh
                INNER JOIN SANPHAM sp ON hh.MaSP = sp.MaSP
                WHERE hh.HanSuDung = ?
                ORDER BY hh.MaHang
        """;

        List<Map<String, Object>> result = new ArrayList<>();
        
        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, Date.valueOf(hanSuDung));
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("MaHang", rs.getString("MaHang"));
                    row.put("MaSP", rs.getString("MaSP"));
                    row.put("TenSP", rs.getString("TenSP"));
                    row.put("SoLuongConLai", rs.getInt("SoLuongConLai"));
                    row.put("NgaySanXuat", rs.getDate("NgaySanXuat").toLocalDate());
                    row.put("HanSuDung", rs.getDate("HanSuDung").toLocalDate());
                    row.put("TrangThai", rs.getString("TrangThai"));
                    row.put("GiaBan", rs.getInt("GiaBan"));
                    result.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm hàng hóa theo hạn sử dụng: " + e.getMessage());
        }
        return result;
    }

    // Xem chi tiết lô hàng theo mã hàng
    public static Map<String, Object> xemChiTietLoHang(String maHang) {
        String query = """
                SELECT 
                    hh.MaHang,
                    hh.MaSP,
                    sp.TenSP,
                    sp.LoaiSP,
                    sp.GiaBan,
                    ncc.TenNCC,
                    hh.NgaySanXuat,
                    hh.HanSuDung,
                    hh.SoLuongNhap,
                    hh.SoLuongConLai,
                    (hh.SoLuongNhap - hh.SoLuongConLai) AS SoLuongDaBan,
                    hh.TrangThai,
                    CASE
                        WHEN hh.HanSuDung < CURDATE() THEN 'Đã hết hạn'
                        WHEN hh.HanSuDung BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY) THEN 'Sắp hết hạn'
                        ELSE 'Còn hạn'
                    END AS TinhTrang,
                    DATEDIFF(hh.HanSuDung, CURDATE()) AS SoNgayConLai
                FROM HANGHOA hh
                JOIN SANPHAM sp ON hh.MaSP = sp.MaSP
                LEFT JOIN NHACUNGCAP ncc ON sp.MaNCC = ncc.MaNCC
                WHERE hh.MaHang = ?
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maHang);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Map<String, Object> result = new HashMap<>();
                    result.put("MaHang", rs.getString("MaHang"));
                    result.put("MaSP", rs.getString("MaSP"));
                    result.put("TenSP", rs.getString("TenSP"));
                    result.put("LoaiSP", rs.getString("LoaiSP"));
                    result.put("GiaBan", rs.getInt("GiaBan"));
                    result.put("TenNCC", rs.getString("TenNCC"));
                    result.put("NgaySanXuat", rs.getDate("NgaySanXuat") != null ? 
                        rs.getDate("NgaySanXuat").toLocalDate() : null);
                    result.put("HanSuDung", rs.getDate("HanSuDung") != null ? 
                        rs.getDate("HanSuDung").toLocalDate() : null);
                    result.put("SoLuongNhap", rs.getInt("SoLuongNhap"));
                    result.put("SoLuongConLai", rs.getInt("SoLuongConLai"));
                    result.put("SoLuongDaBan", rs.getInt("SoLuongDaBan"));
                    result.put("TrangThai", rs.getString("TrangThai"));
                    result.put("TinhTrang", rs.getString("TinhTrang"));
                    result.put("SoNgayConLai", rs.getInt("SoNgayConLai"));
                    return result;
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi xem chi tiết lô hàng: " + e.getMessage());
        }
        return null;
    }

    // Lấy danh sách hàng sắp hết hạn và đã hết hạn (không cập nhật trạng thái)
    public static List<Map<String, Object>> layHangSapHetHan() {
        String query = """
                SELECT hh.MaHang, hh.MaSP, sp.TenSP, hh.SoLuongConLai,
                    DATEDIFF(hh.HanSuDung, CURDATE()) AS SoNgayConLai,
                    CASE
                        WHEN hh.HanSuDung < CURDATE() THEN 'Đã hết hạn'
                        WHEN hh.HanSuDung BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY) THEN 'Sắp hết hạn'
                        ELSE 'Còn hạn'
                    END AS TinhTrangHSD
                FROM HANGHOA hh
                INNER JOIN SANPHAM sp ON hh.MaSP = sp.MaSP
                WHERE hh.HanSuDung <= DATE_ADD(CURDATE(), INTERVAL 7 DAY)
                ORDER BY hh.HanSuDung ASC
        """;

        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("MaHang", rs.getString("MaHang"));
                row.put("MaSP", rs.getString("MaSP"));
                row.put("TenSP", rs.getString("TenSP"));
                row.put("SoLuongConLai", rs.getInt("SoLuongConLai"));
                row.put("SoNgayConLai", rs.getInt("SoNgayConLai"));
                row.put("TinhTrangHSD", rs.getString("TinhTrangHSD"));
                result.add(row);
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi lấy danh sách hàng sắp hết hạn: " + e.getMessage());
        }
        return result;
    }

    // Cập nhật trạng thái 'expired' cho các lô hàng đã quá hạn 
    public static int capNhatTrangThaiExpired() {
        String query = """
                UPDATE HANGHOA
                SET TrangThai = 'expired'
                WHERE HanSuDung < CURDATE()
                AND TrangThai <> 'expired'
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi cập nhật trạng thái hết hạn: " + e.getMessage());
            return 0;
        }
    }

    public static boolean capNhatTrangThai(String maHang, String trangThaiMoi) {
        String query = "UPDATE HANGHOA SET TrangThai = ? WHERE MaHang = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, trangThaiMoi);
            stmt.setString(2, maHang);

            int rowAffected = stmt.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi cập nhật trạng thái hàng hóa: " + e.getMessage());
            return false;
        }
    }

    // Lấy danh sách chi tiết hàng sắp hết hạn (số ngày còn lại <= 30)
    public static List<Map<String, Object>> thongKeSapHetHan() {
        String query = """
                SELECT 
                    hh.MaHang,
                    hh.MaSP,
                    sp.TenSP,
                    hh.SoLuongConLai,
                    hh.NgaySanXuat,
                    hh.HanSuDung,
                    DATEDIFF(hh.HanSuDung, CURDATE()) AS SoNgayConLai
                FROM HANGHOA hh
                JOIN SANPHAM sp ON hh.MaSP = sp.MaSP
                WHERE hh.HanSuDung IS NOT NULL 
                AND hh.TrangThai = 'active'
                AND DATEDIFF(hh.HanSuDung, CURDATE()) BETWEEN 0 AND 30
                ORDER BY SoNgayConLai ASC
        """;

        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("MaHang", rs.getString("MaHang"));
                row.put("MaSP", rs.getString("MaSP"));
                row.put("TenSP", rs.getString("TenSP"));
                row.put("SoLuongConLai", rs.getInt("SoLuongConLai"));
                row.put("NgaySanXuat", rs.getDate("NgaySanXuat") != null ? 
                    rs.getDate("NgaySanXuat").toLocalDate() : null);
                row.put("HanSuDung", rs.getDate("HanSuDung") != null ? 
                    rs.getDate("HanSuDung").toLocalDate() : null);
                row.put("SoNgayConLai", rs.getInt("SoNgayConLai"));
                result.add(row);
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi lấy danh sách hàng sắp hết hạn: " + e.getMessage());
        }
        return result;
    }

    // Thống kê hàng hóa đã hết hạn
    public static List<Map<String, Object>> thongKeHangDaHetHan() {
        String query = """
                SELECT 
                    hh.MaHang,
                    hh.MaSP,
                    sp.TenSP,
                    hh.SoLuongConLai,
                    hh.NgaySanXuat,
                    hh.HanSuDung,
                    DATEDIFF(CURDATE(), hh.HanSuDung) AS SoNgayQuaHan,
                    hh.TrangThai
                FROM HANGHOA hh
                JOIN SANPHAM sp ON hh.MaSP = sp.MaSP
                WHERE hh.HanSuDung IS NOT NULL 
                AND hh.HanSuDung < CURDATE()
                AND hh.SoLuongConLai > 0
                ORDER BY SoNgayQuaHan DESC
        """;

        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("MaHang", rs.getString("MaHang"));
                row.put("MaSP", rs.getString("MaSP"));
                row.put("TenSP", rs.getString("TenSP"));
                row.put("SoLuongConLai", rs.getInt("SoLuongConLai"));
                row.put("NgaySanXuat", rs.getDate("NgaySanXuat") != null ? 
                    rs.getDate("NgaySanXuat").toLocalDate() : null);
                row.put("HanSuDung", rs.getDate("HanSuDung") != null ? 
                    rs.getDate("HanSuDung").toLocalDate() : null);
                row.put("SoNgayQuaHan", rs.getInt("SoNgayQuaHan"));
                row.put("TrangThai", rs.getString("TrangThai"));
                result.add(row);
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi lấy danh sách hàng đã hết hạn: " + e.getMessage());
        }
        return result;
    }

    // Lấy báo cáo tồn kho đầy đủ (sắp xếp theo HSD tăng dần)
    public static List<Map<String, Object>> layBaoCaoTonKho() {
        String query = """
                SELECT 
                    hh.MaHang,
                    hh.MaSP,
                    sp.TenSP,
                    hh.SoLuongConLai,
                    sp.GiaBan,
                    (hh.SoLuongConLai * sp.GiaBan) AS ThanhTien,
                    hh.NgaySanXuat,
                    hh.HanSuDung,
                    hh.TrangThai,
                    DATEDIFF(hh.HanSuDung, CURDATE()) AS SoNgayConLai
                FROM HANGHOA hh
                JOIN SANPHAM sp ON hh.MaSP = sp.MaSP
                WHERE hh.SoLuongConLai > 0
                ORDER BY hh.HanSuDung ASC, hh.MaHang ASC
        """;

        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("MaHang", rs.getString("MaHang"));
                row.put("MaSP", rs.getString("MaSP"));
                row.put("TenSP", rs.getString("TenSP"));
                row.put("SoLuongConLai", rs.getInt("SoLuongConLai"));
                row.put("GiaBan", rs.getInt("GiaBan"));
                row.put("ThanhTien", rs.getLong("ThanhTien"));
                row.put("NgaySanXuat", rs.getDate("NgaySanXuat") != null ? 
                    rs.getDate("NgaySanXuat").toLocalDate() : null);
                row.put("HanSuDung", rs.getDate("HanSuDung") != null ? 
                    rs.getDate("HanSuDung").toLocalDate() : null);
                row.put("TrangThai", rs.getString("TrangThai"));
                row.put("SoNgayConLai", rs.getInt("SoNgayConLai"));
                result.add(row);
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi lấy báo cáo tồn kho: " + e.getMessage());
        }
        return result;
    }
}

