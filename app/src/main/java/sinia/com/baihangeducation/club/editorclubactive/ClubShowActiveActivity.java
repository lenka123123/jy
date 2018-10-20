package sinia.com.baihangeducation.club.editorclubactive;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.editorclubactive.model.ActiveInfoData;
import sinia.com.baihangeducation.club.editorclubactive.model.ObtainActiveInfoListener;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.tool.PickerUtils;

public class ClubShowActiveActivity extends BaseActivity {

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
    }

    @Override
    protected void initView() {
        photo = findViewById(R.id.photo);
        title = findViewById(R.id.title);
        type = findViewById(R.id.type);
        include_club = findViewById(R.id.include_club);
        man_num = findViewById(R.id.man);
        woman_num = findViewById(R.id.woman);
        free = findViewById(R.id.free);
        info = findViewById(R.id.info);
        include_club_ll = findViewById(R.id.include_club_ll);
        join = findViewById(R.id.join);

        active_start_time = findViewById(R.id.active_start_time);
        active_start_time_text = findViewById(R.id.active_start_time_text);
        active_stop_time = findViewById(R.id.active_stop_time);
        active_stop_time_text = findViewById(R.id.active_stop_time_text);
        active_address = findViewById(R.id.active_address);
        active_address_text = findViewById(R.id.active_address_text);


//        active_start_time.setOnClickListener(this);
//        active_stop_time.setOnClickListener(this);
//        active_address.setOnClickListener(this);
        join.setOnClickListener(this);
    }

    private void showInfo(ActiveInfoData activeInfoData) {
        if (activeInfoData.is_apply.equals("1")) {
//            1：已参加
            join.setText("已加入");
            join.setClickable(false);
        } else if (activeInfoData.is_apply.equals("0")) {
            // 0：未报名
            join.setText("加入");
            join.setClickable(true);
        }

        if (activeInfoData.type.equals("2")) {
            include_club_ll.setVisibility(View.GONE);
        } else {
            include_club_ll.setVisibility(View.VISIBLE);
        }

        Glide.with(context).load(activeInfoData.cover).error(R.drawable.logo).into(photo);
        title.setText(activeInfoData.name);
        type.setText(activeInfoData.type_name);
        include_club.setText(activeInfoData.club_name);
        active_start_time_text.setText(activeInfoData.begin_time);
        active_stop_time_text.setText(activeInfoData.end_time);
        man_num.setText(activeInfoData.boy_num);
        woman_num.setText(activeInfoData.girl_num);
        free.setText(activeInfoData.expenditure);
        info.setText(activeInfoData.introduce);
        active_address_text.setText(activeInfoData.addr);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.join:
                clubEditorModel.applyActive(activity_id, new GetRequestListener() {
                    @Override
                    public void setRequestSuccess(String msg) {
                        join.setClickable(false);
                        join.setText("已加入");
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


        }
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
        ((InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow((ClubShowActiveActivity.this).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }
}
