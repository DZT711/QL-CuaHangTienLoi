package dto;

public class ChiTietPhieuNhapDTO {
    private String maPhieu, maSP;
    private int soLuong, donGia;

    public ChiTietPhieuNhapDTO() {}
    
    public ChiTietPhieuNhapDTO(String maPhieu, String maSP, int soLuong, int donGia) {
        this.maPhieu = maPhieu;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }
    
    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getMaSP() {
        return maSP;
    }
    
    public void setMaSP(String maSP) {
        this.maSP = maSP;
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
}