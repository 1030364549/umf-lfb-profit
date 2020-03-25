package com.umf.socket.client;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Package com.umf.socket.client
 * @Author:LiuYuKun
 * @Date:2020/3/25 10:50
 * @Description:
 */
public class TestNew2 {
    public static void main(String[] args) {

        long timeStamp = System.currentTimeMillis();
        long dayStamp = timeStamp%(1000*60*60*24);
        String trStr = "00000000"+dayStamp;
        String substring = trStr.substring(trStr.length() - 8);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = "P"+sdf.format(new Date());
        System.out.println(date+substring);

    }
}
