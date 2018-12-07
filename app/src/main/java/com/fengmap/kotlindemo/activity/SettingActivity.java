package com.fengmap.kotlindemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.fengmap.kotlindemo.R;
import com.fengmap.kotlindemo.util.SPUtils;

/**
 *
 * Created by bai on 2018/12/7.
 */

public class SettingActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        findViewById(R.id.bt_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = (String) SPUtils.get(SettingActivity.this, "username", "");
                if (!username.isEmpty()) {
                    SPUtils.remove(SettingActivity.this, "username");
                    finish();
                } else {
                    Toast.makeText(SettingActivity.this, "您尚未登录", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
