package sinia.com.baihangeducation.mine.activity;

import android.widget.TextView;

import com.example.framwork.utils.SpCommonUtils;
import com.zzhoujay.richtext.RichText;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;

import com.mcxtzhang.swipemenulib.info.bean.CommonInfo;

public class UserRuleActivity extends BaseActivity {

    private TextView mContent;

    @Override
    public int initLayoutResID() {
        return R.layout.userrule;
    }

    @Override
    protected void initData() {
        String regist = (String) SpCommonUtils.get(this, AppConfig.COMMON_INFO_ABOUTREGISTRATION_PROTOCOL, "");

        mCommonTitle.setCenterText(R.string.userrule);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
        if (!regist.equals(""))
            RichText.from(regist).into(mContent);
    }

    @Override
    protected void initView() {
        mContent = $(R.id.userrule_content);
    }
}
