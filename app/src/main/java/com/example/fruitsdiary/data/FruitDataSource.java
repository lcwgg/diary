package com.example.fruitsdiary.data;

import com.example.fruitsdiary.model.Fruit;
import com.example.fruitsdiary.network.FruitsDiaryService;
import com.example.fruitsdiary.util.SchedulerProvider;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


public class FruitDataSource extends DataSource {

    @Inject
    public FruitDataSource(FruitsDiaryService service, SchedulerProvider provider) {
        super(service, provider);
    }

    public Observable<List<Fruit>> getFruits() {
        return Observable.defer(new Callable<ObservableSource<List<Fruit>>>() {
            @Override
            public ObservableSource<List<Fruit>> call() throws Exception {
                return mService.getAllFruits();
            }
        })
                .subscribeOn(mProvider.io())
                .observeOn(mProvider.ui());
    }
}
