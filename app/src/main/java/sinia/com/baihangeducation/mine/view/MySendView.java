package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.bean.MySendListInfo;

/**
 * Created by Administrator on 2018/4/13.
 */

public interface MySendView extends BaseView{

    /**
     * 获取   职位类别：1、全职，2、兼职
     * @return
     */
    String getType();

    /**
     * 获取   页码    ( 非必传 )
     * @return
     */
    String getPage();

    /**
     * 获取 每页数
     * @return
     */
    String getPerpage();


    /**
     * 获取我的投递兼职列表成功
     */
    void getMySendPartTimeDataSuccess(MySendListInfo mMySendListInfo,int maxpage);
    /**
     * 获取我的投递全职列表成功
     */
    void getMySendAllTimeDataSuccess(MySendListInfo mMySendListInfo,int maxpage);
}
