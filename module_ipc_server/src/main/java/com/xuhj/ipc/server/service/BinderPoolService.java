package com.xuhj.ipc.server.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xuhj.ipc.server.IBinderPool;
import com.xuhj.ipc.server.aidl.SecurityProxyImpl;
import com.xuhj.ipc.server.aidl.UserProxyImpl;

/**
 * 描述
 *
 * @author xuhj
 */
public class BinderPoolService extends Service {
    private static final String TAG = "服务端：BinderPoolService";

    /**
     * 常量定义区
     */
    public static final int BINDER_CODE_USER = 1;
    public static final int BINDER_CODE_SECURITY = 2;

    private Binder mBinderPool = new IBinderPool.Stub() {
        @Override
        public IBinder findBinder(int binderCode) throws RemoteException {
            Log.d(TAG, "findBinder: binderCode=" + binderCode);
            IBinder binder = null;
            switch (binderCode) {
                case BINDER_CODE_USER:
                    binder = new UserProxyImpl();
                    break;
                case BINDER_CODE_SECURITY:
                    binder = new SecurityProxyImpl();
                    break;
            }
            return binder;
        }
    };

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return mBinderPool;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

}
