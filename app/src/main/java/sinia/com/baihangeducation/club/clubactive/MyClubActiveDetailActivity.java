package sinia.com.baihangeducation.club.clubactive;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.androidkun.xtablayout.XTabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.ClubPermissModel;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.FragmentAdapter;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

public class MyClubActiveDetailActivity extends BaseActivity implements GetRequestListener {

    private String type;
    private String clubid;
    private boolean pushActivity = false;
    private boolean dropActivity = false;
    private ClubPermissModel clubPermissModel;
    private List<String> mPermissionList;

    public int initLayoutResID() {
        return R.layout.activity_my_coffes_detail;
    }


    @Override
    protected void initData() {
        mCommonTitle.setBackgroundColor(Color.WHITE);
        mCommonTitle.setCenterText("社团活动");
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        clubid = intent.getStringExtra("clubid");
        clubPermissModel = new ClubPermissModel(this);
        clubPermissModel.getClubPermission(clubid, this);

        initViewPager();
    }

    @Override
    public void setRequestSuccess(String msg) {
        mPermissionList = new Gson().fromJson(msg, new TypeToken<List<String>>() {
        }.getType());
        pushActivity = mPermissionList.contains("pushActivity");
        dropActivity = mPermissionList.contains("dropActivity");
        if (pushActivity) {
            mCommonTitle.getRightTxt().setText("创建");
            mCommonTitle.getRightTxt().setOnClickListener(this);
        }
    }

    @Override
    public void setRequestFail() {

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.right_txt:
                if (pushActivity) {
                    Goto.toEditorActive(context, clubid);
                } else {
                    com.example.framwork.utils.Toast.getInstance().showErrorToast(context, "你没有创建权限");
                }

                break;
        }
    }

    private List<String> titles;
    List<Fragment> fragments = new ArrayList<>();

    private void initViewPager() {
        titles = new ArrayList<>();
        titles.add("推荐活动");
        titles.add("社团活动");
        titles.add("校园活动");
        fragments.add(new ReCommendActiveFragment());
        fragments.add(new MyRecommendFragment());
        fragments.add(new MySchoolFragment());
        FragmentAdapter adatper = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adatper);
        viewPager.setOffscreenPageLimit(4);
        //将TabLayout和ViewPager关联起来。
        XTabLayout tabLayout = (XTabLayout) findViewById(R.id.xTablayout);

        // tabLayout.setupWithViewPager(viewPager);
        //给TabLayout设置适配器
        tabLayout.setupWithViewPager(viewPager);
        if (type.equals("main")) {
            viewPager.setCurrentItem(2);
            tabLayout.getTabAt(2).select();
        }
        if (type.equals("club")) {
            viewPager.setCurrentItem(1);
            tabLayout.getTabAt(1).select();
        }

    }


}
