package view;

import java.time.LocalDateTime;
import java.util.Scanner;
import dao.NhapHangDAO;
import dao.NhaCungCapDAO;
import dao.ChiTietPhieuNhapDAO;
import dto.ChiTietPhieuNhapDTO;
import dto.NhaCungCapDTO;
import dto.NhapHangDTO;
import main.Main;
import util.FormatUtil;

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
            System.out.println("▒ [2] ➜ Xem chi tiết phiếu nhập                                                ▒");
            System.out.println("▒ [3] ➜ Chỉnh sửa phiếu nhập                                                   ▒");
            System.out.println("▒ [4] ➜ Xóa phiếu nhập                                                         ▒");
            System.out.println("▒ [5] ➜ Tìm kiếm phiếu nhập                                                    ▒");
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
                case 1: taoPhieuNhap(); break;
                case 2: xemChiTiet(); break;
                case 3: suaPhieuNhap(); break;
                case 4: xoaPhieuNhap(); break;
                case 5: timKiem(); break;
                case 6: 
                    QuanLyNhaCungCap qlncc = new QuanLyNhaCungCap();
                    qlncc.menuQuanLyNhaCungCap();
                    break;
                case 7: thongKeNhapHang(); break;
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

                    // Tạo chi tiết phiếu nhập
                    ChiTietPhieuNhapDTO chiTiet = new ChiTietPhieuNhapDTO(maPhieu, maSP, soLuong, giaNhap);
                    ChiTietPhieuNhapDAO.themChiTietPhieuNhap(chiTiet);
                    tongTien += soLuong * giaNhap;
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


    private void xemChiTiet() { }
    private void suaPhieuNhap() { }
    private void xoaPhieuNhap() { }
    private void timKiem() { }
    private void thongKeNhapHang() { }
    private void xuatBaoCao() { }
}