package sinia.com.baihangeducation.find.view;

import com.example.framwork.base.BaseView;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.GetShareEveryDayInfo;
import com.mcxtzhang.swipemenulib.info.bean.ShareEveryDayTabInfo;

/**
 * Created by Administrator on 2018/4/19.
 */

public interface ShareEveryDayView extends BaseView {

    /**
     * 分类ID
     *
     * @return
     */
    String getTabId();

    /**
     * 页码
     *
     * @return
     */
    String getPage();

    /**
     * 每页数
     *
     * @return
     */
    String getPerpage();

    /**
     * 获取tab数据
     *
     * @param list
     */
    void getShareEveryDaySuccess(List<ShareEveryDayTabInfo> list);

    /**
     * 获取每日分享列表
     */
    void getShareEveryDayDataSuccess(List<GetShareEveryDayInfo> list,int maxpage);

}
