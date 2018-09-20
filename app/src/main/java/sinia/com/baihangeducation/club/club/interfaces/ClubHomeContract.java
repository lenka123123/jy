package sinia.com.baihangeducation.club.club.interfaces;

import java.util.List;

import sinia.com.baihangeducation.club.club.model.ClubHomeInfo;

public class ClubHomeContract {

    public interface View {

        void showClubList(ClubHomeInfo clubSchoolList);

        void showError(String errorMessage);

        void showLoading();
        void hideLoading();
    }

    public interface Presenter {
        void getClubHomeInfo();
    }
}
