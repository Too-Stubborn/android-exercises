// IBinderPool.aidl
package com.xuhj.ipc.server;

// Declare any non-default types here with import statements

interface IBinderPool {

    IBinder findBinder(int binderCode);
}
