package util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class FormatUtil {
    private static final DecimalFormatSymbols VN_SYMBOLS = new DecimalFormatSymbols() {{
        setDecimalSeparator(',');
    }};

    private static final DecimalFormat dfLong = new DecimalFormat("#,###", VN_SYMBOLS);
    private static final DecimalFormat dfDouble = new DecimalFormat("#,###.##", VN_SYMBOLS);

    public static String formatVND(long number) {
        return dfLong.format(number) + " VND";
    }

    public static String formatVND(double number) {
        return dfDouble.format(number) + " VND";
    }

    public static String divideAndFormat(long number, int divisor) {
        double result = (double) number / divisor;
        return formatVND(result);
    }
}
