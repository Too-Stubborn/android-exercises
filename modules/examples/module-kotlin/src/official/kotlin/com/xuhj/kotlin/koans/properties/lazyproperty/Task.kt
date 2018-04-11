package com.xuhj.kotlin.koans.properties.lazyproperty

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/6
 */
class LazyProperty(val initializer: () -> Int) {

    var lazyInit: Int? = null
    val lazy: Int
        get() {
            if (lazyInit == null) {
                lazyInit = initializer()
            }
            return lazyInit ?: 0
        }
}
