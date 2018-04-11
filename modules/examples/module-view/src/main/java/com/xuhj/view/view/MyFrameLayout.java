package com.xuhj.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * 描述
 *
 * @author xuhj
 */
public class MyFrameLayout extends FrameLayout {
    private static final String TAG = "MyFrameLayout";

    public MyFrameLayout(Context context) {
        super(context);
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
