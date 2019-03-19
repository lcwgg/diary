package com.example.fruitsdiary

import com.example.fruitsdiary.exception.FruitDiaryException

interface BaseView {
    fun handleNetworkError(exception: FruitDiaryException)
}
