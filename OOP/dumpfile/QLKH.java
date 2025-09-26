import java.util.Scanner;

public class QLKH {
    Scanner sc = new Scanner(System.in);

    public void menuQLKH() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        DSKH dskh = new DSKH();
        int choice;
        do {
            System.out.println("_____________________________________");
            System.out.println("|                                   |");
            System.out.println("|      MENU QUAN LI KHACH HANG      |");
            System.out.println("|___________________________________|");
            System.out.println("|                                   |");
            System.out.println("| 1. THEM KHACH HANG                |");
            System.out.println("| 2. SUA KHACH HANG                 |");
            System.out.println("| 3. TIM KHACH HANG                 |");
            System.out.println("| 4. XEM DANH SACH KHACH HANG       |");
            System.out.println("| 5. XEM TOP 5 KHACH HANG CUA CHTL  |");
            System.out.println("| 6. DOC DU LIEU TU FILE            |");
            System.out.println("| 7. CHAY TESTCASE                  |");
            System.out.println("| 0. THOAT                          |");
            System.out.println("|___________________________________|");
            System.out.print("Nhap lua chon cua ban: ");
            choice = sc.nextInt();
            sc.nextLine();
            System.out.print("\033[H\033[2J");
            System.out.flush();
            switch (choice) {
                case 1:
                    dskh.themKH();
                    break;
                case 2:
                    dskh.suaKH();
                    break;
                case 3:
                    dskh.timKH();
                    break;
                case 4:
                    dskh.xemDSKH();
                    break;
                case 5:
                    dskh.topFiveKH();
                    break;
                case 6:
                    dskh.readFile();
                    break;
                case 7:
                    dskh.menuTestCase();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lua chon khong hop le, vui long nhap lai!");
                    break;
            }
        } while (choice != 0);
    }
}
