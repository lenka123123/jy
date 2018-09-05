package sinia.com.baihangeducation.release.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.info.HomePartTimeSearchListInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeDistInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeSalaryInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachIndustryInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachOrderInfo;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.home.HomeFragment;
import sinia.com.baihangeducation.release.adapter.SiftUrNeedAreaAdapter;
import sinia.com.baihangeducation.release.adapter.SiftUrNeedIndustryAdapter;
import sinia.com.baihangeducation.release.adapter.SiftUrNeedOrderAdapter;
import sinia.com.baihangeducation.release.adapter.SiftUrNeedSalaryAdapter;
import sinia.com.baihangeducation.release.presenter.SiftUrNeedPresenter;
import sinia.com.baihangeducation.release.view.ISiftUrNeedView;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

public class FullTimeActivity extends BaseActivity implements ISiftUrNeedView {
    private Intent intent;
    private String flag;            //区分从兼职还是全职跳转过来的
    private String type;            //1全职 2兼职
    private SiftUrNeedPresenter presenter;

    private LinearLayout layout;
    private TextView mIndusty;
    private TextView mPrice;
    private TextView mArea;
    private TextView mOrder;
    private Button mComplete;
    private Button mNext;

    public List<HomePartTimeSalaryInfo> mSalaryList;                    //薪资
    public List<HomeTraingSeachIndustryInfo> mIndustryList;             //行业
    public List<HomeTraingSeachOrderInfo> mOrderList;                   //排序
    public List<HomePartTimeDistInfo> mDistList;                        //地区
    private String industryId;
    private String salaryId;
    private String areaId;
    private String orderId;

    private SiftUrNeedIndustryAdapter industryAdapter;
    private SiftUrNeedSalaryAdapter salaryAdapter;
    private SiftUrNeedAreaAdapter areaAdapter;
    private SiftUrNeedOrderAdapter orderAdapter;

    @Override
    public int initLayoutResID() {
        return R.layout.sifturneed;
    }

    @Override
    protected void initData() {
        intent = getIntent();
        flag = intent.getStringExtra("FLAG");
        //根据flag判断全职还是兼职，为type赋值
        switch (flag) {
            case "1":
                type = "1";
                break;
            case "2":
                type = "2";
                break;
        }

        mSalaryList = new ArrayList<>();
        mIndustryList = new ArrayList<>();
        mOrderList = new ArrayList<>();
        mDistList = new ArrayList<>();

        mCommonTitle.setCenterText(R.string.siftUrNeed);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        presenter = new SiftUrNeedPresenter(context, this);
        presenter.getPartTimeSeachList();

    }

    @Override
    protected void initView() {
        layout = $(R.id.sifturneed);
        mIndusty = $(R.id.sifturneed_industry);
        mPrice = $(R.id.sifturneed_price);
        mArea = $(R.id.sifturneed_area);
        mOrder = $(R.id.sifturneed_order);
        mComplete = $(R.id.sifturneed_complete);
        mNext = $(R.id.sifturneed_next);

        mIndusty.setOnClickListener(this);
        mPrice.setOnClickListener(this);
        mArea.setOnClickListener(this);
        mOrder.setOnClickListener(this);
        mComplete.setOnClickListener(this);
        mNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.sifturneed_industry:
                addChoicePopWindow(1);
                break;
            case R.id.sifturneed_price:
                addChoicePopWindow(2);
                break;
            case R.id.sifturneed_area:
                addChoicePopWindow(3);
                break;
            case R.id.sifturneed_order:
                addChoicePopWindow(4);
                break;
            case R.id.sifturneed_complete:
                Log.i("职业筛选条件",industryId+"行业发送");
                Log.i("职业筛选条件",salaryId+"薪资发送");
                Log.i("职业筛选条件",areaId+"地区发送");
                Log.i("职业筛选条件",orderId+"排序发送");
                if (type=="1"){
                    Goto.toHomeHunterActivity(context, industryId, salaryId, areaId, orderId);
                }else
                  //  Goto.toHomePartTimeActivity(context, industryId, salaryId, areaId, orderId);
//                switch (flag) {
//                    case "1":
//                        Goto.toHomeHunterActivity(context, industryId, salaryId, areaId, orderId);
//                        break;
//                    case "2":
//                        Goto.toHomePartTimeActivity(context, industryId, salaryId, areaId, orderId);
//                        break;
//                }
                break;
            case R.id.sifturneed_next:
                if (type=="1")
                    Goto.toHomeHunterActivity(context);
                else
                    Goto.toHomePartTimeActivity(context);
//                switch (flag) {
//                    case "1":
//                        Goto.toHomeHunterActivity(context);
//                        break;
//                    case "2":
//                        Goto.toHomePartTimeActivity(context);
//                        break;
//                }
                break;
        }
    }

    /**
     * 添加选项
     */
    private void addChoicePopWindow(int listTye) {
        // 用于PopupWindow的View
        View contentView = LayoutInflater.from(context).inflate(R.layout.poplistview, null, false);
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        final PopupWindow window = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, 800, true);
        ListView listView = contentView.findViewById(R.id.poplistview);
        Button button = contentView.findViewById(R.id.poplistview_cancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        switch (listTye) {
            case 1:
                industryAdapter = new SiftUrNeedIndustryAdapter(context, mIndustryList);
                listView.setAdapter(industryAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mIndusty.setText(mIndustryList.get(position).industry_name);
                        industryId = mIndustryList.get(position).industry_id + "";
                        window.dismiss();
                    }
                });
                break;
            case 2:
                salaryAdapter = new SiftUrNeedSalaryAdapter(context, mSalaryList);
                listView.setAdapter(salaryAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mPrice.setText(mSalaryList.get(position).money_name);
                        salaryId = mSalaryList.get(position).money_id + "";
                        window.dismiss();
                    }
                });
                break;
            case 3:
                areaAdapter = new SiftUrNeedAreaAdapter(context, mDistList);
                listView.setAdapter(areaAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mArea.setText(mDistList.get(position).dist_name);
                        areaId = mDistList.get(position).dist_id + "";
                        window.dismiss();
                    }
                });
                break;
            case 4:
                orderAdapter = new SiftUrNeedOrderAdapter(context, mOrderList);
                listView.setAdapter(orderAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mOrder.setText(mOrderList.get(position).order_name);
                        orderId = mOrderList.get(position).order_id + "";
                        window.dismiss();
                    }
                });
                break;

        }

        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
        window.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
        // 或者也可以调用此方法显示PopupWindow，其中：
        // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
        // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
        // window.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getCityId() {
        return HomeFragment.cityID;
    }

    @Override
    public void getSuccess(HomePartTimeSearchListInfo mSearchListInfo) {
        if (mSearchListInfo.industry_list != null && mSearchListInfo.industry_list.size() > 0) {
            mIndustryList.addAll(mSearchListInfo.industry_list);
        }
        if (mSearchListInfo.money_list != null && mSearchListInfo.money_list.size() > 0) {
            mSalaryList.addAll(mSearchListInfo.money_list);
        }
        if (mSearchListInfo.dist_list != null && mSearchListInfo.dist_list.size() > 0) {
            mDistList.addAll(mSearchListInfo.dist_list);
        }
        if (mSearchListInfo.order_list != null && mSearchListInfo.order_list.size() > 0) {
            mOrderList.addAll(mSearchListInfo.order_list);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
