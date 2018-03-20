// ISecurityProxy.aidl
package com.xuhj.ipc.server;

// Declare any non-default types here with import statements

interface ISecurityProxy {

    String encrypt(String content);

    String decrypt(String content);
}
