package dto;

public class LoaiSPDTO {
    private String maLoai;
    private String tenLoai;
    private String moTa;

    public LoaiSPDTO() {}

    public LoaiSPDTO(String maLoai, String tenLoai, String moTa) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.moTa = moTa;
    }
    
    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }
    
    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getMoTa() {
        return moTa;
    }
    
    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}