package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import dto.KhachHangDTO;
import util.JDBCUtil;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class KhachHangDAO {
    public static List<KhachHangDTO> getAllKhachHang() {
        String query = "SELECT MaKH, Ho, Ten, GioiTinh, NgaySinh, DienThoai, DiaChi FROM KHACHHANG";

        List<KhachHangDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                list.add(new KhachHangDTO(
                    rs.getString("MaKH"), 
                    rs.getString("Ho"), 
                    rs.getString("Ten"), 
                    rs.getString("GioiTinh"), 
                    rs.getDate("NgaySinh").toLocalDate(), 
                    rs.getString("DienThoai"), 
                    rs.getString("DiaChi")));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy tất cả khách hàng: " + e.getMessage());
        }
        return list;
    }

    public static void themKhachHang(KhachHangDTO kh) {
        String query = "INSERT INTO KHACHHANG (MaKH, Ho, Ten, GioiTinh, NgaySinh, DienThoai, DiaChi) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, kh.getMaKH());
            stmt.setString(2, kh.getHo());
            stmt.setString(3, kh.getTen());
            stmt.setString(4, kh.getGioiTinh());

            if (kh.getNgaySinh() != null) {
                stmt.setDate(5, java.sql.Date.valueOf(kh.getNgaySinh()));
            } else {
                stmt.setDate(5, null);
            }

            stmt.setString(6, kh.getDienThoai());
            stmt.setString(7, kh.getDiaChi());

            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Thêm khách hàng thành công");
            } else {
                System.out.println("Thêm khách hàng thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm khách hàng: " + e.getMessage());
        }
    }

    public static void suaKhachHang(KhachHangDTO kh, String maKH) {
        String query = "UPDATE KHACHHANG SET Ho = ?, Ten = ?, GioiTinh = ?, NgaySinh = ?, DienThoai = ?, DiaChi = ? WHERE MaKH = ?";

        try (Connection conn  = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, kh.getHo());
            stmt.setString(2, kh.getTen());
            stmt.setString(3, kh.getGioiTinh());
            stmt.setDate(4, java.sql.Date.valueOf(kh.getNgaySinh()));
            stmt.setString(5, kh.getDienThoai());
            stmt.setString(6, kh.getDiaChi());
            stmt.setString(7, maKH);
            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Sửa khách hàng thành công");
            } else {
                System.out.println("Sửa khách hàng thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi sửa khách hàng: " + e.getMessage());
        }
    }

    public static void xoaKhachHang(String maKH) {
        String query = "DELETE FROM KHACHHANG WHERE MaKH = ?";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, maKH);
            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Xóa khách hàng thành công");
            } else {
                System.out.println("Xóa khách hàng thất bại");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa khách hàng: " + e.getMessage());        
        }
    }

    public static List<KhachHangDTO> timKhachHangTheoTen(String tenKH) {
        String query = "SELECT MaKH, Ho, Ten, GioiTinh, NgaySinh, DienThoai, DiaChi FROM KHACHHANG WHERE LOWER(Ten) LIKE ?";

        List<KhachHangDTO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + tenKH + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                list.add(new KhachHangDTO(rs.getString("MaKH"), rs.getString("Ho"), rs.getString("Ten"), rs.getString("GioiTinh"), rs.getDate("NgaySinh").toLocalDate(), rs.getString("DienThoai"), rs.getString("DiaChi")));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm khách hàng theo tên: " + e.getMessage());
        }

        return list;
    }

    public static KhachHangDTO timKhachHangTheoMa(String maKH) {
        String query = "SELECT MaKH, Ho, Ten, GioiTinh, NgaySinh, DienThoai, DiaChi FROM KHACHHANG WHERE MaKH = ?";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, maKH);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return new KhachHangDTO(
                    rs.getString("MaKH"), 
                    rs.getString("Ho"), 
                    rs.getString("Ten"), 
                    rs.getString("GioiTinh"), 
                    rs.getDate("NgaySinh").toLocalDate(), 
                    rs.getString("DienThoai"), 
                    rs.getString("DiaChi")
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm khách hàng theo mã: " + e.getMessage());
        }
        return null;
    }

    public static KhachHangDTO timKhachHangTheoDienThoai(String dienThoai){
        String query = "SELECT MaKH, Ho, Ten, GioiTinh, NgaySinh, DienThoai, DiaChi FROM KHACHHANG WHERE DienThoai = ?";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, dienThoai);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return new KhachHangDTO(
                    rs.getString("MaKH"), 
                    rs.getString("Ho"), 
                    rs.getString("Ten"), 
                    rs.getString("GioiTinh"), 
                    rs.getDate("NgaySinh").toLocalDate(), 
                    rs.getString("DienThoai"), 
                    rs.getString("DiaChi")
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm khách hàng theo điện thoại: " + e.getMessage());
        }
        return null;
    }

    public static boolean kiemTraMaKH(String maKH) {
        String query = "SELECT COUNT(*) FROM KHACHHANG WHERE MaKH = ?";

        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, maKH);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1) > 0;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra mã khách hàng: " + e.getMessage());
        }
        return false;
    }

    public static void importDSKH(String filePath) {
        int added = 0, skipped = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int lineNumber = 0;
            String line;


            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] data = line.split(",", -1);
                if (data.length < 7) {
                    System.out.println("Dòng " + lineNumber + " không hợp lệ: " + line);
                    skipped++;
                    continue;
                }

                String maKH = data[0].trim();
                String ho = data[1].trim();
                String ten = data[2].trim();
                String gioiTinh = data[3].trim();

                LocalDate ngaySinh = null;
                if (!data[4].trim().isEmpty()) {
                    try {
                        ngaySinh = LocalDate.parse(data[4].trim());
                    } catch (Exception e) {
                        System.out.println("Ngày sinh không hợp lệ: " + data[4]);
                    }
                }

                String dienThoai = data[5].trim();
                String diaChi = data[6].trim();

                KhachHangDTO kh = new KhachHangDTO(maKH, ho, ten, gioiTinh, ngaySinh, diaChi, dienThoai);

                if (kiemTraMaKH(maKH) || timKhachHangTheoDienThoai(dienThoai) != null) {
                    System.out.println("Khách hàng " + maKH + " hoặc điện thoại " + dienThoai + " đã tồn tại!");
                    skipped++;
                    continue;
                }
                themKhachHang(kh);
                added++;
            }
            System.out.println("Đã thêm thành công " + added + " khách hàng từ file " + filePath);
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }

    public static void thongKeTheoGioiTinh() {
        String query = "SELECT GioiTinh, COUNT(*) AS SoLuong FROM KHACHHANG GROUP BY GioiTinh";

        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            System.out.println("\n╔═════════════════════════════════════════╗");
            System.out.println("║    THỐNG KÊ KHÁCH HÀNG THEO GIỚI TÍNH   ║");
            System.out.println("╠══════════════════╤══════════════════════╣");
            System.out.printf("║ %-16s │ %-20s ║\n", "Giới Tính", "Số Lượng");
            System.out.println("╠══════════════════╪══════════════════════╣");

            while (rs.next()) {
                String gioitinh = rs.getString("GioiTinh");
                int soluong = rs.getInt("SoLuong");

                System.out.printf("║ %-16s │ %-20d ║\n", gioitinh, soluong);
            }
            System.out.println("╚══════════════════╧══════════════════════╝");

                
        } catch (SQLException e) {
            System.err.println("Lỗi khi thống kê khách hàng theo giới tính: " + e.getMessage());
        }
    }
}