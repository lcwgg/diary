package com.example.fruitsdiary

import android.app.Application
import android.content.Context
import com.example.fruitsdiary.dagger.*

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
