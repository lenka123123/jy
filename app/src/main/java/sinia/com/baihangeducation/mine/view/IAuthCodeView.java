package sinia.com.baihangeducation.mine.view;

import com.mcxtzhang.swipemenulib.info.bean.AuthCodeInfo;

/**
 * Created by Administrator on 2018.03.07.
 */

public interface IAuthCodeView {

    String getPhone();

    String getAuthCodeType();

    void showAuthCodeSuccess(AuthCodeInfo authCodeInfo);

    void showGetAuthCodeError(String error);
}
