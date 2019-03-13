package com.example.fruitsdiary.data.fruit;

import com.example.fruitsdiary.model.Fruit;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class FruitRepository {

    private final FruitDataSource mFruitDataSource;

    @Inject
    FruitRepository(FruitDataSource fruitDataSource) {
        mFruitDataSource = fruitDataSource;
    }

    public Observable<List<Fruit>> getFruits() {
        return mFruitDataSource.getFruits();
    }
}
