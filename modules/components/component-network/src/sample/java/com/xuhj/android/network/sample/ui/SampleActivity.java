package com.xuhj.android.network.sample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.xuhj.android.network.HttpFactory;
import com.xuhj.android.network.R;
import com.xuhj.android.network.sample.api.TestService;
import com.xuhj.android.network.subscriber.BaseSubscriber;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class SampleActivity extends AppCompatActivity {

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        ButterKnife.bind(this);

    }

    private void fetch() {
        TestService service = HttpFactory.newClient(this, "http://ip.taobao.com")
                .create(TestService.class);

        HttpFactory.enqueue(service.queryIP("115.200.238.40"), new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    tvMsg.setText(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                tvMsg.setText(e.getMessage());
            }

            @Override
            protected boolean isShowProgressDialog() {
                return true;
            }
        });
    }

    @OnClick(R.id.tv_msg)
    public void onViewClicked() {
        fetch();
    }
}
