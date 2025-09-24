import java.util.Scanner;

public class nhanVien {
    String tenNV;
    String maNV;
    double luong;

    public nhanVien(String tenNV, String maNV, double luong) {
        this.tenNV = tenNV;
        this.maNV = maNV;
        this.luong = luong;
    }

    public void nhap() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("NHAP TEN NHAN VIEN: ");
        tenNV = scanner.next();
        System.out.print("NHAP MA NHAN VIEN: ");
        maNV = scanner.next();
        System.out.print("NHAP lUONG CUA NHAN VIEN: ");
        luong = scanner.nextDouble();
    }

    public void xuat() {
        System.out.println("TEN NHAN VIEN : " + tenNV);
        System.out.println("MA NHAN VIEN : " + maNV);
        System.out.println("LUONG : " + luong);
    }

    public String getTenNV() {
        return tenNV;
    }

    public String getMaNV() {
        return maNV;
    }

    public double getLuong() {
        return luong;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public void setLuong(double luong) {
        this.luong = luong;
    }
}
