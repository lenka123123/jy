package sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersmain;

import sinia.com.baihangeducation.reconsitution.tabs.coffers.CoffersDataBean;

public interface ObtainMyCoffersDataListener {
    void onSuccess(CoffersDataBean successMessage );

    void onError(String errorMessage);
}
