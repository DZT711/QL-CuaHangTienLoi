
package dto;

import java.util.Scanner;

public class sanPhamDTO {
    private String maSP;
    private String tenSP;
    private String loaiSP;
    private int donViTinh;
    private int soLuongTon; 
    private int gia;
    private int hSD;
    private String moTa;
    
    public sanPhamDTO() {}

    public sanPhamDTO(String maSP, String tenSP, String loaiSP, int donViTinh, int soLuongTon, int gia, int hSD, String moTa) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.loaiSP = loaiSP;
        this.donViTinh = donViTinh;
        this.soLuongTon = soLuongTon;
        this.gia = gia;
        this.hSD = hSD;
        this.moTa = moTa;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }
    
    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }
    
    public String getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(String loaiSP) {
        this.loaiSP = loaiSP;
    }
    
    public int getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(int donViTinh) {
        this.donViTinh = donViTinh;
    }
    
    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }
    
    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }
    
    public int getHSD() {
        return hSD;
    }

    public void setHSD(int hSD) {
        this.hSD = hSD;
    }
                    
    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void nhap() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập mã sản phẩm: ");
        this.maSP = scanner.nextLine().trim();
        scanner.nextLine();

        System.out.print("Nhập tên sản phẩm: ");
        this.tenSP = scanner.nextLine().trim();
        
        System.out.print("Nhập số lượng tồn: ");
        this.soLuongTon = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nhập giá: ");
        this.gia = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nhập hạn sử dụng: ");
        this.hSD = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nhập mô tả: ");
        this.moTa = scanner.nextLine().trim();
    }
}
