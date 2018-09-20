package sinia.com.baihangeducation.club.clubannouncedetail;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.clubannouncedetail.model.ClubAnnounce;
import sinia.com.baihangeducation.club.clubannouncedetail.model.ClubAnnounceDetailModel;
import sinia.com.baihangeducation.club.searchschool.model.ClubSchoolList;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

public class ClubAnnounceDetailActivity extends BaseActivity {

    private String club_id = "";
    private String notice_id = "";
    private TextView editor;
    private ClubAnnounceDetailModel clubSendAnnounceModel;
    private TextView announce_name;
    private TextView announce_time;
    private TextView announce_content;
    private String power;
    private TextView edit_announce;


    public int initLayoutResID() {
        return R.layout.activity_club_announce_detail;
    }


    @Override
    protected void initView() {
        Intent intent = getIntent();
        club_id = intent.getStringExtra("club_id");
        notice_id = intent.getStringExtra("notice_id");
        power = intent.getStringExtra("power");


        announce_name = findViewById(R.id.announce_name);
        announce_time = findViewById(R.id.announce_time);
        announce_content = findViewById(R.id.announce_content);
        edit_announce = findViewById(R.id.edit_announce);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editor = findViewById(R.id.editor);

        System.out.println("powerpower========"+power);

        if (power.equals("2")) {// 管理员可以编辑
            editor.setVisibility(View.VISIBLE);
            edit_announce.setVisibility(View.VISIBLE);
        } else {
            editor.setVisibility(View.GONE);
            edit_announce.setVisibility(View.GONE);
        }

        editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //删除
                clubSendAnnounceModel.detailAnnounce(notice_id);

            }
        });

        edit_announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //编辑
                Goto.toClubSendAnnounceActivity(context, club_id, notice_id, announce_name.getText().toString(), announce_content.getText().toString());
            }
        });
    }

    @Override
    protected void initData() {
        clubSendAnnounceModel = new ClubAnnounceDetailModel(this);
        clubSendAnnounceModel.getAnnounceInfo(notice_id);

    }

    public void showData(ClubAnnounce clubSchoolList) {
        announce_name.setText(clubSchoolList.title);
        announce_time.setText(clubSchoolList.add_time);
        announce_content.setText(clubSchoolList.content);
    }


}
