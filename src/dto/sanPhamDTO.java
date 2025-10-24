package dto;

import java.util.Scanner;
import dao.SanPhamDAO;
import util.FormatUtil;

public class SanPhamDTO {
    private String maSP;
    private String tenSP;
    private int loaiSP;
    private int donViTinh;
    private int soLuongTon; 
    private int giaBan;
    private String moTa;
    private String trangThai;
    public SanPhamDTO() {}

    public SanPhamDTO(String maSP, String tenSP, int loaiSP, int donViTinh, int soLuongTon, int giaBan, String moTa, String trangThai) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.loaiSP = loaiSP;
        this.donViTinh = donViTinh;
        this.soLuongTon = soLuongTon;
        this.giaBan = giaBan;
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

    public boolean nhapThongTinSanPham() {
        Scanner scanner = new Scanner(System.in);
        
        try {
            this.maSP = SanPhamDAO.generateMaSP();
            System.out.println("Mã sản phẩm tự động: " + this.maSP);

            System.out.print("Nhập tên sản phẩm (hoặc '0' để hủy): ");
            this.tenSP = scanner.nextLine().trim();
            if ("0".equals(this.tenSP) || this.tenSP.isEmpty()) {
                System.out.println("❌ Tên sản phẩm không được để trống!");
                return false;
            }


            while (true) {
                System.out.print("Nhập loại sản phẩm ('0' để hủy): ");
                String input = scanner.nextLine().trim();
                if ("0".equals(input)) return false;
                
                try {
                    this.loaiSP = Integer.parseInt(input);
                    if (this.loaiSP >= 1 && this.loaiSP <= 10) break;
                    else System.out.println("❌ Loại sản phẩm phải từ 1-10!");
                } catch (NumberFormatException e) {
                    System.out.println("❌ Vui lòng nhập số!");
                }
            }

            // Nhập đơn vị tính (1-11)
            while (true) {
                System.out.print("Nhập đơn vị tính ('0' để hủy): ");
                String input = scanner.nextLine().trim();
                if ("0".equals(input)) return false;
                
                try {
                    this.donViTinh = Integer.parseInt(input);
                    if (this.donViTinh >= 1 && this.donViTinh <= 11) break;
                    else System.out.println("❌ Đơn vị tính phải từ 1-11!");
                } catch (NumberFormatException e) {
                    System.out.println("❌ Vui lòng nhập số!");
                }
            }

            // Nhập giá bán (phải > 0)
            while (true) {
                System.out.print("Nhập giá bán (hoặc '0' để hủy): ");
                String input = scanner.nextLine().trim();
                if ("0".equals(input)) return false;
                
                try {
                    this.giaBan = Integer.parseInt(input);
                    if (this.giaBan > 0) break;
                    else System.out.println("❌ Giá bán phải lớn hơn 0!");
                } catch (NumberFormatException e) {
                    System.out.println("❌ Vui lòng nhập số!");
                }
            }
            return true; // Nhập thành công
        } catch (Exception e) {
            System.err.println("❌ Lỗi không mong muốn: " + e.getMessage());
            return false;
        }
    }

    public void inThongTinSanPham() {
        System.out.printf("%-10s | %-20s | %-10d | %-10d | %-10d | %-15s | %-20s | %-10s%n",
                maSP, tenSP, loaiSP, donViTinh, soLuongTon, FormatUtil.formatVND(giaBan), moTa, trangThai);
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
