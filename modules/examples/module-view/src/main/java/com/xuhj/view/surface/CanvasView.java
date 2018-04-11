package com.xuhj.view.surface;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Observable;
import java.util.Observer;

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/10/25
 */
public class CanvasView extends SurfaceView implements SurfaceHolder.Callback {

    public CanvasView(Context context) {
        super(context);
        init();
    }

    private void init() {
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    class MyOb implements Observer {
        @Override
        public void update(Observable o, Object arg) {

        }
    }

    class PuOb extends Observable {

        public void push() {
            notifyObservers(1);
        }

    }


}
