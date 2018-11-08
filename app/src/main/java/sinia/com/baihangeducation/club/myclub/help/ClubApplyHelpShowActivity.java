package sinia.com.baihangeducation.club.myclub.help;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framwork.utils.Toast;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.club.editorclubactive.ClubEditorModel;
import sinia.com.baihangeducation.club.editorclubactive.model.ApplyData;
import sinia.com.baihangeducation.club.editorclubactive.model.GetActiveOptionListener;
import sinia.com.baihangeducation.club.editorclubactive.model.GetClubActiveOption;
import sinia.com.baihangeducation.club.editorclubactive.model.ObtainApplyInfoListener;
import sinia.com.baihangeducation.club.myclub.myparttime.MyClubModel;
import sinia.com.baihangeducation.supplement.alertview.AlertViewContorller;
import sinia.com.baihangeducation.supplement.alertview.OnItemClickListener;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

public class ClubApplyHelpShowActivity extends BaseActivity implements ObtainApplyInfoListener {


    private TextView title;
    private TextView school_club;
    private TextView money;
    private TextView name;
    private TextView phone;
    private TextView goods;
    private TextView need;
    private TextView apply_ing;
    private ImageView apply_success;

    public int initLayoutResID() {
        return R.layout.activity_apply_show_help;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String support_id = intent.getStringExtra("support_id");
        ClubEditorModel clubEditorModel = new ClubEditorModel(this);
        clubEditorModel.getSupportInfo(support_id, this);


    }

    @Override
    protected void initView() {
        mCommonTitle.setBackgroundColor(Color.WHITE);
        mCommonTitle.setCenterText("申请赞助详情");

        title = findViewById(R.id.title);
        school_club = findViewById(R.id.school_club);
        money = findViewById(R.id.money);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        goods = findViewById(R.id.goods);
        need = findViewById(R.id.need);
        apply_ing = findViewById(R.id.apply_ing);
        apply_success = findViewById(R.id.apply_success);

    }


    @Override
    public void onSuccess(ApplyData successMessage) {
        title.setText(successMessage.title);
        school_club.setText(successMessage.school_name + "    " + successMessage.club_name);
        money.setText(successMessage.money);
        name.setText(successMessage.name);
        //  s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        if (successMessage.mobile.length() == 11)
            phone.setText(successMessage.mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        goods.setText(successMessage.requirement);
        need.setText(successMessage.reason); //原因
        if (successMessage.status.equals("审核成功")) {
            apply_ing.setVisibility(View.GONE);
            apply_success.setVisibility(View.VISIBLE);

        } else {
            apply_ing.setVisibility(View.VISIBLE);
            apply_success.setVisibility(View.GONE);
        }

    }

    @Override
    public void onError(String errorMessage) {

    }

}