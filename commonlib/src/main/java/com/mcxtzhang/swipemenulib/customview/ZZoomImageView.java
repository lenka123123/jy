package com.mcxtzhang.swipemenulib.customview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

//  com.mcxtzhang.swipemenulib.customview.ZZoomImageView
public class ZZoomImageView extends ImageView implements View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "bqt";
    /**一些边界缩放倍数，SCALE_FULL的效果为：第一次双击后将图片宽或高放大到view的宽或高的比例*/
    private float SCALE_FULL, SCALE_DOUBLE, SCALE_MAX;
    /** 初始化时【屏幕/图片】的大小，也是最后一次双击时使用的缩放比例。如果图片宽高大于屏幕宽高，此值将小于1 */
    private float SCALE_INIT;
    /**是否限制双指缩放时的缩放倍数*/
    private boolean isLimitedScale = true;
    private Matrix mScaleMatrix = new Matrix();
    private final float[] matrixValues = new float[9];//用于存放矩阵的9个值
    /** 缩放的手势检测 */
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    private boolean isAutoScale;
    private int mTouchSlop;
    private float mLastX, mLastY;
    private boolean isCanDrag;
    private int lastPointerCount;
    private boolean isCheckTopAndBottom = true, isCheckLeftAndRight = true;
    public ZZoomImageView(Context context) {
        this(context, null);
    }
    public ZZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.MATRIX);//用矩阵来绘制
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {//双击时
                if (isAutoScale == true) return true;
                float x = e.getX();
                float y = e.getY();
                Log.i(TAG, "双击onDoubleTap，" + "缩放比例：" + getScale() + " , " + SCALE_INIT);
                if (getScale() < SCALE_FULL) post(new AutoScaleRunnable(SCALE_FULL, x, y));
                else if (getScale() >= SCALE_FULL && getScale() < SCALE_DOUBLE) post(new AutoScaleRunnable(SCALE_DOUBLE, x, y));
                else post(new AutoScaleRunnable(SCALE_INIT, x, y));
                isAutoScale = true;
                return true;
            }
        });
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {//双指缩放时
                if (getDrawable() == null) return true;
                float scale = getScale();
                float scaleFactor = detector.getScaleFactor();
                //缩放的范围控制
                if (!isLimitedScale) mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
                else if ((scale < SCALE_MAX && scaleFactor > 1.0f) || (scale > SCALE_INIT && scaleFactor < 1.0f)) {
                    //最大值最小值判断
                    if (scaleFactor * scale < SCALE_INIT) scaleFactor = SCALE_INIT / scale;
                    if (scaleFactor * scale > SCALE_MAX) scaleFactor = SCALE_MAX / scale;
                    //设置缩放比例
                    mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
                    checkBorderAndCenterWhenScale();
                    setImageMatrix(mScaleMatrix);
                }
                return true;
            }
        });
        this.setOnTouchListener(this);
    }
    @Override
    protected void onAttachedToWindow() {//在onDraw前调用的。也就是我们写的View在没有绘制出来时调用的
        super.onAttachedToWindow();
        Log.i(TAG, "onAttachedToWindow");
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }


    @Override
    public void onGlobalLayout() {//根据图片的宽和高以及屏幕的宽和高，对图片进行缩放以及移动至屏幕的中心
        if (getDrawable() == null) return;
        // 图片的宽高
        int dw = getDrawable().getIntrinsicWidth();
        int dh = getDrawable().getIntrinsicHeight();
        Log.i(TAG, "view大小：" + getWidth() + " * " + getHeight() + " ，图片大小： " + dw + " * " + dh);
        //初始化缩放比例
        if (dw >= getWidth() && dh >= getHeight()) {// 如果图片的宽【和】高都大于view，则让其按按比例适应屏幕大小
            SCALE_INIT = Math.min(getWidth() * 1.0f / dw, getHeight() * 1.0f / dh);
            SCALE_FULL = Math.max(getWidth() * 1.0f / dw, getHeight() * 1.0f / dh);
        } else if (dw >= getWidth()) { // 如果图片的宽【或】高大于view，则缩放至屏幕的宽或者高
            SCALE_INIT = getWidth() * 1.0f / dw;
            SCALE_FULL = getHeight() * 1.0f / dh;
        } else if (dh >= getHeight()) {
            SCALE_INIT = getHeight() * 1.0f / dh;
            SCALE_FULL = getWidth() * 1.0f / dw;
        } else {//其他情况，也即小图片时，默认不进行缩放
            SCALE_INIT = 1.0f;
            SCALE_FULL = Math.min(getWidth() * 1.0f / dw, getHeight() * 1.0f / dh);
        }
        SCALE_DOUBLE = 1.5f * SCALE_FULL;//第二次双击时的缩放比例
        SCALE_MAX = 3.5f * SCALE_FULL;//最大缩放比例
        Log.i(TAG, "缩放比例SCALE_INIT = " + SCALE_INIT + "--双击缩放比例：" + SCALE_FULL);
        // 将图片移动至屏幕中心，以SCALE_INIT为比例进行缩放
        mScaleMatrix.postTranslate((getWidth() - dw) / 2, (getHeight() - dh) / 2);
        mScaleMatrix.postScale(SCALE_INIT, SCALE_INIT, getWidth() / 2, getHeight() / 2);
        setImageMatrix(mScaleMatrix);
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) return true;//双击时，不再响应其他操作
        mScaleGestureDetector.onTouchEvent(event);
        float x = 0, y = 0;
        // 拿到触摸点的个数
        final int pointerCount = event.getPointerCount();
        // 得到多个触摸点的x与y均值
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x = x / pointerCount;
        y = y / pointerCount;
        /** 每当触摸点发生变化时，重置mLasX , mLastY */
        if (pointerCount != lastPointerCount) {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }
        lastPointerCount = pointerCount;
        RectF rectF = getMatrixRectF();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (rectF.width() > getWidth() || rectF.height() > getHeight()) getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (rectF.width() > getWidth() || rectF.height() > getHeight()) getParent().requestDisallowInterceptTouchEvent(true);
                Log.i(TAG, "ACTION_MOVE");
                float dx = x - mLastX;
                float dy = y - mLastY;
                if (!isCanDrag) isCanDrag = isCanDrag(dx, dy);
                if (isCanDrag) {
                    if (getDrawable() != null) {
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        // 如果宽度小于屏幕宽度，则禁止左右移动
                        if (rectF.width() < getWidth()) {
                            dx = 0;
                            isCheckLeftAndRight = false;
                        }
                        // 如果高度小雨屏幕高度，则禁止上下移动
                        if (rectF.height() < getHeight()) {
                            dy = 0;
                            isCheckTopAndBottom = false;
                        }
                        /**移动时的缩放比例*/
                        float translateScale = getScale();
                        if (translateScale > 2.0f) translateScale = 2.0f;
                        if (translateScale < 0.5f) translateScale = 0.5f;
                        mScaleMatrix.postTranslate(dx * translateScale, dy * translateScale);//非常有用的设置，当放大很多倍时可以快速的滑动
                        checkMatrixBounds();
                        setImageMatrix(mScaleMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "ACTION_UP");
                lastPointerCount = 0;
                break;
        }
        return true;
    }
    //**************************************************************************************************************************
    /** 在缩放时，进行图片显示范围的控制，防止图片宽高大于view时，图片与控件间出现白边；防止图片小于view时不居中 */
    private void checkBorderAndCenterWhenScale() {
        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;
        int width = getWidth();
        int height = getHeight();
        // 如果宽或高大于屏幕，则控制范围
        if (rect.width() >= width) {
            if (rect.left > 0) deltaX = -rect.left;
            if (rect.right < width) deltaX = width - rect.right;
        }
        if (rect.height() >= height) {
            if (rect.top > 0) deltaY = -rect.top;
            if (rect.bottom < height) deltaY = height - rect.bottom;
        }
        // 如果宽或高小于屏幕，则让其居中
        if (rect.width() < width) deltaX = width * 0.5f - rect.right + 0.5f * rect.width();
        if (rect.height() < height) deltaY = height * 0.5f - rect.bottom + 0.5f * rect.height();
        Log.i(TAG, "deltaX = " + deltaX + " , deltaY = " + deltaY);
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }
    /** 根据当前图片的Matrix获得图片的范围 */
    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }
    /** 获得当前的缩放比例 */
    public final float getScale() {
        mScaleMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }
    /** 移动时，进行边界判断，主要判断宽或高大于屏幕的 */
    private void checkMatrixBounds() {
        RectF rect = getMatrixRectF();
        float deltaX = 0, deltaY = 0;
        final float viewWidth = getWidth();
        final float viewHeight = getHeight();
        // 判断移动或缩放后，图片显示是否超出屏幕边界
        if (rect.top > 0 && isCheckTopAndBottom) deltaY = -rect.top;
        if (rect.bottom < viewHeight && isCheckTopAndBottom) deltaY = viewHeight - rect.bottom;
        if (rect.left > 0 && isCheckLeftAndRight) deltaX = -rect.left;
        if (rect.right < viewWidth && isCheckLeftAndRight) deltaX = viewWidth - rect.right;
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }
    /** 是否是拖动行为 */
    private boolean isCanDrag(float dx, float dy) {
        return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
    }
    //**************************************************************************************************************************
    /** 自动缩放的任务*/
    private class AutoScaleRunnable implements Runnable {
        private float mTargetScale;
        private int TIME = 100;//分几次缩放到指定的的比例
        private float tmpScale;//
        private float x, y;//缩放的中心
        /**传入目标缩放值，根据目标值与当前值，判断应该放大还是缩小 */
        public AutoScaleRunnable(float targetScale, float x, float y) {
            this.mTargetScale = targetScale;
            this.x = x;
            this.y = y;
            if (getScale() < mTargetScale) tmpScale = 1.0f + (mTargetScale - getScale() / TIME);
            else tmpScale = 1.0f - (getScale() - mTargetScale / TIME);
        }
        @Override
        public void run() {
            // 进行缩放
            mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
            // 如果值在合法范围内，继续缩放
            if ((tmpScale > 1f && getScale() < mTargetScale) || (tmpScale < 1f && getScale() > mTargetScale)) {
                post(this);
            } else { // 设置为目标的缩放比例
                float deltaScale = mTargetScale / getScale();
                mScaleMatrix.postScale(deltaScale, deltaScale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
                isAutoScale = false;
            }
        }
    }
}

