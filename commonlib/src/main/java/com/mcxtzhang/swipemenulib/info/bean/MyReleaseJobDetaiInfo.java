package com.mcxtzhang.swipemenulib.info.bean;

import java.util.List;

public class MyReleaseJobDetaiInfo {
    public int collect_id;                                  //收藏ID
    public int is_apply;                                    //是否投递简历 1是2否
    public int is_collect;                                  //是否收藏 1是2否
    public String job_add_date;                             //职位添加时间
    public String job_address;                              //工作地点
    public String job_address_only;                         //详细地址（不包含省市区）    ( 2018.06.21迭代 )
    public int job_city_id;                                 //市区ID    ( 2018.06.21迭代 )
    public String job_city_name;                            //职位所在地
    public String job_company_address;                      //公司地址
    public int job_company_id;                              //公司ID
    public String job_company_logo;                         //公司LOGO
    public String job_company_name;                         //公司名称
    public int job_company_status;                          //公司认证状态 1已认证 2未认证
    public String job_company_status_name;                  //公司认证状态名称
    public String job_company_tel;                          //联系电话
    public String job_content;                              //职位要求
    public String job_date_end_ex;                          //结束日期    ( 2018.06.21迭代 )
    public String job_date_start_ex;                        //开始日期    ( 2018.06.21迭代 )
    public int job_dist_id;                                 //县区ID    ( 2018.06.21迭代 )
    public int job_education_ex;                            //学历要求ID 逗号分隔    ( 2018.06.21迭代 )
    public String job_experience;                           //经验要求
    public int job_experience_ex;                           //经验要求ID 逗号分隔    ( 2018.06.21迭代 )
    public int job_id;                                      //职位ID
    public int job_industry_id_ex;                          //岗位类型ID 逗号分隔    ( 2018.06.21迭代 )
    public String job_language;                             //语言要求
    public String job_link_person;                          //职位联系人
    public String job_link_phone;                           //职位联系电话
    public String job_money;                                //薪资
    public String job_money_lower_ex;                       //工资下限    ( 2018.06.21迭代 )
    public String job_money_upper_ex;                       //工资上限    ( 2018.06.21迭代 )
    public int job_money_type_ex;                           //薪水类型ID    ( 2018.06.21迭代 )
    public String job_num_lower_ex;                         //人数下限    ( 2018.06.21迭代 )
    public String job_num_upper_ex;                         //人数上限    ( 2018.06.21迭代 )
    public String job_people_num;                           //需求人数
    public int job_prov_id;                                 //省份ID    ( 2018.06.21迭代 )
    public int job_sex_ex;                                  //性别ID    ( 2018.06.21迭代 )
    public String job_sex_name;                             //性别要求
    public String job_share_url;                            //分享地址
    public String job_sub_type_name;                        //职位子类型名称（对应兼职内的 职位类型 字段）
    public String job_tag_ids_ex;                           //标签ID 逗号分隔    ( 2018.06.21迭代 )
    public List<MySendTagInfo> job_tag_list;                //标签列表    ( 仅type为1、2时返回 )
    public String job_time_end_ex;                          //结束时间    ( 2018.06.21迭代 )
    public String job_time_start_ex;                        //开始时间    ( 2018.06.21迭代 )
    public String job_title;                                //职位名称
    public int job_type;                                    //职位类型 1全职 2兼职
    public String job_type_ids_ex;                          //所属行业ID 逗号分隔    ( 2018.06.21迭代 )
    public String job_work_date;                            //工作日期
    public String job_work_time;                            //工作时间
    public List<JobInfoLikeJob> like_job_list;              //相似职位列表
}
