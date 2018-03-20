package com.xuhj.ipc.client.service.conn;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.xuhj.ipc.server.define.OnUserUpdateListener;
import com.xuhj.ipc.server.model.User;

import java.util.List;


/**
 * 多应用进程间通信
 *
 * @author xuhj
 */
public class AidlConn implements ServiceConnection {
    private static final String TAG = "客户端：AidlConn";

    private com.xuhj.ipc.server.IUserProxy mIUserProxy;
    private OnUserUpdateListener mOnUserUpdateListener;

    Binder.DeathRecipient mDeathRecipient = new Binder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.d(TAG, "binderDied: ");
            // TODO: 2017/3/27 reconnection to server...
        }
    };

    public void unregister() {
        Log.d(TAG, "unregister: ");
        if (mIUserProxy != null && mOnUserUpdateListener != null) {
            try {
                mIUserProxy.unregisterListener(mOnUserUpdateListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.d(TAG, "onServiceConnected: ");
        /*
            AIDL
         */
        mIUserProxy = com.xuhj.ipc.server.IUserProxy.Stub.asInterface(service);
        try {
            service.linkToDeath(mDeathRecipient, 0);

            User sendUser = new User(77, "i am client");
            Log.d(TAG, "onServiceConnected: addUser:" + sendUser.toString());
            mIUserProxy.addUser(sendUser);

            List<User> receiveUser = mIUserProxy.getUserList();
            Log.d(TAG, "onServiceConnected: getUserList:" + receiveUser.toString());

            mOnUserUpdateListener = new OnUserUpdateListener.Stub() {
                @Override
                public void add(User user) throws RemoteException {
                    Log.d(TAG, "add: " + user.toString());
                }

                @Override
                public void delete(User user) throws RemoteException {
                    Log.d(TAG, "delete: " + user.toString());
                }

            };
            mIUserProxy.registerListener(mOnUserUpdateListener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.d(TAG, "onServiceDisconnected: ");
    }


}
