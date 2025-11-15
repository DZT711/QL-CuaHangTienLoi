package view;

import java.util.Scanner;
import dao.KhachHangDAO;
import dto.KhachHangDTO;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import util.ValidatorUtil;
import util.tablePrinter;
public class QuanLyKhachHang {
    public void menuQuanLyKhachHang() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n████████████████████████████████████████████████████████████████████████████████");
            System.out.println("██                                                                            ██");
            System.out.println("██                         HỆ THỐNG QUẢN LÝ KHÁCH HÀNG                        ██");
            System.out.println("██                                                                            ██");
            System.out.println("████████████████████████████████████████████████████████████████████████████████");
            System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓ MENU CHỨC NĂNG ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
            System.out.println("▒ [1] ➜ Thêm khách hàng                                                        ▒");
            System.out.println("▒ [2] ➜ Chỉnh sửa thông tin khách hàng                                         ▒");
            System.out.println("▒ [3] ➜ Xóa khách hàng khỏi danh sách                                          ▒");
            System.out.println("▒ [4] ➜ Tìm kiếm khách hàng                                                    ▒");
            System.out.println("▒ [5] ➜ Thống kê khách hàng                                                    ▒");
            System.out.println("▒ [6] ➜ Xem danh sách khách hàng                                               ▒");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ");
            System.out.println("░ [0] ✗ Quay lại menu chính                                                    ░");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ");
            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            int choice = -1;

            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 0 && choice <= 6) break;
                    System.out.print("Vui lòng nhập số trong khoảng 0–6: ");
                } else {
                    System.out.print("Nhập không hợp lệ. Vui lòng nhập lại: ");
                    scanner.next();
                }
            }

            switch (choice) {
                case 1:
                    System.out.println("\n");
                    System.out.println("    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                    System.out.println("    ┃                           THÊM KHÁCH HÀNG                          ┃");
                    System.out.println("    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    System.out.println("    ┃ [1] ➜ Thêm 1 khách hàng                                            ┃");
                    System.out.println("    ┃ [2] ➜ Thêm danh sách khách hàng                                    ┃");
                    System.out.println("    ┃ [0] ➜ Thoát                                                        ┃");
                    System.out.println("    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                    while (true) {
                        String opt = scanner.nextLine().trim();

                        switch (opt) {
                            case "0":
                                System.out.println("Thoát thêm khách hàng thành công.");
                                break;
                            case "1":
                                them();
                                break;
                            case "2":
                                System.out.println("\n╔════════════════════════════════════════════════════╗");
                                System.out.println("║            IMPORT DANH SÁCH KHÁCH HÀNG             ║");
                                System.out.println("╚════════════════════════════════════════════════════╝");
                                KhachHangDAO.importDSKH("data/khachhang.txt");
                                break;
                            default:
                                System.out.print("Lựa chọn không hợp lệ. Vui lòng nhập lại: ");
                                continue;
                        }
                        break;
                    }
                    break;
                case 2:
                    sua();
                    break;
                case 3:
                    xoa();
                    break;
                case 4:
                    System.out.println("\n");
                    System.out.println("    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                    System.out.println("    ┃                           TÌM KIẾM KHÁCH HÀNG                      ┃");
                    System.out.println("    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    System.out.println("    ┃ [1] ➜ Tìm kiếm khách hàng theo mã khách hàng                       ┃");
                    System.out.println("    ┃ [2] ➜ Tìm kiếm khách hàng theo tên                                 ┃");
                    System.out.println("    ┃ [3] ➜ Tìm kiếm khách hàng theo số điện thoại                       ┃");
                    System.out.println("    ┃ [0] ➜ Thoát                                                        ┃");
                    System.out.println("    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                    while (true) {
                        String opt = scanner.nextLine().trim();

                        switch (opt) {
                            case "0":
                                System.out.println("Thoát tìm kiếm khách hàng thành công.");
                                break;
                            case "1":
                                timKiemTheoMa();
                                break;
                            case "2":
                                timKiemTheoTen();
                                break;
                            case "3":
                                timKiemTheoSDT();
                                break;
                            default: 
                                System.out.print("Lựa chọn không hợp lệ. Vui lòng nhập lại: ");
                                continue;
                        }
                        break;
                    }
                    break;
                case 5: 
                    System.out.println("\n");
                    System.out.println("    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                    System.out.println("    ┃                           THỐNG KÊ KHÁCH HÀNG                      ┃");
                    System.out.println("    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    System.out.println("    ┃ [1] ➜ Thống kê khách hàng theo giới tính                           ┃");
                    System.out.println("    ┃ [2] ➜ Thống kê khách hàng theo độ tuổi                             ┃");
                    System.out.println("    ┃ [3] ➜ Thống kê khách hàng theo số lượng hóa đơn                    ┃");
                    System.out.println("    ┃ [4] ➜ Thống kê khách hàng theo tổng chi tiêu                       ┃");
                    System.out.println("    ┃ [0] ➜ Thoát                                                        ┃");
                    System.out.println("    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                    while (true) {
                        String opt = scanner.nextLine().trim();

                        switch (opt) {
                            case "0":
                                System.out.println("Thoát thống kê khách hàng thành công.");
                                break;
                            case "1":
                                KhachHangDAO.thongKeTheoGioiTinh();
                                break;
                            case "2":
                                KhachHangDAO.thongKeTheoDoTuoi();
                                break;
                            case "3":
                                KhachHangDAO.thongKeTheoSohd();
                                break;
                            case "4":
                                KhachHangDAO.thongKeTheoTongChiTieu();
                                break;
                            default:
                                System.out.print("Lựa chọn không hợp lệ. Vui lòng nhập lại: ");
                                continue;
                        }
                        break;
                    }
                    break;
                case 6:
                    xuat();
                    break;
                case 0:
                    System.out.println("Thoát khỏi menu quản lý khách hàng.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }
        }
    }

    public void them() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║                 THÊM KHÁCH HÀNG MỚI                ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        try {
            String maKH = KhachHangDAO.generateIDKhachHang();
            System.out.println("📋 Mã khách hàng tự động: " + maKH + "\n");
            
            KhachHangDTO kh = new KhachHangDTO();
            kh.setMaKH(maKH);
            
            if (!kh.nhapThongTinKhachHang()) {
                System.out.println("\n⚠️  Đã hủy thêm khách hàng.");
                return;
            }
            
            System.out.println("\n📝 THÔNG TIN KHÁCH HÀNG VỪA NHẬP:");
            kh.inThongTinKhachHang();
            
            Scanner scanner = new Scanner(System.in);
            System.out.print("\n→ Xác nhận thêm khách hàng? (Y/N): ");
            String confirm = scanner.nextLine().trim();
            
            if (!"Y".equalsIgnoreCase(confirm)) {
                System.out.println("⚠️  Đã hủy thêm khách hàng.");
                return;
            }
            
            if (KhachHangDAO.themKhachHang(kh)) {
                System.out.println("\n✅ Thêm khách hàng thành công!");
                System.out.println("📋 Mã khách hàng: " + maKH);
            } else {
                System.out.println("\n❌ Thêm khách hàng thất bại!");
            }
        
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi thêm khách hàng: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sua() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║             SỬA THÔNG TIN KHÁCH HÀNG               ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println("Nhập Enter để bỏ qua hoặc '0' để thoát chức năng");
        
        while (true) {

            System.out.print("→ Nhập mã khách hàng cần sửa (hoặc '0' để thoát): ");
            String maKH = scanner.nextLine().trim();
            
            if ("0".equals(maKH)) {
                System.out.println("✓ Thoát chức năng sửa khách hàng.");
                break;
            }

            if (maKH.isEmpty()) {
                System.out.println("❌ Mã khách hàng không được để trống!");
                continue;
            }
            
            KhachHangDTO kh = KhachHangDAO.timKhachHangTheoMa(maKH);
            if (kh == null) {
                System.out.println("❌ Mã khách hàng không tồn tại!");
                System.out.print("Bạn có muốn thử lại không? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) break;
                continue;
            }

            System.out.println("\n📝 THÔNG TIN HIỆN TẠI:");
            kh.inThongTinKhachHang();
            
            if (!kh.suaThongTinKhachHang()) {
                System.out.println("⚠️  Đã hủy sửa khách hàng.");
                System.out.print("\n→ Bạn có muốn sửa khách hàng khác? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) break;
                continue;
            }

            System.out.println("\n📝 THÔNG TIN SAU KHI SỬA:");
            kh.inThongTinKhachHang();
            
            System.out.print("\n→ Xác nhận lưu thay đổi? (Y/N): ");
            String confirm = scanner.nextLine().trim().toUpperCase();
            if (!"Y".equals(confirm)) {
                System.out.println("⚠️  Đã hủy cập nhật.");
                System.out.print("\n→ Bạn có muốn sửa khách hàng khác? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) break;
                continue;
            }
            
            if (KhachHangDAO.suaKhachHang(kh)) {
                System.out.println("✅ Cập nhật khách hàng thành công!");
            } else {
                System.out.println("❌ Cập nhật khách hàng thất bại!");
            }
            
            System.out.print("\n→ Bạn có muốn sửa khách hàng khác? (Y/N): ");
            if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) break;
        }
    }

    public void xoa() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║                    XÓA KHÁCH HÀNG                  ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
    
        while (true) {
            
            System.out.print("→ Nhập mã khách hàng cần xóa (hoặc '0' để thoát): ");
            String maKH = scanner.nextLine().trim();
            
            if ("0".equals(maKH)) {
                System.out.println("✅ Thoát xóa khách hàng.");
                break;
            }

            if (maKH.isEmpty()) {
                System.out.println("❌ Mã khách hàng không được để trống!");
                continue;
            }
            
            KhachHangDTO kh = KhachHangDAO.timKhachHangTheoMa(maKH);
            if (kh == null) {
                System.out.println("❌ Mã khách hàng không tồn tại!");
                System.out.print("Bạn có muốn thử lại không? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) break;
                continue;
            }

            if ("inactive".equals(kh.getTrangThai())) {
                System.out.println("\n ⚠️ Khách hàng này đã bị vô hiệu hóa trước đó.");
                System.out.print("→ Bạn có muốn kích hoạt lại không? (Y/N): ");
                if ("Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                    kh.setTrangThai("active");
                    if (KhachHangDAO.suaKhachHang(kh)) {
                        System.out.println("\n✅ Kích hoạt lại khách hàng thành công!");
                    } else {
                        System.out.println("\n❌ Kích hoạt lại khách hàng thất bại!");
                    }
                }
                continue;
            }

            System.out.println("\n⚠️  THÔNG TIN KHÁCH HÀNG SẼ BỊ XÓA:");
            kh.inThongTinKhachHang();
            
            System.out.print("\n⚠️  Bạn có chắc chắn muốn xóa khách hàng này? (Y/N): ");

            if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                System.out.println("✓ Đã hủy xóa khách hàng.");
                continue;
            }
            
            System.out.print("⚠️  Xác nhận lần 2 (nhập mã KH để xác nhận): ");
            
            if (!maKH.equals(scanner.nextLine().trim())) {
                System.out.println("❌ Mã xác nhận không khớp! Đã hủy xóa.");
                continue;
            }
            
            if (KhachHangDAO.xoaKhachHang(maKH)) {
                System.out.println("\n✅ Xóa khách hàng thành công!");
                System.out.println("💡 Bạn có thể kích hoạt lại khách hàng này bất cứ lúc nào.");
            } else {
                System.out.println("\n❌ Xóa khách hàng thất bại!");
            }
            
            System.out.print("\n💡Bạn có muốn xóa khách hàng khác? (Y/N): ");
            if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                System.out.println("✅ Thoát xóa khách hàng.");
                break;
            }
        }
    }

    public void timKiemTheoMa() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║                TÌM KHÁCH HÀNG THEO MÃ              ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        while (true) {
            System.out.print("→ Nhập mã khách hàng (hoặc '0' để thoát): ");
            String maKH = scanner.nextLine().trim();
    
            if ("0".equals(maKH)) {
                System.out.println("✓ Thoát tìm kiếm.");
                break;
            }
    
            if (!ValidatorUtil.isValidString(maKH)) {
                System.out.println("❌ Mã khách hàng không hợp lệ!");
                System.out.print("Bạn có muốn thử lại không? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) break;
                continue;
            }
            
            KhachHangDTO kh = KhachHangDAO.timKhachHangTheoMa(maKH);
    
            if (kh != null) {
                System.out.println("\n✅ Tìm thấy khách hàng: " + maKH);
                kh.inThongTinKhachHang();
            } else {
                System.out.println("❌ Không tìm thấy khách hàng với mã: " + maKH);
            }

            System.out.print("\n→ Bạn có muốn tìm khách hàng khác? (Y/N): ");
            if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) break;
        }
    }

    public void timKiemTheoTen() {
        Scanner scanner = new Scanner(System.in);

            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║               TÌM KHÁCH HÀNG THEO TÊN              ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
        while (true) {
            System.out.print("→ Nhập tên khách hàng cần tìm (hoặc '0' để thoát): ");
            String tenKH = scanner.nextLine().trim();
            
            if ("0".equals(tenKH)) {
                System.out.println("✓ Thoát tìm kiếm.");
                break;
            }
            
            if (!ValidatorUtil.isValidString(tenKH)) {
                System.out.println("❌ Tên khách hàng không hợp lệ!");
                System.out.print("Bạn có muốn thử lại không? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) break;
                continue;
            }
            
            List<KhachHangDTO> danhSach = KhachHangDAO.timKhachHangTheoTen(tenKH);

            if (danhSach.isEmpty()) {
                System.out.println("\n❌ Không tìm thấy khách hàng nào với từ khóa: \"" + tenKH + "\"");
            } else {
                System.out.println("\n✅ Tìm thấy " + danhSach.size() + " khách hàng:");
                System.out.println("═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
                System.out.printf("%-10s | %-15s | %-20s | %-10s | %-12s | %-12s | %-30s | %-12s%n",
                        "Mã KH", "Họ", "Tên", "Giới tính", "Ngày sinh", "Điện thoại", "Địa chỉ", "Trạng thái");
                System.out.println("═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");

                for (KhachHangDTO kh : danhSach) {
                    String ngaySinhStr = (kh.getNgaySinh() != null) ? 
                                    kh.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A";
                
                    String diaChiStr = (kh.getDiaChi() != null && !kh.getDiaChi().isEmpty()) ? kh.getDiaChi() : "N/A";
                    
                    String trangThaiStr = (kh.getTrangThai().equals("active")) ? "Dang hoạt động" : "Không hoạt động";

                    System.out.printf("%-10s | %-15s | %-20s | %-10s | %-12s | %-12s | %-30s | %-12s%n",
                        kh.getMaKH(),
                        kh.getHo(),
                        kh.getTen(),
                        kh.getGioiTinh(),
                        ngaySinhStr,
                        kh.getDienThoai(),
                        diaChiStr, 
                        trangThaiStr
                    );
                }

                System.out.println("═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
                System.out.println("📊 Tổng số: " + danhSach.size() + " khách hàng");
            }
            System.out.print("\n→ Bạn có muốn tìm khách hàng khác? (Y/N): ");
            String choice = scanner.nextLine().trim().toUpperCase();
            if (!"Y".equals(choice)) break;
        }
    }

    public void timKiemTheoSDT() {
        Scanner scanner = new Scanner(System.in);
    
        while (true) {
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║          TÌM KHÁCH HÀNG THEO SỐ ĐIỆN THOẠI         ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            
            System.out.print("→ Nhập số điện thoại (hoặc '0' để thoát): ");
            String soDienThoai = scanner.nextLine().trim();
            
            if ("0".equals(soDienThoai)) {
                System.out.println("✓ Thoát tìm khách hàng theo số điện thoại.");
                break;
            }
            
            if (!ValidatorUtil.isValidPhoneNumber(soDienThoai)) {
                System.out.print("Bạn có muốn thử lại không? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) break;
                continue;
            }
            
            KhachHangDTO kh = KhachHangDAO.timKhachHangTheoDienThoai(soDienThoai);
            
            if (kh == null) {
                System.out.println("\n❌ Không tìm thấy khách hàng với số điện thoại: " + soDienThoai);
            } else {
                System.out.println("\n✅ Tìm thấy khách hàng với số điện thoại: " + soDienThoai);
                kh.inThongTinKhachHang();
            }
            
            System.out.print("\n→ Tìm khách hàng khác? (Y/N): ");
            String choice = scanner.nextLine().trim().toUpperCase();
            if (!"Y".equals(choice)) {
                break;
            }
        }   
    }

    public void xuat() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║                 DANH SÁCH KHÁCH HÀNG               ║");
        System.out.println("╚════════════════════════════════════════════════════╝\n");

        List<KhachHangDTO> list = KhachHangDAO.getAllKhachHang();

        if (list.isEmpty()) {
            System.out.println("❌ Không có khách hàng nào trong hệ thống.");
            return;
        }
        
        List<String> headers = List.of(
            "Mã KH", "Họ", "Tên", "Giới tính", "Ngày sinh", "Điện thoại", "Địa chỉ", "Trạng thái"
        );
        
        List<List<String>> rows = new ArrayList<>();
        for (KhachHangDTO kh : list) {
            List<String> row = new ArrayList<>();
            row.add(kh.getMaKH());
            row.add(kh.getHo());
            row.add(kh.getTen());
            row.add(kh.getGioiTinh());
            
            String ngaySinhStr = (kh.getNgaySinh() != null) ? 
                                kh.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A";
            row.add(ngaySinhStr);

            row.add(kh.getDienThoai());
            
            String diaChiStr = (kh.getDiaChi() != null && !kh.getDiaChi().isEmpty()) ? kh.getDiaChi() : "N/A";
            row.add(diaChiStr);
            
            String trangThaiStr = (kh.getTrangThai().equals("active")) ? "Đang hoạt động" : "Không hoạt động";
            row.add(trangThaiStr);
            rows.add(row);
        }
        tablePrinter.printTable(headers, rows);
        System.out.println("📊 Tổng số khách hàng: " + list.size());
    }
}