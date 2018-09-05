package sinia.com.baihangeducation.home.view;

import com.example.framwork.base.BaseView;
import com.mcxtzhang.swipemenulib.info.bean.JobInfoes;

/**
 * Created by Administrator on 2018/4/26.
 */

public interface JobInfoView extends BaseView {
    String getJobId();

    String getCityId();

    void getJobInfoSuccess(JobInfoes mJobInfo);
}
