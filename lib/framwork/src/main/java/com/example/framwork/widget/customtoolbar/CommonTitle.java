package com.example.framwork.widget.customtoolbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framwork.R;


public class CommonTitle extends AutoTitle {
    private LayoutInflater inflater;
    private Context mContext;
    private ImageView addRightOne;
    private ImageView addRightTwo;
    private ViewGroup view;

    public CommonTitle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public CommonTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CommonTitle(Context context) {
        super(context);
        init(context, null);
    }

    public void setCenterText(int resid) {
        center_txt.setText(resid);
    }
    public void setCenterTextColor(int color) {
        center_txt.setTextColor(color);
    }

    public void setCenterText(String res) {
        center_txt.setText(res);
    }

    public TextView getCenter_txt() {
        return center_txt;
    }

    public ImageView getRightRes() {
        return right_res;
    }

    public ImageView getLeftRes() {
        return left_res;
    }

    public TextView getRightTxt() {
        right_txt.setVisibility(VISIBLE);
        return right_txt;
    }

    public void setRightText(String resid) {
        right_txt.setVisibility(VISIBLE);
        right_txt.setText(resid);
    }

    public TextView getLeftText() {
        return left_txt;
    }

    public ImageView getRightImgOne() {
        addRightOne.setVisibility(VISIBLE);
        return addRightOne;
    }

    public ImageView getRightImgTwo() {
        addRightTwo.setVisibility(VISIBLE);
        return addRightTwo;
    }


    public ImageView getConter_img() {
        return conter_img;
    }

    public void setConterImg(int conter_img) {
        this.conter_img.setImageResource(conter_img);
    }

    private ImageView right_res;
    private ImageView left_res;
    private ImageView right_res_top;
    private ImageView conter_img;
    private TextView left_txt;
    private TextView center_txt;
    private TextView right_txt;
    private FrameLayout fl_conter;
    private int height;
    public void setBg(){
        view.setBackgroundColor(Color.TRANSPARENT);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        setBackgroundResource(R.drawable.shape_title_bg);
        view = (ViewGroup) inflater.inflate(R.layout.frame_title, this, false);
        height = view.getLayoutParams().height;
        right_res = (ImageView) view.findViewById(R.id.right_res);
        left_res = (ImageView) view.findViewById(R.id.left_res);
        right_res_top = (ImageView) view.findViewById(R.id.right_res_top);
        left_txt = (TextView) view.findViewById(R.id.left_txt);
        center_txt = (TextView) view.findViewById(R.id.center_txt);
        right_txt = (TextView) view.findViewById(R.id.right_txt);
        conter_img = (ImageView) view.findViewById(R.id.center_img);
        fl_conter = (FrameLayout) view.findViewById(R.id.fl_conter);

        addRightOne = (ImageView) view.findViewById(R.id.add_right_one);
        addRightTwo = (ImageView) view.findViewById(R.id.add_right_two);


        view.removeView(left_res);
        view.removeView(left_txt);
        view.removeView(right_txt);
        view.removeView(right_res);
        view.removeView(right_res_top);
        view.removeView(fl_conter);
        view.removeView(addRightOne);
        view.removeView(addRightTwo);


        getLeftLayout().addView(left_res);
        getLeftLayout().addView(left_txt);
        getCenterLayout().addView(fl_conter);
        getRightLayout().addView(right_txt);
        getRightLayout().addView(right_res);
        getRightLayout().addView(right_res_top);
        getRightLayout().addView(addRightOne);
        getRightLayout().addView(addRightTwo);

        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonTitle);
            int indexCount = typedArray.getIndexCount();
            for (int k = 0; k < indexCount; k++) {
                int attr = typedArray.getIndex(k);
                if (attr == R.styleable.CommonTitle_center_txt) {
                    String titleCenter = typedArray.getString(attr);
                    if (!TextUtils.isEmpty(titleCenter)) {
                        center_txt.setText(titleCenter);
                    }

                } else if (attr == R.styleable.CommonTitle_left_res) {
                    left_res.setVisibility(VISIBLE);
                    Drawable drawLift = typedArray.getDrawable(attr);
                    if (null != drawLift) {
                        left_res.setImageDrawable(drawLift);
                    }

                } else if (attr == R.styleable.CommonTitle_conter_res) {
                    conter_img.setVisibility(VISIBLE);
                    Drawable drawLift = typedArray.getDrawable(attr);
                    if (null != drawLift) {
                        conter_img.setImageDrawable(drawLift);
                    }

                } else if (attr == R.styleable.CommonTitle_right_res) {
                    right_res.setVisibility(VISIBLE);
                    Drawable drawRight = typedArray.getDrawable(attr);
                    if (null != drawRight) {
                        right_res.setImageDrawable(drawRight);
                    }

                } else if (attr == R.styleable.CommonTitle_center_txt_color) {//
                    ColorStateList colorStateList = typedArray.getColorStateList(attr);
                    if (colorStateList != null) {
                        center_txt.setTextColor(colorStateList);
                    }

                } else if (attr == R.styleable.CommonTitle_right_txt) {
                    right_txt.setVisibility(VISIBLE);
                    String right_txt = typedArray.getString(attr);
                    if (!TextUtils.isEmpty(right_txt)) {
                        this.right_txt.setText(right_txt);
                    }

                } else if (attr == R.styleable.CommonTitle_right_txt_color) {
                    ColorStateList colorStateList = typedArray.getColorStateList(attr);
                    if (colorStateList != null) {
                        this.right_txt.setTextColor(colorStateList);
                    }

                } else if (attr == R.styleable.CommonTitle_left_txt) {
                    left_txt.setVisibility(VISIBLE);
                    String left_txt = typedArray.getString(attr);
                    if (!TextUtils.isEmpty(left_txt)) {
                        this.left_txt.setText(left_txt);
                    }

                    int right_txt_bg = typedArray.getResourceId(attr, -1);
                    if (right_txt_bg != -1) {
                        this.right_txt.setBackgroundResource(right_txt_bg);
                    }

                } else if (attr == R.styleable.CommonTitle_right_txt_bg) {
                    int right_txt_bg = typedArray.getResourceId(attr, -1);
                    if (right_txt_bg != -1) {
                        this.right_txt.setBackgroundResource(right_txt_bg);
                    }

                }
            }
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getLayoutParams() != null && getLayoutParams().height == LinearLayout.LayoutParams.WRAP_CONTENT) {
            getLayoutParams().height = height;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
