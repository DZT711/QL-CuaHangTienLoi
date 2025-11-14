package dto;

import java.util.Scanner;
import dao.NhaCungCapDAO;

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
            System.out.print("Nhập tên NCC mới (ENTER ĐỂ GIỮ NGUYÊN): ");
            String ten = scanner.nextLine().trim();
            if (ten.equals("0")) return false;
            if (!ten.isEmpty()) this.tenNCC = ten;

            System.out.print("Nhập địa chỉ mới (ENTER ĐỂ GIỮ NGUYÊN):  ");
            String dc = scanner.nextLine().trim();
            if (dc.equals("0")) return false;
            if (!dc.isEmpty()) this.diaChi = dc;

            while (true) {
                System.out.print("Nhập điện thoại mới (ENTER để giữ nguyên): ");
                String sdt = scanner.nextLine().trim();

                if (sdt.equals("0")) return false; // hủy toàn bộ thao tác sửa

                if (sdt.isEmpty()) break; // giữ nguyên (ENTER)

                if (!sdt.matches("\\d{9,11}")) {
                    System.out.println("  Số điện thoại không hợp lệ (phải có 9–11 chữ số). Vui lòng nhập lại hoặc nhấn ENTER để giữ nguyên:");
                    continue; // 
                }
                if (!sdt.equals(this.dienThoai) && NhaCungCapDAO.checkDienThoaiExist(sdt)) {
                    System.out.println("  ❌ Số điện thoại này đã thuộc về một NCC khác. Vui lòng nhập lại!");
                    continue; // Yêu cầu nhập lại
                }

                this.dienThoai = sdt; 
                break; 
            }

            while (true) {
                System.out.print("Nhập email mới (ENTER ĐỂ GIỮ NGUYÊN): ");
                String mail = scanner.nextLine().trim();
                if (mail.equals("0")) return false; // hủy toàn bộ thao tác sửa
                if (mail.isEmpty()) break; // giữ nguyên (ENTER)
                if (!mail.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    System.out.println("Email không hợp lệ. Vui lòng nhập lại hoặc nhấn ENTER để giữ nguyên");
                    continue;
                }
                if (!mail.equals(this.email) && NhaCungCapDAO.checkEmailExist(mail)) {
                    System.out.println("  ❌ Email này đã thuộc về một NCC khác. Vui lòng nhập lại!");
                    continue; // Yêu cầu nhập lại
                }
                this.email = mail;
                break;
            }
         
            while (true) {
                System.out.print("Nhập trạng thái mới (active / inactive) (ENTER để giữ nguyên): ");
                String tt = scanner.nextLine().trim(); // nhập thường, xử lý thống nhất

                if (tt.equals("0")) return false; // hủy toàn bộ thao tác sửa
                if (tt.isEmpty()) break; // giữ nguyên trạng thái cũ nếu Enter

                if (!tt.equals("active") && !tt.equals("inactive")) {
                    System.out.println("  Trạng thái không hợp lệ! Chỉ được nhập 'active' hoặc 'inactive'.");
                    continue; // yêu cầu nhập lại
                }

                this.trangThai = tt; // hợp lệ → cập nhật
                break;
            }
            return true;

        } catch (Exception e) {
            System.out.println("Lỗi khi sửa thông tin NCC: " + e.getMessage());
            return false;
        }
    }
    
}
