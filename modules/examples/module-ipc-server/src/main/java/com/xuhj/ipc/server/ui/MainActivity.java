package com.xuhj.ipc.server.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xuhj.ipc.server.R;
import com.xuhj.ipc.server.service.SocketService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.tv_text)
    TextView tvText;
    @Bind(R.id.btn_test)
    Button btnTest;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        startSocketService();

    }

    private void startSocketService() {
        Intent i = new Intent(MainActivity.this, SocketService.class);
        startService(i);
    }


    @OnClick({R.id.tv_text, R.id.btn_test})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_text:
                break;
            case R.id.btn_test:
                Toast.makeText(this, "没用，哈哈", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
