package dao;

import dto.ChiTietPhieuNhapDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import util.JDBCUtil;
import dao.SanPhamDAO;

public class ChiTietPhieuNhapDAO {

    public static boolean themChiTietPhieuNhap(Connection conn, ChiTietPhieuNhapDTO ctPhieuNhap) throws SQLException {
        if (ctPhieuNhap == null) return false;
        if (ctPhieuNhap.getSoLuong() <= 0) return false;
        if (ctPhieuNhap.getGiaNhap() < 0) return false;

        String selectQuery = "SELECT SoLuong, ThanhTien FROM CHITIETPHIEUNHAP WHERE MaPhieu = ? AND MaSP = ?";
        String updateQuery = "UPDATE CHITIETPHIEUNHAP SET SoLuong = ?, GiaNhap = ?, ThanhTien = ? WHERE MaPhieu = ? AND MaSP = ?";
        String insertQuery = "INSERT INTO CHITIETPHIEUNHAP (MaPhieu, MaSP, SoLuong, GiaNhap, ThanhTien) VALUES (?, ?, ?, ?, ?)";

        int soLuong = ctPhieuNhap.getSoLuong();
        int giaNhap = ctPhieuNhap.getGiaNhap();
        int thanhTien = soLuong * giaNhap;

        try {
            conn.setAutoCommit(false);

            // Check sản phẩm đã tồn tại trong chi tiết phiếu nhập chưa
            try (PreparedStatement check = conn.prepareStatement(selectQuery)) {
                check.setString(1, ctPhieuNhap.getMaPhieu());
                check.setString(2, ctPhieuNhap.getMaSP());
                try (ResultSet rs = check.executeQuery()) {

                    if (rs.next()) {
                        // sản phẩm đã tồn tại trong chi tiết phiếu nhập
                        int soLuongCu = rs.getInt("SoLuong");
                        int giaNhapCu = rs.getInt("GiaNhap");

                        // nếu giá mới khác giá cũ → lỗi
                        if (giaNhap != giaNhapCu) {
                            System.out.println("❌ LỖI: Sản phẩm " + ctPhieuNhap.getMaSP() +
                                " đã tồn tại với giá " + giaNhapCu +
                                ". Không thể thêm với giá khác (" + giaNhap + "). Bỏ qua!");
                            conn.rollback();
                            return false;
                        }

                        // giá giống nhau -> Cộng dồn số lượng
                        int soLuongMoi = soLuongCu + soLuong;
                        int thanhTienMoi = soLuongMoi * giaNhap;

                        // update cộng dồn
                        try (PreparedStatement update = conn.prepareStatement(updateQuery)) {
                            update.setInt(1, soLuongMoi);
                            update.setInt(2, giaNhap);
                            update.setInt(3, thanhTienMoi);
                            update.setString(4, ctPhieuNhap.getMaPhieu());
                            update.setString(5, ctPhieuNhap.getMaSP());
                            update.executeUpdate();
                        }

                    } else {
                        // sản phẩm chưa tồn tại trong chi tiết phiếu nhập -> Thêm mới
                        try (PreparedStatement insert = conn.prepareStatement(insertQuery)) {
                            insert.setString(1, ctPhieuNhap.getMaPhieu());
                            insert.setString(2, ctPhieuNhap.getMaSP());
                            insert.setInt(3, soLuong);
                            insert.setInt(4, giaNhap);
                            insert.setInt(5, thanhTien);
                            insert.executeUpdate();
                        }
                    }
                }
            }

            // cập nhật tồn kho
            boolean stockUpdated = SanPhamDAO.congSoLuongTon(conn, ctPhieuNhap.getMaSP(), soLuong);
            if (!stockUpdated)
                throw new SQLException("Cập nhật tồn kho thất bại cho sản phẩm: " + ctPhieuNhap.getMaSP());

            conn.commit();

        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
        return true;
    }


    public static List<ChiTietPhieuNhapDTO> timChiTietPhieuNhap(String maPhieu) {
        String query = """
                SELECT ctpn.MaSP, sp.TenSP, dv.TenDonVi AS DonViTinh, 
                       ctpn.SoLuong, ctpn.GiaNhap, ctpn.ThanhTien
                FROM CHITIETPHIEUNHAP ctpn
                INNER JOIN SANPHAM sp ON ctpn.MaSP = sp.MaSP
                INNER JOIN DONVI dv ON sp.MaDonVi = dv.MaDonVi
                WHERE ctpn.MaPhieu = ?
                ORDER BY ctpn.MaSP ASC;
        """;

        List<ChiTietPhieuNhapDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maPhieu);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new ChiTietPhieuNhapDTO(
                        maPhieu,
                        rs.getString("MaSP"),
                        rs.getString("TenSP"),
                        rs.getString("DonViTinh"),
                        rs.getInt("SoLuong"),
                        rs.getInt("GiaNhap"),
                        rs.getInt("ThanhTien")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm chi tiết phiếu nhập: " + e.getMessage());
        }
        return list;
    }

    public static List<ChiTietPhieuNhapDTO> getAllChiTietPhieuNhap() {
        List<ChiTietPhieuNhapDTO> list  = new ArrayList<>();

        String query = """
                SELECT ctpn.MaPhieu, ctpn.MaSP, sp.TenSP, dv.TenDonVi AS DonViTinh, 
                       ctpn.SoLuong, ctpn.GiaNhap, ctpn.ThanhTien
                FROM CHITIETPHIEUNHAP ctpn
                INNER JOIN SANPHAM sp ON ctpn.MaSP = sp.MaSP
                INNER JOIN DONVI dv ON sp.MaDonVi = dv.MaDonVi
                ORDER BY ctpn.MaPhieu ASC, ctpn.MaSP ASC;
        """;

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new ChiTietPhieuNhapDTO(
                    rs.getString("MaPhieu"),
                    rs.getString("MaSP"),
                    rs.getString("TenSP"),
                    rs.getString("DonViTinh"),
                    rs.getInt("SoLuong"),
                    rs.getInt("GiaNhap"),
                    rs.getInt("ThanhTien")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy tất cả chi tiết phiếu nhập: " + e.getMessage());
        }
        return list;
    }
}
