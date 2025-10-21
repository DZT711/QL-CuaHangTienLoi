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
import dao.NhaCungCapDAO;
import dao.NhanVienDAO;
import dao.ChiTietPhieuNhapDAO;
import dto.ChiTietPhieuNhapDTO;
import dto.NhaCungCapDTO;
import dto.NhanVienDTO;
import dto.NhapHangDTO;
import main.Main;
import util.FormatUtil;

import java.sql.Connection;


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
                    while (true) {
                        try {
                            System.out.println("\n");
                            System.out.println("Tìm kiếm phiếu nhập");
                            System.out.println("1. Tìm kiếm phiếu nhập theo mã");
                            System.out.println("2. Tìm kiếm phiếu nhập theo mã nhà cung cấp");
                            System.out.println("3. Tìm kiếm phiếu nhập theo mã nhân viên");
                            System.out.println("4. Tìm kiếm phiếu nhập theo ngày nhập");
                            System.out.println("0. Thoát");
                            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

                            int opt = scanner.nextInt();
                            scanner.nextLine();

                            if (opt == 0) {
                                System.out.println("Thoát tìm kiếm phiếu nhập thành công.");
                                break;
                            }

                            switch (opt) {
                                case 1:
                                    timPhieuNhapTheoMa();
                                    break;
                                case 2:
                                    timPhieuNhapTheoMaNCC();
                                    break;
                                case 3:
                                    timPhieuNhapTheoMaNV();
                                    break;
                                case 4:
                                    timPhieuNhapTheoNgayNhap();
                                    break;
                                default:
                                    System.out.println("⚠️ Lựa chọn không hợp lệ!");
                                    break;
                            }
                        } catch (Exception e) {
                            System.out.println("Lỗi xảy ra: " + e.getMessage());
                            scanner.nextLine();
                        }
                    }
                    break;
                case 3: 
                    suaPhieuNhap(); 
                    break;
                case 4: 
                    while (true) {
                        try {
                            System.out.println("\n");
                            System.out.println("Thống kê phiếu nhập");
                            System.out.println("1. Thống kê phiếu nhập theo khoảng thời gian");
                            System.out.println("2. Thống kê phiếu nhập theo nhà cung cấp");
                            System.out.println("3. Thống kê phiếu nhập theo nhân viên nhập");
                            System.out.println("4. Thống kê phiếu nhập theo sản phẩm nhập");
                            System.out.println("5. Thống kê phiếu nhập theo tháng / năm");
                            System.out.println("0. Thoát");
                            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

                            int opt = scanner.nextInt();
                            scanner.nextLine();

                            if (opt == 0) {
                                System.out.println("Thoát thống kê phiếu nhập thành công.");
                                break;
                            }

                            switch (opt) {
                                case 1: 
                                    thongKePhieuNhapTheoNgay();
                                    break;
                                case 2: 
                                    thongKePhieuNhapTheoNCC();
                                    break;
                                case 3:
                                    thongKePhieuNhapTheoNV();
                                    break;
                                case 4:
                                    thongKePhieuNhapTheoSanPham();
                                    break;
                                case 5:
                                    thongKePhieuNhapTheoThang();
                                    break;
                                default:
                                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại");
                                    break;
                            }
                        } catch (Exception e) {
                            System.out.println("Lỗi xảy ra: " + e.getMessage());
                            scanner.nextLine();
                        }
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
            Connection conn = null;
            try {
                conn = util.JDBCUtil.getConnection();

                String maPhieu = NhapHangDAO.generateMaPhieuNhap();
                System.out.println("Mã phiếu nhập: " + maPhieu);
                
                System.out.println("Nhập mã nhà cung cấp: ");
                String maNCC = scanner.nextLine().trim();

                NhaCungCapDTO ncc = NhaCungCapDAO.timnccTheoMa(maNCC);
                if (ncc == null) {
                    System.out.println("Nhà cung cấp không tồn tại, bạn có muốn thêm nhà cung cấp mới không? (y/n)");
                    String choice = scanner.nextLine().trim();
                    if (choice.equalsIgnoreCase("y")) {
                        // Tạo nhà cung cấp mới
                        System.out.println("Nhập mã nhà cung cấp mới:");
                        maNCC = scanner.nextLine().trim();
                        while (maNCC.isEmpty()) {
                            System.out.println("Mã nhà cung cấp không được để trống, vui lòng nhập lại.");
                            System.out.println("Nhập mã nhà cung cấp mới:");
                            maNCC = scanner.nextLine().trim();
                        }

                        System.out.println("Nhập tên nhà cung cấp: ");
                        String tenNCC = scanner.nextLine().trim();
                        while (tenNCC.isEmpty()) {
                            System.out.println("Tên nhà cung cấp không được để trống, vui lòng nhập lại.");
                            System.out.println("Nhập tên nhà cung cấp: ");
                            tenNCC = scanner.nextLine().trim();
                        }

                        System.out.println("Nhập địa chỉ nhà cung cấp: ");
                        String diaChi = scanner.nextLine().trim();
                        while (diaChi.isEmpty()) {
                            System.out.println("Địa chỉ nhà cung cấp không được để trống, vui lòng nhập lại.");
                            System.out.println("Nhập địa chỉ nhà cung cấp: ");
                            diaChi = scanner.nextLine().trim();
                        }

                        System.out.println("Nhập điện thoại nhà cung cấp: ");
                        String dienThoai = scanner.nextLine().trim();
                        while (dienThoai.isEmpty()) {
                            System.out.println("Điện thoại nhà cung cấp không được để trống, vui lòng nhập lại.");
                            System.out.println("Nhập điện thoại nhà cung cấp: ");
                            dienThoai = scanner.nextLine().trim();
                        }

                        System.out.println("Nhập email nhà cung cấp: ");
                        String email = scanner.nextLine().trim();
                        while (email.isEmpty()) {
                            System.out.println("Email nhà cung cấp không được để trống, vui lòng nhập lại.");
                            System.out.println("Nhập email nhà cung cấp: ");
                            email = scanner.nextLine().trim();
                        }

                        ncc = new NhaCungCapDTO(maNCC, tenNCC, diaChi, dienThoai, email, "active");
                        NhaCungCapDAO.themNCC(ncc);
                        System.out.println("Thêm nhà cung cấp thành công");
                    } else continue;
                }

                String maNV = Main.CURRENT_ACCOUNT.getMaNV();
                if (maNV == null || maNV.isEmpty()) {
                    System.out.println("Không xác định được nhân viên đăng nhập");
                    continue;
                }

                int tongTien = 0;
                int countSuccess = 0;

                NhapHangDTO pn = new NhapHangDTO(maPhieu, maNCC, maNV, 0, LocalDateTime.now());
                boolean headerCreated = NhapHangDAO.themPhieuNhap(pn);
                if (!headerCreated) {
                    System.out.println("Không thể tạo phiếu nhập. Vui lòng thử lại.");
                    continue;
                }

                System.out.println("\n📦 Nhập chi tiết hàng hóa (nhập mã hàng = '0' để kết thúc):");
                while (true) {
                    System.out.print("\nNhập mã hàng: ");
                    String maHang = scanner.nextLine().trim();
                    if (maHang.equals("0")) break;

                    System.out.print("Nhập số lượng: ");
                    String slStr = scanner.nextLine().trim();
                    int soLuong;
                    try {
                        soLuong = Integer.parseInt(slStr);
                        if (soLuong <= 0) {
                            System.out.println("Số lượng phải lớn hơn 0, vui lòng nhập lại.");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Số lượng không hợp lệ, vui lòng nhập lại.");
                        continue;
                    }

                    System.out.print("Nhập giá nhập: ");
                    String giaNhapStr = scanner.nextLine().trim();
                    int giaNhap;
                    try {
                        giaNhap = Integer.parseInt(giaNhapStr);
                        if (giaNhap <= 0) {
                            System.out.println("Giá nhập phải lớn hơn 0, vui lòng nhập lại.");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Giá nhập không hợp lệ, vui lòng nhập lại.");
                        continue;
                    }

                    int thanhTien = soLuong * giaNhap;

                    ChiTietPhieuNhapDTO chiTiet = new ChiTietPhieuNhapDTO(maPhieu, maHang, null, null, soLuong, giaNhap, thanhTien);
                    boolean added = ChiTietPhieuNhapDAO.themChiTietPhieuNhap(conn, chiTiet);

                    if (added) {
                        tongTien += thanhTien;
                        countSuccess++;
                    }
                }

                if (countSuccess == 0) {
                    System.out.println("⚠️  Không có hàng hóa nào được thêm. Hủy phiếu nhập vừa tạo.");
                    NhapHangDAO.xoaPhieuNhapTheoMa(maPhieu);
                    continue;
                }

                // Cập nhật tổng tiền cho header
                if (!NhapHangDAO.capNhatTongTien(maPhieu, tongTien)) {
                    System.out.println("Không thể cập nhật tổng tiền phiếu nhập.");
                    // Không xóa header vì đã có chi tiết và tồn kho đã cập nhật; báo để user xử lý thủ công.
                }

                System.out.println("\nTạo phiếu nhập thành công!");
                System.out.println("Mã phiếu: " + maPhieu);
                System.out.println("Số lượng sản phẩm nhập: " + countSuccess);
                System.out.println("Tổng tiền: " + FormatUtil.formatVND(tongTien));

                System.out.print("\nBạn có muốn tạo phiếu nhập khác không? (y/n): ");
                String cont = scanner.nextLine().trim();
                if (!"y".equalsIgnoreCase(cont)) break;

            } catch (Exception e) {
                System.out.println("❌ Lỗi: " + e.getMessage());
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        System.err.println("Lỗi đóng connection: " + e.getMessage());
                    }
                }
            }
        }
    }

    public void timPhieuNhapTheoMa() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã phiếu nhập cần tìm: ");
        String maPhieu = scanner.nextLine().trim();
        
        if (maPhieu.isEmpty()) {
            System.out.println("⚠️  Mã phiếu nhập không được để trống!");
            return;
        }
        inPhieuNhap(maPhieu);
    }

    public void timPhieuNhapTheoMaNCC() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã nhà cung cấp cần tìm: ");
        String maNCC = scanner.nextLine().trim();
        if (maNCC.isEmpty()) {
            System.out.println("⚠️  Mã nhà cung cấp không được để trống!");
            return;
        }
        NhaCungCapDTO ncc = NhaCungCapDAO.timnccTheoMa(maNCC);
        if (ncc == null) {
            System.out.println("❌ Không tìm thấy nhà cung cấp với mã: " + maNCC);
            return;
        }
        List<NhapHangDTO> pnList = NhapHangDAO.timPhieuNhapTheoMaNCC(maNCC);
        if (pnList == null || pnList.isEmpty()) {
            System.out.println("❌ Không tìm thấy phiếu nhập từ nhà cung cấp mã: " + maNCC);
            return;
        }
        System.out.println("\n═══════ DANH SÁCH PHIẾU NHẬP CỦA NHÀ CUNG CẤP ═══════");
        System.out.println("Tên nhà cung cấp: " + ncc.getTenNCC());
        System.out.println("Số lượng phiếu nhập: " + pnList.size());
        System.out.println("═════════════════════════════════════════════════════");
        for (NhapHangDTO pn : pnList) {
            System.out.printf("%-15s %-20s %-15s %-15s%n",
                pn.getMaPhieu(),
                pn.getNgayLapPhieu(),
                pn.getMaNV(),
                FormatUtil.formatVND(pn.getTongTien()));
        }
        while (true) {
            System.out.print("\nBạn có muốn xem chi tiết phiếu nhập không? (y/n): ");
            String choice = scanner.nextLine().trim();
            if (!choice.equalsIgnoreCase("y")) break;
            System.out.print("Nhập mã phiếu nhập cần xem chi tiết: ");
            String maPhieu = scanner.nextLine().trim();
            if (!maPhieu.isEmpty()) inPhieuNhap(maPhieu);
        }
    }

    public void timPhieuNhapTheoMaNV() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã nhân viên cần tìm: ");
        String maNV = scanner.nextLine().trim();
        if (maNV.isEmpty()) {
            System.out.println("⚠️  Mã nhân viên không được để trống!");
            return;
        }
        NhanVienDTO nv = NhanVienDAO.timNhanVienTheoMa(maNV);
        if (nv == null) {
            System.out.println("❌ Không tìm thấy nhân viên với mã: " + maNV);
            return;
        }
        List<NhapHangDTO> pnList = NhapHangDAO.timPhieuNhapTheoMaNV(maNV);
        if (pnList == null || pnList.isEmpty()) {
            System.out.println("❌ Không tìm thấy phiếu nhập từ nhân viên mã: " + maNV);
            return;
        }
        System.out.println("\n═══════ DANH SÁCH PHIẾU NHẬP CỦA NHÂN VIÊN ═══════");
        // System.out.println("Tên nhân viên: " + nv.getTenNV());
        System.out.println("Số lượng phiếu nhập: " + pnList.size());
        System.out.println("═════════════════════════════════════════════════════");
        for (NhapHangDTO pn : pnList) {
            System.out.printf("%-15s %-20s %-15s %-15s%n",
                pn.getMaPhieu(),
                pn.getNgayLapPhieu(),
                pn.getMaNV(),
                FormatUtil.formatVND(pn.getTongTien()));
        }
        while (true) {
            System.out.print("\nBạn có muốn xem chi tiết phiếu nhập không? (y/n): ");
            String choice = scanner.nextLine().trim();
            if (!choice.equalsIgnoreCase("y")) break;
            System.out.print("Nhập mã phiếu nhập cần xem chi tiết: ");
            String maPhieu = scanner.nextLine().trim();
            if (!maPhieu.isEmpty()) inPhieuNhap(maPhieu);
        }
    }

    public void timPhieuNhapTheoNgayNhap() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        while (true) {
            System.out.print("Nhập ngày bắt đầu (ddMMyyyy): ");
            String from = scanner.nextLine().trim();
            System.out.print("Nhập ngày kết thúc (ddMMyyyy): ");
            String to = scanner.nextLine().trim();
            LocalDate fromDate, toDate;
            try {
                fromDate = LocalDate.parse(from, formatter);
                toDate = LocalDate.parse(to, formatter);
                if (fromDate.isAfter(toDate)) {
                    System.out.println("⚠️  Ngày bắt đầu phải trước ngày kết thúc!");
                    continue;
                }
            } catch (DateTimeParseException e) {
                System.out.println("❌ Định dạng ngày không hợp lệ, vui lòng nhập lại.");
                continue;
            }
            List<NhapHangDTO> pnList = NhapHangDAO.timPhieuNhapTheoNgay(fromDate, toDate);
            System.out.println("\nDanh sách phiếu nhập từ " + fromDate + " đến " + toDate);
            if (pnList == null || pnList.isEmpty()) {
                System.out.println("❌ Không tìm thấy phiếu nhập nào trong khoảng thời gian này.");
            } else {
                for (NhapHangDTO pn : pnList) {
                    System.out.printf("%-15s %-20s %-15s %-15s%n",
                        pn.getMaPhieu(),
                        pn.getNgayLapPhieu(),
                        pn.getMaNV(),
                        FormatUtil.formatVND(pn.getTongTien()));
                }
                System.out.println("Tìm thấy " + pnList.size() + " phiếu nhập từ " + fromDate + " đến " + toDate);
            }
            System.out.print("\nBạn có muốn tìm tiếp không? (y/n): ");
            String choice = scanner.nextLine().trim();
            if (!choice.equalsIgnoreCase("y")) {
                System.out.println("Thoát tìm kiếm phiếu nhập theo ngày thành công.");
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
            System.out.println("║                    PHIẾU NHẬP HÀNG                           ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            
            System.out.println("Mã phiếu: " + pn.getMaPhieu());
            System.out.println("Ngày nhập: " + pn.getNgayLapPhieu());
            System.out.println("Mã nhân viên: " + pn.getMaNV());
            
            NhaCungCapDTO ncc = NhaCungCapDAO.timnccTheoMa(pn.getMaNCC());
            if (ncc != null) {
                System.out.println("\n━━━ Thông tin nhà cung cấp ━━━");
                System.out.println("Tên NCC: " + ncc.getTenNCC());
                System.out.println("Địa chỉ: " + ncc.getDiaChi());
                System.out.println("Điện thoại: " + ncc.getDienThoai());
            }

            List <ChiTietPhieuNhapDTO> chiTietList = ChiTietPhieuNhapDAO.timChiTietPhieuNhap(maPhieu);
            if (chiTietList != null && !chiTietList.isEmpty()) {
                System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                System.out.printf("%-6s | %-20s | %-10s | %-8s | %-12s | %-12s\n",
                        "STT", "Tên sản phẩm", "Đơn vị", "Số lượng", "Giá nhập", "Thành tiền");
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

                int stt = 1;
                for (ChiTietPhieuNhapDTO ct : chiTietList) {
                    System.out.printf("%-10s | %-20s | %-10s | %-8d | %-12s | %-12s\n",
                            stt++,
                            ct.getTenSP(),
                            ct.getDonViTinh(),
                            ct.getSoLuong(),
                            FormatUtil.formatVND(ct.getGiaNhap()),
                            FormatUtil.formatVND(ct.getThanhTien())
                    );
                }
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                System.out.println("Tổng tiền: " + FormatUtil.formatVND(pn.getTongTien()));
            } 
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        } catch (Exception e) {
            System.out.println("Lỗi khi in phiếu nhập: " + e.getMessage());
        }
    }

    public void suaPhieuNhap() { 
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Nhập mã phiếu nhập cần sửa: ");
            String maPhieu = scanner.nextLine().trim();
            if (maPhieu.equals("0")) {
                System.out.println("Thoát sửa phiếu nhập.");
                break;
            }
            NhapHangDTO pn = NhapHangDAO.timPhieuNhapTheoMa(maPhieu);

            if (pn == null) {
                System.out.println("Mã phiếu nhập không tồn tại, vui lòng nhập lại.");
                continue;
            }

            System.out.println("════════ Thông tin phiếu nhập ════════");
            System.out.println("Mã phiếu: " + pn.getMaPhieu());
            System.out.println("Mã nhân viên: " + pn.getMaNV());
            System.out.println("Mã nhà cung cấp: " + pn.getMaNCC());
            System.out.println("Ngày nhập: " + pn.getNgayLapPhieu());
            System.out.println("Tổng tiền: " + FormatUtil.formatVND(pn.getTongTien()));
            System.out.println("═════════════════════════════════════════════════════");

            System.out.println("Nhập thông tin mới cho phiếu nhập: ");
            if (!pn.sua()) {
                System.out.println("Đã hủy sửa phiếu nhập, quay lại menu...");
                break;
            }

            System.out.print("Xác nhận sửa phiếu nhập? (y/n): ");
            String confirm = scanner.nextLine().trim();
            if (!confirm.equalsIgnoreCase("y")) {
                System.out.println("Đã hủy sửa phiếu nhập.");
                break;
            }
            NhapHangDAO.suaPhieuNhap(pn, maPhieu);
            System.out.println("Sửa phiếu nhập thành công.");
            break;
        }
    }

    public void thongKePhieuNhapTheoNgay() { 
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        while (true) {
            try {
                System.out.println("Nhập ngày bắt đầu: ");
                String from = scanner.nextLine().trim();

                System.out.println("Nhập ngày kết thúc: ");
                String to = scanner.nextLine().trim();

                LocalDate fromDate = LocalDate.parse(from, formatter);
                LocalDate toDate = LocalDate.parse(to, formatter);

                if (fromDate.isAfter(toDate)) {
                    System.out.println("Ngày bắt đầu phải trước ngày kết thúc, vui lòng nhập lại.");
                    continue;
                }

                Map<String, Object> tongHop = NhapHangDAO.thongKePhieuNhapTheoNgay(fromDate, toDate);
                List<Map<String, Object>> chiTiet = NhapHangDAO.thongKeChiTietTheoNgay(fromDate, toDate);

                System.out.println("=== THỐNG KÊ PHIẾU NHẬP THEO THỜI GIAN ===");
                System.out.println("Từ ngày: " + fromDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                System.out.println("Đến ngày: " + toDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                System.out.println("-----------------------------------------");

                System.out.println("Tổng số phiếu nhập: "       + tongHop.get("tongPhieuNhap") + " phiếu");
                System.out.println("Tổng giá trị nhập: "        + FormatUtil.formatVND((long)tongHop.get("tongGiaTri")));
                System.out.println("Tổng số sản phẩm nhập: "    + tongHop.get("tongSanPham") + " sản phẩm");
                System.out.println("Số nhà cung cấp: "          + tongHop.get("soNCC") + " nhà cung cấp");
                System.out.println("Giá trị trung bình/phiếu: " + FormatUtil.formatVND((long)tongHop.get("giaTriTB")));
                System.out.println("-----------------------------------------");

                System.out.println("Chi tiết theo ngày:");
                System.out.println("+------------+------------+-----------------+");
                System.out.println("| Ngày       | Số phiếu   | Tổng giá trị    |");
                System.out.println("+------------+------------+-----------------+");

                for (Map<String, Object> row : chiTiet) {
                    System.out.printf("| %-10s | %-10d | %-10s |\n",
                        row.get("NgayLapPhieu"),
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
                System.out.println("Định dạng ngày không hợp lệ, vui lòng nhập lại.");
            }
        }
    }

    public void thongKePhieuNhapTheoNCC() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        while (true) {
            try {
                System.out.println("Nhập ngày bắt đầu: ");
                String from = scanner.nextLine().trim();

                System.out.println("Nhập ngày kết thúc: ");
                String to = scanner.nextLine().trim();

                LocalDate fromDate = LocalDate.parse(from, formatter);
                LocalDate toDate = LocalDate.parse(to, formatter);

                if (fromDate.isAfter(toDate)) {
                    System.out.println("⚠️  Ngày bắt đầu phải trước ngày kết thúc, vui lòng nhập lại.");
                    continue;
                }

                List<Map<String, Object>> result = NhapHangDAO.thongKePhieuNhapTheoNCC(fromDate, toDate);

                System.out.println("=== THỐNG KÊ PHIẾU NHẬP THEO NHÀ CUNG CẤP ===");
                System.out.println("Từ ngày: " + fromDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                System.out.println("Đến ngày: " + toDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                System.out.println("---------------------------------------------------------");

                if (result == null || result.isEmpty()) {
                    System.out.println("❌ Không có dữ liệu phiếu nhập trong khoảng thời gian này.");
                    System.out.println("---------------------------------------------------------");
                    System.out.print("\n✅ Bạn có muốn thống kê tiếp không? (y/n): ");
                    String choice = scanner.nextLine().trim().toLowerCase();
                    if (!choice.equals("y")) {
                        break;
                    }
                    continue;
                }

                System.out.printf("| %-7s | %-22s | %-8s | %-6s | %-13s |%n",
                "Mã NCC", "Tên Nhà Cung Cấp", "Số Phiếu", "Số SP", "Tổng Giá Trị");
                System.out.println("+---------+------------------------+-----------+--------+---------------+");
                
                int tongNCC = 0, tongPhieu = 0, tongSanPham = 0;
                long tongGiaTri = 0;

                for (Map<String, Object> row : result) {
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

                System.out.println("+---------+------------------------+-----------+--------+---------------+");
                System.out.println("Tổng số nhà cung cấp: " + tongNCC);
                System.out.println("Tổng số phiếu nhập: " + tongPhieu);
                System.out.println("Tổng số sản phẩm: " + tongSanPham);
                System.out.println("Tổng giá trị nhập: " + FormatUtil.formatVND(tongGiaTri));
                System.out.println("---------------------------------------------------------");

                // Hỏi người dùng có muốn thống kê tiếp không
                System.out.print("\n✅ Bạn có muốn thống kê tiếp không? (y/n): ");
                String choice = scanner.nextLine().trim().toLowerCase();
                if (!choice.equals("y")) {
                    break;
                }
            } catch (DateTimeParseException e) {
                System.out.println("⚠️ Định dạng ngày không hợp lệ, vui lòng nhập lại (ddMMyyyy).");
            }
        }
    }

    public void thongKePhieuNhapTheoNV() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        while (true) {
            try {
                System.out.println("Nhập ngày bắt đầu: ");
                String from = scanner.nextLine().trim();

                System.out.println("Nhập ngày kết thúc: ");
                String to = scanner.nextLine().trim();

                LocalDate fromDate = LocalDate.parse(from, formatter);
                LocalDate toDate = LocalDate.parse(to, formatter);

                if (fromDate.isAfter(toDate)) {
                    System.out.println("Ngày bắt đầu phải trước ngày kết thúc, vui lòng nhập lại.");
                    continue;
                }

                List<Map<String, Object>> result = NhapHangDAO.thongKePhieuNhapTheoNV(fromDate, toDate);

                System.out.println("=== THỐNG KÊ PHIẾU NHẬP THEO NHÂN VIÊN ===");
                System.out.println("Từ ngày: " + fromDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                System.out.println("Đến ngày: " + toDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                System.out.println("---------------------------------------------------------");

                if (result == null || result.isEmpty()) {
                    System.out.println("❌ Không có dữ liệu phiếu nhập trong khoảng thời gian này.");
                    System.out.println("---------------------------------------------------------");
                    System.out.print("\n✅ Bạn có muốn thống kê tiếp không? (y/n): ");
                    String choice = scanner.nextLine().trim().toLowerCase();
                    if (!choice.equals("y")) break;
                    continue;
                }

                System.out.printf("| %-5s | %-10s | %-20s | %-10s | %-10s | %-10s |%n", 
                "STT", "Mã NV", "Họ Tên", "Số Phiếu", "Số SP", "Tổng Giá Trị");
                System.out.println("+---------+------------------------+-----------+--------+-----------+---------------+");

                int stt = 1;
                for (Map<String, Object> row : result) {
                    System.out.printf("| %-5s | %-10s | %-20s | %-10d | %-10d | %-10s |%n",
                        stt++,
                        row.get("MaNV"),
                        row.get("Ho Ten"),
                        row.get("SoPhieu"),
                        row.get("TongSanPham"),
                        FormatUtil.formatVND((long)row.get("TongGiaTri"))
                    );
                }
                System.out.println("+---------+------------------------+-----------+--------+-----------+---------------+");

                System.out.print("\n✅ Bạn có muốn thống kê tiếp không? (y/n): ");
                String choice = scanner.nextLine().trim().toLowerCase();
                if (!choice.equals("y")) break;
            } catch (DateTimeParseException e) {
                System.out.println("⚠️ Định dạng ngày không hợp lệ, vui lòng nhập lại (ddMMyyyy).");
            }
        }
    }

    public void thongKePhieuNhapTheoSanPham() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        while (true) {
            try {
                System.out.println("Nhập ngày bắt đầu: ");
                String from = scanner.nextLine().trim();

                System.out.println("Nhập ngày kết thúc: ");
                String to = scanner.nextLine().trim();

                LocalDate fromDate = LocalDate.parse(from, formatter);
                LocalDate toDate = LocalDate.parse(to, formatter);

                if (fromDate.isAfter(toDate)) {
                    System.out.println("⚠️  Ngày bắt đầu phải trước ngày kết thúc, vui lòng nhập lại.");
                    continue;
                }

                List<Map<String, Object>> result = NhapHangDAO.thongKePhieuNhapTheoSanPham(fromDate, toDate);
                
                System.out.println("=== THỐNG KÊ PHIẾU NHẬP THEO SẢN PHẨM ===");
                System.out.println("Từ ngày: " + fromDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                System.out.println("Đến ngày: " + toDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                System.out.println("---------------------------------------------------------");

                if (result == null || result.isEmpty()) {
                    System.out.println("❌ Không có dữ liệu phiếu nhập trong khoảng thời gian này.");
                    System.out.println("---------------------------------------------------------");
                    System.out.print("\n✅ Bạn có muốn thống kê tiếp không? (y/n): ");
                    String choice = scanner.nextLine().trim().toLowerCase();
                    if (!choice.equals("y")) {
                        break;
                    }
                    continue;
                }

                System.out.printf("| %-5s | %-15s | %-20s | %-10s | %-10s | %-10s |%n", 
                "STT", "Mã SP", "Tên SP", "Số Phiếu", "Số SP", "Tổng Giá Trị");
                System.out.println("+---------+----------------------+-----------+--------+-----------+---------------+");
                
                int stt = 1;
                for (Map<String, Object> row : result) {
                    System.out.printf("| %-5s | %-15s | %-20s | %-10d | %-10d | %-10s |%n",
                        stt++,
                        row.get("MaSP"),
                        row.get("TenSP"),
                        row.get("SoPhieu"),
                        row.get("TongSanPham"),
                        FormatUtil.formatVND((long)row.get("TongGiaTri"))
                    );
                }
                System.out.println("+---------+----------------------+-----------+--------+-----------+---------------+");

                System.out.print("\n✅ Bạn có muốn thống kê tiếp không? (y/n): ");
                String choice = scanner.nextLine().trim().toLowerCase();
                if (!choice.equals("y")) {
                    break;
                }
            } catch (DateTimeParseException e) {
                System.out.println("⚠️ Định dạng ngày không hợp lệ, vui lòng nhập lại (ddMMyyyy).");
            }
        }
    }

    public void thongKePhieuNhapTheoThang() {
        Scanner scanner = new Scanner(System.in);
        int year = 0;

        while (true) {
            try {
                System.out.println("Nhập năm cần thống kê (yyyy): ");
                year = Integer.parseInt(scanner.nextLine().trim());
                if (year < 2000 || year > LocalDate.now().getYear()) {
                    System.out.println("⚠️  Năm không hợp lệ, vui lòng nhập lại.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Năm không hợp lệ, vui lòng nhập lại.");
            }
        }

        List<Map<String, Object>> result = NhapHangDAO.thongKePhieuNhapTheoNam(year);

        if (result == null || result.isEmpty()) {
            System.out.println("❌ Không có dữ liệu phiếu nhập trong năm " + year + ".");
            return;
        }

        System.out.println("\n=== THỐNG KÊ PHIẾU NHẬP THEO THÁNG NĂM " + year + " ===");
        System.out.println("+-----------+------------+------------------+------------------+");
        System.out.printf("| %-9s | %-10s | %-16s | %-16s |%n",
                "Tháng", "Số Phiếu", "Tổng Số Lượng", "Tổng Giá Trị");
        System.out.println("+-----------+------------+------------------+------------------+");

        int tongPhieu = 0;
        long tongSoLuong = 0;
        long tongGiaTri = 0;

        for (Map<String, Object> row : result) {
            int thang = (int) row.get("Thang");
            int soPhieu = (int) row.get("SoPhieu");
            long soLuong = (long) row.get("TongSanPham");  
            long giaTri = (long) row.get("TongGiaTri");

            tongPhieu += soPhieu;
            tongSoLuong += soLuong;
            tongGiaTri += giaTri;

            System.out.printf("| %-9d | %-10d | %-16d | %-16s |%n",
                    thang, soPhieu, soLuong, FormatUtil.formatVND(giaTri));  
        }

        System.out.println("+-----------+------------+------------------+------------------+");
        System.out.printf("| %-9s | %-10d | %-16d | %-16s |%n",
                "TỔNG CỘNG", tongPhieu, tongSoLuong, FormatUtil.formatVND(tongGiaTri));
        System.out.println("+-----------+------------+------------------+------------------+");
    }

    public void xuatPhieuNhapTheoMaPhieuNhap() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập mã phiếu nhập cần xuất: ");
        String maPhieu = scanner.nextLine().trim();

        try {
            NhapHangDTO pn = NhapHangDAO.timPhieuNhapTheoMa(maPhieu);
            if (pn == null) {
                System.out.println("Không tìm thấy phiếu nhập với mã: " + maPhieu);
                return;
            }

            List<ChiTietPhieuNhapDTO> chiTiet = ChiTietPhieuNhapDAO.timChiTietPhieuNhap(maPhieu);

            String fileName = "PhieuNhap_" + maPhieu + ".txt";

//          (xuatFileUtil)

            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                writer.println("══════════════════════════════════════════════════════════════");
                writer.println("                    PHIẾU NHẬP HÀNG                           ");
                writer.println("══════════════════════════════════════════════════════════════");
                writer.println("Mã phiếu: " + pn.getMaPhieu());
                writer.println("Ngày nhập: " + pn.getNgayLapPhieu());
                writer.println("Mã nhân viên: " + pn.getMaNV());

                NhaCungCapDTO ncc = NhaCungCapDAO.timnccTheoMa(pn.getMaNCC());
                if (ncc != null) {
                    writer.println("\n--- Thông tin nhà cung cấp ---");
                    writer.println("Tên NCC: " + ncc.getTenNCC());
                    writer.println("Địa chỉ: " + ncc.getDiaChi());
                    writer.println("Điện thoại: " + ncc.getDienThoai());
                }
                writer.println("\n──────────────────────────────────────────────────────────────");
                writer.printf("%-6s | %-20s | %-10s | %-8s | %-12s | %-12s%n",
                        "STT", "Tên sản phẩm", "Đơn vị", "Số lượng", "Giá nhập", "Thành tiền");
                writer.println("──────────────────────────────────────────────────────────────");

                int stt = 1;
                for (ChiTietPhieuNhapDTO ct : chiTiet) {
                    writer.printf("%-10s | %-20s | %-10s | %-8d | %-12s | %-12s%n",
                            stt++,
                            ct.getTenSP(),
                            ct.getDonViTinh(),
                            ct.getSoLuong(),
                            FormatUtil.formatVND(ct.getGiaNhap()),
                            FormatUtil.formatVND(ct.getThanhTien())
                    );
                }
                writer.println("──────────────────────────────────────────────────────────────");
                writer.println("Tổng tiền: " + FormatUtil.formatVND(pn.getTongTien()));
                writer.println("══════════════════════════════════════════════════════════════");

                System.out.println("Xuất phiếu nhập thành công. Tệp được lưu tại: " + fileName);

            } catch (IOException e) {
                System.out.println("Lỗi khi xuất phiếu nhập: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi xuất phiếu nhập: " + e.getMessage());
        }
    }

    public void xuatBaoCaoNhapHangTheoNgay() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            try {
                System.out.println("Nhập ngày bắt đầu: ");
                String from = scanner.nextLine().trim();

                System.out.println("Nhập ngày kết thúc: ");
                String to = scanner.nextLine().trim();

                if (!from.matches("\\d{8}") || !to.matches("\\d{8}")) {
                    System.out.println("Định dạng ngày không hợp lệ! Vui lòng nhập theo dạng.");
                    continue;
                }

                LocalDate fromDate = LocalDate.parse(from, formatter);
                LocalDate toDate = LocalDate.parse(to, formatter);

                if (fromDate.isAfter(toDate)) {
                    System.out.println("Ngày bắt đầu phải trước ngày kết thúc, vui lòng nhập lại.");
                    continue;
                }

                List<NhapHangDTO> danhSach = NhapHangDAO.timPhieuNhapTheoNgay(fromDate, toDate);

                if (danhSach.isEmpty()) {
                    System.out.println("Không có phiếu nhập nào trong khoảng thời gian này.");
                    System.out.print("\nBạn có muốn thử lại không? (y/n): ");
                    String retry = scanner.nextLine().trim();
                    if (!retry.equalsIgnoreCase("y")) break;
                    continue;
                }

                String fileName = "BaoCaoNhapHang_" + fromDate.format(formatter) + "_den_" + toDate.format(formatter) + ".txt";

                long tongCong = 0;
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                    writer.println("══════════════════════════════════════════════════════════════");
                    writer.println("                   BÁO CÁO NHẬP HÀNG                          ");
                    writer.println("Ngày lập báo cáo: " + LocalDate.now().format(displayFormatter));
                    writer.println(" Từ ngày: " + fromDate.format(displayFormatter) + " Đến ngày: " + toDate.format(displayFormatter));
                    writer.println("══════════════════════════════════════════════════════════════");
                    writer.printf("%-10s | %-15s | %-10s | %-10s | %-15s%n",
                            "Mã Phiếu", "Ngày Nhập", "Mã NV", "Mã NCC", "Tổng Tiền");
                    writer.println("──────────────────────────────────────────────────────────────");

                    for (NhapHangDTO pn : danhSach) {
                        writer.printf("%-10s | %-15s | %-10s | %-10s | %-15s%n",
                                pn.getMaPhieu(),
                                pn.getNgayLapPhieu().toLocalDate().format(displayFormatter),
                                pn.getMaNV(),
                                pn.getMaNCC(),
                                FormatUtil.formatVND(pn.getTongTien()));
                        tongCong += pn.getTongTien();
                    }
                    writer.println("──────────────────────────────────────────────────────────────");
                    writer.println("TỔNG CỘNG: " + FormatUtil.formatVND(tongCong));
                    writer.println("══════════════════════════════════════════════════════════════");
                } catch (IOException e) {
                    System.out.println("Lỗi khi xuất báo cáo nhập hàng: " + e.getMessage());
                    return;
                }

                System.out.println("Xuất báo cáo nhập hàng thành công. Tệp được lưu tại: " + fileName);

            } catch (DateTimeParseException e) {
                System.out.println("Định dạng ngày không hợp lệ, vui lòng nhập lại.");
            } catch (Exception e) {
                System.out.println("Lỗi khi xuất báo cáo nhập hàng: " + e.getMessage());
            }
        }
    }
}

