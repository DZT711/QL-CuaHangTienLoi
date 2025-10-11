package view;

import java.util.Scanner;

public class QuanLyChiTietPhieuNhap {
    public void menuQuanLyChiTietPhieuNhap() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n████████████████████████████████████████████████████████████████████████████████");
            System.out.println("██                                                                            ██");
            System.out.println("██                      HỆ THỐNG QUẢN LÝ CHI TIẾT PHIẾU NHẬP                      ██");
            System.out.println("██                                                                            ██");
            System.out.println("████████████████████████████████████████████████████████████████████████████████");
            System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓ MENU CHỨC NĂNG ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
            System.out.println("▒ [1] ➜ Thêm chi tiết vào phiếu nhập                                         ▒");
            System.out.println("▒ [2] ➜ Xóa chi tiết phiếu nhập                                            ▒");
            System.out.println("▒ [3] ➜ Tìm kiếm chi tiết phiếu nhập                                        ▒");
            System.out.println("▒ [4] ➜ Xem danh sách chi tiết phiếu nhập                                  ▒");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
            System.out.println("░ [0] ✗ Quay lại menu chính                                                    ░");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            int choice = -1;

            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 0 && choice <= 4) break;
                    System.out.print("⚠️  Vui lòng nhập số trong khoảng 0–4: ");
                } else {
                    System.out.print("⚠️  Nhập không hợp lệ. Vui lòng nhập lại: ");
                    scanner.next();
                }
            }

            switch (choice) {
                case 1:
                    // themChiTietPhieuNhap();
                    break;
                case 2:
                    // xoaChiTietPhieuNhap();
                    break;
                case 3:
                    // timKiemChiTietPhieuNhap();
                    break;
                case 4:
                    // xemDanhSachChiTietPhieuNhap();
                    break;
                default:
                    System.out.println("⚠️ Lựa chọn không hợp lệ!");
                    break;
            }
            scanner.close();
        }
    }
}
