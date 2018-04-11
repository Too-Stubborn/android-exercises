
# RxBus使用说明

### 接收

注册
``` java
 @Override
 public void onCreate() {
     super.onCreate();
     RxBus.getInstance().register(this);
 }

 @Override
 public void onDestroy() {
     super.onDestroy();
     RxBus.getInstance().unregister(this);
 }
```
在退出app时，移除所有粘性消息，一般在Application的onTerminate();
``` java
RxBus.getInstance().removeAllStickyEvents();
```

接收消息方法
``` java
@Subscribe(code = 1, threadMode = ThreadMode.MAIN, isStickyEvent = false)
public void receiverMethod(Object obj) {
    // 处理逻辑
}
```

注释参数说明：  
code：只接收code相同的发送数据  
threadMode：处理接收的线程  
isStickyEvent：是否是粘性事件  

------

### 发送

发送消息方法
``` java
RxBus.getInstance().post(1, object);
```

参数说明：  
code：只接收code相同的发送数据  
object：发送的数据对象  