package util;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLogger {
    private static final String LOG_PATH = "data/auditnhanvien.txt";

    public static void logEmployeeStatusChange(String actorUsername,
            String targetMaNV,
            String oldStatus,
            String newStatus,
            String reason) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // Output format example:
        // [2025-10-13 21:29:19] actor=sonadmin , target=NV777 , old= inactive , new
        // =active ,lí do <reason>
        String line = String.format("[%s] actor=%s , target=%s , old= %s , new =%s ,lí do %s%n",
                timestamp, safe(actorUsername), safe(targetMaNV), safe(oldStatus), safe(newStatus), safe(reason));
        try (FileWriter fw = new FileWriter(LOG_PATH, true)) {
            fw.write(line);
        } catch (IOException e) {
            System.err.println("Không thể ghi audit log: " + e.getMessage());
        }
    }

    private static String safe(String s) {
        return s == null ? "" : s.replaceAll("\n|\r", " ").trim();
    }

    // Thông báo cho quản trị khi cố gắng xóa nhân viên đãinactive
    public static void notifyAdminEmployeeAlreadyDeleted(String actorUsername,
            String targetMaNV,
            String currentStatus,
            String reason) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String line = String.format(
                "[%s] NOTICE: actor=%s cố gắng xóa target=%s nhưng trạng thái hiện tại=%s , lí do %s%n",
                timestamp, safe(actorUsername), safe(targetMaNV), safe(currentStatus), safe(reason));
        try (FileWriter fw = new FileWriter(LOG_PATH, true)) {
            fw.write(line);
        } catch (IOException e) {
            System.err.println("Không thể ghi audit log: " + e.getMessage());
        }
    }
}
