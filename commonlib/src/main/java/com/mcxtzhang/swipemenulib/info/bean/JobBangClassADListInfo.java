package com.mcxtzhang.swipemenulib.info.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/20.
 */

public class JobBangClassADListInfo implements Serializable{
    public int type;                            //类型1：跳转APP内容；2：跳转H5 URL；）
    public String cover_url;                            //广告图片地址
    public int plate;                            //跳转板块 1兼职 2全职 3培训 4转让 5互助 6App内容板块 7趣事 8每日分享    ( 仅type 为1时有效 )
    public int type_id;                            //对应板块ID    ( 仅type 为1时有效 )
    public String title;                            //广告标题
    public int is_need_login;                            //打开该广告是否需要强制登录 1是2否
    public String url;                            //跳转的URL地址    ( 仅type为2时有效 )
    public String share_url;                            //分享地址
}
