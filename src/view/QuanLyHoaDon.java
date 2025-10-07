package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import dao.HoaDonDAO;
import dao.ChiTietHoaDonDAO;
import dao.NhanVienDAO;
import dto.ChiTietHoaDonDTO;
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
            System.out.println("▒ [7] ➜ Xuất hóa đơn                                                           ▒");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ");
            System.out.println("░ [0] ✗ Quay lại menu chính                                                    ░");
            System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ");
            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

            int choice = -1;

            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice > 0 && choice <= 7) {
                        break;
                    }
                    else if (choice == 0) {
                        return;
                    } else {
                        System.out.println("Vui lòng nhập số trong khoảng 0–7.");
                        System.out.print("\n💡 Nhập lựa chọn của bạn: ");
                    }  
                } else {
                    System.out.println("Vui lòng nhập số hợp lệ.");
                    scanner.next();
                    System.out.print("\n💡 Nhập lựa chọn của bạn: ");
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
                            } else if (opt == 1) {
                                System.out.println("Nhập mã hóa đơn cần tìm: ");
                                try {
                                    String maHD = scanner.nextLine().trim();
                                    scanner.nextLine();
                                    HoaDonDTO hd = HoaDonDAO.timHoaDon(maHD);
                                    if (hd != null) {
                                        System.out.println("Thông tin hóa đơn tìm thấy với mã: " + maHD);
                                        inHoaDon(maHD);
                                    } else {
                                        System.out.println("Không tìm thấy hóa đơn với mã: " + maHD);
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("Lỗi: Vui lòng nhập mã hóa đơn hợp lệ");
                                    scanner.nextLine();
                                }
                            } else if (opt == 2) {
                                System.out.println("Nhập mã khách hàng để tìm hóa đơn: ");
                                try {
                                    String maKH = scanner.nextLine().trim();
                                    scanner.nextLine();
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
                                                inHoaDon(maHD);
                                            } else {
                                                System.out.println("Không xem chi tiết hóa đơn nào.");
                                                break;
                                            }
                                        } while (tieptuc.equalsIgnoreCase("y"));
                                    } else {
                                        System.out.println("Không tìm thấy hóa đơn với mã: " + maKH);
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("Lỗi: Vui lòng nhập mã khách hàng hợp lệ");
                                    scanner.nextLine();
                                }
                            } else if (opt == 3) {
                                System.out.println("Nhập mã nhân viên cần tìm: ");
                                try {
                                    String maNV = scanner.nextLine().trim();
                                    scanner.nextLine();

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
                                                inHoaDon(maHD);
                                            } else {
                                                System.out.println("Không xem chi tiết hóa đơn nào.");
                                                break;
                                            }
                                        } while (tieptuc.equalsIgnoreCase("y"));
                                    } else {
                                        System.out.println("Không tìm thấy hóa đơn với mã nhân viên: " + maNV);
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("Lỗi: Vui lòng nhập mã nhân viên hợp lệ");
                                    scanner.nextLine();
                                }
                            } else if (opt == 4) {
                                timHoaDonTheoNgay();
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
                    // thongKeHoaDon();
                    break;
                case 6:
                    // xemDanhSachHoaDon();
                    break;
                case 7:
                    // xuatHoaDon();
                    break;
                case 0:
                    System.out.println("Thoát chương trình thành công!");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
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
                else System.out.println("Khách hàng đã tồn tại trong hệ thống.");
                
                String maHD = HoaDonDAO.generateIDHoaDon();
                String maNV = Main.CURRENT_ACCOUNT.getMaNV();

                List<ChiTietHoaDonDTO> chiTietHoaDon = new ArrayList<>();
                int tongTien = 0;
                while (true) {
                    System.out.println("Nhập mã sản phẩm ");
                    String maSP = scanner.nextLine().trim();
                    if (maSP.equals("0")) break;

                    sanPhamDTO sp = SanPhamDAO.timSanPhamTheoMa(maSP);
                    if (sp == null) {
                        System.out.println("Sản phẩm không tồn tại, vui lòng nhập lại.");
                        continue;
                    }


                    System.out.println("Nhập số lượng: ");
                    String slString = scanner.nextLine().trim();
                    int soLuong;
                    while (true) {
                        try {
                            soLuong = Integer.parseInt(slString);
                            if (soLuong <= 0) {
                                System.out.println("Số lượng phải lớn hơn 0, vui lòng nhập lại.");
                                continue;
                            }
                            if (soLuong > sp.getSoLuongTon()) {
                                System.out.println("Số lượng vượt quá số lượng tồn, vui lòng nhập lại.");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Số lượng không hợp lệ, vui lòng nhập lại.");
                            continue;
                        }
                    }

                    int donGia = sp.getGiaBan();
                    int thanhTien = soLuong * donGia;
                    chiTietHoaDon.add(new ChiTietHoaDonDTO(maHD, maSP, soLuong, donGia, thanhTien));

                    sp.setSoLuongTon(sp.getSoLuongTon() - soLuong);
                    SanPhamDAO.capnhatSoLuongTon(maSP, sp.getSoLuongTon());

                    tongTien += thanhTien;
                }
                
                HoaDonDTO hoaDon = new HoaDonDTO();
                
                while (true) {
                    System.out.println("Nhập phương thức thanh toán: ");
                    System.out.println("1. Tiền mặt");
                    System.out.println("2. Chuyển khoản");
                    System.out.println("Lựa chọn của bạn: ");

                    int choice = scanner.nextInt();

                    if (choice == 1) {
                        hoaDon.setPhuongThucTT("Tiền mặt");
                        break;
                    }
                    else if (choice == 2) {
                        hoaDon.setPhuongThucTT("Chuyển khoản");
                        break;
                    } else {
                        System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại.");
                    }
                }

                System.out.println("Nhập tiền khách đưa: ");
                int tienKhachDua = scanner.nextInt();
                scanner.nextLine();
                while (true) {
                    if (tienKhachDua >= tongTien) {
                        hoaDon.setTienKhachDua(tienKhachDua);
                        hoaDon.setTienThua(tienKhachDua - tongTien);
                        break;
                    } else {
                        System.out.println("Tiền khách đưa không đủ, vui lòng nhập lại.");
                        System.out.println("Nhập tiền khách đưa: ");
                        tienKhachDua = scanner.nextInt();
                        scanner.nextLine();
                    }
                }


                hoaDon.setMaHD(maHD);
                hoaDon.setMaKH(kh.getMaKH());
                hoaDon.setMaNV(maNV);
                hoaDon.setTongTien(tongTien);
                hoaDon.setNgayLapHD(LocalDateTime.now());
                hoaDon.setPhuongThucTT(hoaDon.getPhuongThucTT());
                HoaDonDAO.themHoaDon(hoaDon);

                for (ChiTietHoaDonDTO ctHoaDon : chiTietHoaDon) {
                    ChiTietHoaDonDAO.themChiTietHoaDon(ctHoaDon);
                }
                System.out.println("Thêm hóa đơn thành công");
                inHoaDon(maHD);
                // xuất ra file

                System.out.println("Bạn có muốn tạo hóa đơn khác? (y/n): ");
                String choice = scanner.nextLine().trim();
                if (!"y".equalsIgnoreCase(choice)) break;
            } catch (Exception e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        }
    }

    // Làm lại giao diện cho giống thực tế, đẹp hơn, tự sắp xếp bố cục lại cho phù hợp
    public void inHoaDon(String maHD) {
        HoaDonDTO hoaDon = HoaDonDAO.timHoaDon(maHD);
        List<ChiTietHoaDonDTO> chiTietHoaDon = ChiTietHoaDonDAO.timChiTietHoaDon(maHD);
        
        System.out.println("ABC Store");
        System.out.println("123 An Dương Vương, Q5, TP.HCM");
        System.out.println("Điện thoại: 0909090909");
        System.out.println("Hóa đơn bán hàng");
        System.out.println("Ngày lập hóa đơn: " + hoaDon.getNgayLapHD().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("Phương thức thanh toán: " + hoaDon.getPhuongThucTT());
        System.out.println("Tiền khách đưa: " + hoaDon.getTienKhachDua());
        System.out.println("Tiền thừa: " + hoaDon.getTienThua());
        System.out.println("Tổng tiền: " + hoaDon.getTongTien());
        System.out.println("Khách hàng: " + hoaDon.getMaKH());
        System.out.println("Nhân viên: " + hoaDon.getMaNV());
        System.out.println("Chi tiết hóa đơn: ");

        for (ChiTietHoaDonDTO ctHoaDon : chiTietHoaDon) {
            ctHoaDon.inChiTietHoaDon();
        }
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
                    System.out.println("Nhập ngày bắt đầu: ");
                    from = scanner.nextLine().trim();
    
                    System.out.println("Nhập ngày kết thúc: ");
                    to = scanner.nextLine().trim();
    
                    fromDate = LocalDate.parse(from, formatter);
                    toDate = LocalDate.parse(to, formatter);
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Định dạng ngày không hợp lệ, vui lòng nhập lại.");
                    scanner.nextLine();
                }
            }
            
            List<HoaDonDTO> list = HoaDonDAO.timHoaDonTheoNgayLap(fromDate, toDate);

            System.out.println("Danh sách hóa đơn trong khoảng ngày: " + from + " đến " + to);

            // làm lại giao diện cho giống thực tế, đẹp hơn
            if (list.isEmpty()) {
                System.out.println("Không tìm thấy hóa đơn trong khoảng ngày: " + from + " đến " + to);
            } else {
                for (HoaDonDTO hd : list) {
                    System.out.println("Mã hóa đơn: " + hd.getMaHD());
                    System.out.println("Ngày lập hóa đơn: " + hd.getNgayLapHD().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    System.out.println("Phương thức thanh toán: " + hd.getPhuongThucTT());
                    System.out.println("Tổng tiền: " + FormatUtil.formatVND(hd.getTongTien()));
                    System.out.println("Khách hàng: " + hd.getMaKH());
                    System.out.println("Nhân viên: " + hd.getMaNV());
                }
                System.out.println("Tìm thấy " + list.size() + " hóa đơn trong khoảng ngày: " + from + " đến " + to);
            }
            
            System.out.print("\n Bạn có muốn tìm tiếp không? (y/n): ");
            String choice = scanner.nextLine().trim();
            if (!choice.equalsIgnoreCase("y")) {
                System.out.println("Thoát tìm kiếm hóa đơn thành công.");
                break;
            }
        }
    }

    // Làm lại giao diện cho giống thực tế, đẹp hơn
    public void xemDanhSachHoaDon() {
        Scanner scanner = new Scanner(System.in);
        List<HoaDonDTO> list = HoaDonDAO.getAllHoaDon();

        System.out.println("Danh sách hóa đơn: ");

        if (list.isEmpty()) {
            System.out.println("Không có hóa đơn nào trong hệ thống.");
            return;
        } 

        for (HoaDonDTO hd : list) {
            System.out.println("Mã hóa đơn: " + hd.getMaHD());
            System.out.println("Ngày lập hóa đơn: " + hd.getNgayLapHD().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println("Phương thức thanh toán: " + hd.getPhuongThucTT());
            System.out.println("Tổng tiền: " + FormatUtil.formatVND(hd.getTongTien()));
            System.out.println("Khách hàng: " + hd.getMaKH());
            System.out.println("Nhân viên: " + hd.getMaNV());
            System.out.println("--------------------------------");
        }

        System.out.println("Tìm thấy " + list.size() + " hóa đơn trong hệ thống.");

        String tieptuc;
        do {
            System.out.println("Bạn có muốn xem chi tiết hóa đơn không (y/n): ");
            tieptuc = scanner.nextLine().trim();

            if (tieptuc.equalsIgnoreCase("y")) {
                System.out.println("Nhập mã hóa đơn cần xem chi tiết: ");
                String maHD = scanner.nextLine().trim();
                inHoaDon(maHD);
            } else {
                System.out.println("Không xem chi tiết hóa đơn nào.");
                break;
            }
        } while (tieptuc.equalsIgnoreCase("y"));
    }
}