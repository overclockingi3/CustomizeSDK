package com.overc_i3.mars.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mars on 2017-10-21.
 */

public class DateUtils {

    public static final String DEFAULT_PATTERN = "yyyyMMdd";
    public static final String DEFAULT_FULL_PATTERN = "yyyyMMddHHmmssSSS";
    public static final String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    public static Calendar calendar = null;
    public static String FORMAT_SHORT = "yyyy-MM-dd";
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd";
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    private DateUtils() {
    }

//    public static boolean isDateExpire(String dateStr) {
//        return TextUtils.isEmpty(dateStr)?false:StringUtil.compare(format(), dateStr) > 0;
//    }

    public static String format() {
        return format(new Date(), "yyyyMMdd");
    }

    public static String format(String pattern) {
        if(TextUtils.isEmpty(pattern)) {
            pattern = "yyyyMMdd";
        }

        return format(new Date(), pattern);
    }

    public static Date toDate(String str, String pattern) {
        if(TextUtils.isEmpty(pattern)) {
            pattern = "yyyyMMdd";
        }

        try {
            return (new SimpleDateFormat(pattern)).parse(str);
        } catch (ParseException var3) {
            return null;
        }
    }

    public static Date currentDate() {
        String dateStr = format(new Date(), "yyyy-MM-dd");
        return toDate(dateStr, "yyyy-MM-dd");
    }

    public static String getDatePattern() {
        return FORMAT_LONG;
    }

    public static String getNow() {
        return format(new Date());
    }

    public static String getNow(String format) {
        return format(new Date(), format);
    }

    public static String format(Date date) {
        return format(date, getDatePattern());
    }

    public static String format(Date date, String pattern) {
        String returnValue = "";
        if(date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }

        return returnValue;
    }

    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }

    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);

        try {
            return df.parse(strDate);
        } catch (ParseException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, n);
        return cal.getTime();
    }

    public static String getpreHour(String format, int h) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date();
        date.setTime(date.getTime() + (long)(h * 60 * 60 * 1000));
        return sdf.format(date);
    }

    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    public static String getYear(Date date) {
        return format(date).substring(0, 4);
    }

    public static int getMonth(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDay(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getHour(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    public static int getSecond(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    public static long getMillis(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date));
        long t1 = c.getTime().getTime();
        return (int)(t / 1000L - t1 / 1000L) / 3600 / 24;
    }

    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        long t1 = c.getTime().getTime();
        return (int)(t / 1000L - t1 / 1000L) / 3600 / 24;
    }

//    public static Date getMonday(Date date) {
//        Date _date = DateUtils.truncate(date, 5);
//        Calendar c = Calendar.getInstance();
//        c.setTime(_date);
//        int iWeek = c.getData(Calendar.DAY_OF_WEEK);
//        if(iWeek == 1) {
//            iWeek = 8;
//        }
//
//        return parse((new SimpleDateFormat(FORMAT_SHORT)).format(DateUtils.addDays(_date, 2 - iWeek)), FORMAT_SHORT);
//    }

    public static Date getFirstDayOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.valueOf(getYear(date)).intValue());
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return parse((new SimpleDateFormat(FORMAT_SHORT)).format(c.getTime()), FORMAT_SHORT);
    }

    public static Date getFirstDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.valueOf(getYear(date)).intValue());
        c.set(Calendar.MONTH, Integer.valueOf(getMonth(date)).intValue() - 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return parse((new SimpleDateFormat(FORMAT_SHORT)).format(c.getTime()), FORMAT_SHORT);
    }

    public static int countTwoDays(Date startDate, Date endDate) {
        return (int)(endDate.getTime() / 1000L - startDate.getTime() / 1000L) / 3600 / 24;
    }

    public static Date lastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    public static Date fristDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
}
