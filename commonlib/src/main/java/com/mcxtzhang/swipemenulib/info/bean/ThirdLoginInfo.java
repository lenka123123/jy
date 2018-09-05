package com.mcxtzhang.swipemenulib.info.bean;

import java.io.Serializable;

/**
 * Created by gaoy on 2016/12/27.
 */
public class ThirdLoginInfo implements Serializable{
    public String is_bind;      //是否绑定(1是 2否)

    public String token;        //用户标识

    public String user_id;      //用户ID

    public int type;            //用户类型    ( 1个人2企业 )

    public int is_auth;         //是否已认证    ( (1是，2否 )

    public String mobile;       //用户手机号

    public String nickname;      //昵称

    public String avatar;     //头像地址

    public int gender;          //性别（1男2 女 ）    ( 个人用户字段 )

    public String balance;      //余额(元)

    public String birthday;     //生日    ( 个人用户字段 )

    public String contact;      //企业联系人    ( 企业用户字段 )

    public String hotline;
    public String vcode;
    public String third_token;
    public int third_type;      //1微信  2微博
    public String uuid;
}
