package com.huaxing.a3dgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewActivityNews extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_activity_news);

        progressBar=(ProgressBar)findViewById(R.id.web_view_news_probar);
        webView=(WebView)findViewById(R.id.web_view_news_webview);
        Intent intent=getIntent();
        String url=intent.getStringExtra("htmlUrl");
        Log.i("aaa",url);
        //加载网页
        webView.loadUrl(url);
        //隐藏webview
        webView.setVisibility(View.INVISIBLE);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
         //掌控网页的加载进度
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                //设置标题
                setTitle("加载中....");
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    webView.setVisibility(View.VISIBLE);
                }
                //获得网页的标
            }
                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
                     setTitle(title);
                }

        });
    }
}
