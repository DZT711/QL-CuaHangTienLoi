import java.util.Scanner;
import java.util.ArrayList;

public class hoaDon {
    private String maHD;
    private static int soHoaDon;
    private khachHang khachhang;
    private nhanVien nhanvien;

    public hoaDon() {
    }

    public hoaDon(String maHD, khachHang khachhang, nhanVien nhanvien) {
        this.maHD = maHD;
        this.khachhang = khachhang;
        this.nhanvien = nhanvien;
        soHoaDon++;
    }

    public nhanVien getNhanvien() {
        return nhanvien;
    }

    public String getMaHD() {
        return maHD;
    }

    public khachHang getKhachhang() {
        return khachhang;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public static int getSoHoaDon() {
        return soHoaDon;
    }

    public void nhap() {
        Scanner sc = new Scanner(System.in);
        System.out.println("nhap ma hoa don");
        maHD = sc.nextLine();
    }

    public void xuat() {
        System.out.println("| MA HOA DON lA : " + maHD + "");
    }

    public void printChiTietHoaDon() {
        System.out.println("Ma hoa don: " + maHD);
        System.out.println("Ma nhan vien in hoa don " + nhanvien.getMaNV());
        System.out.println("Ten nhan vien in hoa don: " + nhanvien.getTenNV());
        System.out.println("Ma khach hang: " + khachhang.getMaKH());
        System.out.println("Ten khach hang: " + khachhang.getTenKH());
        System.out.println("Danh sach san pham: ");
        for (sanPham sp : khachhang.getDSSP()) {
            sp.xuatHDon();
        }

        Scanner sc = new Scanner(System.in);
        sc.nextLine();

        System.out.println("Tiep tuc chuong trinh...");
    }

    @Override
    public String toString() {
        String result = "Ma hoa don: " + maHD + "\n";
        result += "Ma nhan vien tao hoa don: " + nhanvien.getMaNV() + "\n";
        result += "Ten nhan vien tao hoa don: " + nhanvien.getTenNV() + "\n";
        result += "Ma khach hang: " + khachhang.getMaKH() + "\n";
        result += "Ten khach hang: " + khachhang.getTenKH() + "\n";
        result += "Danh sach san pham: \n";

        for (sanPham sp : khachhang.getDSSP()) {
            result += sp.toString() + "\n";
        }
        return result;
    }

}
