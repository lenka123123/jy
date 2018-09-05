package sinia.com.baihangeducation.newcampus.tabs.friend;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import sinia.com.baihangeducation.R;


/**
 * @author KCrason
 * @date 2018/4/27
 */
public class NineImageAdapter implements NineGridView.NineGridAdapter<String> {

    private List<String> mImageBeans;

    private Context mContext;


    public NineImageAdapter(Context context, List<String> imageBeans) {
        this.mContext = context;
        this.mImageBeans = imageBeans;
        int itemSize = (Utils.getScreenWidth() - 2 * Utils.dp2px(4) - Utils.dp2px(54)) / 3;

    }

    @Override
    public int getCount() {
        return mImageBeans == null ? 0 : mImageBeans.size();
    }

    @Override
    public String getItem(int position) {
        return mImageBeans == null ? null :
                position < mImageBeans.size() ? mImageBeans.get(position) : null;
    }

    @Override
    public View getView(int position, View itemView) {
        ImageView imageView;
        if (itemView == null) {
            imageView = new ImageView(mContext);
            imageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.base_F2F2F2));
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            imageView = (ImageView) itemView;
        }
        String url = mImageBeans.get(position);
        if (url != null && url.length() > 1) {
            imageView.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(url).centerCrop().error(R.drawable.logo).crossFade().into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }

//        Glide.with(mContext).load(url).apply(mRequestOptions).transition(mDrawableTransitionOptions).into(imageView);
        return imageView;
    }
}
