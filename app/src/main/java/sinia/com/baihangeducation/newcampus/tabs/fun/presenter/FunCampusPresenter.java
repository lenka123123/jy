package sinia.com.baihangeducation.newcampus.tabs.fun.presenter;

import sinia.com.baihangeducation.newcampus.info.FunInfo;
import sinia.com.baihangeducation.newcampus.tabs.fun.FunCampusFragment;
import sinia.com.baihangeducation.newcampus.tabs.fun.interfaces.GetFunCampusListener;
import sinia.com.baihangeducation.newcampus.tabs.fun.model.FunCampusModel;

public class FunCampusPresenter {

    private FunCampusModel funCampusModel;
    private  FunCampusFragment funCampusFragment;

    public FunCampusPresenter(FunCampusModel funCampusModel, FunCampusFragment funCampusFragment) {
        this.funCampusModel = funCampusModel;
        this.funCampusFragment = funCampusFragment;

    }

    public void getFunCampusList() {
        funCampusFragment.showLoading();
        funCampusModel.getFunInfo(funCampusFragment.getFunPage(),funCampusFragment.getFunPerpage(), new GetFunCampusListener() {


            @Override
            public void SuccessInfo(FunInfo funInfo, int maxpag) {
                funCampusFragment.hideLoading();
                funCampusFragment.getFunInfoSuccess(funInfo,maxpag);
            }

            @Override
            public void ErrorInfo() {
                funCampusFragment.hideLoading();
            }
        });
    }


}
