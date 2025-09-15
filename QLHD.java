import java.util.Scanner;
import java.util.ArrayList;

public class QLHD {
    Scanner sc = new Scanner(System.in);

    public void menuQLHD() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        DSHD dshd = new DSHD();
        int choice;
        do {
            System.out.println("\n_____________________________________");
            System.out.println("|                                   |");
            System.out.println("|      MENU QUAN LI HOA DON         |");
            System.out.println("|___________________________________|");
            System.out.println("|                                   |");
            System.out.println("| 1. THEM HOA DON                   |");
            System.out.println("| 2. SUA HOA DON                    |");
            System.out.println("| 3. XOA HOA DON                    |");
            System.out.println("| 4. TIM KIEM CHI TIET HOA DON      |");
            System.out.println("| 5. XUAT DANH SACH HOA DON         |");
            System.out.println("| 6. DOC FILE                       |");
            System.out.println("| 0. THOAT                          |");
            System.out.println("|___________________________________|");
            System.out.print("NHAP LUA CHON CUA BAN : ");
            choice = sc.nextInt();
            sc.nextLine();
            System.out.println("");

            switch (choice) {
                case 1:
                    dshd.them();
                    break;
                case 2:
                    dshd.sua();
                    break;
                case 3:
                    dshd.xoa();
                    break;
                case 4:
                    dshd.timKiem();
                    break;
                case 5:
                    dshd.xuat();
                    break;
                case 6:
                    dshd.docFile();
                case 0:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    break;
                default:
                    System.out.println("LUA CHON KHONG HOP LE, VUI LONG NHAP LAI!");
                    break;
            }
        } while (choice != 0);
    }
}
