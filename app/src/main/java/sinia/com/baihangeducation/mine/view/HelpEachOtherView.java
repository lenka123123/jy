package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.bean.HelpEachOtherListInfo;

/**
 * Created by Administrator on 2018/4/13.
 */

public interface HelpEachOtherView extends BaseView {
    /**
     * 类型 1打赏 2领赏
     *
     * @return
     */
    String getType();

    /**
     * 页码
     *
     * @return
     */
    String getPage();

    /**
     * 每页数量
     *
     * @return
     */
    String getPerpage();

    /**
     * 获取互助信息成功
     */
    void getHelpEachOtherDataSuccess(HelpEachOtherListInfo data, int maxpag);
}
