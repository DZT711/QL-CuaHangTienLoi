package view;

import java.util.Scanner;
// import dao.NhaCungCapDAO;
import dto.NhaCungCapDTO;

public class QuanLyNhaCungCap {
    public void menuQuanLyNhaCungCap() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n████████████████████████████████████████████████████████████████████████████████");
            System.out.println("██                                                                            ██");
            System.out.println("██                         HỆ THỐNG QUẢN LÝ NHÀ CUNG CẤP                        ██");
            System.out.println("██                                                                            ██");
            System.out.println("████████████████████████████████████████████████████████████████████████████████");
            System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓ MENU CHỨC NĂNG ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
            System.out.println("▒ [1] ➜ Thêm nhà cung cấp                                                      ▒");
            System.out.println("▒ [2] ➜ Sửa thông tin nhà cung cấp                                             ▒");
            System.out.println("▒ [3] ➜ Xóa nhà cung cấp                                                       ▒");
            System.out.println("▒ [4] ➜ Tìm kiếm nhà cung cấp                                                  ▒");
            System.out.println("▒ [5] ➜ Xuất danh sách nhà cung cấp                                             ▒");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ");
            System.out.println("░ [0] ✗ Quay lại menu chính                                                    ░");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ");
            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            int choice = -1;

            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice > 0 && choice <= 5) {
                        break;
                    }
                    System.out.println("Vui lòng nhập số trong khoảng 0–5.");
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                } else {
                    System.out.println("Vui lòng nhập số hợp lệ.");
                    scanner.next();
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                }
            }
            
            switch (choice) {
                case 1:
                    // themNhaCungCap();
                    break;
                case 2:
                    // suaNhaCungCap();
                    break;
                case 3:
                    // xoaNhaCungCap();
                    break;
                case 4:
                    // timKiemNhaCungCap();
                    break;
                case 5:
                    // xuatDanhSachNhaCungCap();
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

    public void themNhaCungCap() { }
    public void suaNhaCungCap() { }
    public void xoaNhaCungCap() { }
    public void timKiemNhaCungCap() { }
    public void xuatDanhSachNhaCungCap() { }
    
}

