package com.mcxtzhang.swipemenulib.info.bean;

/**
 * Created by Administrator on 2018/4/23.
 */

public class ReCommendedInfo {
    public String recommend_add_date;       //添加日期
    public String recommend_cover;          //图片
    public int recommend_id;                //1
    public int recommend_plate;             //跳转板块 1兼职 2全职 3培训
    public String recommend_title;          //标题
    public int recommend_type;              //类型1：跳转APP内容；2：跳转H5 URL；
    public int recommend_type_id;           //对应板块ID    ( 仅type 为1时有效 )
    public String recommend_url;            //跳转的URL地址    ( 仅type 为2时有效 )
}
