package com.example.fruitsdiary.exception

import com.example.fruitsdiary.R

class ErrorCommonException(message: String) : FruitDiaryException(message) {

    init {
        title = R.string.error_title
    }
}
