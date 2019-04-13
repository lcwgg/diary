package com.example.fruitsdiary.data.fruit

import com.example.fruitsdiary.model.Fruit
import com.example.fruitsdiary.network.FruitsDiaryService
import com.example.fruitsdiary.util.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject


class FruitRemoteDataSource @Inject
constructor(private val service: FruitsDiaryService,
            private val provider: SchedulerProvider) : FruitDao {

    override fun getFruits(): Flowable<List<Fruit>> =
            service.getAllFruits()
                    .subscribeOn(provider.io())
                    .observeOn(provider.ui())

    override fun insertAll(fruitList: List<Fruit>): Completable {
        // Not available for remote access
        return Completable.complete()
    }
}
