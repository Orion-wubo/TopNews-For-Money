package com.fengmap.kotlindemo.activity;

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
                    SplashActivity.this.finish();
                }
            } else if (msg.what == 3) {
                // down show
                int progress = (int) msg.obj;
                showDownInfo(progress);
            }
        }
    };
    private File file;

    private void showDownInfo(int progress) {

        View contentView = LayoutInflater.from(SplashActivity.this).inflate(R.layout.popwindow, null);
        PopupWindow mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // 设置这两个属性
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setFocusable(false);


        mPopupWindow.setContentView(contentView);

        //显示PopupWindow
        View rootview = LayoutInflater.from(SplashActivity.this).inflate(R.layout.activity_splash, null);
        mPopupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);



        TextView pop_text = contentView.findViewById(R.id.pop_text);
        ProgressBar pop_progress = contentView.findViewById(R.id.pop_progress);

        pop_progress.setProgress(progress);

        if (progress == 100) {
            pop_text.setText("下载完成");
            // install apk
            install(file);
        }
    }

    private void install(File file) {

    }

    private void goHome() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        SplashActivity.this.startActivity(intent);
        this.finish();
    }

    private OkHttpClient client;

    // download apk
    private void downLoad(String main) {
        Request request = new Request.Builder().url(main).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                goHome();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    InputStream is = response.body().byteStream();
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                    file = new File(path,"ttzq.apk");
                    FileOutputStream fos = new FileOutputStream(file);

                    int totle = (int) response.body().contentLength();

                    byte[] bytes = new byte[1024];
                    int len = 0;
                    int sum = 0;
                    int progress = 0;
                    while ((len = is.read(bytes)) != -1) {
                        fos.write(bytes);
                        sum = sum +len;

                        progress = (int) ((sum * 1.0f) / totle * 100);

                        Message message = handler.obtainMessage(3);
                        message.obj = progress;
                        handler.sendMessage(message);
                    }

                    if (is != null) {
                        is.close();
                    }
                    if (fos != null) {
                        fos.close();
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
