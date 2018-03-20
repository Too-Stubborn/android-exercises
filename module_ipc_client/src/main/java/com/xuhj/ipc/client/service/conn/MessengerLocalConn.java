package com.xuhj.ipc.client.service.conn;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;


/**
 * 应用内部进程间通信
 *
 * @author xuhj
 */
public class MessengerLocalConn implements ServiceConnection {
    private static final String TAG = "客户端：MessengerLocalConn";

    private MyHandler mHandler = new MyHandler();
    private Messenger mReplyMessenger = new Messenger(mHandler);

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.d(TAG, "onServiceConnected: ");
        /*
            Messenger
         */
        Messenger msger = new Messenger(service);
        Message msg = Message.obtain();
        msg.replyTo = mReplyMessenger;
        try {
            msger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.d(TAG, "onServiceDisconnected: ");
    }

    static class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: 收到服务器的回复");
        }
    }
}
