package me.vast.common.util;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by zhengz on 2017/8/17.
 */

public class ViewUtils {

    public static boolean isDrawableNULL(View imageView) {
        if (imageView instanceof ImageView) {
            return null == ((ImageView) imageView).getDrawable();
        }
        return false;
    }

    public static boolean isBackgroundNULL(View view) {
        return null == view.getBackground();
    }

    public static void setBackgroundNULL(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(null);
        } else {
            view.setBackgroundDrawable(null);
        }
    }

    public static void setBackgroundImpl(View view, Drawable loadedImage) {
        if (null != loadedImage) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBgAPI16(view, loadedImage);
            } else {
                setBgAPI8(view, loadedImage);
            }
        }

    }

    public static void setBackgroundImpl(View view, Bitmap loadedImage) {
        if (null != loadedImage) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBgAPI16(view, loadedImage);
            } else {
                setBgAPI8(view, loadedImage);
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private static void setBgAPI16(View view, Drawable loadedImage) {
        view.setBackground(loadedImage);
    }

    @SuppressWarnings("deprecation")
    private static void setBgAPI8(View view, Drawable loadedImage) {
        view.setBackgroundDrawable(loadedImage);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private static void setBgAPI16(View view, Bitmap loadedImage) {
        view.setBackground(new BitmapDrawable(view.getResources(), loadedImage));
    }

    @SuppressWarnings("deprecation")
    private static void setBgAPI8(View view, Bitmap loadedImage) {
        view.setBackgroundDrawable(new BitmapDrawable(view.getResources(), loadedImage));
    }




    /**
     * 绘制实心圆弧
     *
     * @param width
     * @param height
     * @return
     */
    public static ShapeDrawable createOvalShape(int width, int height) {
        OvalShape ovalShape = new OvalShape();
        ovalShape.resize(width, height);
        ShapeDrawable shapeDrawable = new ShapeDrawable(ovalShape);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.setIntrinsicWidth(width);//设置固定宽高，否则不显示
        shapeDrawable.setIntrinsicHeight(width);
        return shapeDrawable;
    }


    public static Bitmap createBitmapFromView(View view) {
        try {
            Bitmap bitmap = Bitmap.createBitmap(
                    view.getWidth(),
                    view.getHeight(),
                    Bitmap.Config.ARGB_8888);
            //利用bitmap生成画布
            Canvas canvas = new Canvas(bitmap);
            //把view中的内容绘制在画布上
            view.draw(canvas);
            return bitmap;
        } catch (Exception e) {
//            System.gc();
            return null;
        }
    }

}
