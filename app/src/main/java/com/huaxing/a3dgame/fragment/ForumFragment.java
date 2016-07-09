package com.huaxing.a3dgame.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.huaxing.a3dgame.R;

/**
 * Created by Administrator on 2016/7/8.
 */
public class ForumFragment extends Fragment{

    WebView webView;
    String url="http://bbs.3dmgame.com/forum.php";
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.froum_fragment_item,null);
        webView=(WebView)view.findViewById(R.id.web_view_news_webview);


        progressBar=(ProgressBar)view.findViewById(R.id.froum_probar);
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

                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    webView.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
