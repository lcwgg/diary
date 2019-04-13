package com.example.fruitsdiary.data.fruit

import com.example.fruitsdiary.model.Fruit
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class FruitRepository
@Inject constructor(
        private val fruitRemoteDataSource: FruitRemoteDataSource,
        private val fruitLocalDataSource: FruitLocalDataSource) {

    fun getFruits(): Flowable<List<Fruit>> = fruitRemoteDataSource.getFruits()
                .onErrorResumeNext(fruitLocalDataSource.getFruits())

    fun insertAll(fruitList: List<Fruit>): Completable = fruitLocalDataSource.insertAll(fruitList)

}
