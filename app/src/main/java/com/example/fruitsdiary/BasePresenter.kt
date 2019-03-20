package com.example.fruitsdiary

interface BasePresenter<T : BaseView> {

    fun subscribe()
    fun unsubscribe()
    fun setView(view: T?)

}
