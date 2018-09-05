package com.mcxtzhang.swipemenulib.utils;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    /**
     * 写Internal Card文件
     * @param context
     * @param filename
     * @param content
     * @throws IOException
     */
    public static void writeInternal(Context context, String filename, String content) throws IOException {
        //获取文件在内存卡中files目录下的路径
        java.io.File file = context.getFilesDir();
        filename = file.getAbsolutePath() +   java.io.File.separator + filename;

        //打开文件输出流
        FileOutputStream outputStream = new FileOutputStream(filename);

        //写数据到文件中
        outputStream.write(content.getBytes());
        outputStream.close();
    }

    /**
     * 读内存卡中文件方法
     * @param context
     * @param filename 文件名
     * @return
     * @throws IOException
     */
    public static String readInternal(Context context,String filename) throws IOException{
        StringBuilder sb = new StringBuilder("");

        //获取文件在内存卡中files目录下的路径
        java.io.File file = context.getFilesDir();
        filename = file.getAbsolutePath() +   java.io.File.separator + filename;

        //打开文件输入流
        FileInputStream inputStream = new FileInputStream(filename);

        byte[] buffer = new byte[1024];
        int len = inputStream.read(buffer);
        //读取文件内容
        while(len > 0){
            sb.append(new String(buffer,0,len));

            //继续将数据放到buffer中
            len = inputStream.read(buffer);
        }
        //关闭输入流
        inputStream.close();
        return sb.toString();
    }
}
