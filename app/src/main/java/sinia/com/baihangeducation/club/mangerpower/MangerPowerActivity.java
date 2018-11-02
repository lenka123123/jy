package sinia.com.baihangeducation.club.mangerpower;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framwork.utils.Toast;

import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.addclub.AddClubList;
import sinia.com.baihangeducation.club.addclub.GetAddOptionRequestListener;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.editorclubactive.power.SettingPowerData;
import sinia.com.baihangeducation.club.editorclubactive.power.SettingPowerInfoListener;
import sinia.com.baihangeducation.club.editorclubactive.power.SettingPowerModel;
import sinia.com.baihangeducation.supplement.alertview.AlertViewContorller;
import sinia.com.baihangeducation.supplement.alertview.OnItemClickListener;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

public class MangerPowerActivity extends BaseActivity implements SettingPowerInfoListener, OnItemClickListener, MangerPowerListener {


    private ImageView back;
    private LinearLayout post_layout;
    private TextView post_layout_text;
    private String[] club_list;
    private AlertViewContorller mAlertViewContorllerClub;
    private List<SettingPowerData> settingPowerData;
    private String role_id;
    private MangerPowerModel mangerPowerModel;
    private RecyclerView recycler_view;
    private MangerPowerAdapter mangerPowerAdapter;

    public int initLayoutResID() {
        return R.layout.activity_manger_power;
    }


    @Override
    protected void initData() {
        Intent intent = getIntent();
        String club_id = intent.getStringExtra("club_id");

        mangerPowerModel = new MangerPowerModel(context);
        mangerPowerModel.getActiveInfo(club_id, this);

    }

    @Override
    protected void initView() {
        back = findViewById(R.id.back);
        post_layout = findViewById(R.id.post_layout);
        post_layout_text = findViewById(R.id.post_layout_text);
        recycler_view = findViewById(R.id.recycler_view);


        back.setOnClickListener(this);
        post_layout.setOnClickListener(this);

        mangerPowerAdapter = new MangerPowerAdapter(this);
        recycler_view.setAdapter(mangerPowerAdapter);
        final GridLayoutManager manager = new GridLayoutManager(this, 1);
        recycler_view.setLayoutManager(manager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.post_layout:
                mAlertViewContorllerClub = new AlertViewContorller(post_layout_text,
                        "选择职位类型", null, "取消", null, club_list,
                        context, AlertViewContorller.Style.ActionSheet, this);
                mAlertViewContorllerClub.setCancelable(true).show();
                break;
        }
    }

    @Override
    public void onSuccess(List<SettingPowerData> successMessage) {
        this.settingPowerData = successMessage;
        if (successMessage != null && successMessage.size() >= 1) {
            post_layout_text.setText(successMessage.get(0).role_name);
            role_id = successMessage.get(0).role_id;
            mangerPowerModel.getClubOption(successMessage.get(0).role_id, this);
        }

        club_list = new String[successMessage.size()];
        for (int i = 0; i < successMessage.size(); i++) {
            club_list[i] = successMessage.get(i).role_name;
        }
    }

    @Override
    public void onError(String errorMessage) {

    }


    @Override
    public void setRequestSuccess(List<MangerPowerList> msg) {
        mangerPowerAdapter.setData(msg, MangerPowerActivity.this);


    }

    public void changPermiss(String permission_id ,String check) {

        showProgress();
        mangerPowerModel.setRole(role_id, permission_id,check, new GetRequestListener() {
            @Override
            public void setRequestSuccess(String msg) {
                hideProgress();
                Toast.getInstance().showSuccessToast(context, "修改成功");
            }

            @Override
            public void setRequestFail() {
                hideProgress();
            }
        });

    }

    @Override
    public void setRequestFail() {

    }

    @Override
    public void onItemClick(View view, List<String> mOthers, Object o, int position) {
        if (position == -1) {
            if (mAlertViewContorllerClub != null && mAlertViewContorllerClub.isShowing())
                mAlertViewContorllerClub.dismiss();
        } else {
            TextView view1 = (TextView) view;
            view1.setText(mOthers.get(position));
            role_id = settingPowerData.get(position).role_id;
            mangerPowerModel.getClubOption(role_id, this);

        }
    }
}
