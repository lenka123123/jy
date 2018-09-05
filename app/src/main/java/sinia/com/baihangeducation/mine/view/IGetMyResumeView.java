package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.bean.MyResumInfo;
import com.mcxtzhang.swipemenulib.info.bean.MyResumInfoforResume;

/**
 * Created by Administrator on 2018/3/30.
 */

public interface IGetMyResumeView extends BaseView {
    void getMyResumeSuccess(MyResumInfoforResume myResumInfos);

    void getMyResumeFail();
}
