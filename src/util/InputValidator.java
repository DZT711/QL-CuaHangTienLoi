package util;

public class InputValidator {
    public static boolean isValidString(String input) {
        // tình huống nhập 0 để thoát
        if (input == null || input.isEmpty()) return false;

        if (input.length() < 2 || input.length() > 200) return false;

        if (input.matches("^[0-9\\s\\-\\.\\,]+$")) return false;
        
        if (input.matches(".*[!@#$%^&*()_+={}\\[\\]|\\\\:;\"'<>?/~`].*")) return false;
        
        if (input.matches(".*[^a-zA-Z0-9À-ỹĐđ][^a-zA-Z0-9À-ỹĐđ].*")) return false;
    
        return true;
    }
}
