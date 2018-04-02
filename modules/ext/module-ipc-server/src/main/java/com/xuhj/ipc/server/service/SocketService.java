package com.xuhj.ipc.server.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * 描述
 *
 * @author xuhj
 */
public class SocketService extends Service {
    private static final String TAG = "服务端：SocketService";

    private String[] mReplyMsgs = new String[]{"你好！", "我不在...", "给你讲个笑话吧...", "你是SB吗", "不知道...", "再见！"};
    private boolean mIsStop = false;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: start a new thread");
        super.onCreate();
        new Thread(new SocketRunner()).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        mIsStop = true;
        super.onDestroy();
    }

    class SocketRunner implements Runnable {

        @Override
        public void run() {
            Log.d(TAG, "建立通信");
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8001);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            while (!mIsStop) {
                try {
                    final Socket client = serverSocket.accept();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Log.d(TAG, "run: current thread is " + Thread.currentThread());
                                acceptClientSocket(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void acceptClientSocket(Socket socket) throws IOException {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            Log.d(TAG, "回复消息：有新成员加入聊天室");
            out.println("有新成员加入聊天室");
            while (!mIsStop) {
                String readStr = in.readLine();
                if (readStr == null) {
                    Log.d(TAG, "断开连接...");
                    break;
                }
                int random = new Random().nextInt(mReplyMsgs.length);
                String replyStr = mReplyMsgs[random];
                Log.d(TAG, "回复消息:：" + replyStr);
                out.println(replyStr);
            }
            out.close();
            in.close();
            socket.close();
        }
    }
}
