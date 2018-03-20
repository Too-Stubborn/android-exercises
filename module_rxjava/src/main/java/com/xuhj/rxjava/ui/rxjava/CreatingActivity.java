package com.xuhj.rxjava.ui.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.xuhj.rxjava.R;
import com.xuhj.rxjava.uiadapter.StringAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class CreatingActivity extends AppCompatActivity {
    private static final String TAG = "CreatingActivity";

    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating);
        ButterKnife.bind(this);

        List<String> list = Arrays.asList(
                "just()  — 将一个或多个对象转换成发射这个或这些对象的一个Observable",
                "from( )  — 将一个Iterable, 一个Future, 或者一个数组转换成一个Observable",
                "repeat( )  — 创建一个重复发射指定数据或数据序列的Observable",
                "repeatWhen( )  — 创建一个重复发射指定数据或数据序列的Observable，它依赖于另一个Observable发射的数据",
                "create( )  — 使用一个函数从头创建一个Observable",
                "defer( )  — 只有当订阅者订阅才创建Observable；为每个订阅创建一个新的Observable",
                "range( )  — 创建一个发射指定范围的整数序列的Observable",
                "interval( )  — 创建一个按照给定的时间间隔发射整数序列的Observable",
                "timer( )  — 创建一个在给定的延时之后发射单个数据的Observable",
                "empty( )  — 创建一个什么都不做直接通知完成的Observable",
                "error( )  — 创建一个什么都不做直接通知错误的Observable",
                "never( )  — 创建一个不发射任何数据的Observable");
        listView.setAdapter(new StringAdapter(this, list));

        Arrays.sort(new Object[]{});

    }


    private void rxCreating() {
        Log.d(TAG, "--------------------- rxCreating ---------------------");
    }

    private void rxDefer() {
        Log.d(TAG, "--------------------- rxDefer ---------------------");
    }


    @OnItemClick({R.id.list_view})
    public void onItemClick(int pos) {
        switch (pos) {
            case 0:
                rxCreating();
                break;
            case 1:
                rxDefer();
                break;
        }
    }
}
