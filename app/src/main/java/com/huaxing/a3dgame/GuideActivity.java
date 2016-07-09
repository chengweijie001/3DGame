package com.huaxing.a3dgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huaxing.a3dgame.adapter.GuideActivityAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private ViewPager viewPager;
    private List<View> views;
    private View view01,view02,view03;
    private ImageView[] dots;
    private int currentIndex=0;//当前页面的索引
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
        initDot();

    }
    //初始化图片的选择
    private void initDot(){
        //获得布局
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.guide_activity_linearla);
        dots=new ImageView[views.size()];
        //得到布局下所有的点
        for(int i=0;i<views.size();i++){
            dots[i]=(ImageView) linearLayout.getChildAt(i);
            dots[i].setEnabled(true);
        }
        //设置当前页面的image是白色的
        dots[currentIndex].setEnabled(false);
    }
   //初始化View
    private void initView(){
        viewPager=(ViewPager)findViewById(R.id.guide_activity_viewpage);
        LayoutInflater inflater=LayoutInflater.from(this);
        view01=inflater.inflate(R.layout.guide_activity_page01,null);
        view02=inflater.inflate(R.layout.guide_activity_page02,null);
        view03=inflater.inflate(R.layout.guide_activity_page03,null);
        views=new ArrayList<View>();
        views.add(view01);
        views.add(view02);
        views.add(view03);
        //适配器
        GuideActivityAdapter adapter=new GuideActivityAdapter(views);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //设置底部点显示的颜色
        if(position<0||position>=views.size()){
            return;
        }
        //设置当前的为选择状态
        dots[position].setEnabled(false);
        //设置前一个为非选择状态
        dots[currentIndex].setEnabled(true);
        //更新页面的索引
        currentIndex=position;

        //获得最后一个页面的Button控件
        if(position==(views.size()-1)) {
            Button button=(Button) views.get(position).findViewById(R.id.guide_activity_btn);
            //设置监听
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //保存当前的登录信息
                    saveLogin();
                    //跳转到主界面
                    Intent intent=new Intent(GuideActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        }
    }
     //保存登录过的信息
    private void saveLogin() {
        SharedPreferences sharedPreferences=getSharedPreferences("isFristLogin", Context.MODE_PRIVATE);
       SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putBoolean("isLogin",true);
        editor.commit();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
