package com.fengmap.kotlindemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fengmap.kotlindemo.activity.LoginActivity;
import com.fengmap.kotlindemo.bean.NewsInfo;
import com.fengmap.kotlindemo.R;
import com.fengmap.kotlindemo.fragment.UserFragment;
import com.fengmap.kotlindemo.util.DBManager;
import com.fengmap.kotlindemo.util.SPUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by bai on 2018/11/9.
 */

public class NewsRecycleAdapter extends RecyclerView.Adapter<NewsRecycleAdapter.ViewHolder> {

    private ArrayList<NewsInfo> news;
    private final Context context;
    private OnItemClickListener listener;

    @SuppressLint("CheckResult")
    public NewsRecycleAdapter(Context context, ArrayList<NewsInfo> newsInfos) {
        this.context = context;
        this.news = newsInfos;
    }

    public void setData(ArrayList<NewsInfo> newsInfos) {
        this.news = newsInfos;
        notifyDataSetChanged();
    }

    //② 创建ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title, title22;
        private final TextView date, date22;
        private final ImageView imageView1, imageView11, imageView22, imageView33, iv_collect, iv_collect22;
        private final LinearLayout newsItem, ll_images, ll_one;

        private ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.news_title);
            title22 = v.findViewById(R.id.news_title22);
            date = v.findViewById(R.id.news_date);
            date22 = v.findViewById(R.id.news_date22);
            imageView1 = v.findViewById(R.id.news_iv1);
            imageView11 = v.findViewById(R.id.news_iv11);
            imageView22 = v.findViewById(R.id.news_iv22);
            imageView33 = v.findViewById(R.id.news_iv33);
            newsItem = v.findViewById(R.id.news_item);
            ll_images = v.findViewById(R.id.ll_images);
            ll_one = v.findViewById(R.id.ll_one);

            iv_collect = v.findViewById(R.id.iv_collect);
            iv_collect22 = v.findViewById(R.id.iv_collect22);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_news, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final NewsInfo newsInfo = news.get(position);


        if (newsInfo.getThumbnail_pic_s2() != null) {
            holder.title22.setText(newsInfo.getTitle());
            holder.date22.setText(newsInfo.getDate());

            holder.ll_images.setVisibility(View.VISIBLE);
            holder.ll_one.setVisibility(View.GONE);


            Glide.with(context)
                    .load(newsInfo.getThumbnail_pic_s())
                    .placeholder(R.mipmap.rv_noload)
                    .into(holder.imageView11);
            Glide.with(context)
                    .load(newsInfo.getThumbnail_pic_s2())
                    .placeholder(R.mipmap.rv_noload)
                    .into(holder.imageView22);
            Glide.with(context)
                    .load(newsInfo.getThumbnail_pic_s3())
                    .placeholder(R.mipmap.rv_noload)
                    .into(holder.imageView33);

            if (newsInfo.getIsChoose()) {
                // 说明已经收藏
                holder.iv_collect22.setBackground(context.getResources().getDrawable(R.mipmap.collect));
            } else {
                holder.iv_collect22.setBackground(context.getResources().getDrawable(R.mipmap.no_collect));
            }

            holder.iv_collect22.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username1 = (String) SPUtils.get(context, "username", "");
                    if (username1.isEmpty()) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    } else {
                        boolean tag = newsInfo.getIsChoose();
                        if (tag) {
                            // 已经收藏，变成不收藏
                            holder.iv_collect22.setBackground(context.getResources().getDrawable(R.mipmap.no_collect));
                            newsInfo.setIsChoose(false);

                            DBManager.getInstance(context).updateUser(newsInfo);
                        } else {
                            holder.iv_collect22.setBackground(context.getResources().getDrawable(R.mipmap.collect));
                            newsInfo.setIsChoose(true);

                            DBManager.getInstance(context).updateUser(newsInfo);
                        }
                    }

                }
            });
        } else {
            holder.title.setText(newsInfo.getTitle());
            holder.date.setText(newsInfo.getDate());
            Glide.with(context)
                    .load(newsInfo.getThumbnail_pic_s())
                    .placeholder(R.mipmap.rv_noload)
                    .into(holder.imageView1);



            if (newsInfo.getIsChoose()) {
                // 说明已经收藏
                holder.iv_collect.setBackground(context.getResources().getDrawable(R.mipmap.collect));
            } else {
                holder.iv_collect.setBackground(context.getResources().getDrawable(R.mipmap.no_collect));
            }

            holder.iv_collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean tag = newsInfo.getIsChoose();
                    String username1 = (String) SPUtils.get(context, "username", "");
                    if (username1.isEmpty()) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    } else {
                        if (tag) {
                            // 已经收藏，变成不收藏
                            holder.iv_collect.setBackground(context.getResources().getDrawable(R.mipmap.no_collect));
                            DBManager.getInstance(context).updateUser(newsInfo);
                        } else {
                            holder.iv_collect.setBackground(context.getResources().getDrawable(R.mipmap.collect));
                            DBManager.getInstance(context).updateUser(newsInfo);
                        }
                    }

                }
            });




        }

        holder.newsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.click(view, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return news.size();
    }


    public interface OnItemClickListener {
        void click(View view, int position);
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
