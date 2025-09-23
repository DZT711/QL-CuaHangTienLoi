package view;

import java.util.Scanner;

public class QuanLyKhachHang {
    public void menuQuanLyKhachHang() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n████████████████████████████████████████████████████████████████████████████████");
            System.out.println("██                                                                            ██");
            System.out.println("██                         HỆ THỐNG QUẢN LÝ KHÁCH HÀNG                        ██");
            System.out.println("██                                                                            ██");
            System.out.println("████████████████████████████████████████████████████████████████████████████████");
            System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓ MENU CHỨC NĂNG ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
            System.out.println("▒ [1] ➜ Thêm khách hàng                                                        ▒");
            System.out.println("▒ [2] ➜ Chỉnh sửa thông tin khách hàng                                         ▒");
            System.out.println("▒ [3] ➜ Xóa khách hàng khỏi danh sách                                          ▒");
            System.out.println("▒ [4] ➜ Tìm kiếm khách hàng                                                    ▒");
            System.out.println("▒ [5] ➜ Báo cáo thống kê chi tiết                                              ▒");
            System.out.println("▒ [6] ➜ Đồng bộ và cập nhật dữ liệu                                            ▒");
            System.out.println("▒ [7] ➜ Tìm kiếm nâng cao theo khu vực                                         ▒");
            System.out.println("▒ [8] ➜ In và xuất danh sách                                                   ▒");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
            System.out.println("░ [0] ✗ THOÁT CHƯƠNG TRÌNH                                                     ░");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            int choice = -1;

            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 0 && choice <= 8) {
                        break;
                    }
                    System.out.println("Vui lòng nhập số trong khoảng 0–8.");
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                } else {
                    System.out.println("Vui lòng nhập số hợp lệ.");
                    scanner.next(); // bỏ token không phải số
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                }
            }

            switch (choice) {
                case 1:
                    while (true) {
                        try {
                            System.out.println("\n");
                            System.out.println("    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                            System.out.println("    ┃                           THÊM KHÁCH HÀNG                          ┃");
                            System.out.println("    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                            System.out.println("    ┃ [1] ➜ Thêm 1 khách hàng                                            ┃");
                            System.out.println("    ┃ [2] ➜ Thêm danh sách khách hàng                                    ┃");
                            System.out.println("    ┃ [0] ➜ Thoát                                                        ┃");
                            System.out.println("    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

                            int opt = scanner.nextInt();
                            scanner.nextLine();

                            if (opt == 0) {
                                System.out.println("Thoát thêm khách hàng thành công.");
                                break;
                            } else if (opt == 1) {
                                them();
                            } else if (opt == 2) {
                                // nhapDanhSachKhachHang();
                            } else {
                                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại");
                            }
                        } catch (Exception e) {
                            System.out.println("Lỗi xảy ra: " + e.getMessage());
                            scanner.nextLine();
                        }
                    }
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
        }
    }

    public void them() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Nhập mã khách hàng: ");
                int maKH = scanner.nextInt();
                scanner.nextLine();

                // Kiểm tra mã khách hàng đã tồn tại hay chưa
                if (KhachHangDAO.kiemTraMaKH(maKH)) {
                    System.out.println("Mã khách hàng đã tồn tại, vui lòng nhập lại.");
                    continue;
                } else {
                    System.out.println("Mã khách hàng hợp lệ, vui lòng nhập thông tin khách hàng.");
                    break;
                }

                KhachHangDTO kh = new KhachHangDTO();
                kh.setMaKH(maKH);
                kh.nhapThongTinKhachHang();
                KhachHangDAO.themKhachHang(kh);
                System.out.println("Thêm khách hàng thành công.");
                break;
            } catch (Exception e) {
                System.out.println("Lỗi nhập liệu: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    public void nhapDanhSachKhachHang() {
        // nhập bằng file
    }

    public sua() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            
        }
    }
}