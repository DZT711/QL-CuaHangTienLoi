import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;
public class DSHD1 implements IQuanLy{
    public static ArrayList<hoaDon> danhSacHHoaDons = new ArrayList<>();
    private int n;
    Scanner sc = new Scanner(System.in);


    private boolean isMaHDTrung(String maHD) {
        for (hoaDon hd : danhSacHHoaDons) {
            if (hd.getMaHD().equals(maHD)) {
                return true;
            }
        }
        return false;
    } 

   private boolean isMaKHDaSuDung(String maKH) {
    for (hoaDon hd: danhSacHHoaDons) {
        if (hd.getKhachhang().getMaKH().equals(maKH)){
            return true;
        }
    }
    return false;
   }
 
   private void capNhatFile () {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("a.txt" , false))) {
        if(danhSacHHoaDons.isEmpty()) {
            System.out.println("danh sach hoa don trong vui long them");
        } else{
           bw.write("danh sach hoa don la");
           for (hoaDon hd : danhSacHHoaDons) {
            bw.write("ma hoa don la" + hd.getMaHD());
            System.out.print("ghi file thanh cong");
           }
        }
    } catch (IOException e) {
        System.out.print("loi ghi file" +e.getMessage());
    }
   }
   
    public void docFile() {
        try {
            FileReader fr = new FileReader("danhsachhoadon.txt");
            BufferedReader br = new BufferedReader(fr);

            String line =null;
            outerLoop: while (true) {
                line = br.readLine();
                if(line ==null)
                break;

                String [] txt = line.split(",");
                String maHD = txt[0];
                String maKH = txt[1];
                String maNV = txt[2];

                if (isMaHDTrung(maHD)) 
                continue outerLoop;
                if(!DSNV.isMaNVTonTai(maNV))
                continue outerLoop;
                if(!DSKH.isMaKHTonTai(maKH))
                continue outerLoop;

                khachHang kh = DSKH.getKH(maKH);
                nhanVien nv = DSNV.getNV(maNV);
                hoaDon hd = new hoaDon(maHD, kh, nv);
                danhSacHHoaDons.add(hd);
                System.out.print("loi ghi file");
            }
            br.close();
            fr.close();
                
            } catch (IOException e) {
                System.out.print("loi ghi file" + e.getMessage());
            }
        }
        


   public void them() {
    while (true) {
        // Dọn dẹp màn hình console
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("_____________________________________");
        System.out.println("|                                   |");
        System.out.println("|         MENU THEM HOA DON         |");
        System.out.println("|___________________________________|");
        System.out.println("|                                   |");
        System.out.println("| 1. Them hoa don moi               |");
        System.out.println("| 0. Thoat chuong trinh             |");
        System.out.println("|___________________________________|");
        System.out.print("NHAP LUA CHON: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 0) {
            System.out.println("THOAT CHUC NANG THEM HOA DON.");
            break;
        }

        switch (choice) {
            case 1:
                System.out.print("Nhap so luong hoa don muon them: ");
                n = sc.nextInt();
                sc.nextLine();

                for (int i = 0; i < n; i++) {
                    String maHD;
                    String maKH;
                    String maNV;

                    // Nhập mã hóa đơn và kiểm tra trùng lặp
                    while (true) {
                        System.out.print("Nhap ma hoa don: ");
                        maHD = sc.nextLine();
                        if (!isMaHDTrung(maHD)) {
                            break;
                        } else {
                            System.out.println("Ma hoa don bi trung, vui long nhap lai.");
                        }
                    }
                    // Kiểm tra mã nhân viên và trả về tên nhân viên nếu tồn tại
                    nhanVien nv = null;
                    while (true) {
                        System.out.print("Nhap ma nhan vien tao hoa don: ");
                        maNV = sc.nextLine();
                        if (DSNV.dsnv.isEmpty()) {
                            System.out.println("Danh sach nhan vien trong, vui long them nhan vien truoc.");
                            return;
                        }
                        // Kiểm tra mã nhân viên tồn tại
                        if (DSNV.isMaNVTonTai(maNV)) {
                            nv = DSNV.getNV(maNV); // Lấy nhân viên theo mã
                            System.out.println("Nhan vien: " + nv.getTenNV()); // In ra tên nhân viên
                            break;
                        } else {
                            System.out.println("Ma nhan vien khong ton tai, vui long nhap lai.");
                        }
                    }
                    // Nhập mã khách hàng và kiểm tra tồn tại
                    khachHang kh = null;
                    while (true) {
                        System.out.print("Nhap ma khach hang: ");
                        maKH = sc.nextLine();
                        if (DSKH.dskh.isEmpty()) {
                            System.out.println("Danh sach khach hang trong, vui long them khach hang truoc.");
                            return;
                        }
                        if (isMaKHDaSuDung(maKH)) {
                            System.out
                                    .println("Ma khach hang da duoc gan cho mot hoa don khac, vui long nhap lai.");
                            continue;
                        }
                        if (DSKH.isMaKHTonTai(maKH)) {
                            kh = DSKH.getKH(maKH);
                            System.out.println("Khach hang: " + kh.getTenKH()); // In ra tên khách hàng
                            break;
                        } else {
                            System.out.println("Ma khach hang khong ton tai, vui long nhap lai.");
                        }
                    }
                    hoaDon hd = new hoaDon(maHD, kh, nv);
                    danhSacHHoaDons.add(hd);
                    System.out.println("Hoa don da duoc them thanh cong.");
                    System.out.println("================================");
                    System.out.println("Thong tin khach hang va san pham cua hoa don: ");

                    hd.printChiTietHoaDon();
                    hd.xuat();
                }
                break;

            default:
                System.out.println("LUA CHON KHONG HOP LE. VUI LONG THU LAI.");
        }
    }
}

    @Override
    public void sua() {
        System.out.println("__________________________________________");
            System.out.println("|                                        |");
            System.out.println("|            MENU SUA HOA DON            |");
            System.out.println("|________________________________________|");
            System.out.println("|                                        |");
            System.out.println("| 1. SUA MA HOA DON                      |");
            System.out.println("| 2. THOAT                               |");
            System.out.println("|________________________________________|");
            System.out.print("NHAP LUA CHON: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (danhSacHHoaDons.isEmpty()) {
                System.out.print("....");
                return;
            }

            switch (choice) {
                case 1: 
                System.out.print("nhap ma hoa don");
                String maHD = sc.nextLine();

                boolean found = false;
                for ( hoaDon hd : danhSacHHoaDons) {
                    if (hd.getMaHD().equals(maHD)) {
                        System.out.print(" nhap ma hoa don moi");
                        String maHDmoi = sc.nextLine();

                        if (isMaHDTrung(maHDmoi)) {
                            System.out.print("nhap lai ma hoa don moi");   
                        } else {
                            hd.setMaHD(maHD);
                            
                        }
                    }
                    
                }
                    
                    break;
            
                default:
                    break;
            }
    }


    @Override
    public void xoa() {
     System.out.print("1 xoa");
     System.out.print("0. thoat");
     System.out.print(" nhap lua chon cua bn");
     int choice = sc.nextInt();

     if (danhSacHHoaDons.isEmpty()) {
        System.out.print(" danh sách hóa đơn trống");
        return;
     }
     switch (choice) {
        case 1:
        System.out.print(" nhap ma hoa don muon xoa");
        String maHD = sc.nextLine();

        boolean isDeleted = false;
        for  (hoaDon hd : danhSacHHoaDons) {
            if (hd.getMaHD().equals(maHD)) {
                danhSacHHoaDons.remove(hd);
                capNhatFile();
                isDeleted = true;
            }
        }
        if (isDeleted) {

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("ChiTietHoaDon.txt", true))) {
                bw.write("HOA DON VOI MA '" + maHD + "' DA DUOC XOA.\n");
                bw.write("=====================================\n");
                System.out.println("CAP NHAT TRANG THAI VAO FILE ChiTietHoaDon.txt THANH CONG.");
            } catch (IOException e) {
                System.out.println("LOI KHI GHI VAO FILE: " + e.getMessage());
            }
        } else {
            System.out.println("KHONG TIM THAY HOA DON VOI MA '" + maHD + "'.");
        }
            
        case 2:
        System.out.println("hen gap lai quy khach");
        return;
          
     
        default:
            System.out.print("lua chon cua ban khong hop le");
     }
    }



    @Override
    public void timKiem() {
        System.out.print("nhap hoa don can tim");
        System.out.print("thoat");
        System.out.print("lua chon");
        int choice = sc.nextInt();
        if(danhSacHHoaDons.isEmpty()) {
            System.out.print(" trong");
            return;
        }
        switch (choice) {
            case 1: 
            System.out.print("mahd");
            String maHD = sc.nextLine();

            hoaDon timthay = null;
            for (hoaDon hd : danhSacHHoaDons) {
                if (hd.getMaHD().equals(maHD)) {
                    timthay=hd;
                }
            }
            if (timthay != null) {
                timthay.printChiTietHoaDon();
                try (BufferedWriter bw = new BufferedWriter(new FileWriter("a.txt" ,true))) {
                    bw.write("chi tiet hoa don voi ma hoa don" + maHD );
                    bw.write("chi tiet hoa don" + timthay.toString());
                } catch (IOException e) {
                    System.out.print(" loi " + e.getMessage());
                }
            } else {
                System.out.print (" khong tim thay ma hd" + maHD);
            }
            case 0:
            System.out.print("thoat chuong trinh");
                return;
        
            default:
            System.out.print("lckhl");
        }
    }



    public void xuat() {
       System.out.print("so luong hoa don" + hoaDon.getSoHoaDon());
       if (danhSacHHoaDons.isEmpty()) {
        System.out.print("danh sách trống");
       } else {
        for (hoaDon hd : danhSacHHoaDons) {
            hd.xuat();
        }
       }
       capNhatFile();
    }
}
    
 

