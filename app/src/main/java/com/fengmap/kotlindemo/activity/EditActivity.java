package com.fengmap.kotlindemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fengmap.kotlindemo.R;
import com.fengmap.kotlindemo.util.SPUtils;

/**
 *
 * Created by bai on 2018/12/7.
 */

public class EditActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final EditText et_newname = (EditText) findViewById(R.id.et_newname);
        final EditText et_newpwd = (EditText) findViewById(R.id.et_newpwd);

        findViewById(R.id.bt_confire).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newname = et_newname.getText().toString();
                String newpwd = et_newpwd.getText().toString();
                if (!newname.isEmpty() && !newpwd.isEmpty()) {
                    SPUtils.put(EditActivity.this, "username", newname);
                    SPUtils.put(EditActivity.this, "password", newpwd);

                    Toast.makeText(EditActivity.this, "设置成功", Toast.LENGTH_LONG).show();

                    finish();
                } else {
                    Toast.makeText(EditActivity.this, "用户名密码不能为空", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
