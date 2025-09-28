
import java.util.Scanner;

import dao.TaiKhoanDAO;
import dto.TaiKhoanDTO;
import view.QuanLyKhachHang;
import view.QuanLyNhanVien;
import view.QuanLySanPham;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n");
            System.out.println("╔══════════════════════════════════════════════════════════╗");
            System.out.println("║                                                          ║");
            System.out.println("║                    ĐĂNG NHẬP HỆ THỐNG                    ║");
            System.out.println("║                                                          ║");
            System.out.println("║            Chào mừng đến với hệ thống quản lý!           ║");
            System.out.println("║                                                          ║");
            System.out.println("╠══════════════════════════════════════════════════════════╣");
            System.out.print("║  Tên đăng nhập: ");
            String username = scanner.nextLine();
            System.out.print("║  Mật khẩu     : ");
            String password = scanner.nextLine();
            System.out.println("╚══════════════════════════════════════════════════════════╝");


            System.out.print("\nVui lòng chờ 1 lát , hệ thống đang xác thực");
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(1000);
                    System.out.print(".");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("\n");

            TaiKhoanDTO taiKhoan = TaiKhoanDAO.checkAccount(username, password);
            
            if (taiKhoan != null) {
                System.out.println("\n    ✓ Đăng nhập thành công!");
                if ("Admin".equals(taiKhoan.getRole())) {
                    menuAdmin();
                } else if ("NhanVien".equals(taiKhoan.getRole())) {
                    menuNhanVien();
                }
                break;
            } else {
                System.out.println("\n    ✗ Sai tài khoản hoặc mật khẩu, vui lòng thử lại!");
                clearScreen();
            }
        }
        
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void menuAdmin() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n" + "═".repeat(65));
            System.out.println("🏢              QUẢN LÝ CỬA HÀNG - MENU CHÍNH              🏢");
            System.out.println("═".repeat(65));
            System.out.println("┌─ CHỨC NĂNG CHÍNH ──────────────────────────────────────────┐");
            System.out.println("│                                                            │");
            System.out.println("│  [1] ➜ Quản lý sản phẩm                                    │");
            System.out.println("│  [2] ➜ Quản lý nhân viên                                   │");
            System.out.println("│  [3] ➜ Quản lý khách hàng                                  │");
            System.out.println("│  [4] ➜ Quản lý nhà cung cấp                                │");
            System.out.println("│  [5] ➜ Quản lý nhập hàng                                   │");
            System.out.println("│                                                            │");
            System.out.println("├─ BÁO CÁO & THỐNG KÊ ───────────────────────────────────────┤");
            System.out.println("│                                                            │");
            System.out.println("│  [6] ➜ Xem báo cáo / thống kê                              │");
            System.out.println("│                                                            │");
            System.out.println("├─ HỆ THỐNG ─────────────────────────────────────────────────┤");
            System.out.println("│                                                            │");
            System.out.println("│  [0] ✗ Đăng xuất                                           │");
            System.out.println("│                                                            │");
            System.out.println("└────────────────────────────────────────────────────────────┘");

            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            int choice = -1;

            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 0 && choice <= 6) {
                        break;
                    }
                    System.out.println("Vui lòng nhập số trong khoảng 0–6.");
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                } else {
                    System.out.println("Vui lòng nhập số hợp lệ.");
                    scanner.next(); // bỏ token không phải số
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                }
            }

            switch (choice) {
                case 1:
                    QuanLySanPham qlsp = new QuanLySanPham();
                    qlsp.menuQuanLySanPham();
                    break;
                case 2:
                    QuanLyNhanVien qlnv = new QuanLyNhanVien();
                    qlnv.menuQuanLyNhanVien();
                    break;
                case 3:
                    QuanLyKhachHang qlkh = new QuanLyKhachHang();
                    qlkh.menuQuanLyKhachHang();
                    break;
                case 4:
                    QuanLyNhaCungCap qlncc = new QuanLyNhaCungCap();
                    qlncc.menuQuanLyNhaCungCap();
                    break;
                case 5:
                    QuanLyNhapHang qlnh = new QuanLyNhapHang();
                    qlnh.menuQuanLyNhapHang();
                    break;
                case 6:
                    // xem báo cáo / thống kê
                    break;
                case 0:
                    System.out.println("Đăng xuất thành công!");
                    System.out.println("╔════════════════════════════════════════════════════════════════════════════╗");
                    System.out.println("║                CẢM ƠN BẠN ĐÃ SỬ DỤNG PHẦN MỀM, HẸN GẶP LẠI!                ║");
                    System.out.println("╚════════════════════════════════════════════════════════════════════════════╝");
                    return;  // quay ra form login
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }
        }
    }

    public static void menuNhanVien() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n    ┌────────────────────────────────────────────┐");
            System.out.println("    │        QUẢN LÝ CỬA HÀNG - NHÂN VIÊN        │");
            System.out.println("    ├────────────────────────────────────────────┤");
            System.out.println("    │                                            │");
            System.out.println("    │  [1] ➜ Bán hàng                            │");
            System.out.println("    │  [2] ➜ Xem sản phẩm                        │");
            System.out.println("    │  [3] ➜ Quản lý khách hàng (cơ bản)         │");
            System.out.println("    │  [4] ➜ Xem hóa đơn đã lập                  │");
            System.out.println("    │                                            │");
            System.out.println("    │  [0] ➜ Đăng xuất                           │");
            System.out.println("    │                                            │");
            System.out.println("    └────────────────────────────────────────────┘");

            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            int choice = -1;

            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 0 && choice <= 6) {
                        break;
                    }
                    System.out.println("Vui lòng nhập số trong khoảng 0–6.");
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                } else {
                    System.out.println("Vui lòng nhập số hợp lệ.");
                    scanner.next(); // bỏ token không phải số
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                }
            }
        
            switch (choice) {
                case 1:
                    // bán hàng
                    break;
                case 2:
                    // xem sản phẩm
                    break;
                case 3:
                    QuanLyKhachHang qlkh = new QuanLyKhachHang();
                    qlkh.menuQuanLyKhachHang();
                    break;
                case 4:
                    // xem hóa đơn đã lập
                    break;
                case 0:
                    System.out.println("Đăng xuất thành công!");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }
        }
    }
}