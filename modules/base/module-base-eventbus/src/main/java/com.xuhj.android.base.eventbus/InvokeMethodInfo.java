package com.xuhj.android.base.eventbus;

/**
 * 存储即将被执行的方法的信息
 */
class InvokeMethodInfo {
    private final String TAG = "InvokeMethodInfo";

    private SubscriberMethodInfo subscriberMethodInfo;
    private Object param;

    public InvokeMethodInfo(SubscriberMethodInfo subscriberMethodInfo, Object param) {
        this.subscriberMethodInfo = subscriberMethodInfo;
        this.param = param;
    }

    public SubscriberMethodInfo getSubscriberMethodInfo() {
        return subscriberMethodInfo;
    }

    public void setSubscriberMethodInfo(SubscriberMethodInfo subscriberMethodInfo) {
        this.subscriberMethodInfo = subscriberMethodInfo;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    /**
     * 调用方法
     */
    public void invoke() {
        try {
            subscriberMethodInfo.getMethod().invoke(subscriberMethodInfo.getSubscriber(), param);

        } catch (Exception e) {
            e.printStackTrace();
            RxBusLog.e(TAG, "Exception e:" + e.toString());
        }
    }

    @Override
    public String toString() {
        return "InvokeMethodInfo{" +
                ", subscriberMethodInfo=" + subscriberMethodInfo +
                ", param=" + param +
                '}';
    }
}
