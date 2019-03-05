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


public class FruitDataSource extends DataSource{

    @Inject
    public FruitDataSource(FruitsDiaryService service, SchedulerProvider provider) {
        super(service, provider);
    }

    public Observable<Fruit> getFruits() {
        return Observable.defer(new Callable<ObservableSource<Fruit>>() {
            @Override
            public ObservableSource<Fruit> call() throws Exception {
                return mService.getAllFruits()
                        .flatMap(new Function<List<Fruit>, ObservableSource<Fruit>>() {
                            @Override
                            public ObservableSource<Fruit> apply(List<Fruit> fruits) throws Exception {
                                return Observable.fromIterable(fruits);
                            }
                        });
            }
        })
                .subscribeOn(mProvider.io())
                .observeOn(mProvider.ui());
    }
}
