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
            System.out.println("▒ [5] ➜ Hiển thị danh sách sản phẩm                                            ▒");
            System.out.println("▒ [6] ➜ Quản lý tồn kho                                                        ▒");
            System.out.println("▒ [7] ➜ Báo cáo thống kê                                                       ▒");
            System.out.println("▒ [8] ➜ Xuất danh sách sản phẩm                                                ▒");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ");
            System.out.println("░ [0] ⮐ Quay lại menu chính                                                    ░ ");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ");
            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            // Input validation
            while (!scanner.hasNextInt()) {
                System.out.println("⚠️ Vui lòng nhập số!");
                scanner.next();
                System.out.print("\n💡 Nhập lựa chọn của bạn: ");
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 0) {
                System.out.println("\n⮐ Đang quay lại menu chính...");
                break;
            }

            try {
                switch (choice) {
                    case 1: them(); break;
                    case 2: sua(); break;
                    case 3: xoa(); break;
                    case 4: timKiem(); break;
                    case 5: hienThiDanhSach(); break;
                    case 6: quanLyTonKho(); break;
                    case 7: thongKe(); break;
                    case 8: xuatDanhSach(); break;
                    default:
                        System.out.println("⚠️ Lựa chọn không hợp lệ!");
                        break;
                }
            } catch (Exception e) {
                System.out.println("❌ Lỗi: " + e.getMessage());
            }
        }
    }

    // Add stub methods for each function
    private void them() { }
    private void sua() { }
    private void xoa() { }
    private void timKiem() { }
    private void hienThiDanhSach() { }
    private void quanLyTonKho() { }
    private void thongKe() { }
    private void xuatDanhSach() { }
}