package dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class xinchaoDAO {
    public static String[] getGreeting() {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        String greeting;
        String icon;
        
        // Determine greeting based on time
        if (hour >= 0 && hour < 11) {
            greeting = "Chào buổi sáng";
            icon = "🌅";
        } else if (hour >= 11 && hour < 13) {
            greeting = "Chào buổi trưa";
            icon = "☀️";
        } else if (hour >= 13 && hour < 18) {
            greeting = "Chào buổi chiều";
            icon = "🌤️";
        } else {
            greeting = "Chào buổi tối";
            icon = "🌙";
        }

        // Format current date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        String dateTime = now.format(formatter);

        return new String[]{icon, greeting, dateTime};
    }
}