package com.example.fruitsdiary.data.fruit

import com.example.fruitsdiary.model.Fruit

import javax.inject.Inject

import io.reactivex.Observable

class FruitRepository @Inject
internal constructor(private val mFruitDataSource: FruitDataSource) {

    fun getFruits(): Observable<List<Fruit>> = mFruitDataSource.getFruits()
}
