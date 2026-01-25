package htsc.src_BE.dto;

import java.time.LocalDate;

public class HangHoaDTO {
    private String maHang;
    private String maSP;
    private int soLuonngConLai;
    private LocalDate ngaySanXuat;
    private LocalDate hanSuDung;
    private String trangThai;

    public HangHoaDTO(String maHang, String maSP, int soLuonngConLai, LocalDate ngaySanXuat, LocalDate hanSuDung, String trangThai) {
        this.maHang = maHang;
        this.maSP = maSP;
        this.soLuonngConLai = soLuonngConLai;
        this.ngaySanXuat = ngaySanXuat;
        this.hanSuDung = hanSuDung;
        this.trangThai = trangThai;
    }
    public String getMaHang() {
        return maHang;
    }   
    public String getMaSP() {
        return maSP;
    }
    public int getSoLuongConLai() {
        return soLuonngConLai;
    }
    public LocalDate getNgaySanXuat() {
        return ngaySanXuat;
    }
    public LocalDate getHanSuDung() {
        return hanSuDung;
    }
    public String getTrangThai() {
        return trangThai;
    }
    public void setSoLuongConLai(int soLuongConLai) {
        this.soLuonngConLai = soLuongConLai;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }
    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }
    public void setNgaySanXuat(LocalDate ngaySanXuat) {
        this.ngaySanXuat = ngaySanXuat;
    }
    public void setHanSuDung(LocalDate hanSuDung) {
        this.hanSuDung = hanSuDung;
    }
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
}
