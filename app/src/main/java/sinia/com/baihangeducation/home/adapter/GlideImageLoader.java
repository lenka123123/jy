package sinia.com.baihangeducation.home.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;
import com.youth.banner.loader.ImageLoader;

import sinia.com.baihangeducation.R;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        //Glide.with(context).load(path).into(imageView);

        if (Util.isOnMainThread()) {
            GlideLoadUtils.getInstance().glideLoad(context, (String) path, imageView, R.drawable.logo);
        }

    }
}
