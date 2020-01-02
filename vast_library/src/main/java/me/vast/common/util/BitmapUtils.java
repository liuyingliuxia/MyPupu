package me.vast.common.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;


public class BitmapUtils {

    public static byte[] bmpToByteArray(Bitmap bmp, final boolean needRecycle) {
        return bmpToByteArray(bmp, CompressFormat.PNG, needRecycle);
    }

    public static byte[] bmpToByteArray(Bitmap bmp, CompressFormat format, final boolean needRecycle) {
        return bmpToByteArray(bmp, format, 100, needRecycle);
    }

    public static byte[] bmpToByteArray(Bitmap bmp, CompressFormat format, int quality, final boolean needRecycle) {
        if (null == bmp) {
            return null;
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(format, quality, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

//    /**
//     * Bitmap 圆角处理
//     * @param bitmap
//     * @param pixels
//     * @return
//     */
//    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
//        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
//        Canvas canvas = new Canvas(output);
//        //        final int color = 0xff424242;
//        final Paint paint = new Paint();
//        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//        final RectF rectF = new RectF(rect);
//        float roundPx = pixels;
//        paint.setAntiAlias(true);
//        canvas.drawARGB(0, 0, 0, 0);
//        paint.setColor(0xFF000000);
//        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
//        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, rect, rect, paint);
//        return output;
//    }
//
//    public static InputStream getInputStream(Context context, Uri uri) throws FileNotFoundException {
//        return context.getContentResolver().openInputStream(uri);
//    }
//
//    public static byte[] InputStreamToByte(InputStream is) throws IOException {
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        int ch;
//        while ((ch = is.read()) != -1) {
//            os.write(ch);
//        }
//        byte imgdata[] = os.toByteArray();
//        FileUtils.closeQuietly(os);
//        return imgdata;
//    }
//
//    public static String getBase64FormUri(Context context, Uri uri) {
//        InputStream is = null;
//        try {
//            is = getInputStream(context, uri);
//            return new String(InputStreamToByte(is));
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            FileUtils.closeQuietly(is);
//        }
//        return "";
//    }

    /**
     * 原图缩放到目标宽高（贴到目标宽高位置）
     *
     * @param source
     * @param rWidth
     * @param rHeight
     * @return
     */
    public static Bitmap zoomRectCenter(Bitmap source, int rWidth, int rHeight, boolean recycle) {
        // 资源大小
        float drawableWidth = source.getWidth();
        float drawableHeight = source.getHeight();
        float scale = Math.min(rWidth / drawableWidth, rHeight / drawableHeight);
        float dx = Math.round((rWidth - drawableWidth * scale) * 0.5f);
        float dy = Math.round((rHeight - drawableHeight * scale) * 0.5f);

        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        matrix.postTranslate(dx, dy);
        Bitmap b1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        if (recycle && b1 != source) {
            source.recycle();
        }
        return b1;
    }

//    /**
//     * 图片转为黑白图
//     *
//     * @param src
//     * @return
//     */
//    public static Bitmap transformBlackWhite(Bitmap src) {
//        int width = src.getWidth();
//        int height = src.getHeight();
//        // create output bitmap
//        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
//        // color information
//        int A, R, G, B;
//        int pixel;
//
//        // scan through all pixels
//        for (int x = 0; x < width; ++x) {
//            for (int y = 0; y < height; ++y) {
//                // get pixel color
//                pixel = src.getPixel(x, y);
//                A = Color.alpha(pixel);
//                R = Color.red(pixel);
//                G = Color.green(pixel);
//                B = Color.blue(pixel);
////                int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);
//                int gray = (int) (0.3086 * R + 0.6094 * G + 0.0820 * B);
//                // use 128 as threshold, above -> white, below -> black
//                if (gray > 128)
//                    gray = 255;
//                else
//                    gray = 0;
//                // set new pixel color to output bitmap
//                bmOut.setPixel(x, y, Color.argb(A, gray, gray, gray));
//            }
//        }
//        return bmOut;
//    }
//
//
//    /**
//     * 怀旧效果原理
//     * R=0.393r+0.769g+0.189b
//     * G=0.349r+0.686g+0.168b
//     * B=0.272r+0.534g+0.131b
//     */
//    public Bitmap doProByPix(Bitmap src) {
//        long startTime = System.currentTimeMillis();
//        int width = src.getWidth();
//        int height = src.getHeight();
//        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
//        int pixColor, pixR, pixG, pixB, newR, newG, newB;
//        int[] pixels = new int[width * height];
//        src.getPixels(pixels, 0, width, 0, 0, width, height);
//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++) {
//                //获取对应点的像素
//                pixColor = pixels[width * i + j];
//
//                pixR = Color.red(pixColor);
//                pixG = Color.green(pixColor);
//                pixB = Color.blue(pixColor);
//                newR = (int) (0.393 * pixR + 0.769 * pixG + 0.189 * pixB);
//                newG = (int) (0.349 * pixR + 0.686 * pixG + 0.168 * pixB);
//                newB = (int) (0.272 * pixR + 0.534 * pixG + 0.131 * pixB);
//                int newColor = Color.argb(255, Math.min(255, newR), Math.min(255, newG), Math.min(255, newB));
//                pixels[width * i + j] = newColor;
//            }
//        }
//        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
////        long endTime = System.currentTimeMillis();
////        LogVast.e("tag", "this is old used time " + (endTime - startTime) + "ms");
//        return bitmap;
//    }
//
//    /**
//     * R G B 颜色值都在127以上时，像素点设为透明
//     *
//     * @param src
//     * @return
//     */
//    public static Bitmap doWhite2AlphaByPix(Bitmap src) {
//        int width = src.getWidth();
//        int height = src.getHeight();
//        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
//        int pixColor, pixR, pixG, pixB;
//        int[] pixels = new int[width * height];
//        src.getPixels(pixels, 0, width, 0, 0, width, height);
//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++) {
//                // 获取对应点的像素
//                pixColor = pixels[width * i + j];
//                pixR = Color.red(pixColor);
//                pixG = Color.green(pixColor);
//                pixB = Color.blue(pixColor);
//                if (pixR >= 127 && pixG >= 127 && pixB >= 127) {
//                    pixels[width * i + j] = Color.TRANSPARENT;
//                }
//            }
//
//        }
//        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//        return bitmap;
//    }
//
//
//    /**
//     * 修改图片对比度
//     *
//     * @param bmp
//     * @param contrast
//     * @param brightness
//     * @return
//     */
//    public static Bitmap changeBitmapContrastBrightness(Bitmap bmp, float contrast, float brightness) {
//        float lum = 256 * brightness / 100 * (1f - contrast);
//
//        ColorMatrix cm = new ColorMatrix(new float[]{
//                contrast, 0, 0, 0, lum,
//                0, contrast, 0, 0, lum,
//                0, 0, contrast, 0, lum,
//                0, 0, 0, 1, 0
//        });
////        ColorMatrix cm = new ColorMatrix(new float[]{
////                contrast, 0, 0, 0, brightness,
////                0, contrast, 0, 0, brightness,
////                0, 0, contrast, 0, brightness,
////                0, 0, 0, 1, 0
////        });
//
//        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
//        Canvas canvas = new Canvas(ret);
//        Paint paint = new Paint();
//        paint.setColorFilter(new ColorMatrixColorFilter(cm));
//        canvas.drawBitmap(bmp, 0, 0, paint);
//        return ret;
//    }
//
//    public static Bitmap applyContrast(Bitmap image, double contrastVal) {
//        final int width = image.getWidth();
//        final int height = image.getHeight();
//        final Bitmap contrastedImage = Bitmap.createBitmap(width, height, image.getConfig());
//
//        int A, R, G, B;
//        int pixel;
//
//        double contrast = Math.pow((100 + contrastVal) / 100, 2);
//
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                pixel = image.getPixel(x, y);
//                A = Color.alpha(pixel);
//                /**
//                 * 对比度
//                 */
//                R = Color.red(pixel);
//                R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
//                R = truncate(R);
//
//                G = Color.green(pixel);
//                G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
//                G = truncate(G);
//
//                B = Color.blue(pixel);
//                B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
//                B = truncate(B);
//                /**
//                 * 黑白值
//                 */
//                int gray = (int) (0.3086 * R + 0.6094 * G + 0.0820 * B);
////                int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);
//                if (gray > 128)
//                    gray = 255;
//                else
//                    gray = 0;
////                Log.i("ImageImprove", A + " " + R + " " + " " + G + " " + B);
//                contrastedImage.setPixel(x, y, Color.argb(A, gray, gray, gray));
////                contrastedImage.setPixel(x, y, Color.argb(A, R, G, B));
//            }
//        }
//        return contrastedImage;
//    }
//
//    private static int truncate(int value) {
//        if (value < 0) {
//            return 0;
//        } else if (value > 255) {
//            return 255;
//        }
//        return value;
//    }
//
//
//    /**
//     * 获得图片的高宽
//     *
//     * @param context
//     * @param uri
//     * @return
//     */
//    public static int[] getImageSize(Context context, Uri uri) {
//        int[] size = {0, 0};
//        InputStream temp;
//        try {
//            temp = context.getContentResolver().openInputStream(uri);
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            BitmapFactory.decodeStream(temp, null, options);
//            temp.close();
//            size[0] = options.outWidth;
//            size[1] = options.outHeight;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return size;
//    }

    /**
     * 获得图片的高宽
     *
     * @param path
     * @return
     */
    public static int[] getImageSize(String path) {
        int[] size = {0, 0};
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        size[0] = options.outWidth;
        size[1] = options.outHeight;
        return size;
    }


    /**
     * Bitmap写入到path
     *
     * @param bitmap
     * @param quality
     * @param path
     * @return
     */
    public static boolean writeBitmapToPath(Bitmap bitmap, int quality, String path) {
        return writeBitmapToFile(bitmap, quality, new File(path));
    }

    /**
     * Bitmap写入到文件file
     *
     * @param bitmap
     * @param quality
     * @param file
     * @return
     */
    public static boolean writeBitmapToFile(Bitmap bitmap, int quality, File file) {
        boolean isCompress = false;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            CompressFormat format = CompressFormatUtils.parseFormat(file.getAbsolutePath());
            isCompress = bitmap.compress(format, quality, out);
            out.flush();
            out.close();
        } catch (Exception e) {

        } finally {
            FileUtils.closeQuietly(out);
        }
        return isCompress;
    }


    /**
     * 写入缩放固定大小的Bitmap到File地址
     *
     * @param bitmap
     * @param path
     * @param dstWidth
     * @param dstHeight
     * @return
     */
    public static boolean writeScaledBitmapToFile(Bitmap bitmap, File path, int dstWidth, int dstHeight) {
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, true);
        if (null != thumbBmp) {
            bitmap.recycle();
            return writeBitmapToFile(thumbBmp, 100, path);
        }
        return false;
    }


    /**
     * 居中裁切Bitmap
     *
     * @param bitmap
     * @param corpWith
     * @param corpHeight
     * @return
     */
    public static Bitmap cropCenter(Bitmap bitmap, int corpWith, int corpHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (null == bitmap || bitmap.isRecycled() || width <= 0 || height <= 0) {
            return null;
        }
        int srcLeft = 0;
        int srcTop = 0;
        //      int dstLeft = 0;
        //      int dstTop = 0;
        if (corpWith >= width) {
            corpWith = width;
        } else {
            srcLeft = (width - corpWith) / 2;
        }
        if (corpHeight >= height) {
            corpHeight = height;
        } else {
            srcTop = (height - corpHeight) / 2;
        }
        //        // Crop the subset from the original Bitmap.
        //        final Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, srcLeft, srcTop, corpWith, corpHeight);
        return Bitmap.createBitmap(bitmap, srcLeft, srcTop, corpWith, corpHeight);
    }

    /**
     * 处理可能拍照后，图片翻转的问题
     *
     * @param srcBitmap
     * @param exifInfo
     * @return
     */
    public static Bitmap considerExactScaleAndOrientatiton(Bitmap srcBitmap, ExifInfo exifInfo) {
        if (!exifInfo.flipHorizontal && exifInfo.rotation == 0) {
            return srcBitmap;
        }

        Matrix m = new Matrix();
        // Scale to exact size if need
        // Flip bitmap if need
        if (exifInfo.flipHorizontal) {
            m.postScale(-1, 1);
        }
        // Rotate bitmap if need
        if (exifInfo.rotation != 0) {
            m.postRotate(exifInfo.rotation);
        }
        Bitmap finalBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), m, true);
        if (finalBitmap != srcBitmap) {
            srcBitmap.recycle();
        }
        return finalBitmap;
    }


    /**
     * 计算最接近 reqWidth  reqHeight
     *
     * @param width
     * @param height
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateSampleSize(int width, int height, int reqWidth, int reqHeight) {
        // can't proceed
        if (width <= 0 || height <= 0) {
            return 1;
        }
        // can't proceed
        if (reqWidth <= 0 && reqHeight <= 0) {
            return 1;
        } else if (reqWidth <= 0) {
            reqWidth = (int) (width * reqHeight / (float) height + 0.5f);
        } else if (reqHeight <= 0) {
            reqHeight = (int) (height * reqWidth / (float) width + 0.5f);
        }

        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            if (inSampleSize == 0) {
                inSampleSize = 1;
            }

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger
            // inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down
            // further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }

        return inSampleSize;
    }


    public static Bitmap decodeSampled(String pathName, int sampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        // RGB_565    zz暂时使用8888看是否会出现异常
        options.inPreferredConfig = Config.ARGB_8888;
        // sample size
        options.inSampleSize = sampleSize;

        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeFile(pathName, options);
        } catch (OutOfMemoryError e) {
            return null;
        }

        return checkInBitmap(bitmap, options, pathName);
    }


    private static Bitmap checkInBitmap(Bitmap bitmap, BitmapFactory.Options options, String path) {
        boolean honeycomb = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
        if (honeycomb && bitmap != options.inBitmap && options.inBitmap != null) {
            options.inBitmap.recycle();
            options.inBitmap = null;
        }

        if (bitmap == null) {
            try {
                bitmap = BitmapFactory.decodeFile(path, options);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

}
