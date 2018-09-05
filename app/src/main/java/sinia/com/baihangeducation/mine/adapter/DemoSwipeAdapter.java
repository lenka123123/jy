package sinia.com.baihangeducation.mine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.example.framwork.adapter.BaseViewHolder;
import com.example.framwork.utils.SpCommonUtils;
import com.mcxtzhang.swipemenulib.info.bean.FragmentMessageInfo;
import com.mcxtzhang.swipemenulib.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import sinia.com.baihangeducation.AppConfig;
import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.mine.activity.FragmentMessageActivity;
import sinia.com.baihangeducation.supplement.base.Goto;

/**
 * Created by yukuo on 2016/4/30.
 * 这是一个测试带有侧滑删除的列表适配器
 */
public class DemoSwipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FragmentMessageInfo> list = new ArrayList<>();
    FragmentMessageActivity fragmentMessageActivity;

    public DemoSwipeAdapter(List<FragmentMessageInfo> list, FragmentMessageActivity fragmentMessageActivity) {
        this.list = list;
        this.fragmentMessageActivity = fragmentMessageActivity;
    }

    public void setData(List<FragmentMessageInfo> data) {

        list.addAll(data);
        notifyDataSetChanged();
    }

    public void setDataRead( ) {

        if (  list.size() >= 1) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).is_read = 1;
            }
        }
        notifyDataSetChanged();
    }

    public void addReFreshData() {

        notifyDataSetChanged();
    }

    public void addRLoadMOreData() {
        fragmentMessageActivity.getServerData();
    }

    /**
     * 删除一个数据的方法
     *
     * @param position 索引
     */
    public void removeData(int position) {
        // TODO 使用刷新单一个条目会出现问题,所以请不要使用
        fragmentMessageActivity.removeItem(list.get(position),list.size());
        list.remove(position);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = View.inflate(parent.getContext(), R.layout.item_swipe_menu, null);

        return new MySwipeMenuHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyHolder) {
            MyHolder myHolder = (MyHolder) holder;
//            myHolder.item_content.setText(list.get(position));
        } else if (holder instanceof MySwipeMenuHolder) {
            final MySwipeMenuHolder myHolders = (MySwipeMenuHolder) holder;
            myHolders.item_name.setText(list.get(position).title);
            myHolders.item_content.setText(list.get(position).content);

            if (list.get(position).date != null && list.get(position).date.length() > 12) {
                int dataLength = list.get(position).date.length();
                if (TimeUtils.isToday(list.get(position).date)) {
                    myHolders.item_time.setText(list.get(position).date.substring(dataLength - 8, dataLength - 3));

                } else {
                    myHolders.item_time.setText(list.get(position).date.substring(0, 11));
                }
            }

            System.out.println(list.get(position).is_read + "已读消息==========");


            //是否已阅读1是2否
            if (list.get(position).is_read == 1) {
                myHolders.item_img.setVisibility(View.GONE);
            } else {
                myHolders.item_img.setVisibility(View.VISIBLE);
            }

            myHolders.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Goto.toFragmentMessageDetailActivity(fragmentMessageActivity, list.get(position));
                    changePoint(myHolders.item_img, list.get(position));

                }
            });
            myHolders.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeData(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private final TextView item_name;
        private final TextView item_content;
        private final TextView item_time;
        private final ImageView item_img;

        public MyHolder(View itemView) {
            super(itemView);
            item_name = (TextView) itemView.findViewById(R.id.fragment_message_item_name);
            item_content = (TextView) itemView.findViewById(R.id.fragment_message_item_content);
            item_time = (TextView) itemView.findViewById(R.id.fragment_message_item_date);
            item_img = (ImageView) itemView.findViewById(R.id.point);
        }
    }

    class MySwipeMenuHolder extends RecyclerView.ViewHolder {
        private final TextView delete;
        private final TextView ok;

        private final SwipeLayout srl_item;

        private final TextView item_name;
        private final TextView item_content;
        private final TextView item_time;
        private final ImageView item_img;
        private final RelativeLayout relativeLayout;

        public MySwipeMenuHolder(View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.fragment_message_item);
            item_name = (TextView) itemView.findViewById(R.id.fragment_message_item_name);
            item_content = (TextView) itemView.findViewById(R.id.fragment_message_item_content);
            item_time = (TextView) itemView.findViewById(R.id.fragment_message_item_date);
            item_img = (ImageView) itemView.findViewById(R.id.point);


            srl_item = (SwipeLayout) itemView.findViewById(R.id.srl_item);
            delete = (TextView) itemView.findViewById(R.id.delete);
            ok = (TextView) itemView.findViewById(R.id.ok);
        }
    }

    private void changePoint(ImageView holder, FragmentMessageInfo item) {
        //是否已阅读1是2否
        if (item.is_read == 1) {
            return;
        }
        //是否已阅读1是2否
        String no_read_num_es = (String) SpCommonUtils.get(fragmentMessageActivity, AppConfig.FINAL_NO_READ_NUM, "");

        if (Integer.valueOf(no_read_num_es) >= 1) {
            no_read_num_es = (Integer.valueOf(no_read_num_es) - 1) + "";
        } else {
            no_read_num_es = "0";
        }


        holder.setVisibility(View.GONE);
        SpCommonUtils.put(fragmentMessageActivity, AppConfig.FINAL_NO_READ_NUM, no_read_num_es);
    }
}
