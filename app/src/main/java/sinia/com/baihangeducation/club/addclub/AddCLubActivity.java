package sinia.com.baihangeducation.club.addclub;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.editorclubactive.ClubEditorActiveActivity;
import sinia.com.baihangeducation.club.editorclubactive.ClubEditorModel;
import sinia.com.baihangeducation.mine.activity.UCentreBaseInfoActivity;
import sinia.com.baihangeducation.supplement.alertview.AlertViewContorller;
import sinia.com.baihangeducation.supplement.alertview.OnItemClickListener;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.tool.PicassoImageLoader;

public class AddCLubActivity extends BaseActivity implements GetAddOptionRequestListener, OnItemClickListener {

    private static final int COMPANY_LOGO = 11034;
    private AddClubModel addClubModel;
    private ImageView logo;
    private String img = "";
    private ClubEditorModel clubEditorModel;
    private TextView exit;
    private EditText nick_name;
    private LinearLayout club_type;
    private TextView club_type_text;
    private LinearLayout bellow_school;
    private TextView bellow_school_text;
    private String clickType;
    private String[] club_list;
    private String[] school_list;
    private AddClubList addClubList;
    private AlertViewContorller mSchoolOption;
    private AlertViewContorller mClubType;
    private String mClubTypeId;
    private String mSchoolId;
    private EditText phone;
    private EditText club_name;
    private EditText introduce;
    private String url;
    private String media_id;

    @Override
    public int initLayoutResID() {
        return R.layout.activity_add_club;
    }


    @Override
    protected void initData() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

//        Glide.with(context).load(R.drawable.new_eorrlogo).asBitmap().error(R.drawable.new_eorrlogo).centerCrop().into(new BitmapImageViewTarget(logo) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                RoundedBitmapDrawable circularBitmapDrawable =
//                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
////                circularBitmapDrawable.setCircular(true);
//                logo.setImageDrawable(circularBitmapDrawable);
//            }
//        });

    }

    @Override
    protected void initView() {
        logo = findViewById(R.id.logo);
        exit = findViewById(R.id.exit);
        nick_name = findViewById(R.id.nick_name);
        phone = findViewById(R.id.phone);
        club_name = findViewById(R.id.club_name);


        club_type = findViewById(R.id.club_type);
        club_type_text = findViewById(R.id.club_type_text);
        bellow_school = findViewById(R.id.bellow_school);
        bellow_school_text = findViewById(R.id.bellow_school_text);
        introduce = findViewById(R.id.introduce);
        findViewById(R.id.back).setOnClickListener(this);


        exit.setOnClickListener(this);
        logo.setOnClickListener(this);
        club_type.setOnClickListener(this);
        bellow_school.setOnClickListener(this);

        addClubModel = new AddClubModel(context);
        addClubModel.getClubOption(this);

        clubEditorModel = new ClubEditorModel(this);

    }


    @Override
    public void setRequestSuccess(AddClubList successMessage) {

        club_list = new String[successMessage.club_type_list.size()];
        school_list = new String[successMessage.school_list.size()];
        for (int i = 0; i < successMessage.club_type_list.size(); i++) {
            club_list[i] = successMessage.club_type_list.get(i).type_name;
        }
        for (int i = 0; i < successMessage.school_list.size(); i++) {
            school_list[i] = successMessage.school_list.get(i).school_name;
        }
        addClubList = successMessage;
    }

    @Override
    public void setRequestFail() {

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

                if (createClub()) {
                    showProgress();
                    toCreate();
                }
                break;
            case R.id.logo:

                hideEditTextInput();
                takePhoto(COMPANY_LOGO);
                break;
            case R.id.club_type:

                hideEditTextInput();
                clickType = "type";
                mClubType = new AlertViewContorller(club_type_text,
                        "社团类别", null, "取消", null, club_list,
                        context, AlertViewContorller.Style.ActionSheet, this);
                mClubType.setCancelable(true).show();
                break;
            case R.id.bellow_school:

                hideEditTextInput();
                clickType = "school";
                mSchoolOption = new AlertViewContorller(bellow_school_text,
                        "所属大学", null, "取消", null, school_list,
                        context, AlertViewContorller.Style.ActionSheet, this);
                mSchoolOption.setCancelable(true).show();
                break;
        }
    }

    private boolean createClub() {

        if (nick_name.getText().toString().trim().equals("")) {
            com.example.framwork.utils.Toast.getInstance().showErrorToast(context, "请输入姓名");
            return false;
        }
        if (phone.getText().toString().trim().equals("")) {
            com.example.framwork.utils.Toast.getInstance().showErrorToast(context, "请输入电话号码");
            return false;
        }
        if (club_name.getText().toString().trim().equals("")) {
            com.example.framwork.utils.Toast.getInstance().showErrorToast(context, "请输入社团名称");
            return false;
        }


        if (club_type_text.getText().toString().trim().equals("")) {
            com.example.framwork.utils.Toast.getInstance().showErrorToast(context, "请选择社团类别");
            return false;
        }


        if (bellow_school_text.getText().toString().trim().equals("")) {
            com.example.framwork.utils.Toast.getInstance().showErrorToast(context, "请选择所属大学");
            return false;
        }

        if (introduce.getText().toString().trim().equals("")) {
            com.example.framwork.utils.Toast.getInstance().showErrorToast(context, "请输入社团介绍");
            return false;
        }
        return true;

    }

    public void upPhone() {
        clubEditorModel.updataPhone(img, "1", new GetRequestListener() {
            @Override
            public void setRequestSuccess(String msg) {
                JsonObject jsonObject = (JsonObject) new JsonParser().parse(msg);
                url = jsonObject.get("url").getAsString();
                media_id = jsonObject.get("media_id").getAsString();
                System.out.println();
            }

            @Override
            public void setRequestFail() {
                AddCLubActivity.this.hideProgress();
            }
        });
    }

    public void toCreate() {
        addClubModel.pushClub(nick_name.getText().toString().trim(), phone.getText().toString().trim(),
                club_name.getText().toString().trim(), mClubTypeId, mSchoolId,
                introduce.getText().toString().trim(), url, media_id, new GetRequestListener() {

                    @Override
                    public void setRequestSuccess(String msg) {
                        AddCLubActivity.this.hideProgress();
                        AddCLubActivity.this.finish();
                    }

                    @Override
                    public void setRequestFail() {
                        AddCLubActivity.this.hideProgress();
                    }
                });

    }

    private final int IMAGE_PICKER = 9090;

    private void takePhoto(int type) {
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, IMAGE_PICKER);
//        SImagePicker.from(AddCLubActivity.this)
//                .pickMode(SImagePicker.MODE_IMAGE)
//                .showCamera(true).rowCount(3)
//                .cropFilePath(
//                        AppConfig.IMAGE_PATH + "/add_club_photo.png")
//                .forResult(type);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                img = images.get(0).path;
                upPhone();
                setPhoto(img);
                File appDir = new File(img);
                try {
                    MediaStore.Images.Media.insertImage(AddCLubActivity.this.getContentResolver(), appDir.getAbsolutePath().toString(), appDir.getName(), "");
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void onActivityResultsdfsdf(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == COMPANY_LOGO) {
            ArrayList<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
            if (pathList != null && pathList.size() != 0) {
                img = pathList.get(0);
                System.out.println("更新的图片地址" + img);
                upPhone();

                setPhoto(img);
                File appDir = new File(img);
                try {
                    MediaStore.Images.Media.insertImage(AddCLubActivity.this.getContentResolver(), appDir.getAbsolutePath().toString(), appDir.getName(), "");
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setPhoto(String avatar) {
        Glide.with(context).load(avatar).asBitmap().error(R.drawable.new_eorrlogo).centerCrop().into(new BitmapImageViewTarget(logo) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                logo.setImageDrawable(circularBitmapDrawable);
            }
        });
    }


    /**
     * 隐藏软键盘
     */
    protected void hideEditTextInput() {
        //隐藏键盘
        ((InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow((AddCLubActivity.this).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    @Override
    public void onItemClick(View view, List<String> mOthers, Object o, int position) {
        if (position == -1) {
            if (mClubType != null && mClubType.isShowing())
                mClubType.dismiss();
            if (mSchoolOption != null && mSchoolOption.isShowing())
                mSchoolOption.dismiss();
        } else {
            TextView view1 = (TextView) view;
            view1.setText(mOthers.get(position));
            if (clickType.equals("type")) {
                mClubTypeId = addClubList.club_type_list.get(position).type;
            }
            if (clickType.equals("school")) {
                mSchoolId = addClubList.school_list.get(position).school_id;
            }
        }
    }
}
