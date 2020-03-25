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
        /*��ȡ��Ӧ��ʽ�������պ����̶���ʱ��*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 17:44:00");
        /*formatת�����ַ�����ʽ*/
        String format = sdf.format(new Date());
        System.out.println(format);
        try {
            /*�ٽ��ַ�����ʽ��ʱ��ת����ϵͳʱ���ʽ*/
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
