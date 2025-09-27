
import Login.login;

import view.QuanLyKhachHang;

public class Main {
    public static void main(String[] args) {
        try {
            login loginForm = new login();
            if (loginForm.LoginForm()) {
                // If login is successful, proceed to the main application
                QuanLyKhachHang qlkh = new QuanLyKhachHang();
                qlkh.menuQuanLyKhachHang();
            }
            // QuanLyKhachHang qlkh = new QuanLyKhachHang();
            // qlkh.menuQuanLyKhachHang();
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}