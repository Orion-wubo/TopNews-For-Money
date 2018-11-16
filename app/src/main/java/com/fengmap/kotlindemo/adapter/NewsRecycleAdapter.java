package com.fengmap.kotlindemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fengmap.kotlindemo.bean.NewsInfo;
import com.fengmap.kotlindemo.R;

import java.util.ArrayList;

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
        private final ImageView imageView1, imageView11, imageView22, imageView33;
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
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_news, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        NewsInfo newsInfo = news.get(position);


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
        } else {
            holder.title.setText(newsInfo.getTitle());
            holder.date.setText(newsInfo.getDate());
            Glide.with(context)
                    .load(newsInfo.getThumbnail_pic_s())
                    .placeholder(R.mipmap.rv_noload)
                    .into(holder.imageView1);
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
