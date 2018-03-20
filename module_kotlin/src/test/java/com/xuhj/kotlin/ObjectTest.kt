package com.xuhj.kotlin

import org.junit.Test

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/7/6
 */
class ObjectTest {

    @Test
    fun main() {

        var user: User = User("tencent")
        user.name = "xuhaojie"
        user.age = 28
        user.sex = 1
        user.address = "hangzhou"

        println("name is ${user.name}" +
                "\nage is ${user.age}" +
                "\nsex is ${user.sex}" +
                "\naddress is ${user.address}")

    }
}

object MyEmpty {
    @JvmStatic fun foo() {
        println("foo")
    }
}
