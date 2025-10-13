package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import dao.NhapHangDAO;
import dao.NhaCungCapDAO;
import dao.ChiTietPhieuNhapDAO;
import dto.ChiTietPhieuNhapDTO;
import dto.NhaCungCapDTO;
import dto.NhapHangDTO;
import main.Main;
import util.FormatUtil;
import java.util.Map;

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
            System.out.println("▒ [2] ➜ Xem chi tiết phiếu nhập                                                ▒");
            System.out.println("▒ [6] ➜ Quản lý nhà cung cấp                                                   ▒");
            System.out.println("▒ [7] ➜ Thống kê nhập hàng                                                     ▒");
            System.out.println("▒ [8] ➜ Xuất báo cáo nhập hàng                                                 ▒");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
            System.out.println("░ [0] ✗ Quay lại menu chính                                                    ░");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            int choice = -1;

            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 0 && choice <= 4) break;
                    System.out.print("⚠️  Vui lòng nhập số trong khoảng 0–4: ");
                } else {
                    System.out.print("⚠️  Nhập không hợp lệ. Vui lòng nhập lại: ");
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
                // case 5: timKiem(); break;
                case 6: 
                    QuanLyNhaCungCap qlncc = new QuanLyNhaCungCap();
                    qlncc.menuQuanLyNhaCungCap();
                    break;
                // case 7: thongKePhieuNhap(); break;
                case 8: xuatBaoCao(); break;
                default:
                    System.out.println("⚠️ Lựa chọn không hợp lệ!");
                    break;
            }
        }
    }

    public void taoPhieuNhap() { 
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            try {
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
                    } else {
                        continue;
                    }
                }

                String maNV = Main.CURRENT_ACCOUNT.getMaNV();
                if (maNV == null || maNV.isEmpty()) {
                    System.out.println("Không xác định được nhân viên đăng nhập");
                    continue;
                }

                // Nhập danh sách sản phẩm
                int tongTien = 0;

                while (true) {
                    System.out.println("Nhập mã sản phẩm ");
                    String maSP = scanner.nextLine().trim();
                    if (maSP.equals("0")) break;

                    System.out.println("Nhập số lượng:");
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

                    System.out.println("Nhập giá nhập: ");
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
                    // Tạo chi tiết phiếu nhập
                    ChiTietPhieuNhapDTO chiTiet = new ChiTietPhieuNhapDTO(maPhieu, maSP, null, null, soLuong, giaNhap, thanhTien);
                    ChiTietPhieuNhapDAO.themChiTietPhieuNhap(chiTiet);
                    tongTien += thanhTien;
                }

                if (tongTien == 0) {
                    System.out.println("Không có sản phẩm nào. Hủy tạo phiếu nhập");
                    continue;
                }

                // Tạo phiếu nhập với tổng tiền 
                NhapHangDTO pn = new NhapHangDTO(maPhieu, maNCC, maNV, tongTien, LocalDateTime.now());
                NhapHangDAO.themPhieuNhap(pn);
                System.out.println("Tạo phiếu nhập thành công!");
                System.out.println("Tổng tiền: " + FormatUtil.formatVND(tongTien));

                System.out.println("Bạn có muốn tạo phiếu nhập khác không? (y/n)");
                String cont = scanner.nextLine().trim();
                if (!"y".equalsIgnoreCase(cont)) break;

            } catch (Exception e) {
                System.out.println("Lỗi: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    public void timPhieuNhapTheoMa() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập mã phiếu nhập cần tìm: ");
        try {
            String maPhieu = scanner.nextLine().trim();
            NhapHangDTO pn = NhapHangDAO.timPhieuNhapTheoMa(maPhieu);

            if (pn != null) {
                System.out.println("Thông tin phiếu nhập tìm thấy với mã: " + maPhieu);
                inPhieuNhap(maPhieu);
            } else {
                System.out.println("Không tìm thấy phiếu nhập với mã: " + maPhieu);
            }

        } catch (InputMismatchException e) {
            System.out.println("Lỗi: Vui lòng nhập mã phiếu nhập hợp lệ");
            scanner.nextLine();
        }
    }

    public void timPhieuNhapTheoMaNCC() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập mã nhà cung cấp cần tìm: ");
        try {
            String maNCC = scanner.nextLine().trim();
            NhaCungCapDTO ncc = NhaCungCapDAO.timnccTheoMa(maNCC);
            if (ncc == null) {
                System.out.println("Không tìm thấy nhà cung cấp với mã: " + maNCC);
                return;
            }

            List<NhapHangDTO> pnList = NhapHangDAO.timPhieuNhapTheoMaNCC(maNCC);

            if (pnList != null && !pnList.isEmpty()) {
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
                    System.out.println("\nBạn có muốn xem chi tiết phiếu nhập không? (y/n)");
                    String choice = scanner.nextLine().trim();
                    if (!choice.equalsIgnoreCase("y")) {
                        break;
                    }
                    System.out.println("Nhập mã phiếu nhập cần xem chi tiết: ");
                    String maPhieu = scanner.nextLine().trim();
                    inPhieuNhap(maPhieu);
                }
            } else {
                System.out.println("Không tìm thấy phiếu nhập từ nhà cung cấp mã: " + maNCC);
            }
        } catch (InputMismatchException e) {
            System.out.println("Lỗi: Vui lòng nhập mã nhà cung cấp hợp lệ");
            scanner.nextLine();
        }
    }

    public void timPhieuNhapTheoMaNV() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập mã nhân viên cần tìm: ");
        try {
            String maNV = scanner.nextLine().trim();
            // NhanVenDTO nv = NhanVienDAO.timNVTheoMa(maNV);
            // if (nv == null) {
            //     System.out.println("Không tìm thấy nhân viên với mã: " + maNV);
            //     return;
            // }

            List<NhapHangDTO> pnList = NhapHangDAO.timPhieuNhapTheoMaNV(maNV);

            if (pnList != null && !pnList.isEmpty()) {
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
                    System.out.println("\nBạn có muốn xem chi tiết phiếu nhập không? (y/n)");
                    String choice = scanner.nextLine().trim();
                    if (!choice.equalsIgnoreCase("y")) {
                        break;
                    }
                    System.out.println("Nhập mã phiếu nhập cần xem chi tiết: ");
                    String maPhieu = scanner.nextLine().trim();
                    inPhieuNhap(maPhieu);
                }
            } else {
                System.out.println("Không tìm thấy phiếu nhập từ nhân viên mã: " + maNV);
            }
        } catch (InputMismatchException e) {
            System.out.println("Lỗi: Vui lòng nhập mã nhân viên hợp lệ");
            scanner.nextLine();
        }
    }

    public void timPhieuNhapTheoNgayNhap() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        while (true) {
            String from, to;
            LocalDate fromDate = null, toDate = null;
            
            while (true) {
                try {
                    System.out.println("Nhập ngày bắt đầu: ");
                    from = scanner.nextLine().trim();

                    System.out.println("Nhập ngày kết thúc: ");
                    to = scanner.nextLine().trim();

                    fromDate = LocalDate.parse(from, formatter);
                    toDate = LocalDate.parse(to, formatter);
                    if (fromDate.isAfter(toDate)) {
                        System.out.println("Ngày bắt đầu phải trước ngày kết thúc, vui lòng nhập lại.");
                        continue;
                    }
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Định dạng ngày không hợp lệ, vui lòng nhập lại.");
                    scanner.nextLine();
                }
            }

            List<NhapHangDTO> pnList = NhapHangDAO.timPhieuNhapTheoNgay(fromDate, toDate);

            System.out.println("Danh sách phiếu nhập từ " + fromDate + " đến " + toDate);

            if (pnList.isEmpty()) {
                System.out.println("Không tìm thấy phiếu nhập nào trong khoảng thời gian này.");
            } else {
                for (NhapHangDTO pn : pnList) {
                    System.out.printf("%-15s %-20s %-15s %-15s%n",
                        pn.getMaPhieu(),
                        pn.getNgayLapPhieu(),
                        pn.getMaNV(),
                        FormatUtil.formatVND(pn.getTongTien()));
                }

                System.out.println("Tìm thấy " + pnList.size() + " phiếu nhập từ " + fromDate + " đến " + toDate);

                System.out.println("\n Bạn có muốn tìm tiếp không? (y/n)");
                String choice = scanner.nextLine().trim();
                if (!choice.equalsIgnoreCase("y")) {
                    System.out.println("Thoát tìm kiếm phiếu nhập theo ngày thành công.");
                    break;
                }
            }
        }
    }

    public void inPhieuNhap(String maPhieu) {
        try {
            NhapHangDTO pn = NhapHangDAO.timPhieuNhapTheoMa(maPhieu);
            if (pn == null) {
                System.out.println("Không tìm thấy phiếu nhập với mã: " + maPhieu);
                return;
            }

            NhaCungCapDTO ncc = NhaCungCapDAO.timnccTheoMa(pn.getMaNCC());
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                    PHIẾU NHẬP HÀNG                           ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");

            System.out.println("Mã phiếu: " + pn.getMaPhieu());
            System.out.println("Ngày nhập: " + pn.getNgayLapPhieu());
            System.out.println("Mã nhân viên: " + pn.getMaNV());

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

    private void suaPhieuNhap() { 
        Scanner scanner = new Scanner(System.in);
        boolean conti = true;
        
        while (conti) {
            try {
                System.out.println("Nhập mã phiếu nhập cần sửa: ");
                String maPhieu = scanner.nextLine().trim();
                if (maPhieu.equals("0")) {
                    System.out.println("Thoát sửa phiếu nhập.");
                    break;
                }

                if (NhapHangDAO.timPhieuNhapTheoMa(maPhieu) == null) {
                    System.out.println("Mã phiếu nhập không tồn tại, vui lòng nhập lại.");
                    continue;
                }

                NhapHangDTO pn = NhapHangDAO.timPhieuNhapTheoMa(maPhieu);
                System.out.println("Thông tin phiếu nhập trước khi sửa: ");
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

                NhapHangDAO.suaPhieuNhap(pn, maPhieu);
                System.out.println("Sửa phiếu nhập thành công.");
                break;
            } catch (Exception e) {
                System.out.println("Lỗi nhập liệu: " + e.getMessage());
                scanner.nextLine();
            }
        }

        System.out.println("Bạn có muốn sửa phiếu nhập khác không? (y/n)");
        String choice = scanner.nextLine().trim();
        if (!choice.equalsIgnoreCase("y")) {
            System.out.println("Thoát sửa phiếu nhập.");
            conti = false;
        }        
    }

    private void thongKePhieuNhapTheoNgay() { 
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
            } catch (DateTimeParseException e) {
                System.out.println("Định dạng ngày không hợp lệ, vui lòng nhập lại.");
                scanner.nextLine();
            }
        }
    }


    private void xemChiTiet() { }
    private void xoaPhieuNhap() { }
    private void xuatBaoCao() { }
}