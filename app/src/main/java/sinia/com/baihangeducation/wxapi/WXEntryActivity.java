package sinia.com.baihangeducation.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mcxtzhang.swipemenulib.base.Config;
import com.mcxtzhang.swipemenulib.utils.Constants;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

import org.json.JSONException;
import org.json.JSONObject;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.mine.activity.LoginActivity;
import sinia.com.baihangeducation.mine.presenter.ThirdLoginPresenter;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.OkHttpUtils;
import sinia.com.baihangeducation.supplement.tool.WxShareAndLoginUtils;
import sinia.com.baihangeducation.supplement.weixin.WXConstants;
// https://blog.csdn.net/Suma_sun/article/details/50752528?locationNum=3

//   WXCallbackActivity
public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {
    public int WX_LOGIN = 1;
    private IWXAPI iwxapi;

    private SendAuth.Resp resp;

    private int onNewIntent = 0;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        onNewIntent = 1;
    }

    @Override
    public void finish() {
        if (onNewIntent == 1) {

        } else
            super.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        iwxapi = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
        //接收到分享以及登录的intent传递handleIntent方法，处理结果
        iwxapi.handleIntent(getIntent(), this);


    }


    @Override
    public void onReq(BaseReq baseReq) {
    }


    //请求回调结果处理
    @Override
    public void onResp(BaseResp baseResp) {
        //微信登录为getType为1，分享为0
        if (baseResp.getType() == WX_LOGIN) {
            //登录回调
//            System.out.println("------------登陆回调------------");
            resp = (SendAuth.Resp) baseResp;
//            System.out.println("------------登陆回调的结果------------：" +  new Gson().toJson(resp));
            // 通过code获取授权口令access_token


            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    requestOpenId(String.valueOf(resp.code));
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                    Toast.makeText(WXEntryActivity.this, "用户拒绝授权", Toast.LENGTH_LONG).show();
                    System.out.println();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                    Toast.makeText(WXEntryActivity.this, "用户取消登录", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }

//        else {
//            //分享成功回调
//            System.out.println("------------分享回调------------");
//            switch (baseResp.errCode) {
//                case BaseResp.ErrCode.ERR_OK:
//                    //分享成功
//                    Toast.makeText(WXEntryActivity.this, "分享成功", Toast.LENGTH_LONG).show();
//                    break;
//                case BaseResp.ErrCode.ERR_USER_CANCEL:
//                    //分享取消
//                    Toast.makeText(WXEntryActivity.this, "分享取消", Toast.LENGTH_LONG).show();
//                    break;
//                case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                    //分享拒绝
//                    Toast.makeText(WXEntryActivity.this, "分享拒绝", Toast.LENGTH_LONG).show();
//                    break;
//            }
//        }
    }


    /**
     * 开辟子线程
     */

    private void requestOpenId(String code) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urlstr = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Constants.APP_ID + "&secret=" + Constants.APP_SECRET + "&code=" + code + "&grant_type=authorization_code";

                //获取 网上 数据
                String httpUrl = WXConstants.httpUrl(urlstr);
                JSONObject jsObj;
                try {

                    jsObj = new JSONObject(httpUrl);
                    System.out.println("拿到数据===" + jsObj);
//    拿到数据==={"access_token":"15_vDWnfrHDCAmo7aYBjGhXmtDfRxcRwgoaCWPQ8j-D9rwPH40JnzxkafS-SlxE-Vaq8uO_ngd8EdPE5MTDu2lmUCdo15MiXIjkZv3GW1HJp60",
// "expires_in":7200,"refresh_token":"15_g6NHQSxkZhQYRADVAfpZypFs5uPkH_FpxnmrKCuESBDYmP5wD8q8xH-hBNKP0TXrWWamHa3TWNNDdi-4_FE_FWzpGbVWmolXybXPl4GOV1g",
// "openid":"oa9icwBIXUWSsKwUHyPOI0pNtXQU","scope":"snsapi_userinfo","unionid":"oZ7F3wyMgU-5COwsUTqbzR_7Nzkg"}

                    if (jsObj.has("unionid")) {
                        String unionid = jsObj.get("unionid").toString();
                        System.out.println("可以调用===" + jsObj.get("unionid"));
                        Intent intent = new Intent(WXEntryActivity.this, LoginActivity.class);
                        intent.putExtra("type", unionid);
                        WXEntryActivity.this.startActivity(intent);

                        WXEntryActivity.this.finish();
//                        thirdLoginPresenter.bindWeixinLogin(unionid);

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SendAuth.Req req = new SendAuth.Req();
                                //授权域 获取用户个人信息则填写snsapi_userinfo
                                req.scope = "snsapi_userinfo"; //snsapi_userinfo
                                //用于保持请求和回调的状态 可以任意填写
                                req.state = "test_login" + System.currentTimeMillis();
                                iwxapi.sendReq(req);
                                Toast.makeText(WXEntryActivity.this, "网络异常，请重试", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    SendAuth.Req req = new SendAuth.Req();
                    //授权域 获取用户个人信息则填写snsapi_userinfo
                    req.scope = "snsapi_userinfo"; //snsapi_userinfo
                    //用于保持请求和回调的状态 可以任意填写
                    req.state = "test_login" + System.currentTimeMillis();
                    iwxapi.sendReq(req);
                    e.printStackTrace();
                }
            }
        }).start();

    }
}




