package dto;

import java.util.Scanner;
import dao.SanPhamDAO;

public class ChiTietPhieuNhapDTO {
    private String maPhieu, maHang, tenSP, donViTinh;
    private int soLuong, giaNhap, thanhTien;

    public ChiTietPhieuNhapDTO() {}
    
    public ChiTietPhieuNhapDTO(String maPhieu, String maHang, String tenSP, String donViTinh, int soLuong, int giaNhap, int thanhTien ) {
        this.maPhieu = maPhieu;
        this.maHang = maHang;
        this.tenSP = tenSP;
        this.donViTinh = donViTinh;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.thanhTien = thanhTien;
    }
    
    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    
    public int getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(int giaNhap) {
        this.giaNhap = giaNhap;
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }

    public boolean nhapChiTietPhieuNhap(Scanner scanner, String maPhieu, String maSP, String maNCC, String tenNCC) {
        if ("0".equals(maSP)) return false;

        if (maSP.isEmpty()) {
            System.out.println("❌ Mã sản phẩm không được để trống!");
            return true;
        }

        SanPhamDTO sp = SanPhamDAO.timSanPhamTheoMa(maSP);
        if (sp == null) {
            System.out.println("❌ Sản phẩm không tồn tại!");
            return true;
        }

        System.out.println("✅ Sản phẩm: " + sp.getTenSP());

        if (!SanPhamDAO.kiemTraNCCCungCapSP(maNCC, maSP)) {
            System.out.println("\n⚠️ Nhà cung cấp '" + tenNCC + "' không cung cấp sản phẩm này!");
            System.out.print("→ Bạn có chắc muốn tiếp tục? (Y/N): ");
            if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                System.out.println("⚠️  Đã bỏ qua sản phẩm này.\n");
                return true;
            }
        }

        System.out.print("→ Nhập số lượng: ");
        int soLuong;
        try {
            soLuong = Integer.parseInt(scanner.nextLine().trim());
            if (soLuong <= 0 || soLuong > 5000) {
                System.out.println("❌ Số lượng không hợp lệ! (1-5000)");
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Vui lòng nhập số hợp lệ cho số lượng!");
            return true;
        }

        System.out.print("→ Nhập giá nhập: ");
        int giaNhap;
        try {
            giaNhap = Integer.parseInt(scanner.nextLine().trim());
            if (giaNhap <= 0) {
                System.out.println("❌ Giá nhập phải lớn hơn 0!");
                return true;
            }
            if (giaNhap > 1_000_000) {
                System.out.println("❌ Giá nhập không được vượt quá 1.000.000!");
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Vui lòng nhập số hợp lệ cho giá nhập!");
            return true;
        }

        this.maPhieu = maPhieu;
        this.maHang = null;
        this.tenSP = sp.getTenSP();
        this.donViTinh = sp.getDonViText();
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.thanhTien = soLuong * giaNhap;
        
        return true;
    }
}