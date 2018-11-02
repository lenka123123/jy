package sinia.com.baihangeducation.club.applyclublist;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framwork.utils.SpCommonUtils;
import com.mcxtzhang.swipemenulib.customview.recycleitemdeltet.view.YRecyclerView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.applyclublist.interfaces.ApplyClubListContract;
import sinia.com.baihangeducation.club.applyclublist.model.ApplyClubListBean;
import sinia.com.baihangeducation.club.applyclublist.model.ApplyClubListModel;
import sinia.com.baihangeducation.club.applyclublist.model.GetPersonList;
import sinia.com.baihangeducation.club.applyclublist.presenter.ApplyClubListPresenter;
import sinia.com.baihangeducation.club.applyclublist.view.GetPeronListAdapter;
import sinia.com.baihangeducation.club.club.interfaces.GetRequestListener;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

public class ClubPersonClubListActivity extends BaseActivity implements ApplyClubListContract.View {
    private List<GetPersonList.PersonList> mRows = new ArrayList<>();

    private String text = "";
    private YRecyclerView mAutoLoadRecyclerView;

    private GetPeronListAdapter paiedListAdapter;
    private boolean ishowMessage = false;
    private boolean addData = false;
    private int currentPage = 1;
    private int perpage = 20;
    private boolean isCreated = false;
    private ApplyClubListPresenter clubSchoolListPresenter;
    private TextView textView;
    private String club_id;
    private boolean drop;
    private boolean setClubApply;
    private TextView editor;
    private ImageView dim_ok;
    private LinearLayout dim;
    private String is_chairman;

    public int initLayoutResID() {
        return R.layout.activity_club_apply;
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (clubSchoolListPresenter != null && paiedListAdapter != null) {
            currentPage = 1;
            mRows.clear();
            ishowMessage = false;
            paiedListAdapter.setData(mRows, currentPage, is_chairman);
            clubSchoolListPresenter.getPersonList(club_id, "1", String.valueOf(perpage));
        }


    }


    @Override
    protected void initView() {
        boolean is_dim = (boolean) SpCommonUtils.get(context, AppConfig.IS_DIM, false);
        Intent intent = getIntent();
        is_chairman = intent.getStringExtra("is_chairman");
        club_id = intent.getStringExtra("club_id");
        drop = intent.getBooleanExtra("drop", false);
        setClubApply = intent.getBooleanExtra("setClubApply", false);

        mAutoLoadRecyclerView = findViewById(R.id.recycler_view);
        editor = findViewById(R.id.editor);
        editor.setOnClickListener(this);
        textView = findViewById(R.id.fragment_home_adressName);
        dim_ok = findViewById(R.id.dim_ok);
        dim_ok.setOnClickListener(this);
        dim = findViewById(R.id.dim);
        if (is_dim) {
            dim.setVisibility(View.GONE);
        } else {
            if (is_chairman.equals("1") || drop)
                dim.setVisibility(View.VISIBLE);
        }
        textView.setText("成员列表");
        editor.setVisibility(View.INVISIBLE);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        paiedListAdapter = new GetPeronListAdapter(this, this, drop, setClubApply);
        mAutoLoadRecyclerView.setAdapter(paiedListAdapter);

        final GridLayoutManager manager = new GridLayoutManager(this, 1);
        mAutoLoadRecyclerView.setLayoutManager(manager);
        mAutoLoadRecyclerView.setItemAnimator(new DefaultItemAnimator());
        addData = false;
        paiedListAdapter.setData(mRows, currentPage, is_chairman);

        mAutoLoadRecyclerView.setRefreshAndLoadMoreListener(new YRecyclerView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mRows.clear();
                        currentPage = 1;
                        System.out.println("onLoadMore==== " + currentPage);
                        ishowMessage = false;
                        paiedListAdapter.setData(mRows, currentPage, is_chairman);
                        clubSchoolListPresenter.getPersonList(club_id, currentPage + "", String.valueOf(perpage));

                    }
                }, 2500);
            }

            @Override
            public void onLoadMore() {
                System.out.println("onLoadMore==== " + currentPage);
                Log.i("加载更多", "000");
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mRows.clear();
                        clubSchoolListPresenter.getPersonList(club_id, currentPage + "", String.valueOf(perpage));

                    }
                }, 2500);
            }
        });

    }

    public void removeItem(GetPersonList.PersonList personList, GetRequestListener listener) {
        clubSchoolListPresenter.dropCrew(personList.id, new GetRequestListener() {
            @Override
            public void setRequestSuccess(String msg) {
                listener.setRequestSuccess(msg);
            }

            @Override
            public void setRequestFail() {
                listener.setRequestFail();
            }
        });

    }

    @Override
    protected void initData() {
//        mCommonTitle.setBackgroundColor(Color.WHITE);
//        mCommonTitle.setCenterText("我的购买");
//        mCommonTitle.getRightTxt().setTextSize(16);
//        mCommonTitle.getRightTxt().setTextColor(Color.BLACK);


        clubSchoolListPresenter = new ApplyClubListPresenter(new ApplyClubListModel(context), this);
        clubSchoolListPresenter.getPersonList(club_id, "1", String.valueOf(perpage));
        text = "";


    }

//
//    private void setPullRefresher() {
//        mSmartRefreshLayout.setRefreshHeader(new MaterialHeader(this));
//        //   mSmartRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()));
//        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                currentPage = 1;
//                mRows.clear();
//
////                clubSchoolListPresenter.getSearchSchoolList("1", String.valueOf(perpage), "");
//                refreshlayout.finishRefresh(500);
//            }
//        });
//
//        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(RefreshLayout refreshlayout) {
//                //      AllCoffersFragment.this.refreshlayout = refreshlayout;
//                if (addData) {
//                    clubSchoolListPresenter.getSearchSchoolList(String.valueOf(currentPage), String.valueOf(perpage), "");
//                } else {
//                    mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
//                }
//                refreshlayout.finishLoadMore(2000);
//            }
//        });
//    }
//
//    private void showErrorState(boolean isError) {
////        if (isError) {
////            mErrorImageView.setVisibility(View.VISIBLE);
////            mErrorTextView.setVisibility(View.VISIBLE);
////            Glide.with(context).load(R.mipmap.ic_launcher).into(mErrorImageView);
////            mErrorTextView.setText("没有数据");
////            if (mSmartRefreshLayout != null)
////                mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
////        } else {
////            mErrorImageView.setVisibility(View.GONE);
////            mErrorTextView.setVisibility(View.GONE);
////        }
//    }

    @Override
    public void showApplyClubList(ApplyClubListBean successMessage, int maxpage) {


    }


    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void showApplySuccess(String errorMessage) {

    }

    @Override
    public void showGetPersonListSuccess(GetPersonList getPersonList, int maxpage) {
        if (mAutoLoadRecyclerView != null) {
            mAutoLoadRecyclerView.setloadMoreComplete();
            mAutoLoadRecyclerView.setReFreshComplete();
        }
        if (currentPage == 1) {
            for (int i = 0; i < getPersonList.list.size(); i++) {
                if (getPersonList.list.get(i).type.equals("1") && AppConfig.USERID.equals(getPersonList.list.get(i).user_id)) {
                    ishowMessage = true;
                    paiedListAdapter.setData(mRows, currentPage, is_chairman);
                    editor.setVisibility(View.VISIBLE);
                }
            }
        }

        if (getPersonList.list.size() == 0) {
        } else {
            currentPage++;
            if (maxpage == 1 || currentPage > maxpage) {
                mAutoLoadRecyclerView.setLoadMoreEnabled(false);
            } else {
                mAutoLoadRecyclerView.setLoadMoreEnabled(true);
            }
        }
        mRows.addAll(getPersonList.list);
        paiedListAdapter.setData(mRows, currentPage, is_chairman);
        paiedListAdapter.notifyDataSetChanged();


    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.dim_ok:
                dim.setVisibility(View.GONE);
                SpCommonUtils.put(context, AppConfig.IS_DIM, true);
                break;
            case R.id.editor:
                Goto.toMangerPower(context, club_id);
                break;
        }

    }
}
