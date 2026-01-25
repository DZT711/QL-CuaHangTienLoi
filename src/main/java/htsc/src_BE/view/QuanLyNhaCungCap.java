package htsc.src_BE.view;

import java.util.List;
import java.util.Scanner;
import htsc.src_BE.dao.NhaCungCapDAO;
import htsc.src_BE.dto.NhaCungCapDTO;
import htsc.src_BE.util.ValidatorUtil;

public class QuanLyNhaCungCap {

    private NhaCungCapDAO dao = new NhaCungCapDAO(); 

    public void menuQuanLyNhaCungCap() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n████████████████████████████████████████████████████████████████████████████████");
            System.out.println("██                                                                            ██");
            System.out.println("██                         HỆ THỐNG QUẢN LÝ NHÀ CUNG CẤP                      ██");
            System.out.println("██                                                                            ██");
            System.out.println("████████████████████████████████████████████████████████████████████████████████");
            System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓ MENU CHỨC NĂNG ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
            System.out.println("▒ [1] ➜ Thêm nhà cung cấp                                                      ▒");
            System.out.println("▒ [2] ➜ Sửa thông tin nhà cung cấp                                             ▒");
            System.out.println("▒ [3] ➜ Xóa nhà cung cấp                                                       ▒");
            System.out.println("▒ [4] ➜ Tìm kiếm nhà cung cấp                                                  ▒");
            System.out.println("▒ [5] ➜ Xuất danh sách nhà cung cấp                                            ▒");
            System.out.println("▒ [6] ➜ Thống kê nhà cung cấp                                                  ▒");
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
                    System.out.print("⚠️  Vui lòng nhập số trong khoảng 0–6: ");
                } else {
                    System.out.print("⚠️  Nhập không hợp lệ. Vui lòng nhập lại: ");
                    scanner.next();
                }
            }

            switch (choice) {
                case 1 :
                themNhaCungCap();
                break;
                case 2:
                suaNhaCungCap();
                break;
                case 3 :
                xoaNhaCungCapTheoMa();
                break;
                case 4 :
                while (true) {
                        try {
                            System.out.println("\n");
                            System.out.println("    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                            System.out.println("    ┃                         TÌM KIẾM NHÀ CUNG CẤP                      ┃");
                            System.out.println("    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                            System.out.println("    ┃ [1] ➜ Tìm kiếm nhà cung cấp theo mã                                ┃");
                            System.out.println("    ┃ [2] ➜ Tìm kiếm nhà cung cấp theo tên                               ┃");
                            System.out.println("    ┃ [0] ➜ Thoát                                                        ┃");
                            System.out.println("    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

                            int opt = scanner.nextInt();
                            scanner.nextLine();

                            if (opt == 0) {
                                System.out.println("Thoát tìm nhà cung cấp thành công.");
                                break;
                            } else if (opt == 1) {
                                timKiemNccTheoMa();
                            } else if (opt == 2) {
                                timKiemNccTheoTen();
                            } else {
                                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại");
                            }
                        } catch (Exception e) {
                            System.out.println("Lỗi xảy ra: " + e.getMessage());
                            scanner.nextLine();
                        }
                    }
                    break;
                case 5 :
                NhaCungCapDAO.xuatDanhSachNCC();
                break;
                case 6:
                menuThongKe();
                break;
                case 0:
                    System.out.println("Thoát chương trình thành công!");
                    return;
                default: 
                System.out.println("Lựa chọn không hợp lệ.");
                }
            }
        }
    
    public void themNhaCungCap() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║           THÊM NHÀ CUNG CẤP MỚI                   ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        String maNCC = NhaCungCapDAO.generateMaNCC();
        
        String tenNCC;
        while (true) {
            System.out.print("→ Tên NCC: ");
            tenNCC = scanner.nextLine().trim();
            if (isExist(scanner, tenNCC)) return;
            
            // Kiểm tra chuỗi hợp lệ (không rỗng, không ký tự đặc biệt, v.v.)
            if (!ValidatorUtil.isValidString(tenNCC)) {
                System.out.println("❌ Tên NCC không hợp lệ! Vui lòng nhập lại.");
                continue;
            }   

            break;
        }

        String diaChi;
        while (true) {
            System.out.print("→ Địa chỉ: ");
            diaChi = scanner.nextLine().trim();

            if (isExist(scanner, diaChi)) return;
            
            // Gọi hàm validator
            if (!ValidatorUtil.isValidAddress(diaChi)) {
                continue; // Hàm validator đã tự in lỗi
            }
            break;
        }

        String dienThoai;
        while (true) {
            System.out.print("→ Điện thoại (10 số, bắt đầu bằng 0): ");
            dienThoai = scanner.nextLine().trim();

            if (isExist(scanner, dienThoai)) return;
            
            if (!ValidatorUtil.isValidPhoneNumber(dienThoai)) {
                continue; 
            }
            
            // Viết trong DAO thêm hàm tìm nhà cung cấp bằng số điện thoại để kiểm tra trùng
            if (NhaCungCapDAO.checkDienThoaiExist(dienThoai)) {
                System.out.println("  ❌ Số điện thoại đã tồn tại trong hệ thống!");
                continue;
            }
            
            break;
        }

        String email;
        while (true) {
            System.out.print("→ Email: ");
            email = scanner.nextLine().trim();
            
            if (isExist(scanner, email)) return;

            if (email.isEmpty()) {
                System.out.println("  ❌ Email không được để trống!");
                continue;
            }
            
            if (email.length() > 40) {
                System.out.println("  ❌ Email không được quá 40 ký tự!");
                continue;
            }
            
            if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                System.out.println("  ❌ Email không đúng định dạng!");
                System.out.println("     (VD: example@domain.com)");
                continue;
            }
            
            // Viết trong DAO thêm hàm tìm nhà cung cấp bằng email để kiểm tra trùng
            if (NhaCungCapDAO.checkEmailExist(email)) {
                System.out.println("  ❌ Email đã tồn tại trong hệ thống!");
                continue;
            }
            
            break;
        }

        String trangThai;
        while (true) {
            System.out.print("→ Trạng thái (active/inactive) [Enter = active]: ");
            String input = scanner.nextLine().trim();

            if (isExist(scanner, input)) return;

            if (input.isEmpty()) {
                trangThai = "active";
                break;
            }
            
            trangThai = input.toLowerCase();
            
            if (trangThai.equals("active") || trangThai.equals("inactive")) break;
            
            System.out.println("  ❌ Chỉ được nhập 'active' hoặc 'inactive'!");
        }

        NhaCungCapDTO ncc = new NhaCungCapDTO(maNCC, tenNCC, diaChi, dienThoai, email, trangThai);

        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║              XÁC NHẬN THÔNG TIN NHÀ CUNG CẤP                     ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════╣");
        System.out.printf("║ Mã NCC      : %-50s ║%n", maNCC);
        System.out.printf("║ Tên NCC     : %-50s ║%n", tenNCC);
        System.out.printf("║ Địa chỉ     : %-50s ║%n", diaChi);
        System.out.printf("║ Điện thoại  : %-50s ║%n", dienThoai);
        System.out.printf("║ Email       : %-50s ║%n", email);
        System.out.printf("║ Trạng thái  : %-50s ║%n", trangThai);
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");
        
        while (true) {
            System.out.print("\n→ Xác nhận thêm nhà cung cấp? (Y/N): ");
            String confirm = scanner.nextLine().trim();
            
            if (confirm.isEmpty()) {
                System.out.println("  ⚠️  Vui lòng nhập Y (có) hoặc N (không)!");
                continue;
            }
            
            if (confirm.equalsIgnoreCase("Y")) {
                if (NhaCungCapDAO.themNCC(ncc)) {
                    System.out.println("✅ Thêm nhà cung cấp thành công!");
                } else {
                    System.out.println("❌ Thêm nhà cung cấp thất bại! Vui lòng thử lại.");
                }
                break;
            }
            
            if (confirm.equalsIgnoreCase("N")) {
                System.out.println("⚠️  Đã hủy thêm nhà cung cấp!");
                break;
            }
            
            System.out.println("  ❌ Chỉ được nhập 'Y' hoặc 'N'!");
        }
        System.out.print("\n→ Nhấn Enter để tiếp tục...");
        scanner.nextLine();
    }

    private boolean isExist(Scanner scanner, String input) {
        if (input.equals("0")) {
            System.out.println("⚠️  Đã hủy thao tác!");
            return true;
        }
        return false;
    }

    public void suaNhaCungCap() {
            Scanner scanner = new Scanner(System.in);
            boolean continueWithAnotherProduct = true;
            while (continueWithAnotherProduct) {
                while (true) {
                    try {
                        System.out.print("Nhập nhà cung cấp cần sửa ");
                        String maNCC = scanner.nextLine().trim();
                        if (maNCC.equals("0")) {
                            System.out.println("Thoát sửa nhà cung cấp ");
                            return;
                        }
                        
                        if (NhaCungCapDAO.timnccTheoMa(maNCC)==null) {
                            System.out.println("Mã nhà cung cấp không tồn tại, vui lòng nhập lại");
                            continue;
                        }

                        NhaCungCapDTO ncc = NhaCungCapDAO.timnccTheoMa(maNCC);
                        System.out.println("Thông tin nhà cung cấp trước khi sửa: ");
                        System.out.printf("%-10s | %-25s | %-25s | %-12s | %-25s | %-10s\n",
                "Mã NCC", "Tên NCC", "Địa chỉ", "Điện thoại", "Email", "Trạng thái");
                        ncc.inThongTinNCC();

                        System.out.println("nhập thông tin mới cho nhà cung cấp: ");
                        if(!ncc.sua()) {
                            System.out.println("Đã hủy sửa nhà cung cấp, quay lại menu...");
                            return;
                        }


                        //Cập nhật lại DB sau khi sửa
                        NhaCungCapDAO.suaNhaCungCap(ncc);
                        System.out.println("Sửa nhà cung cáp thành công. ");
                        

                        //  Hỏi người dùng có muốn sửa tiếp không
                        System.out.print("Bạn có muốn sửa nhà cung cấp khác không? (Y/N): ");
                        String choice = scanner.nextLine().trim();
                        if (!choice.equalsIgnoreCase("y")) {
                            continueWithAnotherProduct = false; // dừng vòng ngoài
                        }

                        break; // dừng vòng trong, tránh lặp lại việc sửa cùng NCC

                    } catch (Exception e) {
                        System.err.println("Lỗi nhập liệu" + e.getMessage());
                        scanner.nextLine();
                    }
                }
            }
    }

    public void xoaNhaCungCapTheoMa() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n Nhập mã nhà cung cấp cần xóa: ");
        String maNcc = scanner.nextLine().trim();

        NhaCungCapDTO ncc = NhaCungCapDAO.timnccTheoMa(maNcc);
        if (ncc == null) {
            System.out.println("Mã nhà cung cấp không tồn tại!");
            return;
        }


        //  Kiểm tra trạng thái trước khi xóa
        if ("inactive".equalsIgnoreCase(ncc.getTrangThai())) {
            System.out.println(" Nhà cung cấp [" + ncc.getMaNCC() + "] đã bị ngừng hoạt động (đã xóa trước đó).");
            System.out.println(" Không thể xóa lại nhà cung cấp này!");
            return;
        }
        
        System.out.println("\nThông tin nhà cung cấp muốn xóa:");
        System.out.printf("%-10s | %-25s | %-25s | %-12s | %-25s | %-10s\n",
         "Mã NCC", "Tên NCC", "Địa chỉ", "Điện thoại", "Email", "Trạng thái");
        ncc.inThongTinNCC();


        System.out.print(" Bạn có chắc muốn xóa (y/n)? ");
        String confirm = scanner.nextLine();
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println(" Đã hủy xóa.");
            return;
        }

        if ("inactive".equals(ncc.getTrangThai())) {
            System.out.println("Nhà cung cấp đã ngừng kinh doanh");
            return;
        }

        if (NhaCungCapDAO.xoaNCC(maNcc)) {
            System.out.println(" Xóa nhà cung cấp thành công!");
        } else {
            System.out.println(" Xóa nhà cung cấp thất bại!");
        }
    }

    public static void timKiemNccTheoMa() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã nhà cung cấp cần tìm: ");
        String maNCC = scanner.nextLine().trim();

        NhaCungCapDTO ncc = NhaCungCapDAO.timnccTheoMa(maNCC);

        if (ncc == null) {
            System.out.println("Mã nhà cung cấp không tồn tại");
            return;
        } else {
            System.out.println("Thông tin nhà cung cấp: ");
            System.out.printf("%-10s | %-25s | %-25s | %-12s | %-25s | %-10s\n",
            "Mã NCC", "Tên NCC", "Địa chỉ", "Điện thoại", "Email", "Trạng thái");
            ncc.inThongTinNCC();
        }
    }
    

    public static void timKiemNccTheoTen () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập tên nhà cung cấp cần tìm: ");
        String tenNCC = scanner.nextLine().trim();
        List<NhaCungCapDTO> ncc = NhaCungCapDAO.timnccTheoTen(tenNCC);
        if(ncc.isEmpty()) {
            System.out.println("Không tìm thấy nhà cung cấp");
        } else {
            System.out.println("Danh sách nhà cung cấp có tên " + tenNCC + ":");
             System.out.printf("%-10s | %-25s | %-25s | %-12s | %-25s | %-10s\n",
            "Mã NCC", "Tên NCC", "Địa chỉ", "Điện thoại", "Email", "Trạng thái");
            for (NhaCungCapDTO product : ncc) {
                product.inThongTinNCC();
                System.out.println("-----------------------------------");
            }
        }

    }
    // HÀM MỚI TINH: Dùng để làm menu con cho Thống Kê
    public void menuThongKe() {
        Scanner scanner = new Scanner(System.in); 

        while (true) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║                                              ║");
            System.out.println("║                MENU THỐNG KÊ                 ║");
            System.out.println("║                                              ║");
            System.out.println("╠══════════════════════════════════════════════╣");
            System.out.println("║ [1] ➜ Thống kê theo Trạng Thái              ║");
            System.out.println("║ [2] ➜ Thống kê theo Khu Vực                 ║");
            System.out.println("║ [0] ➜ Quay lại menu chính                   ║");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            int choice = -1;
            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 0 && choice <= 2) break;
                    System.out.print("⚠️  Vui lòng nhập số trong khoảng 0–2: ");
                } else {
                    System.out.print("⚠️  Nhập không hợp lệ. Vui lòng nhập lại: ");
                    scanner.next();
                }
            }

            switch (choice) {
                case 1:
                    NhaCungCapDAO.thongKeTheoTrangThai();
                    System.out.print("\n→ Nhấn Enter để tiếp tục...");
                    scanner.nextLine();
                    break; 
                case 2:
                    NhaCungCapDAO.thongKeTheoKhuVuc();
                    System.out.print("\n→ Nhấn Enter để tiếp tục...");
                    scanner.nextLine();
                    break; 
                case 0:
                    return; 
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

}