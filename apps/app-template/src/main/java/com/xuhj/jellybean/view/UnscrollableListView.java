package com.xuhj.jellybean.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 嵌套listview
 */
public class UnscrollableListView extends ListView {

    public UnscrollableListView(Context context) {
        super(context);
    }

    public UnscrollableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnscrollableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
