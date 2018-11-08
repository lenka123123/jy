package sinia.com.baihangeducation.club.clubsendannounce;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private ImageView school_checked;
    private LinearLayout school_checked_layout;
    private ImageView club_checked;
    private LinearLayout club_checked_layout;
    private boolean dropSchoolNotice;
    private boolean editSchoolNotice;
    private boolean pushSchoolNotice;
    private String typeName;
    private boolean isSchoolAll = false;


    public int initLayoutResID() {
        return R.layout.activity_club_send_announce;
    }

    //             wxpay_checked.setImageResource(wxPay ? R.drawable.pay_select : R.drawable.pay_cancel);
    @Override
    protected void initView() {
        Intent intent = getIntent();
        typeName = intent.getStringExtra("typeName");
        club_id = intent.getStringExtra("club_id");
        notice_id = intent.getStringExtra("notice_id");
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        editor = findViewById(R.id.editor);
        if (typeName.startsWith("发布")) {

        } else {
            //编辑公告

        }
        dropSchoolNotice = intent.getBooleanExtra("dropSchoolNotice", false);
        editSchoolNotice = intent.getBooleanExtra("editSchoolNotice", false);
        pushSchoolNotice = intent.getBooleanExtra("pushSchoolNotice", false);


        TextView titleTextView = findViewById(R.id.fragment_home_adressName);
        titleTextView.setText(typeName);

        edit_title = findViewById(R.id.edit_title);
        edit_content = findViewById(R.id.edit_content);
        school_checked = findViewById(R.id.school_checked);
        school_checked_layout = findViewById(R.id.school_checked_layout);
        club_checked = findViewById(R.id.club_checked);
        club_checked_layout = findViewById(R.id.club_checked_layout);

        /**
         * 删除学校公告
         dropSchoolNotice
         学校公告编辑
         editSchoolNotice
         学校公告发布
         pushSchoolNotice
         */
        school_checked_layout.setVisibility(pushSchoolNotice ? View.VISIBLE : View.GONE);
        school_checked_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSchoolAll = true;
//                school_checked.setImageResource(wxPay ? R.drawable.pay_select : R.drawable.pay_cancel);
                school_checked.setImageResource(R.drawable.pay_select);
                club_checked.setImageResource(R.drawable.pay_cancel);
            }
        });

        club_checked_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSchoolAll = false;
                school_checked.setImageResource(R.drawable.pay_cancel);
                club_checked.setImageResource(R.drawable.pay_select);
            }
        });


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
                clubSendAnnounceModel.getClubListList(isSchoolAll, club_id, notice_id, edit_title.getText().toString(), edit_content.getText().toString());
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
