package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

/**
 * Created by Administrator on 2018.03.15.
 */

public interface IUpdataPwdView extends BaseView {

    /**
     * 获取新密码
     *
     * @return
     */
    String getNewPwd();

    /**
     * 获取第二次新密码
     *
     * @return
     */
    String getNewPwdAgain();

    /**
     * 获取验证码
     *
     * @return
     */
    String getAuthCode();


    /**
     * 修改密码成功
     */
    void showUpdataPwdSuccess();

}
