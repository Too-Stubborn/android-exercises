package com.xuhj.android.lease.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.xuhj.android.lease.R;
import com.xuhj.android.lease.adapter.MapMarkerInfoAdapter;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.btn_relocation)
    AppCompatImageButton btnRelocation;

    private AMap mMap;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar();
        initMap(savedInstanceState);
        initMapSettings();
        initMyLocation();
        initLocation();
        initListerner();
    }

    private void initToolbar() {
        setSupportActionBar(toolBar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }

    private void initMap(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        mMap = mapView.getMap();

        mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        // 自定义InfoWindow
        mMap.setInfoWindowAdapter(new MapMarkerInfoAdapter(this));
    }

    private void initMapSettings() {
        UiSettings settings = mMap.getUiSettings();
        // 设置缩放按钮是否可见。
        settings.setZoomControlsEnabled(true);
        // 设置定位按钮是否可见。
        settings.setMyLocationButtonEnabled(false);
        // 设置倾斜手势是否可用。
        settings.setTiltGesturesEnabled(false);
        // 设置旋转手势是否可用。
        settings.setRotateGesturesEnabled(false);
    }

    private void initMyLocation() {
        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        myLocationStyle.interval(2000);
        //设置定位蓝点的Style
        mMap.setMyLocationStyle(myLocationStyle);
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mMap.setMyLocationEnabled(true);
    }

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //解析定位结果
                        System.out.println(aMapLocation.toStr());
                    }
                }
            }
        });
        mLocationClient.startLocation();
    }

    private void initListerner() {
        // 地图长按事件
        mMap.setOnMapLongClickListener(new AMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLng) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("添加充电柜")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                addMarker(latLng);
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();
            }
        });
        // 标记图标点击事件
        mMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
//                Toast.makeText(MainActivity.this, marker.getTitle() + marker.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.hideInfoWindow();
            }
        });
    }

    private void searchPoi() {


    }

    private void addMarker(LatLng ll) {
        BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.drawable.ic_ev_station);
        MarkerOptions markerOptions = new MarkerOptions()
                .icon(bd)
                .position(ll)
                .title("充电柜")
                .snippet("编号：" + new Random().nextInt(10000)
                        + "\n" + "地址：XXXXXXXXXXXXXXX"
                        + "\n" + "维护人员：ABC");
        mMap.addMarker(markerOptions);
    }

    /**
     * 重新定位
     */
    private void relocation() {
        //启动定位
        if (mLocationClient == null) {
            Toast.makeText(this, "初始化失败", Toast.LENGTH_SHORT).show();
            return;
        }
        LatLng ll = new LatLng(mLocationClient.getLastKnownLocation().getLatitude(),
                mLocationClient.getLastKnownLocation().getLongitude());
        mMap.animateCamera(CameraUpdateFactory.changeLatLng(ll));
    }

    /**
     * 开始导航
     */
    private void startNavigation() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @OnClick({R.id.btn_relocation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_relocation:
                relocation();
                break;
        }
    }
}
