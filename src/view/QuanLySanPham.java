package view;

import java.util.Scanner;
import dao.SanPhamDAO;
import dto.sanPhamDTO;
import util.FormatUtil;
import util.tablePrinter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            System.out.println("▒ [1] ➜ Thêm sản phẩm                                                          ▒");
            System.out.println("▒ [2] ➜ Chỉnh sửa thông tin sản phẩm                                           ▒");
            System.out.println("▒ [3] ➜ Đổi trạng thái sản phẩm                                                ▒");
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
                    if (choice >= 0 && choice <= 6) break;
                    System.out.print("Vui lòng nhập số trong khoảng 0–6: ");
                } else {
                    System.out.print("Nhập không hợp lệ. Vui lòng nhập lại: ");
                    scanner.next();
                }
            }

            switch (choice) {
                case 1:
                    sanPhamDTO sp = new sanPhamDTO();
                    sp.nhapThongTinSanPham();
                    SanPhamDAO.themSanPham(sp);
                    System.out.println("Thêm sản phẩm thành công.");
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
                            System.out.println(
                                    "    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                            System.out.println(
                                    "    ┃                           TÌM KIẾM SẢN PHẨM                        ┃");
                            System.out.println(
                                    "    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                            System.out.println(
                                    "    ┃ [1] ➜ Tìm kiếm sản phẩm theo mã                                    ┃");
                            System.out.println(
                                    "    ┃ [2] ➜ Tìm kiếm sản phẩm theo tên                                   ┃");
                            System.out.println(
                                    "    ┃ [0] ➜ Thoát                                                        ┃");
                            System.out.println(
                                    "    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
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
                    while (true) {
                        try {
                            System.out.println("\n");
                            System.out.println(
                                    "    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                            System.out.println(
                                    "    ┃                          THỐNG KÊ SẢN PHẨM                         ┃");
                            System.out.println(
                                    "    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                            System.out.println(
                                    "    ┃ [1] ➜ Thống kê theo loại sản phẩm                                  ┃");
                            System.out.println(
                                    "    ┃ [2] ➜ Thống kê sản phẩm sắp hết trong kho                          ┃");
                            System.out.println(
                                    "    ┃ [3] ➜ Thống kê top sản phẩm bán chạy nhất                          ┃");
                            System.out.println(
                                    "    ┃ [0] ➜ Thoát                                                        ┃");
                            System.out.println(
                                    "    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

                            int opt = scanner.nextInt();
                            scanner.nextLine();

                            if (opt == 0) {
                                System.out.println("Thoát thống kê sản phẩm thành công.");
                                break;
                            } else if (opt == 1) {
                                SanPhamDAO.thongKeTheoLoai();
                            } else if (opt == 2) {
                                SanPhamDAO.sanPhamSapHetTrongKho(10);
                            } else if (opt == 3) {
                                thongKeTopSanPhamBanChay();
                            } else {
                                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại");
                            }
                        } catch (Exception e) {
                            System.out.println("Lỗi xảy ra: " + e.getMessage());
                            scanner.nextLine();
                        }
                    }
                    break;
                case 6:
                    SanPhamDAO.xuatDanhSachSanPham();
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
                    sp.inThongTinSanPham();

                    System.out.println("Nhập thông tin mới cho sản phẩm: ");
                    if (!sp.sua()) {
                        System.out.println("Đã hủy sửa sản phẩm, quay lại menu...");
                        break;
                    }

                    SanPhamDAO.suaSanPham(sp);
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
        System.out.println("Nhập mã sản phẩm cần đổi trạng thái: ");
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
            System.out.println("Sản phẩm được đổi trạng thái thành công");
        } else {
            System.out.println("Đổi trạng thái sản phẩm thất bại");
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
            sp.inThongTinSanPham();
        }
    }

    public void timKiemSanPhamTheoTen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập tên sản phẩm cần tìm: ");
        String tenSP = scanner.nextLine().trim();
        List<sanPhamDTO> danhSachSP = SanPhamDAO.timSanPhamTheoTen(tenSP);
        
        if (danhSachSP.isEmpty()) {
            System.out.println("Không tìm thấy sản phẩm");
        } else {
            List<String> headers = List.of(
                    "MaSP", "TenSP", "Loai", "SoLuongTon", "DonViTinh",
                    "GiaBan","MoTa", "TrangThai");
            
            // Chuẩn bị rows
            List<List<String>> rows = new ArrayList<>();
            
            for (sanPhamDTO sanPham : danhSachSP) {
                List<String> row = new ArrayList<>();
                row.add(sanPham.getMaSP());
                row.add(sanPham.getTenSP());
                row.add(String.valueOf(sanPham.getLoaiSP()));
                row.add(String.valueOf(sanPham.getSoLuongTon()));
                row.add(String.valueOf(sanPham.getDonViTinh()));
                row.add(String.valueOf(sanPham.getGiaBan()));
                row.add(sanPham.getMoTa() != null ? sanPham.getMoTa() : "");
                row.add(sanPham.getTrangThai());
                rows.add(row);
            }
    
            // Gọi hàm in bảng
            tablePrinter.printTable(headers, rows);
        }
    }

    public void thongKeTopSanPhamBanChay() {
        Scanner scanner = new Scanner (System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        try {
            System.out.print("\nNhập ngày bắt đầu (ddMMyyyy): ");
            String from = scanner.nextLine().trim();

            System.out.print("Nhập ngày kết thúc (ddMMyyyy): ");
            String to = scanner.nextLine().trim();

            LocalDate fromDate = LocalDate.parse(from, formatter);
            LocalDate toDate = LocalDate.parse(to, formatter);

            if (fromDate.isAfter(toDate)) {
                System.out.println("Ngày bắt đầu phải trước ngày kết thúc.");
                return;
            }

            System.out.print("Nhập số lượng sản phẩm top bán chạy: ");
            int limit;
            
            try {
                limit = Integer.parseInt(scanner.nextLine().trim());
                if (limit <= 0) {
                    System.out.println("Số lượng phải lớn hơn 0.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Số lượng không hợp lệ.");
                return;
            }

            List<Map<String, Object>> topProducts = SanPhamDAO.thongKeSanPhamBanChayNhat(fromDate, toDate, limit);

            if (topProducts.isEmpty()) {
                System.out.println("Không có sản phẩm bán chạy trong khoảng thời gian này.");
                return;
            }

            System.out.println("\nDanh sách top " + limit + " sản phẩm bán chạy từ " +
                fromDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " đến " +
                toDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ":");
            
            List<String> headers = List.of("Top", "Mã SP", "Tên Sản Phẩm", "Số Lượng Bán", "Doanh Thu");
            List<List<String>> rows = new ArrayList<>();
            int rank = 1;

            for (Map<String, Object> product : topProducts) {
                List<String> row = new ArrayList<>();
                row.add(String.valueOf(rank++));
                row.add((String) product.get("maSP"));
                row.add((String) product.get("tenSP"));
                row.add(String.valueOf(product.get("soLuongBan")));
                row.add(FormatUtil.formatVND((long) product.get("doanhThu")));
                rows.add(row);
            }

            tablePrinter.printTable(headers, rows);
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }
}