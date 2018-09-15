package com.suikajy.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface DateUtils {

    SimpleDateFormat HMS_FORMAT = new SimpleDateFormat("HH:mm:ss:SSS");

    static String currentHMS() {
        return HMS_FORMAT.format(new Date());
    }

}
