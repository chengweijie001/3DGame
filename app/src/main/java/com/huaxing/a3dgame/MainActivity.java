package com.huaxing.a3dgame;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.huaxing.a3dgame.adapter.MainFragmentAdapter;
import com.huaxing.a3dgame.fragment.ForumFragment;
import com.huaxing.a3dgame.fragment.GameFragment;
import com.huaxing.a3dgame.fragment.MainFragment;
import com.huaxing.a3dgame.utils.SQLiteUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup radioGroup_bottom;
    private RadioButton radioButton_bottom01, radioButton_bottom02, radioButton_bottom03;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        radioGroup_bottom = (RadioGroup) findViewById(R.id.main_mainactivity_bottom_rg);
        radioButton_bottom01 = (RadioButton) findViewById(R.id.mainactivity_bottom_btn01);
        radioButton_bottom02 = (RadioButton) findViewById(R.id.mainactivity_bottom_btn02);
        radioButton_bottom03 = (RadioButton) findViewById(R.id.mainactivity_bottom_btn03);
        viewPager = (ViewPager) findViewById(R.id.main_main_activity_viewpage);

        List<Fragment> fragments = new ArrayList<>();
        MainFragment mainFragment = new MainFragment(this, getSupportFragmentManager());
        ForumFragment forumFragment = new ForumFragment();
        GameFragment gameFragment=new GameFragment();
        fragments.add(mainFragment);
        fragments.add(forumFragment);
        fragments.add(gameFragment);
        MainFragmentAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        initBottomBackground();
        radioGroup_bottom.setOnCheckedChangeListener(this);
        radioButton_bottom01.setEnabled(false);
        Log.i("MainActivity","onCreate执行了");
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.mainactivity_bottom_btn01:
                //设置点击文章 显示第一页的内容
                Toast.makeText(this,"mainactivity_bottom_btn01",Toast.LENGTH_SHORT).show();
                Log.i("MainActivity","onCheckedChanged执行了");
                viewPager.setCurrentItem(0);
                initBottomBackground();
                radioButton_bottom01.setEnabled(false);

                break;
            case R.id.mainactivity_bottom_btn02:
                initBottomBackground();
                radioButton_bottom02.setEnabled(false);
                Log.i("MainActivity","onCheckedChanged执行了");
                viewPager.setCurrentItem(1);

                break;
            case R.id.mainactivity_bottom_btn03:
                initBottomBackground();
                radioButton_bottom03.setEnabled(false);
                viewPager.setCurrentItem(2);
                break;
        }
    }

    //初始化底部图片
    public void initBottomBackground() {
        Log.i("MainActivity","onCheckedChanged执行了");
        radioButton_bottom01.setEnabled(true);
        radioButton_bottom02.setEnabled(true);
        radioButton_bottom03.setEnabled(true);
    }


        protected void onDestroy() {
        super.onDestroy();
        new SQLiteUtils(this).delAllDataFromSQLite();
    }
}
