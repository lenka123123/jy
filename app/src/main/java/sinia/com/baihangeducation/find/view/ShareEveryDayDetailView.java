package sinia.com.baihangeducation.find.view;

import com.example.framwork.base.BaseView;

import com.mcxtzhang.swipemenulib.info.JobBangDetailListInfo;
import com.mcxtzhang.swipemenulib.info.ShareEveryDayDetailListInfo;

/**
 * Created by Administrator on 2018/4/19.
 */

public interface ShareEveryDayDetailView extends BaseView {

    /**
     * 获取id
     *
     * @return
     */
    String getNewsId();

    String getPage();

    String getPerpage();

    String getType();

    String getParentCommentId();



    /**
     * 获取每日分享详情成功
     */
    void getShareEveryDayDetailSuccess(ShareEveryDayDetailListInfo info, int maxpage); /**
     * 获取每日分享详情成功
     */
    void getJobBangDetailSuccess(JobBangDetailListInfo info, int maxpage);
    /**
     * 获取每日分享留言详情成功
     */
    void getShareEveryDaySendCommenSuccess();
}
