package sinia.com.baihangeducation.mine.view;

import com.example.framwork.base.BaseView;

/**
 * Created by Administrator on 2018/3/27.
 */

public interface IReleaseView extends BaseView {

    /**
     * 获取tab   1互助2趣事
     *
     * @return
     */
    String getTab();

    /**
     * 获取type   1转让 2互助    ( 仅tab为1时传递 )
     *
     * @return
     */
    String getType();

    /**
     * 获取求助人数    ( 仅tab为1时传递 )
     *
     * @return
     */
    String getNeedPeopleNum();

    /**
     * 获取付费要求 1是2否    ( 仅tab为1时传递 )
     *
     * @return
     */
    String getIsPay();

    /**
     * 获取性别要求 1男2女3不限    ( 仅tab为1时传递 )
     *
     * @return
     */
    String getSex();

    /**
     * 获取付费金额    ( 仅tab为1时传递 )
     *
     * @return
     */
    String getSPrice();

    /**
     * 获取标题
     *
     * @return
     */
    String getInputTitle();

    /**
     * 获取内容
     *
     * @return
     */
    String getInputContent();

    /**
     * 获取经度    ( 仅tab为1时传递 )
     *
     * @return
     */
    String geLocationLng();

    /**
     * 获取纬度    ( 仅tab为1时传递 )
     *
     * @return
     */
    String getLocationLat();

    /**
     * 获取图片数组    ( 仅tab为2时传递 )
     *
     * @return
     */
    String getImage();

    void releaseHelpSuccess();
    void releaseInterestingSuccess();
}
