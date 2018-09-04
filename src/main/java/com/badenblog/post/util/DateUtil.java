package com.badenblog.post.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class DateUtil {
    public static final Locale LOCALE_MX = new Locale("es", "MX");

    public static String getDateStringFromUuid(UUID uuid) {

        return new SimpleDateFormat("dd 'de' MMMM 'del' yyyy",LOCALE_MX).format(
                new Date((uuid.timestamp() - 0x01b21dd213814000L) / 10000));


    }
}
