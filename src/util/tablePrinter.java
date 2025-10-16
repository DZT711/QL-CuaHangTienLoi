package util;

import java.util.List;
import java.util.Scanner;

import dto.NhanVienDTO;

public class tablePrinter {
    private static final Scanner STDIN = new Scanner(System.in);

    /**
     * In bảng với header + các dòng, tự động tính độ rộng mỗi cột.
     * 
     * @param headers danh sách tiêu đề cột
     * @param rows    danh sách dòng, mỗi dòng là danh sách chuỗi chứa giá trị cột
     *                tương ứng
     */
    public static void printTable(List<String> headers, List<List<String>> rows) {
        int cols = headers.size();
        int[] colWidths = new int[cols];
        // Khởi độ rộng từ tiêu đề
        for (int i = 0; i < cols; i++) {
            colWidths[i] = headers.get(i).length();
        }
        // Cập nhật độ rộng từ dữ liệu dòng
        for (List<String> row : rows) {
            for (int i = 0; i < cols; i++) {
                String cell = row.get(i);
                if (cell != null) {
                    int len = cell.length();
                    if (len > colWidths[i]) {
                        colWidths[i] = len;
                    }
                }
            }
        }

        // Tạo định dạng printf cho mỗi cột, căn trái
        StringBuilder fmtBuilder = new StringBuilder();
        fmtBuilder.append("|");
        for (int i = 0; i < cols; i++) {
            fmtBuilder.append(" %-").append(colWidths[i]).append("s |");
        }
        String fmt = fmtBuilder.toString();

        // In header
        System.out.printf(fmt + "%n", headers.toArray());

        // In đường kẻ phân cách
        // Tính tổng độ rộng bảng
        int totalWidth = 1; // bắt đầu với ký tự '|'
        for (int w : colWidths) {
            totalWidth += 1 + w + 1 + 1; // " space" + nội dung + " space" + "|"
        }
        for (int i = 0; i < totalWidth; i++) {
            System.out.print("-");
        }
        System.out.println();

        // In các dòng dữ liệu
        for (List<String> row : rows) {
            System.out.printf(fmt + "%n", row.toArray());
        }
    }

    /**
     * In chi tiết đầy đủ một nhân viên theo định dạng đẹp.
     */
    public static void inThongTinNhanVienChiTiet(NhanVienDTO nv) {
        if (nv == null) {
            System.out.println("❌ Nhân viên không tồn tại!");
            return;
        }
        // --- Bắt đầu đoạn code đã sửa ---

        // Đặt chiều rộng nội dung vào một biến để dễ thay đổi
        int contentWidth = 60;

        // Tạo các chuỗi nội dung trước để code gọn gàng hơn
        String maNV = nv.getMaNV();
        String hoTen = nv.getFullName();
        String gioiTinh = nv.getGioiTinh();
        String ngaySinh = nv.getNgaySinh() != null ? nv.getNgaySinhFormat() : "Không có";
        String diaChi = nv.getDiaChi() != null ? nv.getDiaChi() : "Không có";
        String email = nv.getEmail();
        // Định dạng tiền tệ thành chuỗi trước
        String luong = String.format("%,d VNĐ", nv.getLuong());
        String chucVu = nv.getChucVu();
        String trangThai = nv.getTrangThai() != null ? nv.getTrangThai() : "Không có";

        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                 THÔNG TIN NHÂN VIÊN                                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");

        System.out.println("┌──────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 📋 Mã nhân viên   │ " + String.format("%-" + contentWidth + "s", maNV) + " │");
        System.out.println("│ 👤 Họ và tên      │ " + String.format("%-" + contentWidth + "s", hoTen) + " │");
        System.out.println("│ ⚧ Giới tính       │ " + String.format("%-" + contentWidth + "s", gioiTinh) + " │");
        System.out.println("│ 🎂 Ngày sinh      │ " + String.format("%-" + contentWidth + "s", ngaySinh) + " │");
        System.out.println("│ 🏠 Địa chỉ        │ " + String.format("%-" + contentWidth + "s", diaChi) + " │");
        System.out.println("│ 📧 Email          │ " + String.format("%-" + contentWidth + "s", email) + " │");
        System.out.println("│ 💰 Lương          │ " + String.format("%-" + contentWidth + "s", luong) + " │");
        System.out.println("│ 💼 Chức vụ        │ " + String.format("%-" + contentWidth + "s", chucVu) + " │");
        System.out.println("│ 🚦 Trạng thái     │ " + String.format("%-" + contentWidth + "s", trangThai) + " │");
        System.out.println("└──────────────────────────────────────────────────────────────────────────────────┘");

        // --- Kết thúc đoạn code đã sửa ---
    }

    /**
     * In bảng tóm tắt danh sách nhân viên (STT, Mã, Họ tên, Giới tính, Chức vụ)
     */
    public static void inBangTomTatNhanVien(List<NhanVienDTO> results) {
        if (results == null || results.isEmpty()) {
            System.out.println("❌ Không có nhân viên để hiển thị!");
            return;
        }

        System.out.println("┌─────┬──────────┬────────────────────────────────────────┬───────────┬─────────┐");
        System.out.println("│ STT │ Mã NV    │ Họ và tên                              │ Giới tính │ Chức vụ │");
        System.out.println("├─────┼──────────┼────────────────────────────────────────┼───────────┼─────────┤");

        for (int i = 0; i < results.size(); i++) {
            NhanVienDTO nv = results.get(i);

            String stt = String.format(" %-3s ", i + 1);
            String maNV = String.format(" %-8s ", nv.getMaNV());
            String gioiTinh = String.format(" %-9s ", nv.getGioiTinh());
            String chucVu = String.format(" %-7s ", nv.getChucVu());

            String fullName = nv.getFullName();

            String hoTenFormatted = fullName.length() > 38
                    ? fullName.substring(0, 35) + "..."
                    : fullName;

            String hoTen = String.format(" %-38s ", hoTenFormatted);

            System.out.printf("│%s│%s│%s│%s│%s│%n",
                    stt, maNV, hoTen, gioiTinh, chucVu);
        }

        System.out.println("└─────┴──────────┴────────────────────────────────────────┴───────────┴─────────┘");
    }

    /**
     * Hiển thị bảng tóm tắt và cho phép người dùng chọn 1 nhân viên.
     * Trả về nhân viên đã chọn hoặc null nếu hủy/chọn sai.
     */
    public static NhanVienDTO hienThiBangTomTatVaChon(List<NhanVienDTO> results) {
        if (results == null || results.isEmpty()) {
            System.out.println("❌ Không có nhân viên để chọn!");
            return null;
        }

        inBangTomTatNhanVien(results);

        System.out.println("\n💡 Chọn nhân viên để xem chi tiết:");
        System.out.print("📝 Nhập số thứ tự (1-" + results.size() + ") hoặc 0 để thoát: ");

        try {
            int choice = Integer.parseInt(STDIN.nextLine().trim());
            if (choice == 0) {
                System.out.println("❌ Đã hủy.");
                return null;
            }
            if (choice >= 1 && choice <= results.size()) {
                return results.get(choice - 1);
            }
            System.out.println("❌ Lựa chọn không hợp lệ!");
        } catch (NumberFormatException e) {
            System.out.println("❌ Vui lòng nhập số hợp lệ!");
        }
        return null;
    }
}
