package main;

import dao.TaiKhoanDAO;
import dao.NhanVienDAO;
import dto.TaiKhoanDTO;
import dto.NhanVienDTO;
import java.util.Scanner;
import view.QuanLyKhachHang;
import view.QuanLyNhaCungCap;
import view.QuanLyNhanVien;
import view.QuanLyNhapHang;
import view.QuanLySanPham;
import view.QuanLyHoaDon;
import view.QuanLyTaiKhoan;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static TaiKhoanDTO CURRENT_ACCOUNT;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n");
            System.out.println("╔══════════════════════════════════════════════════════════╗");
            System.out.println("║                                                          ║");
            System.out.println("║            Chào mừng đến với hệ thống quản lý            ║");
            System.out.println("║                                                          ║");
            System.out.println("║                     Cửa hàng tiện lợi                    ║");
            System.out.println("║                                                          ║");
            System.out.println("║                                                          ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝");
            System.out.println("                                                          ");
            System.out.println("║               VUI LÒNG ĐĂNG NHẬP HỆ THỐNG                ║");
            System.out.println("                                                          ");
            System.out.print("║  Tên đăng nhập: ");
            String username = scanner.nextLine();
            System.out.println("                                                          ");
            System.out.print("║  Mật khẩu     : ");
            String password = scanner.nextLine();
            System.out.println("                                                          ");
            System.out.println("═══════════════════════════════════════════════════════════");

            /*
             * System.out.print("\nVui lòng chờ 1 lát , hệ thống đang xác thực");
             * for (int i = 0; i < 3; i++) {
             * try {
             * Thread.sleep(1000);
             * System.out.print(".");
             * } catch (InterruptedException e) {
             * e.printStackTrace();
             * }
             * }
             * System.out.println("\n");
             */

            // Xác thực - Kiểm tra username và password
            TaiKhoanDTO taiKhoan = TaiKhoanDAO.kiemTraTaiKhoan(username, password);
            if (taiKhoan != null) {
                // Kiểm tra trạng thái nhân viên
                NhanVienDTO nvLogin = NhanVienDAO.timNhanVienTheoMa(taiKhoan.getMaNV());
                if (nvLogin == null) {
                    System.out.println("❌ Không tìm thấy thông tin nhân viên cho tài khoản này.");
                    continue;
                }
                if ("inactive".equalsIgnoreCase(nvLogin.getTrangThai())
                        || "inactive".equalsIgnoreCase(taiKhoan.getStatus())) {
                    System.out.println(
                            "❌ Tài khoản của bạn đã bị vô hiệu hóa. Vui lòng liên hệ với người quản trị để biết thêm thông tin.");
                    continue;
                }

                CURRENT_ACCOUNT = taiKhoan;
                // Kiểm tra mật khẩu mặc định
                // Phân luồng
                if (taiKhoan.isDefaultPassword()) {
                    // isDefaultPassword == true - Bắt buộc đổi mật khẩu
                    if (forceChangePasswordLoop(taiKhoan.getUsername(), taiKhoan.getMaNV())) {
                        System.out.println("✅ Đổi mật khẩu thành công! Bạn có thể tiếp tục sử dụng hệ thống.");
                    } else {
                        System.out.println("❌ Không thể đổi mật khẩu. Vui lòng thử lại sau.");
                        continue;
                    }
                } else {
                    // isDefaultPassword == false
                    System.out.println("✅ Đăng nhập thành công!");
                    System.out.println("✓ Mật khẩu đã được đổi - Bảo mật tốt!");
                }

                // Hiển thị Menu dựa trên vai tròanan
                if ("Admin".equals(taiKhoan.getRole())) {
                    menuAdmin();
                } else if ("NhanVien".equals(taiKhoan.getRole())) {
                    menuNhanVien(taiKhoan.getfullName());
                } else {
                    System.out.println("❌ Vai trò không hợp lệ: " + taiKhoan.getRole());
                }
                break;
            } else {
                // thất bại - Xác thực không thành công
                // Thông báo lỗi đã được xử lý
                System.out.println("Đăng nhập thất bại, vui lòng thử lại!");
                clearScreen();
            }
        }

    }

    public static void clearScreen() {
        for (int i = 0; i < 10; i++) {
            System.out.println("");
        }
    }

    public static void menuAdmin() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n" + "═".repeat(63));

            System.out.println("🏢              QUẢN LÝ CỬA HÀNG - MENU CHÍNH               🏢");

            System.out.println("═".repeat(63));
            System.out.println("┌─ CHỨC NĂNG CHÍNH ──────────────────────────────────────────┐");
            System.out.println("│                                                            │");
            System.out.println("│  [1] ➜ Quản lý sản phẩm                                    │");
            System.out.println("│  [2] ➜ Quản lý nhân viên                                   │");
            System.out.println("│  [3] ➜ Quản lý khách hàng                                  │");
            System.out.println("│  [4] ➜ Quản lý nhà cung cấp                                │");
            System.out.println("│  [5] ➜ Quản lý nhập hàng                                   │");
            System.out.println("│  [6] ➜ Quản lý hóa đơn                                     │");
            System.out.println("│                                                            │");
            System.out.println("├─ BÁO CÁO & THỐNG KÊ ───────────────────────────────────────┤");
            System.out.println("│                                                            │");
            System.out.println("│  [7] ➜ Xem báo cáo / thống kê                              │");
            System.out.println("│                                                            │");
            System.out.println("├─ HỆ THỐNG ─────────────────────────────────────────────────┤");
            System.out.println("│                                                            │");
            System.out.println("│  [8] ➜ Quản lý tài khoản                                   │");
            System.out.println("│  [0] ✗ Đăng xuất                                           │");
            System.out.println("│                                                            │");
            System.out.println("└────────────────────────────────────────────────────────────┘");

            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            int choice = -1;

            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 0 && choice <= 8) {
                        break;
                    }
                    System.out.println("Vui lòng nhập số trong khoảng 0–8.");
                    System.out.print("\nNhập lựa chọn của bạn: ");
                } else {
                    System.out.println("Vui lòng nhập số hợp lệ.");
                    scanner.next();
                    System.out.print("\nNhập lựa chọn của bạn: ");
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
                    QuanLyHoaDon qlhd = new QuanLyHoaDon();
                    qlhd.menuQuanLyHoaDon();
                    break;
                case 7:
                    // xem báo cáo / thống kê
                    break;
                case 8:
                    QuanLyTaiKhoan qltk = new QuanLyTaiKhoan();
                    qltk.menuQuanLyTaiKhoan();
                    break;

                case 0:
                    System.out.println("Đăng xuất thành công!");

                    System.out.println("╔═══════════════════════════════════════════════════╗");
                    System.out.println("║  CẢM ƠN BẠN ĐÃ SỬ DỤNG PHẦN MỀM, CHÀO TẠM BIỆT !  ║");
                    System.out.println("╚═══════════════════════════════════════════════════╝");
                    return; // quay ra form login
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

            System.out.println("\n    ─────────────────────────────────────────────────────────────");
            System.out.println("    │" + paddedTitle.toUpperCase() + "|");
            System.out.println("    ┌────────────────────────────────────────────────────────────┐");
            System.out.println("    │                                                            │");
            System.out.println("    ├─ CHỨC NĂNG CHÍNH ──────────────────────────────────────────┤");
            System.out.println("    │                                                            │");
            System.out.println("    │  [1] ➜ Bán hàng                                            │");
            System.out.println("    │  [2] ➜ Xem sản phẩm                                        │");
            System.out.println("    │  [3] ➜ Quản lý khách hàng (cơ bản)                         │");
            System.out.println("    │  [4] ➜ Xem hóa đơn đã lập                                  │");
            System.out.println("    │                                                            │");
            System.out.println("    ├─ HỆ THỐNG ─────────────────────────────────────────────────┤");
            System.out.println("    │                                                            │");
            System.out.println("    │  [5] ➜ Quản lý tài khoản                                   │");
            System.out.println("    │  [0] ✗ Đăng xuất                                           │");
            System.out.println("    │                                                            │");
            System.out.println("    └────────────────────────────────────────────────────────────┘");

            System.out.print("\nNhập lựa chọn của bạn: ");

            int choice = -1;

            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 0 && choice <= 6) {
                        break;
                    }
                    System.out.println("Vui lòng nhập số trong khoảng 0–6.");
                    System.out.print("\nNhập lựa chọn của bạn: ");
                } else {
                    System.out.println("Vui lòng nhập số hợp lệ.");
                    scanner.next(); // bỏ token không phải số
                    System.out.print("\nNhập lựa chọn của bạn: ");
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
                case 5:
                    // quản lý tài khoản cá nhân
                    QuanLyTaiKhoan qltk = new QuanLyTaiKhoan();
                    qltk.menuQuanLyTaiKhoanNhanVien();
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

    public static boolean forceChangePasswordLoop(String username, String maNV) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n" + "═".repeat(60));
            System.out.println("🔒 BẮT BUỘC ĐỔI MẬT KHẨU");
            System.out.println("═".repeat(60));
            System.out.println("⚠️  Bạn đang sử dụng mật khẩu mặc định!");
            System.out.println("   Để bảo mật tài khoản, bạn PHẢI đổi mật khẩu ngay bây giờ.");
            System.out.println("   Mật khẩu mặc định hiện tại: " + maNV);
            System.out.println("\n📋 YÊU CẦU MẬT KHẨU MỚI:");
            System.out.println("   • Ít nhất 3 ký tự");
            System.out.println("   • Không được trùng với mật khẩu mặc định (" + maNV + ")");
            System.out.println("   • Nên chứa chữ và số để tăng bảo mật");
            System.out.println("═".repeat(60));

            System.out.print("🔑 Nhập mật khẩu mới: ");
            String newPassword = scanner.nextLine();

            System.out.print("🔑 Xác nhận mật khẩu mới: ");
            String confirmPassword = scanner.nextLine();

            // Kiểm tra xác nhận mật khẩu
            if (!newPassword.equals(confirmPassword)) {
                System.out.println("❌ Mật khẩu xác nhận không khớp! Vui lòng thử lại.");
                continue;
            }

            // Thử đổi mật khẩu
            if (dao.TaiKhoanDAO.batBuocDoiMatKhau(username, newPassword, maNV)) {
                System.out.println("\n🎉 CHÚC MỪNG! Bạn đã đổi mật khẩu thành công!");
                System.out.println("   Tài khoản của bạn giờ đây đã an toàn hơn.");
                return true;
            } else {
                System.out.println("\n❌ Đổi mật khẩu thất bại! Vui lòng thử lại.");
                System.out.print("   Nhấn Enter để tiếp tục...");
                scanner.nextLine();
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

        return new String[] { icon, greeting, dateTime };
    }
}
