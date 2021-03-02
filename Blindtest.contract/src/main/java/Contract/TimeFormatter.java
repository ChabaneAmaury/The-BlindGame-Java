package Contract;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatter {

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS");
    private static final Date date = new Date();

    public static String getTimestamp()
    {
        return String.format("[%s] ", timeFormat.format(date.getTime()));
    }

}
