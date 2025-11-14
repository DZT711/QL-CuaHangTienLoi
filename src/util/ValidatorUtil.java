package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class ValidatorUtil {
    public static boolean isValidString(String input) {
        // tình huống nhập 0 để thoát
        if (input == null || input.isEmpty()) return false;

        if (input.length() < 3 || input.length() > 200) return false;

        if (input.matches(".*\\s{2,}.*")) return false;
        
        if (input.matches("^[0-9\\s\\-\\.\\,]+$")) return false;
        
        if (input.matches(".*[!@#$%^&*()_+={}\\[\\]|\\\\:;\"'<>?/~`].*")) return false;
        
        if (input.matches(".*[^a-zA-Z0-9À-ỹĐđ][^a-zA-Z0-9À-ỹĐđ].*")) return false;

        if (input.matches(".*\\s{2,}.*")) return false;
        
        if (!input.matches(".*[a-zA-ZÀ-ỹĐđ]+.*")) return false; 
        
        return true;
    }

    public static boolean isValidateDate(String input) {
        if (input == null || input.isEmpty()){
            System.out.println("❌ Ngày không được để trống!");
            return false;
        }
        
        if (input.length() != 10) {
            System.out.println("❌ Định dạng phải là dd/mm/yyyy (10 ký tự)!");
            return false;
        }

        if (input.matches("\\\\d{2}/\\\\d{2}/\\\\d{4}")) {
            System.out.println("❌ Định dạng không hợp lệ!");
            return false;
        }

        String[] parts = input.split("/");
        int day, month, year;

        try {
            day = Integer.parseInt(parts[0]);
            month = Integer.parseInt(parts[1]);
            year = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            System.out.println("❌ Ngày/tháng/năm phải là số hợp lệ!");
            return false;
        }

        int currentYear = LocalDate.now().getYear();
        if (year < 2000 || year > currentYear) {
            System.out.println("❌ Năm phải từ 2000 đến " + currentYear + "!");
            return false;
        }

        if (month < 1 || month > 12) {
            System.out.println("❌ Tháng phải từ 1 đến 12!");
            return false;
        }

        int maxDaysInMonth = getMaxDaysInMonth(month, year);
        if (day < 1 || day > maxDaysInMonth) {
            System.out.println("❌ Ngày không hợp lệ! Tháng " + month + "/" + year + " chỉ có " + maxDaysInMonth + " ngày!");
            return false;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate parsedDate = LocalDate.parse(input, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("❌ Định dạng ngày không hợp lệ!");
            return false;
        }

        return true;
    }

    private static int getMaxDaysInMonth(int month, int year) {
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            case 4: case 6: case 9: case 11:
                return 30;
            case 2:
                return isLeapYear(year) ? 29 : 28;
            default:
                return 0;
        }
    }

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public static boolean isValidDateRange(LocalDate fromDate, LocalDate toDate) {
        if (fromDate.isEqual(toDate)) {
            System.out.println("❌ Ngày kết thúc không được trùng với ngày bắt đầu.");
            return false;
        }
        
        if (fromDate.isAfter(toDate)) {
            System.out.println("❌ Ngày kết thúc phải sau ngày bắt đầu.");
            return false;
        }
        
        long daysBetween = ChronoUnit.DAYS.between(fromDate, toDate);
        if (daysBetween < 10) {
            System.out.println("❌ Ngày kết thúc phải cách ngày bắt đầu ít nhất 10 ngày.");
            return false;
        }
        
        return true;
    }

    public static boolean isValidAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            System.out.println("❌ Địa chỉ không được để trống!");
            return false;
        }
        
        if (address.length() < 5) {
            System.out.println("❌ Địa chỉ quá ngắn! Phải có ít nhất 5 ký tự.");
            return false;
        }
        
        if (address.length() > 255) {
            System.out.println("❌ Địa chỉ quá dài! Tối đa 255 ký tự.");
            return false;
        }
        
        String validPattern = "^[a-zA-Z0-9À-ỹĐđ\\s\\/\\,\\.\\-]+$";
        if (!address.matches(validPattern)) {
            System.out.println("❌ Địa chỉ chứa ký tự không hợp lệ!");
            return false;
        }
        
        if (!address.matches(".*[a-zA-ZÀ-ỹĐđ]+.*")) {
            System.out.println("❌ Địa chỉ phải chứa ít nhất 1 chữ cái!");
            return false;
        }
        
        if (address.matches(".*\\s{2,}.*")) {
            System.out.println("❌ Địa chỉ không được chứa nhiều khoảng trắng liên tiếp!");
            return false;
        }
        
        if (address.matches(".*[\\/\\,\\.\\-]{2,}.*")) {
            System.out.println("❌ Không được có ký tự đặc biệt lặp liên tiếp!");
            return false;
        }
        
        if (address.matches("^[\\/\\,\\.\\-\\s].*") || address.matches(".*[\\/\\,\\.\\-\\s]$")) {
            System.out.println("❌ Địa chỉ không được bắt đầu/kết thúc bằng ký tự đặc biệt!");
            return false;
        }
        
        return true;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            System.out.println("❌ Số điện thoại không được để trống!");
            return false;
        }
        
        if (!phoneNumber.matches("^[0-9\\s]+$")) {
            System.out.println("❌ Số điện thoại chỉ được chứa chữ số và khoảng cách!");
            return false;
        }
        
        if (phoneNumber.matches(".*\\s{2,}.*")) {
            System.out.println("❌ Số điện thoại không hợp lệ!");
            return false;
        }
        
        String digits = phoneNumber.replaceAll("\\s", "");
    
        if (digits.length() != 10) {
            System.out.println("❌ Số điện thoại phải có đúng 10 chữ số!");
            return false;
        }
        
        if (digits.matches(".*(\\d)\\1\\1.*")) {
            System.out.println("❌ Số điện thoại không hợp lệ");
            return false;
        }
        
        return true;
    }
}
