package com.example.framwork.utils;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogUtils {
    //可以全局控制是否打印log日志
    private static boolean isPrintLog = true;
    private static int LOG_MAXLENGTH = 3000;
    /**
     * log_level 自己设置显示log的级别 设置为0时不打印信息
     */
    public static int LOG_LEVEL = 6;

    public static int ERROR = 1;
    public static int WARN = 2;
    public static int INFO = 3;
    public static int DEBUG = 4;
    public static int VERBOS = 5;

    //log的水平有 All Debug  Impo
    private static String Level = "";
    private static String methodName;

    // 2 只显示错误信息,自己抓的异常
    public static void e(String tag, String msg) {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];//代表上一层
        String className = traceElement.getClassName();//类名看自己需要
        methodName = traceElement.getMethodName();
        if (LOG_LEVEL > ERROR) {
            Level = "Impo";
            Log.e(changeTag(tag), changeMsg(msg));
        }
    }


/*
    // 3 显示警告以上级别的信息
    public static void w(String tag,String msg){
        if(LOG_LEVEL>WARN)
            Log.w(changeTag(tag), changeMsg(msg));
    }*/


    // 4 显示信息以上级别的信息，请求的数据和传出的数据
    public static void i(String tag, String msg) {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];//代表上一层
        String className = traceElement.getClassName();//类名看自己需要
        methodName = traceElement.getMethodName();

        if (LOG_LEVEL > INFO) {
            if (isPrintLog) {
                int strLength = msg.length();
                int start = 0;
                int end = LOG_MAXLENGTH;
                for (int i = 0; i < 100; i++) {
                    if (strLength > end) {
                        Log.i(tag + i, msg.substring(start, end));
                        start = end;
                        end = end + LOG_MAXLENGTH;
                    } else {
                        Log.i(tag + i, msg.substring(start, strLength));
                        break;
                    }
                }
            }
        }
    }
/*    // 5 显示debug以上级别的信息
    public static void d(String tag,String msg){
            Log.d(tag, msg);
        if(LOG_LEVEL>DEBUG)
    }*/

    // 6 全部都可以显示
    public static void v(String tag, String response) {
        if (response.length() > 4000) {
            for (int i = 0; i < response.length(); i += 4000) {
                if (i + 4000 < response.length())
                    Log.i("tag", i + "response第一段log===" + response.substring(i, i + 4000));
                else {
                    Log.i("tag", i + "response第二段log===" + response.substring(i, response.length()));
                }
            }
        } else {
            Log.i("tag", "response第三段log===" + response);
        }


//
//        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];//代表上一层
//        String className = traceElement.getClassName();//类名看自己需要
//        methodName = traceElement.getMethodName();
//        if (LOG_LEVEL > VERBOS) {
//            Level = "All";
//            Log.v(changeTag(tag), changeMsg(msg));
//        }
    }

    private static String changeTag(String tag) {
        return "[" + Level + "][" + getCurrentTime() + "][" + tag;
    }

    private static String changeMsg(String msg) {
        return methodName + "]::" + "\"" + msg + "\"";
    }

    /**
     * 返回当前时间 格式化
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 返回当前时间 格式化
     *
     * @return
     */
    public static String getCurrentTimeByymd() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 返回当前时间
     *
     * @return
     */
    public static Long getCurrentTimeNoFormatter() {
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        Long time = curDate.getTime();
        return time;
    }

    public static int daysOfTwo(String one, String two) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        //跨年的情况会出现问题哦
        //如果时间为：2016-03-18 11:59:59 和 2016-03-19 00:00:01的话差值为 1
        Date fDate = sdf.parse(one);
        Date oDate = sdf.parse(two);
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        int days = day2 - day1;
        return days;
    }
}
