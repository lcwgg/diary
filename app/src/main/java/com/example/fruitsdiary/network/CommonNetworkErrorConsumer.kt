package com.example.fruitsdiary.network

import com.example.fruitsdiary.BaseView
import com.example.fruitsdiary.exception.ErrorFactory
import io.reactivex.functions.Consumer

open class CommonNetworkErrorConsumer(private val mView: BaseView?) : Consumer<Throwable> {

    override fun accept(throwable: Throwable) {
        val exception = ErrorFactory.createFromThrowable(throwable)
        mView?.handleNetworkError(exception)
    }

}
