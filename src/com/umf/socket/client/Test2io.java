package com.umf.socket.client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Author:LiuYuKun
 * @Date:2019/11/19
 */
public class Test2io {
    public static void main(String[] args) {
        String filePath = "D:/a/b";
        String fileName = "/b.txt";
        StringBuffer stringBuffer = new StringBuffer();
        String fileWholePath = stringBuffer.append(filePath).append(fileName).toString();
        File file  = new File(filePath);
        /*mkdir��ֻ�����������ļ��У���ֻ�ܴ���һ��Ŀ¼������ϼ������ڣ��ͻᴴ��ʧ�ܡ�
        mkdirs��ֻ�����������ļ��У����ܴ����༶Ŀ¼ ������ϼ������ڣ��ͻ��Զ�������(�����ļ��ж��ô�)
        createNewFile:ֻ�����������ļ�����ֻ�����Ѵ��ڵ�Ŀ¼�´����ļ�������ᴴ��ʧ�ܡ�(FileOutputStream os=new FileOutputStream(file)Ҳ�ɴ����ļ��������ʹ��)*/
        if(!file.exists()){
            file.mkdirs();
        }
        File checkFile = new File(fileWholePath);
        FileWriter writer = null;
        try {
            // �������Ŀ���ļ��Ƿ���ڣ��������򴴽�
            if (!checkFile.exists()) {
                checkFile.createNewFile();// ����Ŀ���ļ�
            }
            // ������Ŀ���ļ���д������
            // FileWriter(File file, boolean append)��appendΪtrueʱΪ׷��ģʽ��false��ȱʧ��Ϊ����ģʽ
            writer = new FileWriter(checkFile,true);
            writer.append("yyy yyy");
            writer.append("\n");
            writer.append("WDNMD");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer)
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
