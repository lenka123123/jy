package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

/**
 * Created by Administrator on 2018/3/29.
 */

public interface IUCentreBaseInfoView extends BaseView {
    /**
     * 获取头像
     *
     * @return
     */
    String getUCentreImage();

    /**
     * 获取昵称
     *
     * @return
     */
    String getUCentreNickName();

    /**
     * 获取个性签名
     *
     * @return
     */
    String getUCentreSlogan();

    /**
     * 获取性别
     *
     * @return
     */
    String getUCentreGender();

    /**
     * 获取Email
     *
     * @return
     */
    String getUCentreEmail();
    /**
     * 修改成功
     *
     * @return
     */
    void upDataUCentreBaseInfoSuccess();
    /**
     * 修改失败
     *
     * @return
     */
    void upDataUCentreBaseInfoFail();
}
