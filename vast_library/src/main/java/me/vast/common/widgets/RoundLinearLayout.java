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
import android.util.TypedValue;
import android.widget.LinearLayout;


import java.util.Arrays;

import me.vast.common.R;

/**
 * Created by zhengz on 2017/9/1.
 * 自定义带有弧度背景
 * store颜色粗细
 * round弧度大小
 */

public class RoundLinearLayout extends LinearLayout {


    private Paint mPaintColor = new Paint(Paint.ANTI_ALIAS_FLAG);

    private RectF mRectF = new RectF();

//    private Shape mShape;

    private int mRadius;

    private float mBorderStroke;

    private int mBorderColor;

    private Path mRoundPath;


    public RoundLinearLayout(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public RoundLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setWillNotDraw(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(LAYER_TYPE_HARDWARE, null);
        }
//        mPaint.setFilterBitmap(true);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        int defaultRadius = context.getResources().getDimensionPixelSize(R.dimen.image_round_r5);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundLayoutXfermode);
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

        a.recycle();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
//            if (mShape == null) {
//                float[] radius = new float[8];
//                Arrays.fill(radius, mRadius);
//                mShape = new RoundRectShape(radius, null, null);
//            }
//            mShape.resize(getWidth(), getHeight());


            mRectF.set(0f, 0f, getMeasuredWidth(), getMeasuredHeight());

            if (null == mRoundPath) {
                mRoundPath = new Path();
            } else {
                mRoundPath.reset();
            }
            mRoundPath.addRoundRect(mRectF, mRadius, mRadius, Path.Direction.CW);
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
//        int saveCount = canvas.getSaveCount();
//        canvas.save();
//        super.dispatchDraw(canvas);
//        if (mShape != null) {
//            mShape.draw(canvas, mPaint);
//        }
//        canvas.restoreToCount(saveCount);



        if (mRadius > 0f) {
            canvas.save();
            canvas.clipPath(mRoundPath);  // 效果会有锯齿
        }
        super.dispatchDraw(canvas);

        if (mRadius > 0f) {
            canvas.restore();
        }


        canvas.save();
        if (mBorderColor != 0) {
            mRectF.set(0, 0, getWidth(), getHeight());
            canvas.drawRoundRect(mRectF, mRadius, mRadius, mPaintColor);
        }
        canvas.restore();
    }

}
