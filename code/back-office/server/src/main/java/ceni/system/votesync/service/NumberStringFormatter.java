package ceni.system.votesync.service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class NumberStringFormatter {
    public static String formatNumberWithSpaces(long number) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator(' '); // Set space as the grouping separator

        DecimalFormat formatter = new DecimalFormat("#,###", symbols);

        return formatter.format(number);
    }
}
