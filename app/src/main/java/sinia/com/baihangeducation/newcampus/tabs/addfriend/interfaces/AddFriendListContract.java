package sinia.com.baihangeducation.newcampus.tabs.addfriend.interfaces;


import java.util.List;

import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.SearchFriendListBean;

public class AddFriendListContract {

    public interface View {
        void showHotFunList(List<SearchFriendListBean> successMessage, int type);

        void upDateHotFunList(SearchFriendListBean successMessage);

        void showError(String errorMessage);
    }

    public interface Presenter {
        void getHotFunList(int type, int page, String s);
    }
}
