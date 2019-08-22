package util;

import java.nio.charset.StandardCharsets;

public class Utils {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String BER_FORMAT = "ber";

    public static String octalStrToString(byte[] buffer) {
        return new String(buffer, StandardCharsets.UTF_8);
    }
}
