package sinia.com.baihangeducation.newcampus.tabs.searchfriend.model;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.SearchFriendListBean;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.interfaces.SearchFriendDataListener;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class SearchFriendListModel extends BasePresenter {

    private Activity context;

    public SearchFriendListModel(Activity activity) {
        super(activity);
        context = activity;
    }


    public void getHotFunList(int type, int page, String s, final SearchFriendDataListener listener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(context, "getFriendMore", "school", false);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("page", page);
        info.put("perpage", "20");
        info.put("type", type);
        info.put("search", s);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {

                SearchFriendListBean addListInfo = bean.parseObject(SearchFriendListBean.class);
                listener.getHotFunDataSuccess(addListInfo);
            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(context, error);
                listener.getHotFunDataFail();
            }

            @Override
            public void requestFinish() {

            }
        });

    }
}
