package com.xuhj.kotlin

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/7/6
 */

class User constructor(val iname: String) {

    constructor(iname: String, iage: Int) : this(iname) {
        this.name = iname
        this.age = iage
    }

    /**
     * 初始化代码段使用 init 关键字作为前缀
     */
    init {
        println("初始化代码段$iname")
    }

    /**
     *
     */
    var name: String = ""
        get() {
            return field.toUpperCase()
        }

    /**
     *
     */
    var age: Int = 0
        get() {
            return field
        }
        set(value) {
            if (value < 18) {
                field = value
            } else {
                field = 18
            }
        }
    /**
     *
     */
    var sex: Int = 0

    /**
     *
     */
    var address: String? = null
        get() = field


}