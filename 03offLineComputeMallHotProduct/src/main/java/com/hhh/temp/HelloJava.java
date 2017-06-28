package com.hhh.temp;


import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by huanghh on 2017/5/19.
 */
public class HelloJava {
    public static void main(String[] args) throws Exception {
//        testDate();
        String activityStr = "2017-06-01 14:10:05";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date activityDate = sdf.parse(activityStr);
        Calendar activityC = Calendar.getInstance();
        activityC.setTime(activityDate);

        System.out.println(DateUtils.getFragmentInSeconds(activityDate,Calendar.MINUTE));
        System.out.println(DateUtils.getFragmentInSeconds(activityDate,Calendar.HOUR_OF_DAY));
        System.out.println(DateUtils.getFragmentInMinutes(activityDate,Calendar.HOUR_OF_DAY));
        System.out.println(DateUtils.getFragmentInMinutes(activityDate,Calendar.DAY_OF_MONTH));
        System.out.println(DateUtils.getFragmentInHours(activityDate,Calendar.DAY_OF_MONTH));
        System.out.println(DateUtils.getFragmentInDays(activityDate,Calendar.MONTH));


    }

    private static void testDate() throws ParseException, InterruptedException {
        System.out.println("HelloJava");
//        String phpTime =
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String activityDate = "2017-06-03 14:10:05";
        Date date = sdf.parse(activityDate);
        String javaLong = date.getTime()+"";
        System.out.println("javaTime:"+javaLong);
        String phpLong = javaLong.substring(0,10);
        System.out.println("phpTime:"+phpLong);


        long remainLong = 0;
        long ss = 1;
        long min = 60 * ss;
        long hour = 60 * min ;
        long day = 24 * hour ;
        for (int i = 0; i < 10; i++) {
            long nowJavaLong = System.currentTimeMillis();
//            System.out.println(Long.valueOf(phpLong+"000") - nowJavaLong);
            remainLong = Long.valueOf(phpLong+"000") - nowJavaLong;
            String remainLongStr = String.valueOf(remainLong);
            String remainLongStrss = remainLongStr.substring(0,remainLongStr.length()-3);
            long phpRemainLong = Long.valueOf(remainLongStrss);
//            System.out.println(remainLongStrss);
//            System.out.println(String.format("秒：%s",Long.valueOf(remainLongStrss)));
//            System.out.println(String.format("分钟：%s",Long.valueOf(remainLongStrss)/60));
//            System.out.println(String.format("小时：%s",Long.valueOf(remainLongStrss)/60/60));
//            System.out.println(String.format("天：%s",Long.valueOf(remainLongStrss)/60/60/24));
//            System.out.println(String.format("秒：%s",Long.valueOf(remainLongStrss)));
//            System.out.println(String.format("分钟：%s,剩余%s秒",Long.valueOf(remainLongStrss)/60));
//            System.out.println(String.format("小时：%s,剩余分钟：%s,剩余%s秒",Long.valueOf(remainLongStrss)/60/60));
            long remainSs = phpRemainLong % min;
            long remainMin = ((phpRemainLong - remainSs) % hour) / min;
            long remainHour = ((phpRemainLong - remainMin -remainSs) % day)/ hour;
            System.out.println(String.format("剩余%s小时：,剩余%s分钟：,剩余%s秒",
                    remainHour,
                    remainMin,
                    remainSs
                    ));
//            System.out.println(sdf.format(Long.valueOf(phpLong+"000") - nowJavaLong));
            Thread.sleep(1000);
        }
    }
}
