import java.util.Scanner;

public class doUong extends sanPham {
    private double dungTich;

    Scanner sc = new Scanner(System.in);

    public doUong() {
    }

    public doUong(double dungTich, String maSp, String tenSP, int soLuong, double gia) {
        super(maSp, tenSP, soLuong, gia);
        this.dungTich = dungTich;

    }

    public double getDungTich() {
        return dungTich;
    }

    public void setDungTich(double newDungTich) {
        this.dungTich = newDungTich;
    }

    public void nhap() {

        super.nhap();
        System.out.print("nhap dung tich: ");
        dungTich = sc.nextDouble();
    }

    public void xuat() {

        super.xuat();

        System.out.println("| DUNG TICH : " + dungTich + " ml");
        System.out.println("|________________________________________________________");
    }

}
