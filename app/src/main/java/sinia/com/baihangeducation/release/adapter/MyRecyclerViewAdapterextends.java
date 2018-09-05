package sinia.com.baihangeducation.release.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.ninegrid.ImageInfo;
import com.umeng.commonsdk.debug.D;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;

public class MyRecyclerViewAdapterextends extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static enum ITEM_TYPE {
        ITEM_TYPE_IMAGE,
        ITEM_TYPE_TEXT,
        FOUR_IMAGE
    }

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private ArrayList<ImageInfo> imageInfo;

    public MyRecyclerViewAdapterextends(Context context, ArrayList<ImageInfo> imageInfo) {
        this.imageInfo = imageInfo;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getItemViewType(int position) {
        if (imageInfo.size() == 2) {

            return ITEM_TYPE.ITEM_TYPE_TEXT.ordinal();
        } else if (imageInfo.size() == 4) {

            return ITEM_TYPE.FOUR_IMAGE.ordinal();
        } else
            return ITEM_TYPE.ITEM_TYPE_IMAGE.ordinal();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_TEXT.ordinal()) {

            return new ImageViewHolder(mLayoutInflater.inflate(R.layout.campus_recycle_view_item_two, parent, false));
        } else if (viewType == ITEM_TYPE.FOUR_IMAGE.ordinal()) {
            return new ImageViewHolder(mLayoutInflater.inflate(R.layout.campus_recycle_view_item_two, parent, false));
        } else {
            return new ImageViewHolder(mLayoutInflater.inflate(R.layout.campus_recycle_view_item, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TextViewHolder) {
            if (imageInfo.size() >= 1) {
                (((TextViewHolder) holder).imageView).setVisibility(View.VISIBLE);
                (((TextViewHolder) holder).linearLayout).setVisibility(View.VISIBLE);
                Glide.with(mContext).load(imageInfo.get(position).thumbnailUrl).diskCacheStrategy(DiskCacheStrategy.RESULT).into(((TextViewHolder) holder).imageView);
            } else {
                (((TextViewHolder) holder).imageView).setVisibility(View.GONE);
                (((TextViewHolder) holder).linearLayout).setVisibility(View.GONE);
            }
        } else if (holder instanceof ImageViewHolder) {
            if (imageInfo.size() >= 1) {
                (((ImageViewHolder) holder).linearLayout).setVisibility(View.VISIBLE);
                (((ImageViewHolder) holder).imageView).setVisibility(View.VISIBLE);
                Glide.with(mContext).load(imageInfo.get(position).thumbnailUrl).diskCacheStrategy(DiskCacheStrategy.RESULT).into(((ImageViewHolder) holder).imageView);
            } else {
                (((ImageViewHolder) holder).linearLayout).setVisibility(View.GONE);
                (((ImageViewHolder) holder).imageView).setVisibility(View.GONE);
            }
        }

        ((ImageViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoShowDialog photoShowDialog = new PhotoShowDialog(mContext, imageInfo, position);
                photoShowDialog.show();
            }
        });

        ((ImageViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoShowDialog photoShowDialog = new PhotoShowDialog(mContext, imageInfo, position);
                photoShowDialog.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return imageInfo == null ? 0 : imageInfo.size();

    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public LinearLayout linearLayout;

        TextViewHolder(final View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            linearLayout = view.findViewById(R.id.image_LinearLayout);

        }
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
       public ImageView imageView;
       public LinearLayout linearLayout;

        ImageViewHolder(final View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            linearLayout = view.findViewById(R.id.image_LinearLayout);

        }
    }
}
