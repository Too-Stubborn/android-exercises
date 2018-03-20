package com.xuhj.mvp.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xuhj.mvp.ui.product.ProductListActivity;
import com.xuhj.mvp.ui.status.StatusActivity;
import com.xuhj.mvp.ui.user.LoginActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @author xuhj
 */
public class MainActivity extends ListActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> list = new ArrayList<>();
        list.add("登录");
        list.add("商品列表");
        list.add("多状态布局");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case 0:
                LoginActivity.newInstance(this);
                break;
            case 1:
                ProductListActivity.newInstance(this);
                break;
            case 2:
                StatusActivity.newInstance(this);
                break;
            default:
                break;
        }
    }
}
