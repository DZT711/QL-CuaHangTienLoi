package util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class FormatUtil {
    private static final DecimalFormatSymbols VN_SYMBOLS = new DecimalFormatSymbols() {{
        setDecimalSeparator(',');
    }};

    private static final DecimalFormat df = new DecimalFormat("#,###", VN_SYMBOLS);

    public static String formatVND(long number) {
        return df.format(number) + " VND";
    }

    public static String divideAndFormat(long number, int divisor) {
        long result = number / divisor;
        return formatVND(result);
    }

}
