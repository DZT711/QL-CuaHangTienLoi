package htsc.src_BE.dto;

import java.util.Scanner;
import htsc.src_BE.dao.SanPhamDAO;

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
            System.out.println("❌ Không tìm thấy sản phẩm với mã: " + maSP);
            return true;
        }

        if ("inactive".equalsIgnoreCase(sp.getTrangThai())) {
            System.out.println("❌ Sản phẩm này đã ngừng kinh doanh!");
            System.out.println("💡 Không thể nhập hàng cho sản phẩm ngừng kinh doanh.");
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

        int soLuong;
        while (true) {
            System.out.print("→ Nhập số lượng: ");
            try {
                soLuong = Integer.parseInt(scanner.nextLine().trim());
                
                if (soLuong > 0 && soLuong <= 5000)  break;
                System.out.println("❌ Số lượng không hợp lệ! (1-5000)");

            } catch (NumberFormatException e) {
                System.out.println("❌ Vui lòng nhập số hợp lệ cho số lượng!");
            }
        }

        int giaNhap;
        while (true) {
            System.out.print("→ Nhập giá nhập: ");
            try {
                giaNhap = Integer.parseInt(scanner.nextLine().trim());
                if (giaNhap > 0 && giaNhap <= 1_000_000) break;
                System.out.println("❌ Giá nhập không hợp lệ!");
            } catch (NumberFormatException e) {
                System.out.println("❌ Vui lòng nhập số hợp lệ cho giá nhập!");
            }
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