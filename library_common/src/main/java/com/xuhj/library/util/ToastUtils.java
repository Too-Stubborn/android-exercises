package com.xuhj.library.util;

import android.content.Context;
import android.widget.Toast;


/**
 * Toast工具
 *
 * @author xuhj
 */
public class ToastUtils {

    public static final void show(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    public static final void show(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

}
