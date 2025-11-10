package dto;

public class ChiTietPhieuNhapDTO {
    private String maPhieu, maHang, tenSP, donViTinh;
    private int soLuong, giaNhap, thanhTien;

    public ChiTietPhieuNhapDTO() {}
    
    public ChiTietPhieuNhapDTO(String maPhieu, String maHang, String tenSP, String donViTinh, int soLuong, int giaNhap, int thanhTien ) {
        this.maPhieu = maPhieu;
        this.maHang = maHang;
        this.tenSP = tenSP;
        this.donViTinh = donViTinh;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.thanhTien = thanhTien;
    }
    
    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    
    public int getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(int giaNhap) {
        this.giaNhap = giaNhap;
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }
}