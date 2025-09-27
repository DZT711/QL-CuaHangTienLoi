package view;

import java.util.Scanner;
import dao.HoaDonDAO;
import dto.HoaDonDTO;

public class QuanLyHoaDon {
    public void menuQuanLyHoaDon() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n████████████████████████████████████████████████████████████████████████████████");
            System.out.println("██                                                                            ██");
            System.out.println("██                         HỆ THỐNG QUẢN LÝ HÓA ĐƠN                           ██");
            System.out.println("██                                                                            ██");
            System.out.println("████████████████████████████████████████████████████████████████████████████████");
            System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓ MENU CHỨC NĂNG ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
            System.out.println("▒ [1] ➜ Tạo hóa đơn mới                                                        ▒");
            System.out.println("▒ [2] ➜ Xem chi tiết hóa đơn                                                   ▒");
            System.out.println("▒ [3] ➜ Chỉnh sửa hóa đơn                                                      ▒");
            System.out.println("▒ [4] ➜ Xóa hóa đơn                                                            ▒");
            System.out.println("▒ [5] ➜ Tìm kiếm hóa đơn                                                       ▒");
            System.out.println("▒ [6] ➜ Thống kê doanh thu                                                     ▒");
            System.out.println("▒ [7] ➜ Xuất báo cáo                                                           ▒");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ");
            System.out.println("░ [0] ✗ Quay lại menu chính                                                    ░");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ");
            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            // Input validation
            while (!scanner.hasNextInt()) {
                System.out.println("⚠️ Vui lòng nhập số!");
                scanner.next();
                System.out.print("\n💡 Nhập lựa chọn của bạn: ");
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 0) {
                System.out.println("\n⮐ Đang quay lại menu chính...");
                break;
            }

            try {
                switch (choice) {
                    case 1: taoHoaDon(); break;
                    case 2: xemChiTiet(); break;
                    case 3: suaHoaDon(); break;
                    case 4: xoaHoaDon(); break;
                    case 5: timKiem(); break;
                    case 6: thongKeDoanhThu(); break;
                    case 7: xuatBaoCao(); break;
                    default:
                        System.out.println("⚠️ Lựa chọn không hợp lệ!");
                        break;
                }
            } catch (Exception e) {
                System.out.println("❌ Lỗi: " + e.getMessage());
            }
        }
    }

    private void taoHoaDon() { }
    private void xemChiTiet() { }
    private void suaHoaDon() { }
    private void xoaHoaDon() { }
    private void timKiem() { }
    private void thongKeDoanhThu() { }
    private void xuatBaoCao() { }
}