package sinia.com.baihangeducation.minecompany.view;

import com.example.framwork.base.BaseView;

import sinia.com.baihangeducation.minecompany.info.CompanyUCenterReleaseTrainingInfo;
import com.mcxtzhang.swipemenulib.info.bean.CompanyReleaseJobInfo;

public interface ICompanyUCenterReleaseJobView extends BaseView{
    /**
     * 类型 1、全职，2、兼职    ( 必传 )
     * @return
     */
    String getCompanyUCenterReleaseJobTypy();

    String getCompanyUCenterReleaseJobPage();

    String getCompanyUCenterReleaseJobPerPage();


    //修改职位状态
    String getReleaseJobId();
    String getReleaseStatus();
    void getStatusChangeSuccess(int status);

    void getCompanyUCenterReleaseJobSuccess(CompanyReleaseJobInfo info , int maxpage);

    //培训列表
    void getCompanyUcenterReleaseTrianSuccess(CompanyUCenterReleaseTrainingInfo trainingInfo,int maxpage);
}
