package sinia.com.baihangeducation.club.applyclublist.presenter;

import sinia.com.baihangeducation.club.applyclublist.interfaces.ApplyClubListContract;
import sinia.com.baihangeducation.club.applyclublist.interfaces.ApplyClubListListener;
import sinia.com.baihangeducation.club.applyclublist.interfaces.ApplySuccessListListener;
import sinia.com.baihangeducation.club.applyclublist.interfaces.GetPersonListener;
import sinia.com.baihangeducation.club.applyclublist.model.ApplyClubListBean;
import sinia.com.baihangeducation.club.applyclublist.model.ApplyClubListModel;
import sinia.com.baihangeducation.club.applyclublist.model.GetPersonList;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.clubdetail.interfaces.JoinClubDetailListener;

public class ApplyClubListPresenter implements ApplyClubListContract.Presenter {

    private ApplyClubListModel searchSchoolModel;
    private ApplyClubListContract.View view;

    public ApplyClubListPresenter(ApplyClubListModel searchSchoolModel, ApplyClubListContract.View view) {
        this.searchSchoolModel = searchSchoolModel;
        this.view = view;
    }

    @Override
    public void getSearchSchoolList(String page, String perpage, String keyword) {
        searchSchoolModel.getClubListList(page, perpage, keyword, new ApplyClubListListener() {
            @Override
            public void setSchookList(ApplyClubListBean list, int maxpage) {
                view.showApplyClubList(list, maxpage);
            }

            @Override
            public void setSchookListFail(String meg) {
                view.showError(meg);
            }
        });
    }

    @Override
    public void acceptPerson(String club_id, String member_id, ApplySuccessListListener listListener) {
        searchSchoolModel.joinClub(club_id, member_id, new JoinClubDetailListener() {

            @Override
            public void setSchookList(String successInfo) {
                listListener.showApplySuccess();
            }

            @Override
            public void setSchookListFail(String meg) {
                listListener.showApplyFail();
            }
        });
    }


    public void getPersonList(String club_id, String page, String perpage) {
        searchSchoolModel.getPersonList(club_id, page, perpage, new GetPersonListener() {
            @Override
            public void showGetPersonSuccess(GetPersonList getPersonList, int max) {

                view.showGetPersonListSuccess(getPersonList, max);
            }

            @Override
            public void showGetPersonFail(String msg) {

            }
        });
    }
    public void dropCrew(String member_id , GetRequestListener listener ) {
        searchSchoolModel.dropCrew(member_id, new GetRequestListener() {
            @Override
            public void setRequestSuccess(String msg) {
                listener.setRequestSuccess(msg);
            }

            @Override
            public void setRequestFail() {
              listener.setRequestFail();
            }
        });
    }

    public void ignore(String club_id) {
        searchSchoolModel.ignoreClub(club_id);
    }

}
