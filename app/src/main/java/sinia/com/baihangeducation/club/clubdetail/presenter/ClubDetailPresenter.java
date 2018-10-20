package sinia.com.baihangeducation.club.clubdetail.presenter;

import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;

import java.util.HashMap;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.clubdetail.interfaces.ClubDetailContract;
import sinia.com.baihangeducation.club.clubdetail.interfaces.ClubDetailListener;
import sinia.com.baihangeducation.club.clubdetail.interfaces.JoinClubDetailListener;
import sinia.com.baihangeducation.club.clubdetail.model.ClubDetailBean;
import sinia.com.baihangeducation.club.clubdetail.model.ClubDetailModel;
import sinia.com.baihangeducation.supplement.tool.BaseRequestInfo;

public class ClubDetailPresenter implements ClubDetailContract.Presenter {

    private ClubDetailModel searchSchoolModel;
    private ClubDetailContract.View view;

    public ClubDetailPresenter(ClubDetailModel searchSchoolModel, ClubDetailContract.View view) {
        this.searchSchoolModel = searchSchoolModel;
        this.view = view;
    }

    @Override
    public void getSearchSchoolList(String page, String perpage, String club_id) {
        searchSchoolModel.getClubListList(page, perpage, club_id, new ClubDetailListener() {
            @Override
            public void setSchookList(ClubDetailBean list) {
                view.showSearchSchoolList(list);
            }

            @Override
            public void setSchookListFail(String meg) {
                view.showError(meg);
            }
        });
    }

    public void joinClub(String club_id) {
        searchSchoolModel.joinClub(club_id, new JoinClubDetailListener() {

            @Override
            public void setSchookList(String successInfo) {
                view.showjoinClub(successInfo);
            }

            @Override
            public void setSchookListFail(String meg) {

            }
        });
    }

    public void dropClub(String club_id) {
        searchSchoolModel.dropClub(club_id, new JoinClubDetailListener() {

            @Override
            public void setSchookList(String successInfo) {
                view.showdropClub(successInfo);
            }

            @Override
            public void setSchookListFail(String meg) {

            }
        });
    }

    public void getClubPermission(String club_id, GetRequestListener getRequestListener) {
        searchSchoolModel.getClubPermission(club_id, new GetRequestListener() {

            @Override
            public void setRequestSuccess(String msg) {
                getRequestListener.setRequestSuccess(msg);
            }

            @Override
            public void setRequestFail() {
                getRequestListener.setRequestFail();
            }

        });
    }


}
