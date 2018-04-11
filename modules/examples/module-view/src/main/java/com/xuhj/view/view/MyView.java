package com.xuhj.view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Scroller;

import com.xuhj.library.util.DensityUtils;

/**
 * 描述
 *
 * @author xuhj
 */
public class MyView extends View {
    private static final String TAG = "MyView";

    private Context mContext;
    private Scroller mScroller;
    private Paint mPaint;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        initPaint();
        mScroller = new Scroller(mContext, new OvershootInterpolator(), true);

    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mPaint.reset();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAlpha(128);
        canvas.drawRect(20, 20, 200, 200, mPaint);

        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(DensityUtils.sp2px(mContext, 20));
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("自定义视图", 20, 60, mPaint);
        super.onDraw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        Log.d(TAG, "onTouchEvent: x,y = " + x + "," + y);

        return dragOnFullScreen(event);
    }

    private int mLastX;
    private int mLastY;

    /**
     * 全屏滑动
     *
     * @param event
     */
    private boolean dragOnFullScreen(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                smoothScrollTo(-300, -600);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastX;
                int dy = y - mLastY;

                int tx = (int) (getTranslationX() + dx);
                int ty = (int) (getTranslationY() + dy);

                setTranslationX(tx);
                setTranslationY(ty);

                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    /**
     * 弹性滑动
     *
     * @param dstX
     * @param dstY
     */
    private void smoothScrollTo(int dstX, int dstY) {
        Log.d(TAG, "smoothScrollTo: ");
        int scrollX = getScrollX();
        int scrollY = getScrollY();

        int dx = dstX - scrollX;
        int dy = dstY - scrollY;
        mScroller.startScroll(scrollX, scrollY, dx, dy, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            Log.d(TAG, "computeScroll: " + mScroller.getCurrX() + "," + mScroller.getCurrY());
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
