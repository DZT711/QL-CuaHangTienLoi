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
import dto.SanPhamDTO;
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
                    huyHoaDon();
                    break;
                case 3:
                    System.out.println("\n");
                    System.out.println(
                            "    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                    System.out.println(
                            "    ┃                         TÌM KIẾM HÓA ĐƠN                           ┃");
                    System.out.println(
                            "    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    System.out.println(
                            "    ┃ [1] ➜ Tìm kiếm hóa đơn theo mã hóa đơn                             ┃");
                    System.out.println(
                            "    ┃ [2] ➜ Tìm kiếm hóa đơn theo mã khách hàng                          ┃");
                    System.out.println(
                            "    ┃ [3] ➜ Tìm kiếm hóa đơn theo mã nhân viên                           ┃");
                    System.out.println(
                            "    ┃ [4] ➜ Tìm kiếm hóa đơn theo ngày lập                               ┃");
                    System.out.println(
                            "    ┃ [0] ➜ Thoát                                                        ┃");
                    System.out.println(
                            "    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                    while (true) {
                        String opt = scanner.nextLine().trim();

                        switch (opt) {
                            case "0":
                                System.out.println("Thoát tìm kiếm hóa đơn thành công.");
                                break;
                            case "1":
                                timHDTheoMaHD();
                                continue;
                            case "2":
                                timHDTheoMaKH();
                                continue;
                            case "3":
                                timHDTheoMaNV();
                                continue;
                            case "4":
                                timHoaDonTheoNgay();
                                continue;
                            default:
                                System.out.print("Lựa chọn không hợp lệ. Vui lòng nhập lại: ");
                                continue;
                        }
                        break;
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
        DateTimeFormatter displayFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            try {
                // tìm khách hàng theo số điện thoại
                System.out.println("Nhập số điện thoại khách hàng: ");
                String sdt = scanner.nextLine().trim();

                if (sdt.isEmpty()) {
                    System.out.println("❌ Số điện thoại không được để trống!");
                    continue;
                }

                // thêm khách hàng mới nếu chưa có
                KhachHangDTO kh = KhachHangDAO.timKhachHangTheoDienThoai(sdt);
                if (kh == null) {
                    System.out.println("⚠️ Khách hàng chưa có trong hệ thống.");
                    System.out.println("📝 Nhập thông tin khách hàng mới:\n");

                    String maKH = KhachHangDAO.generateIDKhachHang();

                    System.out.print("Nhập họ khách hàng: ");
                    String ho = scanner.nextLine().trim();
                    while (ho.isEmpty()) {
                        System.out.println("❌ Họ không được để trống!");
                        System.out.print("Nhập họ khách hàng: ");
                        ho = scanner.nextLine().trim();
                    }

                    System.out.print("Nhập tên khách hàng: ");
                    String ten = scanner.nextLine().trim();
                    while (ten.isEmpty()) {
                        System.out.println("❌ Tên không được để trống!");
                        System.out.print("Nhập tên khách hàng: ");
                        ten = scanner.nextLine().trim();
                    }

                    KhachHangDTO customer = new KhachHangDTO();
                    customer.setMaKH(maKH);
                    customer.setHo(ho);
                    customer.setTen(ten);
                    customer.setDienThoai(sdt);   

                    if (KhachHangDAO.themKhachHang(customer)) {
                        System.out.println("✅ Thêm khách hàng mới thành công!");
                        kh = customer;
                    } else {
                        System.out.println("❌ Lỗi khi thêm khách hàng! Vui lòng thử lại.");
                        continue;
                    }
                }
                else System.out.println("✅ Tìm thấy: " + kh.getHo() + " " + kh.getTen());
                
                String maHD = HoaDonDAO.generateIDHoaDon();
                String maNV = Main.CURRENT_ACCOUNT.getMaNV();

                // thêm sản phẩm vào chi tiết hóa đơn
                List<ChiTietHoaDonDTO> chiTietHoaDon = new ArrayList<>();
                int tongTien = 0;
                
                System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
                System.out.println("║                    📦 THÊM SẢN PHẨM VÀO HÓA ĐƠN                ║");
                System.out.println("╚════════════════════════════════════════════════════════════════╝");

                while (true) {
                    System.out.print("Nhập mã hàng hóa: ");
                    String maHang = scanner.nextLine().trim();
                    if ("0".equals(maHang)) break;
                    
                    if (maHang.isEmpty()) {
                        System.out.println("❌ Mã hàng không được để trống!");
                        continue;
                    }

                    // Kiểm tra hàng hóa tồn tại
                    HangHoaDTO hangHoa = HangHoaDAO.timHangHoaTheoMa(maHang);
                    if (hangHoa == null) {
                        System.out.println("❌ Mã hàng không tồn tại! Vui lòng nhập lại.\n");
                        continue;
                    }

                    // Chặn bán nếu lô hàng không được phép bán (inactive hoặc expired)
                    String trangThai = hangHoa.getTrangThai();
                    if ("inactive".equalsIgnoreCase(trangThai)) {
                        System.out.println("❌ LÔ HÀNG KHÔNG ĐƯỢC PHÉP BÁN (Inactive). Vui lòng chọn lô khác.\n");
                        continue;
                    }

                    LocalDate hanSuDung = hangHoa.getHanSuDung();
                    if ("expired".equalsIgnoreCase(trangThai) || 
                        (hanSuDung != null && hanSuDung.isBefore(LocalDate.now()))) {
                        System.out.println("╔════════════════════════════════════════════════════════╗");
                        System.out.println("║    ❌ KHÔNG THỂ BÁN - LÔ HÀNG ĐÃ HẾT HẠN!            ║");
                        System.out.println("╚════════════════════════════════════════════════════════╝");
                        System.out.println("📦 Mã hàng: " + maHang);
                        if (hanSuDung != null) {
                            System.out.println("📅 HSD: " + hanSuDung.format(displayFmt));
                        }
                        System.out.println("👉 Vui lòng chọn lô khác.\n");
                        continue;
                    }

                    // Lấy thông tin sản phẩm từ HANGHOA
                    String maSP = hangHoa.getMaSP();
                    SanPhamDTO sp = SanPhamDAO.timSanPhamTheoMa(maSP);
                    if (sp == null) {
                        System.out.println("❌ Lỗi: Không tìm thấy thông tin sản phẩm!\n");
                        continue;
                    }

                    System.out.println("\n📦 Sản phẩm: " + sp.getTenSP());
                    System.out.println("💰 Giá bán: " + FormatUtil.formatVND(sp.getGiaBan()));
                    System.out.println("📊 Tồn kho lô này: " + hangHoa.getSoLuongConLai());

                    // nhập só lượng 
                    int soLuong = -1;
                    while (true) {
                        System.out.print("Nhập số lượng (hoặc '0' để bỏ qua): ");
                        String slInput = scanner.nextLine().trim();
                        
                        if ("0".equals(slInput)) {
                            soLuong = -1;
                            break;
                        }
                        
                        try {
                            soLuong = Integer.parseInt(slInput);
                            
                            if (soLuong <= 0) {
                                System.out.println("❌ Số lượng phải lớn hơn 0!");
                                continue;
                            }
                            
                            if (soLuong > hangHoa.getSoLuongConLai()) {
                                System.out.println("❌ Số lượng vượt quá tồn kho lô này (còn " + hangHoa.getSoLuongConLai() + ")");
                                continue;
                            }
                            
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("❌ Số lượng không hợp lệ! Vui lòng nhập số nguyên.");
                        }
                    }

                    if (soLuong == -1) continue;

                    int donGia = sp.getGiaBan();
                    int thanhTien = soLuong * donGia;
                    chiTietHoaDon.add(new ChiTietHoaDonDTO(maHD, maHang, sp.getTenSP(), soLuong, donGia, thanhTien));
                    tongTien += thanhTien;
                }
                
                // kiểm tra hóa đơn rỗng 
                if (chiTietHoaDon.isEmpty()) {
                    System.out.println("⚠️ Hóa đơn không có sản phẩm nào! Hủy tạo hóa đơn.\n");
                    continue;
                }

                // thanh toán
                HoaDonDTO hoaDon = new HoaDonDTO();
                hoaDon.setMaHD(maHD);
                hoaDon.setMaKH(kh.getMaKH());
                hoaDon.setMaNV(maNV);
                hoaDon.setTongTien(tongTien);
                hoaDon.setNgayLapHD(LocalDateTime.now());

                System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
                System.out.println("║                     💳 PHƯƠNG THỨC THANH TOÁN                  ║");
                System.out.println("╚════════════════════════════════════════════════════════════════╝");
                System.out.println("💰 Tổng tiền: " + FormatUtil.formatVND(tongTien));
                System.out.println("\n1. Tiền mặt");
                System.out.println("2. Chuyển khoản");
                System.out.println("0. Hủy");

                boolean thanhToanThanhCong = false;
                while (true) {
                    System.out.print("\n💡 Lựa chọn của bạn: ");
                    String ptttInput = scanner.nextLine().trim();

                    if ("0".equals(ptttInput)) {
                        System.out.println("❌ Hủy thanh toán!\n");
                        thanhToanThanhCong = false;
                        break;
                    } else if ("1".equals(ptttInput)) {
                        while (true) {
                            System.out.print("💵 Nhập tiền khách đưa: ");
                            String tienInput = scanner.nextLine().trim();
                            
                            try {
                                int tienKhachDua = Integer.parseInt(tienInput);
                                
                                if (tienKhachDua < tongTien) {
                                    System.out.println("❌ Tiền khách đưa không đủ! Còn thiếu: " + 
                                        FormatUtil.formatVND(tongTien - tienKhachDua));
                                    continue;
                                }
                                
                                hoaDon.setPhuongThucTT("Tiền mặt");
                                hoaDon.setTienKhachDua(tienKhachDua);
                                hoaDon.setTienThua(tienKhachDua - tongTien);
                                
                                System.out.println("✅ Tiền thừa: " + FormatUtil.formatVND(tienKhachDua - tongTien));
                                thanhToanThanhCong = true;
                                break;
                                
                            } catch (NumberFormatException e) {
                                System.out.println("❌ Số tiền không hợp lệ!");
                            }
                        }
                        break;
                    } else if ("2".equals(ptttInput)) {
                        hoaDon.setPhuongThucTT("Chuyển khoản");
                        hoaDon.setTienKhachDua(tongTien);
                        hoaDon.setTienThua(0);
                        System.out.println("✅ Chuyển khoản: " + FormatUtil.formatVND(tongTien));
                        thanhToanThanhCong = true;
                        break;
                    } else {
                        System.out.println("❌ Lựa chọn không hợp lệ!");
                    }
                }

                if (!thanhToanThanhCong) continue;

                // lưu hóa đơn và cập nhật tồn kho
                boolean luuThanhCong = true;

                if (!HoaDonDAO.themHoaDon(hoaDon)) {
                    System.err.println("❌ Lỗi khi thêm hóa đơn!");
                    luuThanhCong = false;
                }

                if (luuThanhCong) {
                    for (ChiTietHoaDonDTO ctHoaDon : chiTietHoaDon) {
                        if (!ChiTietHoaDonDAO.themChiTietHoaDon(ctHoaDon)) {
                            System.err.println("❌ Lỗi khi thêm chi tiết hóa đơn!");
                            luuThanhCong = false;
                            break;
                        }
                        
                    
                        String maHangCT = ctHoaDon.getMaHang();
                        HangHoaDTO hh = HangHoaDAO.timHangHoaTheoMa(maHangCT);
                        if (hh != null) {
                            if (!HangHoaDAO.truSoLuongConLai(maHangCT, ctHoaDon.getSoLuong())) {
                                System.err.println("❌ Lỗi khi trừ số lượng lô hàng!");
                                luuThanhCong = false;
                                break;
                            }
                            if (!SanPhamDAO.truSoLuongTon(hh.getMaSP(), ctHoaDon.getSoLuong())) {
                                System.err.println("❌ Lỗi khi trừ số lượng tồn!");
                                luuThanhCong = false;
                                break;
                            }
                        }
                    }
                }

                if (!luuThanhCong) {
                    System.out.println("❌ Lỗi khi lưu hóa đơn! Vui lòng thử lại.\n");
                    continue;
                }

                System.out.println("\n✅ Thêm hóa đơn thành công!");
                HoaDonDTO hoaDonMoi = HoaDonDAO.timHoaDon(maHD);
                if (hoaDonMoi != null) {
                    inHoaDon(hoaDonMoi);
                }

                System.out.print("\n💡 Bạn có muốn tạo hóa đơn khác? (y/n): ");
                String choice = scanner.nextLine().trim();
                if (!"y".equalsIgnoreCase(choice)) {
                    System.out.println("✅ Hoàn tất tạo hóa đơn.");
                    break;
                }
            } catch (Exception e) {
                System.out.println("❌ Lỗi: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void timHDTheoMaHD() {
        Scanner scanner = new Scanner(System.in);        
        while (true) {
            System.out.print("\n🔍 Nhập mã hóa đơn cần tìm (hoặc '0' để thoát): ");
            String maHD = scanner.nextLine().trim();
            
            if ("0".equals(maHD)) {
                System.out.println("✅ Thoát chức năng tìm hóa đơn.");
                break;
            }
            
            if (maHD.isEmpty()) {
                System.out.println("❌ Mã hóa đơn không được để trống!");
                continue;
            }
            
            HoaDonDTO hd = HoaDonDAO.timHoaDon(maHD);
            
            if (hd != null) {
                System.out.println("✅ Tìm thấy hóa đơn: " + maHD);
                
                // Hiển thị trạng thái hóa đơn trước khi in
                String trangThai = hd.getTrangThai();
                if ("cancelled".equalsIgnoreCase(trangThai)) {
                    System.out.println("⚠️ CHÚ Ý: Hóa đơn này đã bị HỦY!");
                    System.out.println("────────────────────────────────────────────────────────────────");
                }
                
                inHoaDon(hd);
            } else {
                System.out.println("❌ Không tìm thấy hóa đơn với mã: " + maHD);
            }
            
            System.out.print("\n💡 Bạn có muốn tìm hóa đơn khác? (y/n): ");
            String choice = scanner.nextLine().trim();
            if (!"y".equalsIgnoreCase(choice)) {
                System.out.println("✅ Hoàn tất chức năng tìm hóa đơn.");
                break;
            }
        }
    }

    public void timHDTheoMaKH() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        while (true) {
            System.out.print("\n🔍 Nhập mã khách hàng để tìm hóa đơn (hoặc '0' để thoát): ");
            String maKH = scanner.nextLine().trim();

            if ("0".equals(maKH)) {
                System.out.println("✅ Thoát chức năng tìm hóa đơn theo khách hàng.");
                break;
            }
            
            if (maKH.isEmpty()) {
                System.out.println("❌ Mã khách hàng không được để trống!");
                continue;
            }

            KhachHangDTO kh = KhachHangDAO.timKhachHangTheoMa(maKH);
            if (kh == null) {
                System.out.println("❌ Không tìm thấy khách hàng với mã: " + maKH + "\n");
                continue;
            }

            System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
            System.out.println("║                     👤 THÔNG TIN KHÁCH HÀNG                    ║");
            System.out.println("╚════════════════════════════════════════════════════════════════╝");
            System.out.println("Mã KH              : " + kh.getMaKH());
            System.out.println("Họ tên             : " + kh.getHo() + " " + kh.getTen());
            System.out.println("Điện thoại         : " + kh.getDienThoai());
            if (kh.getDiaChi() != null && !kh.getDiaChi().isEmpty()) {
                System.out.println("Địa chỉ            : " + kh.getDiaChi());
            }
            System.out.println("────────────────────────────────────────────────────────────────");

            System.out.print("💡 Hiển thị cả hóa đơn đã hủy? (Y/N): ");
            String showCancelled = scanner.nextLine().trim();
            boolean baoGomHuy = "Y".equalsIgnoreCase(showCancelled);

            List<Map<String, Object>> danhSachHD = HoaDonDAO.timHoaDonTheoMaKH(maKH, baoGomHuy);
        
            if (danhSachHD.isEmpty()) {
                System.out.println("\n⚠️ Khách hàng này chưa có hóa đơn nào.\n");
                continue;
            }

            System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                    📋 DANH SÁCH HÓA ĐƠN                                ║");
            System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════╝");
            System.out.printf("%-12s %-20s %-20s %-15s %-15s %-12s%n",
                "Mã HD", "Ngày lập", "Nhân viên", "Tổng tiền", "PT thanh toán", "Trạng thái");
            System.out.println("────────────────────────────────────────────────────────────────────────────────────────");
            
            int tongTien = 0;
            for (Map<String, Object> hd : danhSachHD) {
                String maHD = (String) hd.get("MaHD");
                LocalDateTime thoiGian = (LocalDateTime) hd.get("ThoiGianLapHD");
                String hoNV = (String) hd.get("HoNV");
                String tenNV = (String) hd.get("TenNV");
                Integer tt = (Integer) hd.get("TongTien");
                String pttt = (String) hd.get("PhuongThucTT");
                String trangThai = (String) hd.get("TrangThai");
                
                String trangThaiIcon = "active".equals(trangThai) ? "✅" : "❌ Hủy";
                
                System.out.printf("%-12s %-20s %-20s %-15s %-15s %-12s%n",
                    maHD,
                    thoiGian.format(fmt),
                    (hoNV + " " + tenNV).length() > 20 ? 
                        (hoNV + " " + tenNV).substring(0, 17) + "..." : (hoNV + " " + tenNV),
                    FormatUtil.formatVND(tt != null ? tt : 0),
                    pttt,
                    trangThaiIcon
                );
                
                if ("active".equals(trangThai)) {
                    tongTien += (tt != null ? tt : 0);
                }
            }
            System.out.println("════════════════════════════════════════════════════════════════════════════════════════");
            System.out.printf("📊 Tổng cộng: %d hóa đơn | Tổng giá trị hóa đơn hợp lệ: %s%n", 
                danhSachHD.size(), FormatUtil.formatVND(tongTien));
            System.out.println();

            while (true) {
                System.out.print("💡 Bạn có muốn xem chi tiết hóa đơn nào không? (Y/N): ");
                String xemChiTiet = scanner.nextLine().trim();
                
                if (!"Y".equalsIgnoreCase(xemChiTiet)) break;
                
                
                System.out.print("Nhập mã hóa đơn cần xem chi tiết: ");
                String maHD = scanner.nextLine().trim();
                
                if (maHD.isEmpty()) {
                    System.out.println("❌ Mã hóa đơn không được để trống!");
                    continue;
                }
                
                HoaDonDTO hoaDon = HoaDonDAO.timHoaDon(maHD);
                if (hoaDon != null) {
                    if (!maKH.equals(hoaDon.getMaKH())) {
                        System.out.println("⚠️ Hóa đơn này không thuộc khách hàng " + maKH + "!");
                    } else {
                        inHoaDon(hoaDon);
                    }
                } else {
                    System.out.println("❌ Không tìm thấy hóa đơn với mã: " + maHD);
                }
            }
            
            System.out.print("\n💡 Bạn có muốn tìm hóa đơn của khách hàng khác? (y/n): ");
            String choice = scanner.nextLine().trim();
            if (!"y".equalsIgnoreCase(choice)) {
                System.out.println("✅ Hoàn tất chức năng tìm hóa đơn theo khách hàng.");
                break;
            }
        }
    }

    public void timHDTheoMaNV() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        
        while (true) {
            System.out.print("\n🔍 Nhập mã nhân viên cần tìm (hoặc '0' để thoát): ");
            String maNV = scanner.nextLine().trim();
            
            if ("0".equals(maNV)) {
                System.out.println("✅ Thoát chức năng tìm hóa đơn theo nhân viên.");
                break;
            }
            
            if (maNV.isEmpty()) {
                System.out.println("❌ Mã nhân viên không được để trống!");
                continue;
            }
            
            NhanVienDTO nv = NhanVienDAO.timNhanVienTheoMa(maNV);
            if (nv == null) {
                System.out.println("❌ Không tìm thấy nhân viên với mã: " + maNV + "\n");
                continue;
            }
            
            System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
            System.out.println("║                    👤 THÔNG TIN NHÂN VIÊN                      ║");
            System.out.println("╚════════════════════════════════════════════════════════════════╝");
            System.out.println("Mã NV              : " + nv.getMaNV());
            System.out.println("Họ tên             : " + nv.getHo() + " " + nv.getTen());
            System.out.println("Chức vụ            : " + nv.getChucVu());
            System.out.println("Email              : " + (nv.getEmail() != null ? nv.getEmail() : "N/A"));
            
            String trangThaiNV = nv.getTrangThai();
            String trangThaiIcon = "active".equalsIgnoreCase(trangThaiNV) ? "✅ Đang làm việc" : "⚠️ Đã nghỉ việc";
            System.out.println("Trạng thái         : " + trangThaiIcon);
            System.out.println("────────────────────────────────────────────────────────────────");
            
            System.out.print("💡 Hiển thị cả hóa đơn đã hủy? (Y/N): ");
            String showCancelled = scanner.nextLine().trim();
            boolean baoGomHuy = "Y".equalsIgnoreCase(showCancelled);
            
            List<Map<String, Object>> danhSachHD = HoaDonDAO.timHoaDonTheoMaNV(maNV, baoGomHuy);
            
            if (danhSachHD.isEmpty()) {
                System.out.println("\n⚠️ Nhân viên này chưa lập hóa đơn nào.\n");
                continue;
            }
            
            System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                         📋 DANH SÁCH HÓA ĐƠN DO NHÂN VIÊN LẬP                          ║");
            System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════╝");
            System.out.printf("%-12s %-12s %-20s %-15s %-15s %-12s%n",
                "Mã HD", "Mã KH", "Ngày lập", "Tổng tiền", "PT thanh toán", "Trạng thái");
            System.out.println("────────────────────────────────────────────────────────────────────────────────────────");
            
            int tongTien = 0;
            for (Map<String, Object> hd : danhSachHD) {
                String maHD = (String) hd.get("MaHD");
                String maKH = (String) hd.get("MaKH");
                LocalDateTime thoiGian = (LocalDateTime) hd.get("ThoiGianLapHD");
                Integer tt = (Integer) hd.get("TongTien");
                String pttt = (String) hd.get("PhuongThucTT");
                String trangThai = (String) hd.get("TrangThai");
                
                String trangThaiIconn = "active".equals(trangThai) ? "✅" : "❌ Hủy";
                
                System.out.printf("%-12s %-12s %-20s %-15s %-15s %-12s%n",
                    maHD,
                    maKH,
                    thoiGian.format(fmt),
                    FormatUtil.formatVND(tt != null ? tt : 0),
                    pttt,
                    trangThaiIconn
                );
                
                if ("active".equals(trangThai)) {
                    tongTien += (tt != null ? tt : 0);
                }
            }
            
            System.out.println("════════════════════════════════════════════════════════════════════════════════════════");
            System.out.printf("📊 Tổng cộng: %d hóa đơn | Tổng giá trị hóa đơn hợp lệ: %s%n", 
                danhSachHD.size(), FormatUtil.formatVND(tongTien));
            System.out.println();
            
            while (true) {
                System.out.print("💡 Bạn có muốn xem chi tiết hóa đơn nào không? (Y/N): ");
                String xemChiTiet = scanner.nextLine().trim();
                
                if (!"Y".equalsIgnoreCase(xemChiTiet)) {
                    break;
                }
                
                System.out.print("Nhập mã hóa đơn cần xem chi tiết: ");
                String maHD = scanner.nextLine().trim();
                
                if (maHD.isEmpty()) {
                    System.out.println("❌ Mã hóa đơn không được để trống!");
                    continue;
                }
                
                HoaDonDTO hoaDon = HoaDonDAO.timHoaDon(maHD);
                if (hoaDon != null) {
                    if (!maNV.equals(hoaDon.getMaNV())) {
                        System.out.println("⚠️ Hóa đơn này không do nhân viên " + maNV + " lập!");
                    } else {
                        inHoaDon(hoaDon);
                    }
                } else {
                    System.out.println("❌ Không tìm thấy hóa đơn với mã: " + maHD);
                }
            }
            
            System.out.print("\n💡 Bạn có muốn tìm hóa đơn của nhân viên khác? (y/n): ");
            String choice = scanner.nextLine().trim();
            if (!"y".equalsIgnoreCase(choice)) {
                System.out.println("✅ Hoàn tất chức năng tìm hóa đơn theo nhân viên.");
                break;
            }
        }
    }

    public void inHoaDon(HoaDonDTO hoaDon) {
        if (hoaDon == null) {
            System.out.println("❌ Không có thông tin hóa đơn!");
            return;
        }
        
        List<ChiTietHoaDonDTO> chiTietHoaDon = ChiTietHoaDonDAO.timChiTietHoaDon(hoaDon.getMaHD());
        
        String trangThai = hoaDon.getTrangThai();
        boolean isHuy = "cancelled".equalsIgnoreCase(trangThai);
        
        System.out.println("\n════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("                                  ABC STORE                                         ");
        System.out.println("                      123 An Dương Vương, Q5, TP.HCM                               ");
        System.out.println("                         Điện thoại: 0909090909                                     ");
        System.out.println("════════════════════════════════════════════════════════════════════════════════════");
        
        if (isHuy) {
            System.out.println("                      ❌ HÓA ĐƠN ĐÃ HỦY - CHỈ ĐỂ THAM KHẢO ❌                     ");
        } else {
            System.out.println("                            HÓA ĐƠN BÁN HÀNG                                        ");
        }
        
        System.out.println("════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("Mã hóa đơn         : " + hoaDon.getMaHD());
        System.out.println("Ngày lập           : " + 
            hoaDon.getNgayLapHD().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.println("Khách hàng         : " + hoaDon.getMaKH());
        System.out.println("Nhân viên          : " + hoaDon.getMaNV());
        System.out.println("Phương thức TT     : " + hoaDon.getPhuongThucTT());
        
        String trangThaiDisplay = isHuy ? "❌ Đã hủy" : "✅ Hợp lệ";
        System.out.println("Trạng thái         : " + trangThaiDisplay);
        
        System.out.println("────────────────────────────────────────────────────────────────────────────────────");
        System.out.println("                             CHI TIẾT HÓA ĐƠN                                       ");
        System.out.println("────────────────────────────────────────────────────────────────────────────────────");

        if (chiTietHoaDon == null || chiTietHoaDon.isEmpty()) {
            System.out.println("⚠️ Hóa đơn không có sản phẩm nào!");
        } else {
            System.out.printf("%-12s %-28s %8s %15s %15s%n",
                "Mã hàng", "Tên sản phẩm", "SL", "Đơn giá", "Thành tiền");
            System.out.println("────────────────────────────────────────────────────────────────────────────────────");
            
            for (ChiTietHoaDonDTO ctHoaDon : chiTietHoaDon) {
                ctHoaDon.inChiTietHoaDon();
            }
        }
        
        System.out.println("════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("Tổng tiền          : " + FormatUtil.formatVND(hoaDon.getTongTien()));
        System.out.println("Tiền khách đưa     : " + FormatUtil.formatVND(hoaDon.getTienKhachDua()));
        System.out.println("Tiền thừa          : " + FormatUtil.formatVND(hoaDon.getTienThua()));
        System.out.println("════════════════════════════════════════════════════════════════════════════════════");
        
        if (isHuy) {
            System.out.println("        ⚠️ HÓA ĐƠN NÀY ĐÃ BỊ HỦY - KHÔNG CÒN HIỆU LỰC ⚠️                        ");
        } else {
            System.out.println("                     Cảm ơn quý khách! Hẹn gặp lại!                                ");
        }
        
        System.out.println("════════════════════════════════════════════════════════════════════════════════════\n");
    }

    public void huyHoaDon() { 
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        
        while (true) {
            try {
                System.out.print("\n🗑️ Nhập mã hóa đơn cần hủy (hoặc '0' để thoát): ");
                String maHD = scanner.nextLine().trim();
                
                if ("0".equals(maHD)) {
                    System.out.println("✅ Thoát chức năng hủy hóa đơn.");
                    break;
                }
                
                if (maHD.isEmpty()) {
                    System.out.println("❌ Mã hóa đơn không được để trống!");
                    continue;
                }
                
                // kiểm tra hóa đơn tồn tại
                HoaDonDTO hoaDon = HoaDonDAO.timHoaDon(maHD);
                if (hoaDon == null) {
                    System.out.println("❌ Mã hóa đơn không tồn tại! Vui lòng nhập lại.\n");
                    continue;
                }
                
                // kiểm tra hóa đơn đã hủy chưa
                if ("cancelled".equalsIgnoreCase(hoaDon.getTrangThai())) {
                    System.out.println("⚠️ Hóa đơn này đã bị hủy trước đó!\n");
                    continue;
                }
                
                // hiển thị thông tin hóa đơn
                System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
                System.out.println("║                📋 THÔNG TIN HÓA ĐƠN CẦN HỦY                    ║");
                System.out.println("╚════════════════════════════════════════════════════════════════╝");
                System.out.println("Mã hóa đơn         : " + hoaDon.getMaHD());
                System.out.println("Ngày lập           : " + hoaDon.getNgayLapHD().format(fmt));
                System.out.println("Khách hàng         : " + hoaDon.getMaKH());
                System.out.println("Nhân viên          : " + hoaDon.getMaNV());
                System.out.println("Tổng tiền          : " + FormatUtil.formatVND(hoaDon.getTongTien()));
                System.out.println("Phương thức TT     : " + hoaDon.getPhuongThucTT());
                System.out.println("Trạng thái         : ✅ " + hoaDon.getTrangThai());
                System.out.println("────────────────────────────────────────────────────────────────");
                
                // chi tiết sản phẩm
                List<ChiTietHoaDonDTO> chiTietList = ChiTietHoaDonDAO.timChiTietHoaDon(maHD);
                if (chiTietList != null && !chiTietList.isEmpty()) {
                    System.out.println("\n📦 Chi tiết sản phẩm:");
                    for (ChiTietHoaDonDTO ct : chiTietList) {
                        System.out.printf("  • %s - SL: %d - Đơn giá: %s%n",
                            ct.getTenSP(),
                            ct.getSoLuong(),
                            FormatUtil.formatVND(ct.getDonGia())
                        );
                    }
                }
                
                System.out.println("\n⚠️ CẢNH BÁO: Hủy hóa đơn sẽ:");
                System.out.println("   1. Đánh dấu hóa đơn là 'cancelled' (không xóa khỏi DB)");
                System.out.println("   2. Hoàn lại số lượng hàng hóa vào kho");
                System.out.println("   3. Hóa đơn vẫn được lưu để audit/kiểm tra");
                System.out.print("\n❓ Bạn có chắc chắn muốn hủy? (Y/N): ");
                String confirm = scanner.nextLine().trim();
                
                if (!"Y".equalsIgnoreCase(confirm)) {
                    System.out.println("ℹ️ Đã hủy thao tác hủy hóa đơn.\n");
                    continue;
                }
                
                if (HoaDonDAO.huyHoaDon(maHD)) {
                    System.out.println("\n✅ Hủy hóa đơn thành công!");
                    System.out.println("ℹ️ Đã hoàn lại số lượng hàng hóa vào kho.");
                    System.out.println("ℹ️ Hóa đơn vẫn được lưu trong hệ thống (trạng thái: cancelled).\n");
                } else {
                    System.out.println("❌ Hủy hóa đơn thất bại!\n");
                }
                
                System.out.print("💡 Bạn có muốn hủy hóa đơn khác? (y/n): ");
                String choice = scanner.nextLine().trim();
                if (!"y".equalsIgnoreCase(choice)) {
                    System.out.println("✅ Hoàn tất chức năng hủy hóa đơn.");
                    break;
                }
            } catch (Exception e) {
                System.out.println("❌ Lỗi không xác định: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    public void timHoaDonTheoNgay() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter inputFmt = DateTimeFormatter.ofPattern("ddMMyyyy");
        DateTimeFormatter displayFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        while (true) {
            LocalDate fromDate = null, toDate = null;

            while (true) {
                try {
                    System.out.print("\n📅 Nhập ngày bắt đầu (ddMMyyyy) hoặc '0' để thoát: ");
                    String from = scanner.nextLine().trim();
                    
                    if ("0".equals(from)) {
                        System.out.println("✅ Thoát chức năng tìm hóa đơn theo ngày.");
                        return;
                    }
                    
                    if (from.isEmpty()) {
                        System.out.println("❌ Ngày không được để trống!");
                        continue;
                    }
    
                    fromDate = LocalDate.parse(from, inputFmt);
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("❌ Định dạng ngày không hợp lệ, vui lòng nhập lại (ddMMyyyy).");
                }
            }
            
            while (true) {
                try {
                    System.out.print("📅 Nhập ngày kết thúc (ddMMyyyy): ");
                    String to = scanner.nextLine().trim();
                    
                    if (to.isEmpty()) {
                        System.out.println("❌ Ngày không được để trống!");
                        continue;
                    }

                    toDate = LocalDate.parse(to, inputFmt);
                    
                    if (fromDate.isAfter(toDate)) {
                        System.out.println("❌ Ngày bắt đầu phải trước hoặc bằng ngày kết thúc!");
                        continue;
                    }
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("❌ Định dạng ngày không hợp lệ, vui lòng nhập lại (ddMMyyyy).");
                }
            }
            
            System.out.print("💡 Hiển thị cả hóa đơn đã hủy? (Y/N): ");
            String showCancelled = scanner.nextLine().trim();
            boolean baoGomHuy = "Y".equalsIgnoreCase(showCancelled);

            List<HoaDonDTO> list = HoaDonDAO.timHoaDonTheoNgayLap(fromDate, toDate, baoGomHuy);

            System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                             📅 DANH SÁCH HÓA ĐƠN THEO NGÀY                             ║");
            System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════╝");
            System.out.println("Từ ngày: " + fromDate.format(displayFmt) + " đến " + toDate.format(displayFmt));
            System.out.println("────────────────────────────────────────────────────────────────────────────────────────");

            if (list.isEmpty()) {
                System.out.println("⚠️ Không tìm thấy hóa đơn trong khoảng thời gian này.\n");
            } else {
                System.out.printf("%-12s %-20s %-12s %-12s %-15s %-15s %-12s%n",
                "Mã HD", "Ngày lập", "Mã KH", "Mã NV", "Tổng tiền", "PT thanh toán", "Trạng thái");
                System.out.println("────────────────────────────────────────────────────────────────────────────────────────");

                int tongTien = 0;
                for (HoaDonDTO hd : list) {
                    String trangThai = hd.getTrangThai();
                    String trangThaiIcon = "active".equals(trangThai) ? "✅" : "❌ Hủy";

                    System.out.printf("%-12s %-20s %-12s %-12s %-15s %-15s %-12s%n",
                        hd.getMaHD(),
                        hd.getNgayLapHD().format(timeFmt),
                        hd.getMaKH(),
                        hd.getMaNV(),
                        FormatUtil.formatVND(hd.getTongTien()),
                        hd.getPhuongThucTT(),
                        trangThaiIcon
                    );

                    if ("active".equals(trangThai)) {
                        tongTien += hd.getTongTien();
                    }
                }

                System.out.println("════════════════════════════════════════════════════════════════════════════════════════");
                System.out.printf("📊 Tổng cộng: %d hóa đơn | Tổng giá trị hóa đơn hợp lệ: %s%n", 
                    list.size(), FormatUtil.formatVND(tongTien));
                System.out.println();

                while (true) {
                    System.out.print("💡 Bạn có muốn xem chi tiết hóa đơn nào không? (Y/N): ");
                    String xemChiTiet = scanner.nextLine().trim();

                    if (!"Y".equalsIgnoreCase(xemChiTiet)) break;

                    System.out.print("Nhập mã hóa đơn cần xem chi tiết: ");
                    String maHD = scanner.nextLine().trim();

                    if (maHD.isEmpty()) {
                        System.out.println("❌ Mã hóa đơn không được để trống!");
                        continue;
                    }

                    HoaDonDTO hoaDon = HoaDonDAO.timHoaDon(maHD);
                    if (hoaDon != null) {
                        inHoaDon(hoaDon);
                    } else {
                        System.out.println("❌ Không tìm thấy hóa đơn với mã: " + maHD);
                    }
                }
            }

            System.out.print("\n💡 Bạn có muốn tìm tiếp không? (y/n): ");
            String choice = scanner.nextLine().trim();
            if (!"y".equalsIgnoreCase(choice)) {
                System.out.println("✅ Hoàn tất chức năng tìm hóa đơn theo ngày.");
                break;
            }
        }
    }

    // Làm lại giao diện cho giống thực tế, đẹp hơn
    public void xemDanhSachHoaDon() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        while (true) {
            System.out.print("\n💡 Hiển thị cả hóa đơn đã hủy? (Y/N): ");
            String showCancelled = scanner.nextLine().trim();
            boolean baoGomHuy = "Y".equalsIgnoreCase(showCancelled);

            List<HoaDonDTO> list = HoaDonDAO.getAllHoaDon(baoGomHuy);

            System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                             📋 DANH SÁCH TẤT CẢ HÓA ĐƠN                                ║");
            System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════╝");

            if (list.isEmpty()) {
                System.out.println("⚠️ Không có hóa đơn nào trong hệ thống.\n");
            } else {
                System.out.printf("%-12s %-20s %-12s %-12s %-15s %-15s %-12s%n",
                    "Mã HD", "Ngày lập", "Mã KH", "Mã NV", "Tổng tiền", "PT thanh toán", "Trạng thái");
                System.out.println("────────────────────────────────────────────────────────────────────────────────────────");

                int tongTien = 0;
                for (HoaDonDTO hd : list) {
                    String trangThai = hd.getTrangThai();
                    String trangThaiIcon = "active".equals(trangThai) ? "✅" : "❌ Hủy";

                    System.out.printf("%-12s %-20s %-12s %-12s %-15s %-15s %-12s%n",
                        hd.getMaHD(),
                        hd.getNgayLapHD().format(timeFmt),
                        hd.getMaKH(),
                        hd.getMaNV(),
                        FormatUtil.formatVND(hd.getTongTien()),
                        hd.getPhuongThucTT(),
                        trangThaiIcon
                    );

                    if ("active".equals(trangThai)) {
                        tongTien += hd.getTongTien();
                    }
                }

                System.out.println("════════════════════════════════════════════════════════════════════════════════════════");
                System.out.printf("📊 Tổng cộng: %d hóa đơn | Tổng giá trị hóa đơn hợp lệ: %s%n", 
                    list.size(), FormatUtil.formatVND(tongTien));
                System.out.println();

                while (true) {
                    System.out.print("💡 Bạn có muốn xem chi tiết hóa đơn nào không? (Y/N): ");
                    String xemChiTiet = scanner.nextLine().trim();

                    if (!"Y".equalsIgnoreCase(xemChiTiet)) {
                        break;
                    }

                    System.out.print("Nhập mã hóa đơn cần xem chi tiết: ");
                    String maHD = scanner.nextLine().trim();
                    
                    if (maHD.isEmpty()) {
                        System.out.println("❌ Mã hóa đơn không được để trống!");
                        continue;
                    }

                    HoaDonDTO hoaDon = HoaDonDAO.timHoaDon(maHD);
                    if (hoaDon != null) {
                        inHoaDon(hoaDon);
                    } else {
                        System.out.println("❌ Không tìm thấy hóa đơn với mã: " + maHD);
                    }
                }
            }

            System.out.print("\n💡 Bạn có muốn xem lại danh sách? (y/n): ");
            String choice = scanner.nextLine().trim();
            if (!"y".equalsIgnoreCase(choice)) {
                System.out.println("✅ Hoàn tất xem danh sách hóa đơn.");
                break;
            }
        }
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