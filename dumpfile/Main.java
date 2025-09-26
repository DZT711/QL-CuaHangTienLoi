import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("===== QUẢN LÝ CỬA HÀNG TIỆN LỢI =====");
            System.out.println("1. Quản lý Sản phẩm");
            System.out.println("2. Quản lý Khách hàng");
            System.out.println("3. Quản lý Hóa đơn");
            System.out.println("4. Thoát");
            System.out.print("Vui lòng chọn chức năng: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    menuQuanLySanPham(scanner);
                    break;
                case 2:
                    menuQuanLyKhachHang(scanner);
                    break;
                case 3:
                    menuQuanLyHoaDon(scanner);
                    break;
                case 4:
                    System.out.println("Thoát chương trình. Tạm biệt!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (choice != 4);

        scanner.close();
    }

    private static void menuQuanLySanPham(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n--- Quản lý Sản phẩm ---");
            System.out.println("1. Xem danh sách sản phẩm");
            System.out.println("2. Thêm sản phẩm");
            System.out.println("3. Sửa sản phẩm");
            System.out.println("4. Xóa sản phẩm");
            System.out.println("5. Quay lại menu chính");
            System.out.print("Chọn chức năng: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Gọi hàm hiển thị danh sách sản phẩm
                    System.out.println("Chức năng xem danh sách sản phẩm (chưa triển khai)");
                    break;
                case 2:
                    // Gọi hàm thêm sản phẩm
                    System.out.println("Chức năng thêm sản phẩm (chưa triển khai)");
                    break;
                case 3:
                    // Gọi hàm sửa sản phẩm
                    System.out.println("Chức năng sửa sản phẩm (chưa triển khai)");
                    break;
                case 4:
                    // Gọi hàm xóa sản phẩm
                    System.out.println("Chức năng xóa sản phẩm (chưa triển khai)");
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 5);
    }

    private static void menuQuanLyKhachHang(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n--- Quản lý Khách hàng ---");
            System.out.println("1. Xem danh sách khách hàng");
            System.out.println("2. Thêm khách hàng");
            System.out.println("3. Sửa khách hàng");
            System.out.println("4. Xóa khách hàng");
            System.out.println("5. Quay lại menu chính");
            System.out.print("Chọn chức năng: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Chức năng xem danh sách khách hàng (chưa triển khai)");
                    break;
                case 2:
                    System.out.println("Chức năng thêm khách hàng (chưa triển khai)");
                    break;
                case 3:
                    System.out.println("Chức năng sửa khách hàng (chưa triển khai)");
                    break;
                case 4:
                    System.out.println("Chức năng xóa khách hàng (chưa triển khai)");
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 5);
    }

    private static void menuQuanLyHoaDon(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n--- Quản lý Hóa đơn ---");
            System.out.println("1. Xem danh sách hóa đơn");
            System.out.println("2. Thêm hóa đơn");
            System.out.println("3. Xóa hóa đơn");
            System.out.println("4. Quay lại menu chính");
            System.out.print("Chọn chức năng: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Chức năng xem danh sách hóa đơn (chưa triển khai)");
                    break;
                case 2:
                    System.out.println("Chức năng thêm hóa đơn (chưa triển khai)");
                    break;
                case 3:
                    System.out.println("Chức năng xóa hóa đơn (chưa triển khai)");
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 4);
    }
}
