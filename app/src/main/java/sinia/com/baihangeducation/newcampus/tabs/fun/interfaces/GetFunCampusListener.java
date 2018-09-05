package sinia.com.baihangeducation.newcampus.tabs.fun.interfaces;

import sinia.com.baihangeducation.newcampus.info.FunInfo;

public interface GetFunCampusListener {

    void SuccessInfo(FunInfo funInfo,int maxpag);
    void ErrorInfo();
}
