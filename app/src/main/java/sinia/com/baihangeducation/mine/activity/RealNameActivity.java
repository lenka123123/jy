package sinia.com.baihangeducation.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framwork.glide.ImageLoaderUtils;
import com.example.framwork.utils.DensityUtil;
import com.example.framwork.utils.FileUtil;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;
import com.yanzhenjie.nohttp.Logger;

import java.util.ArrayList;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.AppConfig;
import com.mcxtzhang.swipemenulib.info.bean.RealNameInfo;
import sinia.com.baihangeducation.mine.presenter.RealNamePresenter;
import sinia.com.baihangeducation.mine.view.IRealNameView;

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

    private int itemImgWidth;
    private int itemImgHeigh;

    @Override
    public int initLayoutResID() {
        return R.layout.realname_authentication;
    }

    @Override
    protected void initData() {

        presenter = new RealNamePresenter(context, this);
        presenter.getRealNameInfo();

        mCommonTitle.setCenterText(R.string.realname);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
        int width = DensityUtil.getScreenWidth(context);
        itemImgWidth = width * 90 / 100;
        itemImgHeigh = (int) (itemImgWidth / 1.6);

        ViewGroup.LayoutParams params = mIdCardOn.getLayoutParams();
        params.height = itemImgHeigh;
        params.width = itemImgWidth;

        mIdCardOn.setLayoutParams(params);
        mIdCardOff.setLayoutParams(params);

        ViewGroup.LayoutParams paramsid = mIdCardOnHand.getLayoutParams();
        paramsid.height = width / 2;
        paramsid.width = (int) (width / 2 / 1.1);
        mIdCardOnHand.setLayoutParams(paramsid);
    }

    @Override
    protected void initView() {
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
                presenter.doRealNameAuthentication();
                break;
        }
    }

    private void takePhoto(int type) {
        switch (type) {
            case IDCARD_ON:
                SImagePicker
                        .from(context)
                        .pickMode(SImagePicker.MODE_AVATAR)
                        .showCamera(true).rowCount(3)
                        .cropFilePath(
                                AppConfig.IMAGE_PATH + "/idimg_on.png")
                        .forResult(type);
                break;
            case IDCARD_OFF:
                SImagePicker
                        .from(context)
                        .pickMode(SImagePicker.MODE_AVATAR)
                        .showCamera(true).rowCount(3)
                        .cropFilePath(
                                AppConfig.IMAGE_PATH + "/idimg_off.png")
                        .forResult(type);
                break;
            case IDCARD_ONHEAD:
                SImagePicker
                        .from(context)
                        .pickMode(SImagePicker.MODE_AVATAR)
                        .showCamera(true).rowCount(3)
                        .cropFilePath(
                                AppConfig.IMAGE_PATH + "/idimg_onhead.png")
                        .forResult(type);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                ImageLoaderUtils.display(context, mIdCardOnHand, pathList.get(0), R.drawable.logo, true);
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
        Intent intent = new Intent(context, RealNameSuccessActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void submitFailed() {

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
