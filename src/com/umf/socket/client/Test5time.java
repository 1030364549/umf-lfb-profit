package com.umf.socket.client;

import com.umf.config.ParamsConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Package com.umf.socket.client
 * @Author:LiuYuKun
 * @Date:2020/3/24 17:31
 * @Description:
 */
public class Test5time {
    public static void main(String[] args) {
        /*获取对应格式的年月日和所固定的时间*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 17:44:00");
        /*format转换成字符串格式*/
        String format = sdf.format(new Date());
        System.out.println(format);
        try {
            /*再将字符串格式的时间转换成系统时间格式*/
            Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
            System.out.println(startTime);
            System.out.println(System.currentTimeMillis());
            System.out.println(startTime.getTime());
            System.out.println(System.currentTimeMillis() > startTime.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
