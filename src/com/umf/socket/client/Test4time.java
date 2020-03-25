package com.umf.socket.client;

import java.text.DateFormat;
import java.util.TimeZone;

/**
 * @Author:LiuYuKun
 * @Date:2019/11/20
 */
public class Test4time {
    public static void main(String[] args) {
        //���ϵͳ��ʱ�䣬��λΪ����,ת��Ϊ��
        long totalMilliSeconds = System.currentTimeMillis();
        System.out.println(totalMilliSeconds);
        DateFormat dateFormatterChina = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);//��ʽ�����
        TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");//��ȡʱ�� �����ϣ��ܹؼ���
        dateFormatterChina.setTimeZone(timeZoneChina);//����ϵͳʱ��
        /*ת��Ϊ��*/
        long totalSeconds = totalMilliSeconds/1000;
        System.out.println("������:"+totalSeconds);
        /*������ڵ�����*/
        long currentSecond = totalSeconds%60;
        System.out.println("��ǰ������:"+currentSecond);
        /*������ڵķ�*/
        long totalMinutes = totalSeconds/60;
        long currentMinute = totalMinutes%60;
        System.out.println("��ǰ�ķ���:"+currentMinute);
        //������ڵ�Сʱ
        long totalHour = totalMinutes / 60;
        long currentHour = totalHour % 24;
        System.out.println("��ǰ��Сʱ:"+currentHour);

        String osName = System.getProperty("os.name");
        String user = System.getProperty("user.name");
        System.out.println("��ǰ����ϵͳ�ǣ�" + osName);
        System.out.println("��ǰ�û��ǣ�" + user);

    }
}
