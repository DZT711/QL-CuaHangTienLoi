import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.Buffer;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class DSKH extends QLHKHang {
    public static ArrayList<khachHang> dskh = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    // Check this product, Was it bought buy the customer ?
    public boolean checkBought(String productChecked, ArrayList<sanPham> listSP) {
        for (sanPham x : listSP)
            if (productChecked.equals(x.getMaSP()))
                return false;
        return true;
    }

    // Add new customer
    @Override
    public void themKH() {
        System.out.print("Nhap so khach ban muon them: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= n; i++) {
            // Situation: Add multiple customers
            if (n > 1)
                System.out.print("Khach hang thu " + i + ":\n");
            // Situation: Add only one customer
            else
                System.out.print("Thong tin khach hang: \n");
            System.out.println("____________________________");
            System.out.print("Ten khach hang: ");
            String tenKH = sc.nextLine();
            double tienKH = 0;
            ArrayList<sanPham> listSP = new ArrayList<>();

            // Enter the numbers of items the customer bought
            System.out.print("Nhap so san pham khach hang da mua: ");
            int m = sc.nextInt();
            sc.nextLine();
            if (m > DSSP.sp.size()) {
                System.out.print("Nhap lai so mon ban mua ( <= " + DSSP.sp.size() + "): ");
                m = sc.nextInt();
                sc.nextLine();
            }
            // Enter each items the customer bought (we have m items here)
            productLoop: for (int j = 1; j <= m; j++) {
                String maSP, tenSP = "";
                double giaSP = 0.0;
                boolean check = false;
                int tempSL = 0;

                do {
                    System.out.print("Ma san pham: ");
                    maSP = sc.nextLine();
                    // Check existence of this product by ID product
                    for (sanPham x : DSSP.sp)
                        if (x.getMaSP().equals(maSP)) {
                            check = true;
                            break;
                        }
                    if (!check)
                        System.out.println("Ma san pham nay khong ton tai\nNhap ma moi: ");
                } while (!check);

                // Situation: If this product was sold out
                for (sanPham x : DSSP.sp)
                    if (x.getMaSP().equals(maSP))
                        if (x.getSL() == 0) {
                            System.out.println("San pham nay da het. Ban co the quay lai lan sau nhe");
                            sc.nextLine();
                            continue productLoop;
                        }

                // Situation: customer enter the product that has been entered before
                while (true) {
                    check = checkBought(maSP, listSP);
                    if (!check) {
                        System.out.print("Ban da nhap san pham nay roi. Vui long nhap san pham khac");
                        maSP = sc.nextLine();
                    } else
                        break;
                }

                // Get info of this product when it exists
                for (sanPham x : DSSP.sp)
                    if (x.getMaSP().equals(maSP)) {
                        tenSP = x.getTenSP();
                        giaSP = x.getGia();
                        tempSL = x.getSL();
                        break;
                    }

                // if (tempSL == 0) {
                // // Nhập lại sản phẩm khác hoặc ko mua
                // System.out.println("Ban ");
                // }
                System.out.print("So luong: ");
                int soLuongSP = sc.nextInt();

                sc.nextLine();
                // count4 += soLuongSP;
                soLuongSP = Math.min(soLuongSP, tempSL);
                // Subtract the amount of this product in stockroom when customers buy
                for (sanPham x : DSSP.sp) {
                    if (x.getMaSP().equals(maSP)) {
                        x.setSL(x.getSL() - soLuongSP);
                        break;
                    }
                }
                tienKH += (double) (soLuongSP * giaSP);
                sanPham sp = new sanPham(maSP, tenSP, soLuongSP, giaSP);
                if (soLuongSP > 0)
                    listSP.add(sp);
            }
            if (listSP.size() != 0) {
                khachHang kh = new khachHang(tenKH, listSP, tienKH);
                dskh.add(kh);
            }
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Edit info's customer
    @Override
    public void suaKH() {
        if (dskh.size() > 0) {
            System.out.println("_______________________________________________");
            System.out.println("|                                             |");
            System.out.println("|           SUA THONG TIN KHACH HANG          |");
            System.out.println("|_____________________________________________|");
            System.out.println("|                                             |");
            System.out.println("| 1.SUA TEN KHACH HANG                        |");
            System.out.println("| 2.SUA MA KHACH HANG                         |");
            System.out.println("| 3.THOAT                                     |");
            System.out.println("|_____________________________________________|");
            System.out.print("NHAP LUA CHON CUA BAN: (1/2/3): ");
            int choice = sc.nextInt();
            sc.nextLine();
            int stt = 1;
            for (khachHang kh : dskh) {
                System.out.println(kh.toString(stt));
                stt++;
            }
            // Option 1: Name's customer
            if (choice == 1) {
                System.err.println("");
                System.out.print("Ban muon sua (nhap so): ");
                int num = sc.nextInt();
                sc.nextLine();
                if (num >= 1 && num <= dskh.size()) {
                    System.out.print("Ten khach hang moi: ");
                    String newName = sc.nextLine();
                    dskh.get(num - 1).setTenKH(newName);
                    System.out.println("Sua doi thanh cong");
                } else
                    System.out.println("Khach hang khong ton tai");
            }
            // Option 2: ID's customer
            else if (choice == 2) {
                System.out.println("____________________________");
                System.out.print("Ban muon sua (nhap so): ");
                int num = sc.nextInt();
                sc.nextLine();
                if (num >= 1 && num <= dskh.size()) {
                    boolean check;
                    String newMaKH;
                    do {
                        check = true;
                        System.err.print("Ma khach hang moi: ");
                        newMaKH = sc.nextLine();

                        for (khachHang kh : dskh) {
                            if (kh.getMaKH().equals(newMaKH)) {
                                check = false;
                                System.out.println("Ma nay da ton tai !");
                                break;
                            }
                        }
                        if (check) {
                            dskh.get(num - 1).setMaKH(newMaKH);
                            System.err.println("Sua doi thanh cong");
                            break;
                        }
                    } while (!check);
                } else
                    System.out.println("Khach hang khong ton tai");
            } else if (choice == 3) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                suaKH();
            }
        } else
            System.out.println("\nHien chua co khach hang nao !!!");
    }

    // find customer by ID or Name
    @Override
    public void timKH() {
        if (dskh.size() > 0) {
            System.out.println("############# TIM KIEM KHACH HANG ############");
            System.out.print("Nhap ten hoac ma khach hang: ");
            String find = sc.nextLine();
            boolean found = false; // check Found
            System.out.print("Ban co muon xuat ra file khong?(Y/N)");
            String choice = sc.nextLine();
            int stt = 1;
            for (khachHang kh : dskh) {
                if (khachHang.chuanHoaTen(kh.getTenKH()).equals(khachHang.chuanHoaTen(find))
                    || kh.getMaKH().equals(find)) {
                    found = true;
                    System.out.println(kh.toString(stt));
                    System.out.println("Danh sach SP da mua: ");
                    for (sanPham x : kh.getDSSP())
                        System.out.println("       " + x.getTenSP() + "     " + x.getSL());
                    stt++;
                    System.out.println("-----------------------------------");
                }
            }
            if (!found)
                System.out.println("Khach hang nay khong ton tai");
        } else
            System.out.println("Hien chua co khach hang - Vui long them khach hang ");
    }

    @Override
    public void xemDSKH() {
        if (dskh.size() > 0) {
            System.out.println("############## DANH SACH KHACH HANG #################");
            System.err.println("----------------------------------------");
            System.err.println("Tong so khach hang: " + dskh.size());
            int stt = 1;

            // Print info's customer
            for (khachHang kh : dskh) {
                System.out.println(kh.toString(stt));
                stt++;
                System.out.println("Danh sach SP da mua :");
                int cnt = 1;
                for (sanPham x : kh.getDSSP()) {
                    System.out.println("      " 
                    + cnt + ". Ma SP: " 
                    + x.getMaSP() 
                    + " - Ten SP: " 
                    + x.getTenSP()
                    + " SL: " 
                    + x.getSL());
                    cnt++;
                }
                System.out.println("-----------------------------------");
            }
        } else System.out.println("Hien chua co khach hang !\n");
    }

    // Top Five Customer Of Shop
    @Override
    public void topFiveKH() {
        if (dskh.size() > 0) {
            // ArrayList<khachHang> tempList = new ArrayList<>(dskh);
            Collections.sort(dskh, new Comparator<khachHang>() {
                @Override
                public int compare(khachHang o1, khachHang o2) {
                    if (o1.getTien() != o2.getTien())
                        if (o1.getTien() > o2.getTien())
                            return -1;
                        else
                            return 1;
                    else
                        return o1.getTenKH().compareTo(o2.getTenKH());
                }
            });
            int stt = 1;
            int topCount = Math.min(5, dskh.size());
            for (int i = 0; i < topCount; i++) {
                khachHang kh = dskh.get(i);
                System.out.println(kh.toString(stt)
                        + " - "
                        + String.format("%,.3f", kh.getTien()).replace(",", ".")
                        + "VND");
                stt++;
            }
            // Resort dskh 
            Collections.sort(dskh, new Comparator<khachHang>() {
                @Override
                public int compare(khachHang o1, khachHang o2) {
                    return o1.getMaKH().compareTo(o2.getMaKH());
                }
            });
        } else System.out.println("Hien chua co khach hang\n");
    }

    public static boolean isMaKHTonTai(String maKH) {
        for (khachHang kh : dskh)
            if (kh.getMaKH().equals(maKH))
                return true;
        return false;
    }

    public static khachHang getKH(String maKH) {
        for (khachHang kh : dskh)
            if (kh.getMaKH().equals(maKH))
                return kh;
        return null;
    }

    

    public void readFile() {
        try {
            FileReader fr = new FileReader("docFileKH.txt");
            BufferedReader br = new BufferedReader(fr);

            String line = null;
            while (true) {
                line = br.readLine();
                if (line == null)
                    break;

                ArrayList<sanPham> listSP = new ArrayList<>();
                String[] txt = line.split(",");
                double chiPhiMua = 0;
                String tenKH = txt[0];
                int soMon = Integer.parseInt(txt[1]);
                if (soMon > DSSP.sp.size())
                    soMon = DSSP.sp.size();
                int tmp = 0;
                outerLoop: for (int i = 1; i <= soMon; i++) {
                    boolean checkID = false;
                    String maSP = txt[2 * i];
                    String tenSP = "";
                    double giaSP = 0;
                    int soLuongTmp = 0;
                    for (sanPham x : DSSP.sp) {
                        if (x.getMaSP().equals(maSP)) {
                            checkID = true;
                            tenSP = x.getTenSP();
                            giaSP = x.getGia();
                            soLuongTmp = x.getSL();
                            break;
                        }
                    }
                    if (!checkID || soLuongTmp == 0)
                        continue outerLoop;
                    int soLuongMua = Math.min(Integer.parseInt(txt[3 + tmp]), soLuongTmp);
                    tmp += 2;
                    for (sanPham x : DSSP.sp)
                        if (x.getMaSP().equals(maSP))
                            x.setSL(x.getSL() - soLuongMua);
                    chiPhiMua += (double) (soLuongMua * giaSP);
                    sanPham sp = new sanPham(maSP, tenSP, soLuongMua, giaSP);
                    if (soLuongMua > 0)
                        listSP.add(sp);
                }
                if (listSP.size() != 0) {
                    khachHang kh = new khachHang(tenKH, listSP, soMon);
                    dskh.add(kh);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void suaKHTestCase(){
        System.out.println("Chuyen den file suaKHOutput.txt de xem nhung thay doi");
        try {
            FileReader fr = new FileReader("suaKHInput.txt");
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter("suaKHOuput.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("------------ DANH SACH SUA THONG TIN KH ----------");
            bw.newLine();
            String line = null;
            outerLoop:
            while(true){
                line = br.readLine();
                if (line == null) break;
                String[] txt = line.split(",");
                int choice = Integer.parseInt(txt[0]);
                int stt = Integer.parseInt(txt[1]);
                if (choice != 1 && choice != 2 || stt > dskh.size() || stt < 1)
                    continue outerLoop;
                if (choice == 1) {
                    bw.write("Ten cu: " + dskh.get(stt - 1).getTenKH() + "   -   ");
                    dskh.get(stt - 1).setTenKH(txt[2]);
                    bw.write("Ten moi: " + dskh.get(stt - 1).getTenKH());
                    bw.newLine();
                }
                else if (choice == 2){  
                    for (khachHang kh : dskh)
                        if (kh.getMaKH().equals(txt[2])) continue outerLoop;
                    bw.write("MaKH cu: " + dskh.get(stt - 1).getMaKH() + "   -   ");
                    dskh.get(stt - 1).setMaKH(txt[2]);
                    bw.write("MaKH moi: " + dskh.get(stt - 1).getMaKH());
                    bw.newLine();
                }
            }
            bw.close();     fw.close();
            br.close();     fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void timKHTestCase() {
        System.out.println("Chuyen den file timKHOutput.txt de xem ket qua tim kiem nhe");
        try {
            FileReader fr = new FileReader("timKHInput.txt");
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter("timKHOutput.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            boolean found = false;
            String line = null;
            int stt;
            while(true){
                line = br.readLine();
                if (line == null) break;
                stt = 1;
                String find = line;
                for (khachHang kh : dskh){
                    if (khachHang.chuanHoaTen(kh.getTenKH()).equals(khachHang.chuanHoaTen(find)) || kh.getMaKH().equals(find)) {
                        found = true;
                        bw.write(kh.toString(stt));
                        bw.newLine();
                        bw.write("Danh sach SP da mua:");
                        for (sanPham x : kh.getDSSP())
                            bw.write("      " + x.getTenSP() + "      " + x.getSL());
                        bw.newLine();
                        stt++;
                        bw.write("-----------------------------------");
                        bw.newLine();
                    }
                }
                if (!found){
                    bw.write("Khach hang " + find + " khong ton tai");
                    bw.newLine();
                }
            }
            bw.close();   fw.close();
            br.close();   fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void xemDSKHTestCase() {
        try {
            FileWriter fw = new FileWriter("xemDSKH.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            System.out.println("Chuyen den file xemDSKH.txt de xem");

            bw.write("############## DANH SACH KHACH HANG #################");
            bw.newLine();
            bw.write("----------------------------------------");
            bw.newLine();
            bw.write("Tong so khach hang: " + dskh.size());
            bw.newLine();
            int stt = 1;
            for (khachHang kh : dskh) {
                bw.write(kh.toString(stt));
                bw.newLine();
                int cnt = 1;
                for (sanPham x : kh.getDSSP()) {
                    bw.write("      " + cnt + ". Ma SP: " + x.getMaSP() + " - Ten SP: " + x.getTenSP() + " SL: "
                            + x.getSL());
                    bw.newLine();
                    cnt++;
                }
                stt++;
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void top5TestCase() {
        System.out.println("Chuyen den file top5Output.txt de xem nhe");
        try {
            FileWriter fw = new FileWriter("top5Output.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("############### TOP 5 KHACH HANG CUA SHOP ############");
            bw.newLine();
            if (dskh.size() < 1) 
                bw.write("Hien khong co khach hang nao");

            int stt = 1;
            int topCount = Math.min(5, dskh.size());
            for (int i = 0; i < topCount; i++) {
                khachHang kh = dskh.get(i);
                bw.write(kh.toString(stt)
                    + " - "
                    + String.format("%,.3f", kh.getTien()).replace(",", ".")
                    + " VND");
                bw.newLine();
                stt++;
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuTestCase() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        int choice;
        do {
            System.out.println("_____________________________________");
            System.out.println("|                                   |");
            System.out.println("|           MENU TEST CASE          |");
            System.out.println("|___________________________________|");
            System.out.println("|                                   |");
            System.out.println("| 1. THEM KHACH HANG                |");
            System.out.println("| 2. SUA THONG TIN KHACH HANG       |");
            System.out.println("| 3. TIM KHACH HANG                 |");
            System.out.println("| 4. XEM DANH SACH KHACH HANG       |");
            System.out.println("| 5. XEM TOP 5 KHACH HANG           |");
            System.out.println("| 0. THOAT                          |");
            System.out.println("|___________________________________|");
            System.out.print("Nhap lua chon cua ban: ");
            choice = sc.nextInt();
            sc.nextLine();
            System.out.print("\033[H\033[2J");
            System.out.flush();
            DSKH dskh = new DSKH();
            switch (choice) {
                case 1:
                    dskh.readFile();
                    break;
                case 2:
                    dskh.suaKHTestCase();
                    break;
                case 3:
                    dskh.timKHTestCase();
                    break;
                case 4: 
                    dskh.xemDSKHTestCase();
                    break;
                case 5: 
                    dskh.top5TestCase();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lua chon khong hop le, vui long nhap lai!");
                    break;
            }
        }while(choice != 0);
    }
}
