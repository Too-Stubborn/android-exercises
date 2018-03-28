package com.xuhj.jellybean.ui.imageview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.gjiazhe.panoramaimageview.PanoramaImageView;
import com.xuhj.jellybean.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class DynamicImageViewActivity extends AppCompatActivity {
    @BindView(R.id.image_view)
    PanoramaImageView imageView;
    private GyroscopeObserver gyroscopeObserver;

    public static void newInstance(Context context) {
        Intent i = new Intent(context, DynamicImageViewActivity.class);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_image_view);
        ButterKnife.bind(this);

        // Initialize GyroscopeObserver.
        gyroscopeObserver = new GyroscopeObserver();
        // Set the maximum radian the device should rotate to show image's bounds.
        // It should be set between 0 and π/2.
        // The default value is π/9.
        gyroscopeObserver.setMaxRotateRadian(Math.PI / 9);

        // Set GyroscopeObserver for PanoramaImageView.
        imageView.setGyroscopeObserver(gyroscopeObserver);

        Glide.with(this)
                .load(R.drawable.test_image01)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25)))
                .into(imageView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register GyroscopeObserver.
        gyroscopeObserver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister GyroscopeObserver.
        gyroscopeObserver.unregister();
    }
}
