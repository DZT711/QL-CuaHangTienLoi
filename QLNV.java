
//import java.lang.classfile.instruction.SwitchCase;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class QLNV {
    private static DSNV ds = new DSNV();

    public void menuQLNV() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("______________________________________");
            System.out.println("|                                    |");
            System.out.println("|       MENU QUAN LY NHAN VIEN       |");
            System.out.println("|____________________________________|");
            System.out.println("|                                    |");
            System.out.println("| 1. THEM NHAN VIEN                  |");
            System.out.println("| 2. XOA NHAN VIEN                   |");
            System.out.println("| 3. SUA NHAN VIEN                   |");
            System.out.println("| 4. TIM KIEM NHAN VIEN              |");
            System.out.println("| 5. HIEN THI DANH SACH NHAN VIEN    |");
            System.out.println("| 6. XUAT DANH SACH FILE NHAN VIEN   |");
            System.out.println("| 7. TAI DANH SACH FILE NHAN VIEN    |");
            System.out.println("| 0. THOAT                           |");
            System.out.println("|____________________________________|");
            System.out.println("Chon chuc nang: ");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    ds.them();
                    break;
                case 2:
                    ds.xoa();
                    ds.ghiFile();
                    break;
                case 3:
                    ds.sua();
                    ds.ghiFile();
                    break;
                case 4:
                    ds.timKiem();
                    break;
                case 5:
                    System.out.println("DANH SACH NHAN VIEN: ");
                    ds.xemDSNV();
                    break;
                case 6:
                    ds.ghiFile();
                    break;
                case 7:
                    ds.docFile();
                    break;
                case 0:
                    System.out.println("THOAT. ");
                    break;
                default:
                    System.out.println("LUA CHON KHONG HOP LE. VUI LONG CHON LAI!");
            }
        } while (option != 0);

    }
}
