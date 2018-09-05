package sinia.com.baihangeducation.newcampus.tabs.fans.interfaces;


import sinia.com.baihangeducation.newcampus.tabs.fans.bean.SearchFriendListBean;

public class FansContract {

    public interface View {
        void showHotFunList(SearchFriendListBean successMessage);

        void upDateHotFunList(SearchFriendListBean successMessage);

        void showError(String errorMessage);
    }

    public interface Presenter {
        void getHotFunList(String other_id, String type, String page, String perpage);
    }
}
