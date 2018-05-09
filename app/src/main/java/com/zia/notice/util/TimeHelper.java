package com.zia.notice.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zia on 2018/5/8.
 */
public class TimeHelper {

    public static final int OK = 233;
    public static final int DANGROUS = 2333;
    public static final int WARNING = 23333;

    public static int getLevel(String date) {
        if (date == null) return OK;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String splits[] = date.split("/");
        if (splits.length != 3) return OK;
        int targetYear = Integer.parseInt(splits[0]);
        int targetMonth = Integer.parseInt(splits[1]);
        int targetDay = Integer.parseInt(splits[2]);
        if (targetYear <= year) {
            if (targetMonth <= month) {
                if (targetDay <= day)
                    return DANGROUS;
                else
                    return WARNING;
            } else
                return WARNING;
        } else {
            return OK;
        }
    }

    public static int compare(String o1, String o2) {
        String s1[] = o1.split("/");
        String s2[] = o2.split("/");
        int s1y = Integer.parseInt(s1[0]);
        int s1m = Integer.parseInt(s1[1]);
        int s1d = Integer.parseInt(s1[2]);
        int s2y = Integer.parseInt(s2[0]);
        int s2m = Integer.parseInt(s2[1]);
        int s2d = Integer.parseInt(s2[2]);
        if (s1y < s2y) {
            return -1;
        } else if (s1y > s2y) {
            return 1;
        } else {
            if (s1m < s2m) {
                return -1;
            } else if (s1m > s2m) {
                return 1;
            } else {
                if (s1d < s2d) {
                    return -1;
                } else if (s1d > s2d) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    public static String getCurrentDate(){
        return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }
}
