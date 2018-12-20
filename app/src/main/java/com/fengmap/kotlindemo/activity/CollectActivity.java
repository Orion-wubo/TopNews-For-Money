package com.fengmap.kotlindemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.fengmap.kotlindemo.R;
import com.fengmap.kotlindemo.adapter.NewsRecycleAdapter;
import com.fengmap.kotlindemo.bean.NewsInfo;
import com.fengmap.kotlindemo.util.DBManager;

import java.util.ArrayList;

/**
 * Created by bai on 2018/12/7.
 */

public class CollectActivity extends Activity {
    private RecyclerView recyclerView;
    private NewsRecycleAdapter recycleAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);


        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_collect = findViewById(R.id.tv_collect);
        tv_title.setText("我的收藏");

        recyclerView = findViewById(R.id.rv_collect);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        final ArrayList<NewsInfo> list = (ArrayList) DBManager.getInstance(this).queryUserList();
        final ArrayList<NewsInfo> collect = new ArrayList<>();
        if (list != null && list.size() > 0) {
            tv_collect.setVisibility(View.GONE);

            for (int i = 0; i < list.size(); i++) {
                boolean isChoose = list.get(i).getIsChoose();
                if (isChoose) {
                    collect.add(list.get(i));
                }
            }

            if (collect.size() > 0) {
                recycleAdapter = new NewsRecycleAdapter(this, collect);
                //设置Adapter
                recyclerView.setAdapter(recycleAdapter);
                //设置分隔线
//        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
                //设置增加或删除条目的动画
                int resId = R.anim.layout_animation_fall_down;
                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
                recyclerView.setLayoutAnimation(animation);

                recycleAdapter.setItemClickListener(new NewsRecycleAdapter.OnItemClickListener() {
                    @Override
                    public void click(View view, int position) {
                        String url = collect.get(position).getUrl();
                        String title = collect.get(position).getTitle();
                        Intent intent = new Intent(CollectActivity.this, DetailActivity.class);
                        intent.putExtra("url", url);
                        intent.putExtra("title", title);
                        CollectActivity.this.startActivity(intent);
                    }
                });
            } else {
                tv_collect.setVisibility(View.VISIBLE);
            }
        } else {
            tv_collect.setVisibility(View.VISIBLE);
        }
    }
}
