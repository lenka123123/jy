package sinia.com.baihangeducation.mine.view;

import com.mcxtzhang.swipemenulib.info.bean.FindApplyPersonListInfo;

/**
 * Created by Administrator on 2018/4/10.
 */

public interface GetApplyMyReleaseView {

    String getApplyUserId();

    String getCooperationId();

    void getSuccess(FindApplyPersonListInfo info);

    void getConfirmSuccess();
}
