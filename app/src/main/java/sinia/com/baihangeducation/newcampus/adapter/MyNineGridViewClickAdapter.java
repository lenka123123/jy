//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sinia.com.baihangeducation.newcampus.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.NineGridViewAdapter;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.mine.activity.PlusImageActivity;
import com.mcxtzhang.swipemenulib.utils.Constants;

public class MyNineGridViewClickAdapter extends NineGridViewAdapter {
    private int statusHeight;
    private ArrayList<String> imgList;

    public MyNineGridViewClickAdapter(Context context, List<ImageInfo> imageInfo) {
        super(context, imageInfo);
        this.statusHeight = this.getStatusHeight(context);
    }

    protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List<ImageInfo> imageInfo) {
//        for(int i = 0; i < imageInfo.size(); ++i) {
//            ImageInfo info = (ImageInfo)imageInfo.get(i);
//            View imageView;
//            if (i < nineGridView.getMaxSize()) {
//                imageView = nineGridView.getChildAt(i);
//            } else {
//                imageView = nineGridView.getChildAt(nineGridView.getMaxSize() - 1);
//            }
//
//            info.imageViewWidth = imageView.getWidth();
//            info.imageViewHeight = imageView.getHeight();
//            int[] points = new int[2];
//            imageView.getLocationInWindow(points);
//            info.imageViewX = points[0];
//            info.imageViewY = points[1] - this.statusHeight;
//        }

//        Intent intent = new Intent(context, ImagePreviewActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("IMAGE_INFO", (Serializable)imageInfo);
//        bundle.putInt("CURRENT_ITEM", index);
//        intent.putExtras(bundle);
//        context.startActivity(intent);
//        ((Activity)context).overridePendingTransition(0, 0);
//        imgList.clear();
        imgList = new ArrayList<>();
        Log.i("图片集合的大小@@@@@@@",imageInfo.size()+"");
        for (int i = 0;i<imageInfo.size();i++){

            imgList.add(imageInfo.get(i).bigImageUrl);
        }
        Log.i("图片集合的大小@@@@@@@",imgList.size()+"@@@@@@@");

        Intent intent = new Intent(context, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constants.IMG_LIST, imgList);
        intent.putExtra(Constants.POSITION, index);
        intent.putExtra(Constants.ISDETEL,false);
        context.startActivity(intent);
//        startActivityForResult(intent, Constants.REQUEST_CODE_MAIN);
    }

    public int getStatusHeight(Context context) {
        int statusHeight = -1;

        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return statusHeight;
    }
}
