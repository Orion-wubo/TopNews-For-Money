package com.fengmap.kotlindemo.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fengmap.kotlindemo.activity.DetailActivity;
import com.fengmap.kotlindemo.bean.NewsInfo;
import com.fengmap.kotlindemo.R;
import com.fengmap.kotlindemo.adapter.NewsRecycleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * news
 * Created by bai on 2018/11/9.
 */

public class FirstFragment extends Fragment {
    private ArrayList<NewsInfo> newsInfos = new ArrayList<>();
    private NewsRecycleAdapter recycleAdapter;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                recycleAdapter.setData(newsInfos);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, null);

        initView(view);
        getNews();

        return view;
    }

    private void getNews() {
        OkHttpClient client = new OkHttpClient();
        String url = "http://v.juhe.cn/toutiao/index?type=top";
        RequestBody body = new FormBody.Builder().add("key","a5f45eeab05824a9440ca143b2447528")
        .add("type","caijing").build();
        Request request = new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String result1 = jsonObject.getString("result");
                        JSONObject dataObj = new JSONObject(result1);
                        JSONArray data = dataObj.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject dataobj = new JSONObject(data.get(i).toString());
                            String title = dataobj.getString("title");
                            String date = dataobj.getString("date");
                            String url = dataobj.getString("url");
                            String thumbnail_pic_s = dataobj.getString("thumbnail_pic_s");


                            NewsInfo newsInfo = new NewsInfo();
                            newsInfo.setDate(date);
                            newsInfo.setUrl(url);
                            newsInfo.setTitle(title);
                            newsInfo.setThumbnail_pic_s(thumbnail_pic_s);

                            newsInfos.add(newsInfo);
                        }

                        Message message = handler.obtainMessage(1);
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initView(View view) {
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("头条新闻");
        RecyclerView recyclerView = view.findViewById(R.id.rv_first);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycleAdapter = new NewsRecycleAdapter(this.getContext(), newsInfos);
        //设置Adapter
        recyclerView.setAdapter(recycleAdapter);
        //设置分隔线
//        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recycleAdapter.setItemClickListener(new NewsRecycleAdapter.OnItemClickListener() {
            @Override
            public void click(View view, int position) {
                String url = newsInfos.get(position).getUrl();
                String title = newsInfos.get(position).getTitle();
                Intent intent = new Intent(FirstFragment.this.getContext(), DetailActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                FirstFragment.this.getContext().startActivity(intent);
            }
        });
    }

}
