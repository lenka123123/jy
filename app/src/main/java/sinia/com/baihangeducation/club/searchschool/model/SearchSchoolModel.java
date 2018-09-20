package sinia.com.baihangeducation.club.searchschool.model;

import android.app.Activity;

import com.example.framwork.base.BasePresenter;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.club.searchschool.SearchListActivity;
import sinia.com.baihangeducation.club.searchschool.interfaces.SearchSchoolContract;
import sinia.com.baihangeducation.club.searchschool.interfaces.setSchoolListListener;
import sinia.com.baihangeducation.newcampus.interfaces.AddFirendListener;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

/**
 * 创建日期：2018/5/22 on 11:28
 * 描述:
 * 作者:yuxd Administrator
 */
public class SearchSchoolModel extends BasePresenter {

    private Activity activity;

    public SearchSchoolModel(Activity activity) {
        super(activity);
        this.activity = activity;

    }

    public void getClubListList(String page, String perpage, String keyword, final setSchoolListListener setSchoolListListener) {
        HashMap info = BaseRequestInfo.getInstance().getRequestInfo(activity, "getClubList", "club", true);

        info.put("keyword", keyword);
        info.put("page", page);
        info.put("perpage", perpage);
        post(info, new OnRequestListener() {
            @Override
            public void requestSuccess(BaseResponseBean bean) {
                ClubSchoolList clubSchoolList = bean.parseObject(ClubSchoolList.class);
                int maxpag = CommonUtil.getMaxPage(clubSchoolList.count, clubSchoolList.perpage);

                setSchoolListListener.setSchookList(clubSchoolList, maxpag);

            }

            @Override
            public void requestFailed(String error) {
                Toast.getInstance().showErrorToast(activity, error);
                setSchoolListListener.setSchookListFail(error);

            }

            @Override
            public void requestFinish() {

            }
        });

    }
}
