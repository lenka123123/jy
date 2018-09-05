package sinia.com.baihangeducation.newcampus.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.mcxtzhang.swipemenulib.adapter.BaseRecycleAdapter;
import com.mcxtzhang.swipemenulib.adapter.BaseViewHolder;
import com.mcxtzhang.swipemenulib.info.bean.CityInfo;
import java.util.Locale;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.newcampus.interfaces.AddFirendListener;
import sinia.com.baihangeducation.newcampus.presenter.AddFirendPresenter;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

public class AddFirendActivity extends AppCompatActivity {


    private TextView mRecommendMore;
    private TextView mLikeMore;
    private TextView mRoundMore;
    private RecyclerView mRecommendMoreRv;
    private RecyclerView mLikeMoreRv;
    private RecyclerView mRoundMoreRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campus_add_friend_activity);
        initView();
        initData();
        StatService.start(this);
    }


    private void initView() {
        mRecommendMore = findViewById(R.id.tv_recommend_more);
        mLikeMore = findViewById(R.id.tv_like_more);
        mRoundMore = findViewById(R.id.tv_round_more);

        mRecommendMoreRv = findViewById(R.id.rv_recommend_more);
        mLikeMoreRv = findViewById(R.id.rv_like_more);
        mRoundMoreRv = findViewById(R.id.rv_round_more);
    }

    private void initData() {
        AddFirendPresenter addFirendPresenter = new AddFirendPresenter(AddFirendActivity.this);
        addFirendPresenter.releaseComment(new AddFirendListener() {
            @Override
            public void getAddFirendSuccessListener() {
                //

                setData();
            }

            @Override
            public void getAddFirendFailListener() {

            }
        });
    }

    private void setData() {
        final BaseRecycleAdapter adapter = new BaseRecycleAdapter<CityInfo>(AddFirendActivity.this, null, R.layout.campus_add_firend_item){

            @Override
            public void convert(BaseViewHolder holder, CityInfo item, int position, boolean isScrolling) {
             //   holder.setText(R.id.item_text, item.getText());

            }

//            public void convert(BaseViewHolder holder, Data item, int position, boolean isScrolling) {
//                holder.setText(R.id.item_text, item.getText());
//                if (item.getImageUrl() != null) {
//                    holder.setImageByUrl(R.id.item_image, item.getImageUrl());
//                } else {
//                    holder.setImageResource(R.id.item_image, item.getImageId());
//                }
//            }

        };

        adapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, final View view, int position) {
                Toast.makeText(AddFirendActivity.this, String.format(Locale.CHINA, "你点击了第%d项,长按会删除！", position), Toast.LENGTH_SHORT).show();
            }
        });

//        adapter.setOnItemLongClickListener(new BaseRecycleAdapter.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(RecyclerView parent, View view, int position) {
//                adapter.delete(position);
//                return true;
//            }
//        });


        mRecommendMoreRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecommendMoreRv.setAdapter(adapter);
        mLikeMoreRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mLikeMoreRv.setAdapter(adapter);
        mRoundMoreRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRoundMoreRv.setAdapter(adapter);
    }
}
