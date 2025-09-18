package dto;

public class sanPhamDTO {
    private String maSP;
    private String tenSP;
    private String loaiSP;
    // private String donViTinh;
    private int soLuongTon;
    private int gia;
    private int hSD;
    private String moTa;
    
    public sanPhamDTO() {}

    public sanPhamDTO(String maSP, String tenSP, String loaiSP, int soLuongTon, int gia, int hSD, String moTa) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.loaiSP = loaiSP;
        this.soLuongTon = soLuongTon;
        this.gia = gia;
        this.hSD = hSD;
        this.moTa = moTa;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }
    
    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }
    
    public String getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(String loaiSP) {
        this.loaiSP = loaiSP;
    }
    
    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }
    
    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }
    
    public int getHSD() {
        return hSD;
    }

    public void setHSD(int hSD) {
        this.hSD = hSD;
    }
                    
    
}