package com.example.lyz.myv2ex;


public class AppConfig {
    public static final String API_URL = "http://www.v2ex.com/api";
    public static final String API_LATEST = "/topics/latest.json";
    public static final String API_ALL_NODE = "/nodes/all.json";
    public static final String API_REPLIES = "/replies/show.json";
    public static final String API_TOPIC = "/topics/show.json";
    public static final String API_USER = "/members/show.json";
    public static final String HTTP = "http:";

    public static final String PARCEL_TOPIC_MODEL_KEY = "topicMode";

    public static final long MINUTE_IN_MILLIS = 60 * 1000;
    public static final long HOUR_IN_MILLIS = 60 * MINUTE_IN_MILLIS;
    public static final long DAY_IN_MILLIS = 24 * HOUR_IN_MILLIS;
    public static final long WEEK_IN_MILLIS = 7 * DAY_IN_MILLIS;
    public static final long MONTH_IN_MILLIS = 30 * DAY_IN_MILLIS;
    public static final long YEAR_IN_MILLIS = 365 * DAY_IN_MILLIS;

}
