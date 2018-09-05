package sinia.com.baihangeducation.mine.activity;

import android.webkit.WebView;
import android.widget.TextView;

import com.example.framwork.utils.SpCommonUtils;
import com.zzhoujay.richtext.RichText;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;

import com.mcxtzhang.swipemenulib.info.bean.CommonInfo;

public class AboutJobBangActivity extends BaseActivity {

    private WebView mAboutJobBangContent;
    private MyApplication application;

    @Override
    public int initLayoutResID() {
        return R.layout.aboutjobbang;
    }

    @Override
    protected void initData() {
        mCommonTitle.setCenterText(R.string.aboutjiuyebang);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        String about = (String) SpCommonUtils.get(this, AppConfig.COMMON_INFO_ABOUT, "");


        if (about != null)
            mAboutJobBangContent.loadData(about, "text/html; charset=UTF-8", null);
    }

    @Override
    protected void initView() {
        mAboutJobBangContent = $(R.id.aboutjobbang_content);
    }
}
