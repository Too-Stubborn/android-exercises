package com.xuhj.android.base.core.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2018/4/9
 */
public class ActivityUtils {

    /**
     * 附加fragment
     *
     * @param activity        activity
     * @param containerViewId containerViewId
     * @param instance        instance
     * @param tag             tag
     */
    public static void attachFragment(FragmentActivity activity, int containerViewId, Fragment instance, String tag) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment attachedFragment = fragmentManager.findFragmentById(containerViewId);

        if (attachedFragment == null || !attachedFragment.getTag().equals(tag)) {
            //begin transaction
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            // if exists,at first,detach
            if (attachedFragment != null) {
                transaction.detach(attachedFragment);
            }

            Fragment fragment = fragmentManager.findFragmentByTag(tag);
            // if null,then add
            if (fragment == null) {
                fragment = instance;
                transaction.add(containerViewId, fragment, tag);
            } else {
                //if exists,then attach
                transaction.attach(fragment);
            }

            //finally commit
            transaction.commit();
        }
    }

}
