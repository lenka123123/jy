package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.bean.PartTimeListInfo;

/**
 * Created by Administrator on 2018/4/13.
 */

public interface PartTimeGrowthRecordView extends BaseView {

    String getPage();

    String getPerpage();

    void getPartTimeGrowthRecordSuccess(PartTimeListInfo mPartTimeListInfo, int maxpage);
}
