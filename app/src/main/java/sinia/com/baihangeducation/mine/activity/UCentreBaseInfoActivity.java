package sinia.com.baihangeducation.mine.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;

import io.github.changjiashuai.widget.CropImageView;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;

import io.github.changjiashuai.ImagePicker;

import io.github.changjiashuai.ImagePicker;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.framwork.utils.SpCommonUtils;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;
import com.mcxtzhang.swipemenulib.utils.BitmapSave;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.home.adapter.GlideImageLoader;
import sinia.com.baihangeducation.home.adapter.PhoneGlideImageLoader;
import sinia.com.baihangeducation.supplement.alertview.AlertViewContorller;
import sinia.com.baihangeducation.supplement.alertview.OnItemClickListener;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.mine.presenter.UCentreBaseInfoPresenter;
import sinia.com.baihangeducation.mine.view.IUCentreBaseInfoView;

import static io.github.changjiashuai.ImagePicker.REQUEST_CODE_PICK;

/**
 * 个人中心编辑资料页面
 */

public class UCentreBaseInfoActivity extends BaseActivity implements OnItemClickListener, IUCentreBaseInfoView {
    private static final int COMPANY_LOGO = 11034;
    private static final int IDCARD_ONHEAD = 1000;
    public static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private AlertViewContorller mAlertViewContorller;//避免创建重复View，先创建View，然后需要的时候show出来，推荐这个做法
    public static final int TAKE_PHOTO = 1111;
    public static final int CROP_PHOTO = 21;
    public static final int CHOOSE_PHOTO = 2;
    private Uri imageUri;

    private ImageView mUCentreImage;
    private EditText mUCentreNickName;
    private EditText mUCentreSlogn;
    private TextView mUCentreGender;
    private EditText mUCentreEmail;
    private TextView mUCentreConfirm;

    private UCentreBaseInfoPresenter presenter;
    private String img = "";
    private MyApplication application;

    private String avatar;
    private String nickname;
    private String slogan;
    private String gender;
    private String email;
    private String[] genderes = {"男", "女"};
    private Context activity;
    private File file;
    private String needSavePath;
    private ImagePicker.Config mPhoneConfig1;

    @Override
    public int initLayoutResID() {
        return R.layout.ucercentre_baseinfo;
    }

    @Override
    protected void initData() {
        activity = this;
        avatar = (String) SpCommonUtils.get(this, AppConfig.FINALUAVATAR, "");
        nickname = (String) SpCommonUtils.get(this, AppConfig.FINALNICKNAME, "");
        slogan = (String) SpCommonUtils.get(this, AppConfig.FINALSLOGAN, "");
        gender = (String) SpCommonUtils.get(this, AppConfig.FINALGENDEREEE, "");
        email = (String) SpCommonUtils.get(this, AppConfig.FINALEMEMAIL, "");

        mCommonTitle.setCenterText(R.string.ucentre_baseinfo);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        presenter = new UCentreBaseInfoPresenter(context, this);

        initBaseInfo();


    }


    public void setPhoto(String avatar) {
        Glide.with(context).load(avatar).asBitmap().error(R.drawable.new_eorrlogo).centerCrop().into(new BitmapImageViewTarget(mUCentreImage) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);

                needSavePath = BitmapSave.saveBitmap(activity, resource);
                mUCentreImage.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    private void initBaseInfo() {

        if (!AppConfig.LOGINPHOTOTPATH.equals("")) {
            avatar = AppConfig.LOGINPHOTOTPATH;
            setPhoto(avatar);
        }


        if (!nickname.isEmpty() && !nickname.equals("") && nickname != "")
            mUCentreNickName.setText(nickname);
        if (!slogan.isEmpty() && !slogan.equals("") && slogan != "")
            mUCentreSlogn.setText(slogan);
        if (gender != null && !gender.equals("")) {
            if (Integer.valueOf(gender) == 1) {
                mUCentreGender.setText("男");
            } else if (Integer.valueOf(gender) == 2) {
                mUCentreGender.setText("女");
            } else mUCentreGender.setText("未知");
        }


        if (!email.isEmpty() && !email.equals("") && email != "")
            mUCentreEmail.setText(email);
    }

    @Override
    protected void initView() {
        mUCentreImage = $(R.id.ucentre_img);
        mUCentreNickName = $(R.id.ucentre_nickname);
        mUCentreSlogn = $(R.id.ucentre_slogn);
        mUCentreGender = $(R.id.ucentre_gender);
        mUCentreEmail = $(R.id.ucentre_email);
        mUCentreConfirm = $(R.id.ucentre_confirm);

        mUCentreImage.setOnClickListener(this);
        mUCentreNickName.setOnClickListener(this);
        mUCentreSlogn.setOnClickListener(this);
        mUCentreGender.setOnClickListener(this);
        mUCentreEmail.setOnClickListener(this);
        mUCentreConfirm.setOnClickListener(this);


        mPhoneConfig1 = new ImagePicker.Config(new PhoneGlideImageLoader());
        mPhoneConfig1.multiMode(false);
//        config.selectLimit(1);
        mPhoneConfig1.showCamera(true);
        mPhoneConfig1.crop(true);
        mPhoneConfig1.cropStyle(CropImageView.CIRCLE);
//        mPhoneConfig.saveRectangle(false);//裁剪后是否矩形保存图片
        mPhoneConfig1.focusWidth(800);
        mPhoneConfig1.focusHeight(800);
//        mPhoneConfig.outPutX(800);
//        mPhoneConfig.outPutY(800);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ucentre_img:
                //头像

//                startActivity(new Intent(context, AppCompatActivityPhone.class));


                takePhoto(COMPANY_LOGO);
                break;
            case R.id.ucentre_nickname:
                hideSoftInput(mUCentreSlogn);
                hideSoftInput(mUCentreEmail);
                showSoftInput(mUCentreNickName);
                break;
            case R.id.ucentre_slogn:
                hideSoftInput(mUCentreNickName);
                hideSoftInput(mUCentreEmail);
                showSoftInput(mUCentreSlogn);
                break;
            case R.id.ucentre_gender:
                hideSoftInput(mUCentreNickName);
                hideSoftInput(mUCentreSlogn);
                hideSoftInput(mUCentreEmail);
                istakePhoto = false;
                mAlertViewContorller = new AlertViewContorller(mUCentreGender, "选择类型", null, "取消", null, genderes,
                        context, AlertViewContorller.Style.ActionSheet, this);
                mAlertViewContorller.setCancelable(true).show();
                break;
            case R.id.ucentre_email:
                hideSoftInput(mUCentreNickName);
                hideSoftInput(mUCentreSlogn);
                showSoftInput(mUCentreEmail);
                break;
            case R.id.ucentre_confirm:
                showLoading();
                presenter.updataUCentreBaseInfo();
                break;
        }
    }

    public void saveBitmap(Bitmap bitmap) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "zxing_image");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "zxing_image" + ".png";
        file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(this.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));
    }

    private boolean istakePhoto = false;
    private final int IMAGE_PICKER = 9090;

    private void takePhoto(int type) {

        ImagePicker.getInstance().pickImageForResult(this, mPhoneConfig1);
        ImagePicker.RESULT_CODE_BACK_CLICK = 0;

//        SImagePicker
//                .from(UCentreBaseInfoActivity.this)
//                .pickMode(SImagePicker.MODE_IMAGE)
//                .showCamera(true).rowCount(3)
//                .cropFilePath(
//                        AppConfig.IMAGE_PATH + "/user_logo_my.png")
//                .forResult(type);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("requestCode==" + requestCode + "resultCode==" + resultCode);
// requestCode==1000resultCode==1004
        if (requestCode == REQUEST_CODE_PICK && ImagePicker.RESULT_CODE_BACK_CLICK == 0) {
            if (data != null) {
                if (resultCode == io.github.changjiashuai.ImagePicker.RESULT_CODE_ITEMS) {
                    boolean isOrigin = data.getBooleanExtra(io.github.changjiashuai.ImagePicker.EXTRA_IS_ORIGIN, false);
                    if (!isOrigin) {
                        ArrayList<io.github.changjiashuai.bean.ImageItem> imageItems = io.github.changjiashuai.ImagePicker.getInstance().getSelectedImages();
                        if (imageItems.size() > 0) {
                            io.github.changjiashuai.bean.ImageItem imageItem = imageItems.get(0);
                            img = imageItems.get(0).path;
                            setPhoto(img);
                            File appDir = new File(img);
                            try {
                                MediaStore.Images.Media.insertImage(UCentreBaseInfoActivity.this.getContentResolver(), appDir.getAbsolutePath().toString(), appDir.getName(), "");
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

//    protected void onActivityResultaaa(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
//            if (data != null && requestCode == IMAGE_PICKER) {
//                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                if (images == null || images.size() < 1) return;
//                img = images.get(0).path;
//                setPhoto(img);
//                File appDir = new File(img);
//                try {
//                    MediaStore.Images.Media.insertImage(UCentreBaseInfoActivity.this.getContentResolver(), appDir.getAbsolutePath().toString(), appDir.getName(), "");
//                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }


    protected void onActivityResulsdfst(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == COMPANY_LOGO) {
            ArrayList<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
            if (pathList != null && pathList.size() != 0) {
                img = pathList.get(0);
                Log.i("图片地址", pathList.get(0));
                setPhoto(pathList.get(0));
                File appDir = new File(img);
                try {
                    MediaStore.Images.Media.insertImage(UCentreBaseInfoActivity.this.getContentResolver(), appDir.getAbsolutePath().toString(), appDir.getName(), "");
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


    @Override
    public void onItemClick(View view, List<String> mOthers, Object o, int position) {
        System.out.println("找view" + mOthers.get(0) + "pppp" + position);


        if (istakePhoto) {

            onClickPhoto(position);

            istakePhoto = false;
        }

        if (position == -1) {
            mAlertViewContorller.dismiss();
        } else {
            if (view instanceof TextView) {
                TextView view1 = (TextView) view;
                view1.setText(mOthers.get(position));
            }

        }
    }


    private void onClickPhoto(int position) {
        if (position == 1) {

            openCamera(UCentreBaseInfoActivity.this);

        } else {
            if (ContextCompat.checkSelfPermission(UCentreBaseInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(UCentreBaseInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                openAlbum();
            }
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    public void openCamera(Activity activity) {
        //獲取系統版本
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                    "yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            File tempFile = new File(Environment.getExternalStorageDirectory(),
                    filename + ".jpg");

            if (currentapiVersion < 24) {
                // 从文件中创建uri
                imageUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                //检查是否有存储权限，以免崩溃
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    android.widget.Toast.makeText(activity, "请开启存储权限", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }
                imageUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        activity.startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
    }

    /*
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    android.widget.Toast.makeText(this, "You denied the permission", android.widget.Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }


        Log.i("图片地址个人中心22", imagePath);
//        displayImage(imagePath); // 根据图片路径显示图片
    }


    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mAlertViewContorller != null && mAlertViewContorller.isShowing()) {
                mAlertViewContorller.dismiss();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public String getUCentreImage() {
        return img;
    }

    @Override
    public String getUCentreNickName() {
        return mUCentreNickName.getText().toString().trim();
    }

    @Override
    public String getUCentreSlogan() {
        return mUCentreSlogn.getText().toString().trim();
    }

    @Override
    public String getUCentreGender() {
        return mUCentreGender.getText().toString().trim().equals("男") ? 1 + "" : 2 + "";
    }

    @Override
    public String getUCentreEmail() {
//        Log.i("编辑页面邮箱",mUCentreEmail.getText().toString().trim());
        return mUCentreEmail.getText().toString().trim();
    }

    @Override
    public void upDataUCentreBaseInfoSuccess() {
        if (needSavePath != null && needSavePath.length() > 1) {
            AppConfig.LOGINPHOTOTPATH = needSavePath;
            SpCommonUtils.put(activity, AppConfig.FINAL_SAVE_PHOTO_PATH, needSavePath);
            JMessageClient.updateUserAvatar(new File(needSavePath), new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    System.out.println(i + "更新用户图片" + s);
                }
            });
        }
        SpCommonUtils.put(context, AppConfig.FINAL_NUM_FULL_HULP_NICKNAME, mUCentreNickName.getText().toString());

        hideLoading();
        UCentreBaseInfoActivity.this.finish();
//
//        new AlertDialog.Builder(context).setTitle("提示！").setMessage("修改资料成功,请重新登录").setPositiveButton("登录", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Goto.toLogin(context);
//                UCentreBaseInfoActivity.this.finish();
//            }
//        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                UCentreBaseInfoActivity.this.finish();
//            }
//        }).show();


        //   presenter.login(UCentreBaseInfoActivity.this);

    }

    @Override
    public void upDataUCentreBaseInfoFail() {

    }


}
