package com.xuhj.kotlin.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.xuhj.kotlin.R
import kotlinx.android.synthetic.main.activity_kotlin.*

fun main(args: Array<String>) {
    var scanner = System.`in`
    print(scanner)
}

class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        etUserName.setHint("请输入用户名")
        etPassword.setHint("请输入密码")

        btnLogin.setOnClickListener { Toast.makeText(this, "", Toast.LENGTH_SHORT) }

        var ary = arrayOf(1, 2, 3, 4)
        for (indice in ary.indices) {
        }
    }
}


fun KotlinActivity.test(vararg vg: Int) {
    for (vt in vg) {
        print(vt)
    }
}