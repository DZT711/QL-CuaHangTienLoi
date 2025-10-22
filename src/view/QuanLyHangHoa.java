package view;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import dao.HangHoaDAO;
import dao.SanPhamDAO;
import dto.HangHoaDTO;
import dto.sanPhamDTO;

public class QuanLyHangHoa {
    public void menuQuanLyHangHoa() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n████████████████████████████████████████████████████████████████████████████████");
            System.out.println("██                                                                            ██");
            System.out.println("██                       HỆ THỐNG QUẢN LÝ HÀNG HÓA                            ██");
            System.out.println("██                                                                            ██");
            System.out.println("████████████████████████████████████████████████████████████████████████████████");
            System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓ MENU CHỨC NĂNG ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
            System.out.println("▒ [1] ➜ Xem danh sách hàng hóa trong kho                                       ▒");
            System.out.println("▒ [2] ➜ Tìm kiếm hàng hóa                                                      ▒");
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
                    while (true) {
                        try {
                            System.out.println("\n════════════════════════════════════════════════");
                            System.out.println("        📦 XEM DANH SÁCH HÀNG HÓA TRONG KHO     ");
                            System.out.println("════════════════════════════════════════════════");
                            System.out.println("1. Hiển thị theo sản phẩm (nhóm các lô)");
                            System.out.println("2. Hiển thị tất cả hàng hóa (chi tiết từng lô)");
                            System.out.println("0. Quay lại");
                            System.out.println("════════════════════════════════════════════════");
                            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

                            int opt = scanner.nextInt();
                            scanner.nextLine();

                            if (opt == 0) {
                                System.out.println("✅ Quay lại menu quản lý hàng hóa.");
                                break;
                            } else if (opt == 1) {
                                xemDanhSachTheoSanPham();
                            } else if (opt == 2) {
                                xemTatCaHangHoa();
                            } else {
                                System.out.println("❌ Lựa chọn không hợp lệ!");

                            }
                        } catch (Exception e) {
                            System.out.println("❌ Lỗi xảy ra: " + e.getMessage());
                            scanner.nextLine();
                        }
                    }
                    break;
                case 2:
                    while (true) {
                        try {
                            System.out.println("\n════════════════════════════════════════════════");
                            System.out.println("        📦 TÌM KIẾM HÀNG HÓA TRONG KHO     ");
                            System.out.println("════════════════════════════════════════════════");
                            System.out.println("1. Tìm kiếm hàng hóa theo mã hàng");
                            System.out.println("2. Tìm kiếm hàng hóa theo mã sản phẩm");
                            System.out.println("3. Tìm kiếm hàng hóa theo hạn sử dụng");
                            System.out.println("0. Quay lại");
                            System.out.println("════════════════════════════════════════════════");
                            System.out.print("\n💡 Nhập lựa chọn của bạn: ");

                            int opt = scanner.nextInt();
                            scanner.nextLine();

                            if (opt == 0) {
                                System.out.println("✅ Quay lại menu quản lý hàng hóa.");
                                break;
                            } else if (opt == 1) {
                                timHangHoaTheoMaHang();
                            } else if (opt == 2) {
                                timHangHoaTheoMaSP();
                            } else if (opt == 3) {
                                timHangHoaTheoHanSuDung();
                            } else {
                                System.out.println("❌ Lựa chọn không hợp lệ!");
                            }
                        } catch (Exception e) {
                            System.out.println("❌ Lỗi xảy ra: " + e.getMessage());
                            scanner.nextLine();
                        }
                    }
                    break;
                case 0:
                    System.out.println("✅ Quay lại menu chính.");
                    return;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ!");
                    break;
            }
        }
    }

    public void xemDanhSachTheoSanPham() {
        Scanner scanner = new Scanner(System.in);
        
        List<Map<String, Object>> danhSach = HangHoaDAO.xemDanhSachHangHoaTheoSanPham();
        if (danhSach.isEmpty()) {
            System.out.println("❌ Không có hàng hóa trong kho.");
            return;
        }

        System.out.println("\n════════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("                           📦 DANH SÁCH HÀNG HÓA THEO SẢN PHẨM                          ");
        System.out.println("════════════════════════════════════════════════════════════════════════════════════════");
        System.out.printf("%-10s %-30s %-15s %-10s %-15s %-15s%n",
            "Mã SP", "Tên sản phẩm", "Giá bán", "Số lô", "Tổng SL", "HSD gần nhất");
        System.out.println("────────────────────────────────────────────────────────────────────────────────────────");

        for (Map<String, Object> row : danhSach) {
            String maSP = (String) row.get("MaSP");
            String tenSP = (String) row.get("TenSP");
            int giaBan = (int) row.get("GiaBan");
            int soLo = (int) row.get("SoLo");
            int tongSL = (int) row.get("TongSoLuong");
            Date hsd = (Date) row.get("HanSuDungGanNhat");
            String hsdStr = (hsd != null) ? hsd.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A";

            System.out.printf("%-10s %-30s %-15s %-10d %-15d %-15s%n",
                maSP,
                (tenSP.length() > 30 ? tenSP.substring(0, 27) + "..." : tenSP),
                util.FormatUtil.formatVND(giaBan),
                soLo,
                tongSL,
                hsdStr
            );
        }

        System.out.println("════════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("📊 Tổng cộng: " + danhSach.size() + " sản phẩm");
        System.out.println();

        while (true) {
            System.out.print("❓ Bạn có muốn xem chi tiết lô hàng của sản phẩm nào không? (nhập mã SP hoặc '0' để thoát): ");
            String maSP = scanner.nextLine().trim();
            
            if ("0".equals(maSP)) break;
            
            if (maSP.isEmpty()) {
                System.out.println("❌ Mã sản phẩm không được để trống!");
                continue;
            }
            
            xemChiTietLoHangTheoSanPham(maSP);
        }
    }

    public void xemChiTietLoHangTheoSanPham(String maSP) {
        sanPhamDTO sp = SanPhamDAO.timSanPhamTheoMa(maSP);
        if (sp == null) {
            System.out.println("❌ Không tìm thấy sản phẩm với mã: " + maSP);
            return;
        }

        List<HangHoaDTO> loHangList = HangHoaDAO.timChiTietLoHangTheoSanPham(maSP);

        if (loHangList == null || loHangList.isEmpty()) {
            System.out.println("❌ Không tìm thấy lô hàng nào cho sản phẩm với mã: " + maSP);
            return;
        }

        System.out.println("\n════════════════════════════════════════════════════════════════════════════════");
        System.out.println("                      📦 CHI TIẾT LÔ HÀNG CỦA SẢN PHẨM                        ");
        System.out.println("════════════════════════════════════════════════════════════════════════════════");
        System.out.println("Mã sản phẩm        : " + sp.getMaSP());
        System.out.println("Tên sản phẩm       : " + sp.getTenSP());
        System.out.println("Giá bán            : " + util.FormatUtil.formatVND(sp.getGiaBan()));
        System.out.println("Tồn kho tổng       : " + sp.getSoLuongTon());
        System.out.println("────────────────────────────────────────────────────────────────────────────────");
        System.out.printf("%-15s %-15s %-15s %-15s %-15s%n",
            "Mã hàng", "SL còn lại", "Ngày SX", "Hạn SD", "Trạng thái");
        System.out.println("────────────────────────────────────────────────────────────────────────────────");


        int count = 0;
        int tongSL = 0;
        for (HangHoaDTO loHang : loHangList) {
            String ngaySXStr = (loHang.getNgaySanXuat() != null) ? loHang.getNgaySanXuat().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A";
            String hanSDStr = (loHang.getHanSuDung() != null) ? loHang.getHanSuDung().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A";
            
            // Emoji cho trạng thái
            String trangThaiIcon = "";
            if ("active".equals(loHang.getTrangThai())) {
                trangThaiIcon = "✅ Active";
            } else if ("inactive".equals(loHang.getTrangThai())) {
                trangThaiIcon = "⚠️ Inactive";
            } else if ("expired".equals(loHang.getTrangThai())) {
                trangThaiIcon = "❌ Expired";
            }

            System.out.printf("%-15s %-15d %-15s %-15s %-15s%n",
                loHang.getMaHang(),
                loHang.getSoLuongConLai(),
                ngaySXStr,
                hanSDStr,
                trangThaiIcon
            );
            count++;
            tongSL += loHang.getSoLuongConLai();
        }

        System.out.println("════════════════════════════════════════════════════════════════════════════════");
        System.out.println("📊 Tổng cộng: " + count + " lô hàng | Tổng số lượng: " + tongSL);
        System.out.println();
    }

    public void xemTatCaHangHoa() {
        List<Map<String, Object>> loHangList = HangHoaDAO.layDanhSachHangHoa();

        if (loHangList == null || loHangList.isEmpty()) {
            System.out.println("❌ Không có hàng hóa trong kho.");
            return;
        }

        System.out.println("\n════════════════════════════════════════════════════════════════════════════════");
        System.out.println("                           📦 DANH SÁCH TẤT CẢ HÀNG HÓA                          ");
        System.out.println("════════════════════════════════════════════════════════════════════════════════");
        System.out.printf("%-15s %-15s %-25s %-15s %-15s %-15s %-15s%n",
                "Mã hàng", "Mã SP", "Tên SP", "SL còn lại", "Ngày SX", "Hạn SD", "Trạng thái");
        System.out.println("────────────────────────────────────────────────────────────────────────────────");
        
        int tongSL = 0;
        for (Map<String, Object> loHang : loHangList) {
            String ngaySXStr = (loHang.get("NgaySanXuat") != null) ? ((LocalDate) loHang.get("NgaySanXuat")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A";
            String hanSDStr = (loHang.get("HanSuDung") != null) ? ((LocalDate) loHang.get("HanSuDung")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A";

            // Emoji cho trạng thái
            String trangThaiIcon = "";
            if ("active".equals(loHang.get("TrangThai"))) {
                trangThaiIcon = "✅ Active";
            } else if ("inactive".equals(loHang.get("TrangThai"))) {
                trangThaiIcon = "⚠️ Inactive";
            } else if ("expired".equals(loHang.get("TrangThai"))) {
                trangThaiIcon = "❌ Expired";
            }

            System.out.printf("%-15s %-15s %-25s %-15d %-15s %-15s %-15s%n",
                loHang.get("MaHang"),
                loHang.get("MaSP"),
                loHang.get("TenSP"),  
                loHang.get("SoLuongConLai"),
                ngaySXStr,
                hanSDStr,
                trangThaiIcon
            );
            tongSL += (int) loHang.get("SoLuongConLai");
        }
        System.out.println("════════════════════════════════════════════════════════════════════════════════");
        System.out.println("📊 Tổng cộng: " + loHangList.size() + " lô hàng | Tổng số lượng: " + tongSL);
    }

    public void timHangHoaTheoMaHang() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\nNhập mã hàng cần tìm (hoặc '0' để thoát): ");
            String maHang = scanner.nextLine().trim();
            
            if ("0".equals(maHang)) {
                System.out.println("✅ Thoát tìm kiếm hàng hóa.");
                break;
            }
            
            if (maHang.isEmpty()) {
                System.out.println("❌ Mã hàng không được để trống!");
                continue;
            }
            
            HangHoaDTO hangHoa = HangHoaDAO.timHangHoaTheoMa(maHang);
            
            if (hangHoa == null) {
                System.out.println("❌ Không tìm thấy lô hàng với mã: " + maHang);
                continue;
            }
            
            // Lấy thông tin sản phẩm
            sanPhamDTO sp = SanPhamDAO.timSanPhamTheoMa(hangHoa.getMaSP());
            if (sp == null) {
                System.out.println("❌ Lỗi: Không tìm thấy thông tin sản phẩm!");
                continue;
            }
            
            // Hiển thị thông tin
            System.out.println("\n════════════════════════════════════════════════════════");
            System.out.println("              📦 THÔNG TIN LÔ HÀNG                      ");
            System.out.println("════════════════════════════════════════════════════════");
            System.out.println("Mã hàng            : " + hangHoa.getMaHang());
            System.out.println("Mã sản phẩm        : " + hangHoa.getMaSP());
            System.out.println("Tên sản phẩm       : " + sp.getTenSP());
            System.out.println("Giá bán            : " + util.FormatUtil.formatVND(sp.getGiaBan()));
            System.out.println("Số lượng còn lại   : " + hangHoa.getSoLuongConLai());
            System.out.println("Ngày sản xuất      : " + 
                (hangHoa.getNgaySanXuat() != null ? hangHoa.getNgaySanXuat().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A"));
            System.out.println("Hạn sử dụng        : " + 
                (hangHoa.getHanSuDung() != null ? hangHoa.getHanSuDung().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A"));
            
            // Emoji cho trạng thái
            String trangThaiIcon = "";
            if ("active".equals(hangHoa.getTrangThai())) {
                trangThaiIcon = "✅ Active";
            } else if ("inactive".equals(hangHoa.getTrangThai())) {
                trangThaiIcon = "⚠️ Inactive";
            } else if ("expired".equals(hangHoa.getTrangThai())) {
                trangThaiIcon = "❌ Expired";
            }
            
            System.out.println("Trạng thái         : " + trangThaiIcon);
            System.out.println("════════════════════════════════════════════════════════");
        }
    }

    public void timHangHoaTheoMaSP() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.print("\nNhập mã sản phẩm cần tìm (hoặc '0' để thoát): ");
            String maSP = scanner.nextLine().trim();
            
            if ("0".equals(maSP)) {
                System.out.println("✅ Thoát tìm kiếm hàng hóa.");
                break;
            }
            
            if (maSP.isEmpty()) {
                System.out.println("❌ Mã sản phẩm không được để trống!");
                continue;
            }
            
            // Kiểm tra sản phẩm tồn tại
            sanPhamDTO sp = SanPhamDAO.timSanPhamTheoMa(maSP);
            if (sp == null) {
                System.out.println("❌ Không tìm thấy sản phẩm với mã: " + maSP);
                continue;
            }
            
            // Lấy danh sách lô hàng
            List<HangHoaDTO> loHangList = HangHoaDAO.timChiTietLoHangTheoSanPham(maSP);
            
            if (loHangList == null || loHangList.isEmpty()) {
                System.out.println("⚠️ Sản phẩm này chưa có lô hàng nào trong kho.");
                continue;
            }
            
            // Hiển thị thông tin
            System.out.println("\n════════════════════════════════════════════════════════════════════════════════");
            System.out.println("                    🔍 KẾT QUẢ TÌM KIẾM THEO SẢN PHẨM                          ");
            System.out.println("════════════════════════════════════════════════════════════════════════════════");
            System.out.println("Mã sản phẩm        : " + sp.getMaSP());
            System.out.println("Tên sản phẩm       : " + sp.getTenSP());
            System.out.println("Giá bán            : " + util.FormatUtil.formatVND(sp.getGiaBan()));
            System.out.println("Tồn kho tổng       : " + sp.getSoLuongTon());
            System.out.println("────────────────────────────────────────────────────────────────────────────────");
            System.out.println("                           DANH SÁCH CÁC LÔ HÀNG                               ");
            System.out.println("────────────────────────────────────────────────────────────────────────────────");
            System.out.printf("%-15s %-15s %-15s %-15s %-18s%n",
                "Mã hàng", "SL còn lại", "Ngày SX", "Hạn SD", "Trạng thái");
            System.out.println("────────────────────────────────────────────────────────────────────────────────");
            
            int tongSL = 0;
            for (HangHoaDTO loHang : loHangList) {
                String ngaySXStr = (loHang.getNgaySanXuat() != null) ? 
                    loHang.getNgaySanXuat().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A";
                String hanSDStr = (loHang.getHanSuDung() != null) ? 
                    loHang.getHanSuDung().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A";
                
                // Emoji cho trạng thái
                String trangThaiIcon = "";
                if ("active".equals(loHang.getTrangThai())) {
                    trangThaiIcon = "✅ Active";
                } else if ("inactive".equals(loHang.getTrangThai())) {
                    trangThaiIcon = "⚠️ Inactive";
                } else if ("expired".equals(loHang.getTrangThai())) {
                    trangThaiIcon = "❌ Expired";
                }
                
                System.out.printf("%-15s %-15d %-15s %-15s %-18s%n",
                    loHang.getMaHang(),
                    loHang.getSoLuongConLai(),
                    ngaySXStr,
                    hanSDStr,
                    trangThaiIcon
                );
                
                tongSL += loHang.getSoLuongConLai();
            }
            
            System.out.println("════════════════════════════════════════════════════════════════════════════════");
            System.out.println("📊 Tổng cộng: " + loHangList.size() + " lô hàng | Tổng số lượng: " + tongSL);
            System.out.println();
        }
    }

    public void timHangHoaTheoHanSuDung() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        while (true) {
            System.out.print("\nNhập hạn sử dụng cần tìm (ddMMyyyy) hoặc '0' để thoát: ");
            String input = scanner.nextLine().trim();
            
            if ("0".equals(input)) {
                System.out.println("✅ Thoát tìm kiếm hàng hóa.");
                break;
            }
            
            // Validation format
            if (input.length() != 8) {
                System.out.println("❌ Định dạng không hợp lệ! Vui lòng nhập đúng 8 ký tự (ddMMyyyy).");
                continue;
            }
            
            LocalDate hanSuDung;
            try {
                hanSuDung = LocalDate.parse(input, inputFormatter);
            } catch (Exception e) {
                System.out.println("❌ Ngày không hợp lệ! Vui lòng nhập lại.");
                continue;
            }
            
            // Tìm kiếm
            List<Map<String, Object>> loHangList = HangHoaDAO.timHangHoaTheoHanSuDung(hanSuDung);
            
            if (loHangList == null || loHangList.isEmpty()) {
                System.out.println("⚠️ Không tìm thấy lô hàng nào có hạn sử dụng: " + hanSuDung.format(displayFormatter));
                continue;
            }
            
            // Hiển thị kết quả
            System.out.println("\n════════════════════════════════════════════════════════════════════════════════════════");
            System.out.println("                    🔍 KẾT QUẢ TÌM KIẾM THEO HẠN SỬ DỤNG                              ");
            System.out.println("════════════════════════════════════════════════════════════════════════════════════════");
            System.out.println("Hạn sử dụng: " + hanSuDung.format(displayFormatter));
            System.out.println("────────────────────────────────────────────────────────────────────────────────────────");
            System.out.printf("%-12s %-12s %-25s %-12s %-12s %-18s%n",
                "Mã hàng", "Mã SP", "Tên SP", "SL còn lại", "Ngày SX", "Trạng thái");
            System.out.println("────────────────────────────────────────────────────────────────────────────────────────");
            
            int tongSL = 0;
            for (Map<String, Object> loHang : loHangList) {
                String ngaySXStr = (loHang.get("NgaySanXuat") != null) ? 
                    ((LocalDate) loHang.get("NgaySanXuat")).format(displayFormatter) : "N/A";
                
                // Emoji cho trạng thái
                String trangThaiIcon = "";
                if ("active".equals(loHang.get("TrangThai"))) {
                    trangThaiIcon = "✅ Active";
                } else if ("inactive".equals(loHang.get("TrangThai"))) {
                    trangThaiIcon = "⚠️ Inactive";
                } else if ("expired".equals(loHang.get("TrangThai"))) {
                    trangThaiIcon = "❌ Expired";
                }
                
                System.out.printf("%-12s %-12s %-25s %-12d %-12s %-18s%n",
                    loHang.get("MaHang"),
                    loHang.get("MaSP"),
                    loHang.get("TenSP"),
                    loHang.get("SoLuongConLai"),
                    ngaySXStr,
                    trangThaiIcon
                );
                
                tongSL += (int) loHang.get("SoLuongConLai");
            }
            
            System.out.println("════════════════════════════════════════════════════════════════════════════════════════");
            System.out.println("📊 Tổng cộng: " + loHangList.size() + " lô hàng | Tổng số lượng: " + tongSL);
            System.out.println();
        }
    }
}