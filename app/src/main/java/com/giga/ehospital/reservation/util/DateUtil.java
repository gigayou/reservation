package com.giga.ehospital.reservation.util;

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class DateUtil {

    public static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyyMMddHHmmss";
    public static final String SIMPLE_DATE_FORMAT = "yyyyMMdd";
    public static final String SIMPLE_DATE_FORMAT_2 = "yyyy-MM-dd";

    public static String convertDate(String dateStr, String sourceFormat, String targetFormat) {
        if (StringUtils.isBlank(targetFormat)) {
            return null;
        }
        if (StringUtils.isBlank(sourceFormat)) {
            sourceFormat = STANDARD_DATE_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(sourceFormat);
        try {
            Date date = sdf.parse(dateStr);
            if (date == null) {
                return null;
            }
            SimpleDateFormat targetSdf = new SimpleDateFormat(targetFormat);

            return targetSdf.format(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public static Date formatDate(String dateStr, String format) {
        if (StringUtils.isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        try {

            SimpleDateFormat targetSdf = new SimpleDateFormat(format);
            return targetSdf.parse(dateStr);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public static String getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    /**
     * 格式化 LocalDateTime 为String类型 "yyyyMMdd"，未传入时，返回调用时间
     * @param localDateTime
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String formatLocalDateTimeToSimpleString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(DateUtil.SIMPLE_DATE_FORMAT));
    }

    /**
     * 格式化 LocalDateTime 为String类型 "yyyyMMdd"，未传入时，返回调用时间
     * @param localDateTime
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String formatLocalDateTimeToSimpleString2(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(DateUtil.SIMPLE_DATE_FORMAT_2));
    }



    public static void main(String[] args) {
        String str = convertDate("2018-07-21", "yyyy-MM-dd", "yyyyMMdd");
        System.out.println(str);

        System.out.println("201701".compareTo("201702"));
        System.out.println("20170101".compareTo("201701"));
        System.out.println("201702".compareTo("20170101"));
    }


}
