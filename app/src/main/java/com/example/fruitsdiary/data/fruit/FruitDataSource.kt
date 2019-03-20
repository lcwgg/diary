package com.example.fruitsdiary.data.fruit

import com.example.fruitsdiary.data.DataSource
import com.example.fruitsdiary.model.Fruit
import com.example.fruitsdiary.network.FruitsDiaryService
import com.example.fruitsdiary.util.SchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject


class FruitDataSource @Inject
internal constructor(service: FruitsDiaryService, provider: SchedulerProvider) : DataSource(service, provider) {

    fun getFruits(): Observable<List<Fruit>> =
            service.getAllFruits()
                    .subscribeOn(provider.io())
                    .observeOn(provider.ui())
}
