package sinia.com.baihangeducation.home.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import io.github.changjiashuai.loader.ImageLoader;

public class PhoneGlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Context context, String path, ImageView imageView, int width, int height) {
        Glide.with(context)
                .load(path)
                .centerCrop()
//                .placeholder(R.drawable.default_image)
                .into(imageView);
    }
}
