import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class DSSP implements IQuanLy {
    private int SL;
    public static ArrayList<sanPham> sp = new ArrayList<sanPham>();
    Scanner sc = new Scanner(System.in);

    private boolean isMaSPTrung(String maSP) {
        for (sanPham sp2 : sp) {
            if (sp2.getMaSP().equals(maSP)) {
                return true; // trung
            }
        }
        return false; // ko trung
    }

    public void ghiFile(String fileName) {

        Set<String> writtenMaSP = new HashSet<>(); // Lưu các mã sản phẩm đã ghi
        try (FileWriter writer = new FileWriter(fileName)) {
            if (sp.isEmpty()) {
                writer.write("KHONG CO SAN PHAM \n");
                System.out.println("DANH SACH SAN PHAM TRONG. VUI LONG THEM SAN PHAM.");
            } else {
                for (sanPham spItem : sp) {
                    if (writtenMaSP.contains(spItem.getMaSP())) {
                        // Nếu mã sản phẩm đã tồn tại, bỏ qua
                        System.out.println("MA SAN PHAM " + spItem.getTenSP() + " DA TON TAI, không ghi vào file.");
                        continue;
                    }
                    // Thêm mã sản phẩm vào Set
                    writtenMaSP.add(spItem.getMaSP());

                    // Ghi sản phẩm vào file
                    if (spItem instanceof doGiaDung) {
                        doGiaDung dgd = (doGiaDung) spItem;
                        writer.write("doGiaDung," + dgd.getMaSP() + "," + dgd.getTenSP() + "," + dgd.getSL() + ","
                                + dgd.getGia() + "," + dgd.getMaSac() + "\n");
                    } else if (spItem instanceof doUong) {
                        doUong du = (doUong) spItem;
                        writer.write("doUong," + du.getMaSP() + "," + du.getTenSP() + "," + du.getSL() + ","
                                + du.getGia() + "," + du.getDungTich() + "\n");
                    }
                }

                System.out.println("GHI FILE THANH CONG.");
            }

        } catch (Exception ex) {
            System.out.println("LOI GHI FILE: " + ex.getMessage());
        }
    }

    public void docFile(String fileName) {
        sp.clear(); // Xóa danh sách hiện tại trước khi đọc
        Set<String> existingMaSP = new HashSet<>();
        // Lưu trữ các mã sản phẩm đã thêm vào mảng

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String maSP = parts[1];

                // Kiểm tra nếu mã sản phẩm đã tồn tại
                if (existingMaSP.contains(maSP)) {
                    System.out.println("MA SAN PHAM " + maSP + " DA TON TAI, BO QUA.");
                    continue; // Bỏ qua sản phẩm này
                }

                // Nếu mã chưa tồn tại, thêm sản phẩm vào danh sách
                if (parts[0].equals("doGiaDung")) {
                    String tenSP = parts[2];
                    int soLuong = Integer.parseInt(parts[3]);
                    double gia = Double.parseDouble(parts[4]);
                    String chatLieu = parts[5];
                    sp.add(new doGiaDung(chatLieu, maSP, tenSP, soLuong, gia));
                } else if (parts[0].equals("doUong")) {
                    String tenSP = parts[2];
                    int soLuong = Integer.parseInt(parts[3]);
                    double gia = Double.parseDouble(parts[4]);
                    double dungTich = Double.parseDouble(parts[5]);
                    sp.add(new doUong(dungTich, maSP, tenSP, soLuong, gia));
                }

                // Thêm mã sản phẩm vào Set
                existingMaSP.add(maSP);
            }
            System.out.println("DOC FILE THANH CONG.");
        } catch (Exception ex) {
            System.out.println("LOI DOC FILE: " + ex.getMessage());
        }
    }

    public void them() {

        while (true) {
            // dọn dẹp màn hình console
            System.out.print("\033[H\033[2J");
            System.out.flush();
            //
            System.out.println("__________________________________________________________");
            System.out.println("|                                                        |");
            System.out.println("|               DANH SACH SAN PHAM MUON THEM             |");
            System.out.println("|________________________________________________________|");
            System.out.println("|                                                        |");
            System.out.println("| 1. DO GIA DUNG.                                        |");
            System.out.println("| 2. DO UONG.                                            |");
            System.out.println("| 0.THOAT CHUONG TRINH.                                  |");
            System.out.println("|________________________________________________________|");
            System.out.print("| NHAP LUA CHON: ");
            int input = sc.nextInt();
            System.out.println("____________________________________");
            sc.nextLine();
            switch (input) {
                case 1:
                    System.out.print("NHAP SO LUONG LOAI DO GIA DUNG : ");
                    SL = sc.nextInt();
                    System.out.println("________________________________");
                    sc.nextLine();

                    for (int i = 0; i < SL; i++) {

                        String maSP;
                        while (true) {
                            System.out.print("NHAP MA SP: ");
                            maSP = sc.nextLine();
                            if (!isMaSPTrung(maSP)) {
                                break; // khong trung thoat khoi vong lap
                            } else {
                                System.out.println("MA SP DA TON TAI VUI LONG NHAP MA KHAC!");
                                return;

                            }
                        }

                        System.out.print("NHAP TEN SP: ");
                        String tenSP = sc.nextLine();

                        int sl;
                        while (true) {

                            System.out.print("NHAP SO LUONG : ");
                            sl = sc.nextInt();
                            if (sl > 0) {
                                break;
                            } else {
                                System.out.println("SO LUONG SAN PHAM KHONG HOP LE!\nVUi LONG NHAP LAI");
                            }

                        }

                        System.out.print("NHAP GIA: ");
                        double gia = sc.nextDouble();
                        sc.nextLine();
                        System.out.print("NHAP CHAT LIEU : ");
                        String chatLieu = sc.nextLine();
                        doGiaDung dgd = new doGiaDung(chatLieu, maSP, tenSP, sl, gia);

                        sp.add(dgd);
                        System.out.println("================================");
                    }
                    break;

                case 2:

                    System.out.print("NHAP SO LUONG LOAI DO UONG : ");
                    SL = sc.nextInt();
                    System.out.println("________________________________");
                    sc.nextLine();

                    for (int i = 0; i < SL; i++) {

                        String maSP;
                        while (true) {

                            System.out.print("NHAP MA SP: ");
                            maSP = sc.nextLine();
                            if (!isMaSPTrung(maSP)) {
                                break;
                            } else {
                                System.out.println("MA SP DA TON TAI VUI LONG NHAP MA KHAC!");
                            }

                        }
                        System.out.print("NHAP TEN SP: ");
                        String tenSP = sc.nextLine();

                        int sl;
                        while (true) {
                            System.out.print("NHAP SO LUONG DO UONG : ");
                            sl = sc.nextInt();
                            if (sl >= 0) {

                                break;
                            } else {
                                System.out.println("SO LUONG SAN PHAM KHONG HOP LE!\nVUi LONG NHAP LAI");
                            }

                        }

                        System.out.print("NHAP GIA: ");
                        double gia = sc.nextDouble();
                        if (sl >= 0) {

                        } else {
                            System.out.println("GIA SAN PHAM KHONG HOP LE!\nVUi LONG NHAP LAI");
                        }
                        sc.nextLine();
                        System.out.print("NHAP DUNG TICH: ");
                        double dungTich = sc.nextDouble();
                        sc.nextLine();
                        doUong doUong = new doUong(dungTich, maSP, tenSP, sl, gia);

                        sp.add(doUong);
                        System.out.println("================================");
                    }
                    break;
                case 0:
                    System.out.println("HEN GAP LAI <3");
                    return;
                default:
                    System.out.println("LUA CHON KHONG HOP LE\nVUI LONG NHAP LAI");
                    break;
            }
        }

    }

    public void timKiem() {

        while (true) {
            System.out.println("__________________________________________");
            System.out.println("|                                        |");
            System.out.println("|           TIM KIEM SAN PHAM            |");
            System.out.println("|________________________________________|");
            System.out.println("|                                        |");
            System.out.println("| 1. TIEM KIEM BANG MA SAN PHAM.         |");
            System.out.println("| 2. TiEM KIEM SAN PHAM BANG TEN.        |");
            System.out.println("| 0. THOAT.                              |");
            System.out.println("|________________________________________|");
            System.out.print("NHAP LUA CHON : ");
            int input = sc.nextInt();
            // tạo dấu enter không sẽ gặp lỗi lăp
            sc.nextLine();
            if (sp.isEmpty()) {
                System.out.print("Khong CO SAN PHAM!\nVUI LONG THEM SAN PHAM.");
                break;
            }
            switch (input) {
                case 1:

                    System.out.println("NHAP MA SP: ");
                    String maSP = sc.nextLine();
                    for (sanPham sp4 : sp) {
                        if (sp4.getMaSP().equals(maSP)) {
                            sp4.xuat();
                            System.out.println("\n=====================================");
                            return;
                        }

                    }
                    System.out.println("SAN PHAM KHONG TIM THAY!");
                    break;
                case 2:

                    System.out.println("NHAP TEN SP: ");
                    String tenSP = sc.nextLine();
                    for (sanPham sp6 : sp) {
                        if (sp6.getTenSP().equals(tenSP)) {
                            sp6.xuat();
                            System.out.println("\n=====================================");
                            return;
                        }
                    }
                    System.out.println("SAN PHAM KHONG TIM THAY!");
                    break;
                case 0:
                    System.out.println("HEN GAP LAI <3");
                    return;
                default:
                    System.out.println("LUA CHON KHONG HOP LE!\nVUI LONG NHAP LAI");
                    break;
            }

        }

    }

    public void sua() {

        while (true) {

            System.out.println("_______________________________");
            System.out.println("|                             |");
            System.out.println("|        SUA SAN PHAM         | ");
            System.out.println("|_____________________________|");
            System.out.println("|                             |");
            System.out.println("| 1.DO GIA DUNG.              |");
            System.out.println("| 2.DO UONG.                  |");
            System.out.println("| 0.THOAT.                    |");
            System.out.println("|_____________________________|");
            System.out.print("NHAP LUA CHON : ");
            int input = sc.nextInt();
            sc.nextLine();
            if (sp.isEmpty()) {
                System.out.print("Khong CO SAN PHAM!\nVUI LONG THEM SAN PHAM.");
                return;
            }

            String maSP;

            switch (input) {

                case 1:
                    System.out.println("NHAP MA SP : ");
                    maSP = sc.nextLine();

                    for (sanPham sp1 : sp) {
                        if (sp1.getMaSP().equals(maSP) && sp1 instanceof doGiaDung) {
                            suaChiTietDGD(maSP);
                            return;
                        } else {
                            System.out.println("HIEN DANG KHONG CO SAN PHAM DO GIA DUNG.");
                            return;
                        }
                    }
                    System.out.println("NHAP SAI SP CAN SUA!\nVUI LONG NHAP LAI.");

                    break;
                case 2:
                    System.out.print("NHAP MA SP : ");
                    maSP = sc.nextLine();
                    for (sanPham sp1 : sp) {
                        if (sp1.getMaSP().equals(maSP) && sp1 instanceof doUong) {
                            suaChiTietDGD(maSP);
                            return;
                        } else {
                            System.out.println("HIEN DANG KHONG CO SAN PHAM DO UONG.");
                            return;
                        }
                    }
                    System.out.println("NHAP SAI SP CAN SUA!\nVUI LONG NHAP LAI.");

                    break;
                case 0:
                    System.out.println("HEN GAP LAI <3");
                    return;
                default:
                    System.out.println("LUA CHON KHONG HOP LE!\nVUI LONG NHAP LAI.");
                    break;
            }
        }
    }

    public void suaChiTietDGD(String maSP) {

        for (sanPham DGD : sp) {
            if (DGD.getMaSP().equals(maSP)) {
                DGD.xuat();
                while (true) {

                    System.out.println("___________________________________");
                    System.out.println("|                                 |");
                    System.out.println("|         SUA DO GIA DUNG         |");
                    System.out.println("|_________________________________|");
                    System.out.println("|                                 |");
                    System.out.println("| 1.MA SAN PHAM.                  |");
                    System.out.println("| 2.TEN SAN PHAM.                 |");
                    System.out.println("| 3.SO LUONG.                     |");
                    System.out.println("| 4.GIA.                          |");
                    System.out.println("| 5.CHAT LIEU.                    |");
                    System.out.println("| 0.THOAT.                        |");
                    System.out.println("|_________________________________|\n");
                    System.out.print("NHAP LUA CHON : ");
                    int input = sc.nextInt();
                    sc.nextLine();
                    if (sp.isEmpty()) {
                        System.out.println("Khong CO SAN PHAM!\nVUI LONG THEM SAN PHAM.");
                        break;
                    }

                    switch (input) {
                        case 1:

                            String maSPMoi;

                            System.out.print("NHAP MA DO GIA DUNG MOI : ");
                            maSPMoi = sc.nextLine();

                            if (isMaSPTrung(maSPMoi)) {
                                System.out.println("MA SP DA TON TAI!\nBAN CO MUON THAY DOI.");
                                return;
                            }

                            DGD.setMaSP(maSPMoi);
                            System.out.println("MA DO GIA DUNG DA CAP NHAT THANH CONG <3");
                            System.out.println("KHONG TIM THAY SAN PHAM.");
                            break;
                        case 2:
                            System.out.print("NHAP TEN DO GIA DUNG MOI : ");
                            String tenSPMoi = sc.nextLine();
                            DGD.setTenSP(tenSPMoi);
                            System.out.println("TEN DO GIA DUNG CAP NHAT THANH CONG <3");
                            break;

                        case 3:

                            System.out.print("NHAP SO LUONG DO GIA DUNG MOI : ");
                            int slSPMoi = sc.nextInt();
                            DGD.setSL(slSPMoi);

                            System.out.println("SO LUONG DO GIA DUNG CAP NHAT THANH CONG <3");
                            break;
                        case 4:
                            System.out.println("NHAP GIA DO GIA DUNG MOI : ");
                            double giaSPMoi = sc.nextDouble();
                            DGD.setGia(giaSPMoi);
                            System.out.println("GIA DO GIA DUNG CAP NHAT THANH CONG <3");
                            break;
                        case 5:

                            System.out.println("NHAP CHAT LIEU MOI : ");
                            String chatLieuMoi = sc.nextLine();
                            if (DGD instanceof doGiaDung) {
                                ((doGiaDung) DGD).setchatLieu(chatLieuMoi);
                            }
                            System.out.println("CHAT LIEU DO GIA DUNG DA DUOC CAP NHAT <3");
                            break;
                        case 0:
                            System.out.println("HEN GAP LAI <3");
                            return;
                        default:
                            System.out.println("LUA CHON KHONG HOP LE!\nVUI LONG NHAP LAI.");
                            break;
                    }

                }

            }

        }

    }

    public void suaChiTietDU(String maSP) {

        for (sanPham DU : sp) {
            if (DU.getMaSP().equals(maSP)) {
                DU.xuat();
                while (true) {
                    System.out.println("___________________________________");
                    System.out.println("|                                 |");
                    System.out.println("|           SUA DO UONG           |");
                    System.out.println("|_________________________________|");
                    System.out.println("|                                 |");
                    System.out.println("| 1.MA SAN PHAM.                  |");
                    System.out.println("| 2.TEN SAN PHAM.                 |");
                    System.out.println("| 3.SO LUONG.                     |");
                    System.out.println("| 4.GIA.                          |");
                    System.out.println("| 5.CHAT LIEU.                    |");
                    System.out.println("| 0.THOAT.                        |");
                    System.out.println("|_________________________________|\n");
                    System.out.println("NHAP SU LUA CHON : ");
                    int input = sc.nextInt();

                    if (sp.isEmpty()) {
                        System.out.print("Khong CO SAN PHAM!\nVUI LONG THEM SAN PHAM.");
                        break;
                    }
                    sc.nextLine();
                    switch (input) {
                        case 1:

                            String maspDU;

                            System.out.print("NHAP MA DO UONG MOI : ");

                            maspDU = sc.nextLine();
                            if (isMaSPTrung(maspDU)) {
                                System.out.println("MA SP DA TON TAI!\nBAN CO MUON THAY DOI.");
                                return;
                            }
                            DU.setMaSP(maspDU);
                            System.out.println("MA CAP NHAT THANH CONG!");

                            break;
                        case 2:
                            System.out.println("NHAP TEN DO UONG MOI : ");
                            String tenSPDU = sc.nextLine();
                            DU.setTenSP(tenSPDU);
                            System.out.println("TEN CAP NHAT THANH CONG!");
                            break;
                        case 3:

                            System.out.println("NHAP SO LUONG DO UONG MOI : ");
                            int slPDU = sc.nextInt();
                            DU.setSL(slPDU);

                            System.out.println("SO LUONG CAP NHAT THANH CONG!");
                            break;
                        case 4:
                            System.out.println("NHAP GIA DO UONG MOI : ");
                            double giaSPDU = sc.nextDouble();
                            DU.setGia(giaSPDU);
                            System.out.println("GIA DO UONG CAP NHAT THANH CONG!");
                            break;
                        case 5:
                            System.out.println("NHAP DUNG TICH DO UONG MOI : ");
                            double dungTichSPDU = sc.nextDouble();
                            if (DU instanceof doUong) {
                                ((doUong) DU).setDungTich(dungTichSPDU);
                            }
                            System.out.println("DUNG TICH DO UONG CAP NHAT THANH CONG!");
                            break;
                        case 0:
                            System.out.println("HEN GAP LAI <3");
                            return;
                        default:
                            System.out.println("LUA CHON KHONG HOP LE!\nVUI LONG NHAP LAI.");
                            break;
                    }
                }
            }
        }
    }

    public void xoa() {
        while (true) {

            System.out.println("_____________________________________");
            System.out.println("|                                   |");
            System.out.println("|            XOA SAN PHAM           |");
            System.out.println("|___________________________________|");
            System.out.println("|                                   |");
            System.out.println("| 1. XOA SAN PHAM BANG MA.          |");
            System.out.println("| 2. XOA SAN PHAM BANG TEN.         |");
            System.out.println("| 0. THOAT.                         |");
            System.out.println("|___________________________________|");
            System.out.print("NHAP LUA CHON : ");
            int input = sc.nextInt();
            // tạo dấu enter không sẽ gặp lỗi lăp
            sc.nextLine();
            if (sp.isEmpty()) {
                System.out.print("Khong CO SAN PHAM!\nVUI LONG THEM SAN PHAM.");
                break;
            }
            switch (input) {
                case 1:
                    if (sp.isEmpty()) {
                        System.out.print("Khong CO SAN PHAM!\nVUI LONG THEM SAN PHAM.");
                        break;
                    }
                    System.out.println("NHAP MA SP: ");
                    String maSP = sc.nextLine();
                    for (sanPham sp4 : sp) {
                        if (sp4.getMaSP().equals(maSP)) {
                            sp.remove(sp4);
                            System.out.println("XOA SAN PHAM THANH CONG.");

                            return;
                        }
                    }
                    System.out.println("SAN PHAM KHONG TIM THAY!");
                    break;
                case 2:
                    if (sp.isEmpty()) {
                        System.out.print("Khong CO SAN PHAM!\nVUI LONG THEM SAN PHAM.");
                        break;
                    }
                    System.out.println("NHAP TEN SP: ");
                    String tenSP = sc.nextLine();
                    for (sanPham sp6 : sp) {
                        if (sp6.getTenSP().equals(tenSP)) {
                            sp.remove(sp6);
                            System.out.println("XOA SAN PHAM THANH CONG.");
                            return;
                        }
                    }
                    System.out.println("KHONG TON TAI SAN PHAM!");
                    break;
                case 0:
                    System.out.println("HEN GAP LAI <3");
                    return;
                default:
                    System.out.println("LUA CHON KHONG HOP LE\nVUI LONG NHAP LAI");
                    break;
            }

        }
    }

    public void xuat() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("__________________________________________________________");
        System.out.println("|                                                        |");
        System.out.println("|                   DAMH SACH SAN PHAM                   |");
        System.out.println("|________________________________________________________|");
        if (sp.isEmpty()) {
            System.out.println("| KHONG CO SP.                                           |");
        }
        System.out.println("| DO GIA DUNG                                            |");
        System.out.println("|________________________________________________________|");
        for (sanPham sp2 : sp) {
            if (sp2 instanceof doGiaDung) {
                sp2.xuat();
            } else if (sp2 instanceof doUong) {
                continue;
            }

        }
        System.out.println("| DO UONG                                                |");
        System.out.println("|________________________________________________________|");
        for (sanPham sp2 : sp) {
            if (sp2 instanceof doUong) {
                sp2.xuat();
            } else if (sp2 instanceof doGiaDung) {
                continue;
            }

        }

        System.out.println("SO LUONG SAN PHAM TRONG KHO : " + SLSP());
        System.out.println("SO LUONG LOAI SAN PHAM HIEN TAI CO : " + sp.size());
        System.out.println("______________________________________");

    }

    public int SLSP() {
        int tempSL = 0;
        for (sanPham x : sp) {
            tempSL += x.getSL();
        }
        return tempSL;
    }
}
