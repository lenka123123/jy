package sinia.com.baihangeducation.find.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.JobBangClassSecondListInfo;
import com.mcxtzhang.swipemenulib.info.JobBangClassSecondRadioListInfo;

/**
 * Created by Administrator on 2018/4/25.
 */

public interface JobBangClassInfoView extends BaseView {

    String getType();

    String getRadioType();

    String getCadeId();

    String getMoneyId();

    String getOrderId();

    String getIsHot();
    String getIsFree();

    String getIsChoice();

    String getPage();

    String getPerpage();



    void getJobBangClassInfoSuccess(JobBangClassSecondListInfo mJobBangClassSecondListInfo, int maxpage);

    void getJobBangClassRadioInfoSuccess(JobBangClassSecondRadioListInfo mJobBangClassSecondRadioListInfo);
}
