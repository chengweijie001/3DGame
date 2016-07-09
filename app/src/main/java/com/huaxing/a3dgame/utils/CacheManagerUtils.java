package com.huaxing.a3dgame.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import com.huaxing.a3dgame.adapter.GridViewAdapter;

/**
 *
 * 管理缓存的类
 */
public class CacheManagerUtils {
    private MemoryCacheUtils memoryCacheUtils=new MemoryCacheUtils();
    private FileCacheUtils fileCacheUtils=new FileCacheUtils();
    private WebCacheUtils webCacheUtils=new WebCacheUtils();
    private Handler handler=new Handler();
    private Bitmap afterBitmap;
   //得到缓存
    public void getBitmapToCache(final String urlStr, final ImageView imageView){
        if(memoryCacheUtils.getBitmapToLrucache(urlStr)!=null){
            imageView.setImageBitmap(memoryCacheUtils.getBitmapToLrucache(urlStr));
        }
        else if(fileCacheUtils.getBitmapFromSDCard(urlStr)!=null){
            imageView.setImageBitmap(fileCacheUtils.getBitmapFromSDCard(urlStr));
            memoryCacheUtils.addBitmapToLrucache(urlStr,fileCacheUtils.getBitmapFromSDCard(urlStr));
        }
        else {
            webCacheUtils.getWebCache(urlStr, new WebCacheUtils.CallBacks() {
                @Override
                public void getResult(byte[] data) {
                    if(GridViewAdapter.isGame){
                       afterBitmap=ImageUtils.iamgeCompression(data,80,80);
                    }
                    //压缩图片
                    afterBitmap=ImageUtils.iamgeCompression(data,60,60);
                    //压缩后的图片转换成数组
                    byte[] buf=ImageUtils.bitmapToBytes(afterBitmap);
                    memoryCacheUtils.addBitmapToLrucache(urlStr,afterBitmap);
                    fileCacheUtils.saveFileToCache(urlStr,buf);
                    //调用Handler的Post方法 在UI线程更改视图
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(urlStr.equals(imageView.getTag().toString())) {
                                imageView.setImageBitmap(afterBitmap);
                            }
                        }
                    });
                }
            });
        }
    }
    //删除文件缓存
    public  void delete(){
        fileCacheUtils.clear();
    }
}
