package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.bean.TraingListInfo;

/**
 * Created by Administrator on 2018/4/13.
 */

public interface TraingGrowthRecordView extends BaseView {

    String getPage();

    String getPerpage();

    void getTraingGrowthRecordSuccess(TraingListInfo data, int maxpage);
}
