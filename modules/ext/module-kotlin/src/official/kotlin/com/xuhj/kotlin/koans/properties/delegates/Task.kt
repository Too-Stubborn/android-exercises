package com.xuhj.kotlin.koans.properties.delegates

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/6
 */
fun main(args: Array<String>) {
    var cnt = 0
    var lazy = LazyProperty({ cnt++ })
    lazy.lazyValue
    lazy.lazyValue
    println(cnt)
}

class LazyProperty(val initializer: () -> Int) {
    val lazyValue: Int by lazy {
        initializer()
    }
}
//  -----------------------------------------------------------------------------------------------
/**
 * how it works
 */
class D {
    var date: MyDate by EffectiveDate()
}

class EffectiveDate<R> : ReadWriteProperty<R, MyDate> {

    var timeInMillis: Long? = null

    override fun getValue(thisRef: R, property: KProperty<*>): MyDate {
        return timeInMillis!!.toDate()
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: MyDate) {
        timeInMillis = value.toMillis()
    }
}


