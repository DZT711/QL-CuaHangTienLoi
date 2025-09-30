//  xem xét: tiền giảm 

package dto;

import java.util.Date;

public class HoaDonDTO {
    private String maHD;
    private String maKH;
    private String maNV;
    private String maKM;
    private int tongTien;
    private Date ngayLapHD;

    public HoaDonDTO() {}
    
    public HoaDonDTO(String maHD, String maKH, String maNV, String maKM, int tongTien, Date ngayLapHD) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.maNV = maNV;
        this.maKM = maKM;
        this.tongTien = tongTien;
        this.ngayLapHD = ngayLapHD;
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
    
    public String getMaKM() {
        return maKM;
    }
    
    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }
    
    public Date getNgayLapHD() {
        return ngayLapHD;
    }
    
    public void setNgayLapHD(Date ngayLapHD) {
        this.ngayLapHD = ngayLapHD;
    }
    
    public int getTongTien() {
        return tongTien;
    }
    
    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}