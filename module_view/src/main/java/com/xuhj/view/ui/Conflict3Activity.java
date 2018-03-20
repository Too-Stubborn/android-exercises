package com.xuhj.view.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuhj.library.util.ToastUtils;
import com.xuhj.view.R;
import com.xuhj.view.view.ScrollViewEx3;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Conflict3Activity extends AppCompatActivity {
    private static final String TAG = "Conflict3Activity";
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.list_view1)
    ListView listView1;
    @BindView(R.id.tv_footer)
    TextView tvFooter;
    @BindView(R.id.scroll_view)
    ScrollViewEx3 scrollView;
    @BindView(R.id.activity_conflict)
    RelativeLayout activityConflict;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conflict3);
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
                ToastUtils.show(Conflict3Activity.this, "" + position);
            }
        });
        scrollView.setListView(listView1);
    }

}
