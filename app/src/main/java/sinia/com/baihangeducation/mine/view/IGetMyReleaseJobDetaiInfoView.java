package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.bean.MyReleaseJobDetaiInfo;

public interface IGetMyReleaseJobDetaiInfoView extends BaseView{
    String getMyReleaseJobDetailJobId();
    String getMyReleaseJobDetailCityId();
    void getMyReleaseJobDetailInfoSuccess(MyReleaseJobDetaiInfo jobDetaiInfo);
}
