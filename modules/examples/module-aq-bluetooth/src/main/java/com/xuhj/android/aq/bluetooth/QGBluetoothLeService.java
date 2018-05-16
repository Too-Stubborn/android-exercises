//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xuhj.android.aq.bluetooth;

import android.annotation.TargetApi;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;
import java.util.UUID;

@TargetApi(18)
public class QGBluetoothLeService extends Service {
    private static final String TAG = "BluetoothLeService";

    public static final String ACTION_GATT_CONNECTED = "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public static final String ACTION_GATT_DISCONNECTED = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public static final String ACTION_GATT_SERVICES_DISCOVERED = "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public static final String ACTION_DATA_AVAILABLE = "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public static final String EXTRA_DATA = "com.example.bluetooth.le.EXTRA_DATA";
    public static final String ACTION_GATT_SERVICE_RED = "com.example.bluetooth.le.ACTION_GATT_SERVICE_RED";
    public static final String ACTION_GATT_SERVICE_READ_ERROR = "ACTION_GATT_SERVICE_READ_ERROR";

    private IBinder iBinder = new LocalBinder();

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt;

    private int mConnectionState = 0;
    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    private BluetoothGattCharacteristic mCharacteristic;
    private int accessReadFrequency;
    /**
     * BluetoothGattCallback
     */
    BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            String intentAction;
            if (newState == 2) {
                intentAction = ACTION_GATT_CONNECTED;
                mConnectionState = 2;
                broadcastUpdate(intentAction);
                Log.i("BluetoothLeService", "Connected to GATT server.");
                Log.i("BluetoothLeService", "Attempting to start service discovery:" + mBluetoothGatt.discoverServices());
            } else if (newState == 0) {
                mBluetoothGatt.close();
                intentAction = ACTION_GATT_DISCONNECTED;
                mConnectionState = 0;
                Log.i("BluetoothLeService", "Disconnected from GATT server.");
                broadcastUpdate(intentAction);
            }

        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            if (status == 0) {
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                List<BluetoothGattService> serviceList = getSupportedGattServices();

                for (int i = 0; i < serviceList.size(); ++i) {
                    if (((BluetoothGattService) serviceList.get(i)).getUuid().toString().equals(SampleGattAttributes.DEVICE_SERVICE_UUID)) {
                        mCharacteristic = ((BluetoothGattService) serviceList.get(i)).getCharacteristic(UUID.fromString(SampleGattAttributes.DEVICE_CHARACTERISTIC_UUID));
                        Log.i("BluetoothLeService", "onReceive: ACTION_GATT_SERVICES_DISCOVERED111");
                    }
                }

                readCharacteristic(mCharacteristic);
            } else {
                Log.i("BluetoothLeService", "onServicesDiscovered received: " + status);
            }

        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == 0) {
                Log.e("BluetoothLeService", "onCharRead " + gatt.getDevice().getName() + " read " + characteristic.getUuid().toString() + " -> " + QGBluetoothLeService.byte2HexStr(characteristic.getValue()));
            }

            if (TextUtils.isEmpty(QGBluetoothLeService.byte2HexStr(characteristic.getValue()))) {
                (new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(1000L);
                            accessReadFrequency++;
                            if (accessReadFrequency >= 6) {
                                broadcastUpdate(ACTION_GATT_SERVICE_READ_ERROR, 3);
                                return;
                            }

                            readCharacteristic(mCharacteristic);
                        } catch (InterruptedException var2) {
                            var2.printStackTrace();
                        }

                    }
                })).start();
            } else {
                broadcastUpdate(ACTION_GATT_SERVICE_RED, characteristic);
            }

        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.i("BluetoothLeService", "onCharacteristicWrite: " + status);
            (new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000L);
                        accessReadFrequency = 0;
                        readCharacteristic(mCharacteristic);
                    } catch (InterruptedException var2) {
                        var2.printStackTrace();
                    }

                }
            })).start();
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            Log.i("BluetoothLeService", "onDescriptorWrite: " + status);
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            Log.i("BluetoothLeService", "onCharacteristicChanged: ");
        }
    };

    public QGBluetoothLeService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return this.iBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @TargetApi(18)
    public boolean initialize() {
        if (this.mBluetoothManager == null) {
            this.mBluetoothManager = (BluetoothManager) this.getSystemService(Context.BLUETOOTH_SERVICE);
            if (this.mBluetoothManager == null) {
                Log.i("BluetoothLeService", "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        this.mBluetoothAdapter = this.mBluetoothManager.getAdapter();
        if (this.mBluetoothAdapter == null) {
            Log.i("BluetoothLeService", "Unable to obtain a BluetoothAdapter.");
            return false;
        } else {
            return true;
        }
    }

    @TargetApi(18)
    public boolean connect(String address) {
        if (this.mBluetoothAdapter != null && address != null) {
            if (this.mBluetoothDeviceAddress != null && address.equals(this.mBluetoothDeviceAddress) && this.mBluetoothGatt != null) {
                Log.i("BluetoothLeService", "Trying to use an existing mBluetoothGatt for connection.");
                if (this.mBluetoothGatt.connect()) {
                    this.mConnectionState = 1;
                    return true;
                } else {
                    return false;
                }
            } else {
                BluetoothDevice device = this.mBluetoothAdapter.getRemoteDevice(address);
                if (device == null) {
                    Log.w("BluetoothLeService", "Device not found.  Unable to connect.");
                    return false;
                } else {
                    this.mBluetoothGatt = device.connectGatt(this, false, this.mGattCallback);
                    Log.i("BluetoothLeService", "Trying to create a new connection.");
                    this.mBluetoothDeviceAddress = address;
                    this.mConnectionState = 1;
                    return true;
                }
            }
        } else {
            Log.w("BluetoothLeService", "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }
    }

    public boolean newConnect(String address) {
        if (this.mBluetoothAdapter != null && address != null) {
            BluetoothDevice device = this.mBluetoothAdapter.getRemoteDevice(address);
            if (device == null) {
                Log.w("BluetoothLeService", "Device not found.  Unable to connect.~~~~~~~");
                return false;
            } else {
                this.mBluetoothGatt = device.connectGatt(this, false, this.mGattCallback);
                this.mBluetoothDeviceAddress = address;
                this.mConnectionState = 1;
                return true;
            }
        } else {
            Log.w("BluetoothLeService", "BluetoothAdapter not initialized or unspecified address.~~~~~~~");
            return false;
        }
    }

    private void broadcastUpdate(String action) {
        Intent intent = new Intent(action);
        this.sendBroadcast(intent);
    }

    private void broadcastUpdate(String action, BluetoothGattCharacteristic characteristic) {
        Intent intent = new Intent(action);
        intent.putExtra(EXTRA_DATA, characteristic.getValue());
        this.sendBroadcast(intent);
    }

    private void broadcastUpdate(String action, int errorStatus) {
        Intent intent = new Intent(action);
        intent.putExtra(EXTRA_DATA, errorStatus);
        this.sendBroadcast(intent);
    }

    public List<BluetoothGattService> getSupportedGattServices() {
        return this.mBluetoothGatt == null ? null : this.mBluetoothGatt.getServices();
    }

    public void closeDevice() {
        if (this.mBluetoothGatt != null) {
            this.mBluetoothGatt.close();
        }

    }

    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (this.mBluetoothAdapter != null && this.mBluetoothGatt != null) {
            this.mBluetoothGatt.readCharacteristic(characteristic);
        } else {
            Log.w("BluetoothLeService", "BluetoothAdapter not initialized");
        }
    }

    public void wirteCharacteristic(BluetoothGattCharacteristic characteristic, String md5Text) {
        if (this.mBluetoothAdapter != null && this.mBluetoothGatt != null) {
            List<BluetoothGattDescriptor> l = characteristic.getDescriptors();
            this.mCharacteristic = characteristic;
            characteristic.setWriteType(1);
            characteristic.setValue(bufferToHex(md5Text));
            this.mBluetoothGatt.writeCharacteristic(characteristic);
        } else {
            Log.w("BluetoothLeService", "BluetoothAdapter not initialized");
        }
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src != null && src.length > 0) {
            for (int i = 0; i < src.length; ++i) {
                int v = src[i] & 255;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }

                stringBuilder.append(hv);
            }

            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public static String byte2HexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");

        for (int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            sb.append(stmp.length() == 1 ? "0" + stmp : stmp);
            sb.append(" ");
        }

        return sb.toString().toUpperCase().trim();
    }

    public static byte[] getHexBytes(String message) {
        int len = message.length() / 2;
        char[] chars = message.toCharArray();
        String[] hexStr = new String[len];
        byte[] bytes = new byte[len];
        int i = 0;

        for (int j = 0; j < len; ++j) {
            hexStr[j] = "" + chars[i] + chars[i + 1];
            bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
            i += 2;
        }

        return bytes;
    }

    public static byte[] bufferToHex(String text) {
        byte[] arr = new byte[text.length() / 2];

        for (int i = 0; i < text.length() / 2; ++i) {
            String subStr = text.substring(i * 2, i * 2 + 2);
            int byteValue = Integer.valueOf(subStr, 16).intValue();
            arr[i] = (byte) byteValue;
        }

        return arr;
    }

    public class LocalBinder extends Binder {

        public LocalBinder() {
        }

        public QGBluetoothLeService getService() {
            return QGBluetoothLeService.this;
        }
    }
}
