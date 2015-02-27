package com.example.lyz.myv2ex.utils;


import com.example.lyz.myv2ex.AppConfig;

public class DateUtils {
    public static String convertDistTimeStampToString(long time) {
        long difference = Math.abs(System.currentTimeMillis() - time * 1000);
        if(difference > AppConfig.YEAR_IN_MILLIS) {
            long result = difference / AppConfig.YEAR_IN_MILLIS;
            return result <= 1 ? result + " year ago" : result + " years ago";
        } else if(difference > AppConfig.MONTH_IN_MILLIS) {
            long result = difference / AppConfig.MONTH_IN_MILLIS;
            return result <= 1 ? result + " month ago" : result + " months ago";
        } else if(difference > AppConfig.WEEK_IN_MILLIS) {
            long result = difference / AppConfig.WEEK_IN_MILLIS;
            return result <= 1 ? result + " week ago" : result + " weeks ago";
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
