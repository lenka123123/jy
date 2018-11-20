package sinia.com.baihangeducation.club.editorclubactive;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.framwork.utils.DateUtil;
import com.example.framwork.utils.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.umeng.commonsdk.debug.I;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.ClubPermissModel;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.editorclubactive.model.GetActiveOptionListener;
import sinia.com.baihangeducation.club.editorclubactive.model.GetClubActiveOption;
import sinia.com.baihangeducation.home.adapter.PhoneGlideImageLoader;
import sinia.com.baihangeducation.mine.activity.UCentreBaseInfoActivity;
import sinia.com.baihangeducation.supplement.alertview.AlertViewContorller;
import sinia.com.baihangeducation.supplement.alertview.OnItemClickListener;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.tool.PicassoImageLoader;
import sinia.com.baihangeducation.supplement.tool.PickerUtils;

import static io.github.changjiashuai.ImagePicker.REQUEST_CODE_PICK;


public class ClubEditorActiveActivity extends BaseActivity implements GetRequestListener, GetActiveOptionListener, OnItemClickListener {

    private static final int COMPANY_LOGO = 11034;
    private LinearLayout active_start_time;
    private TimePickerView timePicker;
    private TextView active_start_time_text;
    private TextView active_stop_time_text;
    private LinearLayout active_stop_time;
    private LinearLayout active_address;
    private TextView active_address_text;
    private OptionsPickerView cityPicker;                       //城市选择器

    private String mProvinceId = "";                     //省份ID
    private String mCityId = "";                         //城市ID
    private String mDistrictId = "";                      //区ID
    private LinearLayout editor_photo;
    private ImageView photo;
    private String clubid;
    private LinearLayout active_type;
    private LinearLayout active_club;
    private AlertViewContorller mAlertViewContorller;
    private AlertViewContorller mAlertViewContorllerClub;
    private GetClubActiveOption clubActiveOption;
    private String[] type_list;
    private String[] club_list;
    private TextView active_type_text;
    private TextView active_club_text;
    private String clickType = "";
    private String type_id = "";
    private String sex_id = "";
    private String club_id = "";
    private ClubEditorModel clubEditorModel;
    private EditText active_name;
    private Long activeStartTime = 0L;
    private Long activeStopTime;
    private EditText adr_detail;
    private EditText free;
    private EditText man_num;
    private EditText woman_num;
    private EditText introduce;
    private TextView exit;
    private String img = "";
    private LinearLayout sex_option;
    private TextView sex_option_text;
    private String[] sex_list = {"性别不限", "仅男生", "仅女生", "男女混合"};
    private AlertViewContorller mSexViewContorller;
    private LinearLayout man_woman_linear;
    private TextView man_num_name;
    private TextView woman_num_name;
    private String media_id;
    private String url;
    String inputNum = "";


    @Override
    public int initLayoutResID() {
        return R.layout.activity_club_ecitor_active;
    }


    @Override
    protected void initData() {
        img = "";
        Intent intent = getIntent();
        clubid = intent.getStringExtra("clubid");
        clubEditorModel = new ClubEditorModel(this);

        ClubPermissModel clubPermissModel = new ClubPermissModel(this);
        clubPermissModel.getClubPermission(clubid, this);

        clubEditorModel.getActivityOption(this);


        mPhoneConfig1 = new io.github.changjiashuai.ImagePicker.Config(new PhoneGlideImageLoader());
        mPhoneConfig1.multiMode(false);
//        config.selectLimit(1);
        mPhoneConfig1.showCamera(true);
        mPhoneConfig1.crop(true);
        mPhoneConfig1.cropStyle(io.github.changjiashuai.widget.CropImageView.RECTANGLE);
        mPhoneConfig1.saveRectangle(false);//裁剪后是否矩形保存图片
        mPhoneConfig1.focusWidth(1200);
        mPhoneConfig1.focusHeight(1200);
        mPhoneConfig1.outPutX(1200);
        mPhoneConfig1.outPutY(1200);

//        ImagePicker imagePicker = ImagePicker.getInstance();
//        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
//        imagePicker.setShowCamera(false);  //显示拍照按钮
//        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
//        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
//        imagePicker.setSelectLimit(1);    //选中数量限制
//        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
//        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
//        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    @Override
    protected void initView() {
        active_name = findViewById(R.id.active_name);
        active_start_time = findViewById(R.id.active_start_time);
        active_start_time_text = findViewById(R.id.active_start_time_text);
        active_stop_time = findViewById(R.id.active_stop_time);
        active_stop_time_text = findViewById(R.id.active_stop_time_text);
        active_address = findViewById(R.id.active_address);
        active_address_text = findViewById(R.id.active_address_text);
        editor_photo = findViewById(R.id.editor_photo);
        photo = findViewById(R.id.photo);
        active_type = findViewById(R.id.active_type);
        active_club = findViewById(R.id.active_club);
        active_type_text = findViewById(R.id.active_type_text);
        active_club_text = findViewById(R.id.active_club_text);
        adr_detail = findViewById(R.id.adr_detail);
        free = findViewById(R.id.free);
        man_num = findViewById(R.id.man_num);
        woman_num = findViewById(R.id.woman_num);
        man_num_name = findViewById(R.id.man_num_name);
        woman_num_name = findViewById(R.id.woman_num_name);
        introduce = findViewById(R.id.introduce);
        exit = findViewById(R.id.exit);
        sex_option = findViewById(R.id.sex_option);
        sex_option_text = findViewById(R.id.sex_option_text);
        man_woman_linear = findViewById(R.id.man_woman_linear);


        findViewById(R.id.back).setOnClickListener(this);

        photo.setOnClickListener(this);
        sex_option.setOnClickListener(this);
        active_start_time.setOnClickListener(this);
        active_stop_time.setOnClickListener(this);
        active_address.setOnClickListener(this);
        editor_photo.setOnClickListener(this);
        active_type.setOnClickListener(this);
        active_club.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.exit:
                hideEditTextInput();
                exit.setEnabled(false);
                exit.setVisibility(View.INVISIBLE);
                if (check()) {

                } else {
                    exit.setEnabled(true);
                    exit.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.active_start_time:
                hideEditTextInput();
                timePicker = PickerUtils.initTimePickerAllFor(this, active_start_time_text, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        activeStartTime = date.getTime();


//                        activeStopTime = dateToStamp(active_stop_time_text.getText().toString().trim());
                        active_start_time_text.setText(DateUtil.getInstance().getHHmm(date));
                    }
                });
                timePicker.show();
                break;

            case R.id.active_stop_time:
                hideEditTextInput();
                timePicker = PickerUtils.initTimePickerAllFor(this, active_stop_time_text, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        activeStopTime = date.getTime();
//                        activeStopTime = dateToStamp(active_stop_time_text.getText().toString().trim());
                        active_stop_time_text.setText(DateUtil.getInstance().getHHmm(date));
                    }
                });
                timePicker.show();
                break;

            case R.id.active_address:
                hideEditTextInput();
                getAddress(active_address_text);
                cityPicker.show();
                break;

            case R.id.photo:
            case R.id.editor_photo:
                hideEditTextInput();
                takePhoto(COMPANY_LOGO);
                break;

            case R.id.active_type:
                hideEditTextInput();
                clickType = "type";
                mAlertViewContorller = new AlertViewContorller(active_type_text,
                        "选择活动类别", null, "取消", null, type_list,
                        context, AlertViewContorller.Style.ActionSheet, this);
                mAlertViewContorller.setCancelable(true).show();
                break;
            case R.id.sex_option://,
                hideEditTextInput();
                clickType = "sex";
                mSexViewContorller = new AlertViewContorller(sex_option_text,
                        "选择性别要求", null, "取消", null, sex_list,
                        context, AlertViewContorller.Style.ActionSheet, this);
                mSexViewContorller.setCancelable(true).show();
                break;

            case R.id.active_club:
                hideEditTextInput();
                clickType = "club";
                mAlertViewContorllerClub = new AlertViewContorller(active_club_text,
                        "选择所属社团", null, "取消", null, club_list,
                        context, AlertViewContorller.Style.ActionSheet, this);
                mAlertViewContorllerClub.setCancelable(true).show();
                break;


        }
    }

    private final int IMAGE_PICKER = 9090;
    private io.github.changjiashuai.ImagePicker.Config mPhoneConfig1;

    private void takePhoto(int type) {
        io.github.changjiashuai.ImagePicker.getInstance().pickImageForResult(this, mPhoneConfig1);
        io.github.changjiashuai.ImagePicker.RESULT_CODE_BACK_CLICK = 0;
//        Intent intent = new Intent(this, ImageGridActivity.class);
//        startActivityForResult(intent, IMAGE_PICKER);
//        SImagePicker.from(ClubEditorActiveActivity.this)
//                .pickMode(SImagePicker.MODE_IMAGE)
//                .showCamera(true).rowCount(3)
//                .cropFilePath(
//                        AppConfig.IMAGE_PATH + "/active_photo.png")
//                .forResult(type);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK && io.github.changjiashuai.ImagePicker.RESULT_CODE_BACK_CLICK == 0) {
            if (data != null) {
                if (resultCode == io.github.changjiashuai.ImagePicker.RESULT_CODE_ITEMS) {
                    boolean isOrigin = data.getBooleanExtra(io.github.changjiashuai.ImagePicker.EXTRA_IS_ORIGIN, false);
                    if (!isOrigin) {
                        ArrayList<io.github.changjiashuai.bean.ImageItem> imageItems = io.github.changjiashuai.ImagePicker.getInstance().getSelectedImages();
                        if (imageItems.size() > 0) {
                            io.github.changjiashuai.bean.ImageItem imageItem = imageItems.get(0);
                            img = imageItems.get(0).path;
                            upPhone();
                            setPhoto(img);
                            File appDir = new File(img);
                            try {
                                MediaStore.Images.Media.insertImage(ClubEditorActiveActivity.this.getContentResolver(), appDir.getAbsolutePath().toString(), appDir.getName(), "");
                                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            ArrayList<String> paths = new ArrayList<>();
                            for (io.github.changjiashuai.bean.ImageItem imageItem1 : imageItems) {
                                paths.add(imageItem1.path);
                            }
                        }
                    } else {
                        //遍历做压缩处理
                        android.widget.Toast.makeText(this, "稍后压缩", android.widget.Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }


    protected void onActivityResultFor(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                img = images.get(0).path;

                setPhoto(img);
            } else {
                android.widget.Toast.makeText(this, "没有数据", android.widget.Toast.LENGTH_SHORT).show();
            }
        }
    }


    protected void onActivityResultsdfsdf(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == COMPANY_LOGO) {
            ArrayList<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
            if (pathList != null && pathList.size() != 0) {
                img = pathList.get(0);
                Log.i("图片地址", pathList.get(0));

                setPhoto(img);
//                File appDir = new File(img);
//                try {
//                    MediaStore.Images.Media.insertImage(ClubEditorActiveActivity.this.getContentResolver(), appDir.getAbsolutePath().toString(), appDir.getName(), "");
//                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
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
        ((InputMethodManager) ClubEditorActiveActivity.this.getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow((ClubEditorActiveActivity.this).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    @Override
    public void setRequestSuccess(String msg) {
        List<String> mPermissionList = new Gson().fromJson(msg, new TypeToken<List<String>>() {
        }.getType());


    }

    @Override
    public void setRequestFail() {

    }

    @Override
    public void onSuccess(GetClubActiveOption successMessage) {
        club_list = new String[successMessage.club_list.size()];
        type_list = new String[successMessage.type_list.size()];
        for (int i = 0; i < successMessage.club_list.size(); i++) {
            club_list[i] = successMessage.club_list.get(i).club_name;
        }
        for (int i = 0; i < successMessage.type_list.size(); i++) {
            type_list[i] = successMessage.type_list.get(i).type_name;
        }


        clubActiveOption = successMessage;
    }

    @Override
    public void onError(String errorMessage) {

    }

    @Override
    public void onItemClick(View view, List<String> mOthers, Object o, int position) {
        if (position == -1) {
            if (mAlertViewContorller != null && mAlertViewContorller.isShowing())
                mAlertViewContorller.dismiss();
            if (mAlertViewContorllerClub != null && mAlertViewContorllerClub.isShowing())
                mAlertViewContorllerClub.dismiss();
            if (mSexViewContorller != null && mSexViewContorller.isShowing())
                mSexViewContorller.dismiss();
        } else {
            TextView view1 = (TextView) view;
            view1.setText(mOthers.get(position));
            if (clickType.equals("club")) {
                club_id = clubActiveOption.club_list.get(position).club_id;
            }
            if (clickType.equals("type")) {
                type_id = clubActiveOption.type_list.get(position).type;
            }
            if (clickType.equals("sex")) {//  ( 1：性别不限 2：仅男生 3：仅女生 4：男女混合 )
                man_woman_linear.setVisibility(View.VISIBLE);
                if (sex_list[position].equals("性别不限")) {
                    sex_id = "1";
                    man_num.setVisibility(View.GONE);
                    man_num_name.setVisibility(View.GONE);
                    woman_num.setVisibility(View.VISIBLE);
                    woman_num_name.setVisibility(View.GONE);

                } else if (sex_list[position].equals("仅男生")) {
                    sex_id = "2";
                    man_num.setVisibility(View.VISIBLE);
                    man_num_name.setVisibility(View.VISIBLE);
                    woman_num.setVisibility(View.GONE);
                    woman_num_name.setVisibility(View.GONE);
                } else if (sex_list[position].equals("仅女生")) {
                    sex_id = "3";
                    man_num.setVisibility(View.GONE);
                    man_num_name.setVisibility(View.GONE);
                    woman_num.setVisibility(View.VISIBLE);
                    woman_num_name.setVisibility(View.VISIBLE);
                } else if (sex_list[position].equals("男女混合")) {
                    sex_id = "4";
                    man_num.setVisibility(View.VISIBLE);
                    man_num_name.setVisibility(View.VISIBLE);
                    woman_num.setVisibility(View.VISIBLE);
                    woman_num_name.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mAlertViewContorller != null && mAlertViewContorller.isShowing()) {
                mAlertViewContorller.dismiss();
                return false;
            }
            if (mAlertViewContorllerClub != null && mAlertViewContorllerClub.isShowing()) {
                mAlertViewContorllerClub.dismiss();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);

    }


    public boolean check() {
        if (active_name.getText().toString().trim().equals("")) {
            Toast.getInstance().showErrorToast(context, "请填写活动主题");
            return false;
        }
        if (active_type_text.getText().toString().trim().equals("")) {
            Toast.getInstance().showErrorToast(context, "请选择活动类别");
            return false;
        }
        if (active_club_text.getText().toString().trim().equals("")) {
            Toast.getInstance().showErrorToast(context, "请选择所属社团");
            return false;
        }
        if (active_start_time_text.getText().toString().trim().equals("")) {
            Toast.getInstance().showErrorToast(context, "请选择开始时间");
            return false;
        }

        if (active_stop_time_text.getText().toString().trim().equals("")) {
            Toast.getInstance().showErrorToast(context, "请选择结束时间");
            return false;
        }

        if (System.currentTimeMillis() > activeStartTime) {
            Toast.getInstance().showErrorToast(context, "开始时间不是小于当前时间");
            return false;
        }

        if (activeStartTime > activeStopTime) {
            Toast.getInstance().showErrorToast(context, "结束时间不能早于开始时间");
            return false;
        }

        if (mProvinceId.equals("") || mDistrictId.equals("")) {
            Toast.getInstance().showErrorToast(context, "请选择活动地点");
            return false;
        }

        if (adr_detail.getText().toString().trim().equals("")) {
            Toast.getInstance().showErrorToast(context, "请输入详细的活动地点");
            return false;
        }

        if (free.getText().toString().trim().equals("")) {
            Toast.getInstance().showErrorToast(context, "请输入详细的活动地点");
            return false;
        }

        if (sex_option_text.getText().toString().trim().equals("")) {
            Toast.getInstance().showErrorToast(context, "请选择性别要求");
            return false;
        }
        if (man_num.getText().toString().trim().equals("") && woman_num.getText().toString().trim().equals("")) {
            Toast.getInstance().showErrorToast(context, "请输入人数");
            return false;
        }
        if (introduce.getText().toString().trim().equals("") && woman_num.getText().toString().trim().equals("")) {
            Toast.getInstance().showErrorToast(context, "请输入活动介绍");
            return false;
        }

        if (img.equals("")) {
            Toast.getInstance().showErrorToast(context, "请选择活动图片");
            return false;
        }
        showProgress();

        //1：性别不限 2：仅男生 3：仅女生 4：男女混合 )
        if (sex_id.equals("1")) {
            inputNum = woman_num.getText().toString().trim();
        } else if (sex_id.equals("2")) {
            inputNum = man_num.getText().toString().trim();
        } else if (sex_id.equals("3")) {
            inputNum = woman_num.getText().toString().trim();
        } else if (sex_id.equals("4")) {
            inputNum = man_num.getText().toString().trim() + "," + woman_num.getText().toString().trim();
        }

        System.out.println("human_num== " + inputNum);

        clubEditorModel.sendActive(
                active_name.getText().toString().trim(),
                type_id, club_id,
                activeStartTime + "",
                activeStopTime + "",
                mProvinceId,
                mCityId,
                mDistrictId,
                adr_detail.getText().toString().trim(),
                free.getText().toString().trim(),
                sex_id,
                inputNum,
                introduce.getText().toString().trim(),
                url,
                media_id,
                ClubEditorActiveActivity.this);
        hideProgress();


        return true;
    }

    public void upPhone() {
        clubEditorModel.updataPhone(img, "1", new GetRequestListener() {
            @Override
            public void setRequestSuccess(String msg) {

                JsonObject jsonObject = (JsonObject) new JsonParser().parse(msg);
                url = jsonObject.get("url").getAsString();
                media_id = jsonObject.get("media_id").getAsString();


            }

            @Override
            public void setRequestFail() {
                hideProgress();
            }
        });

    }

    public void requestFailed() {
        exit.setEnabled(true);
        exit.setVisibility(View.VISIBLE);
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

}
