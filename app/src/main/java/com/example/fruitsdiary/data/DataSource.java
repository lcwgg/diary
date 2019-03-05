package com.example.fruitsdiary.data;

import com.example.fruitsdiary.network.FruitsDiaryService;
import com.example.fruitsdiary.util.SchedulerProvider;

import javax.inject.Inject;

public abstract class DataSource {

    protected final FruitsDiaryService mService;
    protected final SchedulerProvider mProvider;

    DataSource(FruitsDiaryService service, SchedulerProvider provider) {
        mService = service;
        mProvider = provider;
    }
}
