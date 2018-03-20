package com.xuhj.ipc.server.aidl;

import android.os.RemoteException;
import android.util.Log;

import com.xuhj.ipc.server.IUserProxy;
import com.xuhj.ipc.server.define.OnUserUpdateListener;
import com.xuhj.ipc.server.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserProxyImpl extends IUserProxy.Stub {
    private static final String TAG = "UserProxyImpl";

    @Override
    public List<User> getUserList() throws RemoteException {
        Log.d(TAG, "getUserList: ");
        List<User> list = new ArrayList<>();
        list.add(new User(1, "My name is No.1"));
        list.add(new User(2, "My name is No.2"));
        list.add(new User(3, "My name is No.3"));
        return list;
    }

    @Override
    public void addUser(User user) throws RemoteException {
        Log.d(TAG, "addUser: " + user.toString());
    }

    @Override
    public void registerListener(OnUserUpdateListener listener) throws RemoteException {
        Log.d(TAG, "registerListener: ");
    }

    @Override
    public void unregisterListener(OnUserUpdateListener listener) throws RemoteException {
        Log.d(TAG, "unregisterListener: ");
    }
}