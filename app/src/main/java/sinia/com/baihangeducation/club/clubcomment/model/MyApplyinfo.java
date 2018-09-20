package sinia.com.baihangeducation.club.clubcomment.model;

import com.mcxtzhang.swipemenulib.info.bean.MySendTagInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/4/17.
 */

public class MyApplyinfo {
    public int job_id ;                          //职位ID
    public int job_apply_id ;                    //职位申请记录ID
    public String job_title ;                    //职位名称
    public String job_money ;                   //薪资
    public String job_company_logo ;             //发布公司logo
    public String job_city_name ;                //区域名称
    public int job_apply_status ;                //申请状态 状态：1、投递成功，2、已查看 3、待审核 4、审核通过 5审核未通过
    public String job_apply_status_name ;        //申请状态名称
    public String job_apply_date ;               //申请日期


    public int job_difficulty_point ;
    public int job_accord_point ;
    public int money_accord_point ;


}
