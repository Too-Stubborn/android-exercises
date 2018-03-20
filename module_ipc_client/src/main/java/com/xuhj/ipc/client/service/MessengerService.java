package com.xuhj.ipc.client.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 描述
 *
 * @author xuhj
 */
public class MessengerService extends Service {

    private static final String TAG = "服务端：MessengerService";

    private MyHandler mHandler = new MyHandler();
    private Messenger mMessenger = new Messenger(mHandler);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return mMessenger.getBinder();
    }
    
    static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: 收到客户端的内容，并回复");
            Messenger replyMsger = msg.replyTo;
            Message replyMsg = new Message();
            try {
                replyMsger.send(replyMsg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
