package com.haotsang.common.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.IOException;

public class BitmapUtils {

    public static Bitmap adjustPhotoRotation(Bitmap bm, final int degrees) {
        Matrix m = new Matrix();
        m.setRotate(degrees, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        try {
            return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Bitmap scale(Bitmap bit, float scaleX, float scaleY) {
        if (bit == null) return null;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        Bitmap buffer = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(), bit.getHeight(), matrix, true);
        return buffer;
    }

    public static Bitmap scaleWithSize(Bitmap bit, int width, int height) {
        if (bit == null) return null;
        float scaleX = ((float) width) / bit.getWidth();
        float scaleY = ((float) height) / bit.getHeight();
        if (scaleX == 1 && scaleY == 1) return bit;
        return scale(bit, scaleX, scaleY);
    }

    private Bitmap scaleBitmap(Bitmap bmp, int maxSize) {
        float maxSizeF = maxSize;
        float widthScale = maxSizeF / bmp.getWidth();
        float heightScale = maxSizeF / bmp.getHeight();
        float scale = Math.min(widthScale, heightScale);
        int height = (int) (bmp.getHeight() * scale);
        int width = (int) (bmp.getWidth() * scale);
        return Bitmap.createScaledBitmap(bmp, width, height, true);
    }


    /**
     * 按比例缩放图片
     *
     * @param origin 原图
     * @param ratio 比例
     * @return 新的bitmap
     */
    private Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }


    /**
     * 获取图片的旋转角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
//                case ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90;
//                case ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180;
//                case ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照指定的角度进行旋转
     *
     * @param bitmap 需要旋转的图片
     * @param degree 指定的旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bitmap, int degree) {
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return newBitmap;
    }
}
