package sinia.com.baihangeducation.newcampus.presenter;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.newcampus.interfaces.AddFirendListener;
import sinia.com.baihangeducation.newcampus.interfaces.CommentListener;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.CommentListBean;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class CommentPresenter extends BasePresenter {
    private Activity activity;

    public CommentPresenter(Activity activity) {
        super(activity);
        this.activity = activity;
    }


    public void releaseComment(String type, String type_id, String parent_id, String page, String perpage, final CommentListener addFirendPresenter) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getCommentList", "home", true);
        info.put("type", type);
        info.put("type_id", type_id);
        info.put("parent_id", parent_id);
        info.put("page", page);
        info.put("perpage", perpage);

        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {


                CommentListBean mTraingDetailInfo = bean.parseObject(CommentListBean.class);
                addFirendPresenter.getAddFirendSuccessListener(mTraingDetailInfo);
            }

            @Override
            public void requestFailed(String error) {
                addFirendPresenter.getAddFirendFailListener();
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {

            }
        });

    }
}
