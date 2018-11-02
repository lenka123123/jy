package sinia.com.baihangeducation.home.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framwork.glide.ImageLoaderUtils;
import com.mcxtzhang.swipemenulib.customview.NoScrollListView;
import com.mcxtzhang.swipemenulib.info.JobInfo;
import com.mcxtzhang.swipemenulib.info.bean.AddCollectionSuccessInfo;
import com.mcxtzhang.swipemenulib.utils.Constants;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Set;

import sinia.com.baihangeducation.MyApplication;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.home.adapter.AllJobDetailAdapter;
import sinia.com.baihangeducation.home.present.AddOrDetelCollctionPresenter;
import sinia.com.baihangeducation.home.present.JobInfoPresenter;
import sinia.com.baihangeducation.home.view.AddCollctionView;
import sinia.com.baihangeducation.home.view.JobView;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;
import sinia.com.baihangeducation.supplement.tool.BaseUtil;

/**
 * Created by Administrator on 2018/4/23.
 */

public class PortionActivity extends BaseActivity {


    @Override
    public int initLayoutResID() {
        return R.layout.activity_webview;
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        WebView view = findViewById(R.id.webview);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        view.loadUrl(url);
    }
}
