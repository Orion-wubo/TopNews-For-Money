package com.fengmap.kotlindemo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fengmap.kotlindemo.R;

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
 *
 * Created by bai on 2018/11/14.
 */

public class UpdateActivity extends AppCompatActivity {

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 3) {
                // down show
                int progress = (int) msg.obj;
                Log.e("handle", progress + "");
                proB.setProgress(progress);

            }else if (msg.what==4){
                install(file);
            } else if (msg.what == 1) {
                goHome();
            }
        }
    };

    private OkHttpClient client;
    private long mExitTime;
    private ProgressBar proB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);



        proB = findViewById(R.id.pro);

        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/app-debug.apk";
//                    file = new File(path,"ttzq.apk");
        file = new File(path);
        if (file.exists()) {
            file.delete();
        }

//        install(new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/app-debug.apk"));

        client = new OkHttpClient();

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        downLoad(url);


    }

    // download apk
    private void downLoad(String url) {
        Request request = new Request.Builder().url(url).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessageDelayed(1, 2000);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        InputStream is = response.body().byteStream();

                        FileOutputStream fos = new FileOutputStream(file);

                        int totle = (int) response.body().contentLength();

                        Log.e("totle", totle + "");

                        byte[] bytes = new byte[1024];
                        int len = 0;
                        int sum = 0;
                        int progress = 0;
                        while ((len = is.read(bytes)) != -1) {
                            fos.write(bytes,0,len);  //在这里使用另一个重载，防止流写入的问题.
                            sum = sum + len;
                            Log.e("sum", sum + "");


                            progress = (int) ((sum * 1.0f) / totle * 100);

                            Log.e("progress", progress + "");

                            Message message = handler.obtainMessage(3);
                            message.obj = progress;
                            handler.sendMessage(message);
                        }

                        Message message = handler.obtainMessage(4);
                        message.obj = progress;
                        handler.sendMessage(message);

                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.flush();
                            fos.close();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    private File file;

    private void install(File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //判读版本是否在7.0以上
            Uri apkUri = FileProvider.getUriForFile(this, "com.fengmap.kotlindemo.fileprovider", file);//在AndroidManifest中的android:authorities值
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            startActivity(install);
        }else{
            //以前的启动方法
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(install);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "取消更新将无法使用本app", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
