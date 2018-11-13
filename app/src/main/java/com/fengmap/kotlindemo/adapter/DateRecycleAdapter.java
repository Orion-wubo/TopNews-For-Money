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
import com.bumptech.glide.request.RequestOptions;
import com.fengmap.kotlindemo.R;
import com.fengmap.kotlindemo.bean.DateInfo;
import com.fengmap.kotlindemo.bean.NewsInfo;

import java.util.ArrayList;

/**
 *
 * Created by bai on 2018/11/9.
 */

public class DateRecycleAdapter extends RecyclerView.Adapter<DateRecycleAdapter.ViewHolder> {

    private ArrayList<DateInfo> news;
    private final Context context;
    private OnItemClickListener listener;

    @SuppressLint("CheckResult")
    public DateRecycleAdapter(Context context, ArrayList<DateInfo> newsInfos) {
        this.context = context;
        this.news = newsInfos;
    }

    public void setData(ArrayList<DateInfo> newsInfos) {
        this.news = newsInfos;
        notifyDataSetChanged();
    }

    //② 创建ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView sname,symbol,per_nav,total_nav,yesterday_nav,nav_rate,nav_a,sg_states,nav_date,jjlx;


        private ViewHolder(View v) {
            super(v);
            sname = v.findViewById(R.id.sname);
            symbol = v.findViewById(R.id.symbol);
            per_nav = v.findViewById(R.id.per_nav);
            total_nav = v.findViewById(R.id.total_nav);
            yesterday_nav = v.findViewById(R.id.yesterday_nav);
            nav_rate = v.findViewById(R.id.nav_rate);
            nav_a = v.findViewById(R.id.nav_a);
            sg_states = v.findViewById(R.id.sg_states);
            nav_date = v.findViewById(R.id.nav_date);
            jjlx = v.findViewById(R.id.jjlx);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_date, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        DateInfo info = news.get(position);
        holder.jjlx.setText(info.getJjlx());
        holder.nav_a.setText(info.getNav_a());
        holder.nav_date.setText(info.getNav_date());
        holder.nav_rate.setText(info.getNav_rate());
        holder.per_nav.setText(info.getPer_nav());
        holder.sg_states.setText(info.getSg_states());
        holder.sname.setText(info.getSname());
        holder.symbol.setText(info.getSymbol());
        holder.total_nav.setText(info.getTotal_nav());
        holder.yesterday_nav.setText(info.getYesterday_nav());

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
