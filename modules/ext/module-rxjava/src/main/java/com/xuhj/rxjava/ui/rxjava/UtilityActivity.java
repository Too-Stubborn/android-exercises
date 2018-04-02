package com.xuhj.rxjava.ui.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.xuhj.rxjava.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UtilityActivity extends AppCompatActivity {
    private static final String TAG = "RxJavaActivity";

    @BindView(R.id.edit_text)
    EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        ButterKnife.bind(this);
        
    }


    @OnClick({R.id.btn_rx_operator_set})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_rx_operator_set:
                break;
        }
    }

}
