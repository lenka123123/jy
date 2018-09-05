package sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersapply;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.example.framwork.utils.Toast;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.CoffersDataBean;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.ObtainMyCoffersDataListener;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

public class MyCoffersApplyActivity extends BaseActivity {


    private EditText ed_apply_number;
    private EditText ed_apply_name;
    private EditText my_coffers_money;
    private String real_capital;

    public int initLayoutResID() {
        return R.layout.activity_my_coffes_apply;
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void initData() {
        mCommonTitle.setBackgroundColor(Color.WHITE);
        mCommonTitle.setCenterText("申请提现");
        mCommonTitle.getRightTxt().setTextColor(Color.BLACK);
        mCommonTitle.getRightTxt().setOnClickListener(this);
    }

    private String med_apply_number;
    private String med_apply_name;
    private String mmy_coffers_money;

    @Override
    protected void initView() {

        ed_apply_number = findViewById(R.id.ed_apply_number);
        ed_apply_name = findViewById(R.id.ed_apply_name);
        my_coffers_money = findViewById(R.id.my_coffers_money);
        TextView money_all = findViewById(R.id.money_all);

        Intent intent = getIntent();
        //intent.putExtra("real_capital", real_capital);
        real_capital = intent.getStringExtra("real_capital");


        if (real_capital != null) {
            money_all.setText("金库总余额￥" + real_capital);
        }


        TextView apply_all = findViewById(R.id.apply_all);
        TextView apply_money = findViewById(R.id.apply_money);//提现
        apply_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_coffers_money.setText(real_capital);
            }
        });

        RankingApplyModel rankingApplyModel = new RankingApplyModel(MyCoffersApplyActivity.this);


        apply_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chang()) {
                    rankingApplyModel.getCompanyUCenterInfo(med_apply_number, med_apply_name, mmy_coffers_money, new ObtainMyCoffersDataListener() {
                        @Override
                        public void onSuccess(CoffersDataBean successMessage) {
                            Toast.getInstance().showSuccessToast(MyCoffersApplyActivity.this, "提现申请成功");
                            MyCoffersApplyActivity.this.finish();

                        }

                        @Override
                        public void onError(String errorMessage) {

                        }
                    });
                }


            }
        });


    }


    private boolean chang() {
        med_apply_number = ed_apply_number.getText().toString().trim();
        med_apply_name = ed_apply_name.getText().toString().trim();
        mmy_coffers_money = my_coffers_money.getText().toString().trim();

        if (ed_apply_number.getText().toString().equals("")) {
            Toast.getInstance().showErrorToast(MyCoffersApplyActivity.this, "请输入支付宝账号");
            return false;
        }
        if (ed_apply_name.getText().toString().equals("")) {
            Toast.getInstance().showErrorToast(MyCoffersApplyActivity.this, "请输入姓名");
            return false;
        }
        if (my_coffers_money.getText().toString().equals("")) {
            String money = my_coffers_money.getText().toString();
            if (Integer.valueOf(money) > Integer.valueOf(real_capital)) {
                Toast.getInstance().showErrorToast(MyCoffersApplyActivity.this, "余额不足");
            }

            Toast.getInstance().showErrorToast(MyCoffersApplyActivity.this, "请输入提现金额");
            return false;
        }
        return true;

    }

}

