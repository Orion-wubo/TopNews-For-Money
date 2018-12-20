package com.fengmap.kotlindemo;

import android.app.Application;

import com.mob.MobSDK;

import cn.jpush.android.api.JPushInterface;
import cn.smssdk.SMSSDK;

/**
 * Created by bai on 2018/12/6.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }
}
