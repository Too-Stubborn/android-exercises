package com.xuhj.jellybean.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import com.xuhj.jellybean.JBApplication;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * 设备属性工具类
 */
public class DeviceUtils {

    public static final String PREFS_DEVICE_ID = "android_device_id";
    public static UUID uuid;

    public static String getDeviceId() {
        Context context = JBApplication.getInstance();
        if (uuid == null) {
            synchronized (DeviceUtils.class) {
                if (uuid == null) {
                    final String id = PreferenceUtils.getString(PREFS_DEVICE_ID, null);
                    if (id != null) {
                        // Use the ids previously computed and stored in the prefs file
                        uuid = UUID.fromString(id);
                    } else {
                        final String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
                        // Use the Android ID unless it's broken, in which case fallback on deviceId,
                        // unless it's not available, then fallback on a random number which we store
                        // to a prefs file
                        try {
                            if (!"9774d56d682e549c".equals(androidId)) {
                                uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                            } else {
                                final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                                uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                            }
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                        // Write the value out to the prefs file
                        PreferenceUtils.putString(PREFS_DEVICE_ID, uuid.toString());
                    }
                }
            }
        }
        return PreferenceUtils.getString(PREFS_DEVICE_ID, "");
    }

    /**
     * 获取应用包信息
     *
     * @return
     */
    public static PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = JBApplication.getInstance().getPackageManager()
                    .getPackageInfo(JBApplication.getInstance().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return info;
    }

}
