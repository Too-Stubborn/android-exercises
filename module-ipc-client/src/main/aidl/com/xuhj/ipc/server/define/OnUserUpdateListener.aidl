// OnUserUpdateListener.aidl
package com.xuhj.ipc.server.define;

// Declare any non-default types here with import statements
import com.xuhj.ipc.server.model.User;

interface OnUserUpdateListener {

    void add(in User user);

    void delete(in User user);

}
