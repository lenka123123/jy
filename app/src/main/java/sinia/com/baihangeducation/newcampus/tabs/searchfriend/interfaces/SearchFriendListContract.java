package sinia.com.baihangeducation.newcampus.tabs.searchfriend.interfaces;


import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.SearchFriendListBean;

public class SearchFriendListContract {

    public interface View {
        void showHotFunList(SearchFriendListBean successMessage);

        void upDateHotFunList(SearchFriendListBean successMessage);

        void showError(String errorMessage);
    }

    public interface Presenter {
        void getHotFunList(int type, int page, String s);
    }
}
