package sinia.com.baihangeducation.club.myparttimeapplylist;



/**
 * 创建日期：2018/5/26 on 16:21
 * 描述:
 * 作者:yuxd Administrator
 */
public interface GetApplyPersonListener<T> {

    void onSuccess(T successMessage, int myIndex);

    void onError(String errorMessage);

}
