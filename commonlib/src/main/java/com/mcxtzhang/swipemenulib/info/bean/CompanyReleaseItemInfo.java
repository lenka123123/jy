package com.mcxtzhang.swipemenulib.info.bean;

import java.util.List;

public class CompanyReleaseItemInfo {
    public String job_add_date;                 //发布日期
    public int job_apply_num;                 //申请数量
    public String job_city_name;                 //城市地址
    public int job_company_id;                 //公司ID
    public String job_company_logo;                 //公司logo
    public String job_company_name;                 //公司名字
    public int job_id;                 //职位id
    public int job_look_num;                 //浏览量
    public String job_money;                 //薪资
    public List<CompanyReleaseItemTagInfo> job_tag_list;        //标签
    public String job_title;                //职位标题
    public int job_type;                    //职位类型 1全职 2兼职
    public int status;                    //职位状态 1通过 2待审核 3未通过 4已下架
    public String status_name;          //职位状态名称
}
