package view;

import java.util.Scanner;
import dao.NhanVienDAO;
import dto.NhanVienDTO;

public class QuanLyNhanVien {
    public void menuQuanLyNhanVien() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n████████████████████████████████████████████████████████████████████████████████");
            System.out.println("██                                                                            ██");
            System.out.println("██                         HỆ THỐNG QUẢN LÝ NHÂN VIÊN                         ██");
            System.out.println("██                                                                            ██");
            System.out.println("████████████████████████████████████████████████████████████████████████████████");
            System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓ MENU CHỨC NĂNG ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
            System.out.println("▒ [1] ➜ Thêm nhân viên mới                                                     ▒");
            System.out.println("▒ [2] ➜ Cập nhật thông tin nhân viên                                           ▒");
            System.out.println("▒ [3] ➜ Xóa nhân viên                                                          ▒");
            System.out.println("▒ [4] ➜ Tìm kiếm nhân viên                                                     ▒");
            System.out.println("▒ [5] ➜ Hiển thị danh sách nhân viên                                           ▒");
            System.out.println("▒ [6] ➜ Quản lý chấm công                                                      ▒");
            System.out.println("▒ [7] ➜ Báo cáo lương                                                          ▒");
            System.out.println("▒ [8] ➜ Xuất danh sách nhân viên                                               ▒");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
            System.out.println("░ [0] ⮐ Quay lại menu chính                                                    ░");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
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
                    case 6: quanLyChamCong(); break;
                    case 7: baoCaoLuong(); break;
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

    private void them() { }
    private void sua() { }
    private void xoa() { }
    private void timKiem() { }
    private void hienThiDanhSach() { }
    private void quanLyChamCong() { }
    private void baoCaoLuong() { }
    private void xuatDanhSach() { }
}