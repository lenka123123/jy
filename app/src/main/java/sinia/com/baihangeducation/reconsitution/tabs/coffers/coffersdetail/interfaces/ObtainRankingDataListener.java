package sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.interfaces;


import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.model.CoffersDetail;

/**
 * 创建日期：2018/5/26 on 16:21
 * 描述:
 * 作者:yuxd Administrator
 */
public interface ObtainRankingDataListener {

    void onSuccess(CoffersDetail successMessage, int myIndex);

    void onError(String errorMessage);

    void onUpdate(int total, int current);

}
