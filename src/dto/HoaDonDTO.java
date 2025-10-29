//  xem xét: tiền giảm 

package dto;

import java.time.LocalDateTime;

public class HoaDonDTO {
    private String maHD;
    private String maKH;
    private String maNV;
    private int tienKhachDua;
    private int tienThua;
    private int tongTien;
    private LocalDateTime ngayLapHD;
    private String phuongThucTT;
    private String trangThai;
    public HoaDonDTO() {}

    public HoaDonDTO(String maHD, String maKH, String maNV, int tienKhachDua, int tienThua, int tongTien, LocalDateTime ngayLapHD, String phuongThucTT, String trangThai) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.maNV = maNV;
        this.tienKhachDua = tienKhachDua;
        this.tienThua = tienThua;
        this.tongTien = tongTien;
        this.ngayLapHD = ngayLapHD;
        this.phuongThucTT = phuongThucTT;
        this.trangThai = trangThai;
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
    
    public int getTienKhachDua() {
        return tienKhachDua;
    }
    
    public void setTienKhachDua(int tienKhachDua) {
        this.tienKhachDua = tienKhachDua;
    }
    
    public int getTienThua() {
        return tienThua;
    }
    
    public void setTienThua(int tienThua) {
        this.tienThua = tienThua;
    }
    
    public int getTongTien() {
        return tongTien;
    }
    
    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}