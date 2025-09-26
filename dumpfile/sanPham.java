import java.util.Scanner;

public class sanPham {
    protected String maSP, tenSP;
    protected int soLuong;
    protected double gia;
    Scanner sc = new Scanner(System.in);

    public sanPham() {

    }

    public sanPham(String maSP, String tenSP, int soLuong, double gia) {
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.tenSP = tenSP;
        this.gia = gia;
    }

    public String getMaSP() {
        return this.maSP;
    }

    public void setMaSP(String newMaSP) {
        this.maSP = newMaSP;
    }

    public String getTenSP() {
        return this.tenSP;
    }

    public void setTenSP(String newTenSP) {
        this.tenSP = newTenSP;
    }

    public int getSL() {
        return this.soLuong;
    }

    public void setSL(int newSL) {
        this.soLuong = newSL;
    }

    public double getGia() {
        return this.gia;
    }

    public void setGia(double newGia) {
        this.gia = newGia;
    }

    public void nhap() {

        System.out.print("nhap ma SP: ");
        maSP = sc.nextLine();
        System.out.print("nhap ten SP: ");
        tenSP = sc.nextLine();

        System.out.print("nhap gia: ");
        gia = sc.nextDouble();
    }

    public void xuat() {
        System.out
                .println("| MA : " + maSP + " | TEN : " + tenSP + "| SO LUONG : " + soLuong + " | GIA: " + gia
                        + "00 ngan dong");
    }

    public void xuatHDon() {
        System.out
                .println("| MA : " + maSP + " | TEN : " + tenSP + "| SO LUONG : " + soLuong + " | GIA: " + gia
                        + "00 ngan dong" + "| TONG TIEN: " + tinhTongTien() + "00 ngan dong");
    }

    @Override
    public String toString() {
        return String.format("| MA : %s | TEN : %s | SO LUONG : %d | GIA: %.2f ngan dong | TONG TIEN: %.2f ngan dong",
                this.maSP,
                this.tenSP,
                this.soLuong,
                this.gia,
                this.tinhTongTien());
    }

    public double tinhTongTien() {
        return soLuong * gia;
    }

}