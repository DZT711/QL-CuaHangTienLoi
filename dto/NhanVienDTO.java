package dto;

public class NhanVienDTO {
    private String maNV;
    private String ho;
    private String ten;
    private String gioiTinh;
    private Date ngaySinh;
    private String diaChi;
    private String email;
    private int luong;
    private String chucVu;
    // private String trangThai;
    // private String matKhau;

    public NhanVienDTO() {}

    public NhanVienDTO(String maNV, String ho, String ten, String gioiTinh, Date ngaySinh, String diaChi, String email, int luong, String chucVu) {
        this.maNV = maNV;
        this.ho = ho;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.email = email;
        this.luong = luong;
        this.chucVu = chucVu;
    }

    public String getFullName() {
        return ho + " " + ten;
    }
    
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
    
    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }
    
    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
    
    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
    
    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
    
    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    
    public int getLuong() {
        return luong;
    }

    public void setLuong(int luong) {
        this.luong = luong;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }
}