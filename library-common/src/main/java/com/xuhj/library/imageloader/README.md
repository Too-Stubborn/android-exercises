
    ImageLoader使用说明

    // ---------------------------------------------
    调用方法如下

    ImageLoader.getInstance().load(context,url,imageView,mode,loadingResId,errorResId);

    ImageLoader.getInstance().load(context,url,imageView,mode);
    // 默认mode = FIT_CENTER
    ImageLoader.getInstance().load(context,url,imageView);

    参数说明：
    context：上下文
    url：图片链接
    imageView：需要加载的View
    mode：加载模式（FIT_CENTER, CENTER_CROP, CIRCLE），默认FIT_CENTER
    loadingResId：加载中图片资源id
    errorResId：加载失败图片资源id