import java.util.Scanner;

public class mainMenu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.print("\033[H\033[2J");
        System.out.flush();
        do {
            System.out.println("\n______________________________");
            System.out.println("|                            |");
            System.out.println("|       MENU QUAN LI         |");
            System.out.println("|____________________________|");
            System.out.println("|                            |");
            System.out.println("| 1. QUAN LI SAN PHAM        |");
            System.out.println("| 2. QUAN LI KHACH HANG      |");
            System.out.println("| 3. QUAN LI NHAN VIEN       |");
            System.out.println("| 4. QUAN LI HOA DON         |");
            System.out.println("| 0. Thoat                   |");
            System.out.println("|____________________________|");
            System.out.print("Nhap lua chon cua ban: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    QLSP qlsp = new QLSP();
                    qlsp.menuQLSP();
                    break;
                case 2:
                    QLKH qlkh = new QLKH();
                    qlkh.menuQLKH();
                    break;
                case 3:
                    QLNV qlnv = new QLNV();
                    qlnv.menuQLNV();
                    break;
                case 4:
                    QLHD qlhd = new QLHD();
                    qlhd.menuQLHD();
                    break;
                default:
                    break;
            }
        } while (choice != 0);
    }
}
