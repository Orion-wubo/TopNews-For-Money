package com.fengmap.kotlindemo.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fengmap.kotlindemo.R;
import com.fengmap.kotlindemo.bean.MoneyInfo;
import com.fengmap.kotlindemo.bean.StarInfo;

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

public class FourthFragment extends Fragment implements View.OnClickListener {

    private ArrayList<TextView> stars;
    private ArrayList<TextView> times;
    private String time = "today";
    private String star = "水瓶座";
    private TextView all, work, love, money, qf, lucycolor, lucynum, summary, health, mima, shuoming, ganqing, jinqian, shiye;
    private ScrollView ll_noyear, ll_year;
    private StarInfo info;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                all.setText(info.getAll());
                work.setText(info.getWork());
                love.setText(info.getLove());
                money.setText(info.getMoney());
                qf.setText(info.getQFriend());
                lucycolor.setText(info.getColor());
                lucynum.setText(info.getNumber() + "");
                summary.setText(info.getSummary());
                health.setText(info.getHealth());

                all.setVisibility(View.VISIBLE);
                qf.setVisibility(View.VISIBLE);
                lucycolor.setVisibility(View.VISIBLE);
                lucynum.setVisibility(View.VISIBLE);
                summary.setVisibility(View.VISIBLE);
                health.setVisibility(View.VISIBLE);
            } else if (msg.what == 2) {
                work.setText(info.getWork());
                love.setText(info.getLove());
                money.setText(info.getMoney());
                qf.setText(info.getQFriend());

                all.setVisibility(View.GONE);
                qf.setVisibility(View.GONE);
                lucycolor.setVisibility(View.GONE);
                lucynum.setVisibility(View.GONE);
                summary.setVisibility(View.GONE);
                health.setVisibility(View.GONE);
            } else if (msg.what == 3) {
                work.setText(info.getWork());
                love.setText(info.getLove());
                money.setText(info.getMoney());
                qf.setText(info.getQFriend());
                all.setText(info.getAll());

                all.setVisibility(View.VISIBLE);
                qf.setVisibility(View.VISIBLE);
                lucycolor.setVisibility(View.GONE);
                lucynum.setVisibility(View.GONE);
                summary.setVisibility(View.GONE);
                health.setVisibility(View.GONE);
            } else if (msg.what == 4) {
                mima.setText(info.getInfo());
                shuoming.setText(info.getText());
                shiye.setText(info.getCareer());
                ganqing.setText(info.getLove());
                jinqian.setText(info.getCareerfinance());
            } else if (msg.what == 5) {
                Toast.makeText(FourthFragment.this.getContext(),"请求网络失败",Toast.LENGTH_LONG).show();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth, null);
        initView(view);

        getDate(star, time);
        return view;
    }

    private void initView(View view) {
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("星座运势");

        TextView byz = view.findViewById(R.id.star_byz);
        byz.setOnClickListener(this);
        TextView jnz = view.findViewById(R.id.star_jnz);
        jnz.setOnClickListener(this);
        TextView szz = view.findViewById(R.id.star_szz);
        szz.setOnClickListener(this);
        TextView jxz = view.findViewById(R.id.star_jxz);
        jxz.setOnClickListener(this);
        TextView shizz = view.findViewById(R.id.star_shizz);
        shizz.setOnClickListener(this);
        TextView cnz = view.findViewById(R.id.star_cnz);
        cnz.setOnClickListener(this);
        TextView tpz = view.findViewById(R.id.star_tpz);
        tpz.setOnClickListener(this);
        TextView txz = view.findViewById(R.id.star_txz);
        txz.setOnClickListener(this);
        TextView ssz = view.findViewById(R.id.star_ssz);
        ssz.setOnClickListener(this);
        TextView mjz = view.findViewById(R.id.star_mjz);
        mjz.setOnClickListener(this);
        TextView spz = view.findViewById(R.id.star_spz);
        spz.setOnClickListener(this);
        TextView syz = view.findViewById(R.id.star_syz);
        syz.setOnClickListener(this);

        stars = new ArrayList<>();
        stars.add(byz);
        stars.add(jnz);
        stars.add(szz);
        stars.add(jxz);
        stars.add(shizz);
        stars.add(cnz);
        stars.add(tpz);
        stars.add(txz);
        stars.add(ssz);
        stars.add(mjz);
        stars.add(spz);
        stars.add(syz);

        TextView today = view.findViewById(R.id.tv_today);
        today.setOnClickListener(this);

        TextView tomorrow = view.findViewById(R.id.tv_tomorrow);
        tomorrow.setOnClickListener(this);

        TextView week = view.findViewById(R.id.tv_week);
        week.setOnClickListener(this);

        TextView month = view.findViewById(R.id.tv_month);
        month.setOnClickListener(this);

        TextView year = view.findViewById(R.id.tv_year);
        year.setOnClickListener(this);

        times = new ArrayList<>();
        times.add(today);
        times.add(tomorrow);
        times.add(week);
        times.add(month);
        times.add(year);

        byz.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        today.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        star = byz.getText().toString();
        time = today.getText().toString();
        revertTime(time);

        all = view.findViewById(R.id.all);
        love = view.findViewById(R.id.love);
        work = view.findViewById(R.id.work);
        health = view.findViewById(R.id.health);
        money = view.findViewById(R.id.money);
        lucycolor = view.findViewById(R.id.lucycolor);
        lucynum = view.findViewById(R.id.lucynum);
        qf = view.findViewById(R.id.qf);
        summary = view.findViewById(R.id.summary);

        ll_noyear = view.findViewById(R.id.sv_noyear);
        ll_year = view.findViewById(R.id.sv_year);

        mima = view.findViewById(R.id.mima);
        shuoming = view.findViewById(R.id.info);
        shiye = view.findViewById(R.id.shiye);
        ganqing = view.findViewById(R.id.ganqing);
        jinqian = view.findViewById(R.id.jinqian);
    }

    private void revertTime(String s) {
        switch (s) {
            case "今天":
                time = "today";
                break;
            case "明天":
                time = "tomorrow";
                break;
            case "本周":
                time = "week";
                break;
            case "本月":
                time = "month";
                break;
            case "今年":
                time = "year";
                break;
        }
    }

    private void changeStar(int id) {
        for (TextView view : stars) {
            if (view.getId() == id) {
                view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                star = view.getText().toString();
            } else {
                view.setBackgroundColor(getResources().getColor(R.color.white));
            }
        }
        getDate(star, time);
    }

    private void changeTime(int id) {
        for (TextView view : times) {
            if (view.getId() == id) {
                view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                time = view.getText().toString();
            } else {
                view.setBackgroundColor(getResources().getColor(R.color.white));
            }
        }
        revertTime(time);
        getDate(star, time);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.star_byz:
                changeStar(view.getId());
                break;
            case R.id.star_jnz:
                changeStar(view.getId());
                break;
            case R.id.star_szz:
                changeStar(view.getId());
                break;
            case R.id.star_jxz:
                changeStar(view.getId());
                break;
            case R.id.star_shizz:
                changeStar(view.getId());
                break;
            case R.id.star_cnz:
                changeStar(view.getId());
                break;
            case R.id.star_tpz:
                changeStar(view.getId());
                break;
            case R.id.star_txz:
                changeStar(view.getId());
                break;
            case R.id.star_ssz:
                changeStar(view.getId());
                break;
            case R.id.star_mjz:
                changeStar(view.getId());
                break;
            case R.id.star_spz:
                changeStar(view.getId());
                break;
            case R.id.star_syz:
                changeStar(view.getId());
                break;

            case R.id.tv_today:
                ll_noyear.setVisibility(View.VISIBLE);
                ll_year.setVisibility(View.GONE);
                changeTime(view.getId());
                break;
            case R.id.tv_tomorrow:
                ll_noyear.setVisibility(View.VISIBLE);
                ll_year.setVisibility(View.GONE);
                changeTime(view.getId());
                break;
            case R.id.tv_week:
                ll_noyear.setVisibility(View.VISIBLE);
                ll_year.setVisibility(View.GONE);
                changeTime(view.getId());
                break;
            case R.id.tv_month:
                ll_noyear.setVisibility(View.VISIBLE);
                ll_year.setVisibility(View.GONE);
                changeTime(view.getId());
                break;
            case R.id.tv_year:
                ll_noyear.setVisibility(View.GONE);
                ll_year.setVisibility(View.VISIBLE);
                changeTime(view.getId());
                break;
        }
    }

    public void getDate(String star, final String time) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://web.juhe.cn:8080/constellation/getAll";

        RequestBody body = new FormBody.Builder()
                .add("key", "b923cb7adc3df8edb4260aaeb893dc97")
                .add("consName", star)
                .add("type", time)
                .build();

        Request request = new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessageDelayed(5, 0);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    try {
                        if (time.equals("today") || time.equals("tomorrow")) {
                            JSONObject jsonObject = new JSONObject(result);
                            info = new StarInfo();
                            info.setAll(jsonObject.getString("all"));
                            info.setColor(jsonObject.getString("color"));
                            info.setWork(jsonObject.getString("work"));
                            info.setLove(jsonObject.getString("love"));
                            info.setHealth(jsonObject.getString("health"));
                            info.setNumber(jsonObject.getInt("number"));
                            info.setQFriend(jsonObject.getString("QFriend"));
                            info.setSummary(jsonObject.getString("summary"));
                            info.setMoney(jsonObject.getString("money"));
                            Message message = handler.obtainMessage(1);
                            handler.sendMessage(message);
                        } else if (time.equals("week")) {
                            JSONObject jsonObject = new JSONObject(result);
                            info = new StarInfo();
                            info.setWork(jsonObject.getString("work"));
                            info.setLove(jsonObject.getString("love"));
                            info.setMoney(jsonObject.getString("money"));
                            Message message = handler.obtainMessage(2);
                            handler.sendMessage(message);
                        } else if (time.equals("month")) {
                            JSONObject jsonObject = new JSONObject(result);
                            info = new StarInfo();
                            info.setAll(jsonObject.getString("all"));
                            info.setWork(jsonObject.getString("work"));
                            info.setLove(jsonObject.getString("love"));
                            info.setMoney(jsonObject.getString("money"));
                            Message message = handler.obtainMessage(3);
                            handler.sendMessage(message);
                        } else {
                            JSONObject jsonObject = new JSONObject(result);
                            String mima = jsonObject.getString("mima");
                            JSONObject infoObj = new JSONObject(mima);
                            String textArray = infoObj.getString("text");
                            JSONArray jsonArray = new JSONArray(textArray);
                            String text = jsonArray.get(0).toString();


                            info = new StarInfo();
                            info.setInfo(infoObj.getString("info"));
                            info.setText(text);
                            info.setLove(jsonObject.getString("love").replace("[", "").replace("]", "").replace("\"", ""));
                            info.setCareer(jsonObject.getString("career").replace("[", "").replace("]", "").replace("\"", ""));
                            info.setCareerfinance(jsonObject.getString("finance").replace("[", "").replace("]", "").replace("\"", ""));
                            info.setHealth(jsonObject.getString("health").replace("[", "").replace("]", "").replace("\"", ""));
                            Message message = handler.obtainMessage(4);
                            handler.sendMessage(message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
