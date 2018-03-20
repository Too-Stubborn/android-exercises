package com.xuhj.remoteviews;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends AppCompatActivity {

    @Bind(R.id.btn_notification)
    Button btnNotification;
    @Bind(R.id.activity_notification)
    LinearLayout activityNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
    }

    private void createNotification() {
        Notification nf = new NotificationCompat.Builder(this)
                .setContentText("setContentText")
                .setSubText("setSubText")
                .setSmallIcon(R.drawable.store_mall_directory)
                .setColor(Color.RED)
                .setProgress(100, 30, true)
                .setAutoCancel(true)
                .build();

        PendingIntent pIntent = PendingIntent.getActivity(this, 1, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager nfmn = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nfmn.notify(1, nf);
    }

    @OnClick({R.id.btn_notification, R.id.activity_notification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_notification:
                createNotification();
                break;
            case R.id.activity_notification:
                break;
        }
    }
}
