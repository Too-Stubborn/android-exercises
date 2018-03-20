// IMyAidl.aidl
package com.xuhj.ipc.client;

// Declare any non-default types here with import statements
import com.xuhj.ipc.client.model.User;

interface IMyAidl {

    void saySth(String content);

    User getUser();

    void setUser(in User user);
}
