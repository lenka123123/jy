package sinia.com.baihangeducation.home.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.HomeAndFindHelpEachOtherDetailAllInfo;

/**
 * Created by Administrator on 2018/4/27.
 */

public interface HomeAndFindHelpEachOtherDetailView extends BaseView {

    String getPage();

    String getPerpage();

    /**
     * 类型 1早读趣事 2秘籍 3攻略 4试卷 5互助
     * @return
     */
    String getType();
    String getTypeId();
    String getParentId();

    String getLocationLat();

    String getLocationLng();

    String getCooperationId();

    void getDetailInfoSuccess(HomeAndFindHelpEachOtherDetailAllInfo mHomeAndFindHelpEachOtherDetailAllInfo , int maxpage);

    /**
     * 获取每日分享详情成功
     */
    void getSendMessageSuccess();

    void getApplySuccess();
}
