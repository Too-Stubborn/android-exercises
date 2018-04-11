package com.xuhj.ipc.client.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.xuhj.ipc.server.IBinderPool;

import java.util.concurrent.CountDownLatch;

/**
 * Binder连接池
 *
 * @author xuhj
 */
public class BinderPool {
    private static final String TAG = "连接池：BinderPool";

    /**
     * 常量定义区，必须与服务端处理Code一样
     */
    public static final int BINDER_CODE_USER = 1;
    public static final int BINDER_CODE_SECURITY = 2;

    /**
     * 单例模式
     *
     * @param context
     * @return
     */
    private static BinderPool sInstance;

    private BinderPool() {
    }

    /**
     * 获取实例,并加锁同步
     *
     * @return
     */
    public static BinderPool getInstance() {
        if (sInstance == null) {
            synchronized (BinderPool.class) {
                if (sInstance == null) {
                    sInstance = new BinderPool();
                }
            }
        }
        return sInstance;
    }

    /**
     * 变量定义区
     */
    // 传入上下文
    private Context mContext;
    // BinderPool接口定义
    private IBinderPool mIBinderPool;
    // 服务连接管理
    private BinderPoolConn mBinderPoolConn = new BinderPoolConn();
    // 同步辅助
    private CountDownLatch mBinderPoolCountDownLatch;
    // Binder销毁监听
    private BinderPoolDeathRecipient mBinderPoolDeathRecipient = new BinderPoolDeathRecipient();

    public void init(Context context) {
        this.mContext = context;
        invokeBinderPoolService();
    }

    /**
     * 开启Binder连接池
     * <p>
     * 注意：因为使用了CountDownLatch对bindService这一异步操作变为同步，所以在调用的时候不能在主线程中操作
     */
    private synchronized void invokeBinderPoolService() {
        Log.d(TAG, "invokeBinderPoolService: ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 线程同步辅助类
                mBinderPoolCountDownLatch = new CountDownLatch(1);

                Intent i = new Intent();
                i.setAction("com.xuhj.action.ipc.server.BinderPoolService");
                i.setPackage("com.xuhj.ipc.server");
                mContext.bindService(i, mBinderPoolConn, Context.BIND_AUTO_CREATE);

                try {
                    // 等待，阻塞方法
                    mBinderPoolCountDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 找到对应code的IBinder
     * <p>
     * 注意：需要等BinderPoolConn连接成功后才可以处理，不然mIBinderPool==null
     *
     * @param binderCode
     * @return
     * @see BinderPoolConn
     */
    public IBinder findBinder(int binderCode) {
        IBinder binder = null;
        try {
            if (mIBinderPool != null) {
                binder = mIBinderPool.findBinder(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }


    /**
     * 服务连接管理
     */
    private class BinderPoolConn implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            mIBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                mIBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mBinderPoolCountDownLatch.countDown();
            if (onServiceConnectedListener != null) {
                onServiceConnectedListener.connected();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    }

    /**
     * Binder销毁监听
     */
    private class BinderPoolDeathRecipient implements IBinder.DeathRecipient {
        @Override
        public void binderDied() {
            Log.d(TAG, "binderDied: ");
            mIBinderPool = null;
            invokeBinderPoolService();
        }
    }

    private OnServiceConnectedListener onServiceConnectedListener;

    public void setOnServiceConnectedListener(OnServiceConnectedListener onServiceConnectedListener) {
        this.onServiceConnectedListener = onServiceConnectedListener;
    }

    public interface OnServiceConnectedListener {

        void connected();

    }

}
