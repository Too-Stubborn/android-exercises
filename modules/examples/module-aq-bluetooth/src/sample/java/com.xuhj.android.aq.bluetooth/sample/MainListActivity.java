package com.xuhj.android.aq.bluetooth.sample;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2018/4/9
 */
public class MainListActivity extends ListActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> list = Arrays.asList("得威租赁蓝牙模块");
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case 0:
                BluetoothActivity.newInstance(this);
                break;
        }
    }
}
