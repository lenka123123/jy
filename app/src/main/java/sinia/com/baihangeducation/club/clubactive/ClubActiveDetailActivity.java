package sinia.com.baihangeducation.club.clubactive;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.AllCoffersFragment;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.BringCoffersFragment;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.FragmentAdapter;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.InComeCoffersFragment;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

public class ClubActiveDetailActivity extends BaseActivity {

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
        initViewPager();
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
    }
}
