package com.example.fruitsdiary.data.fruit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fruitsdiary.model.Fruit
import com.example.fruitsdiary.network.FruitsDiaryService
import com.example.fruitsdiary.util.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

@Dao
interface FruitDao{
    @Query("SELECT * FROM fruit")
    fun getFruits(): Flowable<List<Fruit>>

    @Insert
    fun insertAll(fruitList: List<Fruit>): Completable

}
