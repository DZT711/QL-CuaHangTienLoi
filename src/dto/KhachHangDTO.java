package dto;

import java.time.LocalDate;
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
    // private String trangThai;
    // private int diem;

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
        
        System.out.print("Nhập họ khách hàng: ");
        String input = scanner.nextLine().trim();
        if (input.equals("0")) return false;
        while (input.isEmpty()) {
            System.out.println("Họ khách hàng không được để trống, vui lòng nhập lại.");
            System.out.print("Nhập họ khách hàng: ");
            input = scanner.nextLine().trim();
            if (input.equals("0")) return false;
        }
        this.ho = input;

        System.out.print("Nhập tên khách hàng: ");
        input = scanner.nextLine().trim();
        if (input.equals("0")) return false;
        while (input.isEmpty()) {
            System.out.println("Tên khách hàng không được để trống, vui lòng nhập lại.");
            System.out.print("Nhập tên khách hàng: ");
            input = scanner.nextLine().trim();
            if (input.equals("0")) return false;
        }
        this.ten = input;

        System.out.print("Nhập giới tính khách hàng (Nam/Nữ): ");
        input = scanner.nextLine().trim();
        if (input.equals("0")) return false;
        while (!input.equalsIgnoreCase("Nam") && !input.equalsIgnoreCase("Nữ")) {
            System.out.println("Giới tính không hợp lệ, vui lòng nhập lại (Nam/Nữ): ");
            input = scanner.nextLine().trim();
            if (input.equals("0")) return false;
        }
        this.gioiTinh = input;


        while (true) {
            try {
                System.out.print("Nhập ngày sinh khách hàng (dd/mm/yyyy): ");
                input = scanner.nextLine().trim();
                if (input.equals("0")) return false;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                this.ngaySinh = LocalDate.parse(input, formatter);

                if (this.ngaySinh.isAfter(LocalDate.now())) {
                    System.out.println("Ngày sinh không được lớn hơn ngày hiện tại!");
                    continue;
                }
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Sai định dạng! Vui lòng nhập lại (dd/mm/yyyy).");
            }
        }

        System.out.print("Nhập địa chỉ khách hàng: ");
        input = scanner.nextLine().trim();
        if (input.equals("0")) return false;
        while (input.isEmpty()) {
            System.out.println("Địa chỉ khách hàng không được để trống, vui lòng nhập lại.");
            System.out.print("Nhập địa chỉ khách hàng: ");
            input = scanner.nextLine().trim();
            if (input.equals("0")) return false;
        }
        this.diaChi = input;
        

        System.out.print("Nhập điện thoại khách hàng (10 số): ");
        input = scanner.nextLine().trim();
        if (input.equals("0")) return false;
        while (!input.matches("\\d{10}")) {
            System.out.println("Số điện thoại không hợp lệ, vui lòng nhập lại (10 số): ");
            input = scanner.nextLine().trim();
            if (input.equals("0")) return false;
        }
        this.dienThoai = input;
        return true;
    }

    public boolean suaThongTinKhachHang() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sửa họ: ");
        String newHo = scanner.nextLine().trim();
        if (newHo.equals("0")) return false;
        if (!newHo.isEmpty()) this.ho = newHo;

        System.out.println("Sửa tên: ");
        String newTen = scanner.nextLine().trim();
        if (newTen.equals("0")) return false;
        if (!newTen.isEmpty()) this.ten = newTen;

        // Sửa ngày sinh ?

        System.out.println("Sửa địa chỉ: ");
        String newDiaChi = scanner.nextLine().trim();
        if (newDiaChi.equals("0")) return false;
        if (!newDiaChi.isEmpty()) this.diaChi = newDiaChi;

        System.out.println("Sửa số điện thoại: ");
        String newSdt = scanner.nextLine().trim();
        if (newSdt.equals("0")) return false;
        if(!newSdt.isEmpty()) this.dienThoai = newSdt;
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