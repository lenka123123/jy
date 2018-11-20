package sinia.com.baihangeducation.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.framwork.glide.ImageLoaderUtils;
import com.example.framwork.utils.DensityUtil;
import com.example.framwork.utils.FileUtil;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;
import com.mcxtzhang.swipemenulib.utils.BitmapSave;
import com.yanzhenjie.nohttp.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import io.github.changjiashuai.ImagePicker;
import io.github.changjiashuai.widget.CropImageView;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.home.adapter.PhoneGlideImageLoader;
import sinia.com.baihangeducation.mine.model.AccountManger;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.AppConfig;

import com.mcxtzhang.swipemenulib.info.bean.RealNameInfo;

import sinia.com.baihangeducation.mine.presenter.RealNamePresenter;
import sinia.com.baihangeducation.mine.view.IRealNameView;

import static io.github.changjiashuai.ImagePicker.REQUEST_CODE_PICK;

/**
 * 实名认证页面
 */

public class RealNameActivity extends BaseActivity implements IRealNameView {

    private static final int IDCARD_ON = 100;
    private static final int IDCARD_OFF = 101;
    private static final int IDCARD_ONHEAD = 102;
    private String img_on = "";
    private String img_off = "";
    private String img_onhand = "";

    private EditText mInputName;            //输入姓名
    private EditText mInputIdCard;          //输入身份证号码
    private ImageView mIdCardOn;            //身份证正面
    private ImageView mIdCardOff;           //身份证背面
    private ImageView mIdCardOnHand;        //手持身份证
    private TextView mConfirm;              //确认按钮

    private RealNamePresenter presenter;
    private ImagePicker.Config mPhoneConfig1;

    @Override
    public int initLayoutResID() {
        return R.layout.realname_authentication;
    }

    @Override
    protected void initData() {

        mInputName = $(R.id.realname_name);
        mInputIdCard = $(R.id.realname_idcardnum);
        mIdCardOn = $(R.id.realname_on);
        mIdCardOff = $(R.id.realname_off);
        mIdCardOnHand = $(R.id.realname_onhand);
        mConfirm = $(R.id.realname_confirm);

        mIdCardOn.setOnClickListener(this);
        mIdCardOff.setOnClickListener(this);
        mIdCardOnHand.setOnClickListener(this);
        mConfirm.setOnClickListener(this);

        presenter = new RealNamePresenter(context, this);
        presenter.getRealNameInfo();

        mCommonTitle.setCenterText(R.string.realname);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));


        mPhoneConfig1 = new ImagePicker.Config(new PhoneGlideImageLoader());
        mPhoneConfig1.multiMode(false);
//        config.selectLimit(1);
        mPhoneConfig1.showCamera(true);
        mPhoneConfig1.crop(false);
//        mPhoneConfig1.cropStyle(CropImageView.RECTANGLE);
//        mPhoneConfig.saveRectangle(false);//裁剪后是否矩形保存图片
//        mPhoneConfig1.focusWidth(1000);
//        mPhoneConfig1.focusHeight(800);
////        mPhoneConfig.outPutX(800);
//        mPhoneConfig.outPutY(800);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.realname_on:
                takePhoto(IDCARD_ON);
                break;
            case R.id.realname_off:
                takePhoto(IDCARD_OFF);
                break;
            case R.id.realname_onhand:
                takePhoto(IDCARD_ONHEAD);
                break;
            case R.id.realname_confirm:

                if (!AccountManger.checkRealName(context, getRealName(), getIDNum(), getIDCard_On(), getIDCard_Off(), getIDCard_OnHand())) {
                    return;
                }
                showProgress();
                presenter.doRealNameAuthentication();
                break;
        }
    }

    public int type = 0;

    private void takePhoto(int typeis) {
        switch (typeis) {
            case IDCARD_ON:
                ImagePicker.getInstance().pickImageForResult(this, mPhoneConfig1);
                type = 1;
                ImagePicker.RESULT_CODE_BACK_CLICK = 0;
//                SImagePicker
//                        .from(RealNameActivity.this)
//                        .pickMode(SImagePicker.MODE_IMAGE)
//                        .showCamera(true).rowCount(3)
//                        .cropFilePath(
//                                AppConfig.IMAGE_PATH + "/idimg_on.png")
//                        .forResult(type);
                break;
            case IDCARD_OFF:
                ImagePicker.getInstance().pickImageForResult(this, mPhoneConfig1);
                type = 2;
                ImagePicker.RESULT_CODE_BACK_CLICK = 0;
//                SImagePicker
//                        .from(RealNameActivity.this)
//                        .pickMode(SImagePicker.MODE_IMAGE)
//                        .showCamera(true).rowCount(3)
//                        .cropFilePath(
//                                AppConfig.IMAGE_PATH + "/idimg_off.png")
//                        .forResult(type);
                break;
            case IDCARD_ONHEAD:
                ImagePicker.getInstance().pickImageForResult(this, mPhoneConfig1);
                ImagePicker.RESULT_CODE_BACK_CLICK = 0;
                type = 3;
//                SImagePicker
//                        .from(RealNameActivity.this)
//                        .pickMode(SImagePicker.MODE_IMAGE)
//                        .showCamera(true).rowCount(3)
//                        .cropFilePath(
//                                AppConfig.IMAGE_PATH + "/idimg_onhead.png")
//                        .forResult(type);
                break;
        }
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
                            String img = imageItems.get(0).path;
                            if (type == 1) {
                                setPhoto(img, type, mIdCardOn);
                            } else if (type == 2) {
                                setPhoto(img, type, mIdCardOff);
                            } else if (type == 3) {
                                setPhoto(img, type, mIdCardOnHand);
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


    public void setPhoto(String avatar, int type, ImageView imageView) {
        if (type == 1) {
            img_on = avatar;
            mIdCardOn.setImageBitmap(BitmapFactory.decodeFile(avatar));
        } else if (type == 2) {
            img_off = avatar;
            mIdCardOff.setImageBitmap(BitmapFactory.decodeFile(avatar));
        } else if (type == 3) {
            img_onhand = avatar;
            mIdCardOnHand.setImageBitmap(BitmapFactory.decodeFile(avatar));
        }

//        Glide.with(context).load(avatar).asBitmap().error(R.drawable.new_eorrlogo).centerCrop().into(new BitmapImageViewTarget(imageView) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                RoundedBitmapDrawable circularBitmapDrawable =
//                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
////                circularBitmapDrawable.setCircular(true);
//
//                String needSavePath = BitmapSave.saveBitmap(RealNameActivity.this, resource);
//                if (type == 1) {
//                    img_on = avatar;
//                    mIdCardOn.setImageDrawable(circularBitmapDrawable);
//                } else if (type == 2) {
//                    img_off = avatar;
//                    mIdCardOff.setImageDrawable(circularBitmapDrawable);
//                } else if (type == 3) {
//                    img_onhand = avatar;
//                    mIdCardOnHand.setImageDrawable(circularBitmapDrawable);
//                }
//
//            }
//        });
    }

    protected void onActivityResultdddd(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == IDCARD_ON) {
            ArrayList<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
            if (pathList != null && pathList.size() != 0) {
                Logger.d(FileUtil.getInstance().getFileSize(context, pathList.get(0)));
                img_on = pathList.get(0);
                Log.i("图片地址", pathList.get(0));
                ImageLoaderUtils.display(context, mIdCardOn, pathList.get(0), R.drawable.logo, true);
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == IDCARD_OFF) {
            ArrayList<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
            if (pathList != null && pathList.size() != 0) {
                Logger.d(FileUtil.getInstance().getFileSize(context, pathList.get(0)));
                img_off = pathList.get(0);
                ImageLoaderUtils.display(context, mIdCardOff, pathList.get(0), R.drawable.logo, true);
            }
        } else {
            ArrayList<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
            if (pathList != null && pathList.size() != 0) {
                Logger.d(FileUtil.getInstance().getFileSize(context, pathList.get(0)));
                img_onhand = pathList.get(0);
                Glide.with(RealNameActivity.this).load(pathList.get(0)).skipMemoryCache(true).into(mIdCardOnHand);
//                ImageLoaderUtils.display(context, mIdCardOnHand, pathList.get(0), R.drawable.logo, true);
            }
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public String getRealName() {
        return mInputName.getText().toString().trim();
    }

    @Override
    public String getIDNum() {
        return mInputIdCard.getText().toString().trim();
    }

    @Override
    public String getIDCard_On() {
        return img_on;
    }

    @Override
    public String getIDCard_Off() {
        return img_off;
    }

    @Override
    public String getIDCard_OnHand() {
        return img_onhand;
    }

    @Override
    public void submitSucess() {
        hideLoading();
        Intent intent = new Intent(context, RealNameSuccessActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void submitFailed() {
        hideLoading();
    }

    @Override
    public void getRealNameSuccess(RealNameInfo info) {

        Log.i("图片内容", "图片" + info.idcard_face_img + "图片");

        if (!(info.idcard_face_img).isEmpty()) {
            mInputName.setText(info.realname);
            mInputIdCard.setText(info.idcard_no);
            ImageLoaderUtils.display(context, mIdCardOn, info.idcard_face_img, R.drawable.logo, true);
            ImageLoaderUtils.display(context, mIdCardOff, info.idcard_opposite_img, R.drawable.logo, true);
            ImageLoaderUtils.display(context, mIdCardOnHand, info.idcard_hand_img, R.drawable.logo, true);


            if (info.real_status == 1) {
                mInputName.setFocusable(true);
                mInputIdCard.setFocusable(true);
                mIdCardOn.setEnabled(true);
                mIdCardOff.setEnabled(true);
                mIdCardOnHand.setEnabled(true);
                mConfirm.setEnabled(true);
                mConfirm.setText(R.string.confirm);
                mConfirm.setBackground(getResources().getDrawable(R.drawable.btn_selector));
            } else {

                mInputName.setFocusable(false);
                mInputIdCard.setFocusable(false);
                mIdCardOn.setEnabled(false);
                mIdCardOff.setEnabled(false);
                mIdCardOnHand.setEnabled(false);
                mConfirm.setEnabled(false);
                mConfirm.setText(info.real_status_name);
                mConfirm.setBackground(getResources().getDrawable(R.drawable.textview_unchoicw_round));
            }
        }
    }
}
