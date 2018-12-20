package com.fengmap.kotlindemo.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fengmap.kotlindemo.R;
import com.fengmap.kotlindemo.activity.DetailActivity;
import com.fengmap.kotlindemo.adapter.GlideImageLoader;
import com.fengmap.kotlindemo.adapter.NewsRecycleAdapter;
import com.fengmap.kotlindemo.bean.NewsInfo;
import com.fengmap.kotlindemo.util.DBManager;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                pb_first.setVisibility(View.GONE);
                srl.setRefreshing(false);
                recycleAdapter.setData(newsInfos);
                runLayoutAnimation(recyclerView);
            } else if (msg.what == 2) {
                pb_first.setVisibility(View.GONE);
                Toast.makeText(FirstFragment.this.getContext(),"请求网络失败",Toast.LENGTH_LONG).show();
            }
        }
    };
    private RecyclerView recyclerView;
    private ProgressBar pb_first;
    private ArrayList<Integer> images = new ArrayList<>();
    private SwipeRefreshLayout srl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, null);

        initView(view);
        getNews();

        return view;
    }

    private void getNews() {
        newsInfos = (ArrayList<NewsInfo>) DBManager.getInstance(this.getContext()).queryUserList();
        if (newsInfos != null && newsInfos.size() > 0) {
            pb_first.setVisibility(View.GONE);
            srl.setRefreshing(false);
            recycleAdapter.setData(newsInfos);
            runLayoutAnimation(recyclerView);
        }
        OkHttpClient client = new OkHttpClient();
        String url = "http://v.juhe.cn/toutiao/index?type=top";
        RequestBody body = new FormBody.Builder().add("key","a5f45eeab05824a9440ca143b2447528")
        .add("type","top").build();
        Request request = new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {



            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessageDelayed(2, 0);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();

                    String thumbnail_pic_s3 = null;
                    String thumbnail_pic_s2 = null;
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String result1 = jsonObject.getString("result");
                        JSONObject dataObj = new JSONObject(result1);
                        JSONArray data = dataObj.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject dataobj = new JSONObject(data.get(i).toString());
                            String title = dataobj.getString("title");
                            String uniquekey = dataobj.getString("uniquekey");
                            String date = dataobj.getString("date");
                            String url = dataobj.getString("url");
                            String thumbnail_pic_s = dataobj.getString("thumbnail_pic_s");

                            if (dataobj.has("thumbnail_pic_s02")) {
                                thumbnail_pic_s2 = dataobj.getString("thumbnail_pic_s02");
                            }

                            if (dataobj.has("thumbnail_pic_s03")) {
                                thumbnail_pic_s3 = dataobj.getString("thumbnail_pic_s03");
                            }


                            NewsInfo newsInfo = new NewsInfo();
                            newsInfo.setDate(date);
                            newsInfo.setUrl(url);
                            newsInfo.setTitle(title);
                            newsInfo.setThumbnail_pic_s(thumbnail_pic_s);
                            newsInfo.setThumbnail_pic_s2(thumbnail_pic_s2);
                            newsInfo.setThumbnail_pic_s(thumbnail_pic_s3);

                            if (!newsInfos.contains(newsInfo)) {
                                newsInfos.add(newsInfo);
                            }

                            DBManager.getInstance(FirstFragment.this.getContext()).insertUser(newsInfo);
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

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    private void initView(View view) {
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("头条新闻");

        images.add(R.mipmap.iv_one);
        images.add(R.mipmap.iv_two);
        images.add(R.mipmap.iv_third);

        Banner banner = (Banner) view.findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        pb_first = (ProgressBar) view.findViewById(R.id.pb_first);

        recyclerView = view.findViewById(R.id.rv_first);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
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
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this.getContext(), resId);
        recyclerView.setLayoutAnimation(animation);

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

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                runLayoutAnimation(recyclerView);
//                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                if (lastCompletelyVisibleItemPosition == newsInfos.size() - 1) {
                    Toast.makeText(FirstFragment.this.getContext(),"没有更多数据",Toast.LENGTH_LONG).show();
                }
            }
        });


        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNews();
            }
        });
    }

}
