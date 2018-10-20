package sinia.com.baihangeducation.club.club.presenter;

import sinia.com.baihangeducation.club.club.interfaces.ClubHomeContract;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.club.interfaces.SetClubHomeListener;
import sinia.com.baihangeducation.club.club.model.ClubHomeInfo;
import sinia.com.baihangeducation.club.club.model.ClubHomeModel;

public class ClubHomePresenter implements ClubHomeContract.Presenter {

    private ClubHomeModel clubHomeModel;
    private ClubHomeContract.View clubFragment;

    public ClubHomePresenter() {

    }

    public ClubHomePresenter(ClubHomeModel clubHomeModel, ClubHomeContract.View clubFragment) {
        this.clubHomeModel = clubHomeModel;
        this.clubFragment = clubFragment;
    }


    @Override
    public void getClubHomeInfo() {
        clubHomeModel.getClubHomeInfo(new SetClubHomeListener() {
            @Override
            public void setClubHomeSuccess(ClubHomeInfo clubSchoolList) {
                clubFragment.showClubList(clubSchoolList);
                clubFragment.hideLoading();
            }

            @Override
            public void setClubHomeFail(String msg) {
                clubFragment.showError(msg);
                clubFragment.hideLoading();
            }
        });
    }

    public void getClubPermission(String club_id, GetRequestListener getRequestListener) {
        clubHomeModel.getClubPermission(club_id, new GetRequestListener() {
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

    // member_id 申请成员ID    ( 非必传（传此参数表示：管理员审核该成员通过） )
    public void applyClub(String club_id, String member_id, GetRequestListener getRequestListener) {
        clubHomeModel.applyClub(club_id, member_id, new GetRequestListener() {
            @Override
            public void setRequestSuccess(String msg) {
                getRequestListener.setRequestSuccess("");
            }

            @Override
            public void setRequestFail() {
                getRequestListener.setRequestFail();
            }
        });
    }

    public void setSelectSchool(){
        clubHomeModel.setSelectSchool();
    }


}
