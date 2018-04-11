package com.xuhj.android.base.eventbus.sample.rxbus;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.xuhj.android.base.eventbus.R;
import com.xuhj.android.base.eventbus.RxBus;
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
public class RxBusSendActivity extends AppCompatActivity {

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    public static void newInstance(Context context) {
        Intent i = new Intent(context, RxBusSendActivity.class);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        ButterKnife.bind(this);

        tvMsg.setText("RxBus点击发送消息");
    }

    @OnClick({R.id.tv_msg})
    public void onClick() {
        RxBus.getInstance().post(1, new ObjectMessage(1, "rxbus hahaha"));
    }

}
