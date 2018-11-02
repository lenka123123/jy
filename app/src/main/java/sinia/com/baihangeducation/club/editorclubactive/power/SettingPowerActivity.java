package sinia.com.baihangeducation.club.editorclubactive.power;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

public class SettingPowerActivity extends BaseActivity implements SettingPowerInfoListener {


    private SuperRecyclerView superRecyclerView;
    private List<SettingPowerData> settingPowerDataList = new ArrayList<>();
    private SettingPowerModel settingPowerModel;
    private String member_id;
    private SettingPowerAdapter settingPowerAdapter;
    private TextView exit;

    @Override
    public int initLayoutResID() {
        return R.layout.activity_setting_power;
    }


    @Override
    protected void initData() {
        Intent intent = getIntent();
        member_id = intent.getStringExtra("id");
        settingPowerModel = new SettingPowerModel(this);
        settingPowerModel.getActiveInfo(member_id, this);


    }

    @Override
    protected void initView() {

        superRecyclerView = findViewById(R.id.rv_container);
        exit = findViewById(R.id.exit);

        settingPowerAdapter = new SettingPowerAdapter(this, settingPowerDataList, this);
        superRecyclerView.setAdapter(settingPowerAdapter);
        final GridLayoutManager manager = new GridLayoutManager(this, 1);
        superRecyclerView.setLayoutManager(manager);
        superRecyclerView.setItemAnimator(new DefaultItemAnimator());
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onSuccess(List<SettingPowerData> successMessage) {
        settingPowerAdapter.setData(successMessage, exit);
    }

    @Override
    public void onError(String errorMessage) {

    }

    public void setFinish() {
        SettingPowerActivity.this.finish();
    }

    public void setAppoint(String role_id) {
        settingPowerModel.setAppoint(member_id, role_id, new GetRequestListener() {
            @Override
            public void setRequestSuccess(String msg) {
                SettingPowerActivity.this.finish();
            }

            @Override
            public void setRequestFail() {

            }
        });
    }
}
