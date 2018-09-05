package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.IsCompleteInfo;

/**
 * Created by Administrator on 2018.03.07.
 */

public interface ILoginView extends BaseView {

    /**
     * 登录成功
     */
    void showLoginSuccress();

    /**
     * 获取手机号码
     *
     * @return
     */
    String getPhoneNum();

    /**
     * 获取密码
     *
     * @return
     */
    String getPassword();

    /**
     * 获取经度
     * @return
     */
    String getLocationLng();

    /**
     * 获取纬度
     * @return
     */
    String getLocationLat();

    void getCompleteSucess(IsCompleteInfo mIsCompleteInfo);
}
