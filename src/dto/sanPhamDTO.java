package dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class sanPhamDTO {
    private String maSP;
    private String tenSP;
    private int loaiSP;
    private int donViTinh;
    private int soLuongTon; 
    private int giaBan;
    private LocalDate ngaySanXuat;
    private int hSD;
    private String moTa;
    private String trangThai;
    public sanPhamDTO() {}

    public sanPhamDTO(String maSP, String tenSP, int loaiSP, int donViTinh, int soLuongTon, int giaBan, LocalDate ngaySanXuat, int hSD, String moTa, String trangThai) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.loaiSP = loaiSP;
        this.donViTinh = donViTinh;
        this.soLuongTon = soLuongTon;
        this.giaBan = giaBan;
        this.ngaySanXuat = ngaySanXuat;
        this.hSD = hSD;
        this.moTa = moTa;
        this.trangThai = trangThai;
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
    
    public int getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(int loaiSP) {
        this.loaiSP = loaiSP;
    }
    
    public int getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(int donViTinh) {
        this.donViTinh = donViTinh;
    }
    
    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }
    
    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public LocalDate getNgaySanXuat() {
        return ngaySanXuat;
    }

    public void setNgaySanXuat(LocalDate ngaySanXuat) {
        this.ngaySanXuat = ngaySanXuat;
    }
    
    public int getHSD() {
        return hSD;
    }

    public void setHSD(int hSD) {
        this.hSD = hSD;
    }
                    
    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public void inthongTinSanPham() {
        String hsdStr = String.format("%08d", hSD);
        hsdStr = hsdStr.substring(0, 2) + "/" + hsdStr.substring(2, 4) + "/" + hsdStr.substring(4, 8);

        System.out.printf("%-10s | %-20s | %-10s | %-10s | %-10s | %-10s | %-15s | %-10s | %-20s\n",
                maSP, tenSP, loaiSP, donViTinh, soLuongTon, giaBan, ngaySanXuat.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), hsdStr, moTa, trangThai);
        
    }


    public boolean sua() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sửa tên sản phẩm: ");
        String newTenSP = scanner.nextLine().trim();
        if (newTenSP.equals("0")) return false;
        if (!newTenSP.isEmpty()) this.tenSP = newTenSP;

        System.out.println("Sửa loại sản phẩm: ");
        String inputLoai = scanner.nextLine().trim();
        if (inputLoai.equals("0")) return false;
        if (!inputLoai.isEmpty()) {
            try {
                int newLoaiSP = Integer.parseInt(inputLoai);
                if (newLoaiSP > 0) this.loaiSP = newLoaiSP;
            } catch (NumberFormatException e) {
                System.out.println("Giá trị không hợp lệ, giữ nguyên loại sản phẩm");
            }
        }

        System.out.println("Sửa đơn vị tính: ");
        String inputDonVi = scanner.nextLine().trim();
        if (inputDonVi.equals("0")) return false;
        if (!inputDonVi.isEmpty()) {
            try {
                int newDonVi = Integer.parseInt(inputDonVi);
                if (newDonVi > 0) this.donViTinh = newDonVi;
            } catch (NumberFormatException e) {
                System.out.println("Giá trị không hợp lệ, giữ nguyên đơn vị tính");
            }
        }

        System.out.println("Sửa giá: ");
        String inputGia = scanner.nextLine().trim();
        if (inputGia.equals("0")) return false;
        if (!inputGia.isEmpty()) {
            try {
                int newGia = Integer.parseInt(inputGia);
                if (newGia > 0) this.giaBan = newGia;
            } catch (NumberFormatException e) {
                System.out.println("Giá trị không hợp lệ, giữ nguyên giá");
            }
        }
        
        System.out.println("Sửa ngày sản xuất: ");
        String inputNSX = scanner.nextLine().trim();
        if (inputNSX.equals("0")) return false;
        if (!inputNSX.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate newNgaySanXuat = LocalDate.parse(inputNSX, formatter);
                this.ngaySanXuat = newNgaySanXuat;
            } catch (Exception e) {
                System.out.println("Ngày sản xuất không hợp lệ, giữ nguyên ngày sản xuất");
            }
        }

        System.out.println("Sửa hạn sử dụng: ");
        String inputHSD = scanner.nextLine().trim();
        if (inputHSD.equals("0")) return false;
        if (!inputHSD.isEmpty()) {
            try {
                int newHSD = Integer.parseInt(inputHSD);
                if (newHSD > 0) this.hSD = newHSD;
            } catch (NumberFormatException e) {
                System.out.println("Giá trị không hợp lệ, giữ nguyên hạn sử dụng");
            }
        }

        System.out.println("Sửa mô tả: ");
        String newMoTa = scanner.nextLine().trim();
        if (newMoTa.equals("0")) return false;
        if (!newMoTa.isEmpty()) this.moTa = newMoTa;

        System.out.println("Sửa trạng thái: ");
        String newTrangThai = scanner.nextLine().trim();
        if (newTrangThai.equals("0")) return false;
        if (!newTrangThai.isEmpty()) this.trangThai = newTrangThai;

        return true;
    }
}
