package com.huaxing.a3dgame.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by Administrator on 2016/7/7.
 */
public class MemoryCacheUtils {

    private LruCache<String,Bitmap> lruCache;

    public MemoryCacheUtils() {
        //获得当前缓存的大小
            //获得当前app最大占用内存的大小
        int maxMemory=(int)Runtime.getRuntime().maxMemory();
        //去1/8之一最为缓存空间
        int cacheMemory=maxMemory/8;
        //实例换Lrucache
        lruCache=new LruCache<String,Bitmap>(cacheMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;
            }

        };
    }
    //添加文件到缓存
    public synchronized  void addBitmapToLrucache(String urlStr,Bitmap bitmap){

        if(urlStr!=null){
            lruCache.put(urlStr,bitmap);
        }
    }
    //从内存得到
    public  Bitmap getBitmapToLrucache(String urlStr){
        if(urlStr!=null&&lruCache.get(urlStr)!=null){
            return lruCache.get(urlStr);
        }
        return  null;
    }
    public void clean(){
        if(lruCache.size()>0){
            lruCache.evictAll();
        }
        lruCache=null;
    }
}
