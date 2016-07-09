package com.huaxing.a3dgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.huaxing.a3dgame.service.DownLoadService;
import com.huaxing.a3dgame.utils.NetUtils;

import pl.droidsonroids.gif.GifImageView;

/**
 * 欢迎界面
 */
public class WelcomeActivity extends AppCompatActivity {
    GifImageView gifImageView;
    //动画
    Animation animation;
    //判断网络是否连接
    private boolean isNetConnected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_activitye);
        gifImageView=(GifImageView)findViewById(R.id.welcome_gifiamgeview);

        //添加动画
        animation=new AlphaAnimation(0,1.0f);
        animation.setDuration(3000);
        gifImageView.setAnimation(animation);


        //添加动画监听
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //下载数据前，判断网络是否连接
                isNetConnected=NetUtils.netConnected(WelcomeActivity.this);
                if(isNetConnected) {
                    //开启服务下载数据
                    Intent intent = new Intent(WelcomeActivity.this, DownLoadService.class);
                    startService(intent);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                 if(!isNetConnected){
                     Toast.makeText(WelcomeActivity.this,"请您连接网络",Toast.LENGTH_LONG).show();
                 }

                isFristLogin();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    //判断是否是第一次登录
    public  void isFristLogin(){
        //获得sharePreferences对象 加密存储的文件是否存在
        SharedPreferences sharedPreferences= getSharedPreferences("isFristLogin", Context.MODE_PRIVATE);
        boolean isLogin=sharedPreferences.getBoolean("isLogin",false);
        //如果是第一次的话，跳转到引导页面
        if(!isLogin){
            Intent intent=new Intent(WelcomeActivity.this,GuideActivity.class);
            startActivity(intent);
            finish();
        }else {//不是第一次 跳转到主界面
            Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
