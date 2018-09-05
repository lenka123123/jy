package sinia.com.baihangeducation.reconsitution.tabs.coffers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.example.zhouwei.library.CustomPopWindow;

import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersapply.MyCoffersApplyActivity;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersdetail.MyCoffersDetail;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.reconsitution.tabs.coffers.coffersmain.RankingMainModel;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

public class MyCoffers extends BaseActivity {

    private TextView mMoney;
    private TextView mApplyMoney;
    private TextView mCallMe;
    private CustomPopWindow mCustomPopWindow;
    private String phoneNum = "";
    private String real_capital = "";
    private RankingMainModel rankingMainModel;
    private TextView mfreeze_num;

    public int initLayoutResID() {
        return R.layout.activity_my_coffes;
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }
    @Override
    protected void initData() {

        mCommonTitle.setBackgroundColor(Color.WHITE);
        mCommonTitle.setCenterText("我的金库");
        mCommonTitle.getRightTxt().setText("明细");
        mCommonTitle.getRightTxt().setTextSize(16);
        mCommonTitle.getRightTxt().setTextColor(Color.BLACK);
        mCommonTitle.getRightTxt().setOnClickListener(this);

        rankingMainModel = new RankingMainModel(context);
        getData();
        mCommonTitle.getRightTxt().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyCoffers.this, MyCoffersDetail.class));
            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getData();
    }

    @Override
    protected void initView() {
        mMoney = findViewById(R.id.my_coffers_money);
        mApplyMoney = findViewById(R.id.apply_money);
        mCallMe = findViewById(R.id.call_me);
        mfreeze_num = findViewById(R.id.freeze_num);

        mApplyMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCoffers.this, MyCoffersApplyActivity.class);
                intent.putExtra("real_capital", real_capital);
                MyCoffers.this.startActivity(intent);

            }
        });
        mCallMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shw();
            }
        });
    }


    private void shw() {
        LayoutInflater factory = LayoutInflater.from(MyCoffers.this);
        View myView = factory.inflate(R.layout.coffers_pop_item, null);
        myView.setPadding(0, 0, 0, 0);
        Dialog dialog = new AlertDialog.Builder(MyCoffers.this)
                .setView(myView).create();

        LinearLayout call = (LinearLayout) myView.findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + phoneNum);
                intent.setData(data);
                startActivity(intent);
                dialog.dismiss();
            }
        });

//设置点击Dialog外部任意区域关闭Dialog
        dialog.setCanceledOnTouchOutside(true);

        dialog.show();
//设置弹窗在底部
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = d.getWidth(); //宽度设置为屏幕
        p.height = d.getHeight() / 3;
        dialog.getWindow().setAttributes(p); //设置生效
    }

    private void getData() {
        if (rankingMainModel != null)
            rankingMainModel.getCompanyUCenterInfo(0, 0, new ObtainMyCoffersDataListener() {
                @Override
                public void onSuccess(CoffersDataBean successMessage) {
                    mMoney.setText(successMessage.total_capital);
                    phoneNum = successMessage.hotline;
                    real_capital = successMessage.real_capital;

                    double money = Double.valueOf(successMessage.total_capital);
                    if (money > 0) {
                        mApplyMoney.setEnabled(true);
                        mApplyMoney.setBackgroundResource(R.drawable.textview_round2);
                    } else {
                        mApplyMoney.setEnabled(false);
                        mApplyMoney.setBackgroundResource(R.drawable.textview_round_gray);
                    }


                    if (successMessage.lock_capital == null || successMessage.lock_capital.equals("null") || successMessage.lock_capital.equals("0.00")) {
                        mfreeze_num.setVisibility(View.INVISIBLE);
                    } else {
                        mfreeze_num.setVisibility(View.VISIBLE);
                        mfreeze_num.setText("冻结金额 ￥" + successMessage.lock_capital);
                    }
                }

                @Override
                public void onError(String errorMessage) {

                }
            });
    }
}

