package sinia.com.baihangeducation.club.searchschool.interfaces;

import java.util.List;

import sinia.com.baihangeducation.club.searchschool.model.ClubSchoolList;

public class SearchSchoolContract {

    public interface View {

        void showSearchSchoolList(List<ClubSchoolList.School> successMessage, int maxpage);

        void showError(String errorMessage);
    }

    public interface Presenter {
        void getSearchSchoolList(String page, String perpage, String keyword);


    }
}
