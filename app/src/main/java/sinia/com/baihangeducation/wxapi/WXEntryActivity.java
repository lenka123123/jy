package sinia.com.baihangeducation.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.amap.api.col.sl2.o;
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

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.mine.activity.LoginActivity;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.OkHttpUtils;
import sinia.com.baihangeducation.supplement.tool.WXAccessTokenEntity;
import sinia.com.baihangeducation.supplement.tool.WXBaseRespEntity;
import sinia.com.baihangeducation.supplement.weixin.WXConstants;

//    WXCallbackActivity implements IWXAPIEventHandler {
public class WXEntryActivity extends WXCallbackActivity  {

//    private IWXAPI iwxapi;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        iwxapi = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
//        //接收到分享以及登录的intent传递handleIntent方法，处理结果
//        iwxapi.registerApp(Constants.APP_ID);
//        try {
//            boolean result = iwxapi.handleIntent(getIntent(), this);
//            if (!result) {
//                Log.d("", "参数不合法，未被SDK处理，退出");
//                finish();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        iwxapi.handleIntent(data, this);
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        iwxapi.handleIntent(intent, this);
//        finish();
//    }
//
//    @Override
//    public void onReq(BaseReq baseReq) {
//    }
//
//
//    //请求回调结果处理
//    @Override
//    public void onResp(BaseResp baseResp) {
//        //微信登录为getType为1，分享为0
//
//        WXBaseRespEntity entity = JSON.parseObject(JSON.toJSONString(baseResp), WXBaseRespEntity.class);
//        String result = "";
//        switch (baseResp.errCode) {
//            case BaseResp.ErrCode.ERR_OK:
//                result = "发送成功";
//
//                List<OkHttpUtils.Param> params = new ArrayList<>();
//                params.add(new OkHttpUtils.Param("appid", Constants.APP_ID));
//                params.add(new OkHttpUtils.Param("secret", Constants.APP_SECRET));
//                params.add(new OkHttpUtils.Param("code", entity.getCode()));
//                params.add(new OkHttpUtils.Param("grant_type", "authorization_code"));
//                //获取授权
//                String http = "https://api.weixin.qq.com/sns/oauth2/access_token";
//                OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
//                    @Override
//                    public void onSuccess(String response) {
//                        String access = null;
//                        String openId = null;
//                        System.out.println(entity.getCode() + "===response== you" + response);
//                    }
//
//                    @Override
//                    public void onFailure(Exception e) {
//                        android.widget.Toast.makeText(WXEntryActivity.this, "登录失败", android.widget.Toast.LENGTH_SHORT).show();
//                    }
//                };
//                OkHttpUtils.post(http, resultCallback, params);
//                OkHttpUtils.post(http, resultCallback, params);
//
////                requestOpenId(String.valueOf(entity.getCode()));
//                break;
//            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
//                Toast.makeText(WXEntryActivity.this, "用户拒绝授权", Toast.LENGTH_LONG).show();
//                System.out.println();
//                break;
//            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
//                Toast.makeText(WXEntryActivity.this, "用户取消登录", Toast.LENGTH_LONG).show();
//                break;
//            default:
//                break;
//        }
//    }
//
////        else {
////            //分享成功回调
////            System.out.println("------------分享回调------------");
////            switch (baseResp.errCode) {
////                case BaseResp.ErrCode.ERR_OK:
////                    //分享成功
////                    Toast.makeText(WXEntryActivity.this, "分享成功", Toast.LENGTH_LONG).show();
////                    break;
////                case BaseResp.ErrCode.ERR_USER_CANCEL:
////                    //分享取消
////                    Toast.makeText(WXEntryActivity.this, "分享取消", Toast.LENGTH_LONG).show();
////                    break;
////                case BaseResp.ErrCode.ERR_AUTH_DENIED:
////                    //分享拒绝
////                    Toast.makeText(WXEntryActivity.this, "分享拒绝", Toast.LENGTH_LONG).show();
////                    break;
////            }
////        }
//
//
//    /**
//     * 开辟子线程
//     */
//
//    private void requestOpenId(String code) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String urlstr = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" +
//                        Constants.APP_ID + "&secret=" + Constants.APP_SECRET + "&code=" +
//                        code + "&grant_type=authorization_code";
//
//                //获取 网上 数据
//                String httpUrl = WXConstants.httpUrl(urlstr);
//                System.out.println("getUnionideee222" + code);
//                System.out.println("getUnionideee222" + httpUrl);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        WXAccessTokenEntity accessTokenEntity = JSON.parseObject(httpUrl, WXAccessTokenEntity.class);
//                        System.out.println("得到1Unionid" + accessTokenEntity.getUnionid());
//                        if (accessTokenEntity.getUnionid() == null) {
//                            Toast.makeText(WXEntryActivity.this, "网络错误，请重试", Toast.LENGTH_SHORT).show();
//                        } else
//                            Goto.toLogin(WXEntryActivity.this, accessTokenEntity.getUnionid());
//                    }
//                });
//            }
//        }).start();
//
//    }

}




