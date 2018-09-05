package sinia.com.baihangeducation.newcampus.interfaces;

import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.CommentListBean;

public interface CommentListener {
    void getAddFirendSuccessListener(CommentListBean commentListBean);
    void getAddFirendFailListener();

}
