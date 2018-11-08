package sinia.com.baihangeducation.club.club.interfaces;


import sinia.com.baihangeducation.find.info.bean.SearchRecommend;

public interface GetSearchRequestListener {
    void setRequestSuccess(SearchRecommend  msg);
    void setRequestFail();

}
