package com.xuhj.rxjava.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.xuhj.rxjava.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.subjects.PublishSubject;

public class SubjectsActivity extends AppCompatActivity {

    @BindView(R.id.btn_rx_subjects)
    Button btnRxSubjects;
    @BindView(R.id.activity_subjects)
    RelativeLayout activitySubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        ButterKnife.bind(this);
    }

    private void rxSubjects() {
        PublishSubject bus = PublishSubject.create();
    }

    @OnClick({R.id.btn_rx_subjects})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_rx_subjects:
                break;
        }
    }
}
