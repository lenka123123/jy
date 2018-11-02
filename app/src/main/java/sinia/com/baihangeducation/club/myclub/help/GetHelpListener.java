package sinia.com.baihangeducation.club.myclub.help;


import com.mcxtzhang.swipemenulib.info.ClubPartTimeListInfo;

/**
 * 创建日期：2018/5/26 on 16:21
 * 描述:
 * 作者:yuxd Administrator
 */
public interface GetHelpListener {

    void onSuccess(MyHelplList successMessage, int myIndex);

    void onError(String errorMessage);

}
