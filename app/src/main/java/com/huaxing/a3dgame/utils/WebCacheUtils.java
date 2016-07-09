package com.huaxing.a3dgame.utils;

import android.util.Log;

/**
 * 使用接口回调的方式
 */
public class WebCacheUtils {

    public void getWebCache(final String urlStr, final CallBacks callBacks){
        Log.i("aaa",urlStr);
        //开启线程下载
        new Thread(new Runnable() {
            @Override
            public void run() {
                callBacks.getResult(HttpUtils.downLoad(urlStr));

            }
        }).start();
    }
    //接口
    public interface  CallBacks{
        void getResult(byte[] data);
    }

}
