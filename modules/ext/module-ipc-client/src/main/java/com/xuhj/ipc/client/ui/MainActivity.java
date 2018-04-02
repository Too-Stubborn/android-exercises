package com.xuhj.ipc.client.ui;

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
        list.add("IPC之AIDL");
        list.add("IPC之Messenger");
        list.add("IPC之Provider");
        list.add("IPC之BinderPool");
        list.add("IPC之Socket");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent();
        switch (position) {
            case 0:
            case 1:
            case 2:
            case 3:
                i.setClass(MainActivity.this, IPCActivity.class);
                break;
            case 4:
                i.setClass(MainActivity.this, ChatActivity.class);
                break;
            default:
                break;
        }
        startActivity(i);
    }

}
