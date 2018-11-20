package com.mcxtzhang.swipemenulib.info.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HomePartTimeInfo {
    public int job_id;                  //职位ID
    public int job_type;                  //职位类型 1全职 2兼职
    public String job_title;                  //职位名称
    public String job_money;                  //薪资
    public String job_money_name;                  //结算类型
    public String job_company_name;                  //公司名称
    public int job_company_id;                  //公司ID
    public List<HomeJobTagInfo> job_tag_list;                  //标签
    public String job_company_logo;             //公司LOGO
    public String job_city_name;             //地点名称
    public String job_add_date;             //职位添加时间


    public String job_look_num;
    public String job_apply_num;
    public String job_industry_id;
    public String job_industry_name;
    public String job_industry_icon;
    public String job_time_shaft; //时间轴    ( 07.13迭代-兼职 )
    public String job_distance;   //距离
    public int job_is_top;   //(  0:不置顶 1:置顶 )
    public int job_is_api;   // ( 0:后台添加 1:手机端添加 )
    public int is_platform ;   // 发布平台 1:非平台发布; 2:平台发布
    public String job_time_group ;   // 发布平台 1:非平台发布; 2:平台发布
    public String jmessage_phone ;


}
