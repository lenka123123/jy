package sinia.com.baihangeducation.home.adapter;

import android.content.Context;
import android.view.View;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.CompanyJobListInfo;
import sinia.com.baihangeducation.supplement.base.Goto;

public class CompanyDetailAdapter extends SuperBaseAdapter<CompanyJobListInfo> {
    private Context context;
    public CompanyDetailAdapter(Context context, List<CompanyJobListInfo> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, final CompanyJobListInfo item, int position) {

        holder.setRoundImageUrl(R.id.companydetail_item_img, item.job_company_logo, R.drawable.new_eorrlogo);
        holder.setText(R.id.companydetail_item_title, item.job_title);
        holder.setText(R.id.companydetail_item_name, item.job_company_name);
        holder.setText(R.id.companydetail_item_adressanddate, item.job_city_name + " " + item.job_add_date);
        holder.setText(R.id.companydetail_item_money, item.job_money);
        holder.setOnClickListener(R.id.companydetail_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (item.job_type) {
                    case 1:
                        Goto.toAllJobDetailActivity(context,item.job_id);
                        break;
                    case 2:
                        Goto.toPartTimeJobDetailActivity(context,item.job_id);
                        break;
                }
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, CompanyJobListInfo item) {
        return R.layout.companydetail_item;
    }
}
