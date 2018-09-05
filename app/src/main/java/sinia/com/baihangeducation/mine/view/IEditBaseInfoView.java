package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;
import com.example.framwork.utils.UserInfo;

/**
 * Created by Administrator on 2018.03.14.
 */

public interface IEditBaseInfoView extends BaseView {

    /**
     * 获取头像
     *
     * @return
     */
    String getAvatar();

    /**
     * 获取昵称
     *
     * @return
     */
    String getNickName();

    /**
     * 获取个性签名
     *
     * @return
     */
    String getSlogan();

    /**
     * 获取性别
     *
     * @return
     */
    String getGender();

    /**
     * 编辑成功
     */
    void editBaseInfoSuccess(UserInfo info);

    /**
     * 编辑失败
     */
    void editBaseInfoFail();
}
