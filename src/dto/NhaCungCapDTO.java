package dto;

import java.util.Scanner;

public class NhaCungCapDTO {
    private String maNCC;
    private String tenNCC;
    private String diaChi;
    private String dienThoai;
    private String email;
    private String trangThai;

    public NhaCungCapDTO() {}

    public NhaCungCapDTO(String maNCC, String tenNCC, String diaChi, String dienThoai, String email, String trangThai) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.diaChi = diaChi;
        this.dienThoai = dienThoai;
        this.email = email;
        this.trangThai = trangThai;
    }

    // Getters và Setters
    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    // In thông tin nhà cung cấp
    public void inThongTinNCC() {
        System.out.printf("%-10s | %-25s | %-25s | %-12s | %-25s | %-10s\n",
                maNCC, tenNCC, diaChi, dienThoai, email, trangThai);
    }

    // Hàm nhập/sửa
    public boolean sua() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Nhập tên NCC mới: ");
            String ten = scanner.nextLine().trim();
            if (ten.equals("0")) return false;
            if (!ten.isEmpty()) this.tenNCC = ten;

            System.out.print("Nhập địa chỉ mới: ");
            String dc = scanner.nextLine().trim();
            if (dc.equals("0")) return false;
            if (!dc.isEmpty()) this.diaChi = dc;

            System.out.print("Nhập điện thoại mới: ");
            String sdt = scanner.nextLine().trim();
            if (sdt.equals("0")) return false;
            if (!sdt.isEmpty()) this.dienThoai = sdt;

            System.out.print("Nhập email mới: ");
            String mail = scanner.nextLine().trim();
            if (mail.equals("0")) return false;
            if (!mail.isEmpty()) this.email = mail;

            System.out.print("Nhập trạng thái mới (active/inactive): ");
            String tt = scanner.nextLine().trim();
            if (tt.equals("0")) return false;
            if (!tt.isEmpty()) this.trangThai = tt;
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi khi sửa thông tin NCC: " + e.getMessage());
            return false;
        }
    }
    public boolean isValid() {
        // Kiểm tra số điện thoại hợp lệ hay không 
        if (dienThoai != null && !dienThoai.trim().isEmpty()) {
            if (!dienThoai.matches("\\d{9,11}")) {
                System.out.println("Số điện thoại phải là 9–11 chữ số.");
                return false;
            }
        }
        // Kiểm tra email hợp lệ hay không
        if (email != null && !email.trim().isEmpty()) {
            if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                System.out.println("Email không hợp lệ.");
                return false;
            }
        }
        return true; 
    }
}



