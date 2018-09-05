package sinia.com.baihangeducation.home.view;

import com.example.framwork.base.BaseView;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.ReCommendedInfo;

/**
 * Created by Administrator on 2018/4/23.
 */

public interface ReCommendedView extends BaseView{
    String getPage();

    String getPerpage();

    void getReCommendedDataSuccess(List<ReCommendedInfo> mReCommendedInfo,int maxpage);
}
