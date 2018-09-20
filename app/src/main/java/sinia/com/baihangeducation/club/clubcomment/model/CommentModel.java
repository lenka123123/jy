package sinia.com.baihangeducation.club.clubcomment.model;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.club.clubcomment.CommentActivity;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * 创建日期：2018/5/22 on 11:28
 * 描述:
 * 作者:yuxd Administrator
 */
public class CommentModel extends BasePresenter {


    private CommentActivity commentActivity;

    public CommentModel(CommentActivity activity) {
        super(activity);
        this.commentActivity = activity;
    }

    public void setComment(int job_apply_id, int difficulty_point, int job_accord_point, int money_accord_point) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "pushJobPoint", "publish", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);

        info.put("job_apply_id", job_apply_id);
        info.put("difficulty_point", difficulty_point);
        info.put("job_accord_point", job_accord_point);
        info.put("money_accord_point", money_accord_point);

        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                commentActivity.finish();
                Toast.getInstance().showSuccessToast(activity, "已提交");
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


    public void getMyJobApplyInfo(int job_apply_id) {
        HashMap mMySendData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyJobApplyInfo", "ucenter", true);
        mMySendData.put("user_id", AppConfig.USERID);
        mMySendData.put("token", AppConfig.TOKEN);
        mMySendData.put("job_apply_id", job_apply_id);

        post(mMySendData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {

                MyApplyinfo myApplyinfo = bean.parseObject(MyApplyinfo.class);
                commentActivity.setContentInfo(myApplyinfo);
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
