package com.xuhj.ipc.client.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xuhj.ipc.client.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "客户端：ChatActivity";

    @Bind(R.id.scroll_view)
    ScrollView scrollView;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.et_send_content)
    EditText etSendContent;
    @Bind(R.id.btn_send)
    Button btnSend;
    @Bind(R.id.activity_chat)
    LinearLayout activityChat;

    private Socket mSocket;
    private PrintWriter mPrintWriter;
    private MyHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        initData();

        tvContent.setText("正在连接服务器...");
        invokeIPCSocketFromServer();
        initSocketThread();
    }

    private void initData() {
        mHandler = new MyHandler(this);
    }

    private void initSocketThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                // 初始化连接socket
                Socket socket = null;
                while (socket == null) {
                    try {
                        Log.d(TAG, "开始连接服务器...");
                        socket = new Socket("localhost", 8001);
                        mSocket = socket;
                        mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                        mHandler.sendEmptyMessage(1);
                    } catch (IOException e) {
                        SystemClock.sleep(3000);
                        e.printStackTrace();
                    }
                }
                // 接收服务端的消息
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while (!ChatActivity.this.isFinishing()) {
                        String readStr = br.readLine();
                        if (readStr == null) {
                            Log.d(TAG, "断开连接...");
                            break;
                        }
                        String msg = readStr;
                        mHandler.obtainMessage(2, msg).sendToTarget();
                    }
                    mPrintWriter.close();
                    br.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void invokeIPCSocketFromServer() {
        Log.d(TAG, "启动服务...");
        Intent i = new Intent();
        i.setAction("com.xuhj.action.ipc.server.SocketService");
        i.setPackage("com.xuhj.ipc.server");
//        i.setComponent(new ComponentName("com.xuhj.ipc.server","com.xuhj.ipc.server.service.SocketService"));
        startService(i);
    }

    private void send() {
        String content = etSendContent.getText().toString().trim();
        if (TextUtils.isEmpty(content) || mPrintWriter == null)
            return;
        etSendContent.setText("");
        tvContent.append("\nyou: " + content);
        Log.d(TAG, "发送消息");
        mPrintWriter.println(content);
    }


    @OnClick({R.id.et_send_content, R.id.btn_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_send_content:
                break;
            case R.id.btn_send:
                send();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (mSocket != null) {
            try {
                Log.d(TAG, "主动断开连接...");
                mSocket.shutdownInput();
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    private static class MyHandler extends Handler {
        private ChatActivity activity;

        public MyHandler(ChatActivity activity) {
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d(TAG, "连接成功");
                    activity.tvContent.setText("连接成功");
                    break;
                case 2:
                    Log.d(TAG, "收到消息：" + msg.obj);
                    activity.tvContent.append("\nserver: " + msg.obj);
                    activity.tvContent.post(new Runnable() {
                        @Override
                        public void run() {
                            activity.scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                    break;
                case 3:
                    Log.d(TAG, "连接断开");
                    activity.tvContent.setText("连接断开");
                    break;
            }

        }
    }
}
