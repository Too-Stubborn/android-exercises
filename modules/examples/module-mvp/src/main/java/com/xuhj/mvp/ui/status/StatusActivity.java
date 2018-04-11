package com.xuhj.mvp.ui.status;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.xuhj.library.view.statuslayout.StatusLayout;
import com.xuhj.mvp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatusActivity extends AppCompatActivity {

    @Bind(R.id.status_layout)
    StatusLayout statusLayout;
    @Bind(R.id.activity_status)
    RelativeLayout activityStatus;
    @Bind(R.id.btn_change_status)
    Button btnChangeStatus;
    private int mStatus = 0;

    public static void newInstance(Context context) {
        Intent i = new Intent(context, StatusActivity.class);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.status_layout, R.id.btn_change_status})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.status_layout:
                break;
            case R.id.btn_change_status:
                mStatus++;
                if (mStatus > 5) {
                    mStatus = 0;
                }
                switch (mStatus) {
                    case 0:
                        statusLayout.showContent();
                        break;
                    case 1:
                        statusLayout.showLoading();
                        break;
                    case 2:
                        statusLayout.showEmptyData();
                        break;
                    case 3:
                        statusLayout.showError();
                        break;
                    case 4:
                        statusLayout.showNetworkError();
                        break;
                }
                break;
        }
    }
}
