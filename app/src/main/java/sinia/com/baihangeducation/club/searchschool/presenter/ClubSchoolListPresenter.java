package sinia.com.baihangeducation.club.searchschool.presenter;

import sinia.com.baihangeducation.club.searchschool.SearchListActivity;
import sinia.com.baihangeducation.club.searchschool.interfaces.SearchSchoolContract;
import sinia.com.baihangeducation.club.searchschool.interfaces.setSchoolListListener;
import sinia.com.baihangeducation.club.searchschool.model.ClubSchoolList;
import sinia.com.baihangeducation.club.searchschool.model.SearchSchoolModel;

public class ClubSchoolListPresenter implements SearchSchoolContract.Presenter {

    private SearchSchoolModel searchSchoolModel;
    private SearchSchoolContract.View view;

    public ClubSchoolListPresenter(SearchSchoolModel searchSchoolModel, SearchSchoolContract.View view) {
        this.searchSchoolModel = searchSchoolModel;
        this.view = view;
    }

    @Override
    public void getSearchSchoolList(String page, String perpage, String keyword) {
        searchSchoolModel.getClubListList(page, perpage, keyword, new setSchoolListListener() {
            @Override
            public void setSchookList(ClubSchoolList list, int maxpage) {
                view.showSearchSchoolList(list.list, maxpage);
            }

            @Override
            public void setSchookListFail(String meg) {
                view.showError(meg);
            }
        });
    }
}
