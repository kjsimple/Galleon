package com.gydoc;

import java.text.DateFormatSymbols;
import java.util.Locale;

/**
 *
 */
public class Test {

    public static void main(String[] args) {
        DateFormatSymbols dfs = new DateFormatSymbols(Locale.CHINA);
        String[] weeks = dfs.getShortWeekdays();
        for (String s : weeks) {
            System.out.println("s = " + s);
        }
    }

}
