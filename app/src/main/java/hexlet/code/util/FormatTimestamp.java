package hexlet.code.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class FormatTimestamp {
    public static String convert(Timestamp timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm");
        return simpleDateFormat.format(timestamp);
    }
}
