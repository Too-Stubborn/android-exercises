package com.xuhj.mvp.uiadapter.product;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuhj.mvp.R;
import com.xuhj.mvp.http.response.GetMovieResp;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author xuhj
 */
public class ProductListAdapter extends ArrayAdapter<GetMovieResp.SubjectsBean> {

    public ProductListAdapter(Context context, List<GetMovieResp.SubjectsBean> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ViewHolder holder = viewHolder;
        final GetMovieResp.SubjectsBean data = getItem(position);
        // *************************** 开始逻辑处理 ***************************

        holder.ivImage.setBackgroundColor(Color.GREEN);
        if (!TextUtils.isEmpty(data.getItemTitle()))
            holder.tvName.setText(data.getItemTitle());

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.iv_image)
        ImageView ivImage;
        @Bind(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
