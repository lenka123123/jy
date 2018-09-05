package sinia.com.baihangeducation.home.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.TraingDetailInfo;

/**
 * Created by Administrator on 2018/4/27.
 */

public interface TraingDetailView extends BaseView{

    String getTrainId();

    void getMyReleaseTraingDetailInfoSuccess(TraingDetailInfo mTraingDetailInfo);


}
