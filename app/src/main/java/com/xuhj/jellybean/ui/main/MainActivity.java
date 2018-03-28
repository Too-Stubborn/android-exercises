package com.xuhj.jellybean.ui.main;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xuhj.jellybean.ui.activity_stack.Test1Activity;
import com.xuhj.jellybean.ui.imageview.DynamicImageViewActivity;
import com.xuhj.jellybean.ui.materialdesign.StatusBarActivity;
import com.xuhj.jellybean.ui.recyclerview.RecyclerViewActivity;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * 首页
 */
public class MainActivity extends ListActivity {


    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> list = new ArrayList<>();
        list.add("Activity启动模式测试");
        list.add("RecyclerView的使用");
        list.add("vlayout的使用");
        list.add("MaterialDesign的使用");
        list.add("图片体感展示");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);

        Timber.d(getPackageManager().getSystemSharedLibraryNames().toString());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case 0:
                Intent intent = new Intent(this, Test1Activity.class);
                startActivity(intent);
                break;
            case 1:
                RecyclerViewActivity.newInstance(this);
                break;
            case 2:
                break;
            case 3:
                StatusBarActivity.newInstance(this);
                break;
            case 4:
                DynamicImageViewActivity.newInstance(this);
                break;
            default:
                break;
        }
    }
}
