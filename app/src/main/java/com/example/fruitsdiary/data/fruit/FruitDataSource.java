package com.example.fruitsdiary.data.fruit;

import com.example.fruitsdiary.data.DataSource;
import com.example.fruitsdiary.model.Fruit;
import com.example.fruitsdiary.network.FruitsDiaryService;
import com.example.fruitsdiary.util.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


public class FruitDataSource extends DataSource {

    @Inject
    FruitDataSource(FruitsDiaryService service, SchedulerProvider provider) {
        super(service, provider);
    }

    public Observable<List<Fruit>> getFruits() {
        return mService.getAllFruits()
                .subscribeOn(mProvider.io())
                .observeOn(mProvider.ui());
    }
}
