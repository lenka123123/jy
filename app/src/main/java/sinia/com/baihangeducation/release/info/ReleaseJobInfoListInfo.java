package sinia.com.baihangeducation.release.info;

import java.util.List;

import sinia.com.baihangeducation.release.info.bean.JobEducationListInfo;
import sinia.com.baihangeducation.release.info.bean.JobExpListInfo;
import sinia.com.baihangeducation.release.info.bean.JobIndustryListInfo;
import sinia.com.baihangeducation.release.info.bean.JobSalaryListInfo;
import sinia.com.baihangeducation.release.info.bean.JobSexListInfo;
import sinia.com.baihangeducation.release.info.bean.JobTagListInfo;
import sinia.com.baihangeducation.release.info.bean.JobTypeListInfo;

public class ReleaseJobInfoListInfo {
    public List<JobEducationListInfo> job_education_list;       //学历要求
    public List<JobExpListInfo> job_experience_list;            //经验要求
    public List<JobIndustryListInfo> job_industry_list;         //岗位类型
    public List<JobSalaryListInfo> job_money_type_list;         //薪资类型
    public List<JobSexListInfo> job_sex_list;                   //性别要求
    public List<JobTagListInfo> job_tag_list;                   //职位标签
    public List<JobTypeListInfo> job_type_list;                 //所属行业
}
