package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.rest.Request;

import java.io.File;
import java.util.HashMap;

import sinia.com.baihangeducation.mine.model.AccountManger;
import sinia.com.baihangeducation.mine.view.IReleaseView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * Created by Administrator on 2018/3/28.
 */

public class ReleaseHelpEachOtherPresenter extends BasePresenter {

    private Activity activity;
    private IReleaseView view;

    public ReleaseHelpEachOtherPresenter(Activity activity, IReleaseView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void doReleaseHelpEachOhter() {
        if (!AccountManger.checkReleaHelpEachOther(activity, view.getType(), view.getNeedPeopleNum(), view.getIsPay(), view.getSex(), view.getSPrice(), view.getInputTitle(), view.getInputContent())) {
            return;
        }
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setPush", "ucenter", true);
        info.put("tab", view.getTab());
        info.put("type", view.getType());
        Log.i("查看typy类型",view.getType());
        info.put("need_num", view.getNeedPeopleNum());
        info.put("is_paid", view.getIsPay());
        info.put("sex", view.getSex());
        info.put("price", view.getSPrice());
        info.put("title", view.getInputTitle());
        info.put("content", view.getInputContent());
        info.put("lng", view.geLocationLng());
        info.put("lat", view.getLocationLat());
        info.put("device_id", CommonUtil.getAndroidId(activity));
        info.put("images", view.getImage());
        Log.i("发布求助-求助", info.toString());
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("发布求助-求助", bean.toString());
                Toast.getInstance().showSuccessToast(activity, "发布成功");
                view.releaseHelpSuccess();
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

    public void doReleaseInteresting() {
        if (!AccountManger.checkReleaInteresting(activity, view.getImage(), view.getInputTitle(), view.getInputContent())) {
            return;
        }
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "setPush", "ucenter", true);
        info.put("tab", view.getTab());
        info.put("type", view.getType());
        info.put("need_num", view.getNeedPeopleNum());
        info.put("is_paid", view.getIsPay());
        info.put("sex", view.getSex());
        info.put("price", view.getSPrice());
        info.put("title", view.getInputTitle());
        info.put("content", view.getInputContent());
        info.put("lng", view.geLocationLng());
        info.put("lat", view.getLocationLat());
        info.put("device_id", CommonUtil.getAndroidId(activity));
        Request<String> request = postFile(info);
        request.add("images", new FileBinary(new File(view.getImage())));
        view.showLoading();
        model.execute(activity, request, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("发布求助-趣事", bean.toString());
                Toast.getInstance().showSuccessToast(activity, "发布成功");
                view.releaseInterestingSuccess();
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }
}
