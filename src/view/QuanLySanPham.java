package view;

import java.util.Scanner;
import dao.SanPhamDAO;
import dto.SanPhamDTO;
import util.FormatUtil;
import util.ValidatorUtil;
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
                    if (choice >= 0 && choice <= 6) break;
                    System.out.print("❌ Vui lòng nhập số trong khoảng 0–6: ");
                } else {
                    System.out.print("❌ Nhập không hợp lệ. Vui lòng nhập lại: ");
                    scanner.next();
                }
            }

            switch (choice) {
                case 1:
                    themSanPham();
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
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                    while (true) {
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
                                System.out.print("Lựa chọn không hợp lệ. Vui lòng nhập lại: ");
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
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                    while (true) {
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
                                System.out.print("Lựa chọn không hợp lệ. Vui lòng nhập lại: ");
                                continue;
                        }
                        break;
                    }
                    break;
                case 6:
                    System.out.println("\n");
                    System.out.println(
                            "    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                    System.out.println(
                            "    ┃                        XUẤT DANH SÁCH SẢN PHẨM                     ┃");
                    System.out.println(
                            "    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    System.out.println(
                            "    ┃ [1] ➜ Xuất tất cả sản phẩm                                         ┃");
                    System.out.println(
                            "    ┃ [2] ➜ Xuất danh sách sản phẩm còn hoạt động                        ┃");
                    System.out.println(
                            "    ┃ [3] ➜ Xuất danh sách sản phẩm ngừng hoạt động                      ┃");
                    System.out.println(
                            "    ┃ [0] ➜ Thoát                                                        ┃");
                    System.out.println(
                            "    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                    while (true) {
                        String opt = scanner.nextLine().trim();

                        switch (opt) {
                            case "0":
                                System.out.println("Thoát xuất danh sách sản phẩm thành công.");
                                break;
                            case "1":
                                xuatTatCaSanPham();
                                break;
                            case "2":
                                xuatSanPhamTheoTrangThai("active");
                                break;
                            case "3":
                                xuatSanPhamTheoTrangThai("inactive");
                                break;
                            default:
                                System.out.print("Lựa chọn không hợp lệ. Vui lòng nhập lại: ");
                                continue;
                        }
                        break;
                    }
                    break;
                    
                case 0:
                    System.out.println("Thoát khỏi menu quản lý sản phẩm!");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }
        }
    }

    public void themSanPham() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║                 THÊM SẢN PHẨM MỚI                  ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        try {
            String MaSP = SanPhamDAO.generateMaSP();
            System.out.println("📋 Mã sản phẩm tự động: " + MaSP + "\n");

            SanPhamDTO sp = new SanPhamDTO();
            sp.setMaSP(MaSP);

            if (!sp.nhapThongTinSanPham()) {
                System.out.println("⚠️ Đã hủy thêm sản phẩm.");
                return;
            }

            System.out.println("\n📝 THÔNG TIN SẢN PHẨM VỪA NHẬP:");
            sp.inThongTinSanPham();

            Scanner scanner = new Scanner(System.in);
            System.out.print("\n→ Xác nhận thêm sản phẩm này? (Y/N): ");
            String confirm = scanner.nextLine().trim();   
            if (!"Y".equalsIgnoreCase(confirm)) {
                System.out.println("❌ Đã hủy thêm sản phẩm.");
                return;
            }

            if (SanPhamDAO.themSanPham(sp)) System.out.println("✅ Thêm sản phẩm thành công!");
            else System.out.println("❌ Thêm sản phẩm thất bại!");
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi thêm sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void suaSanPham() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║               SỬA THÔNG TIN SẢN PHẨM               ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println("Nhập Enter để bỏ qua hoặc '0' để thoát chức năng");

        while (true) {

            System.out.print("→ Nhập mã sản phẩm cần sửa (hoặc '0' để thoát): ");
            String maSP = scanner.nextLine().trim();

            if (maSP.equals("0")) {
                System.out.println("✓ Thoát chức năng sửa sản phẩm.");
                break;
            }

            if (maSP.isEmpty()) {
                System.out.println("❌ Mã sản phẩm không được để trống!");
                continue;
            }

            SanPhamDTO sp = SanPhamDAO.timSanPhamTheoMa(maSP);

            if (sp == null) {
                System.out.println("❌ Mã sản phẩm không tồn tại!");
                System.out.print("Bạn có muốn thử lại không? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) break;
                continue;
            }

            System.out.println("\n📝 THÔNG TIN HIỆN TẠI:");
            sp.inThongTinSanPham();

            if (!sp.sua()) {
                System.out.println("⚠️ Đã hủy sửa sản phẩm.");
                System.out.print("\n→ Bạn có muốn sửa sản phẩm khác? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) break;
                continue;
            }

            System.out.println("\n📝 THÔNG TIN SAU KHI SỬA:");
            sp.inThongTinSanPham();

            System.out.print("\n→ Xác nhận lưu thay đổi? (Y/N): ");
            String confirm = scanner.nextLine().trim().toUpperCase();
            if (!"Y".equals(confirm)) {
                System.out.println("⚠️  Đã hủy cập nhật.");
                System.out.print("\n→ Bạn có muốn sửa sản phẩm khác? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) break;
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
        System.out.println("          XÓA SẢN PHẨM");
        System.out.println("════════════════════════════════════════════");

        while (true) {
            System.out.print("Nhập mã sản phẩm (hoặc '0' để thoát): ");
            String maSP = scanner.nextLine().trim();

            if ("0".equals(maSP)) {
                System.out.println("✅ Thoát chức năng đổi trạng thái.");
                break;
            }

            if (maSP.isEmpty()) {
                System.out.println("❌ Mã sản phẩm không được để trống.");
                continue;
            }

            SanPhamDTO sp = SanPhamDAO.timSanPhamTheoMa(maSP);
    
            if (sp == null) {
                System.out.println("❌ Không tìm thấy sản phẩm với mã: " + maSP);
                System.out.print("→ Bạn có muốn thử lại không? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) break;
                continue;
            }

            System.out.println("\n📋 THÔNG TIN SẢN PHẨM:");
            sp.inThongTinSanPham();

            // inactive -> active
            if ("inactive".equals(sp.getTrangThai())) {
                System.out.println("\n⚠️ Sản phẩm đang ở trạng thái ngừng kinh doanh.");
                System.out.print("→ Bạn có muốn kích hoạt lại sản phẩm này? (Y/N): ");
        
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                    System.out.println("❌ Đã hủy thao tác kích hoạt.");
                } else {
                    if (SanPhamDAO.kichHoatSanPham(maSP)) {
                        System.out.println("✅ Kích hoạt sản phẩm thành công!");
                    } else {
                        System.out.println("❌ Kích hoạt sản phẩm thất bại!");
                    }
                }
            } 
            else {  // active -> inactive
                System.out.println("\n⚠️ Bạn muốn ngừng kinh doanh sản phẩm này?");
                
                if (sp.getSoLuongTon() > 0) {
                    System.out.println("❌ Không thể ngừng kinh doanh!");
                    System.out.println("   Lý do: Sản phẩm còn " + sp.getSoLuongTon() + " trong kho.");
                    System.out.println("   → Vui lòng bán hết hàng trước khi ngừng kinh doanh.");
                } else {
                    System.out.print("→ Xác nhận ngừng kinh doanh? (Y/N): ");
                    
                    if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                        System.out.println("❌ Đã hủy thao tác ngừng kinh doanh.");
                    } else {
                        if (SanPhamDAO.ngungKinhDoanhSanPham(maSP)) {
                            System.out.println("✅ Ngừng kinh doanh sản phẩm thành công!");
                        } else {
                            System.out.println("❌ Ngừng kinh doanh sản phẩm thất bại!");
                        }
                    }    
                }
            }
            System.out.print("\n→ Tiếp tục đổi trạng thái sản phẩm khác? (Y/N): ");
            if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                System.out.println("✅ Thoát chức năng đổi trạng thái.");
                break;
            }
        }
    }


    public void timKiemSanPhamTheoMa() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    TÌM KIẾM SẢN PHẨM THEO MÃ                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        System.out.println();

        while (true) {
            System.out.print("\nNhập mã sản phẩm (hoặc '0' để thoát): ");
            String maSP = scanner.nextLine().trim();
    
            if ("0".equals(maSP)) {
                System.out.println("✅ Thoát chức năng tìm kiếm.");
                break;
            }
    
            if (maSP.isEmpty()) {
                System.out.println("❌ Mã sản phẩm không được để trống.");
                continue;
            }

            SanPhamDTO sp = SanPhamDAO.timSanPhamTheoMa(maSP);

            if (sp == null) {
                System.out.println("❌ Không tìm thấy sản phẩm với mã: " + maSP);
            } else {
                System.out.println("\n✅ Đã tìm thấy sản phẩm:");
                sp.inThongTinSanPham();
            }

            System.out.print("\n→ Tiếp tục tìm kiếm sản phẩm khác? (Y/N): ");
            if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                System.out.println("✅ Thoát chức năng tìm kiếm.");
                break;
            }
        }
    }

    public void timKiemSanPhamTheoTen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    TÌM KIẾM SẢN PHẨM THEO TÊN                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        System.out.println();

        while (true) {
            System.out.print("→ Nhập tên sản phẩm (hoặc '0' để thoát): ");
            String tenSP = scanner.nextLine().trim();

            if ("0".equals(tenSP)) {
                System.out.println("✅ Thoát chức năng tìm kiếm.");
                break;
            }

            if (tenSP.isEmpty()) {
                System.out.println("❌ Tên sản phẩm không được để trống!");
                continue;
            } 

            List<SanPhamDTO> danhSachSP = SanPhamDAO.timSanPhamTheoTen(tenSP);

            if (danhSachSP.isEmpty()) {
                System.out.println("\n❌ Không tìm thấy sản phẩm nào có tên chứa: \"" + tenSP + "\"");

                System.out.print("→ Bạn có muốn thử lại không? (Y/N): ");
                String choice = scanner.nextLine().trim().toUpperCase();
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) break;
                continue;
            }

            System.out.println("\n✅ Tìm thấy " + danhSachSP.size() + " sản phẩm");
            System.out.println("════════════════════════════════════════════════════════════════════════════════");

            List<String> headers = List.of(
                    "Mã SP", "Tên Sản Phẩm", "Loại", "Số lượng tồn", "Đơn vị",
                    "Giá bán", "Trạng thái"
            );

            List <List<String>> rows = new ArrayList<>();
            for (SanPhamDTO sanPham : danhSachSP) {
                List<String> row = new ArrayList<>();
                String ttIcon = "active".equals(sanPham.getTrangThai()) ? "✅ Đang kinh doanh" : "❌ Ngừng kinh doanh";
                
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
                System.out.println("✅ Thoát chức năng tìm kiếm.");
                break;
            }
        }
    }

    public void thongKeTopSanPhamBanChay() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                           THỐNG KÊ SẢN PHẨM BÁN CHẠY                       ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════╝");

        while (true) {

            try {
                LocalDate fromDate;
                while (true) {
                    System.out.print("\nNhập ngày bắt đầu (dd/MM/yyyy) hoặc '0' để thoát: ");
                    String from = scanner.nextLine().trim();
                    
                    if ("0".equals(from)) {
                        System.out.println("✅ Thoát thống kê sản phẩm bán chạy.");
                        return;
                    }
                    
                    if (!ValidatorUtil.isValidateDate(from)) continue;
    
                    fromDate = LocalDate.parse(from, dateFormatter);
                    break;
                }
    
                LocalDate toDate;
                while (true) {
                    System.out.print("Nhập ngày kết thúc (dd/MM/yyyy) hoặc '0' để thoát: ");
                    String to = scanner.nextLine().trim();
    
                    if ("0".equals(to)) {
                        System.out.println("✅ Thoát thống kê sản phẩm bán chạy.");
                        return;
                    }
    
                    if (!ValidatorUtil.isValidateDate(to)) continue;
    
                    toDate = LocalDate.parse(to, dateFormatter);
    
                    if (!ValidatorUtil.isValidDateRange  (fromDate, toDate)) continue;
                    
                    break;
                }
    
                int limit;
                while (true) {
                    System.out.print("→ Nhập số lượng sản phẩm top bán chạy: ");
                    String limitInput = scanner.nextLine().trim();
    
                    if ("0".equals(limitInput)) {
                        System.out.println("✅ Thoát thống kê sản phẩm bán chạy.");
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
                } else {
                    System.out.println("\n╔════════════════════════════════════════════════════════════════════════════╗");
                    System.out.println(
                            "║                        TOP " + limit + " SẢN PHẨM BÁN CHẠY NHẤT                       ║");
                    System.out.println("║                        Từ " + fromDate.format(dateFormatter) + " đến "
                            + toDate.format(dateFormatter) + "                        ║");
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
                }
                
                System.out.print("\n Bạn có muốn xem thống kê khoảng thời gian khác? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                    System.out.println("✅ Thoát thống kê sản phẩm bán chạy.");
                    break;
                }
    
            } catch (Exception e) {
                System.out.println("❌ Đã xảy ra lỗi: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void xuatTatCaSanPham() {
        List<SanPhamDTO> danhSach = SanPhamDAO.getAllSanPham();
    
        if (danhSach.isEmpty()) {
            System.out.println("\n❌ Không có sản phẩm nào trong hệ thống!");
            return;
        }

        inDanhSachSanPham(danhSach, "TẤT CẢ SẢN PHẨM HIỆN CÓ");
    }

    public void xuatSanPhamTheoTrangThai(String trangThai) {
        List<SanPhamDTO> danhSach = SanPhamDAO.getSanPhamByTrangThai(trangThai);

        if (danhSach.isEmpty()) {
            System.out.println("\n❌ Không có sản phẩm nào với trạng thái: " + trangThai);
            return;
        }

        String tieuDe = "active".equals(trangThai) ? 
                    "SẢN PHẨM ĐANG KINH DOANH" : 
                    "SẢN PHẨM NGỪNG KINH DOANH";

        inDanhSachSanPham(danhSach, tieuDe);
    }

    public void inDanhSachSanPham(List<SanPhamDTO> danhSach, String title) {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                          " + title + "                                        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println();

        List<String> headers = List.of("Mã SP", "Tên sản phẩm", "Loại", "Đơn vị", 
                                    "SL Tồn", "Giá bán", "Giá trị tồn","Trạng thái");

        List<List<String>> rows = new ArrayList<>();
        long tongGiaTriTon = 0;
        long tongSoLuongTon = 0;

        for (SanPhamDTO sp: danhSach) {
            List<String> row = new ArrayList<>();

            long giaTriTon = (long) sp.getGiaBan() * sp.getSoLuongTon();
            String ttIcon = "active".equals(sp.getTrangThai()) ? "✅ Active" : "❌ Inactive";

            row.add(sp.getMaSP());
            row.add(sp.getTenSP());
            row.add(sp.getLoaiText());
            row.add(sp.getDonViText());
            row.add(String.format("%,d", sp.getSoLuongTon()));
            row.add(FormatUtil.formatVND(sp.getGiaBan()));
            row.add(FormatUtil.formatVND(giaTriTon));
            row.add(ttIcon);
            rows.add(row);

            tongGiaTriTon += giaTriTon;
            tongSoLuongTon += sp.getSoLuongTon();
        }

        tablePrinter.printTable(headers, rows);

        System.out.println("\n📊 TỔNG KẾT:");
        System.out.println("   • Tổng số sản phẩm: " + String.format("%,d", danhSach.size()));
        System.out.println("   • Tổng số lượng tồn: " + String.format("%,d", tongSoLuongTon));
        System.out.println("   • Tổng giá trị tồn kho: " + FormatUtil.formatVND(tongGiaTriTon));
    }
}