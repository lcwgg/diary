package com.example.fruitsdiary

import android.app.Application
import android.content.Context

import com.example.fruitsdiary.dagger.AppComponent
import com.example.fruitsdiary.dagger.ApplicationModule
import com.example.fruitsdiary.dagger.DaggerAppComponent
import com.example.fruitsdiary.dagger.NetworkModule
import com.example.fruitsdiary.dagger.PresenterModule

class FruitsDiaryApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
                .applicationModule(ApplicationModule(this))
                .networkModule(NetworkModule())
                .presenterModule(PresenterModule())
                .build()
    }

    companion object {

        operator fun get(context: Context): FruitsDiaryApplication {
            return context.applicationContext as FruitsDiaryApplication
        }
    }
}
