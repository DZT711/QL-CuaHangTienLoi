package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import dao.HoaDonDAO;
import dao.ChiTietHoaDonDAO;
import dao.HangHoaDAO;
import dao.NhanVienDAO;
import dto.ChiTietHoaDonDTO;
import dto.HangHoaDTO;
import dto.HoaDonDTO;
import dto.KhachHangDTO;
import dto.NhanVienDTO;
import main.Main;
import dao.KhachHangDAO;
import dao.SanPhamDAO;
import dto.sanPhamDTO;
import java.util.InputMismatchException;
import java.time.format.DateTimeParseException;
import util.FormatUtil;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class QuanLyHoaDon {
    public void menuQuanLyHoaDon() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n████████████████████████████████████████████████████████████████████████████████");
            System.out.println("██                                                                            ██");
            System.out.println("██                         HỆ THỐNG QUẢN LÝ HÓA ĐƠN                           ██");
            System.out.println("██                                                                            ██");
            System.out.println("████████████████████████████████████████████████████████████████████████████████");
            System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓ MENU CHỨC NĂNG ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
            System.out.println("▒ [1] ➜ Thêm hóa đơn                                                           ▒");
            System.out.println("▒ [2] ➜ Xóa hóa đơn                                                            ▒");
            System.out.println("▒ [3] ➜ Tìm kiếm hóa đơn                                                       ▒");
            System.out.println("▒ [4] ➜ Xem danh sách hóa đơn                                                  ▒");
            System.out.println("▒ [5] ➜ Thống kê hóa đơn                                                       ▒");
            System.out.println("▒ [6] ➜ Xuất hóa đơn                                                           ▒");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ");
            System.out.println("░ [0] ✗ Quay lại menu chính                                                    ░");
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
                    themHoaDon();
                    break;
                case 2:
                    xoaHoaDon();
                    break;
                case 3:
                    while (true) {
                        try {
                            // Làm lại giao diện cho đẹp hơn
                            System.out.println("\n");
                            System.out.println("Tìm kiếm hóa đơn");
                            System.out.println("1. Tìm kiếm hóa đơn theo mã hóa đơn");
                            System.out.println("2. Tìm kiếm hóa đơn theo mã khách hàng");
                            System.out.println("3. Tìm kiếm hóa đơn theo mã nhân viên");
                            System.out.println("4. Tìm kiếm hóa đơn theo ngày lập");
                            System.out.println("0. Thoát");
                            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

                            int opt = scanner.nextInt();
                            scanner.nextLine();

                            if (opt == 0) {
                                System.out.println("Thoát tìm kiếm hóa đơn thành công.");
                                break;
                            } 
                            switch (opt) {
                                case 1:
                                    timHDTheoMaHD();
                                    break;
                                case 2:
                                    timHDTheoMaKH();
                                    break;
                                case 3:
                                    timHDTheoMaNV();
                                    break;
                                case 4:
                                    timHoaDonTheoNgay();
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
                case 4:
                    xemDanhSachHoaDon();
                    break;
                case 5:
                    while (true) {
                        try {
                            System.out.println("\n");
                            System.out.println("Thống kê hóa đơn");
                            System.out.println("1. Thống kê doanh thu theo khoảng thời gian");
                            System.out.println("2. Thống kê hóa đơn theo nhân viên");
                            System.out.println("3. Thống kê hóa đơn theo khách hàng");
                            System.out.println("4. Thống kê hóa dơn theo năm");
                            System.out.println("5. Thống kê theo phương thức thanh toán");
                            System.out.println("0. Thoát");
                            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

                            int opt = scanner.nextInt();
                            scanner.nextLine();

                            if (opt == 0) {
                                System.out.println("Thoát thống kê hóa đơn thành công.");
                                break;
                            }
                            switch (opt) {
                                case 1:
                                    thongKeHDTheoNgay();
                                    break;
                                case 2:
                                    thongKeHoaDonTheoNV();
                                    break;
                                case 3:
                                    thongKeHoaDonTheoKH();
                                    break;
                                case 4:
                                    thongKeHoaDonTheoNam();
                                    break;
                                case 5:
                                    thongKeHoaDonTheoPTTT();
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
                case 6:
                    while (true) {
                        try {
                            System.out.println("\n");
                            System.out.println("Xuất hóa đơn");
                            System.out.println("1. Xuất hóa đơn theo mã hóa đơn");
                            System.out.println("2. Xuất chi tiết hóa đơn theo mã hóa đơn");
                            System.out.println("3. Xuất hóa đơn kèm chi tiết hóa đơn theo mã hóa đơn");
                            System.out.println("0. Thoát");
                            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

                            int opt = scanner.nextInt();
                            scanner.nextLine();

                            if (opt == 0) {
                                System.out.println("Thoát xuất hóa đơn thành công.");
                                break;
                            }

                            switch (opt) {
                                case 1:
                                    xuatHoaDonTheoMaHD();
                                    break;
                                case 2:
                                    xuatChiTietHoaDonTheoMaHD();
                                    break;
                                case 3:
                                    xuatHoaDonKemChiTietHoaDonTheoMaHD();
                                    break;
                                default:
                                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại");
                                    break;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Lỗi xảy ra: " + e.getMessage());
                            scanner.nextLine();
                        } catch (Exception e) {
                            System.out.println("Lỗi xảy ra: " + e.getMessage());
                        }
                    }
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại");
                    break;
            }
        }
    }

    public void themHoaDon() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Nhập số điện thoại khách hàng: ");
                String sdt = scanner.nextLine().trim();

                KhachHangDTO kh = KhachHangDAO.timKhachHangTheoDienThoai(sdt);
                if (kh == null) {
                    System.out.println("Khách hàng chưa có trong hệ thống, Nhập thông tin khách hàng mới: ");

                    String maKH = KhachHangDAO.generateIDKhachHang();

                    System.out.println("Nhập họ khách hàng: ");
                    String ho = scanner.nextLine().trim();
                    while(ho.isEmpty()) {
                        System.out.println("Họ khách hàng không được để trống, vui lòng nhập lại.");
                        System.out.println("Nhập họ khách hàng: ");
                        ho = scanner.nextLine().trim();
                    }

                    System.out.println("Nhập tên khách hàng: ");
                    String ten = scanner.nextLine().trim();
                    while(ten.isEmpty()) {
                        System.out.println("Tên khách hàng không được để trống, vui lòng nhập lại.");
                        System.out.println("Nhập tên khách hàng: ");
                        ten = scanner.nextLine().trim();
                    }

                    KhachHangDTO customer = new KhachHangDTO();
                    customer.setMaKH(maKH);
                    customer.setHo(ho);
                    customer.setTen(ten);
                    customer.setDienThoai(sdt);   
                    KhachHangDAO.themKhachHang(customer);
                    kh = customer;
                }
                else System.out.println("✅ Khách hàng đã tồn tại trong hệ thống.");
                
                String maHD = HoaDonDAO.generateIDHoaDon();
                String maNV = Main.CURRENT_ACCOUNT.getMaNV();

                List<ChiTietHoaDonDTO> chiTietHoaDon = new ArrayList<>();
                int tongTien = 0;
                
                System.out.println("\n📦 THÊM SẢN PHẨM VÀO HÓA ĐƠN (Nhập '0' để kết thúc)");
                while (true) {
                    System.out.print("Nhập mã hàng hóa: ");
                    String maHang = scanner.nextLine().trim();
                    if (maHang.equals("0")) break;

                    // Kiểm tra hàng hóa tồn tại TRƯỚC
                    HangHoaDTO hangHoa = HangHoaDAO.timHangHoaTheoMa(maHang);
                    if (hangHoa == null) {
                        System.out.println("❌ Mã hàng không tồn tại! Vui lòng nhập lại.");
                        continue;
                    }

                    // Lấy thông tin sản phẩm từ HANGHOA
                    String maSP = hangHoa.getMaSP();
                    sanPhamDTO sp = SanPhamDAO.timSanPhamTheoMa(maSP);
                    if (sp == null) {
                        System.out.println("❌ Lỗi: Không tìm thấy thông tin sản phẩm!");
                        continue;
                    }

                    // Hiển thị thông tin sản phẩm và tồn kho lô
                    System.out.println("📦 Sản phẩm: " + sp.getTenSP());
                    System.out.println("💰 Giá bán: " + FormatUtil.formatVND(sp.getGiaBan()));
                    System.out.println("📊 Tồn kho lô này: " + hangHoa.getSoLuongConLai());

                    System.out.print("Nhập số lượng: ");
                    String slString = scanner.nextLine().trim();
                    int soLuong;
                    
                    while (true) {
                        try {
                            soLuong = Integer.parseInt(slString);
                            if (soLuong <= 0) {
                                System.out.println("❌ Số lượng phải lớn hơn 0!");
                                System.out.print("Nhập số lượng: ");
                                slString = scanner.nextLine().trim();
                                continue;
                            }
                            // Kiểm tra tồn kho LÔ HÀNG (không phải tổng)
                            if (soLuong > hangHoa.getSoLuongConLai()) {
                                System.out.println("❌ Số lượng vượt quá tồn kho lô này (còn " + hangHoa.getSoLuongConLai() + ")");
                                System.out.print("Nhập số lượng: ");
                                slString = scanner.nextLine().trim();
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("❌ Số lượng không hợp lệ!");
                            System.out.print("Nhập số lượng: ");
                            slString = scanner.nextLine().trim();
                        }
                    }

                    int donGia = sp.getGiaBan();
                    int thanhTien = soLuong * donGia;
                    chiTietHoaDon.add(new ChiTietHoaDonDTO(maHD, maHang, sp.getTenSP(), soLuong, donGia, thanhTien));
                    tongTien += thanhTien;
                }
                
                // Kiểm tra hóa đơn rỗng
                if (chiTietHoaDon.isEmpty()) {
                    System.out.println("⚠️ Hóa đơn không có sản phẩm nào! Hủy tạo hóa đơn.");
                    continue;
                }
                
                HoaDonDTO hoaDon = new HoaDonDTO();
                
                System.out.println("\n💳 PHƯƠNG THỨC THANH TOÁN");
                System.out.println("Tổng tiền: " + FormatUtil.formatVND(tongTien));
                
                while (true) {
                    System.out.println("1. Tiền mặt");
                    System.out.println("2. Chuyển khoản");
                    System.out.print("Lựa chọn của bạn: ");

                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    if (choice == 1) {
                        hoaDon.setPhuongThucTT("Tiền mặt");
                        
                        System.out.print("Nhập tiền khách đưa: ");
                        int tienKhachDua = scanner.nextInt();
                        scanner.nextLine();
                        
                        while (tienKhachDua < tongTien) {
                            System.out.println("❌ Tiền khách đưa không đủ! Còn thiếu: " + FormatUtil.formatVND(tongTien - tienKhachDua));
                            System.out.print("Nhập tiền khách đưa: ");
                            tienKhachDua = scanner.nextInt();
                            scanner.nextLine();
                        }
                        
                        hoaDon.setTienKhachDua(tienKhachDua);
                        hoaDon.setTienThua(tienKhachDua - tongTien);
                        break;
                        
                    } else if (choice == 2) {
                        hoaDon.setPhuongThucTT("Chuyển khoản");
                        hoaDon.setTienKhachDua(tongTien);
                        hoaDon.setTienThua(0);
                        System.out.println("✅ Chuyển khoản: " + FormatUtil.formatVND(tongTien));
                        break;
                    } else {
                        System.out.println("❌ Lựa chọn không hợp lệ!");
                    }
                }

                // Lưu hóa đơn và cập nhật tồn kho
                hoaDon.setMaHD(maHD);
                hoaDon.setMaKH(kh.getMaKH());
                hoaDon.setMaNV(maNV);
                hoaDon.setTongTien(tongTien);
                hoaDon.setNgayLapHD(LocalDateTime.now());
                
                HoaDonDAO.themHoaDon(hoaDon);

                for (ChiTietHoaDonDTO ctHoaDon : chiTietHoaDon) {
                    ChiTietHoaDonDAO.themChiTietHoaDon(ctHoaDon);
                    String maHangCT = ctHoaDon.getMaHang();
                    
                    HangHoaDTO hh = HangHoaDAO.timHangHoaTheoMa(maHangCT);
                    if (hh != null) {
                        // Trừ tồn kho lô hàng cụ thể
                        HangHoaDAO.truSoLuongConLai(maHangCT, ctHoaDon.getSoLuong());
                        // Trừ tồn kho sản phẩm (tổng)
                        SanPhamDAO.truSoLuongTon(hh.getMaSP(), ctHoaDon.getSoLuong());
                    }
                }
                
                System.out.println("\n✅ Thêm hóa đơn thành công!");
                HoaDonDTO hoaDonMoi = HoaDonDAO.timHoaDon(maHD);
                inHoaDon(hoaDonMoi);

                System.out.print("\nBạn có muốn tạo hóa đơn khác? (y/n): ");
                String choice = scanner.nextLine().trim();
                if (!"y".equalsIgnoreCase(choice)) break;
                
            } catch (Exception e) {
                System.out.println("❌ Lỗi: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void timHDTheoMaHD() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập mã hóa đơn cần tìm: ");
        try {
            String maHD = scanner.nextLine().trim();
            HoaDonDTO hd = HoaDonDAO.timHoaDon(maHD);
            if (hd != null) {
                System.out.println("✅ Tìm thấy hóa đơn: " + maHD);
                inHoaDon(hd);  
            } else {
                System.out.println("❌ Không tìm thấy hóa đơn với mã: " + maHD);
            }
        } catch (InputMismatchException e) {
            System.out.println("Lỗi: Vui lòng nhập mã hóa đơn hợp lệ");
            scanner.nextLine();
        }
    }

    public void timHDTheoMaKH() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập mã khách hàng để tìm hóa đơn: ");
        try {
            String maKH = scanner.nextLine().trim();
            KhachHangDTO kh = KhachHangDAO.timKhachHangTheoMa(maKH);
            if (kh != null) {
                System.out.println("Thông tin hóa đơn tìm thấy với mã: " + maKH);
                HoaDonDAO.timHoaDonTheoMaKH(maKH);
                String tieptuc;
                do {
                    System.out.println("Bạn có muốn xem chi tiết hóa đơn không (y/n): ");
                    tieptuc = scanner.nextLine().trim();

                    if (tieptuc.equalsIgnoreCase("y")) {
                        System.out.println("Nhập mã hóa đơn cần xem chi tiết: ");
                        String maHD = scanner.nextLine().trim();
                        HoaDonDTO hoaDon = HoaDonDAO.timHoaDon(maHD);
                        inHoaDon(hoaDon);
                    } else {
                        System.out.println("Không xem chi tiết hóa đơn nào.");
                    }
                } while (tieptuc.equalsIgnoreCase("y"));
            } else {
                System.out.println("Không tìm thấy hóa đơn với mã: " + maKH);
            }
        } catch (InputMismatchException e) {
            System.out.println("Lỗi: Vui lòng nhập mã khách hàng hợp lệ");
            scanner.nextLine();
        }
    }

    public void timHDTheoMaNV() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập mã nhân viên cần tìm: ");
        try {
            String maNV = scanner.nextLine().trim();

            NhanVienDTO nv = NhanVienDAO.timNhanVienTheoMa(maNV);
            if (nv != null) {
                System.out.println("Thông tin hóa đơn do nhân viên: " + maNV + " lập: ");
                HoaDonDAO.timHoaDonTheoMaNV(maNV);
                String tieptuc;
                do {
                    System.out.println("Bạn có muốn xem chi tiết hóa đơn không (y/n): ");
                    tieptuc = scanner.nextLine().trim();
                    if (tieptuc.equalsIgnoreCase("y")) {
                        System.out.println("Nhập mã hóa đơn cần xem chi tiết: ");
                        String maHD = scanner.nextLine().trim();
                        HoaDonDTO hoaDon = HoaDonDAO.timHoaDon(maHD);
                        inHoaDon(hoaDon);
                    } else {
                        System.out.println("Không xem chi tiết hóa đơn nào.");
                    }
                } while (tieptuc.equalsIgnoreCase("y"));
            } else {
                System.out.println("Không tìm thấy hóa đơn với mã nhân viên: " + maNV);
            }
        } catch (InputMismatchException e) {
            System.out.println("Lỗi: Vui lòng nhập mã nhân viên hợp lệ");
            scanner.nextLine();
        }
    }

    // Làm lại giao diện cho giống thực tế, đẹp hơn, tự sắp xếp bố cục lại cho phù hợp
    public void inHoaDon(HoaDonDTO hoaDon) {
        // Kiểm tra hóa đơn tồn tại
        if (hoaDon == null) {
            System.out.println("❌ Không có thông tin hóa đơn!");
            return;
        }
        
        List<ChiTietHoaDonDTO> chiTietHoaDon = ChiTietHoaDonDAO.timChiTietHoaDon(hoaDon.getMaHD());
        
        System.out.println("\n════════════════════════════════════════════════════════");
        System.out.println("                    ABC STORE                           ");
        System.out.println("            123 An Dương Vương, Q5, TP.HCM             ");
        System.out.println("               Điện thoại: 0909090909                   ");
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("                  HÓA ĐƠN BÁN HÀNG                      ");
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("Mã hóa đơn         : " + hoaDon.getMaHD());
        System.out.println("Ngày lập           : " + hoaDon.getNgayLapHD().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.println("Khách hàng         : " + hoaDon.getMaKH());
        System.out.println("Nhân viên          : " + hoaDon.getMaNV());
        System.out.println("Phương thức TT     : " + hoaDon.getPhuongThucTT());
        System.out.println("────────────────────────────────────────────────────────");
        System.out.println("                   CHI TIẾT HÓA ĐƠN                     ");
        System.out.println("────────────────────────────────────────────────────────");

        if (chiTietHoaDon.isEmpty()) {
            System.out.println("⚠️ Hóa đơn không có sản phẩm nào!");
        } else {
            for (ChiTietHoaDonDTO ctHoaDon : chiTietHoaDon) {
                ctHoaDon.inChiTietHoaDon();
            }
        }
        
        System.out.println("────────────────────────────────────────────────────────");
        System.out.println("Tổng tiền          : " + FormatUtil.formatVND(hoaDon.getTongTien()));
        System.out.println("Tiền khách đưa     : " + FormatUtil.formatVND(hoaDon.getTienKhachDua()));
        System.out.println("Tiền thừa          : " + FormatUtil.formatVND(hoaDon.getTienThua()));
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("           Cảm ơn quý khách! Hẹn gặp lại!              ");
        System.out.println("════════════════════════════════════════════════════════\n");
    }
    
    public void xoaHoaDon() { 
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Nhập mã hóa đơn cần xóa: ");
                String maHD = scanner.nextLine().trim();
                
                if (HoaDonDAO.timHoaDon(maHD) == null) {
                    System.out.println("Mã hóa đơn không tồn tại, vui lòng nhập lại.");
                    continue;
                }

                HoaDonDAO.xoaHoaDon(maHD);
                break;
            }
            catch (Exception e) {
                System.out.println("Lỗi: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
    
    public void timHoaDonTheoNgay() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        while (true) {
            String from, to;
            LocalDate fromDate = null, toDate = null;
            
            while (true) {
                try {
                    System.out.println("Nhập ngày bắt đầu (ddMMyyyy): ");
                    from = scanner.nextLine().trim();
    
                    System.out.println("Nhập ngày kết thúc (ddMMyyyy): ");
                    to = scanner.nextLine().trim();
    
                    fromDate = LocalDate.parse(from, formatter);
                    toDate = LocalDate.parse(to, formatter);
                    if (fromDate.isAfter(toDate)) {
                        System.out.println("❌ Ngày bắt đầu phải trước ngày kết thúc, vui lòng nhập lại.");
                        continue;
                    }
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("❌ Định dạng ngày không hợp lệ, vui lòng nhập lại (ddMMyyyy).");
                }
            }
            
            List<HoaDonDTO> list = HoaDonDAO.timHoaDonTheoNgayLap(fromDate, toDate);

            System.out.println("\n📅 DANH SÁCH HÓA ĐƠN");
            System.out.println("Từ ngày: " + fromDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + 
                                    " đến " + toDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println("════════════════════════════════════════════════════════");

            if (list.isEmpty()) {
                System.out.println("⚠️ Không tìm thấy hóa đơn trong khoảng thời gian này");
            } else {
                for (HoaDonDTO hd : list) {
                    System.out.println("Mã hóa đơn         : " + hd.getMaHD());
                    System.out.println("Ngày lập           : " + hd.getNgayLapHD().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                    System.out.println("Khách hàng         : " + hd.getMaKH());
                    System.out.println("Nhân viên          : " + hd.getMaNV());
                    System.out.println("Phương thức TT     : " + hd.getPhuongThucTT());
                    System.out.println("Tổng tiền          : " + FormatUtil.formatVND(hd.getTongTien()));
                    System.out.println("────────────────────────────────────────────────────────");
                }
                System.out.println("📊 Tổng cộng: " + list.size() + " hóa đơn");
                
                // Option xem chi tiết hóa đơn
                String tieptuc;
                do {
                    System.out.println("\nBạn có muốn xem chi tiết hóa đơn không (y/n): ");
                    tieptuc = scanner.nextLine().trim();

                    if (tieptuc.equalsIgnoreCase("y")) {
                        System.out.println("Nhập mã hóa đơn cần xem chi tiết: ");
                        String maHD = scanner.nextLine().trim();
                        HoaDonDTO hoaDon = HoaDonDAO.timHoaDon(maHD);
                        inHoaDon(hoaDon);
                    }
                } while (tieptuc.equalsIgnoreCase("y"));
            }
            
            System.out.print("\n❓ Bạn có muốn tìm tiếp không? (y/n): ");
            String choice = scanner.nextLine().trim();
            if (!choice.equalsIgnoreCase("y")) {
                System.out.println("✅ Thoát tìm kiếm hóa đơn thành công.");
                break;
            }
        }
    }

    // Làm lại giao diện cho giống thực tế, đẹp hơn
    public void xemDanhSachHoaDon() {
        Scanner scanner = new Scanner(System.in);
        List<HoaDonDTO> list = HoaDonDAO.getAllHoaDon();

        System.out.println("\n📋 DANH SÁCH TẤT CẢ HÓA ĐƠN");
        System.out.println("════════════════════════════════════════════════════════");

        if (list.isEmpty()) {
            System.out.println("⚠️ Không có hóa đơn nào trong hệ thống.");
            return;
        } 

        for (HoaDonDTO hd : list) {
            System.out.println("Mã hóa đơn         : " + hd.getMaHD());
            System.out.println("Ngày lập           : " + hd.getNgayLapHD().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            System.out.println("Khách hàng         : " + hd.getMaKH());
            System.out.println("Nhân viên          : " + hd.getMaNV());
            System.out.println("Phương thức TT     : " + hd.getPhuongThucTT());
            System.out.println("Tổng tiền          : " + FormatUtil.formatVND(hd.getTongTien()));
            System.out.println("────────────────────────────────────────────────────────");
        }

        System.out.println("📊 Tổng cộng: " + list.size() + " hóa đơn");

        String tieptuc;
        do {
            System.out.println("\nBạn có muốn xem chi tiết hóa đơn không (y/n): ");
            tieptuc = scanner.nextLine().trim();

            if (tieptuc.equalsIgnoreCase("y")) {
                System.out.println("Nhập mã hóa đơn cần xem chi tiết: ");
                String maHD = scanner.nextLine().trim();
                HoaDonDTO hoaDon = HoaDonDAO.timHoaDon(maHD);
                inHoaDon(hoaDon);
            } else {
                System.out.println("✅ Không xem chi tiết hóa đơn nào.");
                break;
            }
        } while (tieptuc.equalsIgnoreCase("y"));
    }

    // Làm lại giao diện cho giống thực tế, đẹp hơn
    public void thongKeHDTheoNgay() {
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
                Map<String, Object> result = HoaDonDAO.thongKeHDTheoThoiGian(fromDate, toDate);

                System.out.println("\n========= BÁO CÁO THỐNG KÊ HÓA ĐƠN =========");
                System.out.println("Từ ngày: " + from + "  đến ngày: " + to);

                if (result.isEmpty() || result.get("SoHoaDon") == null) {
                    System.out.println("Không tìm thấy hóa đơn trong khoảng thời gian này");
                } else {
                    System.out.println("Số hóa đơn: " + result.get("SoHoaDon"));
                    System.out.println("Số khách hàng: " + result.get("SoKhachHang"));
                    System.out.println("Tổng sản phẩm: " + result.get("TongSanPham"));
                    System.out.println("Tổng doanh thu: " + FormatUtil.formatVND((long)result.get("TongDoanhThu")));
                    System.out.println("Doanh thu trung bình: " + FormatUtil.formatVND((double)result.get("DoanhThuTrungBinh")));
                    System.out.println("Tìm thấy " + result.get("SoHoaDon") + " hóa đơn trong khoảng thời gian này");
                }
                System.out.println("========================================================");
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Định dạng ngày không hợp lệ, vui lòng nhập lại.");
                scanner.nextLine();
            }
        }
    }

    // Làm lại giao diện cho giống thực tế, đẹp hơn
    public void thongKeHoaDonTheoNam() {
        Scanner scanner = new Scanner(System.in);
        int year = 0;

        while (true) {
            try {
                System.out.print("Nhập năm muốn thống kê: ");
                year = Integer.parseInt(scanner.nextLine().trim());
                if (year < 2000 || year > LocalDate.now().getYear()) {
                    System.out.println("Năm không hợp lệ, vui lòng nhập lại.");
                    continue;
                }
                break; 
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên hợp lệ cho năm.");
            }
        }

        List<Map<String, Object>> result = HoaDonDAO.thongKeHDTheoNam(year);

        System.out.println("\n========= BÁO CÁO THỐNG KÊ HÓA ĐƠN THEO NĂM =========");
        if (result.isEmpty()) {
            System.out.println("Không tìm thấy hóa đơn trong năm này");
            return;
        } 

        int tongSoHoaDon = 0;
        int tongSanPham = 0;
        long tongDoanhThu = 0;

        System.out.println("-------------------------------------------------------------------");
        System.out.println("| Tháng | Số hóa đơn | Tổng sản phẩm | Tổng doanh thu |");
        System.out.println("-------------------------------------------------------------------");
        for (Map<String, Object> row : result) {
            System.out.println("| " + row.get("Thang") + 
                                " | " + row.get("SoHoaDon") + 
                                " | " + row.get("TongSanPham") + 
                                " | " + FormatUtil.formatVND((long)row.get("TongDoanhThu")) + " |"
            );
            tongSoHoaDon += (int)row.get("SoHoaDon");
            tongSanPham += (int)row.get("TongSanPham");
            tongDoanhThu += (long)row.get("TongDoanhThu");
        }
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Tìm thấy " + result.size() + " hóa đơn trong năm này");
        System.out.println("Tổng số hóa đơn: " + tongSoHoaDon);
        System.out.println("Tổng sản phẩm bán được: " + tongSanPham);
        System.out.println("Tổng doanh thu: " + FormatUtil.formatVND(tongDoanhThu));
        System.out.println("========================================================");
    }
    
    // Làm lại giao diện cho giống thực tế, đẹp hơn
    public void thongKeHoaDonTheoNV() {
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

                List<Map<String, Object>> result = HoaDonDAO.thongKeHDTheoNhanVien(fromDate, toDate);

                System.out.println("\n========= BÁO CÁO THỐNG KÊ HÓA ĐƠN THEO NHÂN VIÊN =========");
                System.out.println("Từ ngày: " + from + " đến ngày: " + to);
                if (result.isEmpty()) {
                    System.out.println("Không tìm thấy hóa đơn trong khoảng thời gian này");
                    break;
                } else {
                    System.out.println("Danh sách nhân viên: ");
                    System.out.println("Mã nhân viên | Họ và tên | Số hóa đơn | Tổng sản phẩm | Tổng doanh thu");
                    System.out.println("----------------------------------------------------------");
                    long tongDoanhThu = 0;
                    for (Map<String, Object> row : result) {
                        System.out.println(row.get("MaNV") + " | " + row.get("Ho Ten") + " | " + row.get("SoHoaDon") + " | " + row.get("TongSanPham") + " | " + FormatUtil.formatVND((long)row.get("TongDoanhThu")));
                        tongDoanhThu += (long)row.get("TongDoanhThu");
                    }
                    System.out.println("Tìm thấy " + result.size() + " nhân viên trong khoảng thời gian này");
                    System.out.println("Tổng doanh thu: " + FormatUtil.formatVND(tongDoanhThu));
                    System.out.println("========================================================");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Định dạng ngày không hợp lệ, vui lòng nhập lại.");
                scanner.nextLine();
            }
        }
    }

    // Làm lại giao diện cho giống thực tế, đẹp hơn
    public void thongKeHoaDonTheoKH() {
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

                List<Map<String, Object>> result = HoaDonDAO.thongKeHDTheoKhachHang(fromDate, toDate);

                System.out.println("\n========= BÁO CÁO THỐNG KÊ HÓA ĐƠN THEO KHÁCH HÀNG =========");
                System.out.println("Từ ngày: " + from + " đến ngày: " + to);
                if (result.isEmpty()) {
                    System.out.println("Không tìm thấy hóa đơn trong khoảng thời gian này");
                    break;
                }
                else {
                    System.out.println("Danh sách khách hàng: ");
                    System.out.println("Mã khách hàng | Họ và tên | Số hóa đơn | Tổng sản phẩm | Tổng chi tiêu");
                    System.out.println("----------------------------------------------------------");
                    long tongChiTieu = 0;
                    for (Map<String, Object> row : result) {
                        System.out.println(
                            row.get("MaKH") + " | " + 
                            row.get("Ho Ten") + " | " + 
                            row.get("SoHoaDon") + " | " + 
                            row.get("TongSanPham") + " | " + 
                            FormatUtil.formatVND((long)row.get("TongChiTieu"))
                        );
                        tongChiTieu += (long)row.get("TongChiTieu");
                    }
                    System.out.println("Tìm thấy " + result.size() + " khách hàng trong khoảng thời gian này");
                    System.out.println("Tổng chi tiêu của khách hàng: " + FormatUtil.formatVND(tongChiTieu));
                    System.out.println("========================================================");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Định dạng ngày không hợp lệ, vui lòng nhập lại.");
                scanner.nextLine();
            }
        }
    }

    public void thongKeHoaDonTheoPTTT() {
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

                List<Map<String, Object>> result = HoaDonDAO.thongKeHDTheoPhuongThucTT(fromDate, toDate);

                System.out.println("\n========= BÁO CÁO THỐNG KÊ HÓA ĐƠN THEO PHƯƠNG THỨC THANH TOÁN =========");
                System.out.println("Từ ngày: " + from + " đến ngày: " + to);

                if (result.isEmpty()) {
                    System.out.println("Không tìm thấy hóa đơn trong khoảng thời gian này");
                    break;
                }
                
                System.out.println("Danh sách phương thức thanh toán: ");
                System.out.println("Phương thức thanh toán | Số hóa đơn | Tổng sản phẩm | Tổng doanh thu");
                System.out.println("----------------------------------------------------------");

                int tongSoHoaDon = 0;
                int tongSanPham = 0;
                long tongDoanhThu = 0;

                for (Map<String, Object> row : result) {
                    System.out.println(
                        row.get("PTTT") + " | " + 
                        row.get("SoHoaDon") + " | " + 
                        row.get("TongSanPham") + " | " + 
                        FormatUtil.formatVND((long)row.get("TongDoanhThu"))
                    );

                    tongSoHoaDon += (int)row.get("SoHoaDon");
                    tongSanPham += (int)row.get("TongSanPham");
                    tongDoanhThu += (long)row.get("TongDoanhThu");
                }

                System.out.println("========================================================");
                System.out.println("Tìm thấy " + result.size() + " phương thức thanh toán trong khoảng thời gian này");
                System.out.println("Tổng số hóa đơn: " + tongSoHoaDon);
                System.out.println("Tổng sản phẩm bán được: " + tongSanPham);
                System.out.println("Tổng doanh thu: " + FormatUtil.formatVND(tongDoanhThu));
                System.out.println("========================================================");
                
            } catch (DateTimeParseException e) {
                System.out.println("Định dạng ngày không hợp lệ, vui lòng nhập lại.");
                scanner.nextLine();
            }
        }
    }

    public void xuatHoaDonTheoMaHD() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn cần xuất: ");
        String maHD = scanner.nextLine().trim();

        HoaDonDTO hoaDon = HoaDonDAO.timHoaDon(maHD);
        if (hoaDon == null) {
            System.out.println("❌ Không tìm thấy hóa đơn với mã: " + maHD);
            return;
        }

        String fileName = "HoaDon_" + maHD + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("════════════════════════════════════════════════════════");
            writer.println("                    HÓA ĐƠN BÁN HÀNG                   ");
            writer.println("════════════════════════════════════════════════════════");
            writer.println("Mã hóa đơn         : " + hoaDon.getMaHD());
            writer.println("Mã khách hàng      : " + hoaDon.getMaKH());
            writer.println("Mã nhân viên       : " + hoaDon.getMaNV());
            writer.println("Ngày lập hóa đơn   : " + hoaDon.getNgayLapHD().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            writer.println("Phương thức TT     : " + hoaDon.getPhuongThucTT());
            writer.println("────────────────────────────────────────────────────────");
            writer.println("Tổng tiền          : " + FormatUtil.formatVND(hoaDon.getTongTien()));
            writer.println("Tiền khách đưa     : " + FormatUtil.formatVND(hoaDon.getTienKhachDua()));
            writer.println("Tiền thừa          : " + FormatUtil.formatVND(hoaDon.getTienThua()));
            writer.println("════════════════════════════════════════════════════════");
            
            System.out.println("✅ Xuất hóa đơn thành công! File: " + fileName);
        } catch (IOException e) {
            System.out.println("❌ Lỗi khi xuất hóa đơn: " + e.getMessage());
        }
    }

    public void xuatChiTietHoaDonTheoMaHD() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn muốn in chi tiết: ");
        String maHD = scanner.nextLine().trim();

        List<ChiTietHoaDonDTO> chiTietHoaDon = ChiTietHoaDonDAO.timChiTietHoaDon(maHD);
        
        if (chiTietHoaDon.isEmpty()) {
            System.out.println("❌ Không tìm thấy chi tiết hóa đơn với mã: " + maHD);
            return;
        }

        String fileName = "ChiTietHoaDon_" + maHD + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("════════════════════════════════════════════════════════");
            writer.println("                 CHI TIẾT HÓA ĐƠN                      ");
            writer.println("════════════════════════════════════════════════════════");
            writer.println("Mã hóa đơn: " + maHD);
            writer.println("────────────────────────────────────────────────────────");
            writer.printf("%-10s %-20s %-10s %-15s %-15s%n",
                "Mã hàng", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền");
            writer.println("────────────────────────────────────────────────────────");
            
            for (ChiTietHoaDonDTO ctHoaDon : chiTietHoaDon) {
                writer.printf("%-10s %-20s %-10d %-15s %-15s%n",
                    ctHoaDon.getMaHang(),
                    ctHoaDon.getTenSP(),
                    ctHoaDon.getSoLuong(),
                    FormatUtil.formatVND(ctHoaDon.getDonGia()),
                    FormatUtil.formatVND(ctHoaDon.getThanhTien()));
            }
            writer.println("════════════════════════════════════════════════════════");
            
            System.out.println("✅ Xuất chi tiết hóa đơn thành công! File: " + fileName);
        } catch (IOException e) {
            System.out.println("❌ Lỗi khi xuất chi tiết hóa đơn: " + e.getMessage());
        }
    }

    public void xuatHoaDonKemChiTietHoaDonTheoMaHD() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn muốn in: ");
        String maHD = scanner.nextLine().trim();

        HoaDonDTO hoaDon = HoaDonDAO.timHoaDon(maHD);
        if (hoaDon == null) {
            System.out.println("❌ Không tìm thấy hóa đơn với mã: " + maHD);
            return;
        }
        
        List<ChiTietHoaDonDTO> chiTietHoaDon = ChiTietHoaDonDAO.timChiTietHoaDon(maHD);

        String fileName = "HoaDonChiTiet_" + maHD + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("════════════════════════════════════════════════════════");
            writer.println("                    ABC STORE                          ");
            writer.println("            123 An Dương Vương, Q5, TP.HCM            ");
            writer.println("               Điện thoại: 0909090909                  ");
            writer.println("════════════════════════════════════════════════════════");
            writer.println("                  HÓA ĐƠN BÁN HÀNG                     ");
            writer.println("════════════════════════════════════════════════════════");
            writer.println("Mã hóa đơn         : " + hoaDon.getMaHD());
            writer.println("Ngày lập           : " + hoaDon.getNgayLapHD().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            writer.println("Khách hàng         : " + hoaDon.getMaKH());
            writer.println("Nhân viên          : " + hoaDon.getMaNV());
            writer.println("Phương thức TT     : " + hoaDon.getPhuongThucTT());
            writer.println("────────────────────────────────────────────────────────");
            writer.println("                   CHI TIẾT HÓA ĐƠN                    ");
            writer.println("────────────────────────────────────────────────────────");

            writer.printf("%-5s %-20s %-8s %-15s %-15s%n",
                "STT", "Tên SP", "SL", "Đơn giá", "Thành tiền");
            writer.println("────────────────────────────────────────────────────────");

            int stt = 1;
            for (ChiTietHoaDonDTO ctHoaDon : chiTietHoaDon) {
                writer.printf("%-5d %-20s %-8d %-15s %-15s%n",
                    stt++,
                    ctHoaDon.getTenSP(),
                    ctHoaDon.getSoLuong(),
                    FormatUtil.formatVND(ctHoaDon.getDonGia()),
                    FormatUtil.formatVND(ctHoaDon.getThanhTien()));
            }

            writer.println("────────────────────────────────────────────────────────");
            writer.println("Tổng tiền          : " + FormatUtil.formatVND(hoaDon.getTongTien()));
            writer.println("Tiền khách đưa     : " + FormatUtil.formatVND(hoaDon.getTienKhachDua()));
            writer.println("Tiền thừa          : " + FormatUtil.formatVND(hoaDon.getTienThua()));
            writer.println("════════════════════════════════════════════════════════");
            writer.println("           Cảm ơn quý khách! Hẹn gặp lại!             ");
            writer.println("════════════════════════════════════════════════════════");
            
            System.out.println("✅ Xuất hóa đơn kèm chi tiết thành công! File: " + fileName);
        } catch (IOException e) {
            System.out.println("❌ Lỗi khi xuất hóa đơn kèm chi tiết: " + e.getMessage());
        }
    }
}