package sinia.com.baihangeducation.club.myclub.myclub;


import com.mcxtzhang.swipemenulib.info.ClubPartTimeListInfo;

import java.util.List;

import sinia.com.baihangeducation.club.clubschoollist.model.ClubSchoolList;

/**
 * 创建日期：2018/5/26 on 16:21
 * 描述:
 * 作者:yuxd Administrator
 */
public interface GetMyClubListener {

    void onSuccess(List<MyClubSchoolList.School> successMessage, int myIndex);

    void onError(String errorMessage);

}
