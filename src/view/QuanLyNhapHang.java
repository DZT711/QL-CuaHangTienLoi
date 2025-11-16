package view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import dao.NhapHangDAO;
import dao.SanPhamDAO;
import dao.NhaCungCapDAO;
import dao.NhanVienDAO;
import dao.ChiTietPhieuNhapDAO;
import dao.HangHoaDAO;
import dto.ChiTietPhieuNhapDTO;
import dto.NhaCungCapDTO;
import dto.NhanVienDTO;
import dto.NhapHangDTO;
import main.Main;
import util.FormatUtil;
import util.JDBCUtil;
import util.ValidatorUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class QuanLyNhapHang {
    public void menuQuanLyNhapHang() {
        Scanner scanner = new Scanner(System.in);
        boolean isAdmin = !"nhanvien".equalsIgnoreCase(Main.CURRENT_ACCOUNT.getRole());
        int maxChoice = isAdmin ? 7 : 4; 
        String format = "▒ %-76s ▒%n";

        while (true) {
            System.out.println("\n████████████████████████████████████████████████████████████████████████████████");
            System.out.println("██                                                                            ██");
            System.out.println("██                      HỆ THỐNG QUẢN LÝ NHẬP HÀNG                            ██");
            System.out.println("██                                                                            ██");
            System.out.println("████████████████████████████████████████████████████████████████████████████████");
            System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓ MENU CHỨC NĂNG ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
            System.out.printf(format, "[1] ➜ Tạo phiếu nhập hàng mới");
            System.out.printf(format, "[2] ➜ Tìm kiếm phiếu nhập");

            if (isAdmin) {
                System.out.printf(format, "[3] ➜ Chỉnh sửa phiếu nhập");
                System.out.printf(format, "[4] ➜ Thống kê phiếu nhập");
            }

            System.out.printf(format, String.format("[%d] ➜ Quản lý chi tiết phiếu nhập hàng", isAdmin ? 5 : 3));
            System.out.printf(format, String.format("[%d] ➜ Xuất file phiếu nhập hàng", isAdmin ? 6 : 4));

            if (isAdmin) {
                System.out.printf(format, "[7] ➜ Xuất báo cáo nhập hàng");
            }
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
                    taoPhieuNhap(); 
                    break;
                case 2: 
                    System.out.println("\n");
                    System.out.println(
                            "    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                    System.out.println(
                            "    ┃                         TÌM KIẾM PHIẾU NHẬP                        ┃");
                    System.out.println(
                            "    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    System.out.println(
                            "    ┃ [1] ➜ Tìm kiếm phiếu nhập theo mã                                  ┃");
                    System.out.println(
                            "    ┃ [2] ➜ Tìm kiếm phiếu nhập theo nhà cung cấp                        ┃");
                    System.out.println(
                            "    ┃ [3] ➜ Tìm kiếm phiếu nhập theo nhân viên                           ┃");
                    System.out.println(
                            "    ┃ [4] ➜ Tìm kiếm phiếu nhập theo ngày nhập                           ┃");
                    System.out.println(
                            "    ┃ [0] ➜ Thoát                                                        ┃");
                    System.out.println(
                            "    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                    while (true) {
                        String opt = scanner.nextLine().trim();

                        switch (opt) {
                            case "0":
                                System.out.println("Thoát tìm kiếm phiếu nhập thành công.");
                                break;
                            case "1":
                                timPhieuNhapTheoMa();
                                break;
                            case "2":
                                timPhieuNhapTheoMaNCC();
                                break;
                            case "3":
                                timPhieuNhapTheoMaNV();
                                break;
                            case "4":
                                timPhieuNhapTheoNgayNhap();
                                break;
                            default:
                                System.out.print("Lựa chọn không hợp lệ. Vui lòng nhập lại: ");
                                continue;
                        }
                        break;
                    }
                    break;
                case 3: 
                    if (isAdmin) suaPhieuNhap(); 
                    else {
                        QuanLyChiTietPhieuNhap qlctpn = new QuanLyChiTietPhieuNhap();
                        qlctpn.menuQuanLyChiTietPhieuNhap();
                    }
                    break;
                case 4: 
                    if (isAdmin) {
                        System.out.println("\n");
                        System.out.println(
                                "    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                        System.out.println(
                                "    ┃                        THỐNG KÊ PHIẾU NHẬP                         ┃");
                        System.out.println(
                                "    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                        System.out.println(
                                "    ┃ [1] ➜ Thống kê phiếu nhập theo khoảng thời gian                    ┃");
                        System.out.println(
                                "    ┃ [2] ➜ Thống kê phiếu nhập theo nhà cung cấp                        ┃");
                        System.out.println(
                                "    ┃ [3] ➜ Thống kê phiếu nhập theo nhân viên nhập                      ┃");
                        System.out.println(
                                "    ┃ [4] ➜ Thống kê phiếu nhập theo sản phẩm nhập                       ┃");
                        System.out.println(
                                "    ┃ [5] ➜ Thống kê phiếu nhập theo tháng / năm                         ┃");
                        System.out.println(
                                "    ┃ [0] ➜ Thoát                                                        ┃");
                        System.out.println(
                                "    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                        System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                        while (true) {
                            String opt = scanner.nextLine().trim();
                            switch (opt) {
                                case "0":
                                    System.out.println("Thoát thống kê phiếu nhập thành công.");
                                    break;
                                case "1":
                                    thongKePhieuNhapTheoNgay();
                                    break;
                                case "2":
                                    thongKePhieuNhapTheoNCC();
                                    break;
                                case "3":
                                    thongKePhieuNhapTheoNV();
                                    break;
                                case "4":
                                    thongKePhieuNhapTheoSanPham();
                                    break;
                                case "5":
                                    thongKePhieuNhapTheoThang();
                                    break;
                                default:
                                    System.out.print("Lựa chọn không hợp lệ. Vui lòng nhập lại: ");
                                    continue;
                            }
                            break;
                        }

                    }
                    else xuatPhieuNhapTheoMaPhieuNhap();
                    break;
                case 5:
                    if (isAdmin) {
                        QuanLyChiTietPhieuNhap qlctpn = new QuanLyChiTietPhieuNhap();
                        qlctpn.menuQuanLyChiTietPhieuNhap();
                    }
                    break;
                case 6:
                    if (isAdmin) xuatPhieuNhapTheoMaPhieuNhap();
                    break;
                case 7:
                    if (isAdmin) xuatBaoCaoNhapHangTheoNgay();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
                    break;
            }
        }
    }

    public void taoPhieuNhap() { 
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║                 TẠO PHIẾU NHẬP HÀNG                ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            
            Connection conn = null;
            String maPhieu = null; 
            
            try {
                System.out.print("→ Nhập mã nhà cung cấp (hoặc '0' để hủy): ");
                String maNCC = scanner.nextLine().trim();
                
                if ("0".equals(maNCC)) {
                    System.out.println("⚠️  Đã hủy tạo phiếu nhập.");
                    break;
                }

                if (maNCC.isEmpty()) {
                    System.out.println("❌ Mã nhà cung cấp không được để trống!");
                    continue;
                }

                NhaCungCapDTO ncc = NhaCungCapDAO.timnccTheoMa(maNCC);
                if (ncc == null) {
                    System.out.println("❌ Nhà cung cấp không tồn tại!");
                    System.out.print("→ Bạn có muốn thêm nhà cung cấp mới? (Y/N): ");

                    if ("Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                        QuanLyNhaCungCap qlncc = new QuanLyNhaCungCap();
                        qlncc.themNhaCungCap();
                    }
                    continue;
                }

                System.out.println("✅ Nhà cung cấp: " + ncc.getTenNCC());

                String maNV = Main.CURRENT_ACCOUNT.getMaNV();
                if (maNV == null || maNV.isEmpty()) {
                    System.out.println("❌ Không xác định được nhân viên đăng nhập!");
                    continue;
                }

                maPhieu = NhapHangDAO.generateMaPhieu();
                System.out.println("→ Mã phiếu nhập: " + maPhieu);

                NhapHangDTO pn = new NhapHangDTO(maPhieu, maNCC, maNV, 0, LocalDateTime.now());
                if (!NhapHangDAO.themPhieuNhap(pn)) {
                    System.out.println("❌ Không thể tạo phiếu nhập. Vui lòng thử lại.");
                    continue;
                }

                conn = JDBCUtil.getConnection();
                conn.setAutoCommit(false); 

                int tongTien = 0;
                int countSuccess = 0;
                
                System.out.println("\n📦 NHẬP CHI TIẾT HÀNG HÓA");

                while (true) {
                    System.out.print("→ Nhập mã sản phẩm (hoặc '0' để kết thúc): ");
                    String maSP = scanner.nextLine().trim();

                    ChiTietPhieuNhapDTO chiTiet = new ChiTietPhieuNhapDTO();
                    if (!chiTiet.nhapChiTietPhieuNhap(scanner, maPhieu, maSP, maNCC, ncc.getTenNCC())) break;

                    if (chiTiet.getTenSP() == null) continue;
                    
                    System.out.print("-> Nhập ngày sản xuất (dd/MM/yyyy): ");
                    String nsxInput = scanner.nextLine().trim();

                    if (!ValidatorUtil.isValidateDate(nsxInput)) continue;

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate ngaySanXuat = LocalDate.parse(nsxInput, formatter);

                    if (ngaySanXuat.isAfter(LocalDate.now())) {
                        System.out.println("❌ Ngày sản xuất không được sau ngày hiện tại!");
                        continue;
                    }

                    System.out.print("-> Nhập ngày hết hạn (dd/MM/yyyy): ");
                    String hsdInput = scanner.nextLine().trim();

                    if (!ValidatorUtil.isValidateDate(hsdInput)) continue;

                    LocalDate hanSuDung = LocalDate.parse(hsdInput, formatter);

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
                        System.out.println("⚠️  Cảnh báo: Sản phẩm đã hết hạn!");
                        System.out.print("→ Bạn có chắc muốn tiếp tục? (Y/N): ");
                        if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) continue;
                    }
                    
                    try {
                        String maHang = HangHoaDAO.taoHangHoa(conn, maSP, chiTiet.getSoLuong(), ngaySanXuat, hanSuDung);
                        if (maHang == null) {
                            throw new SQLException("Không thể tạo hàng hóa!");
                        }
                        chiTiet.setMaHang(maHang);
                        
                        if (!ChiTietPhieuNhapDAO.themChiTietPhieuNhap(conn, chiTiet)) {
                            throw new SQLException("Không thể thêm chi tiết!");
                        }
                        
                        if (!SanPhamDAO.congSoLuongTon(conn, maSP, chiTiet.getSoLuong())) {
                            throw new SQLException("Không thể cập nhật tồn kho!");
                        }
                        
                        
                        tongTien += chiTiet.getThanhTien();
                        countSuccess++;
                        System.out.println("✅ Đã thêm: " + chiTiet.getTenSP() + " x " + chiTiet.getSoLuong() + 
                                        " = " + FormatUtil.formatVND(chiTiet.getThanhTien()));
                        
                    } catch (SQLException e) {
                        System.out.println("⚠️  Lỗi: " + e.getMessage());
                        System.out.println("⚠️  Bỏ qua sản phẩm này.");
                    }
                }

                if (countSuccess == 0) {
                    conn.rollback();
                    NhapHangDAO.xoaPhieuNhapTheoMa(maPhieu);
                    System.out.println("⚠️  Không có sản phẩm nào được nhập. Đã hủy phiếu nhập.");
                } else {
                    if (!NhapHangDAO.capNhatTongTien(conn, maPhieu, tongTien)) {
                        throw new SQLException("Không thể cập nhật tổng tiền!");
                    }
                    
                    conn.commit();

                    System.out.println("\n╔════════════════════════════════════════════════════╗");
                    System.out.println("║           TẠO PHIẾU NHẬP THÀNH CÔNG                ║");
                    System.out.println("╚════════════════════════════════════════════════════╝");
                    System.out.println("✅ Mã phiếu: " + maPhieu);
                    System.out.println("✅ Nhà cung cấp: " + ncc.getTenNCC());
                    System.out.println("✅ Số sản phẩm: " + countSuccess);
                    System.out.println("📊 Tổng tiền: " + FormatUtil.formatVND(tongTien));
                }

                System.out.print("\n→ Tạo phiếu nhập khác? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) break;
                
            } catch (Exception e) {
                if (conn != null) {
                    try {
                        conn.rollback();
                        System.out.println("❌ Đã rollback toàn bộ thao tác!");
                        
                        if (maPhieu != null) {
                            NhapHangDAO.xoaPhieuNhapTheoMa(maPhieu);
                        }
                    } catch (SQLException ex) {
                        System.err.println("❌ Lỗi rollback: " + ex.getMessage());
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
    }

    public void timPhieuNhapTheoMa() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    TÌM KIẾM PHIẾU NHẬP THEO MÃ                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");

        while (true) {
            System.out.print("\n→ Nhập mã phiếu nhập (hoặc '0' để hủy): ");
            String maPhieu = scanner.nextLine().trim();
            
            if ("0".equals(maPhieu)) {
                System.out.println("✅ Thoát chức năng tìm kiếm.");
                break;
            }
        
            if (maPhieu.isEmpty()) {
                System.out.println("❌ Mã phiếu nhập không được để trống!");
                continue;
            }

            inPhieuNhap(maPhieu);

            System.out.print("→ Tiếp tục tìm kiếm phiếu nhập khác? (Y/N): ");
            if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                System.out.println("✅ Thoát chức năng tìm kiếm.");
                break;
            }
        }
    }

    public void timPhieuNhapTheoMaNCC() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║          TÌM PHIẾU NHẬP THEO NHÀ CUNG CẤP          ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        while (true) {
            System.out.print("\n→ Nhập mã nhà cung cấp (hoặc '0' để hủy): ");
            String maNCC = scanner.nextLine().trim();
    
            if ("0".equals(maNCC)) {
                System.out.println("✅ Thoát chức năng tìm kiếm.");
                break;
            }
            
            if (maNCC.isEmpty()) {
                System.out.println("❌ Mã nhà cung cấp không được để trống!");
                continue;
            }
            
            NhaCungCapDTO ncc = NhaCungCapDAO.timnccTheoMa(maNCC);
            if (ncc == null) {
                System.out.println("❌ Không tìm thấy nhà cung cấp với mã: " + maNCC);
                System.out.print("Tiếp tục tìm kiếm phiếu nhập theo nhà cung cấp khác? (y/n): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                    System.out.println("✅ Hoàn tất chức năng tìm kiếm.");
                    break;
                }
                continue;
            }
    
            List<NhapHangDTO> pnList = NhapHangDAO.timPhieuNhapTheoMaNCC(maNCC);
            if (pnList == null || pnList.isEmpty()) {
                System.out.println("⚠️  Nhà cung cấp này chưa có phiếu nhập nào.");
                System.out.print("\nTiếp tục tìm kiếm phiếu nhập theo nhà cung cấp khác? (y/n): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                    System.out.println("✅ Hoàn tất chức năng tìm kiếm.");
                    break;
                }
                continue;
            }
    
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║                THÔNG TIN NHÀ CUNG CẤP              ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.println("Mã NCC        : " + ncc.getMaNCC());
            System.out.println("Tên NCC       : " + ncc.getTenNCC());
            System.out.println("Địa chỉ       : " + (ncc.getDiaChi() != null ? ncc.getDiaChi() : "Chưa có"));
            System.out.println("Điện thoại    : " + (ncc.getDienThoai() != null ? ncc.getDienThoai() : "Chưa có"));
    
            long tongGiaTri = pnList.stream().mapToLong(NhapHangDTO::getTongTien).sum();
    
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║                 DANH SÁCH PHIẾU NHẬP               ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.println("Số lượng phiếu : " + pnList.size());
            System.out.println("Tổng giá trị   : " + FormatUtil.formatVND(tongGiaTri));
    
            System.out.println("\n┌─────┬────────────┬──────────────────────┬─────────────┬────────────────┐");
            System.out.printf("│ %-3s │ %-10s │ %-20s │ %-11s │ %-14s │%n",
                    "STT", "Mã phiếu", "Ngày lập", "Nhân viên", "Tổng tiền");
            System.out.println("├─────┼────────────┼──────────────────────┼─────────────┼────────────────┤");
    
            int stt = 1;
            for (NhapHangDTO pn : pnList) {
                System.out.printf("│ %-3d │ %-10s │ %-20s │ %-11s │ %14s │%n",
                    stt++,
                    pn.getMaPhieu(),
                    pn.getNgayLapPhieu().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    pn.getMaNV(),
                    FormatUtil.formatVND(pn.getTongTien()));
            }
    
            System.out.println("└─────┴────────────┴──────────────────────┴─────────────┴────────────────┘");

            while (true) {
                System.out.print("\n→ Bạn có muốn xem chi tiết phiếu nhập? (Y/N): ");
                
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                    System.out.println("✅ Hoàn tất tra cứu.");
                    break;
                }
                
                System.out.print("→ Nhập mã phiếu nhập cần xem (hoặc '0' để thoát): ");
                String maPhieu = scanner.nextLine().trim();
                
                if ("0".equals(maPhieu)) {
                    System.out.println("✅ Hoàn tất tra cứu.");
                    break;
                }
                
                if (maPhieu.isEmpty()) {
                    System.out.println("❌ Mã phiếu không được rỗng!");
                    continue;
                }
                
                boolean exists = pnList.stream().anyMatch(pn -> pn.getMaPhieu().equalsIgnoreCase(maPhieu));
                
                if (!exists) {
                    System.out.println("❌ Phiếu nhập không thuộc nhà cung cấp này!");
                    continue;
                }
                inPhieuNhap(maPhieu);
            }
            System.out.print("→ Tiếp tục tìm kiếm phiếu nhập khác? (Y/N): ");
            if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                System.out.println("✅ Hoàn tất chức năng tìm kiếm.");
                break;
            }
        }
    }

    public void timPhieuNhapTheoMaNV() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║            TÌM PHIẾU NHẬP THEO NHÂN VIÊN           ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        while (true) {
            System.out.print("\n→ Nhập mã nhân viên (hoặc '0' để thoát): ");
            String maNV = scanner.nextLine().trim();
    
            if ("0".equals(maNV)) {
                System.out.println("✅ Thoát chức năng tìm kiếm.");
                break;
            }
            
            if (maNV.isEmpty()) {
                System.out.println("❌ Mã nhân viên không được để trống!");
                continue;
            }

            NhanVienDTO nv = NhanVienDAO.timNhanVienTheoMa(maNV);
            if (nv == null) {
                System.out.println("❌ Không tìm thấy nhân viên với mã: " + maNV);
                System.out.print("Tiếp tục tìm kiếm phiếu nhập theo nhân viên khác? (y/n): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                    System.out.println("✅ Hoàn tất chức năng tìm kiếm.");
                    break;
                }
                continue;
            }
    
            List<NhapHangDTO> pnList = NhapHangDAO.timPhieuNhapTheoMaNV(maNV);
            if (pnList == null || pnList.isEmpty()) {
                System.out.println("⚠️  Nhân viên này chưa lập phiếu nhập nào.");
                System.out.print("Tiếp tục tìm kiếm phiếu nhập theo nhân viên khác? (y/n): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                    System.out.println("✅ Hoàn tất chức năng tìm kiếm.");
                    break;
                }
                continue;
            }
            
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║                THÔNG TIN NHÂN VIÊN                 ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.println("Mã NV         : " + nv.getMaNV());
            System.out.println("Họ tên        : " + nv.getHo() + " " + nv.getTen());
            System.out.println("Chức vụ       : " + nv.getChucVu());
            System.out.println("Email         : " + (nv.getEmail() != null ? nv.getEmail() : "Chưa có"));

            long tongGiaTri = pnList.stream().mapToLong(NhapHangDTO::getTongTien).sum();
    
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║                DANH SÁCH PHIẾU NHẬP                ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.println("Số lượng phiếu : " + pnList.size());
            System.out.println("Tổng giá trị   : " + FormatUtil.formatVND(tongGiaTri));
            
            System.out.println("\n┌─────┬────────────┬──────────────────────┬─────────────┬─────────────┐");
            System.out.printf("│ %-3s │ %-10s │ %-20s │ %-11s │ %-11s │%n",
                    "STT", "Mã phiếu", "Ngày lập", "Nhà CC", "Tổng tiền");
            System.out.println("├─────┼────────────┼──────────────────────┼─────────────┼─────────────┤");
    
            int stt = 1;
            for (NhapHangDTO pn : pnList) {
                System.out.printf("│ %-3d │ %-10s │ %-20s │ %-11s │ %11s │%n",
                        stt++,
                        pn.getMaPhieu(),
                        pn.getNgayLapPhieu().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                        pn.getMaNCC(),
                        FormatUtil.formatVND(pn.getTongTien()));
            }
    
            System.out.println("└─────┴────────────┴──────────────────────┴─────────────┴─────────────┘");

            while (true) {
                System.out.print("\n→ Bạn có muốn xem chi tiết phiếu nhập? (Y/N): ");
                
                if (!"Y".equals(scanner.nextLine().trim())) {
                    System.out.println("✅ Hoàn tất tra cứu.");
                    break;
                }
                
                System.out.print("→ Nhập mã phiếu nhập cần xem chi tiết (hoặc '0' để thoát): ");
                String maPhieu = scanner.nextLine().trim();
                
                if ("0".equals(maPhieu)) {
                    System.out.println("✅ Hoàn tất tra cứu.");
                    break;
                }
                
                if (maPhieu.isEmpty()) {
                    System.out.println("❌ Mã phiếu không được rỗng!");
                    continue;
                }
                
                boolean exists = pnList.stream().anyMatch(pn -> pn.getMaPhieu().equalsIgnoreCase(maPhieu));
                
                if (!exists) {
                    System.out.println("❌ Phiếu nhập không do nhân viên này lập!");
                    continue;
                }
                
                inPhieuNhap(maPhieu);
            }
            System.out.print("\n💡 Tiếp tục tìm kiếm nhân viên khác? (y/n): ");
            if (!"y".equalsIgnoreCase(scanner.nextLine().trim())) {
                System.out.println("✅ Hoàn tất chức năng tìm kiếm.");
                break;
            }
        }
    }

    public void timPhieuNhapTheoNgayNhap() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        boolean continueSearch = true;
        
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║        TÌM PHIẾU NHẬP THEO KHOẢNG THỜI GIAN        ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        while (continueSearch) {

            LocalDate fromDate = null;
            while (true) {
                System.out.print("\n→ Nhập ngày bắt đầu (dd/MM/yyyy) hoặc '0' để thoát: ");
                String from = scanner.nextLine().trim();

                if ("0".equals(from)) {
                    System.out.println("✅ Thoát chức năng tìm kiếm.");
                    continueSearch = false;
                    break;
                }

                if (!ValidatorUtil.isValidateDate(from))  continue;
                fromDate = LocalDate.parse(from, formatter);
                break; 
            }

            if (!continueSearch) break;

            LocalDate toDate = null ;
            while (true) {
                System.out.print("→ Nhập ngày kết thúc (dd/MM/yyyy) hoặc '0' để thoát: ");
                String to = scanner.nextLine().trim();
                
                if ("0".equals(to)) {
                    System.out.println("✅ Thoát chức năng tìm kiếm.");
                    continueSearch = false;
                    break;
                }

                if (!ValidatorUtil.isValidateDate(to))  continue;
                
                toDate = LocalDate.parse(to, formatter);
                
                if (fromDate.isAfter(toDate)) {
                    System.out.println("❌ Ngày bắt đầu phải trước hoặc bằng ngày kết thúc!");
                    continue;
                }
                break; 
            }

            if (!continueSearch) break;

            List<NhapHangDTO> pnList = NhapHangDAO.timPhieuNhapTheoNgay(fromDate, toDate);

            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║                  KẾT QUẢ TÌM KIẾM                  ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.println("Khoảng thời gian: " + fromDate.format(formatter) + " → " + toDate.format(formatter));

            if (pnList == null || pnList.isEmpty()) {
                System.out.println("\n⚠️  Không tìm thấy phiếu nhập nào trong khoảng thời gian này.");
                System.out.print("\n💡 Bạn có muốn tìm kiếm khoảng thời gian khác? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                    System.out.println("✅ Hoàn tất chức năng tìm kiếm.");
                    break;
                }
                continue;
            } 
            long tongGiaTri = pnList.stream().mapToLong(NhapHangDTO::getTongTien).sum();
            
            System.out.println("Số lượng phiếu  : " + pnList.size());
            System.out.println("Tổng giá trị    : " + FormatUtil.formatVND(tongGiaTri));
            
            System.out.println("\n┌─────┬────────────┬──────────────────────┬─────────────┬─────────────┬─────────────┐");
            System.out.printf("│ %-3s │ %-10s │ %-20s │ %-11s │ %-11s │ %-11s │%n",
                    "STT", "Mã phiếu", "Ngày lập", "NCC", "Nhân viên", "Tổng tiền");
            System.out.println("├─────┼────────────┼──────────────────────┼─────────────┼─────────────┼─────────────┤");
            
            int stt = 1;
            for (NhapHangDTO pn : pnList) {
                System.out.printf("│ %-3d │ %-10s │ %-20s │ %-11s │ %-11s │ %11s │%n",
                        stt++,
                        pn.getMaPhieu(),
                        pn.getNgayLapPhieu().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                        pn.getMaNCC(),
                        pn.getMaNV(),
                        FormatUtil.formatVND(pn.getTongTien()));
            }
            
            System.out.println("└─────┴────────────┴──────────────────────┴─────────────┴─────────────┴─────────────┘");

            while (true) {
                System.out.print("\n→ Nhập mã phiếu nhập cần xem chi tiết phiếu nhập (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                    System.out.println("✅ Hoàn tất tra cứu.");
                    break;
                }
            }

            System.out.print("→ Nhập mã phiếu nhập cần xem (hoặc '0' để thoát): ");
            String maPhieu = scanner.nextLine().trim();

            if ("0".equals(maPhieu)) {
                System.out.println("✅ Hoàn tất tra cứu.");
                break;
            }

            if (maPhieu.isEmpty()) {
                System.out.println("❌ Mã phiếu không được rỗng!");
                continue;
            }
            
            boolean exists = pnList.stream().anyMatch(pn -> pn.getMaPhieu().equalsIgnoreCase(maPhieu));

            if (!exists) {
                System.out.println("❌ Phiếu nhập không thuộc khoảng thời gian này!");
                continue;
            }

            inPhieuNhap(maPhieu);
            

            System.out.print("\n💡 Bạn có muốn tìm kiếm khoảng thời gian khác? (Y/N): ");
            if (!"y".equalsIgnoreCase(scanner.nextLine().trim())) {
                System.out.println("✅ Hoàn tất chức năng tìm kiếm.");
                break;
            }
        }
    }

    public void inPhieuNhap(String maPhieu) {
        try {
            NhapHangDTO pn = NhapHangDAO.timPhieuNhapTheoMa(maPhieu);
            if (pn == null) {
                System.out.println("❌ Không tìm thấy phiếu nhập với mã: " + maPhieu);
                return;
            } 

            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                        PHIẾU NHẬP HÀNG                       ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            
            System.out.println("Mã phiếu      : " + pn.getMaPhieu());
            System.out.println("Ngày nhập     : " + pn.getNgayLapPhieu().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            System.out.println("Mã nhân viên  : " + pn.getMaNV());
            
            NhaCungCapDTO ncc = NhaCungCapDAO.timnccTheoMa(pn.getMaNCC());
            if (ncc != null) {
                System.out.println("\n━━━━━━━━━━━━━━━ Thông tin nhà cung cấp ━━━━━━━━━━━━━━━");
                System.out.println("Mã NCC        : " + ncc.getMaNCC());
                System.out.println("Tên NCC       : " + ncc.getTenNCC());
                System.out.println("Địa chỉ       : " + (ncc.getDiaChi() != null ? ncc.getDiaChi() : "Chưa có"));
                System.out.println("Điện thoại    : " + (ncc.getDienThoai() != null ? ncc.getDienThoai() : "Chưa có"));
            }

            List <ChiTietPhieuNhapDTO> chiTietList = ChiTietPhieuNhapDAO.timChiTietPhieuNhap(maPhieu);
            if (chiTietList == null || chiTietList.isEmpty()) {
                System.out.println("\n⚠️ Phiếu nhập không có chi tiết nào.");
            } else {
                System.out.println("\n━━━━━━━━━━━━━━━━━━ Chi tiết phiếu nhập ━━━━━━━━━━━━━━━━━━");
                System.out.println("┌─────┬──────────────────────────┬──────────┬──────────┬─────────────┬─────────────┐");
                System.out.printf("│ %-3s │ %-24s │ %-8s │ %-8s │ %-11s │ %-11s │%n",
                        "STT", "Tên sản phẩm", "Đơn vị", "Số lượng", "Giá nhập", "Thành tiền");
                System.out.println("├─────┼──────────────────────────┼──────────┼──────────┼─────────────┼─────────────┤");

                int stt = 1;
                for (ChiTietPhieuNhapDTO ct : chiTietList) {
                    System.out.printf("│ %-3d │ %-24s │ %-8s │ %8s │ %11s │ %11s │%n",
                            stt++,
                            truncate(ct.getTenSP(), 24),
                            truncate(ct.getDonViTinh(), 8),
                            String.format("%,d", ct.getSoLuong()),
                            FormatUtil.formatVND(ct.getGiaNhap()),
                            FormatUtil.formatVND(ct.getThanhTien()));
                }

                System.out.println("├─────┴──────────────────────────┴──────────┴──────────┴─────────────┼─────────────┤");
                System.out.printf("│ %-52s │ %25s │%n", "TỔNG CỘNG", FormatUtil.formatVND(pn.getTongTien()));
                System.out.println("└────────────────────────────────────────────────────────────────────┴─────────────┘");
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi in phiếu nhập: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void suaPhieuNhap() { 
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║              SỬA THÔNG TIN PHIẾU NHẬP              ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        while (true) {
            System.out.print("\n→ Nhập mã phiếu nhập cần sửa (hoặc '0' để hủy): ");
            String maPhieu = scanner.nextLine().trim();
            
            if ("0".equals(maPhieu)) {
                System.out.println("⚠️  Đã hủy sửa phiếu nhập.");
                break;
            }
            
            if (maPhieu.isEmpty()) {
                System.out.println("❌ Mã phiếu nhập không được để trống!");
                continue;
            }
            NhapHangDTO pn = NhapHangDAO.timPhieuNhapTheoMa(maPhieu);
            if (pn == null) {
                System.out.println("❌ Không tìm thấy phiếu nhập với mã: " + maPhieu);
                continue;
            }

            List<ChiTietPhieuNhapDTO> chiTietList = ChiTietPhieuNhapDAO.timChiTietPhieuNhap(maPhieu);
            if (chiTietList != null && !chiTietList.isEmpty()) {
                System.out.println("❌ KHÔNG THỂ SỬA: Phiếu nhập này đã có chi tiết hàng hóa!");
                break;
            }

            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║          THÔNG TIN PHIẾU NHẬP HIỆN TẠI             ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.println("Mã phiếu        : " + pn.getMaPhieu());
            System.out.println("Nhà cung cấp    : " + pn.getMaNCC());
            System.out.println("Nhân viên       : " + pn.getMaNV());
            System.out.println("Ngày lập        : " + pn.getNgayLapPhieu().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            System.out.println("Tổng tiền       : " + FormatUtil.formatVND(pn.getTongTien()));

            System.out.println("\n📝 NHẬP THÔNG TIN MỚI");

            String newMaNCC = pn.getMaNCC();
            while (true) {
                System.out.print("→ Mã nhà cung cấp [" + pn.getMaNCC() + "]: ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) break;
                NhaCungCapDTO ncc = NhaCungCapDAO.timnccTheoMa(input);
                if (ncc == null) {
                    System.out.println("❌ Nhà cung cấp không tồn tại! Vui lòng nhập lại.");
                    continue;
                }
                newMaNCC = input;
                System.out.println("✅ Đã chọn: " + ncc.getTenNCC());
                break;
            }

            String newMaNV = pn.getMaNV();
            while (true) {
                System.out.print("→ Mã nhân viên [" + pn.getMaNV() + "]: ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) break;
                NhanVienDTO nv = NhanVienDAO.timNhanVienTheoMa(input);
                if (nv == null) {
                    System.out.println("❌ Nhân viên không tồn tại! Vui lòng nhập lại.");
                    continue;
                }
                newMaNV = input;
                System.out.println("✅ Đã chọn: " + nv.getHo() + " " + nv.getTen());
                break;
            }

            if (newMaNCC.equals(pn.getMaNCC()) && newMaNV.equals(pn.getMaNV())) {
                System.out.println("\n⚠️  Bạn không thay đổi thông tin nào.");
                break;
            }

            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║              THÔNG TIN THAY ĐỔI                    ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            if (!newMaNCC.equals(pn.getMaNCC()))
                System.out.println("Nhà cung cấp: " + pn.getMaNCC() + " → " + newMaNCC);
            if (!newMaNV.equals(pn.getMaNV()))
                System.out.println("Nhân viên   : " + pn.getMaNV() + " → " + newMaNV);

            System.out.print("\n→ Xác nhận sửa phiếu nhập? (Y/N): ");
            String confirm = scanner.nextLine().trim().toUpperCase();

            if (!"Y".equals(confirm)) {
                System.out.println("⚠️  Đã hủy sửa phiếu nhập.");
                break;
            }

            boolean success = NhapHangDAO.suaPhieuNhap(maPhieu, newMaNCC, newMaNV);

            if (success) {
                System.out.println("✅ Sửa phiếu nhập thành công!");
            } else {
                System.out.println("❌ Sửa phiếu nhập thất bại!");
            }
            break;
        }
    }

    public void thongKePhieuNhapTheoNgay() { 
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║            THỐNG KÊ PHIẾU NHẬP THEO KHOẢNG THỜI GIAN         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("💡 Nhập '0' ở bất kỳ bước nào để hủy thống kê và quay lại.");
        System.out.println();
        

        while (true) {
            try {
                LocalDate fromDate, toDate;
                while (true) {
                    System.out.print("Nhập ngày bắt đầu (dd/MM/yyyy): ");
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
                    System.out.print("Nhập ngày kết thúc (dd/MM/yyyy): ");
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
                        System.out.println("❌ Ngày bắt đầu phải trước hoặc bằng ngày kết thúc.");
                        continue;
                    }
                    break;
                }

                Map<String, Object> tongHop = NhapHangDAO.thongKePhieuNhapTheoNgay(fromDate, toDate);
                List<Map<String, Object>> chiTiet = NhapHangDAO.thongKeChiTietTheoNgay(fromDate, toDate);

                System.out.println("\n╔════════════════════════════════════════════════════╗");
                System.out.println("║             KẾT QUẢ THỐNG KÊ PHIẾU NHẬP            ║");
                System.out.println("╚════════════════════════════════════════════════════╝");
                System.out.println("Từ ngày       : " + fromDate.format(formatter));
                System.out.println("Đến ngày      : " + toDate.format(formatter));
            
                if (tongHop.isEmpty() || (long)tongHop.getOrDefault("tongGiaTri", 0L) == 0) {
                    System.out.println("\n⚠️ Không có dữ liệu phiếu nhập trong khoảng này!");
                } else {
                    System.out.println("Tổng số phiếu nhập   : " + tongHop.get("tongPhieuNhap") + " phiếu");
                    System.out.println("Tổng giá trị nhập    : " + FormatUtil.formatVND((long)tongHop.get("tongGiaTri")));
                    System.out.println("Tổng số sản phẩm     : " + tongHop.get("tongSanPham") + " sản phẩm");
                    System.out.println("Số nhà cung cấp      : " + tongHop.get("soNCC") + " nhà cung cấp");
                    System.out.println("Giá trị TB/phiếu     : " + FormatUtil.formatVND((long)tongHop.get("giaTriTB")));

                    if (!chiTiet.isEmpty()) {
                        System.out.println("\n━━━━━━━━━━━━━━━━━━ CHI TIẾT THEO NGÀY ━━━━━━━━━━━━━━━━━━");
                        System.out.println("┌────────────┬──────────────┬─────────────────┐");
                        System.out.printf("│ %-10s │ %-12s │ %-15s │%n", "Ngày", "Số phiếu", "Tổng giá trị");
                        System.out.println("├────────────┼──────────────┼─────────────────┤");

                        for (Map<String, Object> row : chiTiet) {
                        String day = ((LocalDate)row.get("Ngay")).format(formatter);
                        System.out.printf("│ %-10s │ %12d │ %15s │%n",
                            day,
                            row.get("SoPhieu"),
                            FormatUtil.formatVND((long)row.get("TongTien")));
                        }
                        System.out.println("└────────────┴──────────────┴─────────────────┘");
                    }
                }
                
                System.out.print("\nBạn có muốn thống kê khoảng thời gian khác? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                    System.out.println("✅ Hoàn tất chức năng thống kê.");
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

    public void thongKePhieuNhapTheoNCC() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║             THỐNG KÊ PHIẾU NHẬP THEO NHÀ CUNG CẤP            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("💡 Nhập '0' ở bất kỳ bước nào để hủy thống kê và quay lại.");
        System.out.println();

        while (true) {
            try {
                LocalDate fromDate, toDate;
                while (true) {
                    System.out.print("Nhập ngày bắt đầu (dd/MM/yyyy): ");
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
                    System.out.print("Nhập ngày kết thúc (dd/MM/yyyy): ");
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
                        System.out.println("❌ Ngày bắt đầu phải trước hoặc bằng ngày kết thúc.");
                        continue;
                    }
                    break;
                }

                List<Map<String, Object>> result = NhapHangDAO.thongKePhieuNhapTheoNCC(fromDate, toDate);

                System.out.println("\n╔════════════════════════════════════════════════════╗");
                System.out.println("║             KẾT QUẢ THỐNG KÊ PHIẾU NHẬP            ║");
                System.out.println("╚════════════════════════════════════════════════════╝");
                System.out.println("Từ ngày       : " + fromDate.format(formatter));
                System.out.println("Đến ngày      : " + toDate.format(formatter));

                if (result == null || result.isEmpty()) {
                    System.out.println("\n⚠️ Không có dữ liệu phiếu nhập trong khoảng này!");
                } else {
                    System.out.println("\n┌─────────┬────────────────────────┬──────────┬────────┬─────────────────┐");
                    System.out.printf("│ %-7s │ %-22s │ %-8s │ %-6s │ %-15s │%n", 
                    "Mã NCC", "Tên NCC", "Số phiếu", "Số SP", "Tổng giá trị");
                    System.out.println("├─────────┼────────────────────────┼──────────┼────────┼─────────────────┤");

                    int tongNCC = 0, tongPhieu = 0, tongSanPham = 0;
                    long tongGiaTri = 0;

                    for (Map<String, Object> row : result) {
                        if (row.get("MaNCC") == null || row.get("TenNCC") == null)  continue;
                
        
                        tongNCC++;
                        int soPhieu = (int) row.getOrDefault("SoPhieu", 0);
                        int soSanPham = (int) row.getOrDefault("TongSanPham", 0);
                        long giaTri = (long) row.getOrDefault("TongGiaTri", 0L);

        
                        tongPhieu += soPhieu;
                        tongSanPham += soSanPham;
                        tongGiaTri += giaTri;

                        String tenNCC = (String) row.get("TenNCC");
                        if (tenNCC.length() > 22) {
                            tenNCC = tenNCC.substring(0, 19) + "...";
                        }

                        System.out.printf("│ %-7s │ %-22s │ %8d │ %6d │ %15s │%n",
                            row.get("MaNCC"), 
                            tenNCC, 
                            soPhieu, 
                            soSanPham, 
                            FormatUtil.formatVND(giaTri));
                    }
                    System.out.println("├─────────┴────────────────────────┴──────────┴────────┼─────────────────┤");
                    System.out.printf("│ %-56s │ %15s │%n",
                            String.format("TỔNG CỘNG: %d NCC - %d phiếu - %s sản phẩm", 
                                    tongNCC, tongPhieu, String.format("%,d", tongSanPham)),
                            FormatUtil.formatVND(tongGiaTri));
                    System.out.println("└──────────────────────────────────────────────────────┴─────────────────┘");
                    
                    System.out.println("\n📊 Thống kê:");
                    System.out.println("   • Số nhà cung cấp: " + tongNCC);
                    System.out.println("   • Số phiếu nhập  : " + tongPhieu);
                    System.out.println("   • Số sản phẩm    : " + String.format("%,d", tongSanPham));
                    System.out.println("   • Tổng giá trị   : " + FormatUtil.formatVND(tongGiaTri));
                }

                System.out.print("\n💡 Bạn có muốn thống kê khoảng thời gian khác? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                    System.out.println("✅ Hoàn tất chức năng thống kê.");
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

    public void thongKePhieuNhapTheoNV() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║             THỐNG KÊ PHIẾU NHẬP THEO NHÀ CUNG CẤP            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("💡 Nhập '0' ở bất kỳ bước nào để hủy thống kê và quay lại.");
        System.out.println();

        while (true) {
            try {
                LocalDate fromDate, toDate;
                while (true) {
                    System.out.print("Nhập ngày bắt đầu (dd/MM/yyyy): ");
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
                    System.out.print("Nhập ngày kết thúc (dd/MM/yyyy): ");
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
                        System.out.println("❌ Ngày bắt đầu phải trước hoặc bằng ngày kết thúc.");
                        continue;
                    }
                    break;
                }

                List<Map<String, Object>> result = NhapHangDAO.thongKePhieuNhapTheoNV(fromDate, toDate);
        
                System.out.println("\n╔════════════════════════════════════════════════════╗");
                System.out.println("║             KẾT QUẢ THỐNG KÊ PHIẾU NHẬP            ║");
                System.out.println("╚════════════════════════════════════════════════════╝");
                System.out.println("Từ ngày       : " + fromDate.format(formatter));
                System.out.println("Đến ngày      : " + toDate.format(formatter));

                if (result == null || result.isEmpty()) {
                    System.out.println("\n⚠️  Không có dữ liệu phiếu nhập trong khoảng thời gian này!");
                } else {
                    System.out.println("\n┌───────┬────────────┬────────────────────────┬──────────┬────────┬─────────────────┐");
                    System.out.printf("│ %-5s │ %-10s │ %-22s │ %-8s │ %-6s │ %-15s │%n", 
                            "STT", "Mã NV", "Họ tên", "Số phiếu", "Số SP", "Tổng giá trị");
                    System.out.println("├───────┼────────────┼────────────────────────┼──────────┼────────┼─────────────────┤");

                    int stt = 1;
                    int tongNV = 0, tongPhieu = 0, tongSanPham = 0;
                    long tongGiaTri = 0;

                    for (Map<String, Object> row : result) {
                        if (row.get("MaNV") == null || row.get("HoTen") == null) {
                            continue;
                        }

                        tongNV++;
                        int soPhieu = (int) row.getOrDefault("SoPhieu", 0);
                        int soSanPham = (int) row.getOrDefault("TongSanPham", 0);
                        long giaTri = (long) row.getOrDefault("TongGiaTri", 0L);

                        tongPhieu += soPhieu;
                        tongSanPham += soSanPham;
                        tongGiaTri += giaTri;

                        String hoTen = (String) row.get("HoTen");
                        if (hoTen.length() > 22) {
                            hoTen = hoTen.substring(0, 19) + "...";
                        }

                        System.out.printf("│ %5d │ %-10s │ %-22s │ %8d │ %6d │ %15s │%n",
                                stt++,
                                row.get("MaNV"),
                                hoTen,
                                soPhieu,
                                soSanPham,
                                FormatUtil.formatVND(giaTri));
                    }

                    System.out.println("├───────┴────────────┴────────────────────────┴──────────┴────────┼─────────────────┤");
                    System.out.printf("│ %-63s │ %15s │%n",
                            String.format("TỔNG CỘNG: %d NV - %d phiếu - %s sản phẩm", 
                                    tongNV, tongPhieu, String.format("%,d", tongSanPham)),
                            FormatUtil.formatVND(tongGiaTri));
                    System.out.println("└────────────────────────────────────────────────────────────────┴─────────────────┘");

                    System.out.println("\n📊 Thống kê:");
                    System.out.println("   • Số nhân viên   : " + tongNV);
                    System.out.println("   • Số phiếu nhập  : " + tongPhieu);
                    System.out.println("   • Số sản phẩm    : " + String.format("%,d", tongSanPham));
                    System.out.println("   • Tổng giá trị   : " + FormatUtil.formatVND(tongGiaTri));
                }
                System.out.print("\n💡 Bạn có muốn thống kê khoảng thời gian khác? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                    System.out.println("✅ Hoàn tất chức năng thống kê.");
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

    public void thongKePhieuNhapTheoSanPham() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║             THỐNG KÊ PHIẾU NHẬP THEO SẢN PHẨM                ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("💡 Nhập '0' ở bất kỳ bước nào để hủy thống kê và quay lại.");
        System.out.println();
        while (true) {
            try {
                LocalDate fromDate, toDate;
                while (true) {
                    System.out.print("Nhập ngày bắt đầu (dd/MM/yyyy): ");
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
                    System.out.print("Nhập ngày kết thúc (dd/MM/yyyy): ");
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
                        System.out.println("❌ Ngày bắt đầu phải trước hoặc bằng ngày kết thúc.");
                        continue;
                    }
                    break;
                }

                List<Map<String, Object>> result = NhapHangDAO.thongKePhieuNhapTheoSanPham(fromDate, toDate);
                
                System.out.println("\n╔════════════════════════════════════════════════════╗");
                System.out.println("║             KẾT QUẢ THỐNG KÊ PHIẾU NHẬP            ║");
                System.out.println("╚════════════════════════════════════════════════════╝");
                System.out.println("Từ ngày       : " + fromDate.format(formatter));
                System.out.println("Đến ngày      : " + toDate.format(formatter));

                if (result == null || result.isEmpty()) {
                    System.out.println("\n⚠️  Không có dữ liệu phiếu nhập trong khoảng thời gian này!");
                } else {
                    System.out.println("\n┌───────┬─────────────────┬──────────────────────┬──────────┬────────┬─────────────────┐");
                    System.out.printf("│ %-5s │ %-15s │ %-20s │ %-8s │ %-6s │ %-15s │%n", 
                            "STT", "Mã SP", "Tên sản phẩm", "Số phiếu", "Số SP", "Tổng giá trị");
                    System.out.println("├───────┼─────────────────┼──────────────────────┼──────────┼────────┼─────────────────┤");

                    int stt = 1, tongPhieu = 0, tongSanPham = 0;
                    long tongGiaTri = 0;

                    for (Map<String, Object> row : result) {
                        if (row.get("MaSP") == null || row.get("TenSP") == null)  continue;
                    
                        int soPhieu = (int) row.getOrDefault("SoPhieu", 0);
                        int soSP = (int) row.getOrDefault("TongSanPham", 0);
                        long giaTri = (long) row.getOrDefault("TongGiaTri", 0L);
    
                        tongPhieu += soPhieu;
                        tongSanPham += soSP;
                        tongGiaTri += giaTri;
    
                        String tenSP = (String) row.get("TenSP");
                        if (tenSP.length() > 20) {
                            tenSP = tenSP.substring(0, 17) + "...";
                        }
    
                        System.out.printf("│ %5d │ %-15s │ %-20s │ %8d │ %6d │ %15s │%n",
                                stt++,
                                row.get("MaSP"),
                                tenSP,
                                soPhieu,
                                soSP,
                                FormatUtil.formatVND(giaTri));
                    }
                    System.out.println("├───────┴─────────────────┴──────────────────────┴──────────┴────────┼─────────────────┤");
                    System.out.printf("│ %-67s │ %15s │%n",
                            String.format("TỔNG CỘNG: %d sản phẩm - %d phiếu - %s SP nhập", 
                            result.size(), tongPhieu, String.format("%,d", tongSanPham)),
                            FormatUtil.formatVND(tongGiaTri));
                    System.out.println("└────────────────────────────────────────────────────────────────────┴─────────────────┘");
                    
                    System.out.println("\n📊 Thống kê:");
                    System.out.println("   • Số sản phẩm    : " + result.size());
                    System.out.println("   • Số phiếu nhập  : " + tongPhieu);
                    System.out.println("   • Số lượng nhập  : " + String.format("%,d", tongSanPham));
                    System.out.println("   • Tổng giá trị   : " + FormatUtil.formatVND(tongGiaTri));
                }
                System.out.print("\n💡 Bạn có muốn thống kê khoảng thời gian khác? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                    System.out.println("✅ Hoàn tất chức năng thống kê.");
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

    public void thongKePhieuNhapTheoThang() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║             THỐNG KÊ PHIẾU NHẬP THEO THÁNG / NĂM             ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("💡 Nhập '0' ở bất kỳ bước nào để hủy thống kê và quay lại.");
        System.out.println();

        while (true) {
            int year = 0;
            while (true) {
                System.out.print("\n→ Nhập năm cần thống kê (yyyy): ");
                String strYear = scanner.nextLine().trim();
    
                if ("0".equals(strYear)) {
                    System.out.println("✅ Thoát chức năng thống kê.");
                    return;
                }
                
                if (strYear.isEmpty()) {
                    System.out.println("❌ Năm không được bỏ trống!");
                    continue;
                }
    
                try {
                    year = Integer.parseInt(strYear);
                    
                    if (year < 2000) {
                        System.out.println("❌ Năm phải từ 2000 trở lên!");
                        continue;
                    }
                    
                    if (year > LocalDate.now().getYear()) {
                        System.out.println("❌ Năm không được trong tương lai!");
                        continue;
                    }
                    
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("⚠️  Năm không hợp lệ, vui lòng nhập dạng số (VD: 2025)!");
                }
            }
    
            List<Map<String, Object>> result;
            try {
                result = NhapHangDAO.thongKePhieuNhapTheoNam(year);
            } catch (Exception ex) {
                System.out.println("❌ Lỗi truy vấn thống kê: " + ex.getMessage());
                ex.printStackTrace();
                return;
            }
    
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║      KẾT QUẢ THỐNG KÊ PHIẾU NHẬP NĂM " + year + "  ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
    
            if (result == null || result.isEmpty()) {
                System.out.println("\n⚠️  Không có dữ liệu phiếu nhập trong năm " + year + "!");
            } else {
                System.out.println("\n┌───────────┬──────────────┬──────────────────┬──────────────────┐");
                System.out.printf("│ %-9s │ %-12s │ %-16s │ %-16s │%n",
                        "Tháng", "Số phiếu", "Tổng số lượng", "Tổng giá trị");
                System.out.println("├───────────┼──────────────┼──────────────────┼──────────────────┤");
    
                int tongPhieu = 0;
                long tongSoLuong = 0;
                long tongGiaTri = 0;
    
                for (Map<String, Object> row : result) {
                    Integer thang = (Integer) row.get("Thang");
                    Integer soPhieu = (Integer) row.getOrDefault("SoPhieu", 0);
                    Long soLuong = (Long) row.getOrDefault("TongSanPham", 0L);
                    Long giaTri = (Long) row.getOrDefault("TongGiaTri", 0L);
    
                    if (thang == null) continue;
    
                    tongPhieu += soPhieu;
                    tongSoLuong += soLuong;
                    tongGiaTri += giaTri;
    
                    System.out.printf("│ Tháng %-3d │ %12d │ %16s │ %16s │%n",
                            thang, soPhieu, String.format("%,d", soLuong), FormatUtil.formatVND(giaTri));
                }
    
                System.out.println("├───────────┼──────────────┼──────────────────┼──────────────────┤");
                System.out.printf("│ %-9s │ %12d │ %16s │ %16s │%n",
                        "TỔNG CỘNG", tongPhieu, String.format("%,d", tongSoLuong), FormatUtil.formatVND(tongGiaTri));
                System.out.println("└───────────┴──────────────┴──────────────────┴──────────────────┘");
            }
            System.out.print("\n💡 Bạn có muốn thống kê năm khác? (y/n): ");
            if (!"y".equalsIgnoreCase(scanner.nextLine().trim())) {
                System.out.println("✅ Hoàn tất chức năng thống kê.");
                break;
            }
        }
    }

    public void xuatPhieuNhapTheoMaPhieuNhap() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                     XUẤT PHIẾU NHẬP RA FILE                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        while (true) {
            System.out.print("\nNhập mã phiếu nhập cần xuất (hoặc '0' để thoát): ");
            String maPhieu = scanner.nextLine().trim();

            if (maPhieu.equals("0")) {
                System.out.println("✅ Thoát xuất phiếu nhập.");
                break;
            }
            if (maPhieu.isEmpty()) {
                System.out.println("❌ Mã phiếu nhập không được để trống!");
                continue;
            }

            try {
                NhapHangDTO pn = NhapHangDAO.timPhieuNhapTheoMa(maPhieu);
                if (pn == null) {
                    System.out.println("❌ Không tìm thấy phiếu nhập với mã: " + maPhieu);
                    continue;
                }

                List<ChiTietPhieuNhapDTO> chiTiet = ChiTietPhieuNhapDAO.timChiTietPhieuNhap(maPhieu);
                if (chiTiet == null || chiTiet.isEmpty()) {
                    System.out.println("⚠️  Phiếu nhập không có chi tiết, không thể xuất file.");
                    continue;
                }

                System.out.println("\n📄 Thông tin phiếu nhập:");
                System.out.println("   • Mã phiếu    : " + pn.getMaPhieu());
                System.out.println("   • Ngày lập    : " + pn.getNgayLapPhieu().format(formatter));
                System.out.println("   • Nhân viên   : " + pn.getMaNV());
                System.out.println("   • Nhà cung cấp: " + pn.getMaNCC());
                System.out.println("   • Số mặt hàng : " + chiTiet.size());
                System.out.println("   • Tổng tiền   : " + FormatUtil.formatVND(pn.getTongTien()));

                String fileName = "PhieuNhap_" + maPhieu + "_" + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";

                File file = new File(fileName);
                if (file.exists()) {
                    System.out.print("\n⚠️  File đã tồn tại. Bạn có muốn ghi đè? (y/n): ");
                    if (!"y".equalsIgnoreCase(scanner.nextLine().trim())) {
                        System.out.println("❌ Đã hủy xuất file.");
                        continue;
                    }
                }

                System.out.print("\n💾 Xác nhận xuất file? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                    System.out.println("❌ Đã hủy xuất file.");
                    continue;
                }

                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false))) {
                    writer.println("══════════════════════════════════════════════════════════════");
                    writer.println("                        PHIẾU NHẬP HÀNG                       ");
                    writer.println("══════════════════════════════════════════════════════════════");
                    writer.println();
                    writer.println("Mã phiếu       : " + pn.getMaPhieu());
                    writer.println("Ngày nhập      : " + pn.getNgayLapPhieu());
                    writer.println("Mã nhân viên   : " + pn.getMaNV());

                    NhaCungCapDTO ncc = NhaCungCapDAO.timnccTheoMa(pn.getMaNCC());
                    if (ncc != null) {
                        writer.println();
                        writer.println("━━━━━━━━━━━━━━━ Thông tin nhà cung cấp ━━━━━━━━━━━━━━━");
                        writer.println("Mã NCC     : " + ncc.getMaNCC());
                        writer.println("Tên NCC    : " + (ncc.getTenNCC() != null ? ncc.getTenNCC() : "Chưa có"));
                        writer.println("Địa chỉ    : " + (ncc.getDiaChi() != null ? ncc.getDiaChi() : "Chưa có"));
                        writer.println("Điện thoại : " + (ncc.getDienThoai() != null ? ncc.getDienThoai() : "Chưa có"));
                    }

                    writer.println();
                    writer.println("┌──────┬────────────────────────┬────────────┬──────────┬─────────────┬─────────────┐");
                    writer.printf("│ %-4s │ %-22s │ %-10s │ %-8s │ %-11s │ %-11s │%n",
                    "STT", "Tên sản phẩm", "Đơn vị", "Số lượng", "Giá nhập", "Thành tiền");
                    writer.println("├──────┼────────────────────────┼────────────┼──────────┼─────────────┼─────────────┤");


                    int stt = 1;
                    for (ChiTietPhieuNhapDTO ct : chiTiet) {
                        String tenSP = ct.getTenSP() != null ? ct.getTenSP() : "";
                        if (tenSP.length() > 22) {
                            tenSP = tenSP.substring(0, 19) + "...";
                        }
                        
                        String donVi = ct.getDonViTinh() != null ? ct.getDonViTinh() : "";
                        if (donVi.length() > 10) {
                            donVi = donVi.substring(0, 7) + "...";
                        }

                        writer.printf("│ %4d │ %-22s │ %-10s │ %8s │ %11s │ %11s │%n",
                                stt++,
                                tenSP,
                                donVi,
                                String.format("%,d", ct.getSoLuong()),
                                FormatUtil.formatVND(ct.getGiaNhap()),
                                FormatUtil.formatVND(ct.getThanhTien()));
                    }

                    
                    writer.println("├──────┴────────────────────────┴────────────┴──────────┼───────────────────────────┤");
                    writer.printf("│ %-53s │ %25s │%n", "TỔNG CỘNG", FormatUtil.formatVND(pn.getTongTien()));
                    writer.println("└───────────────────────────────────────────────────────┴───────────────────────────┘");

                    writer.println();
                    writer.println("Ngày xuất file : " + LocalDateTime.now().format(formatter));
                    writer.println();

                    System.out.println("✅ Xuất phiếu nhập thành công!");
                    System.out.println("📄 Tệp được lưu tại: " + new File(fileName).getAbsolutePath());
                } catch (IOException e) {
                    System.out.println("❌ Lỗi khi ghi file: " + e.getMessage());
                    System.out.println("   Vui lòng kiểm tra quyền truy cập thư mục.");
                    continue;
                }

                System.out.print("\n💡 Bạn có muốn xuất phiếu nhập khác? (Y/N): ");
                if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                    System.out.println("✅ Hoàn tất chức năng xuất file.");
                    break;
                }
            } catch (Exception e) {
                System.out.println("❌ Lỗi ngoài dự kiến: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void xuatBaoCaoNhapHangTheoNgay() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter fileFmt = DateTimeFormatter.ofPattern("yyyyMMdd");

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 XUẤT BÁO CÁO NHẬP HÀNG THEO NGÀY             ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        while (true) {
            LocalDate fromDate = null;
            while (true) {
                System.out.print("\n→ Nhập ngày bắt đầu (dd/MM/yyyy) hoặc '0' để thoát: ");
                String from = scanner.nextLine().trim();

                if ("0".equals(from)) {
                    System.out.println("✅ Thoát chức năng xuất báo cáo.");
                    return;
                }

                if (!ValidatorUtil.isValidateDate(from)) {
                    continue;
                }

                fromDate = LocalDate.parse(from, formatter);
                
                if (fromDate.isAfter(LocalDate.now())) {
                    System.out.println("❌ Ngày bắt đầu không được trong tương lai!");
                    continue;
                }
                
                break;
            }

            LocalDate toDate = null;
            while (true) {
                System.out.print("→ Nhập ngày kết thúc (dd/MM/yyyy) hoặc '0' để thoát: ");
                String to = scanner.nextLine().trim();
                
                if ("0".equals(to)) {
                    System.out.println("✅ Thoát chức năng xuất báo cáo.");
                    return;
                }
                
                if (!ValidatorUtil.isValidateDate(to)) {
                    continue;
                }

                toDate = LocalDate.parse(to, formatter);
                
                if (toDate.isAfter(LocalDate.now())) {
                    System.out.println("❌ Ngày kết thúc không được trong tương lai!");
                    continue;
                }
                
                if (fromDate.isAfter(toDate)) {
                    System.out.println("❌ Ngày bắt đầu phải trước hoặc bằng ngày kết thúc!");
                    continue;
                }
                
                break;
            }

            List<NhapHangDTO> danhSach = NhapHangDAO.timPhieuNhapTheoNgay(fromDate, toDate);

            if (danhSach == null || danhSach.isEmpty()) {
                System.out.println("\n⚠️  Không có phiếu nhập nào trong khoảng thời gian này!");
                continue;
            }

            long tongCong = danhSach.stream()
                .mapToLong(NhapHangDTO::getTongTien)
                .sum();

            System.out.println("\n📄 Thông tin báo cáo:");
            System.out.println("   • Từ ngày      : " + fromDate.format(formatter));
            System.out.println("   • Đến ngày     : " + toDate.format(formatter));
            System.out.println("   • Số phiếu     : " + danhSach.size());
            System.out.println("   • Tổng giá trị : " + FormatUtil.formatVND(tongCong));

            System.out.print("\n💾 Xác nhận xuất báo cáo? (Y/N): ");
            if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                System.out.println("❌ Đã hủy xuất báo cáo.");
                continue;
            }

            String fileName = String.format(
                "BaoCaoNhapHang_%s_den_%s.txt",
                fromDate.format(fileFmt),
                toDate.format(fileFmt)
            );

            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                writer.println("══════════════════════════════════════════════════════════════");
                writer.println("        BÁO CÁO NHẬP HÀNG THEO NGÀY        ");
                writer.println("Ngày lập báo cáo : " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                writer.println("Từ ngày: " + fromDate.format(formatter) + "  Đến ngày: " + toDate.format(formatter));
                writer.println();
                writer.println("┌────────────┬──────────────┬────────────┬────────────┬─────────────────┐");
                writer.printf("│ %-10s │ %-12s │ %-10s │ %-10s │ %-15s │%n",
                        "Mã phiếu", "Ngày nhập", "Mã NV", "Mã NCC", "Tổng tiền");
                writer.println("├────────────┼──────────────┼────────────┼────────────┼─────────────────┤");

                for (NhapHangDTO pn : danhSach) {
                    writer.printf("│ %-10s │ %-12s │ %-10s │ %-10s │ %15s │%n",
                        pn.getMaPhieu(),
                        pn.getNgayLapPhieu().toLocalDate().format(formatter),
                        pn.getMaNV(),
                        pn.getMaNCC(),
                        FormatUtil.formatVND(pn.getTongTien())
                    );
                }
                writer.println("├────────────┴──────────────┴────────────┴────────────┼─────────────────┤");
                writer.printf("│ %-51s │ %15s │%n", 
                        String.format("TỔNG CỘNG (%d phiếu)", danhSach.size()), 
                        FormatUtil.formatVND(tongCong));
                writer.println("└─────────────────────────────────────────────────────┴─────────────────┘");
                
                writer.println();
                System.out.println("✅ Xuất báo cáo nhập hàng thành công!");
                System.out.println("📄 Tệp được lưu tại: " + fileName);
                System.out.println("📊 Tổng số phiếu: " + danhSach.size());
                System.out.println("💰 Tổng giá trị: " + FormatUtil.formatVND(tongCong));
            } catch (IOException e) {
                System.out.println("❌ Lỗi khi ghi file: " + e.getMessage());
                System.out.println("   Vui lòng kiểm tra quyền truy cập thư mục.");
                continue;
            }

            System.out.print("\n💡 Bạn có muốn xuất báo cáo khác? (y/n): ");
            if (!"y".equalsIgnoreCase(scanner.nextLine().trim())) {
                System.out.println("✅ Hoàn tất chức năng xuất báo cáo.");
                break;
            }
        }
    }

    private String truncate(String str, int maxLen) {
        if (str == null) return "";
        if (str.length() <= maxLen) return str;
        return str.substring(0, maxLen - 3) + "...";
    }
}

