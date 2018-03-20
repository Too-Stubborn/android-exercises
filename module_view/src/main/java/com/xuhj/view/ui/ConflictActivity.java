package com.xuhj.view.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.xuhj.library.util.AndroidUtils;
import com.xuhj.library.util.ToastUtils;
import com.xuhj.view.R;
import com.xuhj.view.view.HorizontalScrollViewEx;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConflictActivity extends AppCompatActivity {
    private static final String TAG = "ConflictActivity";
    @BindView(R.id.list_view1)
    ListView listView1;
    @BindView(R.id.list_view2)
    ListView listView2;
    @BindView(R.id.horizontal_scroll_view)
    HorizontalScrollViewEx horizontalScrollView;
    @BindView(R.id.activity_conflict)
    RelativeLayout activityConflict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conflict);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("内容位置：" + i);
        }
        listView1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.show(ConflictActivity.this, "" + position);
            }
        });
        listView2.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.show(ConflictActivity.this, "" + position);
            }
        });
    }

    @OnClick({R.id.horizontal_scroll_view, R.id.activity_conflict})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.horizontal_scroll_view:
                break;
            case R.id.activity_conflict:

                Log.d(TAG, "onClick: screenwidth=" + AndroidUtils.getDisplayMetrics(this).widthPixels
                        + "\nw,h=" + horizontalScrollView.getWidth() + "," + horizontalScrollView.getHeight());

                break;
        }
    }
}
