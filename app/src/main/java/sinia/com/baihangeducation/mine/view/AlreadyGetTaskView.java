package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.bean.AlreadyGetTaskListInfo;

/**
 * Created by Administrator on 2018/4/16.
 */

public interface AlreadyGetTaskView extends BaseView {

    String getPage();

    String getPerpage();

    /**
     * 查询类型 1未完成 2审核中 3已完成
     *
     * @return
     */
    String getTab();

    /**
     * 获得已完成的数据
     */
    void getUnCompleteSuccess(AlreadyGetTaskListInfo mAlreadyGetTaskListInfo,int maxpage);
    /**
     * 刷新
     */
    void doRefresh();

    /**
     * 获得审核中的数据
     */
    void getAuditSuccess();

    /**
     * 获得已完成的数据
     */
    void getCompleteSuccess();
}
