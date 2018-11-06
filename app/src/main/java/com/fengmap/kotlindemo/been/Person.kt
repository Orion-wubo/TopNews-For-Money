package com.fengmap.kotlindemo.been

/**
 * 数据类，相当于config
 * Created by bai on 2018/4/23.
 */
data class Person(var name:String,var age:Int){
    // 静态模块
    companion object {
        private var NUM = 1
        private var AGE = 23
    }
}