package view;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import dao.NhanVienDAO;
import dto.NhanVienDTO;
import main.Main;
import util.tablePrinter;

//========= MENU QL NHÂN VIÊN ======= 
public class QuanLyNhanVien {
    private static final Scanner STDIN = new Scanner(System.in);

    public void menuQuanLyNhanVien() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out
                    .println("\n████████████████████████████████████████████████████████████████████████████████");
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
                    if (choice >= 0 && choice <= 6) {
                        break;
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
                    themNhanVien();
                    break;
                case 2:
                    suaNhanVien();
                    break;
                case 3:
                    xoaNhanVien();
                    break;
                case 4:
                    timKiemNhanVien();
                    break;
                case 5:
                    thongKeNhanVien();
                    break;
                case 6:
                    xemDanhSachNhanVien();
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

    // ========= THÊM NHÂN VIÊN =======
    public void themNhanVien() {
        Scanner sc = new Scanner(System.in);

        System.out.println(
                "\n╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                THÊM NHÂN VIÊN MỚI                                  ║");
        System.out
                .println("╚════════════════════════════════════════════════════════════════════════════════════╝");

        // Nhập mã nhân viên
        String maNV;
        while (true) {
            System.out.print("📝 Nhập mã nhân viên (VD: NV001): ");
            maNV = sc.nextLine().trim();
            if (maNV.isEmpty()) {
                System.out.println("❌ Mã nhân viên không được để trống!");
                continue;
            }
            if (NhanVienDAO.timNhanVienTheoMa(maNV) != null) {
                System.out.println("❌ Mã nhân viên đã tồn tại! Vui lòng nhập mã khác.");
                continue;
            }
            break;
        }

        // Nhập họ
        String ho;
        while (true) {
            System.out.print("📝 Nhập họ: ");
            ho = sc.nextLine().trim();
            if (ho.isEmpty()) {
                System.out.println("❌ Họ không được để trống!");
                continue;
            }
            break;
        }

        // Nhập tên
        String ten;
        while (true) {
            System.out.print("📝 Nhập tên: ");
            ten = sc.nextLine().trim();
            if (ten.isEmpty()) {
                System.out.println("❌ Tên không được để trống!");
                continue;
            }
            break;
        }

        // Nhập giới tính
        String gioiTinh;
        while (true) {
            System.out.print("📝 Nhập giới tính (Nam/Nu): ");
            gioiTinh = sc.nextLine().trim();
            if (!gioiTinh.equals("Nam") && !gioiTinh.equals("Nu")) {
                System.out.println("❌ Giới tính chỉ được nhập 'Nam' hoặc 'Nu'!");
                continue;
            }
            break;
        }

        // Nhập ngày sinh (có thể bỏ trống)
        LocalDate ngaySinh = null;
        while (true) {
            System.out.print("📝 Nhập ngày sinh (dd/MM/yyyy) - Enter để bỏ qua: ");
            String ngaySinhStr = sc.nextLine().trim();
            if (ngaySinhStr.isEmpty()) {
                break;
            }
            try {
                ngaySinh = LocalDate.parse(ngaySinhStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                break;
            } catch (DateTimeParseException e) {
                System.out.println("❌ Định dạng ngày không đúng! Vui lòng nhập theo định dạng dd/MM/yyyy");
            }
        }

        // Nhập địa chỉ
        System.out.print("📝 Nhập địa chỉ: ");
        String diaChi = sc.nextLine().trim();

        // Nhập email
        String email;
        while (true) {
            System.out.print("📝 Nhập email: ");
            email = sc.nextLine().trim();
            if (email.isEmpty()) {
                System.out.println("❌ Email không được để trống!");
                continue;
            }
            if (!email.contains("@")) {
                System.out.println("❌ Email không hợp lệ!");
                continue;
            }
            break;
        }

        // Nhập lương
        int luong;
        while (true) {
            System.out.print("📝 Nhập lương: ");
            try {
                luong = Integer.parseInt(sc.nextLine().trim());
                if (luong < 0) {
                    System.out.println("❌ Lương phải >= 0!");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("❌ Lương phải là số nguyên!");
            }
        }

        // Nhập chức vụ
        String chucVu;
        while (true) {
            System.out.print("📝 Nhập chức vụ (QL/NV): ");
            chucVu = sc.nextLine().trim();
            if (!chucVu.equals("QL") && !chucVu.equals("NV")) {
                System.out.println("❌ Chức vụ chỉ được nhập 'QL' hoặc 'NV'!");
                continue;
            }
            break;
        }

        // Tạo đối tượng NhanVienDTO
        NhanVienDTO nv = new NhanVienDTO(maNV, ho, ten, gioiTinh, ngaySinh, diaChi, email, luong, chucVu);

        // Xác nhận thông tin với UI giống inThongTinNhanVienChiTiet
        int contentWidth = 60;
        String hoTen = ho + " " + ten;
        String ngaySinhStr = ngaySinh != null ? ngaySinh.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "Không có";
        String luongStr = String.format("%,d VNĐ", luong);

        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                XÁC NHẬN THÔNG TIN                                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");

        System.out.println("┌──────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 📋 Mã nhân viên   │ " + String.format("%-" + contentWidth + "s", maNV) + " │");
        System.out.println("│ 👤 Họ và tên      │ " + String.format("%-" + contentWidth + "s", hoTen) + " │");
        System.out.println("│ ⚧ Giới tính       │ " + String.format("%-" + contentWidth + "s", gioiTinh) + " │");
        System.out.println("│ 🎂 Ngày sinh      │ " + String.format("%-" + contentWidth + "s", ngaySinhStr) + " │");
        System.out.println("│ 🏠 Địa chỉ        │ " + String.format("%-" + contentWidth + "s", diaChi) + " │");
        System.out.println("│ 📧 Email          │ " + String.format("%-" + contentWidth + "s", email) + " │");
        System.out.println("│ 💰 Lương          │ " + String.format("%-" + contentWidth + "s", luongStr) + " │");
        System.out.println("│ 💼 Chức vụ        │ " + String.format("%-" + contentWidth + "s", chucVu) + " │");
        System.out.println("└──────────────────────────────────────────────────────────────────────────────────┘");

        System.out.print("\n❓ Bạn có muốn thêm nhân viên này? (y/n): ");
        String confirm = sc.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            NhanVienDAO.themNhanVien(nv);
        } else {
            System.out.println("❌ Đã hủy thêm nhân viên!");
        }

        System.out.print("\n⏸️  Nhấn Enter để tiếp tục...");
        sc.nextLine();
    }

    // ========= Sửa NHÂN VIÊN =======
    public void suaNhanVien() {
        Scanner sc = new Scanner(System.in);
        boolean continueWithAnotherEmployee = true;
        while (continueWithAnotherEmployee) {
            while (true) {
                try {
                    System.out.print("📝 Nhập mã nhân viên cần sửa (0 để thoát): ");
                    String maNV = sc.nextLine().trim();
                    if (maNV.equals("0")) {
                        System.out.println("Thoát sửa nhân viên.");
                        break;
                    }

                    if (maNV.isEmpty()) {
                        System.out.println("❌ Mã nhân viên không được để trống!");
                        continue;
                    }

                    NhanVienDTO nvCanSua = NhanVienDAO.timNhanVienTheoMa(maNV);
                    if (nvCanSua == null) {
                        System.out.println("❌ Không tìm thấy nhân viên với mã: " + maNV);
                        continue;
                    }

                    // Hiển thị thông tin hiện tại
                    System.out.println("\n--- THÔNG TIN HIỆN TẠI ---");
                    inThongTinNhanVienChiTiet(nvCanSua);

                    System.out.println("\n--- NHẬP THÔNG TIN MỚI (Enter để giữ nguyên) ---");

                    // Sửa họ tên
                    String hoMoi = nhapVoiGiuNguyen(sc, "Họ mới", nvCanSua.getHo());
                    String tenMoi = nhapVoiGiuNguyen(sc, "Tên mới", nvCanSua.getTen());

                    // Sửa giới tính
                    String gioiTinhMoi = nhapGioiTinhVoiGiuNguyen(sc, nvCanSua.getGioiTinh());

                    // Sửa ngày sinh
                    LocalDate ngaySinhMoi = nhapNgaySinhVoiGiuNguyen(sc, nvCanSua.getNgaySinh());

                    // Sửa địa chỉ
                    String diaChiMoi = nhapVoiGiuNguyen(sc, "Địa chỉ mới", nvCanSua.getDiaChi());

                    // Sửa email
                    String emailMoi = nhapEmailVoiGiuNguyen(sc, nvCanSua.getEmail());

                    // Sửa lương
                    int luongMoi = nhapLuongVoiGiuNguyen(sc, nvCanSua.getLuong());

                    // Sửa chức vụ
                    String chucVuMoi = nhapChucVuVoiGiuNguyen(sc, nvCanSua.getChucVu());

                    // Nhập trạng thái hợp lệ
                    String trangThaiMoi;
                    while (true) {
                        System.out.print("📝 Trạng thái (active/inactive) - Enter để giữ nguyên: ");
                        String input = sc.nextLine().trim();
                        if (input.isEmpty()) {
                            trangThaiMoi = null; // giữ nguyên
                            break;
                        }
                        if (input.equals("active") || input.equals("inactive")) {
                            trangThaiMoi = input;
                            break;
                        }
                        System.out.println("❌ Chỉ được nhập 'active' hoặc 'inactive'!");
                    }

                    // Nếu đổi trạng thái => yêu cầu quyền Admin và lấy lý do
                    String reason = "";
                    if (trangThaiMoi != null && !trangThaiMoi.equals(nvCanSua.getTrangThai())) {
                        if (Main.CURRENT_ACCOUNT == null || !"Admin".equals(Main.CURRENT_ACCOUNT.getRole())) {
                            System.out.println("❌ Bạn không có quyền thay đổi trạng thái nhân viên!");
                            continue;
                        }
                        if ("active".equals(trangThaiMoi) && "inactive".equalsIgnoreCase(nvCanSua.getTrangThai())) {
                            // Menu lý do kích hoạt lại
                            System.out.println(
                                    "📝 Chọn lý do kích hoạt lại (1: Quay lại sau nghỉ dài hạn, 2: Hết tạm đình chỉ/kỷ luật, 3: Hoàn tất đào tạo/bồi dưỡng, 4: Nhu cầu công việc/tái bố trí, 5: Khác)");
                            System.out.print("Lý do: ");
                            String opt = sc.nextLine().trim();
                            switch (opt) {
                                case "1":
                                    reason = "Quay lại sau nghỉ phép dài hạn";
                                    break;
                                case "2":
                                    reason = "Hết thời gian tạm đình chỉ/kỷ luật";
                                    break;
                                case "3":
                                    reason = "Hoàn tất đào tạo/bồi dưỡng bắt buộc";
                                    break;
                                case "4":
                                    reason = "Nhu cầu công việc, tái bố trí vị trí";
                                    break;
                                case "5":
                                    reason = "Khác";
                                    break;
                                default:
                                    reason = "Khác";
                            }
                        } else if ("inactive".equals(trangThaiMoi)
                                && "active".equalsIgnoreCase(nvCanSua.getTrangThai())) {
                            // Menu lý do vô hiệu hóa (active -> inactive)
                            System.out.println(
                                    "📝 Chọn lý do vô hiệu hóa (1: Nghỉ việc (chấm dứt hợp đồng), 2: Tạm nghỉ dài hạn (thai sản/ốm đau/cá nhân), 3: Vi phạm kỷ luật/quy định, 4: Tái cơ cấu/bố trí nhân sự, tạm dừng công việc, 5: Khác)");
                            System.out.print("Lý do: ");
                            String opt = sc.nextLine().trim();
                            switch (opt) {
                                case "1":
                                    reason = "Nghỉ việc (chấm dứt hợp đồng)";
                                    break;
                                case "2":
                                    reason = "Tạm nghỉ dài hạn (thai sản/ốm đau/cá nhân)";
                                    break;
                                case "3":
                                    reason = "Vi phạm kỷ luật/quy định";
                                    break;
                                case "4":
                                    reason = "Tái cơ cấu/bố trí nhân sự, tạm dừng công việc";
                                    break;
                                case "5":
                                    reason = "Khác";
                                    break;
                                default:
                                    reason = "Khác";
                            }
                        }
                    }

                    // Tạo đối tượng mới với thông tin đã sửa
                    NhanVienDTO nvMoi = new NhanVienDTO(maNV, hoMoi, tenMoi, gioiTinhMoi, ngaySinhMoi, diaChiMoi,
                            emailMoi, luongMoi, chucVuMoi);

                    // Trạng thái để cập nhật: nếu không nhập, giữ nguyên
                    String trangThaiUpdate = trangThaiMoi != null ? trangThaiMoi : nvCanSua.getTrangThai();

                    // Cập nhật thông tin nhân viên (truyền oldStatus và reason để ghi audit)
                    NhanVienDAO.suaNhanVien(nvMoi, trangThaiUpdate, nvCanSua.getTrangThai(), reason);
                    System.out.println("✅ Sửa nhân viên thành công.");
                    break;
                } catch (Exception e) {
                    System.out.println("❌ Lỗi nhập liệu: " + e.getMessage());
                    sc.nextLine();
                }
            }

            System.out.print("\n❓ Bạn có muốn sửa thông tin nhân viên khác không? (Y/N): ");
            String choice = sc.nextLine().trim();
            if (choice.equalsIgnoreCase("N")) {
                continueWithAnotherEmployee = false;
            }
        }
    }

    // ========= Xóa NHÂN VIÊN =======
    public void xoaNhanVien() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                       XÓA NHÂN VIÊN THEO MÃ                        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");

        System.out.print("📝 Nhập mã nhân viên cần xóa: ");
        String maNV = sc.nextLine().trim();

        if (maNV.isEmpty()) {
            System.out.println("❌ Mã nhân viên không được để trống!");
            return;
        }

        NhanVienDTO nv = NhanVienDAO.timNhanVienTheoMa(maNV);
        if (nv == null) {
            System.out.println("❌ Không tìm thấy nhân viên với mã: " + maNV);
            return;
        }

        // Hiển thị thông tin nhân viên cần xóa
        System.out.println("\n--- THÔNG TIN NHÂN VIÊN CẦN XÓA ---");
        inThongTinNhanVienChiTiet(nv);

        System.out.print("\n⚠️  Bạn có chắc chắn muốn xóa nhân viên này? (y/n): ");
        String confirm = sc.nextLine().trim().toLowerCase();

        if (!confirm.equals("y") && !confirm.equals("yes")) {
            System.out.println("❌ Đã hủy xóa nhân viên.");
            return;
        }

        // Chọn lý do trước khi xóa (active -> inactive)
        String reason = "Khác";
        System.out.println(
                "📝 Chọn lý do vô hiệu hóa (1: Nghỉ việc (chấm dứt hợp đồng), 2: Tạm nghỉ dài hạn (thai sản/ốm đau/cá nhân), 3: Vi phạm kỷ luật/quy định, 4: Tái cơ cấu/bố trí nhân sự, tạm dừng công việc, 5: Khác)");
        System.out.print("Lý do: ");
        String opt = sc.nextLine().trim();
        switch (opt) {
            case "1":
                reason = "Nghỉ việc (chấm dứt hợp đồng)";
                break;
            case "2":
                reason = "Tạm nghỉ dài hạn (thai sản/ốm đau/cá nhân)";
                break;
            case "3":
                reason = "Vi phạm kỷ luật/quy định";
                break;
            case "4":
                reason = "Tái cơ cấu/bố trí nhân sự, tạm dừng công việc";
                break;
            case "5":
                reason = "Khác";
                break;
            default:
                reason = "Khác";
        }

        if (NhanVienDAO.xoaNhanVien(maNV, reason)) {
            System.out.println("✅ Xóa nhân viên thành công!");
        } else {
            System.out.println("❌ Xóa nhân viên thất bại!");
        }

        System.out.print("\n⏸️  Nhấn Enter để tiếp tục...");
        sc.nextLine();
    }

    // ========= TÌM KIẾM NHÂN VIÊN =======
    public void timKiemNhanVien() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.println(
                        "\n╔════════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println(
                        "║                              TÌM KIẾM NHÂN VIÊN                                    ║");
                System.out.println(
                        "╚════════════════════════════════════════════════════════════════════════════════════╝");
                System.out.println(
                        "┃ [1] ➜ Tìm kiếm nhân viên theo mã                                                   ┃");
                System.out.println(
                        "┃ [2] ➜ Tìm kiếm nhân viên theo tên (tự động xử lý trùng tên)                        ┃");
                System.out.println(
                        "┃ [0] ➜ Thoát                                                                        ┃");
                System.out.println(
                        "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                System.out.print("\n💡 Nhập lựa chọn của bạn: ");

                int opt = sc.nextInt();
                sc.nextLine();

                if (opt == 0) {
                    System.out.println("Thoát tìm kiếm nhân viên thành công.");
                    break;
                } else if (opt == 1) {
                    timKiemNhanVienTheoMa();
                } else if (opt == 2) {
                    timKiemNhanVienTheoTen();
                } else {
                    System.out.println("❌ Lựa chọn không hợp lệ. Vui lòng nhập lại");
                }
            } catch (Exception e) {
                System.out.println("❌ Lỗi xảy ra: " + e.getMessage());
                sc.nextLine();
            }
        }
    }

    // ========= THÊM NHÂN VIÊN THEO MÃ =======
    public void timKiemNhanVienTheoMa() {
        Scanner sc = new Scanner(System.in);
        System.out.print("📝 Nhập mã nhân viên cần tìm: ");
        String maNV = sc.nextLine().trim();

        if (maNV.isEmpty()) {
            System.out.println("❌ Mã nhân viên không được để trống!");
            return;
        }

        NhanVienDTO nv = NhanVienDAO.timNhanVienTheoMa(maNV);

        if (nv == null) {
            System.out.println("❌ Không tìm thấy nhân viên với mã: " + maNV);
            return;
        }

        inThongTinNhanVienChiTiet(nv);

        System.out.print("\n⏸️  Nhấn Enter để tiếp tục...");
        sc.nextLine();
    }

    // ========= TÌM KIẾM NHÂN VIÊN THEO TÊN =======
    public void timKiemNhanVienTheoTen() {
        Scanner sc = new Scanner(System.in);
        System.out.print("📝 Nhập tên nhân viên cần tìm: ");
        String tuKhoa = sc.nextLine().trim();

        if (tuKhoa.isEmpty()) {
            System.out.println("❌ Tên nhân viên không được để trống!");
            return;
        }

        // Kiểm tra thêm để đảm bảo từ khóa có ít nhất 1 ký tự không phải khoảng trắng
        if (tuKhoa.replaceAll("\\s+", "").isEmpty()) {
            System.out.println("❌ Tên nhân viên không được chỉ chứa khoảng trắng!");
            return;
        }

        // Debug: In ra từ khóa tìm kiếm để kiểm tra
        System.out.println("🔍 Đang tìm kiếm với từ khóa: \"" + tuKhoa + "\"");

        List<NhanVienDTO> results = NhanVienDAO.timNhanVienTheoTen(tuKhoa);

        if (results.isEmpty()) {
            System.out.println("❌ Không tìm thấy nhân viên nào với từ khóa: " + tuKhoa);
            return;
        }

        // Kiểm tra số lượng kết quả để quyết định cách hiển thị
        if (results.size() == 1) {
            // Chỉ có 1 kết quả - hiển thị bình thường
            System.out.println("🔍 Tìm thấy 1 nhân viên với từ khóa: \"" + tuKhoa + "\"");
            System.out.println();
            inThongTinNhanVienChiTiet(results.get(0));
        } else {
            // Có nhiều kết quả - tự động chuyển sang tìm kiếm nâng cao
            System.out
                    .println("🔍 Tìm thấy " + results.size() + " nhân viên trùng tên với từ khóa: \"" + tuKhoa + "\"");
            System.out.println("💡 Tự động chuyển sang chế độ tìm kiếm nâng cao...");
            System.out.println();

            // Hiển thị bảng tóm tắt và cho phép chọn
            NhanVienDTO selected = hienThiBangTomTatVaChon(results);
            if (selected != null) {
                System.out.println("\n--- THÔNG TIN CHI TIẾT ---");
                inThongTinNhanVienChiTiet(selected);
            }
        }

        System.out.print("\n⏸️  Nhấn Enter để tiếp tục...");
        sc.nextLine();
    }

    // Các phương thức hiển thị/ chọn đã được gom về util.tablePrinter

    // ========= THỐNG KÊ NHÂN VIÊN ==========
    public void thongKeNhanVien() {
        Scanner sc = new Scanner(System.in);

        // Lấy thống kê từ DAO
        int[] basicStats = NhanVienDAO.layThongKeCoBan();
        int tongSoNV = basicStats[0];
        int dangLamViec = basicStats[1];
        int daNghiViec = basicStats[2];

        if (tongSoNV == 0) {
            System.out.println("❌ Không có dữ liệu nhân viên để thống kê!");
            System.out.print("\n⏸️  Nhấn Enter để tiếp tục...");
            sc.nextLine();
            return;
        }

        // Lấy lương (chính xác từ database)
        long luongTrungBinh = NhanVienDAO.layLuongTrungBinh();
        long tongQuyLuong = NhanVienDAO.layTongQuyLuong(); // Chính xác: SUM(luong) từ SQL

        // Hiển thị giao diện cải thiện
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                          📊 THỐNG KÊ TỔNG QUAN NHÂN VIÊN 📊                        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");

        System.out.println("\n┌──────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                                📈 SỐ LIỆU CHÍNH 📈                               │");
        System.out.println("└──────────────────────────────────────────────────────────────────────────────────┘");

        // Tính tỷ lệ phần trăm
        double tyLeDangLamViec = (double) dangLamViec / tongSoNV * 100;
        double tyLeDaNghiViec = (double) daNghiViec / tongSoNV * 100;

        // Định dạng giá trị (làm cho code printf gọn hơn)
        String valDangLam = String.format("%d (%.1f%%)", dangLamViec, tyLeDangLamViec);
        String valDaNghi = String.format("%d (%.1f%%)", daNghiViec, tyLeDaNghiViec);
        String valTongLuong = String.format("%,d VNĐ", tongQuyLuong);
        String valLuongTB = String.format("%,d VNĐ", luongTrungBinh);

        System.out.println("┌──────────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s %29d │%n", "📊 Tổng số nhân viên:", tongSoNV);
        System.out.printf("│ %-50s %28s │%n", "✅ Đang làm việc:", valDangLam);
        System.out.printf("│ %-50s %28s │%n", "❌ Đã nghỉ việc:", valDaNghi);
        System.out.printf("│ %-50s %29s │%n", "💰 Tổng quỹ lương (tháng):", valTongLuong);
        System.out.printf("│ %-50s %29s │%n", "💵 Lương trung bình:", valLuongTB);
        System.out.println("└──────────────────────────────────────────────────────────────────────────────────┘");

        // Menu xem chi tiết với vòng lặp
        while (true) {
            System.out
                    .println("\n┌──────────────────────────────────────────────────────────────────────────────────┐");
            System.out.println("│                           📋 MENU CHI TIẾT THỐNG KÊ 📋                           │");
            System.out.println("└──────────────────────────────────────────────────────────────────────────────────┘");
            System.out.println("┌──────────────────────────────────────────────────────────────────────────────────┐");
            System.out.printf("│ %-80s │%n", " 1. 👔 Thống kê theo chức vụ (NV/QL)");
            System.out.printf("│ %-80s │%n", " 2. ⚧ Thống kê theo giới tính (Nam/Nữ)");
            System.out.printf("│ %-80s │%n", " 0. 🔙 Quay lại menu chính");
            System.out.println("└──────────────────────────────────────────────────────────────────────────────────┘");
            System.out.print("\n➤ Nhập lựa chọn: ");

            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("❌ Lựa chọn phải là một con số!");
                sc.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    hienThiBieuDoChucVu();
                    System.out.print("\n⏸️  Nhấn Enter để tiếp tục...");
                    sc.nextLine();
                    break;
                case 2:
                    hienThiBieuDoGioiTinh();
                    System.out.print("\n⏸️  Nhấn Enter để tiếp tục...");
                    sc.nextLine();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ! Vui lòng chọn từ 0 đến 2.");
            }
        }
    }

    // Hiển thị biểu đồ chức vụ
    private void hienThiBieuDoChucVu() {
        int[] positionStats = NhanVienDAO.layThongKeTheoChucVu();
        int soNV = positionStats[0];
        int soQL = positionStats[1];
        int tongSo = soNV + soQL;

        if (tongSo == 0) {
            System.out.println("❌ Không có dữ liệu chức vụ!");
            return;
        }

        double tyLeNV = (double) soNV / tongSo * 100;
        double tyLeQL = (double) soQL / tongSo * 100;

        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                           👔 THỐNG KÊ THEO CHỨC VỤ 👔                              ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");

        System.out.println("┌──────────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf("│ 👷 Nhân viên (NV): %-61s │%n", String.format("%d người (%.1f%%)", soNV, tyLeNV));
        System.out.printf("│ 👨‍💼 Quản lý (QL): %-61s │%n", String.format("%d người (%.1f%%)", soQL, tyLeQL));
        System.out.println("└──────────────────────────────────────────────────────────────────────────────────┘");

    }

    // Hiển thị biểu đồ giới tính
    private void hienThiBieuDoGioiTinh() {
        int[] genderStats = NhanVienDAO.layThongKeTheoGioiTinh();
        int soNam = genderStats[0];
        int soNu = genderStats[1];
        int tongSo = soNam + soNu;

        if (tongSo == 0) {
            System.out.println("❌ Không có dữ liệu giới tính!");
            return;
        }

        double tyLeNam = (double) soNam / tongSo * 100;
        double tyLeNu = (double) soNu / tongSo * 100;

        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                            ⚧ THỐNG KÊ THEO GIỚI TÍNH ⚧                             ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");

        System.out.println("┌──────────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf("│ 👨 Nam: %-72s │%n", String.format("%d người (%.1f%%)", soNam, tyLeNam));
        System.out.printf("│ 👩 Nữ: %-73s │%n", String.format("%d người (%.1f%%)", soNu, tyLeNu));
        System.out.println("└──────────────────────────────────────────────────────────────────────────────────┘");

    }

    // ========= IN TOÀN BỘ NHÂN VIÊN ===========
    public void xemDanhSachNhanVien() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        DANH SÁCH NHÂN VIÊN                         ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");

        List<NhanVienDTO> danhSachNV = NhanVienDAO.getAllNhanVien();

        if (danhSachNV.isEmpty()) {
            System.out.println("❌ Không có nhân viên nào trong hệ thống!");
            return;
        }

        System.out.println("📊 Tổng số nhân viên: " + danhSachNV.size());
        System.out.println();

        // // Header bảng
        // System.out.println(
        // "┌─────┬──────────┬─────────────────────┬────────┬────────────┬─────────────────────┬──────────────┬────────┬────────────┐");
        // System.out.println(
        // "│ STT │ Mã NV │ Họ và tên │ Giới tính │ Ngày sinh │ Email │ Lương │ Chức vụ
        // │ Trạng thái │");
        // System.out.println(
        // "├─────┼──────────┼─────────────────────┼────────┼────────────┼─────────────────────┼──────────────┼────────┼────────────┤");

        // int count = 1;
        // for (NhanVienDTO nv : danhSachNV) {
        // String stt = String.format("%3d", count);
        // String maNV = String.format("%-8s", nv.getMaNV());
        // String hoTen = String.format("%-19s",
        // nv.getFullName().length() > 19 ? nv.getFullName().substring(0, 16) + "..." :
        // nv.getFullName());
        // String gioiTinh = String.format("%-6s", nv.getGioiTinh());
        // String ngaySinh = String.format("%-10s", nv.getNgaySinh() != null ?
        // nv.getNgaySinhFormat() : "N/A");
        // String email = String.format("%-19s",
        // nv.getEmail().length() > 19 ? nv.getEmail().substring(0, 16) + "..." :
        // nv.getEmail());
        // String luong = String.format("%-12s", String.format("%,d VNĐ",
        // nv.getLuong()));
        // String chucVu = String.format("%-6s", nv.getChucVu());
        // String trangThai = String.format("%-10s", nv.getTrangThai() != null ?
        // nv.getTrangThai() : "N/A");

        // System.out.printf("│%s│ %s │ %s │ %s │ %s │ %s │ %s │ %s │ %s │%n",
        // stt, maNV, hoTen, gioiTinh, ngaySinh, email, luong, chucVu, trangThai);
        // count++;
        // }

        // System.out.println(
        // "└─────┴──────────┴─────────────────────┴────────┴────────────┴─────────────────────┴──────────────┴────────┴────────────┘");
        List<String> headers = List.of("STT", "Mã NV", "Họ và tên", "Giới tính", "Ngày sinh", "Email", "Lương",
                "Chức vụ", "Trạng thái");
        List<List<String>> rows = new java.util.ArrayList<>();
        for (NhanVienDTO nv : danhSachNV) {
            List<String> row = new java.util.ArrayList<>();
            row.add(String.valueOf(rows.size() + 1));
            row.add(nv.getMaNV());
            row.add(nv.getFullName());
            row.add(nv.getGioiTinh());
            row.add(nv.getNgaySinh() != null ? nv.getNgaySinhFormat() : "N/A");
            row.add(nv.getEmail());
            row.add(String.format("%,d VNĐ", nv.getLuong()));
            row.add(nv.getChucVu());
            row.add(nv.getTrangThai() != null ? nv.getTrangThai() : "N/A");
            rows.add(row);
        }
        tablePrinter.printTable(headers, rows);
        System.out.print("\n⏸️  Nhấn Enter để tiếp tục...");
        sc.nextLine();
    }

    // ========= HIỂN THỊ THÔNG TIN CHI TIẾT NHÂN VIÊN ==============
    /**
     * In chi tiết đầy đủ một nhân viên theo định dạng đẹp.
     */
    public static void inThongTinNhanVienChiTiet(NhanVienDTO nv) {
        if (nv == null) {
            System.out.println("❌ Nhân viên không tồn tại!");
            return;
        }
        int contentWidth = 60;
        String maNV = nv.getMaNV();
        String hoTen = nv.getFullName();
        String gioiTinh = nv.getGioiTinh();
        String ngaySinh = nv.getNgaySinh() != null ? nv.getNgaySinhFormat() : "Không có";
        String diaChi = nv.getDiaChi() != null ? nv.getDiaChi() : "Không có";
        String email = nv.getEmail();
        String luong = String.format("%,d VNĐ", nv.getLuong());
        String chucVu = nv.getChucVu();
        String trangThai = nv.getTrangThai() != null ? nv.getTrangThai() : "Không có";

        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                 THÔNG TIN NHÂN VIÊN                                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");

        System.out.println("┌──────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 📋 Mã nhân viên   │ " + String.format("%-" + contentWidth + "s", maNV) + " │");
        System.out.println("│ 👤 Họ và tên      │ " + String.format("%-" + contentWidth + "s", hoTen) + " │");
        System.out.println("│ ⚧ Giới tính       │ " + String.format("%-" + contentWidth + "s", gioiTinh) + " │");
        System.out.println("│ 🎂 Ngày sinh      │ " + String.format("%-" + contentWidth + "s", ngaySinh) + " │");
        System.out.println("│ 🏠 Địa chỉ        │ " + String.format("%-" + contentWidth + "s", diaChi) + " │");
        System.out.println("│ 📧 Email          │ " + String.format("%-" + contentWidth + "s", email) + " │");
        System.out.println("│ 💰 Lương          │ " + String.format("%-" + contentWidth + "s", luong) + " │");
        System.out.println("│ 💼 Chức vụ        │ " + String.format("%-" + contentWidth + "s", chucVu) + " │");
        System.out.println("│ 🚦 Trạng thái     │ " + String.format("%-" + contentWidth + "s", trangThai) + " │");
        System.out.println("└──────────────────────────────────────────────────────────────────────────────────┘");
    }

    /**
     * In bảng tóm tắt danh sách nhân viên (STT, Mã, Họ tên, Giới tính, Chức vụ)
     */
    // ========= HIỂN THỊ LỰA CHỌN NHÂN VIÊN TỪ TÌM KIẾM NV BẰNG TÊN =======
    public static void inBangTomTatNhanVien(List<NhanVienDTO> results) {
        if (results == null || results.isEmpty()) {
            System.out.println("❌ Không có nhân viên để hiển thị!");
            return;
        }

        System.out.println("┌─────┬──────────┬────────────────────────────────────────┬───────────┬─────────┐");
        System.out.println("│ STT │ Mã NV    │ Họ và tên                              │ Giới tính │ Chức vụ │");
        System.out.println("├─────┼──────────┼────────────────────────────────────────┼───────────┼─────────┤");

        for (int i = 0; i < results.size(); i++) {
            NhanVienDTO nv = results.get(i);

            String stt = String.format(" %-3s ", i + 1);
            String maNV = String.format(" %-8s ", nv.getMaNV());
            String gioiTinh = String.format(" %-9s ", nv.getGioiTinh());
            String chucVu = String.format(" %-7s ", nv.getChucVu());

            String fullName = nv.getFullName();

            String hoTenFormatted = fullName.length() > 38
                    ? fullName.substring(0, 35) + "..."
                    : fullName;

            String hoTen = String.format(" %-38s ", hoTenFormatted);

            System.out.printf("│%s│%s│%s│%s│%s│%n",
                    stt, maNV, hoTen, gioiTinh, chucVu);
        }

        System.out.println("└─────┴──────────┴────────────────────────────────────────┴───────────┴─────────┘");
    }

    /**
     * Hiển thị bảng tóm tắt và cho phép người dùng chọn 1 nhân viên.
     * Trả về nhân viên đã chọn hoặc null nếu hủy/chọn sai.
     */
    public static NhanVienDTO hienThiBangTomTatVaChon(List<NhanVienDTO> results) {
        if (results == null || results.isEmpty()) {
            System.out.println("❌ Không có nhân viên để chọn!");
            return null;
        }

        inBangTomTatNhanVien(results);

        System.out.println("\n💡 Chọn nhân viên để xem chi tiết:");
        System.out.print("📝 Nhập số thứ tự (1-" + results.size() + ") hoặc 0 để thoát: ");

        try {
            int choice = Integer.parseInt(STDIN.nextLine().trim());
            if (choice == 0) {
                System.out.println("❌ Đã hủy.");
                return null;
            }
            if (choice >= 1 && choice <= results.size()) {
                return results.get(choice - 1);
            }
            System.out.println("❌ Lựa chọn không hợp lệ!");
        } catch (NumberFormatException e) {
            System.out.println("❌ Vui lòng nhập số hợp lệ!");
        }
        return null;
    }

    // ========= KIỂM TRA DỮ LIỆU ĐẦU VÀO KHI SỬA NHÂN VIÊN =======
    // Helper methods cho suaNhanVien
    private String nhapVoiGiuNguyen(Scanner sc, String label, String giaTriCu) {
        System.out.print("📝 " + label + " (Enter để giữ nguyên): ");
        String input = sc.nextLine().trim();
        return input.isEmpty() ? giaTriCu : input;
    }

    private String nhapGioiTinhVoiGiuNguyen(Scanner sc, String giaTriCu) {
        while (true) {
            System.out.print("📝 Giới tính mới (Nam/Nu) - Enter để giữ nguyên: ");
            String input = sc.nextLine().trim();
            if (input.isEmpty())
                return giaTriCu;
            if (input.equals("Nam") || input.equals("Nu"))
                return input;
            System.out.println("❌ Chỉ được nhập 'Nam' hoặc 'Nu'!");
        }
    }

    private LocalDate nhapNgaySinhVoiGiuNguyen(Scanner sc, LocalDate giaTriCu) {
        while (true) {
            System.out.print("📝 Ngày sinh mới (dd/MM/yyyy) - Enter để giữ nguyên: ");
            String input = sc.nextLine().trim();
            if (input.isEmpty())
                return giaTriCu;
            try {
                return LocalDate.parse(input, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                System.out.println("❌ Định dạng ngày không đúng! (dd/MM/yyyy)");
            }
        }
    }

    private String nhapEmailVoiGiuNguyen(Scanner sc, String giaTriCu) {
        while (true) {
            System.out.print("📝 Email mới (Enter để giữ nguyên): ");
            String input = sc.nextLine().trim();
            if (input.isEmpty())
                return giaTriCu;
            if (input.contains("@"))
                return input;
            System.out.println("❌ Email không hợp lệ!");
        }
    }

    private int nhapLuongVoiGiuNguyen(Scanner sc, int giaTriCu) {
        while (true) {
            System.out.print("📝 Lương mới (Enter để giữ nguyên): ");
            String input = sc.nextLine().trim();
            if (input.isEmpty())
                return giaTriCu;
            try {
                int luong = Integer.parseInt(input);
                if (luong >= 0)
                    return luong;
                System.out.println("❌ Lương phải >= 0!");
            } catch (NumberFormatException e) {
                System.out.println("❌ Lương phải là số nguyên!");
            }
        }
    }

    private String nhapChucVuVoiGiuNguyen(Scanner sc, String giaTriCu) {
        while (true) {
            System.out.print("📝 Chức vụ mới (QL/NV) - Enter để giữ nguyên: ");
            String input = sc.nextLine().trim();
            if (input.isEmpty())
                return giaTriCu;
            if (input.equals("QL") || input.equals("NV"))
                return input;
            System.out.println("❌ Chỉ được nhập 'QL' hoặc 'NV'!");
        }
    }

}