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
import com.google.gson.JsonObject;
import com.mcxtzhang.swipemenulib.customview.listcustomlist.CustomDialogPicker;
import com.mcxtzhang.swipemenulib.customview.listcustomlist.DiscountDetail;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

public class PayActivity extends BaseActivity implements IWXAPIEventHandler {

    private JSONObject orderInfo;   // 订单信息
    private String aliPayorderInfo = "";   // 订单信息
    private String type;
    private String title;
    private String price;
    private LinearLayout wxpay_linearlayout;
    private LinearLayout alipay_linearlayout;
    private LinearLayout discound_linearlayout;
    private TextView count_pay;
    private TextView discount_use_tv;
    private ImageView alipay_checked;
    private ImageView wxpay_checked;
    private String raiders_id;
    private IWXAPI api;
    private PayPresenter payPresenter;
    private String use_type = "2";  //使用范围 考卷
    private CustomDialogPicker changeISNOPicker;
    private String coupon_id = "";
    private CenterDialog centerDialog;
    private int nextTimePosition = 0;

    public int initLayoutResID() {
        return R.layout.pay;
    }

    @Override
    protected void initView() {
        nextTimePosition = 0;
        AppConfig.PAYSUCCESS = false;
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        title = intent.getStringExtra("title");
        price = intent.getStringExtra("price");
        raiders_id = intent.getStringExtra("raiders_id");

        if (price.endsWith("元")) price = price.split("元")[0];

        api = WXAPIFactory.createWXAPI(this, BaseAppConfig.WX_APP_ID);
        api.handleIntent(getIntent(), this);

        TextView titleTextView = findViewById(R.id.title);
        TextView typeTextView = findViewById(R.id.type);
        TextView priceTextView = findViewById(R.id.price);
        alipay_checked = findViewById(R.id.alipay_checked);
        wxpay_checked = findViewById(R.id.wxpay_checked);
        wxpay_linearlayout = findViewById(R.id.wxpay_linearlayout);
        alipay_linearlayout = findViewById(R.id.alipay_linearlayout);
        discound_linearlayout = findViewById(R.id.discound_linearlayout);
        count_pay = findViewById(R.id.count_pay);
        discount_use_tv = findViewById(R.id.discount_use_tv);


        titleTextView.setText(title);
        typeTextView.setText(type);
        priceTextView.setText(price);

        mCommonTitle.setBackgroundColor(Color.WHITE);
        mCommonTitle.setCenterText("支付页面");
        mCommonTitle.getRightTxt().setTextSize(16);
        mCommonTitle.getRightTxt().setTextColor(Color.BLACK);
        alipay_linearlayout.setOnClickListener(this);
        discound_linearlayout.setOnClickListener(this);

        wxpay_linearlayout.setOnClickListener(this);
        findViewById(R.id.goto_pay).setOnClickListener(this);

        payPresenter = new PayPresenter(this, this);
        centerDialog = new CenterDialog(PayActivity.this);

        centerDialog.setAlertOnClickListener(new ChangeISNOPicker.AlertOnClickListener() {
            @Override
            public void alertClick(String age) {
                if (age.startsWith("支付成功")) {
                    PayActivity.this.finish();
                }

            }
        });

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

            case R.id.discound_linearlayout:
                if (mInfo != null && mInfo.list != null && mInfo.list.size() > 0) {
                    changeISNOPicker.showAlertDialog(mInfo.list, "选择优惠券", nextTimePosition);
                    changeISNOPicker.setAlertOnClickListener(new CustomDialogPicker.AlertOnClickListener() {
                        @Override
                        public void alertClick(int position) {
                            nextTimePosition = position;
                            discount_use_tv.setText("-￥" + mInfo.list.get(position).coupon_price);
                            coupon_id = mInfo.list.get(position).coupon_id;
                            if (Double.valueOf(price) >= Double.valueOf(mInfo.list.get(position).coupon_price)) {
                                Double mm = (Double.valueOf(price) - Double.valueOf(mInfo.list.get(position).coupon_price));
                                BigDecimal bd = new BigDecimal(mm);
                                bd = bd.setScale(2, RoundingMode.HALF_UP);
                                count_pay.setText("￥" + bd.toString());
                            } else {
                                count_pay.setText("￥" + price);
                            }

                            changeISNOPicker.closeAlertDialog();

                        }
                    });
                }


                break;
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
                    payPresenter.getAliPayInfo("2", raiders_id, "ali", coupon_id);
                }

                if (wxPay) {
                    payPresenter.updateCompanyInfo("2", raiders_id, "wx", coupon_id);
                }

                break;
        }
    }

    private void wXPay() {
        try {
//            JSONObject jsonObject = new JSONObject(orderInfo);
            PayReq req = new PayReq();
            req.appId = (String) orderInfo.get("appid");
            req.partnerId = (String) orderInfo.get("partnerid");
            //预支付订单
            req.prepayId = (String) orderInfo.get("prepayid");
            req.nonceStr = (String) orderInfo.get("noncestr");
            req.timeStamp = (String) orderInfo.get("timestamp");
            req.packageValue = (String) orderInfo.get("package");
            req.sign = (String) orderInfo.get("sign");
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

    private void alipayManage(Map<String, String> result) {
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
        // 类型    ( 1：已用；2：未用；3过期 )  type
        // 使用范围    ( 非必传；1：培训；2：考卷；不传默认全部 )  use_type


        payPresenter.getDiscountInfo("2", use_type, price);
        changeISNOPicker = new CustomDialogPicker(PayActivity.this);

    }

    private DiscountDetail mInfo = null;

    public void setDiscountSuccess(DiscountDetail mInfo) {
        this.mInfo = mInfo;

        count_pay.setText("￥" + price);
        if (mInfo != null && mInfo.list != null && mInfo.list.size() > 0) {
            discount_use_tv.setText("-￥" + mInfo.list.get(0).coupon_price);
            coupon_id = mInfo.list.get(0).coupon_id;
            Double dd = (Double.valueOf(price));
            Double cc = Double.valueOf(mInfo.list.get(0).coupon_price);
            Double mm = dd - cc;
            BigDecimal bd = new BigDecimal(mm);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            count_pay.setText("￥" + bd.toString() );
        }
    }


    public void setAliPaySuccessInfo(JSONObject jsonObject, String jump_sdk) {

        if (jump_sdk.equals("N")) {
            centerDialog.showDialog("支付成功！", R.drawable.payes_success);
        } else {
            try {
                String link = (String) jsonObject.get("link");
                aliPayorderInfo = link;
                aliPay();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void setSuccessInfo(JSONObject link, String jump_sdk) {
        if (jump_sdk.equals("N")) {
            centerDialog.showDialog("支付成功！", R.drawable.payes_success);
        } else {
            orderInfo = link;
            wXPay();
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

    }
}
