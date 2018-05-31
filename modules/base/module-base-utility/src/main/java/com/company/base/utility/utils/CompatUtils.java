package com.company.base.utility.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

/**
 * 兼容工具类
 *
 * @author xuhj
 */
public class CompatUtils {

    public static void makeScaleUpAnimation(Activity activity, Intent intent, View view) {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view,
                view.getWidth() / 2, view.getHeight() / 2, 0, 0);
        ActivityCompat.startActivity(activity, intent, compat.toBundle());
    }

}
