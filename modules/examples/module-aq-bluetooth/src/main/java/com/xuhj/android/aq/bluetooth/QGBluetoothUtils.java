//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xuhj.android.aq.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.bluetooth.le.ScanSettings.Builder;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.xuhj.android.base.eventbus.RxBus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QGBluetoothUtils {

    private static final String TAG = "QGBluetoothUtils";
    private static final String LOGGER = "操作日志：";
    public static final int LOGGER_BUS_CODE = 1001;

    public static final String URL_HOST = "http://api.vipcare.com";
    public static final int NOT_CONNECTED = 0;
    public static final int CONNECTION_SUCCESS = 1;
    public static final int DISCONNECTED = 2;
    public static final int CONNECTION_SUCCESS_WRITE = 3;

    private static QGBluetoothUtils mInstance;

    Context mContext;

    private int mSerialNumber = -1;
    private BluetoothLeScanner mScanner;
    private ScanCallback mScanCallback;
    private List<ScanFilter> mBleScanFilters;
    private ScanSettings mScanSettings;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mCurrentDevice;
    private QGBluetoothLeService mBluetoothLeService;
    private BluetoothGattCharacteristic mCurrentCharacteristic;
    private int mBleStatus;
    private String mBluetoothResult;
    private boolean isFirstConnected = true;

    /**
     * ServiceConnection
     */
    ServiceConnection mServiceConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder service) {
            mBluetoothLeService = ((QGBluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.i("QGBluetoothUtils", "Unable to initialize Bluetooth");
            } else {
                if (mCurrentDevice != null) {
                    mBluetoothLeService.connect(mCurrentDevice.getAddress());
                }

            }
        }

        public void onServiceDisconnected(ComponentName name) {
        }
    };

    /**
     * BroadcastReceiver
     */
    BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @RequiresApi(api = 18)
        public void onReceive(Context context, Intent intent) {
            RxBus.getInstance().post(LOGGER_BUS_CODE, "接收到广播：" + intent.getAction());
            Logger.show(LOGGER + "接收到广播：" + intent.getAction());

            String action = intent.getAction();
            if (QGBluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mBleStatus = 1;
                isFirstConnected = true;
            } else if (QGBluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mBleStatus = 2;
                mSerialNumber = -1;
                if (mCurrentDevice != null) {
                    mBluetoothLeService.newConnect(mCurrentDevice.getAddress());
                }
            } else if (QGBluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                List<BluetoothGattService> serviceList = mBluetoothLeService.getSupportedGattServices();

                for (int i = 0; i < serviceList.size(); ++i) {
                    if (((BluetoothGattService) serviceList.get(i)).getUuid().toString().equals(SampleGattAttributes.DEVICE_SERVICE_UUID)) {
                        mCurrentCharacteristic = ((BluetoothGattService) serviceList.get(i)).getCharacteristic(UUID.fromString(SampleGattAttributes.DEVICE_CHARACTERISTIC_UUID));
                        mBleStatus = 3;
                        Log.i("QGBluetoothUtils", "onReceive: ACTION_GATT_SERVICES_DISCOVERED");
                        return;
                    }
                }
            } else if (QGBluetoothLeService.ACTION_GATT_SERVICE_RED.equals(action)) {
                byte[] data = intent.getByteArrayExtra(QGBluetoothLeService.EXTRA_DATA);
                String str = QGBluetoothLeService.byte2HexStr(data);
                if (!TextUtils.isEmpty(str)) {
                    mBluetoothResult = str.substring(str.length() - 1, str.length());
                }

                if (isFirstConnected) {
                    isFirstConnected = false;
                    String result = QGBluetoothLeService.bytesToHexString(data);
                    if (result.length() >= 6) {
                        String sub = result.substring(2, 6);
                        mSerialNumber = Integer.parseInt(sub, 16);
                    }
                }
            } else if (QGBluetoothLeService.ACTION_GATT_SERVICE_READ_ERROR.equals(action)) {
                int errorStatus = intent.getIntExtra(QGBluetoothLeService.EXTRA_DATA, 3);
                mBluetoothResult = errorStatus + "";
            }

        }
    };

    private QGBluetoothUtils() {
    }

    /**
     * 单例
     */
    public static synchronized QGBluetoothUtils getInstance() {
        return mInstance == null ? (mInstance = new QGBluetoothUtils()) : mInstance;
    }

    /**
     * 初始化蓝牙
     */
    @RequiresApi(api = 21)
    public void initBluetooth(Context context) {
        RxBus.getInstance().post(LOGGER_BUS_CODE, "初始化蓝牙配置");
        Log.d(LOGGER, "初始化蓝牙配置");
        this.mContext = context;
        this.mBluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        this.mBluetoothAdapter = this.mBluetoothManager.getAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            RxBus.getInstance().post(LOGGER_BUS_CODE, "打开蓝牙");
            Log.d(LOGGER, "打开蓝牙");
            mBluetoothAdapter.enable();
        }
        this.mBleScanFilters = new ArrayList();
        this.mScanSettings = new Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build();
        this.mScanner = this.mBluetoothAdapter.getBluetoothLeScanner();
    }

    public void bindBluetoothService(Context context) {
        RxBus.getInstance().post(LOGGER_BUS_CODE, "绑定蓝牙服务");
        Log.d(LOGGER, "绑定蓝牙服务");
        Intent gattServiceIntent = new Intent(context.getApplicationContext(), QGBluetoothLeService.class);
        context.getApplicationContext().bindService(gattServiceIntent, this.mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void registerBluetoothBroadcast(Context context) {
        RxBus.getInstance().post(LOGGER_BUS_CODE, "注册蓝牙广播");
        Log.d(LOGGER, "注册蓝牙广播");
        context.registerReceiver(this.mGattUpdateReceiver, this.makeGattUpdateIntentFilter());
    }

    public void unBindBluetoothService(Context context) {
        RxBus.getInstance().post(LOGGER_BUS_CODE, "解绑蓝牙服务");
        Log.d(LOGGER, "解绑蓝牙服务");
        context.getApplicationContext().unbindService(this.mServiceConnection);
    }

    public void unregisterBluetoothService(Context context) {
        RxBus.getInstance().post(LOGGER_BUS_CODE, "注销蓝牙广播");
        Log.d(LOGGER, "注销蓝牙广播");
        context.unregisterReceiver(this.mGattUpdateReceiver);
    }

    public void closeDevice() {
        RxBus.getInstance().post(LOGGER_BUS_CODE, "关闭蓝牙服务");
        Log.d(LOGGER, "关闭蓝牙服务");
        if (this.mBluetoothLeService != null) {
            this.mBluetoothLeService.closeDevice();
        }
    }

    /**
     * 获取蓝牙类型
     *
     * @param udid     udid
     * @param channel  channel
     * @param secret   secret
     * @param callback callback
     */
    @RequiresApi(api = 21)
    public void getBluetoothType(String udid, String channel, String secret, QGBluetoothUtils.BluetoothDeviceCallback callback) {
        RxBus.getInstance().post(LOGGER_BUS_CODE, "从服务器获取设备号");
        Log.d(LOGGER, "从服务器获取设备号");

        long timestamp = System.currentTimeMillis() / 1000L;
        StringBuffer sb = new StringBuffer();
        sb.append(channel);
        sb.append(timestamp);
        sb.append(udid);
        sb.append(secret);
        String sign = MD5Utils.getMD5String(sb.toString());
        Log.i("QGBluetoothUtils", "getBluetoothType: " + sign + "===" + sb.toString());
        String path = "http://api.vipcare.com/api/getBtId?timestamp=" + timestamp + "&sign=" + sign + "&udid=" + udid + "&channel=" + channel;
        Log.i("QGBluetoothUtils", "getBluetoothType: " + path);
        String result = "";

        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            Log.i("QGBluetoothUtils", "getBluetoothType: " + responseCode);
            if (responseCode == 200) {
                InputStream is = connection.getInputStream();

                BufferedReader in;
                String inputLine;
                for (in = new BufferedReader(new InputStreamReader(is));
                     (inputLine = in.readLine()) != null;
                     result = result + inputLine) {
                }
                in.close();

                Log.i("QGBluetoothUtils", "getBluetoothType: " + result);
                JSONObject jsonObject2 = new JSONObject(result);
                String btId = jsonObject2.getString("btId");

                RxBus.getInstance().post(LOGGER_BUS_CODE, "获取成功，设备号为：" + btId);
                Log.d(LOGGER, "获取成功，设备号 = " + btId.substring(4));
                if (!TextUtils.isEmpty(btId)) {
                    this.scanDevice(btId, callback);
                }

                Log.i("QGBluetoothUtils", "getBluetoothTypeI: " + btId);
            }
        } catch (MalformedURLException var19) {
            var19.printStackTrace();
        } catch (IOException var20) {
            var20.printStackTrace();
        } catch (JSONException var21) {
            var21.printStackTrace();
        }

    }

    @RequiresApi(api = 21)
    public void scanDevice(final String btId, final QGBluetoothUtils.BluetoothDeviceCallback callback) {
        if (this.mBluetoothAdapter.isEnabled() && this.mScanner != null) {
            if (this.mScanCallback != null) {
                this.mScanner.stopScan(this.mScanCallback);
            }

            Log.i("QGBluetoothUtils", "scanDevice: test");
            RxBus.getInstance().post(LOGGER_BUS_CODE, "开始扫描蓝牙设备...");
            Log.d(LOGGER, "开始扫描蓝牙设备...");
            this.mScanner.startScan(this.mBleScanFilters, this.mScanSettings,
                    this.mScanCallback = new ScanCallback() {
                        public void onScanResult(int callbackType, ScanResult result) {
                            super.onScanResult(callbackType, result);
                            if (result == null || result.getDevice() == null) {
                                RxBus.getInstance().post(LOGGER_BUS_CODE, "没有搜索到蓝牙设备");
                                Log.d(LOGGER, "没有搜索到蓝牙设备");
                                return;
                            }

                            Log.d(LOGGER, "蓝牙搜索结果：MAC = " + result.getDevice().getAddress() + ",设备名称 = " + result.getDevice().getName());
                            Log.i("QGBluetoothUtils", "BluetoothLeScanner onScanResult: " + result.getDevice().getAddress());

                            SparseArray<byte[]> sparseArray = result.getScanRecord().getManufacturerSpecificData();
                            if (sparseArray.size() > 0) {
                                String str = btId.substring(4);

                                Log.i("QGBluetoothUtils", "onScanResult11:str= " + str + "  sparseArray=" + MD5Utils.bytesToHex((byte[]) sparseArray.valueAt(0)));

                                if (str.equalsIgnoreCase(MD5Utils.bytesToHex((byte[]) sparseArray.valueAt(0)))) {

                                    Log.i("QGBluetoothUtils", "onScanResult: " + str);
                                    RxBus.getInstance().post(LOGGER_BUS_CODE, "匹配到对应设备");
                                    Log.d(LOGGER, "匹配到对应设备");

                                    // 获取当前搜索到的设备
                                    mCurrentDevice = result.getDevice();
                                    // 连接蓝牙
                                    if (mBluetoothLeService != null) {
                                        RxBus.getInstance().post(LOGGER_BUS_CODE, "开始连接设备");
                                        Log.d(LOGGER, "开始连接设备");
                                        mBluetoothLeService.connect(mCurrentDevice.getAddress());
                                    }
                                    // 回调结果
                                    callback.onScanResult(result);
                                    // 停止扫描
                                    RxBus.getInstance().post(LOGGER_BUS_CODE, "停止扫描");
                                    Log.d(LOGGER, "停止扫描");
                                    mScanner.stopScan(mScanCallback);
                                }
                            }
                        }
                    });
        }

    }

    public void writeBluetooth(final int instruction, final String udid, final String secret, final String channel, Context context) {
        this.mBluetoothResult = null;
        switch (this.mBleStatus) {
            case 0:
                RxBus.getInstance().post(LOGGER_BUS_CODE, "未连接到车辆，请靠近车辆后操作");
                Log.i("QGBluetoothUtils", "writeBluetooth: 未连接到车辆，请靠近车辆后操作");
                break;
            case 1:
                RxBus.getInstance().post(LOGGER_BUS_CODE, "正在连接中");
                Log.i("QGBluetoothUtils", "writeBluetooth: 正在连接中");
                break;
            case 2:
                RxBus.getInstance().post(LOGGER_BUS_CODE, "连接失败，尝试重新连接");
                Log.i("QGBluetoothUtils", "writeBluetooth: 连接失败，尝试重新连接");
                break;
            case 3:
                if (this.mSerialNumber == -1) {
                    RxBus.getInstance().post(LOGGER_BUS_CODE, "写入失败。原因：未初始化序列号");
                    Log.i("QGBluetoothUtils", "writeBluetooth: 写入失败。原因：未初始化序列号");
                    return;
                }

                RxBus.getInstance().post(LOGGER_BUS_CODE, "发送命令：" + Integer.toHexString(instruction));
                (new Thread(new Runnable() {
                    public void run() {
                        if (mBluetoothLeService != null && mCurrentCharacteristic != null) {
                            mSerialNumber++;
                            if (mSerialNumber >= 65536) {
                                mSerialNumber = 1;
                            }

                            long timestamp = System.currentTimeMillis() / 1000L;
                            StringBuffer sb = new StringBuffer();
                            sb.append(channel);
                            sb.append(instruction);
                            sb.append(mSerialNumber);
                            sb.append(timestamp);
                            sb.append(udid);
                            sb.append(secret);
                            String sign = MD5Utils.getMD5String(sb.toString());
                            String path = "http://api.vipcare.com/api/getBtEncode?timestamp=" + timestamp + "&sign=" + sign + "&cmd=" + instruction + "&snum=" + mSerialNumber + "&udid=" + udid + "&channel=" + channel;
                            String result = "";

                            try {
                                URL url = new URL(path);
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                connection.setConnectTimeout(5000);
                                connection.setRequestMethod("GET");
                                int responseCode = connection.getResponseCode();
                                Log.i("QGBluetoothUtils", "writeBluetooth: " + responseCode);
                                if (responseCode == 200) {
                                    InputStream is = connection.getInputStream();

                                    BufferedReader in;
                                    String inputLine;
                                    for (in = new BufferedReader(new InputStreamReader(is)); (inputLine = in.readLine()) != null; result = result + inputLine) {
                                        ;
                                    }

                                    in.close();
                                    JSONObject jsonObject2 = new JSONObject(result);
                                    String btencode = jsonObject2.getString("btencode");
                                    if (btencode != null) {
                                        Log.i("QGBluetoothUtils", "run: ＝ " + mSerialNumber);
                                        mBluetoothLeService.wirteCharacteristic(mCurrentCharacteristic, btencode);
                                    }
                                }
                            } catch (MalformedURLException var15) {
                                var15.printStackTrace();
                            } catch (ProtocolException var16) {
                                var16.printStackTrace();
                            } catch (IOException var17) {
                                var17.printStackTrace();
                            } catch (JSONException var18) {
                                var18.printStackTrace();
                            }
                        }
                    }
                })).start();
        }
    }

    @RequiresApi(api = 21)
    public void stopScan() {
        if (this.mBluetoothAdapter.isEnabled() && this.mScanner != null) {
            this.mScanner.stopScan(this.mScanCallback);
    }
    }

    @RequiresApi(api = 21)
    public void startScan() {
        if (this.mBluetoothAdapter.isEnabled() && this.mScanner != null) {
            this.mScanner.startScan(this.mBleScanFilters, this.mScanSettings, this.mScanCallback);
        }
    }

    public String getBluetoothResult() {
        return this.mBluetoothResult;
    }

    private IntentFilter makeGattUpdateIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(QGBluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(QGBluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(QGBluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(QGBluetoothLeService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(QGBluetoothLeService.ACTION_GATT_SERVICE_RED);
        intentFilter.addAction(QGBluetoothLeService.ACTION_GATT_SERVICE_READ_ERROR);
        return intentFilter;
    }

    public interface BluetoothDeviceCallback {
        void onScanResult(ScanResult var1);
    }
}
