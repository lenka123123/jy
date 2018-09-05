package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

/**
 * Created by Administrator on 2018.03.12.
 */

public interface IForgetPasswordView extends BaseView{

    String getPhoneNum();

    String getAuthCode();

    String getPassword();

    String getPasswordAgain();

    void showAuthCodeSuccess();

}
