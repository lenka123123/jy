package sinia.com.baihangeducation.home.present;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.mine.model.AccountManger;
import sinia.com.baihangeducation.home.view.IMarkTrainingView;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class TraingJoinInPresenter extends BasePresenter {
    private Activity activity;
    private IMarkTrainingView view;

    public TraingJoinInPresenter(Activity activity, IMarkTrainingView view) {
        super(activity);
        this.activity = activity;
        this.view = view;
    }

    public void markTraining() {
        if (!AccountManger.checkMarkTraining(activity,view.getRealName(),view.getContactTel())){
            return;
        }
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity,"setNewTrainOrder","home",true);
        Log.i("报名培训参数",view.getTrainingId()+"ID");
        Log.i("报名培训参数",view.getRealName()+"名字");
        Log.i("报名培训参数",view.getContactTel()+"电话");
        Log.i("报名培训参数",view.getEmail()+"邮箱");
        Log.i("报名培训参数",view.getMarkTraingMessage()+"留言");

        info.put("training_id",view.getTrainingId());
        info.put("realname",view.getRealName());
        info.put("contact_tel",view.getContactTel());
        info.put("email",view.getEmail());
        info.put("message",view.getMarkTraingMessage());
        info.put("user_coupon_id",view.getUserCouponId());
        view.showLoading();
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("报名结果查询",bean.toString());
                view.joinSuccess();
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity,error);
            }

            @Override
            public void requestFinish() {
                view.hideLoading();
            }
        });
    }
}
