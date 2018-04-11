package com.xuhj.android.base.eventbus.sample.eventbus;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.xuhj.android.base.eventbus.R;
import com.xuhj.android.base.eventbus.sample.ObjectMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2018/3/30
 */
public class EventBusReceiverActivity extends AppCompatActivity {

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    public static void newInstance(Context context) {
        Intent i = new Intent(context, EventBusReceiverActivity.class);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        tvMsg.setText("EventBus点击跳转新的页面");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusReceiver(ObjectMessage msg) {
        tvMsg.setText(msg.getMessage());
        Toast.makeText(this, "收到消息：" + msg.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.tv_msg})
    public void onClick() {
        EventBusSendActivity.newInstance(this);
    }
}
