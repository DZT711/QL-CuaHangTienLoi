// xem xét: giá trị, số lượng 
package dto;

public class KhuyenMaiDTO {
    private String maKM;
    private String tenKM;
    private String maSP;
    private int giaTriKM;
    private int soLuongToiThieu;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String trangThai;

    public KhuyenMaiDTO() {}

    public KhuyenMaiDTO(String maKM, String tenKM, String maSP, String maNV, Date ngayBatDau, Date ngayKetThuc, String trangThai) {
        this.maKM = maKM;
        this.tenKM = tenKM;
        this.maSP = maSP;
        this.maNV = maNV;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
    }

    public String getMaKM() {
        return maKM;
    }
    
    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }
    
    public String getTenKM() {
        return tenKM;
    }
    
    public void setTenKM(String tenKM) {
        this.tenKM = tenKM;
    }
    
    public String getMaSP() {
        return maSP;
    }
    
    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }
    
    public String getMaNV() {
        return maNV;
    }
    
    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
    
    public Date getNgayBatDau() {
        return ngayBatDau;
    }
    
    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }
    
    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }
    
    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
    
    public String getTrangThai() {
        return trangThai;
    }
    
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}