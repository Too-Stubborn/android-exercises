
    RxBus使用说明

    // ---------------------------------------------
    接收方法

    示例如下：
    @Subscribe(code = 1, threadMode = ThreadMode.MAIN, isStickyEvent = false)
    public void receiverMethod(Object obj) {
        // 处理逻辑
    }

    注释参数说明：
    code：只接收code相同的发送数据
    threadMode：处理接收的线程
    isStickyEvent：是否是粘性事件


    // ---------------------------------------------
    发送方法
    RxBus.getInstance().post(1, object);

    参数说明：
    code：只接收code相同的发送数据
    object：发送的数据对象