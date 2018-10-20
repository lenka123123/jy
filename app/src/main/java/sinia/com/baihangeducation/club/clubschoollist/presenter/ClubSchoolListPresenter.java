package sinia.com.baihangeducation.club.clubschoollist.presenter;

import sinia.com.baihangeducation.club.clubschoollist.interfaces.ClubSchoolListContract;
import sinia.com.baihangeducation.club.clubschoollist.interfaces.setSchoolListListener;
import sinia.com.baihangeducation.club.clubschoollist.model.ClubSchoolList;
import sinia.com.baihangeducation.club.clubschoollist.model.ClubSchoolListModel;

public class ClubSchoolListPresenter implements ClubSchoolListContract.Presenter {

    private ClubSchoolListModel searchSchoolModel;
    private ClubSchoolListContract.View view;

    public ClubSchoolListPresenter(ClubSchoolListModel searchSchoolModel, ClubSchoolListContract.View view) {
        this.searchSchoolModel = searchSchoolModel;
        this.view = view;
    }

    @Override
    public void getSearchSchoolList(String page, String perpage, String keyword) {
        System.out.println("===========" + page + "keywordkeyword" + perpage + "");
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
