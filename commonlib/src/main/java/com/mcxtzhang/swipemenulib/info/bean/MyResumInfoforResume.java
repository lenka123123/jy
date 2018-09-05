package com.mcxtzhang.swipemenulib.info.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/30.
 */

public class MyResumInfoforResume implements Serializable {

    public String address;                                     //详细地址
    public String area_id;                                        //区ID
    public String area_name;                                   //省市区名称
    public String birthday;                                    //生日
    public String city_id;                                        //市ID
    public MyResumeEducation_expInfo education_exp;            //教育经历
    public String evaluation;//"",
    public String gender;//"3",
    public String gender_name;//"保密",
    public String graduated;//"2",
    public String graduated_name;//"未毕业",
    public String health_no;//"",
    public String health_photo;//"",
    public String intact;//"40",
    public List<MyResumeJobInfo>  job_exp;//"A",
    public String name;//
    public String province_id;//"0",
    public String resume_id;//"196",
    public String student_no;//"",
    public String student_photo;//"",
    public String tel;//"",
    public String upd_time;//"1530502180",
    public String user_avatar;//"http://yun-attachment.oss-cn-hangzhou.aliyuncs.com/njyb_avatar/832758bd77141b17dd274a5a84125d38.png",
    public String user_id;//"15030",
    public String user_nickname;//"jyb6741"                           //学生证号码
}
