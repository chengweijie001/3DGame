package com.huaxing.a3dgame;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huaxing.a3dgame.adapter.MainFragmentAdapter;
import com.huaxing.a3dgame.fragment.ForumFragment;
import com.huaxing.a3dgame.fragment.MainFragment;
import com.huaxing.a3dgame.fragment.SubMainFragement;
import com.huaxing.a3dgame.utils.SQLiteUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener,RadioGroup.OnCheckedChangeListener {

    private HorizontalScrollView horizontalScrollView;
    private LinearLayout linearLayout;
    private RadioGroup radioGroup_top,radioGroup_bottom;
    private RadioButton radioButton_top_01,radioButton_top_02,radioButton_top_03,radioButton_top_04,radioButton_top_05,radioButton_top_06,
    radioButton_top_07,radioButton_top_08,radioButton_top_09,radioButton_top_10,radioButton_bottom_01,radioButton_bottom_02,radioButton_bottom_03;
    private ViewPager viewPager;
    private MainFragmentAdapter adapter;
    private List<Fragment> fragments;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();
        iniTListener();
    }
   //初始化监听事件
    private void iniTListener() {
        viewPager.addOnPageChangeListener(this);
        radioGroup_top.setOnCheckedChangeListener(this);
        radioGroup_bottom.setOnCheckedChangeListener(this);
    }

    //初始化碎片
    private void initFragment() {
        fragments=new ArrayList<Fragment>();
        mainFragment=new MainFragment(1,this);
        SubMainFragement SubMainFragement2=new SubMainFragement(2,this);
        SubMainFragement SubMainFragement3=new SubMainFragement(151,this);
        SubMainFragement SubMainFragement4=new SubMainFragement(152,this);
        SubMainFragement SubMainFragement5=new SubMainFragement(153,this);
        SubMainFragement SubMainFragement6=new SubMainFragement(154,this);
        SubMainFragement SubMainFragement7=new SubMainFragement(196,this);
        SubMainFragement SubMainFragement8=new SubMainFragement(197,this);
        SubMainFragement SubMainFragement9=new SubMainFragement(199,this);
        SubMainFragement SubMainFragement10=new SubMainFragement(25,this);
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

        adapter=new MainFragmentAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
    }

    //初始化view
    private void initView() {
        viewPager=(ViewPager)findViewById(R.id.main_activity_viewpager);
        horizontalScrollView=(HorizontalScrollView)findViewById(R.id.mainactivity_top_hscrollview);
        radioGroup_top=(RadioGroup)findViewById(R.id.mainactivity_top_radiogroup);
        radioGroup_bottom=(RadioGroup)findViewById(R.id.mainactivity_bottom_rg);
        radioButton_top_01=(RadioButton)findViewById(R.id.mainactivity_radiobtn01);
        radioButton_top_02=(RadioButton)findViewById(R.id.mainactivity_radiobtn02);
        radioButton_top_03=(RadioButton)findViewById(R.id.mainactivity_radiobtn03);
        radioButton_top_04=(RadioButton)findViewById(R.id.mainactivity_radiobtn04);
        radioButton_top_05=(RadioButton)findViewById(R.id.mainactivity_radiobtn05);
        radioButton_top_06=(RadioButton)findViewById(R.id.mainactivity_radiobtn06);
        radioButton_top_07=(RadioButton)findViewById(R.id.mainactivity_radiobtn07);
        radioButton_top_08=(RadioButton)findViewById(R.id.mainactivity_radiobtn08);
        radioButton_top_09=(RadioButton)findViewById(R.id.mainactivity_radiobtn09);
        radioButton_top_10=(RadioButton)findViewById(R.id.mainactivity_radiobtn10);
        radioButton_bottom_01=(RadioButton)findViewById(R.id.mainactivity_bottom_btn01);
        radioButton_bottom_02=(RadioButton)findViewById(R.id.mainactivity_bottom_btn02);
        radioButton_bottom_03=(RadioButton)findViewById(R.id.mainactivity_bottom_btn03);
        radioButton_top_01.setChecked(true);
        radioButton_bottom_01.setEnabled(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.mainactivity_radiobtn01:
                    Toast.makeText(MainActivity.this,"mainactivity_radiobtn01",Toast.LENGTH_SHORT).show();
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
                case R.id.mainactivity_bottom_btn01:
                    //设置点击文章 显示第一页的内容
                    viewPager.setCurrentItem(0);
                    initBottomBackground();
                    radioButton_bottom_01.setEnabled(false);
                    Toast.makeText(MainActivity.this,"bottom_btn01",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.mainactivity_bottom_btn02:
                    initBottomBackground();
                    radioButton_bottom_02.setEnabled(false);
                    Toast.makeText(MainActivity.this,"bottom_btn02",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.mainactivity_bottom_btn03:
                    initBottomBackground();
                    radioButton_bottom_03.setEnabled(false);
                    Toast.makeText(MainActivity.this,"bottom_btn03",Toast.LENGTH_SHORT).show();
                    break;
            }
    }

    //初始化底部图片
    public void initBottomBackground(){
        radioButton_bottom_01.setEnabled(true);
        radioButton_bottom_02.setEnabled(true);
        radioButton_bottom_03.setEnabled(true);
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        new SQLiteUtils(this).delAllDataFromSQLite();
    }

}
