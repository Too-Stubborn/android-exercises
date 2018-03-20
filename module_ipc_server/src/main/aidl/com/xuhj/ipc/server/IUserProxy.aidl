// IUserProxy.aidl
package com.xuhj.ipc.server;

// Declare any non-default types here with import statements
import com.xuhj.ipc.server.model.User;
import com.xuhj.ipc.server.define.OnUserUpdateListener;

interface IUserProxy {

    List<User> getUserList();

    void addUser(in User user);

    void registerListener(OnUserUpdateListener listener );

    void unregisterListener( OnUserUpdateListener listener );

}
