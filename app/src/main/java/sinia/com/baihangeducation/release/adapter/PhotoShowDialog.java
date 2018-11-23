package sinia.com.baihangeducation.release.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.lzy.ninegrid.ImageInfo;
import com.mcxtzhang.swipemenulib.R;
import com.mcxtzhang.swipemenulib.customview.ViewPagerFix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import sinia.com.baihangeducation.club.clubdetail.ClubDetailActivity;
import sinia.com.baihangeducation.supplement.alertview.AlertViewContorller;
import sinia.com.baihangeducation.supplement.alertview.OnItemClickListener;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * @Author cuiChenBo
 * Created by zz on 2018/2/27 15:28.
 * 　　class explain:
 * 　　　　update:       upAuthor:      explain:
 */

public class PhotoShowDialog extends Dialog {
    private Context mContext;
    private ArrayList<ImageInfo> photoLists;
    private int mPosition;
    private ViewPagerFix vp;
    private TextView tv;
    private TextView pass;
    private LinearLayout sava_phone;

    public PhotoShowDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public PhotoShowDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }


    public PhotoShowDialog(Context context, ArrayList<ImageInfo> photoLists, int position) {
        this(context, R.style.Pic_Dialog);
        this.photoLists = photoLists;
        this.mPosition = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_dialog);
        init();
    }

    private void init() {
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        vp = findViewById(R.id.vp);
        tv = findViewById(R.id.tv);
        sava_phone = findViewById(R.id.sava_phone);
        pass = findViewById(R.id.pass);
        vp.setAdapter(new VpAdapter(mContext));
        vp.setCurrentItem(mPosition);
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePhone();
            }
        });
        tv.setText(vp.getCurrentItem() + 1 + "/" + photoLists.size());
        tv.setVisibility(photoLists.size() == 1 ? View.INVISIBLE : View.VISIBLE);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                sava_phone.setVisibility(View.GONE);
                tv.setText(position + 1 + "/" + photoLists.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void getCenterCancelDialogApply(Bitmap resource) {
        phoneResource = resource;

    }

    private void savePhone() {
        sava_phone.setVisibility(View.GONE);
        if (phoneResource != null)
            saveImageToGallery(mContext, phoneResource);
    }


    class VpAdapter extends PagerAdapter {
        Context context;

        public VpAdapter(Context context) {
            this.context = context;
        }


        @Override
        public int getCount() {
            return photoLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(context, R.layout.photo_show_item, null);
            final PhotoView photoView = view.findViewById(R.id.photo);
//                RequestOptions options = new RequestOptions()
//                        .placeholder(R.mipmap.loading)
//                        .error(R.mipmap.zanwutupian);
//               Glide.with(context).load(photoLists.get(position).bigImageUrl).into(photoView);
            Glide.with(mContext).load(photoLists.get(position).bigImageUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    photoView.setImageBitmap(resource);
                    photoView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            sava_phone.setVisibility(View.VISIBLE);

                            getCenterCancelDialogApply(resource);


                            return false;
                        }
                    });


                }
            }); //方法中设置asBitmap可以设置回调类型


            photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
                @Override
                public void onPhotoTap(ImageView view, float x, float y) {
                    PhotoShowDialog.this.dismiss();
                }
            });
            ((ViewPager) container).addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }


    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "BianMin");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "sign_" + System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        String path = file.getAbsolutePath();
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), path, fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Toast.makeText(context, "保存图片成功", Toast.LENGTH_LONG).show();
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    private Bitmap phoneResource;


}
