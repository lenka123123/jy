package sinia.com.baihangeducation.newcampus.tabs.addfriend.model;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.newcampus.tabs.addfriend.interfaces.AddFriendDataListener;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.SearchFriendListBean;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class AddFriendListModel extends BasePresenter {

    private List<SearchFriendListBean> listBeans = new ArrayList<>();

    private Activity context;

    public AddFriendListModel(Activity activity) {
        super(activity);
        context = activity;
    }

    private AddFriendDataListener listener;

    public void getHotFunList(int type, int page, String s, AddFriendDataListener listener) {
        this.listener = listener;
        getList(type);
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
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(context, "getFriendMore", "school", false);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("page", "1");
        info.put("perpage", "3");
        info.put("type", type);
//        info.put("search", AppConfig.TOKEN);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                SearchFriendListBean addListInfo = bean.parseObject(SearchFriendListBean.class);
                addListInfo.count = String.valueOf(RequestType);
                listBeans.add(addListInfo);
                if (RequestType == 4) {
                    listener.getHotFunDataSuccess(listBeans, 1);
                }
                ++RequestType;

                getList(RequestType);


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

}
