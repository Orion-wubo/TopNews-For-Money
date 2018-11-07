package com.fengmap.kotlindemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * splash
 * Created by bai on 2018/11/7.
 */

public class SplashActivity extends Activity{

    private String url = "http://aigoodies.com/bick/public/index.php/api/index/get_appid/appid/kunlin20181106ttz";

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String main = (String) msg.obj;
                // go main show my
            } else if (msg.what == 2) {
                String main = (String) msg.obj;
                // go main show url
                if (main.endsWith(".apk")) {
                    // down load and update
                } else {
                    // go to main with url
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    SplashActivity.this.startActivity(intent);
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SplashActivity.this,"please check you network",Toast.LENGTH_LONG).show();
                    }
                });
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

                        String showWebBase64 = Base64.encodeToString(showWeb.getBytes(), Base64.DEFAULT);
                        String PushKeyBase64 = Base64.encodeToString(pushKey.getBytes(), Base64.DEFAULT);
                        String mainURlBase64 = Base64.encodeToString(mainURl.getBytes(), Base64.DEFAULT);

                        Log.e("web",showWebBase64);
                        Log.e("push",PushKeyBase64);
                        Log.e("main",mainURlBase64);

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
}
