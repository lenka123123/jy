package com.example.framwork.utils;

import java.io.Serializable;

/**
 * Created by mc on 16/12/16.
 */

public class UserInfo implements Serializable {
    public String user_id;          //用户编号
    public String token;            //登录唯一凭证
    public String mobile;           //手机号
    public String nickname;         //昵称
    public String avatar;           //头像
    public int gender;              //性别（1男2 女 ） ( 个人用户字段 )
    public String code;             //邀请码
    public int type;                //用户类型（1个人/2企业/3培训机构）
    public String type_name;        //类型名称
    public int integral;            //积分
    public int growth;              //总成长值
    public int growth_sign;         //签到成长值
    public int growth_job;          //兼职成长值
    public int growth_train;        //培训成长值
    public int growth_invite;       //邀请成长值
    public int growth_help;         //互助成长值
    public int vip_level;           //VIP等级
    public String slogan;           //个性签名
    public int real_status;         //实名认证状态    ( 1未通过 2待审核 3已审核 )
    public String real_status_name; //实名认证状态名称
    public int status;              //帐号状态    ( 1正常 2待审核 3审核未通过 4已停用 )
    public String status_name;      //帐号状态名称
    public int intact;              //简历完成度
    public int has_message;         //是否有未读消息    ( 1有2无 )
    public int is_bind;             //是否已绑定    ( 1是 2否 )
    public int is_seed;             //是否种子用户    ( 1是2否 )
    public String email;            //邮箱地址
    public String no_read_num;            //未读消息数


    public int company_apply_status;            //企业入驻状态  1已通过 2未通过


    public String birthday;    //生日    ( 个人用户字段 )
    public String contact;     //企业联系人    ( 企业用户字段 )

    public String hotline;     //热线电话

    public String area;               //省市区
    public String area_id;
    public int auth_status;  //认证状态（1：未认证；2：审核中；3：已认证；4：认证不通过；）
    public String beans_num;
    public String city_id;
    public String password;
    public String province_id;
    public String uid;

    public MyNum my_num;

    public class MyNum {
        public String full_job_num;
        public String part_job_num;
        public String train_num;
        public String help_num;
        public String sign_num;
    }


}
