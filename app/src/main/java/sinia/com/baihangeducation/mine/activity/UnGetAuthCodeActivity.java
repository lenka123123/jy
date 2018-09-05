package sinia.com.baihangeducation.mine.activity;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

/**
 * 无法获取验证码页面
 */

public class UnGetAuthCodeActivity extends BaseActivity {

    @Override
    public int initLayoutResID() {
        return R.layout.ungetauthcode;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mCommonTitle.setCenterText(R.string.findloginpassword);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {

    }
}
