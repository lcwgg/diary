package com.example.fruitsdiary.dagger

import android.content.Context
import androidx.room.Room
import com.example.fruitsdiary.data.AppDatabase

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

    @Provides
    fun getRoomDatabase(context: Context) : AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "database-fruit")
                    .build()

}
