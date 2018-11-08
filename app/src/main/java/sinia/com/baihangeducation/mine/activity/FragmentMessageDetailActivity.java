package sinia.com.baihangeducation.mine.activity;

import android.content.Intent;
import android.widget.TextView;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.mine.presenter.FragmentMessagePresenter;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

import com.lzy.imagepicker.ImagePicker;
import com.mcxtzhang.swipemenulib.info.bean.FragmentMessageInfo;

/**
 * Created by Administrator on 2018/4/28.
 */

public class FragmentMessageDetailActivity extends BaseActivity {

    private Intent intent;
    private FragmentMessageInfo mMessageInfo;

    private TextView title;
    private TextView date;
    private TextView content;

    @Override
    public int initLayoutResID() {
        return R.layout.fragment_message_detail;
    }

    @Override
    protected void initData() {
//        ImagePicker.Config config = new ImagePicker.Config(new GlideImageLoader());
        mCommonTitle.setCenterText(R.string.messagedetail);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
        FragmentMessagePresenter  mFragmentMessagePresenter = new FragmentMessagePresenter(this);

        intent = getIntent();
        mMessageInfo = (FragmentMessageInfo) intent.getSerializableExtra("MESSAGE");

        if (mMessageInfo != null) {
            title.setText(mMessageInfo.title);
            date.setText(mMessageInfo.date);
            content.setText(mMessageInfo.content);
            mFragmentMessagePresenter.setMessageRead(mMessageInfo.system_message_id);
        }
    }

    @Override
    protected void initView() {
        title = $(R.id.fragment_message_detail_title);
        date = $(R.id.fragment_message_detail_date);
        content = $(R.id.fragment_message_detail_content);
    }
}
