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

        System.out.println("\n➕ Nhập thông tin nhà cung cấp mới:");
        System.out.print("Mã NCC: ");
        String ma = sc.nextLine();
        System.out.print("Tên NCC: ");
        String ten = sc.nextLine();
        System.out.print("Địa chỉ: ");
        String diaChi = sc.nextLine();
        System.out.print("Điện thoại: ");
        String dienThoai = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Trạng thái: ");
        String trangThai = sc.nextLine();

        NhaCungCapDTO ncc = new NhaCungCapDTO(ma, ten, diaChi, dienThoai, email, trangThai);
        // kiểm tra có trùng mã ncc không
        if (NhaCungCapDAO.timnccTheoMa(ma) != null) {
            System.out.println("Mã NCC đã tồn tại. Vui lòng nhập mã khác!");
            return;
        }
        if (!ncc.isValid()) {
            System.out.println("Thông tin nhà cung cấp không hợp lệ. Vui lòng nhập lại!");
            return;
        }

        if (NhaCungCapDAO.themNCC(ncc)) {
            System.out.println(" Thêm nhà cung cấp thành công!");
        } else {
            System.out.println("Thêm nhà cung cấp thất bại!");
        }
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
                            break;
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
                            break;
                        }
                        if (!ncc.isValid()) {
                            System.out.println("⚠️ Thông tin không hợp lệ (số điện thoại hoặc email sai định dạng). Hủy cập nhật!");
                            break;
                        }

                        //Cập nhật lại DB sau khi sửa
                        NhaCungCapDAO.suaNhaCungCap(ncc);
                        System.out.println("Sửa nhà cung cáp thành công. ");
                        break;
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
