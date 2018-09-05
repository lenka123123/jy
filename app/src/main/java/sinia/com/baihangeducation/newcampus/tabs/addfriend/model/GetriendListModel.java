package sinia.com.baihangeducation.newcampus.tabs.addfriend.model;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.interfaces.AddFriendDataListener;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.interfaces.GetFriendDataListener;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.GetFriendListBean;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.SearchFriendListBean;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class GetriendListModel extends BasePresenter {

    private List<SearchFriendListBean> listBeans = new ArrayList<>();

    private Activity context;

    public GetriendListModel(Activity activity) {
        super(activity);
        context = activity;
    }


    public void getHotFunList(int type, int page, String s, final GetFriendDataListener listener) {

        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(context, "getFriend", "school", false);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
//        info.put("search", AppConfig.TOKEN);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                GetFriendListBean addListInfo = bean.parseObject(GetFriendListBean.class);
                listener.getHotFunDataSuccess(addListInfo);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(context, error);

            }

            @Override
            public void requestFinish() {

            }
        });

    }


    private int RequestType = 0;

    private void getList(int type) {
        if (type >= 5) {
            return;
        }
        RequestType = type;
        if (type == 1) {
            listBeans.clear();
        }

    }

}
