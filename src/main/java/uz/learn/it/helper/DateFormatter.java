package uz.learn.it.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    public static String dateFormatter(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.format(date);
    }
}
