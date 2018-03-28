package com.xuhj.video.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xuhj.video.demo1.MyDemo1Activity;
import com.xuhj.video.demo2.MyDemo2Activity;
import com.xuhj.video.demo3.MyDemo3Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @author xuhj
 */
public class MainActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> list = new ArrayList<>();
        list.add("我的示例一");
        list.add("我的示例二");
        list.add("调用系统视频拍摄");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case 0:
                MyDemo1Activity.newInstance(this);
                break;
            case 1:
                MyDemo2Activity.newInstance(this);
                break;
            case 2:
                MyDemo3Activity.newInstance(this);
                break;
            default:
                break;
        }
    }
}
