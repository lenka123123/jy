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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.release.adapter.SiftUrNeedAreaAdapter;
import sinia.com.baihangeducation.release.adapter.SiftUrNeedIndustryAdapter;
import sinia.com.baihangeducation.release.adapter.SiftUrNeedOrderAdapter;
import sinia.com.baihangeducation.release.adapter.SiftUrNeedSalaryAdapter;
import sinia.com.baihangeducation.release.presenter.SiftUrNeedPresenter;
import sinia.com.baihangeducation.release.view.ISiftUrNeedView;
import sinia.com.baihangeducation.supplement.base.BaseActivity;
import sinia.com.baihangeducation.home.HomeFragment;

import com.mcxtzhang.swipemenulib.info.HomePartTimeSearchListInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeDistInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomePartTimeSalaryInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachIndustryInfo;
import com.mcxtzhang.swipemenulib.info.bean.HomeTraingSeachOrderInfo;

import sinia.com.baihangeducation.supplement.base.Goto;

public class SiftUrNeedActivity extends BaseActivity implements ISiftUrNeedView, CompoundButton.OnCheckedChangeListener {
    private Intent intent;
    private String flag;            //区分从兼职还是全职跳转过来的
    private String type;            //1全职 2兼职
    private SiftUrNeedPresenter presenter;

    private TextView mSelectIndustry;
    private TextView mArea;

    //    public List<HomePartTimeSalaryInfo> mSalaryList;                    //薪资
    public List<HomeTraingSeachIndustryInfo> mIndustryList;             //行业
    //    public List<HomeTraingSeachOrderInfo> mOrderList;                   //排序
    public List<HomePartTimeDistInfo> mDistList;                        //地区
    private String industryId = "0";
    private String salaryId;
    private String areaId = "0";
    private String orderId;

    private String money_id = "0";
    private String worktime_id = "0";
    private String distance_id = "0";
    private String sex_id = "0";
    private String pubtime_id = "0";

    private SiftUrNeedIndustryAdapter industryAdapter;
    private SiftUrNeedSalaryAdapter salaryAdapter;
    private SiftUrNeedAreaAdapter areaAdapter;
    private SiftUrNeedOrderAdapter orderAdapter;
    private RadioButton mRadioButtonNone;
    private RadioButton mRadioButtonThreeDay;
    private RadioButton mRadioButtonOneWeek;
    private RadioButton mRadioButtonOneWeekOut;
    private RadioButton mRadioButtonSexNeedNone;
    private RadioButton mRadioButtonSexNeedMan;
    private RadioButton mRadioButtonSexNeedWoman;
    private RadioButton mRadioButtonRangeNeedNone;
    private RadioButton mRadioButtonRangeNeedOne;
    private RadioButton mRadioButtonRangeNeedFive;
    private RadioButton mRadioButtonRangeNeedFiveOut;
    private RadioButton mRadioButtonRangeWorkTimeNone;
    private RadioButton mRadioButtonRangeWorkTimeWeekday;
    private RadioButton mRadioButtonRangeWorkTimeWeekends;
    private RadioButton mRadioButtonAccountNone;
    private RadioButton mRadioButtonAccountByDay;
    private RadioButton mRadioButtonAccountByMorrow;
    private RadioButton mRadioButtonAccountByWeek;
    private RadioButton mRadioButtonAccountByMonth;
    private LinearLayout layout;

    @Override
    public int initLayoutResID() {
        return R.layout.activity_part_job;
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
        mIndustryList = new ArrayList<>();
        mDistList = new ArrayList<>();

        mCommonTitle.setCenterText(R.string.siftUrNeed);
        mCommonTitle.setBackground(getResources().getDrawable(R.color.white));

        presenter = new SiftUrNeedPresenter(context, this);
        presenter.getPartTimeSeachList();

    }

    @Override
    protected void initView() {
        layout = findViewById(R.id.sifturneed);
        mSelectIndustry = findViewById(R.id.select_industry);
        mArea = findViewById(R.id.select_area);

        mRadioButtonNone = findViewById(R.id.sent_time_none);
        mRadioButtonThreeDay = findViewById(R.id.sent_time_three_day);
        mRadioButtonOneWeek = findViewById(R.id.sent_time_one_week);
        mRadioButtonOneWeekOut = findViewById(R.id.sent_time_one_week_out);

        mRadioButtonSexNeedNone = findViewById(R.id.sex_need_none);
        mRadioButtonSexNeedMan = findViewById(R.id.sex_need_man);
        mRadioButtonSexNeedWoman = findViewById(R.id.sex_need_woman);

        mRadioButtonRangeNeedNone = findViewById(R.id.range_need_none);
        mRadioButtonRangeNeedOne = findViewById(R.id.range_need_one);
        mRadioButtonRangeNeedFive = findViewById(R.id.range_need_five);
        mRadioButtonRangeNeedFiveOut = findViewById(R.id.range_need_five_out);

        mRadioButtonRangeWorkTimeNone = findViewById(R.id.work_time_none);
        mRadioButtonRangeWorkTimeWeekday = findViewById(R.id.work_time_weekday);
        mRadioButtonRangeWorkTimeWeekends = findViewById(R.id.work_time_weekends);

        mRadioButtonAccountNone = findViewById(R.id.account_none);
        mRadioButtonAccountByDay = findViewById(R.id.account_by_day);
        mRadioButtonAccountByMorrow = findViewById(R.id.account_by_morrow);
        mRadioButtonAccountByWeek = findViewById(R.id.account_by_week);
        mRadioButtonAccountByMonth = findViewById(R.id.account_by_month);


        mRadioButtonNone.setOnCheckedChangeListener(this);
        mRadioButtonThreeDay.setOnCheckedChangeListener(this);
        mRadioButtonOneWeek.setOnCheckedChangeListener(this);
        mRadioButtonOneWeekOut.setOnCheckedChangeListener(this);

        mRadioButtonSexNeedNone.setOnCheckedChangeListener(this);
        mRadioButtonSexNeedMan.setOnCheckedChangeListener(this);
        mRadioButtonSexNeedWoman.setOnCheckedChangeListener(this);

        mRadioButtonRangeNeedNone.setOnCheckedChangeListener(this);
        mRadioButtonRangeNeedOne.setOnCheckedChangeListener(this);
        mRadioButtonRangeNeedFive.setOnCheckedChangeListener(this);
        mRadioButtonRangeNeedFiveOut.setOnCheckedChangeListener(this);

        mRadioButtonRangeWorkTimeNone.setOnCheckedChangeListener(this);
        mRadioButtonRangeWorkTimeWeekday.setOnCheckedChangeListener(this);
        mRadioButtonRangeWorkTimeWeekends.setOnCheckedChangeListener(this);

        mRadioButtonAccountNone.setOnCheckedChangeListener(this);
        mRadioButtonAccountByDay.setOnCheckedChangeListener(this);
        mRadioButtonAccountByMorrow.setOnCheckedChangeListener(this);
        mRadioButtonAccountByWeek.setOnCheckedChangeListener(this);
        mRadioButtonAccountByMonth.setOnCheckedChangeListener(this);

        mSelectIndustry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addChoicePopWindow(1);
            }
        });
        mArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addChoicePopWindow(3);
            }
        });
/**
 *    private String money_id = "0";
 private String worktime_id = "0";
 private String distance_id = "0";
 private String sex_id = "0";
 private String pubtime_id = "0";
 */

        //完成
        findViewById(R.id.sifturneed_complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  Toast.makeText(SiftUrNeedActivity.this, "" + worktime_id, Toast.LENGTH_LONG).show();
                Log.i("", "onClick:worktime_idworktime_id " + worktime_id);
                if (type == "1") {  //industryId,行业   money_id,日结 areaId,地址  worktime_id周末,distance_id一公里,sex_id,pubtime_id 三天内

                    Goto.toHomeHunterActivity(context, industryId, money_id, areaId, worktime_id);
                } else {
                    Goto.toHomePartTimeActivity(context, industryId, money_id, areaId, worktime_id, distance_id, sex_id, pubtime_id);
//                switch (flag) {
//                    case "1":
//                        Goto.toHomeHunterActivity(context, industryId, salaryId, areaId, orderId);
//                        break;
//                    case "2":
//                        Goto.toHomePartTimeActivity(context, industryId, salaryId, areaId, orderId);
//                        break;
                }
            }
        });
        //跳过
        findViewById(R.id.sifturneed_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == "1")
                    Goto.toHomeHunterActivity(context);
                else
                    Goto.toHomePartTimeActivity(context);
            }
        });

    }

//    @Override
//    public void onClick(View view) {
//
//        switch (view.getId()) {
//            case R.id.select_industry:
//                Toast.makeText(this, "ddd", Toast.LENGTH_LONG).show();
//
//
//                break;
////            case R.id.sifturneed_price:
////                addChoicePopWindow(2);
////                break;
//            case R.id.select_area:
//
//                break;
////            case R.id.sifturneed_order:
////                addChoicePopWindow(4);
////                break;
//            case R.id.sifturneed_complete:
//                Log.i("职业筛选条件", industryId + "行业发送");
//                Log.i("职业筛选条件", salaryId + "薪资发送");
//                Log.i("职业筛选条件", areaId + "地区发送");
//                Log.i("职业筛选条件", orderId + "排序发送");
//                if (type == "1") {
//                    Goto.toHomeHunterActivity(context, industryId, salaryId, areaId, orderId);
//                } else{
//                    Goto.toHomePartTimeActivity(context, industryId, salaryId, areaId, orderId);
////                switch (flag) {
////                    case "1":
////                        Goto.toHomeHunterActivity(context, industryId, salaryId, areaId, orderId);
////                        break;
////                    case "2":
////                        Goto.toHomePartTimeActivity(context, industryId, salaryId, areaId, orderId);
////                        break;
//             }
//                break;
//            case R.id.sifturneed_next:
//
////                switch (flag) {
////                    case "1":
////                        Goto.toHomeHunterActivity(context);
////                        break;
////                    case "2":
////                        Goto.toHomePartTimeActivity(context);
////                        break;
////                }
//                break;
//        }
//    }

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
        Log.i("", "onClick:addChoicePopWindow q" + listTye);
        switch (listTye) {
            case 1:
                industryAdapter = new SiftUrNeedIndustryAdapter(context, mIndustryList);
                listView.setAdapter(industryAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mSelectIndustry.setText(mIndustryList.get(position).industry_name);
                        industryId = mIndustryList.get(position).industry_id + "";
                        window.dismiss();
                    }
                });
                break;
            case 2:
//                salaryAdapter = new SiftUrNeedSalaryAdapter(context, mSalaryList);
//                listView.setAdapter(salaryAdapter);
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        mPrice.setText(mSalaryList.get(position).money_name);
//                        salaryId = mSalaryList.get(position).money_id + "";
//                        window.dismiss();
//                    }
//                });
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
//                orderAdapter = new SiftUrNeedOrderAdapter(context, mOrderList);
//                listView.setAdapter(orderAdapter);
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        mOrder.setText(mOrderList.get(position).order_name);
//                        orderId = mOrderList.get(position).order_id + "";
//                        window.dismiss();
//                    }
//                });
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

        if (mSearchListInfo.dist_list != null && mSearchListInfo.dist_list.size() > 0) {
            mDistList.addAll(mSearchListInfo.dist_list);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (!b) return;
        switch (compoundButton.getId()) {
            case R.id.sent_time_none:
                pubtime_id = "0";
                break;
            case R.id.sent_time_three_day:
                pubtime_id = "1";
                break;
            case R.id.sent_time_one_week:
                pubtime_id = "2";
                break;
            case R.id.sent_time_one_week_out:

                pubtime_id = "3";
                break;  //

            case R.id.sex_need_none:
                sex_id = "0";
                break;
            case R.id.sex_need_man:
                sex_id = "1";
                break;
            case R.id.sex_need_woman:
                sex_id = "2";
                break;   //

            case R.id.range_need_none:
                distance_id = "0";
                break;
            case R.id.range_need_one:
                distance_id = "1";
                break;
            case R.id.range_need_five:
                distance_id = "2";
                break;
            case R.id.range_need_five_out:
                distance_id = "3";
                break;   //

            case R.id.work_time_none:
                worktime_id = "0";
                break;
            case R.id.work_time_weekday:
                worktime_id = "1";
                break;
            case R.id.work_time_weekends:
                worktime_id = "2";
                break;  //

            case R.id.account_none:
                money_id = "0";
                break;
            case R.id.account_by_day:
                money_id = "1";
                break;
            case R.id.account_by_morrow:
                money_id = "5";
                break;
            case R.id.account_by_week:
                money_id = "2";
                break;
            case R.id.account_by_month:
                money_id = "3";
                break;

        }
    }


}
