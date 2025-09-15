import java.util.Scanner;

public class doGiaDung extends sanPham {
    private String chatLieu;
    Scanner sc = new Scanner(System.in);

    public doGiaDung() {
    }

    public doGiaDung(String chatLieu, String maSp, String tenSP, int soLuong, double gia) {
        super(maSp, tenSP, soLuong, gia);
        this.chatLieu = chatLieu;

    }

    public String getMaSac() {
        return chatLieu;
    }

    public void setchatLieu(String newChatLieu) {
        this.chatLieu = newChatLieu;
    }

    public void nhap() {

        super.nhap();
        System.out.print("nhap chat lieu: ");
        chatLieu = sc.nextLine();

    }

    public void xuat() {

        super.xuat();

        System.out.println("| CHAT LIEU : " + chatLieu);
        System.out.println("|________________________________________________________|");
    }
}
