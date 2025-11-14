package view;

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
import dto.SanPhamDTO;
import main.Main;
import util.FormatUtil;
import util.JDBCUtil;
import util.ValidatorUtil;

import java.sql.Connection;
import java.sql.SQLException;

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
            System.out.println("▒ [2] ➜ Tìm kiếm phiếu nhập                                                    ▒");
            System.out.println("▒ [3] ➜ Chỉnh sửa phiếu nhập                                                   ▒");
            System.out.println("▒ [4] ➜ Thống kê phiếu nhập                                                    ▒");
            System.out.println("▒ [5] ➜ Quản lý chi tiết phiếu nhập hàng                                       ▒");
            System.out.println("▒ [6] ➜ Xuất file phiếu nhập hàng                                              ▒");
            System.out.println("▒ [7] ➜ Xuất báo cáo nhập hàng                                                 ▒");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
            System.out.println("░ [0] ✗ Quay lại menu chính                                                    ░");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            int choice = -1;

            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 0 && choice <= 7) break;
                    System.out.print("Vui lòng nhập số trong khoảng 0–7: ");
                } else {
                    System.out.print("Nhập không hợp lệ. Vui lòng nhập lại: ");
                    scanner.next();
                }
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
                    suaPhieuNhap(); 
                    break;
                case 4: 
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
                            "    ┃ [3] ➜ Thống kê phiếu nhập theo nhân viên nhập                        ┃");
                    System.out.println(
                            "    ┃ [4] ➜ Thống kê phiếu nhập theo sản phẩm nhập                         ┃");
                    System.out.println(
                            "    ┃ [5] ➜ Thống kê phiếu nhập theo tháng / năm                         ┃");
                    System.out.println(
                            "    ┃ [0] ➜ Thoát                                                          ┃");
                    System.out.println(
                            "    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
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
                    break;
                case 5:
                    view.QuanLyChiTietPhieuNhap.menuQuanLyChiTietPhieuNhap();
                    break;
                case 6:
                    xuatPhieuNhapTheoMaPhieuNhap();
                    break;
                case 7:
                    xuatBaoCaoNhapHangTheoNgay();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
                    break;
                case 0: 
                    System.out.println("Quay lại menu chính thành công.");
                    return;
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
                        if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                            break;
                        }
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

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║                 TÌM KIẾM PHIẾU NHẬP                ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        System.out.print("\n→ Nhập mã phiếu nhập (hoặc '0' để hủy): ");
        String maPhieu = scanner.nextLine().trim();
        
        if ("0".equals(maPhieu)) {
            System.out.println("⚠️  Đã hủy tìm kiếm.");
            return;
        }
    
        if (maPhieu.isEmpty()) {
            System.out.println("❌ Mã phiếu nhập không được để trống!");
            return;
        }
        inPhieuNhap(maPhieu);
    }

    public void timPhieuNhapTheoMaNCC() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║          TÌM PHIẾU NHẬP THEO NHÀ CUNG CẤP          ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        System.out.print("\n→ Nhập mã nhà cung cấp (hoặc '0' để hủy): ");
        String maNCC = scanner.nextLine().trim();

        if ("0".equals(maNCC)) {
            System.out.println("⚠️  Đã hủy tìm kiếm.");
            return;
        }
        
        if (maNCC.isEmpty()) {
            System.out.println("❌ Mã nhà cung cấp không được để trống!");
            return;
        }
        
        NhaCungCapDTO ncc = NhaCungCapDAO.timnccTheoMa(maNCC);
        if (ncc == null) {
            System.out.println("❌ Không tìm thấy nhà cung cấp với mã: " + maNCC);
            return;
        }


        List<NhapHangDTO> pnList = NhapHangDAO.timPhieuNhapTheoMaNCC(maNCC);
        if (pnList == null || pnList.isEmpty()) {
            System.out.println("⚠️  Nhà cung cấp này chưa có phiếu nhập nào.");
            return;
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

        System.out.println("\n┌─────┬────────────┬──────────────────────┬─────────────┬─────────────┐");
        System.out.printf("│ %-3s │ %-10s │ %-20s │ %-11s │ %-11s │%n",
                "STT", "Mã phiếu", "Ngày lập", "Nhân viên", "Tổng tiền");
        System.out.println("├─────┼────────────┼──────────────────────┼─────────────┼─────────────┤");

        int stt = 1;
        for (NhapHangDTO pn : pnList) {
            System.out.printf("│ %-3d │ %-10s │ %-20s │ %-11s │ %11s │%n",
                stt++,
                pn.getMaPhieu(),
                pn.getNgayLapPhieu().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                pn.getMaNV(),
                FormatUtil.formatVND(pn.getTongTien()));
        }

        System.out.println("└─────┴────────────┴──────────────────────┴─────────────┴─────────────┘");
        while (true) {
            System.out.print("\n→ Bạn có muốn xem chi tiết phiếu nhập? (Y/N): ");
            String choice = scanner.nextLine().trim().toUpperCase();
            
            if (!"Y".equals(choice)) {
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
            
            boolean exists = pnList.stream().anyMatch(pn -> pn.getMaPhieu().equals(maPhieu));
            
            if (!exists) {
                System.out.println("❌ Phiếu nhập không thuộc nhà cung cấp này!");
                continue;
            }
            
            inPhieuNhap(maPhieu);
        }
    }

    public void timPhieuNhapTheoMaNV() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║       TÌM PHIẾU NHẬP THEO NHÂN VIÊN                ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        System.out.print("\n→ Nhập mã nhân viên (hoặc '0' để hủy): ");
        String maNV = scanner.nextLine().trim();

        if ("0".equals(maNV)) {
            System.out.println("⚠️  Đã hủy tìm kiếm.");
            return;
        }
        
        if (maNV.isEmpty()) {
            System.out.println("❌ Mã nhân viên không được để trống!");
            return;
        }
        
        NhanVienDTO nv = NhanVienDAO.timNhanVienTheoMa(maNV);
        if (nv == null) {
            System.out.println("❌ Không tìm thấy nhân viên với mã: " + maNV);
            return;
        }

        List<NhapHangDTO> pnList = NhapHangDAO.timPhieuNhapTheoMaNV(maNV);
        if (pnList == null || pnList.isEmpty()) {
            System.out.println("⚠️  Nhân viên này chưa lập phiếu nhập nào.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║            THÔNG TIN NHÂN VIÊN                     ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println("Mã NV         : " + nv.getMaNV());
        System.out.println("Họ tên        : " + nv.getHo() + " " + nv.getTen());
        System.out.println("Chức vụ       : " + nv.getChucVu());
        System.out.println("Email         : " + (nv.getEmail() != null ? nv.getEmail() : "Chưa có"));

        long tongGiaTri = pnList.stream().mapToLong(NhapHangDTO::getTongTien).sum();

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║          DANH SÁCH PHIẾU NHẬP                      ║");
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
            String choice = scanner.nextLine().trim().toUpperCase();
            
            if (!"Y".equals(choice)) {
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
            
            boolean exists = pnList.stream().anyMatch(pn -> pn.getMaPhieu().equals(maPhieu));
            
            if (!exists) {
                System.out.println("❌ Phiếu nhập không do nhân viên này lập!");
                continue;
            }
            
            inPhieuNhap(maPhieu);
        }
    }

    public void timPhieuNhapTheoNgayNhap() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║        TÌM PHIẾU NHẬP THEO KHOẢNG THỜI GIAN        ║");
            System.out.println("╚════════════════════════════════════════════════════╝");

            LocalDate fromDate;
            while (true) {
                System.out.print("\n→ Nhập ngày bắt đầu (dd/MM/yyyy) hoặc '0' để hủy: ");
                String from = scanner.nextLine().trim();

                if ("0".equals(from)) {
                    System.out.println("⚠️  Đã hủy tìm kiếm.");
                    return;
                }

                try {
                    fromDate = LocalDate.parse(from, inputFormatter);
                    break; 
                } catch (DateTimeParseException e) {
                    System.out.println("❌ Định dạng ngày không hợp lệ! (VD: 25/10/2025)");
                }
            }

            LocalDate toDate;
            while (true) {
                System.out.print("→ Nhập ngày kết thúc (dd/MM/yyyy) hoặc '0' để hủy: ");
                String to = scanner.nextLine().trim();
                
                if ("0".equals(to)) {
                    System.out.println("⚠️  Đã hủy tìm kiếm.");
                    return;
                }
                
                try {
                    toDate = LocalDate.parse(to, inputFormatter);
                    
                    if (fromDate.isAfter(toDate)) {
                        System.out.println("❌ Ngày bắt đầu phải trước hoặc bằng ngày kết thúc!");
                        continue;
                    }
                    
                    break; 
                } catch (DateTimeParseException e) {
                    System.out.println("❌ Định dạng ngày không hợp lệ! (VD: 26/10/2025)");
                }
            }

            List<NhapHangDTO> pnList = NhapHangDAO.timPhieuNhapTheoNgay(fromDate, toDate);

            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║              KẾT QUẢ TÌM KIẾM                      ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.println("Khoảng thời gian: " + fromDate.format(displayFormatter) + " → " + toDate.format(displayFormatter));

            if (pnList == null || pnList.isEmpty()) {
                System.out.println("\n⚠️  Không tìm thấy phiếu nhập nào trong khoảng thời gian này.");
            } else {
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
            }
            
            System.out.print("\n→ Bạn có muốn tìm kiếm phiếu nhập khác? (Y/N): ");
            String choice = scanner.nextLine().trim().toUpperCase();
            
            if (!"Y".equals(choice)) {
                System.out.println("✅ Hoàn tất tìm kiếm.");
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
                System.out.printf("│ %-65s │ %11s │%n", "TỔNG CỘNG", FormatUtil.formatVND(pn.getTongTien()));
                System.out.println("└─────────────────────────────────────────────────────────────────────┴─────────────┘");
            }
            // System.out.println("\n╔══════════════════════════════════════════════════════════════");
            System.out.printf(" %-30s %n", "TỔNG TIỀN PHIẾU NHẬP: " + FormatUtil.formatVND(pn.getTongTien()));
            // System.out.println("╚══════════════════════════════════════════════════════════════");
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
        DateTimeFormatter inputFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter displayFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            try {
                System.out.print("Nhập ngày bắt đầu (dd/MM/yyyy): ");
                String from = scanner.nextLine().trim();

                System.out.print("Nhập ngày kết thúc (dd/MM/yyyy): ");
                String to = scanner.nextLine().trim();

                LocalDate fromDate = LocalDate.parse(from, inputFmt);
                LocalDate toDate = LocalDate.parse(to, inputFmt);

                if (fromDate.isAfter(toDate)) {
                    System.out.println("Ngày bắt đầu phải trước ngày kết thúc, vui lòng nhập lại.");
                    continue;
                }

                Map<String, Object> tongHop = NhapHangDAO.thongKePhieuNhapTheoNgay(fromDate, toDate);
                List<Map<String, Object>> chiTiet = NhapHangDAO.thongKeChiTietTheoNgay(fromDate, toDate);

                System.out.println("\n=== THỐNG KÊ PHIẾU NHẬP THEO THỜI GIAN ===");
                System.out.println("Từ ngày: " + fromDate.format(displayFmt));
                System.out.println("Đến ngày: " + toDate.format(displayFmt));
                System.out.println("-----------------------------------------");

                if (tongHop.isEmpty() || (long)tongHop.getOrDefault("tongGiaTri", 0L) == 0) {
                    System.out.println("Không có dữ liệu phiếu nhập trong khoảng này!");
                } else {
                    System.out.println("Tổng số phiếu nhập   : " + tongHop.get("tongPhieuNhap") + " phiếu");
                    System.out.println("Tổng giá trị nhập    : " + FormatUtil.formatVND((long)tongHop.get("tongGiaTri")));
                    System.out.println("Tổng số sản phẩm     : " + tongHop.get("tongSanPham") + " sản phẩm");
                    System.out.println("Số nhà cung cấp      : " + tongHop.get("soNCC") + " nhà cung cấp");
                    System.out.println("Giá trị TB/phiếu     : " + FormatUtil.formatVND((long)tongHop.get("giaTriTB")));
                }
                System.out.println("-----------------------------------------");

                System.out.println("Chi tiết theo ngày:");
                System.out.println("+------------+------------+-----------------+");
                System.out.println("| Ngày       | Số phiếu   | Tổng giá trị    |");
                System.out.println("+------------+------------+-----------------+");

                for (Map<String, Object> row : chiTiet) {
                    String day = ((LocalDate)row.get("Ngay")).format(displayFmt);
                    System.out.printf("| %-10s | %-10d | %-13s |\n",
                            day,
                            row.get("SoPhieu"),
                            FormatUtil.formatVND((long)row.get("TongTien")));
                }
                System.out.println("+------------+------------+-----------------+");

                System.out.print("\nBạn có muốn thống kê tiếp không? (y/n): ");
                String choice = scanner.nextLine().trim();
                if (!choice.equalsIgnoreCase("y")) {
                    System.out.println("Thoát thống kê phiếu nhập theo ngày.");
                    break;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Định dạng ngày không hợp lệ (đúng: dd/MM/yyyy), vui lòng nhập lại.");
            }
        }
    }

    public void thongKePhieuNhapTheoNCC() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter inputFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter displayFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            try {
                System.out.print("Nhập ngày bắt đầu (dd/MM/yyyy): ");
                String from = scanner.nextLine().trim();

                System.out.print("Nhập ngày kết thúc (dd/MM/yyyy): ");
                String to = scanner.nextLine().trim();

                if (from.isEmpty() || to.isEmpty()) {
                    System.out.println("⚠️ Ngày không được để trống!");
                    continue;
                }

                LocalDate fromDate, toDate;
                try {
                    fromDate = LocalDate.parse(from, inputFmt);
                    toDate = LocalDate.parse(to, inputFmt);
                } catch (DateTimeParseException e) {
                    System.out.println("⚠️ Định dạng ngày không hợp lệ (đúng: dd/MM/yyyy), vui lòng nhập lại.");
                    continue;
                }

                if (fromDate.isAfter(toDate)) {
                    System.out.println("⚠️ Ngày bắt đầu phải trước hoặc bằng ngày kết thúc, vui lòng nhập lại.");
                    continue;
                }

                List<Map<String, Object>> result;
                try {
                    result = NhapHangDAO.thongKePhieuNhapTheoNCC(fromDate, toDate);
                } catch (Exception ex) {
                    System.out.println("❌ Lỗi khi truy vấn database: " + ex.getMessage());
                    ex.printStackTrace();
                    break;
                }

                System.out.println("\n=== THỐNG KÊ PHIẾU NHẬP THEO NHÀ CUNG CẤP ===");
                System.out.println("Từ ngày: " + fromDate.format(displayFmt));
                System.out.println("Đến ngày: " + toDate.format(displayFmt));
                System.out.println("---------------------------------------------------------");

                if (result == null) {
                    System.out.println("❌ Không thể lấy dữ liệu từ DAO.");
                    System.out.println("---------------------------------------------------------");
                    System.out.print("\n✅ Bạn có muốn thống kê tiếp không? (y/n): ");
                    String choice = scanner.nextLine().trim().toLowerCase();
                    if (!"y".equals(choice)) break;
                    continue;
                }

                if (result.isEmpty()) {
                    System.out.println("❌ Không có dữ liệu phiếu nhập trong khoảng thời gian này.");
                    System.out.println("---------------------------------------------------------");
                    System.out.print("\n✅ Bạn có muốn thống kê tiếp không? (y/n): ");
                    String choice = scanner.nextLine().trim().toLowerCase();
                    if (!"y".equals(choice)) break;
                    continue;
                }

                System.out.printf("| %-7s | %-22s | %-8s | %-6s | %-13s |%n", "Mã NCC", "Tên NCC", "Số Phiếu", "Số SP", "Tổng Giá Trị");
                System.out.println("+---------+------------------------+----------+--------+---------------+");

                int tongNCC = 0, tongPhieu = 0, tongSanPham = 0;
                long tongGiaTri = 0;

                for (Map<String, Object> row : result) {
                    if (row.get("MaNCC")== null || row.get("TenNCC") == null || row.get("SoPhieu") == null || row.get("TongSanPham") == null || row.get("TongGiaTri") == null) continue;

                    tongNCC++;
                    int soPhieu = (int)row.get("SoPhieu");
                    int tongSoSanPham = (int)row.get("TongSanPham");
                    long giaTri = (long)row.get("TongGiaTri");

                    tongPhieu += soPhieu;
                    tongSanPham += tongSoSanPham;
                    tongGiaTri += giaTri;

                    System.out.printf("| %-7s | %-22s | %-8d | %-6d | %-13s |%n",
                            row.get("MaNCC"), row.get("TenNCC"), soPhieu, tongSoSanPham, FormatUtil.formatVND(giaTri));
                }

                System.out.println("+---------+------------------------+----------+--------+---------------+");
                System.out.println("Tổng số nhà cung cấp : " + tongNCC);
                System.out.println("Tổng số phiếu nhập   : " + tongPhieu);
                System.out.println("Tổng số sản phẩm     : " + tongSanPham);
                System.out.println("Tổng giá trị nhập    : " + FormatUtil.formatVND(tongGiaTri));
                System.out.println("---------------------------------------------------------");

                System.out.print("\n✅ Bạn có muốn thống kê tiếp không? (y/n): ");
                String choice = scanner.nextLine().trim().toLowerCase();
                if (!"y".equals(choice)) break;
            } catch (Exception e) {
                System.out.println("❌ Lỗi không xác định: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }
    }

    public void thongKePhieuNhapTheoNV() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter inputFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter displayFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            try {
                System.out.print("Nhập ngày bắt đầu (dd/MM/yyyy): ");
                String from = scanner.nextLine().trim();
                System.out.print("Nhập ngày kết thúc (dd/MM/yyyy): ");
                String to = scanner.nextLine().trim();

                if (from.isEmpty() || to.isEmpty()) {
                    System.out.println("⚠️ Ngày không được để trống!");
                    continue;
                }

                LocalDate fromDate, toDate;
                try {
                    fromDate = LocalDate.parse(from, inputFmt);
                    toDate = LocalDate.parse(to, inputFmt);
                } catch (DateTimeParseException e) {
                    System.out.println("⚠️ Định dạng ngày không hợp lệ (đúng: dd/MM/yyyy), vui lòng nhập lại.");
                    continue;
                }
                if (fromDate.isAfter(toDate)) {
                    System.out.println("⚠️ Ngày bắt đầu phải trước hoặc bằng ngày kết thúc, vui lòng nhập lại.");
                    continue;
                }

                List<Map<String, Object>> result;
                try {
                    result = NhapHangDAO.thongKePhieuNhapTheoNV(fromDate, toDate);
                } catch (Exception ex) {
                    System.out.println("❌ Lỗi khi truy vấn database: " + ex.getMessage());
                    ex.printStackTrace();
                    break;
                }

                System.out.println("\n=== THỐNG KÊ PHIẾU NHẬP THEO NHÂN VIÊN ===");
                System.out.println("Từ ngày: " + fromDate.format(displayFmt));
                System.out.println("Đến ngày: " + toDate.format(displayFmt));
                System.out.println("---------------------------------------------------------");

                if (result == null) {
                    System.out.println("❌ Không thể lấy dữ liệu từ DAO.");
                    System.out.print("\n✅ Bạn có muốn thống kê tiếp không? (y/n): ");
                    String choice = scanner.nextLine().trim().toLowerCase();
                    if (!choice.equals("y")) break;
                    continue;
                }
                if (result.isEmpty()) {
                    System.out.println("❌ Không có dữ liệu phiếu nhập trong khoảng thời gian này.");
                    System.out.print("\n✅ Bạn có muốn thống kê tiếp không? (y/n): ");
                    String choice = scanner.nextLine().trim().toLowerCase();
                    if (!choice.equals("y")) break;
                    continue;
                }

                System.out.printf("| %-5s | %-10s | %-22s | %-10s | %-10s | %-13s |\n", 
                    "STT", "Mã NV", "Họ Tên", "Số Phiếu", "Số SP", "Tổng Giá Trị");
                System.out.println("+------+------+------------------------+-----------+-----------+---------------+");

                int stt = 1;
                int tongNV = 0, tongPhieu = 0, tongSanPham = 0;
                long tongGiaTri = 0;

                for (Map<String, Object> row : result) {
                    Object maNV = row.get("MaNV");
                    Object hoTen = row.get("HoTen");
                    Object soPhieu = row.get("SoPhieu");
                    Object tongSP = row.get("TongSanPham");
                    Object tongGT = row.get("TongGiaTri");

                    if (maNV == null || hoTen == null || soPhieu == null || tongSP == null || tongGT == null) continue;

                    tongNV++;
                    int soPhieuInt = (int)soPhieu;
                    int tongSPInt = (int)tongSP;
                    long tongGTLong = (long)tongGT;

                    tongPhieu += soPhieuInt;
                    tongSanPham += tongSPInt;
                    tongGiaTri += tongGTLong;

                    System.out.printf("| %-5d | %-10s | %-22s | %-10d | %-10d | %-13s |\n",
                            stt++, maNV, hoTen, soPhieuInt, tongSPInt, FormatUtil.formatVND(tongGTLong));
                }

                System.out.println("+------+------+------------------------+-----------+-----------+---------------+");
                System.out.println("Tổng số nhân viên: " + tongNV);
                System.out.println("Tổng số phiếu nhập: " + tongPhieu);
                System.out.println("Tổng số sản phẩm: " + tongSanPham);
                System.out.println("Tổng giá trị nhập: " + FormatUtil.formatVND(tongGiaTri));
                System.out.println("---------------------------------------------------------");

                System.out.print("\n✅ Bạn có muốn thống kê tiếp không? (y/n): ");
                String choice = scanner.nextLine().trim().toLowerCase();
                if (!"y".equals(choice)) break;
            } catch (Exception e) {
                System.out.println("❌ Lỗi ngoài dự kiến: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }
    }

    public void thongKePhieuNhapTheoSanPham() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter inputFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter displayFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            try {
                System.out.print("Nhập ngày bắt đầu (dd/MM/yyyy): ");
                String from = scanner.nextLine().trim();
                System.out.print("Nhập ngày kết thúc (dd/MM/yyyy): ");
                String to = scanner.nextLine().trim();

                if (from.isEmpty() || to.isEmpty()) {
                    System.out.println("⚠️ Ngày không được để trống!");
                    continue;
                }
                LocalDate fromDate, toDate;
                try {
                    fromDate = LocalDate.parse(from, inputFmt);
                    toDate = LocalDate.parse(to, inputFmt);
                } catch (DateTimeParseException e) {
                    System.out.println("⚠️ Định dạng ngày không hợp lệ (đúng: dd/MM/yyyy), vui lòng nhập lại.");
                    continue;
                }
                if (fromDate.isAfter(toDate)) {
                    System.out.println("⚠️ Ngày bắt đầu phải trước hoặc bằng ngày kết thúc, vui lòng nhập lại.");
                    continue;
                }

                List<Map<String, Object>> result;
                try {
                    result = NhapHangDAO.thongKePhieuNhapTheoSanPham(fromDate, toDate);
                } catch (Exception ex) {
                    System.out.println("❌ Lỗi khi truy vấn database: " + ex.getMessage());
                    ex.printStackTrace();
                    break;
                }

                System.out.println("\n=== THỐNG KÊ PHIẾU NHẬP THEO SẢN PHẨM ===");
                System.out.println("Từ ngày: " + fromDate.format(displayFmt));
                System.out.println("Đến ngày: " + toDate.format(displayFmt));
                System.out.println("---------------------------------------------------------");

                if (result == null) {
                    System.out.println("❌ Không thể lấy dữ liệu từ DAO.");
                    System.out.print("\n✅ Bạn có muốn thống kê tiếp không? (y/n): ");
                    String choice = scanner.nextLine().trim().toLowerCase();
                    if (!choice.equals("y")) break;
                    continue;
                }

                if (result.isEmpty()) {
                    System.out.println("❌ Không có dữ liệu phiếu nhập trong khoảng thời gian này.");
                    System.out.print("\n✅ Bạn có muốn thống kê tiếp không? (y/n): ");
                    String choice = scanner.nextLine().trim().toLowerCase();
                    if (!choice.equals("y")) break;
                    continue;
                }

                System.out.printf("| %-5s | %-15s | %-20s | %-10s | %-10s | %-13s |\n", 
                    "STT", "Mã SP", "Tên SP", "Số Phiếu", "Số SP", "Tổng Giá Trị");
                System.out.println("+------+-----------------+----------------------+-----------+-----------+---------------+");

                int stt = 1, tongPhieu = 0, tongSanPham = 0;
                long tongGiaTri = 0;

                for (Map<String, Object> row : result) {
                    Object maSP = row.get("MaSP"), tenSP = row.get("TenSP"), soPhieu = row.get("SoPhieu"),
                        tongSP = row.get("TongSanPham"), tongGT = row.get("TongGiaTri");
                    if (maSP == null || tenSP == null || soPhieu == null || tongSP == null || tongGT == null) continue;

                    int soPhieuInt = (int)soPhieu;
                    int tongSPInt = (int)tongSP;
                    long tongGTLong = (long)tongGT;
                    tongPhieu += soPhieuInt;
                    tongSanPham += tongSPInt;
                    tongGiaTri += tongGTLong;

                    System.out.printf("| %-5d | %-15s | %-20s | %-10d | %-10d | %-13s |\n",
                            stt++, maSP, tenSP, soPhieuInt, tongSPInt, FormatUtil.formatVND(tongGTLong));
                }
                System.out.println("+------+-----------------+----------------------+-----------+-----------+---------------+");
                System.out.println("Tổng sản phẩm     : " + result.size());
                System.out.println("Tổng phiếu nhập   : " + tongPhieu);
                System.out.println("Tổng số nhập      : " + tongSanPham);
                System.out.println("Tổng giá trị nhập : " + FormatUtil.formatVND(tongGiaTri));
                System.out.println("---------------------------------------------------------");

                System.out.print("\n✅ Bạn có muốn thống kê tiếp không? (y/n): ");
                String choice = scanner.nextLine().trim().toLowerCase();
                if (!choice.equals("y")) break;
            } catch (Exception e) {
                System.out.println("❌ Lỗi ngoài dự kiến: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }
    }

    public void thongKePhieuNhapTheoThang() {
        Scanner scanner = new Scanner(System.in);
        int year = 0;

        while (true) {
            try {
                System.out.print("Nhập năm cần thống kê (yyyy): ");
                String strYear = scanner.nextLine().trim();
                if (strYear.isEmpty()) {
                    System.out.println("❌ Năm không được bỏ trống.");
                    continue;
                }
                year = Integer.parseInt(strYear);
                if (year < 2000 || year > LocalDate.now().getYear()) {
                    System.out.println("⚠️  Năm không hợp lệ, vui lòng nhập trong khoảng 2000 đến " + LocalDate.now().getYear());
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

        if (result == null) {
            System.out.println("❌ Không thể lấy dữ liệu từ DAO!");
            return;
        }
        if (result.isEmpty()) {
            System.out.println("❌ Không có dữ liệu phiếu nhập trong năm " + year + ".");
            return;
        }

        System.out.println("\n=== THỐNG KÊ PHIẾU NHẬP THEO THÁNG NĂM " + year + " ===");
        System.out.println("+-----------+------------+------------------+------------------+");
        System.out.printf("| %-9s | %-10s | %-16s | %-16s |\n",
                "Tháng", "Số Phiếu", "Tổng Số Lượng", "Tổng Giá Trị");
        System.out.println("+-----------+------------+------------------+------------------+");

        int tongPhieu = 0;
        long tongSoLuong = 0;
        long tongGiaTri = 0;

        for (Map<String, Object> row : result) {
            Integer thang = (Integer) row.get("Thang");
            Integer soPhieu = (Integer) row.get("SoPhieu");
            Long soLuong = (Long) row.get("TongSanPham");
            Long giaTri = (Long) row.get("TongGiaTri");

            if (thang == null || soPhieu == null || soLuong == null || giaTri == null) continue;

            tongPhieu += soPhieu;
            tongSoLuong += soLuong;
            tongGiaTri += giaTri;

            System.out.printf("| %-9d | %-10d | %-16d | %-16s |\n",
                    thang, soPhieu, soLuong, FormatUtil.formatVND(giaTri));
        }

        System.out.println("+-----------+------------+------------------+------------------+");
        System.out.printf("| %-9s | %-10d | %-16d | %-16s |\n",
                "TỔNG CỘNG", tongPhieu, tongSoLuong, FormatUtil.formatVND(tongGiaTri));
        System.out.println("+-----------+------------+------------------+------------------+");
    }

    public void xuatPhieuNhapTheoMaPhieuNhap() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\nNhập mã phiếu nhập cần xuất (0 để thoát): ");
            String maPhieu = scanner.nextLine().trim();

            if (maPhieu.equals("0")) {
                System.out.println("✅ Thoát xuất phiếu nhập.");
                break;
            }
            if (maPhieu.isEmpty()) {
                System.out.println("⚠️  Mã phiếu nhập không được để trống!");
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

                String fileName = "PhieuNhap_" + maPhieu + ".txt";
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false))) {
                    writer.println("══════════════════════════════════════════════════════════════");
                    writer.println("                   PHIẾU NHẬP HÀNG                          ");
                    writer.println("══════════════════════════════════════════════════════════════");
                    writer.println("Mã phiếu       : " + pn.getMaPhieu());
                    writer.println("Ngày nhập      : " + pn.getNgayLapPhieu());
                    writer.println("Mã nhân viên   : " + pn.getMaNV());

                    NhaCungCapDTO ncc = NhaCungCapDAO.timnccTheoMa(pn.getMaNCC());
                    if (ncc != null) {
                        writer.println("\n--- Thông tin nhà cung cấp ---");
                        writer.println("Tên NCC    : " + (ncc.getTenNCC() != null ? ncc.getTenNCC() : "Chưa có"));
                        writer.println("Địa chỉ    : " + (ncc.getDiaChi() != null ? ncc.getDiaChi() : "Chưa có"));
                        writer.println("Điện thoại : " + (ncc.getDienThoai() != null ? ncc.getDienThoai() : "Chưa có"));
                    }

                    writer.println("\n──────────────────────────────────────────────────────────────");
                    writer.printf("%-6s | %-20s | %-10s | %-8s | %-12s | %-12s%n",
                            "STT", "Tên sản phẩm", "Đơn vị", "Số lượng", "Giá nhập", "Thành tiền");
                    writer.println("──────────────────────────────────────────────────────────────");

                    int stt = 1;
                    for (ChiTietPhieuNhapDTO ct : chiTiet) {
                        writer.printf("%-6d | %-20s | %-10s | %-8d | %-12s | %-12s%n",
                                stt++,
                                ct.getTenSP() != null ? ct.getTenSP() : "",
                                ct.getDonViTinh() != null ? ct.getDonViTinh() : "",
                                ct.getSoLuong(),
                                FormatUtil.formatVND(ct.getGiaNhap()),
                                FormatUtil.formatVND(ct.getThanhTien()));
                    }
                    writer.println("──────────────────────────────────────────────────────────────");
                    writer.println("Tổng tiền: " + FormatUtil.formatVND(pn.getTongTien()));
                    writer.println("══════════════════════════════════════════════════════════════");

                    System.out.println("✅ Xuất phiếu nhập thành công!");
                    System.out.println("📄 Tệp được lưu tại: " + fileName);

                } catch (IOException e) {
                    System.out.println("❌ Lỗi khi xuất file: " + e.getMessage());
                }

                System.out.print("\nBạn có muốn xuất phiếu nhập khác không? (y/n): ");
                String choice = scanner.nextLine().trim().toLowerCase();
                if (!choice.equals("y")) break;

            } catch (Exception e) {
                System.out.println("❌ Lỗi ngoài dự kiến: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void xuatBaoCaoNhapHangTheoNgay() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter inputFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter fileFmt = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter displayFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            try {
                System.out.print("\nNhập ngày bắt đầu (dd/MM/yyyy): ");
                String from = scanner.nextLine().trim();
                System.out.print("Nhập ngày kết thúc (dd/MM/yyyy): ");
                String to = scanner.nextLine().trim();

                if (from.isEmpty() || to.isEmpty()) {
                    System.out.println("⚠️  Ngày không được để trống!");
                    continue;
                }

                LocalDate fromDate, toDate;
                try {
                    fromDate = LocalDate.parse(from, inputFmt);
                    toDate = LocalDate.parse(to, inputFmt);
                } catch (DateTimeParseException e) {
                    System.out.println("⚠️  Định dạng ngày không hợp lệ! Vui lòng nhập lại (dd/MM/yyyy).");
                    continue;
                }

                if (fromDate.isAfter(toDate)) {
                    System.out.println("⚠️  Ngày bắt đầu phải trước hoặc bằng ngày kết thúc!");
                    continue;
                }

                List<NhapHangDTO> danhSach = NhapHangDAO.timPhieuNhapTheoNgay(fromDate, toDate);

                if (danhSach == null || danhSach.isEmpty()) {
                    System.out.println("❌ Không có phiếu nhập nào trong khoảng thời gian này.");
                    System.out.print("\nBạn có muốn xuất báo cáo khác không? (y/n): ");
                    String choice = scanner.nextLine().trim().toLowerCase();
                    if (!choice.equals("y")) break;
                    continue;
                }

                String fileName = String.format(
                    "BaoCaoNhapHang_%s_den_%s.txt",
                    fromDate.format(fileFmt),
                    toDate.format(fileFmt)
                );

                long tongCong = 0L;
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                    writer.println("══════════════════════════════════════════════════════════════");
                    writer.println("        BÁO CÁO NHẬP HÀNG THEO NGÀY        ");
                    writer.println("Ngày lập báo cáo: " + LocalDate.now().format(displayFmt));
                    writer.println("Từ ngày: " + fromDate.format(displayFmt) + "  Đến ngày: " + toDate.format(displayFmt));
                    writer.println("══════════════════════════════════════════════════════════════");
                    writer.printf("%-10s | %-15s | %-10s | %-10s | %-15s%n",
                            "Mã Phiếu", "Ngày Nhập", "Mã NV", "Mã NCC", "Tổng Tiền");
                    writer.println("──────────────────────────────────────────────────────────────");

                    for (NhapHangDTO pn : danhSach) {
                        writer.printf("%-10s | %-15s | %-10s | %-10s | %-15s%n",
                            pn.getMaPhieu(),
                            pn.getNgayLapPhieu().toLocalDate().format(displayFmt),
                            pn.getMaNV(),
                            pn.getMaNCC(),
                            FormatUtil.formatVND(pn.getTongTien())
                        );
                        tongCong += pn.getTongTien();
                    }
                    writer.println("──────────────────────────────────────────────────────────────");
                    writer.println("TỔNG CỘNG: " + FormatUtil.formatVND(tongCong));
                    writer.println("══════════════════════════════════════════════════════════════");
                } catch (IOException e) {
                    System.out.println("❌ Lỗi khi xuất file báo cáo: " + e.getMessage());
                    System.out.print("\nBạn có muốn xuất báo cáo khác không? (y/n): ");
                    String choice = scanner.nextLine().trim().toLowerCase();
                    if (!choice.equals("y")) break;
                    continue;
                }

                System.out.println("✅ Xuất báo cáo nhập hàng thành công!");
                System.out.println("📄 Tệp được lưu tại: " + fileName);
                System.out.println("📊 Tổng số phiếu: " + danhSach.size());
                System.out.println("💰 Tổng giá trị: " + FormatUtil.formatVND(tongCong));

                System.out.print("\nBạn có muốn xuất báo cáo khác không? (y/n): ");
                String choice = scanner.nextLine().trim().toLowerCase();
                if (!choice.equals("y")) break;

            } catch (Exception e) {
                System.out.println("❌ Lỗi ngoài dự kiến: " + e.getMessage());
                e.printStackTrace();
                System.out.print("\nBạn có muốn xuất báo cáo khác không? (y/n): ");
                String choice = scanner.nextLine().trim().toLowerCase();
                if (!choice.equals("y")) break;
            }
        }
    }
    private String truncate(String str, int maxLen) {
        if (str == null) return "";
        if (str.length() <= maxLen) return str;
        return str.substring(0, maxLen - 3) + "...";
    }
}

