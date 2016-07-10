package com.huaxing.a3dgame.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.huaxing.a3dgame.model.GameNews;
import com.huaxing.a3dgame.utils.HttpUtils;
import com.huaxing.a3dgame.utils.JsonUtils;
import com.huaxing.a3dgame.utils.SQLiteUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/7/7.
 */
public class DownLoadService02 extends Service {
    public static String urlStr="http://www.3dmgame.com/sitemap/api.php?row=20&typeid=1&paging=1&page=";

    List<GameNews> gameNewses;
    int num=1;
    String url=null;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("DownLoadService","下载数据");
        int downId=intent.getIntExtra("downId",1);
        Log.i("onStartCommand",downId+":downId");
        url=urlStr+downId;
        //异步任务下载数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                //网络加载数据
                byte[] jsonData= HttpUtils.downLoad(url);
                if(jsonData!=null){
                    try {
                        //开始解析Json
                        String json=new String(jsonData,"utf-8");
                        gameNewses= JsonUtils.getGames(json);
                        //图片保存到SD卡，并压缩 最后保存到数据库
                        for(int i=0;i<gameNewses.size();i++){
                            GameNews gameNews=gameNewses.get(i);
                            if(new SQLiteUtils(getApplicationContext()).saveGamedataToSQLite(gameNews)){
                                Log.i("DownLoadService02","保存成功");
                            }

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }
}

