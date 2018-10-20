package sinia.com.baihangeducation.club.clubactive.model;


/**
 * 创建日期：2018/5/26 on 16:21
 * 描述:
 * 作者:yuxd Administrator
 */
public interface ObtainActiveDataListener {

    void onSuccess(ActiveListData successMessage, int myIndex);

    void onError(String errorMessage);

    void onUpdate(int total, int current);

}
