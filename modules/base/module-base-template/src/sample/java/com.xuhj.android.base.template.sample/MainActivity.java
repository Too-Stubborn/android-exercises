package com.xuhj.android.base.template.sample;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xuhj.android.base.eventbus.sample.eventbus.EventBusReceiverActivity;
import com.xuhj.android.base.eventbus.sample.rxbus.RxBusReceiverActivity;

import java.util.Arrays;
import java.util.List;

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2018/4/9
 */
public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> list = Arrays.asList("Function1", "Function2");
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case 0:
                break;
            case 1:
                break;

        }
    }
}
