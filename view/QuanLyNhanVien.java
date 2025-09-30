package view;

import java.util.Scanner;

public class QuanLyNhanVien {
    public void menuQuanLyNhanVien() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n████████████████████████████████████████████████████████████████████████████████");
            System.out.println("██                                                                            ██");
            System.out.println("██                         HỆ THỐNG QUẢN LÝ NHÂN VIÊN                         ██");
            System.out.println("██                                                                            ██");
            System.out.println("████████████████████████████████████████████████████████████████████████████████");
            System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓ MENU CHỨC NĂNG ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
            System.out.println("▒ [1] ➜ Thêm nhân viên mới                                                     ▒");
            System.out.println("▒ [2] ➜ Sửa thông tin nhân viên                                                ▒");
            System.out.println("▒ [3] ➜ Xóa nhân viên                                                          ▒");
            System.out.println("▒ [4] ➜ Tìm kiếm nhân viên                                                     ▒");
            System.out.println("▒ [5] ➜ Thống kê nhân viên                                                     ▒");
            System.out.println("▒ [6] ➜ Xuất danh sách nhân viên                                               ▒");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
            System.out.println("░ [0] ⮐ Quay lại menu chính                                                    ░");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            int choice = -1;

            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice > 0 && choice <= 6) {
                        break;
                    }else if (choice == 0) {
                        return;
                    } else {
                    System.out.println("Vui lòng nhập số trong khoảng 0–6.");
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
                    // themNhanVien();
                    break;
                case 2:
                    // suaNhanVien();
                    break;
                case 3:
                    // xoaNhanVien();
                    break;
                case 4:
                    // timKiemNhanVien();
                    break;
                case 5:
                    // thongKeNhanVien();
                    break;
                case 6:
                    // xemDanhSachNhanVien();
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

    public void themNhanVien() { }
    public void suaNhanVien() { }
    public void xoaNhanVien() { }
    public void timKiemNhanVien() { }
    public void thongKeNhanVien() { }
    public void xemDanhSachNhanVien() { }
    
}