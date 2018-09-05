package sinia.com.baihangeducation.home.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framwork.glide.ImageLoaderUtils;
import com.mcxtzhang.swipemenulib.customview.NoScrollListView;
import com.mcxtzhang.swipemenulib.info.CityIdInfo;
import com.mcxtzhang.swipemenulib.info.HomeListInfo;
import com.mcxtzhang.swipemenulib.info.IsCompleteInfo;
import com.mcxtzhang.swipemenulib.info.JobInfo;
import com.mcxtzhang.swipemenulib.info.bean.AddCollectionSuccessInfo;
import com.mcxtzhang.swipemenulib.info.bean.CityInfo;
import com.mcxtzhang.swipemenulib.info.bean.IndustryListInfo;
import com.mcxtzhang.swipemenulib.info.bean.IndustryListInfoHome;
import com.mcxtzhang.swipemenulib.utils.Constants;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.home.adapter.AllJobDetailAdapter;
import sinia.com.baihangeducation.home.adapter.GridViewHome;
import sinia.com.baihangeducation.home.adapter.GridViewHomeMore;
import sinia.com.baihangeducation.home.adapter.GridViewSimForOther;
import sinia.com.baihangeducation.home.present.AddOrDetelCollctionPresenter;
import sinia.com.baihangeducation.home.present.GetIndustryListener;
import sinia.com.baihangeducation.home.present.HomePresenter;
import sinia.com.baihangeducation.home.present.JobInfoPresenter;
import sinia.com.baihangeducation.home.view.AddCollctionView;
import sinia.com.baihangeducation.home.view.HomeView;
import sinia.com.baihangeducation.home.view.JobView;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.BaseUtil;

/**
 * Created by Administrator on 2018/4/23.
 */

public class MoreDetailActivity extends BaseActivity {
    private GridView gridView;
    private SimpleAdapter adapter;

    private List<IndustryListInfoHome> listInfos;
    private GridViewSimForOther gridViewSimForOther;

    public int initLayoutResID() {
        return R.layout.more_gradview;
    }

    protected void initData() {
        HomePresenter homePresenter = new HomePresenter(this, null);
        homePresenter.getIndustry(new GetIndustryListener() {
            @Override
            public void getDataSuccess(List<IndustryListInfoHome> data) {
                MoreDetailActivity.this.listInfos = data;
                gridViewSimForOther.puData(data);
            }

            @Override
            public void getDataFaile() {

            }
        });
    }

    @Override
    protected void initView() {

        mCommonTitle.setCenterText("更多");
        mCommonTitle.setBackgroundColor(Color.WHITE);

        gridView = findViewById(R.id.gridview);
        gridViewSimForOther = new GridViewSimForOther(MoreDetailActivity.this);

        gridView.setAdapter(gridViewSimForOther);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listInfos.size() >= 1) {
                    Intent intent = new Intent(context, HomePartTimeActivity.class);
                    intent.putExtra("indutryId", listInfos.get(i).id);
                    context.startActivity(intent);
                }
            }
        });
    }
}
