package com.xuhj.android.base.eventbus.sample.rxbus;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.xuhj.android.base.eventbus.R;
import com.xuhj.android.base.eventbus.RxBus;
import com.xuhj.android.base.eventbus.Subscribe;
import com.xuhj.android.base.eventbus.ThreadMode;
import com.xuhj.android.base.eventbus.sample.ObjectMessage;

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
public class RxBusReceiverActivity extends AppCompatActivity {

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    public static void newInstance(Context context) {
        Intent i = new Intent(context, RxBusReceiverActivity.class);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        ButterKnife.bind(this);
        RxBus.getInstance().register(this);

        tvMsg.setText("RxBus点击跳转新的页面");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister(this);
    }

    @Subscribe(code = 1, threadMode = ThreadMode.MAIN)
    public void onRxBusReceiver(ObjectMessage msg) {
        tvMsg.setText(msg.getMessage());
        Toast.makeText(this, "收到消息：" + msg.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.tv_msg})
    public void onClick() {
        RxBusSendActivity.newInstance(this);
    }
}
