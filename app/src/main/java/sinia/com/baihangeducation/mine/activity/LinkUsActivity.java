package sinia.com.baihangeducation.mine.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.MyApplication;

import com.example.framwork.utils.SpCommonUtils;
import com.mcxtzhang.swipemenulib.info.bean.CommonInfo;

/**
 * Created by Administrator on 2018/4/9.
 */

public class LinkUsActivity extends BaseActivity {

    private TextView mTelNum;
    private TextView mCall;


    @Override
    public int initLayoutResID() {
        return R.layout.linkus;
    }

    @Override
    protected void initData() {

        String hotline = (String) SpCommonUtils.get(this, AppConfig.COMMON_INFO_HOTLINE, "");
        mCommonTitle.setCenterText(R.string.linkue);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
        if (!(hotline).isEmpty())
            mTelNum.setText(hotline);
    }

    @Override
    protected void initView() {
        mCall = $(R.id.linkus_call);
        mTelNum = $(R.id.linkus_telnum);

        mCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.linkus_call:
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + mTelNum.getText().toString().trim());
                intent.setData(data);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
                break;
        }
    }
}
