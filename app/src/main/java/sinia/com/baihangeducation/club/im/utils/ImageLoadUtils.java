package sinia.com.baihangeducation.club.im.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;

import sinia.com.baihangeducation.club.im.chat.ImageLoader;
import sinia.com.baihangeducation.club.im.imageloader.ImageBase;


public class ImageLoadUtils extends ImageLoader {

    public ImageLoadUtils(Context context) {
        super(context);
    }

    @Override
    protected void displayImageFromFile(String imageUri, ImageView imageView) throws IOException {
        String filePath = ImageBase.Scheme.FILE.crop(imageUri);
        Glide.with(imageView.getContext())
                .load(filePath)
                .asBitmap()
                .into(imageView);
    }

    @Override
    protected void displayImageFromAssets(String imageUri, ImageView imageView) throws IOException {
        String uri = ImageBase.Scheme.cropScheme(imageUri);
        ImageBase.Scheme.ofUri(imageUri).crop(imageUri);
        Glide.with(imageView.getContext())
                .load(Uri.parse("file:///android_asset/" + uri))
                .into(imageView);
    }
}
