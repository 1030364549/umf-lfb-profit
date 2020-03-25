package com.umf.socket.client;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Package com.umf.socket.client
 * @Author:LiuYuKun
 * @Date:2020/3/25 9:51
 * @Description:
 */
public class TestNew1 {
    public static void main(String[] args) {
        long timeStamp = System.currentTimeMillis();
        System.out.println(timeStamp);
        long timeStamp1 = System.currentTimeMillis();
        System.out.println(timeStamp1);
        long dayStamp = timeStamp*timeStamp1;
        String trStr = dayStamp+"";
        System.out.println(trStr);
        String substring = trStr.substring(trStr.length() - 12);
        System.out.println(substring);

        System.out.println("**********************");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = "XYR"+sdf.format(new Date());
        System.out.println(date);
        System.out.println(date+substring);
    }
}
