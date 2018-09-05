package sinia.com.baihangeducation.home.present;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import com.mcxtzhang.swipemenulib.info.bean.AddCollectionSuccessInfo;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.home.view.AddCollctionView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/4/20.
 */

public class AddOrDetelCollctionPresenter extends BasePresenter {
    private Activity activity;
    private AddCollctionView view;

    public AddOrDetelCollctionPresenter(Activity activity, AddCollctionView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void addCollection() {
        HashMap addCollectionInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "setCollect", "home", true);
//（1：全职 2：兼职；3：趣事；4:攻略 5试卷 6秘籍 7资讯（每日分享） 8培训）
        addCollectionInfo.put("user_id", AppConfig.USERID);
        addCollectionInfo.put("token", AppConfig.TOKEN);
        addCollectionInfo.put("type", view.getAddCollctionType());
        addCollectionInfo.put("type_id", view.getAddClloectionTpyeId());


        post(addCollectionInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                AddCollectionSuccessInfo mAddCollectionSuccessInfo = bean.parseObject(AddCollectionSuccessInfo.class);
                view.addCollectionSuccess(mAddCollectionSuccessInfo);
            }

            @Override
            public void requestFailed(String error) {

            }

            @Override
            public void requestFinish() {

            }
        });
    }

    public void detelCollection() {
        HashMap addCollectionInfo = BaseRequestInfo.getInstance().getRequestInfo(activity, "dropMyCollect", "ucenter", true);
        addCollectionInfo.put("collect_id", view.getAddClloectionId());
        addCollectionInfo.put("user_id", AppConfig.USERID);
        addCollectionInfo.put("token", AppConfig.TOKEN);
        System.out.println(AppConfig.USERID + "AppConfigAppConfig22222" + AppConfig.USERID + "" + view.getAddClloectionId());
        post(addCollectionInfo, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                view.detelCollectionSuccess();
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
