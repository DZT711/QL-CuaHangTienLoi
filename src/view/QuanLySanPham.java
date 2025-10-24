package view;

import java.util.Scanner;
import dao.SanPhamDAO;
import dto.SanPhamDTO;
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
                    try {
                        SanPhamDTO sp = new SanPhamDTO();
                        
                        if (!sp.nhapThongTinSanPham()) {
                            System.out.println("⚠️ Đã hủy thêm sản phẩm.");
                            break;
                        }
                        

                        if (SanPhamDAO.themSanPham(sp)) System.out.println("✅ Thêm sản phẩm thành công!");
                        else System.out.println("❌ Thêm sản phẩm thất bại! Vui lòng thử lại.");
                    } catch (Exception e) {
                        System.err.println("❌ Lỗi khi thêm sản phẩm: " + e.getMessage());
                    }
                    break;
                case 2:
                    suaSanPham();
                    break;
                case 3:
                    doiTrangThaiSanPham();
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
        while (true) {
            System.out.print("Nhập mã sản phẩm cần sửa: ");
            String maSP = scanner.nextLine().trim();
            if (maSP.equals("0")) {
                System.out.println("Thoát chức năng sửa sản phẩm.");
                break;
            }

            SanPhamDTO sp = SanPhamDAO.timSanPhamTheoMa(maSP);

            if (sp == null) {
                System.out.println("❌ Không tìm thấy sản phẩm với mã: " + maSP);
                System.out.print("Bạn có muốn thử lại không? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) break;
                continue;
            }

            System.out.println("\n Thông tin sản phẩm hiện tại: ");
            sp.inThongTinSanPham();

            if (!sp.sua()) {
                System.out.println("Đã hủy sửa sản phẩm.");
                continue;
            }

            System.out.println("\n Thông tin sau khi sửa:");
            sp.inThongTinSanPham();

            System.out.print("\n Xác nhận lưu thay đổi? (Y/N): ");
            String confirm = scanner.nextLine().trim().toUpperCase();
            if (!"Y".equals(confirm)) {
                System.out.println("Đã hủy lưu thay đổi.");
                continue;
            }

            if (SanPhamDAO.suaSanPham(sp)) {
                System.out.println("✅ Cập nhật sản phẩm thành công!");
            } else {
                System.out.println("❌ Cập nhật sản phẩm thất bại!");
            }

            System.out.print("\n→ Tiếp tục sửa sản phẩm khác? (Y/N): ");
            if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) break;
        }
    }

    public void doiTrangThaiSanPham() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n════════════════════════════════════════════");
        System.out.println("       ĐỔI TRẠNG THÁI SẢN PHẨM");
        System.out.println("════════════════════════════════════════════");

        System.out.print("Nhập mã sản phẩm (hoặc '0' để thoát): ");
        String maSP = scanner.nextLine().trim();

        if ("0".equals(maSP)) {
            System.out.println("✓ Thoát chức năng đổi trạng thái.");
            return;
        }

        SanPhamDTO sp = SanPhamDAO.timSanPhamTheoMa(maSP);

        if (sp == null) {
            System.out.println("❌ Không tìm thấy sản phẩm với mã: " + maSP);
            return;
        }

        System.out.println("\n📋 Thông tin sản phẩm:");
        sp.inThongTinSanPham();

        // inactive -> active
        if ("inactive".equals(sp.getTrangThai())) {
            System.out.println("\n⚠ Sản phẩm đang ở trạng thái ngừng kinh doanh.");
            System.out.print("→ Bạn có muốn kích hoạt lại sản phẩm này? (Y/N): ");
        
            String confirm = scanner.nextLine().trim().toUpperCase();
            if (!"Y".equals(confirm)) {
                System.out.println("❌ Đã hủy thao tác.");
                return;
            }
            if (SanPhamDAO.kichHoatSanPham(maSP)) {
                System.out.println("✅ Kích hoạt sản phẩm thành công!");
            } else {
                System.out.println("❌ Kích hoạt sản phẩm thất bại!");
            }
        } else {  // active -> inactive
            System.out.println("\n⚠ Bạn muốn ngừng kinh doanh sản phẩm này?");
            
            if (sp.getSoLuongTon() > 0) {
                System.out.println("❌ Không thể ngừng kinh doanh!");
                System.out.println("   Lý do: Sản phẩm còn " + sp.getSoLuongTon() + " trong kho.");
                System.out.println("   → Vui lòng bán hết hàng trước khi ngừng kinh doanh.");
                return;
            }
            
            System.out.print("→ Xác nhận ngừng kinh doanh? (Y/N): ");
            String confirm = scanner.nextLine().trim().toUpperCase();
            
            if (!"Y".equals(confirm)) {
                System.out.println("❌ Đã hủy thao tác.");
                return;
            }
            
            if (SanPhamDAO.ngungKinhDoanhSanPham(maSP)) {
                System.out.println("✅ Ngừng kinh doanh sản phẩm thành công!");
            } else {
                System.out.println("❌ Ngừng kinh doanh sản phẩm thất bại!");
            }
        }
    }

    public void timKiemSanPhamTheoMa() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập mã sản phẩm cần tìm: ");
        String maSP = scanner.nextLine().trim();

        SanPhamDTO sp = SanPhamDAO.timSanPhamTheoMa(maSP);

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
        List<SanPhamDTO> danhSachSP = SanPhamDAO.timSanPhamTheoTen(tenSP);
        
        if (danhSachSP.isEmpty()) {
            System.out.println("Không tìm thấy sản phẩm");
        } else {
            List<String> headers = List.of(
                    "MaSP", "TenSP", "Loai", "SoLuongTon", "DonViTinh",
                    "GiaBan","MoTa", "TrangThai");
            
            // Chuẩn bị rows
            List<List<String>> rows = new ArrayList<>();
            
            for (SanPhamDTO sanPham : danhSachSP) {
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