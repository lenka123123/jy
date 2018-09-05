package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.UserInfo;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.rest.Request;

import java.io.File;
import java.util.HashMap;

import sinia.com.baihangeducation.mine.view.IEditBaseInfoView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018.03.14.
 */

public class EditBaseInfoPresenter extends BasePresenter {

    private Activity activity;
    private IEditBaseInfoView view;
    private UserInfo baseinfo;


    public EditBaseInfoPresenter(Activity activity, IEditBaseInfoView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    /**
     * 编辑资料请求
     */
    public void editBaseInfo() {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "editBaseInfo", "ucenter", true);
        info.put("nickname", view.getNickName());
        info.put("gender", view.getGender());
        info.put("slogan", view.getSlogan());

        Request<String> request = postFile(info);
        if (!TextUtils.isEmpty(view.getAvatar())) {
            request.add("avatar", new FileBinary(new File(view.getAvatar())));
            view.showLoading();
            model.execute(activity, request, new OnRequestListener() {
                @Override
                public void requestSuccess(BaseResponseBean bean) {
                    UserInfo userInfo = bean.parseObject(UserInfo.class);
                    view.editBaseInfoSuccess(userInfo);
                }

                @Override
                public void requestFailed(String error) {

                }

                @Override
                public void requestFinish() {

                }
            });
        }
    }
}
