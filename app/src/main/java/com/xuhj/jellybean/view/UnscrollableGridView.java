package com.xuhj.jellybean.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 嵌套GridView
 */
public class UnscrollableGridView extends GridView {

    public UnscrollableGridView(Context context) {
        super(context);
    }

    public UnscrollableGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnscrollableGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
