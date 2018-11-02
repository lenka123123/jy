package sinia.com.baihangeducation.club.myclub.myactive;


import java.util.List;

import sinia.com.baihangeducation.club.clubactive.model.ActiveListData;
import sinia.com.baihangeducation.club.myclub.myclub.MyClubSchoolList;

/**
 * 创建日期：2018/5/26 on 16:21
 * 描述:
 * 作者:yuxd Administrator
 */
public interface GetMyActiveListener {

    void onSuccess(ActiveListData successMessage, int myIndex);

    void onError(String errorMessage);

}
