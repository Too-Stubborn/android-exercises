package com.xuhj.android.aq.bluetooth.sample;

import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.xuhj.android.aq.bluetooth.QGBluetoothUtils;
import com.xuhj.android.aq.bluetooth.R;
import com.xuhj.android.base.eventbus.RxBus;
import com.xuhj.android.base.eventbus.Subscribe;
import com.xuhj.android.base.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2018/4/9
 */
public class BluetoothActivity extends AppCompatActivity {
    private static final String TAG = "BluetoothActivity";

    public static final String DEVICE_UDID = "920834047409";
    public static final String DEVICE_CHANNEL = "dewin";
    public static final String DEVICE_SECRET = "XohZebcUf7EEoZCKGycMDPXT";

    public static final int CMD_LOCK = 0x01;  // 加锁
    public static final int CMD_UNLOCK = 0x02;  // 解锁
    public static final int CMD_POWER_ON = 0x11;  // 开电门
    public static final int CMD_POWER_OFF = 0x12;  // 关电门
    public static final int CMD_WARNING_SOUND = 0x21;  // 蜂鸣器响
    public static final int CMD_ACTIVE = 0x31;  // 激活
    public static final int CMD_BATTERY_LOCK = 0x41;  // 锁电池
    public static final int CMD_BATTERY_UNLOCK = 0x42;  // 解锁电池
    public static final int CMD_LOCK_EBIKE = 0x51;  // 闪骑锁车
    public static final int CMD_UNLOCK_EBIKE = 0x52;  // 闪骑解锁

    @BindView(R.id.btn_connect_to_device)
    Button btnConnectToDevice;
    @BindView(R.id.btn_lock)
    Button btnLock;
    @BindView(R.id.btn_unlock)
    Button btnUnlock;
    @BindView(R.id.tv_show_log)
    TextView tvShowLog;
    @BindView(R.id.btn_power_on)
    Button btnPowerOn;
    @BindView(R.id.btn_power_off)
    Button btnPowerOff;
    @BindView(R.id.btn_warning_sound)
    Button btnWarningSound;
    @BindView(R.id.btn_active)
    Button btnActive;
    @BindView(R.id.btn_battery_lock)
    Button btnBatteryLock;
    @BindView(R.id.btn_battery_unlock)
    Button btnBatteryUnlock;
    @BindView(R.id.btn_lock_ebike)
    Button btnLockEbike;
    @BindView(R.id.btn_unlock_ebike)
    Button btnUnlockEbike;
    @BindView(R.id.flex_actions)
    FlexboxLayout flexActions;


    public static void newInstance(Context context) {
        Intent i = new Intent(context, BluetoothActivity.class);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        ButterKnife.bind(this);

        RxBus.getInstance().register(this);
        QGBluetoothUtils.getInstance().initBluetooth(this);
        QGBluetoothUtils.getInstance().bindBluetoothService(this);
        QGBluetoothUtils.getInstance().registerBluetoothBroadcast(this);

        tvShowLog.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvShowLog.setText("");

    }

    private void searchDevice() {
        Observable
                .create(new ObservableOnSubscribe<ScanResult>() {
                    @Override
                    public void subscribe(final ObservableEmitter<ScanResult> e) throws Exception {
                        QGBluetoothUtils.getInstance()
                                .getBluetoothType(DEVICE_UDID, DEVICE_CHANNEL, DEVICE_SECRET,
                                        new QGBluetoothUtils.BluetoothDeviceCallback() {
                                            @Override
                                            public void onScanResult(ScanResult scanResult) {
                                                e.onNext(scanResult);
                                            }
                                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ScanResult>() {
                    @Override
                    public void accept(ScanResult scanResult) throws Exception {
                        Log.d(TAG, "onScanResult: " + scanResult.toString());
                    }
                });
    }

    private void sendCommon(int cmd) {
        QGBluetoothUtils.getInstance().writeBluetooth(cmd, DEVICE_UDID, DEVICE_SECRET, DEVICE_CHANNEL, this);
    }

    @Subscribe(code = QGBluetoothUtils.LOGGER_BUS_CODE, threadMode = ThreadMode.MAIN, isStickyEvent = false)
    public void receiverMethod(String msg) {
        tvShowLog.append("\n" + msg);
        tvShowLog.post(new Runnable() {
            @Override
            public void run() {
                int offset = tvShowLog.getLineCount() * tvShowLog.getLineHeight();
                if (offset > tvShowLog.getHeight()) {
                    tvShowLog.scrollTo(0, offset - tvShowLog.getHeight());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        RxBus.getInstance().unregister(this);
        QGBluetoothUtils.getInstance().unBindBluetoothService(this);
        QGBluetoothUtils.getInstance().unregisterBluetoothService(this);
        QGBluetoothUtils.getInstance().stopScan();
        QGBluetoothUtils.getInstance().closeDevice();
        super.onDestroy();
    }

    @OnClick({R.id.btn_connect_to_device, R.id.btn_lock, R.id.btn_unlock, R.id.btn_power_on,
            R.id.btn_power_off, R.id.btn_warning_sound, R.id.btn_active, R.id.btn_battery_lock,
            R.id.btn_battery_unlock, R.id.btn_lock_ebike, R.id.btn_unlock_ebike})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_connect_to_device:
                searchDevice();
                break;

            case R.id.btn_lock:
                sendCommon(CMD_LOCK);
                break;

            case R.id.btn_unlock:
                sendCommon(CMD_UNLOCK);
                break;

            case R.id.btn_power_on:
                sendCommon(CMD_POWER_ON);
                break;

            case R.id.btn_power_off:
                sendCommon(CMD_POWER_OFF);
                break;

            case R.id.btn_warning_sound:
                sendCommon(CMD_WARNING_SOUND);
                break;

            case R.id.btn_active:
                sendCommon(CMD_ACTIVE);
                break;

            case R.id.btn_battery_lock:
                sendCommon(CMD_BATTERY_LOCK);
                break;

            case R.id.btn_battery_unlock:
                sendCommon(CMD_BATTERY_UNLOCK);
                break;

            case R.id.btn_lock_ebike:
                sendCommon(CMD_LOCK_EBIKE);
                break;

            case R.id.btn_unlock_ebike:
                sendCommon(CMD_UNLOCK_EBIKE);
                break;
        }
    }

}
