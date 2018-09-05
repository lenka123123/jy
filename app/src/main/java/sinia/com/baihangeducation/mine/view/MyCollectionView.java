package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.MyCollectionListInfo;

/**
 * Created by Administrator on 2018/4/11.
 */

public interface MyCollectionView extends BaseView {

    /**
     * 收藏类型（1：兼职 2：全职；3：趣事；4:攻略 5试卷 6秘籍 7资讯）
     *
     * @return
     */
    String getType();

    /**
     * 获取页码
     *
     * @return
     */
    String getPage();

    /**
     * 获取每页数
     */
    String getPerpage();

    /**
     * 获取兼职成功
     */
    void getPatrTimeSuccess(List<MyCollectionListInfo> list,int maxpage);

    /**
     * 获取全职成功
     */
    void getAllTimeSuccess(List<MyCollectionListInfo> list,int maxpage);

    /**
     * 获取趣事成功
     */
    void getFunnySuccess(List<MyCollectionListInfo> list,int maxpage);
    /**
     * 获取攻略成功
     */
    void getStrategySuccess(List<MyCollectionListInfo> list,int maxpage);
    /**
     * 获取试卷成功
     */
    void getTestSuccess(List<MyCollectionListInfo> list,int maxpage);
    /**
     * 获取秘籍成功
     */
    void getSecretSuccess(List<MyCollectionListInfo> list,int maxpage);
    /**
     * 获取资讯成功
     */
    void getInformationSuccess(List<MyCollectionListInfo> list,int maxpage);
}
