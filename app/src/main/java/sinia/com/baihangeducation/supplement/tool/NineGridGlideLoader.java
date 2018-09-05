package sinia.com.baihangeducation.supplement.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.ninegrid.NineGridView;

import sinia.com.baihangeducation.R;

/**
 * Created by wanjingyu on 2016/6/17.
 */
public class NineGridGlideLoader implements NineGridView.ImageLoader {
    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url)//
                .placeholder(R.drawable.ic_de_mrt)//
                .error(R.drawable.ic_de_mrt)//
                .thumbnail(0.1f).skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//
                .into(imageView);
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }
}
