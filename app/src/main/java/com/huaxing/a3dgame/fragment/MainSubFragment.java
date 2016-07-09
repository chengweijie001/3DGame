package com.huaxing.a3dgame.fragment;

import android.annotation.SuppressLint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.huaxing.a3dgame.adapter.MainFragmentPagerAdapter;
import com.huaxing.a3dgame.adapter.PullToRe_ListView_Adapter;
import com.huaxing.a3dgame.customview.MainFragmentViewPager;
import com.huaxing.a3dgame.loader.DownLoader;
import com.huaxing.a3dgame.service.DownLoadService;
import com.huaxing.a3dgame.service.DownLoadService02;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * Created by Administrator on 2016/7/6.
 */
public class MainSubFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private MainFragmentViewPager mainFragmentViewPager;
    private LinearLayout linearLayout;
    View view;
    //文章类型
    private int typeId;
    List<ImageView> imagesList;
    View[] views;
    int curreIndex;//当前页面的索引
    //图片资源数组
    int[] images = {R.drawable.main_fragment_viewpager_default1, R.drawable.main_fragment_viewpager_default2, R.drawable.main_fragment_viewpager_default3};

    //实现下拉刷新的控件
    PullToRefreshListView pullToRefreshListView;
    //判断是上拉还是下拉
    public static boolean isRefresh = false;
    ListView listView;
    PullToRe_ListView_Adapter adapter;
    LinkedList<HashMap<String, Object>> alldata = new LinkedList<>();//网页数据

    //创建不同的Loader
   public static int num = 1;
   int downId=1;
    //loder管理器
    LoaderManager loaderManager;
    //接口
    LoaderManager.LoaderCallbacks<LinkedList<HashMap<String, Object>>> callbacks;

    private Activity activity;
    Intent intent;

    public MainSubFragment() {
    }

    @SuppressLint("ValidFragment")
    public MainSubFragment(int typeId, Activity activity) {
        this.typeId = typeId;
        this.activity = activity;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment, null);
        initFragmentView();
        initListener();

        initCallbacks();
         intent= new Intent(getContext(), DownLoadService02.class);
        final Bundle bundle = new Bundle();
        bundle.putInt("typeid", typeId);
        Log.i("LinkedList",typeId+"Bundle");
        loaderManager.initLoader(1, bundle, callbacks);
        //设置点击跳转浏览网页
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String htmlUrl=alldata.get(position).get("arcurl").toString();
                Intent intent=new Intent(activity, WebViewActivityNews.class);
                intent.putExtra("htmlUrl",htmlUrl);
                activity.startActivity(intent);
            }
        });
        //刷新监听
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    downId++;
                    isRefresh = false;
                    intent.putExtra("typeId",typeId);
                    intent.putExtra("downId",downId);
                    activity.startService(intent);
                    loaderManager.initLoader(num++, bundle, callbacks);


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                    isRefresh = true;

                    intent.putExtra("typeId",typeId);
                    intent.putExtra("downId",downId);
                    activity.startService(intent);
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
                DownLoader loader = new DownLoader(getContext(), args);
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<LinkedList<HashMap<String, Object>>> loader, LinkedList<HashMap<String, Object>> data) {

                if (data != null) {
                    if(num>1){
                        addData(data);
                    }
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
                     alldata.clear();

            }
        };
    }

    //初始化所有监听
    private void initListener() {
        mainFragmentViewPager.addOnPageChangeListener(this);
    }


    //初始化view
    private void initFragmentView() {
        mainFragmentViewPager = (MainFragmentViewPager) view.findViewById(R.id.mainfragmentviewpager);
        linearLayout = (LinearLayout) view.findViewById(R.id.mainfragment_linearlayout);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.mainfragment_pulltorefresh_listview);
        listView = (ListView) pullToRefreshListView.getRefreshableView();
            //初始化Viewpager数据
            initViewpager();

        //适配器
        MainFragmentPagerAdapter mainFragmentPagerAdapter = new MainFragmentPagerAdapter(imagesList);
        mainFragmentViewPager.setAdapter(mainFragmentPagerAdapter);
        adapter = new PullToRe_ListView_Adapter(alldata, getContext());
        listView.setAdapter(adapter);

        //获得Loader管理器
        loaderManager = getLoaderManager();
    }

    private void initViewpager() {
        imagesList = new ArrayList<ImageView>();
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(images[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imagesList.add(imageView);

        }
        views = new View[imagesList.size()];
        for (int i = 0; i < imagesList.size(); i++) {
            views[i] = linearLayout.getChildAt(i);
        }
        curreIndex = 0;
        views[curreIndex].setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //设置原点的背景
    @Override
    public void onPageSelected(int position) {
        if (position < 0 || position > imagesList.size()) {
            return;
        }
        views[position].setBackgroundColor(Color.WHITE);
        views[curreIndex].setBackgroundColor(Color.BLACK);
        curreIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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

    @Override
    public void onPause() {
        super.onPause();
//        activity.stopService(intent);
    }
}
