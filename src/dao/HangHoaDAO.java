package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.ResultSet;
import dto.HangHoaDTO;
import util.JDBCUtil;

public class HangHoaDAO {
    public static HangHoaDTO timHangHoaTheoMa(String maHang) {
        String query = "SELECT * FROM HANGHOA WHERE MaHang = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maHang);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new HangHoaDTO(
                    rs.getString("MaHang"),
                    rs.getString("MaSP"),
                    rs.getInt("SoLuongConLai"),
                    rs.getDate("NgaySanXuat").toLocalDate(),
                    rs.getDate("HanSuDung").toLocalDate(),
                    rs.getString("TrangThai")
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm hàng hóa: " + e.getMessage());
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

        List<Map<String, Object>> result  = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new java.util.HashMap<>();
                row.put("MaSP", rs.getString("MaSP"));
                row.put("TenSP", rs.getString("TenSP"));
                row.put("GiaBan", rs.getInt("GiaBan"));
                row.put("SoLo", rs.getInt("SoLo"));
                row.put("TongSoLuong", rs.getInt("TongSoLuong"));
                row.put("HanSuDungGanNhat", rs.getDate("HanSuDungGanNhat"));
                result.add(row);
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi xem danh sách hàng hóa: " + e.getMessage());
        }
        return result;
    }

    // Xem chi tiết các lô hàng của một sản phẩm
    public static List<HangHoaDTO> timChiTietLoHangTheoSanPham(String maSP) {
        String query = """
                SELECT hh.MaHang, hh.SoLuongConLai, hh.NgaySanXuat, hh.HanSuDung, hh.TrangThai
                FROM HANGHOA hh
                WHERE hh.MaSP = ?
                ORDER BY hh.HanSuDung ASC
        """;

        List<HangHoaDTO> result = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);) {

            stmt.setString(1, maSP);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                result.add(new HangHoaDTO(
                    rs.getString("MaHang"),
                    maSP,
                    rs.getInt("SoLuongConLai"),
                    rs.getDate("NgaySanXuat").toLocalDate(),
                    rs.getDate("HanSuDung").toLocalDate(),
                    rs.getString("TrangThai")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi xem chi tiết lô hàng: " + e.getMessage());
        }
        return result;
    }

    // Xem tất cả hàng hóa
    public static List<Map<String, Object>> layDanhSachHangHoa() {
        String query = """
                SELECT hh.MaHang, hh.MaSP, sp.TenSP, hh.SoLuongConLai,
                    hh.NgaySanXuat, hh.HanSuDung, hh.TrangThai, sp.GiaBan
                FROM HANGHOA hh
                INNER JOIN SANPHAM sp ON hh.MaSP = sp.MaSP
                ORDER BY hh.HanSuDung ASC        
        """;

        List<Map<String, Object>> result  = new ArrayList<>();
        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new java.util.HashMap<>();
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
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi lấy danh sách hàng hóa: " + e.getMessage());
        }
        return result;
    }
}
