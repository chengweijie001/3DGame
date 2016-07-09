package com.huaxing.a3dgame.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2016/7/5.
 */
public class NetUtils {
    //判断网络是否连接
    public static boolean netConnected(Activity activity) {
        //获得网络连接管理对象
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        //根据网络连接对象 获得连接信息
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //如果连接的对象不为空，或者信息对象是活动的 说明网络是连接的
        if (networkInfo != null || networkInfo.isAvailable()) {
            return true;
        }
        return false;
    }
}
