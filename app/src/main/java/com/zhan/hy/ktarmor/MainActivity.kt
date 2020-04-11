package com.zhan.hy.ktarmor

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.zhan.hy.ktarmor.account.ui.LoginActivity
import com.zhan.mvp.common.Intent
import com.zhan.mvp.common.Preference
import com.zhan.mvp.ext.startActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.reflect.KProperty


class MainActivity : AppCompatActivity() {

    val p= Pair("1","22")
    val intent = Intent("a","c")
    val list = mutableListOf<String>()
//    val property1 :KProperty<R> = KProperty<>

    //属性代理
    var username2:String by Intent("username","22")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin.setOnClickListener {
//            Settings.password="111212121"
//            Log.i("info","ss--"+Settings.password)
            username2="cccc"
            Log.i("info","ss--"+username2)
            startActivity<LoginActivity>(p)
        }

        btnArticle.setOnClickListener {
        }

        mLoading.setOnClickListener {
            mLoading.start()
        }

        emptyView.setOnClickListener {
            emptyView.triggerNetError("Hello World")
        }
    }
}
