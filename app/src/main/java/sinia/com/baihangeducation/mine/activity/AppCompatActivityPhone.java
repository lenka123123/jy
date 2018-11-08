package sinia.com.baihangeducation.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.mcxtzhang.swipemenulib.utils.BitmapSave;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import io.github.changjiashuai.ImagePicker;
import io.github.changjiashuai.bean.ImageItem;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.home.adapter.PhoneGlideImageLoader;

import static io.github.changjiashuai.ImagePicker.REQUEST_CODE_PICK;

public class AppCompatActivityPhone extends AppCompatActivity {

    private ImageView imageView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        context = this;
        imageView = findViewById(R.id.img);
        initView();

        initPicker();

    }


    private void initPicker() {
        ImagePicker.Config mPhoneConfig = new ImagePicker.Config(new PhoneGlideImageLoader());
        mPhoneConfig.multiMode(false);
//        config.selectLimit(1);
        mPhoneConfig.showCamera(true);
        ImagePicker.getInstance().pickImageForResult(this, mPhoneConfig);
    }


    private void initView() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK) {
            if (data != null) {
                if (resultCode == io.github.changjiashuai.ImagePicker.RESULT_CODE_ITEMS) {
                    boolean isOrigin = data.getBooleanExtra(io.github.changjiashuai.ImagePicker.EXTRA_IS_ORIGIN, false);
                    if (!isOrigin) {
                        ArrayList<ImageItem> imageItems = io.github.changjiashuai.ImagePicker.getInstance().getSelectedImages();
                        if (imageItems.size() > 0) {
                            io.github.changjiashuai.bean.ImageItem imageItem = imageItems.get(0);
                            String img = imageItems.get(0).path;
                            setPhoto(img);


                        }
                    } else {
                        //遍历做压缩处理
                        Toast.makeText(this, "稍后压缩", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }


    public void setPhoto(String avatar) {
        Glide.with(context).load(avatar).asBitmap().error(R.drawable.new_eorrlogo).centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                BitmapSave.saveBitmap(context, resource);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }


}
