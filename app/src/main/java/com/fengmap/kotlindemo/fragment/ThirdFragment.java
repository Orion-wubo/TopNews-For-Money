package com.fengmap.kotlindemo.fragment;


import android.annotation.SuppressLint;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fengmap.kotlindemo.R;
import com.fengmap.kotlindemo.adapter.MoneyRecycleAdapter;
import com.fengmap.kotlindemo.bean.MoneyInfo;
import com.fengmap.kotlindemo.view.MultiLineRadioGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * Created by bai on 2018/11/9.
 */

public class ThirdFragment extends Fragment{

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                recycleAdapter.setData(moneyInfos);
            }
        }
    };
    private MoneyRecycleAdapter recycleAdapter;
    private ArrayList<MoneyInfo> moneyInfos = new ArrayList<>();
    private ArrayList<MoneyInfo> moneyInfos_gs = new ArrayList<>();
    private ArrayList<MoneyInfo> moneyInfos_zs = new ArrayList<>();
    private ArrayList<MoneyInfo> moneyInfos_js = new ArrayList<>();
    private ArrayList<MoneyInfo> moneyInfos_jt = new ArrayList<>();
    private ArrayList<MoneyInfo> moneyInfos_ny = new ArrayList<>();
    private MultiLineRadioGroup radioGroup1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, null);
        initView(view);
        getNews(3);
        return view;
    }

    private void initView(View view) {
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("货币汇率");

        RecyclerView recyclerView = view.findViewById(R.id.rv_money);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycleAdapter = new MoneyRecycleAdapter(this.getContext(), moneyInfos);
        //设置Adapter
        recyclerView.setAdapter(recycleAdapter);
        //设置分隔线
//        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        // bank
        radioGroup1 = view.findViewById(R.id.rg_bank);
        radioGroup1.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MultiLineRadioGroup group, int checkedId) {

            }
        });

    }

    private void getNews(int bank) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://web.juhe.cn:8080/finance/exchange/rmbquot";

//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("key", "16c165a40dfe6a83b6f5fcf135f0533c");
//            jsonObject.put("bank", "3");
//            // ...
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String content = jsonObject.toString();
//
//        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),content);

        RequestBody body = new FormBody.Builder()
                .add("key","16c165a40dfe6a83b6f5fcf135f0533c")
                .add("bank","3")
                .build();


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
                        JSONArray jsonArray = new JSONArray(result1);

                        JSONObject dataobj = new JSONObject(jsonArray.get(0).toString());
                        for (int i = 0; i < 21; i++) {
                            String d = dataobj.getString("data" + (i + 1));
                            JSONObject dobj = new JSONObject(d);
                            String bankConversionPri = dobj.getString("bankConversionPri");
                            String date = dobj.getString("date");
                            String fBuyPri = dobj.getString("fBuyPri");
                            String fSellPri = dobj.getString("fSellPri");
                            String mBuyPri = dobj.getString("mBuyPri");
                            String mSellPri = dobj.getString("mSellPri");
                            String name = dobj.getString("name");
                            String time = dobj.getString("time");


                            MoneyInfo moneyInfo = new MoneyInfo();
                            moneyInfo.setDate(date);
                            moneyInfo.setBankConversionPri(bankConversionPri);
                            moneyInfo.setFBuyPri(fBuyPri);
                            moneyInfo.setFSellPri(fSellPri);
                            moneyInfo.setMBuyPri(mBuyPri);
                            moneyInfo.setMSellPri(mSellPri);
                            moneyInfo.setName(name);
                            moneyInfo.setTime(time);

                            moneyInfos.add(moneyInfo);
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
}
