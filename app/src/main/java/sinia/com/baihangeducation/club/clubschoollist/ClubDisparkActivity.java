package sinia.com.baihangeducation.club.clubschoollist;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.clubschoollist.interfaces.ClubSchoolListContract;
import sinia.com.baihangeducation.club.clubschoollist.model.ClubSchoolList;
import sinia.com.baihangeducation.club.clubschoollist.model.ClubSchoolListModel;
import sinia.com.baihangeducation.club.clubschoollist.presenter.ClubSchoolListPresenter;
import sinia.com.baihangeducation.club.clubschoollist.view.ClubSchoolListAdapter;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

public class ClubDisparkActivity extends BaseActivity {


    private ImageView dispark_img;
    private LinearLayout dispark_img_layout;
    private ImageView school_img;
    private LinearLayout school_img_layout;
    private ImageView club_img;
    private LinearLayout club_img_layout;

    private ImageView back;
    private TextView finish;


    public int initLayoutResID() {
        return R.layout.activity_dispark;
    }


    @Override
    protected void initView() {

        dispark_img = findViewById(R.id.dispark_img);
        dispark_img_layout = findViewById(R.id.dispark_img_layout);

        school_img = findViewById(R.id.school_img);
        school_img_layout = findViewById(R.id.school_img_layout);

        club_img = findViewById(R.id.club_img);
        club_img_layout = findViewById(R.id.club_img_layout);
        back = findViewById(R.id.back);
        finish = findViewById(R.id.finish);

        dispark_img_layout.setOnClickListener(this);
        school_img_layout.setOnClickListener(this);
        club_img_layout.setOnClickListener(this);
        finish.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finish:
                finish();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.dispark_img_layout:
                dispark_img.setVisibility(View.VISIBLE);
                school_img.setVisibility(View.INVISIBLE);
                club_img.setVisibility(View.INVISIBLE);
                AppConfig.SEND_FUN_FOR_ALL = "公开";
                break;
            case R.id.school_img_layout:
                dispark_img.setVisibility(View.INVISIBLE);
                school_img.setVisibility(View.VISIBLE);
                club_img.setVisibility(View.INVISIBLE);
                AppConfig.SEND_FUN_FOR_ALL = "大学";
                break;
            case R.id.club_img_layout:
                dispark_img.setVisibility(View.INVISIBLE);
                school_img.setVisibility(View.INVISIBLE);
                club_img.setVisibility(View.VISIBLE);
                AppConfig.SEND_FUN_FOR_ALL = "社团";
                break;
        }
    }

}
