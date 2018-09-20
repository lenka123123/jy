package sinia.com.baihangeducation.club.clubdetail.interfaces;

import java.util.List;

import sinia.com.baihangeducation.club.clubdetail.model.ClubDetailBean;

public class ClubDetailContract {

    public interface View {

        void showSearchSchoolList(ClubDetailBean  successMessage);

        void showError(String errorMessage);
        void showdropClub(String errorMessage);
        void showjoinClub(String errorMessage);


    }

    public interface Presenter {
        void getSearchSchoolList(String page, String perpage, String keyword);
    }
}
