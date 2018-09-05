package com.mcxtzhang.swipemenulib.info.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/17.
 */

public class MySendInfo {
    public int job_id;                          //职位ID
    public int job_apply_id;                    //职位申请记录ID
    public String job_title;                    //职位名称
    public String job_company_name;             //发布公司名称
    public int job_company_id;                  //发布公司ID
    public List<MySendTagInfo> job_tag_list;    //标签列表    ( 仅type为2时返回 )
    public String job_company_logo;             //发布公司logo
    public String job_city_name;                //区域名称
    public int job_apply_status;                //申请状态 状态：1、投递成功，2、已查看 3、待审核 4、审核通过 5审核未通过
    public String job_apply_status_name;        //申请状态名称
    public String job_apply_date;               //申请日期
    public String job_money ;                   //薪资
}
