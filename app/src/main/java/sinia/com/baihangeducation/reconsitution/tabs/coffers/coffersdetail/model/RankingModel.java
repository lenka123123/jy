package sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.model;

import android.app.Activity;
import android.util.Log;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;
import com.mcxtzhang.swipemenulib.info.bean.HomeAndFindHelpEachOtherDetailCommentInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.interfaces.ObtainRankingDataListener;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

import static android.content.ContentValues.TAG;

/**
 * 创建日期：2018/5/22 on 11:28
 * 描述:
 * 作者:yuxd Administrator
 */
public class RankingModel extends BasePresenter {


    public RankingModel(Activity activity) {
        super(activity);
    }

    public void getCompanyUCenterInfo(int type, int page, ObtainRankingDataListener obtainRankingDataListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getMyIncome", "ucenter", true);
        info.put("user_id", AppConfig.USERID);
        info.put("token", AppConfig.TOKEN);
        info.put("type", type);// 1收入
        info.put("page", page);
        info.put("perpage", "10");

        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                CoffersDetail info = bean.parseObject(CoffersDetail.class);
                int maxpag = CommonUtil.getMaxPage(info.count, info.perpage);
                obtainRankingDataListener.onSuccess(info, 10);
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
