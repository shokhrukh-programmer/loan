package uz.learn.it.helper;

import uz.learn.it.constants.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    public static String dateFormatter(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_PATTERN);

        return formatter.format(date);
    }
}
