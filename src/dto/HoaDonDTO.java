//  xem xét: tiền giảm 

package dto;

import java.time.LocalDateTime;

public class HoaDonDTO {
    private String maHD;
    private String maKH;
    private String maNV;
    private int tongTien;
    private LocalDateTime ngayLapHD;
    private String phuongThucTT;
    public HoaDonDTO() {}
    
    public HoaDonDTO(String maHD, String maKH, String maNV, int tongTien, LocalDateTime ngayLapHD, String phuongThucTT) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.maNV = maNV;
        this.tongTien = tongTien;
        this.ngayLapHD = ngayLapHD;
        this.phuongThucTT = phuongThucTT;
    }
    
    public String getMaHD() {
        return maHD;
    }
    
    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }
    
    public String getMaKH() {
        return maKH;
    }
    
    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }
    
    public String getMaNV() {
        return maNV;
    }
    
    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
    
    public String getPhuongThucTT() {
        return phuongThucTT;
    }
    
    public void setPhuongThucTT(String phuongThucTT) {
        this.phuongThucTT = phuongThucTT;
    }
    
    public LocalDateTime getNgayLapHD() {
        return ngayLapHD;
    }
    
    public void setNgayLapHD(LocalDateTime ngayLapHD) {
        this.ngayLapHD = ngayLapHD;
    }
    
    public int getTongTien() {
        return tongTien;
    }
    
    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}