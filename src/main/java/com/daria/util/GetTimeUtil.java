package com.daria.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author Daria
 * @Description
 * @Date 2020/4/7 -- 0:51
 */
public class GetTimeUtil {
    //获取系统当前时间
    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    //还书日期，在借书时间的基础上加一个月
    public static String getReturnBookTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, + 1);
        return formatter.format(calendar.getTime());
    }
}
