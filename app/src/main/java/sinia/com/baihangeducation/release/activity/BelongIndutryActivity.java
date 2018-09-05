package sinia.com.baihangeducation.release.activity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.release.adapter.BelongIndutryAdapter;
import sinia.com.baihangeducation.release.info.ReleaseJobInfoListInfo;
import sinia.com.baihangeducation.release.info.bean.JobTypeListInfo;
import sinia.com.baihangeducation.release.presenter.ReleaseJobInfoPresenter;
import sinia.com.baihangeducation.release.view.IReleaseJobInfoView;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import com.mcxtzhang.swipemenulib.utils.ACache;

public class BelongIndutryActivity extends BaseActivity implements IReleaseJobInfoView {
    private ReleaseJobInfoPresenter presenter;
    private TextView mConfirm;
    private TagFlowLayout layout;
    private List<JobTypeListInfo> belongindutry;                 //所属行业
    private BelongIndutryAdapter adapter;
    private ACache cache;
    private String indexdata;
    private String data ;

    @Override
    public int initLayoutResID() {
        return R.layout.belongindutry;
    }

    @Override
    protected void initData() {
        cache = ACache.get(context);
        belongindutry = new ArrayList<>();

        mCommonTitle.setCenterText(R.string.belingindutry);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));
        presenter = new ReleaseJobInfoPresenter(context, this);
        presenter.getJobOptionList();


        layout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                data = "";
                indexdata = "";
                if (belongindutry != null || belongindutry.size() != 0) {
                    Iterator<Integer> iterator = selectPosSet.iterator();
                    while (iterator.hasNext()) {
                        int index = iterator.next();
                        String data1 = belongindutry.get(index).type_name;
                        data = data + data1+",";
                        indexdata = indexdata+index+",";
                        Log.i("选择数据",data);
                        Log.i("选择数据",indexdata);
                    }
                }

            }
        });

    }

    @Override
    protected void initView() {
        layout = $(R.id.belongindutry);
        mConfirm = $(R.id.confirm);
        mConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.confirm:
                data = data.substring(0,data.length()-1);
                indexdata = indexdata.substring(0,indexdata.length()-1);
                cache.put("belongindutry",data);
                cache.put("belongindutryindex",indexdata);
                finish();
                break;
        }
    }

    @Override
    public void getReleaseJobInfoSuccess(ReleaseJobInfoListInfo info) {
        if (info != null) {
            if (info.job_type_list != null && info.job_type_list.size() > 0) {
                belongindutry = info.job_type_list;

                adapter = new BelongIndutryAdapter(context, layout, belongindutry);
                layout.setAdapter(adapter);
            }
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
