import java.util.ArrayList;
import java.util.Scanner;

public class khachHang {
    private String maKH, tenKH;
    private ArrayList<sanPham> DSSP = new ArrayList<>();
    public static int ma = 0;
    private double tien;
    // private int soLuongMua;
    Scanner sc = new Scanner(System.in);

    public khachHang(String tenKH, ArrayList<sanPham> DSSP, double tien) {
        this.maKH = "MH" + String.format("%03d", ma);
        this.tenKH = tenKH;
        this.DSSP = new ArrayList<>(DSSP);
        this.tien = tien;
        // this.soLuongMua = soLuongMua;
        ma++;
    }

    // getter & setter
    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public ArrayList<sanPham> getDSSP() {
        return DSSP;
    }

    public void setDSSP(ArrayList<sanPham> dSSP) {
        this.DSSP = new ArrayList<>(DSSP);
    }

    public double getTien() {
        return tien;
    }

    public void setTien(double tien) {
        this.tien = tien;
    }

    // format name's customer
    public static String chuanHoaTen(String s) {
        String[] arr = s.split("\\s+");
        String ten = "";
        for (String x : arr) {
            ten += Character.toUpperCase(x.charAt(0));
            for (int i = 1; i < x.length(); i++)
                ten += Character.toUpperCase(x.charAt(i));
            ten += " ";
        }
        return ten.trim();
    }

    public String toString() {
        return this.getTenKH() + " - " + this.getMaKH();
    }

    // print info's customer
    public String toString(int n) {
        return n + ". " + this.tenKH + " - " + this.maKH;
    }
}

