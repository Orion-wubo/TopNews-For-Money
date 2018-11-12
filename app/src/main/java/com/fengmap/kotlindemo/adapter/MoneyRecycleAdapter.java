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
import com.fengmap.kotlindemo.R;
import com.fengmap.kotlindemo.bean.MoneyInfo;
import com.fengmap.kotlindemo.bean.NewsInfo;

import java.util.ArrayList;

/**
 *
 * Created by bai on 2018/11/9.
 */

public class MoneyRecycleAdapter extends RecyclerView.Adapter<MoneyRecycleAdapter.ViewHolder> {

    private ArrayList<MoneyInfo> moneyInfos;
    private final Context context;
    private OnItemClickListener listener;

    public MoneyRecycleAdapter(Context context, ArrayList<MoneyInfo> newsInfos) {
        this.context = context;
        this.moneyInfos = newsInfos;
    }

    public void setData(ArrayList<MoneyInfo> newsInfos) {
        this.moneyInfos = newsInfos;
        notifyDataSetChanged();
    }

    //② 创建ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView money_tv_bank,money_tv_fb,money_tv_fs,money_tv_mb,money_tv_ms
                ,money_tv_name,money_tv_time;

        private ViewHolder(View v) {
            super(v);
            money_tv_bank = v.findViewById(R.id.money_tv_bank);
            money_tv_fb = v.findViewById(R.id.money_tv_fb);
            money_tv_fs = v.findViewById(R.id.money_tv_fs);
            money_tv_mb = v.findViewById(R.id.money_tv_mb);
            money_tv_ms = v.findViewById(R.id.money_tv_ms);
            money_tv_name = v.findViewById(R.id.money_tv_name);
            money_tv_time = v.findViewById(R.id.money_tv_time);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_money, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        MoneyInfo moneyInfo = moneyInfos.get(position);
        holder.money_tv_bank.setText(moneyInfo.getBankConversionPri());
        holder.money_tv_fb.setText(moneyInfo.getFBuyPri());
        holder.money_tv_fs.setText(moneyInfo.getFSellPri());
        holder.money_tv_mb.setText(moneyInfo.getMBuyPri());
        holder.money_tv_ms.setText(moneyInfo.getMSellPri());
        holder.money_tv_name.setText(moneyInfo.getName());
        holder.money_tv_time.setText(moneyInfo.getTime());
    }

    @Override
    public int getItemCount() {
        return moneyInfos.size();
    }


    public interface OnItemClickListener {
        void click(View view, int position);
    }

    public void setItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
