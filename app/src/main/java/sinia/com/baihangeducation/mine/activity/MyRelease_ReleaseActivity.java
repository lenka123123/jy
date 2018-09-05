package sinia.com.baihangeducation.mine.activity;

import android.view.View;
import android.widget.TextView;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/10.
 */

public class MyRelease_ReleaseActivity extends BaseActivity {
    private TextView mSchoolHelp;
    private TextView mInteresting;

    @Override
    public int initLayoutResID() {
        return R.layout.myrelease_release;
    }

    @Override
    protected void initData() {
        mCommonTitle.setCenterText(R.string.myrelease_release);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

    }

    @Override
    protected void initView() {
        mSchoolHelp = $(R.id.addschoolhelp);
        mInteresting = $(R.id.addschoolinteresting);

        mSchoolHelp.setOnClickListener(this);
        mInteresting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.addschoolhelp:
                Goto.toReleaseHelpEachOtherActivity(context);
                finish();
                break;
            case R.id.addschoolinteresting:
                Goto.toReleaseInterestingActiviyy(context);
                finish();
                break;
        }
    }
}
