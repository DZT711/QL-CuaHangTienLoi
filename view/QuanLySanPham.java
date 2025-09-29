package view;

import java.util.Scanner;
import dao.SanPhamDAO;
import dto.sanPhamDTO;

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
                    System.out.println("Vui lòng nhập số trong khoảng 0–6.");
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                } else {
                    System.out.println("Vui lòng nhập số trong khoảng 0–6.");
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
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
                    // xoaSanPham();
                    break;
                case 4:
                    // timKiemSanPham();
                    break;
                case 5:
                    // thongKeSanPham();
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
                    System.out.printf("%-10s | %-20s | %-10s | %-10s | %-10s | %-10s | %-15s | %-10s | %-20s\n",
                "MaSP", "TenSP", "Loai", "SoLuongTon", "DonViTinh", "GiaBan",
                        "NgaySanXuat", "HanSuDung", "MoTa");
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

    public void xoaSanPham() { }
    public void timKiemSanPham() { }
    public void thongKeSanPham() { }
    public void xuatDanhSachSanPham() { }
    
}