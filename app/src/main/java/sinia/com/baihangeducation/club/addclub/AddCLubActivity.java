package sinia.com.baihangeducation.club.addclub;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import sinia.com.baihangeducation.club.club.interfaces.GetRequestForSchoolListener;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.editorclubactive.ClubEditorActiveActivity;
import sinia.com.baihangeducation.club.editorclubactive.ClubEditorModel;
import sinia.com.baihangeducation.home.adapter.PhoneGlideImageLoader;
import sinia.com.baihangeducation.mine.activity.UCentreBaseInfoActivity;
import sinia.com.baihangeducation.supplement.alertview.AlertViewContorller;
import sinia.com.baihangeducation.supplement.alertview.OnItemClickListener;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.PicassoImageLoader;

import static io.github.changjiashuai.ImagePicker.REQUEST_CODE_PICK;

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
    private io.github.changjiashuai.ImagePicker.Config mPhoneConfig1;

    @Override
    public int initLayoutResID() {
        return R.layout.activity_add_club;
    }


    @Override
    protected void initData() {

        mPhoneConfig1 = new io.github.changjiashuai.ImagePicker.Config(new PhoneGlideImageLoader());
        mPhoneConfig1.multiMode(false);
//        config.selectLimit(1);
        mPhoneConfig1.showCamera(true);
//        mPhoneConfig.crop(true);
//        mPhoneConfig.cropStyle(CropImageView.CIRCLE);
//        mPhoneConfig.saveRectangle(false);//裁剪后是否矩形保存图片
        mPhoneConfig1.focusWidth(800);
        mPhoneConfig1.focusHeight(800);
//        mPhoneConfig1.outPutX(800);
//        mPhoneConfig1.outPutY(800);


        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(1200);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(1200);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
//        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

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
                exit.setEnabled(false);
                exit.setClickable(false);
                if (createClub()) {
                    showProgress();
                    toCreate("0");
                } else {
                    exit.setEnabled(true);
                    exit.setClickable(true);
                }
                break;
            case R.id.logo:

                hideEditTextInput();
                takePhoto(COMPANY_LOGO);
                break;
            case R.id.club_type:

                hideEditTextInput();
                clickType = "type";
             //   System.out.println("typelength===" + club_list.length);
                mClubType = new AlertViewContorller(club_type_text,
                        "社团类别", null, "取消", null, club_list,
                        AddCLubActivity.this, AlertViewContorller.Style.ActionSheet, this);
                mClubType.setCancelable(true).show();
                break;
            case R.id.bellow_school:
                Goto.toEditMyResumeEducationExpChoiceSchool(context, "club");
//                hideEditTextInput();
//                clickType = "school";
//                mSchoolOption = new AlertViewContorller(bellow_school_text,
//                        "所属大学", null, "取消", null, school_list,
//                        context, AlertViewContorller.Style.ActionSheet, this);
//                mSchoolOption.setCancelable(true).show();
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (bellow_school_text != null && !AppConfig.SCHOOLNAME.equals("")) {
            bellow_school_text.setText(AppConfig.SCHOOLNAME);
            mSchoolId = AppConfig.SCHOOLNAMEID;
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

            }

            @Override
            public void setRequestFail() {
                AddCLubActivity.this.hideProgress();
            }
        });
    }

    //  Toast.getInstance().showErrorToast(activity, error);
    public void toCreate(String force_quit) {
        addClubModel.pushClub(force_quit, nick_name.getText().toString().trim(), phone.getText().toString().trim(),
                club_name.getText().toString().trim(), mClubTypeId, mSchoolId,
                introduce.getText().toString().trim(), url, media_id, new GetRequestForSchoolListener() {

                    @Override
                    public void setRequestSuccess(String msg) {
                        AddCLubActivity.this.hideProgress();
                        AddCLubActivity.this.finish();
                    }

                    @Override
                    public void setRequestFail(String msg) {
                        if (msg.equals("9108")) {
                            getCenterCancelDialogShow();
                        }
                        exit.setEnabled(true);
                        exit.setClickable(true);
                        AddCLubActivity.this.hideProgress();
                    }
                });

    }

    private final int IMAGE_PICKER = 9090;

    private void takePhoto(int type) {

        io.github.changjiashuai.ImagePicker.getInstance().pickImageForResult(this, mPhoneConfig1);
        io.github.changjiashuai.ImagePicker.RESULT_CODE_BACK_CLICK = 0;

//        Intent intent = new Intent(this, ImageGridActivity.class);
//        startActivityForResult(intent, IMAGE_PICKER);
//        SImagePicker.from(AddCLubActivity.this)
//                .pickMode(SImagePicker.MODE_IMAGE)
//                .showCamera(true).rowCount(3)
//                .cropFilePath(
//                        AppConfig.IMAGE_PATH + "/add_club_photo.png")
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
                                MediaStore.Images.Media.insertImage(AddCLubActivity.this.getContentResolver(), appDir.getAbsolutePath().toString(), appDir.getName(), "");
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
                        Toast.makeText(this, "稍后压缩", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }


    protected void onActivityResultForWorld(int requestCode, int resultCode, Intent data) {
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
           //     System.out.println("更新的图片地址" + img);
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
        Glide.with(AddCLubActivity.this).load(avatar).asBitmap().error(R.drawable.new_eorrlogo).centerCrop().into(new BitmapImageViewTarget(logo) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
                logo.setImageDrawable(circularBitmapDrawable);
            }
        });
    }


    /**
     * 隐藏软键盘
     */
    protected void hideEditTextInput() {
        //隐藏键盘
        ((InputMethodManager) AddCLubActivity.this.getSystemService(INPUT_METHOD_SERVICE))
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

    @Override
    protected void onDestroy() {
        AppConfig.SCHOOLNAME = "";
        super.onDestroy();

    }

    public void getCenterCancelDialogShow() {
        final Dialog dialog = new Dialog(AddCLubActivity.this, com.example.framwork.R.style.custom_cancel_dialog);
        dialog.setContentView(R.layout.clcub_join_dialog_apply_update_school);
        Window dialogWindow = dialog.getWindow();

        dialogWindow.findViewById(R.id.club_join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCreate("1");
                dialog.cancel();
            }
        });
        dialogWindow.findViewById(R.id.club_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        //dialogWindow.setWindowAnimations(R.style.mystyle);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = AddCLubActivity.this.getResources().getDisplayMetrics().widthPixels;
        lp.alpha = 1.0f;
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }

}
