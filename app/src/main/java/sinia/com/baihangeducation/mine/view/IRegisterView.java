package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

/**
 * Created by Administrator on 2018.03.07.
 */

public interface IRegisterView extends BaseView {

    void succress();

    /**
     * 获取手机号码
     * @return
     */
    String getphoneNum();

    /**
     * 获取验证码
     * @return
     */
    String getAuthCode();

    /**
     * 获取密码
     * @return
     */
    String getPassword();

    /**
     * 获取第二次密码
     * @return
     */
    String getPasswordAgain();
    /**
     * 检查是否阅读协议
     * @return
     */
    Boolean getIsRead();

}
