package dto;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import util.ValidatorUtil;

public class KhachHangDTO {
    private String maKH;
    private String ho;
    private String ten;
    private String gioiTinh;
    private LocalDate ngaySinh;
    private String diaChi;
    private String dienThoai;
    private String trangThai;

    public KhachHangDTO() {}

    public KhachHangDTO(String maKH, String ho, String ten, String gioiTinh, LocalDate ngaySinh, String diaChi, String dienThoai, String trangThai) {
        this.maKH = maKH;
        this.ho = ho;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.dienThoai = dienThoai;
        this.trangThai = trangThai;
    }

    public String getMaKH() {
        return maKH;
    }
    
    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHo() {
        return ho;
    }
    
    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public String getNgaySinhFormat() {
        return ngaySinh.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));        
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }   

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public boolean nhapThongTinKhachHang() {
        Scanner scanner = new Scanner(System.in);
        String input;
        
        try {
            while (true) {
                System.out.print("→ Nhập họ khách hàng (hoặc '0' để hủy): ");
                input = scanner.nextLine().trim();
                if ("0".equals(input)) return false;

                if (ValidatorUtil.isValidLastName(input)) {
                    this.ho = input;
                    break;
                }
                System.out.println("❌ Họ khách hàng không hợp lệ! Vui lòng nhập lại.");
            }
            
            while (true) {
                System.out.print("→ Nhập tên khách hàng (hoặc '0' để hủy): ");
                input = scanner.nextLine().trim();
                if ("0".equals(input)) return false;

                if (ValidatorUtil.isValidFirstName(input)) {
                    this.ten = input;
                    break;
                }
                System.out.println("❌ Tên khách hàng không hợp lệ! Vui lòng nhập lại.");
            }

            while (true) {
                System.out.print("→ Nhập giới tính (hoặc '0' để hủy): ");
                input = scanner.nextLine().trim();
                if ("0".equals(input)) return false;

                String lower = input.toLowerCase();
                if (lower.equals("nam")) {
                    this.gioiTinh = "Nam";
                    break;
                } else if (lower.equals("nữ") || lower.equals("nu") || lower.equals("nư")) {
                    this.gioiTinh = "Nữ";
                    break;
                } else {
                    System.out.println("❌ Giới tính không hợp lệ! Vui lòng nhập: Nam/Nữ/nam/nu");
                }
            }

            while (true) {
                System.out.print("→ Nhập ngày sinh (dd/MM/yyyy, hoặc '0' để hủy): ");
                input = scanner.nextLine().trim();
                if ("0".equals(input)) return false;

                if (input.isEmpty()) {
                    this.ngaySinh = null;
                    break;
                }

                if (!ValidatorUtil.isValidateDate(input)) continue;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                this.ngaySinh = LocalDate.parse(input, formatter);

                if (this.ngaySinh.isAfter(LocalDate.now())) {
                    System.out.println("❌ Ngày sinh không được lớn hơn ngày hiện tại!");
                    continue;
                }

                int tuoi = Period.between(this.ngaySinh, LocalDate.now()).getYears();
                if (tuoi < 12 || tuoi > 80) {
                    System.out.println("❌ Khách hàng phải từ 12 đến 80 tuổi!");
                    continue;
                }
                break;
            }

            while (true) {
                System.out.print("→ Nhập địa chỉ khách hàng (hoặc '0' để hủy): ");
                input = scanner.nextLine().trim();
                if ("0".equals(input)) return false;

                if (input.isEmpty()) {
                    this.diaChi = null;
                    break;
                }

                if (!ValidatorUtil.isValidAddress(input)) continue;
                this.diaChi = input;
                break;
            }

            while (true) {
                System.out.print("→ Nhập số điện thoại (10 số, hoặc '0' để hủy): ");
                input = scanner.nextLine().trim();
                if ("0".equals(input)) return false;

                if (ValidatorUtil.isValidPhoneNumber(input)) {
                    this.dienThoai = input;
                    break;
                }
                System.out.println("❌ Số điện thoại không hợp lệ! Vui lòng nhập lại.");
            }
            return true;
        } catch (Exception e) {
            System.err.println("❌ Lỗi nhập thông tin khách hàng: " + e.getMessage());
            return false;
        }
    }

    public boolean suaThongTinKhachHang() {
        Scanner scanner = new Scanner(System.in);

        // sửa họ
        System.out.print("Sửa họ khách hàng: ");
        String newHo = scanner.nextLine().trim();
        if ("0".equals(newHo)) return false;
        if (!newHo.isEmpty()) {
            if (!ValidatorUtil.isValidLastName(newHo)) {
                System.out.println("❌ Họ khách hàng không hợp lệ! Giữ nguyên họ");
            } else this.ho = newHo;
        }


        // sửa tên
        System.out.print("Sửa tên khách hàng: ");
        String newTen = scanner.nextLine().trim();
        if ("0".equals(newTen)) return false;
        if (!newTen.isEmpty()) {
            if (!ValidatorUtil.isValidFirstName(newTen)) {
                System.out.println("❌ Tên khách hàng không hợp lệ! Giữ nguyên tên khách hàng");
            } else this.ten = newTen;
        }


        // sửa giới tính    
        System.out.print("Sửa giới tính (Nam/Nữ): ");
        String newGioiTinh = scanner.nextLine().trim();
        if ("0".equals(newGioiTinh)) return false;

        if (!newGioiTinh.isEmpty()) {
            String lower = newGioiTinh.toLowerCase();
            if (lower.equals("nam")) {
                this.gioiTinh = "Nam";
            } else if (lower.equals("nữ") || lower.equals("nu") || lower.equals("nư")) {
                this.gioiTinh = "Nữ";
            } else {
                System.out.println("❌ Giới tính không hợp lệ! Giữ nguyên giới tính");
            }
        } 


        // sửa ngày sinh
        System.out.print("Sửa ngày sinh (dd/MM/yyyy): ");
        String newNgaySinh = scanner.nextLine().trim();
        if ("0".equals(newNgaySinh)) return false;
        if (!newNgaySinh.isEmpty()) {
            if (!ValidatorUtil.isValidateDate(newNgaySinh)) {
                System.out.println("❌ Ngày sinh không hợp lệ! Giữ nguyên ngày sinh");
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate ngaySinh = LocalDate.parse(newNgaySinh, formatter);

                if (ngaySinh.isAfter(LocalDate.now())) {
                    System.out.println("❌ Ngày sinh không được lớn hơn ngày hiện tại! Giữ nguyên ngày sinh");
                } else {
                    int tuoi = Period.between(ngaySinh, LocalDate.now()).getYears();
                    if (tuoi < 12 || tuoi > 80) {
                        System.out.println("❌ Khách hàng phải từ 12 đến 80 tuổi! Giữ nguyên ngày sinh");
                    } else {
                        this.ngaySinh = ngaySinh;
                    }
                }
            }
        }

        // sửa địa chỉ
        System.out.print("Sửa địa chỉ: ");
        String newDiaChi = scanner.nextLine().trim();

        if ("0".equals(newDiaChi)) return false;

        if (!newDiaChi.isEmpty()) {
            if (ValidatorUtil.isValidAddress(newDiaChi)) {
                this.diaChi = newDiaChi;
            } else {
                System.out.println("❌ Địa chỉ không hợp lệ! Giữ nguyên địa chỉ");
            }
        }

        System.out.print("Sửa số điện thoại (10 số): ");
        String newSdt = scanner.nextLine().trim();

        if ("0".equals(newSdt)) return false;

        if (!newSdt.isEmpty()) {
            if (ValidatorUtil.isValidPhoneNumber(newSdt.replaceAll("\\s", ""))) {
                this.dienThoai = newSdt;
            } else {
                System.out.println("❌ Số điện thoại không hợp lệ! Giữ nguyên số điện thoại");
            }
        }

        return true;
    }

    public void inThongTinKhachHang() {
        System.out.println("┌───────────────────────────────────────────────────┐");
        System.out.printf("│ %-18s : %-28s │\n", "Mã KH", maKH);
        System.out.printf("│ %-18s : %-28s │\n", "Họ tên", ho + " " + ten);
        System.out.printf("│ %-18s : %-28s │\n", "Giới tính", gioiTinh);

        String ngaySinhStr = (ngaySinh != null) ?
                ngaySinh.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) :
                "(Chưa cập nhật)";
        System.out.printf("│ %-18s : %-28s │\n", "Ngày sinh", ngaySinhStr);
        

        String diaChiStr = (diaChi != null && !diaChi.isEmpty()) ?
                diaChi : "(Chưa cập nhật)";
        System.out.printf("│ %-18s : %-28s │\n", "Địa chỉ", diaChiStr);

        System.out.printf("│ %-18s : %-28s │\n", "Điện thoại", dienThoai);

        String trangThaiStr = (trangThai.equals("active") ? "Hoạt động" : "Không hoạt động");
        System.out.printf("│ %-18s : %-28s │\n", "Trạng thái", trangThaiStr);
        System.out.println("└───────────────────────────────────────────────────┘");
    }
}