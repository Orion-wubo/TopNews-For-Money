package com.fengmap.kotlindemo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.fengmap.kotlindemo.R;
import com.fengmap.kotlindemo.util.ShareUtil;
import com.fengmap.kotlindemo.view.CustomePopwindow;

/**
 * Created by bai on 2018/11/9.
 */

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView webView;
    private PopupWindow mPopupWindow;
    private String url;
    private FloatingActionButton fab;
    private CustomePopwindow mCustomPopWindow;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop(view);
            }
        });

        webView = findViewById(R.id.webView);
        TextView tv_title = findViewById(R.id.detail_tv_title);
        tv_title.setText(title);


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        loadUrl(url);
    }

    private void showPop(View view) {
        View contentView = LayoutInflater.from(DetailActivity.this).inflate(R.layout.popuplayout, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow = new CustomePopwindow.PopupWindowBuilder(DetailActivity.this)
                .setView(contentView)
                .create();
        mCustomPopWindow.showAsDropDown(fab, fab.getWidth()/2-mCustomPopWindow.getWidth()/2, -(fab.getHeight() + mCustomPopWindow.getHeight()));
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(final View contentView) {
        contentView.findViewById(R.id.ib_qq).setOnClickListener(this);
        contentView.findViewById(R.id.ib_wx).setOnClickListener(this);
    }

    public void back(View view) {
        finish();
    }

    private void loadUrl(String url) {
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView var1, int var2, String var3, String var4) {
                Toast.makeText(DetailActivity.this, "加载失败", Toast.LENGTH_LONG).show();
            }
        });
        //进度条
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    return;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) webView.destroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_qq:
                ShareUtil.shareQQ(DetailActivity.this, url);
                break;
            case R.id.ib_wx:
                ShareUtil.shareWechatFriend(DetailActivity.this, url);
                break;
        }
        if (mCustomPopWindow != null) {
            mCustomPopWindow.dissmiss();
        }
    }
}
