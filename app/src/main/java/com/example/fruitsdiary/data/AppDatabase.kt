package com.example.fruitsdiary.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fruitsdiary.data.fruit.FruitDao
import com.example.fruitsdiary.model.Fruit

@Database(entities = arrayOf(Fruit::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fruitDao(): FruitDao
}