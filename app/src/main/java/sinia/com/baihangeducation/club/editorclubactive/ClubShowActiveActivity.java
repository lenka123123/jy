package sinia.com.baihangeducation.club.editorclubactive;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.ClubPermissModel;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.editorclubactive.model.ActiveInfoData;
import sinia.com.baihangeducation.club.editorclubactive.model.ObtainActiveInfoListener;
import sinia.com.baihangeducation.home.activity.PartTimeJobDetailActivity;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.BaseUtil;
import sinia.com.baihangeducation.supplement.tool.PickerUtils;

public class ClubShowActiveActivity extends BaseActivity implements GetRequestListener {

    private static final int COMPANY_LOGO = 11034;
    private LinearLayout active_start_time;
    private TimePickerView timePicker;
    private TextView active_start_time_text;
    private TextView active_stop_time_text;
    private LinearLayout active_stop_time;
    private LinearLayout active_address;
    private TextView active_address_text;
    private OptionsPickerView cityPicker;                       //城市选择器

    private String mProvinceId;                     //省份ID
    private String mCityId;                         //城市ID
    private String mDistrictId;                     //区ID
    private ImageView photo;
    private String activity_id;
    private TextView title;
    private TextView type;
    private TextView include_club;
    private TextView man_num;
    private TextView woman_num;
    private TextView free;
    private TextView info;
    private LinearLayout include_club_ll;
    private TextView join;
    private ClubEditorModel clubEditorModel;
    private TextView exit;
    private TextView sex_option_text;
    private TextView man_text;
    private TextView woman_text;
    private ImageView home_search;
    private LinearLayout join_layout;


    @Override
    public int initLayoutResID() {
        return R.layout.activity_club_show_active;
    }


    @Override
    protected void initData() {
        Intent intent = getIntent();
        activity_id = intent.getStringExtra("activity_id");
        clubEditorModel = new ClubEditorModel(this);
        clubEditorModel.getActiveInfo(activity_id, new ObtainActiveInfoListener() {
            @Override
            public void onSuccess(ActiveInfoData successMessage, int myIndex) {
                showInfo(successMessage);

            }

            @Override
            public void onError(String errorMessage) {

            }

            @Override
            public void onUpdate(int total, int current) {

            }
        });

        ClubPermissModel clubPermissModel = new ClubPermissModel(this);
        clubPermissModel.getClubPermission("", this);
    }

    @Override
    protected void initView() {
        home_search = findViewById(R.id.home_search);
        join_layout = findViewById(R.id.join_layout);
        photo = findViewById(R.id.photo);
        title = findViewById(R.id.title);
        type = findViewById(R.id.type);
        include_club = findViewById(R.id.include_club);
        man_num = findViewById(R.id.man);
        woman_num = findViewById(R.id.woman);
        man_text = findViewById(R.id.man_text);
        woman_text = findViewById(R.id.woman_text);
        free = findViewById(R.id.free);
        info = findViewById(R.id.info);
        include_club_ll = findViewById(R.id.include_club_ll);
        join = findViewById(R.id.join);
        exit = findViewById(R.id.exit);
        sex_option_text = findViewById(R.id.sex_option_text);

        active_start_time = findViewById(R.id.active_start_time);
        active_start_time_text = findViewById(R.id.active_start_time_text);
        active_stop_time = findViewById(R.id.active_stop_time);
        active_stop_time_text = findViewById(R.id.active_stop_time_text);
        active_address = findViewById(R.id.active_address);
        active_address_text = findViewById(R.id.active_address_text);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


//        active_start_time.setOnClickListener(this);
//        active_stop_time.setOnClickListener(this);
//        active_address.setOnClickListener(this);
        join.setOnClickListener(this);
        exit.setOnClickListener(this);
        home_search.setOnClickListener(this);
    }

    private ActiveInfoData activeInfoData;

    private void showInfo(ActiveInfoData activeInfoData) {
        this.activeInfoData = activeInfoData;
        if (activeInfoData.is_apply.equals("1")) {
//            1：已参加
            join.setText("已报名");
            join.setClickable(false);
        } else if (activeInfoData.is_apply.equals("0")) {
            // 0：未报名
            join.setText("报名");
            join.setClickable(true);
        }

        if (activeInfoData.type.equals("2")) {
            include_club_ll.setVisibility(View.GONE);
        } else {
            include_club_ll.setVisibility(View.VISIBLE);
        }


        sex_option_text.setText(activeInfoData.human_type_name);
        if (activeInfoData.human_type.equals("4")) {
            String[] sexNum = activeInfoData.human_num.split(",");
            if (sexNum.length > 1) {
                man_num.setText(sexNum[0]);
                woman_num.setText(sexNum[1]);
            }
        } else if (activeInfoData.human_type.equals("3")) {
            man_num.setVisibility(View.GONE);
            man_text.setVisibility(View.GONE);
            woman_num.setText(activeInfoData.human_num);
        } else if (activeInfoData.human_type.equals("2")) {
            woman_num.setVisibility(View.GONE);
            woman_text.setVisibility(View.GONE);

            man_num.setText(activeInfoData.human_num);
        } else if (activeInfoData.human_type.equals("1")) {
            man_num.setVisibility(View.GONE);
            man_text.setVisibility(View.GONE);
            woman_text.setVisibility(View.GONE);
            woman_num.setText(activeInfoData.human_num);
        }


        Glide.with(context).load(activeInfoData.cover).error(R.drawable.logo).into(photo);
        title.setText(activeInfoData.name);
        type.setText(activeInfoData.type_name);
        include_club.setText(activeInfoData.club_name);
        active_start_time_text.setText(activeInfoData.begin_time);
        active_stop_time_text.setText(activeInfoData.end_time);


        free.setText(activeInfoData.expenditure);
        info.setText(activeInfoData.introduce);
        active_address_text.setText(activeInfoData.city_name + "" + activeInfoData.addr);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.join:
                clubEditorModel.applyActive(activity_id, new GetRequestListener() {
                    @Override
                    public void setRequestSuccess(String msg) {
                        if (msg.equals("请完善")) {
                            getCenterCancelDialogShow();
                            return;
                        }
                        join.setClickable(false);
                        join.setText("已报名");
                    }

                    @Override
                    public void setRequestFail() {

                    }
                });


                break;

            case R.id.active_start_time:
                hideEditTextInput();
                timePicker = PickerUtils.initTimePickerAll(this, active_start_time_text);
                timePicker.show();
                break;

            case R.id.active_stop_time:
                hideEditTextInput();
                timePicker = PickerUtils.initTimePickerAll(this, active_stop_time_text);
                timePicker.show();
                break;

            case R.id.active_address:
                hideEditTextInput();
                getAddress(active_address_text);
                cityPicker.show();
                break;

            case R.id.editor_photo:
                hideEditTextInput();
                takePhoto(COMPANY_LOGO);
                break;
            case R.id.home_search:
                if (!BaseUtil.isLogin(context, null)) {
                    return;
                }
//                if (jobInfo != null)
                addShareMeun();
                break;

            case R.id.sharemeun_qqfriend:
                //QQ好友
                doShare(SHARE_MEDIA.QQ);
                break;
            case R.id.sharemeun_qqzone:
                //QQ空间
                doShare(SHARE_MEDIA.QZONE);
                Toast.makeText(context, "QQ空间", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sharemeun_wechatfriend:
                //微信好友
                doShare(SHARE_MEDIA.WEIXIN);
                Toast.makeText(context, "微信好友", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sharemeun_moment:
                //朋友圈
                doShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                Toast.makeText(context, "朋友圈", Toast.LENGTH_SHORT).show();
                break;


        }
    }


    private void doShare(SHARE_MEDIA media) {
        if (activeInfoData == null) return;

        UMWeb web = new UMWeb(activeInfoData.share_url);
        web.setTitle(activeInfoData.share_title);//标题
        web.setDescription(activeInfoData.share_introduce);
//        web.setThumb(thumb);  //缩略图
        new ShareAction(ClubShowActiveActivity.this)
                .setPlatform(media)
                .withMedia(web)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA platform) {
                    }

                    /**
                     * @descrption 分享成功的回调
                     * @param platform 平台类型
                     */
                    @Override
                    public void onResult(SHARE_MEDIA platform) {
//                        Toast.makeText(context, "分享成功", Toast.LENGTH_LONG).show();
                    }

                    /**
                     * @descrption 分享失败的回调
                     * @param platform 平台类型
                     * @param t 错误原因
                     */
                    @Override
                    public void onError(SHARE_MEDIA platform, Throwable t) {
                        Toast.makeText(context, "分享失败" + t.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    /**
                     * @descrption 分享取消的回调
                     * @param platform 平台类型
                     */
                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(context, "分享取消了", Toast.LENGTH_LONG).show();
                    }
                })
                .share();
    }

    /**
     * 分享popwindow
     */
    private void addShareMeun() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.sharemenu, null);
        // 创建PopupWindow对象
        PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失
        TextView qq = contentView.findViewById(R.id.sharemeun_qqfriend);
        TextView qqZone = contentView.findViewById(R.id.sharemeun_qqzone);
        TextView wechat = contentView.findViewById(R.id.sharemeun_wechatfriend);
        TextView moment = contentView.findViewById(R.id.sharemeun_moment);
        qq.setOnClickListener(this);
        qqZone.setOnClickListener(this);
        wechat.setOnClickListener(this);
        moment.setOnClickListener(this);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置点击窗口外边窗口消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.pop_anim);
        // 设置此参数获得焦点，否则无法点击
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(join_layout.getRootView(), Gravity.BOTTOM, 0, 0);
    }

    private String img = "";

    private void takePhoto(int type) {
        SImagePicker.from(ClubShowActiveActivity.this)
                .pickMode(SImagePicker.MODE_IMAGE)
                .showCamera(true).rowCount(3)
                .cropFilePath(
                        AppConfig.IMAGE_PATH + "/active_photo.png")
                .forResult(type);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == COMPANY_LOGO) {
            ArrayList<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
            if (pathList != null && pathList.size() != 0) {
                img = pathList.get(0);
                Log.i("图片地址", pathList.get(0));
                setPhoto(pathList.get(0));
                File appDir = new File(img);
                try {
                    MediaStore.Images.Media.insertImage(ClubShowActiveActivity.this.getContentResolver(), appDir.getAbsolutePath().toString(), appDir.getName(), "");
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                ///storage/emulated/0/DCIM/Camera/IMG_20180903_074033.jpg
                ///storage/emulated/0/Pictures/2018_09_06_18_45_19.jpg

//                ImageLoaderUtils.display(context, mUCentreImage, pathList.get(0), R.drawable.logo, true);
            }
        }
//        else if (resultCode == Activity.RESULT_OK && requestCode == LICENSE_IMG) {
//            ArrayList<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
//            if (pathList != null && pathList.size() != 0) {
//                Logger.d(FileUtil.getInstance().getFileSize(context, pathList.get(0)));
//                licenseImg = pathList.get(0);
//                ImageLoaderUtils.display(context, mUCentreImage, pathList.get(0), R.drawable.logo, true);
//            }
//        }
    }

    public void setPhoto(String avatar) {
        Glide.with(context).load(avatar).asBitmap().error(R.drawable.new_eorrlogo).centerCrop().into(new BitmapImageViewTarget(photo) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
                photo.setImageDrawable(circularBitmapDrawable);
            }
        });
    }


    private void getAddress(TextView view) {
        cityPicker = PickerUtils.initCityPicker(this, view, new PickerUtils.OnCityClickListener() {
            @Override
            public void onCityClick(String provinceId, String cityId, String districtId) {
                mProvinceId = provinceId;
                mCityId = cityId;
                mDistrictId = districtId;
            }
        });
    }

    /**
     * 隐藏软键盘
     */
    protected void hideEditTextInput() {
        //隐藏键盘
        ((InputMethodManager) ClubShowActiveActivity.this.getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow((ClubShowActiveActivity.this).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    @Override
    public void setRequestSuccess(String msg) {
        List<String> mPermissionList = new Gson().fromJson(msg, new TypeToken<List<String>>() {
        }.getType());

    }

    @Override
    public void setRequestFail() {

    }


    public void getCenterCancelDialogShow() {
        final Dialog dialog = new Dialog(context, com.example.framwork.R.style.custom_cancel_dialog);
        dialog.setContentView(R.layout.clcub_join_dialog_apply_app);
        Window dialogWindow = dialog.getWindow();

        dialogWindow.findViewById(R.id.club_join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.toRealNameActivity(context);
                dialog.cancel();
            }
        });
        //dialogWindow.setWindowAnimations(R.style.mystyle);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = context.getResources().getDisplayMetrics().widthPixels;
        lp.alpha = 1.0f;
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }

}
