package dao;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import dto.sanPhamDTO;
import util.FormatUtil;
import util.JDBCUtil;

public class SanPhamDAO {
    public static List<sanPhamDTO> getAllSanPham() {
        String query = "SELECT MaSP, TenSP, Loai, SoLuongTon, DonViTinh, GiaBan, NgaySanXuat, HanSuDung, MoTa, TrangThai FROM SANPHAM\n" +
        "FROM SANPHAM\n" +
        "INNER JOIN LOAI ON SANPHAM.loai = LOAI.MaLoai\n" +
        "INNER JOIN DONVI ON SANPHAM.donVi = DONVI.DonViTinh\n";

        List<sanPhamDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                LocalDate ngaySanXuat = rs.getDate("NgaySanXuat").toLocalDate();

                // HSD (Date -> int ddMMyyyy)
                LocalDate hSD = rs.getDate("HanSuDung").toLocalDate();
                int hsdInt = hSD.getDayOfMonth() * 1000000
                           + hSD.getMonthValue() * 10000
                           + hSD.getYear();

                
                list.add(new sanPhamDTO(
                    rs.getString("MaSP"), 
                    rs.getString("TenSP"), 
                    rs.getInt("Loai"), 
                    rs.getInt("DonViTinh"), 
                    rs.getInt("SoLuongTon"), 
                    rs.getInt("GiaBan"), 
                    ngaySanXuat, 
                    hsdInt, 
                    rs.getString("MoTa"),
                    rs.getString("TrangThai")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy tất cả sản phẩm: " + e.getMessage());
        }
        return list;
    }

    public static List<sanPhamDTO> timSanPhamTheoTen(String name) {
        String query = "SELECT MaSP, TenSP, Loai, SoLuongTon, DonViTinh, GiaBan, NgaySanXuat, HanSuDung, MoTa, TrangThai FROM SANPHAM\n" +
        "INNER JOIN LOAI ON SANPHAM.loai = LOAI.MaLoai\n" +
        "INNER JOIN DONVI ON SANPHAM.donVi = DONVI.DonViTinh\n" +
        "WHERE tenSP LIKE ?";

        List<sanPhamDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            stmt.setString(1, "%" + name + "%");        
            while(rs.next()){
                LocalDate ngaySanXuat = rs.getDate("NgaySanXuat").toLocalDate();

                // HSD (Date -> int ddMMyyyy)
                LocalDate hSD = rs.getDate("HanSuDung").toLocalDate();
                int hsdInt = hSD.getDayOfMonth() * 1000000
                           + hSD.getMonthValue() * 10000
                           + hSD.getYear();


                list.add(new sanPhamDTO(
                    rs.getString("MaSP"), 
                    rs.getString("TenSP"), 
                    rs.getInt("Loai"), 
                    rs.getInt("DonViTinh"), 
                    rs.getInt("SoLuongTon"), 
                    rs.getInt("GiaBan"), 
                    ngaySanXuat, 
                    hsdInt, 
                    rs.getString("MoTa"),
                    rs.getString("TrangThai")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm sản phẩm theo tên: " + e.getMessage());
        }
        return list;
    }

    public static sanPhamDTO timSanPhamTheoMa(String ma) {
        String query = "SELECT MaSP, TenSP, Loai, SoLuongTon, DonViTinh, GiaBan, NgaySanXuat, HanSuDung, MoTa, TrangThai FROM SANPHAM\n" +
        "INNER JOIN LOAI ON SANPHAM.loai = LOAI.MaLoai\n" +
        "INNER JOIN DONVI ON SANPHAM.donVi = DONVI.DonViTinh\n" +
        "WHERE maSP = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ma);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                LocalDate ngaySanXuat = rs.getDate("NgaySanXuat").toLocalDate();

                // HSD (Date -> int ddMMyyyy)
                LocalDate hSD = rs.getDate("HanSuDung").toLocalDate();
                int hsdInt = hSD.getDayOfMonth() * 1000000
                           + hSD.getMonthValue() * 10000
                           + hSD.getYear();

                return new sanPhamDTO(
                    rs.getString("MaSP"), 
                    rs.getString("TenSP"), 
                    rs.getInt("Loai"), 
                    rs.getInt("DonViTinh"), 
                    rs.getInt("SoLuongTon"), 
                    rs.getInt("GiaBan"), 
                    ngaySanXuat, 
                    hsdInt, 
                    rs.getString("MoTa"),
                    rs.getString("TrangThai")
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm sản phẩm theo mã: " + e.getMessage());
        }
        return null;
    }

    public static void themSanPham(sanPhamDTO sp, int loai, int donVi) {
        String query = "INSERT INTO SANPHAM (MaSP, TenSP, Loai, DonViTinh, GiaBan, NgaySanXuat, HanSuDung, MoTa, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sp.getMaSP());
            stmt.setString(2, sp.getTenSP());
            stmt.setInt(3, sp.getLoaiSP());
            stmt.setInt(4, sp.getDonViTinh());
            stmt.setInt(5, sp.getGiaBan());
            stmt.setDate(6, Date.valueOf(sp.getNgaySanXuat()));
            
            // HSD (int ddMMyyyy -> LocalDate -> Date)
            int hSD = sp.getHSD();
            String hSDStr = String.format("%08d", hSD);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            LocalDate hSDDate = LocalDate.parse(hSDStr, formatter);
            stmt.setDate(7, Date.valueOf(hSDDate));

            stmt.setString(8, sp.getMoTa());
            stmt.setString(9, sp.getTrangThai());
            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Thêm sản phẩm thành công");
            } else {
                System.out.println("Thêm sản phẩm thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
        }
    }

    public static void suaSanPham(sanPhamDTO sp, String maSP) {
        String query = "UPDATE SANPHAM SET TenSP = ?, LoaiSP = ?, DonViTinh = ?, GiaBan = ?, NgaySanXuat = ?, HanSuDung = ?, MoTa = ?, TrangThai = ? WHERE MaSP = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sp.getTenSP());
            stmt.setInt(2, sp.getLoaiSP());
            stmt.setInt(3, sp.getDonViTinh());
            stmt.setInt(4, sp.getGiaBan());
            stmt.setDate(5, Date.valueOf(sp.getNgaySanXuat()));

            // HSD (int ddMMyyyy -> LocalDate -> Date)
            int hSD = sp.getHSD();
            String hSDStr = String.format("%08d", hSD);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            LocalDate hSDDate = LocalDate.parse(hSDStr, formatter);
            stmt.setDate(6, Date.valueOf(hSDDate));

            
            stmt.setString(7, sp.getMoTa());
            stmt.setString(8, maSP);
            stmt.setString(9, sp.getTrangThai());
            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Sửa sản phẩm thành công");
            } else {
                System.out.println("Sửa sản phẩm thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi sửa sản phẩm theo mã: " + e.getMessage());
        }
    }

    public static boolean xoaSanPham(String ma) {
        String query = "UPDATE SANPHAM SET TrangThai = 'inactive' WHERE MaSP = ?";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ma);
            int rowAffected = stmt.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa sản phẩm theo mã: " + e.getMessage());
        }
        return false;
    }

    public static void capnhatTrangThaiHetHan() {
        String query = "UPDATE SANPHAM SET TrangThai = 'inactive' WHERE HanSuDung < CURDATE() AND TrangThai = 'active'";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Đã cập nhật " + rowAffected + " sản phẩm trạng thái hết hạn");
            } else {
                System.out.println("Không có sản phẩm nào trạng thái hết hạn");
            }
                
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật trạng thái hết hạn: " + e.getMessage());
        }
    }

    public static void thongKeTheoLoai() {
        String query = "SELECT Loai.TenLoai, " + 
                        "COUNT(sp.MaSP) AS SoLuongSanPham, " +
                        "SUM(SANPHAM.SoLuongTon) AS TongSoLuongTon, " +
                        "SUM(sp.GiaBan * sp.SoLuongTon) AS TongGiaTriTon " +
                        "FROM SANPHAM sp " +
                        "INNER JOIN LOAI ON sp.Loai = Loai.MaLoai " +
                        "GROUP BY Loai.TenLoai " +
                        "ORDER BY SoLuongSanPham DESC";

        try (Connection conn = JDBCUtil.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                                   THỐNG KÊ SẢN PHẨM THEO LOẠI                                                ║");
            System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
            System.out.printf("║  %-35s │ %-19s │ %-19s │ %-19s │ %-19s║\n", "LOẠI SẢN PHẨM", "SỐ LƯỢNG SẢN PHẨM", "SỐ LƯỢNG TỒN KHO", "GIÁ TRỊ TỒN KHO", "GIÁ TRUNG BÌNH (Tồn)");
            System.out.println("╠══════════════════════════════════════╪═════════════════════╪═════════════════════╪═════════════════════╪═════════════════════╣");

            int soLoai = 0;
            int soLuongSanPham = 0;
            int soLuongTon = 0;
            long giaTriTon = 0;

            while (rs.next()) {
                String tenLoai = rs.getString("TenLoai");
                int soLuongSP = rs.getInt("SoLuongSanPham");
                int TongTonKho = rs.getInt("TongSoLuongTon");
                long TongGiaTriTon = rs.getLong("TongGiaTriTon");

                long giaTrungBinh = (TongTonKho == 0) ? 0 : (TongGiaTriTon / TongTonKho);
                
                soLoai++;
                soLuongSanPham += soLuongSP;
                soLuongTon += TongTonKho;
                giaTriTon += TongGiaTriTon;

                System.out.printf("║  %-35s │ %-19d │ %-19d │ %-19s │ %-19s ║\n", tenLoai, soLuongSP, TongTonKho, FormatUtil.formatVND(TongGiaTriTon), FormatUtil.formatVND(giaTrungBinh));
            }

            long tongGiaTrungBinh = (soLuongTon == 0) ? 0 : (giaTriTon / soLuongTon);

            System.out.println("╠══════════════════════════════════════╪═════════════════════╪═════════════════════╪═════════════════════╪═════════════════════╣");

            System.out.printf("║  %-35s │ %-19d │ %-19d │ %-19s │ %-19s ║\n", "TỔNG CỘNG", soLuongSanPham, soLuongTon, FormatUtil.formatVND(giaTriTon), FormatUtil.formatVND(tongGiaTrungBinh));
            System.out.printf("║  %-35s │ %-19d │ %-19s │ %-19s │ %-19s ║\n", "TỔNG SỐ LOẠI", soLoai, "-", "-", "-");
            System.out.println("╚══════════════════════════════════════╧═════════════════════╧═════════════════════╧═════════════════════╧═════════════════════╝");

        } catch (SQLException e) {
            System.err.println("Lỗi khi thống kê sản phẩm theo loại: " + e.getMessage());
        }
    }

    public static void thongKeTheoNSX() {
        String query = "SELECT NgaySanXuat, " +
                        "COUNT(sp.MaSP) AS SoLuongSanPham, " +
                        "SUM(sp.SoLuongTon) AS TongSoLuongTon, " +
                        "SUM(sp.GiaBan * sp.SoLuongTon) AS TongGiaTriTon " +
                        "FROM SANPHAM sp " +
                        "GROUP BY NgaySanXuat " +
                        "ORDER BY NgaySanXuat DESC";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                                   THỐNG KÊ SẢN PHẨM THEO NGÀY SẢN XUẤT                                                ║");
            System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
            System.out.printf("║  %-35s │ %-19s │ %-19s │ %-19s │ %-19s║\n", "NGÀY SẢN XUẤT", "SỐ LƯỢNG SẢN PHẨM", "SỐ LƯỢNG TỒN KHO", "GIÁ TRỊ TỒN KHO", "GIÁ TRUNG BÌNH (Tồn)");
            System.out.println("╠══════════════════════════════════════╪═════════════════════╪═════════════════════╪═════════════════════╪═════════════════════╣");
            

            int tongSoLuongSanPham = 0;
            int tongSoLuongTon = 0;
            long tongGiaTriTon = 0;
            int soNgay = 0;

            while (rs.next()) {
                Date ngaySanXuat = rs.getDate("NgaySanXuat");
                int soLuongSP = rs.getInt("SoLuongSanPham");
                int soLuongTon = rs.getInt("TongSoLuongTon");
                int giaTriTon = rs.getInt("TongGiaTriTon");

                long giaTrungBinh = (soLuongTon == 0) ? 0 : (giaTriTon / soLuongTon);

                soNgay++;
                tongSoLuongSanPham += soLuongSP;
                tongSoLuongTon += soLuongTon;
                tongGiaTriTon += giaTriTon;

                System.out.printf("║  %-35s │ %-19d │ %-19d │ %-19s │ %-19s ║\n", ngaySanXuat.toLocalDate(), soLuongSP, soLuongTon, FormatUtil.formatVND(giaTriTon), FormatUtil.formatVND(giaTrungBinh));
            }

            long tongGiaTrungBinh = (tongSoLuongTon == 0) ? 0 : (tongGiaTriTon / tongSoLuongTon);

            System.out.println("╠══════════════════════════════════════╪═════════════════════╪═════════════════════╪═════════════════════╪═════════════════════╣");
            System.out.printf("║  %-35s │ %-19d │ %-19d │ %-19s │ %-19s ║\n", "TỔNG CỘNG", tongSoLuongSanPham, tongSoLuongTon, FormatUtil.formatVND(tongGiaTriTon), FormatUtil.formatVND(tongGiaTrungBinh));
            System.out.printf("║  %-35s │ %-19d │ %-19s │ %-19s │ %-19s ║\n", "TỔNG SỐ NGÀY", soNgay, "-", "-", "-");
            System.out.println("╚══════════════════════════════════════╧═════════════════════╧═════════════════════╧═════════════════════╧═════════════════════╝");

        } catch (SQLException e) {
            System.err.println("Lỗi khi thống kê sản phẩm theo ngày sản xuất: " + e.getMessage());
        }
    }

    public static void sanPhamSapHetTrongKho(int soLuong) {
        String query = "SELECT sp.MaSP, sp.TenSP, sp.SoLuongTon, sp.GiaBan,Loai.TenLoai " +
                        "FROM SANPHAM sp " + 
                        "INNER JOIN LOAI ON sp.Loai = Loai.MaLoai " +
                        "WHERE sp.SoLuongTon <= ? AND sp.TrangThai = 'active'" + 
                        " ORDER BY sp.SoLuongTon ASC";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, soLuong);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                DANH SÁCH SẢN PHẨM SẮP HẾT TỒN KHO                          ║");
            System.out.println("╠════════════╤══════════════════════╤══════════════════╤══════════════════╤══════════════════╣");
            System.out.printf("║ %-10s │ %-20s │ %-16s │ %-16s │ %-16s ║\n",
                        "MÃ SP", "TÊN SP", "LOẠI", "SỐ LƯỢNG", "GIÁ BÁN");
            System.out.println("╠════════════╪══════════════════════╪══════════════════╪══════════════════╪══════════════════╣");

            boolean found = false;
            while (rs.next()) {
                found = true;
                String maSP = rs.getString("MaSP");
                String tenSP = rs.getString("TenSP");
                String tenLoai = rs.getString("TenLoai");
                int soLuongTon = rs.getInt("SoLuongTon");
                int giaBan = rs.getInt("GiaBan");

                System.out.printf("║ %-10s │ %-20s │ %-16s │ %-16d │ %-16s ║\n",
                            maSP, tenSP, tenLoai, soLuongTon, FormatUtil.formatVND(giaBan));
            }

            if (!found) {
                System.out.println("║                       Không có sản phẩm nào sắp hết tồn kho theo ngưỡng này.               ║");
            }

            System.out.println("╚════════════╧══════════════════════╧══════════════════╧══════════════════╧══════════════════╝");
        } catch (SQLException e) {
            System.err.println("Lỗi khi thống kê sản phẩm sắp hết trong kho: " + e.getMessage());
        }

    }
}