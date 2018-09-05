package sinia.com.baihangeducation.mine.activity;

import android.widget.TextView;

import com.example.framwork.utils.SpCommonUtils;
import com.zzhoujay.richtext.RichText;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;

import com.mcxtzhang.swipemenulib.info.bean.CommonInfo;

public class NewUserGuideActivity extends BaseActivity {

    private TextView mContent;

    @Override
    public int initLayoutResID() {
        return R.layout.newuserguide;
    }

    @Override
    protected void initData() {

        String help = (String) SpCommonUtils.get(this, AppConfig.COMMON_INFO_HELP, "");
        mCommonTitle.setCenterText(R.string.newuserguide);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
        if (help != null && !help.equals(""))
            RichText.from(help).into(mContent);
    }

    @Override
    protected void initView() {
        mContent = $(R.id.newuserguide_content);
    }
}
