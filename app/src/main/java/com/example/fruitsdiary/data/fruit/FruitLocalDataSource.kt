package com.example.fruitsdiary.data.fruit

import com.example.fruitsdiary.data.AppDatabase
import com.example.fruitsdiary.model.Fruit
import com.example.fruitsdiary.util.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject


class FruitLocalDataSource @Inject
constructor(database: AppDatabase,
            private val provider: SchedulerProvider) : FruitDao {

    private val fruitDao: FruitDao = database.fruitDao()

    override fun getFruits(): Flowable<List<Fruit>> =
            fruitDao.getFruits()
                    .subscribeOn(provider.io())
                    .observeOn(provider.ui())

    override fun insertAll(fruitList: List<Fruit>): Completable =
            fruitDao.insertAll(fruitList)
                    .subscribeOn(provider.io())
                    .observeOn(provider.ui())
}
