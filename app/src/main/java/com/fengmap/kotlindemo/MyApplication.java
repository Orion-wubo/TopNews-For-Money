package com.fengmap.kotlindemo;

import android.app.Application;

import com.mob.MobSDK;

import cn.smssdk.SMSSDK;

/**
 * Created by bai on 2018/12/6.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);
    }
}
