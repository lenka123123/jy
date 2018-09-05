package sinia.com.baihangeducation.find.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.JobBangClassListInfo;

/**
 * Created by Administrator on 2018/4/20.
 */

public interface JobBangClassView extends BaseView {
    /**
     * 获取就业邦学堂信息成功
     */
    void getJobBangClassDataSuccess(JobBangClassListInfo mJobBangClassListInfo);
}
