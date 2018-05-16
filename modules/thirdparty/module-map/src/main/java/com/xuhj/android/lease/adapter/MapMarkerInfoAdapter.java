package com.xuhj.android.lease.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.xuhj.android.lease.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述
 *
 * @author xuhj <a href="mailto:1604983712@qq.com">Contact me.</a>
 * @version 1.0.0
 * @since 2018/4/20
 */
public class MapMarkerInfoAdapter implements AMap.InfoWindowAdapter {

    private Context mContext;
    private View mView;
    private ViewHolder mViewHolder;

    public MapMarkerInfoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void initView(final Marker marker) {
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.popup_map_marker_info, null);
            mViewHolder = new ViewHolder(mView);
        }
        mViewHolder.tvName.setText(marker.getTitle());
        mViewHolder.tvAddress.setText(marker.getSnippet());
        mViewHolder.btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marker.hideInfoWindow();
            }
        });
    }

    @Override
    public View getInfoWindow(final Marker marker) {
        initView(marker);
        return null;
    }

    /**
     * 此方法不能修改整个 InfoWindow 的背景和边框，无论自定义的样式是什么样，
     * SDK 都会在最外层添加一个默认的边框。
     */
    @Override
    public View getInfoContents(Marker marker) {
        initView(marker);
        return mView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.btn_go)
        Button btnGo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
