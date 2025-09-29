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
                    themSanPham();
                    break;
                case 2:
                    // suaSanPham();
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

    public void themSanPham() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Nhập mã sản phẩm: ");
                String maSP = scanner.nextLine().trim();
                if (maSP.equals("0")) {
                    System.out.println("Thoát thêm sản phẩm.");
                    break;
                }

                // Kiểm tra mã sản phẩm đã tồn tại hay chưa 
                if (SanPhamDAO.timSanPhamTheoMa(maSP) == null) {
                    System.out.println("Mã sản phẩm đã tồn tại, vui lòng nhập lại.");
                    continue;
                }
                
                sanPhamDTO sanPham = new sanPhamDTO();
                sanPham.setMaSP(maSP);
                if (!sanPham.nhap()) {
                    System.out.println("Đã hủy thêm sản phẩm, quay lại menu...");
                    break;
                }

                System.out.print("Nhập mã loại: ");
                int loai = scanner.nextInt();
                if (loai == 0) {
                    System.out.println("Thoát thêm sản phẩm.");
                    break;
                }
                
                System.out.println("1: chai, 2: gói, 3: hộp, 4: cái, 5: thùng, 6: bộ, 7: vỉ, 8: cuộn");
                System.out.print("Nhập mã đơn vị: ");
                int donVi = scanner.nextInt();
                if (donVi == 0) {
                    System.out.println("Thoát thêm sản phẩm.");
                    break;
                }

                SanPhamDAO.themSanPham(sanPham, loai, donVi);
            } catch (Exception e) {
                System.out.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }


    public void suaSanPham() { }
    public void xoaSanPham() { }
    public void timKiemSanPham() { }
    public void thongKeSanPham() { }
    public void xuatDanhSachSanPham() { }
    
}