package com.zhan.mvp.mvp

import android.support.annotation.StringRes
import com.zhan.mvp.bean.KResponse
import com.zhan.mvp.constant.Const
import com.zhan.mvp.ext.showLog
import com.zhan.mvp.ext.tryCatch
import kotlinx.coroutines.*
import java.lang.ref.WeakReference

/**
 *  @author: hyzhan
 *  @date:   2019/5/16
 *  @desc:   基本 BasePresenter, 封装基本请求方法, view对象, 以及基本操作 view方法
 */
abstract class BasePresenter<V : BaseContract.View>(view: V) : BaseContract.Presenter {

    val view: V?
        get() = mViewRef.get()

    // View 接口类型的弱引用
    private var mViewRef = WeakReference(view)

    val presenterScope: CoroutineScope by lazy {
        CoroutineScope(Dispatchers.Main + Job())
    }

    fun launchUI(block: suspend CoroutineScope.() -> Unit, error: ((Throwable) -> Unit)? = null) {
        presenterScope.launch {
            tryCatch({
                block()
            }, {
                error?.invoke(it) ?: showException(it.toString())
            })
        }
    }

    fun <R> quickLaunch(block: Execute<R>.() -> Unit) {
        Execute<R>().apply(block)
    }

    private fun showException(exception: String) {
        exception.showLog()
        view?.showError(Const.NETWORK_ERROR)
    }

    override fun showError(msg: String) {
        view?.showError(msg)
    }

    override fun showError(@StringRes strRes: Int) {
        view?.showError(strRes)
    }

    override fun showLoading() {
        view?.showLoading()
    }

    override fun detachView() {
        mViewRef.clear()
        // 取消掉 presenterScope创建的所有协程和其子协程。
        presenterScope.cancel()
    }


    inner class Execute<R> {

        private var successBlock: ((R?) -> Unit)? = null
        private var failBlock: ((String?) -> Unit)? = null
        private var exceptionBlock: ((Throwable?) -> Unit)? = null

        fun request(block: suspend CoroutineScope.() -> KResponse<R>?) {
            launchUI({ block()?.execute(successBlock, failBlock) }, exceptionBlock)
        }

        fun onSuccess(block: (R?) -> Unit) {
            this.successBlock = block
        }

        fun onFail(block: (String?) -> Unit) {
            this.failBlock = block
        }

        fun onException(block: (Throwable?) -> Unit) {
            this.exceptionBlock = block
        }
    }
}