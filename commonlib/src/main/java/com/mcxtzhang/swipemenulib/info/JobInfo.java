package com.mcxtzhang.swipemenulib.info;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.HomeJobTagInfo;
import com.mcxtzhang.swipemenulib.info.bean.JobInfoLikeJob;

/**
 * Created by Administrator on 2018/4/26.
 */

public class JobInfo {
    public int collect_id;                              //收藏ID
    public int is_apply;                                //是否投递简历 1是2否
    public int is_collect;                              //是否收藏 1是2否
    public String job_add_date;                         //职位添加时间
    public String job_address;                          //工作地点
    public String job_city_name;                        //职位所在地
    public String job_company_address;                  //公司地址
    public int job_company_id;                          //公司ID
    public String job_company_log;                      //公司LOGO
    public String job_company_name;                     //公司名称
    public int job_company_status;                      //公司认证状态 1已认证 2未认证
    public String job_company_status_name;              //公司认证状态名称
    public String job_company_tel;                      //联系电话
    public String job_content;                          //职位要求
    public String job_experience;                       //经验要求
    public int job_id;                                  //职位ID
    public String job_language;                         //语言要求
    public String job_link_person;                      //职位联系人
    public String job_link_phone;                       //职位联系电话
    public String job_money;                            //薪资
    public String job_people_num;                       //需求人数
    public String job_sex_name;                         //性别要求
    public String job_share_url;                        //分享地址
    public List<HomeJobTagInfo> job_tag_list;           //标签列表    ( 仅type为1、2时返回 )
    public String job_title;                            //职位名称
    public int job_type;                                //职位类型 1全职 2兼职
    public String job_work_date;                        //工作日期
    public String job_work_time;                        //工作时间
    public String job_sub_type_name;                    //职位子类名称 对应兼职内的兼职类型
    public List<JobInfoLikeJob> like_job_list;          //相似职位列表
}
