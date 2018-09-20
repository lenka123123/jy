package sinia.com.baihangeducation.club.club.presenter;

import sinia.com.baihangeducation.club.club.interfaces.ClubHomeContract;
import sinia.com.baihangeducation.club.club.interfaces.SetClubHomeListener;
import sinia.com.baihangeducation.club.club.model.ClubHomeInfo;
import sinia.com.baihangeducation.club.club.model.ClubHomeModel;

public class ClubHomePresenter implements ClubHomeContract.Presenter {

    private ClubHomeModel clubHomeModel;
    private ClubHomeContract.View clubFragment;

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


}
