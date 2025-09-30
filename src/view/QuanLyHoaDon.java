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
            System.out.println("▒ [2] ➜ Thêm danh sách hóa đơn                                                 ▒");
            System.out.println("▒ [3] ➜ Xóa hóa đơn                                                            ▒");
            System.out.println("▒ [4] ➜ Tìm kiếm hóa đơn                                                       ▒");
            System.out.println("▒ [5] ➜ Thống kê hóa đơn                                                       ▒");
            System.out.println("▒ [6] ➜ Xem danh sách hóa đơn                                                  ▒");
            System.out.println("▒ [7] ➜ Xuất hóa đơn                                                           ▒");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ");
            System.out.println("░ [0] ✗ Quay lại menu chính                                                    ░");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ");
            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            int choice = -1;

            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice > 0 && choice <= 7) {
                        break;
                    }
                    else if (choice == 0) {
                        return;
                    } else {
                    System.out.println("Vui lòng nhập số trong khoảng 0–7.");
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                }  
                } else {
                    System.out.println("Vui lòng nhập số hợp lệ.");
                    scanner.next();
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                }
            }

            switch (choice) {
                case 1:
                    // themHoaDon();
                    break;
                case 2:
                    // themDanhSachHoaDon();
                    break;
                case 3:
                    // xoaHoaDon();
                    break;
                case 4:
                    // timKiemHoaDon();
                    break;
                case 5:
                    // thongKeHoaDon();
                    break;
                case 6:
                    // xemDanhSachHoaDon();
                    break;
                case 7:
                    // xuatHoaDon();
                    break;
                case 0:
                    System.out.println("Thoát chương trình thành công!");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }
        }
    }

    public void themHoaDon() { }
    public void themDanhSachHoaDon() { }
    public void xoaHoaDon() { }
    public void timKiemHoaDon() { }
    public void thongKeHoaDon() { }
    public void xemDanhSachHoaDon() { }
    public void xuatHoaDon() { }
    
}