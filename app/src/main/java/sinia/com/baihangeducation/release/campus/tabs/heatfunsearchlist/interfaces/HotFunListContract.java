package sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.interfaces;


import sinia.com.baihangeducation.release.campus.tabs.heatfunsearchlist.bean.HotFunListBean;

public class HotFunListContract {

    public interface View {
        void showHotFunList(HotFunListBean successMessage);

        void upDateHotFunList(HotFunListBean successMessage);

        void showError(String errorMessage);
    }

    public interface Presenter {
        void getHotFunList(int type, int page,String s);
    }
}
