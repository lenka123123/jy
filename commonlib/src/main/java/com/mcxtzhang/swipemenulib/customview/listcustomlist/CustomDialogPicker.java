package com.mcxtzhang.swipemenulib.customview.listcustomlist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.R;
import com.mcxtzhang.swipemenulib.customview.listdialog.ChangeISNOPicker;
import com.mcxtzhang.swipemenulib.customview.listdialog.MaxListView;
import com.mcxtzhang.swipemenulib.customview.listdialog.MyAdapter;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

public class CustomDialogPicker {

    private Activity activity;

    public AlertOnClickListener alertOnClickListener;
    private AlertDialog dlg;
    private CustomDialogAdapter customDialogAdapter;

    public CustomDialogPicker(Activity activity) {
        this.activity = activity;
    }


    public interface AlertOnClickListener {
        void alertClick(int position);
    }

    public void setAlertOnClickListener(AlertOnClickListener alertOnClickListener) {
        this.alertOnClickListener = alertOnClickListener;
    }

    private RefreshLayout mSmartRefreshLayout;
    private RecyclerView mAutoLoadRecyclerView;

    public void showAlertDialog(final List<DiscountDetail.Discount> list, String title, int nextTimePosition) {

        dlg = new AlertDialog.Builder(activity, R.style.dialog_helf_transparent).create();
        dlg.show();

        WindowManager m = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        final WindowManager.LayoutParams p = dlg.getWindow().getAttributes();  //获取对话框当前的参数值

        p.height = (int) d.getHeight() / 2;
        p.width = (int) d.getWidth();    //宽度设置为屏幕的0.5
        dlg.getWindow().setAttributes(p);     //设置生效


        Window window = dlg.getWindow();
        window.setGravity(Gravity.BOTTOM);//设置弹框在屏幕的下方
        window.setContentView(R.layout.activity_list_custom_dialog);
        TextView textTitle = (TextView) window.findViewById(R.id.title);
        textTitle.setText(title);

        mSmartRefreshLayout = window.findViewById(R.id.refreshLayout);
        mAutoLoadRecyclerView = window.findViewById(R.id.recyclerView);


        customDialogAdapter = new CustomDialogAdapter(activity);
        mAutoLoadRecyclerView.setAdapter(customDialogAdapter);
        setPullRefresher();
        final GridLayoutManager manager = new GridLayoutManager(activity, 1);
        mAutoLoadRecyclerView.setLayoutManager(manager);
        mAutoLoadRecyclerView.setItemAnimator(new DefaultItemAnimator());

        customDialogAdapter.setData(list, 1, nextTimePosition);
        customDialogAdapter.setOnItemClickListener(new CustomDialogAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                alertOnClickListener.alertClick(position);

            }
        });


    }

    public void closeAlertDialog() {
        if (dlg != null && dlg.isShowing())
            dlg.dismiss();
    }

    private void setPullRefresher() {
        mSmartRefreshLayout.setRefreshHeader(new MaterialHeader(activity));
        //   mSmartRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()));
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                currentPage = 1;
//                mRows.clear();
//                payListPresenter.getPayList(1, perpage);
                refreshlayout.finishRefresh(1000);
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                //      AllCoffersFragment.this.refreshlayout = refreshlayout;
//                if (addData) {
//                    payListPresenter.getPayList(currentPage, perpage);
//                } else {
//                    mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
//                }
//                refreshlayout.finishLoadMore(2000);
                mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        });
    }


}
