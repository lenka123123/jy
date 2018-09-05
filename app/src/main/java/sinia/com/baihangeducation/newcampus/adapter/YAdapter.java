package sinia.com.baihangeducation.newcampus.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by y on 2016/5/30.
 * 参考:http://blog.csdn.net/xuehuayous/article/details/50498345
 */
public class YAdapter<T extends RecyclerView.Adapter> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean isStaggeredGrid; //判断是否是瀑布流

    //头布局脚布局类型
    private static final int HEADER_TYPE = -1 << 10;
    private static final int FOOTER_TYPE = -1 << 11;

    private List<FixedViewInfo> mHeaderViewList = new ArrayList<>();
    private List<FixedViewInfo> mFooterViewList = new ArrayList<>();

    //和Listview类似
    public class FixedViewInfo {
        public View view;
        public int viewType;
    }

    private T mAdapter;

    public YAdapter(T adapter) {
        super();
        mAdapter = adapter;
    }

    /**
     * 添加头布局
     *
     * @param view
     */
    public void addHeaderView(View view) {
        if (null == view) {
            throw new RuntimeException("the view to add must not be null!");
        }
        FixedViewInfo fixedViewInfo = new FixedViewInfo();
        fixedViewInfo.view = view;
        fixedViewInfo.viewType = mHeaderViewList.size() + HEADER_TYPE;
        mHeaderViewList.add(fixedViewInfo);
        notifyDataSetChanged();
    }

    /**
     * 添加脚布局
     *
     * @param view
     */
    public void addFooterView(View view) {
        if (null == view) {
            throw new RuntimeException("the view to add must not be null!");
        }
        FixedViewInfo fixedViewInfo = new FixedViewInfo();
        fixedViewInfo.view = view;
        fixedViewInfo.viewType = mHeaderViewList.size() + FOOTER_TYPE;
        mFooterViewList.add(fixedViewInfo);
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isHeader(viewType)) {
            int abs = Math.abs(viewType - HEADER_TYPE);
            View view = mHeaderViewList.get(abs).view;
            return onCreateHeaderFooterViewHolder(view);

        } else if (isFooter(viewType)) {
            int abs = Math.abs(viewType - FOOTER_TYPE);
            View view = mFooterViewList.get(abs).view;
            return onCreateHeaderFooterViewHolder(view);

        } else {
            return mAdapter.createViewHolder(parent, viewType);
        }
    }

    /**
     * 头/脚布局不用处理
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < mHeaderViewList.size()) {
        } else if (position >= mHeaderViewList.size() + mAdapter.getItemCount()) {
        } else {
            mAdapter.onBindViewHolder(holder, position-mHeaderViewList.size());
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderViewList.size() + mAdapter.getItemCount() + mFooterViewList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            return mHeaderViewList.get(position).viewType;
        } else if (isFooterPosition(position)) {
            return mFooterViewList.get(position - mHeaderViewList.size() - mAdapter.getItemCount()).viewType;
        } else {
            return mAdapter.getItemViewType(position - mHeaderViewList.size());
        }

    }

    private RecyclerView.ViewHolder onCreateHeaderFooterViewHolder(View view) {
        if (isStaggeredGrid) {
            StaggeredGridLayoutManager.LayoutParams params = new StaggeredGridLayoutManager.LayoutParams(
                    StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT, StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT);
            params.setFullSpan(true);
            view.setLayoutParams(params);
        }
        return new RecyclerView.ViewHolder(view) {
        };

    }

    /**
     * 判断是否是头布局
     *
     * @param position
     * @return
     */
    private boolean isHeaderPosition(int position) {
        return position < mHeaderViewList.size();
    }

    /**
     * 判断是否是脚布局
     *
     * @param position
     * @return
     */
    private boolean isFooterPosition(int position) {
        return position >= mHeaderViewList.size() + mAdapter.getItemCount();
    }

    /**
     * 根据传入的类型判断是否是头布局
     *
     * @param viewType
     * @return
     */
    private boolean isHeader(int viewType) {
        return viewType >= HEADER_TYPE && viewType <= HEADER_TYPE + mHeaderViewList.size();
    }

    /**
     * 根据传入的类型判断是否是脚布局
     *
     * @param viewType
     * @return
     */
    private boolean isFooter(int viewType) {
        return viewType >= FOOTER_TYPE && viewType <= FOOTER_TYPE + mFooterViewList.size();
    }

    /**
     * 设置头/脚布局占用一整行
     *
     * @param recyclerView
     */
    public void setConutSpan(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) {
            throw new RuntimeException("layoutManager is null");
        }
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    boolean itemViewType = isHeaderPosition(position) || isFooterPosition(position);
                    return itemViewType ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }

        if (layoutManager instanceof StaggeredGridLayoutManager) {
            this.isStaggeredGrid = true;
        }

    }

    /**
     * 设置是否隐藏头布局
     *
     * @param isShow
     */
    public void setHeaderViewVisibility(boolean isShow) {
        for (FixedViewInfo fixedViewInfo : mHeaderViewList) {
            fixedViewInfo.view.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        notifyDataSetChanged();
    }

    /**
     * 设置是否隐藏脚布局
     *
     * @param isShow
     */
    public void setFooterViewVisibility(boolean isShow) {
        for (FixedViewInfo fixedViewInfo : mFooterViewList) {
            fixedViewInfo.view.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        notifyDataSetChanged();
    }

    public T getAdapter(){
        return mAdapter;
    }

}
