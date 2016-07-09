package com.huaxing.a3dgame.aysnctask;

import android.content.Context;
import android.os.AsyncTask;

import com.huaxing.a3dgame.adapter.PullToRe_ListView_Adapter;
import com.huaxing.a3dgame.model.GameNews;
import com.huaxing.a3dgame.utils.HttpUtils;
import com.huaxing.a3dgame.utils.JsonUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/7/7.
 */
public class GameNewsAysncTask extends AsyncTask<String,Void,List<GameNews>> {
    private  List<GameNews> gameNewses;
    private Context context;
    private PullToRe_ListView_Adapter adapter;

    public GameNewsAysncTask(List<GameNews> gameNewses, Context context, PullToRe_ListView_Adapter adapter) {
        this.gameNewses = gameNewses;
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    protected List<GameNews> doInBackground(String... params) {
        //网络加载数据
        byte[] buf= HttpUtils.downLoad(params[0]);
        if(buf!=null){
            try {
                //解析Json数据
                String json=new String(buf,"utf-8");

                    return JsonUtils.getGames(json);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        return null;
    }

    @Override
    protected void onPostExecute(List<GameNews> gameNewses) {

        super.onPostExecute(gameNewses);
    }
}
