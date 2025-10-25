package dto;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class KhachHangDTO {
    private String maKH;
    private String ho;
    private String ten;
    private String gioiTinh;
    private LocalDate ngaySinh;
    private String diaChi;
    private String dienThoai;

    public KhachHangDTO() {}

    public KhachHangDTO(String maKH, String ho, String ten, String gioiTinh, LocalDate ngaySinh, String diaChi, String dienThoai) {
        this.maKH = maKH;
        this.ho = ho;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.dienThoai = dienThoai;
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

    public boolean nhapThongTinKhachHang() {
        Scanner scanner = new Scanner(System.in);
        
        // 1. Nhập họ
        System.out.print("→ Nhập họ khách hàng (hoặc '0' để hủy): ");
        String input = scanner.nextLine().trim();
        if ("0".equals(input)) return false;
        while (input.isEmpty()) {
            System.out.println("❌ Họ khách hàng không được để trống!");
            System.out.print("→ Nhập họ khách hàng: ");
            input = scanner.nextLine().trim();
            if ("0".equals(input)) return false;
        }
        this.ho = input;
        
        // 2. Nhập tên
        System.out.print("→ Nhập tên khách hàng (hoặc '0' để hủy): ");
        input = scanner.nextLine().trim();
        if ("0".equals(input)) return false;
        while (input.isEmpty()) {
            System.out.println("❌ Tên khách hàng không được để trống!");
            System.out.print("→ Nhập tên khách hàng: ");
            input = scanner.nextLine().trim();
            if ("0".equals(input)) return false;
        }
        this.ten = input;
        
        // 3. Nhập giới tính (chấp nhận: Nam, Nữ, nam, nu)
        System.out.print("→ Nhập giới tính (hoặc '0' để hủy): ");
        input = scanner.nextLine().trim();
        if ("0".equals(input)) return false;
        while (true) {
            String lower = input.toLowerCase();
            if (lower.equals("nam")) {
                this.gioiTinh = "Nam";
                break;
            } else if (lower.equals("nữ") || lower.equals("nu") || lower.equals("nư")) {
                this.gioiTinh = "Nữ";
                break;
            } else {
                System.out.println("❌ Giới tính không hợp lệ! Vui lòng nhập: Nam/Nữ/nam/nu");
                System.out.print("→ Nhập giới tính: ");
                input = scanner.nextLine().trim();
                if ("0".equals(input)) return false;
            }
        }
        
        // 4. Nhập ngày sinh
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.print("→ Nhập ngày sinh (dd/MM/yyyy, Enter để bỏ qua, '0' để hủy): ");
        while (true) {
            input = scanner.nextLine().trim();
            if ("0".equals(input)) return false;
            
            if (input.isEmpty()) {
                this.ngaySinh = null;
                break;
            }
            
            try {
                this.ngaySinh = LocalDate.parse(input, formatter);
                
                if (this.ngaySinh.isAfter(LocalDate.now())) {
                    System.out.println("❌ Ngày sinh không được lớn hơn ngày hiện tại!");
                    System.out.print("→ Nhập lại ngày sinh (hoặc Enter để bỏ qua): ");
                    continue;
                }
                
                int tuoi = Period.between(this.ngaySinh, LocalDate.now()).getYears();
                if (tuoi > 100) {
                    System.out.println("❌ Ngày sinh không hợp lý (quá 100 tuổi)!");
                    System.out.print("→ Nhập lại ngày sinh (hoặc Enter để bỏ qua): ");
                    continue;
                }
                
                break;
            } catch (DateTimeParseException e) {
                System.out.println("❌ Sai định dạng! Vui lòng nhập: dd/MM/yyyy (VD: 25/10/2000)");
                System.out.print("→ Nhập lại ngày sinh (hoặc Enter để bỏ qua): ");
            }
        }
        
        // // 5. Nhập địa chỉ
        System.out.print("→ Nhập địa chỉ khách hàng (Enter để bỏ qua, '0' để hủy): ");
        input = scanner.nextLine().trim();
        if ("0".equals(input)) return false;
        
        if (input.isEmpty()) this.diaChi = null;
        else this.diaChi = input;
        
        // 6. Nhập điện thoại
        System.out.print("→ Nhập số điện thoại (10 số, hoặc '0' để hủy): ");
        input = scanner.nextLine().trim();
        if ("0".equals(input)) return false;
        while (!input.matches("\\d{10}")) {
            System.out.println("❌ Số điện thoại không hợp lệ! Phải là 10 chữ số.");
            System.out.print("→ Nhập số điện thoại: ");
            input = scanner.nextLine().trim();
            if ("0".equals(input)) return false;
        }
        this.dienThoai = input;
        return true;
    }

    public boolean suaThongTinKhachHang() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sửa họ: ");
        String newHo = scanner.nextLine().trim();
        if ("0".equals(newHo)) return false;
        if (!newHo.isEmpty()) this.ho = newHo;

        System.out.println("Sửa tên: ");
        String newTen = scanner.nextLine().trim();
        if ("0".equals(newTen)) return false;
        if (!newTen.isEmpty()) this.ten = newTen;

        System.out.print("→ Sửa giới tính: ");
        String newGioiTinh = scanner.nextLine().trim();
        if ("0".equals(newGioiTinh)) return false;
        if (!newGioiTinh.isEmpty()) {
            String lower = newGioiTinh.toLowerCase();
            if (lower.equals("nam")) {
                this.gioiTinh = "Nam";
            } else if (lower.equals("nữ") || lower.equals("nu") || lower.equals("nư")) {
                this.gioiTinh = "Nữ";
            } else {
                System.out.println("❌ Giới tính không hợp lệ! Giữ nguyên: " + this.gioiTinh);
            }
        }

        String ngaySinhHienTai = (this.ngaySinh != null) ? 
                            this.ngaySinh.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : 
                            "(Chưa có)";
        System.out.print("→ Sửa ngày sinh [" + ngaySinhHienTai + "] (dd/MM/yyyy): ");
        String newNgaySinh = scanner.nextLine().trim();
        if ("0".equals(newNgaySinh)) return false;
        if (!newNgaySinh.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate ngaySinh = LocalDate.parse(newNgaySinh, formatter);
                
                if (ngaySinh.isAfter(LocalDate.now())) {
                    System.out.println("❌ Ngày sinh không hợp lệ! Giữ nguyên.");
                } else {
                    this.ngaySinh = ngaySinh;
                }
            } catch (DateTimeParseException e) {
                System.out.println("❌ Sai định dạng! Giữ nguyên ngày sinh.");
            }
        }

        String diaChiHienTai = (this.diaChi != null && !this.diaChi.isEmpty()) ? 
                        this.diaChi : "(Chưa có)";
        System.out.print("→ Sửa địa chỉ [" + diaChiHienTai + "]: ");
        String newDiaChi = scanner.nextLine().trim();
        if ("0".equals(newDiaChi)) return false;
        if (!newDiaChi.isEmpty()) this.diaChi = newDiaChi;

        System.out.print("→ Sửa số điện thoại [" + this.dienThoai + "] (10 số): ");
        String newSdt = scanner.nextLine().trim();
        if ("0".equals(newSdt)) return false;
        if (!newSdt.isEmpty()) {
            if (newSdt.matches("\\d{10}")) {
                this.dienThoai = newSdt;
            } else {
                System.out.println("❌ Số điện thoại không hợp lệ! Giữ nguyên: " + this.dienThoai);
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return String.format("| MA : %s | HO : %s | TEN : %s | GIOI TINH : %s | NGAY SINH : %s | DIA CHI : %s | DIEN THOAI : %s",
                this.maKH,
                this.ho,
                this.ten,
                this.gioiTinh,
                this.ngaySinh.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                this.diaChi,
                this.dienThoai
        );
    }
}