package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.bean.DoSignInfo;
import com.mcxtzhang.swipemenulib.info.bean.GetSignInfo;

/**
 * Created by Administrator on 2018/4/13.
 */

public interface ISignView extends BaseView {

    String getMouth();

    void getSignDaysSuccess(GetSignInfo getSignInfo);

    void doSignSuccess(DoSignInfo doSignInfo);
}
