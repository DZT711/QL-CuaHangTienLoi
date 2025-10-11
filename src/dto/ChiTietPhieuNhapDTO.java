package dto;

public class ChiTietPhieuNhapDTO {
    private String maPhieu, maSP;
    private int soLuong, giaNhap;

    public ChiTietPhieuNhapDTO() {}
    
    public ChiTietPhieuNhapDTO(String maPhieu, String maSP, int soLuong, int giaNhap) {
        this.maPhieu = maPhieu;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
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
    
    public int getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(int giaNhap) {
        this.giaNhap = giaNhap;
    }
}