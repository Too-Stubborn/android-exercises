package com.xuhj.ipc.server.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xuhj.ipc.server.IUserProxy;
import com.xuhj.ipc.server.define.OnUserUpdateListener;
import com.xuhj.ipc.server.model.User;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * 多应用进程间通信
 *
 * @author xuhj
 */
public class AidlService extends Service {
    private static final String TAG = "服务端：AidlService";

    private CopyOnWriteArrayList<User> mUserList = new CopyOnWriteArrayList<>();
    // FIXME: 2017/3/27 scheme 1
    //    private CopyOnWriteArrayList<OnUserUpdateListener> mListenerList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<OnUserUpdateListener> mListenerList = new RemoteCallbackList<>();

    private AtomicBoolean mIsStop = new AtomicBoolean(false);
    private ServiceRunner mServiceRunner = new ServiceRunner();

    private Binder mBinder = new IUserProxy.Stub() {

        @Override
        public List<User> getUserList() throws RemoteException {
            return mUserList;
        }

        @Override
        public void addUser(User user) throws RemoteException {
            AidlService.this.addUser(user);
        }

        @Override
        public void registerListener(OnUserUpdateListener listener) throws RemoteException {
            Log.d(TAG, "registerListener: ");
            // FIXME: 2017/3/27 scheme 1
//            if (!mListenerList.contains(listener)) {
//                mListenerList.add(listener);
//            } else {
//                Log.d(TAG, "registerListener: already exists.");
//            }
            mListenerList.register(listener);
        }

        @Override
        public void unregisterListener(OnUserUpdateListener listener) throws RemoteException {
            Log.d(TAG, "unregisterListener: ");
            mListenerList.unregister(listener);
            mIsStop.set(true);
            // FIXME: 2017/3/27 scheme 1
//            if (mListenerList.contains(listener)) {
//                mListenerList.remove(listener);
//            } else {
//                Log.d(TAG, "registerListener: not found.");
//            }
        }

    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: start a new thread");
        new Thread(mServiceRunner).start();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        mIsStop.set(true);
        super.onDestroy();
    }

    private void addUser(User user) throws RemoteException {
        Log.d(TAG, "addUser: " + user.toString());
        mUserList.add(user);
        // FIXME: 2017/3/27 scheme 1
//        for (OnUserUpdateListener l : mListenerList) {
//            l.add(user);
//        }
        int n = mListenerList.beginBroadcast();
        for (int i = 0; i < n; i++) {
            mListenerList.getBroadcastItem(i).add(user);
        }
        mListenerList.finishBroadcast();
    }

    class ServiceRunner implements Runnable {
        @Override
        public void run() {
            Log.d(TAG, "run: 循环每10秒钟执行一次addUser");
            while (!mIsStop.get()) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    int rd = new Random().nextInt(1000);
                    addUser(new User((long) rd, "I am No." + rd));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
            Log.d(TAG, "run: 结束循环addUser");
        }
    }
}
