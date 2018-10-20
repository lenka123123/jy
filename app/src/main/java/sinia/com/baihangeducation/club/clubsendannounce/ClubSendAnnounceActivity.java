package sinia.com.baihangeducation.club.clubsendannounce;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.clubsendannounce.model.ClubSendAnnounceModel;
import sinia.com.baihangeducation.club.searchschool.model.ClubSchoolList;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

public class ClubSendAnnounceActivity extends BaseActivity {
    private EditText edit_title;
    private EditText edit_content;
    private String club_id = "";
    private String notice_id = "";
    private TextView editor;
    private ClubSendAnnounceModel clubSendAnnounceModel;


    public int initLayoutResID() {
        return R.layout.activity_club_send_announce;
    }


    @Override
    protected void initView() {
        Intent intent = getIntent();
        club_id = intent.getStringExtra("club_id");
        notice_id = intent.getStringExtra("notice_id");
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");


        edit_title = findViewById(R.id.edit_title);
        edit_content = findViewById(R.id.edit_content);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftInput(edit_title);
                hideSoftInput(edit_content);
                finish();
            }
        });
        if (!title.equals("") && !content.equals("")) {
            edit_title.setText(title);
            edit_content.setText(content);
        }


        editor = findViewById(R.id.editor);
        editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edit_title.getText().toString();
                String content = edit_content.getText().toString();

                if (title.length() < 2 || title.length() > 20) {
                    com.example.framwork.utils.Toast.getInstance().showErrorToast(context, "标题请输入2-20个字");
                    return;
                }
                if (content.length() < 10 || content.length() > 200) {
                    com.example.framwork.utils.Toast.getInstance().showErrorToast(context, "正文请输入10-200个字");
                    return;
                }
                hideSoftInput(edit_title);
                hideSoftInput(edit_content);
                clubSendAnnounceModel.getClubListList(club_id, notice_id, edit_title.getText().toString(), edit_content.getText().toString());
            }
        });

    }

    @Override
    protected void initData() {
        clubSendAnnounceModel = new ClubSendAnnounceModel(this);
//        clubSendAnnounceModel.getClubListList(club_id,notice_id,);
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput(EditText view) {
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        mInputMethodManager
                .hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
