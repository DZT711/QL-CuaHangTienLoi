package view;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import util.FormatUtil;
import dao.TaiKhoanDAO;
import dto.NhanVienDTO;
import dto.TaiKhoanDTO;
import main.Main;

public class QuanLyTaiKhoan {

    private Scanner scanner = new Scanner(System.in);

    // ================= MENU TK ADMIN ================================
    public void menuQuanLyTaiKhoan() {
        while (true) {
            System.out.println("\n" + "═".repeat(60));
            System.out.println("🔐              QUẢN LÝ TÀI KHOẢN ADMIN               🔐");
            System.out.println("═".repeat(60));
            System.out.println("┌─ CHỨC NĂNG CHÍNH ──────────────────────────────────────┐");
            System.out.println("│                                                        │");
            System.out.println("│  [1] ➜ Cập nhật thông tin cá nhân                      │");
            System.out.println("│  [2] ➜ Đổi mật khẩu                                    │");
            System.out.println("│  [3] ➜ Xem thông tin tài khoản                         │");
            System.out.println("│  [4] ➜ Xem danh sách tài khoản                         │");
            System.out.println("│  [5] ➜ Tìm kiếm tài khoản                              │");
            System.out.println("│  [6] ➜ Đặt lại mật khẩu                                │");
            System.out.println("│                                                        │");
            System.out.println("├─ HỆ THỐNG ─────────────────────────────────────────────┤");
            System.out.println("│                                                        │");
            System.out.println("│  [0] ✗ Quay lại menu chính                             │");
            System.out.println("│                                                        │");
            System.out.println("└────────────────────────────────────────────────────────┘");

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
                    scanner.next();
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                }
            }

            switch (choice) {
                case 1:
                    capNhatThongTinCaNhan();
                    break;
                case 2:
                    doiMatKhau();
                    break;
                case 3:
                    xemThongTinTaiKhoan();
                    break;
                case 4:
                    if ("Admin".equals(Main.CURRENT_ACCOUNT.getRole())) {
                        xemDanhSachTaiKhoan();
                    } else {
                        System.out.println("❌ Chỉ Admin mới có quyền xem danh sách tài khoản!");
                    }
                    break;
                case 5:
                    if ("Admin".equals(Main.CURRENT_ACCOUNT.getRole())) {
                        timKiemTaiKhoan();
                    } else {
                        System.out.println("❌ Chỉ Admin mới có quyền tìm kiếm tài khoản!");
                    }
                    break;
                case 6:
                    if ("Admin".equals(Main.CURRENT_ACCOUNT.getRole())) {
                        datLaiMatKhau();
                    } else {
                        System.out.println("❌ Chỉ Admin mới có quyền đặt lại mật khẩu!");
                    }
                    break;
                case 0:
                    System.out.println("Quay lại menu chính...");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }
        }
    }

    // ================ CẬP NHẬT THÔNG TIN CÁ NHÂN CỦA ADMIN ==================

    private void capNhatThongTinCaNhan() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("👤              CẬP NHẬT THÔNG TIN CÁ NHÂN              👤");
        System.out.println("═".repeat(60));

        String maNV = Main.CURRENT_ACCOUNT.getMaNV();

        // Lấy thông tin hiện tại
        NhanVienDTO currentInfo = TaiKhoanDAO.layThongTinCaNhanAdmin(maNV);
        if (currentInfo == null) {
            System.out.println("❌ Không thể lấy thông tin cá nhân!");
            return;
        }

        // Hiển thị thông tin hiện tại
        System.out.println("📋 THÔNG TIN HIỆN TẠI:");
        System.out.println("┌──────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 📋 Mã nhân viên   │ " + String.format("%-" + 60 + "s", currentInfo.getMaNV()) + " │");
        System.out.println("│ 👤 Họ và tên      │ " + String.format("%-" + 60 + "s", currentInfo.getFullName()) + " │");
        System.out
                .println("│ ⚧ Giới tính       │ "
                        + String.format("%-" + 60 + "s",
                                currentInfo.getGioiTinh() != null ? currentInfo.getGioiTinh() : "Chưa cập nhật")
                        + " │");
        System.out
                .println(
                        "│ 🎂 Ngày sinh      │ "
                                + String.format("%-" + 60 + "s",
                                        currentInfo.getNgaySinh() != null ? currentInfo.getNgaySinh()
                                                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "Chưa cập nhật")
                                + " │");
        System.out.println("│ 🏠 Địa chỉ        │ " + String.format("%-" + 60 + "s",
                currentInfo.getDiaChi() != null ? currentInfo.getDiaChi() : "Chưa cập nhật") + " │");
        System.out.println("│ 📧 Email          │ " + String.format("%-" + 60 + "s",
                currentInfo.getEmail() != null ? currentInfo.getEmail() : "Chưa cập nhật") + " │");
        System.out.println("│ 💰 Lương          │ "
                + String.format("%-" + 60 + "s", FormatUtil.formatVND(currentInfo.getLuong())) + " │");
        System.out.println("│ 💼 Chức vụ        │ " + String.format("%-" + 60 + "s", currentInfo.getChucVu()) + " │");
        System.out.println("└──────────────────────────────────────────────────────────────────────────────────┘");

        System.out.println("\n⚠️  LƯU Ý: Bạn chỉ có thể cập nhật các thông tin cá nhân sau:");
        System.out.println("   • Họ và tên");
        System.out.println("   • Giới tính");
        System.out.println("   • Ngày sinh");
        System.out.println("   • Địa chỉ");
        System.out.println("   • Email");
        System.out.println("   • Mã nhân viên, lương, chức vụ KHÔNG được phép thay đổi!");

        System.out.println("\n" + "─".repeat(60));
        System.out.println("📝 NHẬP THÔNG TIN MỚI (Enter để giữ nguyên):");
        System.out.println("─".repeat(80));

        // Nhập họ
        System.out.print("👤 Họ [" + currentInfo.getHo() + "]: ");
        String ho = scanner.nextLine().trim();
        if (ho.isEmpty()) {
            ho = currentInfo.getHo();
        }

        // Nhập tên
        System.out.print("👤 Tên [" + currentInfo.getTen() + "]: ");
        String ten = scanner.nextLine().trim();
        if (ten.isEmpty()) {
            ten = currentInfo.getTen();
        }

        // Nhập giới tính
        System.out.print("⚥ Giới tính [" + (currentInfo.getGioiTinh() != null ? currentInfo.getGioiTinh() : "Chưa có")
                + "] (Nam/Nữ): ");
        String gioiTinh = scanner.nextLine().trim();
        if (gioiTinh.isEmpty()) {
            gioiTinh = currentInfo.getGioiTinh();
        } else if (!gioiTinh.equalsIgnoreCase("Nam") && !gioiTinh.equalsIgnoreCase("Nữ")) {
            System.out.println("⚠️  Giới tính không hợp lệ, giữ nguyên giá trị cũ.");
            gioiTinh = currentInfo.getGioiTinh();
        }

        // Nhập ngày sinh
        LocalDate ngaySinh = currentInfo.getNgaySinh();
        System.out.print("📅 Ngày sinh ["
                + (ngaySinh != null ? ngaySinh.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "Chưa có")
                + "] (dd/MM/yyyy): ");
        String ngaySinhStr = scanner.nextLine().trim();
        if (!ngaySinhStr.isEmpty()) {
            try {
                ngaySinh = LocalDate.parse(ngaySinhStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                System.out.println("⚠️  Định dạng ngày không đúng, giữ nguyên giá trị cũ.");
                ngaySinh = currentInfo.getNgaySinh();
            }
        }

        // Nhập địa chỉ
        System.out.print(
                "🏠 Địa chỉ [" + (currentInfo.getDiaChi() != null ? currentInfo.getDiaChi() : "Chưa có") + "]: ");
        String diaChi = scanner.nextLine().trim();
        if (diaChi.isEmpty()) {
            diaChi = currentInfo.getDiaChi();
        }

        // Nhập email
        System.out.print("📧 Email [" + (currentInfo.getEmail() != null ? currentInfo.getEmail() : "Chưa có") + "]: ");
        String email = scanner.nextLine().trim();
        if (email.isEmpty()) {
            email = currentInfo.getEmail();
        }

        // Xác nhận cập nhật
        System.out.println("\n" + "─".repeat(60));
        System.out.println("📋 THÔNG TIN MỚI SẼ ĐƯỢC CẬP NHẬT:");
        System.out.println("─".repeat(80));
        System.out.printf("Họ: %s\n", ho);
        System.out.printf("Tên: %s\n", ten);
        System.out.printf("Giới tính: %s\n", gioiTinh != null ? gioiTinh : "Chưa cập nhật");
        System.out.printf("Ngày sinh: %s\n",
                ngaySinh != null ? ngaySinh.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "Chưa cập nhật");
        System.out.printf("Địa chỉ: %s\n", diaChi != null ? diaChi : "Chưa cập nhật");
        System.out.printf("Email: %s\n", email != null ? email : "Chưa cập nhật");

        System.out.print("\n❓ Bạn có chắc chắn muốn cập nhật thông tin này? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            boolean success = TaiKhoanDAO.capNhatThongTinCaNhanAdmin(maNV, ho, ten, gioiTinh, ngaySinh, diaChi, email);
            if (success) {
                System.out.println("\n🎉 CẬP NHẬT THÀNH CÔNG!");
                System.out.println("✅ Thông tin cá nhân đã được cập nhật.");
                System.out.println("✅ Thông tin tài khoản đã được đồng bộ.");
            } else {
                System.out.println("\n❌ CẬP NHẬT THẤT BẠI!");
                System.out.println("Vui lòng kiểm tra lại thông tin và thử lại.");
            }
        } else {
            System.out.println("❌ Hủy bỏ cập nhật thông tin.");
        }

        System.out.print("\nNhấn Enter để tiếp tục...");
        scanner.nextLine();
    }

    // ================ ĐỔI MẬT KHẨU AD ==================
    private void doiMatKhau() {
        System.out.println("\n" + "═".repeat(80));
        System.out
                .println("🔑                              ĐỔI MẬT KHẨU TÀI KHOẢN                                  🔑");
        System.out.println("═".repeat(80));

        String username = Main.CURRENT_ACCOUNT.getUsername();
        String maNV = Main.CURRENT_ACCOUNT.getMaNV();

        System.out.println("📋 THÔNG TIN TÀI KHOẢN:");
        System.out.printf("Tên đăng nhập: %s\n", username);
        System.out.printf("Mã nhân viên: %s\n", maNV);

        System.out.println("\n⚠️  YÊU CẦU MẬT KHẨU MỚI:");
        System.out.println("   • Ít nhất 3 ký tự");
        System.out.println("   • Không được trùng với mã nhân viên");
        System.out.println("   • Nên chứa chữ và số để tăng bảo mật");

        System.out.println("\n" + "─".repeat(60));
        System.out.print("🔑 Nhập mật khẩu hiện tại: ");
        String currentPassword = scanner.nextLine();

        System.out.print("🔑 Nhập mật khẩu mới: ");
        String newPassword = scanner.nextLine();

        System.out.print("🔑 Xác nhận mật khẩu mới: ");
        String confirmPassword = scanner.nextLine();

        // Kiểm tra xác nhận mật khẩu
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("❌ Mật khẩu xác nhận không khớp!");
            System.out.print("\nNhấn Enter để tiếp tục...");
            scanner.nextLine();
            return;
        }

        // Thực hiện đổi mật khẩu (phương thức DAO sẽ kiểm tra mật khẩu hiện tại)
        boolean success = TaiKhoanDAO.doiMatKhau(username, currentPassword, newPassword);
        if (success) {
            System.out.println("\n🎉 ĐỔI MẬT KHẨU THÀNH CÔNG!");
            System.out.println("✅ Mật khẩu đã được cập nhật.");
            System.out.println("✅ Tài khoản của bạn giờ đây đã an toàn hơn.");
        } else {
            System.out.println("\n❌ ĐỔI MẬT KHẨU THẤT BẠI!");
            System.out.println("Vui lòng kiểm tra lại thông tin và thử lại.");
        }

        System.out.print("\nNhấn Enter để tiếp tục...");
        scanner.nextLine();
    }

    // ================ XEM THONG TIN TAI KHOAN HT CUA AD ==================
    private void xemThongTinTaiKhoan() {
        System.out.println("\n" + "═".repeat(80));
        System.out
                .println("👤                              THÔNG TIN TÀI KHOẢN                                      👤");
        System.out.println("═".repeat(80));

        String maNV = Main.CURRENT_ACCOUNT.getMaNV();
        NhanVienDTO info = TaiKhoanDAO.layThongTinCaNhanAdmin(maNV);

        if (info != null) {
            int contentWidth = 60;
            String tenDangNhap = Main.CURRENT_ACCOUNT.getUsername();
            String maNVInfo = info.getMaNV();
            String hoTen = info.getFullName();
            String vaiTro = Main.CURRENT_ACCOUNT.getRole();
            String trangThai = Main.CURRENT_ACCOUNT.getStatus();
            String email = info.getEmail() != null ? info.getEmail() : "Chưa cập nhật";
            String gioiTinh = info.getGioiTinh() != null ? info.getGioiTinh() : "Chưa cập nhật";
            String ngaySinh = info.getNgaySinh() != null
                    ? info.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    : "Chưa cập nhật";
            String diaChi = info.getDiaChi() != null ? info.getDiaChi() : "Chưa cập nhật";
            String luong = FormatUtil.formatVND(info.getLuong());
            String chucVu = info.getChucVu();
            String matKhauMacDinh = Main.CURRENT_ACCOUNT.isDefaultPassword() ? "Có (Cần đổi)" : "Không";

            System.out.println(
                    "\n╔════════════════════════════════════════════════════════════════════════════════════╗");
            System.out
                    .println("║                                 THÔNG TIN TÀI KHOẢN                                ║");
            System.out
                    .println("╚════════════════════════════════════════════════════════════════════════════════════╝");

            System.out.println("┌──────────────────────────────────────────────────────────────────────────────────┐");
            System.out
                    .println("│ 🔐 Tên đăng nhập   │ " + String.format("%-" + contentWidth + "s", tenDangNhap) + " │");
            System.out.println("│ 📋 Mã nhân viên    │ " + String.format("%-" + contentWidth + "s", maNVInfo) + " │");
            System.out.println("│ 👤 Họ và tên       │ " + String.format("%-" + contentWidth + "s", hoTen) + " │");
            System.out.println("│ 👑 Vai trò          │ " + String.format("%-" + contentWidth + "s", vaiTro) + " │");
            System.out.println("│ 🚦 Trạng thái      │ " + String.format("%-" + contentWidth + "s", trangThai) + " │");
            System.out.println("│ 📧 Email           │ " + String.format("%-" + contentWidth + "s", email) + " │");
            System.out.println("│ ⚧ Giới tính        │ " + String.format("%-" + contentWidth + "s", gioiTinh) + " │");
            System.out.println("│ 🎂 Ngày sinh       │ " + String.format("%-" + contentWidth + "s", ngaySinh) + " │");
            System.out.println("│ 🏠 Địa chỉ         │ " + String.format("%-" + contentWidth + "s", diaChi) + " │");
            System.out.println("│ 💰 Lương           │ " + String.format("%-" + contentWidth + "s", luong) + " │");
            System.out.println("│ 💼 Chức vụ         │ " + String.format("%-" + contentWidth + "s", chucVu) + " │");
            System.out.println(
                    "│ 🔑 Mật khẩu mặc định│ " + String.format("%-" + contentWidth + "s", matKhauMacDinh) + " │");
            System.out.println("└──────────────────────────────────────────────────────────────────────────────────┘");

            if (Main.CURRENT_ACCOUNT.isDefaultPassword()) {
                System.out.println("\n⚠️  CẢNH BÁO: Bạn đang sử dụng mật khẩu mặc định!");
                System.out.println("   Vui lòng đổi mật khẩu để bảo mật tài khoản.");
            }
        } else {
            System.out.println("❌ Không thể lấy thông tin tài khoản!");
        }

        System.out.print("\nNhấn Enter để tiếp tục...");
        scanner.nextLine();
    }

    // ================ XEM DANH SÁCH TÀI KHOAN AD ==================
    private void xemDanhSachTaiKhoan() {
        System.out.println("\n" + "═".repeat(80));
        System.out
                .println("📋                              DANH SÁCH TÀI KHOẢN                            📋");
        System.out.println("═".repeat(80));

        // Yêu cầu mật khẩu Admin để xem thông tin chi tiết
        System.out.println("🔒 CHỨC NĂNG ADMIN: Xem danh sách tài khoản với thông tin chi tiết");
        System.out.println("⚠️  Cần xác thực mật khẩu Admin để tiếp tục");
        System.out.print("🔑 Nhập mật khẩu Admin: ");
        String adminPassword = scanner.nextLine();

        // Kiểm tra mật khẩu Admin
        String currentUsername = Main.CURRENT_ACCOUNT.getUsername();
        TaiKhoanDTO adminAccount = TaiKhoanDAO.kiemTraTaiKhoan(currentUsername, adminPassword);

        if (adminAccount == null || !"Admin".equals(adminAccount.getRole())) {
            System.out.println("❌ Mật khẩu Admin không đúng!");
            System.out.print("\nNhấn Enter để tiếp tục...");
            scanner.nextLine();
            return;
        }

        System.out.println("✅ Xác thực thành công!");

        // Lấy thống kê tài khoản
        int[] thongKe = TaiKhoanDAO.layThongKeTaiKhoan();
        System.out.println("\n📊 THỐNG KÊ TÀI KHOẢN:");
        System.out
                .println("┌─────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf("│ Tổng số tài khoản: %-55s │\n", thongKe[0]);
        System.out.printf("│ Admin: %-65s │\n", thongKe[1]);
        System.out.printf("│ Nhân viên: %-60s │\n", thongKe[2]);
        System.out.printf("│ Đang hoạt động: %-57s │\n", thongKe[3]);
        System.out.printf("│ Đã khóa: %-63s │\n", thongKe[4]);
        System.out
                .println("└─────────────────────────────────────────────────────────────────────────────────────────┘");

        // Lấy danh sách tài khoản
        java.util.List<TaiKhoanDTO> danhSachTaiKhoan = TaiKhoanDAO.xemDanhSachTaiKhoan();

        if (danhSachTaiKhoan == null) {
            System.out.println("❌ Lỗi khi lấy danh sách tài khoản!");
            System.out.print("\nNhấn Enter để tiếp tục...");
            scanner.nextLine();
            return;
        }

        if (danhSachTaiKhoan.isEmpty()) {
            System.out.println("🔍 Không có tài khoản nào trong hệ thống!");
        } else {
            System.out.println("\n📋 DANH SÁCH TÀI KHOẢN:");
            System.out.println(
                    "┌─────┬─────────────────┬──────────┬──────────┬──────────────────────────┬────────────────────────────┬────────────┬──────────────────┐");

            // Header của bảng
            System.out.printf("│ %-3s │ %-15s │ %-8s │ %-8s │ %-24s │ %-26s │ %-10s │ %-16s │\n",
                    "STT", "USERNAME", "MÃ NV", "VAI TRÒ", "HỌ TÊN", "EMAIL", "TRẠNG THÁI", "MẬT KHẨU");
            System.out.println(
                    "├─────┼─────────────────┼──────────┼──────────┼──────────────────────────┼────────────────────────────┼────────────┼──────────────────┤");

            // Hiển thị từng tài khoản trong bảng
            for (int i = 0; i < danhSachTaiKhoan.size(); i++) {
                TaiKhoanDTO tk = danhSachTaiKhoan.get(i);
                String vaiTroIcon = "Admin".equals(tk.getRole()) ? "👑" : "👤";
                String trangThaiIcon = "Active".equals(tk.getStatus()) ? "✅" : "❌";
                String matKhauIcon = tk.isDefaultPassword() ? "⚠️" : "🔒";

                // Cắt ngắn text nếu quá dài
                String hoTen = tk.getfullName() != null ? tk.getfullName() : "Chưa có";
                String email = tk.getEmail() != null ? tk.getEmail() : "Chưa có";
                String matKhauText = tk.isDefaultPassword() ? "Mặc định" : "Đã đổi";

                if (hoTen.length() > 24)
                    hoTen = hoTen.substring(0, 21) + "...";
                if (email.length() > 26)
                    email = email.substring(0, 23) + "...";

                System.out.printf("│ %-3d │ %-15s │ %-8s │ %-8s │ %-24s │ %-26s │ %-10s │ %-16s │\n",
                        i + 1,
                        tk.getUsername(),
                        tk.getMaNV(),
                        vaiTroIcon + " " + tk.getRole(),
                        hoTen,
                        email,
                        trangThaiIcon + " " + tk.getStatus(),
                        matKhauIcon + " " + matKhauText);
            }

            System.out.println(
                    "└─────┴─────────────────┴──────────┴──────────┴──────────────────────────┴────────────────────────────┴────────────┴──────────────────┘");
            System.out.printf("📊 Tổng cộng: %d tài khoản\n", danhSachTaiKhoan.size());
        }

        System.out.print("\nNhấn Enter để tiếp tục...");
        scanner.nextLine();
    }

    // ================ ĐẶT LẠI MẬT KHẨU CHO BẤT KỲ TK ==================
    private void datLaiMatKhau() {
        System.out.println("\n" + "═".repeat(80));
        System.out.println(
                "🔄                              ĐẶT LẠI MẬT KHẨU TÀI KHOẢN                       🔄");
        System.out.println("═".repeat(80));

        System.out.println("⚠️  CHỨC NĂNG ADMIN: Đặt lại mật khẩu cho bất kỳ tài khoản nào");
        System.out.println("   Không cần mật khẩu hiện tại của tài khoản đó");

        System.out.println("\n" + "─".repeat(60));
        System.out.print("👤 Nhập tên đăng nhập cần đặt lại mật khẩu: ");
        String username = scanner.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println("❌ Tên đăng nhập không được để trống!");
            System.out.print("\nNhấn Enter để tiếp tục...");
            scanner.nextLine();
            return;
        }

        // Kiểm tra tài khoản có tồn tại không
        java.util.List<TaiKhoanDTO> danhSachTaiKhoan = TaiKhoanDAO.timKiemTaiKhoan(username);
        if (danhSachTaiKhoan == null || danhSachTaiKhoan.isEmpty()) {
            System.out.println("❌ Không tìm thấy tài khoản với tên đăng nhập: " + username);
            System.out.print("\nNhấn Enter để tiếp tục...");
            scanner.nextLine();
            return;
        }

        TaiKhoanDTO taiKhoanCanDatLai = danhSachTaiKhoan.get(0);
        System.out.println("\n📋 THÔNG TIN TÀI KHOẢN:");
        System.out
                .println("┌─────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf("│ Tên đăng nhập: %-62s │\n", taiKhoanCanDatLai.getUsername());
        System.out.printf("│ Mã nhân viên: %-63s │\n", taiKhoanCanDatLai.getMaNV());
        System.out.printf("│ Họ tên: %-68s │\n",
                taiKhoanCanDatLai.getfullName() != null ? taiKhoanCanDatLai.getfullName() : "Chưa có");
        System.out.printf("│ Vai trò: %-67s │\n", taiKhoanCanDatLai.getRole());
        System.out.printf("│ Trạng thái: %-65s │\n", taiKhoanCanDatLai.getStatus());
        System.out
                .println("└─────────────────────────────────────────────────────────────────────────────────────────┘");

        System.out.println("\n" + "─".repeat(60));
        System.out.println("🔧 TÙY CHỌN ĐẶT LẠI MẬT KHẨU:");
        System.out.println("   [1] Đặt về mật khẩu mặc định (mã nhân viên)");
        System.out.println("   [2] Đặt mật khẩu mới");
        System.out.println("   [0] Hủy bỏ");
        System.out.print("💡 Chọn tùy chọn: ");

        int choice = -1;
        while (true) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= 0 && choice <= 2) {
                    break;
                }
                System.out.println("Vui lòng nhập số trong khoảng 0–2.");
                System.out.print("\nChọn tùy chọn: ");
            } else {
                System.out.println("Vui lòng nhập số hợp lệ.");
                scanner.next();
                System.out.print("\nChọn tùy chọn: ");
            }
        }

        boolean success = false;
        switch (choice) {
            case 1:
                // Đặt về mật khẩu mặc định
                System.out.println("\n🔄 Đặt lại mật khẩu về mặc định: " + taiKhoanCanDatLai.getMaNV());
                System.out.print("❓ Bạn có chắc chắn muốn đặt lại mật khẩu về mặc định? (y/n): ");
                String confirm1 = scanner.nextLine().trim().toLowerCase();
                if (confirm1.equals("y") || confirm1.equals("yes")) {
                    success = TaiKhoanDAO.datLaiMatKhauMacDinh(username);
                } else {
                    System.out.println("❌ Hủy bỏ đặt lại mật khẩu.");
                }
                break;
            case 2:
                // Đặt mật khẩu mới
                System.out.println("\n" + "─".repeat(60));
                System.out.println("🔑 NHẬP MẬT KHẨU MỚI:");
                System.out.println("   • Ít nhất 3 ký tự");
                System.out.println("   • Không được trùng với mã nhân viên");
                System.out.print("🔑 Mật khẩu mới: ");
                String newPassword = scanner.nextLine().trim();

                if (newPassword.isEmpty()) {
                    System.out.println("❌ Mật khẩu không được để trống!");
                    success = false;
                } else if (newPassword.equals(taiKhoanCanDatLai.getMaNV())) {
                    System.out.println("❌ Mật khẩu mới không được trùng với mã nhân viên!");
                    success = false;
                } else {
                    System.out.print("❓ Bạn có chắc chắn muốn đặt mật khẩu mới? (y/n): ");
                    String confirm2 = scanner.nextLine().trim().toLowerCase();
                    if (confirm2.equals("y") || confirm2.equals("yes")) {
                        success = TaiKhoanDAO.datLaiMatKhau(username, newPassword);
                    } else {
                        System.out.println("❌ Hủy bỏ đặt lại mật khẩu.");
                        success = false;
                    }
                }
                break;
            case 0:
                System.out.println("❌ Hủy bỏ đặt lại mật khẩu.");
                success = false;
                break;
        }

        if (success) {
            System.out.println("\n🎉 ĐẶT LẠI MẬT KHẨU THÀNH CÔNG!");
            System.out.println("✅ Mật khẩu đã được cập nhật cho tài khoản: " + username);
        }

        System.out.print("\nNhấn Enter để tiếp tục...");
        scanner.nextLine();
    }

    // ================ TÌM KIẾM TÀI KHOẢN ==================
    private void timKiemTaiKhoan() {
        System.out.println("\n" + "═".repeat(80));
        System.out.println("🔍                              TÌM KIẾM TÀI KHOẢN                            🔍");
        System.out.println("═".repeat(80));

        System.out.println("🔍 CHỨC NĂNG ADMIN: Tìm kiếm tài khoản theo từ khóa");
        System.out.println("   Tìm kiếm theo: username, họ tên, mã nhân viên");
        System.out.println("   Enter để xem tất cả tài khoản");

        System.out.println("\n" + "─".repeat(60));
        System.out.print("🔍 Nhập từ khóa tìm kiếm: ");
        String keyword = scanner.nextLine().trim();

        // Lấy danh sách tài khoản
        java.util.List<TaiKhoanDTO> danhSachTaiKhoan;
        if (keyword.isEmpty()) {
            danhSachTaiKhoan = TaiKhoanDAO.xemDanhSachTaiKhoan();
            System.out.println("📋 Hiển thị tất cả tài khoản:");
        } else {
            danhSachTaiKhoan = TaiKhoanDAO.timKiemTaiKhoan(keyword);
            System.out.println("🔍 Kết quả tìm kiếm cho từ khóa: \"" + keyword + "\"");
        }

        if (danhSachTaiKhoan == null) {
            System.out.println("❌ Lỗi khi tìm kiếm tài khoản!");
            System.out.print("\nNhấn Enter để tiếp tục...");
            scanner.nextLine();
            return;
        }

        if (danhSachTaiKhoan.isEmpty()) {
            System.out.println("🔍 Không tìm thấy tài khoản nào phù hợp!");
        } else {
            System.out.println("\n📋 KẾT QUẢ TÌM KIẾM:");
            System.out.println(
                    "┌─────┬─────────────────┬──────────┬──────────┬──────────────────────────┬────────────────────────────┬────────────┬──────────────────┐");

            // Header của bảng
            System.out.printf("│ %-3s │ %-15s │ %-8s │ %-8s │ %-24s │ %-26s │ %-10s │ %-16s │\n",
                    "STT", "USERNAME", "MÃ NV", "VAI TRÒ", "HỌ TÊN", "EMAIL", "TRẠNG THÁI", "MẬT KHẨU");
            System.out.println(
                    "├─────┼─────────────────┼──────────┼──────────┼──────────────────────────┼────────────────────────────┼────────────┼──────────────────┤");

            // Hiển thị từng tài khoản trong bảng
            for (int i = 0; i < danhSachTaiKhoan.size(); i++) {
                TaiKhoanDTO tk = danhSachTaiKhoan.get(i);
                String vaiTroIcon = "Admin".equals(tk.getRole()) ? "👑" : "👤";
                String trangThaiIcon = "Active".equals(tk.getStatus()) ? "✅" : "❌";
                String matKhauIcon = tk.isDefaultPassword() ? "⚠️" : "🔒";

                // Cắt ngắn text nếu quá dài
                String hoTen = tk.getfullName() != null ? tk.getfullName() : "Chưa có";
                String email = tk.getEmail() != null ? tk.getEmail() : "Chưa có";
                String matKhauText = tk.isDefaultPassword() ? "Mặc định" : "Đã đổi";

                if (hoTen.length() > 24)
                    hoTen = hoTen.substring(0, 21) + "...";
                if (email.length() > 26)
                    email = email.substring(0, 23) + "...";

                System.out.printf("│ %-3d │ %-15s │ %-8s │ %-8s │ %-24s │ %-26s │ %-10s │ %-16s │\n",
                        i + 1,
                        tk.getUsername(),
                        tk.getMaNV(),
                        vaiTroIcon + " " + tk.getRole(),
                        hoTen,
                        email,
                        trangThaiIcon + " " + tk.getStatus(),
                        matKhauIcon + " " + matKhauText);
            }

            System.out.println(
                    "└─────┴─────────────────┴──────────┴──────────┴──────────────────────────┴────────────────────────────┴────────────┴──────────────────┘");
            System.out.printf("📊 Tìm thấy: %d tài khoản\n", danhSachTaiKhoan.size());

            // Hiển thị chi tiết nếu có ít tài khoản
            if (danhSachTaiKhoan.size() <= 5) {
                System.out.println("\n📋 CHI TIẾT TÀI KHOẢN:");
                System.out.println(
                        "┌─────────────────────────────────────────────────────────────────────────────────────────┐");
                for (int i = 0; i < danhSachTaiKhoan.size(); i++) {
                    TaiKhoanDTO tk = danhSachTaiKhoan.get(i);
                    System.out.printf("│ %d. %-70s │\n", i + 1, tk.getUsername());
                    System.out.printf("│    Mã NV: %-65s │\n", tk.getMaNV());
                    System.out.printf("│    Vai trò: %-63s │\n", tk.getRole());
                    System.out.printf("│    Họ tên: %-64s │\n",
                            tk.getfullName() != null ? tk.getfullName() : "Chưa có");
                    System.out.printf("│    Email: %-65s │\n", tk.getEmail() != null ? tk.getEmail() : "Chưa có");
                    System.out.printf("│    Trạng thái: %-60s │\n", tk.getStatus());
                    System.out.printf("│    Mật khẩu: %-62s │\n",
                            tk.isDefaultPassword() ? "Mặc định (Cần đổi)" : "Đã đổi");
                    if (i < danhSachTaiKhoan.size() - 1) {
                        System.out.println(
                                "├─────────────────────────────────────────────────────────────────────────────────────────┤");
                    }
                }
                System.out.println(
                        "└─────────────────────────────────────────────────────────────────────────────────────────┘");
            }
        }

        System.out.print("\nNhấn Enter để tiếp tục...");
        scanner.nextLine();
    }

    // ========================= NHÂN VIÊN ==================

    // ================ MENU TK NHÂN VIÊN ==================
    public void menuQuanLyTaiKhoanNhanVien() {
        while (true) {
            System.out.println("\n" + "═".repeat(60));
            System.out.println("👤              QUẢN LÝ TÀI KHOẢN NHÂN VIÊN               👤");
            System.out.println("═".repeat(60));
            System.out.println("┌─ CHỨC NĂNG CHÍNH ──────────────────────────────────────┐");
            System.out.println("│                                                        │");
            System.out.println("│  [1] ➜ Xem thông tin cá nhân                           │");
            System.out.println("│  [2] ➜ Đổi mật khẩu                                    │");
            System.out.println("│  [3] ➜ Cập nhật thông tin cá nhân                      │");
            System.out.println("│                                                        │");
            System.out.println("├─ HỆ THỐNG ─────────────────────────────────────────────┤");
            System.out.println("│                                                        │");
            System.out.println("│  [0] ✗ Quay lại menu chính                             │");
            System.out.println("│                                                        │");
            System.out.println("└────────────────────────────────────────────────────────┘");

            System.out.print("\n💡 Nhập lựa chọn của bạn: ");
            int choice = -1;

            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 0 && choice <= 3) {
                        break;
                    }
                    System.out.println("Vui lòng nhập số trong khoảng 0–3.");
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                } else {
                    System.out.println("Vui lòng nhập số hợp lệ.");
                    scanner.next();
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                }
            }

            switch (choice) {
                case 1:
                    xemThongTinCaNhanNhanVien();
                    break;
                case 2:
                    doiMatKhauNhanVien();
                    break;
                case 3:
                    capNhatThongTinCaNhanNhanVien();
                    break;
                case 0:
                    System.out.println("Quay lại menu chính...");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }
        }
    }

    // ================ XEM THÔNG TIN TK CÁ NHÂN NV ==================
    private void xemThongTinCaNhanNhanVien() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("👤              THÔNG TIN CÁ NHÂN NHÂN VIÊN              👤");
        System.out.println("═".repeat(60));

        String maNV = Main.CURRENT_ACCOUNT.getMaNV();
        NhanVienDTO info = TaiKhoanDAO.layThongTinCaNhanAdmin(maNV);

        if (info != null) {
            int contentWidth = 60;
            String tenDangNhap = Main.CURRENT_ACCOUNT.getUsername();
            String maNVInfo = info.getMaNV();
            String hoTen = info.getFullName();
            String vaiTro = Main.CURRENT_ACCOUNT.getRole();
            String trangThai = Main.CURRENT_ACCOUNT.getStatus();
            String email = info.getEmail() != null ? info.getEmail() : "Chưa cập nhật";
            String gioiTinh = info.getGioiTinh() != null ? info.getGioiTinh() : "Chưa cập nhật";
            String ngaySinh = info.getNgaySinh() != null
                    ? info.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    : "Chưa cập nhật";
            String diaChi = info.getDiaChi() != null ? info.getDiaChi() : "Chưa cập nhật";
            String luong = FormatUtil.formatVND(info.getLuong());
            String chucVu = info.getChucVu();
            String matKhauMacDinh = Main.CURRENT_ACCOUNT.isDefaultPassword() ? "Có (Cần đổi)" : "Không";

            System.out.println(
                    "\n╔════════════════════════════════════════════════════════════════════════════════════╗");
            System.out
                    .println("║                                 THÔNG TIN CÁ NHÂN                                  ║");
            System.out
                    .println("╚════════════════════════════════════════════════════════════════════════════════════╝");

            System.out
                    .println("┌────────────────────────────────────────────────────────────────────────────────────┐");
            System.out
                    .println(
                            "│ 🔐 Tên đăng nhập     │ " + String.format("%-" + contentWidth + "s", tenDangNhap) + "│");
            System.out.println("│ 📋 Mã nhân viên      │ " + String.format("%-" + contentWidth + "s", maNVInfo) + "│");
            System.out.println("│ 👤 Họ và tên         │ " + String.format("%-" + contentWidth + "s", hoTen) + "│");
            System.out.println("│ 👑 Vai trò           │ " + String.format("%-" + contentWidth + "s", vaiTro) + "│");
            System.out
                    .println("│ 🚦 Trạng thái        │ " + String.format("%-" + contentWidth + "s", trangThai) + "│");
            System.out.println("│ 📧 Email             │ " + String.format("%-" + contentWidth + "s", email) + "│");
            System.out.println("│ ⚧ Giới tính          │ " + String.format("%-" + contentWidth + "s", gioiTinh) + "│");
            System.out.println("│ 🎂 Ngày sinh         │ " + String.format("%-" + contentWidth + "s", ngaySinh) + "│");
            System.out.println("│ 🏠 Địa chỉ           │ " + String.format("%-" + contentWidth + "s", diaChi) + "│");
            System.out.println("│ 💰 Lương             │ " + String.format("%-" + contentWidth + "s", luong) + "│");
            System.out.println("│ 💼 Chức vụ           │ " + String.format("%-" + contentWidth + "s", chucVu) + "│");
            System.out.println(
                    "│ 🔑 Mật khẩu mặc định │ " + String.format("%-" + contentWidth + "s", matKhauMacDinh) + "│");
            System.out
                    .println("└────────────────────────────────────────────────────────────────────────────────────┘");

            if (Main.CURRENT_ACCOUNT.isDefaultPassword()) {
                System.out.println("\n⚠️  CẢNH BÁO: Bạn đang sử dụng mật khẩu mặc định!");
                System.out.println("   Vui lòng đổi mật khẩu để bảo mật tài khoản.");
            }
        } else {
            System.out.println("❌ Không thể lấy thông tin cá nhân!");
        }

        System.out.print("\nNhấn Enter để tiếp tục...");
        scanner.nextLine();
    }

    // ================ ĐỔI MẬT KHẨU TK NV ==================
    private void doiMatKhauNhanVien() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("🔑              ĐỔI MẬT KHẨU TÀI KHOẢN                  🔑");
        System.out.println("═".repeat(60));

        String username = Main.CURRENT_ACCOUNT.getUsername();
        String maNV = Main.CURRENT_ACCOUNT.getMaNV();

        System.out.println("📋 THÔNG TIN TÀI KHOẢN:");
        System.out.printf("Tên đăng nhập: %s\n", username);
        System.out.printf("Mã nhân viên: %s\n", maNV);

        System.out.println("\n⚠️  YÊU CẦU MẬT KHẨU MỚI:");
        System.out.println("   • Ít nhất 3 ký tự");
        System.out.println("   • Không được trùng với mã nhân viên");
        System.out.println("   • Nên chứa chữ và số để tăng bảo mật");

        System.out.println("\n" + "─".repeat(60));
        System.out.print("🔑 Nhập mật khẩu hiện tại: ");
        String currentPassword = scanner.nextLine();

        System.out.print("🔑 Nhập mật khẩu mới: ");
        String newPassword = scanner.nextLine();

        System.out.print("🔑 Xác nhận mật khẩu mới: ");
        String confirmPassword = scanner.nextLine();

        // Kiểm tra xác nhận mật khẩu
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("❌ Mật khẩu xác nhận không khớp!");
            System.out.print("\nNhấn Enter để tiếp tục...");
            scanner.nextLine();
            return;
        }

        // Thực hiện đổi mật khẩu
        boolean success = TaiKhoanDAO.doiMatKhau(username, currentPassword, newPassword);
        if (success) {
            System.out.println("\n🎉 ĐỔI MẬT KHẨU THÀNH CÔNG!");
            System.out.println("✅ Mật khẩu đã được cập nhật.");
            System.out.println("✅ Tài khoản của bạn giờ đây đã an toàn hơn.");
        } else {
            System.out.println("\n❌ ĐỔI MẬT KHẨU THẤT BẠI!");
            System.out.println("Vui lòng kiểm tra lại thông tin và thử lại.");
        }

        System.out.print("\nNhấn Enter để tiếp tục...");
        scanner.nextLine();
    }

    // ================ CẬP NHẬT THÔNG TIN TK NV ==================
    private void capNhatThongTinCaNhanNhanVien() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("📝              CẬP NHẬT THÔNG TIN CÁ NHÂN              📝");
        System.out.println("═".repeat(60));

        String maNV = Main.CURRENT_ACCOUNT.getMaNV();

        // Lấy thông tin hiện tại
        NhanVienDTO currentInfo = TaiKhoanDAO.layThongTinCaNhanAdmin(maNV);
        if (currentInfo == null) {
            System.out.println("❌ Không thể lấy thông tin cá nhân!");
            return;
        }

        // Hiển thị thông tin hiện tại
        System.out.println("📋 THÔNG TIN HIỆN TẠI:");
        System.out.println("┌──────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 📋 Mã nhân viên   │ " + String.format("%-" + 60 + "s", currentInfo.getMaNV()) + " │");
        System.out.println("│ 👤 Họ và tên      │ " + String.format("%-" + 60 + "s", currentInfo.getFullName()) + " │");
        System.out
                .println("│ ⚧ Giới tính       │ "
                        + String.format("%-" + 60 + "s",
                                currentInfo.getGioiTinh() != null ? currentInfo.getGioiTinh() : "Chưa cập nhật")
                        + " │");
        System.out
                .println(
                        "│ 🎂 Ngày sinh      │ "
                                + String.format("%-" + 60 + "s",
                                        currentInfo.getNgaySinh() != null ? currentInfo.getNgaySinh()
                                                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "Chưa cập nhật")
                                + " │");
        System.out.println("│ 🏠 Địa chỉ        │ " + String.format("%-" + 60 + "s",
                currentInfo.getDiaChi() != null ? currentInfo.getDiaChi() : "Chưa cập nhật") + " │");
        System.out.println("│ 📧 Email          │ " + String.format("%-" + 60 + "s",
                currentInfo.getEmail() != null ? currentInfo.getEmail() : "Chưa cập nhật") + " │");
        System.out.println("│ 💰 Lương          │ "
                + String.format("%-" + 60 + "s", FormatUtil.formatVND(currentInfo.getLuong())) + " │");
        System.out.println("│ 💼 Chức vụ        │ " + String.format("%-" + 60 + "s", currentInfo.getChucVu()) + " │");
        System.out.println("└──────────────────────────────────────────────────────────────────────────────────┘");

        System.out.println("\n⚠️  LƯU Ý: Bạn chỉ có thể cập nhật các thông tin cá nhân sau:");
        System.out.println("   • Họ và tên");
        System.out.println("   • Giới tính");
        System.out.println("   • Ngày sinh");
        System.out.println("   • Địa chỉ");
        System.out.println("   • Email");
        System.out.println("   • Mã nhân viên, lương, chức vụ KHÔNG được phép thay đổi!");

        System.out.println("\n" + "─".repeat(60));
        System.out.println("📝 NHẬP THÔNG TIN MỚI (Enter để giữ nguyên):");
        System.out.println("─".repeat(80));

        // Nhập họ
        System.out.print("👤 Họ [" + currentInfo.getHo() + "]: ");
        String ho = scanner.nextLine().trim();
        if (ho.isEmpty()) {
            ho = currentInfo.getHo();
        }

        // Nhập tên
        System.out.print("👤 Tên [" + currentInfo.getTen() + "]: ");
        String ten = scanner.nextLine().trim();
        if (ten.isEmpty()) {
            ten = currentInfo.getTen();
        }

        // Nhập giới tính
        System.out.print("⚥ Giới tính [" + (currentInfo.getGioiTinh() != null ? currentInfo.getGioiTinh() : "Chưa có")
                + "] (Nam/Nữ): ");
        String gioiTinh = scanner.nextLine().trim();
        if (gioiTinh.isEmpty()) {
            gioiTinh = currentInfo.getGioiTinh();
        } else if (!gioiTinh.equalsIgnoreCase("Nam") && !gioiTinh.equalsIgnoreCase("Nữ")) {
            System.out.println("⚠️  Giới tính không hợp lệ, giữ nguyên giá trị cũ.");
            gioiTinh = currentInfo.getGioiTinh();
        }

        // Nhập ngày sinh
        LocalDate ngaySinh = currentInfo.getNgaySinh();
        System.out.print("📅 Ngày sinh ["
                + (ngaySinh != null ? ngaySinh.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "Chưa có")
                + "] (dd/MM/yyyy): ");
        String ngaySinhStr = scanner.nextLine().trim();
        if (!ngaySinhStr.isEmpty()) {
            try {
                ngaySinh = LocalDate.parse(ngaySinhStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                System.out.println("⚠️  Định dạng ngày không đúng, giữ nguyên giá trị cũ.");
                ngaySinh = currentInfo.getNgaySinh();
            }
        }

        // Nhập địa chỉ
        System.out.print(
                "🏠 Địa chỉ [" + (currentInfo.getDiaChi() != null ? currentInfo.getDiaChi() : "Chưa có") + "]: ");
        String diaChi = scanner.nextLine().trim();
        if (diaChi.isEmpty()) {
            diaChi = currentInfo.getDiaChi();
        }

        // Nhập email
        System.out.print("📧 Email [" + (currentInfo.getEmail() != null ? currentInfo.getEmail() : "Chưa có") + "]: ");
        String email = scanner.nextLine().trim();
        if (email.isEmpty()) {
            email = currentInfo.getEmail();
        }

        // Xác nhận cập nhật
        System.out.println("\n" + "─".repeat(60));
        System.out.println("📋 THÔNG TIN MỚI SẼ ĐƯỢC CẬP NHẬT:");
        System.out.println("─".repeat(80));
        System.out.printf("Họ: %s\n", ho);
        System.out.printf("Tên: %s\n", ten);
        System.out.printf("Giới tính: %s\n", gioiTinh != null ? gioiTinh : "Chưa cập nhật");
        System.out.printf("Ngày sinh: %s\n",
                ngaySinh != null ? ngaySinh.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "Chưa cập nhật");
        System.out.printf("Địa chỉ: %s\n", diaChi != null ? diaChi : "Chưa cập nhật");
        System.out.printf("Email: %s\n", email != null ? email : "Chưa cập nhật");

        System.out.print("\n❓ Bạn có chắc chắn muốn cập nhật thông tin này? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            boolean success = TaiKhoanDAO.capNhatThongTinCaNhanAdmin(maNV, ho, ten, gioiTinh, ngaySinh, diaChi, email);
            if (success) {
                System.out.println("\n🎉 CẬP NHẬT THÀNH CÔNG!");
                System.out.println("✅ Thông tin cá nhân đã được cập nhật.");
                System.out.println("✅ Thông tin tài khoản đã được đồng bộ.");
            } else {
                System.out.println("\n❌ CẬP NHẬT THẤT BẠI!");
                System.out.println("Vui lòng kiểm tra lại thông tin và thử lại.");
            }
        } else {
            System.out.println("❌ Hủy bỏ cập nhật thông tin.");
        }

        System.out.print("\nNhấn Enter để tiếp tục...");
        scanner.nextLine();
    }
}