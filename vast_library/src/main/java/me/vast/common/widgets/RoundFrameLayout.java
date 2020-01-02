package me.vast.common.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;


import java.util.Arrays;

import me.vast.common.R;

/**
 * Created by zhengz on 2017/9/1.
 * 自定义带有弧度背景
 * store颜色粗细
 * round弧度大小
 */

public class RoundFrameLayout extends FrameLayout {

//    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Paint mPaintColor = new Paint(Paint.ANTI_ALIAS_FLAG);

    private RectF mRectF = new RectF();

//    private Shape mShape;

    private int mRadius;

    private float mBorderStroke;

    private int mBorderColor;


    //
    private Path mRoundPath;
//    private RectF mRoundRectF;

    public RoundFrameLayout(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public RoundFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setWillNotDraw(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(LAYER_TYPE_HARDWARE, null);
        }
//        mPaint.setFilterBitmap(true);
//        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundLayoutXfermode);


        int defaultRadius = context.getResources().getDimensionPixelSize(R.dimen.image_round_r5);
//        int defaultRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BODER_RADIUS_DEFAULT, getResources().getDisplayMetrics());
        mRadius = a.getDimensionPixelSize(R.styleable.RoundLayoutXfermode_borderRadius, defaultRadius);


        // 边框颜色
        mBorderColor = a.getColor(R.styleable.RoundLayoutXfermode_borderColor, 0);
        // 边框Stroke
        mBorderStroke = a.getDimension(R.styleable.RoundLayoutXfermode_borderStroke, 0);

        // stroke
        mPaintColor.setStyle(Paint.Style.STROKE);
        if (0 != mBorderColor) {
            mPaintColor.setColor(mBorderColor);
        }
        if (0 != mBorderStroke) {
            mPaintColor.setStrokeWidth(mBorderStroke);
        }


        mRoundPath = new Path();

        a.recycle();
    }

//    // 添加一个圆角矩形到path中, 如果要实现任意形状的View, 只需要手动添加path就行
//    private void setRoundPath() {
//        mRoundPath.addRoundRect(mRoundRectF, mRadius, mRadius, Path.Direction.CW);
//    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        if (changed) {
//            if (mShape == null) {
//                float[] radius = new float[8];
//                Arrays.fill(radius, mRadius);
//                mShape = new RoundRectShape(radius, null, null);
//            }
//            mShape.resize(getWidth(), getHeight());
//        }

        if (changed) {
            mRectF.set(0f, 0f, getMeasuredWidth(), getMeasuredHeight());
            mRoundPath.addRoundRect(mRectF, mRadius, mRadius, Path.Direction.CW);
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mRadius > 0f) {
            canvas.save();
            canvas.clipPath(mRoundPath);  // 效果会有锯齿
        }
        super.dispatchDraw(canvas);

        if (mRadius > 0f) {
            canvas.restore();
        }



//        int saveCount = canvas.getSaveCount();
//        canvas.save();
//        super.dispatchDraw(canvas);
//        if (mShape != null) {
//            mShape.draw(canvas, mPaint);
//        }
//        canvas.restoreToCount(saveCount);




        canvas.save();
        if (mBorderColor != 0) {
            mRectF.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
            canvas.drawRoundRect(mRectF, mRadius, mRadius, mPaintColor);
        }
        canvas.restore();




//            if (mBorderColor != 0 && 0 != mBorderStroke) {
//            float strokeHalf = mBorderStroke;
//            float strokeHalf = mBorderStroke / 2;
//            mRectF.set(0, 0, getWidth() , getHeight() );
//            mRectF.set(strokeHalf, strokeHalf, getWidth() - strokeHalf, getHeight() - strokeHalf);
//            canvas.drawRoundRect(mRectF, mRadius, mRadius, mPaintColor);
//        }
    }

}
