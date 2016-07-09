package com.huaxing.a3dgame.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.huaxing.a3dgame.R;
import com.huaxing.a3dgame.adapter.MainFragmentAdapter;
import com.huaxing.a3dgame.customview.MainFragmentViewPager;
import com.huaxing.a3dgame.fragment.ForumFragment;
import com.huaxing.a3dgame.fragment.MainSubFragment;
import com.huaxing.a3dgame.fragment.SubMainFragement;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements ViewPager.OnPageChangeListener,RadioGroup.OnCheckedChangeListener {

    private HorizontalScrollView horizontalScrollView;
    private LinearLayout linearLayout;
    private RadioGroup radioGroup_top;
    private RadioButton radioButton_top_01;
    private MainFragmentViewPager viewPager;
    private MainFragmentAdapter adapter;
    private List<Fragment> fragments;
    private MainSubFragment mainFragment;
    View view;
    Activity activity;
    FragmentManager fragmentManager;

    public MainFragment() {
    }
    @SuppressLint("ValidFragment")
    public MainFragment(Activity activity, FragmentManager fragmentManager) {
        this.activity=activity;
        this.fragmentManager=fragmentManager;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_main,null);
        Log.i("bbbb","MainFragment__onCreateView");
        initView();
        initFragment();
        iniTListener();
        return view;

    }

    //初始化监听事件
    private void iniTListener() {
        viewPager.addOnPageChangeListener(this);
        radioGroup_top.setOnCheckedChangeListener(this);
    }

    //初始化碎片
    private void initFragment() {
        fragments=new ArrayList<Fragment>();
        mainFragment=new MainSubFragment(1,activity);
        SubMainFragement SubMainFragement2=new SubMainFragement(2,activity);
        SubMainFragement SubMainFragement3=new SubMainFragement(151,activity);
        SubMainFragement SubMainFragement4=new SubMainFragement(152,activity);
        SubMainFragement SubMainFragement5=new SubMainFragement(153,activity);
        SubMainFragement SubMainFragement6=new SubMainFragement(154,activity);
        SubMainFragement SubMainFragement7=new SubMainFragement(196,activity);
        SubMainFragement SubMainFragement8=new SubMainFragement(197,activity);
        SubMainFragement SubMainFragement9=new SubMainFragement(199,activity);
        SubMainFragement SubMainFragement10=new SubMainFragement(25,activity);
        ForumFragment forumFragment=new ForumFragment();
        fragments.add(mainFragment);
        fragments.add(SubMainFragement2);
        fragments.add(SubMainFragement3);
        fragments.add(SubMainFragement4);
        fragments.add(SubMainFragement5);
        fragments.add(SubMainFragement6);
        fragments.add(SubMainFragement7);
        fragments.add(SubMainFragement8);
        fragments.add(SubMainFragement9);
        fragments.add(SubMainFragement10);
        fragments.add(forumFragment);

        adapter=new MainFragmentAdapter(getActivity().getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
    }

    //初始化view
    private void initView() {
        viewPager=(MainFragmentViewPager)view.findViewById(R.id.main_activity_viewpager);
        horizontalScrollView=(HorizontalScrollView)view.findViewById(R.id.mainactivity_top_hscrollview);
        radioGroup_top=(RadioGroup)view.findViewById(R.id.mainactivity_top_radiogroup);
        radioButton_top_01=(RadioButton)view.findViewById(R.id.mainactivity_radiobtn01);
        radioButton_top_01.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.mainactivity_radiobtn01:
                     viewPager.setCurrentItem(0);
                    break;
                case R.id.mainactivity_radiobtn02:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.mainactivity_radiobtn03:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.mainactivity_radiobtn04:
                    viewPager.setCurrentItem(3);
                    break;
                case R.id.mainactivity_radiobtn05:
                    viewPager.setCurrentItem(4);
                    break;
                case R.id.mainactivity_radiobtn06:
                    viewPager.setCurrentItem(5);
                    break;
                case R.id.mainactivity_radiobtn07:
                    viewPager.setCurrentItem(6);
                    break;
                case R.id.mainactivity_radiobtn08:
                    viewPager.setCurrentItem(7);
                    break;
                case R.id.mainactivity_radiobtn09:
                    viewPager.setCurrentItem(8);
                    break;
                case R.id.mainactivity_radiobtn10:
                    viewPager.setCurrentItem(9);
                    break;

            }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //顶部的滚动条出现移动效果
        horizontalScrollView.setVisibility(View.VISIBLE);
        radioGroup_top.setVisibility(View.VISIBLE);
        //获得当前page对象的Radiobutton
        RadioButton radioButton=(RadioButton)radioGroup_top.getChildAt(position);
        radioButton.setChecked(true);
        //让顶部的RadioButton随着pager一起滑动
        int left=radioButton.getLeft();
        horizontalScrollView.smoothScrollTo(left,0);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
   //关闭程序的时候删除数据

//    protected void onDestroy() {
//        super.onDestroy();
//        new SQLiteUtils(this).delAllDataFromSQLite();
//    }

}
