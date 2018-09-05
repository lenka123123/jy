package sinia.com.baihangeducation.reconsitution.tabs.pay;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.framwork.baseapp.BaseAppConfig;
import com.mcxtzhang.swipemenulib.customview.listdialog.CenterDialog;
import com.mcxtzhang.swipemenulib.customview.listdialog.ChangeISNOPicker;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.wxapi.WXPayEntryActivity;

public class PayActivity extends BaseActivity implements IWXAPIEventHandler {

    private String orderInfo = "";   // 订单信息
    private String aliPayorderInfo = "";   // 订单信息
    private String type;
    private String title;
    private String price;
    private LinearLayout wxpay_linearlayout;
    private LinearLayout alipay_linearlayout;
    private ImageView alipay_checked;
    private ImageView wxpay_checked;
    private String raiders_id;
    private IWXAPI api;
    private PayPresenter payPresenter;

    public int initLayoutResID() {
        return R.layout.pay;
    }

    @Override
    protected void initView() {
        AppConfig.PAYSUCCESS = false;
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        title = intent.getStringExtra("title");
        price = intent.getStringExtra("price");
        raiders_id = intent.getStringExtra("raiders_id");

        api = WXAPIFactory.createWXAPI(this, BaseAppConfig.WX_APP_ID);
        api.handleIntent(getIntent(), this);

        TextView titleTextView = findViewById(R.id.title);
        TextView typeTextView = findViewById(R.id.type);
        TextView priceTextView = findViewById(R.id.price);
        alipay_checked = findViewById(R.id.alipay_checked);
        wxpay_checked = findViewById(R.id.wxpay_checked);
        wxpay_linearlayout = findViewById(R.id.wxpay_linearlayout);
        alipay_linearlayout = findViewById(R.id.alipay_linearlayout);


        titleTextView.setText(title);
        typeTextView.setText(type);
        priceTextView.setText(price);

        mCommonTitle.setBackgroundColor(Color.WHITE);
        mCommonTitle.setCenterText("支付页面");
        mCommonTitle.getRightTxt().setTextSize(16);
        mCommonTitle.getRightTxt().setTextColor(Color.BLACK);
        alipay_linearlayout.setOnClickListener(this);

        wxpay_linearlayout.setOnClickListener(this);
        findViewById(R.id.goto_pay).setOnClickListener(this);

        payPresenter = new PayPresenter(this, this);
        System.out.println("raiders_id===========" + raiders_id);

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (AppConfig.PAYSUCCESS)
            PayActivity.this.finish();
    }

    private int payType = 0;
    private boolean aliPay = false;
    private boolean wxPay = false;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.alipay_linearlayout:
                aliPay = true;
                wxPay = false;
                wxpay_checked.setImageResource(wxPay ? R.drawable.pay_select : R.drawable.pay_cancel);
                alipay_checked.setImageResource(aliPay ? R.drawable.pay_select : R.drawable.pay_cancel);
                break;
            case R.id.wxpay_linearlayout:
                aliPay = false;
                wxPay = true;
                alipay_checked.setImageResource(aliPay ? R.drawable.pay_select : R.drawable.pay_cancel);
                wxpay_checked.setImageResource(wxPay ? R.drawable.pay_select : R.drawable.pay_cancel);
                break;

            case R.id.goto_pay:
                if (wxPay == false && aliPay == false) {
                    Toast.makeText(PayActivity.this, "请选择支付方式", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (aliPay) {
                    payPresenter.getAliPayInfo("2", raiders_id, "ali");
                }

                if (wxPay) {
                    payPresenter.updateCompanyInfo("2", raiders_id, "wx");
                }

                break;
        }
    }

    private void wXPay() {
        try {
            JSONObject jsonObject = new JSONObject(orderInfo);
            PayReq req = new PayReq();
            req.appId = (String) jsonObject.get("appid");
            req.partnerId = (String) jsonObject.get("partnerid");
            //预支付订单
            req.prepayId = (String) jsonObject.get("prepayid");
            req.nonceStr = (String) jsonObject.get("noncestr");
            req.timeStamp = (String) jsonObject.get("timestamp");
            req.packageValue = (String) jsonObject.get("package");
            req.sign = (String) jsonObject.get("sign");
            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
            //3.调用微信支付sdk支付方法
//            Toast.makeText(PayActivity.this, "正常调起支付" + req.sign, Toast.LENGTH_SHORT).show();
            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
            api.sendReq(req);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void aliPay() {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2(aliPayorderInfo, true);
                for (String s : result.keySet()) {
                    System.out.println("alipaykey:==========" + s);
                    System.out.println("alipayvalues:=======" + result.get(s));
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alipayManage(result);
                    }
                });
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * alipaykey:==========resultStatus
     * alipayvalues:=======6001
     * alipaykey:==========result
     * alipayvalues:=======
     * alipaykey:==========memo
     * alipayvalues:=======操作已经取消。
     * <p>
     * 4000            支付失败
     * <p>
     * alipaykey:==========resultStatus
     * alipayvalues:=======9000
     * alipaykey:==========result
     * alipayvalues:======={"alipay_trade_app_pay_response":
     * {"code":"10000","msg":"Success","app_id":"2017050807168060",
     * "auth_app_id":"2017050807168060","charset":"UTF-8",
     * "timestamp":"2018-08-30 14:26:20","total_amount":"0.10",
     * "trade_no":"2018083021001004840515214055","seller_id":"2088421458159441",
     * "out_trade_no":"08301422562287"},
     * "sign":"cUjJkU1SLdgpcjmAUJ5tN0Xls7KASHXPAuan/gXQcicSFOj2nbnA9JRF04SPgZA1yb/AtAM8NR6xKZcctctIcg42oOVejYU6McxIrYlxLAdZ8BbBwsVYmjEK4T/qTrkfE09zdTpdlvoMj1lNQJSO6kDb4waHNx6hQ7106OxB/LEYL1FgTQoL2/PsHQe9udbgDLYfmm1Lsdri5kCh0tEUraKXRsh1+EigkXvw/C9NMOx2gJ/IHpIjMpSFz5gvCij1KaIYCMblp4Sm7RpNLisjutIl/i7wX7+w/f+F6DhCOBEeWTTyOCgL0Fi4Lqf6WNFxBAViQIX3VXbIbu5keNQOQw==","sign_type":"RSA2"}
     * alipaykey:==========memo
     * alipayvalues:=======
     * {
     * "code": "10000",
     * "msg": "Success",
     * "app_id": "2017050807168060",
     * "auth_app_id": "2017050807168060",
     * "charset": "UTF-8",
     * "timestamp": "2018-08-30 14:26:20",
     * "total_amount": "0.10",
     * "trade_no": "2018083021001004840515214055",
     * "seller_id": "2088421458159441",
     * "out_trade_no": "08301422562287"
     * },
     * "sign": "cUjJkU1SLdgpcjmAUJ5tN0Xls7KASHXPAuan/gXQcicSFOj2nbnA9JRF04SPgZA1yb/AtAM8NR6xKZcctctIcg42oOVejYU6McxIrYlxLAdZ8BbBwsVYmjEK4T/qTrkfE09zdTpdlvoMj1lNQJSO6kDb4waHNx6hQ7106OxB/LEYL1FgTQoL2/PsHQe9udbgDLYfmm1Lsdri5kCh0tEUraKXRsh1+EigkXvw/C9NMOx2gJ/IHpIjMpSFz5gvCij1KaIYCMblp4Sm7RpNLisjutIl/i7wX7+w/f+F6DhCOBEeWTTyOCgL0Fi4Lqf6WNFxBAViQIX3VXbIbu5keNQOQw==", "sign_type": "RSA2"
     * }
     */


    private void alipayManage(Map<String, String> result) {
        CenterDialog centerDialog = new CenterDialog(PayActivity.this);
        centerDialog.setAlertOnClickListener(new ChangeISNOPicker.AlertOnClickListener() {
            @Override
            public void alertClick(String age) {
                if (age.startsWith("支付成功")) {
                    PayActivity.this.finish();
                }

            }
        });
        if (result.containsKey("resultStatus")) {
            if (result.get("resultStatus").equals("9000")) {
                centerDialog.showDialog("支付成功！", R.drawable.payes_success);

            }

            if (result.get("resultStatus").equals("4000")) {
                centerDialog.showDialog("支付失败，请重试！", R.drawable.payes_fail);
            }

            if (result.get("resultStatus").equals("6001")) {
                centerDialog.showDialog("取消支付", R.drawable.payes_fail);
            }
        }

    }

    @Override
    protected void initData() {

    }


    public void setAliPaySuccessInfo(String link) {
        aliPayorderInfo = link;
        aliPay();
    }


    public void setSuccessInfo(String link) {
        orderInfo = link;
        wXPay();
    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

    }
}
