package dto;

import java.util.Date;

public class PhieuNhapDTO {
    private String maPhieu, maNCC, maNV;
    private int tongTien;
    private Date ngayLapPhieu;

    public PhieuNhapDTO() {}

    public PhieuNhapDTO(String maPhieu, String maNCC, String maNV, int tongTien, Date ngayLapPhieu) {
        this.maPhieu = maPhieu;
        this.maNCC = maNCC;
        this.maNV = maNV;
        this.tongTien = tongTien;
        this.ngayLapPhieu = ngayLapPhieu;
    }
    
    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public Date getNgayLapPhieu() {
        return ngayLapPhieu;
    }
    
    public void setNgayLapPhieu(Date ngayLapPhieu) {
        this.ngayLapPhieu = ngayLapPhieu;
    }
}