package com.xuhj.jellybean.uiadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * 示例adapter
 */
public class SampleAdapter extends ArrayAdapter<String> {

    private List<String> list;

    public SampleAdapter(Context context, List<String> list) {
        super(context, 0, list);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder = null;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_item_text1, parent, false);
//            viewHolder = new ViewHolder(convertView);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        final ViewHolder holder = viewHolder;
//        final String data = getItem(position);
        //-------------------------------逻辑处理---------------------------------
        return convertView;
    }

}
