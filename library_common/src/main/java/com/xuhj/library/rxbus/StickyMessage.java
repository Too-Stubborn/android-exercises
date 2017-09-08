package com.xuhj.library.rxbus;

/**
 * 粘性消息类
 */
class StickyMessage extends Message {
    private int canExecuteTimes = 1;

    /**
     * 构造函数
     *
     * @param canExecuteTimes 可被执行次数，当该事件被执行的次数达到canExecuteTimes时，该事件即会消亡<br/>
     *                        当canExecuteTimes小于0时，该粘性事会一直存在
     * @param o               事件类型
     */
    protected StickyMessage(int canExecuteTimes, int code, Object o) {
        super(code, o);
        this.canExecuteTimes = (canExecuteTimes == 0 ? 1 : canExecuteTimes);
    }

    public int getCanExecuteTimes() {
        return canExecuteTimes;
    }

    public void setCanExecuteTimes(int canExecuteTimes) {
        this.canExecuteTimes = canExecuteTimes;
    }


}
