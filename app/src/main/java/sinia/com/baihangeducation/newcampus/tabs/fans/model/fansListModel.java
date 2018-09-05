package sinia.com.baihangeducation.newcampus.tabs.fans.model;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.newcampus.tabs.fans.bean.SearchFriendListBean;
import sinia.com.baihangeducation.newcampus.tabs.fans.interfaces.SearchFriendDataListener;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class fansListModel extends BasePresenter {

    private Activity context;

    public fansListModel(Activity activity) {
        super(activity);
        context = activity;
    }


    public void getHotFunList(String other_id, String type, String page, String perpage, final SearchFriendDataListener listener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(context, "getFollow", "school", false);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);

        info.put("other_id", other_id);
        info.put("type", type); // 1：获取该用户的粉丝 2：获取该用户的关注
        info.put("page", page);
        info.put("perpage", perpage);
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
