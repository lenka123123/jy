package sinia.com.baihangeducation.club.clubannouncedetail;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView edit_announce;
    private boolean dropNotice;
    private boolean editNotice;
    private String type;
    private boolean dropSchoolNotice;
    private boolean editSchoolNotice;
    private boolean pushSchoolNotice;


    public int initLayoutResID() {
        return R.layout.activity_club_announce_detail;
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (clubSendAnnounceModel != null)
            clubSendAnnounceModel.getAnnounceInfo(notice_id);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        club_id = intent.getStringExtra("club_id");
        notice_id = intent.getStringExtra("notice_id");
        // 公告类型ID    ( 1：社团公告 2：学校公告 3：系统公告 )
        type = intent.getStringExtra("type");
        dropNotice = intent.getBooleanExtra("dropNotice", false);
        editNotice = intent.getBooleanExtra("editNotice", false);

        dropSchoolNotice = intent.getBooleanExtra("dropSchoolNotice", false);
        editSchoolNotice = intent.getBooleanExtra("editSchoolNotice", false);
        pushSchoolNotice = intent.getBooleanExtra("pushSchoolNotice", false);
//                power.contains("dropSchoolNotice")
//                power.contains("editSchoolNotice")
//                power.contains("pushSchoolNotice")
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

        editor.setVisibility(dropNotice ? View.VISIBLE : View.GONE);
        if (type.equals("3")) {
            edit_announce.setVisibility(View.INVISIBLE);
        } else {
            edit_announce.setVisibility(editNotice ? View.VISIBLE : View.GONE);
        }
        if (type.equals("1")) {
            editor.setVisibility(View.VISIBLE);
        } else {
            editor.setVisibility(View.INVISIBLE);
        }

        editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //删除
                if (type.equals("1")) {
                    clubSendAnnounceModel.detailAnnounce(notice_id);
                } else {

                }


            }
        });

        edit_announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //编辑
                Goto.toClubSendAnnounceActivity(context, "编辑公告", club_id, notice_id, announce_name.getText().toString(), announce_content.getText().toString(),
                        dropSchoolNotice,
                        editSchoolNotice,
                        pushSchoolNotice
                );
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
