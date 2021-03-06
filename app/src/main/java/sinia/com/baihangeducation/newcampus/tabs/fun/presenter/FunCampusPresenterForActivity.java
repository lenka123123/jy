package sinia.com.baihangeducation.newcampus.tabs.fun.presenter;

import sinia.com.baihangeducation.newcampus.info.FunInfo;
import sinia.com.baihangeducation.newcampus.tabs.fun.FunCampusForActivity;
import sinia.com.baihangeducation.newcampus.tabs.fun.FunCampusFragment;
import sinia.com.baihangeducation.newcampus.tabs.fun.interfaces.GetFunCampusListener;
import sinia.com.baihangeducation.newcampus.tabs.fun.model.FunCampusModel;

public class FunCampusPresenterForActivity {

    private FunCampusModel funCampusModel;
    private  FunCampusForActivity funCampusFragment;

    public FunCampusPresenterForActivity(FunCampusModel funCampusModel, FunCampusForActivity funCampusFragment) {
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
