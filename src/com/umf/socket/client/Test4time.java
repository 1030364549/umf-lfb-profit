package com.umf.socket.client;

import java.text.DateFormat;
import java.util.TimeZone;

/**
 * @Author:LiuYuKun
 * @Date:2019/11/20
 */
public class Test4time {
    public static void main(String[] args) {
        //获得系统的时间，单位为毫秒,转换为妙
        long totalMilliSeconds = System.currentTimeMillis();
        System.out.println(totalMilliSeconds);
        DateFormat dateFormatterChina = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);//格式化输出
        TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");//获取时区 这句加上，很关键。
        dateFormatterChina.setTimeZone(timeZoneChina);//设置系统时区
        /*转换为秒*/
        long totalSeconds = totalMilliSeconds/1000;
        System.out.println("总秒数:"+totalSeconds);
        /*求出现在的秒数*/
        long currentSecond = totalSeconds%60;
        System.out.println("当前的秒数:"+currentSecond);
        /*求出现在的分*/
        long totalMinutes = totalSeconds/60;
        long currentMinute = totalMinutes%60;
        System.out.println("当前的分钟:"+currentMinute);
        //求出现在的小时
        long totalHour = totalMinutes / 60;
        long currentHour = totalHour % 24;
        System.out.println("当前的小时:"+currentHour);

        String osName = System.getProperty("os.name");
        String user = System.getProperty("user.name");
        System.out.println("当前操作系统是：" + osName);
        System.out.println("当前用户是：" + user);

    }
}
