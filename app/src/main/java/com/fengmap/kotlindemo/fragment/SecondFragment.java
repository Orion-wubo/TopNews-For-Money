package com.fengmap.kotlindemo.fragment;


import android.annotation.SuppressLint;
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

import com.fengmap.kotlindemo.R;
import com.fengmap.kotlindemo.adapter.DateRecycleAdapter;
import com.fengmap.kotlindemo.adapter.NewsRecycleAdapter;
import com.fengmap.kotlindemo.bean.DateInfo;
import com.fengmap.kotlindemo.bean.NewsInfo;

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
 * Created by bai on 2018/11/9.
 */

public class SecondFragment extends Fragment {

    private ArrayList<DateInfo> dateInfos = new ArrayList<>();
    private int index = 0;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                recycleAdapter.setData(dateInfos);
            }
        }
    };
    private DateRecycleAdapter recycleAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, null);
        initView(view);
        getNews(index);
        return view;
    }

    private void initView(View view) {
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("净值数据");

        RecyclerView recyclerView = view.findViewById(R.id.rv_second);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycleAdapter = new DateRecycleAdapter(this.getContext(), dateInfos);
        //设置Adapter
        recyclerView.setAdapter(recycleAdapter);
        //设置分隔线
//        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                if (lastCompletelyVisibleItemPosition == dateInfos.size() - 1) {
                    getNews(index);
                }
            }
        });
    }

    private void getNews(final int i) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://v.juhe.cn/jingzhi/query.php?page=1&pagesize=20&type=zhaiquan";
        RequestBody body = new FormBody.Builder()
                .add("key","02155575a1ebea40dee4347ae56fc32e")
                .add("page",i+"")
                .add("type","zhaiquan").build();
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
                        JSONArray data = jsonObject.getJSONArray("result");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject obj = new JSONObject(data.get(i).toString());
                            DateInfo info = new DateInfo();
                            info.setJjlx(obj.getString("jjlx"));
                            info.setSymbol(obj.getString("symbol"));
                            info.setSname(obj.getString("sname"));
                            info.setPer_nav(obj.getString("per_nav"));
                            info.setTotal_nav(obj.getString("total_nav"));
                            info.setYesterday_nav(obj.getString("yesterday_nav"));
                            info.setNav_rate(obj.getString("nav_rate"));
                            info.setNav_a(obj.getString("nav_a"));
                            info.setSg_states(obj.getString("sg_states"));
                            info.setNav_date(obj.getString("nav_date"));

                            dateInfos.add(info);
                        }

                        index++;

                        Message message = handler.obtainMessage(1);
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
