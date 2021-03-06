package com.xuhj.view.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> list = new ArrayList<>();
        list.add("滑动冲突示例1");
        list.add("滑动冲突示例2");
        list.add("滑动冲突示例3");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent();
        switch (position) {
            case 0:
                i.setClass(MainActivity.this, ConflictActivity.class);
                break;
            case 1:
                i.setClass(MainActivity.this, Conflict2Activity.class);
                break;
            case 2:
                i.setClass(MainActivity.this, Conflict3Activity.class);
                break;
            default:
                break;
        }
        startActivity(i);
    }

}
