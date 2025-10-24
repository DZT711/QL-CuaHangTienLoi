package view;

import java.util.Scanner;
import dao.SanPhamDAO;
import dto.SanPhamDTO;
import util.FormatUtil;
import util.tablePrinter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
                    while (true) {
                        System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                        String opt = scanner.nextLine().trim();

                        switch (opt) {
                            case "0":
                                System.out.println("Thoát tìm kiếm sản phẩm thành công.");
                                break;
                            case "1":
                                timKiemSanPhamTheoMa();
                                break;
                            case "2":
                                timKiemSanPhamTheoTen();
                                break;
                            default:
                                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại");
                                continue;
                        }
                        break;
                    }
                    break;
                case 5:
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
                    while (true) {
                        System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                        String opt = scanner.nextLine().trim();

                        switch (opt) {
                            case "0":
                                System.out.println("Thoát thống kê sản phẩm thành công.");
                                break;
                            case "1":
                                SanPhamDAO.thongKeTheoLoai();
                                break;
                            case "2":
                                SanPhamDAO.sanPhamSapHetTrongKho(10);
                                break;
                            case "3":
                                thongKeTopSanPhamBanChay();
                                break;
                            default:
                                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại");
                                continue;
                        }
                        break;
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
        System.out.print("\nNhập mã sản phẩm (hoặc '0' để thoát): ");
        String maSP = scanner.nextLine().trim();

        if ("0".equals(maSP)) return;

        if (maSP.isEmpty()) {
            System.out.println("Mã sản phẩm không được để trống.");
            return;
        }

        SanPhamDTO sp = SanPhamDAO.timSanPhamTheoMa(maSP);

        if (sp == null) {
            System.out.println("❌ Không tìm thấy sản phẩm với mã: " + maSP);
        } else {
            System.out.println("\n✅ Đã tìm thấy sản phẩm:");
            sp.inThongTinSanPham();
        }
    }

    public void timKiemSanPhamTheoTen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║             TÌM KIẾM SẢN PHẨM THEO TÊN                        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        System.out.println();

        while (true) {
            System.out.print("-> Nhập tên sản phẩm (hoặc '0' để thoát): ");
            String tenSP = scanner.nextLine().trim();

            if ("0".equals(tenSP)) {
                System.out.println("✓ Hủy tìm kiếm");
                return;
            }

            if (tenSP.isEmpty()) {
                System.out.println("❌ Tên sản phẩm không được để trống!");
                continue;
            } 

            List<SanPhamDTO> danhSachSP = SanPhamDAO.timSanPhamTheoTen(tenSP);

            if (danhSachSP.isEmpty()) {
                System.out.println("\n❌ Không tìm thấy sản phẩm nào có tên chứa: \"" + tenSP + "\"");
                System.out.println();

                System.out.print("→ Bạn có muốn thử lại không? (Y/N): ");
                String choice = scanner.nextLine().trim().toUpperCase();
                if (!"Y".equals(choice)) return;
                continue;
            }

            System.out.println("\n Tìm thấy " + danhSachSP.size() + "sản phẩm");
            System.out.println("════════════════════════════════════════════════════════════════════════════════");

            List<String> headers = List.of(
                    "Mã SP", "Tên Sản Phẩm", "Loại", "Số lượng tồn", "Đơn vị",
                    "Giá bán", "Trạng thái"
            );

            List <List<String>> rows = new ArrayList<>();
            for (SanPhamDTO sanPham : danhSachSP) {
                List<String> row = new ArrayList<>();
                String ttIcon = "active".equals(sanPham.getTrangThai()) ? "✅" : "❌";
                
                row.add(sanPham.getMaSP());
                row.add(sanPham.getTenSP());
                row.add(sanPham.getLoaiText());
                row.add(String.valueOf(sanPham.getSoLuongTon()));
                row.add(sanPham.getDonViText());
                row.add(FormatUtil.formatVND(sanPham.getGiaBan()));
                row.add(ttIcon);
                rows.add(row);
            }

            tablePrinter.printTable(headers, rows);

            System.out.print("\n→ Tiếp tục tìm kiếm sản phẩm khác? (Y/N): ");
            if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                System.out.println("✓ Kết thúc tìm kiếm.");
                break;
            }
        }
    }

    public void thongKeTopSanPhamBanChay() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate fromDate;
            while (true) {
                System.out.print("\nNhập ngày bắt đầu (ddMMyyyy): ");
                String from = scanner.nextLine().trim();
                
                if ("0".equals(from)) {
                    System.out.println("✓ Hủy thống kê sản phẩm bán chạy.");
                    return;
                }

                try {
                    fromDate = LocalDate.parse(from, inputFormatter);
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("❌ Định dạng ngày không hợp lệ. Vui lòng nhập lại.");
                }
            }

            LocalDate toDate;
            while (true) {
                System.out.print("Nhập ngày kết thúc (ddMMyyyy): ");
                String to = scanner.nextLine().trim();

                if ("0".equals(to)) {
                    System.out.println("✓ Hủy thống kê sản phẩm bán chạy.");
                    return;
                }

                try {
                    toDate = LocalDate.parse(to, inputFormatter);
                    
                    if (fromDate.isAfter(toDate)) {
                        System.out.println("❌ Ngày kết thúc phải sau ngày bắt đầu. Vui lòng nhập lại.");
                        continue;
                    }
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("❌ Định dạng ngày không hợp lệ. Vui lòng nhập lại.");
                }
            }

            int limit;
            while (true) {
                System.out.print("-> Nhấp số lượng sản phẩm top bán chạy: ");
                String limitInput = scanner.nextLine().trim();

                if ("0".equals(limitInput)) {
                    System.out.println("✓ Hủy thống kê sản phẩm bán chạy.");
                    return;
                }

                try {
                    limit = Integer.parseInt(limitInput);
                    if (limit > 0) break;
                    else System.out.println("❌ Số lượng phải lớn hơn 0.");
                } catch (NumberFormatException e) {
                    System.out.println("❌ Vui lòng nhập số hợp lệ.");
                }
            }

            List<Map<String, Object>> topProducts = SanPhamDAO.thongKeSanPhamBanChayNhat(fromDate, toDate, limit);

            if (topProducts.isEmpty()) {
                System.out.println("❌ Không có sản phẩm nào được bán trong khoảng thời gian này.");
                return;
            }

            System.out.println("\n╔════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║           TOP " + limit + " SẢN PHẨM BÁN CHẠY NHẤT                                ║");
            System.out.println("║   Từ " + fromDate.format(displayFormatter) + " đến " + toDate.format(displayFormatter) + "                                      ║");
            System.out.println("╚════════════════════════════════════════════════════════════════════════════╝");
            
            List<String> headers = List.of("Top", "Mã SP", "Tên Sản Phẩm", "Số Lượng Bán", "Doanh Thu");
            List<List<String>> rows = new ArrayList<>();

            int rank = 1;
            long tongDoanhThu = 0;
            long tongSoLuongBan = 0;

            for (Map<String, Object> product : topProducts) {
                List<String> row = new ArrayList<>();

                String maSP = (String) product.get("maSP");
                String tenSP = (String) product.get("tenSP");
                Integer soLuongBan = (Integer) product.get("tongSoLuongBan");
                Long doanhThu = (Long) product.get("doanhThu");
                
                if (soLuongBan == null) soLuongBan = 0;
                if (doanhThu == null) doanhThu = 0L;

                row.add(String.valueOf(rank++));
                row.add(maSP);
                row.add(tenSP);
                row.add(String.format("%,d", soLuongBan));
                row.add(FormatUtil.formatVND(doanhThu));

                rows.add(row);

                tongDoanhThu += doanhThu;
                tongSoLuongBan += soLuongBan;
            }
            tablePrinter.printTable(headers, rows);
            
            System.out.println("\n📊 TỔNG KẾT:");
            System.out.println("   • Tổng số lượng bán: " + String.format("%,d", tongSoLuongBan) + " sản phẩm");
            System.out.println("   • Tổng doanh thu: " + FormatUtil.formatVND(tongDoanhThu));
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }
}