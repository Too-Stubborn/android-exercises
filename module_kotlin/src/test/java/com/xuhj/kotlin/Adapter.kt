package com.xuhj.kotlin

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/7/7
 */

abstract class BaseAdapter {

    open fun getSize(): Int {
        return 0
    }

    open fun getItem() {

    }

    open fun getCount(): Int {

        return 0
    }
}

abstract class SimpleAdapter : BaseAdapter() {

    override fun getSize(): Int {
        return super.getSize()
    }

    override fun getItem() {
        super.getItem()
    }

    override fun getCount(): Int {
        return super.getCount()
    }

    abstract fun getView()
}

class SimpleAdapterImpl : SimpleAdapter() {

    override fun getSize(): Int {
        return super.getSize()
    }

    override fun getItem() {
        super.getItem()
    }

    override fun getCount(): Int {
        return super.getCount()
    }

    override fun getView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class Outer {

    var color: Int = 0xFFFFFF

    class Nested {
        var size: Int = 14
        fun foo() = 250

    }

    inner class InnerNested {
        var width: Int = 0

        fun changeColor() {
            color = 0x000000
        }

    }

    fun setListener(listener: OnClicklistener) {
        listener.onClick()
    }

}

interface OnClicklistener  {

    fun onClick()

}

annotation class ActivityScope
