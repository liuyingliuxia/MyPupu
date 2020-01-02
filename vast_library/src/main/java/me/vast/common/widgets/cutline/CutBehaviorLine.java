package me.vast.common.widgets.cutline;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

import me.vast.common.R;

/**
 * author : zhengz
 * time   : 2018/3/8
 * desc   : 绘制分割线代理类
 */

public class CutBehaviorLine {
    public static final int CUT_HORIZONTAL = 0x1;
    public static final int CUT_VERTICAL = 0x2;

    private View targetView;
    private LineRect horiLine;
    private LineRect veriLine;

    class LineRect {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int count;
        float size;
        float start;
        float end;
        boolean hide;
    }

    private CutBehaviorLine() {

    }

    public void init(View view, AttributeSet attrs) {
        targetView = view;
        if (null != attrs) {
            TypedArray a = view.getContext().obtainStyledAttributes(attrs, R.styleable.CutLine);
            // 线的数量、位置、高度，颜色
            int lineCount = a.getInt(R.styleable.CutLine_cutline_count, 1);
            int lineOrientation = a.getInt(R.styleable.CutLine_cutline_orientation, CUT_HORIZONTAL);
            int lineColor = a.getColor(R.styleable.CutLine_cutline_color, Color.BLACK);
            float lineSize = a.getDimension(R.styleable.CutLine_cutline_size, 1);

            if ((lineOrientation & CUT_HORIZONTAL) != 0) {
                setLine(CUT_HORIZONTAL,
                        a.getInt(R.styleable.CutLine_cutline_h_count, lineCount),
                        a.getDimension(R.styleable.CutLine_cutline_h_size, lineSize),
                        a.getColor(R.styleable.CutLine_cutline_h_color, lineColor),
                        a.getDimension(R.styleable.CutLine_cutline_h_start, 0),
                        a.getDimension(R.styleable.CutLine_cutline_h_end, 0));
            }

            if ((lineOrientation & CUT_VERTICAL) != 0) {
                setLine(CUT_VERTICAL,
                        a.getInt(R.styleable.CutLine_cutline_v_count, lineCount),
                        a.getDimension(R.styleable.CutLine_cutline_v_size, lineSize),
                        a.getColor(R.styleable.CutLine_cutline_v_color, lineColor),
                        a.getDimension(R.styleable.CutLine_cutline_v_start, 0),
                        a.getDimension(R.styleable.CutLine_cutline_v_end, 0));
            }
            a.recycle();
        }
    }

    public void setLine(int lineGravity, int count, float size, @ColorInt int lineColor, float start, float end) {
        if ((lineGravity & CUT_HORIZONTAL) != 0 && count > 0) {
            horiLine = new LineRect();
            horiLine.paint.setColor(lineColor);
            horiLine.count = count;
            horiLine.size = size;
            horiLine.start = start;
            horiLine.end = end;
        }
        if ((lineGravity & CUT_VERTICAL) != 0 && count > 0) {
            veriLine = new LineRect();
            veriLine.paint.setColor(lineColor);
            veriLine.count = count;
            veriLine.size = size;
            veriLine.start = start;
            veriLine.end = end;
        }
    }


    /**
     * 隐藏线
     *
     * @param lineOrientation
     */
    public void hideOrientation(int lineOrientation) {
        hideOrientation(lineOrientation, true);
    }

    /**
     * 显示线
     *
     * @param lineOrientation
     */
    public void showOrientation(int lineOrientation) {
        hideOrientation(lineOrientation, false);
    }

    private void hideOrientation(int lineOrientation, boolean hide) {
        boolean change = false;
        if ((lineOrientation & CUT_HORIZONTAL) != 0) {
            if (null != horiLine && horiLine.hide != hide) {
                horiLine.hide = hide;
                change = true;
            }
        }
        if ((lineOrientation & CUT_VERTICAL) != 0) {
            if (null != veriLine && veriLine.hide != hide) {
                veriLine.hide = hide;
                change = true;
            }
        }
        if (change) {
            targetView.invalidate();
        }
    }

    public void drawLine(Canvas canvas) {
        if (null == targetView) {
            return;
        }
        float width = targetView.getWidth();
        float height = targetView.getHeight();
        if (width == 0 || height == 0) {
            return;
        }

        int saveCount = canvas.getSaveCount();
        if (null != horiLine && !horiLine.hide && horiLine.count > 0) {
            float item = height / (horiLine.count + 1);
            float top = item;   // FIXME 后期包含前后 位置
            for (int i = 0; i < horiLine.count; i++) {
                top += item * i - horiLine.size / 2;
                canvas.drawRect(horiLine.start, top, width - horiLine.end, top + horiLine.size, horiLine.paint);
            }
        }

        if (null != veriLine && !veriLine.hide && veriLine.count > 0) {
            float item = width / (veriLine.count + 1);
            float left = item;   // FIXME 后期包含前后 位置
            for (int i = 0; i < veriLine.count; i++) {
                left += item * i - veriLine.size / 2;
                canvas.drawRect(left, veriLine.start, left + veriLine.size, height - veriLine.end, veriLine.paint);
            }
        }

        canvas.restoreToCount(saveCount);
    }


    public static CutBehaviorLine create() {
        return new CutBehaviorLine();
    }

}
