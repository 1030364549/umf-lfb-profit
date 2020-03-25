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
        /*mkdir：只能用来创建文件夹，且只能创建一级目录，如果上级不存在，就会创建失败。
        mkdirs：只能用来创建文件夹，且能创建多级目录 ，如果上级不存在，就会自动创建。(创建文件夹多用此)
        createNewFile:只能用来创建文件，且只能在已存在的目录下创建文件，否则会创建失败。(FileOutputStream os=new FileOutputStream(file)也可创建文件，看情况使用)*/
        if(!file.exists()){
            file.mkdirs();
        }
        File checkFile = new File(fileWholePath);
        FileWriter writer = null;
        try {
            // 二、检查目标文件是否存在，不存在则创建
            if (!checkFile.exists()) {
                checkFile.createNewFile();// 创建目标文件
            }
            // 三、向目标文件中写入内容
            // FileWriter(File file, boolean append)，append为true时为追加模式，false或缺失则为覆盖模式
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
