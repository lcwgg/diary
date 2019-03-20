package com.example.fruitsdiary.exception

import com.example.fruitsdiary.R

class ErrorUnknownException : FruitDiaryException() {
    init {
        errorMessage = R.string.error_unknown_message
    }
}
