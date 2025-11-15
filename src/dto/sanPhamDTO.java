package dto;

import java.util.Scanner;
import util.FormatUtil;
import util.ValidatorUtil;

public class SanPhamDTO {
    private String maSP;
    private String tenSP;
    private int loaiSP;
    private int donViTinh;
    private int soLuongTon;
    private int giaBan;
    private String moTa;
    private String trangThai;

    public SanPhamDTO() {
    }

    public SanPhamDTO(String maSP, String tenSP, int loaiSP, int donViTinh, int soLuongTon, int giaBan, String moTa,
            String trangThai) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.loaiSP = loaiSP;
        this.donViTinh = donViTinh;
        this.soLuongTon = soLuongTon;
        this.giaBan = giaBan;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(int loaiSP) {
        this.loaiSP = loaiSP;
    }

    public int getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(int donViTinh) {
        this.donViTinh = donViTinh;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public boolean nhapThongTinSanPham() {
        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {
                System.out.print("Nhập tên sản phẩm (hoặc '0' để hủy): ");
                String input = scanner.nextLine().trim();

                if ("0".equals(input))
                    return false;
                if (ValidatorUtil.isValidString(input)) {
                    this.tenSP = input;
                    break;
                }
                System.out.println("❌ Tên sản phẩm không hợp lệ! Vui lòng nhập lại.");
            }

            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║       DANH SÁCH LOẠI SẢN PHẨM      ║");
            System.out.println("╠════╦═══════════════════════════════╣");
            System.out.println("║  1 ║ Đồ uống                       ║");
            System.out.println("║  2 ║ Thực phẩm ăn liền             ║");
            System.out.println("║  3 ║ Bánh kẹo & Snack              ║");
            System.out.println("║  4 ║ Sữa & sản phẩm từ sữa         ║");
            System.out.println("║  5 ║ Thực phẩm khô & gia vị        ║");
            System.out.println("║  6 ║ Đồ gia dụng & vệ sinh cá nhân ║");
            System.out.println("║  7 ║ Mỹ phẩm & chăm sóc cơ thể     ║");
            System.out.println("║  8 ║ Đồ dùng văn phòng & tiện ích  ║");
            System.out.println("║  9 ║ Thức ăn & phụ kiện thú cưng   ║");
            System.out.println("║ 10 ║ Đồ y tế & chăm sóc sức khỏe   ║");
            System.out.println("╚════╩═══════════════════════════════╝");
            while (true) {
                System.out.print("Nhập loại sản phẩm ('0' để hủy): ");
                String input = scanner.nextLine().trim();
                if ("0".equals(input))
                    return false;

                try {
                    this.loaiSP = Integer.parseInt(input);
                    if (this.loaiSP >= 1 && this.loaiSP <= 10)
                        break;
                    else
                        System.out.println("❌ Loại sản phẩm phải từ 1-10!");
                } catch (NumberFormatException e) {
                    System.out.println("❌ Loại sản phẩm không hợp lệ! Vui lòng nhập số!");
                }
            }

            System.out.println("\n╔══════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                               ĐƠN VỊ TÍNH                                ║");
            System.out.println("╠══════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║  1. Chai      2. Gói       3. Lon       4. Hộp       5. Thùng      6. Bộ ║");
            System.out.println("║  7. Vỉ        8. Cuộn      9. Túi      10. Can      11. Bao              ║");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════╝");
            while (true) {
                System.out.print("Nhập đơn vị tính ('0' để hủy): ");
                String input = scanner.nextLine().trim();
                if ("0".equals(input))
                    return false;

                try {
                    this.donViTinh = Integer.parseInt(input);
                    if (this.donViTinh >= 1 && this.donViTinh <= 11)
                        break;
                    else
                        System.out.println("❌ Đơn vị tính phải từ 1-11!");
                } catch (NumberFormatException e) {
                    System.out.println("❌ Đơn vị tính không hợp lệ! Vui lòng nhập số!");
                }
            }

            while (true) {
                System.out.print("Nhập giá bán (hoặc '0' để hủy): ");
                String input = scanner.nextLine().trim();
                if ("0".equals(input))
                    return false;

                try {
                    this.giaBan = Integer.parseInt(input);
                    if (this.giaBan > 0 && this.giaBan <= 1_000_000)
                        break;
                    else
                        System.out.println("❌ Giá bán phải lớn hơn 0 và không vượt quá 1 triệu!");
                } catch (NumberFormatException e) {
                    System.out.println("❌ Giá bán không hợp lệ! Vui lòng nhập số!");
                }
            }

            while (true) {
                System.out.print("Nhập mô tả sản phẩm (Enter để bỏ qua hoặc '0' để hủy): ");
                String input = scanner.nextLine().trim();

                if ("0".equals(input))
                    return false;

                if (input.isEmpty()) {
                    this.moTa = null;
                    System.out.println("ℹ️  Đã bỏ qua mô tả.");
                    break;
                }

                if (ValidatorUtil.isValidString(input)) {
                    this.moTa = input;
                    break;
                }
                System.out.println("❌ Mô tả không hợp lệ! Vui lòng nhập lại.");
            }
            return true;
        } catch (Exception e) {
            System.err.println("❌ Lỗi không mong muốn: " + e.getMessage());
            return false;
        }
    }

    public String getLoaiText() {
        String[] loai = {
                "",
                "Đồ uống",
                "Thực phẩm ăn liền",
                "Bánh kẹo & Snack",
                "Sữa & sản phẩm từ sữa",
                "Thực phẩm khô & gia vị",
                "Đồ gia dụng & vệ sinh cá nhân",
                "Mỹ phẩm & chăm sóc cơ thể",
                "Đồ dùng văn phòng & tiện ích",
                "Thức ăn & phụ kiện thú cưng",
                "Đồ y tế & chăm sóc sức khỏe"
        };

        return (loaiSP > 0 && loaiSP < loai.length)
                ? loai[loaiSP]
                : "Loại " + loaiSP;
    }

    public String getDonViText() {
        String[] donVi = {
                "", "Chai", "Gói", "Lon", "Hộp", "Thùng", "Bộ", "Vỉ", "Cuộn", "Túi", "Can", "Bao"
        };

        return (donViTinh > 0 && donViTinh < donVi.length)
                ? donVi[donViTinh]
                : "Đơn vị: " + donViTinh;
    }

    public void inThongTinSanPham() {
        System.out.println("┌────────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf("│ Mã SP         : %-63s│\n", maSP);
        System.out.printf("│ Tên sản phẩm  : %-63s│\n", tenSP);
        System.out.printf("│ Loại          : %-63s│\n", getLoaiText());
        System.out.printf("│ Đơn vị tính   : %-63s│\n", getDonViText());
        System.out.printf("│ Số lượng tồn  : %-63d│\n", soLuongTon);
        System.out.printf("│ Giá bán       : %-63s│\n", FormatUtil.formatVND(giaBan));
        System.out.printf("│ Mô tả         : %-63s│\n", moTa != null ? moTa : "(Không có)");
        System.out.println("└────────────────────────────────────────────────────────────────────────────────┘");
    }

    public boolean sua() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Sửa tên sản phẩm: ");
        String newTenSP = scanner.nextLine().trim();
        if (newTenSP.equals("0"))
            return false;
        if (!newTenSP.isEmpty()) {
            if (!ValidatorUtil.isValidString(newTenSP)) 
                System.out.println("❌ Tên sản phẩm không hợp lệ! Giữ nguyên tên sản phẩm.");
            else this.tenSP = newTenSP;
        }

        // sửa loại sản phẩm
        System.out.print("Sửa loại sản phẩm (1-10): ");
        String inputLoai = scanner.nextLine().trim();
        if (inputLoai.equals("0"))
            return false;
        if (!inputLoai.isEmpty()) {
            try {
                int newLoaiSP = Integer.parseInt(inputLoai);
                if (newLoaiSP >= 1 && newLoaiSP <= 10)
                    this.loaiSP = newLoaiSP;
                else
                    System.out.println("❌ Loại phải từ 1-10, giữ nguyên loại sản phẩm");
            } catch (NumberFormatException e) {
                System.out.println("❌ Giá trị không hợp lệ, giữ nguyên loại sản phẩm");
            }
        }

        System.out.print("Sửa đơn vị tính (1-11): ");
        String inputDonVi = scanner.nextLine().trim();
        if (inputDonVi.equals("0"))
            return false;
        if (!inputDonVi.isEmpty()) {
            try {
                int newDonVi = Integer.parseInt(inputDonVi);
                if (newDonVi >= 1 && newDonVi <= 11)
                    this.donViTinh = newDonVi;
                else
                    System.out.println("❌ Đơn vị phải từ 1-11, giữ nguyên đơn vị tính");
            } catch (NumberFormatException e) {
                System.out.println("❌ Giá trị không hợp lệ, giữ nguyên đơn vị tính");
            }
        }

        System.out.print("Sửa giá bán: ");
        String inputGia = scanner.nextLine().trim();
        if (inputGia.equals("0"))
            return false;
        if (!inputGia.isEmpty()) {
            try {
                int newGia = Integer.parseInt(inputGia);
                if (newGia > 0 && newGia <= 1_000_000)
                    this.giaBan = newGia;
                else
                    System.out.println("❌Giá phải lớn hơn 0 và nhỏ hơn hoặc bằng 1,000,000, giữ nguyên giá");
            } catch (NumberFormatException e) {
                System.out.println("❌Giá trị không hợp lệ, giữ nguyên giá");
            }
        }

        System.out.print("Sửa mô tả: ");
        String newMoTa = scanner.nextLine().trim();
        if (newMoTa.equals("0"))
            return false;
        if (!newMoTa.isEmpty()) {
            if (!ValidatorUtil.isValidString(newMoTa)) {
                System.out.println("❌ Mô tả không hợp lệ! Giữ nguyên mô tả.");
            } else this.moTa = newMoTa;
        }
        

        System.out.print("Sửa trạng thái (active / inactive): ");
        String newTrangThai = scanner.nextLine().trim().toLowerCase();
        if (newTrangThai.equals("0"))
            return false;

        if (!newTrangThai.isEmpty()) {
            if ("active".equals(newTrangThai) || "inactive".equals(newTrangThai)) {
                this.trangThai = newTrangThai;
            } else if (!newTrangThai.isEmpty()) {
                System.out.println("❌ Trạng thái không hợp lệ, giữ nguyên trạng thái");
            }
        }

        return true;
    }
}
