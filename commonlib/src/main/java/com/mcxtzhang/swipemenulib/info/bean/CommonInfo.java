package com.mcxtzhang.swipemenulib.info.bean;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/3/8.
 */

public class CommonInfo implements Serializable {


    public String audit_ios_version;        //IOS版本审核
    public String android_audit_version;        //Android版本审核
    public String hotline;        //客服电话
    public String open_img;        //开机图
    public String about;        //关于我们
    public String registrationProtocol;        //用户协议地址 富文本
    public String help;        //新手指南
    public AdSwitch ad_switch;      //广告开关
}
