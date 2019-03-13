package com.example.fruitsdiary.data;

import com.example.fruitsdiary.model.Fruit;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class FruitRepository {

    private final FruitDataSource mFruitDataSource;

    @Inject
    public FruitRepository(FruitDataSource fruitDataSource) {
        mFruitDataSource = fruitDataSource;
    }

    public Observable<List<Fruit>> getFruits() {
        return mFruitDataSource.getFruits();
    }
}
