package com.xuhj.jellybean.util;

import android.widget.Toast;

import com.xuhj.jellybean.JBApplication;


/**
 * Toast工具
 */
public class ToastUtils {

    public static final void showToast(int resId) {
        Toast.makeText(JBApplication.getInstance(), resId, Toast.LENGTH_SHORT).show();
    }

    public static final void showToast(String text) {
        Toast.makeText(JBApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
    }

}
