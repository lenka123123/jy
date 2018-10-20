package sinia.com.baihangeducation.club.clubdetail.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;

import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.adapter.SuperBaseAdapter;
import com.mcxtzhang.swipemenulib.customview.GlideLoadUtils;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.club.model.ClubHomeInfo;
import sinia.com.baihangeducation.club.clubdetail.ClubDetailActivity;
import sinia.com.baihangeducation.club.clubdetail.model.ClubDetailBean;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by Administrator on 2018/4/24.
 */

public class ClubDetailListAdapter extends SuperBaseAdapter<ClubDetailBean.NoticeList.Notice> {

    private String power = "";
    private String clubid = "";
    private Context context;

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
                if (isNeetLogin()) return;
                Goto.toClubAnnounceDetailActivity(context, clubid, mInviteListInfo.id, power);
            }
        });
    }


    public boolean isNeetLogin() {
        if (!AppConfig.ISlOGINED) {
            new AlertDialog.Builder(context).setTitle("提示！").setMessage("您尚未登录，请先登录。").setPositiveButton("登录", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Goto.toLogin(context);
                }
            }).setNegativeButton("取消", null).show();
            return true;
        }
        return false;
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
