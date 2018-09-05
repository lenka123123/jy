package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.bean.RealNameInfo;

/**
 * Created by Administrator on 2018/3/23.
 */

public interface IRealNameView extends BaseView {

    /**
     * 提交实名认证 获取名字
     * @return
     */
    String getRealName();
    /**
     * 提交实名认证 获取身份证号码
     * @return
     */
    String getIDNum();
    /**
     * 提交实名认证 获取身份证正面照
     * @return
     */
    String getIDCard_On();
    /**
     * 提交实名认证 获取身份证反面照
     * @return
     */
    String getIDCard_Off();
    /**
     * 提交实名认证 获取手持身份证照
     * @return
     */
    String getIDCard_OnHand();

    /**
     * 提交实名认证 成功
     * @return
     */
    void submitSucess();
    /**
     * 提交实名认证 失败
     * @return
     */
    void submitFailed();
    /**
     * 获取实名认证信息成功
     * @return
     */
    void getRealNameSuccess(RealNameInfo info);
}
