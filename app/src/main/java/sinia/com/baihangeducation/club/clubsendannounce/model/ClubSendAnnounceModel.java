package sinia.com.baihangeducation.club.clubsendannounce.model;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.club.clubsendannounce.ClubSendAnnounceActivity;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * 创建日期：2018/5/22 on 11:28
 * 描述:
 * 作者:yuxd Administrator
 */
public class ClubSendAnnounceModel extends BasePresenter {

    private ClubSendAnnounceActivity activity;

    public ClubSendAnnounceModel(ClubSendAnnounceActivity activity) {
        super(activity);
        this.activity = activity;

    }

    public void getClubListList(boolean isShoolType, String club_id, String notice_id, String title, String content) {
        if (!AppConfig.ISlOGINED) {
            new AlertDialog.Builder(activity).setTitle("提示！").setMessage("您尚未登录，请先登录。").setPositiveButton("登录", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Goto.toLogin(activity);
                }
            }).setNegativeButton("取消", null).show();
            return;
        }

        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setNotice", "club", true);

        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);

        info.put("club_id", club_id);
        info.put("type", isShoolType ? "2" : "1");

        if (!notice_id.equals(""))
            info.put("notice_id", notice_id);  ///公告ID    ( 非必传（修改时传） )
        info.put("title", title);
        info.put("content", content);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Toast.getInstance().showSuccessToast(activity, "发布成功");
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
