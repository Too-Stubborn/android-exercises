//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xuhj.android.aq.bluetooth;

import java.util.HashMap;

public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();
    public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
    public static String DEVICE_SERVICE_UUID;
    public static String DEVICE_CHARACTERISTIC_UUID;
    public static String testServiceUuid;
    public static String testCharacteristicUuid;

    public SampleGattAttributes() {
    }

    public static String lookup(String uuid, String defaultName) {
        String name = (String)attributes.get(uuid);
        return name == null?defaultName:name;
    }

    static {
        attributes.put("0000180d-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
        DEVICE_SERVICE_UUID = "000028af-0000-1000-8000-00805f9b34fb";
        DEVICE_CHARACTERISTIC_UUID = "00002aa2-0000-1000-8000-00805f9b34fb";
        testServiceUuid = "0000180f-0000-1000-8000-00805f9b34fb";
        testCharacteristicUuid = "00002a19-0000-1000-8000-00805f9b34fb";
    }
}
