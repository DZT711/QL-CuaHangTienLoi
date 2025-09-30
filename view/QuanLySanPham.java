package view;

import java.util.Scanner;
import dao.SanPhamDAO;
import dto.sanPhamDTO;
import java.util.List;

public class QuanLySanPham {
    public void menuQuanLySanPham() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n████████████████████████████████████████████████████████████████████████████████");
            System.out.println("██                                                                            ██");
            System.out.println("██                         HỆ THỐNG QUẢN LÝ SẢN PHẨM                          ██");
            System.out.println("██                                                                            ██");
            System.out.println("████████████████████████████████████████████████████████████████████████████████");
            System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓ MENU CHỨC NĂNG ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
            System.out.println("▒ [1] ➜ Thêm sản phẩm mới                                                      ▒");
            System.out.println("▒ [2] ➜ Chỉnh sửa thông tin sản phẩm                                           ▒");
            System.out.println("▒ [3] ➜ Xóa sản phẩm                                                           ▒");
            System.out.println("▒ [4] ➜ Tìm kiếm sản phẩm                                                      ▒");
            System.out.println("▒ [5] ➜ Thống kê sản phẩm                                                      ▒");
            System.out.println("▒ [6] ➜ Xuất danh sách sản phẩm                                                ▒");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ");
            System.out.println("░ [0] ⮐ Quay lại menu chính                                                    ░ ");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ");
            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            int choice = -1;

            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice > 0 && choice <= 6) {
                        break;
                    }
                    else if (choice == 0) {
                        return;
                    } else {
                    System.out.println("Vui lòng nhập số trong khoảng 0–6.");
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                }  
                } else {
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập số trong khoảng 0–6.");
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                    scanner.next(); // Clear invalid input
                }
            }
        
            switch (choice) {
                case 1:
                    // themSanPham();
                    break;
                case 2:
                    suaSanPham();
                    break;
                case 3:
                    xoaSanPhamTheoMa();
                    break;
                case 4:
                    while (true) {
                        try {
                            System.out.println("\n");
                            System.out.println("    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                            System.out.println("    ┃                           TÌM KIẾM SẢN PHẨM                        ┃");
                            System.out.println("    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                            System.out.println("    ┃ [1] ➜ Tìm kiếm sản phẩm theo mã                                    ┃");
                            System.out.println("    ┃ [2] ➜ Tìm kiếm sản phẩm theo tên                                   ┃");
                            System.out.println("    ┃ [0] ➜ Thoát                                                        ┃");
                            System.out.println("    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

                            int opt = scanner.nextInt();
                            scanner.nextLine();

                            if (opt == 0) {
                                System.out.println("Thoát tìm kiếm sản phẩm thành công.");
                                break;
                            } else if (opt == 1) {
                                timKiemSanPhamTheoMa();
                            } else if (opt == 2) {
                                timKiemSanPhamTheoTen();
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
                    
                    break;
                case 6:
                    // xuatDanhSachSanPham();
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

    public void suaSanPham() {
        Scanner scanner = new Scanner(System.in);
        boolean continueWithAnotherProduct = true;
        while (continueWithAnotherProduct) {
            while (true) {
                try {
                    System.out.print("Nhập mã sản phẩm cần sửa: ");
                    String maSP = scanner.nextLine().trim();
                    if (maSP.equals("0")) {
                        System.out.println("Thoát sửa sản phẩm.");
                        break;
                    }
                    
                    if (SanPhamDAO.timSanPhamTheoMa(maSP) == null) {
                        System.out.println("Mã sản phẩm không tồn tại, vui lòng nhập lại.");
                        continue;
                    }
    
                    sanPhamDTO sp = SanPhamDAO.timSanPhamTheoMa(maSP);
                    System.out.println("Thông tin sản phẩm trước khi sửa: ");
                    System.out.printf("%-10s | %-20s | %-10s | %-10s | %-10s | %-10s | %-15s | %-10s | %-20s | %-10s\n",
                "MaSP", "TenSP", "Loai", "SoLuongTon", "DonViTinh", "GiaBan",
                        "NgaySanXuat", "HanSuDung", "MoTa", "TrangThai");
                    sp.inthongTinSanPham();
    
                    System.out.println("Nhập thông tin mới cho sản phẩm: ");
                    if (!sp.sua()) {
                        System.out.println("Đã hủy sửa sản phẩm, quay lại menu...");
                        break;
                    }
    
                    // Cập nhật vô DB sau khi sửa 
                    SanPhamDAO.suaSanPham(sp, maSP);
                    System.out.println("Sửa sản phẩm thành công.");
                    break;
                } catch (Exception e) {
                    System.out.println("Lỗi nhập liệu: " + e.getMessage());
                    scanner.nextLine();
                }
            }
        
            System.out.println("Bạn có muốn sửa thông tin sản phẩm khác không? (Y/N)");
            String choice = scanner.nextLine().trim();
            if (choice.equalsIgnoreCase("N")) {
                continueWithAnotherProduct = false;
            }
        }
    }

    public void xoaSanPhamTheoMa() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập mã sản phẩm cần xóa: ");
        String maSP = scanner.nextLine().trim();

        sanPhamDTO sp = SanPhamDAO.timSanPhamTheoMa(maSP);

        if (sp == null) {
            System.out.println("Mã sản phẩm không tồn tại");
            return;
        }

        if ("inactive".equals(sp.getTrangThai())) {
            System.out.println("Sản phẩm đã ngừng kinh doanh");
            return;
        }

        if (SanPhamDAO.xoaSanPham(maSP)) {
            System.out.println("Xóa sản phẩm thành công");
        } else {
            System.out.println("Xóa sản phẩm thất bại");
        }
    }

    public void timKiemSanPhamTheoMa() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập mã sản phẩm cần tìm: ");
        String maSP = scanner.nextLine().trim();

        sanPhamDTO sp = SanPhamDAO.timSanPhamTheoMa(maSP);

        if (sp == null) {
            System.out.println("Mã sản phẩm không tồn tại");
            return;
        } else {
            System.out.println("Thông tin sản phẩm: ");
            System.out.printf("%-10s | %-20s | %-10s | %-10s | %-10s | %-10s | %-15s | %-10s | %-20s | %-10s\n",
            "MaSP", "TenSP", "Loai", "SoLuongTon", "DonViTinh", "GiaBan",
            "NgaySanXuat", "HanSuDung", "MoTa", "TrangThai");
            sp.inthongTinSanPham();
        }
    }

    public void timKiemSanPhamTheoTen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập tên sản phẩm cần tìm: ");
        String tenSP = scanner.nextLine().trim();
        List<sanPhamDTO> sp = SanPhamDAO.timSanPhamTheoTen(tenSP); 
        if (sp.isEmpty()) {
            System.out.println("Không tìm thấy sản phẩm");
        } else {
            System.out.println("Danh sách sản phẩm có tên " + tenSP + ": ");
            System.out.printf("%-10s | %-20s | %-10s | %-10s | %-10s | %-10s | %-15s | %-10s | %-20s | %-10s\n",
            "MaSP", "TenSP", "Loai", "SoLuongTon", "DonViTinh", "GiaBan",
            "NgaySanXuat", "HanSuDung", "MoTa", "TrangThai");
            for (sanPhamDTO product : sp) {
                product.inthongTinSanPham();
                System.out.println("--------------------------------");
            }
        }
    }
    
    public void xuatDanhSachSanPham() { }
    
}