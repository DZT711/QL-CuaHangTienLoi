import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class DSNV implements IQuanLy {
    public static ArrayList<nhanVien> dsnv = new ArrayList<>();
    private int n;

    public DSNV() {
        n = 0;
    }

    public DSNV(int n) {
        this.n = n;
    }

    /// Ham them nhan vien
    public void them() {
        Scanner scanner = new Scanner(System.in);
        String tenNV = "";
        String maNV = "";
        double luong = 0;
        boolean bool = true;

        nhanVien nv = new nhanVien(tenNV, maNV, luong);
        nv.nhap();

        // Kiem tra trung ma nhan vien
        for (int i = 0; i < n; i++) {
            if (dsnv.get(i).maNV.equals(nv.getMaNV())) {
                System.out.println("MA NHAN VIEN DA TON TAI, VUI LONG NHAP TEN VOI MA MOI 'TEM' VA 'MA' MOI!");
                bool = false;
                them();
            }
        }
        // Cho phep luu khi khong trung ma
        if (bool == true) {
            dsnv.add(n, new nhanVien(nv.getTenNV(), nv.getMaNV(), nv.getLuong()));
            n++; // Tang so luong phan tu trong mang DSNV
            System.out.println("THEM NHAN VIEN THANH CONG!");
        }
    }

    /// Ham xoa nhan vien khoi danh sach
    public void xoa() {
        String tenNV;
        String maNV;
        Scanner sc = new Scanner(System.in);

        System.out.println("NHAP TEN NHAN VIEN CAN XOA : ");
        tenNV = sc.nextLine();
        System.out.println("NHAP MA NHAN VIEN CAN XOA : ");
        maNV = sc.nextLine();
        boolean bool = false;

        for (int i = 0; i < n; i++) {
            if (dsnv.get(i).getTenNV().equals(tenNV) && dsnv.get(i).getMaNV().equals(maNV)) {
                dsnv.remove(i);
                bool = true;
                n--;
            }
        }

        if (bool == true)
            System.out.println("XOA THANH CONG!");
        else
            System.out.println("MA HOAC TEN NHAN VIEN KHONG TON TAI!");
    }

    /// Ham sua ten/ma nhan vien
    public void sua() {
        String tenNV, tenNVMoi;
        String maNV, maNVMoi;
        double luong, luongMoi;
        Scanner sc = new Scanner(System.in);

        System.out.println("NHAP TEN NHAN VIEN CAN SUA : ");
        tenNV = sc.nextLine();
        System.out.println("NHAP MA NHAN VIEN CAN SUA : ");
        maNV = sc.nextLine();

        for (int i = 0; i < n; i++) {
            if (dsnv.get(i).getTenNV().equals(tenNV) && dsnv.get(i).getMaNV().equals(maNV)) {
                System.out.println("NHAP 0 (SUA TEN) - NHAP 1 (SUA MA) - NHAP 2 (SUA LUONG)");
                int type = sc.nextInt();

                if (type == 0) // Sua ten nhan vien
                {
                    System.out.println("NHAP TEN NHAN VIEN MOI: ");
                    tenNVMoi = sc.next();
                    dsnv.get(i).tenNV = tenNVMoi;
                } else if (type == 1) // Sua ma nhan vien
                {
                    System.out.println("NHAP MA NHAN VIEN MOI: ");
                    maNVMoi = sc.next();

                    for (int j = 0; j < n; j++) {
                        if (dsnv.get(j).getMaNV().equals(maNVMoi)) {
                            System.out.println("MA NHAN VIEN DA TON TAI, MOI THAY DOI MA KHAC: ");
                            maNVMoi = sc.next();
                            j = 0;
                        }
                    }
                    dsnv.get(i).maNV = maNVMoi;
                } else if (type == 2) // Sua luong nhan vien
                {
                    System.out.println("NHAP LUONG MOI: ");
                    luongMoi = sc.nextDouble();
                    dsnv.get(i).luong = luongMoi;
                } else
                    System.out.println("LUA CHON KHONG HOP LE. VUI LONG CHON LAI!");
            } else {
                System.out.println("MA HOAC TEN MUON SUA KHONG TON TAI!");
            }
        }
    }

    public void timKiem() {
        boolean found = false;
        int type;
        Scanner scanner = new Scanner(System.in);
        System.out.println("NHAP 0 (TIM THEO TEN) - NHAP 1 (TIM THEO MA)");
        type = scanner.nextInt();

        if (type == 0 || type == 1) {
            switch (type) {
                case 0:
                    System.out.println("NHAP 'TEN NHAN VIEN' CAN TIM KIEM: ");
                    String tenNV = scanner.next();
                    for (int i = 0; i < n; i++) {
                        if (dsnv.get(i).getTenNV().equals(tenNV)) {
                            System.out.println("THONG TIN");
                            dsnv.get(i).xuat();
                            found = true;
                        }
                    }
                    break;
                case 1:
                    System.out.println("NHAP 'MA NHAN VIEN' CAN TIM KIEM: ");
                    String maNV = scanner.next();
                    for (int i = 0; i < n; i++) {
                        if (dsnv.get(i).getMaNV().equals(maNV)) {
                            System.out.println("THONG TIN");
                            dsnv.get(i).xuat();
                            found = true;
                        }
                    }
                    break;
            }
        } else {
            System.out.println("LUA CHON KHONG HOP LE. VUI LONG CHON LAI!");
        }

        if (!found) {
            System.out.println("KHONG TIM THAY");
        }
    }

    public void xemDSNV() {
        if (n == 0) {
            System.out.println("KHONG CO NHAN VIEN NAO.");
        } else {
            for (int i = 0; i < n; i++) {
                dsnv.get(i).xuat();
                System.out.println("________________");
            }
        }
    }

    public void ghiFile() {
        Scanner scanner = new Scanner(System.in);
        String data = null;
        try (BufferedWriter wr = new BufferedWriter(new FileWriter("docFileNV.txt"))) {
            for (int i = 0; i < n; i++) {
                wr.write(String.valueOf("TEN NHAN VIEN: " + dsnv.get(i).tenNV) + " \n");
                wr.write(String.valueOf("MA NHAN VIEN: " + dsnv.get(i).maNV) + " \n");
                wr.write(String.valueOf("LUONG: " + dsnv.get(i).luong) + " \n");
                wr.write(String.valueOf("________________" + " \n"));
            }
        } catch (Exception ex) {
            System.out.println("LOI GHI FILE: " + ex);
        }
    }

    public void docFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("docFileNV.txt"))) {
            String line;
            int index = 0;
            double luong = 0;
            String tenNV = "", maNV = "";
            while ((line = br.readLine()) != null) {

                switch (index) {
                    case 0:
                        tenNV = line;
                        break;
                    case 1:
                        maNV = line;
                        break;
                    case 2:
                        luong = Double.parseDouble(line);
                        break;
                }
                if (index == 2) {
                    dsnv.add(n, new nhanVien(tenNV, maNV, luong));
                    index = 0;
                    n++;
                } else {
                    index++;
                }
            }
            System.out.println("DOC DU LIEU TU FILE THANH CONG!");
            br.close();
        } catch (Exception ex) {
            System.out.println("LOI DOC FILE: " + ex);
        }
    }

    // ham kiem tra nhan vien co ton tai trong danh sach khong
    public static boolean isMaNVTonTai(String maNV) {
        for (nhanVien nv : dsnv) {
            if (nv.getMaNV().equals(maNV)) {
                return true;
            }
        }
        return false;
    }

    // ham tra ve nhan vien theo ma nhan vien
    public static nhanVien getNV(String maNV) {
        for (nhanVien nv : dsnv) {
            if (nv.getMaNV().equals(maNV)) {
                return nv;
            }
        }
        return null;
    }
}
