package sinia.com.baihangeducation.club.clubschoollist.interfaces;

import java.util.List;

import sinia.com.baihangeducation.club.clubschoollist.model.ClubSchoolList;

public class ClubSchoolListContract {

    public interface View {

        void showSearchSchoolList(List<ClubSchoolList.School> successMessage, int maxpage);

        void showError(String errorMessage);
    }

    public interface Presenter {
        void getSearchSchoolList(String page, String perpage, String keyword);
    }
}
