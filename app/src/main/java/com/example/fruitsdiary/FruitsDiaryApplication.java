package com.example.fruitsdiary;

import android.app.Application;
import android.content.Context;

import com.example.fruitsdiary.dagger.AppComponent;
import com.example.fruitsdiary.dagger.ApplicationModule;
import com.example.fruitsdiary.dagger.DaggerAppComponent;
import com.example.fruitsdiary.dagger.NetworkModule;
import com.example.fruitsdiary.dagger.PresenterModule;

public class FruitsDiaryApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }

    public AppComponent initAppComponent()
    {
        mAppComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule())
                .presenterModule(new PresenterModule())
                .build();

        return mAppComponent;
    }

    public static FruitsDiaryApplication get(Context context)
    {
        return (FruitsDiaryApplication) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
