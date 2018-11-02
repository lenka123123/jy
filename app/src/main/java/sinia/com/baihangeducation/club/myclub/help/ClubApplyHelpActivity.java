package sinia.com.baihangeducation.club.myclub.help;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framwork.utils.ProgressActivityUtils;
import com.example.framwork.utils.Toast;
import com.example.framwork.widget.ProgressActivity;
import com.example.framwork.widget.superrecyclerview.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.editorclubactive.ClubEditorActiveActivity;
import sinia.com.baihangeducation.club.editorclubactive.ClubEditorModel;
import sinia.com.baihangeducation.club.editorclubactive.model.GetActiveOptionListener;
import sinia.com.baihangeducation.club.editorclubactive.model.GetClubActiveOption;
import sinia.com.baihangeducation.club.myclub.myclub.GetMyClubListener;
import sinia.com.baihangeducation.club.myclub.myclub.MyClubAdapter;
import sinia.com.baihangeducation.club.myclub.myclub.MyClubSchoolList;
import sinia.com.baihangeducation.club.myclub.myparttime.MyClubModel;
import sinia.com.baihangeducation.supplement.alertview.AlertViewContorller;
import sinia.com.baihangeducation.supplement.alertview.OnItemClickListener;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

public class ClubApplyHelpActivity extends BaseActivity implements GetActiveOptionListener, OnItemClickListener, GetRequestListener {

    private MyClubModel myPartTimeModel;
    private EditText title;
    private EditText name;
    private EditText phone;
    private EditText money;
    private EditText googs;
    private EditText cause;
    private TextView apply;
    private String[] club_list;
    private TextView active_type_text;
    private GetClubActiveOption clubActiveOption;
    private AlertViewContorller mAlertViewContorllerClub;
    private String club_id = "";
    private LinearLayout active_type;


    public int initLayoutResID() {
        return R.layout.activity_apply_help;
    }

    @Override
    protected void initData() {
        myPartTimeModel = new MyClubModel(context);

        mCommonTitle.setBackgroundColor(Color.WHITE);
        mCommonTitle.setCenterText("申请赞助");

        ClubEditorModel clubEditorModel = new ClubEditorModel(this);
        clubEditorModel.getActivityOption(this);
    }

    @Override
    protected void initView() {
        title = findViewById(R.id.title);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        money = findViewById(R.id.money);
        googs = findViewById(R.id.googs);
        cause = findViewById(R.id.cause);
        apply = findViewById(R.id.apply);
        active_type_text = findViewById(R.id.active_type_text);
        active_type = findViewById(R.id.active_type_linear);
        active_type.setOnClickListener(this);
        apply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.active_type_linear:
                System.out.println("===active_club=====");
                hideEditTextInput();
                mAlertViewContorllerClub = new AlertViewContorller(active_type_text,
                        "选择所属社团", null, "取消", null, club_list,
                        context, AlertViewContorller.Style.ActionSheet, this);
                mAlertViewContorllerClub.setCancelable(true).show();
                break;

            case R.id.apply:
                String title_text = title.getText().toString().trim();
                String name_text = name.getText().toString().trim();
                String phone_text = phone.getText().toString().trim();
                String money_text = money.getText().toString().trim();
                String googs_text = googs.getText().toString().trim();
                String cause_text = cause.getText().toString().trim();
                System.out.println("===active_club====cause_text=");
                if (check(title_text, name_text, phone_text, money_text, googs_text, cause_text, club_id)) {
                    myPartTimeModel.setSupport(name_text, phone_text, title_text, club_id, money_text, googs_text, cause_text, this);
                }
                break;
        }
    }

    public boolean check(String title_text, String name_text, String phone_text,
                         String money_text, String googs_text, String cause_text, String club_id) {
        if (title_text.equals("")) {
            Toast.getInstance().showErrorToast(context, "请输入主题");
            return false;
        }
        if (name_text.equals("")) {
            Toast.getInstance().showErrorToast(context, "请输入姓名");
            return false;
        }
        if (phone_text.equals("")) {
            Toast.getInstance().showErrorToast(context, "请输入正确的手机号");
            return false;
        }
        if (money_text.equals("")) {
            Toast.getInstance().showErrorToast(context, "请输入金额");
            return false;
        }
//        if (googs_text.equals("")) {
//            Toast.getInstance().showErrorToast(context, "请输入物品需求");
//            return false;
//        }

        if (cause_text.equals("")) {
            Toast.getInstance().showErrorToast(context, "请输入申请原因");
            return false;
        }
        if (club_id.equals("")) {
            Toast.getInstance().showErrorToast(context, "请选择所属社团");
            return false;
        }
        return true;
    }


    /**
     * 隐藏软键盘
     */
    protected void hideEditTextInput() {
        //隐藏键盘
        ((InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow((ClubApplyHelpActivity.this).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }


    @Override
    public void onSuccess(GetClubActiveOption successMessage) {
        club_list = new String[successMessage.club_list.size()];

        for (int i = 0; i < successMessage.club_list.size(); i++) {
            club_list[i] = successMessage.club_list.get(i).club_name;
        }
        clubActiveOption = successMessage;
    }

    @Override
    public void onError(String errorMessage) {

    }

    @Override
    public void onItemClick(View view, List<String> mOthers, Object o, int position) {
        if (position == -1) {
            if (mAlertViewContorllerClub != null && mAlertViewContorllerClub.isShowing())
                mAlertViewContorllerClub.dismiss();
        } else {
            TextView view1 = (TextView) view;
            view1.setText(mOthers.get(position));
            club_id = clubActiveOption.club_list.get(position).club_id;

        }
    }

    @Override
    public void setRequestSuccess(String msg) {
        Toast.getInstance().showSuccessToast(context, "申请成功");
        finish();
    }

    @Override
    public void setRequestFail() {

    }
}