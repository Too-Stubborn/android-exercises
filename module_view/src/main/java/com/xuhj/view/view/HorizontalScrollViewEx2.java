package com.xuhj.view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 描述
 *
 * @author xuhj
 */
public class HorizontalScrollViewEx2 extends ViewGroup {
    private static final String TAG = "HorizontalScrollViewEx";

    private int mChildrenSize;
    private int mChildWidth;
    private int mChildIndex;

    private int mLastX = 0;
    private int mLastY = 0;

    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public HorizontalScrollViewEx2(Context context) {
        this(context, null);
    }

    public HorizontalScrollViewEx2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScrollViewEx2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure: ");
        int measuredWidth = 0;
        int measuredHeight = 0;
        int childCnt = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        if (childCnt == 0) {
            measuredWidth = widthSpecSize;
            measuredHeight = heightSpecSize;

        } else {
            if (widthSpecMode == MeasureSpec.AT_MOST) {
                for (int i = 0; i < childCnt; i++) {
                    View child = getChildAt(i);
                    measuredWidth += child.getMeasuredWidth();
                }
            } else {
                measuredWidth = widthSpecSize;
            }
            if (heightSpecMode == MeasureSpec.AT_MOST) {
                for (int i = 0; i < childCnt; i++) {
                    View child = getChildAt(i);
                    measuredHeight = Math.max(measuredHeight, child.getMeasuredHeight());
                }
            } else {
                measuredHeight = heightSpecSize;
            }

        }

        if (measuredWidth < getSuggestedMinimumWidth()) {
            measuredWidth = getSuggestedMinimumWidth();
        }
        if (measuredHeight < getSuggestedMinimumHeight()) {
            measuredHeight = getSuggestedMinimumHeight();
        }

//        if (measuredWidth > widthSpecSize) {
//            measuredWidth = widthSpecSize;
//        }
//        if (measuredHeight > heightSpecSize) {
//            measuredHeight = heightSpecSize;
//        }
        setMeasuredDimension(measuredWidth, measuredHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout: ");
        int childLeft = 0;
        int childCnt = getChildCount();
        mChildrenSize = childCnt;
        for (int i = 0; i < childCnt; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                int childWidth = childView.getMeasuredWidth();
                mChildWidth = childWidth;
                childView.layout(childLeft, 0, childLeft + childWidth, childView.getMeasuredHeight());
                childLeft += childWidth;
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
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
            default:
                intercepted = true;
                break;
        }
        mLastX = x;
        mLastY = y;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mVelocityTracker.addMovement(ev);
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastX;
                int dy = y - mLastY;
                scrollBy(-dx, 0);
                break;
            case MotionEvent.ACTION_UP:
//                int scrollX = getScrollX();
//                int scrollToChildIndex = scrollX / mChildWidth;
//                float xVelocity = mVelocityTracker.getXVelocity();
//                if (Math.abs(xVelocity) >= 50) {
//                    mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
//                } else {
//                    mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
//                }
//                mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize));
//                int sx = mChildIndex * mChildWidth - scrollX;
//                smoothScrollBy(sx, 0);
//                mVelocityTracker.clear();
                break;
        }
        mLastX = x;
        mLastY = y;
        int offset = computeHorizontalScrollOffset();
        int sx = getScrollX();
        int range = computeHorizontalScrollRange() - computeHorizontalScrollExtent();
        boolean hl = canScrollHorizontally(-1);
        boolean hr = canScrollHorizontally(1);
        return super.onTouchEvent(ev);
    }

    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
