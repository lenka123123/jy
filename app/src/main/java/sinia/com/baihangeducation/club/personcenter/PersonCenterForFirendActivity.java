package sinia.com.baihangeducation.club.personcenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.utils.SpCommonUtils;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.club.interfaces.SetClubHomeListener;
import sinia.com.baihangeducation.club.club.model.ClubHomeInfo;
import sinia.com.baihangeducation.club.club.model.ClubHomeModel;
import sinia.com.baihangeducation.club.im.ChatActivity;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

public class PersonCenterForFirendActivity extends BaseActivity implements SuperRecyclerView.LoadingListener,
        SwipeRefreshLayout.OnRefreshListener, SetClubHomeListener {

    private SwipeRefreshLayout swipeContainer;
    private PersonCentrListForFriendAdapter mClubListAdapter;
    private SuperRecyclerView rvContainer;
    private List<ClubHomeInfo.School> list = new ArrayList<>();
    private TextView name;
    private TextView school_name;
    private ImageView logo;
    private TextView part_num;
    private TextView active_num;
    private TextView club_num;
    private TextView rinking;
    private ProgressActivity progressActivity;
    private ProgressActivityUtils progressActivityUtils;
    private ClubHomeModel clubHomeModel;
    private String other_id;
    private String name1;
    private String phoneImg;
    private TextView setting_logout;
    private TextView my_apply_help_num;
    private LinearLayout my_apply_help_layout;


    @Override
    public int initLayoutResID() {
        return R.layout.activity_person_center_for_friend;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        other_id = intent.getStringExtra("other_id");
        name1 = intent.getStringExtra("name");
        phoneImg = intent.getStringExtra("phone");
        clubHomeModel = new ClubHomeModel(this);
        clubHomeModel.getClubHomeInfo("", "1", other_id, this);
        clubTitleChange();
    }

    public void clubTitleChange() {
        String nickname = (String) SpCommonUtils.get(context, AppConfig.FINAL_NUM_FULL_HULP_NICKNAME, "");
        String phone = (String) SpCommonUtils.get(context, AppConfig.FINAL_SAVE_PHOTO_PATH, "");
        if (!nickname.equals("") && AppConfig.ISlOGINED) {
            name.setText(nickname);
        } else {
//            name.setText("暂未登陆");
            name.setText("暂无");
        }
        if (!name1.equals(""))
            name.setText(name1);

        if (!phone.equals("")) {
            Glide.with(context).load(phoneImg).asBitmap().error(R.drawable.new_eorrlogo).centerCrop()
                    .into(new BitmapImageViewTarget(logo) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            logo.setImageDrawable(circularBitmapDrawable);
                        }
                    });
            return;
        }

        if (!phone.equals("") && AppConfig.ISlOGINED) {

            Glide.with(context).load(AppConfig.LOGINPHOTOTPATH).asBitmap().error(R.drawable.new_eorrlogo).centerCrop()
                    .into(new BitmapImageViewTarget(logo) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            logo.setImageDrawable(circularBitmapDrawable);
                        }
                    });

        }
    }

    @Override
    protected void initView() {

        my_apply_help_layout = $(R.id.my_apply_help_layout);
        my_apply_help_num = $(R.id.my_apply_help_num);
        swipeContainer = $(R.id.swipe_container);
        rvContainer = $(R.id.rv_container);
        name = $(R.id.name);
        school_name = $(R.id.school_name);
        logo = $(R.id.logo);

        part_num = $(R.id.part_num);
        active_num = $(R.id.active_num);
        club_num = $(R.id.club_num);

        rinking = $(R.id.rinking);
        progressActivity = $(R.id.progressActivity);
        setting_logout = $(R.id.setting_logout);
        setting_logout.setOnClickListener(this);

//        findViewById(R.id.my_club_layout).setOnClickListener(this);
//        findViewById(R.id.my_active_layout).setOnClickListener(this);
//        findViewById(R.id.my_part_time_layout).setOnClickListener(this);
//        my_apply_help_layout.setOnClickListener(this);

        $(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        initSwipeLayout(swipeContainer, this);
        showSwipeRefreshLayout(swipeContainer);
        mClubListAdapter = new PersonCentrListForFriendAdapter(this, list);
        initRecyclerView(rvContainer, mClubListAdapter, this);
        progressActivityUtils = new ProgressActivityUtils(context, progressActivity);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.setting_logout:

                JMessageClient.getUserInfo(other_id, new GetUserInfoCallback() {
                    @Override
                    public void gotResult(int i, String s, cn.jpush.im.android.api.model.UserInfo userInfo) {
                      //  System.out.println("JMessageClient dong1" + i + "===" + s + "===" + userInfo.getAppKey());
                        chat(userInfo);
                    }
                });
                break;
            case R.id.my_part_time_layout:
                Goto.toMyPartTime(context, other_id);
                break;
            case R.id.my_active_layout:
                Goto.toMyClubActivity(context, other_id);
                break;
            case R.id.my_club_layout:
                Goto.toMyClub(context, other_id);
                break;
            case R.id.my_apply_help_layout:
                Goto.toMyHelpActivity(context, other_id);
                break;
        }
    }

    @Override
    public void onRefresh() {
        hideLoading();
    }

    @Override
    public void onLoadMore() {
        hideLoading();
    }

    public void showLoading() {//调用接口时候
        showProgress();
    }

    public void hideLoading() {//得到数据的时候
        hideProgress();
        hideSwipeRefreshLayout(swipeContainer);
        rvContainer.completeLoadMore();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void setClubHomeSuccess(ClubHomeInfo clubSchoolList) {
      //  System.out.println(" =====jav===" + clubSchoolList.user_info.club_num);
        ClubHomeInfo.UserInfo userInfo = clubSchoolList.user_info;
        if (userInfo != null) {
            school_name.setText(userInfo.school_name);
            AppConfig.SCHOOLNAMEID = userInfo.school_id;
            part_num.setText(userInfo.job_num);
            my_apply_help_num.setText(userInfo.support_num);
            active_num.setText(userInfo.activity_num);
            club_num.setText(userInfo.club_num);
        }
        if (clubSchoolList.list.size() == 0) {
//            look_more.setText("暂无俱乐部入驻，敬请期待");
//            loke.setVisibility(View.INVISIBLE);
            rinking.setVisibility(View.INVISIBLE);
//            look_more.setClickable(false);
            progressActivityUtils.showContent();
        } else {
//            loke.setVisibility(View.VISIBLE);
            rinking.setVisibility(View.INVISIBLE);
//            look_more.setText("查看更多");
//            look_more.setClickable(true);
        }

        rvContainer.setLoadMoreEnabled(false);
        rvContainer.setRefreshEnabled(false);
        hideLoading();
        list.clear();

        list.addAll(clubSchoolList.list);
        mClubListAdapter.notifyDataSetChanged();
    }

    @Override
    public void setClubHomeFail(String msg) {

    }

    /**
     * 申请加入社团
     */
    public void applyClub(String club_id, String member_id, GetRequestListener listener) {
        clubHomeModel.applyClub(club_id, member_id, new GetRequestListener() {
            @Override
            public void setRequestSuccess(String msg) {
                listener.setRequestSuccess("");
            }

            @Override
            public void setRequestFail() {
                listener.setRequestFail();

            }
        });
    }

    public void chat(cn.jpush.im.android.api.model.UserInfo userInfo) {
        Intent intent = new Intent();
        intent.putExtra(MyApplication.CONV_TITLE, name1);
        String targetId = userInfo.getUserName();
        intent.putExtra(MyApplication.TARGET_ID, targetId);
        intent.putExtra(MyApplication.TARGET_APP_KEY, userInfo.getAppKey());
//        intent.putExtra(MyApplication.DRAFT, "***");
        intent.setClass(context, ChatActivity.class);
        startActivity(intent);
    }
}
