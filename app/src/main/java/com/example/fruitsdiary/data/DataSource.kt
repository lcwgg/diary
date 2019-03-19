package com.example.fruitsdiary.data

import com.example.fruitsdiary.network.FruitsDiaryService
import com.example.fruitsdiary.util.SchedulerProvider

abstract class DataSource(
        protected val service: FruitsDiaryService,
        protected val provider: SchedulerProvider)
