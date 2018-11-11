package com.fengmap.kotlindemo.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fengmap.kotlindemo.R;
import com.fengmap.kotlindemo.fragment.FirstFragment;
import com.fengmap.kotlindemo.fragment.FourthFragment;
import com.fengmap.kotlindemo.fragment.SecondFragment;
import com.fengmap.kotlindemo.fragment.ThirdFragment;

import java.util.ArrayList;

/**
 * home
 * Created by bai on 2018/11/7.
 */

public class HomeActivity extends FragmentActivity implements View.OnClickListener {
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ImageButton bt_news;
    private ImageButton bt_date;
    private ImageButton bt_money;
    private ImageButton bt_star;
    private RelativeLayout rv_star;
    private RelativeLayout rv_news;
    private RelativeLayout rv_date;
    private RelativeLayout rv_money;

    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initFragment();

        initView();

        showFragment(0);

    }

    private void initFragment() {
        FirstFragment f1 = new FirstFragment();
        SecondFragment f2 = new SecondFragment();
        ThirdFragment f3 = new ThirdFragment();
        FourthFragment f4 = new FourthFragment();

        fragments.add(f1);
        fragments.add(f2);
        fragments.add(f3);
        fragments.add(f4);
    }

    private void initView() {
        rv_news = findViewById(R.id.rv_news);
        rv_date = findViewById(R.id.rv_date);
        rv_money = findViewById(R.id.rv_money);
        rv_star = findViewById(R.id.rv_star);

        bt_news = findViewById(R.id.button1);
        rv_news.setOnClickListener(this);

        bt_date = findViewById(R.id.button2);
        rv_date.setOnClickListener(this);

        bt_money = findViewById(R.id.button3);
        rv_money.setOnClickListener(this);

        bt_star = findViewById(R.id.button4);
        rv_star.setOnClickListener(this);

        bt_news.setSelected(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rv_news:
                showFragment(0);
                btSeletc(R.id.button1);
                break;
            case R.id.rv_date:
                showFragment(1);
                btSeletc(R.id.button2);
                break;
            case R.id.rv_money:
                showFragment(2);
                btSeletc(R.id.button3);
                break;
            case R.id.rv_star:
                showFragment(3);
                btSeletc(R.id.button4);
                break;
        }
    }

    private void btSeletc(int id) {
        switch (id) {
            case R.id.button1:
                bt_news.setSelected(true);
                bt_date.setSelected(false);
                bt_money.setSelected(false);
                bt_star.setSelected(false);
                break;
            case R.id.button2:
                bt_news.setSelected(false);
                bt_date.setSelected(true);
                bt_money.setSelected(false);
                bt_star.setSelected(false);
                break;
            case R.id.button3:
                bt_news.setSelected(false);
                bt_date.setSelected(false);
                bt_money.setSelected(true);
                bt_star.setSelected(false);
                break;
            case R.id.button4:
                bt_news.setSelected(false);
                bt_date.setSelected(false);
                bt_money.setSelected(false);
                bt_star.setSelected(true);
                break;
        }

    }

    private void showFragment(int i) {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragments.get(i).isAdded())
            transaction.add(R.id.fragment, fragments.get(i));

        for (int j = 0; j <fragments.size(); j++) {
            if (j != i) {
                transaction.hide(fragments.get(j));
            }
        }
        transaction.show(fragments.get(i));
        transaction.commit();
    }

    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
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
