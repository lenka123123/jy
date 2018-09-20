package sinia.com.baihangeducation.club.notice.model;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.club.notice.ClubNoticeActivity;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * 创建日期：2018/5/22 on 11:28
 * 描述:
 * 作者:yuxd Administrator
 */
public class ClubNoticeModel extends BasePresenter {

    private ClubNoticeActivity activity;

    public ClubNoticeModel(ClubNoticeActivity activity) {
        super(activity);
        this.activity = activity;

    }

    public void getClubAnnounce(String club_id, String introduce ) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setIntroduce", "club", true);

        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);

        info.put("club_id", club_id);
        info.put("introduce", introduce );

        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Toast.getInstance().showSuccessToast(activity, "编辑成功");
//                ClubDetailBean clubSchoolList = bean.parseObject(ClubDetailBean.class);
                activity.finish();

            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);


            }

            @Override
            public void requestFinish() {

            }
        });

    }

}
