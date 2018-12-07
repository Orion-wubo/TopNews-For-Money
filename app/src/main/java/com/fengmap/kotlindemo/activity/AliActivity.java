package com.fengmap.kotlindemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.fengmap.kotlindemo.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by bai on 2018/11/20.
 */

public class AliActivity extends Activity {

    private EditText et_num;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ali);

        et_num = (EditText) findViewById(R.id.et_phone_num);

    }

    /**
     * AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","app_id","your private_key","json","GBK","alipay_public_key","RSA2");
     AlipayOpenAgentCreateRequest request = new AlipayOpenAgentCreateRequest();
     request.setBizContent("{" +
     "\"account\":\"test@alipay.com\"," +
     "\"contact_info\":{" +
     "\"contact_name\":\"张三\"," +
     "\"contact_mobile\":\"18866668888\"," +
     "\"contact_email\":\"zhangsan@alipy.com\"" +
     "    }," +
     "\"order_ticket\":\"00ee2d475f374ad097ee0f1ac223fX00\"" +
     "  }");
     AlipayOpenAgentCreateResponse response = alipayClient.execute(request);
     if(response.isSuccess()){
     System.out.println("调用成功");
     } else {
     System.out.println("调用失败");
     * @param view
     */

    public void check(View view) {
        String num = et_num.getText().toString();

        String url = "https://openapi.alipay.com/gateway.do";

        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder().add("app_id","a5f45eeab05824a9440ca143b2447528")
                .add("type","top").build();
        Request request = new Request.Builder().url(url).post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
