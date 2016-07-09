package com.huaxing.a3dgame.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.app.Fragment;;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huaxing.a3dgame.R;
import com.huaxing.a3dgame.WebViewActivityNews;

import com.huaxing.a3dgame.adapter.PullToRe_ListView_Adapter;

import com.huaxing.a3dgame.loader.DownLoader;



import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 *  子菜单的视图
 */
public class SubMainFragement  extends Fragment {
    public static String urlStr="http://www.3dmgame.com/sitemap/api.php?row=20&typeid=";
    private static  String urlStr1="&paging=1&page=";
    //文章类型
    private int typeId;
    //实现下拉刷新的控件
    PullToRefreshListView pullToRefreshListView;
    //判断是上拉还是下拉
    public static boolean isRefresh = false;
    ListView listView;
    PullToRe_ListView_Adapter adapter;
    LinkedList<HashMap<String, Object>> alldata = new LinkedList<>();//网页数据
    //创建不同的Loader
    int num = 101;
    //loder管理器
    LoaderManager loaderManager;
    //接口
    LoaderManager.LoaderCallbacks<LinkedList<HashMap<String, Object>>> callbacks;

    View view;
    //刷新时执行下载的条件
    public static boolean isFinish = false;
      int downId=1;
     int upDownId=1;
    String url;
    private Activity activity;

    public SubMainFragement() {
    }

    @SuppressLint("ValidFragment")
    public SubMainFragement(int typeId, Activity activity) {
        this.typeId = typeId;
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.sub_main_fragment, null);
        Log.i("SubMainFragement",typeId+"  onCreateView  "+downId);
        initFragmentView();
        initListener();

        initCallbacks();
        url=urlStr+typeId+urlStr1+upDownId;
        final Bundle bundle = new Bundle();
        bundle.putInt("path", num);
        bundle.putString("url",url);
        loaderManager.initLoader(100, bundle, callbacks);
        //设置点击跳转浏览网页
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String htmlUrl=alldata.get(position).get("arcurl").toString();
                Intent intent=new Intent(activity, WebViewActivityNews.class);
                Log.i("SubMainFragementooo",activity.toString());
                intent.putExtra("htmlUrl",htmlUrl);
                activity.startActivity(intent);
            }
        });
        //刷新监听
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                isRefresh = false;
                url=urlStr+typeId+urlStr1+upDownId;
                loaderManager.initLoader(num++, bundle, callbacks);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                downId++;
                isRefresh = true;
                loaderManager.initLoader(num++, bundle, callbacks);
            }
        });
        return view;

    }

    //匿名内部类 实现LoaderCallbacks
    private void initCallbacks() {
        callbacks = new LoaderManager.LoaderCallbacks<LinkedList<HashMap<String, Object>>>() {
            @Override
            public Loader<LinkedList<HashMap<String, Object>>> onCreateLoader(int id, Bundle args) {
                if(isRefresh) {
                   String urlUp = urlStr + typeId + urlStr1 + downId;
                    args.putString("url", urlUp);
                }else {
                    args.putString("url", url);
                }
                DownLoader loader = new DownLoader(getContext(), args);
                Log.i("SubMainFragementooo","onCreateLoader"+args.getString("url"));
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<LinkedList<HashMap<String, Object>>> loader, LinkedList<HashMap<String, Object>> data) {
                if (data != null) {
                    if(num>101){
                        addData(data);
                    }
                    Log.i("SubMainFragement","onLoadFinished");
                    Log.i("LinkedList", data.toString());
                    alldata.addAll(data);
                    clearRepeatData(alldata);
                    adapter.notifyDataSetChanged();
                    pullToRefreshListView.onRefreshComplete();
                    //加载完
                    Log.i("isfinish", "onLoadFinished执行了");

                } else {
                    Log.i("aaa", "数据加载失败");
                }
            }
            @Override
            public void onLoaderReset(Loader<LinkedList<HashMap<String, Object>>> loader) {

            }
        };
    }
    //初始化所有监听
    private void initListener() {
    }

    //初始化view
    private void initFragmentView() {
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.sub_mainfragment_pulltorefresh_listview);
        listView = (ListView) pullToRefreshListView.getRefreshableView();
        //适配器
        adapter = new PullToRe_ListView_Adapter(alldata, getContext());
        listView.setAdapter(adapter);

        //获得Loader管理器
        loaderManager = getLoaderManager();
    }
    public void clearRepeatData(LinkedList<HashMap<String, Object>> data) {
        Set<HashMap<String, Object>> set = new LinkedHashSet<>();
        set.addAll(data);
        data.clear();
        data.addAll(set);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    public void addData(LinkedList<HashMap<String,Object>> data){
        for(int i=0;i<data.size();i++){
            if(isRefresh){
                alldata.addLast(data.get(i));
            }else {
                alldata.addFirst(data.get(i));
            }
        }

    }
}
