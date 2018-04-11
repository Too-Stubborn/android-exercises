package com.xuhj.view.interpolator;

import android.graphics.Interpolator;

/**
 * 描述
 *
 * @author xuhj
 */
public class MyInterpolator2 extends Interpolator {
    @Override
    public void reset(int valueCount) {
        super.reset(valueCount);
    }

    @Override
    public void reset(int valueCount, int frameCount) {
        super.reset(valueCount, frameCount);
    }

    @Override
    public void setKeyFrame(int index, int msec, float[] values) {
        super.setKeyFrame(index, msec, values);
    }

    @Override
    public void setKeyFrame(int index, int msec, float[] values, float[] blend) {
        super.setKeyFrame(index, msec, values, blend);
    }

    @Override
    public void setRepeatMirror(float repeatCount, boolean mirror) {
        super.setRepeatMirror(repeatCount, mirror);
    }

    @Override
    public Result timeToValues(float[] values) {
        return super.timeToValues(values);
    }

    @Override
    public Result timeToValues(int msec, float[] values) {
        return super.timeToValues(msec, values);
    }

    public MyInterpolator2(int valueCount) {
        super(valueCount);
    }

    public MyInterpolator2(int valueCount, int frameCount) {
        super(valueCount, frameCount);
    }
}
