package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

/**
 * Created by Administrator on 2018.03.14.
 */

public interface IBindingPhoneView extends BaseView {

    String getPhoneNum();

    String getAuthCode();

    String getUid();

    String getType();

    void showBindingSucess();
}
