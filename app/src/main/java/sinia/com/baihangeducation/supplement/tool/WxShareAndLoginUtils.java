package sinia.com.baihangeducation.supplement.tool;

import android.content.Context;
import android.widget.Toast;

import com.mcxtzhang.swipemenulib.utils.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by ${wjw} on 2016/5/18.
 */
public class WxShareAndLoginUtils {

    public static int WECHAT_FRIEND = 0;  //分享好友
    public static int WECHAT_MOMENT = 1;  //分享朋友圈
    private static IWXAPI iwxapi;

    public static IWXAPI getWXAPI(Context context) {
        if (iwxapi == null) {
            //通过WXAPIFactory创建IWAPI实例
            iwxapi = WXAPIFactory.createWXAPI(context, Constants.APP_ID, false);
            //将应用的appid注册到微信
            iwxapi.registerApp(Constants.APP_ID);
        }
        return iwxapi;
    }

    /**
     * 微信登录
     */
    public static void WxLogin(Context context) {
//        https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE&connect_redirect=1#wechat_redirect
        if (!judgeCanGo(context)) {
            return;
        }
        SendAuth.Req req = new SendAuth.Req();
        //授权域 获取用户个人信息则填写snsapi_userinfo
        req.scope = "snsapi_userinfo"; //snsapi_userinfo
        //用于保持请求和回调的状态 可以任意填写
        req.state = "test_login" + System.currentTimeMillis();
        iwxapi.sendReq(req);
    }

    /**
     * 分享文本至朋友圈
     * @param text  文本内容
     * @param judge 类型选择 好友-WECHAT_FRIEND 朋友圈-WECHAT_MOMENT
     */
//    public static void WxTextShare(Context context,String text,int judge){
//        if (!judgeCanGo(context)){
//            return;
//        }
//        //初始化WXTextObject对象，填写对应分享的文本内容
//        WXTextObject textObject = new WXTextObject();
//        textObject.text = text;
//        //初始化WXMediaMessage消息对象，
//        WXMediaMessage message = new WXMediaMessage();
//        message.mediaObject = textObject;
//        message.description = text;
//        //构建一个Req请求对象
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = String.valueOf(System.currentTimeMillis());   //transaction用于标识请求
//        req.message = message;
//        req.scene = judge;      //分享类型 好友==0 朋友圈==1
//        //发送请求
//        iwxapi.sendReq(req);
//    }

    /**
     *  分享图片
     * @param bitmap 图片bitmap,建议别超过32k
     * @param judge 类型选择 好友-WECHAT_FRIEND 朋友圈-WECHAT_MOMENT
     */
//    public static void WxBitmapShare(Context context, Bitmap bitmap,int judge){
//        if (!judgeCanGo(context)){
//            return;
//        }
//        WXImageObject wxImageObject = new WXImageObject(bitmap);
//        WXMediaMessage message = new WXMediaMessage();
//        message.mediaObject = wxImageObject;
//
//        Bitmap thunmpBmp = Bitmap.createScaledBitmap(bitmap,50,50,true);
//        bitmap.recycle();
//        message.thumbData = ImageUtil.bmpToByteArray(thunmpBmp,true);
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = String.valueOf(System.currentTimeMillis());
//        req.message = message;
//        req.scene = judge;
//
//        iwxapi.sendReq(req);
//
//    }

    /**

     */
//    public static void WxUrlShare(Context context,String url,String title,String description,String imgUrl,int judge){
//        if (!judgeCanGo(context)){
//            return;
//        }
//        Bitmap bitmap = getBitMBitmap(imgUrl);
//        WXWebpageObject wxWebpageObject = new WXWebpageObject();
//        wxWebpageObject.webpageUrl = url;
//
//        WXMediaMessage wxMediaMessage = new WXMediaMessage(wxWebpageObject);
//        wxMediaMessage.title = title;
//        wxMediaMessage.description = description;
//        Bitmap thunmpBmp = Bitmap.createScaledBitmap(bitmap,50,50,true);
//        bitmap.recycle();
//        wxMediaMessage.thumbData = ImageUtil.bmpToByteArray(thunmpBmp,true);
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = String.valueOf(System.currentTimeMillis());
//        req.message = wxMediaMessage;
//        req.scene = judge;
//
//        iwxapi.sendReq(req);
//    }
    private static boolean judgeCanGo(Context context) {
        getWXAPI(context);
        if (!iwxapi.isWXAppInstalled()) {
            Toast.makeText(context, "请先安装微信应用", Toast.LENGTH_SHORT).show();
            return false;
        }
//        else if (!iwxapi.isWXAppSupportAPI()) {
//            Toast.makeText(context, "请先更新微信应用", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        return true;
    }


    /**
     * 图片url转bitmap
     */
//    public static Bitmap getBitMBitmap(String urlpath) {
//        Bitmap map = null;
//        try {
//            URL url = new URL(urlpath);
//            URLConnection conn = url.openConnection();
//            conn.connect();
//            InputStream in;
//            in = conn.getInputStream();
//            map = BitmapFactory.decodeStream(in);
//            // TODO Auto-generated catch block
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return map;
//    }
}
