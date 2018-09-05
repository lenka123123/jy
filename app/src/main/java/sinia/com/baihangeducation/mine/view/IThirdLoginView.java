package sinia.com.baihangeducation.mine.view;

/**
 * Created by gao on 2017/5/4.
 */

public interface IThirdLoginView {


    String getUserType();

    String getVcode();

    String getPhoneNum();

    void showLoading();

    void hideLoading();

    void showSuccess();
}
