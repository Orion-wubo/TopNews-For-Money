package com.fengmap.kotlindemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fengmap.kotlindemo.R;
import com.fengmap.kotlindemo.activity.CollectActivity;
import com.fengmap.kotlindemo.activity.EditActivity;
import com.fengmap.kotlindemo.activity.LoginActivity;
import com.fengmap.kotlindemo.activity.SettingActivity;
import com.fengmap.kotlindemo.util.SPUtils;

/**
 *
 * Created by bai on 2018/12/6.
 */

public class UserFragment extends Fragment implements View.OnClickListener{
    private String phone;
    private String country = "86";
    private boolean ready;
    private TextView tv_user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String username = (String) SPUtils.get(this.getContext(), "username", "");
        if (!username.isEmpty()) {
            tv_user.setText(username);
        }
    }

    private void initView(View view) {

        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("个人中心");

        tv_user = view.findViewById(R.id.tv_user);
        LinearLayout ll_collect = view.findViewById(R.id.ll_shoucang);
        LinearLayout ll_update = view.findViewById(R.id.ll_update);
        LinearLayout ll_setting = view.findViewById(R.id.ll_setting);

        ll_collect.setOnClickListener(this);
        ll_update.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        tv_user.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_user:
                String username = (String) SPUtils.get(UserFragment.this.getContext(), "username", "");
                if (username.isEmpty()) {
                    Intent intent = new Intent(UserFragment.this.getContext(), LoginActivity.class);
                    UserFragment.this.startActivity(intent);
                } else {
                    Intent intent = new Intent(UserFragment.this.getContext(), EditActivity.class);
                    UserFragment.this.startActivity(intent);
                }
                break;
            case R.id.ll_shoucang:
                String username1 = (String) SPUtils.get(UserFragment.this.getContext(), "username", "");
                if (username1.isEmpty()) {
                    Intent intent = new Intent(UserFragment.this.getContext(), LoginActivity.class);
                    UserFragment.this.startActivity(intent);
                } else {
                    Intent intent2 = new Intent(UserFragment.this.getContext(),CollectActivity.class);
                    UserFragment.this.startActivity(intent2);
                }
                break;
            case R.id.ll_update:
                Toast.makeText(UserFragment.this.getContext(),"已经是最新版本", Toast.LENGTH_LONG).show();
                break;
            case R.id.ll_setting:
                Intent intent3 = new Intent(UserFragment.this.getContext(),SettingActivity.class);
                UserFragment.this.startActivity(intent3);
                break;
        }
    }


}
