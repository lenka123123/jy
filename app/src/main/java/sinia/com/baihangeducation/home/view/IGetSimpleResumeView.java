package sinia.com.baihangeducation.home.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.SimpleResumeInfo;

public interface IGetSimpleResumeView extends BaseView{

    String getName();

    String getSchoolName();

    String getSchoolId();

    String getMajorName();

    String getMajorId();

    void getSimpleResumeSuccess(SimpleResumeInfo simpleResumeInfo);

    void sendSimpleResumeSuccess();
}
