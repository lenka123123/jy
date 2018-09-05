package sinia.com.baihangeducation.home.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import sinia.com.baihangeducation.MainActivity;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

/**
 * Created by Administrator on 2018/4/22.
 */

public class FristActivity extends BaseActivity {

    private TextView tv;

    @Override
    public int initLayoutResID() {
        return R.layout.frist;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tv = $(R.id.hah);
        tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.hah:
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
