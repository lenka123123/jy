package com.mcxtzhang.swipemenulib.info.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/22.
 */

public class HomeJobInfo {
    public int job_id;              //职位ID
    public int job_type;            //职位类型 1全职 2兼职 3、社团兼职
    public String job_title;        //职位名称
    public String job_money;        //薪资
    public String job_company_name; //公司名称
    public int job_company_id;      //公司ID
    public List<HomeJobTagInfo> job_tag_list;       //标签
    public String job_company_logo;     //公司LOGO
    public String job_city_name;        //地点名称
    public String job_add_date;     //职位添加时间


    public String job_apply_num;
    public String job_look_num;
    public String job_industry_id;
    public String job_industry_name;
    public String job_industry_icon;
    public String job_time_shaft;
    public String job_money_name;
    public String job_distance;

    public int job_is_platform;
    public String job_time_group;
    public String jmessage_phone;


}
