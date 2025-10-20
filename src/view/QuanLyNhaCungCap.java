package view;

import java.util.List;
import java.util.Scanner;
import dao.NhaCungCapDAO;
import dto.NhaCungCapDTO;

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
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ");
            System.out.println("░ [0] ✗ Quay lại menu chính                                                    ░");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ");
            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            int choice = -1;
            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 0 && choice <= 5) break;
                    System.out.print("⚠️  Vui lòng nhập số trong khoảng 0–5: ");
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
                            System.out.println("    ┃                           TÌM KIẾM SẢN PHẨM                        ┃");
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
                case 0:
                    System.out.println("Thoát chương trình thành công!");
                    return;
                default: 
                System.out.println("Lựa chọn không hợp lệ.");
                }
            }
        }
    
    public void themNhaCungCap() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n Nhập thông tin nhà cung cấp mới:");
        // kiểm tra trống hay trùng lặp mã ncc 
        String ma;
        while (true) {
            System.out.print("Mã NCC: ");
            ma = sc.nextLine().trim();

            if (ma.isEmpty()) {
                System.out.println("  Mã NCC không được để trống!");
                continue;
            }

            if (NhaCungCapDAO.timnccTheoMa(ma) != null) {
                System.out.println("  Mã NCC đã tồn tại. Vui lòng nhập mã khác!");
                continue;
            }

            break; 
        }

        String ten;
        while (true) {
            System.out.print("Tên NCC: ");
            ten = sc.nextLine().trim();
            
            if (ten.isEmpty()) {
                System.out.println("Tên NCC không được để trống!");
                continue;
            }
            break;
        }

        String diaChi; 
        while (true) {
            System.out.print("Địa chỉ NCC: ");
            diaChi = sc.nextLine().trim();

            if (diaChi.isEmpty()) {
                System.out.println("Địa chỉ NCC không được để trống!");
                continue;
            }
            break;
        }
        // nhập và kiểm tra tính hợp lệ của số điện thoại
        String dienThoai ;
        while (true) {
            System.out.print("Điện thoại (9–11 số): ");
            dienThoai = sc.nextLine().trim();
            if (dienThoai.matches("\\d{9,11}")) break;
            System.out.println("  Số điện thoại không hợp lệ. Vui lòng nhập lại!");
        }
        // Nhập và kiểm tra tính hợp lệ của email 
        String email;
        while (true) {
            System.out.print("Email: ");
            email = sc.nextLine().trim();
            if (email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) break;
            System.out.println("  Email không hợp lệ. Vui lòng nhập lại!");
        }

        String trangThai;
        while (true) {
            System.out.print("Trạng thái NCC (active / inactive): ");
            trangThai = sc.nextLine().trim();
            if (!trangThai.equals("active") && !trangThai.equals("inactive")) {
                System.out.println("  Chỉ được nhập active hoặc inactive!");
                continue;
            }
            break;
        }

        NhaCungCapDTO ncc = new NhaCungCapDTO(ma, ten, diaChi, dienThoai, email, trangThai);

        // Xác nhận thông tin
        System.out.println("╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                             XÁC NHẬN THÔNG TIN NHÀ CUNG CẤP                        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("Mã NCC: " + ma);
        System.out.println("Tên NCC: " + ten);
        System.out.println("Địa chỉ: " + diaChi);
        System.out.println("Điện thoại: " + dienThoai);
        System.out.println("Email: " + email);
        System.out.println("Trạng thái: " + trangThai);

        System.out.print("\n Bạn có muốn thêm nhà cung cấp này? (y/n): ");
        String confirm = sc.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            if (NhaCungCapDAO.themNCC(ncc)) {
                System.out.println(" Thêm nhà cung cấp thành công!");
            } else {
                System.out.println(" Thêm nhà cung cấp thất bại!");
            }
        } else {
            System.out.println(" Đã hủy thêm nhà cung cấp!");
        }

        System.out.print("\n Nhấn Enter để tiếp tục...");
        sc.nextLine();
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

    public void timKiemNccTheoMa() {
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
    

    public void timKiemNccTheoTen () {
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

}
