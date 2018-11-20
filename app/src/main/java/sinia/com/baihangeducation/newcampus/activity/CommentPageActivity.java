package sinia.com.baihangeducation.newcampus.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.Toast;
import com.lzy.ninegrid.ImageInfo;
import com.mcxtzhang.swipemenulib.info.bean.SingleImageBean;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.newcampus.adapter.CommentAdapter;
import sinia.com.baihangeducation.newcampus.adapter.YAdapter;
import sinia.com.baihangeducation.newcampus.info.FunContantInfo;
import sinia.com.baihangeducation.newcampus.interfaces.AddFirendListener;
import sinia.com.baihangeducation.newcampus.interfaces.CommentListener;
import sinia.com.baihangeducation.newcampus.presenter.CommentPresenter;
import sinia.com.baihangeducation.newcampus.tabs.friend.NineGridView;
import sinia.com.baihangeducation.newcampus.tabs.friend.NineImageAdapter;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.bean.CommentListBean;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.interfaces.AddFollowDataListener;
import sinia.com.baihangeducation.newcampus.tabs.searchfriend.interfaces.FollowDataListener;
import sinia.com.baihangeducation.release.adapter.PhotoShowDialog;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.CommonModel;
import sinia.com.baihangeducation.supplement.tool.AdapterUtils;

public class CommentPageActivity extends BaseActivity {

    private ImageView userPhoto;
    private TextView userName;
    private TextView userTime;
    private TextView addfollow;
    private TextView userCaontant;
    private TextView tv_show;
    private TextView tv_chat_number;
    private TextView tv_ok;
    private RecyclerView comment_recycleview;
    private EditText comment;
    private CommentAdapter commentAdapter;
    private CommentPresenter commentPresenter;
    private RefreshLayout mSmartRefreshLayout;
    private TextView send;
    String parendID = "0";
    private NineGridView mNineGridView;
    private YAdapter mYAdapter;
    private RecyclerView recyclerview;
    private int mCurrentPage = 1;
    private boolean mAddData = false;

    public int initLayoutResID() {
        return R.layout.activity_comment_campus;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppConfig.mFunContantInfo = null;
    }

    protected void initView() {
        StatService.start(this);
        mCommonTitle.setCenterText("详情");

        mSmartRefreshLayout = findViewById(R.id.refreshLayout);
        send = findViewById(R.id.send);
        comment = findViewById(R.id.comment);

        comment_recycleview = findViewById(R.id.comment_recycleview);

        commentAdapter = new CommentAdapter(context, AppConfig.mFunContantInfo.publish_user_nickname, comment);

        //创建负责处理Header和footer的adpapter
        mYAdapter = new YAdapter<>(commentAdapter);
        comment_recycleview.setLayoutManager(new LinearLayoutManager(this));
        mYAdapter.setConutSpan(comment_recycleview);
        comment_recycleview.setAdapter(mYAdapter);
        comment_recycleview.setNestedScrollingEnabled(false);
        comment_recycleview.setItemAnimator(new DefaultItemAnimator());


        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parendID = "0";
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonModel commonModel = new CommonModel(context);
                commonModel.setComment(String.valueOf(AppConfig.mFunContantInfo.dynamic_id),
                        comment.getText().toString().trim(), parendID, new AddFollowDataListener() {
                            @Override
                            public void getHotFunDataSuccess() {
                                comment.setText("");
                                getData();
                                showKeyboard();
                                commentAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void getHotFunDataFail() {
                                showKeyboard();
                            }
                        });
            }
        });
    }

    private void setPullRefresher() {
//        mSmartRefreshLayout.setRefreshHeader(new MaterialHeader(context));
//        //   mSmartRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()));
//        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                if (mAddData) {
//                    mCurrentPage = 1;
//                    getData();    // TODO: 2018/7/29 0029     mFunCampusPresenter.getFunCampusList();
//                } else {
//                    mSmartRefreshLayout.setNoMoreData(false);
//                }
//                refreshlayout.finishRefresh(2000);
//            }
//        });
//        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(RefreshLayout refreshlayout) {
//                getData();
//                refreshlayout.finishRefresh(2000);
//            }
//        });
    }

    @Override
    protected void initData() {

        addFooterView();
        if (AppConfig.mFunContantInfo != null) {
            Glide.with(context).load(AppConfig.mFunContantInfo.publish_user_avatar).error(R.drawable.logo).into(userPhoto);
            userName.setText(AppConfig.mFunContantInfo.publish_user_nickname);
            userTime.setText(AppConfig.mFunContantInfo.add_time);


            String content = AppConfig.mFunContantInfo.content;
            String topic_title = AppConfig.mFunContantInfo.topic_title;
            int index = content.indexOf(topic_title);
            //文本内容
            SpannableString spannableString = new SpannableString(content);
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.title_color)), index, topic_title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            userCaontant.setText(spannableString);

            tv_show.setText("转发 " + AppConfig.mFunContantInfo.share_num);
            tv_chat_number.setText("评论 " + AppConfig.mFunContantInfo.comment_num);
            tv_ok.setText("赞 " + AppConfig.mFunContantInfo.praise_num);
            if (AppConfig.mFunContantInfo.is_follow.equals("3")) {
                addfollow.setVisibility(View.INVISIBLE);
            }
            if (AppConfig.mFunContantInfo.is_follow.equals("2")) {
                addfollow.setBackgroundResource(R.drawable.add_follow_ed);
                addfollow.setText("");
            }

            setPullRefresher();
            commentAdapter.setItemClickListener(new CommentAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(final String position) {
                    showKeyboard();
                    parendID = position;
                }
            });

//            详情页不用点击关注
//            addfollow.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    CommonModel commonModel = new CommonModel(context);
//                    commonModel.addFollow(String.valueOf(AppConfig.mFunContantInfo.publish_user_id), new FollowDataListener() {
//                        @Override
//                        public void getHotFunDataSuccess(int flag) {
//                            if (flag == 2) {
//                                addfollow.setBackgroundResource(R.drawable.add_follow_ed);
//                                addfollow.setText("");
//                            } else {
//                                addfollow.setBackgroundResource(R.drawable.attention_add);
//                                addfollow.setText("+关注");
//                                addfollow.setTextColor(Color.RED);
//                            }
//                        }
//
//                        @Override
//                        public void getHotFunDataFail() {
//
//                        }
//                    });
//                }
//            });
        }
//        AdapterUtils.setNineGrid(3, CommentPageActivity.this, AppConfig.mFunContantInfo.thumbnail_list, AppConfig.mFunContantInfo.images_list, recyclerview);
        if (AppConfig.mFunContantInfo.thumbnail_list != null
                && AppConfig.mFunContantInfo.thumbnail_list.size() >= 1
                && AppConfig.mFunContantInfo.thumbnail_list.get(0) != null
                && AppConfig.mFunContantInfo.thumbnail_list.get(0).url != null
                && AppConfig.mFunContantInfo.thumbnail_list.get(0).url.length() > 1) {
            clickNineGridView(mNineGridView, AppConfig.mFunContantInfo.thumbnail_list, AppConfig.mFunContantInfo.images_list, 1);
            mNineGridView.setVisibility(View.VISIBLE);
        } else {
            mNineGridView.setVisibility(View.GONE);
        }

        commentPresenter = new CommentPresenter(this);

        getData();

    }


    private void clickNineGridView(NineGridView nineGridView, List<SingleImageBean> tem, List<SingleImageBean> image, int position) {

        if (tem == null || tem.size() <= 0) {
            nineGridView.setVisibility(View.GONE);
            return;
        } else {
            nineGridView.setVisibility(View.VISIBLE);
        }

        List<String> urlList = new ArrayList<>();
        for (int i = 0; i < tem.size(); i++) {
            urlList.add(i, tem.get(i).url);
        }

        final ArrayList<ImageInfo> imageInfo = new ArrayList<>();

        for (int i = 0; i < tem.size(); i++) {
            ImageInfo info = new ImageInfo();
            info.setThumbnailUrl(tem.get(i).url);
            info.setBigImageUrl(image.get(i).url);
            imageInfo.add(info);
        }

        nineGridView.setAdapter(new NineImageAdapter(context, urlList));
        nineGridView.setOnImageClickListener(new NineGridView.OnImageClickListener() {
            @Override
            public void onImageClick(int position, View view) {
                PhotoShowDialog photoShowDialog = new PhotoShowDialog(context, imageInfo, position);
                photoShowDialog.show();
            }
        });

    }


    private void addFooterView() {
        View layout = LayoutInflater.from(CommentPageActivity.this).inflate(R.layout.activity_comment_campus_head, null);
        recyclerview = layout.findViewById(R.id.recyclerview);
        userPhoto = layout.findViewById(R.id.newcampayfunitem_uesericon);
        userName = layout.findViewById(R.id.newcampayfunitem_uesername);
        userTime = layout.findViewById(R.id.newcampayfunitem_time);
        addfollow = layout.findViewById(R.id.addfollow);
        addfollow.setVisibility(View.GONE);
        userCaontant = layout.findViewById(R.id.newcampayfunitem_caontant);
        tv_show = layout.findViewById(R.id.tv_show);
        tv_chat_number = layout.findViewById(R.id.tv_chat_number);
        tv_ok = layout.findViewById(R.id.tv_ok);
        mNineGridView = layout.findViewById(R.id.nine_grid_view);
        mYAdapter.addHeaderView(layout);
    }

    public void getData() {
        commentAdapter.cleanData();
        commentPresenter.releaseComment("7", AppConfig.mFunContantInfo.dynamic_id + "", "0", String.valueOf(mCurrentPage), "20", new CommentListener() {
            @Override
            public void getAddFirendSuccessListener(CommentListBean commentListBean) {
                if (20 * mCurrentPage < Integer.valueOf(commentListBean.count)) {
                    mAddData = true;
                    mCurrentPage = mCurrentPage + 1;
                } else {
                    mAddData = false;
                }

                ((CommentAdapter) mYAdapter.getAdapter()).setData(commentListBean);
                mYAdapter.notifyDataSetChanged();
            }

            @Override
            public void getAddFirendFailListener() {

            }
        });
    }


    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //这个方法在界面上切换输入法的功能，如果输入法出于现实状态，就将他隐藏，如果处于隐藏状态，就显示输入法
    }
}
