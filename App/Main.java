
import java.util.Scanner;

import Login.login;
import view.QuanLyHoaDon;
import view.QuanLyKhachHang;
import view.QuanLyNhanVien;
import view.QuanLySanPham;

public class Main {
    public void mainMenu() {
        
        Scanner sc = new Scanner(System.in);
        int choice;
        
        // Clear screen
        System.out.print("\033[H\033[2J");
        System.out.flush();

        while (true) {
            System.out.println("\n████████████████████████████████████████████████████████████████████████████████");
            System.out.println("██                                                                            ██");
            System.out.println("██                         HỆ THỐNG QUẢN LÝ CỬA HÀNG                          ██");
            System.out.println("██                                                                            ██");
            System.out.println("████████████████████████████████████████████████████████████████████████████████");
            System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓ MENU CHỨC NĂNG ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
            System.out.println("▒ [1] ➜ Quản lý sản phẩm                                                       ▒");
            System.out.println("▒ [2] ➜ Quản lý khách hàng                                                     ▒");
            System.out.println("▒ [3] ➜ Quản lý nhân viên                                                      ▒");
            System.out.println("▒ [4] ➜ Quản lý hóa đơn                                                        ▒");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
            System.out.println("░ [0] ✗ THOÁT CHƯƠNG TRÌNH                                                     ░");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            // Input validation
            while (!sc.hasNextInt()) {
                System.out.println("Vui lòng nhập số hợp lệ.");
                sc.next(); // clear invalid input
                System.out.print("\n💡 Nhập lựa chọn của bạn: ");
            }
            
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            if (choice == 0) {
                System.out.println("╔════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║                CẢM ƠN BẠN ĐÃ SỬ DỤNG PHẦN MỀM, HẸN GẶP LẠI!                ║");
                System.out.println("╚════════════════════════════════════════════════════════════════════════════╝");
                break;
            }

            try {
                switch (choice) {
                    case 1:
                        QuanLySanPham qlsp = new QuanLySanPham();
                        qlsp.menuQuanLySanPham();
                        break;
                    case 2:
                        QuanLyKhachHang qlkh = new QuanLyKhachHang();
                        qlkh.menuQuanLyKhachHang();
                        break;
                    case 3:
                        QuanLyNhanVien qlnv = new QuanLyNhanVien();
                        qlnv.menuQuanLyNhanVien();
                        break;
                    case 4:
                        QuanLyHoaDon qlhd = new QuanLyHoaDon();
                        qlhd.menuQuanLyHoaDon();
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại!");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        }
        sc.close();
    
    }
    public static void main(String[] args) {
        try {
            login loginForm = new login();
            if (loginForm.LoginForm()) {
                // If login is successful, proceed to the main application

                Main app = new Main();
                app.mainMenu();
            }

        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}