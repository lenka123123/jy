package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.MyReleaseFunInfo;
import com.mcxtzhang.swipemenulib.info.bean.MyReleaseInfo;

/**
 * Created by Administrator on 2018/4/10.
 */

public interface MyReleaseHelpView extends BaseView {

    String getTab();

    String getType();

    String getPage();

    String getPerpage();

    void getHelpHelpSuccess(List<MyReleaseInfo> myReleaseInfoHelp, int maxpage);

    void getHelpTranfSuccess(List<MyReleaseInfo> myReleaseInfoTranf, int maxpage);

    void getFunSuccess(List<MyReleaseFunInfo> myReleaseFunInfos, int maxpage);
}
