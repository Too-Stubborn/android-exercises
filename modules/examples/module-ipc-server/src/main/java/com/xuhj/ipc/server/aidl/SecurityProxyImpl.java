package com.xuhj.ipc.server.aidl;

import android.os.RemoteException;
import android.util.Log;

import com.xuhj.ipc.server.ISecurityProxy;

public class SecurityProxyImpl extends ISecurityProxy.Stub {
    private static final String TAG = "SecurityProxyImpl";

    @Override
    public String encrypt(String content) throws RemoteException {
        Log.d(TAG, "encrypt: " + content);
        String result = "加密后的" + content;
        return result;
    }

    @Override
    public String decrypt(String content) throws RemoteException {
        Log.d(TAG, "decrypt: " + content);
        String result = "解密后的" + content;
        return result;
    }
}