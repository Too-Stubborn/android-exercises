package com.xuhj.view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * 描述
 *
 * @author xuhj
 */
public class ScrollViewEx3 extends ScrollView {
    private static final String TAG = "ScrollViewEx3";

    private int mLastX = 0;
    private int mLastY = 0;

    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;

    private ListView mListView;

    public ScrollViewEx3(Context context) {
        super(context);
    }

    public ScrollViewEx3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewEx3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent: ");
        // Android4.4以下会有Invalid pointerId=-1报错
        super.onInterceptTouchEvent(ev);
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastXIntercept;
                int dy = y - mLastYIntercept;
                if (dy > 0) {
                    intercepted = !mListView.canScrollVertically(-1);
                } else if (dy < 0) {
                    intercepted = !mListView.canScrollVertically(1);
                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }
        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onTouchEvent: " + ev.getAction());
        return super.onTouchEvent(ev);
    }

    public void setListView(ListView mListView) {
        this.mListView = mListView;
    }
}
