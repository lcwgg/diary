package com.example.fruitsdiary.data.fruit

import com.example.fruitsdiary.model.Fruit
import io.reactivex.Observable
import javax.inject.Inject

class FruitRepository @Inject
internal constructor(private val mFruitDataSource: FruitDataSource) {

    fun getFruits(): Observable<List<Fruit>> = mFruitDataSource.getFruits()
}
