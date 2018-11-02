package sinia.com.baihangeducation.club.applyclublist.interfaces;

import java.util.List;

import sinia.com.baihangeducation.club.applyclublist.model.ApplyClubListBean;
import sinia.com.baihangeducation.club.applyclublist.model.GetPersonList;

public class ApplyClubListContract {

    public interface View {

        void showApplyClubList(ApplyClubListBean successMessage, int maxpage);

        void showError(String errorMessage);

        void showApplySuccess(String errorMessage);



        void showGetPersonListSuccess(GetPersonList getPersonList, int max);

    }

    public interface Presenter {
        void getSearchSchoolList(String page, String perpage, String keyword);

        void acceptPerson(String club_id, String member_id, ApplySuccessListListener listListener);
    }
}
