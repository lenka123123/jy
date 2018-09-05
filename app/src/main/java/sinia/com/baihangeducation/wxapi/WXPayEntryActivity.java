package sinia.com.baihangeducation.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.mcxtzhang.swipemenulib.customview.listdialog.CenterDialog;
import com.mcxtzhang.swipemenulib.customview.listdialog.ChangeISNOPicker;
import com.mcxtzhang.swipemenulib.utils.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.reconsitution.tabs.pay.PayActivity;

// sinia.com.baihangeducation.wxapi.WXPayEntryActivity
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {


        CenterDialog centerDialog = new CenterDialog(WXPayEntryActivity.this);
        centerDialog.setAlertOnClickListener(new ChangeISNOPicker.AlertOnClickListener() {
            @Override
            public void alertClick(String age) {
                if (age.startsWith("支付成功"))
                    AppConfig.PAYSUCCESS = true;
                WXPayEntryActivity.this.finish();

            }
        });
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            if (resp.errCode == 0) {
                //支付成功
                centerDialog.showDialog("支付成功!", R.drawable.payes_success);


            }

            if (resp.errCode == -1) {
                //支付失败
                centerDialog.showDialog("支付失败，请重试!", R.drawable.payes_fail);
            }

            if (resp.errCode == -2) {
                //取消支付
                centerDialog.showDialog("取消支付", R.drawable.payes_fail);
            }


        }
    }

}