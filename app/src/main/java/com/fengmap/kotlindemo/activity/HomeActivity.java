package com.fengmap.kotlindemo.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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
    private FirstFragment f1;
    private SecondFragment f2;
    private ThirdFragment f3;
    private FourthFragment f4;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        f1 = new FirstFragment();
        f2 = new SecondFragment();
        f3 = new ThirdFragment();
        f4 = new FourthFragment();

        fragments.add(f1);
        fragments.add(f2);
        fragments.add(f3);
        fragments.add(f4);

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);

        Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(this);

        initFragment(0);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                initFragment(0);
                break;
            case R.id.button2:
                initFragment(1);
                break;
            case R.id.button3:
                initFragment(2);
                break;
            case R.id.button4:
                initFragment(3);
                break;
        }
    }

    private void initFragment(int i) {
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

//        switch (i) {
//            case 1:
//                if (f == null) {
//                    f1 = new FirstFragment();
//                    transaction.add(R.id.fragment, f1);
//                    f = f1;
//                }
//                break;
//            case 2:
//                if (f == null) {
//                    f2 = new SecondFragment();
//                    transaction.add(R.id.fragment, f2);
//                    f = f2;
//                }
//                break;
//            case 3:
//                if (f == null) {
//                    f3 = new ThirdFragment();
//                    transaction.add(R.id.fragment, f3);
//                    f = f3;
//                }
//                break;
//            case 4:
//                if (f == null) {
//                    f4 = new FourthFragment();
//                    transaction.add(R.id.fragment, f4);
//                    f = f4;
//                }
//                break;
//        }
//        //隐藏所有fragment
//        hideFragment(i);
//        //显示需要显示的fragment
//        transaction.show(f);
//
//        //提交事务
//        transaction.commit();
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
