package com.mcxtzhang.swipemenulib.info.bean;

import java.util.List;

public class CompanyJobListInfo {
    public String job_add_date;                     //发布日期
    public String job_city_name;                    //所在城市
    public String job_company_id;                   //公司ID
    public String job_company_logo;                 //公司LOGO
    public String job_company_name;                 //公司名称
    public int job_id;                              //职位ID
    public String job_money;                        //薪资
    public String job_title;                        //职位名称
    public int job_type;                         //职位类型 1全职 2兼职
    public List<HomeJobTagInfo> job_tag_list;       //标签
}
