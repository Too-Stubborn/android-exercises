package com.xuhj.android.imageloader.sample;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.xuhj.android.imageloader.R;
import com.xuhj.android.imageloader.sample.glide.GlideApp;
import com.xuhj.java.processor.GeneratedTemplate;
import com.xuhj.java.processor.Template;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Template

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


        GlideApp.with(this)
                .load("")
                .disallowHardwareConfig()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target, DataSource dataSource,
                                                   boolean isFirstResource) {
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(new ImageView(this));

        tvRetrofit.setText(new GeneratedTemplate().getMessage());
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
