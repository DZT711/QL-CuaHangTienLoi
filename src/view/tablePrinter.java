package view;

import java.util.List;
public class tablePrinter {
    /**
     * In bảng với header + các dòng, tự động tính độ rộng mỗi cột.
     * @param headers danh sách tiêu đề cột
     * @param rows danh sách dòng, mỗi dòng là danh sách chuỗi chứa giá trị cột tương ứng
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
        int totalWidth = 1;  // bắt đầu với ký tự '|'
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
}

