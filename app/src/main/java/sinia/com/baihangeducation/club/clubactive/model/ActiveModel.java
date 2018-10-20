package sinia.com.baihangeducation.club.clubactive.model;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.interfaces.ObtainRankingDataListener;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.model.CoffersDetail;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * 创建日期：2018/5/22 on 11:28
 * 描述:
 * 作者:yuxd Administrator
 */
public class ActiveModel extends BasePresenter {


    public ActiveModel(Activity activity) {
        super(activity);
    }

    public void getCompanyUCenterInfo(int type, int page, ObtainActiveDataListener obtainRankingDataListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getActivityList", "club", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("type", type);//
        info.put("page", page);
        info.put("perpage", "10");

        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                ActiveListData info = bean.parseObject(ActiveListData.class);
                int maxpag = CommonUtil.getMaxPage(info.count, info.perpage);

                obtainRankingDataListener.onSuccess(info, maxpag);
//                view.getMessageSuccess(info , maxpag);
//                CoffersDetail coffersDetail = new CoffersDetail();
//
//                List<CoffersDetail.Coffers> ll = new ArrayList<>();
//                for (int i = 0; i < 10; i++) {
//                    CoffersDetail.Coffers coffers = new CoffersDetail.Coffers();
//                    coffers.add_time = "1111";
//                    coffers.title = "1111";
//                    coffers.total = "1111";
//                    coffers.type = "1111";
//                    ll.add(i, coffers);
//                }
//                coffersDetail.list = ll;
//                coffersDetail.count = 10;
//                System.out.println("后台数据onSuccess");
//                obtainRankingDataListener.onSuccess(coffersDetail, 10);

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
