package view;

import java.util.Scanner;
import dao.NhapHangDAO;
import dto.NhapHangDTO;

public class QuanLyNhapHang {
    public void menuQuanLyNhapHang() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n████████████████████████████████████████████████████████████████████████████████");
            System.out.println("██                                                                            ██");
            System.out.println("██                      HỆ THỐNG QUẢN LÝ NHẬP HÀNG                            ██");
            System.out.println("██                                                                            ██");
            System.out.println("████████████████████████████████████████████████████████████████████████████████");
            System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓ MENU CHỨC NĂNG ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
            System.out.println("▒ [1] ➜ Tạo phiếu nhập hàng mới                                                ▒");
            System.out.println("▒ [2] ➜ Xem chi tiết phiếu nhập                                                ▒");
            System.out.println("▒ [3] ➜ Chỉnh sửa phiếu nhập                                                   ▒");
            System.out.println("▒ [4] ➜ Xóa phiếu nhập                                                         ▒");
            System.out.println("▒ [5] ➜ Tìm kiếm phiếu nhập                                                    ▒");
            System.out.println("▒ [6] ➜ Quản lý nhà cung cấp                                                   ▒");
            System.out.println("▒ [7] ➜ Thống kê nhập hàng                                                     ▒");
            System.out.println("▒ [8] ➜ Xuất báo cáo nhập hàng                                                 ▒");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
            System.out.println("░ [0] ✗ Quay lại menu chính                                                    ░");
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
                    case 1: taoPhieuNhap(); break;
                    case 2: xemChiTiet(); break;
                    case 3: suaPhieuNhap(); break;
                    case 4: xoaPhieuNhap(); break;
                    case 5: timKiem(); break;
                    case 6: 
                        QuanLyNhaCungCap qlncc = new QuanLyNhaCungCap();
                        qlncc.menuQuanLyNhaCungCap();
                        break;
                    case 7: thongKeNhapHang(); break;
                    case 8: xuatBaoCao(); break;
                    default:
                        System.out.println("⚠️ Lựa chọn không hợp lệ!");
                        break;
                }
            } catch (Exception e) {
                System.out.println("❌ Lỗi: " + e.getMessage());
            }
        }
    }

    private void taoPhieuNhap() { }
    private void xemChiTiet() { }
    private void suaPhieuNhap() { }
    private void xoaPhieuNhap() { }
    private void timKiem() { }
    private void thongKeNhapHang() { }
    private void xuatBaoCao() { }
}