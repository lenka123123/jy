package sinia.com.baihangeducation.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framwork.glide.ImageLoaderUtils;
import com.example.framwork.utils.FileUtil;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;
import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;
import com.yanzhenjie.nohttp.Logger;

import java.util.ArrayList;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.mine.model.AccountManger;
import sinia.com.baihangeducation.mine.model.ContrastResumeInfo;

import com.mcxtzhang.swipemenulib.info.bean.MyResumInfo;

/**
 * Created by Administrator on 2018/4/4.
 */

public class MyResumeStudentCardActivity extends BaseActivity {
    private static final int STUDENTCARD_IMG = 100;
    private EditText mStudentCardNum;//学生证号
    private ImageView mStudentCardImage;//学生证照片
    private TextView mStudentCardConfirm;//提交
    private String studentCard_img;

    private MyApplication application;
    private MyResumInfo myResumInfoCopy;

    @Override
    public int initLayoutResID() {
        return R.layout.studentcard;
    }

    @Override
    protected void initData() {
        mCommonTitle.setCenterText(R.string.studentcard);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
    }

    @Override
    protected void initView() {
        mStudentCardNum = $(R.id.studentcard_num);
        mStudentCardImage = $(R.id.studentcard_img);
        mStudentCardConfirm = $(R.id.studentcard_confirm);

        mStudentCardNum.setOnClickListener(this);
        mStudentCardImage.setOnClickListener(this);
        mStudentCardConfirm.setOnClickListener(this);

//        GlideLoadUtils.getInstance().glideLoad(this, MyResumInfo.student_photo, mStudentCardImage, R.drawable.new_input_studentcard);


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.studentcard_num:
                //学生证号码
                break;
            case R.id.studentcard_img:
                //学生证照片
                takePhoto(STUDENTCARD_IMG);
                break;
            case R.id.studentcard_confirm:
                //提交
                //非空判断
                if (AccountManger.checkMyResumeUpStudentCard(context, mStudentCardNum.getText().toString().trim(), studentCard_img)) {
                    setData();
                    ContrastResumeInfo contrastResumeInfo = new ContrastResumeInfo(context);
                    contrastResumeInfo.contras();
                    finish();
                }
                break;
        }
    }

    /**
     * 将数据储存进备份缓存中
     */
    private void setData() {
        application = (MyApplication) context.getApplication();
        myResumInfoCopy = application.getUserResumeInfoCopy();
        myResumInfoCopy.student_no = mStudentCardNum.getText().toString().trim();
        myResumInfoCopy.student_photo = studentCard_img;
    }

    private void takePhoto(int type) {
        SImagePicker
                .from(MyResumeStudentCardActivity.this)
                .pickMode(SImagePicker.MODE_IMAGE)
                .showCamera(true).rowCount(3)
                .cropFilePath(
                        AppConfig.IMAGE_PATH + "/studentcard_img.png")
                .forResult(type);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == STUDENTCARD_IMG) {
            ArrayList<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
            if (pathList != null && pathList.size() != 0) {
                Logger.d(FileUtil.getInstance().getFileSize(context, pathList.get(0)));
                studentCard_img = pathList.get(0);
                Log.i("图片地址", pathList.get(0));
                ImageLoaderUtils.display(context, mStudentCardImage, pathList.get(0), R.drawable.logo, true);
            }
        }
    }
}
