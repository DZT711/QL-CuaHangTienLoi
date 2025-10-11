package dto;

import java.time.LocalDateTime;

public class NhapHangDTO {
    private String maPhieu, maNCC, maNV;
    private int tongTien;
    private LocalDateTime ngayLapPhieu;

    public NhapHangDTO() {}

    public NhapHangDTO(String maPhieu, String maNCC, String maNV, int tongTien, LocalDateTime ngayLapPhieu) {
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

    public LocalDateTime getNgayLapPhieu() {
        return ngayLapPhieu;
    }

    public void setNgayLapPhieu(LocalDateTime ngayLapPhieu) {
        this.ngayLapPhieu = ngayLapPhieu;
    }   
}
