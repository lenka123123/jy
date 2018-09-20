package sinia.com.baihangeducation.club.notice;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.notice.model.ClubNoticeModel;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

public class ClubNoticeActivity extends BaseActivity {

    private String club_id = "";
    private String introduce = "";
    private TextView editor;
    private ClubNoticeModel clubNoticeModel;
    private EditText edit_content;


    public int initLayoutResID() {
        return R.layout.activity_club_notice;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        club_id = intent.getStringExtra("club_id");
        introduce = intent.getStringExtra("introduce");
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edit_content = findViewById(R.id.edit_content);
        editor = findViewById(R.id.editor);
        if (!introduce.equals(""))
            edit_content.setText(introduce.trim());

        edit_content.setSelection(introduce.trim().length());
        editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //发布
                String content = edit_content.getText().toString();
                if (content.length() < 10 || content.length() > 200) {
                    com.example.framwork.utils.Toast.getInstance().showErrorToast(context, "正文请输入10-200个字");
                    return;
                }
                clubNoticeModel.getClubAnnounce(club_id, content);
            }
        });

    }

    @Override
    protected void initData() {
        clubNoticeModel = new ClubNoticeModel(this);
    }


}
