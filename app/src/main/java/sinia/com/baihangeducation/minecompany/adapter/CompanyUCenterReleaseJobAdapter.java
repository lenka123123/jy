package sinia.com.baihangeducation.minecompany.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.CompanyReleaseItemInfo;
import sinia.com.baihangeducation.minecompany.view.INotifyRefreshActivityView;

public class CompanyUCenterReleaseJobAdapter extends SuperBaseAdapter<CompanyReleaseItemInfo> implements View.OnClickListener {
    private Context mContext;
    private List<CompanyReleaseItemInfo> mData;
    private View contentViewAction;                         //编辑 下架 删除 view
    private PopupWindow windowAction;                       //编辑 下架 删除 popwindow
    private INotifyRefreshActivityView actionView;
    private CompanyReleaseItemInfo mItem;
    TextView edit;
    LinearLayout editLayout;
    TextView isdown;
    LinearLayout isdownLayout;
    TextView detel;
    LinearLayout detelLayout;

    public CompanyUCenterReleaseJobAdapter(Context context, List<CompanyReleaseItemInfo> data, INotifyRefreshActivityView view) {
        super(context, data);
        this.mContext = context;
        this.mData = data;
        this.actionView = view;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final CompanyReleaseItemInfo item, int position) {
        mItem=null;
        mItem = item;
        holder.setText(R.id.fragment_mine_company_myreleaseparttime_item_title, item.job_title);
        holder.setText(R.id.fragment_mine_company_myreleaseparttime_item_looknum, "浏览了 " + item.job_look_num + " 次");
        holder.setText(R.id.fragment_mine_company_myreleaseparttime_item_alppynum, "申请 " + item.job_apply_num + " 人");
        holder.setText(R.id.fragment_mine_company_myreleaseparttime_item_adress, item.job_city_name);
        holder.setText(R.id.fragment_mine_company_myreleaseparttime_item_date, item.job_add_date);
        holder.setText(R.id.fragment_mine_company_myreleaseparttime_item_statue, item.status_name);

        holder.setOnClickListener(R.id.fragment_mine_company_myreleaseparttime_item_action, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //职位状态 1通过 2待审核 3未通过 4已下架
                //下架只能在已通过状态下显示
                //上架只能在下架状态下显示
                switch (item.status) {
                    case 1:
                        //通过
                        addPopWindow(holder, 1);
                        break;
                    case 2:
                        //待审核
                        addPopWindow(holder, 2);
                        break;
                    case 3:
                        //未通过
                        addPopWindow(holder, 3);
                        break;
                    case 4:
                        //已下架
                        addPopWindow(holder, 4);
                        break;
                }
            }
        });

    }

    /**
     * 编辑 下架 删除
     *
     * @param holder
     * @param status
     */

    private void addPopWindow(BaseViewHolder holder, int status) {
        // 用于PopupWindow的View
        Log.i("空指针判断", mContext.toString());
        Log.i("空指针判断", R.layout.companyreleasejobactionpop + "");
//        contentViewAction = LayoutInflater.from(mContext).inflate(R.layout.companyreleasejobactionpop,null);
        contentViewAction = LayoutInflater.from(mContext).inflate(R.layout.companyreleasejobactionpop, null, false);
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        windowAction = new PopupWindow(contentViewAction, 400, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        edit = contentViewAction.findViewById(R.id.companyreleasejobactionpop_edit);
        editLayout = contentViewAction.findViewById(R.id.companyreleasejobactionpop_edit_layout);
        isdown = contentViewAction.findViewById(R.id.companyreleasejobactionpop_isdown);
        isdownLayout = contentViewAction.findViewById(R.id.companyreleasejobactionpop_isdown_layout);
        detel = contentViewAction.findViewById(R.id.companyreleasejobactionpop_detel);
        detelLayout = contentViewAction.findViewById(R.id.companyreleasejobactionpop_detel_layout);
        editLayout.setOnClickListener(this);
        isdownLayout.setOnClickListener(this);
        detelLayout.setOnClickListener(this);
        //根据状态判断是否显示上下架item
        switch (status) {
            case 1:
                //通过
                isdownLayout.setVisibility(View.VISIBLE);
                isdown.setText("下架");
                break;
            case 2:
                //待审核
                isdownLayout.setVisibility(View.GONE);
                break;
            case 3:
                //未通过
                isdownLayout.setVisibility(View.GONE);
                break;
            case 4:
                //已下架
                isdownLayout.setVisibility(View.VISIBLE);
                isdown.setText("上架");
                break;
        }

        windowAction.setBackgroundDrawable(new ColorDrawable());
        // 设置点击窗口外边窗口消失
        windowAction.setOutsideTouchable(true);
        windowAction.setAnimationStyle(R.style.pop_animright);
        // 设置此参数获得焦点，否则无法点击
        windowAction.setFocusable(true);
//        windowAction.showAtLocation(holder.getView(R.id.newcampayfunitem_more),Gravity.LEFT,0,0);
        windowAction.showAsDropDown(holder.getView(R.id.fragment_mine_company_myreleaseparttime_item_action));
//        int[] location = new int[2];
//        holder.getView(R.id.fragment_mine_company_myreleaseparttime_item_action).getLocationOnScreen(location);
//        windowAction.showAtLocation(holder.getView(R.id.fragment_mine_company_myreleaseparttime_item_action), Gravity.NO_GRAVITY
//                , location[0] - holder.getView(R.id.fragment_mine_company_myreleaseparttime_item_action).getWidth(), location[1]);
    }

    @Override
    protected int getItemViewLayoutId(int position, CompanyReleaseItemInfo item) {
        return R.layout.fragment_mine_company_myreleaseparttime_item;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.companyreleasejobactionpop_edit_layout:
                //跳转编辑页面
                actionView.go2EditActivity(mItem.job_id);
                if (windowAction!=null)
                windowAction.dismiss();
                break;
            case R.id.companyreleasejobactionpop_isdown_layout:
                //上架下架
                if ((isdown.getText().toString()) == "上架" || (isdown.getText().toString().equals("上架"))) {
                    //上架
                    actionView.upJob(mItem.job_id);

                } else {
                    //下架
                    actionView.downJob(mItem.job_id);
                }
                if (windowAction!=null)
                    windowAction.dismiss();
                break;
            case R.id.companyreleasejobactionpop_detel_layout:
                //删除
                actionView.detelJob(mItem.job_id);
                if (windowAction!=null)
                    windowAction.dismiss();
                break;
        }
    }
}
