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

    public void nhapThongTinKhachHang() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập họ khách hàng: ");
        this.ho = scanner.nextLine().trim();
        while (this.ho.isEmpty()) {
            System.out.println("Họ khách hàng không được để trống, vui lòng nhập lại.");
            System.out.print("Nhập họ khách hàng: ");
            this.ho = scanner.nextLine().trim();
        }

        System.out.print("Nhập tên khách hàng: ");
        this.ten = scanner.nextLine().trim();
        while (this.ten.isEmpty()) {
            System.out.println("Tên khách hàng không được để trống, vui lòng nhập lại.");
            System.out.print("Nhập tên khách hàng: ");
            this.ten = scanner.nextLine().trim();
        }

        System.out.print("Nhập giới tính khách hàng (Nam/Nữ): ");
        this.gioiTinh = scanner.nextLine().trim();
        while (!this.gioiTinh.equalsIgnoreCase("Nam") && !this.gioiTinh.equalsIgnoreCase("Nữ")) {
            System.out.println("Giới tính không hợp lệ, vui lòng nhập lại (Nam/Nữ): ");
            this.gioiTinh = scanner.nextLine().trim();
        }

        while (true) {
            try {
                System.out.print("Nhập ngày sinh khách hàng (dd/mm/yyyy): ");
                String ngaySinh = scanner.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                this.ngaySinh = LocalDate.parse(ngaySinh, formatter);

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
        this.diaChi = scanner.nextLine();

        while (this.diaChi.isEmpty()) {
            System.out.println("Địa chỉ khách hàng không được để trống, vui lòng nhập lại.");
            System.out.print("Nhập địa chỉ khách hàng: ");
            this.diaChi = scanner.nextLine().trim();
        }

        System.out.print("Nhập điện thoại khách hàng (10 số): ");
        this.dienThoai = scanner.nextLine();
        while (true) {
            this.dienThoai = scanner.nextLine().trim();
            if (this.dienThoai.matches("\\d{10}")) {
                break;
            } else {
                System.out.print("Số điện thoại không hợp lệ, vui lòng nhập lại (10 số): ");
            }
        }
    }
}