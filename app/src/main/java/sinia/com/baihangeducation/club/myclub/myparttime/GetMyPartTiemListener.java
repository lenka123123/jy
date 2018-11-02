package sinia.com.baihangeducation.club.myclub.myparttime;


import com.mcxtzhang.swipemenulib.info.ClubPartTimeListInfo;
import com.mcxtzhang.swipemenulib.info.HomePartTimeListInfo;

import sinia.com.baihangeducation.club.clubactive.model.ActiveListData;

/**
 * 创建日期：2018/5/26 on 16:21
 * 描述:
 * 作者:yuxd Administrator
 */
public interface GetMyPartTiemListener {

    void onSuccess(ClubPartTimeListInfo successMessage, int myIndex);

    void onError(String errorMessage);

}
