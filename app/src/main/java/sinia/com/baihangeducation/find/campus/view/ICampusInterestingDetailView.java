package sinia.com.baihangeducation.find.campus.view;

import com.example.framwork.base.BaseView;

import sinia.com.baihangeducation.find.campus.info.CampusInterestingDetailListInfo;

public interface ICampusInterestingDetailView extends BaseView {
    String getFunId();

    String getPage();

    String getPerpage();

    String getType();

    String getParentCommentId();

    void getFunSuccess(CampusInterestingDetailListInfo info);

    /**
     * 获取每日分享留言详情成功
     */
    void getShareEveryDaySendCommenSuccess();
}
