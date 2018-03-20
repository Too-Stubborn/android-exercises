package com.xuhj.rxjava.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xuhj.rxjava.ui.rxjava.AsyncActivity;
import com.xuhj.rxjava.ui.rxjava.BlockingActivity;
import com.xuhj.rxjava.ui.rxjava.CombiningActivity;
import com.xuhj.rxjava.ui.rxjava.ConditionalActivity;
import com.xuhj.rxjava.ui.rxjava.ConnectActivity;
import com.xuhj.rxjava.ui.rxjava.ConvertActivity;
import com.xuhj.rxjava.ui.rxjava.CreatingActivity;
import com.xuhj.rxjava.ui.rxjava.ErrorHandlingActivity;
import com.xuhj.rxjava.ui.rxjava.FilteringActivity;
import com.xuhj.rxjava.ui.rxjava.MathermaticalActivity;
import com.xuhj.rxjava.ui.rxjava.StringActivity;
import com.xuhj.rxjava.ui.rxjava.TransformingActivity;
import com.xuhj.rxjava.ui.rxjava.UtilityActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * ----------Dragon be here!----------/
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * <p>
 * 描述
 *
 * @author xuhj
 */
public class MainActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> list = new ArrayList<>();
        list.add("Creating创建操作");
        list.add("Transforming变换操作");
        list.add("Filtering过滤操作");
        list.add("Combining结合操作");
        list.add("ErrorHandling错误处理");
        list.add("Utility辅助操作");
        list.add("Conditional条件和布尔操作");
        list.add("Mathermatical算数和聚合操作");
        list.add("Async异步操作");
        list.add("Connect连接操作");
        list.add("Convert转换操作");
        list.add("Blocking阻塞操作");
        list.add("String字符串操作");

        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent();
        Class clazz = null;
        switch (position) {
            case 0:
                clazz = CreatingActivity.class;
                break;
            case 1:
                clazz = TransformingActivity.class;
                break;
            case 2:
                clazz = FilteringActivity.class;
                break;
            case 3:
                clazz = CombiningActivity.class;
                break;
            case 4:
                clazz = ErrorHandlingActivity.class;
                break;
            case 5:
                clazz = UtilityActivity.class;
                break;
            case 6:
                clazz = ConditionalActivity.class;
                break;
            case 7:
                clazz = MathermaticalActivity.class;
                break;
            case 8:
                clazz = AsyncActivity.class;
                break;
            case 9:
                clazz = ConnectActivity.class;
                break;
            case 10:
                clazz = ConvertActivity.class;
                break;
            case 11:
                clazz = BlockingActivity.class;
                break;
            case 12:
                clazz = StringActivity.class;
                break;
            default:
                break;
        }
        i.setClass(MainActivity.this, clazz);
        startActivity(i);
    }
}
