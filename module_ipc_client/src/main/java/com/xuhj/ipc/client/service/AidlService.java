package com.xuhj.ipc.client.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xuhj.ipc.client.IMyAidl;
import com.xuhj.ipc.client.model.User;

/**
 * 应用内部进程间通信
 *
 * @author xuhj
 */
public class AidlService extends Service {
    private static final String TAG = "服务端：AidlService";

    private Binder mBinder = new IMyAidl.Stub() {
        @Override
        public void saySth(String content) throws RemoteException {
            Log.d(TAG, "saySth: " + content);
        }

        @Override
        public User getUser() throws RemoteException {
            User user = new User(44, "you are sb");
            Log.d(TAG, "getUser: " + user.toString());
            return user;
        }

        @Override
        public void setUser(User user) throws RemoteException {
            Log.d(TAG, "setUser: " + user.toString());
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
