import dao.SanPhamDAO;
import dao.TaiKhoanDAO;
import dto.TaiKhoanDTO;
import java.util.Scanner;
import view.QuanLyKhachHang;
import view.QuanLyNhaCungCap;
import view.QuanLyNhanVien;
import view.QuanLyNhapHang;
import view.QuanLySanPham;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n");
            System.out.println("╔══════════════════════════════════════════════════════════╗");
            System.out.println("║                                                          ║");
            System.out.println("║            Chào mừng đến với hệ thống quản lý            ║");
            System.out.println("║                                                          ║");
            System.out.println("║                    Cửa hàng tiện lợi                     ║");
            System.out.println("║                                                          ║");
            System.out.println("║                                                          ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝");
            System.out.println("\n║               VUI LÒNG ĐĂNG NHẬP HỆ THỐNG                ║");
            System.out.print("\n║  Tên đăng nhập: ");
            String username = scanner.nextLine();
            System.out.print("\n║  Mật khẩu     : ");
            String password = scanner.nextLine();

            System.out.println("\n═══════════════════════════════════════════════════════════");


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
                System.out.println("✓ Đăng nhập thành công! Xin chào "+taiKhoan.getRole()+" " + taiKhoan.getUsername() + "!");
                String[] greetingInfo = getGreeting();
                System.out.println(greetingInfo[0] + " " + greetingInfo[1] + ", hiện tại là " + greetingInfo[2]);

                // Tự động inactive sản phẩm hết hạn khi vào chương trình
                SanPhamDAO.capnhatTrangThaiHetHan();

                if ("Admin".equals(taiKhoan.getRole())) {
                    menuAdmin();
                } else if ("NhanVien".equals(taiKhoan.getRole())) {
                    menuNhanVien(taiKhoan.getfullName());
                }
                break;
            } else {
                System.out.println("✗ Sai tài khoản hoặc mật khẩu, vui lòng thử lại!");
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

    public static void menuNhanVien(String fullName) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
        // Calculate padding for employee name
        int totalWidth = 60; // Total width of the box
        String title = "QUẢN LÝ CỬA HÀNG - NHÂN VIÊN " + fullName;
        int padding = (totalWidth - title.length()) / 2;
        String paddedTitle = String.format("%" + padding + "s%s%" + padding + "s", "", title, "");

        System.out.println("\n    ──────────────────────────────────────────────────────────────");
        System.out.println("    ║" + paddedTitle + " ");
        System.out.println("    ┌────────────────────────────────────────────────────────────┐");
        System.out.println("    │                                                            │");
        System.out.println("    │  [1] ➜ Bán hàng                                            │");
        System.out.println("    │  [2] ➜ Xem sản phẩm                                        │");
        System.out.println("    │  [3] ➜ Quản lý khách hàng (cơ bản)                         │");
        System.out.println("    │  [4] ➜ Xem hóa đơn đã lập                                  │");
        System.out.println("    │                                                            │");
        System.out.println("    │  [0] ➜ Đăng xuất                                           │");
        System.out.println("    │                                                            │");
        System.out.println("    └────────────────────────────────────────────────────────────┘");
        
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

    public static String[] getGreeting() {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        String greeting;
        String icon;
        
        // Determine greeting based on time
        if (hour >= 0 && hour < 11) {
            greeting = "Chào buổi sáng";
            icon = "🌅";
        } else if (hour >= 11 && hour < 13) {
            greeting = "Chào buổi trưa";
            icon = "☀️";
        } else if (hour >= 13 && hour < 18) {
            greeting = "Chào buổi chiều";
            icon = "🌤️";
        } else {
            greeting = "Chào buổi tối";
            icon = "🌙";
        }

        // Format current date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        String dateTime = now.format(formatter);

        return new String[]{icon, greeting, dateTime};
    }
}