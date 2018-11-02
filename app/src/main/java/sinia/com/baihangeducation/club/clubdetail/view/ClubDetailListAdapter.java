package sinia.com.baihangeducation.club.clubdetail.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    private List<String> power;
    private String clubid = "";
    private Context context;

    public ClubDetailListAdapter(Context context, List<ClubDetailBean.NoticeList.Notice> data) {
        super(context, data);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder holder, final ClubDetailBean.NoticeList.Notice mInviteListInfo, int position) {
        holder.getView(R.id.name).setTag(position);

        TextView name = holder.getView(R.id.name);
        //公告类型ID    ( 1：社团公告 2：学校公告 3：系统公告 )


        if (mInviteListInfo.type.equals("1")) {
            SpannableString spannableString = new SpannableString("[社团] " + mInviteListInfo.title);
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.font2_blue)),
                    0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            name.setText(spannableString);
        }
        if (mInviteListInfo.type.equals("2")) {
            SpannableString spannableString = new SpannableString("[学校] " + mInviteListInfo.title);
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.font2_)),
                    0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            name.setText(spannableString);
        }
        if (mInviteListInfo.type.equals("3")) {
            SpannableString spannableString = new SpannableString("[系统] " + mInviteListInfo.title);
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red_fa3e3e)),
                    0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            name.setText(spannableString);
        }

        holder.setText(R.id.time, mInviteListInfo.add_time);

        holder.setOnClickListener(R.id.club_item_view, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNeetLogin()) return;

//                if (mPermissionList.get(i).equals("dropNotice")) {
//                }
//                if (mPermissionList.get(i).equals("editNotice")) {


                Goto.toClubAnnounceDetailActivity(context, mInviteListInfo.type, clubid, mInviteListInfo.id, power.contains("dropNotice"),
                        power.contains("editNotice"));
            }
        });
    }

    private SpannableString setClolor(String msg, String clolor) {
        SpannableString spannableString = new SpannableString(msg);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(clolor)), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
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

    public void setData(List<String> power, String clubid) {
        this.power = power;
        this.clubid = clubid;


    }
}
