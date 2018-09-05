package sinia.com.baihangeducation.mine.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.alertview.AlertViewContorller;
import sinia.com.baihangeducation.supplement.alertview.OnItemClickListener;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.mine.presenter.ReleaseHelpEachOtherPresenter;
import sinia.com.baihangeducation.mine.view.IReleaseView;

import com.mcxtzhang.swipemenulib.utils.Constants;

/**
 * 发布互助页面
 */

public class ReleaseHelpEachOtherActivity extends BaseActivity implements OnItemClickListener, IReleaseView {

    private TextView mChoiceType;               //选择类型      type1转让 type2互助
    private EditText mHelpPeopleNum;            //求助人数      仅type为1传递
    private TextView mIsPay;                    //付费要求      仅type为1传递       1为付费，2为免费
    private TextView mGender;                   //性别要求      1男  2女  3不限
    private EditText mPayMoney;                 //输入金额      仅type为1传递
    private EditText mTitle;                    //输入标题
    private EditText mAddContant;               //添加内容
    private TextView mWordNum;                  //添加内容字数限制
    private TextView mClean;                    //清除添加内容
    private TextView mConfirm;                  //确认按钮

    private ReleaseHelpEachOtherPresenter presenter;
    private AlertViewContorller mAlertViewContorller;//避免创建重复View，先创建View，然后需要的时候show出来，推荐这个做法
    String[] type = {"转让", "互助"};
    String[] ispay = {"付费", "免费"};
    String[] gender = {"男", "女", "不限"};


    @Override
    public int initLayoutResID() {
        return R.layout.releasehelpeachother;
    }

    @Override
    protected void initData() {
        mCommonTitle.setCenterText(R.string.releasehelpeachother_title);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        presenter = new ReleaseHelpEachOtherPresenter(context, this);
        mAddContant.addTextChangedListener(mTextWatcher);

    }

    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (temp.length() <= 200) {
                mWordNum.setText((200 - temp.length()) + "");
            }
        }
    };

    @Override
    protected void initView() {

        mChoiceType = $(R.id.releasehelpeachother_type);
        mHelpPeopleNum = $(R.id.releasehelpeachother_helppeoplenum);
        mIsPay = $(R.id.releasehelpeachother_ispay);
        mGender = $(R.id.releasehelpeachother_gender);
        mPayMoney = $(R.id.releasehelpeachother_paymoney);
        mTitle = $(R.id.releasehelpeachother_title);
        mAddContant = $(R.id.releasehelpeachother_addcaontant);
        mWordNum = $(R.id.releasehelpeachother_wordnum);
        mClean = $(R.id.releasehelpeachother_clean);
        mConfirm = $(R.id.releasehelpeachother_confirm);

        mChoiceType.setOnClickListener(this);
        mIsPay.setOnClickListener(this);
        mGender.setOnClickListener(this);
        mClean.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.releasehelpeachother_type:
                //选择类型

                //隐藏键盘
                ((InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow((ReleaseHelpEachOtherActivity.this).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                mAlertViewContorller = new AlertViewContorller(mChoiceType, "选择类型", null, "取消", null, type,
                        context, AlertViewContorller.Style.ActionSheet, this);
                mAlertViewContorller.setCancelable(true).show();
                break;
            case R.id.releasehelpeachother_ispay:
                //付费要求
                mAlertViewContorller = new AlertViewContorller(mIsPay, "付费要求", null, "取消", null, ispay,
                        context, AlertViewContorller.Style.ActionSheet, this);
                mAlertViewContorller.setCancelable(true).show();
                break;
            case R.id.releasehelpeachother_gender:
                //选择性别f
                mAlertViewContorller = new AlertViewContorller(mGender, "选择性别", null, "取消", null, gender,
                        context, AlertViewContorller.Style.ActionSheet, this);
                mAlertViewContorller.setCancelable(true).show();
                break;
            case R.id.releasehelpeachother_clean:
                //清除内容
                mAddContant.setText("");
                mAddContant.setHint(R.string.releasehelpeachother_addcontant);
                break;
            case R.id.releasehelpeachother_confirm:
                //确认发布
                mConfirm.setEnabled(false);
                presenter.doReleaseHelpEachOhter();
                break;
        }
    }

    @Override
    public void onItemClick(View view, List<String> mOthers, Object o, int position) {
        if (position == -1) {
            mAlertViewContorller.dismiss();
        } else {
            TextView view1 = (TextView) view;
            view1.setText(mOthers.get(position));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mAlertViewContorller != null && mAlertViewContorller.isShowing()) {
                mAlertViewContorller.dismiss();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void showLoading() {
        showProgress();
    }

    @Override
    public void hideLoading() {
        hideProgress();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideProgress();
    }

    @Override
    public String getTab() {
        return Constants.RELEASE_TAB_HELPEACHOTHER;
    }

    @Override
    public String getType() {
        return (mChoiceType.getText().toString()).equals("转让") ? Constants.RELEASE_TAB_HELPEACHOTHER_TYPE_TRANSFER : Constants.RELEASE_TAB_HELPEACHOTHER_TYPE_HELPEACHOTHER;
    }

    @Override
    public String getNeedPeopleNum() {
        return mHelpPeopleNum.getText().toString().trim();
    }

    @Override
    public String getIsPay() {
        return (mIsPay.getText().toString()).equals("付费") ? Constants.RELEASE_TAB_HELPEACHOTHER_ISPAY_YES : Constants.RELEASE_TAB_HELPEACHOTHER_ISPAY_NO;
    }

    @Override
    public String getSex() {
        if ((mGender.getText().toString().trim()).equals("男")) {
            return Constants.RELEASE_TAB_HELPEACHOTHER_SEX_MALE;
        } else if ((mGender.getText().toString().trim()).equals("女")) {
            return Constants.RELEASE_TAB_HELPEACHOTHER_SEX_FAMALE;
        } else {
            return Constants.RELEASE_TAB_HELPEACHOTHER_SEX_OTHER;
        }
    }

    @Override
    public String getSPrice() {
        return mPayMoney.getText().toString().trim();
    }

    @Override
    public String getInputTitle() {
        return mTitle.getText().toString().trim();
    }

    @Override
    public String getInputContent() {
        return mAddContant.getText().toString().trim();
    }

    @Override
    public String geLocationLng() {
        return getLng();
    }

    @Override
    public String getLocationLat() {
        return getLat();
    }

    @Override
    public String getImage() {
        return null;
    }

    @Override
    public void releaseHelpSuccess() {
        finish();
    }

    @Override
    public void releaseInterestingSuccess() {

    }
}
