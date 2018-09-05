package sinia.com.baihangeducation.mine.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.SPUtils;
import com.example.framwork.utils.Toast;
import com.example.framwork.utils.UserInfo;

import java.io.IOException;
import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.mine.model.AccountManger;
import sinia.com.baihangeducation.mine.view.GetBaseInfoView;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

import com.google.gson.Gson;
import com.mcxtzhang.swipemenulib.utils.Constants;
import com.mcxtzhang.swipemenulib.utils.FileUtils;

/**
 * Created by Administrator on 2018/4/17.
 */

public class GetBaseInfoPresenter extends BasePresenter {

    private Activity activity;
    private GetBaseInfoView view;


    public GetBaseInfoPresenter(Activity activity, GetBaseInfoView view) {
        super(activity);
        this.activity = activity;
        this.view = view;

    }

    public GetBaseInfoPresenter(Activity activity) {
        super(activity);
        this.activity = activity;

    }


    public void getBaseInfoLoginAfter(String token,String userid) {
        HashMap mGetBaseInfoData = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyInfo", "ucenter", true);
        mGetBaseInfoData.put("token", token);
        mGetBaseInfoData.put("user_id",userid);
        if (AppConfig.TOKEN.length() < 2 || AppConfig.USERID.length() < 2) {
            new AlertDialog.Builder(activity).setTitle("提示！").setMessage("您尚未登录，请先登录。").setPositiveButton("登录", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Goto.toLogin(activity);
                }
            }).setNegativeButton("取消", null).show();
            return;
        }
        post(mGetBaseInfoData, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                Log.i("获取资本信息", bean.toString());

                UserInfo userInfo = bean.parseObject(UserInfo.class);
                Gson gson=new Gson();
               String s= gson.toJson(userInfo);

                try {
                    FileUtils.writeInternal(activity,"userinfo",s);
                } catch (IOException e) {
                    e.printStackTrace();
                }


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
