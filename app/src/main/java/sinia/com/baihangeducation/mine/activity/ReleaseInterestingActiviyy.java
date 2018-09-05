package sinia.com.baihangeducation.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.framwork.glide.ImageLoaderUtils;
import com.example.framwork.utils.FileUtil;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;
import com.yanzhenjie.nohttp.Logger;

import java.util.ArrayList;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.mine.presenter.ReleaseHelpEachOtherPresenter;
import sinia.com.baihangeducation.mine.view.IReleaseView;
import com.mcxtzhang.swipemenulib.utils.Constants;

/**
 * Created by Administrator on 2018/4/10.
 */

public class ReleaseInterestingActiviyy extends BaseActivity implements IReleaseView {
    private static final int ARTICLEIMG = 200;
    private String articleimg;

    private RelativeLayout layout;
    private ImageView mOutImg;
    private ImageView mInImg;
    private EditText mTitle;
    private EditText mContant;
    private TextView mSubmit;

    private ReleaseHelpEachOtherPresenter presenter;

    @Override
    public int initLayoutResID() {
        return R.layout.releaseinteresting;
    }

    @Override
    protected void initData() {

        mCommonTitle.setCenterText(R.string.myrelease_interesting);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
        presenter = new ReleaseHelpEachOtherPresenter(context, this);
    }

    @Override
    protected void initView() {
        layout = $(R.id.releaseinteresting_outimglayout);
        mOutImg = $(R.id.releaseinteresting_outimg);
        mInImg = $(R.id.releaseinteresting_inimg);
        mTitle = $(R.id.releaseinteresting_title);
        mContant = $(R.id.releaseinteresting_contant);
        mSubmit = $(R.id.releaseinteresting_confirm);

        mOutImg.setOnClickListener(this);
        mTitle.setOnClickListener(this);
        mContant.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.releaseinteresting_outimg:
                takePhoto(ARTICLEIMG);
                break;
            case R.id.releaseinteresting_confirm:
                presenter.doReleaseInteresting();
                break;
        }
    }

    private void takePhoto(int type) {
        SImagePicker
                .from(context)
                .pickMode(SImagePicker.MODE_AVATAR)
                .showCamera(true).rowCount(3)
                .cropFilePath(
                        AppConfig.IMAGE_PATH + "/articl_img.png")
                .forResult(type);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == ARTICLEIMG) {
            ArrayList<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
            if (pathList != null && pathList.size() != 0) {
                Logger.d(FileUtil.getInstance().getFileSize(context, pathList.get(0)));
                articleimg = pathList.get(0);
                Log.i("图片地址", pathList.get(0));
                ImageLoaderUtils.display(context, mOutImg, pathList.get(0), R.drawable.logo, true);
                mInImg.setVisibility(View.GONE);
                layout.setBackground(null);
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
    public String getTab() {
        return Constants.RELEASE_TAB_FUN;
    }

    @Override
    public String getType() {
        return "";
    }

    @Override
    public String getNeedPeopleNum() {
        return "";
    }

    @Override
    public String getIsPay() {
        return "";
    }

    @Override
    public String getSex() {
        return "";
    }

    @Override
    public String getSPrice() {
        return "";
    }

    @Override
    public String getInputTitle() {
        return mTitle.getText().toString().trim();
    }

    @Override
    public String getInputContent() {
        return mContant.getText().toString().trim();
    }

    @Override
    public String geLocationLng() {
        return getLng();
    }

    @Override
    public String getLocationLat() {
        return getLat();
    }

    @Override
    public String getImage() {
        return articleimg;
    }

    @Override
    public void releaseHelpSuccess() {

    }

    @Override
    public void releaseInterestingSuccess() {
        finish();
    }
}
