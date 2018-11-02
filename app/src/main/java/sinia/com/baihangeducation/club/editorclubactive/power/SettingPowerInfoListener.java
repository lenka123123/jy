package sinia.com.baihangeducation.club.editorclubactive.power;


import java.util.List;

import sinia.com.baihangeducation.club.editorclubactive.model.ActiveInfoData;

/**
 * 创建日期：2018/5/26 on 16:21
 * 描述:
 * 作者:yuxd Administrator
 */
public interface SettingPowerInfoListener {

    void onSuccess( List<SettingPowerData> successMessage);

    void onError(String errorMessage);


}
