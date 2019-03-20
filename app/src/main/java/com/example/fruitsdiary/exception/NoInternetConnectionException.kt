package com.example.fruitsdiary.exception

import com.example.fruitsdiary.R

class NoInternetConnectionException : FruitDiaryException() {
    init {
        errorMessage = R.string.no_internet_dialog_message
        title = R.string.no_internet_dialog_title
    }
}
