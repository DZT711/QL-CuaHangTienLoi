import java.util.Scanner;

public class QLSP {
    public void menuQLSP() {
        Scanner sc = new Scanner(System.in);
        DSSP dssp = new DSSP();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        int choice;
        do {
            System.out.println("\n_____________________________________");
            System.out.println("|                                   |");
            System.out.println("|      MENU QUAN LI SAN PHAM        |");
            System.out.println("|___________________________________|");
            System.out.println("|                                   |");
            System.out.println("| 1. THEM SAN PHAM                  |");
            System.out.println("| 2. SUA SAN PHAM                   |");
            System.out.println("| 3. XOA SAN PHAM                   |");
            System.out.println("| 4. TIM KIEM SAN PHAM              |");
            System.out.println("| 5. XEM DANH SACH SAN PHAM         |");
            System.out.println("| 6. TAI DANH SACH FILE SAN PHAM    |");
            System.out.println("| 7. XUAT DANH SACH FILE SAN PHAM   |");
            System.out.println("| 0. THOAT                          |");
            System.out.println("|___________________________________|");
            System.out.print("Nhap lua chon cua ban: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    dssp.them();
                    break;
                case 2:
                    dssp.sua();
                    break;
                case 3:
                    dssp.xoa();
                    break;
                case 4:
                    dssp.timKiem();
                    break;
                case 5:
                    dssp.xuat();
                    break;
                case 6:
                    dssp.docFile("docFileSP.txt");
                    break;
                case 7:
                    dssp.ghiFile("xuatFileSP.txt");

                    break;
                case 0:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    break;
                default:
                    System.out.println("LUA CHON KHONG HOP LE , VUI LONG NHAP LAI   !");
                    break;
            }
        } while (choice != 0);

    }
}
