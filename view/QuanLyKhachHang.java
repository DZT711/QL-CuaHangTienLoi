package view;

import java.util.Scanner;
import dao.KhachHangDAO;
import dto.KhachHangDTO;

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
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ");
            System.out.println("░ [0] ✗ Quay lại menu chính                                                    ░");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ");
            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            int choice = -1;

            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 0 && choice <= 8) {
                        break;
                    }
                    else if (choice == 0) {
                        return;
                    } else {
                    System.out.println("Vui lòng nhập số trong khoảng 0–8.");
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                }  
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
                    sua();
                    break;
                case 3:
                    xoa();
                    break;
                case 4:
                    break;
                case 0:
                    System.out.println("Thoát khỏi menu quản lý khách hàng.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }
        }
    }

    public void them() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Nhập mã khách hàng: ");
                String maKH = scanner.nextLine().trim();

                // Kiểm tra mã khách hàng đã tồn tại hay chưa
                if (KhachHangDAO.kiemTraMaKH(maKH)) {
                    System.out.println("Mã khách hàng đã tồn tại, vui lòng nhập lại.");
                    continue;
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
        scanner.close();
    }

    public void nhapDanhSachKhachHang() {
        // nhập bằng file
    }

    public void sua() {
        Scanner scanner = new Scanner(System.in);
        boolean continueWithAnotherCustomer = true;
        while (continueWithAnotherCustomer) {
            while (true) {
                try {
                    System.out.print("Nhập mã khách hàng cần sửa: ");
                    String maKH = scanner.nextLine().trim();
                    scanner.nextLine();

                    if (!KhachHangDAO.kiemTraMaKH(maKH)) {
                        System.out.println("Mã khách hàng không tồn tại, vui lòng nhập lại.");
                        continue;
                    } 

                    KhachHangDTO kh = KhachHangDAO.timKhachHangTheoMa(maKH);
                    System.out.println("Thông tin khách hàng trước khi sửa: ");
                    System.out.println(kh.toString());
                    kh.suaThongTinKhachHang();
                    System.out.println("Thông tin khách hàng sau khi sửa: ");
                    System.out.println(kh.toString());

                    // Cập nhật vô DB sau khi sửa 
                    KhachHangDAO.suaKhachHang(kh, maKH);
                    break;
                } catch (Exception e) {
                    System.out.println("Lỗi nhập liệu: " + e.getMessage());
                    scanner.nextLine();
                }
            }
            System.out.println("Bạn có muốn sửa thông tin khách hàng khác không? (Y/N)");
            String choice = scanner.nextLine().trim();
            if (choice.equalsIgnoreCase("N")) {
                continueWithAnotherCustomer = false;
            }
        }
        scanner.close();
    }

    public void xoa() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Nhập mã khách hàng cần xóa: ");
                String maKH = scanner.nextLine().trim();

                if(!KhachHangDAO.kiemTraMaKH(maKH)) {
                    System.out.println("Mã khách hàng không tồn tại, vui lòng nhập lại.");
                    continue;
                }

                KhachHangDAO.xoaKhachHang(maKH);
                break;
            } catch (Exception e) {
                System.out.println("Lỗi nhập liệu: " + e.getMessage());
                scanner.nextLine();
            }
        }
        scanner.close();
    }
}