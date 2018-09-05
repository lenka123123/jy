package com.mcxtzhang.swipemenulib.titlebar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/** cn.eshore.pms.gjj.titlebar.GScrollView
 * Created by wuhenzhizao on 16/1/14.
 */
public class GScrollView extends ScrollView {
    private OnScrollChangeListener listener;

    public GScrollView(Context context) {
        super(context);
    }

    public GScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null){
            listener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public void setOnScrollChangeListener(OnScrollChangeListener listener){
        this.listener = listener;
    }

    public interface OnScrollChangeListener{
        void onScrollChanged(int x, int y, int oldX, int oldY);
    }
}
