package com.fengmap.kotlindemo.adapter;

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
 *
 * Created by bai on 2018/11/9.
 */

public class NewsRecycleAdapter extends RecyclerView.Adapter<NewsRecycleAdapter.ViewHolder> {

    private ArrayList<NewsInfo> news;
    private final Context context;
    private OnItemClickListener listener;

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
        public final TextView title;
        public final TextView date;
        public final ImageView imageView1;
        public final LinearLayout newsItem;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.news_title);
            date = (TextView) v.findViewById(R.id.news_date);
            imageView1 = v.findViewById(R.id.news_iv1);
            newsItem = v.findViewById(R.id.news_item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_news, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        NewsInfo newsInfo = news.get(position);
        holder.title.setText(newsInfo.getTitle());
        holder.date.setText(newsInfo.getDate());

        Glide.with(context)
                .load(newsInfo.getThumbnail_pic_s())
                .into(holder.imageView1);

        holder.newsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.click(view,position);
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

    public void setItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
