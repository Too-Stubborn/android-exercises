package com.xuhj.library.rxbus;

import java.lang.reflect.Method;

/**
 * 订阅方法信息类
 */
class SubscriberMethodInfo {
    private Object subscriber;
    private Method method;
    private Class<?> eventType;
    private int code;
    private ThreadMode threadMode;
    private boolean isStickyEvent;

    /**
     * 订阅者方法信息
     *
     * @param subscriber    订阅者
     * @param method        订阅者的订阅方法
     * @param eventType     订阅的事件类型
     * @param code          自定义的code
     * @param threadMode    线程类型{@link ThreadMode}
     * @param isStickyEvent 是否是粘性事件
     */
    SubscriberMethodInfo(Object subscriber, Method method, Class<?> eventType, int code, ThreadMode threadMode, boolean isStickyEvent) {
        this.method = method;
        this.threadMode = threadMode;
        this.eventType = eventType;
        this.subscriber = subscriber;
        this.code = code;
        this.isStickyEvent = isStickyEvent;
    }

    public Object getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Object subscriber) {
        this.subscriber = subscriber;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getEventType() {
        return eventType;
    }

    public void setEventType(Class<?> eventType) {
        this.eventType = eventType;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }

    public boolean isStickyEvent() {
        return isStickyEvent;
    }

    public void setStickyEvent(boolean stickyEvent) {
        isStickyEvent = stickyEvent;
    }

    @Override
    public String toString() {
        return "SubscriberMethodInfo{" +
                "subscriber=" + subscriber +
                ", method=" + method +
                ", eventType=" + eventType +
                ", code=" + code +
                ", threadMode=" + threadMode +
                ", isStickyEvent=" + isStickyEvent +
                '}';
    }
}
