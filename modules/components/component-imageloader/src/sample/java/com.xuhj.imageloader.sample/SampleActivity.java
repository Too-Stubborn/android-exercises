package com.xuhj.imageloader.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.annotation.GlideExtension;
import com.xuhj.imageloader.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@GlideExtension
public class SampleActivity extends AppCompatActivity {

    @BindView(R.id.tv_retrofit)
    TextView tvRetrofit;
    @BindView(R.id.ll_content)
    LinearLayout llContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_retrofit, R.id.ll_content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_retrofit:
                tvRetrofit.setText("width:" + llContent.getHeight());
                break;
            case R.id.ll_content:
                break;
        }
    }
}
