package view;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import dao.ChiTietPhieuNhapDAO;
import dao.NhapHangDAO;
import dto.ChiTietPhieuNhapDTO;
import dto.NhapHangDTO;
import util.FormatUtil;

public class QuanLyChiTietPhieuNhap {
    public static void menuQuanLyChiTietPhieuNhap() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n████████████████████████████████████████████████████████████████████████████████");
            System.out.println("██                                                                            ██");
            System.out.println("██                      HỆ THỐNG QUẢN LÝ CHI TIẾT PHIẾU NHẬP                      ██");
            System.out.println("██                                                                            ██");
            System.out.println("████████████████████████████████████████████████████████████████████████████████");
            System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓ MENU CHỨC NĂNG ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
            System.out.println("▒ [1] ➜ Thêm chi tiết vào phiếu nhập                                         ▒");
            System.out.println("▒ [2] ➜ Xóa chi tiết phiếu nhập                                            ▒");
            System.out.println("▒ [3] ➜ Tìm kiếm chi tiết phiếu nhập                                        ▒");
            System.out.println("▒ [4] ➜ Xem danh sách chi tiết phiếu nhập                                  ▒");
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
                    themChiTietVaoPhieuNhap(scanner);
                    break;
                case 2:
                    // xoaChiTietPhieuNhap();
                    break;
                case 3:
                    // timKiemChiTietPhieuNhap();
                    break;
                case 4:
                    // xemDanhSachChiTietPhieuNhap();
                    break;
                case 0:
                    System.out.println("Quay lại menu chính.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
                    break;
            }
        }
    }


    public static void themChiTietVaoPhieuNhap(Scanner scanner) {
        Connection conn = null;
        try {
            System.out.print("\nNhập mã phiếu nhập: ");
            String maPhieu = scanner.nextLine().trim();

            NhapHangDTO phieuNhap = NhapHangDAO.timPhieuNhapTheoMa(maPhieu);
            if (phieuNhap == null) {
                System.out.println("Không tìm thấy phiếu nhập với mã: " + maPhieu);
                return;
            }

            conn = util.JDBCUtil.getConnection();

            int tongTienThem = 0;
            int countSuccess = 0;

            while (true) {
                System.out.print("\nNhập mã sản phẩm (nhập '0' để kết thúc): ");
                String maSP = scanner.nextLine().trim();
                if (maSP.equals("0")) break;

                System.out.print("Nhập số lượng: ");
                int soLuong;
                try {
                    soLuong = Integer.parseInt(scanner.nextLine().trim());
                    if (soLuong <= 0) {
                        System.out.println("Số lượng phải lớn hơn 0.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Số lượng không hợp lệ.");
                    continue;
                }

                System.out.print("Nhập giá nhập: ");
                int giaNhap;
                try {
                    giaNhap = Integer.parseInt(scanner.nextLine().trim());
                    if (giaNhap <= 0) {
                        System.out.println("Giá nhập phải lớn hơn 0.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Giá nhập không hợp lệ.");
                    continue;
                }

                int thanhTien = soLuong * giaNhap;

                // Thêm chi tiết (DAO tự kiểm tra trùng mã + giá)
                ChiTietPhieuNhapDTO chiTiet = new ChiTietPhieuNhapDTO(maPhieu, maSP, null, null, soLuong, giaNhap, thanhTien);
                boolean added = ChiTietPhieuNhapDAO.themChiTietPhieuNhap(conn, chiTiet);

                if (added) {
                    tongTienThem += thanhTien;
                    countSuccess++;
                }
            }

            if (countSuccess > 0) {
                int tongTienMoi = phieuNhap.getTongTien() + tongTienThem;
                phieuNhap.setTongTien(tongTienMoi);

                NhapHangDAO.suaPhieuNhap(phieuNhap, maPhieu);

                System.out.println("\nĐã thêm " + countSuccess + " sản phẩm vào phiếu nhập " + maPhieu + ".");

            } else {
                System.out.println("Không có sản phẩm nào được thêm.");
            }

        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
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

    /**
     * Tìm kiếm và hiển thị chi tiết phiếu nhập theo mã phiếu
     */
    private static void timKiemChiTietPhieuNhap(Scanner scanner) {
        System.out.print("\nNhập mã phiếu nhập cần tìm: ");
        String maPhieu = scanner.nextLine().trim();

        List<ChiTietPhieuNhapDTO> danhSach = ChiTietPhieuNhapDAO.timChiTietPhieuNhap(maPhieu);

        if (danhSach.isEmpty()) {
            System.out.println("❌ Không tìm thấy chi tiết cho phiếu nhập: " + maPhieu);
        } else {
            inChiTietPhieuNhap(maPhieu, danhSach);
        }
    }

    /**
     * Xem danh sách chi tiết phiếu nhập
     */
    private static void xemDanhSachChiTietPhieuNhap(Scanner scanner) {
        System.out.print("\nNhập mã phiếu nhập: ");
        String maPhieu = scanner.nextLine().trim();

        List<ChiTietPhieuNhapDTO> danhSach = ChiTietPhieuNhapDAO.timChiTietPhieuNhap(maPhieu);

        if (danhSach.isEmpty()) {
            System.out.println("❌ Phiếu nhập không có chi tiết hoặc không tồn tại.");
        } else {
            inChiTietPhieuNhap(maPhieu, danhSach);
        }
    }

    /**
     * In bảng chi tiết phiếu nhập
     */
    private static void inChiTietPhieuNhap(String maPhieu, List<ChiTietPhieuNhapDTO> danhSach) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║           CHI TIẾT PHIẾU NHẬP: " + maPhieu + "                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.printf("%-5s | %-10s | %-20s | %-10s | %-10s | %-12s | %-12s%n",
                "STT", "Mã SP", "Tên SP", "Đơn vị", "Số lượng", "Giá nhập", "Thành tiền");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        int stt = 1;
        int tongTien = 0;
        for (ChiTietPhieuNhapDTO ct : danhSach) {
            System.out.printf("%-5d | %-10s | %-20s | %-10s | %-10d | %-12s | %-12s%n",
                    stt++,
                    ct.getMaSP(),
                    ct.getTenSP(),
                    ct.getDonViTinh(),
                    ct.getSoLuong(),
                    FormatUtil.formatVND(ct.getGiaNhap()),
                    FormatUtil.formatVND(ct.getThanhTien()));
            tongTien += ct.getThanhTien();
        }

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("💰 TỔNG TIỀN: " + FormatUtil.formatVND(tongTien));
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}
