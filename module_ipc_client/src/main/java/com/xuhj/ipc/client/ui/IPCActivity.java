package com.xuhj.ipc.client.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xuhj.ipc.client.R;
import com.xuhj.ipc.client.service.AidlService;
import com.xuhj.ipc.client.service.MessengerService;
import com.xuhj.ipc.client.service.conn.AidlConn;
import com.xuhj.ipc.client.service.conn.AidlLocalConn;
import com.xuhj.ipc.client.service.conn.MessengerLocalConn;
import com.xuhj.ipc.client.util.BinderPool;
import com.xuhj.ipc.server.ISecurityProxy;
import com.xuhj.ipc.server.IUserProxy;
import com.xuhj.ipc.server.model.User;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IPCActivity extends AppCompatActivity {
    private static final String TAG = "IPCActivity";

    @Bind(R.id.tv_text)
    TextView tvText;
    @Bind(R.id.btn_test1)
    Button btnTest1;
    @Bind(R.id.btn_test2)
    Button btnTest2;
    @Bind(R.id.btn_test3)
    Button btnTest3;
    @Bind(R.id.btn_test4)
    Button btnTest4;

    private AidlConn mAidlConn;
    private AidlLocalConn mAidlLocalConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);
        ButterKnife.bind(this);

    }

    private void invokeIPCMessengerFromLocal() {
        Intent i = new Intent(IPCActivity.this, MessengerService.class);
        MessengerLocalConn mMessengerLocalConn = new MessengerLocalConn();
        bindService(i, mMessengerLocalConn, BIND_AUTO_CREATE);
    }

    private void invokeIPCAidlFromLocal() {
        if (mAidlLocalConn != null)
            return;
        Intent i = new Intent(IPCActivity.this, AidlService.class);
        mAidlLocalConn = new AidlLocalConn();
        bindService(i, mAidlLocalConn, BIND_AUTO_CREATE);
    }

    private void invokeIPCAidlFromServer() {
        if (mAidlConn != null)
            return;
        Intent i = new Intent();
        i.setAction("com.xuhj.action.ipc.server.AidlService");
        i.setPackage("com.xuhj.ipc.server");
        mAidlConn = new AidlConn();
        bindService(i, mAidlConn, BIND_AUTO_CREATE);
    }

    private void invokeIPCProviderFromServer() {
        // 指定provider的authorities属性
        Uri uri = Uri.parse("content://com.xuhj.ipc.server.UserProvider");
        getContentResolver().query(uri, null, null, null, null);
//        getContentResolver().insert(uri, null);
//        getContentResolver().delete(uri, null, null);
//        getContentResolver().update(uri, null, null, null);
    }

    private void invokeIPCBinderPoolFromServer() {
        IBinder iUserBinder = BinderPool.getInstance().findBinder(BinderPool.BINDER_CODE_USER);
        IUserProxy iUserProxy = IUserProxy.Stub.asInterface(iUserBinder);
        try {
            List<User> list = iUserProxy.getUserList();
            Log.d(TAG, "invoke iUserProxy.getUserList: " + list);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        IBinder iSecurityBinder = BinderPool.getInstance().findBinder(BinderPool.BINDER_CODE_SECURITY);
        ISecurityProxy iSecurityProxy = ISecurityProxy.Stub.asInterface(iSecurityBinder);
        try {
            String encryptStr = iSecurityProxy.encrypt("你好");
            Log.d(TAG, "invoke iSecurityProxy.encrypt: " + encryptStr);
            String decryptStr = iSecurityProxy.decrypt("你好");
            Log.d(TAG, "invoke iSecurityProxy.decrypt: " + decryptStr);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAidlLocalConn != null) {
            mAidlLocalConn = null;
        }
        if (mAidlConn != null) {
            mAidlConn.unregister();
            mAidlConn = null;
        }
    }

    @OnClick({R.id.btn_test1, R.id.btn_test2, R.id.btn_test3, R.id.btn_test4, R.id.btn_test5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_test1:
                invokeIPCMessengerFromLocal();
                break;
            case R.id.btn_test2:
                invokeIPCAidlFromLocal();
                break;
            case R.id.btn_test3:
                invokeIPCAidlFromServer();
                break;
            case R.id.btn_test4:
                invokeIPCProviderFromServer();
                break;
            case R.id.btn_test5:
                invokeIPCBinderPoolFromServer();
                break;
        }
    }
}
