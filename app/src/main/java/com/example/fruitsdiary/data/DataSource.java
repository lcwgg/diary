package com.example.fruitsdiary.data;

import com.example.fruitsdiary.network.FruitsDiaryService;
import com.example.fruitsdiary.util.SchedulerProvider;

public abstract class DataSource {

    protected final FruitsDiaryService mService;
    protected final SchedulerProvider mProvider;

    protected DataSource(FruitsDiaryService service, SchedulerProvider provider) {
        mService = service;
        mProvider = provider;
    }
}
