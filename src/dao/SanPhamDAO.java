package dao;

import dto.SanPhamDTO;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.FormatUtil;
import util.JDBCUtil;
import java.text.Normalizer;
public class SanPhamDAO {
    public static List<SanPhamDTO> timSanPhamTheoTen(String ten) {
        List<SanPhamDTO> list = new ArrayList<>();

        if (ten == null || ten.trim().isEmpty()) return list;

        String query = """
                SELECT MaSP, TenSP, Loai, DonViTinh, SoLuongTon, GiaBan, MoTa, TrangThai
                FROM SANPHAM
                WHERE TenSP COLLATE utf8mb4_unicode_ci LIKE ? 
                ORDER BY TenSP;
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + ten.trim() + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new SanPhamDTO(
                        rs.getString("MaSP"),
                        rs.getString("TenSP"),
                        rs.getInt("Loai"),
                        rs.getInt("DonViTinh"),
                        rs.getInt("SoLuongTon"),
                        rs.getInt("GiaBan"),
                        rs.getString("MoTa"),
                        rs.getString("TrangThai")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm sản phẩm theo tên: " + e.getMessage());
        }
        return list;
    }

    public static SanPhamDTO timSanPhamTheoMa(String maSP) {
        if (maSP == null || maSP.trim().isEmpty())  return null;

        String query = """
                SELECT sp.MaSP, sp.TenSP, sp.Loai, sp.SoLuongTon, sp.DonViTinh, sp.GiaBan, sp.MoTa, sp.TrangThai 
                FROM SANPHAM sp 
                WHERE sp.MaSP = ?        
        """;

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maSP);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new SanPhamDTO(
                        rs.getString("MaSP"),
                        rs.getString("TenSP"),
                        rs.getInt("Loai"),
                        rs.getInt("DonViTinh"),
                        rs.getInt("SoLuongTon"),
                        rs.getInt("GiaBan"),
                        rs.getString("MoTa"),
                        rs.getString("TrangThai")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm sản phẩm theo mã: " + e.getMessage());
        }
        return null;
    }

    public static String generateMaSP() {
        String prefix = "SP";
        String newID = prefix + "001";
        String query = "SELECT MaSP FROM SANPHAM ORDER BY CAST(SUBSTRING(MaSP, 3) AS UNSIGNED) DESC LIMIT 1";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String lastID = rs.getString("MaSP");
                int number = Integer.parseInt(lastID.substring(2)) + 1;
                newID = prefix + String.format("%03d", number);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi tạo mã sản phẩm: " + e.getMessage());
        }

    return newID;
}

    public static boolean themSanPham(SanPhamDTO sp) {
        String query = "INSERT INTO SANPHAM (MaSP, TenSP, Loai, DonViTinh, GiaBan) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, sp.getMaSP());
            stmt.setString(2, sp.getTenSP());
            stmt.setInt(3, sp.getLoaiSP());
            stmt.setInt(4, sp.getDonViTinh());
            stmt.setInt(5, sp.getGiaBan());
            
            int rowAffected = stmt.executeUpdate();
            return rowAffected > 0;  
            
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thêm sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public static boolean suaSanPham(SanPhamDTO sp) {
        if (sp == null || sp.getMaSP() == null) return false;

        String query = """
            UPDATE SANPHAM SET TenSP = ?, Loai = ?, DonViTinh = ?, GiaBan = ?,
            MoTa = ?, TrangThai = ? WHERE MaSP = ?;        
        """;
        
        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, sp.getTenSP());
            stmt.setInt(2, sp.getLoaiSP());
            stmt.setInt(3, sp.getDonViTinh());
            stmt.setInt(4, sp.getGiaBan());
            stmt.setString(5, sp.getMoTa());
            stmt.setString(6, sp.getTrangThai());
            stmt.setString(7, sp.getMaSP());

            int rowAffected = stmt.executeUpdate();
            return rowAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Lỗi khi sửa sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public static boolean ngungKinhDoanhSanPham(String maSP) {
        if (maSP == null || maSP.trim().isEmpty()) return false;
        
        String checkStock = "SELECT SoLuongTon FROM SANPHAM WHERE MaSP = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement checkStmt = conn.prepareStatement(checkStock)) {
        
            checkStmt.setString(1, maSP);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                int soLuongTon = rs.getInt("SoLuongTon");
                if (soLuongTon > 0) {
                    System.err.println("❌ Không thể ngừng kinh doanh: Sản phẩm còn " + soLuongTon + " trong kho!");
                    return false;
                }
            } else {
                System.err.println("❌ Không tìm thấy sản phẩm!");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi kiểm tra tồn kho: " + e.getMessage());
            return false;
        }

        String updateStatus = "UPDATE SANPHAM SET TrangThai = 'inactive' WHERE MaSP = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(updateStatus)) {

            stmt.setString(1, maSP);
            int rowAffected = stmt.executeUpdate();
            return rowAffected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi đổi trạng thái sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public static boolean kichHoatSanPham(String maSP) {
        if (maSP == null || maSP.trim().isEmpty()) {
            return false;
        }
        
        String query = "UPDATE SANPHAM SET TrangThai = 'active' WHERE MaSP = ?";
        
        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, maSP);
            int rowAffected = stmt.executeUpdate();
            return rowAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi kích hoạt sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public static void thongKeTheoLoai() {

        String query = """
                    SELECT l.TenLoai, 
                        COUNT(sp.MaSP) AS SoLuongSanPham, 
                        SUM(sp.SoLuongTon) AS TongSoLuongTon, 
                        SUM(sp.GiaBan * sp.SoLuongTon) AS TongGiaTriTon
                    FROM SANPHAM sp
                    INNER JOIN LOAI l ON sp.Loai = l.MaLoai
                    GROUP BY l.TenLoai
                    ORDER BY SoLuongSanPham DESC;        
        """;
        
        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            if (!rs.isBeforeFirst()) {
                System.out.println("Không có dữ liệu để thống kê!");
                return;
            }

            System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                                      THỐNG KÊ SẢN PHẨM THEO LOẠI                                                  ║");
            System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
            System.out.printf("║  %-35s │ %-19s │ %-19s │ %-25s │ %-19s ║\n", 
                            "LOẠI SẢN PHẨM",  "SỐ LƯỢNG SẢN PHẨM", "SỐ LƯỢNG TỒN KHO",  "GIÁ TRỊ TỒN KHO",  "GIÁ TB (1 SP)");
            System.out.println("╠══════════════════════════════════════╪═════════════════════╪═════════════════════╪═══════════════════════════╪═════════════════════╣");

            int soLoai = 0;
            int tongSoLuongSP = 0;
            int tongSoLuongTon = 0;
            long tongGiaTriTon = 0;

            while (rs.next()) {
                String tenLoai = rs.getString("TenLoai");
                int soLuongSP = rs.getInt("SoLuongSanPham");
                int soLuongTonKho = rs.getInt("TongSoLuongTon");
                long giaTriTon = rs.getLong("TongGiaTriTon");

                double giaTrungBinh = (soLuongTonKho == 0) ? 0 : ((double) giaTriTon / soLuongTonKho);

                soLoai++;
                tongSoLuongSP += soLuongSP;
                tongSoLuongTon += soLuongTonKho;
                tongGiaTriTon += giaTriTon;

                System.out.printf("║  %-35s │ %19s │ %19s │ %25s │ %19s ║\n", 
                                tenLoai, 
                                String.format("%,d", soLuongSP),           
                                String.format("%,d", soLuongTonKho),       
                                FormatUtil.formatVND(giaTriTon), 
                                FormatUtil.formatVND(giaTrungBinh));
            }

            double tongGiaTrungBinh = (tongSoLuongTon == 0) ? 0 : ((double) tongGiaTriTon / tongSoLuongTon);

            System.out.println("╠══════════════════════════════════════╪═════════════════════╪═════════════════════╪═══════════════════════════╪═════════════════════╣");
            System.out.printf("║  %-35s │ %19s │ %19s │ %25s │ %19s ║\n", 
                            "TỔNG CỘNG", 
                            String.format("%,d", tongSoLuongSP),
                            String.format("%,d", tongSoLuongTon),
                            FormatUtil.formatVND(tongGiaTriTon), 
                            FormatUtil.formatVND(tongGiaTrungBinh));
            System.out.printf("║  %-35s │ %19s │ %19s │ %25s │ %19s ║\n", 
                                "TỔNG SỐ LOẠI", 
                                String.format("%,d", soLoai), 
                                "-", 
                                "-", 
                                "-");
            System.out.println("╚══════════════════════════════════════╧═════════════════════╧═════════════════════╧═══════════════════════════╧═════════════════════╝");

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thống kê sản phẩm theo loại: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static List<Map<String, Object>> thongKeSanPhamBanChayNhat(LocalDate fromDate, LocalDate toDate, int limit) {
        List<Map<String, Object>> result = new ArrayList<>();

        String query = """
                SELECT sp.MaSP, sp.TenSP, SUM(ct.SoLuong) AS TongSoLuongBan, SUM(ct.ThanhTien) AS DoanhThu
                FROM CHITIETHOADON ct
                JOIN HANGHOA hh ON ct.MaHang = hh.MaHang
                JOIN SanPham sp ON sp.MaSP = hh.MaSP
                JOIN HoaDon hd ON hd.MaHD = ct.MaHD
                WHERE hd.ThoiGianLapHD >= ? AND hd.ThoiGianLapHD < ?
                GROUP BY sp.MaSP, sp.TenSP
                ORDER BY TongSoLuongBan DESC
                LIMIT ?;        
        """;
        

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
                    row.put("maSP", rs.getString("MaSP"));
                    row.put("tenSP", rs.getString("TenSP"));
                    row.put("tongSoLuongBan", rs.getInt("TongSoLuongBan"));
                    row.put("doanhThu", rs.getLong("DoanhThu"));
                    result.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thống kê sản phẩm bán chạy nhất: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static void sanPhamSapHetTrongKho(int soLuong) {
        if (soLuong < 0) {
            System.out.println("❌ Ngưỡng tồn kho phải lớn hơn hoặc bằng 0!");
            return;
        }

        String query = """
                    SELECT sp.MaSP, sp.TenSP, sp.SoLuongTon, sp.GiaBan, Loai.TenLoai
                    FROM SANPHAM sp
                    INNER JOIN LOAI ON sp.Loai = Loai.MaLoai
                    WHERE sp.SoLuongTon <= ? AND sp.TrangThai = 'active'
                    ORDER BY sp.SoLuongTon ASC, sp.TenSP ASC        
        """;

        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, soLuong);
            
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║                    DANH SÁCH SẢN PHẨM SẮP HẾT TỒN KHO (≤ " + soLuong + " sản phẩm)                      ║");
                System.out.println("╠════════════╤══════════════════════╤══════════════════╤══════════════════╤══════════════════╣");
                System.out.printf("║ %-10s │ %-20s │ %-16s │ %-16s │ %-16s ║\n",
                        "MÃ SP", "TÊN SẢN PHẨM", "LOẠI", "SỐ LƯỢNG TỒN", "GIÁ BÁN");
                System.out.println("╠════════════╪══════════════════════╪══════════════════╪══════════════════╪══════════════════╣");

                int count = 0;
                long tongGiaTri = 0;

                while (rs.next()) {
                    String maSP = rs.getString("MaSP");
                    String tenSP = rs.getString("TenSP");
                    String loai = rs.getString("TenLoai");
                    int soLuongTon = rs.getInt("SoLuongTon");
                    int giaBan = rs.getInt("GiaBan");

                    String tenSPRutGon = (tenSP.length() > 20) ? tenSP.substring(0, 17) + "..." : tenSP;
                    String tenLoaiRutGon = (loai.length() > 16) ? loai.substring(0, 13) + "..." : loai;

                    System.out.printf("║ %-10s │ %-20s │ %-16s │ %16s │ %16s ║\n",
                        maSP, 
                        tenSPRutGon, 
                        tenLoaiRutGon, 
                        String.format("%,d", soLuongTon),
                        FormatUtil.formatVND(giaBan));
                    
                    count++;
                    tongGiaTri += (long) soLuongTon * giaBan;
                }

                if (count == 0) {
                    System.out.println("║         Không có sản phẩm nào có tồn kho ≤ " + soLuong + " sản phẩm                           ║");
                } else {
                    System.out.println("╠════════════╧══════════════════════╧══════════════════╧══════════════════╧══════════════════╣");
                    System.out.printf("║ %-14s │ Tổng số SP: %-8d │ Tổng giá trị: %18s ║\n",
                        "", count, FormatUtil.formatVND(tongGiaTri));
                }
                System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════╝");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thống kê sản phẩm sắp hết trong kho: " + e.getMessage());
        }
    }

    public static List<SanPhamDTO> getAllSanPham() {
    List<SanPhamDTO> list = new ArrayList<>();
    
    String query = "SELECT MaSP, TenSP, Loai, DonViTinh, SoLuongTon, " +
                "GiaBan, MoTa, TrangThai " +
                "FROM SANPHAM " +
                "ORDER BY MaSP ASC";

    try (Connection conn = JDBCUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            list.add(new SanPhamDTO(
                rs.getString("MaSP"),
                rs.getString("TenSP"),
                rs.getInt("Loai"),
                rs.getInt("DonViTinh"),
                rs.getInt("SoLuongTon"),
                rs.getInt("GiaBan"),
                rs.getString("MoTa"),
                rs.getString("TrangThai")
            ));
        }

    } catch (SQLException e) {
        System.err.println("❌ Lỗi khi lấy danh sách sản phẩm: " + e.getMessage());
        e.printStackTrace();
    }

    return list;
}

    public static List<SanPhamDTO> getSanPhamByTrangThai(String trangThai) {
    List<SanPhamDTO> list = new ArrayList<>();
    
    String query = "SELECT MaSP, TenSP, Loai, DonViTinh, SoLuongTon, " +
                "GiaBan, MoTa, TrangThai " +
                "FROM SANPHAM " +
                "WHERE TrangThai = ? " +
                "ORDER BY MaSP ASC";

    try (Connection conn = JDBCUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, trangThai);
        
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new SanPhamDTO(
                    rs.getString("MaSP"),
                    rs.getString("TenSP"),
                    rs.getInt("Loai"),
                    rs.getInt("DonViTinh"),
                    rs.getInt("SoLuongTon"),
                    rs.getInt("GiaBan"),
                    rs.getString("MoTa"),
                    rs.getString("TrangThai")
                ));
            }
        }

    } catch (SQLException e) {
        System.err.println("❌ Lỗi khi lấy sản phẩm theo trạng thái: " + e.getMessage());
        e.printStackTrace();
    }

    return list;
}

    public static void capnhatSoLuongTon(String maSP, int soLuong) {
        String query = "UPDATE SANPHAM SET SoLuongTon = ? WHERE MaSP = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, soLuong);
            stmt.setString(2, maSP);
            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Cập nhật số lượng tồn thành công");
            } else {
                System.out.println("Cập nhật số lượng tồn thất bại");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật số lượng tồn: " + e.getMessage());
        }
    }

    public static boolean congSoLuongTon(String maSP, int soLuong) {
        if (maSP == null || maSP.trim().isEmpty() || soLuong <= 0) {
            System.err.println("❌ Thông tin không hợp lệ!");
            return false;
        }

        String query = "UPDATE SANPHAM SET SoLuongTon = SoLuongTon + ? WHERE MaSP = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, soLuong);
            stmt.setString(2, maSP);

            int rowAffected = stmt.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi cộng số lượng tồn: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean congSoLuongTon(Connection conn, String maSP, int soLuong) throws SQLException {
        if (maSP == null || maSP.trim().isEmpty() || soLuong <= 0) {
            return false;
        }

        String query = """
            UPDATE SANPHAM 
            SET SoLuongTon = SoLuongTon + ? 
            WHERE MaSP = ?        
        """;
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, soLuong);
            stmt.setString(2, maSP);
            int rowAffected = stmt.executeUpdate();
            return rowAffected > 0;
        }
    }

    public static boolean truSoLuongTon(String maSP, int soLuong) {
        if (maSP == null || maSP.trim().isEmpty() || soLuong <= 0) {
            System.err.println("❌ Thông tin không hợp lệ!");
            return false;
        }

        String query = "UPDATE SANPHAM SET SoLuongTon = SoLuongTon - ? WHERE MaSP = ? AND SoLuongTon >= ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, soLuong);
            stmt.setString(2, maSP);
            stmt.setInt(3, soLuong); // Check đủ số lượng

            int rowAffected = stmt.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi trừ số lượng tồn: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static boolean kiemTraNCCCungCapSP(String maNCC, String maSP) {
        String query = "SELECT COUNT(*) FROM CHITIETPHIEUNHAP ct " +
                    "JOIN PHIEUNHAP pn ON ct.MaPhieu = pn.MaPhieu " +
                    "JOIN HANGHOA hh ON ct.MaHang = hh.MaHang " +
                    "WHERE pn.MaNCC = ? AND hh.MaSP = ?";
        
        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, maNCC);
            stmt.setString(2, maSP);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; 
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Lỗi kiểm tra NCC-SP: " + e.getMessage());
            e.printStackTrace(); 
        }
        return false; 
    }
}
