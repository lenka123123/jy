package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.bean.FragmentMessageListInfo;

/**
 * Created by Administrator on 2018/4/28.
 */

public interface FragmentMessageView extends BaseView {

    String getPage();

    String getPerpage();

    void getMessageSuccess(FragmentMessageListInfo mInfo, int maxpage);

    void setMessageReadSuccess( );
}
