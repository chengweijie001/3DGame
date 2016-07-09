package com.huaxing.a3dgame.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * 操作图片的类
 */
public class ImageUtils {
    /**
     * 进行图片的压缩
     * @param data 原始数据
     * @param picWidth 期望的宽度
     * @param picHeight 期望的高度
     * @return 压缩完成的图片
     */
    public static Bitmap iamgeCompression(byte[] data,int picWidth,int picHeight){
        //获得原始图片的大小 而不获得图片的本身
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        Bitmap bitmap=BitmapFactory.decodeByteArray(data,0,data.length,options);

        //计算压缩的宽高比
       options.inSampleSize=getComperssionSize(options,picWidth,picHeight);
        //压缩图片
        options.inJustDecodeBounds=false;
        Bitmap afterBitmap=BitmapFactory.decodeByteArray(data,0,data.length,options);

        return afterBitmap;

    }

    private static int getComperssionSize(BitmapFactory.Options options,int picWidth,int picHeight){
        //原始宽高
        int width=options.outWidth;
        int height=options.outHeight;
        //开始计算宽高比
        if(width>picWidth||height>picHeight){
            int widthRadio=Math.round((float)width/picWidth);
            int heightRadio=Math.round((float)height/picHeight);
            //返回最终的压缩比（取宽度压缩比和高度压缩比的最小值 ，防止图片控件出现空白）
            return  widthRadio>heightRadio?heightRadio:widthRadio;
        }
        return  1;
    }
    //bitmap转换成字节数组
    public static byte[] bitmapToBytes(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        return  byteArrayOutputStream.toByteArray();
    }
}