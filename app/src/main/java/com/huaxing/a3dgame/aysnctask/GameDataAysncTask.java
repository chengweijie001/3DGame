package com.huaxing.a3dgame.aysnctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.huaxing.a3dgame.adapter.ListViewAdapter;
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
public class GameDataAysncTask extends AsyncTask<String,Void,List<HashMap<String,Object>>> {
    private List<HashMap<String,Object>> data;
    private Context context;
    private ListViewAdapter adapter;

    public GameDataAysncTask(List<HashMap<String, Object>> data, Context context, ListViewAdapter adapter) {
        this.data = data;
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    protected List<HashMap<String, Object>> doInBackground(String... params) {
        Log.i("onPostExecute",params[0]);
        if(params[0]!=null){
            byte[] buf=HttpUtils.downLoad(params[0]);
            if(buf!=null){
                try {
                    String json=new String(buf,"utf-8");
                    Log.i("onPostExecute",json);
                    return JsonUtils.getGamesThemeFromJson(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<HashMap<String, Object>> hashMaps) {
        super.onPostExecute(hashMaps);
        Log.i("onPostExecute","onPostExecute");
        if(hashMaps!=null){
            Log.i("Hash",hashMaps.toString());
            data.addAll(hashMaps);
            Log.i("onPostExecute111",data.toString());
            adapter.notifyDataSetChanged();

        }
    }
}
