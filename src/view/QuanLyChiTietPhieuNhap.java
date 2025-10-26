package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dao.ChiTietPhieuNhapDAO;
import dao.HangHoaDAO;
import dao.NhapHangDAO;
import dao.SanPhamDAO;
import dto.ChiTietPhieuNhapDTO;
import dto.NhapHangDTO;
import dto.SanPhamDTO;
import util.FormatUtil;
import java.util.Map;

public class QuanLyChiTietPhieuNhap {
    public static void menuQuanLyChiTietPhieuNhap() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n████████████████████████████████████████████████████████████████████████████████");
            System.out.println("██                                                                            ██");
            System.out.println("██                      HỆ THỐNG QUẢN LÝ CHI TIẾT PHIẾU NHẬP                      ██");
            System.out.println("██                                                                            ██");
            System.out.println("████████████████████████████████████████████████████████████████████████████████");
            System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓ MENU CHỨC NĂNG ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
            System.out.println("▒ [1] ➜ Thêm chi tiết vào phiếu nhập                                         ▒");
            System.out.println("▒ [2] ➜ Tìm kiếm chi tiết phiếu nhập                                        ▒");
            System.out.println("▒ [3] ➜ Xem danh sách chi tiết phiếu nhập                                  ▒");
            System.out.println("▒ [4] ➜ Thống kê sản phẩm nhập nhiều nhất                                  ▒");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
            System.out.println("░ [0] ✗ Quay lại menu chính                                                    ░");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            int choice = -1;

            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 0 && choice <= 4) break;
                    System.out.print("Vui lòng nhập số trong khoảng 0–4: ");
                } else {
                    System.out.print("Nhập không hợp lệ. Vui lòng nhập lại: ");
                    scanner.next();
                }
            }

            switch (choice) {
                case 1:
                    themChiTietVaoPhieuNhap(scanner);
                    break;
                case 2:
                    timTheoMaPhieu(scanner);
                    break;
                case 3:
                    xemDanhSachChiTietPhieuNhap();
                    break;
                case 4:
                    thongKeSanPhamNhap(scanner);
                    break;
                case 0:
                    System.out.println("Quay lại menu chính thành công.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
                    break;
            }
        }
    }

    public static void themChiTietVaoPhieuNhap(Scanner scanner) {

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║          THÊM CHI TIẾT VÀO PHIẾU NHẬP              ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        // tìm phiếu nhập
        System.out.print("\n→ Nhập mã phiếu nhập (hoặc '0' để hủy): ");
        String ma = scanner.nextLine().trim();

        if ("0".equals(ma)) {
            System.out.println("⚠️  Đã hủy thao tác.");
            return;
        }

        NhapHangDTO phieuNhap = NhapHangDAO.timPhieuNhapTheoMa(ma);
        if (phieuNhap == null) {
            System.out.println("❌ Không tìm thấy phiếu nhập với mã: " + ma);
            return;
        }

        System.out.println("\n✅ Tìm thấy phiếu nhập:");
        System.out.println("   Mã phiếu: " + phieuNhap.getMaPhieu());
        System.out.println("   Nhà cung cấp: " + phieuNhap.getMaNCC());
        System.out.println("   Tổng tiền hiện tại: " + FormatUtil.formatVND(phieuNhap.getTongTien()));
        System.out.println("   Ngày lập: " + phieuNhap.getNgayLapPhieu().format(
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        String maPhieu = phieuNhap.getMaPhieu();
        String maNCC = phieuNhap.getMaNCC();

        // nhập chi tiết
        Connection conn = null;
        try {
            conn = util.JDBCUtil.getConnection();
            conn.setAutoCommit(false);

            int tongTienThem = 0;
            int countSuccess = 0;

            System.out.println("\n📦 NHẬP CHI TIẾT HÀNG HÓA");
            while (true) {
                System.out.print("\n -> Nhập mã sản phẩm (hoặc'0' để kết thúc): ");
                String maSP = scanner.nextLine().trim();
                if (maSP.equals("0")) break;

                SanPhamDTO sanPham = SanPhamDAO.timSanPhamTheoMa(maSP);
                if (sanPham == null) {
                    System.out.println("❌ Không tìm thấy sản phẩm: " + maSP);
                    continue;
                }
                
                System.out.println("✅ Sản phẩm: " + sanPham.getTenSP());

                boolean nccDaCungCap = SanPhamDAO.kiemTraNCCCungCapSP(maNCC, maSP);
                if (!nccDaCungCap) {
                    System.out.println("\n⚠️  CẢNH BÁO:");
                    System.out.println("   Nhà cung cấp này chưa từng cung cấp sản phẩm này!");
                    System.out.print("→ Bạn có chắc muốn tiếp tục? (Y/N): ");
                    String confirm = scanner.nextLine().trim().toUpperCase();

                    if (!"Y".equals(confirm)) {
                        System.out.println("⚠️  Đã hủy thêm sản phẩm " + maSP + " vào phiếu nhập.");
                        continue;
                    }
                    System.out.println("✅ Đã xác nhận. Tiếp tục nhập thông tin...\n");
                }

                int soLuong;
                while (true) {
                    System.out.print("→ Số lượng: ");
                    String slStr = scanner.nextLine().trim();
                    
                    try {
                        soLuong = Integer.parseInt(slStr);
                        if (soLuong > 0) {
                            break; // Hợp lệ
                        }
                        System.out.println("❌ Số lượng phải lớn hơn 0!");
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Số lượng không hợp lệ!");
                    }
                }

                int giaNhap;
                while (true) {
                    System.out.print("→ Giá nhập: ");
                    String giaStr = scanner.nextLine().trim();
                    
                    try {
                        giaNhap = Integer.parseInt(giaStr);
                        if (giaNhap > 0) {
                            break; 
                        }
                        System.out.println("❌ Giá nhập phải lớn hơn 0!");
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Giá nhập không hợp lệ!");
                    }
                }

                LocalDate ngaySanXuat;
                while (true) {
                    System.out.print("→ Ngày sản xuất (dd/MM/yyyy): ");
                    String nsxStr = scanner.nextLine().trim();
                    
                    try {
                        ngaySanXuat = LocalDate.parse(nsxStr, 
                            DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        break; 
                    } catch (DateTimeParseException e) {
                        System.out.println("❌ Ngày sản xuất không hợp lệ! (VD: 25/10/2025)");
                    }
                }

                LocalDate hanSuDung;
                while (true) {
                    System.out.print("→ Hạn sử dụng (dd/MM/yyyy): ");
                    String hsdStr = scanner.nextLine().trim();
                    
                    try {
                        hanSuDung = LocalDate.parse(hsdStr, 
                            DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        
                        // ✅ Validate HSD > NSX
                        if (hanSuDung.isAfter(ngaySanXuat)) {
                            break; // Hợp lệ
                        }
                        System.out.println("❌ Hạn sử dụng phải sau ngày sản xuất!");
                        
                    } catch (DateTimeParseException e) {
                        System.out.println("❌ Hạn sử dụng không hợp lệ! (VD: 25/10/2026)");
                    }
                }

                int thanhTien = soLuong * giaNhap;
                try {
                    String maHang = HangHoaDAO.taoHangHoa(conn, maSP, soLuong, ngaySanXuat, hanSuDung);

                    if (maHang == null) 
                        throw new SQLException("Không thể tạo hàng hóa!");
                    
                    ChiTietPhieuNhapDTO chiTiet = new ChiTietPhieuNhapDTO(
                        maPhieu, maHang, sanPham.getTenSP(), null, soLuong, giaNhap, thanhTien
                    );
                
                    boolean added = ChiTietPhieuNhapDAO.themChiTietPhieuNhap(conn, chiTiet);
                    if (!added) 
                        throw new SQLException("Không thể thêm chi tiết!");
                    
                    boolean updated = SanPhamDAO.congSoLuongTon(conn, maSP, soLuong);
                    if (!updated) 
                        throw new SQLException("Không thể cập nhật tồn kho!");
                    
                    tongTienThem += thanhTien;
                    countSuccess++;
                    System.out.println("✅ Đã thêm: " + sanPham.getTenSP() + " x " + soLuong + 
                                " = " + FormatUtil.formatVND(thanhTien) + "\n");
                } catch (SQLException e) {
                    System.out.println("⚠️  Lỗi: " + e.getMessage());
                    System.out.println("⚠️  Bỏ qua sản phẩm này.\n");
                }
            }

            // cập nhật tổng tiền cho phiếu nhập
            if (countSuccess > 0) {
                int tongTienMoi = phieuNhap.getTongTien() + tongTienThem;

                boolean updated = NhapHangDAO.capNhatTongTien(conn, maPhieu, tongTienMoi);
                if (!updated) 
                    throw new SQLException("Không thể cập nhật tổng tiền!");
                conn.commit();

                System.out.println("\n╔════════════════════════════════════════════════════╗");
                System.out.println("║         CẬP NHẬT PHIẾU NHẬP THÀNH CÔNG           ║");
                System.out.println("╚════════════════════════════════════════════════════╝");
                System.out.println("✅ Đã thêm: " + countSuccess + " sản phẩm");
                System.out.println("📊 Tổng tiền cũ: " + FormatUtil.formatVND(phieuNhap.getTongTien()));
                System.out.println("📊 Tổng tiền mới: " + FormatUtil.formatVND(tongTienMoi));
            
            } else {
                conn.rollback();
                System.out.println("⚠️  Không có sản phẩm nào được thêm.");
            }
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("❌ Đã rollback toàn bộ thao tác!");
                } catch (SQLException ex) {
                    System.err.println("❌Lỗi rollback: " + ex.getMessage());
                }
            }
            System.err.println("❌ Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("❌ Lỗi đóng connection: " + e.getMessage());
                }
            }
        }
    }

    public static void timTheoMaPhieu(Scanner scanner) {

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║          TÌM CHI TIẾT PHIẾU NHẬP                 ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        System.out.print("\n→ Nhập mã phiếu nhập (hoặc '0' để hủy): ");
        String maPhieu = scanner.nextLine().trim();

        if ("0".equals(maPhieu)) {
            System.out.println("⚠️  Đã hủy tìm kiếm.");
            return;
        }

        if (maPhieu.isEmpty()) {
            System.out.println("❌ Mã phiếu nhập không được để trống!");
            return;
        }

        NhapHangDTO phieuNhap = NhapHangDAO.timPhieuNhapTheoMa(maPhieu);
        if (phieuNhap == null) {
            System.out.println("❌ Không tìm thấy phiếu nhập với mã: " + maPhieu);
            return;
        }

        List<ChiTietPhieuNhapDTO> chiTietList = ChiTietPhieuNhapDAO.timChiTietPhieuNhap(maPhieu);

        if (chiTietList.isEmpty()) {
            System.out.println("\n⚠️  Phiếu nhập này chưa có chi tiết nào.");
            System.out.println("💡 Bạn có thể thêm chi tiết vào phiếu nhập bằng chức năng 'Thêm chi tiết'.");
        } else {
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║              THÔNG TIN PHIẾU NHẬP                ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.println("Mã phiếu: " + phieuNhap.getMaPhieu());
            System.out.println("Nhà cung cấp: " + phieuNhap.getMaNCC());
            System.out.println("Nhân viên: " + phieuNhap.getMaNV());
            System.out.println("Ngày lập: " + phieuNhap.getNgayLapPhieu().format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            System.out.println("Tổng tiền: " + FormatUtil.formatVND(phieuNhap.getTongTien()));
            
            System.out.println("\n📦 CHI TIẾT PHIẾU NHẬP:");
            inBangChiTiet(chiTietList);

            int tongSoLuong = chiTietList.stream().mapToInt(ChiTietPhieuNhapDTO::getSoLuong).sum();
            System.out.println("\n📊 Tổng số mặt hàng: " + chiTietList.size());
            System.out.println("📊 Tổng số lượng: " + String.format("%,d", tongSoLuong));
            System.out.println("📊 Tổng tiền: " + FormatUtil.formatVND(phieuNhap.getTongTien()));
        }
    }

    public static void xemDanhSachChiTietPhieuNhap() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║            DANH SÁCH CHI TIẾT PHIẾU NHẬP           ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        List<ChiTietPhieuNhapDTO> chiTietList = ChiTietPhieuNhapDAO.getAllChiTietPhieuNhap();

        if (chiTietList.isEmpty()) {
            System.out.println("\n⚠️Không có chi tiết phiếu nhập nào trong hệ thống.");
            return;
        }

        System.out.println("Danh sách tất cả chi tiết phiếu nhập:");
        System.out.println("==================================================================================");
        System.out.printf("| %-10s | %-10s | %-20s | %-10s | %-8s | %-10s | %-10s |\n",
                "Mã phiếu", "Mã Hàng", "Tên SP", "Đơn vị", "SL", "Giá nhập", "Thành tiền");
        System.out.println("==================================================================================");

        int tongSoLuong = 0;
        int tongThanhTien = 0;

        for (ChiTietPhieuNhapDTO ct : chiTietList) {
            System.out.printf("| %-10s | %-10s | %-20s | %-10s | %-8d | %-10d | %-10d |\n",
                    ct.getMaPhieu(), ct.getMaHang(), ct.getTenSP(), ct.getDonViTinh(),
                    ct.getSoLuong(), ct.getGiaNhap(), ct.getThanhTien());
            tongSoLuong += ct.getSoLuong();
            tongThanhTien += ct.getThanhTien();
        }

        System.out.println("==================================================================================");
        System.out.printf("Tổng số lượng SP: %d | Tổng giá trị: %,d VNĐ\n", tongSoLuong, tongThanhTien);
    }

    public static void inBangChiTiet(List<ChiTietPhieuNhapDTO> danhSach) {
        if (danhSach == null || danhSach.isEmpty()) {
            System.out.println("⚠️  Không có dữ liệu để hiển thị.");
            return;
        }
        
        System.out.println("\n┌─────┬────────────┬──────────┬──────────────────────────┬──────────┬─────────────┬─────────────┐");
        System.out.printf("│ %-3s │ %-10s │ %-8s │ %-24s │ %-8s │ %-11s │ %-11s │%n",
                "STT", "Mã Phiếu", "Mã Hàng", "Tên sản phẩm", "Số lượng", "Giá nhập", "Thành tiền");
        System.out.println("├─────┼────────────┼──────────┼──────────────────────────┼──────────┼─────────────┼─────────────┤");

        int stt = 1;
        long tongTien = 0;

        for (ChiTietPhieuNhapDTO ct : danhSach) {
            System.out.printf("│ %-3d │ %-10s │ %-8s │ %-24s │ %8s │ %11s │ %11s │%n",
                    stt++,
                    ct.getMaPhieu(),
                    ct.getMaHang(),
                    truncate(ct.getTenSP(), 24),
                    String.format("%,d", ct.getSoLuong()),
                    FormatUtil.formatVND(ct.getGiaNhap()),
                    FormatUtil.formatVND(ct.getThanhTien()));
            
            tongTien += ct.getThanhTien();
        }

        System.out.println("├─────┴────────────┴──────────┴──────────────────────────┴──────────┴─────────────┼─────────────┤");
        System.out.printf("│ %-30s │ %11s │%n", "TỔNG CỘNG", FormatUtil.formatVND(tongTien));
        System.out.println("└───────────────────────────────────────────────────────────────────────────────────┴─────────────┘");
    }

    public static void thongKeSanPhamNhap(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            try {
                System.out.print("\nNhập ngày bắt đầu (dd/MM/yyyy): ");
                String from = scanner.nextLine().trim();

                System.out.print("Nhập ngày kết thúc (dd/MM/yyyy): ");
                String to = scanner.nextLine().trim();

                LocalDate fromDate = LocalDate.parse(from, formatter);
                LocalDate toDate = LocalDate.parse(to, formatter);

                if (fromDate.isAfter(toDate)) {
                    System.out.println("⚠️  Ngày bắt đầu phải trước ngày kết thúc, vui lòng nhập lại.");
                    continue;
                }

                System.out.print("Nhập số lượng sản phẩm muốn xem (TOP): ");
                int limit;
                try {
                    limit = Integer.parseInt(scanner.nextLine().trim());
                    if (limit <= 0) {
                        System.out.println("⚠️  Số lượng phải lớn hơn 0, vui lòng nhập lại.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠️  Số lượng không hợp lệ, vui lòng nhập lại.");
                    continue;
                }

                List<Map<String, Object>> results = ChiTietPhieuNhapDAO.thongKeSanPhamNhapNhieuNhat(fromDate, toDate, limit);

                System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║          TOP " + limit + " SẢN PHẨM NHẬP NHIỀU NHẤT (" + from + " - " + to + ")          ║");
                System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");

                if (results.isEmpty()) {
                    System.out.println("❌ Không có dữ liệu nhập hàng trong khoảng thời gian này.");
                    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                    System.out.print("✅ Bạn có muốn thống kê tiếp không? (y/n): ");
                    String choice = scanner.nextLine().trim().toLowerCase();
                    if (!choice.equals("y")) break;
                    continue;
                }

                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                System.out.printf("%-5s | %-10s | %-25s | %-12s | %-10s | %-15s%n",
                        "Top", "Mã SP", "Tên sản phẩm", "Tổng SL nhập", "Số lần", "Tổng giá trị");
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

                int top = 1;
                int tongSoLuong = 0;
                long tongGiaTri = 0;

                for (Map<String, Object> row : results) {
                    tongSoLuong += (int) row.get("TongSoLuongNhap");
                    tongGiaTri += (long) row.get("TongGiaTriNhap");

                    String tenSP = (String) row.get("TenSP");
                    if (tenSP.length() > 25) tenSP = tenSP.substring(0, 22) + "...";

                    System.out.printf("%-5d | %-10s | %-25s | %-12d | %-10d | %-15s%n",
                            top++,
                            row.get("MaSP"),
                            tenSP,
                            (int) row.get("TongSoLuongNhap"),
                            (int) row.get("SoLanNhap"),
                            FormatUtil.formatVND((long) row.get("TongGiaTriNhap")));
                }

                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                System.out.println("\n📊 THỐNG KÊ KHOẢNG THỜI GIAN:");
                System.out.println("  • Từ ngày: " + from);
                System.out.println("  • Đến ngày: " + to);
                System.out.println("  • Tổng số lượng nhập (TOP " + limit + "): " + tongSoLuong);
                System.out.println("  • Tổng giá trị nhập: " + FormatUtil.formatVND(tongGiaTri));
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

                System.out.print("✅ Bạn có muốn thống kê tiếp không? (y/n): ");
                String choice = scanner.nextLine().trim().toLowerCase();
                if (!choice.equals("y")) break;

            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("⚠️  Định dạng ngày không hợp lệ! Vui lòng nhập theo dd/MM/yyyy");
            }
        }
    }

    private static String truncate(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
}