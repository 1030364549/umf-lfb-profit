package com.umf.socket.client;

import java.io.*;

/**
 * @Package com.umf.socket.client
 * @Author:LiuYuKun
 * @Date:2020/3/24 11:24
 * @Description:
 */
public class TestFile {
    public static void main(String[] args) {
        FileOutputStream fs = null;
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;

        try {
            /*����ƴ����ɵ��ļ���ַ*/
            File file = new File("D:/lol1.txt");
            /*�ڶ����ͻᴴ���ļ�*/
			/*�����ļ���append[׷��]����Ϊtrue�������ڲ��������ļ�����׷���ı��������ھʹ�����Ĭ��Ϊfalse���ͻḲ��֮ǰ���ı���*/
            fs = new FileOutputStream(file, true);
            /*ow = new OutputStreamWriter(fs, "UTF-8");
            bw = new BufferedWriter(ow);

            bw.write("hhhh");
            bw.flush();
            bw.newLine();
            bw.newLine();*/
        } catch (IOException e) {
            try {
                closeIO(fs,ow,bw);
            } catch (Exception e1) {
                System.out.println(e1);
            }
        }

    }

    private static void closeIO(FileOutputStream fs, OutputStreamWriter ow,BufferedWriter bw) throws Exception {
        try {
            if (bw != null) {
                bw.close();
            }
            if (ow != null) {
                ow.close();
            }
            if (fs != null) {
                fs.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
