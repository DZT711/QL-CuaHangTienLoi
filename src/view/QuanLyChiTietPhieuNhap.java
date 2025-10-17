package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.sql.Connection;
import java.util.List;
import dao.ChiTietPhieuNhapDAO;
import dao.NhapHangDAO;
import dto.ChiTietPhieuNhapDTO;
import dto.NhapHangDTO;
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
        Connection conn = null;
        try {
            System.out.print("\nNhập mã phiếu nhập: ");
            String maPhieu = scanner.nextLine().trim();

            NhapHangDTO phieuNhap = NhapHangDAO.timPhieuNhapTheoMa(maPhieu);
            if (phieuNhap == null) {
                System.out.println("Không tìm thấy phiếu nhập với mã: " + maPhieu);
                return;
            }

            conn = util.JDBCUtil.getConnection();

            int tongTienThem = 0;
            int countSuccess = 0;

            while (true) {
                System.out.print("\nNhập mã sản phẩm (nhập '0' để kết thúc): ");
                String maSP = scanner.nextLine().trim();
                if (maSP.equals("0")) break;

                System.out.print("Nhập số lượng: ");
                int soLuong;
                try {
                    soLuong = Integer.parseInt(scanner.nextLine().trim());
                    if (soLuong <= 0) {
                        System.out.println("Số lượng phải lớn hơn 0.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Số lượng không hợp lệ.");
                    continue;
                }

                System.out.print("Nhập giá nhập: ");
                int giaNhap;
                try {
                    giaNhap = Integer.parseInt(scanner.nextLine().trim());
                    if (giaNhap <= 0) {
                        System.out.println("Giá nhập phải lớn hơn 0.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Giá nhập không hợp lệ.");
                    continue;
                }

                int thanhTien = soLuong * giaNhap;

                // Thêm chi tiết (DAO tự kiểm tra trùng mã + giá)
                ChiTietPhieuNhapDTO chiTiet = new ChiTietPhieuNhapDTO(maPhieu, maSP, null, null, soLuong, giaNhap, thanhTien);
                boolean added = ChiTietPhieuNhapDAO.themChiTietPhieuNhap(conn, chiTiet);

                if (added) {
                    tongTienThem += thanhTien;
                    countSuccess++;
                }
            }

            if (countSuccess > 0) {
                int tongTienMoi = phieuNhap.getTongTien() + tongTienThem;
                phieuNhap.setTongTien(tongTienMoi);

                NhapHangDAO.suaPhieuNhap(phieuNhap, maPhieu);

                System.out.println("\nĐã thêm " + countSuccess + " sản phẩm vào phiếu nhập " + maPhieu + ".");

            } else {
                System.out.println("Không có sản phẩm nào được thêm.");
            }

        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    System.err.println("Lỗi đóng connection: " + e.getMessage());
                }
            }
        }
    }

    public static void timTheoMaPhieu(Scanner scanner) {
        System.out.print("\nNhập mã phiếu nhập: ");
        String maPhieu = scanner.nextLine().trim();

        List<ChiTietPhieuNhapDTO> chiTietList = ChiTietPhieuNhapDAO.timChiTietPhieuNhap(maPhieu);

        if (chiTietList.isEmpty()) {
            System.out.println("Không tìm thấy chi tiết phiếu nhập với mã: " + maPhieu);
        } else {
            System.out.println("Kết quả tìm kiếm chi tiết phiếu nhập với mã: " + maPhieu);
            inBangChiTiet(chiTietList);
        }
    }

    public static void xemDanhSachChiTietPhieuNhap() {
        List<ChiTietPhieuNhapDTO> chiTietList = ChiTietPhieuNhapDAO.getAllChiTietPhieuNhap();

        if (chiTietList.isEmpty()) {
            System.out.println("Không có chi tiết phiếu nhập nào trong hệ thống.");
            return;
        }

        System.out.println("Danh sách tất cả chi tiết phiếu nhập:");
        System.out.println("==================================================================================");
        System.out.printf("| %-10s | %-10s | %-20s | %-10s | %-8s | %-10s | %-10s |\n",
                "Mã phiếu", "Mã SP", "Tên SP", "Đơn vị", "SL", "Giá nhập", "Thành tiền");
        System.out.println("==================================================================================");

        int tongSoLuong = 0;
        int tongThanhTien = 0;

        for (ChiTietPhieuNhapDTO ct : chiTietList) {
            System.out.printf("| %-10s | %-10s | %-20s | %-10s | %-8d | %-10d | %-10d |\n",
                    ct.getMaPhieu(), ct.getMaSP(), ct.getTenSP(), ct.getDonViTinh(),
                    ct.getSoLuong(), ct.getGiaNhap(), ct.getThanhTien());
            tongSoLuong += ct.getSoLuong();
            tongThanhTien += ct.getThanhTien();
        }

        System.out.println("==================================================================================");
        System.out.printf("Tổng số lượng SP: %d | Tổng giá trị: %,d VNĐ\n", tongSoLuong, tongThanhTien);
    }

    public static void inBangChiTiet(List<ChiTietPhieuNhapDTO> danhSach) {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.printf("%-5s | %-10s | %-10s | %-20s | %-8s | %-12s | %-12s%n",
                "STT", "Mã Phiếu", "Mã SP", "Tên SP", "Số lượng", "Giá nhập", "Thành tiền");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        int stt = 1;
        long tongTien = 0;

        for (ChiTietPhieuNhapDTO ct : danhSach) {
            System.out.printf("%-5d | %-10s | %-10s | %-20s | %-8d | %-12s | %-12s%n",
                    stt++,
                    ct.getMaPhieu(),
                    ct.getMaSP(),
                    ct.getTenSP(),
                    ct.getSoLuong(),
                    FormatUtil.formatVND(ct.getGiaNhap()),
                    FormatUtil.formatVND(ct.getThanhTien()));
            tongTien += ct.getThanhTien();
        }

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    public static void thongKeSanPhamNhap(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {

            System.out.print("\nNhập ngày bắt đầu: ");
            String from = scanner.nextLine().trim();

            System.out.print("Nhập ngày kết thúc: ");
            String to = scanner.nextLine().trim();

            LocalDate fromDate = LocalDate.parse(from, formatter);
            LocalDate toDate = LocalDate.parse(to, formatter);

            if (fromDate.isAfter(toDate)) {
                System.out.println("Ngày bắt đầu phải trước ngày kết thúc!");
                return;
            }

            System.out.print("Nhập số lượng sản phẩm muốn xem (TOP): ");
            int limit;
            try {
                limit = Integer.parseInt(scanner.nextLine().trim());
                if (limit <= 0) {
                    System.out.println("Số lượng phải lớn hơn 0.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Số lượng không hợp lệ.");
                return;
            }

            List<Map<String, Object>> results = ChiTietPhieuNhapDAO.thongKeSanPhamNhapNhieuNhat(fromDate, toDate, limit);

            if (results.isEmpty()) {
                System.out.println("Không có dữ liệu nhập hàng trong khoảng thời gian này.");
                return;
            }

            // Hiển thị kết quả
            System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║          TOP " + limit + " SẢN PHẨM NHẬP NHIỀU NHẤT (" + from + " - " + to + ")          ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
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
            System.out.println("\n THỐNG KÊ KHOẢNG THỜI GIAN:");
            System.out.println("  • Từ ngày: " + from);
            System.out.println("  • Đến ngày: " + to);
            System.out.println("  • Tổng số lượng nhập (TOP " + limit + "): " + tongSoLuong);
            System.out.println("  • Tổng giá trị nhập: " + FormatUtil.formatVND(tongGiaTri));
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Định dạng ngày không hợp lệ! Vui lòng nhập theo dd/MM/yyyy");
        }
    }
}