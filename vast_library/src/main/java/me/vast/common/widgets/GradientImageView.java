package me.vast.common.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import me.vast.common.R;


/**
 * author : zhengz
 * time   : 2018/2/3
 * desc   : 图片 上层的渐变效果
 */

public class GradientImageView extends android.support.v7.widget.AppCompatImageView {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mColorStart, mColorEnd;
    private float mPosStart, mPosEnd;

    public GradientImageView(Context context) {
        this(context, null);
    }

    public GradientImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.GradientView);
        mPosStart = typedArray.getFloat(R.styleable.GradientView_gradient_position_start, 0);
        mPosEnd = typedArray.getFloat(R.styleable.GradientView_gradient_position_end, 0);
        mColorStart = typedArray.getColor(R.styleable.GradientView_gradient_color_start, 0);
        mColorEnd = typedArray.getColor(R.styleable.GradientView_gradient_color_end, 0);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        if (width == 0 || height == 0) {
            return;
        }
        if (null == mPaint.getShader()) {
            mPaint.setShader(new LinearGradient(0, 0, 0, height, new int[]{mColorStart, mColorEnd}, new float[]{mPosStart, mPosEnd}, Shader.TileMode.CLAMP));
        }
        canvas.drawRect(0, 0, width, height, mPaint);
    }
}
