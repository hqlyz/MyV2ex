package com.example.lyz.myv2ex.utils;


import com.example.lyz.myv2ex.AppConfig;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String convertDistTimeStampToString(long time) {
        long difference = Math.abs(System.currentTimeMillis() - time * 1000);
        if(difference > AppConfig.DAY_IN_MILLIS * 3) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time * 1000));
        } else if(difference > AppConfig.DAY_IN_MILLIS) {
            long result = difference / AppConfig.DAY_IN_MILLIS;
            return result <= 1 ? result + " day ago" : result + " days ago";
        } else if(difference > AppConfig.HOUR_IN_MILLIS) {
            long result = difference / AppConfig.HOUR_IN_MILLIS;
            return result <= 1 ? result + " hour ago" : result + " hours ago";
        } else {
            long result = difference / AppConfig.MINUTE_IN_MILLIS;
            return result <= 1 ? result + " minute ago" : result + " minutes ago";
        }
    }
}
