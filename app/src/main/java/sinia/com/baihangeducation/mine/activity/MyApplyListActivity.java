package sinia.com.baihangeducation.mine.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.mine.adapter.MyApplyListAdapter;
import com.mcxtzhang.swipemenulib.info.bean.FindApplyPersonInfo;
import com.mcxtzhang.swipemenulib.info.bean.FindApplyPersonListInfo;
import sinia.com.baihangeducation.mine.presenter.GetApplyMyReleasePersent;
import sinia.com.baihangeducation.mine.view.GetApplyMyReleaseView;

public class MyApplyListActivity extends BaseActivity implements GetApplyMyReleaseView {

    private GetApplyMyReleasePersent presenter;
    private Intent intent;
    private String cooperationId;
    private String applyUserId;

    private List<FindApplyPersonInfo> mData;
    private MyApplyListAdapter adapter;

    private ListView mListView;
    private TextView mNoContent;

    @Override
    public int initLayoutResID() {
        return R.layout.applylist;
    }

    @Override
    protected void initData() {
        mCommonTitle.setCenterText(R.string.applyperson);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        intent = getIntent();
        cooperationId = intent.getStringExtra("COOPID");

        presenter = new GetApplyMyReleasePersent(context, this);
        presenter.getApplyList();
    }

    @Override
    protected void initView() {
        mListView = $(R.id.applylist_listview);
        mNoContent = $(R.id.applylist_nocontent);
    }

    @Override
    public String getApplyUserId() {
        return applyUserId;
    }

    @Override
    public String getCooperationId() {
        return cooperationId;
    }

    @Override
    public void getSuccess(FindApplyPersonListInfo info) {
        if (info.count <= 0) {
            mNoContent.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        } else {
            mNoContent.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
        }

        if (info.list!=null&&info.list.size()>0){
            mData = info.list;
            adapter = new MyApplyListAdapter(context,mData);
            mListView.setAdapter(adapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    new AlertDialog.Builder(context).setTitle("提示").setMessage("你确定选择"+mData.get(position).apply_user_name+"为完成对象？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //确定
                            applyUserId = mData.get(position).apply_user_id+"";
                            presenter.confirmPerson();
                        }
                    }).setNegativeButton("取消",null).show();
                }
            });
        }
    }

    @Override
    public void getConfirmSuccess() {
        finish();
    }
}
