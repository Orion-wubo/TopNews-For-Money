package com.fengmap.kotlindemo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * Created by bai on 2018/11/7.
 */

public class MainActivity extends Activity {

    private WebView mWebView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.webView);

        load("http://aigoodies.com/bick/public/index.php/api/index/get_appid/appid/kunlin20181106ttz");
    }

    private void update(String url){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetJavaScriptEnabled")
    void load(String url) {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
//        settings.setAllowFileAccess(true);
        /**
         * 允许通过 file url 加载的 Javascript 读取其他的本地文件,
         * Android 4.1 之前默认是true，在 Android 4.1 及以后默认是false,也就是禁止。
         */
        settings.setAllowFileAccessFromFileURLs(true);
        /**
         * 允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源，
         * Android 4.1 之前默认是true，在 Android 4.1 及以后默认是false,
         * 也就是禁止如果此设置是允许，则 setAllowFileAccessFromFileURLs 不起做用
         */
//        settings.setAllowUniversalAccessFromFileURLs(true);

        mWebView.loadUrl(url);
    }
}
