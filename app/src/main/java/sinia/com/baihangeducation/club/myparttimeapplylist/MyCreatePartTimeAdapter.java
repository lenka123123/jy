package sinia.com.baihangeducation.club.myparttimeapplylist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.clubactive.model.ActiveListData;
import sinia.com.baihangeducation.club.im.ChatActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 *
 *
 */

public class MyCreatePartTimeAdapter extends SuperBaseAdapter<ClubCreateData.ApplyPerson> {

    private ClubMyCreatePartTimeActivity activity;
    private String type = "";
    private List<ClubCreateData.ApplyPerson> data;

    public MyCreatePartTimeAdapter(ClubMyCreatePartTimeActivity activity, List<ClubCreateData.ApplyPerson> data) {
        super(activity, data);
        this.activity = activity;
        this.data = data;
    }


    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder holder, final ClubCreateData.ApplyPerson item, int position) {

//      holder.setRoundImageUrl(R.id.logo, item.cover, R.drawable.new_eorrlogo);
//        holder.setImageUrl(R.id.img, item.cover, R.drawable.new_eorrlogo);
        holder.setText(R.id.name, item.nickname);
        holder.setText(R.id.time, item.add_time);
        holder.setText(R.id.gender, item.gender);
        holder.setText(R.id.age, item.age);
        holder.setText(R.id.school, item.school_name);

        // type = 1 待处理
        holder.setVisible(R.id.layout, type.equals("1"));
        holder.setVisible(R.id.line, type.equals("1"));
//        holder.setVisible(R.id.button, !type.equals("1"));

        ImageView logo = holder.getView(R.id.logo);

        Glide.with(activity).load(item.avatar).asBitmap().error(R.drawable.new_eorrlogo).centerCrop()
                .into(new BitmapImageViewTarget(logo) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(activity.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        logo.setImageDrawable(circularBitmapDrawable);
                    }
                });

        ImageView passes_type = holder.getView(R.id.passes_type);
        if (type.equals("2")) {
            passes_type.setVisibility(View.VISIBLE);
            Glide.with(activity).load(R.drawable.part_time_passe).asBitmap().error(R.drawable.new_eorrlogo).centerCrop()
                    .into(new BitmapImageViewTarget(passes_type) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(activity.getResources(), resource);
                            passes_type.setImageDrawable(circularBitmapDrawable);
                        }
                    });
        } else if (type.equals("3")) {
            passes_type.setVisibility(View.VISIBLE);
            Glide.with(activity).load(R.drawable.part_time_cancel).asBitmap().error(R.drawable.new_eorrlogo).centerCrop()
                    .into(new BitmapImageViewTarget(passes_type) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(activity.getResources(), resource);
                            passes_type.setImageDrawable(circularBitmapDrawable);
                        }
                    });
        } else {
            passes_type.setVisibility(View.INVISIBLE);
        }

        holder.setOnClickListener(R.id.pass, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.dealWithApply(item.apply_id, "1", new GetRequestListener() {
                    @Override
                    public void setRequestSuccess(String msg) {

                        data.remove(item);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void setRequestFail() {

                    }
                });

            }
        });
        //tiem 点击
        holder.setOnClickListener(R.id.active_item_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("2")) {
                    JMessageClient.getUserInfo(item.jmessage_phone, new GetUserInfoCallback() {
                        @Override
                        public void gotResult(int i, String s, cn.jpush.im.android.api.model.UserInfo userInfo) {
                            System.out.println("JMessageClient dong1" + i + "===" + s + "===" + userInfo.getAppKey());
                            chat(userInfo);
                        }
                    });
                }

            }
        });
        //是否通过 1：同意 2：拒绝    ( 必传 )
        holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.dealWithApply(item.apply_id, "2", new GetRequestListener() {
                    @Override
                    public void setRequestSuccess(String msg) {
                        data.remove(item);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void setRequestFail() {

                    }
                });

            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, ClubCreateData.ApplyPerson item) {
        return R.layout.activity_my_club_my_create_parttime;
    }

    public void chat(cn.jpush.im.android.api.model.UserInfo userInfo) {
        Intent intent = new Intent();
        intent.putExtra(MyApplication.CONV_TITLE, userInfo.getNickname());
        String targetId = userInfo.getUserName();
        intent.putExtra(MyApplication.TARGET_ID, targetId);
        intent.putExtra(MyApplication.TARGET_APP_KEY, userInfo.getAppKey());

        intent.setClass(activity, ChatActivity.class);
        activity.startActivity(intent);
    }


}
