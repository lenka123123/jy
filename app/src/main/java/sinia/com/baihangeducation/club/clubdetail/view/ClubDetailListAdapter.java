package sinia.com.baihangeducation.club.clubdetail.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;
import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.club.model.ClubHomeInfo;
import sinia.com.baihangeducation.club.clubdetail.model.ClubDetailBean;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/24.
 */

public class ClubDetailListAdapter extends SuperBaseAdapter<ClubDetailBean.NoticeList.Notice> {

    private String power = "";
    private String clubid = "";
    private Context context;
    private List<ClubDetailBean.NoticeList.Notice> data = new ArrayList<>();

    public ClubDetailListAdapter(Context context, List<ClubDetailBean.NoticeList.Notice> data) {
        super(context, data);
        this.context = context;


    }


    @Override
    protected void convert(BaseViewHolder holder, final ClubDetailBean.NoticeList.Notice mInviteListInfo, int position) {


        holder.setText(R.id.name, mInviteListInfo.title);
        holder.setText(R.id.time, mInviteListInfo.add_time);

        holder.setOnClickListener(R.id.club_item_view, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Goto.toClubAnnounceDetailActivity(context, clubid, mInviteListInfo.id, power);
            }
        });
    }


    @Override
    protected int getItemViewLayoutId(int position, ClubDetailBean.NoticeList.Notice item) {
        return R.layout.activity_club_notice_item;
    }

    public void setData(String power, String clubid) {
        this.power = power;
        this.clubid = clubid;


    }
}
