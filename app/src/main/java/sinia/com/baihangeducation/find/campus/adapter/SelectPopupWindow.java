package sinia.com.baihangeducation.find.campus.adapter;

import android.app.ActionBar;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.List;

import sinia.com.baihangeducation.R;
import com.mcxtzhang.swipemenulib.info.bean.CityInfo;


/**
 * 选择PopupWindow
 * @author ansen
 * @create time 2015-10-09
 */
public class SelectPopupWindow extends PopupWindow{
    private SelectCategory selectCategory;

    private List<CityInfo> mCityDatas;

    private ListView lvParentCategory = null;
    private ParentCategoryAdapter parentCategoryAdapter = null;

    /**
     * @param activity
     * @param selectCategory  回调接口注入
     */
    public SelectPopupWindow(List<CityInfo> mCityDatas, Activity activity, SelectCategory selectCategory) {
        this.selectCategory=selectCategory;
        this.mCityDatas=mCityDatas;

        View contentView = LayoutInflater.from(activity).inflate(R.layout.layout_adress_choose_view, null);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取手机屏幕的大小

        this.setContentView(contentView);
        this.setWidth(dm.widthPixels/5);
//        this.setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
//        this.setHeight(dm.heightPixels*7/10);
        this.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);

		/* 设置背景显示 */
        this.setBackgroundDrawable(activity.getResources().getDrawable(R.color.word_cccccc));
		/* 设置触摸外面时消失 */
        this.setOutsideTouchable(true);
        this.setTouchable(true);
        this.setFocusable(true); /*设置点击menu以外其他地方以及返回键退出 */

        /**
         * 1.解决再次点击MENU键无反应问题
         */
        contentView.setFocusableInTouchMode(true);

        //父类别适配器
        lvParentCategory= contentView.findViewById(R.id.lv_parent_category);
        if (mCityDatas!=null) {
            parentCategoryAdapter = new ParentCategoryAdapter(activity, mCityDatas);
            lvParentCategory.setAdapter(parentCategoryAdapter);
        }
        lvParentCategory.setOnItemClickListener(parentItemClickListener);
    }
    /**
     * 父类别点击事件
     */
    private OnItemClickListener parentItemClickListener=new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            parentCategoryAdapter.setSelectedPosition(position);
            selectCategory.selectCategory(position);
            dismiss();
        }
    };
    /**
     * 选择成功回调
     * @author apple
     *
     */
    public interface SelectCategory{
        /**
         * 把选中的下标通过方法回调回来
         * @param parentSelectposition  父类别选中下标
         */
        public void selectCategory(int parentSelectposition);
    }

}
