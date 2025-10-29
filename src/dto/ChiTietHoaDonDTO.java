package dto;

public class ChiTietHoaDonDTO {
    private String maHD;
    private String maHang;
    private String tenSP;
    private int soLuong;
    private int donGia;
    private int thanhTien;

    public ChiTietHoaDonDTO() {}

    public ChiTietHoaDonDTO(String maHD, String maHang, String tenSP, int soLuong, int donGia, int thanhTien) {
        this.maHD = maHD;
        this.maHang = maHang;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }
    
    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
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
    
    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    
    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }   

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }

    public void inChiTietHoaDon() {
        String tenSPDisplay = (tenSP != null && tenSP.length() > 25) ? 
            tenSP.substring(0, 22) + "..." : (tenSP != null ? tenSP : "");
        
        System.out.printf("%-12s %-28s %8d %,15d %,15d%n",
            maHang != null ? maHang : "",
            tenSPDisplay,
            soLuong,
            donGia,
            thanhTien
        );
    }
}