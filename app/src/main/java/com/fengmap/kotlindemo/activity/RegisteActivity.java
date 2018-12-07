package com.fengmap.kotlindemo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fengmap.kotlindemo.R;
import com.fengmap.kotlindemo.fragment.FirstFragment;
import com.fengmap.kotlindemo.util.SPUtils;
import com.mob.cms.gui.CMSGUI;
import com.mob.cms.gui.themes.defaultt.DefaultTheme;

import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by bai on 2018/12/6.
 */

public class RegisteActivity extends Activity {
    private boolean ready;
    private String country = "86";
    private String phone;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                pb_regest.setVisibility(View.GONE);
                Toast.makeText(RegisteActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisteActivity.this, EditActivity.class);
                startActivity(intent);
                finish();

                SPUtils.put(RegisteActivity.this,"username",phone);
            }
        }
    };
    private int tag = 60;
    private Button bt_getcoord;
    Timer timer = new Timer();
    private ProgressBar pb_regest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regesit);
        registerSDK();

        pb_regest = (ProgressBar) findViewById(R.id.pb_regest);

        final EditText editText = findViewById(R.id.et_regesit);
        final EditText et_code = findViewById(R.id.et_code);


        bt_getcoord = findViewById(R.id.bt_getcood);
        bt_getcoord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phone = editText.getText().toString();
                // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
                if (!phone.isEmpty()) {
                    if (isMobileNO(phone)) {
                        SMSSDK.getVerificationCode(country, phone);
                        timer.schedule(task, 1000, 1000);       // timeTask
                    } else {
                        Toast.makeText(RegisteActivity.this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisteActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });

        findViewById(R.id.btzhuce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = et_code.getText().toString();
                SMSSDK.submitVerificationCode(country, phone, s);

//                // 先设置界面主题，不同的主题对应不同的界面风格
//                CMSGUI.setTheme(DefaultTheme.class);
//
//                // 如果您集成了UMSSDK，可以使用下面的代码进入CMSSDK
//                // CMSGUI.showNewsListPageWithUMSSDKUser(com.mob.ums.gui.themes.defaultt.DefaultTheme.class);
//
//                // 如果您拥有自己的用户系统，可以使用下面的代码进入CMSSDK
//                 CMSGUI.showNewsListPageWithCustomUser("uid-123abc", "nickname", "http://www.company.com/avatar-url");
//
//                // 如果您希望用户以“游客”的身份查看资讯和进行评论
////                CMSGUI.showNewsListPageWithAnonymousUser();
            }
        });
    }

    public boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        // "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tag--;
                    bt_getcoord.setText(tag+"");
                    bt_getcoord.setEnabled(false);
                    if (tag < 0) {
                        timer.cancel();
                        bt_getcoord.setText("重新获取");
                    }
                }
            });
        }
    };
    private void registerSDK() {
        EventHandler eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                new Handler(Looper.getMainLooper(), new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        int event = msg.arg1;
                        int result = msg.arg2;
                        Object data = msg.obj;
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 处理成功得到验证码的结果
                                // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                                Toast.makeText(RegisteActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                            } else {
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                            }
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 处理验证码验证通过的结果
                                // 提交验证码，其中的code表示验证码，如“1357”
                                // 注册成功
                                pb_regest.setVisibility(View.VISIBLE);
                                handler.sendEmptyMessageDelayed(1, 2000);
                            } else {
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                                Toast.makeText(RegisteActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();

                            }
                        }
                        // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                        return false;
                    }
                }).sendMessage(msg);
            }
        };
        // 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler);


    }

    protected void onDestroy() {
        if (ready) {
            // 销毁回调监听接口
            SMSSDK.unregisterAllEventHandler();
        }
        super.onDestroy();
    }
}
