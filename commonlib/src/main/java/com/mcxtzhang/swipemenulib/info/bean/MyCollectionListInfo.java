package com.mcxtzhang.swipemenulib.info.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class MyCollectionListInfo {
    public int collect_id;              //收藏ID
    public int job_id;                 //职位ID    ( 仅type为1、2时返回 )
    public String job_title;           //职位名称    ( 仅type为1、2时返回 )
    public String job_company_name;    //发布公司名称    ( 仅type为1、2时返回 )
    public String job_company_id;      //发布公司ID    ( 仅type为1、2时返回 )
    public String job_city_name;       //职位地址    ( 仅type为1、2时返回 )
    public String job_add_date;           //职位时间    ( 仅type为1、2时返回 )
    public List<MyCollectionJobTagInfo> job_tag_list;     //标签列表    ( 仅type为1、2时返回 )
    public String job_company_logo;    //发布公司logo    ( 仅type为1、2时返回 )
    public int fun_id;                 //趣事ID    ( 仅type为3时返回 )
    public String fun_title;           //趣事标题    ( 仅type为3时返回 )
    public String fun_short_info;      //趣事简介    ( 仅type为3时返回 )
    public String fun_images;          //趣事封面    ( 仅type为3时返回 )
    public String fun_look_num;          //趣事封面    ( 仅type为3时返回 )
    public String fun_add_date;        //趣事发布日期    ( 仅type为3时返回 )
    public int raiders_id;             //攻略/试卷/秘籍ID    ( 仅type为4、5、6时返回 )
    public String raiders_title;       //攻略/试卷/秘籍标题    ( 仅type为4、5、6时返回 )
    public String raiders_short_info;  //攻略/试卷/秘籍简介    ( 仅type为4、5、6时返回 )
    public String raiders_cover;       //攻略/试卷/秘籍封面    ( 仅type为4、5、6时返回 )
    public String raiders_add_date;    //攻略/试卷/秘籍添加时间    ( 仅type为4、5、6时返回 )
    public int news_id;                //资讯ID    ( 仅type为7时返回 )
    public String news_title;          //资讯标题    ( 仅type为7时返回 )
    public String news_short_info;     //资讯简介    ( 仅type为7时返回 )
    public List<MyCollectionNewTagInfo> news_tag_list;     //标签列表    ( 仅type为7时返回 )
    public String news_cover;          //封面    ( 仅type为7时返回 )
    public String news_add_date;       //资讯发布日期    ( 仅type为7时返回 )
}
