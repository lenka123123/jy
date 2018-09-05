package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.WantAreaList;
import com.mcxtzhang.swipemenulib.info.bean.WantAreaList_Up;
import com.mcxtzhang.swipemenulib.info.bean.WantJobList;
import com.mcxtzhang.swipemenulib.info.bean.WantJobList_Up;

/**
 * Created by Administrator on 2018/4/14.
 */

public interface WantJobView extends BaseView {

    /**
     * 获取我的求职意向数据
     *
     * @return
     */
    WantJobList_Up getMyWantJobData();

    /**
     * 获取我的意向区域数据
     *
     * @return
     */
    WantAreaList_Up getMyWantAreaList_Up();

    /**
     * 获取求职意向信息
     */
    void getWantJobSuccess(List<WantJobList> wantJobLists);

    /**
     * 获取 意向区域信息
     */
    void getWantAreaSuccess(List<WantAreaList> wantAreaLists);
}
