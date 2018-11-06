package com.fengmap.kotlindemo

import android.app.Application
import android.os.Environment
import com.fengmap.android.FMMapSDK

/**
 *
 * Created by bai on 2018/5/21.
 */
class MyApplication : Application() {
    override fun onCreate() {
        //初始化SDK
        FMMapSDK.init(this, Environment.getExternalStorageDirectory().getAbsolutePath()+"/kotlin/");
        super.onCreate()
    }
}