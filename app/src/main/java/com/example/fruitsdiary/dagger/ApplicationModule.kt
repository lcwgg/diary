package com.example.fruitsdiary.dagger

import android.content.Context

import com.example.fruitsdiary.util.SchedulerProvider

import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(context: Context) {

    internal val context: Context = context.applicationContext

    @Provides
    fun getContext() = context

    @Provides
    fun getSchedulerProvider() = SchedulerProvider()

}
