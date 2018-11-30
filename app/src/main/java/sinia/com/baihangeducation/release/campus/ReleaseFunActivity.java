package sinia.com.baihangeducation.release.campus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Parcel;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import io.github.changjiashuai.ImagePicker;
import io.github.changjiashuai.widget.CropImageView;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.home.adapter.PhoneGlideImageLoader;
import sinia.com.baihangeducation.mine.activity.PlusImageActivity;
import sinia.com.baihangeducation.mine.activity.UCentreBaseInfoActivity;
import sinia.com.baihangeducation.release.campus.tabs.heatfunlist.view.SearchFunListActivity;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.mine.adapter.GridViewAdapter;

import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;
import com.mcxtzhang.swipemenulib.info.bean.SingleImageBean;

import sinia.com.baihangeducation.mine.presenter.ReleaseFunPresenter;
import sinia.com.baihangeducation.mine.view.IReleaseFunView;
import sinia.com.baihangeducation.supplement.base.Goto;

import com.mcxtzhang.swipemenulib.utils.Constants;

import static io.github.changjiashuai.ImagePicker.REQUEST_CODE_PICK;

public class ReleaseFunActivity extends BaseActivity implements IReleaseFunView {
    private static final int REQUEST_CODE = 0x00000011;
    private static final int COMPANY_LOGO = 11034;
    private ReleaseFunPresenter presenter;
    private LinearLayout mBack;
    private TextView mRelease;
    private EditText mContant;
    private GridView mGridView;
    private String topic_title = "";
    private boolean isRelease = false;
    private ArrayList<String> paths = new ArrayList<>();
    int j = 0;

    List<SingleImageBean> imgs;
    List<String> imgList;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    String imgurl;
    private GridViewAdapter mAdapter;
    private ImageView mSendImg;
    private ImageView mSendTopic;
    private String mTopicID;
    private boolean one = false;
    private LinearLayout dispark_layout;
    private TextView dispark;
    private ImagePicker.Config mPhoneConfig1;

    @Override
    public int initLayoutResID() {
        return R.layout.releasemoment;
    }

    @Override
    protected void initData() {
        paths.clear();
        mPhoneConfig1 = new ImagePicker.Config(new PhoneGlideImageLoader());
        mPhoneConfig1.multiMode(true);
        mPhoneConfig1.selectLimit(9);
        mPhoneConfig1.showCamera(false);
        mPhoneConfig1.crop(false);
//        mPhoneConfig1.cropStyle(CropImageView.CIRCLE);
//        mPhoneConfig.saveRectangle(false);//裁剪后是否矩形保存图片
//        mPhoneConfig1.focusWidth(800);
//        mPhoneConfig1.focusHeight(800);
//        mPhoneConfig.outPutX(800);
//        mPhoneConfig.outPutY(800);


        one = true;
        imgs = new ArrayList<>();
        imgList = new ArrayList<>();
        presenter = new ReleaseFunPresenter(context, this);

        mAdapter = new GridViewAdapter(context, mPicList);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if (position == adapterView.getChildCount() - 1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过5张，才能点击
                    if (mPicList.size() == 9) {
                        //最多添加5张图片
                        viewPluImg(position);
                    } else {
                        //添加凭证图片
//                        selectPic(MainConstant.MAX_SELECT_PIC_NUM - mPicList.size());
//                        ImageSelectorUtils.openPhoto(context, REQUEST_CODE, false, 9, mPicList);

                        takePhoto(1);
                    }
                } else {
                    viewPluImg(position);
                }
            }
        });

    }

    /**
     * 查看大图
     *
     * @param position
     */
    private void viewPluImg(int position) {
        Intent intent = new Intent(context, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constants.IMG_LIST, paths);
        intent.putExtra(Constants.POSITION, position);
        startActivityForResult(intent, Constants.REQUEST_CODE_MAIN);
    }

    @Override
    protected void initView() {
        mBack = $(R.id.releasemoment_back);
        mRelease = $(R.id.releasemoment_release);
        mContant = $(R.id.releasemoment_contant);
        mBack = $(R.id.releasemoment_back);
        mSendImg = $(R.id.send_img);
        mSendTopic = $(R.id.send_topic);
        dispark_layout = $(R.id.dispark_layout);
        dispark = $(R.id.dispark);

        mGridView = $(R.id.releasemoment_gridView);

        mRelease.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mSendImg.setOnClickListener(this);
        mSendTopic.setOnClickListener(this);
        dispark_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.dispark_layout:

                Goto.toSendFunDispark(context);
                break;
            case R.id.releasemoment_back:
                finish();
                break;
            case R.id.send_img:
                takePhoto(COMPANY_LOGO);

//                SImagePicker
//                        .from(ReleaseFunActivity.this)
//                        .pickMode(SImagePicker.MODE_IMAGE)
//                        .rowCount(3)
//                        .maxCount(9)
//                        .setSelected(mPicList)
//                        .forResult(1712);


//                ImageSelectorUtils.openPhoto(ReleaseFunActivity.this, REQUEST_CODE, false, 9, mPicList);
                break;
            case R.id.send_topic:
                Intent intent = new Intent(context, SearchFunListActivity.class);
                startActivityForResult(intent, 999);
                break;
            case R.id.releasemoment_release:
//                imgs.clear();
//                for (int i = 0; i < mPicList.size(); i++) {
//                presenter.releaseImages(mPicList.get(0), 1);
//                }
                mRelease.setClickable(false);
                mRelease.setEnabled(false);
                presenter.releaseFun(open);
                break;
        }
    }

    private void takePhoto(int type) {

        ImagePicker.getInstance().pickImageForResult(this, mPhoneConfig1);
        ImagePicker.RESULT_CODE_BACK_CLICK = 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_CODE_PICK && ImagePicker.RESULT_CODE_BACK_CLICK == 0) {
            if (data != null) {
                if (resultCode == io.github.changjiashuai.ImagePicker.RESULT_CODE_ITEMS) {
                    boolean isOrigin = data.getBooleanExtra(io.github.changjiashuai.ImagePicker.EXTRA_IS_ORIGIN, false);
                    if (!isOrigin) {
                        ArrayList<io.github.changjiashuai.bean.ImageItem> imageItems = io.github.changjiashuai.ImagePicker.getInstance().getSelectedImages();
                        if (imageItems.size() > 0) {
                            for (io.github.changjiashuai.bean.ImageItem imageItem1 : imageItems) {
                                paths.add(imageItem1.path);
                            }
                            mAdapter.setData(paths);
                        }
                    } else {
                        //遍历做压缩处理
//                        Toast.makeText(this, "稍后压缩", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

//        if (resultCode == Activity.RESULT_OK && requestCode == 1712) {
//            final ArrayList<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
//
//            final boolean original = data.getBooleanExtra(PhotoPickerActivity.EXTRA_RESULT_ORIGINAL, false);
//            mAdapter.setData(pathList);
//        }


        if (requestCode == 999 && resultCode == 1000) {

            String text = mContant.getText().toString().trim();
            if (topic_title.equals("")) {
                topic_title = data.getStringExtra("topic_title");
                text = topic_title + text;
                mTopicID = data.getStringExtra("topic_id");
            } else {
                System.out.println("=====DDD" + text);
                if (text.equals("")) {

                } else {
                    if (text.length() > topic_title.length())
                        text = text.substring(topic_title.length(), text.length());
                }

                topic_title = data.getStringExtra("topic_title");
                text = topic_title + text;
            }

            //文本内容
            SpannableString ss = new SpannableString(text);
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.title_color)), 0, topic_title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            String con = mContant.getText().toString().trim();


            mContant.setText(ss);
            mContant.setSelection(ss.length());


        }


        System.out.println(requestCode + "toDeletePicList================" + resultCode);
// TODO: 2018/11/3 0003  
//        if (requestCode == REQUEST_CODE && data != null) {
//            ArrayList<String> images = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
//
//            System.out.println(images.size() + "toDeletePicList================");
//            if (images.size() >= 1) System.out.println(images.get(0));
//            mAdapter.setData(images);
//        }
        if (requestCode == Constants.REQUEST_CODE_MAIN && resultCode == Constants.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            paths = data.getStringArrayListExtra(Constants.IMG_LIST); //要删除的图片的集合
            System.out.println(paths.size() + "toDeletePicList ee================");
            if (paths.size() >= 1) System.out.println(paths.get(0));
            mAdapter.setData(paths);
        }
    }

    @Override
    public void showLoading() {
        showProgress();
    }

    @Override
    public void hideLoading() {
        hideProgress();
    }

    @Override
    public List<String> getReleaseFunImgs() {
        imgList.clear();
        Log.i("获取选择图片size", mPicList.size() + "选择大小");
        for (int i = 0; i < mPicList.size(); i++) {
            imgList.add(mPicList.get(i));
        }
        Log.i("获取选择图片size", imgList.size() + "转换大小");
        return imgList;

    }

    @Override
    public String getReleaseFunContent() {
        return mContant.getText().toString().trim();
    }

    @Override
    public String getReleaseFunLng() {
        return getLng();
    }

    @Override
    public String getReleaseFunLat() {
        return getLat();
    }

    @Override
    public String getReleaseFunAdcode() {
        return getAdCode();
    }

    @Override
    public JSONArray getReleaseFunAllImgs() {
        JSONArray jarr = JSONArray.parseArray(JSON.toJSONString(imgs));
        return jarr;
    }

    @Override
    public void releaseSingleImageSuccess(SingleImageBean imgbean, int i) {
        imgs.add(imgbean);
        j++;
        Log.i("发布参数flag", j + "参数I");
        Log.i("发布参数flag", mPicList.size() + "参数大小");
        if (mPicList.size() == j) {
            presenter.releaseFun(open);
        }
    }

    @Override
    public void releaseFunSuccess() {
        finish();
    }

    public void releaseFunFail() {
        mRelease.setClickable(true);
        mRelease.setEnabled(true);
    }

    private int open = 1;

    // 可见级 1：公开可见 2：学校可见 3：社团可见
    @Override
    protected void onRestart() {
        super.onRestart();
        if (AppConfig.SEND_FUN_FOR_ALL.equals("公开")) {
            open = 1;
            dispark.setText("全部可见");
        } else if (AppConfig.SEND_FUN_FOR_ALL.equals("大学")) {
            open = 2;
            dispark.setText("学校可见");
        } else if (AppConfig.SEND_FUN_FOR_ALL.equals("社团")) {
            open = 3;
            dispark.setText("社团可见");
        }

    }
}
