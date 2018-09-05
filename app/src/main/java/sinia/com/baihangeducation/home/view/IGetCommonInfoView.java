package sinia.com.baihangeducation.home.view;


import com.mcxtzhang.swipemenulib.info.bean.CommonInfo;

/**
 * Created by lenovo on 2017/3/9.
 */

public interface IGetCommonInfoView {

    String getLocationAdcode();

    void showSuccess( CommonInfo commonInfo);

    void showError();
}
