package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.mysql.cj.xdevapi.Schema.Validation.ValidationLevel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dao.ChiTietPhieuNhapDAO;
import dao.HangHoaDAO;
import dao.NhapHangDAO;
import dao.SanPhamDAO;
import dto.ChiTietPhieuNhapDTO;
import dto.NhapHangDTO;
import dto.SanPhamDTO;
import main.Main;
import util.FormatUtil;
import util.ValidatorUtil;

import java.util.Map;

public class QuanLyChiTietPhieuNhap {
    public void menuQuanLyChiTietPhieuNhap() {
        Scanner scanner = new Scanner(System.in);
        boolean isAdmin = !"nhanvien".equalsIgnoreCase(Main.CURRENT_ACCOUNT.getRole());
        int maxChoice = isAdmin ? 4 : 3;  
        String format = "▒ %-76s ▒%n";

        while (true) {
            System.out.println("\n████████████████████████████████████████████████████████████████████████████████");
            System.out.println("██                                                                            ██");
            System.out.println("██                      HỆ THỐNG QUẢN LÝ CHI TIẾT PHIẾU NHẬP                  ██");
            System.out.println("██                                                                            ██");
            System.out.println("████████████████████████████████████████████████████████████████████████████████");
            System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓ MENU CHỨC NĂNG ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
            System.out.printf(format, "[1] ➜ Thêm chi tiết vào phiếu nhập");
            System.out.printf(format, "[2] ➜ Tìm kiếm chi tiết phiếu nhập");
            System.out.printf(format, "[3] ➜ Xem danh sách chi tiết phiếu nhập");

            if (isAdmin) 
                System.out.printf(format, "[4] ➜ Thống kê sản phẩm nhập nhiều nhất");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
            System.out.println("░ [0] ✗ Quay lại menu chính                                                    ░");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            int choice = -1;

            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 0 && choice <= maxChoice) break;
                    System.out.print("❌ Vui lòng nhập số trong khoảng 0 – " + maxChoice + ": ");
                } else {
                    System.out.print("❌ Nhập không hợp lệ. Vui lòng nhập lại: ");
                    scanner.next();
                }
            }

            if (choice == 0) {
                System.out.println("✅ Quay lại menu chính thành công.");
                break;
            }

            switch (choice) {
                case 1:
                    themChiTietVaoPhieuNhap(scanner);
                    break;
                case 2:
                    timTheoMaPhieu(scanner);
                    break;
                case 3:
                    xemDanhSachChiTietPhieuNhap();
                    break;
                case 4:
                    if (isAdmin) thongKeSanPhamNhap(scanner);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
                    break;
            }
        }
    }

    public static void themChiTietVaoPhieuNhap(Scanner scanner) {
        boolean isAdmin = !"nhanvien".equalsIgnoreCase(Main.CURRENT_ACCOUNT.getRole());

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║            THÊM CHI TIẾT VÀO PHIẾU NHẬP            ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        String ma;
        NhapHangDTO phieuNhap = null;
        while (true) {
            System.out.print("\n→ Nhập mã phiếu nhập (hoặc '0' để hủy): ");
            ma = scanner.nextLine().trim();
            
            if ("0".equals(ma)) {
                System.out.println("⚠️  Đã hủy thao tác.");
                return;
            }
            if (ma.isEmpty()) {
                System.out.println("❌ Mã phiếu nhập không được để trống!");
                continue;
            }

            phieuNhap = NhapHangDAO.timPhieuNhapTheoMa(ma);
            if (phieuNhap == null) {
                System.out.println("❌ Không tìm thấy phiếu nhập với mã: " + ma);
                continue;
            }

            if (!isAdmin && !phieuNhap.getMaNV().equalsIgnoreCase(Main.CURRENT_ACCOUNT.getMaNV())) {
                System.out.println("❌ Bạn chỉ có quyền thêm chi tiết vào phiếu do mình tạo!");
                System.out.println("💡 Phiếu này do nhân viên " + phieuNhap.getMaNV() + " tạo.");
                continue;
            }
            break;
        }

        System.out.println("\n✅ Tìm thấy phiếu nhập:");
        System.out.println("   Mã phiếu: " + phieuNhap.getMaPhieu());
        System.out.println("   Nhà cung cấp: " + phieuNhap.getMaNCC());
        System.out.println("   Tổng tiền hiện tại: " + FormatUtil.formatVND(phieuNhap.getTongTien()));
        System.out.println("   Ngày lập: " + phieuNhap.getNgayLapPhieu().format(
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        String maPhieu = phieuNhap.getMaPhieu();
        String maNCC = phieuNhap.getMaNCC();

        Connection conn = null;
        boolean canceled = false;
        try {
            conn = util.JDBCUtil.getConnection();
            conn.setAutoCommit(false);

            int tongTienThem = 0;
            int countSuccess = 0;

            System.out.println("\n📦 NHẬP CHI TIẾT HÀNG HÓA  (nhập '0' để kết thúc)");
            productLoop:
            while (true) {
                System.out.print("\n -> Nhập mã sản phẩm: ");
                String maSP = scanner.nextLine().trim();
                if (maSP.equals("0")) break;

                if (maSP.isEmpty()) {
                    System.out.println("❌ Mã sản phẩm không được để trống!");
                    continue;
                }

                SanPhamDTO sanPham = SanPhamDAO.timSanPhamTheoMa(maSP);
                if (sanPham == null) {
                    System.out.println("❌ Không tìm thấy sản phẩm với mã: " + maSP);
                    continue;
                }

                if ("inactive".equals(sanPham.getTrangThai())) {
                    System.out.println("❌ Sản phẩm đã ngừng kinh doanh!");
                    System.out.println("💡 Không thể nhập hàng cho sản phẩm ngừng kinh doanh.");
                    continue;
                }
                
                System.out.println("✅ Sản phẩm: " + sanPham.getTenSP());

                if (!SanPhamDAO.kiemTraNCCCungCapSP(maNCC, maSP)) {
                    System.out.println("\n⚠️  CẢNH BÁO:");
                    System.out.println("   Nhà cung cấp này chưa từng cung cấp sản phẩm này!");
                    System.out.print("→ Bạn có chắc muốn tiếp tục? (Y/N): ");
                    if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                        System.out.println("⚠️  Đã hủy thêm sản phẩm " + maSP + " vào phiếu nhập.");
                        continue;
                    }
                }

                int soLuong;
                while (true) {
                    System.out.print("→ Số lượng (1 - 5000): ");
                    String slStr = scanner.nextLine().trim();

                    if ("0".equals(slStr)) {
                        canceled = true;
                        break productLoop;
                    }

                    try {
                        soLuong = Integer.parseInt(slStr);
                        if (soLuong > 0 && soLuong <= 5000) break; 
                        System.out.println("❌ Số lượng phải từ 1 đến 5000!");
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Vui lòng nhập số nguyên hợp lệ!");
                    }
                }

                int giaNhap;
                while (true) {
                    System.out.print("→ Giá nhập: ");
                    String giaStr = scanner.nextLine().trim();

                    if ("0".equals(giaStr)) {
                        canceled = true;
                        break productLoop;
                    }

                    try {
                        giaNhap = Integer.parseInt(giaStr);
                        if (giaNhap > 0 && giaNhap <= 1_000_000) break; 
                        System.out.println("❌ Giá nhập phải từ 1 đến 1,000,000!");
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Vui lòng nhập số nguyên hợp lệ!");
                    }
                }

                LocalDate ngaySanXuat;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                while (true) {
                    System.out.print("→ Ngày sản xuất (dd/MM/yyyy): ");
                    String nsxStr = scanner.nextLine().trim();

                    if ("0".equals(nsxStr)) {
                        canceled = true;
                        break productLoop;
                    }
                    
                    if (!ValidatorUtil.isValidateDate(nsxStr)) continue;
                    ngaySanXuat = LocalDate.parse(nsxStr, formatter);

                    if (ngaySanXuat.isAfter(LocalDate.now())) {
                        System.out.println("❌ Ngày sản xuất không được trong tương lai!");
                        continue;
                    }
                    break;
                }

                LocalDate hanSuDung;
                while (true) {
                    System.out.print("→ Hạn sử dụng (dd/MM/yyyy): ");
                    String hsdStr = scanner.nextLine().trim();

                    if ("0".equals(hsdStr)) {
                        canceled = true;
                        break productLoop;
                    }
                    
                    if (!ValidatorUtil.isValidateDate(hsdStr)) continue;
                    hanSuDung = LocalDate.parse(hsdStr, formatter);

                    if (!hanSuDung.isAfter(ngaySanXuat)) {
                        System.out.println("❌ Hạn sử dụng phải sau ngày sản xuất!");
                        continue;
                    }

                    LocalDate minHSD = ngaySanXuat.plusMonths(1);
                    if (hanSuDung.isBefore(minHSD)) {
                        System.out.println("❌ Hạn sử dụng phải cách ngày sản xuất ít nhất 1 tháng!");
                        continue;
                    }

                    if (hanSuDung.isBefore(LocalDate.now())) {
                        System.out.println("❌ Hàng này đã hết hạn! Vui lòng nhập lại.");
                        continue;
                    }
                    break;
                }

                int thanhTien = soLuong * giaNhap;
                try {
                    String maHang = HangHoaDAO.taoHangHoa(conn, maSP, soLuong, ngaySanXuat, hanSuDung);

                    if (maHang == null) 
                        throw new SQLException("Không thể tạo hàng hóa!");
                    
                    ChiTietPhieuNhapDTO chiTiet = new ChiTietPhieuNhapDTO(
                        maPhieu, maHang, sanPham.getTenSP(), null, soLuong, giaNhap, thanhTien
                    );
                
                    if (!ChiTietPhieuNhapDAO.themChiTietPhieuNhap(conn, chiTiet)) 
                        throw new SQLException("Không thể thêm chi tiết!");
                    
                    if (!SanPhamDAO.congSoLuongTon(conn, maSP, soLuong)) 
                        throw new SQLException("Không thể cập nhật tồn kho!");
                    
                    
                    tongTienThem += thanhTien;
                    countSuccess++;
                    System.out.println("✅ Đã thêm: " + sanPham.getTenSP() + " x " + soLuong + 
                                " = " + FormatUtil.formatVND(thanhTien) + "\n");
                } catch (SQLException e) {
                    System.out.println("⚠️  Lỗi: " + e.getMessage());
                    System.out.println("⚠️  Bỏ qua sản phẩm này.\n");
                }
            }

            if (canceled) {
                conn.rollback();
                System.out.println("\n⚠️  Đã hủy thao tác và rollback toàn bộ!");
                System.out.println("ℹ️  Không có thay đổi nào được lưu vào phiếu nhập.");
                return;
            }

            if (countSuccess > 0) {
                int tongTienMoi = phieuNhap.getTongTien() + tongTienThem;

                if (!NhapHangDAO.capNhatTongTien(conn, maPhieu, tongTienMoi)) 
                    throw new SQLException("Không thể cập nhật tổng tiền!");
                conn.commit();

                System.out.println("\n╔════════════════════════════════════════════════════╗");
                System.out.println("║           CẬP NHẬT PHIẾU NHẬP THÀNH CÔNG           ║");
                System.out.println("╚════════════════════════════════════════════════════╝");
                System.out.println("✅ Đã thêm: " + countSuccess + " sản phẩm");
                System.out.println("📊 Tổng tiền cũ: " + FormatUtil.formatVND(phieuNhap.getTongTien()));
                System.out.println("📊 Tổng tiền mới: " + FormatUtil.formatVND(tongTienMoi));
            
            } else {
                conn.rollback();
                System.out.println("⚠️  Không có sản phẩm nào được thêm.");
            }
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("❌ Đã rollback toàn bộ thao tác!");
                } catch (SQLException ex) {
                    System.err.println("❌Lỗi rollback: " + ex.getMessage());
                }
            }
            System.err.println("❌ Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("❌ Lỗi đóng connection: " + e.getMessage());
                }
            }
        }
    }

    public static void timTheoMaPhieu(Scanner scanner) {
        boolean isAdmin = !"nhanvien".equalsIgnoreCase(Main.CURRENT_ACCOUNT.getRole());

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║              TÌM CHI TIẾT PHIẾU NHẬP               ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        while (true) {
            System.out.print("\n→ Nhập mã phiếu nhập (hoặc '0' để hủy): ");
            String maPhieu = scanner.nextLine().trim();
    
            if ("0".equals(maPhieu)) {
                System.out.println("⚠️  Đã hủy tìm kiếm.");
                break;
            }
    
            if (maPhieu.isEmpty()) {
                System.out.println("❌ Mã phiếu nhập không được để trống!");
                continue;
            }
            
            NhapHangDTO phieuNhap = NhapHangDAO.timPhieuNhapTheoMa(maPhieu);
            if (phieuNhap == null) {
                System.out.println("❌ Không tìm thấy phiếu nhập với mã: " + maPhieu);
                continue;
            }

            if(!isAdmin && !phieuNhap.getMaNV().equalsIgnoreCase(Main.CURRENT_ACCOUNT.getMaNV())) {
                System.out.println("❌ Bạn chỉ có quyền xem chi tiết phiếu nhập này!");
                System.out.println("💡 Phiếu này do nhân viên " + phieuNhap.getMaNV() + " tạo.");
                continue;
            }

            List<ChiTietPhieuNhapDTO> chiTietList = ChiTietPhieuNhapDAO.timChiTietPhieuNhap(maPhieu);

            if (chiTietList.isEmpty()) {
                System.out.println("\n⚠️  Phiếu nhập này chưa có chi tiết nào.");
                System.out.println("💡 Bạn có thể thêm chi tiết vào phiếu nhập bằng chức năng 'Thêm chi tiết'.");
            } else {
                System.out.println("\n╔════════════════════════════════════════════════════╗");
                System.out.println("║                THÔNG TIN PHIẾU NHẬP                ║");
                System.out.println("╚════════════════════════════════════════════════════╝");
                System.out.println("Mã phiếu: " + phieuNhap.getMaPhieu());
                System.out.println("Nhà cung cấp: " + phieuNhap.getMaNCC());
                System.out.println("Nhân viên: " + phieuNhap.getMaNV());
                System.out.println("Ngày lập: " + phieuNhap.getNgayLapPhieu().format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                System.out.println("Tổng tiền: " + FormatUtil.formatVND(phieuNhap.getTongTien()));
                
                System.out.println("\n📦 CHI TIẾT PHIẾU NHẬP:");
                inBangChiTiet(chiTietList);
    
                int tongSoLuong = chiTietList.stream().mapToInt(ChiTietPhieuNhapDTO::getSoLuong).sum();
                System.out.println("\n📊 Tổng số mặt hàng: " + chiTietList.size());
                System.out.println("📊 Tổng số lượng: " + String.format("%,d", tongSoLuong));
                System.out.println("📊 Tổng tiền: " + FormatUtil.formatVND(phieuNhap.getTongTien()));
            }

            System.out.print("💡 Bạn có muốn tìm phiếu nhập khác? (y/n): ");
            if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                System.out.println("✅ Thoát chức năng tìm kiếm.");
                break;
            }
        }
    }

    public static void xemDanhSachChiTietPhieuNhap() {
        boolean isAdmin = !"nhanvien".equalsIgnoreCase(Main.CURRENT_ACCOUNT.getRole());

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║            DANH SÁCH CHI TIẾT PHIẾU NHẬP           ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        List<ChiTietPhieuNhapDTO> chiTietList = ChiTietPhieuNhapDAO.getAllChiTietPhieuNhap();

        if (!isAdmin) {
            String currentID = Main.CURRENT_ACCOUNT.getMaNV();

            List<String> maPhieuCuaNV = chiTietList.stream()
                .map(ChiTietPhieuNhapDTO::getMaPhieu)
                .distinct()
                .filter(maPhieu -> {
                    NhapHangDTO pn = NhapHangDAO.timPhieuNhapTheoMa(maPhieu);
                    return pn != null && currentID.equalsIgnoreCase(pn.getMaNV());
                })
                .collect(Collectors.toList());

            chiTietList = chiTietList.stream()
                .filter(ct -> maPhieuCuaNV.contains(ct.getMaPhieu()))
                .collect(Collectors.toList());

            if (chiTietList.isEmpty()) {
                System.out.println("\n⚠️  Bạn chưa có chi tiết phiếu nhập nào.");
                System.out.println("💡 Hãy tạo phiếu nhập mới và thêm chi tiết.");
                return;
            }
        }

        if (chiTietList.isEmpty()) {
            System.out.println("\n⚠️Không có chi tiết phiếu nhập nào trong hệ thống.");
            return;
        }

        System.out.println("Danh sách tất cả chi tiết phiếu nhập:");
        System.out.println("┌────────────┬────────────┬──────────────────────────┬────────────┬──────────┬─────────────┬───────────────┐");
        System.out.printf("│ %-10s │ %-10s │ %-24s │ %-10s │ %-8s │ %-11s │ %-13s │%n",
                "Mã phiếu", "Mã Hàng", "Tên SP", "Đơn vị", "SL", "Giá nhập", "Thành tiền");
        System.out.println("├────────────┼────────────┼──────────────────────────┼────────────┼──────────┼─────────────┼───────────────┤");

        int tongSoLuong = 0;
        long tongThanhTien = 0;

        for (ChiTietPhieuNhapDTO ct : chiTietList) {
            String tenSP = ct.getTenSP();
            if (tenSP.length() > 24) {
                tenSP = tenSP.substring(0, 21) + "...";
            }
            
            System.out.printf("│ %-10s │ %-10s │ %-24s │ %-10s │ %8s │ %11s │ %13s │%n",
                    ct.getMaPhieu(),
                    ct.getMaHang(),
                    tenSP,
                    ct.getDonViTinh(),
                    String.format("%,d", ct.getSoLuong()),
                    FormatUtil.formatVND(ct.getGiaNhap()),
                    FormatUtil.formatVND(ct.getThanhTien()));
            
            tongSoLuong += ct.getSoLuong();
            tongThanhTien += ct.getThanhTien();
        }

        System.out.println("├────────────┴────────────┴──────────────────────────┴────────────┴──────────┼─────────────────────────────┤");
        System.out.printf("│ %-74s │ %27s │%n", 
                "TỔNG CỘNG: " + String.format("%,d", tongSoLuong) + " sản phẩm",
                FormatUtil.formatVND(tongThanhTien));
        System.out.println("└────────────────────────────────────────────────────────────────────────────┴─────────────────────────────┘");

    }

    public static void inBangChiTiet(List<ChiTietPhieuNhapDTO> danhSach) {
        if (danhSach == null || danhSach.isEmpty()) {
            System.out.println("⚠️  Không có dữ liệu để hiển thị.");
            return;
        }
        
        System.out.println("\n┌─────┬────────────┬──────────┬──────────────────────────┬──────────┬─────────────┬─────────────┐");
        System.out.printf("│ %-3s │ %-10s │ %-8s │ %-24s │ %-8s │ %-11s │ %-11s │%n",
                "STT", "Mã Phiếu", "Mã Hàng", "Tên sản phẩm", "Số lượng", "Giá nhập", "Thành tiền");
        System.out.println("├─────┼────────────┼──────────┼──────────────────────────┼──────────┼─────────────┼─────────────┤");

        int stt = 1;
        long tongTien = 0;

        for (ChiTietPhieuNhapDTO ct : danhSach) {
            System.out.printf("│ %-3d │ %-10s │ %-8s │ %-24s │ %8s │ %11s │ %11s │%n",
                    stt++,
                    ct.getMaPhieu(),
                    ct.getMaHang(),
                    truncate(ct.getTenSP(), 24),
                    String.format("%,d", ct.getSoLuong()),
                    FormatUtil.formatVND(ct.getGiaNhap()),
                    FormatUtil.formatVND(ct.getThanhTien()));
            
            tongTien += ct.getThanhTien();
        }

        System.out.println("├─────┴────────────┴──────────┴──────────────────────────┴──────────┴─────────────┼─────────────┤");
        System.out.printf("│ %-65s │ %25s │%n", "TỔNG CỘNG", FormatUtil.formatVND(tongTien));
        System.out.println("└───────────────────────────────────────────────────────────────────────────────────────────────┘");
    }

    public static void thongKeSanPhamNhap(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║          THỐNG KÊ SẢN PHẨM NHẬP NHIỀU NHẤT         ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println("💡 Nhập '0' ở bất kỳ bước nào để hủy thống kê và quay lại.");
        System.out.println();

        while (true) {
            try {
                LocalDate fromDate, toDate;
                while (true) {
                    System.out.print("\nNhập ngày bắt đầu (dd/MM/yyyy): ");
                    String from = scanner.nextLine().trim();
        
                    if ("0".equals(from)) {
                        System.out.println("✅ Thoát chức năng thống kê.");
                        return;
                    }
        
                    if (!ValidatorUtil.isValidateDate(from)) continue;
                    fromDate = LocalDate.parse(from, formatter);
        
                    if (fromDate.isAfter(LocalDate.now())) {
                        System.out.println("❌  Ngày bắt đầu không được trong tương lai.");
                        continue;
                    }
                    break;
                }
        
                while (true) {
                    System.out.print("\nNhập ngày kết thúc (dd/MM/yyyy): ");
                    String to = scanner.nextLine().trim();
        
                    if ("0".equals(to)) {
                        System.out.println("✅ Thoát chức năng thống kê.");
                        return;
                    }
        
                    if (!ValidatorUtil.isValidateDate(to)) continue;
                    toDate = LocalDate.parse(to, formatter);
        
                    if (toDate.isAfter(LocalDate.now())) {
                        System.out.println("❌  Ngày kết thúc không được trong tương lai.");
                        continue;
                    }
        
                    if (fromDate.isAfter(toDate)) {
                        System.out.println("❌  Ngày bắt đầu phải trước ngày kết thúc.");
                        continue;
                    }
                    break;
                }
        
                int limit;
                while (true) {
                    System.out.print("\nNhập số lượng sản phẩm muốn xem (TOP): ");
                    String limitStr = scanner.nextLine().trim();
        
                    if ("0".equals(limitStr)) {
                        System.out.println("✅ Thoát chức năng thống kê.");
                        return;
                    }
        
                    try {
                        limit = Integer.parseInt(limitStr);
                        if (limit > 0 && limit <= 100) {
                            break;
                        }
                        System.out.println("❌ Số lượng phải từ 1 đến 100!");
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Vui lòng nhập số nguyên hợp lệ!");
                    }
                }

                List<Map<String, Object>> results = ChiTietPhieuNhapDAO.thongKeSanPhamNhapNhieuNhat(fromDate, toDate, limit);

                System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║                    TOP " + limit + " SẢN PHẨM NHẬP NHIỀU NHẤT (" + fromDate.format(formatter) + " - " + toDate.format(formatter) + ")             ║");
                System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════╝");
        
                if (results.isEmpty()) {
                    System.out.println("❌ Không có dữ liệu nhập hàng trong khoảng thời gian này.");
                } else {
                    System.out.println("┌─────┬────────────┬───────────────────────────┬──────────────┬──────────┬─────────────────┐");
                    System.out.printf("│ %-3s │ %-10s │ %-25s │ %-12s │ %-8s │ %-15s │%n",
                            "Top", "Mã SP", "Tên sản phẩm", "Tổng SL nhập", "Số lần", "Tổng giá trị");
                    System.out.println("├─────┼────────────┼───────────────────────────┼──────────────┼──────────┼─────────────────┤");
        
                    int top = 1;
                    int tongSoLuong = 0;
                    long tongGiaTri = 0;
            
                    for (Map<String, Object> row : results) {
                        int soLuongNhap = (int) row.get("TongSoLuongNhap");
                        long giaTriNhap = (long) row.get("TongGiaTriNhap");
        
                        tongSoLuong += soLuongNhap;
                        tongGiaTri += giaTriNhap;
            
                        String tenSP = (String) row.get("TenSP");
                        if (tenSP.length() > 25) tenSP = tenSP.substring(0, 22) + "...";
            
                        System.out.printf("│ %-3s │ %-10s │ %-25s │ %12s │ %8d │ %15s │%n",
                                    top++,
                                    row.get("MaSP"),
                                    tenSP,
                                    String.format("%,d", soLuongNhap),
                                    (int) row.get("SoLanNhap"),
                                    FormatUtil.formatVND(giaTriNhap));
                    }
                    System.out.println("├─────┴────────────┴───────────────────────────┴──────────────┴──────────┼─────────────────┤");
                    System.out.printf("│ %-70s │ %15s │%n",
                                "TỔNG CỘNG: " + String.format("%,d", tongSoLuong) + " sản phẩm",
                                FormatUtil.formatVND(tongGiaTri));
                    System.out.println("└────────────────────────────────────────────────────────────────────────┴─────────────────┘");
        
                    System.out.println("\n📊 Thống kê:");
                    System.out.println("   • Số loại sản phẩm: " + results.size());
                    System.out.println("   • Tổng số lượng: " + String.format("%,d", tongSoLuong));
                    System.out.println("   • Tổng giá trị: " + FormatUtil.formatVND(tongGiaTri));
                }
                System.out.print("\n💡 Bạn có muốn thống kê khoảng thời gian khác? (y/n): ");
                String choice = scanner.nextLine().trim();
                if (!"y".equalsIgnoreCase(choice)) {
                    System.out.println("✅ Hoàn tất thống kê sản phẩm nhập.");
                    break;
                }
            } catch (DateTimeParseException e) {
                System.out.println("❌ Định dạng ngày không hợp lệ! Vui lòng nhập theo dd/MM/yyyy.");
            } catch (Exception e) {
                System.out.println("❌ Lỗi xảy ra: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static String truncate(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
}