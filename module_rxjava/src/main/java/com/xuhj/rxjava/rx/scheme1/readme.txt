
    http://johnnyshieh.github.io/android/2017/03/10/rxbus-rxjava2/

    基于 RxJava 的 RxBus 作为一种事件总线，相信许多人都了解一些，Square 的 Otto 也因此弃用，因为现在 RxJava 太火了，用它几行代码就可以写出事件总线。不过大家所熟悉的是基于 RxJava 1.x 版本的，2016 年十月底 RxJava 更新到 2.x 版本了，具体变化请看 What’s different in 2.0，下面总结下适合不同场景的 RxJava 2 版本的 RxBus 写法。

    没有背压处理（Backpressure）的 Rxbus
    有背压处理的 RxBus
    有异常处理的 Rxbus （订阅者处理事件出现异常也能继续收到事件）