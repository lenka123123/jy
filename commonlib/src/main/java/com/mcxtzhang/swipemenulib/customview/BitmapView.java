package com.mcxtzhang.swipemenulib.customview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.View;

import com.mcxtzhang.swipemenulib.R;

public class BitmapView extends View {

    private int img;

    public BitmapView(Context context, int img) {
        super(context);
        this.img = img;
    }

    //重写onDraw方法
    public void onDraw(Canvas canvas) {
        // 获取资源文件的引用res
        Resources res = getResources();
        // 获取图形资源文件
        Bitmap bmp = BitmapFactory.decodeResource(res, img);
        // 设置canvas画布背景为白色
        canvas.drawColor(Color.BLACK);
        // 在画布上绘制缩放之前的位图，以做对比
        //屏幕上的位置坐标是0,0
        canvas.drawBitmap(bmp, 0, 0, null);
        // 定义矩阵对象
        Matrix matrix = new Matrix();
        // 缩放原图
        matrix.postScale(1f, 1f);
        // 向左旋转45度，参数为正则向右旋转
        matrix.postRotate(-45);
        //bmp.getWidth(), 500分别表示重绘后的位图宽高
        Bitmap dstbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), 500,
                matrix, true);
        // 在画布上绘制旋转后的位图
        //放在坐标为0,200的位置
        canvas.drawBitmap(dstbmp, 0, 200, null);
    }
}
