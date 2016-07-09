package com.huaxing.a3dgame.loader;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.huaxing.a3dgame.utils.HttpUtils;
import com.huaxing.a3dgame.utils.JsonUtils;
import com.huaxing.a3dgame.utils.SQLiteUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/7.
 */
public class DownLoader extends AsyncTaskLoader<LinkedList<HashMap<String, Object>>> {
    private Bundle bundle;
    private int typeId;
    String url;

    public DownLoader(Context context, Bundle bundle ){
        super(context);
        this.bundle=bundle;
        typeId=bundle.getInt("typeid");
        if(typeId!=1){
            url=bundle.getString("url");
            Log.i("DownLoader",url);
        }


    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        //强制下载
        forceLoad();
    }

    @Override
    public LinkedList<HashMap<String, Object>> loadInBackground() {
        SQLiteUtils sqLiteUtils=new SQLiteUtils(getContext());
        if(typeId!=1){
            byte[] buf= HttpUtils.downLoad(url);
            try {
                String json= new String(buf,"utf-8");
                Log.i("SubMainFragement",typeId+"");
                Log.i("SubMainFragement",sqLiteUtils.getGameNewsListByTypeId(typeId).toString());
                return  JsonUtils.getLinkedList(json);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        Log.i("SubMainFragement",typeId+":typeId");
        return  sqLiteUtils.getGameNewsList();
    }
}
