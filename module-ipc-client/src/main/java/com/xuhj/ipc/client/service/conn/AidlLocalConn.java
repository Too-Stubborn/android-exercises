package com.xuhj.ipc.client.service.conn;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.xuhj.ipc.client.IMyAidl;
import com.xuhj.ipc.client.model.User;


/**
 * 应用内部进程间通信
 *
 * @author xuhj
 */
public class AidlLocalConn implements ServiceConnection {
    private static final String TAG = "客户端：AidlLocalConn";

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.d(TAG, "onServiceConnected: ");
        /*
            AIDL
         */
        IMyAidl aidl = IMyAidl.Stub.asInterface(service);
        try {
            String content = "hello";
            Log.d(TAG, "onServiceConnected: saySth:" + content);
            aidl.saySth(content);

            User sendUser = new User(7, "nono");
            Log.d(TAG, "onServiceConnected: setUser:" + sendUser.toString());
            aidl.setUser(sendUser);

            User receiveUser = aidl.getUser();
            Log.d(TAG, "onServiceConnected: getUser:" + receiveUser.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.d(TAG, "onServiceDisconnected: ");
    }

}
