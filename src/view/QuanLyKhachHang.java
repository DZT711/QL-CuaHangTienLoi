package view;

import java.util.InputMismatchException;
import java.util.Scanner;
import dao.KhachHangDAO;
import dto.KhachHangDTO;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
                                System.out.println("║           IMPORT KHÁCH HÀNG TỪ FILE               ║");
                                System.out.println("╚════════════════════════════════════════════════════╝");
                                /*
                                    System.out.print("→ Nhập đường dẫn file (VD: D:\\data\\khachhang.txt): ");
                                    String filePath = scanner.nextLine().trim();
                                    
                                    File file = new File(filePath);
                                    if (!file.exists()) {
                                        System.out.println("❌ File không tồn tại: " + filePath);
                                        return;
                                    }
                                */
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
                    while (true) {
                        try {
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

                            int opt = scanner.nextInt();
                            scanner.nextLine();

                            if (opt == 0) {
                                System.out.println("Thoát tìm kiếm khách hàng thành công.");
                                break;
                            } else if (opt == 1) {
                                System.out.print("Nhập mã khách hàng cần tìm: ");
                                try {
                                    String maKH = scanner.nextLine().trim();
                                    // scanner.nextLine();
                                    KhachHangDTO kh = KhachHangDAO.timKhachHangTheoMa(maKH);
                                    if (kh != null) {
                                        System.out.println("Thông tin khách hàng tìm thấy với mã: " + maKH);
                                        System.out.println(kh.toString());
                                    } else {
                                        System.out.println("Không tìm thấy khách hàng với mã: " + maKH);
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("Lỗi: Vui lòng nhập mã khách hàng hợp lệ");
                                    scanner.nextLine();
                                }
                            } else if (opt == 2) {
                                System.out.print("Nhập tên khách hàng cần tìm: ");
                                String tenKH = scanner.nextLine().trim().toLowerCase();
                                int count = 0;
                                List<KhachHangDTO> list = KhachHangDAO.timKhachHangTheoTen(tenKH);
                                if (!list.isEmpty()) {
                                    System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
                                    System.out.println("║                                      DANH SÁCH KHÁCH HÀNG CÓ TÊN CHỨA '" + tenKH + "'                                   ║");
                                    System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
                                    System.out.printf("║ %-10s │ %-20s │ %-12s │ %-10s │ %-10s │ %-10s │ %-20s ║\n",
                                                "Mã KH", "Họ", "Tên", "Giới tính", "Ngày sinh", "SĐT", "Địa chỉ");
                                    System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
                                    
                                    for (KhachHangDTO kh : list) {
                                        System.out.printf("║ %-10s │ %-20s │ %-12s │ %-10s │ %-10s │ %-10s │ %-20s ║\n",
                                                kh.getMaKH(),
                                                kh.getHo(),
                                                kh.getTen(),
                                                kh.getGioiTinh(),
                                                kh.getNgaySinhFormat(), 
                                                kh.getDienThoai(),
                                                kh.getDiaChi());
                                        count++;
                                    }
                                    System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
                                    System.out.println("Tìm thấy " + count + " khách hàng có tên chứa '" + tenKH + "'");
                                } else {
                                    System.out.println("Không tìm thấy khách hàng có tên chứa '" + tenKH + "'");
                                }
                            } else if (opt == 3) {
                                System.out.println("Nhập số điện thoại khách hàng cần tìm: ");
                                try {
                                    String dienThoai = scanner.nextLine().trim();
                                    // scanner.nextLine();
                                    KhachHangDTO kh = KhachHangDAO.timKhachHangTheoDienThoai(dienThoai);
                                    if (kh != null) {
                                        System.out.println("Thông tin khách hàng tìm thấy với số điện thoại: " + dienThoai);
                                        System.out.println(kh.toString());
                                    } else {
                                        System.out.println("Không tìm thấy khách hàng với số điện thoại: " + dienThoai);
                                    }

                                } catch (InputMismatchException e) {
                                    System.out.println("Lỗi: Vui lòng nhập số điện thoại hợp lệ");
                                    scanner.nextLine();
                                }

                                break;
                            } else {
                                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại");
                            }
                        } catch (Exception e) {
                            System.out.println("Lỗi xảy ra: " + e.getMessage());
                            scanner.nextLine();
                        }
                    }
                    break;
                case 5: 
                    while (true) {
                        try {
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

                            int opt = scanner.nextInt();
                            scanner.nextLine();

                            if (opt == 0) {
                                System.out.println("Thoát thống kê khách hàng thành công.");
                                break;
                            } else if (opt == 1) {
                                KhachHangDAO.thongKeTheoGioiTinh();
                            } else if (opt == 2) {
                                KhachHangDAO.thongKeTheoDoTuoi();
                            } else if (opt == 3) {
                                KhachHangDAO.thongKeTheoSohd();
                            } else if (opt == 4) {
                                KhachHangDAO.thongKeTheoTongChiTieu();
                            } else {
                                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại");
                            }
                        } catch (Exception e) {
                            System.out.println("Lỗi xảy ra: " + e.getMessage());
                            scanner.nextLine();
                        }
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
        System.out.println("║           THÊM KHÁCH HÀNG MỚI                     ║");
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
            hienThiThongTinKhachHang(kh);
            
            Scanner scanner = new Scanner(System.in);
            System.out.print("\n→ Xác nhận thêm khách hàng? (Y/N): ");
            String confirm = scanner.nextLine().trim().toUpperCase();
            
            if (!"Y".equals(confirm)) {
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
            System.err.println("❌ Đã xảy ra lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sua() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║           SỬA THÔNG TIN KHÁCH HÀNG                ║");
            System.out.println("╚════════════════════════════════════════════════════╝");

            System.out.print("→ Nhập mã khách hàng cần sửa (hoặc '0' để thoát): ");
            String maKH = scanner.nextLine().trim();
            
            if ("0".equals(maKH)) {
                System.out.println("✓ Thoát sửa khách hàng.");
                break;
            }

            if (!KhachHangDAO.kiemTraMaKH(maKH)) {
                System.out.println("❌ Mã khách hàng không tồn tại! Vui lòng nhập lại.");
                continue;
            }
            
            KhachHangDTO kh = KhachHangDAO.timKhachHangTheoMa(maKH);
            if (kh == null) {
                System.out.println("❌ Không thể tải thông tin khách hàng!");
                continue;
            }

            System.out.println("\n📝 THÔNG TIN HIỆN TẠI:");
            hienThiThongTinKhachHang(kh);
            
            System.out.println("\n📝 NHẬP THÔNG TIN MỚI:");
            if (!kh.suaThongTinKhachHang()) {
                System.out.println("⚠️  Đã hủy sửa khách hàng.");
                continue;
            }

            System.out.println("\n📝 THÔNG TIN SAU KHI SỬA:");
            hienThiThongTinKhachHang(kh);
            
            System.out.print("\n→ Xác nhận cập nhật? (Y/N): ");
            String confirm = scanner.nextLine().trim().toUpperCase();
            
            if (!"Y".equals(confirm)) {
                System.out.println("⚠️  Đã hủy cập nhật.");
                continue;
            }
            
            if (KhachHangDAO.suaKhachHang(kh)) {
                System.out.println("\n✅ Sửa khách hàng thành công!");
            } else {
                System.out.println("\n❌ Sửa khách hàng thất bại!");
            }
            
            System.out.print("\n→ Bạn có muốn sửa khách hàng khác? (Y/N): ");
            String choice = scanner.nextLine().trim().toUpperCase();
            if (!"Y".equals(choice)) break;
        }
    }

    private void hienThiThongTinKhachHang(KhachHangDTO kh) {
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-18s : %-28s │\n", "Mã KH", kh.getMaKH());
        System.out.printf("│ %-18s : %-28s │\n", "Họ tên", kh.getHo() + " " + kh.getTen());
        System.out.printf("│ %-18s : %-28s │\n", "Giới tính", kh.getGioiTinh());
        
        String ngaySinhStr = (kh.getNgaySinh() != null) ? 
                            kh.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : 
                            "(Chưa cập nhật)";
        System.out.printf("│ %-18s : %-28s │\n", "Ngày sinh", ngaySinhStr);
        

        String diaChiStr = (kh.getDiaChi() != null && !kh.getDiaChi().isEmpty()) ? 
                        kh.getDiaChi() : 
                        "(Chưa cập nhật)";
        System.out.printf("│ %-18s : %-28s │\n", "Địa chỉ", diaChiStr);
        
        System.out.printf("│ %-18s : %-28s │\n", "Điện thoại", kh.getDienThoai());
        System.out.println("└────────────────────────────────────────────────────┘");
    }

    public void xoa() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Nhập mã khách hàng cần xóa: ");
                String maKH = scanner.nextLine().trim();

                if(!KhachHangDAO.kiemTraMaKH(maKH)) {
                    System.out.println("Mã khách hàng không tồn tại, vui lòng nhập lại.");
                    continue;
                }

                KhachHangDAO.xoaKhachHang(maKH);
                break;
            } catch (Exception e) {
                System.out.println("Lỗi nhập liệu: " + e.getMessage());
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    public void xuat() {
        List <KhachHangDTO> list = KhachHangDAO.getAllKhachHang();

        if (list.isEmpty()) {
            System.out.println("Không có khách hàng nào trong hệ thống.");
            return;
        }
        List<String> headers = List.of(
            "MaKH", "Ho", "Ten", "GioiTinh", "NgaySinh", "DienThoai", "DiaChi"
        );
        List<List<String>> rows = new ArrayList<>();
        for (KhachHangDTO kh : list) {

            List<String> row = new ArrayList<>();
            row.add(kh.getMaKH());
            row.add(kh.getHo());
            row.add(kh.getTen());
            row.add(kh.getGioiTinh());
            row.add(kh.getNgaySinhFormat());
            row.add(kh.getDienThoai());
            row.add(kh.getDiaChi());
            rows.add(row);
        }
        tablePrinter.printTable(headers, rows);

        // System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        // System.out.println("║                                         DANH SÁCH KHÁCH HÀNG TRONG CỬA HÀNG                                    ║");
        // System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        // System.out.printf("║ %-10s │ %-20s │ %-12s │ %-10s │ %-10s │ %-10s │ %-20s ║\n",
        //         "Mã KH", "Họ", "Tên", "Giới tính", "Ngày sinh", "SĐT", "Địa chỉ");
        // System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        // for (KhachHangDTO kh : list) {
        //     System.out.printf("║ %-10s │ %-20s │ %-12s │ %-10s │ %-10s │ %-10s │ %-20s ║\n",
        //             kh.getMaKH(),
        //             kh.getHo(),
        //             kh.getTen(),
        //             kh.getGioiTinh(),
        //             kh.getNgaySinhFormat(), 
        //             kh.getDienThoai(),
        //             kh.getDiaChi());
        // }
        // System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }
}