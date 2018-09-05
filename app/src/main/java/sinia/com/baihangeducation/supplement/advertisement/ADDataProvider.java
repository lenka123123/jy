package sinia.com.baihangeducation.supplement.advertisement;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.framwork.banner.BannerItem;
import com.example.framwork.banner.SimpleImageBanner;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.SpCommonUtils;
import com.flyco.banner.anim.select.ZoomInEnter;

import java.util.ArrayList;
import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.JobBangClassADListInfo;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.SplashActivity;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.R;

/**
 * 头部广告帮助类
 */
public class ADDataProvider {

    public static ArrayList<BannerItem> getList(List<JobBangClassADListInfo> imageInfos) {
        ArrayList<BannerItem> list = new ArrayList<>();
        for (int i = 0; i < imageInfos.size(); i++) {
            BannerItem item = new BannerItem();
            item.imgUrl = imageInfos.get(i).cover_url;
            item.title = imageInfos.get(i).title;
            list.add(item);
        }
        return list;
    }

    public static ArrayList<BannerItem> getStrList(List<String> imageInfos) {
        ArrayList<BannerItem> list = new ArrayList<>();
        for (int i = 0; i < imageInfos.size(); i++) {
            BannerItem item = new BannerItem();
            item.imgUrl = imageInfos.get(i);
            list.add(item);
        }
        return list;
    }


    /**
     * 广告点击事件
     *
     * @param context
     * @param imageInfo
     */
    public static void adToInformation(final Context context, JobBangClassADListInfo imageInfo) {
//         MyApplication application = (MyApplication) context.getApplicationContext();

        if (CommonUtil.isFastClick())
            return;
        if (imageInfo.is_need_login == 1) {
            if (!AppConfig.ISlOGINED) {
                new AlertDialog.Builder(context).setTitle("提示！").setMessage("您尚未登录，请先登录。").setPositiveButton("登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Goto.toLogin(context);
                    }
                }).setNegativeButton("取消", null).show();

            } else {
                if (imageInfo != null) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    String title = imageInfo.title;
                    //广告类型(1：跳转APP内容 2：H5页面)
                    int type = imageInfo.type;
                    //App内容板块 (1: 兼职 ; 2 : 全职  ; 3 : 培训  ; 4 : 转让  ； 5：互助  ； 6：App内容板块   ； 7：趣事   ； 8：每日分享    ）
                    int plate = imageInfo.plate;
                    if (type == 1) {
                        switch (plate) {
                            case 1:
                                Goto.toPartTimeJobDetailActivity(context, imageInfo.type_id);
                                break;
                            case 2:
                                Goto.toAllJobDetailActivity(context, imageInfo.type_id);
                                break;
                            case 3:
                                Goto.toTraingDetailActivity(context, imageInfo.type_id);
                                break;
                            case 4:
                                Goto.toHomeAndFindHelpEachOtherDetailActivity(context, imageInfo.type_id);
                                break;
                            case 5:
                                Goto.toHomeAndFindHelpEachOtherDetailActivity(context, imageInfo.type_id);
                                break;
                            case 8:
                                Goto.toShareEveryDayDetailActivity(context, imageInfo.type_id);
                                break;
                        }
                    } else {
                        if (TextUtils.isEmpty(imageInfo.url)) {
                            return;
                        }
                        String url = "";
                        String shareUrl = "";
                        url = imageInfo.url;
                        shareUrl = imageInfo.share_url;
                        String userId = (String) SpCommonUtils.get(context, AppConfig.FINALUSERID, "");

                        url = imageInfo.url + "&user_id=" + userId;
                        Goto.toShareWebViewActivity(context, title, url, R.drawable.new_realname_title_bg);
                    }
                }
            }
        } else {
            if (imageInfo != null) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                String title = imageInfo.title;
                //广告类型(1：跳转APP内容 2：H5页面)
                int type = imageInfo.type;
                //App内容板块 (1: 兼职 ; 2 : 全职  ; 3 : 培训  ; 4 : 转让  ； 5：互助  ； 6：App内容板块   ； 7：趣事   ； 8：每日分享    ）
                int plate = imageInfo.plate;
                if (type == 1) {
                    switch (plate) {
                        case 1:
                            Goto.toPartTimeJobDetailActivity(context, imageInfo.type_id);
                            break;
                        case 2:
                            Goto.toAllJobDetailActivity(context, imageInfo.type_id);
                            break;
                        case 3:
                            Goto.toTraingDetailActivity(context, imageInfo.type_id);
                            break;
                        case 4:
                            Goto.toHomeAndFindHelpEachOtherDetailActivity(context, imageInfo.type_id);
                            break;
                        case 5:
                            Goto.toHomeAndFindHelpEachOtherDetailActivity(context, imageInfo.type_id);
                            break;
                        case 8:
                            Goto.toShareEveryDayDetailActivity(context, imageInfo.type_id);
                            break;
                    }
                } else {
                    if (TextUtils.isEmpty(imageInfo.url)) {
                        return;
                    }
                    String url = "";
                    String shareUrl = "";
                    url = imageInfo.url;
                    shareUrl = imageInfo.share_url;
                    Goto.toShareWebViewActivity(context, title, url, R.drawable.new_realname_title_bg);
                }
            }
        }
    }

    /**
     * 初始化广告栏
     */
    public static void initAdvertisement(SimpleImageBanner sib, List<JobBangClassADListInfo> imageInfos, int height, int width) {
        if (imageInfos.size() == 0) {
            return;
        }
        sib.setItemHeight(height);
        sib.setItemWidth(width);
        //设置默认加载图片

        sib.setErrorResourceId(R.drawable.bg_spinner_default);
        sib.setSelectAnimClass(ZoomInEnter.class) // set indicator select anim
                .setSource(ADDataProvider.getList(imageInfos)) // data source list
                // transformer
                .startScroll(); // start scroll,the last method to call
    }

    /**
     * 初始化广告栏 list<String></String>
     */
    public static void initAdvertisementForStrings(SimpleImageBanner sib, List<String> imageInfos, int height, int width) {
        if (imageInfos.size() == 0) {
            return;
        }
        sib.setItemHeight(height);
        sib.setItemWidth(width);
        sib.setSelectAnimClass(ZoomInEnter.class) // set indicator select anim
                .setSource(ADDataProvider.getStrList(imageInfos)) // data source list
                // transformer
                .startScroll(); // start scroll,the last method to call
    }

    /**
     * 加载广告头
     *
     * @param context
     * @return
     */
    public static View getCustomHeaderView(final Context context, int layoutID) {
        View headerView = View.inflate(context, layoutID, null);
        return headerView;
    }


}
