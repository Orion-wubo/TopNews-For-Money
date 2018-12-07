package com.fengmap.kotlindemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fengmap.kotlindemo.R;

/**
 * Created by bai on 2018/12/7.
 */

public class LoginActivity extends Activity implements View.OnClickListener{

    private EditText et_username;
    private EditText et_pwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = findViewById(R.id.et_username);
        et_pwd = findViewById(R.id.et_pwd);

        Button bt_login = (Button) findViewById(R.id.bt_login);
        Button bt_regesit = (Button) findViewById(R.id.bt_regesit);

        bt_login.setOnClickListener(this);
        bt_regesit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:

                break;
            case R.id.bt_regesit:
                Intent intent = new Intent(LoginActivity.this, RegisteActivity.class);
                LoginActivity.this.startActivity(intent);
                finish();
                break;
        }
    }
}
