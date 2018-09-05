package sinia.com.baihangeducation.newcampus.view;

import com.example.framwork.base.BaseView;

import sinia.com.baihangeducation.newcampus.info.FunInfo;

public interface IGetFunView extends BaseView{
    String getFunPage();

    String getFunPerpage();

    void getFunInfoSuccess(FunInfo info , int maxpage);
}
