//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xuhj.android.aq.bluetooth;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    protected static char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    protected static MessageDigest messagedigest = null;
    static final char[] hexArray;

    public MD5Utils() {
    }

    public static String getFileMD5String(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(MapMode.READ_ONLY, 0L, file.length());
        messagedigest.update(byteBuffer);
        return bufferToHex(messagedigest.digest());
    }

    public static String getMD5String(String s) {
        return getMD5String(s.getBytes());
    }

    public static String getMD5String(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte[] bytes) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte[] bytes, int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;

        for(int l = m; l < k; ++l) {
            char c0 = hexDigits[(bytes[l] & 240) >> 4];
            char c1 = hexDigits[bytes[l] & 15];
            stringbuffer.append(c0);
            stringbuffer.append(c1);
        }

        return stringbuffer.toString();
    }

    public static String bufferToHex(String text) {
        byte[] arr = new byte[text.length() / 2];

        for(int i = 0; i < text.length() / 2; ++i) {
            String subStr = text.substring(i * 2, i * 2 + 2);
            int byteValue = Integer.valueOf(subStr, 16).intValue();
            arr[i] = (byte)byteValue;
        }

        return getMD5String(arr);
    }

    public static String bytesToHex(byte[] bytes) {
        if(bytes != null && bytes.length > 0) {
            char[] hexChars = new char[bytes.length * 2];

            for(int j = 0; j < bytes.length; ++j) {
                int v = bytes[j] & 255;
                hexChars[j * 2] = hexArray[v >>> 4];
                hexChars[j * 2 + 1] = hexArray[v & 15];
            }

            String str = new String(hexChars);
            return str.toLowerCase();
        } else {
            return "unknownData";
        }
    }

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var1) {
            System.err.println("MD5FileUtil messagedigest初始化失败");
            var1.printStackTrace();
        }

        hexArray = "0123456789ABCDEF".toCharArray();
    }
}
