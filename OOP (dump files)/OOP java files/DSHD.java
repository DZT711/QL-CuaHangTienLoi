import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class DSHD implements IQuanLy {
    public static ArrayList<hoaDon> danhSacHHoaDons = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    private int n;

    private boolean isMaHDTrung(String maHD) {
        for (hoaDon hd : danhSacHHoaDons) {
            if (hd.getMaHD().equals(maHD)) {
                return true; // trả về nếu trùng
            }
        }
        return false; // trả về nếu không trùng
    }

    private boolean isMaKHDaSuDung(String maKH) {
        for (hoaDon hd : danhSacHHoaDons) {
            if (hd.getKhachhang().getMaKH().equals(maKH)) {
                return true; // trả về nếu mã khách hàng đã được sử dụng
            }
        }
        return false; // trả về nếu mã khách hàng chưa được sử dụng
    }

    private void capNhatFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("DanhSachHoaDon.txt", false))) {
            if (danhSacHHoaDons.isEmpty()) {
                bw.write("Danh sach hoa don hien tai rong.\n");
            } else {
                bw.write("=========== DANH SACH MA HOA DON ===========\n");
                for (hoaDon hd : danhSacHHoaDons) {
                    bw.write("Ma hoa don: " + hd.getMaHD() + "\n");
                }
            }
            System.out.println("DANH SACH HOA DON DA DUOC CAP NHAT VAO FILE.");
        } catch (IOException e) {
            System.out.println("LOI KHI GHI FILE: " + e.getMessage());
        }
    }

    public void docFile() {
        try {
            FileReader fr = new FileReader("docFileHD.txt");
            BufferedReader br = new BufferedReader(fr);

            String line = null;
            outerLoop: while (true) {
                line = br.readLine();
                if (line == null)
                    break;

                String[] txt = line.split(",");
                String maHD = txt[0];
                String maKH = txt[1];
                String maNV = txt[2];

                if (isMaHDTrung(maHD))
                    continue outerLoop;
                if (!DSNV.isMaNVTonTai(maNV))
                    continue outerLoop;
                if (!DSKH.isMaKHTonTai(maKH))
                    continue outerLoop;

                khachHang kh = DSKH.getKH(maKH);
                nhanVien nv = DSNV.getNV(maNV);
                hoaDon hd = new hoaDon(maHD, kh, nv);
                danhSacHHoaDons.add(hd);
                System.out.println("Doc file thanh cong");
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
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

    public void timKiem() {
        while (true) {
            System.out.println("\n___________________________________________________");
            System.out.println("|                                                 |");
            System.out.println("|          MENU TIM KIEM CHI TIET HOA DON         |");
            System.out.println("|_________________________________________________|");
            System.out.println("|                                                 |");
            System.out.println("| 1. TIM KIEM BANG MA HOA DON                     |");
            System.out.println("| 2. THOAT CHUONG TRINH                           |");
            System.out.println("|_________________________________________________|");
            System.out.print("NHAP LUA CHON CUA BAN: ");
            int choice = sc.nextInt();
            sc.nextLine();

            // Kiểm tra danh sách hóa đơn có rỗng không trước khi vào vòng lặp
            if (danhSacHHoaDons.isEmpty()) {
                System.out.println("KHONG CO HOA DON! VUI LONG THEM HOA DON TRUOC.");
                return; // Thoát hàm nếu danh sách rỗng
            }

            switch (choice) {
                case 1:
                    System.out.print("NHAP MA HOA DON: ");
                    String maHD = sc.nextLine();

                    // Tìm kiếm hóa đơn
                    hoaDon hoaDonTimThay = null;
                    for (hoaDon hd : danhSacHHoaDons) {
                        if (hd.getMaHD().equals(maHD)) {
                            hoaDonTimThay = hd;
                            break; // Dừng vòng lặp khi tìm thấy
                        }
                    }
                    if (hoaDonTimThay != null) {
                        hoaDonTimThay.printChiTietHoaDon();
                        System.out.println("\n===========================");

                        // Ghi chi tiết hóa đơn vào file
                        try (BufferedWriter bw = new BufferedWriter(new FileWriter("ChiTietHoaDon.txt", true))) {
                            bw.write("Chi tiet hoa don voi ma: " + maHD + "\n");
                            bw.write(hoaDonTimThay.toString()); // Giả sử `toString` trả về thông tin chi tiết
                            bw.write("\n===========================\n");
                            System.out.println("Thong tin chi tiet da duoc ghi vao file ChiTietHoaDon.txt");
                        } catch (IOException e) {
                            System.out.println("Loi khi ghi file: " + e.getMessage());
                        }
                    } else {
                        System.out.println("KHONG TIM THAY HOA DON VOI MA: " + maHD);
                    }
                    break;

                case 2:
                    System.out.println("THOAT CHUC NANG TIM KIEM HOA DON.");
                    return; // Thoát hàm khi người dùng chọn thoát

                default:
                    System.out.println("LUA CHON KHONG HOP LE! VUI LONG THU LAI.");
            }
        }
    }

    public void xoa() {
        while (true) {
            System.out.println("\n____________________________________");
            System.out.println("|                                  |");
            System.out.println("|         MENU XOA HOA DON         |");
            System.out.println("|__________________________________|");
            System.out.println("|                                  |");
            System.out.println("| 1. XOA HOA DON BANG MA           |");
            System.out.println("| 2. THOAT                         |");
            System.out.println("|__________________________________|");
            System.out.print("NHAP LUA CHON : ");
            int choice = sc.nextInt();
            sc.nextLine();
            if (danhSacHHoaDons.isEmpty()) {
                System.out.print("Khong CO HOA DON!\nVUI LONG THEM HOA DON.");
                break;
            }
            switch (choice) {
                case 1:
                    System.out.println("nhap ma HD: ");
                    String maHD = sc.nextLine();
                    boolean isDeleted = false; // Biến cờ kiểm tra việc xóa thành công
                    for (hoaDon hd : danhSacHHoaDons) {
                        if (hd.getMaHD().equals(maHD)) {
                            danhSacHHoaDons.remove(hd);
                            System.out.println(" xoa hoa don thanh cong");
                            isDeleted = true; // Đánh dấu xóa thành công
                            capNhatFile(); // Ghi danh sách mới vào file
                            break;
                        }
                    }
                    if (isDeleted) {
                        // Ghi thông tin xóa vào file
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
                    break;

                case 2:
                    System.out.println("hen gap lai quy khach");
                    break;
                default:
                    System.out.println("LUA CHON KHONG HOP LE\nVUI LONG NHAP LAI");
                    break;
            }
        }
    }

    public void sua() {
        while (true) {
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

            if (choice == 2) {
                System.out.println("THOAT CHUC NANG SUA HOA DON.");
                break;
            }

            switch (choice) {
                case 1:
                    if (danhSacHHoaDons.isEmpty()) {
                        System.out.println("KHONG CO HOA DON! VUI LONG THEM HOA DON TRUOC.");
                        break;
                    }

                    // Nhập mã hóa đơn cần sửa
                    System.out.print("NHAP MA HOA DON CAN SUA: ");
                    String maHD = sc.nextLine();
                    boolean found = false;

                    // Duyệt danh sách hóa đơn để tìm hóa đơn cần sửa
                    for (hoaDon hd : danhSacHHoaDons) {
                        if (hd.getMaHD().equals(maHD)) {
                            System.out.print("NHAP MA HOA DON MOI: ");
                            String maHDMoi = sc.nextLine();

                            // Kiểm tra trùng lặp mã mới
                            if (isMaHDTrung(maHDMoi)) {
                                System.out.println("MA HOA DON MOI BI TRUNG, VUI LONG NHAP LAI.");
                            } else {
                                // Cập nhật mã hóa đơn
                                hd.setMaHD(maHDMoi);

                                // Ghi log vào file
                                try (BufferedWriter bw = new BufferedWriter(
                                        new FileWriter("ChiTietHoaDon.txt", true))) {
                                    bw.write("HOA DON VOI MA '" + maHD + "' DA DUOC SUA THANH MA MOI '" + maHDMoi
                                            + "'.\n");
                                    bw.write("-------------------------------------\n");
                                    bw.write(hd.toString() + "\n"); // Ghi chi tiết hóa đơn mới vào file
                                    bw.write("=====================================\n");
                                } catch (IOException e) {
                                    System.out.println("LOI KHI GHI FILE: " + e.getMessage());
                                }

                                System.out.println("CAP NHAT MA HOA DON VAO FILE THANH CONG.");
                                capNhatFile(); // Ghi danh sách mới vào file
                            }
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("KHONG TIM THAY HOA DON VOI MA: " + maHD);
                    }
                    break;

                default:
                    System.out.println("LUA CHON KHONG HOP LE, VUI LONG THU LAI.");
            }
        }
    }

    public void xuat() {
        System.out.print("\033[H\033[2J"); // Làm sạch màn hình console
        System.out.flush();

        // Hiển thị số lượng hóa đơn
        System.out.println("so luong hoa don hien tai: " + hoaDon.getSoHoaDon());
        System.out.println("\n===========DANH SACH HOA DON===========\n");

        if (danhSacHHoaDons.isEmpty()) {
            System.out.println("KHONG CO HOA DON NAO.");
        } else {
            for (hoaDon hd : danhSacHHoaDons) {
                hd.xuat(); // Gọi phương thức xuat() để in ra thông tin của từng hóa đơn
            }
        }
        capNhatFile(); // Ghi danh sách mới vào file
    }
}