package sinia.com.baihangeducation.supplement.tool;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;


import com.example.framwork.ricyclerview.DividerGridItemDecoration;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;

import java.util.ArrayList;
import java.util.List;

import com.mcxtzhang.swipemenulib.info.bean.SingleImageBean;

import sinia.com.baihangeducation.release.adapter.CustomNineGridViewAdapter;
import sinia.com.baihangeducation.release.adapter.MyRecyclerViewAdapterextends;

/**
 * Created by ${wjw} on 2016/5/18.
 */
public class AdapterUtils {

    public static void setNineGrid(int spanCount, Context context, List<SingleImageBean> thumbnailimages,
                                   List<SingleImageBean> images, RecyclerView nineGridView) {
        final ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        if (images != null) {
//            for (String image : images) {
//                ImageInfo info = new ImageInfo();
//                info.setThumbnailUrl(image);
//                info.setBigImageUrl(image);
//                imageInfo.add_friend(info);
//            }
            for (int i = 0; i < images.size(); i++) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(images.get(i).url);
                info.setBigImageUrl(images.get(i).url);
                imageInfo.add(info);
            }
        }

        if (thumbnailimages.size() == 0 || images.size() == 0) {
            nineGridView.setVisibility(View.GONE);
        } else {
            nineGridView.setVisibility(View.VISIBLE);
        }

        final MyRecyclerViewAdapterextends adapter = new MyRecyclerViewAdapterextends(context, imageInfo);
        nineGridView.setAdapter(adapter);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanCount);  //设置每行显示几个item
       nineGridView.addItemDecoration(new DividerGridItemDecoration(context, spanCount));
      //  nineGridView.addItemDecoration(new GridSpacingItemDecoration(context, spanCount,getS(context)));

        nineGridView.setLayoutManager(gridLayoutManager);
        nineGridView.setItemAnimator(new DefaultItemAnimator());
//设置适配器


    }

    private static int getS(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        return width;
    }

    private static boolean isSection(int p) {
        if (p == 4) {
            return false;
        } else
            return true;
    }

}
