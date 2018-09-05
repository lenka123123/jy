package com.example.framwork.noHttp.Bean;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

public class BaseResponseGetBean {


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 服务端业务数据
     */
    @JSONField(name = "result")
    private String result;
    /**
     * 服务端业务错误码
     */
    @JSONField(name = "status")
    private String status;

    @JSONField(name = "msg")
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseResponseBean{" +
                "result='" + result + '\'' +
                ", status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    /**
     * 业务是否成功
     */
    public boolean isSuccess() {
        if (getStatus() != null)
            return getStatus().equals("0");
        else
            return true;
    }



    /**
     * 解析JsonObject，你的{@link E}必须提供默认无参构造
     *
     * @param clazz 要解析的实体类的class
     * @param <E>   实体类泛型
     * @return 实体类
     */
    public <E> E parseObject(Class<E> clazz) {
        E e = null;
        try {
            e = JSON.parseObject(getResult(), clazz);
        } catch (Exception e1) {
            e1.printStackTrace();
            // 服务端数据格式错误时，返回data的空构造
            try {
                e = clazz.newInstance();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return e;
    }

    /**
     * 解析JsonArray，你的{@link E}必须提供默认无参构造
     *
     * @param clazz 要解析的实体类的class
     * @param <E>   实体类泛型
     * @return 实体类的List集合
     */
    public <E> List<E> parseList(Class<E> clazz) {
        List<E> e = new ArrayList<>();
        try {
            e = JSON.parseArray(getResult(), clazz);
        } catch (Exception e1) {
        }
        return e;
    }

    /**
     * 解析JsonObject，你的{@link E}必须提供默认无参构造
     *
     * @param clazz 要解析的实体类的class
     * @param <E>   实体类泛型
     * @return 实体类
     */
    public static <E> E parseObj(String data, Class<E> clazz) {
        E e = null;
        try {
            e = JSON.parseObject(data, clazz);
        } catch (Exception e1) {
            e1.printStackTrace();
            // 服务端数据格式错误时，返回data的空构造
            try {
                e = clazz.newInstance();
            } catch (Exception e2) {
            }
        }
        return e;
    }

    /**
     * 解析JsonArray，封装为捕获异常
     *
     * @param clazz 要解析的实体类的class
     * @param <E>   实体类泛型
     * @return 实体类的List集合
     */
    public static <E> List<E> parseArray(String data, Class<E> clazz) {
        List<E> e = new ArrayList<>();
        try {
            e = JSON.parseArray(data, clazz);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return e;
    }
}
