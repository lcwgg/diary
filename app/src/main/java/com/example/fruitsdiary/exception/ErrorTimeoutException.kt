package com.example.fruitsdiary.exception

import com.example.fruitsdiary.R

class ErrorTimeoutException : FruitDiaryException() {
    init {
        errorMessage = R.string.error_timeout_message
    }
}
